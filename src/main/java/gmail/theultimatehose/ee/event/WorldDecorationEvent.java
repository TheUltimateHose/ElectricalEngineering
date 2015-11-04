package gmail.theultimatehose.ee.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import gmail.theultimatehose.ee.Main;
import gmail.theultimatehose.ee.config.values.CfgFloatValues;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

public class WorldDecorationEvent {

    @SubscribeEvent
    public void onWorldDecoration(DecorateBiomeEvent.Decorate event) {
        float cfg = CfgFloatValues.OLD_MACHINE_RARITY.getValue();
        int chance = Math.round(1024*cfg);
        if (event.rand.nextInt(chance) == 0) {
            if (event.getResult() == Event.Result.ALLOW || event.getResult() == Event.Result.DEFAULT) {
                int posX = event.chunkX + event.rand.nextInt(16) + 8;
                int posZ = event.chunkZ + event.rand.nextInt(16) + 8;
                int posY = event.world.getTopSolidOrLiquidBlock(posX, posZ);

                event.world.setBlock(posX, posY, posZ, Main.machineBurnt);
            }
        }
    }

}