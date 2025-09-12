package com.ragnarok.engine.repository;


import com.ragnarok.engine.job.Job;
import com.ragnarok.engine.job.Novice;
import com.ragnarok.engine.job.Swordman;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Un repositorio en memoria que actúa como un catálogo de todos los Jobs disponibles en el juego.
 * Utiliza un enfoque estático para que no sea necesario instanciarlo.
 */
public class JobRepository {

    private static final Map<String, Job> JOBS;

    static{

        JOBS = Stream.of(
                new Novice(),
                new Swordman()
                // Otros jobs van aquí
        ).collect(Collectors.toUnmodifiableMap(Job::getId, Function.identity()));


    }

    // Esta clase no debe ser instanciada >=(
    private JobRepository() {}


    public static Job findById(String jobId){

        Job job = JOBS.get(jobId);
        if (job == null){
            throw new IllegalArgumentException("No se encontró ningún job con el id: "+jobId);
        }
        return job;
    }




}
