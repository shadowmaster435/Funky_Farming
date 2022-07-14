package shadowmaster435.funkyfarming.util;

import net.minecraft.util.StringIdentifiable;

public enum FuseSide implements StringIdentifiable {
    NONE("none"),

    CONNECTED("connected"),
    DOWN("down"),
    UP("up");

    private final String name;

    FuseSide(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}