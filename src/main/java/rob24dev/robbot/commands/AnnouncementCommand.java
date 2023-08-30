package rob24dev.robbot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import rob24dev.robbot.Main;

import java.util.Objects;


public class AnnouncementCommand extends ListenerAdapter {

    String[] args2;


    public static String message(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; ++i) {
            builder.append(args[i]).append(" ");
        }
        return builder.toString();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String[] args = message.getContentRaw().split(" ");
        if(event.getAuthor().getId().equalsIgnoreCase("872819574108733450") | event.getAuthor().getId().equalsIgnoreCase("746710850319941652")) {
            if (args[0].equalsIgnoreCase("/oznameni")) {
                message.delete().complete();
                if (!(args.length > 1)) {
                    EmbedBuilder pleaseUse = new EmbedBuilder();
                    pleaseUse.setTitle("**Rob | Oznámení**");
                    pleaseUse.setDescription("Použijte /oznameni [zpráva]");
                    pleaseUse.setColor(0xf00a0a);
                    sendPrivateMessage(pleaseUse, event.getAuthor(), "embed");
                } else {
                    EmbedBuilder pleaseUse2 = new EmbedBuilder();
                    pleaseUse2.setTitle("**Rob | Oznámení**");
                    pleaseUse2.setDescription("Pokud chceš oznámení s pingem, klikni na button chci oznámení s pingem, pokud ne klikni na button nechci oznámení s pingem");
                    pleaseUse2.setColor(0xf00a0a);
                    sendPrivateMessage(pleaseUse2, event.getAuthor(), "embedbuttons");
                    args2 = args;
                }
            }
        }
    }


    private void sendPrivateMessage(EmbedBuilder message, User user, String method) {
        if (method.equalsIgnoreCase("embed")) {
            user.openPrivateChannel()
                .flatMap(privateChannel -> privateChannel.sendMessageEmbeds(message.build()))
                .queue();
        } else if (method.equalsIgnoreCase("embedbuttons")) {
            Button ano = Button.success("Ano", "Klikni, pokud chceš oznámení s pingem");
            Button ne = Button.success("Ne", "Klikni, pokud nechceš oznámení s pingem");
            user.openPrivateChannel()
                .flatMap(privateChannel -> privateChannel.sendMessageEmbeds(message.build())
                                                         .setActionRows(ActionRow.of(ano, ne))).queue();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (event.getMessage().getChannel().getType().equals(ChannelType.PRIVATE)) {
            if (Objects.requireNonNull(event.getButton().getId()).equalsIgnoreCase("Ano")) {
                Main.getInstance().getConfig().set(event.getUser().getId() + ".ping", "Yes");
                Main.getInstance().saveConfig();
                Main.getInstance().reloadConfig();
                String ping = Main.getInstance().getConfig().getString(event.getUser().getId() + ".ping");
                Oznameni(ping, event.getJDA(), event.getUser(), args2);
            }
            if (event.getButton().getId().equalsIgnoreCase("Ne")) {
                Main.getInstance().getConfig().set(event.getUser().getId() + ".ping", "No");
                Main.getInstance().saveConfig();
                Main.getInstance().reloadConfig();
                String ping = Main.getInstance().getConfig().getString(event.getUser().getId() + ".ping");
                Oznameni(ping, event.getJDA(), event.getUser(), args2);
            }
        }
    }

    public void Oznameni(String ping, JDA jda, User author, String[] args) {
        TextChannel channel;
        if (ping.equalsIgnoreCase("Yes")) {
            channel = jda.getTextChannelById("1001225879235604560");
            EmbedBuilder oznameni = new EmbedBuilder();
            oznameni.setTitle("**Rob | Oznámení**");
            oznameni.setDescription("Uživatel: " + author
                    .getName() + " přidal nové oznámení :)");
            oznameni.addField("Oznámení:", AnnouncementCommand.message(args).replace("_", " "), false);
            oznameni.setFooter("Bota vytvořil: Rob24Dev#4349");
            oznameni.setColor(0xf00a0a);
            Objects.requireNonNull(channel).sendMessageEmbeds(oznameni.build()).complete();
            channel.sendMessage("<@&1040666887698272277>").complete();
            args2 = null;
        } else {
            channel = jda.getTextChannelById("1001225879235604560");
            EmbedBuilder oznameni = new EmbedBuilder();
            oznameni.setTitle("**Rob | Oznámení**");
            oznameni.setDescription("Uživatel: " + author
                    .getName() + " přidal nové oznámení :)");
            oznameni.addField("Oznámení:", AnnouncementCommand.message(args).replace("_", " "), false);
            oznameni.setFooter("Bota vytvořil: Rob24Dev#4349");
            oznameni.setColor(0xf00a0a);
            Objects.requireNonNull(channel).sendMessageEmbeds(oznameni.build()).complete();
            args2 = null;
        }
    }
}

