package shadowmaster435.funkyfarming.animation;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.checkerframework.checker.units.qual.A;
import org.joml.*;

import java.util.List;

public class AnimatedLine {



    private final List<AnimatedVertex> vertices;

    public AnimatedLine(List<AnimatedVertex> vertices) {
        this.vertices = vertices;
    }


    public int currentFrame = 0;



    public void render(VertexConsumer consumer, MatrixStack matrixStack, double delta) {
        Matrix4f mat4 = matrixStack.peek().getPositionMatrix();
        for (AnimatedVertex vertex : this.vertices) {
            Easing easing = vertex.getAnimation().getKeyframes().get(currentFrame).getEasing();
            Vector3f startpos = vertex.getAnimation().keyframes.get(currentFrame).getStartpos();
            Vector3f endpos = vertex.getAnimation().keyframes.get(currentFrame).getEndpos();

           /* if (currentFrame + 1 > 3) {
                endpos = vertex.getAnimation().keyframes.get((0)).getStartpos();
            } else  {
                endpos = vertex.getAnimation().keyframes.get((currentFrame + 1)).getStartpos();
            }*/
            if (delta % 20 == 0) {
                ++currentFrame;
            }
            if (currentFrame > 3) {
                currentFrame = 0;
            }
            int length = vertex.getAnimation().getLength();

            float easedposx = easing.ease((float) delta, startpos.x, endpos.x, vertex.getAnimation().getLength());
            float easedposy = easing.ease((float) delta, startpos.y, endpos.y, vertex.getAnimation().getLength());
            float easedposz = easing.ease((float) delta, startpos.z, endpos.z, vertex.getAnimation().getLength());
            Vector4i argb = vertex.getArgb();
            Vector3f normalpos = startpos.cross(endpos);


            consumer.vertex(mat4, easedposx, easedposy, easedposz).color(argb.x, argb.y, argb.z, argb.w).normal(normalpos.x, normalpos.y, normalpos.z).next();

        }
    }
}
