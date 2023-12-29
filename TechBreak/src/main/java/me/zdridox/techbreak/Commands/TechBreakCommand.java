package me.zdridox.techbreak.Commands;

import me.zdridox.techbreak.TechBreak;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TechBreakCommand implements CommandExecutor, TabExecutor {

   public static String Reason;
   public static boolean OnOff = false;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        StringBuilder sb = new StringBuilder();
        if(args.length != 0) {
            for(int i = 0; i < args.length; i++) {
                sb.append(args[i] + " ");
            }
            Reason = sb.toString();
        } else {
            Reason = "";
        }

        OnOff = !OnOff;
        commandSender.sendMessage(String.valueOf(OnOff), Reason);

        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(!p.hasPermission("TechBreak.join")) p.kickPlayer("§4§lPrzerwa Technicna! \n §9§lDiscord: §r§9discord.gg/Rg7pMCXPFU \n §6§l-------------------------- \n" +(ChatColor.DARK_AQUA + Reason));
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTaskTimer(TechBreak.GetInstance(), task -> {
                if(!OnOff) task.cancel();
                p.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "§lPrzerwa Techniczna"));
            }, 0L, 40L);

        }

        FileConfiguration config = TechBreak.GetInstance().getConfig();
        config.set("techbreak.Bool", OnOff);
        config.set("techbreak.Reason", Reason);
        TechBreak.GetInstance().saveConfig();
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length != 0) return Arrays.asList("Powod");
        return null;
    }
}
