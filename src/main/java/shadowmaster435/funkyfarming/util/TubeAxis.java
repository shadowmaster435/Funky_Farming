package shadowmaster435.funkyfarming.util;

import net.minecraft.util.StringIdentifiable;

public enum TubeAxis implements StringIdentifiable {
    LARGE("large"),
    MEDIUM("medium"),
    SMALL("small");

    private final String name;

    TubeAxis(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}