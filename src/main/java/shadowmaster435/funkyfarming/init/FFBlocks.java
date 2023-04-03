package shadowmaster435.funkyfarming.init;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import shadowmaster435.funkyfarming.block.*;
import shadowmaster435.funkyfarming.block.entity.*;
import shadowmaster435.funkyfarming.block.entity.renderer.*;
import shadowmaster435.funkyfarming.rendering.CTQuadProvider;
import shadowmaster435.funkyfarming.util.FFBlockSoundGroups;

public class FFBlocks {

    public static final ExoticSoil EXOTIC_SOIL = new ExoticSoil(FabricBlockSettings.of(Material.SOIL).sounds(FFBlockSoundGroups.EXOTIC_SOIL).strength(0.5f));
    public static final ExoticFarmland EXOTIC_FARMLAND = new ExoticFarmland(FabricBlockSettings.of(Material.SOIL).sounds(FFBlockSoundGroups.EXOTIC_SOIL).strength(0.5f));
    public static final VoracePlant VORACE_PLANT = new VoracePlant(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.CROP).strength(0f).nonOpaque().noCollision());
    public static final Pylon PYLON = new Pylon(FabricBlockSettings.of(Material.METAL).hardness(2f).resistance(20f).requiresTool().nonOpaque().noCollision());
    public static final ItemPylon ITEM_PYLON = new ItemPylon(FabricBlockSettings.of(Material.METAL).hardness(2f).resistance(20f).requiresTool().nonOpaque().noCollision());
    public static final MechaLilly MECHALILLY = new MechaLilly(FabricBlockSettings.of(Material.METAL).strength(0f).nonOpaque().noCollision());
    public static final GlycerootPlant GLYCEROOT_CROP = new GlycerootPlant(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.NYLIUM).strength(.75f).nonOpaque().noCollision());
    public static final GlycerootBlock GLYCEROOT_BLOCK = new GlycerootBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.NYLIUM).strength(0f).nonOpaque());
    public static final GlycerootFuse GLYCEROOT_FUSE = new GlycerootFuse(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.WOOL).strength(0f).nonOpaque().noCollision());
    public static final Generator GENERATOR = new Generator(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).hardness(1.75f).resistance(20f).requiresTool().nonOpaque());
    public static final Gateway GATEWAY = new Gateway(FabricBlockSettings.of(Material.STONE).sounds(FFBlockSoundGroups.PEARLSTONE).hardness(2f).resistance(40f).nonOpaque().requiresTool());
    public static final EnergyVoid ENERGYVOID = new EnergyVoid(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(0f).nonOpaque());
    public static final Block PEARLSTONE = new Block(FabricBlockSettings.of(Material.STONE).sounds(FFBlockSoundGroups.PEARLSTONE).hardness(1.6f).resistance(9f).requiresTool());
    public static final Block FLORALITE_ORE = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).hardness(1.6f).resistance(9f).requiresTool());
    public static final Block PEARLSTONE_PILLAR = new Block(FabricBlockSettings.of(Material.STONE).sounds(FFBlockSoundGroups.PEARLSTONE).hardness(1.6f).resistance(9f).requiresTool());
    public static final Block SLAGATE = new Block(FabricBlockSettings.of(Material.STONE).sounds(FFBlockSoundGroups.SLAGATE).hardness(1f).resistance(9f).requiresTool());
    public static final Block SCRAP_SOIL = new Block(FabricBlockSettings.of(Material.SOIL).sounds(FFBlockSoundGroups.EXOTIC_SOIL).hardness(1.6f).resistance(9f).requiresTool());
    public static final GatewayAmp GATEWAY_AMP = new GatewayAmp(FabricBlockSettings.of(Material.STONE).sounds(FFBlockSoundGroups.PEARLSTONE).hardness(1.6f).resistance(40f).requiresTool());
    public static final Stalagpipe STALAGPIPE = new Stalagpipe(FabricBlockSettings.of(Material.METAL).sounds(FFBlockSoundGroups.METAL).hardness(2f).resistance(20f).requiresTool().nonOpaque());
    public static final WorkTable WORKTABLE = new WorkTable(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).hardness(0.5f).resistance(10f).requiresTool().nonOpaque());
    public static final CopperTube COPPER_TUBE = new CopperTube(FabricBlockSettings.of(Material.METAL).sounds(FFBlockSoundGroups.METAL).hardness(0.5f).resistance(10f).requiresTool());
    public static final SizedCopperTube MEDUIM_COPPER_TUBE = new SizedCopperTube(FabricBlockSettings.of(Material.METAL).sounds(FFBlockSoundGroups.METAL).hardness(0.5f).resistance(10f).requiresTool());
    public static final SizedCopperTube SMALL_COPPER_TUBE = new SizedCopperTube(FabricBlockSettings.of(Material.METAL).sounds(FFBlockSoundGroups.METAL).hardness(0.5f).resistance(10f).requiresTool());

    public static final Infuser INFUSER = new Infuser(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).hardness(1f).resistance(20f).requiresTool().nonOpaque());

    public static final InfusionPedastal INFUSION_PEDASTAL = new InfusionPedastal(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).hardness(1f).resistance(20f).requiresTool().nonOpaque());

    public static final Shearuellia SHEARUELLIA = new Shearuellia(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.STONE).hardness(0f).resistance(20f).requiresTool());

    public static final Erodaisy ERODAISY = new Erodaisy(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.STONE).hardness(0f).resistance(20f).requiresTool());
    public static final TesterBlock TESTER = new TesterBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).hardness(0f).resistance(20f).requiresTool());



    public static void registerBlocks() {
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "vorace_plant"), VORACE_PLANT);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "exotic_soil"), EXOTIC_SOIL);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "exotic_farmland"), EXOTIC_FARMLAND);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "glyceroot_crop"), GLYCEROOT_CROP);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "glyceroot_block"), GLYCEROOT_BLOCK );
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "glyceroot_fuse"), GLYCEROOT_FUSE );
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "pylon"), PYLON );
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "mechalilly"), MECHALILLY);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "generator"), GENERATOR);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "gateway"), GATEWAY);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "energyvoid"), ENERGYVOID);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "pearlstone"), PEARLSTONE);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "pearlstone_pillar"), PEARLSTONE_PILLAR);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "gateway_amp"), GATEWAY_AMP);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "slagate"), SLAGATE);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "scrap_soil"), SCRAP_SOIL);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "item_pylon"), ITEM_PYLON);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "stalagpipe"), STALAGPIPE);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "worktable"), WORKTABLE);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "copper_tube"), COPPER_TUBE);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "small_copper_tube"), SMALL_COPPER_TUBE);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "medium_copper_tube"), MEDUIM_COPPER_TUBE);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "floralite_ore"), FLORALITE_ORE);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "infuser"), INFUSER);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "infusion_pedestal"), INFUSION_PEDASTAL);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "shearuellia"), SHEARUELLIA);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "erodaisy"), ERODAISY);
        Registry.register(Registries.BLOCK, new Identifier("funkyfarming", "tester"), TESTER);

    }

    public static void registerItemBlocks() {
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "vorace_plant"), new BlockItem(VORACE_PLANT, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "exotic_soil"), new BlockItem(EXOTIC_SOIL, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "exotic_farmland"), new BlockItem(EXOTIC_FARMLAND, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "glyceroot_block"), new BlockItem(GLYCEROOT_BLOCK, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "glyceroot_fuse"), new BlockItem(GLYCEROOT_FUSE, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "pylon"), new BlockItem(PYLON, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "mechalilly"), new BlockItem(MECHALILLY, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "generator"), new BlockItem(GENERATOR, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "gateway"), new BlockItem(GATEWAY, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "energyvoid"), new BlockItem(ENERGYVOID, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "pearlstone"), new BlockItem(PEARLSTONE, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "pearlstone_pillar"), new BlockItem(PEARLSTONE_PILLAR, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "slagate"), new BlockItem(SLAGATE, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "scrap_soil"), new BlockItem(SCRAP_SOIL, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "item_pylon"), new BlockItem(ITEM_PYLON, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "stalagpipe"), new BlockItem(STALAGPIPE, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "small_copper_tube"), new BlockItem(SMALL_COPPER_TUBE, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "medium_copper_tube"), new BlockItem(MEDUIM_COPPER_TUBE, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "copper_tube"), new BlockItem(COPPER_TUBE, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "floralite_ore"), new BlockItem(FLORALITE_ORE, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "worktable"), new BlockItem(WORKTABLE, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "infuser"), new BlockItem(INFUSER, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "infusion_pedestal"), new BlockItem(INFUSION_PEDASTAL, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "shearuellia"), new BlockItem(SHEARUELLIA, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "erodaisy"), new BlockItem(ERODAISY, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("funkyfarming", "tester"), new BlockItem(TESTER, new FabricItemSettings()));

    }

    public static BlockEntityType<VoracePlantEntity> VORACE_PLANT_ENTITY;
    public static BlockEntityType<PylonEntity> PYLON_ENTITY;
    public static BlockEntityType<MechaLillyEntity> MECHALILLY_ENTITY;
    public static BlockEntityType<GatewayEntity> GATEWAY_ENTITY;

    public static BlockEntityType<GeneratorEntity> GENERATOR_ENTITY;

    public static BlockEntityType<EnergyVoidEntity> ENERGY_VOID_ENTITY;


    public static BlockEntityType<ItemPylonEntity> ITEM_PYLON_ENTITY;

    public static BlockEntityType<WorkTableEntity> WORKTABLE_ENTITY;

    public static BlockEntityType<InfusionPedestalEntity> PEDESTAL_ENTITY;

    public static BlockEntityType<InfuserEntity> INFUSER_ENTITY;

    public static BlockEntityType<ShearuelliaEntity> SHEARUELLIA_ENTITY;

    public static BlockEntityType<TesterBlockEntity> TESTER_BLOCK_ENTITY;




    public static void registerBlockEntities() {
        VORACE_PLANT_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "funkyfarming:vorace_plant_entity", FabricBlockEntityTypeBuilder.create(VoracePlantEntity::new, VORACE_PLANT).build(null));
        PYLON_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "funkyfarming:pylon_entity", FabricBlockEntityTypeBuilder.create(PylonEntity::new, PYLON).build(null));
        MECHALILLY_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "funkyfarming:mechalilly_entity", FabricBlockEntityTypeBuilder.create(MechaLillyEntity::new, MECHALILLY).build(null));
        GATEWAY_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "funkyfarming:gateway_entity", FabricBlockEntityTypeBuilder.create(GatewayEntity::new, GATEWAY).build(null));
        GENERATOR_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "funkyfarming:generator_entity", FabricBlockEntityTypeBuilder.create(GeneratorEntity::new, GENERATOR).build(null));
        ENERGY_VOID_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "funkyfarming:energyvoid_entity", FabricBlockEntityTypeBuilder.create(EnergyVoidEntity::new, ENERGYVOID).build(null));
        ITEM_PYLON_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "funkyfarming:item_pylon_entity", FabricBlockEntityTypeBuilder.create(ItemPylonEntity::new, ITEM_PYLON).build(null));
        WORKTABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "funkyfarming:worktable_entity", FabricBlockEntityTypeBuilder.create(WorkTableEntity::new, WORKTABLE).build(null));
        INFUSER_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "funkyfarming:infuser_entity", FabricBlockEntityTypeBuilder.create(InfuserEntity::new, INFUSER).build(null));
        PEDESTAL_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "funkyfarming:infusion_pedestal_entity", FabricBlockEntityTypeBuilder.create(InfusionPedestalEntity::new, INFUSION_PEDASTAL).build(null));
        SHEARUELLIA_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "funkyfarming:shearuellia_entity", FabricBlockEntityTypeBuilder.create(ShearuelliaEntity::new, SHEARUELLIA).build(null));
        TESTER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "funkyfarming:tester", FabricBlockEntityTypeBuilder.create(TesterBlockEntity::new, TESTER).build(null));

    }

    public static void registerBlockLayerTypes() {
        BlockRenderLayerMap.INSTANCE.putBlock(VORACE_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GENERATOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WORKTABLE, RenderLayer.getCutout());


        BlockRenderLayerMap.INSTANCE.putBlock(GLYCEROOT_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GLYCEROOT_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GATEWAY_AMP, RenderLayer.getCutout());

    }

    public static void registerCT() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new CTQuadProvider());

    }
    public static void registerRenderers() {
        BlockEntityRendererFactories.register(PYLON_ENTITY, PylonRenderer::new);
        BlockEntityRendererFactories.register(ITEM_PYLON_ENTITY, ItemPylonRenderer::new);
        BlockEntityRendererFactories.register(TESTER_BLOCK_ENTITY, TesterRenderer::new);

        BlockEntityRendererFactories.register(MECHALILLY_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new MechaLillyRenderer());
        BlockEntityRendererFactories.register(GATEWAY_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new GatewayRenderer());
        BlockEntityRendererFactories.register(WORKTABLE_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new WorkTableRenderer());
        BlockEntityRendererFactories.register(PEDESTAL_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new InfusionPedestalRenderer());
        BlockEntityRendererFactories.register(INFUSER_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new InfuserRenderer());


    }

}
