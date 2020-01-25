package org.pyacademy.banlistener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.pyacademy.BanX;
import org.pyacademy.commands.CommandExec;

import java.util.HashMap;

public class BListener implements Listener {
    public BanX plugin;
    public BListener(BanX p){
        plugin = p;
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent e){
        Player player = e.getPlayer();

        if(getBanned().containsKey(player.getName().toLowerCase())){
            if(getBanned().get(player.getName().toLowerCase()) != null){
                long endOfBan = getBanned().get(player.getName().toLowerCase());
                long now = System.currentTimeMillis();
                long diff = endOfBan - now;

                if(diff<=0){
                    getBanned().remove(player.getName().toLowerCase());
                }else{
                    e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "[TempBan] You are temp-banned for " + CommandExec.getMSG(endOfBan));
                }
            }
        }
    }

    public void tell(Player player, String m){
        player.sendMessage(m);
    }

    public HashMap<String, Long> getBanned(){
        return BanX.banned;
    }
}
