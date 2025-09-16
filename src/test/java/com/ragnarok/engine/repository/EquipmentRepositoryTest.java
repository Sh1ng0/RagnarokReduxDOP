package com.ragnarok.engine.repository;

import com.ragnarok.engine.item.equip.model.Equipment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Pruebas para el Repositorio de Equipo")
class EquipmentRepositoryTest {

    @Test
    @DisplayName("debería encontrar un item existente por su ID")
    void shouldFindExistingItemById() {
        // ARRANGE
        long cottonShirtId = 1001L;

        // ACT
        Equipment foundItem = EquipmentRepository.findById(cottonShirtId);

        // ASSERT
        assertThat(foundItem).isNotNull();
        assertThat(foundItem.id()).isEqualTo(cottonShirtId);
        assertThat(foundItem.name()).isEqualTo("Cotton Shirt");
    }

    @Test
    @DisplayName("debería lanzar una excepción para un ID de item no existente")
    void shouldThrowExceptionForNonExistentItemId() {
        // ARRANGE
        long nonExistentId = 9999L;

        // ACT & ASSERT
        // Verificamos que la llamada a findById con un ID inválido
        // lanza exactamente la excepción que esperamos.
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> EquipmentRepository.findById(nonExistentId)
        );

        // Opcional: Verificar que el mensaje de error es el correcto.
        assertThat(exception.getMessage()).isEqualTo("No se encontró ningún EquipmentItem con el ID: " + nonExistentId);
    }

    @Test
    @DisplayName("debería encontrar una lista de items existentes por sus IDs")
    void shouldFindMultipleItemsByIds() {
        // ARRANGE
        List<Long> itemIds = List.of(1001L, 2001L);

        // ACT
        List<Equipment> foundItems = EquipmentRepository.findByIds(itemIds);

        // ASSERT
        assertThat(foundItems).isNotNull();
        assertThat(foundItems).hasSize(2);
        // Extraemos los nombres para verificar que son los items correctos.
        assertThat(foundItems).extracting(Equipment::name)
                .containsExactlyInAnyOrder("Cotton Shirt", "Knife");
    }

    @Test
    @DisplayName("debería devolver una lista vacía si se le pasa una lista de IDs nula o vacía")
    void shouldReturnEmptyListForEmptyOrNullIdList() {
        // ARRANGE
        List<Long> emptyList = Collections.emptyList();
        List<Long> nullList = null;

        // ACT
        List<Equipment> resultFromEmpty = EquipmentRepository.findByIds(emptyList);
        List<Equipment> resultFromNull = EquipmentRepository.findByIds(nullList);

        // ASSERT
        assertThat(resultFromEmpty).isNotNull().isEmpty();
        assertThat(resultFromNull).isNotNull().isEmpty();
    }
}