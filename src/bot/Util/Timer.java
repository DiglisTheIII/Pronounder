package bot.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class Timer extends Thread {
    
    public long time;

    public void run() {
        RuntimeMXBean uptime = ManagementFactory.getRuntimeMXBean();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./src/bot/Util/timer.txt")))) {
            writer.write(String.valueOf(uptime.getUptime()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTime(uptime.getUptime());

    }

    public void setTime(long t) {
        time = t;
    }

    public long getTime() {
        return time;
    }

}
