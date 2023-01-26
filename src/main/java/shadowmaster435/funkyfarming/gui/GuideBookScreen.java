package shadowmaster435.funkyfarming.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.gui.bookelements.Page;

public class GuideBookScreen extends HandledScreen<GuideBookScreenHandler> {

    public PlayerInventory inventory;

    public GuideBookScreen(GuideBookScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.inventory = inventory;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

        Page.renderpage(inventory, mouseX, mouseY);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.enableTexture();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, new Identifier("funkyfarming:textures/gui/book/book_bg.png"));
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(0, 0, 0).texture(0,0);
        bufferBuilder.vertex(255, 0, 0).texture(0.5f,0);
        bufferBuilder.vertex(255, 255, 0).texture(0.5f,0.5f);
        bufferBuilder.vertex(0, 255, 0).texture(0,0.5f);

        tessellator.draw();
        RenderSystem.disableTexture();
    }
}
