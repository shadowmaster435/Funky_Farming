package shadowmaster435.funkyfarming.item;

import com.ibm.icu.impl.number.parse.InfinityMatcher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import shadowmaster435.funkyfarming.gui.GuideBookScreenHandler;
import shadowmaster435.funkyfarming.util.BookUtil;

import java.awt.print.Book;
import java.util.Objects;

public class GuideBook extends Item {
    public GuideBook(Settings settings) {
        super(settings);
    }
    public static boolean isopen = false;
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {


        return super.finishUsing(stack, world, user);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        NbtCompound nbt = stack.getOrCreateNbt();

        nbt.putString("currentpage", "funkyfarming:frontpage");

        super.onCraft(stack, world, player);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        NbtCompound nbt = stack.getOrCreateNbt();
        PlayerEntity player = MinecraftClient.getInstance().player;

        if (player != null) {
            if (nbt.getString("currentpage") == null || nbt.get("currentpage") == null) {
                nbt.putString("currentpage", "funkyfarming:frontpage");
            }



        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }




    public NamedScreenHandlerFactory createScreenHandlerFactory() {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new GuideBookScreenHandler(syncId), Text.translatable("funkyfarming.guidebook"));
    }
}
