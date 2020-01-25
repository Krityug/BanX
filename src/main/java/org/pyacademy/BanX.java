package org.pyacademy;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.pyacademy.banlistener.BListener;
import org.pyacademy.commands.CommandExec;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Logger;


public final class BanX extends JavaPlugin {
    public static HashMap<String, Long> banned = new HashMap<String, Long>();

    public static String Path = "plugins/TempBan" + File.separator + "BanList.dat";
    public BListener Listener = new BListener(this);
    public Server server;
    public Logger log;

    public void onEnable(){
        server = this.getServer();
        log = this.getLogger();

        server.getPluginManager().registerEvents(Listener, this);

        File file = new File(Path);
        new File("plugins/BanX").mkdir();

        if(file.exists()){
            banned = load();
        }

        if(banned == null){
            banned = new HashMap<String, Long>();
        }

        this.getCommand("tempban").setExecutor(new CommandExec(this));
        this.getCommand("tempbanexact").setExecutor(new CommandExec(this));
        this.getCommand("unban").setExecutor(new CommandExec(this));
        this.getCommand("check").setExecutor(new CommandExec(this));
    }

    public void onDisable(){
        save();
    }

    public static void save(){
        File file = new File("plugins/BanX" + File.separator + "BanList.dat");
        new File("plugins/TempBanX").mkdir();
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Path));
            oos.writeObject(banned);
            oos.flush();
            oos.close();
            //Handle I/O exceptions
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    public static HashMap<String, Long> load(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Path));
            Object result = ois.readObject();
            ois.close();
            return (HashMap<String,Long>)result;
        }catch(Exception e){
            return null;
        }
    }

}
