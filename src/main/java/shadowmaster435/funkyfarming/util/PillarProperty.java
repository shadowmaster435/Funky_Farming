package shadowmaster435.funkyfarming.util;

import net.minecraft.util.StringIdentifiable;

public enum PillarProperty implements StringIdentifiable {
    CENTER("center"),
    BOTTOM("bottom"),
    TOP("top");

    private final String name;

    PillarProperty(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}