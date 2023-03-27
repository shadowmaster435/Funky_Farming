package shadowmaster435.funkyfarming;

import net.fabricmc.api.ModInitializer;
import shadowmaster435.funkyfarming.init.*;

public class Funkyfarming implements ModInitializer {
    @Override
    public void onInitialize() {
        FFBlocks.registerBlocks();
        FFBlocks.registerItemBlocks();
        FFBlocks.registerBlockEntities();
        FFItems.registerItems();
        FFSounds.registerSounds();
        FFScreens.init();
        FFTabs.init();
        FFCustomRecipes.init();

    }
}
