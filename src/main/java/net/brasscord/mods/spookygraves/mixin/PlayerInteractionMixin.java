package net.brasscord.mods.spookygraves.mixin;

import net.brasscord.mods.spookygraves.blocks.GraveBlock;
import net.brasscord.mods.spookygraves.entities.blocks.GraveBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayerInteractionManager.class)
public class PlayerInteractionMixin
{

    @Inject(method = "breakBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void breakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir, World world, Block block, BlockState blockState, PlayerEntity player)
    {

        BlockEntity blockEntity = world.getBlockEntity(pos);

        if(blockEntity instanceof GraveBlockEntity graveBlockEntity && block instanceof GraveBlock graveBlock)
        {
            if(graveBlockEntity.getOwner() != null)
            {
                graveBlock.onBreak(world, pos, blockState, player);
            }
        }

    }

}
