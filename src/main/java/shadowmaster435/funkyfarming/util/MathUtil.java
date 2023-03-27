package shadowmaster435.funkyfarming.util;

import com.google.common.primitives.Ints;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class MathUtil {


/*    public static Vec3d PlaneIntersect(ArrayList<Vector4f> objposlist, ArrayList<Vec3d> planeposlist) {
        Vec3d result;


        for (Vector4f obj: objposlist) {
            for (Vec3d plane: planeposlist) {
                obj.getX()
            }
        }
    }*/


    public static int boolInt(boolean bool) {
        if (bool) {
            return 1;
        } else {
            return 0;
        }
    }
    public static Vector3f angleTowards(Vector3f first, Vector3f towards) {
        float dx = first.x - towards.x;
        float dy = first.y - towards.y;
        float dz = first.z - towards.z;

        return new Vector3f(0, (float) Math.atan2(dz, -dx), (float)Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) - 45);
    }

    public static Vector3f getNormal(Vector3f min, Vector3f max) {
        float k = (float)(max.x - min.x);
        float l = (float)(max.y - min.y);
        float m = (float)(max.z - min.z);
        float n = MathHelper.sqrt(k * k + l * l + m * m);
        k /= n;
        l /= n;
        m /= n;
        return new Vector3f(k, l, m);
    }


    public static Vector2f rotateAround(float distance, Vector2f xy) {
        double tan = (Math.tan(distance) * Math.tan(distance));
        double x = Math.sin(xy.x) * tan;
        double y = Math.cos(xy.y) * tan;
        return new Vector2f((float) x, (float) y);
    }


    public static Vector3f absPos(Vector3f pos) {
        return new Vector3f(Math.abs(pos.x), Math.abs(pos.z), Math.abs(pos.y));
    }

    public static Vec3d absVec3d(Vec3d pos) {
        return new Vec3d(Math.abs(pos.x), Math.abs(pos.z), Math.abs(pos.y));
    }

    public static Vector3f bpToVec(BlockPos pos) {
        return new Vector3f(pos.getX(), pos.getY(), pos.getZ());
    }
    public static Vec3d bpToVec3d(BlockPos pos) {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }

    public static BlockPos vec3dToBP(Vec3d pos) {
        return new BlockPos(pos.x, pos.y, pos.z);
    }
    public static Vector3f getDistanceVec(Vector3f first, Vector3f second) {
        return new Vector3f(Math.abs(first.x - second.x),Math.abs(first.y - second.y),Math.abs(first.z - second.z));
    }

    public static float getDistance(Vector3f first, Vector3f second) {
        return Vector3f.distance(first.x, first.y, first.z, second.x, second.y, second.z);
    }

    public static boolean pointInsideCircle(int circle_x, int circle_y,
                            int rad, int x, int y)
    {

        return (x - circle_x) * (x - circle_x) +
                (y - circle_y) * (y - circle_y) <= rad * rad;
    }


    public static double getbinrange(double val, double min, double max) {
        double range = Math.abs(min) + Math.abs(max);
        return Math.abs(val) / range;
    }



}
