package shadowmaster435.funkyfarming.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class Erodaisy extends Block {
    public Erodaisy(Settings settings) {
        super(settings);
    }


    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {


        if (random.nextDouble() > 0.5) {
            for (int x = 0; x < 3; ++x) {
                for (int z = 0; z < 3; ++z) {
                    if (z != 0 && x != 0) {
                        if (random.nextDouble() > 0.9) {
                            BlockPos bpos = pos.add(x, -1, z);
                            Block block = world.getBlockState(bpos).getBlock();
                            if (block == Blocks.COBBLESTONE) {
                                world.setBlockState(bpos, Blocks.GRAVEL.getDefaultState());
                            } else if (block == Blocks.GRAVEL || block == Blocks.SANDSTONE) {
                                world.setBlockState(bpos, Blocks.SAND.getDefaultState());

                            } else if (block == Blocks.RED_SANDSTONE) {
                                world.setBlockState(bpos, Blocks.RED_SAND.getDefaultState());
                            }
                        }
                    }
                }
            }
        }
        super.randomDisplayTick(state, world, pos, random);
    }
}
