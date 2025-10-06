package com.ragnarok.engine.item.inventory.service;

import com.ragnarok.engine.item.ItemCategory;
import com.ragnarok.engine.item.instance.EquipInstance;
import com.ragnarok.engine.item.instance.ItemStack;
import com.ragnarok.engine.item.inventory.model.CharacterInventories;
import com.ragnarok.engine.item.template.ConsumableTemplate;
import com.ragnarok.engine.item.template.WeaponTemplate;
import com.ragnarok.engine.repository.ItemTemplateRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

// With this anotation every mutable object will be instanced only ONCE, it will notbe stathic
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InventoryServiceTest {


    private InventoryService inventoryService;


    private static final ConsumableTemplate RED_POTION_TEMPLATE = new ConsumableTemplate(1501L, "Red Potion", List.of());
    private static final WeaponTemplate DAGGER_TEMPLATE = new WeaponTemplate(1201L, "Dagger", null, null, 0, null, 1, List.of(), 1);
    private static final ConsumableTemplate BLUE_POTION_TEMPLATE = new ConsumableTemplate(1502L, "Blue Potion", List.of());


    private ItemStack bluePotionStack;
    private EquipInstance daggerInstance;
    private ItemStack tenRedPotions;
    private ItemStack fiveRedPotions;


    @BeforeAll
    void setUp() {

        ItemTemplateRepository itemTemplateRepository = ItemTemplateRepository.INSTANCE;

        itemTemplateRepository.addTemporaryTemplate(BLUE_POTION_TEMPLATE.id(), ItemCategory.CONSUMABLE);

        inventoryService = new InventoryService(itemTemplateRepository);


        daggerInstance = new EquipInstance(DAGGER_TEMPLATE);
        tenRedPotions = new ItemStack(RED_POTION_TEMPLATE.id(), 10);
        fiveRedPotions = new ItemStack(RED_POTION_TEMPLATE.id(), 5);
        bluePotionStack = new ItemStack(BLUE_POTION_TEMPLATE.id(), 1);

    }



    @Test
    @DisplayName("Test 1: Añadir un nuevo stack a un inventario vacío")
    void shouldAdd_NewItemStack_ToEmptyInventory() {
        // ARRANGE
        CharacterInventories initialState = CharacterInventories.EMPTY;



        // ACT
        InventoryUpdateResult result = inventoryService.addItem(initialState, tenRedPotions);
        CharacterInventories finalState = result.updatedInventories();



        // ASSERT

        assertThat(finalState).isNotNull().isNotEqualTo(initialState);


        var consumables = finalState.consumables().items();
        assertThat(consumables).hasSize(1);


        assertThat(consumables).containsKey(RED_POTION_TEMPLATE.id());
        assertThat(consumables).containsValue(tenRedPotions);


        assertThat(finalState.equipment().items()).isEmpty();
        assertThat(finalState.cards().items()).isEmpty();
        assertThat(finalState.miscellaneous().items()).isEmpty();
    }


    @DisplayName(" Test 2: Apilar ítems sobre un stack existente")
    @Test
    void shouldStack_Items_OnExistingStack() {
       // ARRANGE
        CharacterInventories initialState = inventoryService.addItem(CharacterInventories.EMPTY, tenRedPotions).updatedInventories();

       // ACT
        InventoryUpdateResult result = inventoryService.addItem(initialState, fiveRedPotions);
        CharacterInventories finalState = result.updatedInventories();

       // ASSERT
        assertThat(finalState).isNotNull().isNotEqualTo(initialState);

        var consumables = finalState.consumables().items();
        assertThat(consumables).hasSize(1);


        ItemStack potionStack = consumables.get(RED_POTION_TEMPLATE.id());
        assertThat(potionStack).isNotNull();


        assertThat(potionStack.quantity()).isEqualTo(15);
    }


    @DisplayName("✅ Test 3: Añadir un equipo único (EquipInstance)")
    @Test
    void shouldAdd_UniqueEquipInstance_ToEmptyInventory() {
        // ARRANGE
        CharacterInventories initialState = CharacterInventories.EMPTY;

        // ACT
        InventoryUpdateResult result = inventoryService.addItem(initialState, daggerInstance);
        CharacterInventories finalState = result.updatedInventories();

        // ASSERT
        assertThat(finalState).isNotNull().isNotEqualTo(initialState);

        var equipment = finalState.equipment().items();
        assertThat(equipment).hasSize(1);
        assertThat(equipment).containsKey(daggerInstance.getUniqueId());
        assertThat(equipment).containsValue(daggerInstance);

        assertThat(finalState.consumables().items()).isEmpty();
        assertThat(finalState.cards().items()).isEmpty();
        assertThat(finalState.miscellaneous().items()).isEmpty();
    }



    @Test
    @DisplayName("Test 4: No añadir un nuevo stack a un inventario lleno")
    void shouldNotAdd_NewItemStack_ToFullInventory() {
        // ARRANGE
        // 1. Creamos un estado donde el inventario de consumibles está en su máxima capacidad.
        //    Este método ya lo teníamos preparado.
        CharacterInventories initialState = createFullConsumablesInventory();

        // 2. El 'bluePotionStack' que intentaremos añadir ya está inicializado en el setUp().
        //    Es un ítem que NO existe en el inventario lleno.

        // ACT
        // 3. Intentamos añadir el nuevo ítem.
        InventoryUpdateResult result = inventoryService.addItem(initialState, bluePotionStack);
        CharacterInventories finalState = result.updatedInventories();

        // ASSERT
        // 4. El estado final DEBE SER IDÉNTICO al inicial.
        //    Como usamos 'records', el método .equals() compara todos los campos,
        //    siendo esta la aserción más potente y concisa.
        assertThat(finalState).isEqualTo(initialState);

        // 5. (Opcional pero recomendable) Verificación extra para asegurar que la poción azul no se añadió.
        assertThat(finalState.consumables().items())
                .doesNotContainKey(BLUE_POTION_TEMPLATE.id());
    }







    /**
     * Helper method (our "strange spell") to create a state where the
     * consumables inventory is completely full with unique items.
     */
    private CharacterInventories createFullConsumablesInventory() {
        CharacterInventories state = CharacterInventories.EMPTY;
        int capacity = state.consumables().capacity();

        for (int i = 0; i < capacity; i++) {
            // We use a high, unique ID for each item to ensure they don't stack
            long uniqueTemplateId = 9000L + i;
            ItemStack newItem = new ItemStack(uniqueTemplateId, 1);
            // We need a fake template for the repository to categorize it
            ItemTemplateRepository.INSTANCE.addTemporaryTemplate(uniqueTemplateId, ItemCategory.CONSUMABLE);

            state = inventoryService.addItem(state, newItem).updatedInventories();
        }
        return state;
    }


}