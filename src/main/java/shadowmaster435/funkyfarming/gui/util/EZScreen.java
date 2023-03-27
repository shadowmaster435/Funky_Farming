package shadowmaster435.funkyfarming.gui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.gui.customelements.*;
import shadowmaster435.funkyfarming.gui.customelements.barTypes.CardinalProgressElement;

import java.util.List;

public class EZScreen<T extends EZScreenHandler<T>> extends HandledScreen<T> {
    public boolean narrow;
    private final Identifier texture;

    private final List<BaseElement<T>> elements;


    public EZScreen(EZScreenHandler<T> handler, PlayerInventory inventory, Text title, Identifier texture) {
        super((T) handler, inventory, title);
        this.texture = texture;
        this.elements = handler.getElements();
    }




    public List<BaseElement<T>> getElements() {
        return elements;
    }

    public void drawAll(MatrixStack matrices, List<BaseElement<T>> elements, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.texture);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
        this.drawElements(matrices, elements, mouseX, mouseY);
        this.drawProgressTypes(matrices, mouseX, mouseY);
    }


    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

    }
    public void drawElements(MatrixStack matrices, List<BaseElement<T>> elements, int mouseX, int mouseY) {
            for (BaseElement<T> element : this.elements) {
                if (!(element instanceof ProgressBar<T> progressBar)) {
                    if (element instanceof Button<T> button) {
                        if (button.isIndexType()) {
                            button.render(matrices, mouseX, mouseY, button.getDelagateIndex());
                        } else {
                            button.render(matrices, mouseX, mouseY, button.getDelagateIndex());

                        }
                    }
                    if (element instanceof RenderedSlot<T> slot) {
                        slot.renderSlot(matrices, this);
                    }
                    if (element instanceof SlotGrid<T> slot) {
                        slot.renderSlotGrid(matrices, this);
                    }
                }
            }
    }

    public void drawProgressTypes(MatrixStack matrices, int mouseX, int mouseY) {
        for (BaseElement<T> element : this.elements) {
            if (element instanceof ProgressBar<T> progressBar) {
                int delagateIndex = progressBar.delagateIndex;
                if (progressBar.tooltip != null) {
                    progressBar.renderTooltip(matrices, mouseX, mouseY);
                }
                if (element instanceof CardinalProgressElement<T> cardinalProgressElement) {
                    switch (cardinalProgressElement.getDirection()) {
                        case "horizontal" -> cardinalProgressElement.renderHorizontal(matrices, delagateIndex);
                        case "vertical" -> cardinalProgressElement.renderVertical(matrices, delagateIndex);
                    }
                }
            }
        }
    }


}
