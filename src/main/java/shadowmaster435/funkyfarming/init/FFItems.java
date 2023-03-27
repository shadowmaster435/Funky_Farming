package shadowmaster435.funkyfarming.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.item.GuideBook;
import shadowmaster435.funkyfarming.item.Linker;
import shadowmaster435.funkyfarming.item.VoraceBulb;

public class FFItems {
    public static final VoraceBulb VORACE_BULB = new VoraceBulb(new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).meat().saturationModifier(4).build()).maxDamage(20));
    public static final GuideBook GUIDE_BOOK = new GuideBook(new FabricItemSettings().maxCount(1));
    public static final Linker LINKER = new Linker(new FabricItemSettings().maxCount(1));

    public static final Item FLORALITE = new Item(new FabricItemSettings());

    public static final Item SIMPLE_ROTOR = new Item(new FabricItemSettings());

    public static final Item IRON_GEAR = new Item(new FabricItemSettings());
    public static final Item VERDURIUM_INGOT = new Item(new FabricItemSettings());
    public static final Item COPPER_GEAR = new Item(new FabricItemSettings());

    public static final Item CONFLAGRITE = new Item(new FabricItemSettings());

    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "vorace_bulb"), VORACE_BULB);
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "guide_book"), GUIDE_BOOK);
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "linker"), LINKER);
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "floralite"), FLORALITE);
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "simple_rotor"), SIMPLE_ROTOR);
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "iron_gear"), IRON_GEAR);
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "verdurium_ingot"), VERDURIUM_INGOT);
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "copper_gear"), COPPER_GEAR);
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "conflagrite"), CONFLAGRITE);
    }

}
