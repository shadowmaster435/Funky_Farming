package shadowmaster435.funkyfarming.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.EntityList;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadowmaster435.funkyfarming.util.MathUtil;
import shadowmaster435.funkyfarming.util.QuadGrid;

import java.util.UUID;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {


    @Shadow public abstract ServerWorld toServerWorld();

    @Shadow @Final private EntityList entityList;

    @Shadow @Nullable public abstract Entity getEntity(UUID uuid);


}
