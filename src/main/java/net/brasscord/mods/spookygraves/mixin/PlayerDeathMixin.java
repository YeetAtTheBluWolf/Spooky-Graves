package net.brasscord.mods.spookygraves.mixin;

import net.brasscord.mods.spookygraves.Spookygraves;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.World;
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

    /**
    * This method is a redirect of the dropAll call. This redirects to this method which calls to insert a grave block.
    * After calling the graveInsert method then it prints that the method has run.
    *
    * @param  inventory  This is a PlayInventory that is used to get the player and will later be used to store the inventory.
    */
    @Redirect(method = "dropInventory", at = @At(target = "net.minecraft.entity.player.PlayerInventory.dropAll()V", value = "INVOKE"))
    public void dropAll(PlayerInventory inventory) {
        Spookygraves.graveInsert(this.world, this.getPos(), this.inventory.player);
        System.out.println("Exiting dropAll method.");
    }



}
