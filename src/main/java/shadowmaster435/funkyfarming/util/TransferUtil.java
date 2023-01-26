package shadowmaster435.funkyfarming.util;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import shadowmaster435.funkyfarming.block.Pylon;
import shadowmaster435.funkyfarming.block.entity.PylonEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class TransferUtil {

    public static Block[] validEnergyTransferBlocks = {FFBlocks.PYLON};
    public static Block[] validEnergyIOBlocks = {FFBlocks.ENERGYVOID, FFBlocks.GENERATOR};

    public static Block[] validEnergyConsumingBlocks = {FFBlocks.ENERGYVOID};
    public static Block[] validEnergyProducingBlocks = {FFBlocks.GENERATOR};

    public static List<BlockPos> transferlist = new ArrayList<>();


    public static boolean canTransferEnergy(World world, BlockPos pos) {
        return Arrays.stream(validEnergyTransferBlocks).toList().contains(world.getBlockState(pos).getBlock());
    }

    public static boolean isConsumer(World world, BlockPos pos) {
        return Arrays.stream(validEnergyConsumingBlocks).toList().contains(world.getBlockState(pos).getBlock());
    }
    public static List<BlockPos> getTransferPoints(World world, PylonEntity entity, int maxConnections) {
        return subTransferAlgorithm(world, entity.getNBTPosList(), entity, maxConnections, 0, new ArrayList<>());
    }
    public static ArrayList<BlockPos> finalvalids = new ArrayList<>();
    public static List<BlockPos> subTransferAlgorithm(World world, ArrayList<BlockPos> list, PylonEntity entity, int maxConnections, int currentConnectionDepth, ArrayList<BlockPos> subvalids) {
        ArrayList<BlockPos> valids = new ArrayList<>(subvalids);
        int depth = currentConnectionDepth + 1;
        // Only clear on first iteration
        if (currentConnectionDepth == 0) {
            finalvalids.clear();
        }
        if (depth < maxConnections) {
            for (BlockPos pos1 : list) {
                entity.visited = true;
                PylonEntity entity1 = (PylonEntity) world.getBlockEntity(pos1);
                assert entity1 != null;
                // Check for a valid io block offset opposite to provided direction then add if found
                if (Arrays.stream(validEnergyIOBlocks).toList().contains(world.getBlockState(pos1.offset(world.getBlockState(pos1).get(Pylon.DIRECTION).getOpposite())).getBlock())) {
                    if (!valids.contains(pos1.offset(world.getBlockState(pos1).get(Pylon.DIRECTION).getOpposite())) && !finalvalids.contains(pos1.offset(world.getBlockState(pos1).get(Pylon.DIRECTION).getOpposite()))) {
                        finalvalids.add(pos1.offset(world.getBlockState(pos1).get(Pylon.DIRECTION).getOpposite()));
                        valids.add(pos1.offset(world.getBlockState(pos1).get(Pylon.DIRECTION).getOpposite()));
                    }
                }
                if (!entity1.visited) {
                    // if transfer node not visited. iterate.
                    subTransferAlgorithm(world, entity1.getNBTPosList(), entity1, maxConnections, depth, valids);
                }
            }
        }
        // Needed so an empty list isn't returned
        ArrayList<BlockPos> resultedValids = new ArrayList<>(finalvalids);

        entity.visited = true;

        return resultedValids;
    }


}
