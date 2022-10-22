package net.brasscord.mods.spookygraves.register;

import net.brasscord.mods.spookygraves.Spookygraves;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegister
{

    /**
    * This method registers a block.
    *
    * @param  name  This takes a String, and it's used to give an id to a block.
    * @param  block  This takes a Block, and it's used to register a block.
    */
    protected static void register(String name, Block block)
    {
        Registry.register(Registry.BLOCK, new Identifier(Spookygraves.id, name), block);
    }

}
