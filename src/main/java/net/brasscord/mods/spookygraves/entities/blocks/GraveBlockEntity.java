package net.brasscord.mods.spookygraves.entities.blocks;

import com.mojang.authlib.GameProfile;
import net.brasscord.mods.spookygraves.Spookygraves;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class GraveBlockEntity extends BlockEntity {

    private GameProfile owner;

    public GraveBlockEntity(BlockPos pos, BlockState state) {
        super(Spookygraves.GRAVE, pos, state);
    }

    public GameProfile getOwner()
    {
        return owner;
    }

    public void setOwner(GameProfile owner)
    {
        this.owner = owner;
    }

}
