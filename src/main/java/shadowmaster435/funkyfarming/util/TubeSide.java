package shadowmaster435.funkyfarming.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;
import org.apache.http.cookie.SM;

import java.util.Arrays;
import java.util.Comparator;

public enum TubeSide implements StringIdentifiable {
    NONE("none", 0),
    MEDIUM("medium", 2),
    SMALL("small", 1);

    private static final TubeSide[] ALL = values();

    private static final TubeSide[] ONLYSMALL = Arrays.stream(ALL).filter((size) -> size.isntBiggerThan(1, size)).toArray((TubeSide[]::new));
    private static final TubeSide[] ONLYMEDIUM = Arrays.stream(ALL).filter((size) -> size.isntBiggerThan(2, size)).toArray((TubeSide[]::new));

    private final String name;

    public static TubeSide byName(String string) {
        TubeSide side = NONE;
        switch (string){
            case "medium" -> side = MEDIUM;
            case "small" -> side = SMALL;
        }
        return side;
    }

    private final int size;

    TubeSide(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String toString() {
        return this.name;
    }

    public static TubeSide[] getONLYMEDIUM() {
        return ONLYMEDIUM;
    }

    public static TubeSide[] getONLYSMALL() {
        return ONLYSMALL;
    }

    public static TubeSide[] getALL() {
        return ALL;
    }

    public String asString() {
        return this.name;
    }

    public int getSize() {
        return size;
    }

    public boolean isntBiggerThan(int size, TubeSide side) {
        int first = side.getSize();

        return first <= size;
    }
}