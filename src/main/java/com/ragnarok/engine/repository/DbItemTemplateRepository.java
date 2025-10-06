package com.ragnarok.engine.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragnarok.engine.item.equip.model.WeaponType;
import com.ragnarok.engine.item.template.*;
import org.jooq.DSLContext;
import java.io.IOException;

import java.util.Optional;

import static com.ragnarok.engine.db.generated.tables.ItemTemplates.ITEM_TEMPLATES;

/**
 * A database-driven implementation of the item template repository using jOOQ.
 */
public class DbItemTemplateRepository {

    // This thingy is the one that makes the queries
    private final DSLContext jooq;

   // This thingy turns stuff into JSONs
    private final ObjectMapper objectMapper;


    public DbItemTemplateRepository(DSLContext jooq, ObjectMapper objectMapper) {
        this.jooq = jooq;
        this.objectMapper = objectMapper;
    }


    /**
     * Finds an item template by its ID in the database.
     * @param id The ID of the item to find.
     * @return An Optional containing the ItemTemplate if found, otherwise empty.
     */
    public Optional<ItemTemplate> findById(long id) {
        // Step 1: Use jOOQ to build and execute the SQL query.
        // We ask for the row where the 'id' column matches our parameter.
        // fetchOne() retrieves at most one record.
        org.jooq.Record dbRecord  = jooq.select(ITEM_TEMPLATES.CATEGORY, ITEM_TEMPLATES.DATA)
                .from(ITEM_TEMPLATES)
                .where(ITEM_TEMPLATES.ID.eq(id))
                .fetchOne();

        // Step 2: Handle the case where the item was not found.
        if (dbRecord == null) {
            return Optional.empty();
        }

        // Step 3: Extract the raw data from the jOOQ Record.
        String category = dbRecord.get(ITEM_TEMPLATES.CATEGORY);
        String jsonData = dbRecord.get(ITEM_TEMPLATES.DATA).data();

        // Step 4: Use Jackson to deserialize the JSON string into the correct Java Record.
        // This is where we use the 'category' to decide which class to create.
        try {
            ItemTemplate template = switch (category) {
                case "CONSUMABLE" -> objectMapper.readValue(jsonData, ConsumableTemplate.class);
                case "EQUIPMENT" -> deserializeEquipment(jsonData); // Special handling for sealed interface
                case "CARD" -> objectMapper.readValue(jsonData, CardTemplate.class);
                case "MISC" -> objectMapper.readValue(jsonData, MiscTemplate.class);
                default -> throw new IllegalStateException("Unknown item category in DB: " + category);
            };
            return Optional.of(template);
        } catch (IOException e) {
            // This error is critical. It means our DB data is corrupt or out of sync with our code.
            // We wrap it in a RuntimeException because we can't recover from it here.
            throw new RuntimeException("Failed to deserialize item template JSON for id: " + id, e);
        }
    }


    /**
     * Helper method to handle the polymorphic nature of EquipmentTemplate.
     */
    private EquipmentTemplate deserializeEquipment(String jsonData) throws IOException {
        // First, parse into a generic JSON tree to inspect its contents
        JsonNode node = objectMapper.readTree(jsonData);

        // Then, get the 'type' field to know if it's a weapon or armor
        String type = node.get("type").asText();

        // Finally, deserialize into the specific record class
        return switch (WeaponType.valueOf(type)) {
            case DAGGER, ONE_HANDED_SWORD, TWO_HANDED_SWORD, KATAR, BOW -> // Add all weapon types here
                    objectMapper.treeToValue(node, WeaponTemplate.class);
            default ->
                // We assume if it's not a known weapon type, it must be an armor type.
                // A more robust solution might use a different field for armor vs weapon.
                    objectMapper.treeToValue(node, ArmorTemplate.class);
        };
    }
}