package shadowmaster435.funkyfarming.animation;

import org.joml.Vector3f;

import java.util.List;

public class Animation {

    public List<Keyframe> keyframes;

    private int length;

    private int currentFrame = 0;

    public Animation(List<Keyframe> keyframes, int length) {
        this.keyframes = keyframes;
        this.length = length;
    }

    public List<Keyframe> getKeyframes() {
        return this.keyframes;
    }

    public Vector3f getCurrentPos(float delta, int currentFrame) {
        float easedposx = this.keyframes.get(currentFrame).getEasing().ease(delta, this.keyframes.get(currentFrame).getStartpos().x, this.keyframes.get(currentFrame).getEndpos().x, this.length);
        float easedposy = this.keyframes.get(currentFrame).getEasing().ease(delta, this.keyframes.get(currentFrame).getStartpos().y, this.keyframes.get(currentFrame).getEndpos().y, this.length);
        float easedposz = this.keyframes.get(currentFrame).getEasing().ease(delta, this.keyframes.get(currentFrame).getStartpos().z, this.keyframes.get(currentFrame).getEndpos().z, this.length);
        return new Vector3f(easedposx, easedposy, easedposz);
    }


    public int getLength() {
        return this.length;
    }

    public Keyframe getKeyframe(int index) {
        return this.keyframes.get(index);
    }
}
