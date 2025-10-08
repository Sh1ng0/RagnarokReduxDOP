package com.ragnarok.engine.actor;


    import com.ragnarok.engine.character.CharacterEquipment;
import com.ragnarok.engine.item.inventory.model.CharacterInventories;
import com.ragnarok.engine.enums.Element;
import com.ragnarok.engine.enums.Race;
import com.ragnarok.engine.enums.Size;
import com.ragnarok.engine.stat.*;

import java.util.Map;

    /**
     * An immutable record representing a player's complete "stat sheet".
     * <p>
     * It combines the shared stats from a {@link BaseProfile} with player-specific data
     * like equipment and inventories, using delegation to access the base stats.
     */
    public record PlayerProfile(
            BaseProfile baseProfile,
            CharacterEquipment equipment,
            CharacterInventories inventories
    ) implements BaseProfile {

        // --- Delegated Methods from BaseProfile ---
        // This forwards all calls for base stats to the contained baseProfile object.

        @Override public long id() { return baseProfile.id(); }
        @Override public String name() { return baseProfile.name(); }
        @Override public int baseLevel() { return baseProfile.baseLevel(); }
        @Override public String jobId() { return baseProfile.jobId(); }
        @Override public int maxHp() { return baseProfile.maxHp(); }
        @Override public int maxSp() { return baseProfile.maxSp(); }
        @Override public StatBlock totalStats() { return baseProfile.totalStats(); }
        @Override public Race race() { return baseProfile.race(); }
        @Override public Size size() { return baseProfile.size(); }
        @Override public Element element() { return baseProfile.element(); }
        @Override public Attack attack() { return baseProfile.attack(); }
        @Override public int hitRate() { return baseProfile.hitRate(); }
        @Override public int attackDelayInTicks() { return baseProfile.attackDelayInTicks(); }
        @Override public int criticalRate() { return baseProfile.criticalRate(); }
        @Override public MagicAttack magicAttack() { return baseProfile.magicAttack(); }
        @Override public Defense defense() { return baseProfile.defense(); }
        @Override public MagicDefense magicDefense() { return baseProfile.magicDefense(); }
        @Override public Flee flee() { return baseProfile.flee(); }
        @Override public Map<String, Integer> availableSkills() { return baseProfile.availableSkills(); }

        /**
         * Returns a new PlayerProfile instance with updated equipment.
         * @param newEquipment The new equipment state.
         * @return A new, updated PlayerProfile.
         */
        public PlayerProfile withEquipment(CharacterEquipment newEquipment) {
            return new PlayerProfile(this.baseProfile, newEquipment, this.inventories);
        }

        /**
         * Returns a new PlayerProfile instance with updated inventories.
         * @param newInventories The new inventories state.
         * @return A new, updated PlayerProfile.
         */
        public PlayerProfile withInventories(CharacterInventories newInventories) {
            return new PlayerProfile(this.baseProfile, this.equipment, newInventories);
        }


    }



