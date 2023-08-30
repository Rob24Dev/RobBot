package rob24dev.robbot.tasks;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.scheduler.BukkitRunnable;
import rob24dev.robbot.Main;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DayTask extends BukkitRunnable {

    //This task was for notifications when I will/won't be streaming / Tento task byl pro upozornění, když budu/nebudu streamovat

    @Override
    public void run() {
        Calendar calendar = Calendar.getInstance();
        Format f = new SimpleDateFormat("EEEE");
        String day = f.format(new Date());
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        TextChannel textChannel = Main.jda.getTextChannelById("1007668308549570720");
        if (minute == 0 & hour == 0 & sec == 0) {
            switch (day) {
                case "Monday": { //Monday
                    Emoji emoji = Emoji.fromUnicode("U+1F7E0");
                    Objects.requireNonNull(textChannel).getManager().setName("【" + emoji + "】status").complete();
                    break;
                }
                case "Tuesday": { //Tuesday
                    Emoji emoji = Emoji.fromUnicode("U+26AB");
                    Objects.requireNonNull(textChannel).getManager().setName("【" + emoji + "】status").complete();
                    break;
                }
                case "Wednesday": { //Wednesday
                    Emoji emoji = Emoji.fromUnicode("U+26AB");
                    Objects.requireNonNull(textChannel).getManager().setName("【" + emoji + "】status").complete();
                    break;
                }
                case "Thursday": { //Thursday
                    Emoji emoji = Emoji.fromUnicode("U+1F534");
                    Objects.requireNonNull(textChannel).getManager().setName("【" + emoji + "】status").complete();
                    break;
                }
                case "Friday": { //Friday
                    Emoji emoji = Emoji.fromUnicode("U+26AB");
                    Objects.requireNonNull(textChannel).getManager().setName("【" + emoji + "】status").complete();
                    break;
                }
                case "Saturday": { //Saturday
                    Emoji emoji = Emoji.fromUnicode("U+1F7E4");
                    Objects.requireNonNull(textChannel).getManager().setName("【" + emoji + "】status").complete();
                    break;
                }
                case "Sunday": { //Sunday
                    Emoji emoji = Emoji.fromUnicode("U+26AB");
                    Objects.requireNonNull(textChannel).getManager().setName("【" + emoji + "】status").complete();
                    break;
                }
            }
        }
    }
}