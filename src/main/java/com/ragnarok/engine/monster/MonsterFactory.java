package com.ragnarok.engine.monster;


import com.ragnarok.engine.actor.ActorProfile;

import com.ragnarok.engine.actor.Position;
import com.ragnarok.engine.stat.*;

import java.util.Collections;
import java.util.Optional;


// this dictates monster generation, they lack progression, so a Factory is the more sound decish
public class MonsterFactory {
    public static ActorProfile createStateFrom(MonsterData data) {

        StatBlock monsterStats = new StatBlock(
                data.str(), data.agi(), data.vit(),
                data.intel(), data.dex(), data.luk()
        );


        // REFACTOR : Move this to the StatCalculator
        Attack monsterAttack = new Attack(data.baseAttack(), 0);
        Defense monsterDefense = new Defense(0, data.vit());
        MagicDefense monsterMagicDefense = new MagicDefense(0, data.intel());
        Flee monsterFlee = new Flee(data.fleeRate(), 0);

        MagicAttack monsterMagicAttack = new MagicAttack(0, 0);

        return new ActorProfile(


                data.id(),   // Este es el id del template del bicho, "Es un poring"
                data.name(),
                data.level(),
                String.valueOf(data.id()), // Este es el id del actor en pantalla "Es ESE poring que tengo delante"git
                data.maxHp(),    // El monstruo aparece con HP al máximo.
                data.maxHp(),
                monsterStats,    // El bloque de stats primarios.
                data.race(),
                data.size(),
                data.element(),
                monsterAttack,
                data.hitRate(),
                data.attackDelayInTicks(),
                data.criticalRate(),
                monsterMagicAttack,
                monsterDefense,
                monsterMagicDefense,
                monsterFlee,
                Collections.emptyMap(),// MIRAR ESTO UN MONSTRUO DEBE PODER TIRAR SKILLS
                Optional.empty()
        );



    }
}