package com.ragnarok.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragnarok.engine.db.DatabaseManager;
import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.item.consumable.model.HealEffect;
import com.ragnarok.engine.item.equip.model.ArmorType;
import com.ragnarok.engine.item.equip.model.EquipmentBonuses;
import com.ragnarok.engine.item.equip.model.WeaponType;
import com.ragnarok.engine.item.template.ConsumableTemplate;
import com.ragnarok.engine.item.template.WeaponTemplate;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Map;

import static com.ragnarok.engine.item.consumable.enums.Stat.HP;


public class Main {
    public static void main(String[] args) {

//        System.out.println("Intentando inicializar el pool de conexiones...");
//
//        try {
//            // Esta simple llamada fuerza la ejecución del bloque static en DatabaseManager
//            DSLContext jooq = DatabaseManager.getJooqContext();
//
//            System.out.println("¡Éxito! HikariCP se ha inicializado y conectado a la base de datos.");
//            System.out.println("Contexto de jOOQ listo para usar.");
//
//        } catch (Exception e) {
//            System.err.println("¡Error! No se pudo inicializar la conexión a la base de datos.");
//            e.printStackTrace();
//        } finally {
//            // Cierra el pool al terminar la prueba
//            DatabaseManager.closeDataSource();
//        }
//    }


        // A unified ActorProfile reflecting all the data needed for combat interaction
        // Work on CharacterData and MonsterFactory regarding the above

        // *Actor state of a character must mirror that of a monster

        // --- Plantillas de los ítems que queremos visualizar ---

        // --- Template for the Dagger ---
        var daggerTemplate = new WeaponTemplate(
                1201L, "Dagger", Element.NEUTRAL, WeaponType.DAGGER, 0.7,
                new EquipmentBonuses(null, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0, null, null),
                1, List.of("THIEF","SWORDMAN"), 1, List.of(WeaponType.DAGGER,WeaponType.ONE_HANDED_SWORD, ArmorType.SHIELD)
        );

        // --- Template for the Red Potion ---
        var redPotionTemplate = new ConsumableTemplate(
                1501L, "Red Potion", List.of(new HealEffect(HP, 50))
        );

        // --- JSON Generation Process ---
        ObjectMapper mapper = new ObjectMapper();

        try {
            // 1. Generate JSON for the Dagger
            System.out.println("--- Dagger JSON (id: 1201) ---");

            // Convert the record to a Map to add the extra field
            Map<String, Object> daggerMap = mapper.convertValue(daggerTemplate, new TypeReference<>() {});

            // Inject the new field required by the deserializer
            daggerMap.put("equipmentCategory", "WEAPON");

            // Serialize the modified map to JSON
            String daggerJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(daggerMap);
            System.out.println(daggerJson);

            System.out.println("\n");

            // 2. Generate JSON for the Red Potion (no changes needed here)
            System.out.println("--- Red Potion JSON (id: 1501) ---");
            String potionJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(redPotionTemplate);
            System.out.println(potionJson);

        } catch (JsonProcessingException e) {
            System.err.println("Error generating JSON:");
            e.printStackTrace();
        }
    }
}