package com.wickedgaminguk.TranxCraft;

import com.wickedgaminguk.mcstats.Metrics;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TranxCraft extends JavaPlugin {
    
    public TranxCraft plugin;
    public TranxListener listener;
    public String pluginName;
    public String pluginVersion;
    public String pluginAuthor;
    File configFile;
    FileConfiguration config;
    PluginManager pm;

  @Override
  public void onEnable() { 
        plugin = this;
        configFile = new File(plugin.getDataFolder(), "config.yml");
        this.pm = getServer().getPluginManager();
         
        PluginDescriptionFile pdf = plugin.getDescription();
        pluginName = pdf.getName();
        pluginVersion = pdf.getVersion();
        pluginAuthor = pdf.getAuthors().get(0);
        
        if(!new File(plugin.getDataFolder(), "config.yml").isFile()) {
            this.saveDefaultConfig();
            TCP_Util.logger.log(Level.INFO, "{0} version {1} configuration file saved.", new Object[]{pluginName, pluginVersion});
        }
        
        YamlConfiguration.loadConfiguration(configFile);
        
        TCP_Util.logger.log(Level.INFO, "{0} version {1} by {2} is enabled", new Object[]{pluginName, pluginVersion, pluginAuthor});
        
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        }
        catch (IOException e) {
            TCP_Util.logger.log(Level.SEVERE, "{0} Plugin Metrics have failed to submit the statistics to McStats! >:(", pluginName);
        }
        
        listener = new TranxListener(plugin);
        pm.registerEvents(listener, plugin);
        
        //Register Command Executors.
        plugin.getCommand("mong").setExecutor(new Command_mong(plugin));
        plugin.getCommand("tranxcraft").setExecutor(new Command_tranxcraft(plugin));
        plugin.getCommand("donator").setExecutor(new Command_donator(plugin));
        plugin.getCommand("admininfo").setExecutor(new Command_admininfo(plugin));
        plugin.getCommand("c").setExecutor(new Command_c(plugin));
        plugin.getCommand("a").setExecutor(new Command_a(plugin));
        plugin.getCommand("s").setExecutor(new Command_s(plugin));
        plugin.getCommand("gtfo").setExecutor(new Command_gtfo(plugin));
        plugin.getCommand("fuckoff").setExecutor(new Command_fuckoff(plugin));  
        
        init();
  }
  
  @Override
  public void onDisable() {
    TCP_Util.logger.log(Level.INFO, "{0} version {1} configuration file saved.", new Object[]{pluginName, pluginVersion});
    TCP_Util.logger.log(Level.INFO, "{0} version {1} by {2} is disabled", new Object[]{pluginName, pluginVersion, pluginAuthor});
  }
  
  public void onReload() {
    plugin.saveConfig();
  }
   
   public void init() {
       Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv unload Spawn_nether");
       Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv unload Spawn_the_end");
       TCP_Util.logger.log(Level.INFO, "[TranxCraft] Hopefully the Nether and End have unloaded!");
   }   
}