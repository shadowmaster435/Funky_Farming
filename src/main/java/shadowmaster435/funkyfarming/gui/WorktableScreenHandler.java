package shadowmaster435.funkyfarming.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;
import shadowmaster435.funkyfarming.gui.customelements.BaseElement;
import shadowmaster435.funkyfarming.gui.customelements.SlotGrid;
import shadowmaster435.funkyfarming.gui.util.EZScreenHandler;

import javax.annotation.Nullable;
import java.util.List;

public class WorktableScreenHandler extends EZScreenHandler<WorktableScreenHandler> {

    public WorktableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PacketByteBuf buf) {
        super(syncId, playerInventory, buf);
        this.elements = this.elementBuilder()
                .addElement(new SlotGrid<>(new Vector2i(52, 16), new Vector2i(1, 3), this))
                .addElement(new SlotGrid<>(new Vector2i(72, 16), new Vector2i(3, 3), this))
                .createElements();
        this.inventory = inventory;
        this.addInventory(playerInventory, inventory);
    }

    public WorktableScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(syncId, playerInventory, buf);
        this.elements = this.elementBuilder()
                .addElement(new SlotGrid<>(new Vector2i(52, 16), new Vector2i(1, 3), this))
                .addElement(new SlotGrid<>(new Vector2i(72, 16), new Vector2i(6, 3), this))
                .createElements();
        this.inventory = new SimpleInventory(elements.size());
        this.addInventory(playerInventory, inventory);


    }
}
