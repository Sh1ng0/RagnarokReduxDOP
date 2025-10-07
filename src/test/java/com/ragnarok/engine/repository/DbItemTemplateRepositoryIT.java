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

        // Assert: Verify the outcome is correct.
        // 1. Ensure the Optional is not empty.
        assertThat(result).isPresent();

        // 2. Confirm the object is the correct type (a WeaponTemplate).
        // This proves our deserialization logic based on 'category' works.
        ItemTemplate item = result.get();
        assertThat(item).isInstanceOf(WeaponTemplate.class);

        // 3. Cast to the specific type to check detailed properties.
        WeaponTemplate dagger = (WeaponTemplate) item;

        // 4. Verify the data was deserialized correctly from the JSON.
        assertThat(dagger.id()).isEqualTo(1201L);
        assertThat(dagger.name()).isEqualTo("Dagger");
        assertThat(dagger.bonuses().attack()).isEqualTo(15);
        assertThat(dagger.equippableJobs()).containsExactly("THIEF", "SWORDMAN");
        assertThat(dagger.compatibleOffHandTypes()).containsExactlyInAnyOrder(
                WeaponType.DAGGER, WeaponType.ONE_HANDED_SWORD, ArmorType.SHIELD
        );
    }
}