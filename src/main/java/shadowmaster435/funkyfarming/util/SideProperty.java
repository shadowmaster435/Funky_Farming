package shadowmaster435.funkyfarming.util;

import net.minecraft.util.StringIdentifiable;

public enum SideProperty implements StringIdentifiable {
    LEFT("left"),
    RIGHT("right"),
    FRONT("front"),
    BACK("back"),
    TOP("top"),
    BOTTOM("bottom");

    public final String name;
    SideProperty(String name) {
        this.name = name;
    }
    @Override
    public String asString() {
        return this.name;
    }
}
