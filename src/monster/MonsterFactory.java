package monster;


import actor.ActorState;
import stat.StatBlock;



// this dictates monster generation, they lack progression, so a Factory is the more sound decish
public class MonsterFactory {
    public static ActorState createStateFrom(MonsterData data) {

        StatBlock monsterStats = new StatBlock(
                data.str(), data.agi(), data.vit(),
                data.intel(), data.dex(), data.luk()
        );

        return new ActorState(
                data.id(),
                data.name(),
                data.maxHp(),    // El monstruo aparece con HP al máximo.
                data.maxHp(),
                data.maxSp(),    // Y con SP al máximo.
                data.maxSp(),
                monsterStats,    // El bloque de stats primarios.
                data.race(),
                data.size(),
                data.element(),
                data.attack(),
                data.hitRate(),
                data.attackSpeed(),
                data.criticalRate(),
                data.magicAttack(),
                data.defense(),
                data.magicDefense(),
                data.fleeRate()
        );



    }
}