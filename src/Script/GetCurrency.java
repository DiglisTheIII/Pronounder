package Script;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GetCurrency {
    
    public static int getCurrency(MessageReceivedEvent e) throws IOException {
        String temp_curr = Files.readAllLines(Paths.get("./gitignore/GameStats/" + e.getGuild().getName().replaceAll(" ", "_") + "/" + e.getAuthor().getName() + "/currency.txt")).get(0);

        int total = Integer.parseInt(temp_curr);

        return total;
    }

    public static void freeCurrency() {
        
    }

}
