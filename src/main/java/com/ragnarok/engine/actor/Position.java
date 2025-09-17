package com.ragnarok.engine.actor;

/**
 * Represents a point in the game's 2D space.
 */
public record Position(int x, int y) {
    /**
     * The default or origin position.
     */
    public static final Position ORIGIN = new Position(0, 0);
}