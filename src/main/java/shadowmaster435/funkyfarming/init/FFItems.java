package shadowmaster435.funkyfarming.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemGroup;
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

    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "vorace_bulb"), VORACE_BULB);
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "guide_book"), GUIDE_BOOK);
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "linker"), LINKER);

    }

}
