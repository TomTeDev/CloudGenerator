package mucho.more.WorkloadPackage;

import mucho.more.CloudGenerator;
import mucho.more.MuchoConfigMethods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChunkFile extends MuchoConfigMethods {


    public ChunkFile() {
        super("cloudGeneratorConfig.yml");
    }
    private List<String> chunks = new ArrayList<>();
    public void addChunk(String s){
        chunks.add(s);
    }
    public boolean chunkWasGenerated(String s){
        return chunks.contains(s);
    }
    public void saveFilesAsync(){
        Bukkit.getScheduler().scheduleAsyncDelayedTask(CloudGenerator.getPlugin(CloudGenerator.class), new Runnable() {
            @Override
            public void run() {
                save();
            }
        });
    }
    public void saveFilesSync(){
        save();
    }

    public void save(){
        if(!chunks.isEmpty()){
            setStringList(chunks,"data");
        }
        saveConfig();
    }
    private final String mainPath = "data";
    public void load(){
        try {
            reloadPlantShopConfig();
        }catch (Exception e){

        }
        if(getConfig().getConfigurationSection(mainPath)==null)return;
        if(getConfig().getConfigurationSection(mainPath).getKeys(false).isEmpty())return;
        if(!getConfig().contains("data"))return;
        List<String> s = getConfig().getStringList("data");

        saveFromTimeToTime();
    }
    private void saveFromTimeToTime(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CloudGenerator.getPlugin(CloudGenerator.class), new Runnable() {
            @Override
            public void run() {
                System.out.println(ChatColor.GREEN+"Plant location data saved!");
                saveFilesAsync();
            }
        },6000,6000);
    }




    private File plantShopFile = null;
    private File getHousesFile(){
        String fileName = "plantShop.yml";
        if(plantShopFile !=null)return plantShopFile;
        this.plantShopFile = new File(CloudGenerator.getPlugin(CloudGenerator.class).getDataFolder(), fileName);
        if(!plantShopFile.exists()){
            this.plantShopFile = new File(CloudGenerator.getPlugin(CloudGenerator.class).getDataFolder(), fileName);
            CloudGenerator.getPlugin(CloudGenerator.class).saveResource(fileName, false);

        }
        return plantShopFile;
    }
    private FileConfiguration plantShopYamlConf = null;
    public FileConfiguration getHousesFileConfiguration() {
        if (plantShopYamlConf != null) return plantShopYamlConf;
        File file = getHousesFile();
        this.plantShopYamlConf = YamlConfiguration.loadConfiguration(file);
        return plantShopYamlConf;
    }
    public void reloadPlantShopConfig(){
        this.plantShopFile = null;
        this.plantShopYamlConf = null;
        getHousesFileConfiguration();
    }
}
