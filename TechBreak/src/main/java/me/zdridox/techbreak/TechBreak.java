package me.zdridox.techbreak;

import me.zdridox.techbreak.Commands.TechBreakCommand;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class TechBreak extends JavaPlugin implements Listener {

    TechBreakCommand tbc = new TechBreakCommand();
    private static TechBreak instance;

    public static TechBreak GetInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        System.out.println("TechBreak on!");
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("techBreak").setExecutor(new TechBreakCommand());
        instance = this;
        saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        tbc.OnOff = config.getBoolean("techbreak.Bool");
        tbc.Reason = config.getString("techbreak.Reason");
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(!event.getPlayer().hasPermission("TechBreak.join") && tbc.OnOff) {
            event.getPlayer().kickPlayer("§4§lPrzerwa Technicna! \n §9§lDiscord: §r§9discord.gg/Rg7pMCXPFU \n §6§l-------------------------- \n" +(ChatColor.DARK_AQUA + tbc.Reason));
        } else {
            if(tbc.OnOff) {
                BukkitScheduler scheduler = Bukkit.getScheduler();
                scheduler.runTaskTimer(this, task -> {
                    if(!tbc.OnOff) task.cancel();
                    event.getPlayer().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "§lPrzerwa Techniczna"));
                }, 0L, 40L);
            }
        }
    }


}
