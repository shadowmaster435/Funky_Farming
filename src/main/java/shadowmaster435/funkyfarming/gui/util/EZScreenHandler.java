package shadowmaster435.funkyfarming.gui.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import shadowmaster435.funkyfarming.gui.customelements.BaseElement;
import shadowmaster435.funkyfarming.gui.customelements.RenderedSlot;
import shadowmaster435.funkyfarming.gui.customelements.SlotGrid;
import shadowmaster435.funkyfarming.init.FFScreens;

import java.util.ArrayList;
import java.util.List;

public class EZScreenHandler<T extends EZScreenHandler<T>> extends ScreenHandler  {
    public BlockPos pos;
    PropertyDelegate propertyDelegate;

    public List<BaseElement<T>> elements;

    private List<BaseElement<T>> builderElements;

    public PlayerInventory playerInventory;

    public ScreenHandlerType<T> type;


    public Inventory inventory;

    public int size = 0;

    public int syncId;
    public EZScreenHandler(int syncId, PlayerInventory playerInventory, int size, PacketByteBuf buf, List<BaseElement<T>> elements) {

        this(syncId, playerInventory, new SimpleInventory(size), elements);

        pos = buf.readBlockPos();
    }
    public EZScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, List<BaseElement<T>> elements) {
        super(FFScreens.WORKTABLE_SCREEN_HANDLER, syncId);
        this.playerInventory = playerInventory;
        this.inventory = inventory;
        this.elements = elements;
        //some inventories do custom logic when a player opens it.
        assert this.elements != null;
        checkSize(inventory, this.elements.size());
        inventory.onOpen(playerInventory.player);
        pos = BlockPos.ORIGIN;
        checkSize(inventory, this.elements.size());

        this.addInventory(playerInventory, inventory);


        this.syncId = syncId;
    }

    public EZScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(FFScreens.WORKTABLE_SCREEN_HANDLER, syncId);
    }

    public EZScreenHandler<T> getHandler() {
        return this;
    }


    public void addInventory(PlayerInventory playerInventory, Inventory inventory) {
        int slotindex = 0;
        int m;
        int l;
        // The Player Inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
        // The Rest
        for (BaseElement<T> element: elements) {
            if (element instanceof RenderedSlot<T>) {
                this.addSlot(new Slot(inventory, slotindex, element.x, element.y));
                ++this.size;
            }
            if (element instanceof SlotGrid<T> slotGrid) {
                for (int x = 0; x < slotGrid.size.x; x++) {
                    for (int y = 0; y < slotGrid.size.y; y++) {

                        this.addSlot(new Slot(inventory, slotindex,slotGrid.pos.x + (x * 18), slotGrid.pos.y + (y * 18)));
                        ++this.size;
                        ++slotindex;

                    }
                }
            }
        }
    }


    public EZScreenHandler<T> elementBuilder() {
        this.builderElements = new ArrayList<>();
        return this;
    }

    public EZScreenHandler<T> addElement(BaseElement<T> element) {
        this.builderElements.add(element);
        return this;
    }

    public List<BaseElement<T>> createElements() {
        return this.builderElements;
    }

    public List<BaseElement<T>> getElements() {
        return elements;
    }

    public PropertyDelegate getPropertyDelegate() {
        return propertyDelegate;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }




    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }
}
