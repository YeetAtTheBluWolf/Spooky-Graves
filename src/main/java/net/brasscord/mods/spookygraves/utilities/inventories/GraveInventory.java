package net.brasscord.mods.spookygraves.utilities.inventories;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public interface GraveInventory extends Inventory {

    DefaultedList<ItemStack> getInvStackList();

    @Override
    default int size() {
        return PlayerInventory.MAIN_SIZE + 5;
    }

    @Override
    default boolean isEmpty() {
        return this.getInvStackList().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    default ItemStack getStack(int slot) {
        return this.getInvStackList().get(slot);
    }

    @Override
    default ItemStack removeStack(int slot, int amount)
    {
        ItemStack itemStack = Inventories.splitStack(this.getInvStackList(), slot, amount);
        if (!itemStack.isEmpty()) {
            markDirty();
        }
        return itemStack;
    }

    @Override
    default ItemStack removeStack(int slot) {
        return this.getInvStackList().remove(slot);
    }

    @Override
    default void setStack(int slot, ItemStack stack) {
        this.getInvStackList().set(slot, stack);
    }

    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    default void clear() {
        this.getInvStackList().clear();
    }

    NbtCompound toClientTag(NbtCompound tag);

    void fromClientTag(NbtCompound tag);
}
