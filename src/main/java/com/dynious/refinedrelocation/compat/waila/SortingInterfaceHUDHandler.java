package com.dynious.refinedrelocation.compat.waila;

import com.dynious.refinedrelocation.helper.StringHelper;
import com.dynious.refinedrelocation.lib.Strings;
import com.dynious.refinedrelocation.tileentity.TileSortingInterface;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class SortingInterfaceHUDHandler implements IWailaDataProvider
{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> strings, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        if (accessor.getTileEntity() instanceof TileSortingInterface)
        {
            strings.add(StatCollector.translateToLocalFormatted(Strings.CONNECTED_DIRECTION, StringHelper.getLocalizedDirection(accessor.getNBTData().getByte("side"))));
        }
        return strings;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP entityPlayerMP, TileEntity tileEntity, NBTTagCompound nbtTagCompound, World world, int i, int i1, int i2)
    {
        if (tileEntity != null)
            tileEntity.writeToNBT(nbtTagCompound);
        return nbtTagCompound;
    }
}
