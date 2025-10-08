-- data.sql

-- 1. Insertamos la Daga (ID: 1201) con los datos actualizados
INSERT INTO `item_templates` (id, category, data)
VALUES (
    1201,
    'EQUIPMENT',
    -- JSON actualizado con las nuevas reglas de 'equippableJobs' y 'compatibleOffHandTypes'
    '{
      "id" : 1201,
      "name" : "Dagger",
      "equipmentCategory": "WEAPON",
      "element" : "NEUTRAL",
      "type" : "DAGGER",

      "speedRatio" : 0.7,
      "bonuses" : {
        "primaryStats" : null,
        "maxHp" : 0,
        "maxSp" : 0,
        "attack" : 15,
        "defense" : 0,
        "magicAttack" : 0,
        "magicDefense" : 0,
        "criticalRate" : 0,
        "flee" : 0,
        "hit" : 0,
        "maxHpPercent" : 0.0,
        "maxSpPercent" : 0.0,
        "attackPercent" : 0.0,
        "grantSkills" : null,
        "autocastEffects" : null
      },
      "requiredLevel" : 1,
      "equippableJobs" : [ "THIEF", "SWORDMAN" ],
      "cardSlots" : 1,
      "compatibleOffHandTypes" : [ "DAGGER", "ONE_HANDED_SWORD", "SHIELD" ]
    }'
);

-- 2. Insertamos la Poción Roja (ID: 1501)
-- (Este INSERT no cambia)
INSERT INTO `item_templates` (id, category, data)
VALUES (
    1501,
    'CONSUMABLE',
    '{
      "id" : 1501, "name" : "Red Potion",
      "effects" : [ { "stat" : "HP", "amount" : 50 } ]
    }'
);