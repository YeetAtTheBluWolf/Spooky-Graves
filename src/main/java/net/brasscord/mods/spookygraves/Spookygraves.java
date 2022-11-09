package net.brasscord.mods.spookygraves;

import net.brasscord.mods.spookygraves.blocks.GraveBlock;
import net.brasscord.mods.spookygraves.entities.blocks.GraveBlockEntity;
import net.brasscord.mods.spookygraves.register.BlockRegister;
import net.brasscord.mods.spookygraves.register.ItemRegister;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class Spookygraves implements ModInitializer {
    // Doing some work, and found a beautiful seed 6412750098519794633
    // ID of the mod
    public static final String id = "spookygraves";

    // Blocks
    private final static GraveBlock graveBlock = new GraveBlock(FabricBlockSettings.of(Material.STONE));

    // Items
    private final BlockItem graveItem = new BlockItem(graveBlock, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16));


    public static BlockEntityType<GraveBlockEntity> GRAVE;

    @Override
    public void onInitialize() 
    {

        // Blocks
        BlockRegister.register("grave", graveBlock);

        // Items
        ItemRegister.register("grave", graveItem);

        GRAVE = Registry.register(
                Registry.BLOCK_ENTITY_TYPE, "spookygraves:grave",
                FabricBlockEntityTypeBuilder.create(GraveBlockEntity::new,
                        graveBlock).build(null)
        );

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
      */
    public static void graveInsert(World world, Vec3d position, PlayerEntity player)
    {
        if(world.isClient)
            return;

        BlockPos blockPos = new BlockPos(position.x, position.y, position.z);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();

        DefaultedList<ItemStack> items = DefaultedList.of();
        items.addAll(player.getInventory().main);
        items.addAll(player.getInventory().armor);
        items.addAll(player.getInventory().offHand);

        int experience = player.totalExperience;
        player.totalExperience = 0;
        player.experienceProgress = 0;
        player.experienceLevel = 0;

        boolean placed = false;

        BlockState graveState =  Spookygraves.graveBlock.getDefaultState().with(Properties.HORIZONTAL_FACING, player.getHorizontalFacing());

        placed = world.setBlockState(blockPos, graveState);
        GraveBlockEntity graveBlockEntity = new GraveBlockEntity(blockPos, blockState);
        graveBlockEntity.setStacks(items);
        graveBlockEntity.setTotalExperience(experience);
        graveBlockEntity.setOwner(player.getGameProfile());
        world.addBlockEntity(graveBlockEntity);

        graveBlockEntity.markDirty();
        block.onBreak(world, blockPos, blockState, player);

        System.out.println("Executed the graveInsert method.");

        if(!placed)
            player.getInventory().dropAll();
    }
}
