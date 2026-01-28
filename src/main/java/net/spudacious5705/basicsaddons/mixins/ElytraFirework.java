package net.spudacious5705.basicsaddons.mixins;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkRocketItem.class)
public class ElytraFirework {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void onUseHead(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (player.isFallFlying()) {
            player.displayClientMessage(forge_1_20_1_basics_addons$msg,true);
        }
        cir.setReturnValue(InteractionResultHolder.pass(player.getItemInHand(hand)));
        cir.cancel();
    }

    @Unique
    private static final Component forge_1_20_1_basics_addons$msg = Component.literal("Firework boost is disabled");

}
