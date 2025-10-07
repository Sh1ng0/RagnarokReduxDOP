package com.ragnarok.engine.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragnarok.engine.item.equip.model.WeaponType;
import com.ragnarok.engine.item.template.*;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import java.util.Optional;

// DEUDA TECNICA MIRAR BIEN EL DESERIALIZADOR DE ARMAS/ARMADURAS DEBE MEJORARSE!

import static com.ragnarok.engine.db.generated.tables.ItemTemplates.ITEM_TEMPLATES;

/**
 * A database-driven implementation of the item template repository using jOOQ.
 */
public class DbItemTemplateRepository {


    private static final Logger logger = LoggerFactory.getLogger(DbItemTemplateRepository.class);
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

        RepositoryLogEvent.SEARCHING_BY_ID.log(logger, id);
        // JOOQ for the magical type safe query
        org.jooq.Record dbRecord  = jooq.select(ITEM_TEMPLATES.CATEGORY, ITEM_TEMPLATES.DATA)
                .from(ITEM_TEMPLATES)
                .where(ITEM_TEMPLATES.ID.eq(id))
                .fetchOne();


        if (dbRecord == null) {
            RepositoryLogEvent.RECORD_NOT_FOUND_BY_ID.log(logger, id);
            return Optional.empty();
        }

        // Extract data from the jooq record
        String category = dbRecord.get(ITEM_TEMPLATES.CATEGORY);
        String jsonData = dbRecord.get(ITEM_TEMPLATES.DATA).data();
        RepositoryLogEvent.RECORD_FOUND_BY_ID.log(logger, id, category);

        // We decide through "category" the kind of record we need the deserialization to coalesce into,
        // Jackson does the rest
        try {
            ItemTemplate template = switch (category) {
                case "CONSUMABLE" -> objectMapper.readValue(jsonData, ConsumableTemplate.class);
                case "EQUIPMENT" -> deserializeEquipment(jsonData); // Special handling for sealed interface
                case "CARD" -> objectMapper.readValue(jsonData, CardTemplate.class);
                case "MISC" -> objectMapper.readValue(jsonData, MiscTemplate.class);
                default -> {RepositoryLogEvent.UNKNOWN_CATEGORY.log(logger, category, id);
                    throw new IllegalStateException("Unknown item category in DB: " + category);
                }
            };
            RepositoryLogEvent.DESERIALIZATION_SUCCESS.log(logger, id, template.getClass().getSimpleName());
            return Optional.of(template);
        } catch (IOException e) {
            // This error is critical. It means our DB data is corrupt or out of sync with our code.
            RepositoryLogEvent.DESERIALIZATION_FAILED.log(logger, id, e.getMessage());

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
            case DAGGER,
                 ONE_HANDED_SWORD,
                 TWO_HANDED_SWORD,
                 KATAR,
                 BOW,
                 GUN,
                 KNUCKLE,
                 ONE_HANDED_ROD,
                 SPEAR,
                 ONE_HANDED_AXE,
                 BOOK,
                 HUUMA_SHURIKEN,
                 INSTRUMENT,
                 TWO_HANDED_ROD ->
                    objectMapper.treeToValue(node, WeaponTemplate.class);
            default ->
                // We assume if it's not a known weapon type, it must be an armor type.
                // A more robust solution might use a different field for armor vs weapon.
                    objectMapper.treeToValue(node, ArmorTemplate.class);
        };
    }
}