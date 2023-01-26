package shadowmaster435.funkyfarming.gravity;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GravitySphere {


    public Vec3d CameraRotation = new Vec3d(0,0,0);

    private double radius;

    private final BlockPos centerPos;

    public static List<GravitySphere> spheres = new ArrayList<>();

    public GravitySphere(double radius, BlockPos centerPos) {
        this.centerPos = centerPos;
        this.radius = radius;
    }

    public double getRadius() {
        return this.radius;
    }

    public BlockPos getCenterPos() {
        return this.centerPos;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Direction getDirection(Vec3d pos) {
        Direction direction = null;
        double isXnegative = (pos.getX() < this.centerPos.getX()) ? -1 : 1;
        double isYnegative = (pos.getY() < this.centerPos.getY()) ? -1 : 1;
        double isZnegative = (pos.getZ() < this.centerPos.getZ()) ? -1 : 1;
        Vec3d distancePos = this.getDistancePos(pos);
        double maxRadiusX = (this.centerPos.getX() + this.radius) * isXnegative;
        double maxRadiusY = (this.centerPos.getY() + this.radius) * isYnegative;
        double maxRadiusZ = (this.centerPos.getZ() + this.radius) * isZnegative;
        double maxRadiusAbsX = (this.centerPos.getX() + this.radius);
        double maxRadiusAbsY = (this.centerPos.getY() + this.radius);
        double maxRadiusAbsZ = (this.centerPos.getZ() + this.radius);
        double posInsideX = MathHelper.lerp(distancePos.getX() / this.radius, centerPos.getX(), maxRadiusX);
        double posInsideY = MathHelper.lerp(distancePos.getY() / this.radius, centerPos.getY(), maxRadiusY);
        double posInsideZ = MathHelper.lerp(distancePos.getZ() / this.radius, centerPos.getZ(), maxRadiusZ);

        if (this.inside(pos)) {
            if (posInsideX < maxRadiusAbsX & posInsideZ < maxRadiusAbsZ) {
                if (posInsideY > this.centerPos.getY()) {
                    direction = Direction.DOWN;
                    this.CameraRotation = new Vec3d(0,0,0);
                }
                if (posInsideY < this.centerPos.getY()) {
                    direction = Direction.UP;
                    this.CameraRotation = new Vec3d(180,0,0);
                }
            }
            if (posInsideX < maxRadiusAbsX & posInsideY < maxRadiusAbsY) {
                if (posInsideZ > this.centerPos.getZ()) {
                    direction = Direction.SOUTH;
                    this.CameraRotation = new Vec3d(90,0,0);
                }
                if (posInsideZ < this.centerPos.getZ()) {
                    direction = Direction.NORTH;
                    this.CameraRotation = new Vec3d(90,180,0);
                }
            }
            if (posInsideZ < maxRadiusAbsZ & posInsideY < maxRadiusAbsY) {
                if (posInsideX > this.centerPos.getX()) {
                    direction = Direction.EAST;
                    this.CameraRotation = new Vec3d(90,90,0);
                }
                if (posInsideX < this.centerPos.getX()) {
                    direction = Direction.WEST;
                    this.CameraRotation = new Vec3d(90,270,0);
                }
            }
        } else {
            direction = Direction.DOWN;
        }
        return direction;
    }
    public double getDistance(double cx, double cy, double cz, double x, double y, double z)
    {
        double x1 = (int)Math.pow((x - cx), 2);
        double y1 = (int)Math.pow((y - cy), 2);
        double z1 = (int)Math.pow((z - cz), 2);

        return (x1 + y1 + z1);
    }
    public Vec3d getDistancePos(Vec3d pos)
    {
        double x1 = Math.abs(pos.getX() - this.centerPos.getX());
        double y1 = Math.abs(pos.getY() - this.centerPos.getY());
        double z1 = Math.abs(pos.getZ() - this.centerPos.getZ());
        return new Vec3d(x1, y1, z1);
    }
    public boolean inside(Vec3d pos) {
        return this.getDistance(this.centerPos.getX(), this.centerPos.getY(), this.centerPos.getZ(), pos.getX(), pos.getY(), pos.getZ()) < (this.radius * this.radius);
    }
    public int fallticks = 1;
    public Vec3d getVelocity(Vec3d pos, Vec3d evel, boolean onground) {
        if (!onground) {
            fallticks++;
        } else {
            fallticks = 1;
        }
        Vec3d vel = new Vec3d(0, 0, 0);
        switch (this.getDirection(pos)) {
            case UP -> vel = new Vec3d(evel.x,  evel.y, evel.z);
            case DOWN -> vel = new Vec3d(evel.x, -evel.y, evel.z);
            case NORTH -> vel = new Vec3d(evel.x, evel.z,-evel.y);
            case SOUTH -> vel = new Vec3d(evel.x, evel.z, evel.y);
            case EAST -> vel = new Vec3d(-evel.y, evel.x, evel.z);
            case WEST -> vel = new Vec3d(evel.y, evel.x, evel.z);
        }
        return vel;
    }

    public static Vec3d getVelocityStatic(Vec3d pos, Vec3d evel, GravitySphere sphere) {

        switch (sphere.getDirection(pos)) {
            case UP -> pos = new Vec3d(evel.x, -evel.y, evel.z);
            case DOWN -> pos = new Vec3d(evel.x, evel.y, evel.z);
            case NORTH -> pos = new Vec3d(evel.x, evel.z,evel.y);
            case SOUTH -> pos = new Vec3d(evel.x, evel.z, -evel.y);
            case EAST -> pos = new Vec3d(evel.y, evel.x, evel.z);
            case WEST -> pos = new Vec3d(-evel.y, evel.x, evel.z);
        }
        return pos;
    }
    public List<Entity> getEntitiesInRadius(World world) {
     //   System.out.println(world.getNonSpectatingEntities(Entity.class, new Box(this.centerPos.getX() - this.radius,this.centerPos.getY() - this.radius,this.centerPos.getZ() - this.radius,this.centerPos.getX() + this.radius,this.centerPos.getY() + this.radius,this.centerPos.getZ() + this.radius)));
        return world.getNonSpectatingEntities(Entity.class, new Box(this.centerPos.getX() - this.radius,this.centerPos.getY() - this.radius,this.centerPos.getZ() - this.radius,this.centerPos.getX() + this.radius,this.centerPos.getY() + this.radius,this.centerPos.getZ() + this.radius));
    }

    public Box getRotatedBoundingBox(Entity entity) {
        Box box = entity.getBoundingBox();
        if (this.getDirection(entity.getPos()) == Direction.NORTH || this.getDirection(entity.getPos()) == Direction.SOUTH)  {
            box = new Box(box.minX, box.minZ, box.minY, box.maxX, box.maxZ, box.maxY);
        }
        if (this.getDirection(entity.getPos()) == Direction.WEST || this.getDirection(entity.getPos()) == Direction.EAST)  {
            box = new Box(box.minY, box.minX, box.minZ, box.maxY, box.maxX, box.maxZ);

        }
        return box;
    }
}
