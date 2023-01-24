package bot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.security.auth.login.LoginException;

import bot.Util.Timer;
import bot.commands.Commands;
import bot.commands.SlashCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class index {

    public static void main(String[] args) throws LoginException, IOException {
        String token = Files.readAllLines(Paths.get("./gitignore/token.txt")).get(0);
        
        JDA jda = JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_WEBHOOKS)
                .disableCache(CacheFlag.EMOTE)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES,
                    GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_WEBHOOKS)
                .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.ONLINE_STATUS, CacheFlag.ACTIVITY,
                    CacheFlag.VOICE_STATE, CacheFlag.ROLE_TAGS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new Commands(), new SlashCommands())
                .setActivity(Activity.playing("I literally hate you with every fiber of my being"))
                .setStatus(OnlineStatus.ONLINE)
                .build();

        jda.upsertCommand("register", "Register Server").queue();

        Timer t = new Timer();
        t.start();
        while(!Timer.currentThread().isInterrupted()) {
            t.run();
        }
    }
}