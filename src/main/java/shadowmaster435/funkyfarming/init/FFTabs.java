package shadowmaster435.funkyfarming.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class FFTabs {

    private static final ItemGroup BLOCKS = FabricItemGroup.builder(new Identifier("funkyfarming", "blocks"))
            .icon(() -> new ItemStack(FFBlocks.PEARLSTONE))
            .build();
    private static final ItemGroup ITEMS = FabricItemGroup.builder(new Identifier("funkyfarming", "items"))
            .icon(() -> new ItemStack(FFItems.VORACE_BULB))
            .build();

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(BLOCKS).register(content -> {
            content.add(FFBlocks.GLYCEROOT_BLOCK);
            content.add(FFBlocks.GLYCEROOT_FUSE);
            content.add(FFBlocks.MECHALILLY);
            content.add(FFBlocks.GENERATOR);
            content.add(FFBlocks.EXOTIC_SOIL);
            content.add(FFBlocks.EXOTIC_FARMLAND);
            content.add(FFBlocks.PYLON);
            content.add(FFBlocks.PEARLSTONE);
            content.add(FFBlocks.PEARLSTONE_PILLAR);
            content.add(FFBlocks.GATEWAY);
            content.add(FFBlocks.SLAGATE);
            content.add(FFBlocks.SCRAP_SOIL);
            content.add(FFBlocks.STALAGPIPE);
            content.add(FFBlocks.COPPER_TUBE);
            content.add(FFBlocks.MEDUIM_COPPER_TUBE);
            content.add(FFBlocks.SMALL_COPPER_TUBE);
            content.add(FFBlocks.WORKTABLE);
            content.add(FFBlocks.FLORALITE_ORE);
            content.add(FFBlocks.INFUSER);
            content.add(FFBlocks.INFUSION_PEDASTAL);
            content.add(FFBlocks.SHEARUELLIA);
            content.add(FFBlocks.ERODAISY);

        });

        ItemGroupEvents.modifyEntriesEvent(ITEMS).register(content -> {
            content.add(FFItems.VORACE_BULB);
            content.add(FFItems.LINKER);
            content.add(FFItems.FLORALITE);
            content.add(FFItems.SIMPLE_ROTOR);
            content.add(FFItems.IRON_GEAR);
            content.add(FFItems.COPPER_GEAR);
            content.add(FFItems.VERDURIUM_INGOT);
            content.add(FFItems.CONFLAGRITE);

        });
    }
}
