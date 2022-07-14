package shadowmaster435.funkyfarming;

import net.fabricmc.api.ModInitializer;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.init.FFItems;
import shadowmaster435.funkyfarming.init.FFSounds;

public class Funkyfarming implements ModInitializer {
    @Override
    public void onInitialize() {
        FFBlocks.registerBlocks();
        FFBlocks.registerItemBlocks();
        FFBlocks.registerBlockEntities();
        FFItems.registerItems();
        FFSounds.registerSounds();
    }
}
