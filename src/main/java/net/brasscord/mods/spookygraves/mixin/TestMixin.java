package net.brasscord.mods.spookygraves.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TestMixin {

    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo callbackInfo)
    {
        System.out.println("At menu");
    }

}
