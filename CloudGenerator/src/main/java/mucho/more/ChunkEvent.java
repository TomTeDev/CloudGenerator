package mucho.more;

import mucho.more.WorkloadPackage.ChunkFile;
import mucho.more.WorkloadPackage.GenerateOre;
import mucho.more.WorkloadPackage.Workload;
import mucho.more.WorkloadPackage.WorkloadThread;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChunkEvent{
    public void startGeneratingOres(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CloudGenerator.getPlugin(CloudGenerator.class), new Runnable() {
            @Override
            public void run() {
                for(Player p:Bukkit.getOnlinePlayers()){
                    Random rand = new Random();
                    int x = rand.nextInt(5);
                    if(x!=1)return;
                    Location l = p.getLocation();
                    if(wasGeneratedBefore(l.getChunk()))return;
                    populate(l.getChunk().getX(),l.getChunk().getZ(),l.getWorld());
                }
            }
        },20,120);
    }
    private boolean wasGeneratedBefore(Chunk c){
        int x = c.getX();
        int z = c.getZ();
        String v = x+"O"+z;
       ChunkFile f =  CloudGenerator.getPlugin(CloudGenerator.class).getChunkFile();
       if(f.chunkWasGenerated(v))return true;
       f.addChunk(v);
       return false;
    }
    public void populate(int x,int z,World w) {
        Location startingLoc = new Location(w,x*16,getStartingHeight(),z*16);

        Workload workload = new GenerateOre(getRandomOreLocs(startingLoc));
        WorkloadThread workLoadThread = new WorkloadThread();
        workLoadThread.addLoad(workload);
        workLoadThread.run();
    }


    private final int oresize = 8;

    private List<Location> getRandomOreLocs (Location startingLoc){
        List<Location> centers = new ArrayList<>();
        centers.add(startingLoc);
        List<Location> list = new ArrayList<>();
        list.addAll(genBlocksAround(startingLoc));

        for(int i = 0;i<oresize;i++){
            Location randLoc = getRandLoc(list);
            if(centers.contains(randLoc))continue;
            centers.add(randLoc);
            if(randLoc!=startingLoc){
                list.addAll(genBlocksAround(randLoc));
            }
        }
        return list;
    }
    private Location getRandLoc(List<Location> list){

        int size = list.size();
        int rand = new Random().nextInt(size);
        return list.get(rand);
    }
    private List<Location> genBlocksAround(Location loc){
        List<Location> list = new ArrayList<>();
        list.add(loc.clone().add(1,0,0));
        list.add(loc.clone().add(0,0,1));
        list.add(loc.clone().add(-1,0,0));
        list.add(loc.clone().add(0,0,-1));
        list.add(loc.clone().add(0,1,0));
        list.add(loc.clone().add(0,-1,0));
        return list;
    }
    private Location crLoc(World w, int x, int y, int z){
        return new Location(w,x,y,z);
    }

    private int getStartingHeight(){
        Random random = new Random();
        return 200 + random.nextInt(20);
    }
}
