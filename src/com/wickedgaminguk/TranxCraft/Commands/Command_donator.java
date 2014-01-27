
package com.wickedgaminguk.TranxCraft.Commands;

import com.wickedgaminguk.TranxCraft.*;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.ANY, usage = "Usage: /<command> <add|remove|activate>")
public class Command_donator extends BukkitCommand {

    @Override
    public boolean run(CommandSender sender, Command command, String commandLabel, String[] args) {
            
            if(args.length >4) {
                sender.sendMessage(TCP_Util.Invalid_Usage);
                return false;
            }
        
            Player player;
            player = getPlayer(args[0]);
                
            String Player = player.getName();
            
            if(args[0].equalsIgnoreCase("add")) {
            
                if(!(sender instanceof Player) || !(sender.getName().equalsIgnoreCase("WickedGamingUK"))) {
                    sender.sendMessage(TCP_Util.noPerms);
                    return true;
                }                
                
                TCP_ModeratorList.getDonators().add(Player);
                plugin.getConfig().set("Donators",TCP_ModeratorList.getDonators());
                plugin.saveConfig();
                Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + " has bought a donator rank, congratulations!");
                return true;
            }
            
            if(args[0].equalsIgnoreCase("remove")) {
            
                if(!(sender instanceof Player) || !(sender.getName().equalsIgnoreCase("WickedGamingUK"))) {
                    sender.sendMessage(TCP_Util.noPerms);
                    return true;
                }
                
                TCP_ModeratorList.getDonators().remove(Player);
                plugin.getConfig().set("Donators",TCP_ModeratorList.getDonators());
                plugin.saveConfig();
                Bukkit.broadcastMessage(ChatColor.RED + player.getName() + "'s donator rank has expired, or (s)he's been abusing, how unfortunate!");
                return true;
            }
        return true;
        
    }
    
}
