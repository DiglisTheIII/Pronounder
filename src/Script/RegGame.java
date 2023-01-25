package Script;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import bot.commands.Commands;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RegGame extends Commands{
    
    public static void regGame(MessageReceivedEvent e) {
        File check = new File("./gitignore/GameStats/" + e.getGuild().getName().replaceAll(" ", "_") + "/" + e.getAuthor().getName() + "/reg_check.txt");
        if(!check.exists()) {
            try {
                File gameStats = new File("./gitignore/GameStats/" + e.getGuild().getName().replaceAll(" ", "_"));
    
                if(!gameStats.exists()) {
                    gameStats.mkdir();
                }
                
                String path = gameStats.getAbsolutePath() + "/" + e.getAuthor().getName();
                File f = new File(path);
                File[] gameFiles = { new File(path + "/borg.txt"), new File(path + "/gloob.txt"), new File(path + "/currency.txt"), new File(path + "/reg_check.txt"), new File(path + "/is_eligible.txt")};
    
                if(!f.exists()) {
                    f.mkdir();
                    for(File fx : gameFiles) {
                        if(!fx.exists()) {
                            try {
                                fx.createNewFile();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    RegGame.sendMessage("You are now registered for the games", e);

                    try {
                        BufferedWriter borg = new BufferedWriter(new FileWriter(gameFiles[0]));
                        BufferedWriter gloob = new BufferedWriter(new FileWriter(gameFiles[1]));
                        BufferedWriter curr = new BufferedWriter(new FileWriter(gameFiles[2]));
                        
                        borg.write("Borgs: \nHarvest Count: ");
                        borg.close();

                        gloob.write("Kills: \nCurrent Weapon: ");
                        gloob.close();
                        
                        curr.write("0");
                        curr.close();

                        gameFiles[3].setWritable(false);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }catch(SecurityException | NullPointerException e3) {
                RegGame.sendMessage("File creation failed, notifying Diglis.", e);
                Member dig = e.getGuild().getMemberById("695688150466428989");
                dig.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(e3.getStackTrace().toString())).queue();
            }
        } else {
            RegGame.sendMessage("Already registered! Choose a game to play.", e);
        }

    }

    private static void sendMessage(String message, MessageReceivedEvent e) {
        e.getMessage().reply(message).queue();
    }


}
