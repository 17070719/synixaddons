package net.simoncameron.synix2.other;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.plugin.Plugin;
import java.lang.reflect.Field;
import java.util.HashMap;
import org.bukkit.command.CommandMap;
import org.bukkit.Bukkit;
import org.bukkit.plugin.SimplePluginManager;
import java.util.ArrayList;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.Command;
import java.util.List;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRegistrar {

    public static void registerCommand(JavaPlugin plugin, CommandExecutor c, String name, List<String> aliases) {
        PluginCommand p = getPluginCommand(plugin, name, aliases);
        getCommandMap().register(plugin.getName(), p);
        p.setExecutor(c);
    }

    public static void registerCommand(JavaPlugin plugin, CommandExecutor c, String name) {
        PluginCommand p = getPluginCommand(plugin, name, new ArrayList<>());
        getCommandMap().register(plugin.getName(), p);
        p.setExecutor(c);
    }

    public static void unregisterCommand(String name, List<String> aliases) {
        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);
                CommandMap map = (CommandMap) field.get(Bukkit.getPluginManager());
                Field f = map.getClass().getDeclaredField("knownCommands");
                f.setAccessible(true);
                @SuppressWarnings("unchecked")
				HashMap<String, Command> commands = (HashMap<String, Command>) f.get(map);
                commands.remove(name);
                aliases.forEach(commands::remove);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static PluginCommand getPluginCommand(JavaPlugin plugin, String name, List<String> aliases) {
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(new Class[] {String.class, Plugin.class});
            c.setAccessible(true);
            PluginCommand pc = c.newInstance(new Object[] {name, plugin});
            pc.setAliases(aliases);
            return pc;

        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static CommandMap getCommandMap() {
        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                return (CommandMap)f.get(Bukkit.getServer());
            }
            return null;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return null;
        }
    }

}
