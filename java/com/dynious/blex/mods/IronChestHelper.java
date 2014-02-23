package com.dynious.blex.mods;

import com.dynious.blex.block.BlockFilteringIronChest;
import com.dynious.blex.block.ModBlocks;
import com.dynious.blex.item.ItemFilteringIronChest;
import com.dynious.blex.lib.BlockIds;
import com.dynious.blex.lib.Names;
import com.dynious.blex.renderer.ItemRendererFilteringIronChest;
import com.dynious.blex.renderer.RendererFilteringIronChest;
import com.dynious.blex.tileentity.TileFilteringChest;
import com.dynious.blex.tileentity.TileFilteringIronChest;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.ironchest.IronChest;
import cpw.mods.ironchest.IronChestType;
import cpw.mods.ironchest.ItemChestChanger;
import cpw.mods.ironchest.TileEntityIronChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class IronChestHelper
{
    public static void addIronChestBlocks()
    {
        ModBlocks.filteringIronChest = new BlockFilteringIronChest(BlockIds.FILTERING_IRON_CHEST);
        GameRegistry.registerBlock(ModBlocks.filteringIronChest, ItemFilteringIronChest.class, Names.filteringIronChest);
    }

    public static void addIronChestRecipes()
    {
        for (int i = 0; i < IronChestType.values().length; i++)
        {
            GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.filteringIronChest, 1, i), "g g", " b ", "g g", 'g', Item.ingotGold, 'b', new ItemStack(IronChest.ironChestBlock, 1, i));
        }
    }

    public static void addIronChestRenders()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileFilteringIronChest.class, new RendererFilteringIronChest());
        MinecraftForgeClient.registerItemRenderer(BlockIds.FILTERING_IRON_CHEST, new ItemRendererFilteringIronChest());
    }

    public static boolean upgradeToIronChest(World world, EntityPlayer player, int x, int y, int z)
    {
        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemChestChanger)
        {
            ItemChestChanger chestChanger = (ItemChestChanger) player.getHeldItem().getItem();
            if (chestChanger.getType().canUpgrade(IronChestType.WOOD))
            {
                TileEntity tile = world.getBlockTileEntity(x, y, z);
                if (tile instanceof TileFilteringChest)
                {
                    TileFilteringChest tec = (TileFilteringChest) tile;
                    if (tec.numUsingPlayers > 0)
                    {
                        return false;
                    }
                    // Force old TE out of the world so that adjacent chests can update
                    TileFilteringIronChest newchest = new TileFilteringIronChest(IronChestType.values()[chestChanger.getType().getTarget()]);
                    ItemStack[] chestInventory = tec.inventory;
                    ItemStack[] chestContents = chestInventory.clone();
                    newchest.setFacing((byte) tec.getBlockMetadata());
                    for (int i = 0; i < chestInventory.length; i++)
                    {
                        chestInventory[i] = null;
                    }
                    // Clear the old block out
                    world.setBlock(x, y, z, 0, 0, 3);
                    // Force the Chest TE to reset it's knowledge of neighbouring blocks
                    // And put in our block instead
                    world.setBlock(x, y, z, ModBlocks.filteringIronChest.blockID, chestChanger.getType().getTarget(), 3);

                    world.setBlockTileEntity(x, y, z, newchest);
                    world.setBlockMetadataWithNotify(x, y, z, chestChanger.getType().getTarget(), 3);
                    System.arraycopy(chestContents, 0, newchest.chestContents, 0, Math.min(chestContents.length, newchest.getSizeInventory()));
                    player.getHeldItem().stackSize--;
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean upgradeIronToFilteringChest(TileEntity tile)
    {
        if (tile instanceof TileEntityIronChest)
        {
            World world = tile.getWorldObj();

            TileEntityIronChest teic = (TileEntityIronChest) tile;
            int numUsers = ObfuscationReflectionHelper.getPrivateValue(TileEntityIronChest.class, teic, "numUsingPlayers");
            if (numUsers > 0)
            {
                return false;
            }
            TileFilteringIronChest chest = new TileFilteringIronChest(teic.getType());
            ItemStack[] chestContents = teic.chestContents.clone();
            chest.setFacing(teic.getFacing());
            for (int i = 0; i < teic.chestContents.length; i++)
            {
                teic.chestContents[i] = null;
            }
            // Clear the old block out
            world.setBlock(tile.xCoord, tile.yCoord, tile.zCoord, 0, 0, 3);
            // And put in our block instead
            world.setBlock(tile.xCoord, tile.yCoord, tile.zCoord, ModBlocks.filteringIronChest.blockID, teic.getType().ordinal(), 3);

            world.setBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord, chest);
            world.setBlockMetadataWithNotify(tile.xCoord, tile.yCoord, tile.zCoord, chest.getType().ordinal(), 3);
            System.arraycopy(chestContents, 0, chest.chestContents, 0, chest.getSizeInventory());
            return true;
        }
        return false;
    }
}
