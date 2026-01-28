package net.spudacious5705.basicsaddons.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public class VillagerMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTickTail(CallbackInfo ci) {

        Villager villager = (Villager)(Object)this;
        Level world = villager.level();

        if (world.getNearestPlayer(villager, 7) != null) {
            Frog frog = EntityType.FROG.create(world);
            if (frog != null) {
                Vec3 pos = villager.position();
                float yaw = villager.yBodyRot;

                frog.moveTo(pos.x, pos.y + 1, pos.z, yaw, 0f);
                world.addFreshEntity(frog);
                villager.remove(Entity.RemovalReason.DISCARDED);

                BlockPos bPos = new BlockPos((int)pos.x, (int)pos.y, (int)pos.z);

                world.playSound(null, bPos, SoundEvents.VILLAGER_TRADE, SoundSource.NEUTRAL, 2.2f, 2.2f);
                world.playSound(null, bPos, SoundEvents.PILLAGER_AMBIENT, SoundSource.NEUTRAL, 2.2f, 4.0f);
                world.playSound(null, bPos, SoundEvents.PANDA_CANT_BREED, SoundSource.NEUTRAL, 0.5f, 1.2f);

                if (world instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.POOF, pos.x, pos.y, pos.z, 20, 0, 0, 0, 0.3);
                    server.sendParticles(ParticleTypes.POOF, pos.x, pos.y + 1, pos.z, 20, 0, 1, 0, 0);
                    server.sendParticles(ParticleTypes.POOF, pos.x, pos.y + 2, pos.z, 20, 0, 1, 0, 0);
                }
            }
        }
    }

}
