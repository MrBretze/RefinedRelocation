package com.dynious.blex.network.packet;

import com.dynious.blex.network.PacketTypeHandler;
import com.dynious.blex.tileentity.IAdvancedTile;
import com.dynious.blex.tileentity.TileAdvancedBlockExtender;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketSpread extends PacketTile
{
    public PacketSpread()
    {
        super(PacketTypeHandler.SPREAD_ITEMS, false);
    }

    public PacketSpread(TileEntity tile)
    {
        super(PacketTypeHandler.SPREAD_ITEMS, false, tile);
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        super.writeData(data);
    }

    @Override
    public void readData(DataInputStream data) throws IOException
    {
        super.readData(data);
    }

    @Override
    public void execute(INetworkManager manager, Player player)
    {
        super.execute(manager, player);
        System.out.println(tile);
        if (tile instanceof IAdvancedTile)
        {
            System.out.println("!SPREAD");
            ((IAdvancedTile)tile).setSpreadItems(!((IAdvancedTile)tile).getSpreadItems());
        }
    }
}