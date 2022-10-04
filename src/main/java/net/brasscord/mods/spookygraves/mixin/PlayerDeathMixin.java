package net.brasscord.mods.spookygraves.mixin;

import net.brasscord.mods.spookygraves.events.PlayerDeathCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerDeathMixin {

    @Inject(at = @At("HEAD"), method = "onDeath")
    private void onDeath(DamageSource damageSource, CallbackInfo ci)
    {
        PlayerDeathCallback.EVENT.invoker();
    }


}
