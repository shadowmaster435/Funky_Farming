package shadowmaster435.funkyfarming.util;

import net.minecraft.util.StringIdentifiable;

public enum ConnectableSide implements StringIdentifiable {
    NONE("none"),
    DOWN("down"),
    UP("up");

    private final String name;

    ConnectableSide(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}