package net.brasscord.mods.spookygraves.blocks;

import net.brasscord.mods.spookygraves.entities.blocks.GraveBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.state.property.Properties.FACING;

public class GraveBlock extends Block implements BlockEntityProvider {

    public GraveBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
    {
        stateManager.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GraveBlockEntity(pos, state);
    }
}
