package shadowmaster435.funkyfarming.init;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.gui.GeneratorScreen;
import shadowmaster435.funkyfarming.gui.GeneratorScreenHandler;
import shadowmaster435.funkyfarming.gui.WorktableScreen;
import shadowmaster435.funkyfarming.gui.WorktableScreenHandler;

public class FFScreens {

    public static final ScreenHandlerType<GeneratorScreenHandler> GENERATOR_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(GeneratorScreenHandler::new);
    public static final ExtendedScreenHandlerType<WorktableScreenHandler> WORKTABLE_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(WorktableScreenHandler::new);


    public static void initClient() {
        HandledScreens.register(GENERATOR_SCREEN_HANDLER, GeneratorScreen::new);
        HandledScreens.register(WORKTABLE_SCREEN_HANDLER, WorktableScreen::new);

    }

    public static void init() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier("funkyfarming", "generator"), GENERATOR_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier("funkyfarming", "worktable"), WORKTABLE_SCREEN_HANDLER);

    }



}
