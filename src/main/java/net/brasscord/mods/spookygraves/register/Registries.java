package net.brasscord.mods.spookygraves.register;

import net.brasscord.mods.spookygraves.Spookygraves;
import net.brasscord.mods.spookygraves.blocks.GraveBlock;
import net.brasscord.mods.spookygraves.events.PlayerDeathCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.NotImplementedException;

public class Registries {
    // Blocks
    private final GraveBlock graveBlock = new GraveBlock(FabricBlockSettings.of(Material.STONE).dropsNothing());

    // Items
    private final BlockItem graveItem = new BlockItem(graveBlock, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16));

    // Identifier
    public final static Identifier SET_WORLD_PACKET = new Identifier(Spookygraves.id, "setblock");

    public void getRegistries()
    {

        // Blocks
        BlockRegister.register("grave", graveBlock);

        // Items
        ItemRegister.register("grave", graveItem);

    }

}
