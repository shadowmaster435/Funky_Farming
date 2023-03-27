package shadowmaster435.funkyfarming.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.init.FFSounds;

public class StalagDrip extends SpriteBillboardParticle {
    public StalagDrip(ClientWorld world, double x, double y, double z, SpriteProvider sprites) {
        super(world, x, y, z);
        this.velocityX = 0.0D;
        this.velocityY = -0.05f;
        this.velocityZ = 0.0D;
        this.gravityStrength = 0f;
        this.scale = 0.125f / 4;
        this.setBoundingBoxSpacing(0.02F, 0.005F);
        this.setSprite(sprites.getSprite(world.random));



    }
    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.onGround || (this.world.getBlockState(new BlockPos(this.x, this.y, this.z)).getBlock() != Blocks.AIR && this.world.getBlockState(new BlockPos(this.x, this.y, this.z)).getBlock() != FFBlocks.STALAGPIPE)) {
            this.world.playSound(this.x, this.y, this.z, FFSounds.DRIP_EVENT, SoundCategory.AMBIENT, 1,(float) (1 + ((Math.random() - 0.5)) / 4), true);
            this.markDead();
        } else {
            this.velocityY = this.velocityY - 0.025f;
        }
        this.move(this.velocityX, this.velocityY, this.velocityZ);

    }

    @Environment(EnvType.CLIENT)
    public static class DripFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public DripFactory(FabricSpriteProvider sprites) {
            this.spriteProvider = sprites;

        }

        @Override
        public Particle createParticle(DefaultParticleType type, ClientWorld world, double x, double y, double z, double Xv, double Yv, double Zv) {
            return new StalagDrip(world, x, y, z, spriteProvider);
        }
    }
}
