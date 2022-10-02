package net.brasscord.mods.spookygraves.register;

import net.brasscord.mods.spookygraves.blocks.GraveBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class Registries {

    // Blocks
    private final GraveBlock graveBlock = new GraveBlock(FabricBlockSettings.of(Material.STONE).dropsNothing());

    // Items
    private final BlockItem graveItem = new BlockItem(graveBlock, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16));

    public void getRegistries()
    {
        BlockRegister.register("grave", graveBlock);
        ItemRegister.register("grave", graveItem);
    }

}
