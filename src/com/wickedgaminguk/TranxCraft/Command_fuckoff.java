
package com.wickedgaminguk.TranxCraft;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class Command_fuckoff extends TranxCraft implements CommandExecutor {

    public Command_fuckoff(TranxCraft plugin) {
    this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if (args.length != 1) {
            return false;
        }
        
        if(sender instanceof Player) {
            sender.sendMessage(ChatColor.RED + "Fuckoff is a console command");
            return true;
        }
        
        Player player = getPlayer(args[0]);
        
        Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " Casting oblivion over " + player.getName());
        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " will be completely obliviated!");

        final String IP = player.getAddress().getAddress().getHostAddress().trim();
        
        List<String> Executives = plugin.getConfig().getStringList("Executives");
        List<String> leadAdmins = plugin.getConfig().getStringList("Lead_Admins");
        List<String> Admins = plugin.getConfig().getStringList("Admins");
        List<String> Moderators = plugin.getConfig().getStringList("Moderators");
        
        // remove from TranxCraft Moderator (and any above ranks)
        Executives.remove(player.getName());
        leadAdmins.remove(player.getName());
        Admins.remove(player.getName());
        Moderators.remove(player.getName());
        //Save these changes.
        plugin.getConfig().set("Executives",Executives);
        plugin.getConfig().set("leadAdmins",leadAdmins);
        plugin.getConfig().set("Admins",Admins);
        plugin.getConfig().set("Moderators",Moderators);
        plugin.saveConfig();
        
        // remove from whitelist
        player.setWhitelisted(false);

        // deop
        player.setOp(false);

        // ban IP
        TranxCraft.banIP(IP, null, null);

        // ban name
        TranxCraft.banUsername(player.getName(), null, null);

        // set gamemode to survival
        player.setGameMode(GameMode.SURVIVAL);

        // clear inventory
        player.closeInventory();
        player.getInventory().clear();

        // ignite player
        player.setFireTicks(10000);

        // generate explosion
        player.getWorld().createExplosion(player.getLocation(), 4F);
                
        // strike lightning
        player.getWorld().strikeLightning(player.getLocation());

        // kill (if not done already)
        player.setHealth(0.0);
        
        // message
        Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " Banning " + player.getName() + ", IP: " + IP);

        // generate explosion
        player.getWorld().createExplosion(player.getLocation(), 4F);

        // kick player
        player.kickPlayer(ChatColor.RED + "FUCKOFF, and get your shit together!");
        return true;
      }
}