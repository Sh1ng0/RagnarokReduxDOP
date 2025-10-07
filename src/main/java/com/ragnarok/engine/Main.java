package com.ragnarok.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
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

        var daggerTemplate = new WeaponTemplate(
                1201L, "Dagger", Element.NEUTRAL, WeaponType.DAGGER, 0.7,
                new EquipmentBonuses(null, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0, null, null), // Usando el constructor canónico por claridad
                1, List.of("THIEF","SWORDMAN"), 1, List.of(WeaponType.DAGGER,WeaponType.ONE_HANDED_SWORD, ArmorType.SHIELD)
        );

        var redPotionTemplate = new ConsumableTemplate(
                1501L,
                "Red Potion",
                List.of(new HealEffect(HP,50)) // Asumimos que HealEffect es un record que contiene la cantidad a curar
        );

        // --- Proceso de Generación de JSON ---

        ObjectMapper mapper = new ObjectMapper();

        try {
            // 1. Generamos y mostramos el JSON para la Daga
            System.out.println("--- Dagger JSON (id: 1201) ---");
            String daggerJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(daggerTemplate);
            System.out.println(daggerJson);

            System.out.println("\n"); // Añadimos un separador para mayor claridad

            // 2. Generamos y mostramos el JSON para la Poción Roja
            System.out.println("--- Red Potion JSON (id: 1501) ---");
            String potionJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(redPotionTemplate);
            System.out.println(potionJson);

        } catch (JsonProcessingException e) {
            System.err.println("Error al generar el JSON:");
            e.printStackTrace();
        }
    }
}