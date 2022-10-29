package net.brasscord.mods.spookygraves.entities.blocks;

import com.mojang.authlib.GameProfile;
import net.brasscord.mods.spookygraves.Spookygraves;
import net.brasscord.mods.spookygraves.utilities.inventories.IGraveInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GraveBlockEntity extends BlockEntity //implements IGraveInventory
{

    private DefaultedList<ItemStack> items;
    private GameProfile owner;
    private int totalExperience;

    public GraveBlockEntity(BlockPos pos, BlockState state)
    {
        super(Spookygraves.GRAVE, pos, state);

        this.items = DefaultedList.ofSize(41, ItemStack.EMPTY);
        this.owner = null;
        this.totalExperience = 0;
    }

    /**
    * This is used to get the owner.
    *
    * @return  owner is a Game Profile that is to prove that the user is the owner of the grave.
    */
    public GameProfile getOwner()
    {
        return owner;
    }

    /**
    * This is used to set the owner of the grave
    * 
    * @param  owner  This param is a GameProfile param that sets the owner variable.
    */
    public void setOwner(GameProfile owner)
    {
        this.owner = owner;
        this.markDirty();
    }

    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public void setStacks(DefaultedList<ItemStack> defaultedList)
    {
        items = defaultedList;
        this.markDirty();
    }

    public int getTotalExperience()
    {
        return totalExperience;
    }

    public void setTotalExperience(int totalExperience)
    {
        this.totalExperience = totalExperience;
        this.markDirty();
    }

    @Override
    public void readNbt(NbtCompound tag)
    {
        super.readNbt(tag);

        this.items = DefaultedList.ofSize(tag.getInt("ItemCount"), ItemStack.EMPTY);

        Inventories.readNbt(tag.getCompound("Items"), this.items);

        this.totalExperience = tag.getInt("XP");

        if(tag.contains("GraveOwner"))
            this.owner = NbtHelper.toGameProfile(tag.getCompound("GraveOwner"));
    }

    @Override
    public void writeNbt(NbtCompound tag)
    {
        super.writeNbt(tag);

        tag.putInt("ItemCount", this.items.size());

        tag.put("Items", Inventories.writeNbt(new NbtCompound(), this.items, true));

        tag.putInt("XP", totalExperience);

        if(owner != null)
            tag.put("GraveOwner", NbtHelper.writeGameProfile(new NbtCompound(), owner));
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

}
