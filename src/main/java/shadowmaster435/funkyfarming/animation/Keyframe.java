package shadowmaster435.funkyfarming.animation;

import org.checkerframework.checker.units.qual.A;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Keyframe {


    private final Vector3f startpos;
    private final Vector3f endpos;
    private final Easing easing;

    private List<Keyframe> frames = new ArrayList<>();

    private int length;

    public Keyframe(Vector3f startpos, Vector3f endpos, Easing easing, int length) {
        this.easing = easing;
        this.startpos = startpos;
        this.endpos = endpos;
        this.length = length;

    }

    public static Keyframe builder(Vector3f startpos, Vector3f endpos, Easing easing, int length) {
        Keyframe keyframe = new Keyframe(startpos, endpos, easing, length);
        keyframe.frames.add(keyframe);
        return keyframe;
    }

    public Keyframe next(Vector3f startpos, Vector3f endpos, Easing easing, int length) {
        frames.add(new Keyframe(startpos, endpos, easing, length));
        return this;
    }

    public Animation build() {
        return new Animation(this.frames, this.getLength());
    }

    public int getLength() {
        return this.length;
    }

    public Easing getEasing() {
        return this.easing;
    }
    public Vector3f getStartpos() {
        return this.startpos;
    }
    public Vector3f getEndpos() {
        return this.endpos;
    }
}
