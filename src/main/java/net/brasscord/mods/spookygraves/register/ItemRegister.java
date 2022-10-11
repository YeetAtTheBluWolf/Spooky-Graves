package net.brasscord.mods.spookygraves.register;

import net.brasscord.mods.spookygraves.Spookygraves;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegister {

    /**
    * This method registers an item easily by taking only two parameters.
    *
    * @param  name  This takes a string, and it's used to id the item.
    * @param  item  This takes an item, and it's used to register the item given.
    */
    protected static void register(String name, Item item)
    {
        Registry.register(Registry.ITEM, new Identifier(Spookygraves.id, name), item);
    }

}
