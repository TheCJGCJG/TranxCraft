
package com.wickedgaminguk.TranxCraft;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TCP_ModeratorList {
    
    private static List<String> Executives = TCP_Util.getConfigFile().getStringList("Executives");
    private static List<String> leadAdmins = TCP_Util.getConfigFile().getStringList("Lead_Admins");
    private static List<String> Admins = TCP_Util.getConfigFile().getStringList("Admins");
    private static List<String> Moderators = TCP_Util.getConfigFile().getStringList("Moderators");
    private static List<String> Donators = TCP_Util.getConfigFile().getStringList("Donators");
    
    public static List<String> getModerators() {
        return Moderators;
    }
    
    public static List<String> getAdmins() {
        return Admins;
    }
    public static List<String> getleadAdmins() {
        return leadAdmins;
    }
    
    public static List<String> getExecutives() {
        return Executives;
    }
    
    public static List<String> getAllAdmins() {
        List<String> all = Moderators;
        all.addAll(Executives);
        all.addAll(leadAdmins);
        all.addAll(Admins);
        
        List<String> ops = new ArrayList();
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.isOp()) {
                String pn = p.getName();
                ops.add(pn);
            }
        }
        
        all.addAll(ops);
        return all;
    }
    
    
    public static List<String> getDonators() {
        return Donators;
    }
}