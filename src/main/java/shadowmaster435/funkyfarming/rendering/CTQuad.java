package shadowmaster435.funkyfarming.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import shadowmaster435.funkyfarming.block.entity.TesterBlockEntity;
import shadowmaster435.funkyfarming.init.FFBlocks;
import shadowmaster435.funkyfarming.util.ExtendedDirection;
import shadowmaster435.funkyfarming.util.MiscUtil;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class CTQuad implements UnbakedModel, BakedModel, FabricBakedModel {

    private static final SpriteIdentifier[] SPRITE_IDS = new SpriteIdentifier[]{
            new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("funkyfarming:block/ct_test")),
            new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("funkyfarming:block/ctdefault"))
    };
    private Sprite[] SPRITES = new Sprite[2];
    public static final Identifier tex = new Identifier("funkyfarming:textures/block/ct_test.png");
    public static final Identifier def = new Identifier("funkyfarming:textures/block/ctdefault.png");

    public Direction side;
    public String corner;

    public World world;

    public BlockPos pos;

    private Mesh mesh;


    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptyList(); // This model does not depend on other models.
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }




    public CTQuad(Direction side, String corner) {
        this.side = side;
        this.corner = corner;
        this.world = world;
        this.pos = pos;
    }


    public CTQuad(Direction side, String corner, World world, BlockPos pos) {
        this.side = side;
        this.corner = corner;
        this.world = world;
        this.pos = pos;
    }

    public String cornerLogic(Block block) {
        String result = "none";
        int success = 0;
        boolean isH = false;
        boolean isV = false;
        ArrayList<ExtendedDirection> dirs = ExtendedDirection.ctFilter(this.corner, this.side);
        for (ExtendedDirection direction : dirs) {
            boolean bool = world.getBlockState(direction.offset(pos, direction)).getBlock() == block;
            boolean diag = world.getBlockState(direction.offset(pos, dirs.get(2))).getBlock() == block;

            boolean horizontal = world.getBlockState(direction.offset(pos, dirs.get(1))).getBlock() == block;
            boolean vertical = world.getBlockState(direction.offset(pos, dirs.get(0))).getBlock() == block;

            if (horizontal && !vertical && bool) {
                result = "h";
            }
            if (vertical && !horizontal && bool) {
                result = "v";
            }
            if (vertical && horizontal && bool) {
                result = "hv";
            }
            if (vertical && horizontal && diag && bool) {
                result = "d";
            }

        }
        return result;
    }

    public String cornerLogic(BlockRenderView world, BlockPos pos, Block block) {
        String result = "none";
        int success = 0;
        boolean isH = false;
        boolean isV = false;
        ArrayList<ExtendedDirection> dirs = ExtendedDirection.ctFilter(this.corner, this.side);
        for (ExtendedDirection direction : dirs) {
            boolean bool = world.getBlockState(direction.offset(pos, direction)).getBlock() == block;
            boolean diag = world.getBlockState(direction.offset(pos, dirs.get(2))).getBlock() == block;

            boolean horizontal = world.getBlockState(direction.offset(pos, dirs.get(1))).getBlock() == block;
            boolean vertical = world.getBlockState(direction.offset(pos, dirs.get(0))).getBlock() == block;

            if (horizontal && !vertical && bool) {
                result = "h";
            }
            if (vertical && !horizontal && bool) {
                result = "v";
            }
            if (vertical && horizontal && bool) {
                result = "hv";
            }
            if (vertical && horizontal && diag && bool) {
                result = "d";
            }

        }
        return result;
    }







    public Direction dirFromCorner() {
        if (Objects.equals(this.corner, "tl") || Objects.equals(this.corner, "tr")) {
            return Direction.UP;
        } else {
            return Direction.DOWN;
        }
    }


    public Vector4f defaultUV() {
        Vector4f result = new Vector4f(0,0,0,0);
        switch (this.corner) {
            case "bl" -> result = new Vector4f(0,0,0.5f,0.5f);
            case "br" -> result = new Vector4f(0.5f,0,1,0.5f);
            case "tl" -> result = new Vector4f(0,0.5f,0.5f,1);
            case "tr" -> result = new Vector4f(0.5f,0.5f,1,1);
        }
        return result.mul(0.5f);
    }

    public Vector4f addedUV(BlockRenderView world, BlockPos pos) {
        Vector4f result;
        String clogic = cornerLogic(world, pos, FFBlocks.TESTER);
         switch (clogic) {
            case "v" -> result = new Vector4f(0, 0.5f, 0, 0.5f);

            case "h" -> result = new Vector4f(0.5f, 0, 0.5f, 0);

            case "hv" -> result = new Vector4f(0.5f, 0.5f, 0.5f, 0.5f);

            case "d" -> result = new Vector4f(0, 0, 0, 0);

            default -> result = new Vector4f(0, 0, 0, 0);
        }

        return result;
    }
    public Vector4f addedUV() {
        Vector4f result;
        String clogic = cornerLogic(world, pos, FFBlocks.TESTER);
        switch (clogic) {
            case "v" -> result = new Vector4f(0, 0.5f, 0, 0.5f);

            case "h" -> result = new Vector4f(0.5f, 0, 0.5f, 0);

            case "hv" -> result = new Vector4f(0.5f, 0.5f, 0.5f, 0.5f);


            default -> result = new Vector4f(0, 0, 0, 0);
        }

        return result;
    }
    public Vector4f uv(BlockRenderView world, BlockPos pos) {
        return defaultUV().add(addedUV(world, pos));
    }
    public Vector4f uv() {
        return defaultUV().add(addedUV());
    }
    public Vector2f defaultOffsetMin() {
        Vector2f result;
        switch (this.corner) {
            case "br" -> result = new Vector2f(0.5f,0);
            case "tl" -> result = new Vector2f(0,0.5f);
            case "tr" -> result = new Vector2f(0.5f,0.5f);
            default -> result = new Vector2f(0,0);
        }
        return result;
    }

    public Vector2f defaultOffsetMax() {
        Vector2f result;
        switch (this.corner) {
            case "br" -> result = new Vector2f(1,0.5f);
            case "tl" -> result = new Vector2f(0.5f,1);
            case "tr" -> result = new Vector2f(1,1);
            default -> result = new Vector2f(0.5f,0.5f);
        }
        return result;
    }
    public Vector3f offsetMin() {
        Vector3f result;
        float h = defaultOffsetMin().x;
        float v = defaultOffsetMin().y;
        if (MiscUtil.dirPositive(this.side)) {
            result = new Vector3f(h, v, 1);
        } else {
            result = new Vector3f(h, v, 0);
        }

        return result;
    }
    public Vector3f offsetMax() {
        Vector3f result;
        float h = defaultOffsetMax().x;
        float v = defaultOffsetMax().y;

        if (MiscUtil.dirPositive(this.side)) {
            result = new Vector3f(h, v, 1);
        } else {
            result = new Vector3f(h, v, 0);
        }

        return result;
    }

    public Vector3f offsetMinE() {
        Vector3f result = null;
        float h = defaultOffsetMin().x;
        float v = defaultOffsetMin().y;
        switch (this.side) {
            case UP -> result = new Vector3f(h, 1, v);
            case DOWN -> result = new Vector3f(h, 0, v);
            case NORTH -> result = new Vector3f(h, v, 0);
            case SOUTH -> result = new Vector3f(h, v, 1);
            case EAST -> result = new Vector3f(1, v, h);
            case WEST -> result = new Vector3f(0, v, h);

        }

        return result;
    }
    public Vector3f offsetMaxE() {
        Vector3f result = null;
        float h = defaultOffsetMax().x;
        float v = defaultOffsetMax().y;

        switch (this.side) {
            case UP -> result = new Vector3f(h, 1, v);
            case DOWN -> result = new Vector3f(h, 0, v);
            case NORTH -> result = new Vector3f(h, v, 0);
            case SOUTH -> result = new Vector3f(h, v, 1);
            case EAST -> result = new Vector3f(1, v, h);
            case WEST -> result = new Vector3f(0, v, h);

        }

        return result;
    }
    public void render(Matrix4f matrix4f, TesterBlockEntity entity) {
        finishRender(matrix4f, offsetMinE(), offsetMaxE(), uv());

    }


    public void finishRender(Matrix4f mat4, Vector3f min, Vector3f max, Vector4f uvs) {
        boolean Unconnected = Objects.equals(cornerLogic(FFBlocks.TESTER), "none");
        Vector4f uv = (!Unconnected) ? uvs : defaultUV().mul(2);
        Direction.Axis axis = side.getAxis();
        RenderSystem.enableTexture();
        RenderSystem.enableDepthTest();
        RenderSystem.disableCull();
        Tessellator tessellator =  Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        if (!Unconnected) {
            RenderSystem.setShaderTexture(0, tex);
        } else {
            RenderSystem.setShaderTexture(0, def);
        }
        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        if (axis == Direction.Axis.X) {

            builder.vertex(mat4, min.x, min.y, min.z).texture(uv.x, uv.y).color(255, 255, 255, 255).next();
            builder.vertex(mat4, min.x, min.y, max.z).texture(uv.z, uv.y).color(255, 255, 255, 255).next();
            builder.vertex(mat4, min.x, max.y, max.z).texture(uv.z, uv.w).color(255, 255, 255, 255).next();
            builder.vertex(mat4, min.x, max.y, min.z).texture(uv.x, uv.w).color(255, 255, 255, 255).next();
        }
        if (axis == Direction.Axis.Y) {
            if (Objects.equals(this.corner, "tr") && Unconnected) {
                builder.vertex(mat4, min.x, min.y, min.z).texture(uv.x, uv.y).color(255, 255, 255, 255).next();
                builder.vertex(mat4, min.x, min.y, max.z).texture(uv.x, uv.w).color(255, 255, 255, 255).next();
                builder.vertex(mat4, max.x, min.y, max.z).texture(uv.z, uv.w).color(255, 255, 255, 255).next();
                builder.vertex(mat4, max.x, min.y, min.z).texture(uv.z, uv.y).color(255, 255, 255, 255).next();

            } else {
                builder.vertex(mat4, min.x, min.y, min.z).texture(uv.x, uv.y).color(255, 255, 255, 255).next();
                builder.vertex(mat4, min.x, min.y, max.z).texture(uv.x, uv.w).color(255, 255, 255, 255).next();
                builder.vertex(mat4, max.x, min.y, max.z).texture(uv.z, uv.w).color(255, 255, 255, 255).next();
                builder.vertex(mat4, max.x, min.y, min.z).texture(uv.z, uv.y).color(255, 255, 255, 255).next();
            }

        }
        if (axis == Direction.Axis.Z) {

            builder.vertex(mat4, min.x, min.y, min.z).texture(uv.x, uv.y).color(255, 255, 255, 255).next();
            builder.vertex(mat4, max.x, min.y, min.z).texture(uv.z, uv.y).color(255, 255, 255, 255).next();
            builder.vertex(mat4, max.x, max.y, min.z).texture(uv.z, uv.w).color(255, 255, 255, 255).next();
            builder.vertex(mat4, min.x, max.y, min.z).texture(uv.x, uv.w).color(255, 255, 255, 255).next();
        }
        tessellator.draw();
        RenderSystem.disableTexture();


    }
    @Override
    public boolean isVanillaAdapter() {
        return false;
    }


    @Override
    public void emitBlockQuads(BlockRenderView world, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        QuadEmitter emitter = context.getEmitter();

        for (Direction direction : Direction.values()) {
            try {
                emitter.sprite(0, 0, uv(world, pos).x, uv(world, pos).y);
                emitter.sprite(1, 0, uv(world, pos).x, uv(world, pos).w);
                emitter.sprite(2, 0, uv(world, pos).z, uv(world, pos).w);
                emitter.sprite(3, 0, uv(world, pos).x, uv(world, pos).w);
                emitter.square(direction, offsetMin().x, offsetMin().y, offsetMax().x, offsetMax().y, offsetMin().z);

                emitter.spriteBake(0, SPRITES[0], MutableQuadView.BAKE_NORMALIZED);
                // Enable texture usage
                emitter.spriteColor(0, -1, -1, -1, -1);
                emitter.emit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        }

    }
    @Nullable
    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        for(int i = 0; i < 2; ++i) {
            SPRITES[i] = textureGetter.apply(SPRITE_IDS[i]);
        }
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        assert renderer != null;
        MeshBuilder builder = renderer.meshBuilder();
        QuadEmitter emitter = builder.getEmitter();
        for (Direction direction : Direction.values()) {

            emitter.square(direction, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f);

            emitter.spriteColor(0, -1, -1, -1, -1);
            emitter.emit();
        }
        mesh = builder.build();

        return this;
    }
    @Override
    public void emitItemQuads(ItemStack itemStack, Supplier<Random> supplier, RenderContext renderContext) {
        renderContext.meshConsumer().accept(mesh);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean hasDepth() {
        return true;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return SPRITES[1];
    }


    @Override
    public ModelTransformation getTransformation() {
        return ModelHelper.MODEL_TRANSFORM_BLOCK;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }
}
