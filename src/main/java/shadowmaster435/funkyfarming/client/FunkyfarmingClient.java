package shadowmaster435.funkyfarming.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shadowmaster435.funkyfarming.init.FFBlocks;

@Environment(EnvType.CLIENT)
public class FunkyfarmingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FFBlocks.registerBlockLayerTypes();
    }
}
