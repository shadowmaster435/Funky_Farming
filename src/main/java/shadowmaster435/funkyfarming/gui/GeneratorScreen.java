package shadowmaster435.funkyfarming.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class GeneratorScreen extends HandledScreen<GeneratorScreenHandler> {
    //A path to the gui texture. In this example we use the texture from the dispenser
    private static final Identifier TEXTURE = new Identifier("funkyfarming", "textures/gui/generatorgui.png");
    GeneratorScreenHandler screenHandler;


    public GeneratorScreen(GeneratorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, getPositionText(handler).orElse(title));
        screenHandler = (GeneratorScreenHandler) handler;
    }

    private static Optional<Text> getPositionText(ScreenHandler handler) {
        return Optional.empty();

    }
    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
        int prog = this.getScreenHandler().propertyDelegate.get(0);
        int energy = this.getScreenHandler().propertyDelegate.get(1);

        if (energy > 0) {
            drawTexture(matrices, x + 126, (y + 58) - (int) Math.floor(31 * ((energy) / 10000f)), 6, 166, 6, (int) Math.floor(31 * ((energy) / 10000f )));
        }
        drawTexture(matrices, x + 120, (y + 58) - prog , 0, 166, 6,  prog);
        if (mouseX > 277 && mouseX < 284 && mouseY > 70 && mouseY < 104) {
            renderTooltip(matrices, Text.of(energy + "/10000"), mouseX, mouseY);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);


        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}