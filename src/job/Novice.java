package job;

import enums.Element;
import enums.Race;
import enums.Size;
import stat.StatBlock;

import java.util.Map;



/**
 * Implementación del Job "Novice".
 * Hereda todos los valores por defecto de la clase Job,
 * ya que un Novice no tiene bonus de stats.
 */
public class Novice extends Job {

    @Override
    public String getJobName() {
        return "Novice";
    }

    @Override
    protected Map<Integer, StatBlock> getJobBonusSchedule() {
        return Map.of();
    }
}