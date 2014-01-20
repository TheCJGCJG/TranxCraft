
package com.wickedgaminguk.TranxCraft.UCP;

import com.wickedgaminguk.TranxCraft.TCP_Log;
import com.wickedgaminguk.TranxCraft.TCP_Time;
import com.wickedgaminguk.TranxCraft.TranxCraft;
import java.sql.SQLException;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TCP_UCP extends BukkitRunnable {
    
    private final TranxCraft plugin;
    TranxCraft TranxCraft = new TranxCraft();
    Permission permission;
    
    public TCP_UCP(TranxCraft instance) {
        this.plugin = instance;
    }

    @Override
    public void run() {
        TCP_Log.info("[TranxCraft] Starting UCP Sync Now.");
        
        if(Bukkit.getOnlinePlayers().length == 0) {
            TCP_Log.info("No players online, recreating table...");
            
            try {
                plugin.updateDatabase("DROP TABLE players");
                plugin.updateDatabase("CREATE TABLE players ( ID INT NOT NULL AUTO_INCREMENT, InGameName CHAR(30), OnlineTime CHAR(30), Rank CHAR(30), LastUpdated CHAR(30), PRIMARY KEY(ID) )");
                plugin.updateDatabase("INSERT INTO players (LastUpdated) VALUES ('" + TCP_Time.getUnixTimestamp() + "');");
                TCP_Log.info("Table Recreated");
            }
            catch(SQLException ex) {
                TCP_Log.severe("SQL Connection Failed.");
                TCP_Log.severe(ex);
            }
            
            TCP_Log.info("UCP Sync Finished.");
        }
        
        if(Bukkit.getOnlinePlayers().length != 0) {
            try {
                plugin.updateDatabase("DROP TABLE players");
                plugin.updateDatabase("CREATE TABLE players ( ID INT NOT NULL AUTO_INCREMENT, InGameName CHAR(30), OnlineTime CHAR(30), Rank CHAR(30), LastUpdated CHAR(30), PRIMARY KEY(ID) )");        
            }
            catch (SQLException ex) {
                TCP_Log.severe("SQL Connection Failed.");
                TCP_Log.severe(ex);
            }

            for (Player player : plugin.getServer().getOnlinePlayers()) {
                
                String playerPermission = null;
                
                try {
                    playerPermission = TranxCraft.getPlayerGroup(player);
                }
                catch(Exception ex) {
                    
                }
                
                if(playerPermission == null) {
                    playerPermission = "Argh - it didn't work. Contact someone with a Java Brain.";
                }
                
                String playerName = player.getName().toString();
                
                try {
                    plugin.updateDatabase("INSERT INTO players (InGameName, OnlineTime, Rank, LastUpdated) VALUES ('" + playerName + "', 98, '" + playerPermission + "', '" + TCP_Time.getUnixTimestamp() + "');");
                } 
                catch (SQLException ex) {
                    TCP_Log.severe("SQL Connection Failed.");
                    TCP_Log.severe(ex);
                }
                
            }
            TCP_Log.info("UCP Sync Finished.");
        }
    }
}
