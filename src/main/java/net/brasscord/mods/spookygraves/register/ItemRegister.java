package net.brasscord.mods.spookygraves.register;

import net.brasscord.mods.spookygraves.Spookygraves;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegister {

    protected static void register(String name, Item item)
    {
        Registry.register(Registry.ITEM, new Identifier(Spookygraves.id, name), item);
    }

}
