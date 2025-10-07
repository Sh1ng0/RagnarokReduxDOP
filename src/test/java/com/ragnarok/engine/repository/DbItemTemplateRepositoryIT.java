package com.ragnarok.engine.repository;

import com.ragnarok.engine.item.equip.model.ArmorType;
import com.ragnarok.engine.item.equip.model.WeaponType;
import com.ragnarok.engine.item.template.ItemTemplate;
import com.ragnarok.engine.item.template.WeaponTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the DbItemTemplateRepository class.
 * These tests run against a real MySQL database managed by Testcontainers.
 */
class DbItemTemplateRepositoryIT extends BaseIntegrationTest {

    @Test
    @DisplayName("findById should return correct template when ID exists")
    void findById_shouldReturnCorrectTemplate_whenIdExists() {
        // Arrange
        long existingId = 1201L;

        // Act
        Optional<ItemTemplate> result = dbItemTemplateRepository.findById(existingId);

        // Assert

        assertThat(result).isPresent();


        ItemTemplate item = result.get();
        assertThat(item).isInstanceOf(WeaponTemplate.class);


        WeaponTemplate dagger = (WeaponTemplate) item;


        assertThat(dagger.id()).isEqualTo(1201L);
        assertThat(dagger.name()).isEqualTo("Dagger");
        assertThat(dagger.bonuses().attack()).isEqualTo(15);
        assertThat(dagger.equippableJobs()).containsExactly("THIEF", "SWORDMAN");
        assertThat(dagger.compatibleOffHandTypes()).containsExactlyInAnyOrder(
                WeaponType.DAGGER, WeaponType.ONE_HANDED_SWORD, ArmorType.SHIELD
        );
    }


    @Test
    @DisplayName("findById should return empty when ID does not exist")
    void findById_shouldReturnEmpty_whenIdDoesNotExist() {
        // Arrange
        long nonExistentId = 9999L;

        // Act
        Optional<ItemTemplate> result = dbItemTemplateRepository.findById(nonExistentId);

        // Assert
        assertThat(result).isEmpty();
    }
}