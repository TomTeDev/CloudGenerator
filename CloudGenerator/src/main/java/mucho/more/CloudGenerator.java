package mucho.more;

import mucho.more.WorkloadPackage.ChunkFile;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

public final class CloudGenerator extends JavaPlugin {
    public static CloudGenerator plugin;
    private ChunkFile chunkFile;
    @Override
    public void onEnable() {
        plugin = this;
        chunkFile = new ChunkFile();
        chunkFile.load();
        // Plugin startup logic
        new ChunkEvent().startGeneratingOres();
        registerEvents(this);
    }
    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
    @Override
    public void onDisable() {
        chunkFile.save();
        // Plugin shutdown logic
        plugin = null;
    }

    public ChunkFile getChunkFile(){
        return this.chunkFile;
    }
}
