package shadowmaster435.funkyfarming.util;

import net.minecraft.util.Pair;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum ExtendedDirection implements StringIdentifiable {

    UP(new Vec3i(0, 1, 0), "up", "down", "y"),
    DOWN(new Vec3i(0, -1, 0), "down", "up", "y"),
    NORTH(new Vec3i(-1, 0, 0), "north", "south", "z"),
    SOUTH(new Vec3i(1, 0, 0), "south", "north", "z"),
    EAST(new Vec3i(0, 0, 1), "east", "west", "x"),
    WEST(new Vec3i(0, 0, -1), "west", "east", "x"),
    UPNORTH(new Vec3i(-1, 1, 0), "upnorth", "downsouth", "yz"),
    UPSOUTH(new Vec3i(1, 1, 0), "upsouth", "downnorth", "yz"),
    UPEAST(new Vec3i(0, 1, 1), "upeast", "downwest", "xy"),
    UPWEST(new Vec3i(0, 1, -1), "upwest", "downeast", "xy"),

    DOWNNORTH(new Vec3i(-1, -1, 0), "downnorth", "upsouth", "yz"),
    DOWNSOUTH(new Vec3i(1, -1, 0), "downsouth", "upnorth", "yz"),
    DOWNEAST(new Vec3i(0, -1, 1), "downeast", "upwest", "xy"),
    DOWNWEST(new Vec3i(0, -1, -1), "downwest", "upeast", "xy"),
    NORTHEAST(new Vec3i(-1, 0, 1), "northeast", "southwest", "xz"),
    NORTHWEST(new Vec3i(-1, 0, -1), "northwest", "southeast", "xz"),
    SOUTHEAST(new Vec3i(1, 0, 1), "southeast", "northwest", "xz"),
    SOUTHWEST(new Vec3i(1, 0, -1), "southwest", "northeast", "xz");
    
    private final Vec3i offsetPos;

    private final String name;
    
    private final String opposite;


    private final String axis;



  //  private final ExtendedDirection[] CARDINAL = (ExtendedDirection[]) Arrays.stream(values()).filter(s -> s.getAxis().length() < 2).toArray();




    ExtendedDirection(Vec3i offsetPos, String name, String opposite, String axis) {
        this.offsetPos = offsetPos;
        this.name = name;
        this.opposite = opposite;
        this.axis = axis;
    }


    public static ArrayList<ExtendedDirection> ctFilter(String corner, Direction side) {
        ArrayList<ExtendedDirection> directions = new ArrayList<>();
        boolean xAxis = side.getAxis() == Direction.Axis.X;
        boolean yAxis = side.getAxis() == Direction.Axis.Y;
        boolean zAxis = side.getAxis() == Direction.Axis.Z;



        if (zAxis) {
            switch (corner) {
                case "bl" -> {
                    directions.add(NORTH);
                    directions.add(DOWN);
                    directions.add(DOWNNORTH);
                }
                case "br" -> {
                    directions.add(SOUTH);
                    directions.add(DOWN);
                    directions.add(DOWNSOUTH);
                }
                case "tl" -> {
                    directions.add(NORTH);
                    directions.add(UP);
                    directions.add(UPNORTH);
                }
                case "tr" -> {
                    directions.add(SOUTH);
                    directions.add(UP);
                    directions.add(UPSOUTH);
                }
            }
        }
        if (yAxis) {
            switch (corner) {
                case "bl" -> {
                    directions.add(NORTH);
                    directions.add(WEST);
                    directions.add(NORTHWEST);
                }
                case "tl" -> {
                    directions.add(NORTH);
                    directions.add(EAST);
                    directions.add(NORTHEAST);
                }
                case "tr" -> {
                    directions.add(SOUTH);
                    directions.add(WEST);
                    directions.add(SOUTHWEST);
                }
                case "br" -> {
                    directions.add(SOUTH);
                    directions.add(EAST);
                    directions.add(SOUTHEAST);
                }
            }
        }
        if (xAxis) {
            switch (corner) {
                case "bl" -> {
                    directions.add(WEST);
                    directions.add(DOWN);
                    directions.add(DOWNWEST);
                }
                case "br" -> {
                    directions.add(EAST);
                    directions.add(DOWN);
                    directions.add(DOWNEAST);
                }
                case "tl" -> {
                    directions.add(WEST);
                    directions.add(UP);
                    directions.add(UPWEST);
                }
                case "tr" -> {
                    directions.add(EAST);
                    directions.add(UP);
                    directions.add(UPEAST);
                }
            }
        }
        return directions;
    }

    public Vec3i getOffsetPos() {
        return offsetPos;
    }


    public String getAxis() {
        return axis;
    }

    public BlockPos offset(BlockPos pos, ExtendedDirection direction) {
        return pos.add(direction.getOffsetPos());
    }

    public ExtendedDirection getOpposite(ExtendedDirection direction) {
        return this.byName(direction.opposite);
    }
    @Override
    public String asString() {
        return null;
    }

    public String getName() {
        return name;
    }
    
    public ExtendedDirection[] ofSide(Direction side) {
         return (ExtendedDirection[]) Arrays.stream(values()).filter(s -> !s.getName().contains(side.getOpposite().getName())).toArray();
    }

    public ExtendedDirection byName(String name) {
        ExtendedDirection result = null;
        try {
            for (ExtendedDirection direction :  values()) {
                if (Objects.equals(direction.getName(), name)) {
                    result = direction;
                    break;
                }
            }
            return result;

        } catch (Exception ignored) {
            System.out.println("direction [" + name + "] not found returned up");
            return UP;
        }
    }

    public enum ExtendedAxis implements StringIdentifiable {
        X("x"),
        Y("y"),
        Z("z"),
        XY("xy"),
        YZ("yz"),
        XZ("xz");


        private final String name;
        ExtendedAxis(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public ExtendedAxis byName(String name) {
            ExtendedAxis result = null;
            try {
                for (ExtendedAxis axis :  values()) {
                    if (Objects.equals(axis.getName(), name)) {
                        result = axis;
                        break;
                    }
                }
                return result;

            } catch (Exception ignored) {
                System.out.println("direction [" + name + "] not found returned up");
                return X;
            }
        }
        @Override
        public String asString() {
            return null;
        }




    }

}
