package shadowmaster435.funkyfarming.animation;

import java.util.List;

public class Animation {

    public List<Keyframe> keyframes;

    private int length;

    public Animation(List<Keyframe> keyframes, int length) {
        this.keyframes = keyframes;
        this.length = length;
    }

    public List<Keyframe> getKeyframes() {
        return this.keyframes;
    }



    public int getLength() {
        return this.length;
    }

    public Keyframe getKeyframe(int index) {
        return this.keyframes.get(index);
    }
}
