# Logging Architecture Design Document

## 1. Executive Summary
This document outlines the standard for a high-performance, structured, and maintainable logging system for the game engine. The architecture is designed to be robust for a real-time, multi-threaded environment while remaining easy to use and manage.

The system is composed of three core components:

- **SLF4J**: A logging facade that decouples our code from any specific logging implementation.  
- **Logback**: A high-performance logging framework that will act as the engine.  
- **LogEvent Enum**: A custom, type-safe API for defining all possible loggable events within the game.  

---

## 2. Core Principles
- **Performance**: The logging system must have a minimal impact on the main game loop and scale effectively in a multi-threaded environment.  
- **Structured Data**: Log events are treated as structured data, not arbitrary strings. This ensures consistency and makes logs easier to parse, search, and analyze.  
- **Decoupling**: The game logic should be completely decoupled from the logging implementation. A service should declare that an event happened, not how it should be logged.  
- **Centralized Control**: All logging behavior (levels, output formats, destinations) will be controlled externally via a configuration file, without requiring code changes.  

---

## 3. Component Breakdown
The logging process follows a clear chain of responsibility:

**Game Logic ➡️ LogEvent Enum ➡️ SLF4J API ➡️ Logback Engine ➡️ Final Output**

### LogEvent Enum (The "What")
This is our custom, type-safe API.

- **Role**: To provide a centralized catalog of every possible loggable event in the application (e.g., `INVENTORY_FULL`, `ACTOR_DEFEATED`).  
- **Function**: Game services will call a method on this enum (e.g., `LogEvent.INVENTORY_FULL.log(...)`).  
  The enum itself is responsible for holding the message template and severity level, and for passing this structured information to the SLF4J API.  

### SLF4J API (The "How to Ask")
This is the standard facade that our LogEvent enum will interact with.

- **Role**: To provide a simple, universal interface for logging (`logger.info()`, `logger.warn()`, etc.).  
- **Function**: It acts as a middleman, forwarding the logging requests to the actual logging engine that is present at runtime.  

### Logback Engine (The "How to Do")
This is the powerful backend that does the heavy lifting.

- **Role**: To receive logging requests from SLF4J and process them with high efficiency.  
- **Function**: Based on the `logback.xml` configuration, it will:  
  - **Filter**: Immediately discard messages that are below the configured log level (e.g., ignore `DEBUG` messages in a production environment). This is a critical performance feature.  
  - **Format**: Add timestamps, thread names, and other context to the message.  
  - **Write (Append)**: Send the final formatted string to a destination, such as the console or a file. This can be done asynchronously to avoid blocking the main game threads.  

---

## 4. Implementation Steps
1. **Add Dependencies**: Add `slf4j-api` and `logback-classic` to the `pom.xml` file.  
2. **Create Configuration**: Create a `logback.xml` file in `src/main/resources` to define the log levels, format, and output destination.  
3. **Implement LogEvent.java**: Create the central logging enum in the `com.ragnarok.engine.logger` package.  
4. **Refactor Existing Logs**: Replace all instances of `System.out.println` with the appropriate `LogEvent.EVENT_NAME.log(...)` calls.  
