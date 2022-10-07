package net.brasscord.mods.spookygraves.mixin;

import net.brasscord.mods.spookygraves.Spookygraves;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerDeathMixin extends LivingEntity{

    @Shadow @Final private PlayerInventory inventory;

    protected PlayerDeathMixin(EntityType<? extends LivingEntity> type, World world)
    {
        super(type, world);
    }

    @Redirect(method = "dropInventory", at = @At(target = "net.minecraft.entity.player.PlayerInventory.dropAll()V", value = "INVOKE"))
    public void dropAll(PlayerInventory inventory) {
        Spookygraves.graveInstrument(this.world, this.getPos(), this.inventory.player);
        System.out.println("At dropAll method.");
    }



}
