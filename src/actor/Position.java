package actor;

/**
 * Representa un punto en el espacio 2D del juego.
 */
public record Position(int x, int y) {
    /**
     * Posición por defecto o de origen.
     */
    public static final Position ORIGIN = new Position(0, 0);
}