package shadowmaster435.funkyfarming.block.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import shadowmaster435.funkyfarming.block.entity.GatewayEntity;
import shadowmaster435.funkyfarming.block.entity.model.GatewayModel;
import shadowmaster435.funkyfarming.util.RenderUtil;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GatewayRenderer extends GeoBlockRenderer<GatewayEntity> {

    public GatewayRenderer() {
        super(new GatewayModel());
    }


    public float frametime = 1;
    public float lineframetime = 0;

    public static final Identifier beamtex = new Identifier("funkyfarming:textures/misc/dim_beam.png");
    public static final Identifier rifttex = new Identifier("funkyfarming:textures/misc/rift_crack.png");
    public static final Identifier ringtex = new Identifier("funkyfarming:textures/misc/ring.png");

    public static final Identifier gatewaybg = new Identifier("funkyfarming:textures/misc/gateway_bg.png");
    public static final Identifier gatewaystars = new Identifier("funkyfarming:textures/misc/gateway_bg.png");

    public int ringtimer = 0;
    public int rifttimer = 0;
    public int beamtimer = 0;



    @Override
    public void postRender(MatrixStack matrices, GatewayEntity entity, BakedGeoModel model, VertexConsumerProvider vertexConsumers, VertexConsumer buffer, boolean isReRender, float tickDelta, int light, int overlay, float red, float green, float blue, float alpha) {
        int extratimeoffset = 45;

        if (entity != null) {
            VertexConsumer vertexConsumerquad = vertexConsumers.getBuffer(RenderLayer.getSolid());

            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            matrices.push();

            if (entity.animtimer > 15 + extratimeoffset && entity.animtimer < 165) {

                RenderUtil.cubeshaft(new Vector3f(0.4375f , 0.75f, 0.4375f), new Vector3f(0.5625f , 6f, 0.5625f), vertexConsumerquad, matrix4f, beamtex, entity.animtimer / 16f, 6, new Vector4f(1f, 1f, 1f, 1f));

            }
            matrix4f.translate(0.5f, 0, 0.5f);

            if (entity.animtimer > 15 + extratimeoffset && entity.animtimer <= 25 + extratimeoffset) {
                RenderUtil.screenshakeamount = 0.005f - ((entity.animtimer -  15) * 0.0005f);
                System.out.println( RenderUtil.screenshakeamount);
            } else {
                RenderUtil.screenshakeamount = 0;
            }
            if (entity.animtimer >= 15 + extratimeoffset && entity.animtimer < 45 + extratimeoffset) {
                if (ringtimer < 30) {
                    ++ringtimer;
                }
                if (ringtimer != 0) {
                    RenderUtil.screenshakeamount = 30f / ringtimer;
                    float scalar =  ((1 * (ringtimer / 30f)));
                    RenderUtil.texturedquad(new Vector2f(-3f * scalar, -3f * scalar), new Vector2f(3f * scalar, 3f * scalar), 6, Direction.Axis.Y, new Vector2f(1, 1), ringtex, vertexConsumerquad, matrix4f, new Vector2f(0, 0), new Vector4f(1f, 1f, 1f,  Math.abs(-1  + scalar)));
                }
            } else {
                ringtimer = 0;
            }
            if (entity.animtimer >= 30 + extratimeoffset && entity.animtimer <= 80 + extratimeoffset) {
                RenderUtil.texturedquadopaqu(new Vector2f(-1, -1f), new Vector2f(1f, 1f), 6f, Direction.Axis.Y, new Vector2f(1, 1), rifttex, vertexConsumerquad, matrix4f, new Vector2f(0, 0), new Vector4f(1f, 1f, 1f, 1f));

            }

            if (entity.animtimer <= 120 + extratimeoffset && entity.animtimer >= 100 + extratimeoffset) {

                float scalar = ((entity.animtimer - 100) - extratimeoffset)/ 20f;
                matrix4f.scale(scalar * 3, 1, scalar * 3);
                matrix4f.translate(-0.5f * scalar, 0, -0.5f * scalar);

            } else if (entity.animtimer > 180 + extratimeoffset && entity.animtimer < 200 + extratimeoffset){
                float scalar = Math.abs(1 - ((entity.animtimer - 180) - extratimeoffset) / 20f);
                matrix4f.scale(scalar * 3, 1, scalar * 3);
                matrix4f.translate(-0.5f * scalar, 0, -0.5f * scalar);
            } else {
                if (entity.animtimer >100 + extratimeoffset && entity.animtimer < 200 + extratimeoffset) {
                    matrix4f.scale(3, 1, 3);
                    matrix4f.translate(-0.5f, 0, -0.5f);
                } else {
                    rifttimer = 0;
                    matrix4f.scale(0, 1, 0);
                }
            }

         //   System.out.println(ringtimer);
            matrix4f.rotateAround(RotationAxis.POSITIVE_Y.rotationDegrees(frametime * 2), 0.5f, 4, 0.5f);

            if (entity.animtimer >= 100 + extratimeoffset && entity.animtimer < 200 + extratimeoffset) {
                this.quad(new Vector2f(0.125f, 0.125f), new Vector2f(0.875f, 0.875f), vertexConsumers, 0, matrix4f, matrices, entity);
                this.quad(new Vector2f(0.125f, 0f), new Vector2f(0.875f, 1), vertexConsumers, 0, matrix4f, matrices, entity);
                this.quad(new Vector2f(0, 0.125f), new Vector2f(1, 0.875f), vertexConsumers, 0, matrix4f, matrices, entity);

                this.quad(new Vector2f(0.25f, 1), new Vector2f(0.75f, 1.5f), vertexConsumers, 0, matrix4f, matrices, entity);
                this.quad(new Vector2f(0.25f, -0.5f), new Vector2f(0.75f, 0), vertexConsumers, 0, matrix4f, matrices, entity);
                this.quad(new Vector2f(1, 0.25f), new Vector2f(1.5f, 0.75f), vertexConsumers, 0, matrix4f, matrices, entity);
                this.quad(new Vector2f(-0.5f, 0.25f), new Vector2f(0, 0.75f), vertexConsumers, 0, matrix4f, matrices, entity);

                this.quadneg(new Vector2f(0.25f, 1), new Vector2f(0.75f, 1.5f), vertexConsumers, 45, matrix4f, matrices, entity);
                this.quadneg(new Vector2f(0.25f, 1), new Vector2f(0.75f, 1.5f), vertexConsumers, 135, matrix4f, matrices, entity);
                this.quad(new Vector2f(0.25f, 1), new Vector2f(0.75f, 1.5f), vertexConsumers, 45, matrix4f, matrices, entity);
                this.quad(new Vector2f(0.25f, 1), new Vector2f(0.75f, 1.5f), vertexConsumers, 135, matrix4f, matrices, entity);

                this.quad(new Vector2f(0.25f, 1), new Vector2f(0.75f, 1.5f), vertexConsumers, 45, matrix4f, matrices, entity);
                this.quad(new Vector2f(0.25f, 1), new Vector2f(0.75f, 1.5f), vertexConsumers, 135, matrix4f, matrices, entity);
                this.quadneg(new Vector2f(0.25f, 1), new Vector2f(0.75f, 1.5f), vertexConsumers, 45, matrix4f, matrices, entity);
                this.quadneg(new Vector2f(0.25f, 1), new Vector2f(0.75f, 1.5f), vertexConsumers, 135, matrix4f, matrices, entity);

            }
            matrices.pop();


        }
        super.postRender(matrices, animatable, model, vertexConsumers, buffer, isReRender, tickDelta, light, overlay, red, green, blue, alpha);

    }

    public void quad(Vector2f min, Vector2f max, VertexConsumerProvider consumers, int rot, Matrix4f mat4f, MatrixStack mat, GatewayEntity entity) {

        VertexConsumer consumer = consumers.getBuffer(RenderLayer.getEndPortal());


        mat4f.rotateAround(RotationAxis.POSITIVE_Y.rotationDegrees(rot), 0.5f, 0, 0.5f );

        consumer.vertex(mat4f, min.x, 6, min.y).next();
        consumer.vertex(mat4f, max.x, 6, min.y).next();
        consumer.vertex(mat4f, max.x, 6, max.y).next();
        consumer.vertex(mat4f, min.x, 6, max.y).next();

    }

    public void quadneg(Vector2f min, Vector2f max, VertexConsumerProvider consumers, int rot, Matrix4f mat4f, MatrixStack mat, GatewayEntity entity) {
        VertexConsumer consumer = consumers.getBuffer(RenderLayer.getEndPortal());
        RenderSystem.setShaderTexture(0, gatewaybg);
        RenderSystem.setShaderTexture(1, gatewaybg);

        mat4f.rotateAround(RotationAxis.NEGATIVE_Y.rotationDegrees(rot), 0.5f, 0, 0.5f );

        consumer.vertex(mat4f, min.x, 6, min.y).next();
        consumer.vertex(mat4f, max.x, 6, min.y).next();
        consumer.vertex(mat4f, max.x, 6, max.y).next();
        consumer.vertex(mat4f, min.x, 6, max.y).next();

    }
}
