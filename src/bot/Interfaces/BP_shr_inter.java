package bot.Interfaces;

import java.io.File;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;

public interface BP_shr_inter {
    
    public void sendMessage(String message, MessageReceivedEvent e, boolean isReply, File f);

    public RestAction<?> sendMessageQueue(String message, MessageReceivedEvent e, boolean isReply);

}
