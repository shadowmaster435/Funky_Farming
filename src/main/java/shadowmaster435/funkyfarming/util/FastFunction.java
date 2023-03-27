package shadowmaster435.funkyfarming.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.Property;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;

public class FastFunction {


    private BlockPos pos;

    private final ArrayList<Boolean> boolTester = new ArrayList<>();

    private World world;

    private BlockEntity entity;

    private Direction direction;

    public FastFunction(BlockPos pos) {
        this.pos = pos;
    }


    public FastFunction(BlockPos pos, World world) {
        this.pos = pos;
        this.world = world;

    }

    public FastFunction(BlockPos pos, World world, BlockEntity entity) {
        this.pos = pos;
        this.world = world;
        this.entity = entity;
    }

    public FastFunction(BlockPos pos, World world, BlockEntity entity, Direction direction) {
        this.pos = pos;
        this.world = world;
        this.entity = entity;
        this.direction = direction;

    }

    public FastFunction(BlockPos pos, World world, Direction direction) {
        this.pos = pos;
        this.world = world;
        this.direction = direction;
    }

    public BlockEntity getEntity() {
        return entity;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Direction getDirection() {
        return direction;
    }

    public World getWorld() {
        return world;
    }


    public <T extends Enum<T> & StringIdentifiable> FastFunction isBlockState(BlockPos ofs, BlockState state, Property<T> property) {
        this.boolTester.add(world.getBlockState(this.pos.add(ofs)).get(property) == state.get(property));
        return this;
    }

    public FastFunction addBool(boolean bool) {
        this.boolTester.add(bool);
        return this;
    }

    public FastFunction executeIfTesterTrue() {
        if (this.result()) {
            return this;
        } else {
            return null;
        }
    }

    public void printError(Exception e) {
        System.out.println("function caused error with message of: " + e.getMessage());
    }

    public boolean result() {
        return this.boolTester.stream().allMatch(aBoolean -> aBoolean);
    }

    public FastFunction within(double min, double max, int val) {
        this.boolTester.add(val > min && val < max);
        return this;
    }
    public FastFunction withinEQ(double min, double max, int val) {
        this.boolTester.add(val >= min && val <= max);
        return this;
    }
    public FastFunction within(float min, float max, int val) {
        this.boolTester.add(val > min && val < max);
        return this;
    }
    public FastFunction withinEQ(float min, float max, int val) {
        this.boolTester.add(val >= min && val <= max);
        return this;
    }
    public FastFunction within(int min, int max, int val) {
        this.boolTester.add(val > min && val < max);
        return this;
    }

    public FastFunction withinEQ(int min, int max, int val) {
        this.boolTester.add(val >= min && val <= max);
        return this;
    }

    public FastFunction blockCounter(int successesReq, ArrayList<BlockPos> val, Block testBlock) {
        int successes = 0;
        for (BlockPos pos : val) {
            if (this.world.getBlockState(pos).getBlock() == testBlock) {
                ++successes;
            }
        }
        boolTester.add(successes >= successesReq);
        return this;
    }


    public ArrayList<BlockPos> DIRarrayifier() {
        ArrayList<BlockPos> result = new ArrayList<>();
        for (Direction direction: Direction.values()) {
            result.add(pos.offset(direction));
        }
        return result;
    }

    public ArrayList<BlockPos> DIRarrayifier(int offset) {
        ArrayList<BlockPos> result = new ArrayList<>();
        for (Direction direction: Direction.values()) {
            result.add(pos.offset(direction, offset));
        }
        return result;
    }

    public ArrayList<BlockPos> DIRarrayifier(ArrayList<Direction> directions) {
        ArrayList<BlockPos> result = new ArrayList<>();
        for (Direction direction: directions) {
            result.add(pos.offset(direction));
        }
        return result;
    }

    public FastFunction isBlock(BlockPos ofs, BlockState state) {
        this.boolTester.add(world.getBlockState(this.pos.add(ofs)) == state);
        return this;
    }

    public FastFunction isBlock(BlockPos ofs, Block block) {
        this.boolTester.add(world.getBlockState(this.pos.add(ofs)).getBlock() == block);
        return this;
    }


    public FastFunction setBlock(BlockState blockState) {
        this.world.setBlockState(this.pos, blockState);
        return this;
    }


    public FastFunction setBlock(Block block) {
        this.world.setBlockState(this.pos, block.getDefaultState());
        return this;
    }

}
