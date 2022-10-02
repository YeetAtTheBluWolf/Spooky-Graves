package net.brasscord.mods.spookygraves.register;

import net.brasscord.mods.spookygraves.Spookygraves;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegister {

    protected static void register(String name, Block block)
    {
        Registry.register(Registry.BLOCK, new Identifier(Spookygraves.id, name), block);
    }

}
