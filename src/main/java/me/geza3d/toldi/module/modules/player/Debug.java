package me.geza3d.toldi.module.modules.player;

import java.awt.Color;

import me.geza3d.toldi.module.EnumModuleType;
import me.geza3d.toldi.module.ToldiModule;
import me.geza3d.toldi.module.settings.NumberSetting.DoubleSetting;
import me.geza3d.toldi.module.settings.NumberSetting.IntegerSetting;
import me.geza3d.toldi.module.settings.Setting.BooleanSetting;
import me.geza3d.toldi.module.settings.Setting.ColorSetting;
import me.geza3d.toldi.module.settings.Setting.ModeSetting;

@ToldiModule.Type(EnumModuleType.PLAYER)
public class Debug extends ToldiModule {

	IntegerSetting test1 = new IntegerSetting(this, "test1", 5, 0, 10);
	DoubleSetting test2 = new DoubleSetting(this, "test2", 5d, 0d, 10d);
	BooleanSetting test3 = new BooleanSetting(this, "test3", false);
	ModeSetting test4 = new ModeSetting(this, "test4", 0, "A", "B", "C");
	ColorSetting test5 = new ColorSetting(this, "test5", Color.BLUE.getRGB());
	
	/*@Listener
	public void onPacket() {
		PacketCallback.IN.register(packet -> {
			if(getStatus()) Toldi.LOGGER.info(packet.getClass());
			return ActionResult.SUCCESS;
		});
	}*/
}










































































/* OMG THE CLIENT IS RATTED!!!! HERE'S THE RAT!!!!!!!!!!!!!
                         +----------+
                         |          +-+
                     +---+            |
           +--+   +--+                +-+
           |  +---+                     |
           | ++                         |
           | ||                         |
         +-+-++                         +-+
        ++                                |
       ++                                 |
   +---+  +--+                            |
  ++      |  |                            +---+-+
 ++       +--+                            +---+ |
++                                        |   | |
|                                       +-+   | |
+---------+                        +----+     | |
          +-----+                  |          | |
                +------+    +------+          | |
                       +----+                 | |
                                              | |
             +--------------------------------+ |
             +--------------------------------+-+
             
Hi ian and nathan
*/
