package shadowmaster435.funkyfarming.init;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.particle.StalagDrip;

public class FFParticles {

    public static final DefaultParticleType STALAGDRIP = FabricParticleTypes.simple(true);

    public static void initClient() {
        ParticleFactoryRegistry.getInstance().register(STALAGDRIP, StalagDrip.DripFactory::new);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier("funkyfarming", "stalagdrip"), STALAGDRIP);

    }
}
