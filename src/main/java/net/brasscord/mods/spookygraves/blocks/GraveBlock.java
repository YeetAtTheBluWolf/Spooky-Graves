package net.brasscord.mods.spookygraves.blocks;

import net.brasscord.mods.spookygraves.entities.GraveBlockEntity;
import net.brasscord.mods.spookygraves.register.Registries;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class GraveBlock extends Block /*implements BlockEntityProvider*/ {

    public GraveBlock(Settings settings) {
        super(settings);
    }

//    @Override
//    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState)
//    {
//        return new GraveBlockEntity(new BlockEntityType<>(GraveBlockEntity::new), blockPos, blockState);
//    }

}
