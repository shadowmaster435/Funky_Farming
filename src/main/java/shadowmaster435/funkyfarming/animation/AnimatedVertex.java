package shadowmaster435.funkyfarming.animation;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexConsumer;
import org.joml.Vector3f;
import org.joml.Vector4i;

public class AnimatedVertex {


    private final Animation animation;
    private final VertexConsumer consumer;

    private Vector4i argb;

    public AnimatedVertex(VertexConsumer consumer, Animation animation, Vector4i argb) {
        this.animation = animation;
        this.consumer = consumer;
        this.argb = argb;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public Vector4i getArgb() {
        return this.argb;
    }

    public VertexConsumer getConsumer() {
        return this.consumer;
    }

}
