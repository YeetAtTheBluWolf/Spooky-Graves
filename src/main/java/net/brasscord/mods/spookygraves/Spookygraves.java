package net.brasscord.mods.spookygraves;

import net.brasscord.mods.spookygraves.register.Registries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static net.brasscord.mods.spookygraves.register.Registries.SET_WORLD_PACKET;

public class Spookygraves implements ModInitializer {
    //Doing some work, and found a beautiful seed 6412750098519794633

    // Data
    private static PacketByteBuf data;

    public static final String id = "spookygraves";

    private final Registries registries = new Registries();

    @Override
    public void onInitialize() {
        registries.getRegistries();
    }

    public static void graveInstrument(World world, Vec3d position, PlayerEntity player)
    {
        if(world.isClient)
            return;

        BlockPos blockPos = new BlockPos(position.x, position.y - 1, position.z);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();

        world.setBlockState(blockPos, Registries.graveBlock.getDefaultState());
        System.out.println("Executed the graveInstrument method.");

    }
}
