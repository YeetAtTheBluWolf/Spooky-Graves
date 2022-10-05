package net.brasscord.mods.spookygraves;

import net.brasscord.mods.spookygraves.events.PlayerDeathCallback;
import net.brasscord.mods.spookygraves.register.Registries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.commons.lang3.NotImplementedException;

import static net.brasscord.mods.spookygraves.register.Registries.SET_WORLD_PACKET;

public class Spookygraves implements ModInitializer {

    // Data
    private static PacketByteBuf data;


    public static final String id = "spookygraves";

    private final Registries registries = new Registries();

    @Override
    public void onInitialize() {
        registries.getRegistries();

        PlayerDeathCallback.EVENT.register((player) -> {

            data = PacketByteBufs.create();
            data.writeBlockPos(player.getBlockPos());

            ClientPlayNetworking.send(SET_WORLD_PACKET, data);

            ServerPlayNetworking.registerGlobalReceiver(SET_WORLD_PACKET, (server, player1, handler, data, responseSender) -> {

                BlockPos pos = data.readBlockPos();
                Block blockToSet = Registry.BLOCK.get(data.readIdentifier());

                server.execute(() -> {
                    player1.getWorld().setBlockState(pos, blockToSet.getDefaultState());
                });

            });

            return ActionResult.SUCCESS;
        });

    }

    private static void blockReplacement(BlockPos pos, World world)
    {


        data.writeIdentifier(new Identifier(Spookygraves.id, "grave"));

        if(world.isClient) ClientPlayNetworking.send(SET_WORLD_PACKET, data);
    }
}
