-- Elimina la tabla si ya existe para asegurar un estado limpio en cada test.
DROP TABLE IF EXISTS `item_templates`;

-- Tabla para los "planos" de todos los ítems del juego.
CREATE TABLE `item_templates` (
    `id` BIGINT PRIMARY KEY,
    `category` VARCHAR(50) NOT NULL,
    `data` JSON NOT NULL,
    INDEX `idx_category` (`category`)
);

