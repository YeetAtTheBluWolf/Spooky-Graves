package net.brasscord.mods.spookygraves.mixin;

import net.brasscord.mods.spookygraves.config.PlayerDeath;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(PlayerDeath.class)
public class PlayerDeathMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;dead()V"), method = "death", cancellable = false)


}
