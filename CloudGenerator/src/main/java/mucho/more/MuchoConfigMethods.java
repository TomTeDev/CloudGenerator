package mucho.more;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;

public abstract class MuchoConfigMethods {
    private final CloudGenerator plugin;
    private FileConfiguration fileConfig = null;
    private File file = null;
    private final String fileName;
    public MuchoConfigMethods(String fileName){
        this.plugin = CloudGenerator.plugin;
        this.fileName = fileName;
        saveDeafaultConfig();
    }
    public void reloadConfig() {
        if (this.file == null) {
            this.file = new File(this.plugin.getDataFolder(), fileName);
        }
        this.fileConfig = YamlConfiguration.loadConfiguration(this.file);
        InputStream defaultStream = this.plugin.getResource(fileName);
        if (defaultStream != null) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.fileConfig.setDefaults(yamlConfiguration);
        }
    }
    public FileConfiguration getConfig() {
        if (this.fileConfig == null) reloadConfig();
        return this.fileConfig;
    }
    public void saveConfig() {
        if (this.fileConfig == null || this.file == null) return;
        try {
            this.getConfig().save(file);
        } catch (IOException exception) {
            this.plugin.getLogger().log(Level.SEVERE, ChatColor.DARK_RED + "Failed on trying to save "+fileName, exception);
        }
    }
    private void saveDeafaultConfig() {
        if (this.file == null) {
            this.file = new File(this.plugin.getDataFolder(), fileName);
        }
        if (!this.file.exists()) {
            this.plugin.saveResource(fileName, false);
        }
    }

    public boolean setInt(int value,String path){
        getConfig().set(path,value);
        saveConfig();
        return true;
    }
    public int getInt(String path,int reserveValue){
        return (getConfig().contains(path)&&(getConfig().get(path)!=null))?getConfig().getInt(path):reserveValue;
    }
    public boolean setString(String value,String path){
        getConfig().set(path,value);
        saveConfig();
        return true;
    }
    public String getString(String path,String reserveValue){
        return (getConfig().contains(path)&&(getConfig().get(path)!=null))?getConfig().getString(path):reserveValue;
    }
    public boolean setBoolean(boolean value,String path){
        getConfig().set(path,value);
        saveConfig();
        return true;
    }
    public boolean getBoolean(String path,boolean reserveValue){
        return (getConfig().contains(path)&&(getConfig().get(path)!=null))?getConfig().getBoolean(path):reserveValue;
    }
    public boolean setStringList(List<String> value, String path){
        getConfig().set(path,value);
        saveConfig();
        return true;
    }
    public List<String> getStringList(String path,List<String> reserveValue){
        return (getConfig().contains(path)&&(getConfig().get(path)!=null))?getConfig().getStringList(path):reserveValue;
    }
    public boolean setIntegerList(List<Integer> value, String path){
        getConfig().set(path,value);
        saveConfig();
        return true;
    }
    public List<Integer> getIntegerList(String path,List<Integer> reserveValue){
        return (getConfig().contains(path)&&(getConfig().get(path)!=null))?getConfig().getIntegerList(path):reserveValue;
    }
    public boolean setFloatList(List<Float> value, String path){
        getConfig().set(path,value);
        saveConfig();
        return true;
    }
    public List<Float> getFloatList(String path,List<Float> reserveValue){
        return (getConfig().contains(path)&&(getConfig().get(path)!=null))?getConfig().getFloatList(path):reserveValue;
    }
    public boolean setFloat(float value, String path){
        getConfig().set(path,value);
        saveConfig();
        return true;
    }
    public double getDouble(String path, double reservedValue){
        return (getConfig().contains(path)&&(getConfig().get(path)!=null))?getConfig().getDouble(path):reservedValue;
    }

}

