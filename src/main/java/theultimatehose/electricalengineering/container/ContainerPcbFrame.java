package theultimatehose.electricalengineering.container;

import theultimatehose.electricalengineering.tile.TileEntityPcbFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerPcbFrame extends Container {

    private TileEntityPcbFrame tilePcbFrame;

    public ContainerPcbFrame(InventoryPlayer inventory, TileEntityPcbFrame tile) {
        tilePcbFrame = tile;

        //Player inventory slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 48 + j * 18, 146 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inventory, i, 48 + i * 18, 204));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tilePcbFrame.isUseableByPlayer(player);
    }
}
