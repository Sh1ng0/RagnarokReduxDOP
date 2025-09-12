package com.ragnarok.engine.stat;


/**
 * Representa el ataque desglosado de un actor, separando el poder
 * que proviene de sus stats (Status ATK) del que proviene de su
 * equipo (Weapon ATK).
 */
public record Attack(
        int statAttack,
        int weaponAttack
) {

    public int total() {
        return statAttack + weaponAttack;
    }
}