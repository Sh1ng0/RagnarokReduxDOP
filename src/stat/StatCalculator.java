package stat;


import character.CharacterData;
import character.CharacterState;
import job.Job;

/**
 * El motor de cálculo de stats para JUGADORES.
 * Esta clase es un servicio "sin estado" (stateless). No almacena datos de un personaje,
 * sino que los recibe como parámetros, ejecuta las fórmulas y devuelve un estado final calculado.
 * Es la "cocina" de nuestra arquitectura.
 */
public class StatCalculator {

    public CharacterState buildState(CharacterData data, Job job){

// Final primary stats calc
        StatBlock totalStats = calculateTotalStats(data,Job);
    }




    private StatBlock calculateTotalStats(CharacterData data, Job job, StatBlock totalStats){
        return null;
    }
}
