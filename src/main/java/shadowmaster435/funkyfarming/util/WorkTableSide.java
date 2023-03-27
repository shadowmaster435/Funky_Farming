package shadowmaster435.funkyfarming.util;

import net.minecraft.util.StringIdentifiable;

public enum WorkTableSide implements StringIdentifiable {
    LEFT("left"),
    RIGHT("right");

    public final String name;
    WorkTableSide(String name) {
        this.name = name;
    }
    @Override
    public String asString() {
        return this.name;
    }
}
