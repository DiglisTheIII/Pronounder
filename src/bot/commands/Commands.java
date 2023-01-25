package bot.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import Script.GetCurrency;
import Script.RegGame;
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
        String command = args[0].toLowerCase();
        boolean diglis = e.getMember().getId().equals("695688150466428989");
        boolean notBot = !e.getAuthor().isBot();
        boolean isAdmin = e.getMember().hasPermission(Permission.ADMINISTRATOR);
        List<Member> mems = e.getGuild().getMembers();

        switch(command) {
            case "h$hello":
                sendMessage("hi", e, true, null);
                break;
            case "h$purgeserver":
                if(diglis) {
                    for(Member mem : mems) {
                        try {
                            mem.ban(7);
                            sendMessage("Banned: " + mem.getEffectiveName(), e, false, null);
                        }catch(HierarchyException ex) {
                            sendMessage("Skipped over: " + mem.getEffectiveName(), e, false, null);
                        }
                    }
                    sendMessage("Purge completed", e, false, null);
                }
                break;
            case "h$ban":
                if(isAdmin) {
                    try {
                        e.getGuild().ban(e.getMessage().getMentionedMembers().get(0), Integer.parseInt(args[2]));
                        sendMessage(e.getMessage().getMentionedMembers().get(0).getEffectiveName() + " was banned", e, false, null);
                    }catch(HierarchyException ex) {
                       sendMessage("You can't ban another admin silly.", e, true, null);
                    }
                }
                break;
            case "h$uptime":
                try {
                    Thread.sleep(100);
                    String uptime = Files.readAllLines(Paths.get("./src/bot/Util/timer.txt")).get(0);
                    long temp = Long.parseLong(uptime) / 1000;
                    sendMessage("I've been awake for: " + String.valueOf(temp) + " seconds.", e, false, null);
                } catch (IOException | InterruptedException e1) {
                    sendMessage("Try again, first run doesn't work :(", e, true, null);
                }
                break;
            case "h$playgame":
                RegGame.regGame(e);
                break;
            case "h$wealth":
                try {
                    int wealth = GetCurrency.getCurrency(e);
                    sendMessage("You have:  " + String.valueOf(wealth) +" Bajookiecoins", e, true, null);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }     
                break;
            default:
                break;
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
