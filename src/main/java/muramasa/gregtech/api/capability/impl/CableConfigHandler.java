package muramasa.gregtech.api.capability.impl;

import muramasa.gregtech.common.tileentities.base.TileEntityBase;
import muramasa.gregtech.common.tileentities.base.TileEntityCable;
import net.minecraft.util.EnumFacing;

public class CableConfigHandler extends ConfigHandler {

    public CableConfigHandler(TileEntityBase tile) {
        super(tile);
    }

    @Override
    public boolean onWrench(EnumFacing side) {
        if (tile instanceof TileEntityCable) {
            ((TileEntityCable) tile).toggleConnection(side);
            return true;
        }
        return false;
    }
}