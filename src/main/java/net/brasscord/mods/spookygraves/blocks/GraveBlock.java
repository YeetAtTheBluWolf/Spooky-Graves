package net.brasscord.mods.spookygraves.blocks;

import net.brasscord.mods.spookygraves.entities.blocks.GraveBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.brasscord.mods.spookygraves.Spookygraves.GRAVE;
import static net.minecraft.state.property.Properties.FACING;

public class GraveBlock extends HorizontalFacingBlock implements BlockEntityProvider
{

    public GraveBlock(Settings settings)
    {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    /**
    * This method adds the ability to change the way the grave faces.
    *
    * @param  stateManager  This param adds a StateManager builder which adds the capabilities to change the state.
    */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
    {
        stateManager.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ct) {
        if(stateManager.getDefaultState().get(Properties.HORIZONTAL_FACING) == Direction.WEST)
            return VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.1875f, 1.0f, 0.9375f);
        if(stateManager.getDefaultState().get(Properties.HORIZONTAL_FACING) == Direction.SOUTH)
            return VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.1875f, 1.0f, 0.9375f);
        if(stateManager.getDefaultState().get(Properties.HORIZONTAL_FACING) == Direction.EAST)
            return VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.1875f, 1.0f, 0.9375f);
        return VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.1875f, 1.0f, 0.9375f);
    }
    // 16
    /*
    1pixel = 0.0625
    2pixel = 0.125
    3pixel = 0.1875
    4pixel = 0.25
    5pixel = 0.3125
    6pixel = 0.375
    7pixel = 0.4375
    8pixel = 0.5
    9pixel = 0.5625
    10pixel = 0.625
    11pixel = 0.6875
    12pixel = 0.75
    13pixel = 0.8125
    14pixel = 0.875
    15pixel = 0.9375
    16pixel = 1.0
     */

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
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        if(RetrieveGrave(player, world, pos))
        {
            System.out.println("returning");
            return;
        }

        System.out.println("TEST ONBREAK METHOD");
        graveRetrieval(world, pos);
        super.onBreak(world, pos, state, player);
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

    private boolean RetrieveGrave(PlayerEntity playerEntity, World world, BlockPos pos) {
        if(world.isClient)
            return false;

        BlockEntity blockEntity = world.getBlockEntity(pos);

        if(!(blockEntity instanceof GraveBlockEntity)) return false;
        GraveBlockEntity blockEntity1 = (GraveBlockEntity) blockEntity;

        blockEntity1.markDirty();

        if(blockEntity1.getItems() == null) return false;
        if(blockEntity1.getOwner() == null) return false;

        DefaultedList<ItemStack> retrievalInventory = DefaultedList.of();
        retrievalInventory.addAll(playerEntity.getInventory().main);
        retrievalInventory.addAll(playerEntity.getInventory().armor);
        retrievalInventory.addAll(playerEntity.getInventory().offHand);

        playerEntity.getInventory().clear();

        ItemScatterer.spawn(world, pos, blockEntity1.getItems());

        playerEntity.addExperience(blockEntity1.getTotalExperience());

        world.removeBlock(pos, false);
        return true;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack itemStack)
    {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(!(blockEntity instanceof GraveBlockEntity))
        {
            super.onPlaced(world,pos,state,entity, itemStack);
        }
    }
}
