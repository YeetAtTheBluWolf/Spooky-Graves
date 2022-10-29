package net.brasscord.mods.spookygraves.blocks;

import net.brasscord.mods.spookygraves.entities.blocks.GraveBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.brasscord.mods.spookygraves.Spookygraves.GRAVE;
import static net.minecraft.state.property.Properties.FACING;

public class GraveBlock extends Block implements BlockEntityProvider
{

    public GraveBlock(Settings settings)
    {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    /**
    * This method adds the ability to change the way the grave faces.
    *
    * @param  stateManager  This param adds a StateManager builder which adds the capabilities to change the state.
    */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
    {
        stateManager.add(FACING);
    }

    /**
    * This adds the capabilities of a Grave Block Entity.
    *
    * @param  pos  The position is used to create a new GraveBlockEntity.
    * @param  state  The state is used to create a new GraveBlockEntity.
    * @return  new GraveBlockEntity  This creates a new grave block entity using the position and state param.
    */
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GraveBlockEntity(pos, state);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {

        graveRetrieval(world, pos);
        super.onBreak(world, pos, state, player);
        System.out.println("TEST ONBREAK METHOD");

    }

    private void graveRetrieval(World world, BlockPos pos)
    {
        if(world.isClient)
            return;

        BlockEntity blockEntity = world.getBlockEntity(pos);

        if(!(blockEntity instanceof GraveBlockEntity))
            return;

        GraveBlockEntity graveBlockEntity = (GraveBlockEntity) blockEntity;
        graveBlockEntity.markDirty();

        if(graveBlockEntity.getItems() == null)
            return;

        ItemScatterer.spawn(world, pos, graveBlockEntity.getItems());
        graveBlockEntity.setStacks(DefaultedList.copyOf(ItemStack.EMPTY));


    }

}
