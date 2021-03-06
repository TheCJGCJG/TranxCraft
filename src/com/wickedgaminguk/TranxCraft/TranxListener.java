package com.wickedgaminguk.TranxCraft;

import com.wickedgaminguk.TranxCraft.UCP.TCP_UCP;
import net.pravian.bukkitlib.util.ChatUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;


public class TranxListener extends TranxCraft implements Listener {
    
    String kickMessage = "I'm sorry, but you've been kicked to make room for a reserved player, to stop this happening, buy a donator rank!";  
    
    TranxListener(TranxCraft plugin) {
        TranxCraft.plugin = plugin;
    }
     
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityExplode(EntityExplodeEvent event) {
        if(!(event.getEntityType().equals(EntityType.PRIMED_TNT))) {
            TCP_Log.info("A " + WordUtils.capitalizeFully(event.getEntityType().toString().toLowerCase()) + " exploded at: " + event.getLocation().getBlockX() + ", " + event.getLocation().getBlockY() + ", " + event.getLocation().getBlockZ());
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerEvent(final PlayerJoinEvent event) {
        int TotalPlayers = plugin.getConfig().getInt("TotalPlayers");        
        TotalPlayers++;
        
        plugin.getConfig().set("TotalPlayers", Integer.valueOf(TotalPlayers));
        plugin.saveConfig();
        Bukkit.broadcastMessage(ChatColor.BLUE + "[Player Counter] " + TotalPlayers + " players have joined in total.");
        
        final Player player = event.getPlayer();
        if(event.getPlayer().getName().equals("WickedGamingUK")) {
            Bukkit.broadcastMessage(ChatColor.AQUA + event.getPlayer().getName() + " is the " + ChatColor.DARK_RED + "Owner!");
        }
        
        if(TCP_ModeratorList.getleadAdmins().contains(event.getPlayer().getName())) {
            Bukkit.broadcastMessage(ChatColor.AQUA + event.getPlayer().getName() + " is a lead Admin.");
        }
        
        if(TCP_ModeratorList.getExecutives().contains(event.getPlayer().getName())) {
            Bukkit.broadcastMessage(ChatColor.AQUA + event.getPlayer().getName() + " is an executive Admin.");
        }
        
        if(TCP_ModeratorList.getAdmins().contains(event.getPlayer().getName())) {
            Bukkit.broadcastMessage(ChatColor.AQUA + event.getPlayer().getName() + " is an " + ChatColor.GOLD + "Admin.");
        }
        
        if(TCP_ModeratorList.getModerators().contains(event.getPlayer().getName())) {
            Bukkit.broadcastMessage(ChatColor.AQUA + event.getPlayer().getName() + " is a " + ChatColor.DARK_PURPLE + "Moderator.");
        }
        
        if(TCP_ModeratorList.getDonators().contains(event.getPlayer().getName())) {
            Bukkit.broadcastMessage(ChatColor.AQUA + event.getPlayer().getName() + " is a " + ChatColor.LIGHT_PURPLE + "Donator! <3");
        }
        
        if(!(player.hasPermission("tranxcraft.member"))) {
            
            new BukkitRunnable() {
                @Override
                public void run() {
                    Player p = event.getPlayer();
                    p.sendMessage(ChatColor.GREEN + "Welcome to TranxCraft!\nBefore you can continue, please read the following rules and then accept them with /acceptrules to become Member.");
                    Bukkit.dispatchCommand(player, "rules");
                    p.sendMessage(ChatColor.GREEN + "Remember to read these rules and accept them with /acceptrules !");
                }
            }.runTaskLater(plugin, 40L);        
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLogin(PlayerLoginEvent event) {
        
        Player player = event.getPlayer();
        
        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
            if(TCP_ModeratorList.getAllAdmins().contains(event.getPlayer().getName()) || TCP_ModeratorList.getDonators().contains(event.getPlayer().getName())) {
                kickPlayer(player, event);
                event.allow();
                Bukkit.broadcastMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + " is a reserved member!");
            }
        }
        
        new TCP_UCP(plugin).runTaskAsynchronously(plugin);
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        new TCP_UCP(plugin).runTaskAsynchronously(plugin);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        
        switch (event.getBlockPlaced().getType()) {
            case FIREWORK: {
                if(!((TCP_ModeratorList.getAllAdmins().contains(event.getPlayer().getName())))) {
                    if(!(event.getPlayer().getName().equalsIgnoreCase("WickedGamingUK"))) {
                        player.sendMessage(ChatColor.RED + "The Use of Fireworks is not permitted on TranxCraft.");
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        event.setCancelled(true);
                    }
                }
                break;
            }
            
            case TNT: {
                if(!((TCP_ModeratorList.getAllAdmins().contains(event.getPlayer().getName())))) {
                    if(!(event.getPlayer().getName().equalsIgnoreCase("WickedGamingUK"))) {
                        player.sendMessage(ChatColor.RED + "The Use of TNT is not permitted on TranxCraft.");
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        event.setCancelled(true);
                    }
                }
                break;
            }
            
            case LAVA:
            case STATIONARY_LAVA:
            case LAVA_BUCKET: {
                if(!((TCP_ModeratorList.getAllAdmins().contains(event.getPlayer().getName())))) {
                    if(!(event.getPlayer().getName().equalsIgnoreCase("WickedGamingUK"))) {
                        player.sendMessage(ChatColor.RED + "The Use of Lava is not permitted on TranxCraft.");
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        event.setCancelled(true);
                    }
                }
                break;
            }
                
            case WATER:
            case STATIONARY_WATER:
            case WATER_BUCKET: {
                if(!((TCP_ModeratorList.getAllAdmins().contains(event.getPlayer().getName())))) {
                    if(!(event.getPlayer().getName().equalsIgnoreCase("WickedGamingUK"))) {
                        player.sendMessage(ChatColor.RED + "The Use of Water is not permitted on TranxCraft.");
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        event.setCancelled(true);
                    }
                }
                break;
            }
                
            case FIRE: {
                if(!((TCP_ModeratorList.getAllAdmins().contains(event.getPlayer().getName())))) {
                    if(!(event.getPlayer().getName().equalsIgnoreCase("WickedGamingUK"))) {
                        player.sendMessage(ChatColor.RED + "The Use of Fire is not permitted on TranxCraft.");
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        event.setCancelled(true);
                    }
                }
                break;
            }
        }
    }
    //Credits to Madgeek1450 & DarthSalamon for this event handler
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerPing(ServerListPingEvent event) {
        if (TCP_Util.isIPBanned(event.getAddress().getHostAddress())) {
            event.setMotd(ChatColor.RED + "You are banned.");
        }
        else if (Bukkit.hasWhitelist()) {
            event.setMotd(ChatColor.RED + "Whitelist enabled.");
        }
        else if (Bukkit.getOnlinePlayers().length >= Bukkit.getMaxPlayers()) {
            event.setMotd(ChatColor.RED + "Server is full.");
        }
    }
    
    private void kickPlayer(Player player, PlayerLoginEvent event) {
        Player[] players = TranxCraft.plugin.getServer().getOnlinePlayers();
        
        for (Player p : players) {
            if (TCP_ModeratorList.getAllAdmins().contains(p.getName()) || TCP_ModeratorList.getDonators().contains(p.getName())) {
                p.kickPlayer(this.kickMessage);
                event.allow();
                TCP_Log.info("Allowed player " + player.getName() + " to join full server by kicking player " + p.getName() + "!");
            }
        }

        event.disallow(PlayerLoginEvent.Result.KICK_FULL, "Unable to find any kickable players to open slots!");
  }
}