# Motor de Combate Java DOP (Estilo RO Clásico)

## 🎯 Objetivo del Proyecto

Este es un proyecto de estudio para construir un motor de lógica de combate en **Java 23**, inspirado en las mecánicas de **Ragnarok Online clásico (pre-Renewal)**. El objetivo principal no es clonar el juego, sino destilar su esencia en una arquitectura moderna, simplificada y robusta, explorando un enfoque híbrido que combina lo mejor de la **Programación Orientada a Objetos (OOP)** y la **Programación Orientada a Datos (DOP)**.

## ✨ Conceptos Clave

El motor se basa en una estricta separación de responsabilidades:

1.  **El "Motor de Cálculo" (OOP):** Una serie de clases tradicionales se encargan de la lógica compleja de calcular los stats de un personaje. Toman datos base (nivel, stats asignados, job, equipo) y los procesan a través de un pipeline para generar un estado final.
2.  **La "Foto" del Estado (DOP):** El resultado de los cálculos es un `record` inmutable (`CharacterState`) que representa una "foto" completa de un personaje en un instante. Es simple, seguro y predecible.
3.  **El Bucle de Combate (DOP):** La lógica de combate se manejará de forma declarativa. Una función central tomará el estado actual y un evento (ej: `PlayerAttack`) y, usando un `switch` con pattern matching, devolverá un **nuevo** estado como resultado, sin modificar nunca el original.

## 🛠️ Stack Tecnológico

* **Lenguaje:** Java 23
* **Frameworks:** Ninguno. El proyecto se está desarrollando en Java puro para centrarnos en los principios de arquitectura y diseño.

## 🗺️ Scope Actual

* Diseño del motor de cálculo de stats para jugadores.
* Diseño de un sistema de "Jobs" flexible y basado en composición.
* Diseño de un sistema de factoría simple para instanciar monstruos con stats fijos.
* Definición de las estructuras de datos (`records`) para `CharacterData` (datos persistentes) y `CharacterState` (datos de combate).