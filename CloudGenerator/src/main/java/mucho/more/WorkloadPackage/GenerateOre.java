package mucho.more.WorkloadPackage;

import mucho.more.CloudGenerator;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

import java.util.List;

public class GenerateOre implements Workload{


    private final List<Location> blocks;
    private final Material oreMaterial = Material.COMMAND_BLOCK;
    public GenerateOre(List<Location> blocks){
        this.blocks = blocks;

    }
    @Override
    public void compute() {
        if(blocks.isEmpty())return;
        World w = blocks.get(0).getWorld();
        for(Location l:blocks){
            if(l.getChunk().isLoaded()){
                setBlockInNativeWorld(w,l.getBlockX(), l.getBlockY(), l.getBlockZ(), oreMaterial,false);
            }
        }


    }

    public void setBlockInNativeWorld(World world, int x, int y, int z, Material mat, boolean applyPhysics) {

        net.minecraft.world.level.World nmsWorld = ((CraftWorld) world).getHandle();
        net.minecraft.core.BlockPosition bp = new BlockPosition(x, y, z);
        nmsWorld.a(bp, CraftMagicNumbers.getBlock(mat).n(), applyPhysics ? 3 : 2);
    }
}
