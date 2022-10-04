package net.brasscord.mods.spookygraves.register;

import net.brasscord.mods.spookygraves.blocks.GraveBlock;
import net.brasscord.mods.spookygraves.events.PlayerDeathCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import org.apache.commons.lang3.NotImplementedException;

public class Registries {

    // Blocks
    private final GraveBlock graveBlock = new GraveBlock(FabricBlockSettings.of(Material.STONE).dropsNothing());

    // Items
    private final BlockItem graveItem = new BlockItem(graveBlock, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16));

    public void getRegistries()
    {

        // Blocks
        BlockRegister.register("grave", graveBlock);

        // Items
        ItemRegister.register("grave", graveItem);

        // Listeners
        PlayerDeathCallback.EVENT.register((player) -> {

            BlockState blockState = new BlockState(graveBlock, player.getWorld().getMapState(), null);

            player.getWorld().setBlockState(blockState);

            throw new NotImplementedException();
        });

    }

}
