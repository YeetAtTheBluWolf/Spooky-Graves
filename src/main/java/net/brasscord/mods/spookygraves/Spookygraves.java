package net.brasscord.mods.spookygraves;

import net.brasscord.mods.spookygraves.entities.blocks.GraveBlockEntity;
import net.brasscord.mods.spookygraves.register.Registries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
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

    public static final String id = "spookygraves";
    public static BlockEntityType<GraveBlockEntity> GRAVE;

    private final Registries registries = new Registries();


    @Override
    public void onInitialize() 
    {
        registries.getRegistries();

        GRAVE = Registry.register(
                Registry.BLOCK_ENTITY_TYPE, new Identifier(id, "grave_entity"), FabricBlockEntityTypeBuilder.create(GraveBlockEntity::new, graveBlock).build()
        );

        ServerPlayNetworking.registerGlobalReceiver(SET_WORLD_PACKET, (server, player, handler, buf, sender) -> {

            // https://stackoverflow.com/questions/65903006/how-to-change-what-block-is-at-certain-coordinates

            BlockPos pos = buf.readBlockPos();
            Block blockToSet = Registry.BLOCK.get(buf.readIdentifier());

            server.execute(() -> player.getWorld().setBlockState(pos, blockToSet.getDefaultState().with(Properties.FACING, player.getMovementDirection())));
        });

        PlayerBlockBreakEvents.AFTER.register(((world, player, pos, state, blockEntity) -> {
            if(state.isOf(graveBlock)/* && Objects.requireNonNull(GRAVE.get(world, pos)).getOwner() == player.getGameProfile()*/)
            {
                for (ItemStack itemStack : GRAVE.get(world, pos).getInvStackList())
                {
                    if(itemStack != ItemStack.EMPTY)
                    {
                        player.giveItemStack(itemStack);
                    }
                }
            }
        }));

    }

     /**
     * This method gets the player position and world. If the player is a client of a server then it will return and let the server do the job instead.
     * It gets the BlockState at the position, and then we create a Grave block entity and set the owner to the player.
     * We then create a packet, and then we write the block position to the packet along with an identifier with the grave block id.
     * After all this we send the packet and print a message saying the method has been run.
     *
     * @param  world  The world where the player was at the point in time.
     * @param  position  A Vec3d of the position of the player when the method was called.
     * @param  player  This PlayerEntity is used to set the owner of the grave.
     * @param  inventory  This is needed to check and store the inventory information.
     */
    public static void graveInsert(World world, Vec3d position, PlayerEntity player, PlayerInventory inventory)
    {
        if(world.isClient)
            return;

        BlockPos blockPos = new BlockPos(position.x, position.y, position.z);
        BlockState blockState = world.getBlockState(blockPos);

        PacketByteBuf data = PacketByteBufs.create();
        data.writeBlockPos(blockPos);
        data.writeIdentifier(new Identifier(id, "grave"));
        ClientPlayNetworking.send(SET_WORLD_PACKET, data);

        GraveBlockEntity graveBlockEntity = new GraveBlockEntity(blockPos, blockState);
        graveBlockEntity.setOwner(player.getGameProfile());

        if(!inventory.isEmpty())
        {
            graveBlockEntity.setTotalExperience(player.getXpToDrop());

            int slot = 0;
            for (ItemStack itemStack : inventory.main)
            {
                if(!(slot == graveBlockEntity.size()))
                {
                    graveBlockEntity.setStack(slot, itemStack);
                    slot++;
                }
            }

            for (ItemStack itemStack : inventory.armor)
            {
                graveBlockEntity.setStack(slot, itemStack);
                slot++;
            }

            for (ItemStack itemStack : inventory.offHand)
            {
                graveBlockEntity.setStack(slot, itemStack);
                slot++;
            }
        }

        System.out.println("Executed the graveInsert method.");

    }
}
