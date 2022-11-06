package shadowmaster435.funkyfarming.util;

import com.google.common.primitives.Ints;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vector4f;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class MathUtil {


    public static Vec3d PlaneIntersect(ArrayList<Vector4f> objposlist, ArrayList<Vec3d> planeposlist) {
        Vec3d result;


        for (Vector4f obj: objposlist) {
            for (Vec3d plane: planeposlist) {
                obj.getX()
            }
        }
    }

    public static double getbinrange(double val, double min, double max) {
        double range = Math.abs(min) + Math.abs(max);
        return Math.abs(val) / range;
    }



}
