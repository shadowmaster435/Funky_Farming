package shadowmaster435.funkyfarming.init;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shadowmaster435.funkyfarming.block.ExoticFarmland;
import shadowmaster435.funkyfarming.block.*;
import shadowmaster435.funkyfarming.block.entity.VoracePlantEntity;
import shadowmaster435.funkyfarming.util.FFBlockSoundGroups;

public class FFBlocks {

    public static final ExoticSoil EXOTIC_SOIL = new ExoticSoil(FabricBlockSettings.of(Material.SOIL).sounds(FFBlockSoundGroups.EXOTIC_SOIL).strength(0.5f));
    public static final ExoticFarmland EXOTIC_FARMLAND = new ExoticFarmland(FabricBlockSettings.of(Material.SOIL).sounds(FFBlockSoundGroups.EXOTIC_SOIL).strength(0.5f));

    public static final VoracePlant VORACE_PLANT = new VoracePlant(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).strength(0f).nonOpaque().noCollision());
    public static final GlycerootPlant GLYCEROOT_CROP = new GlycerootPlant(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.NYLIUM).strength(.75f).nonOpaque().noCollision());
    public static final GlycerootBlock GLYCEROOT_BLOCK = new GlycerootBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.NYLIUM).strength(0f).nonOpaque());
    public static final GlycerootFuse GLYCEROOT_FUSE = new GlycerootFuse(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.WOOL).strength(0f).nonOpaque().noCollision());

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier("funkyfarming", "vorace_plant"), VORACE_PLANT);
        Registry.register(Registry.BLOCK, new Identifier("funkyfarming", "exotic_soil"), EXOTIC_SOIL);
        Registry.register(Registry.BLOCK, new Identifier("funkyfarming", "exotic_farmland"), EXOTIC_FARMLAND);
        Registry.register(Registry.BLOCK, new Identifier("funkyfarming", "glyceroot_crop"), GLYCEROOT_CROP);
        Registry.register(Registry.BLOCK, new Identifier("funkyfarming", "glyceroot_block"), GLYCEROOT_BLOCK );
        Registry.register(Registry.BLOCK, new Identifier("funkyfarming", "glyceroot_fuse"), GLYCEROOT_FUSE );

    }

    public static void registerItemBlocks() {
        Registry.register(Registry.ITEM, new Identifier("funkyfarming", "vorace_plant"), new BlockItem(VORACE_PLANT, new FabricItemSettings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("funkyfarming", "exotic_soil"), new BlockItem(EXOTIC_SOIL, new FabricItemSettings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("funkyfarming", "exotic_farmland"), new BlockItem(EXOTIC_FARMLAND, new FabricItemSettings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("funkyfarming", "glyceroot_block"), new BlockItem(GLYCEROOT_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("funkyfarming", "glyceroot_fuse"), new BlockItem(GLYCEROOT_FUSE, new FabricItemSettings().group(ItemGroup.MISC)));

    }

    public static BlockEntityType<VoracePlantEntity> VORACE_PLANT_ENTITY;

    public static void registerBlockEntities() {
        VORACE_PLANT_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "funkyfarming:vorace_plant_entity", FabricBlockEntityTypeBuilder.create(VoracePlantEntity::new, VORACE_PLANT).build(null));
    }

    public static void registerBlockLayerTypes() {
        BlockRenderLayerMap.INSTANCE.putBlock(VORACE_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GLYCEROOT_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GLYCEROOT_CROP, RenderLayer.getCutout());

    }

}
