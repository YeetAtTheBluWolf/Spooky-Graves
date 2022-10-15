package net.brasscord.mods.spookygraves.blocks;

import net.brasscord.mods.spookygraves.entities.blocks.GraveBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.state.property.Properties.FACING;

public class GraveBlock extends Block implements BlockEntityProvider {

    public GraveBlock(Settings settings) {
        super(settings);
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

    public void onBreak(World world, PlayerEntity player, BlockPos pos, BlockState blockState, int experience)
    {
        if(retrieveGrave(player, world, pos, experience))
            return;

        super.onBreak(world, pos, blockState, player);
    }

    public boolean retrieveGrave(PlayerEntity player, World world, BlockPos pos, int experience)
    {

        if(world.isClient)
            return false;

        BlockEntity block = world.getBlockEntity(pos);

        if(!(block instanceof GraveBlockEntity graveBlockEntity))
            return false;

        if(graveBlockEntity.getOwner() == null)
            return false;
        if(graveBlockEntity.getInvStackList() == null)
            return false;

        if(!player.getGameProfile().getId().equals(graveBlockEntity.getOwner().getId()))
            return false;

        DefaultedList<ItemStack> itemStacks = graveBlockEntity.getInvStackList();
        DefaultedList<ItemStack> retrieval = DefaultedList.of();

        retrieval.addAll(player.getInventory().main);
        retrieval.addAll(player.getInventory().armor);
        retrieval.addAll(player.getInventory().offHand);

        player.getInventory().clear();

        List<ItemStack> armor = itemStacks.subList(36, 40);
        for (ItemStack stack : armor) {
            EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(stack);
            player.equipStack(equipmentSlot, stack);
        }

        player.equipStack(EquipmentSlot.OFFHAND, itemStacks.get(40));
        List<ItemStack> mainInventory = itemStacks.subList(0, 36);

        for (ItemStack itemStack : mainInventory) {
            player.giveItemStack(itemStack);
        }

        DefaultedList<ItemStack> extraItems = DefaultedList.of();
        List<Integer> openArmorSlots = getInventoryOpenSlots(player.getInventory().armor);

        for(int i = 0; i < 4; i++)
        {
            if(openArmorSlots.contains(i))
                player.equipStack(EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, i), retrieval.subList(36, 40).get(i));
            else
                extraItems.add(retrieval.subList(36, 40).get(i));
        }

        if(player.getInventory().offHand.get(0) == ItemStack.EMPTY)
            player.equipStack(EquipmentSlot.OFFHAND, retrieval.get(40));
        else
            extraItems.add(retrieval.get(40));

        extraItems.addAll(retrieval.subList(0, 36));

        if(retrieval.size() > 41)
            extraItems.addAll(retrieval.subList(41, retrieval.size()));

        List<Integer> openSlots = getInventoryOpenSlots(player.getInventory().main);

        for(int i = 0; i < openSlots.size(); i++)
            player.getInventory().main.add(openSlots.size(), extraItems.get(i));

        DefaultedList<ItemStack> droppedItems = DefaultedList.of();
        droppedItems.addAll(extraItems.subList(openSlots.size(), extraItems.size()));
        ItemScatterer.spawn(world, pos, droppedItems);

        player.addExperience(experience);

        world.removeBlock(pos, false);
        return true;
    }

    private List<Integer> getInventoryOpenSlots(DefaultedList<ItemStack> inventory) {
        List<Integer> openSlots = new ArrayList<>();

        for (int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i) == ItemStack.EMPTY)
                openSlots.add(i);
        }

        return openSlots;
    }

}
