package shadowmaster435.funkyfarming.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;

public record ScrapPileFeatureConfig(int number, Identifier blockID) implements FeatureConfig {
    public ScrapPileFeatureConfig(int number, Identifier blockID) {
        this.blockID = blockID;
        this.number = number;
    }

    public static Codec<ScrapPileFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance ->
                    instance.group(
                                    // you can add as many of these as you want, one for each parameter
                                    Codecs.POSITIVE_INT.fieldOf("number").forGetter(ScrapPileFeatureConfig::number),
                                    Identifier.CODEC.fieldOf("blockID").forGetter(ScrapPileFeatureConfig::blockID))
                            .apply(instance, ScrapPileFeatureConfig::new));

    public int number() {
        return number;
    }

    public Identifier blockID() {
        return blockID;
    }
}