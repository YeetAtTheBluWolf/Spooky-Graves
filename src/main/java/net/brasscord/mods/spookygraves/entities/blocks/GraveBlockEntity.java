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
    }



}
