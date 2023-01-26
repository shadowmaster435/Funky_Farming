package shadowmaster435.funkyfarming.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import shadowmaster435.funkyfarming.init.FFBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PylonEntity extends BlockEntity {
    public ArrayList<Integer> xlist = new ArrayList<>();
    public ArrayList<Integer> ylist = new ArrayList<>();
    public ArrayList<Integer> zlist = new ArrayList<>();



    public boolean writePosNBT = false;


    public PylonEntity(BlockPos pos, BlockState state) {
        super(FFBlocks.PYLON_ENTITY, pos, state);

    }




    public BlockPos getNBTPos(int index) {
        if (this.xlist.size() > 0) {
            return new BlockPos(this.xlist.get(index), this.ylist.get(index), this.zlist.get(index));
        } else {
            return BlockPos.ORIGIN;
        }
    }
    public ArrayList<BlockPos> getNBTPosList() {
        ArrayList<BlockPos> result = new ArrayList<>();
        for (int i = 0; i < this.xlist.size(); ++i) {
            result.add(new BlockPos(this.xlist.get(i), this.ylist.get(i), this.zlist.get(i)));
        }
        return result;
    }

    public List<Integer> getXlist() {
        return xlist;
    }

    public List<Integer> getYlist() {
        return ylist;
    }

    public List<Integer> getZlist() {
        return zlist;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
            nbt.putIntArray("xlist", this.getXlist());
            nbt.putIntArray("ylist", this.getYlist());
            nbt.putIntArray("zlist", this.getZlist());
        super.writeNbt(nbt);
    }




    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);


        ArrayList<Integer> tempx = new ArrayList<>(Arrays.stream(nbt.getIntArray("xlist")).boxed().toList());
        ArrayList<Integer> tempy = new ArrayList<>(Arrays.stream(nbt.getIntArray("ylist")).boxed().toList());
        ArrayList<Integer> tempz = new ArrayList<>(Arrays.stream(nbt.getIntArray("zlist")).boxed().toList());
        this.xlist = tempx;
        this.ylist = tempy;
        this.zlist = tempz;

    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public boolean visited = false;





    private int loaddelay = 0;

    public static void tick(World world, BlockPos pos, BlockState state, PylonEntity be) {
        if (be.loaddelay < 10) {
            ++be.loaddelay;
        } else {
            for (int i = 0; i < be.xlist.size(); ++i) {
                BlockPos bpos = new BlockPos(be.xlist.get(i), be.ylist.get(i), be.zlist.get(i));
                if (world.getBlockState(bpos).getBlock() != FFBlocks.PYLON) {
                    be.xlist.remove(be.xlist.get(i));
                    be.ylist.remove(be.ylist.get(i));
                    be.zlist.remove(be.zlist.get(i));

                }
            }
        }
        if (be.visited) {
            be.visited = false;
        }
    }
}
