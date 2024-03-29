package shadowmaster435.funkyfarming.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import shadowmaster435.funkyfarming.init.*;
//import shadowmaster435.funkyfarming.init.FFScreens;

@Environment(EnvType.CLIENT)
public class FunkyfarmingClient implements ClientModInitializer {
    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        FFScreens.initClient();
        FFBlocks.registerBlockLayerTypes();
        FFBlocks.registerRenderers();
        FFBlocks.registerCT();
        FFShaders.initClient();
        FFParticles.initClient();

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.examplemod.spook", // The translation key of the keybinding's name
                InputUtil.Type.MOUSE, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_MOUSE_BUTTON_RIGHT, // The keycode of the key
                "category.examplemod.test" // The translation key of the keybinding's category.
        ));
    }
}
