package bot.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommands extends ListenerAdapter {
    
    @Override
    public void onSlashCommand(SlashCommandEvent e) {
        if(e.getName().equals("register")) {
            writeFile(e, Permission.ADMINISTRATOR);
        }
    }


    /*
     * Seperates all members of the server into Bots, Admins, and Members, then creates and
     * writes to ./gitignore/server_name.txt, while also seperating member types throughout the file to keep it organized.
     * It also sorts each string list alphabetically, just because it makes it easier to look through.
     */
    public void writeFile(SlashCommandEvent e, Permission adm) {
        File server = new File("./gitignore/" + e.getGuild().getName().replaceAll(" ", "_") + ".txt");
        ArrayList<String> bots = new ArrayList<>();
        ArrayList<String> admins = new ArrayList<>();
        ArrayList<String> mems = new ArrayList<>();
        if(!server.exists()) {
            try {
                server.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(server));
                List<net.dv8tion.jda.api.entities.Member> members = e.getGuild().getMembers();
                        
                for(int i = 0; i < members.size(); i++) {
                    if(members.get(i).getUser().isBot()) {
                        bots.add(members.get(i).getUser().getName());
                    } else if(members.get(i).hasPermission(adm) && !members.get(i).getUser().isBot()) {
                        admins.add(members.get(i).getUser().getName());
                    } else {
                        mems.add(members.get(i).getUser().getName());
                    }
                }

                bots.sort(String::compareToIgnoreCase);
                admins.sort(String::compareToIgnoreCase);
                mems.sort(String::compareToIgnoreCase);
                         
                if(bots.size() > 0) {
                    writer.write("BOTS \n<-------------->\n");
                    for(int i = 0; i < bots.size(); i++) { writer.write(bots.get(i) + "\n"); }
                }
    
                if(admins.size() > 0) {
                    writer.write("\nADMINS \n<-------------->\n");
                    for(int i = 0; i < admins.size(); i++) { writer.write(admins.get(i) + "\n"); }
                }
    
                if(mems.size() > 0) {
                    writer.write("\nMEMBERS \n<-------------->\n");
                    for(int i = 0; i < mems.size(); i++) { writer.write(mems.get(i) + "\n"); }
                }
                e.reply(e.getGuild().getName() + " registered.").queue();
                writer.close();
    
            } catch (IOException e1) {
                e.reply(e1.getStackTrace().toString());
            }
        } else {
            e.reply(e.getGuild().getName() + " already registered!").queue();
        }
    }

}
