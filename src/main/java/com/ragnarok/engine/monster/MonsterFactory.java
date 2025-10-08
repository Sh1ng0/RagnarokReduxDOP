package com.ragnarok.engine.monster;


import com.ragnarok.engine.actor.ActorProfile;

import com.ragnarok.engine.actor.MonsterProfile;
import com.ragnarok.engine.actor.Position;
import com.ragnarok.engine.stat.*;

import java.util.Collections;
import java.util.Optional;


/**
 * A factory class responsible for orchestrating the creation of monster actor profiles.
 * <p>
 * This class follows the Single Responsibility Principle by delegating the complex task
 * of stat calculation and {@link com.ragnarok.engine.actor.ActorProfile} construction
 * to the {@link StatCalculator}. Its primary role is to serve as a clear and convenient
 * entry point for generating monster states from their base {@link MonsterData}.
 */
public class MonsterFactory {


        public static MonsterProfile createStateFrom (MonsterData data){
            // La responsabilidad ahora se delega al StatCalculator,
            // que centraliza toda la lógica de creación de ActorProfile.
            return new StatCalculator().buildState(data);
        }


}