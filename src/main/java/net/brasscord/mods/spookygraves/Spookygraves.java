package net.brasscord.mods.spookygraves;

import net.brasscord.mods.spookygraves.entities.blocks.GraveBlockEntity;
import net.brasscord.mods.spookygraves.register.Registries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static net.brasscord.mods.spookygraves.register.Registries.SET_WORLD_PACKET;
import static net.brasscord.mods.spookygraves.register.Registries.graveBlock;

public class Spookygraves implements ModInitializer {
    //Doing some work, and found a beautiful seed 6412750098519794633

    // Data
    private static PacketByteBuf data;

    public static final String id = "spookygraves";

    private final Registries registries = new Registries();

    public static BlockEntityType<GraveBlockEntity> GRAVE;

    @Override
    public void onInitialize() {
        registries.getRegistries();

        GRAVE = Registry.register(
                Registry.BLOCK_ENTITY_TYPE, new Identifier(id, "grave_entity"), FabricBlockEntityTypeBuilder.create(GraveBlockEntity::new, graveBlock).build()
        );

        ServerPlayNetworking.registerGlobalReceiver(SET_WORLD_PACKET, (server, player, handler, buf, sender) -> {

            // https://stackoverflow.com/questions/65903006/how-to-change-what-block-is-at-certain-coordinates

            BlockPos pos = buf.readBlockPos();
            Block blockToSet = Registry.BLOCK.get(buf.readIdentifier());

            server.execute(() -> {
                player.getWorld().setBlockState(pos, blockToSet.getDefaultState()); // changing the block state

            });
        });

    }

    public static void graveInsert(World world, Vec3d position, PlayerEntity player)
    {
        if(world.isClient)
            return;

        BlockPos blockPos = new BlockPos(position.x, position.y, position.z);
        BlockState blockState = world.getBlockState(blockPos);
        BlockState setBlock = Registries.graveBlock.getDefaultState().with(Properties.HORIZONTAL_FACING, player.getHorizontalFacing());
        GraveBlockEntity graveBlockEntity = new GraveBlockEntity(blockPos, blockState);
        graveBlockEntity.setOwner(player.getGameProfile());

        PacketByteBuf data = PacketByteBufs.create();
        data.writeBlockPos(blockPos);
        data.writeIdentifier(new Identifier(id, "grave"));


        ClientPlayNetworking.send(SET_WORLD_PACKET, data);

        System.out.println("Executed the graveInsert method.");

    }
}
