Item table

CREATE TABLE `item_templates` (
    -- El ID único del item, que coincide con el de nuestros records.
    -- Es la clave primaria para búsquedas ultra-rápidas.
    `id` BIGINT PRIMARY KEY,

    -- La categoría del item. La guardamos fuera del JSON para poder
    -- hacer búsquedas y filtros eficientes (ej: "dame todas las armas").
    `category` VARCHAR(50) NOT NULL,

    -- Aquí guardaremos el resto del objeto ItemTemplate (Weapon, Armor, etc.)
    -- serializado como un string JSON.
    `data` JSON NOT NULL,

    -- Creamos un índice en la categoría para acelerar las búsquedas por este campo.
    INDEX `idx_category` (`category`)
);