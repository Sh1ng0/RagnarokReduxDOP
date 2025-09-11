package com.ragnarok.engine.stat;

public record StatBlock(
        int str,
        int agi,
        int vit,
        int intel,
        int dex,
        int luk
) {

    public static final StatBlock ZERO = new StatBlock(0, 0, 0, 0, 0, 0);

    public StatBlock add(StatBlock other) {
        return new StatBlock(
                this.str + other.str,
                this.agi + other.agi,
                this.vit + other.vit,
                this.intel + other.intel,
                this.dex + other.dex,
                this.luk + other.luk
        );

    }

}
