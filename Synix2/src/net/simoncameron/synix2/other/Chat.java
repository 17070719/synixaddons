package net.simoncameron.synix2.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Chat {

    public static void send(Player player, List<String> strings) {
        strings.forEach(player::sendMessage);
    }

    public static void send(CommandSender sender, List<String> strings) {
        strings.forEach(sender::sendMessage);
    }

    @SuppressWarnings("deprecation")
	public static void sendTitle(Player player, String string1, String string2) {
        player.sendTitle(string1, string2);
    }

    public static void sendActionBar(Player player, String message) {
        message = translate(message);
        String version = Bukkit.getServer().getClass().getPackage().getName();
        version = version.substring(version.lastIndexOf(".") + 1);
        if (!player.isOnline())
            return;
        try {
            Object packet;
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutChat");
            Class<?> packetClass = Class.forName("net.minecraft.server." + version + ".Packet");
            Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + version + ".ChatComponentText");
            Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + version + ".IChatBaseComponent");
            try {
                Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + version + ".ChatMessageType");
                Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                Object chatMessageType = null;
                for (Object obj : chatMessageTypes) {
                    if (obj.toString().equals("GAME_INFO"))
                        chatMessageType = obj;
                }
                Object chatCompontentText = chatComponentTextClass.getConstructor(new Class[] { String.class }).newInstance(message);
                packet = packetPlayOutChatClass.getConstructor(new Class[] { iChatBaseComponentClass, chatMessageTypeClass }).newInstance(chatCompontentText, chatMessageType);
            } catch (ClassNotFoundException cnfe) {
                Object chatCompontentText = chatComponentTextClass.getConstructor(new Class[] { String.class }).newInstance(message);
                packet = packetPlayOutChatClass.getConstructor(new Class[] { iChatBaseComponentClass, byte.class }).newInstance(chatCompontentText, (byte) 2);
            }
            Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
            Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
            Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
            Object playerConnection = playerConnectionField.get(craftPlayerHandle);
            Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
            sendPacketMethod.invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> translate(List<String> strings) {
        List<String> toReturn = new ArrayList<>();
        strings.forEach(string -> toReturn.add(translate(string)));
        return toReturn;
    }

    public static void log(Object... objects) {
        for (Object object : objects)
            Logger.getLogger("FatCore").log(Level.SEVERE, object.toString());
    }

    public static void log(List<String> lines) {
        lines.forEach(Chat::log);
    }

    public static String formatDouble(double string) {
        DecimalFormat format = new DecimalFormat("###,###.##");
        return format.format(string);
    }

}
