package shadowmaster435.funkyfarming.mixin;


import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.EntityList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {


    @Shadow public abstract ServerWorld toServerWorld();

    @Shadow @Final private EntityList entityList;

    @Shadow @Nullable public abstract Entity getEntity(UUID uuid);


}
