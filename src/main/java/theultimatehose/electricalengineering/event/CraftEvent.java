package theultimatehose.electricalengineering.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import theultimatehose.electricalengineering.item.ItemManager;
import net.minecraft.item.ItemStack;

public class CraftEvent {

    @SubscribeEvent
    public void onCrafted(ItemCraftedEvent event) {
        if (event.crafting.isItemEqual(new ItemStack(ItemManager.coil))) {
            event.crafting.setItemDamage(event.craftMatrix.getStackInSlot(4).getItemDamage() - 8);
        }
    }

}
