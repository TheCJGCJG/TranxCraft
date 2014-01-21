
package com.wickedgaminguk.TranxCraft;

import com.wickedgaminguk.MySQL.*;
import com.wickedgaminguk.TranxCraft.Commands.Command_tranxcraft;
import com.wickedgaminguk.TranxCraft.TCP_Mail.RecipientType;
import com.wickedgaminguk.TranxCraft.UCP.TCP_UCP;
import org.mcstats.Metrics;
import java.io.*;
import java.sql.*;
import java.util.Properties;
import net.milkbowl.vault.permission.Permission;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.*;
import twitter4j.TwitterException;

public class TranxCraft extends BukkitPlugin {
    
    public static TranxCraft plugin = null;
    public String pluginName;
    public String pluginVersion;
    public String pluginAuthor;
    public String pluginBuildNumber;
    public String pluginBuildDate;
    public YamlConfig config;
    public BukkitCommandHandler handler;
    public TranxListener listener;
    public static Permission permission = null;
    
    PluginManager pm;
    MySQL mySQL;
    TCP_Mail mail;
    
    @Override
    public void onLoad() {
        plugin = this;
        config = new YamlConfig(plugin, "config.yml", true);
        handler = new BukkitCommandHandler(plugin);
    }

    @Override
    public void onEnable() {
        
        BukkitLib.init(plugin);
        
        this.pm = getServer().getPluginManager();
        
        config.load();   
        
        mySQL = new MySQL(plugin, config.getString("HOSTNAME"), config.getString("PORT"), config.getString("DATABASE"), config.getString("USER"), config.getString("PASSWORD"));
        mail = new TCP_Mail();
        
        try {
            final Properties build;
            
            try (InputStream in = plugin.getResource("build.properties")) {
                build = new Properties();
                build.load(in);
            }

            pluginBuildNumber = build.getProperty("program.buildnumber");
            pluginBuildDate = build.getProperty("program.builddate");
        }
        catch (Exception ex) {
            TCP_Log.severe("Could not load build information!");
            TCP_Log.severe(ex);
            pluginBuildNumber = "1";
            pluginBuildDate = TCP_Time.getLongDate();
        }
        
        TCP_Log.info(pluginName + " version " + pluginVersion + " by " + pluginAuthor + " is enabled");
        
        new TCP_Scheduler(plugin).runTaskTimer(plugin, config.getInt("interval") * 20L, config.getInt("interval") * 20L);
        new TCP_UCP(plugin).runTaskTimer(plugin, 6000L, 6000L);
        
        listener = new TranxListener(plugin);
        register(listener);       
        
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv unload Spawn_nether");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv unload Spawn_the_end");
        TCP_Log.info("[TranxCraft] Hopefully the Nether and End have unloaded!");
        
        mail.send(RecipientType.SYS, "TranxCraft Reports - Server Started", "Hey there, TranxCraft has been successfully started on " + TCP_Time.getDate());
        
        try {
            TCP_Twitter.tweet("TranxCraft has been successfully started on " + TCP_Time.getDate());
        }
        catch (TwitterException | IOException ex) {
            TCP_Log.warning("[TranxCraft] Twitter functionality is broken!\n" + ex);
        }
        
        setupPermissions();
        
        handler.setCommandLocation(Command_tranxcraft.class.getPackage());
        
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        }
        catch (IOException ex) {
            TCP_Log.severe("[" + pluginName + "] Plugin Metrics failed to submit to McStats.\n " + ex);
        }    
    }
  
    @Override
    public void onDisable() {
        TCP_Log.info(pluginName + " v" + pluginVersion + " configuration file saved.");
        TCP_Log.info(pluginName + " v" + pluginVersion + " by" + pluginAuthor + " is disabled.");
    }
  
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        return handler.handleCommand(sender, command, commandLabel, args);
    }
  
    public void updateDatabase(String SQLquery) throws SQLException {
        Connection c = mySQL.openConnection();
        Statement statement = c.createStatement();      
        statement.executeUpdate(SQLquery);
    }
  
    public void getValueFromDB(String SQLquery) throws SQLException {      
        Connection c = mySQL.openConnection();
        Statement statement = c.createStatement();
        ResultSet res = statement.executeQuery(SQLquery);
        res.next();
    }
    
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
    
    public String getBuildNumber() {
        return pluginBuildNumber;
    }

    public String getBuildDate() {
        return pluginBuildDate;
    }
    
    public String getPlayerGroup(Player player) {
        String perm = permission.getPrimaryGroup(player);
        return perm;
    }
}