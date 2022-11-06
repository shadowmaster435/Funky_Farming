package shadowmaster435.funkyfarming.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shadowmaster435.funkyfarming.item.GuideBook;
import shadowmaster435.funkyfarming.item.VoraceBulb;

public class FFItems {
    public static final VoraceBulb VORACE_BULB = new VoraceBulb(new FabricItemSettings().group(ItemGroup.MISC).food(new FoodComponent.Builder().hunger(6).meat().saturationModifier(4).build()).maxDamage(20));
    public static final GuideBook GUIDE_BOOK = new GuideBook(new FabricItemSettings().maxCount(1).group(ItemGroup.MISC));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier("funkyfarming", "vorace_bulb"), VORACE_BULB);
        Registry.register(Registry.ITEM, new Identifier("funkyfarming", "guide_book"), GUIDE_BOOK);

    }

}
