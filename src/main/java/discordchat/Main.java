package discordchat;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class Main extends PluginBase {

    public boolean debug;

    public static JDA jda;
    public static Guild server;
    public static TextChannel channel;
    public static Config config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        config = getConfig();
        debug = config.getBoolean("debug", true);
        try {
            if (debug) getServer().getLogger().info("Logging in with bot token " + config.getString("botToken", "\u00A7cnull"));
            jda = new JDABuilder(AccountType.BOT).setToken(config.getString("botToken")).buildBlocking();
            if (debug) getServer().getLogger().info("Set server id to " + config.getString("serverId", "\u00A7cnull"));
            server = jda.getGuildById(config.getString("serverId"));
            if (debug) getServer().getLogger().info("Set server channel id to " + config.getString("channelId", "\u00A7cnull"));
            channel = jda.getTextChannelById(config.getString("channelId"));
            jda.addEventListener(new DiscordListener());
            jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, config.getString("botStatus")));
        } catch (Exception e) {
            getLogger().error("Couldn't enable discord chat sync");
            if (debug) e.printStackTrace();
        }
    }
}