package shadowmaster435.funkyfarming.util;

import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.Objects;

public enum CTSide {
    NORTH("north", new BlockPos(0,0,-1), "udc"),
    SOUTH("south", new BlockPos(0,0,1), "udc"),
    EAST("east", new BlockPos(1,0,0), "udc"),
    WEST("west", new BlockPos(-1,0,0), "udc"),
    UP("up", new BlockPos(0,1,0), "nsew"),
    DOWN("down", new BlockPos(0,-1,0), "nsew"),
    NORTHUP("northup", new BlockPos(0,1,-1), "wild"),
    SOUTHUP("southup", new BlockPos(0,1,1), "wild"),
    EASTUP("eastup", new BlockPos(1,1,0), "wild"),
    WESTUP("westup", new BlockPos(-1,1,0), "wild"),
    NORTHDOWN("northdown", new BlockPos(0,-1,-1), "wild"),
    SOUTHDOWN("southdown", new BlockPos(0,-1,1), "wild"),
    EASTDOWN("eastdown", new BlockPos(1,-1,0), "wild"),
    WESTDOWN("westdown", new BlockPos(-1,-1,0), "wild");


    private final BlockPos pos;

    public static final CTSide[] ALL = CTSide.values();

    public boolean isCardinal() {
        return (Objects.equals(allowedaxis, "nsew") || Objects.equals(allowedaxis, "udc"));
    }

    public final String allowedaxis;

    public final String name;



    public static CTSide[] cardinal() {
        return Arrays.stream(ALL).filter(CTSide::isCardinal).toArray(CTSide[]::new);
    }
    public static CTSide[] diagonal() {
        return Arrays.stream(ALL).filter((cardinal) -> !cardinal.isCardinal()).toArray(CTSide[]::new);
    }
    CTSide(String name, BlockPos pos, String allowedaxis) {
        this.pos = pos;
        this.name = name;
        this.allowedaxis = allowedaxis;
    }

    public String getAllowedAxis() {
        return super.toString();
    }

    public BlockPos getPos() {
        return pos;
    }
}