package shadowmaster435.funkyfarming.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import shadowmaster435.funkyfarming.world.ScrapPileFeature;

public class FFWorld {
    public static final Identifier SCRAP_PILE_ID = new Identifier("funkyfarming", "scrappile");
    public static Feature<DefaultFeatureConfig> SCRAP_PILE_FEATURE = new ScrapPileFeature(DefaultFeatureConfig.CODEC);

    public static ConfiguredFeature<DefaultFeatureConfig, ScrapPileFeature> SCRAP_PILE_CONFIGURED = new ConfiguredFeature<>(
            (ScrapPileFeature) SCRAP_PILE_FEATURE,
            new DefaultFeatureConfig()
    );

    public static void init() {
        Registry.register(Registries.FEATURE, SCRAP_PILE_ID, SCRAP_PILE_FEATURE);

    }

}
