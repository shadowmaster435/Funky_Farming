package shadowmaster435.funkyfarming.world;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.util.MathUtil;

public class ScrapPileFeature extends Feature<DefaultFeatureConfig> {

    public ScrapPileFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    // this method is what is called when the game tries to generate the feature. it is where the actual blocks get placed into the world.
    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        // the origin is the place where the game starts trying to place the feature
        BlockPos origin = context.getOrigin();
        // we won't use the random here, but we could if we wanted to
        Random random = context.getRandom();
        DefaultFeatureConfig config = context.getConfig();
        int randomRadius = random.nextInt(8);
        int randomHeight = random.nextInt(10);
        BlockState scrap = FFBlocks.SCRAP_SOIL.getDefaultState();

        for (int x = -randomRadius; x < randomRadius; ++x) {
            for (int y = 1; y < randomHeight; ++y) {
                for (int z = -randomRadius; z < randomRadius; ++z) {
                    if (origin.getY() + y >= world.getTopY()) break;

                    if (MathUtil.pointInsideCircle(origin.getX(), origin.getZ(), (int) Math.exp(y / 10f) , x, z)) {
                        if (random.nextFloat() > 0.675) {
                            world.setBlockState(origin.add(x, y + 1, z), scrap, 0);
                        }
                        if (!(random.nextFloat() > 0.75 && world.getBlockState(origin.add(x, y - 1, z)).equals(scrap))) {
                            world.setBlockState(origin.add(x, y, z), scrap, 0);
                        }
                        world.setBlockState(origin.add(x, y + 1, z), scrap, 0);

                    }
                }
            }
        }

        return false;
    }
}