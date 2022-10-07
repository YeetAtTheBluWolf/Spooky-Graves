package net.brasscord.mods.spookygraves.entities;

import net.brasscord.mods.spookygraves.register.Registries;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class GraveBlockEntity extends BlockEntity {

    private PlayerEntity owner;

    public GraveBlockEntity(BlockEntityType<? extends BlockEntity> type, BlockPos blockPos, BlockState blockState)
    {
        super(type, blockPos, blockState);
    }



}
