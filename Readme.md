Motor de Juego Java DOP (Estilo RO Clásico)
🎯 Objetivo del Proyecto
Este proyecto ha evolucionado de un simple motor de combate a un prototipo de backend para un juego multijugador en tiempo real, inspirado en las mecánicas de Ragnarok Online clásico.

El objetivo principal es explorar y aplicar patrones de arquitectura de software modernos para construir un sistema altamente escalable, resiliente y mantenible. Sirve como un caso de estudio práctico de los siguientes conceptos en Java 24+:

Programación Orientada a Datos (DOP) y principios Funcionales.

Arquitectura Orientada a Eventos (EDA) con un message broker.

Modelos de concurrencia masiva utilizando Virtual Threads (Project Loom).

Estrategias de gestión de estado para sistemas distribuidos.

✨ Filosofía de Diseño
El motor se rige por una serie de principios estrictos para garantizar un código limpio y predecible:

Datos por Encima de la Lógica: La separación entre la data (records) y la lógica (stateless services) es total.

Inmutabilidad como Norma: El estado del juego se representa a través de "snapshots" o "fotos" inmutables. El estado nunca se modifica; se genera uno nuevo como resultado de una operación.

Funciones Puras: Los servicios que contienen la lógica del juego operan como funciones puras, sin efectos secundarios. Su salida depende únicamente de su entrada.

Desacoplamiento Total: Los componentes principales (UI, Lógica, Persistencia) no tienen conocimiento directo unos de otros. Se comunican de forma asíncrona a través de eventos.

🏗️ Arquitectura General
El sistema está diseñado siguiendo un patrón de Arquitectura Orientada a Eventos (EDA), similar al que se usa en sistemas de microservicios a gran escala.

Fragmento de código

graph TD
subgraph Cliente
UI[Interfaz de Usuario]
end

    subgraph "Backend"
        KAFKA(Plataforma de Eventos <br> Kafka);
        
        subgraph "Engine"
            AS[Servicio de Actores (Stateful)<br><i>Gestiona sesiones con Virtual Threads</i>];
            LS[Servicios de Lógica (Stateless)<br><i>StatCalculator, EquipmentService, etc.</i>];
        end

        subgraph "Capa de Datos"
            REDIS[(Estado Caliente <br> Redis)];
            DB[(Estado Frío <br> PostgreSQL)];
        end
    end

    UI --"1. Emite Evento de Acción"--> KAFKA;
    KAFKA --"2. Consume Evento"--> AS;
    AS --"3. Delega Lógica"--> LS;
    LS --"4. Consulta Estado"--> REDIS;
    AS --"5. Actualiza Estado"--> REDIS;
    AS --"6. Emite Evento de Resultado"--> KAFKA;
    KAFKA --"7. Notifica a la UI"--> UI;
    REDIS --" Escritura Asíncrona"-.-> DB;

Cliente (UI): Es un servicio completamente independiente. Su única tarea es renderizar el estado del juego y emitir "intenciones" del jugador como eventos a Kafka.

Kafka: Actúa como el sistema nervioso central, desacoplando a todos los participantes.

Engine:

Servicio de Actores: Gestiona el ciclo de vida de las sesiones de los jugadores. Utiliza el patrón "Un Actor por Sesión" sobre Virtual Threads para manejar miles de conexiones concurrentes.

Servicios de Lógica: Son calculadoras puras y stateless que contienen las reglas del juego.

Capa de Datos:

Redis (Estado Caliente): Mantiene el estado en tiempo real de todos los jugadores online para un acceso de latencia ultra-baja.

PostgreSQL (Estado Frío): Es la base de datos persistente. Se actualiza de forma asíncrona para no impactar el rendimiento del bucle de juego.

🧬 Modelo de Datos Clave
ActorProfile vs. ActorView: Se separa el estado en dos records. El ActorProfile es la "ficha de personaje" con stats que cambian poco, generada por el StatCalculator. El ActorView es el estado dinámico (posición, HP actual) que se actualiza en cada tick del GameLoop.

Modelo Híbrido de Ítems: Se utiliza una sealed interface ItemInstance que permite la coexistencia de:

Entidades (clases mutables): Para ítems persistentes con un ciclo de vida, como EquipInstance.

Objetos de Valor (records inmutables): Para ítems apilables o efímeros, como ConsumableInstance.

🛠️ Stack Tecnológico
Lenguaje: Java 24+

Arquitectura: Java Puro, sin frameworks de inyección de dependencias.

Componentes de Infraestructura (Conceptuales):

Broker de Mensajes: Kafka

Caché en Memoria: Redis

Base de Datos Persistente: MySQL

🗺️ Roadmap y Estado Actual
✅ Completado:

Diseño y testeo completo del EquipmentService.

Implementación de la jerarquía de Job para validaciones.

Refactor a ActorProfile/ActorView para separar el estado.

Diseño del StatCalculator y la lógica de ASPD.

➡️ Siguiente Paso:

Implementación del InventoryService y el orquestador de acciones de inventario/equipamiento.

🚀 Futuro:

Motor de Combate V1.

Sistema de Progresión (Level Up).

Pipeline de Animación Orientado a Datos.