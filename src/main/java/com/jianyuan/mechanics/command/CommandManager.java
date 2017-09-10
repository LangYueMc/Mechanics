package com.jianyuan.mechanics.command;

import com.jianyuan.mechanics.Mechanics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final Mechanics plugin;
    private final String[] cmds = {"debug", "reload"};

    public CommandManager(Mechanics instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mechanics")) {
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("[Mechanics] Use '/mec reload' to reload config");
            sender.sendMessage("[Mechanics] Use '/mec debug' to on/off debug");
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("Mechanics.command.reload") || !sender.isOp()) {
                sender.sendMessage("[Mechanics] I'm sorry, but you do not have permission to perform this command.");
                return true;
            }
            plugin.getSettings().reload();
            sender.sendMessage("[Mechanics] Configuration has been reloaded.");
            return true;
        } else if (args[0].equalsIgnoreCase("debug")) {
            if (!sender.hasPermission("Mechanics.command.debug") || !sender.isOp()) {
                sender.sendMessage("[Mechanics] I'm sorry, but you do not have permission to perform this command.");
                return true;
            }
            boolean debug = !plugin.getConfig().getBoolean("debug");
            plugin.getConfig().set("debug", debug);
            plugin.saveConfig();
            plugin.getMyLogger().setDebug(debug);
            sender.sendMessage("[Mechanics] debug " + (debug ? "on." : "off."));
            return true;
        }
        sender.sendMessage("[Mechanics] Use '/mec reload' to reload config");
        sender.sendMessage("[Mechanics] Use '/mec debug' to on/off debug");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("mechanics")) {
            return null;
        }
        for (int i = 0; i < args.length; i++) {
            args[i] = args[i].toLowerCase();
        }
        if (args.length == 1) {
            List<String> completions = getCompletions(args[0]);
            if (completions.size() > 0)
                args[0] = completions.get(0);
            return completions;
        }
        return null;
    }

    private List<String> getCompletions(String str) {
        List<String> completions = new ArrayList<String>();
        for (String cmd : cmds) {
            if (str == null || str.isEmpty()) {
                completions.add(cmd);
            } else if (cmd.contains(str)) {
                completions.add(cmd);
            }
        }
        if (completions.size() == 0) {
            for (String cmd : cmds) {
                completions.add(cmd);
            }
        }
        return completions;
    }
}
