package rob24dev.robbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.plugin.java.JavaPlugin;
import rob24dev.robbot.commands.*;
import rob24dev.robbot.tasks.DayTask;

import javax.security.auth.login.LoginException;

public final class Main extends JavaPlugin {

    public static JDA jda;

    private static Main instance;


    public Main() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            jda = JDABuilder.createDefault(Main.getInstance().getConfig().getString("token"))
                    .setActivity(Activity.watching("Události na Rob ꕥ Komunita"))
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .addEventListeners(new AnnouncementCommand(), new rob24dev.robbot.commands.EmbedCommand(), new MessageCommand(), new SurveyYesNoCommand(), new SurveyNumbersCommand(), new rob24dev.robbot.commands.LogCommand(), new SurveyCommand())
                    .build().awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        new DayTask().runTaskTimer(this, 0,20);
        jda.upsertCommand("anketa", "Treti typ ankety ").addOption(OptionType.STRING, "otazka", "Zde napis otazku", true).addOption(OptionType.BOOLEAN, "ping", "Zde napis true nebo false podle toho jestli chces ping", true).queue();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }
}
