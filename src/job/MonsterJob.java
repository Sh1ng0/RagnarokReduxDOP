package job;

// Deprecated: Los monstruos carecen de progresión, es absurdo forzarlos al sistema de jobs, el punto final de las similitudes
// entre character y monster reside ahora en "ActorState" el record que representa el estado en batalla del coso.

@Deprecated
public class MonsterJob {

//    private final MonsterData monsterData;

//    @Override
//    public String getJobName() {
//        return monsterData.name(); // El "job" es el propio monstruo.
//    }
//
//    // Sobreescribimos los métodos para que devuelvan los datos de la plantilla
//    // en lugar de calcularlos o usar valores por defecto.
//
//    @Override
//    public int getBaseHp() {
//        // Para un monstruo, su HP es un valor fijo, no sigue nuestra fórmula simplificada.
//        // Así que ignoramos los factores y devolvemos el valor final directamente.
//        return monsterData.maxHp();
//    }
//
//    // Para los stats, el "bonus" es simplemente el stat base total del monstruo.
//    @Override
//    public int getStrBonus(int jobLevel) {
//        return monsterData.str();
//    }
//
//    @Override
//    public int getVitBonus(int jobLevel) {
//        return monsterData.vit();
//    }
//
//    // ... y así para todos los demás stats.
//
//    // Los factores de la fórmula se pueden quedar en 0, ya que getBaseHp() ya da el valor final.
//    @Override
//    public int getVitHpFactor() { return 0; }
//
//    @Override
//    public int getLevelHpFactor() { return 0; }
}