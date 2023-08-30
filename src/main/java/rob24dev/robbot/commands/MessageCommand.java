package rob24dev.robbot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import rob24dev.robbot.Main;

import java.util.Objects;

public class MessageCommand extends ListenerAdapter {

    String[] args2;
    TextChannel textChannel;

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
            if (args[0].equalsIgnoreCase("/zprava")) {
                message.delete().complete();
                if (!(args.length > 1)) {
                    EmbedBuilder pleaseUse = new EmbedBuilder();
                    pleaseUse.setTitle("**Rob | Zprava**");
                    pleaseUse.setDescription("Použijte /zprava [zpráva]");
                    pleaseUse.setColor(0xf00a0a);
                    sendPrivateMessage(pleaseUse, event.getAuthor(), "embed");
                } else {
                    EmbedBuilder pleaseUse2 = new EmbedBuilder();
                    pleaseUse2.setTitle("**Rob | Zprava**");
                    pleaseUse2.setDescription("Pokud chceš zpravu s pingem, klikni na button chci zpravu s pingem, pokud ne klikni na button nechci zpravu s pingem");
                    pleaseUse2.setColor(0xf00a0a);
                    sendPrivateMessage(pleaseUse2, event.getAuthor(), "embedbuttons");
                    args2 = args;
                    textChannel = event.getMessage().getTextChannel();
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
            Button ano = Button.success("Ano", "Klikni, pokud chceš zpravu s pingem");
            Button ne = Button.success("Ne", "Klikni, pokud nechceš zpravu s pingem");
            user.openPrivateChannel()
                .flatMap(privateChannel -> privateChannel.sendMessageEmbeds(message.build())
                                                         .setActionRows(ActionRow.of(ano, ne))).queue();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (event.getMessage().getChannel().getType().equals(ChannelType.PRIVATE)) {
            if (Objects.requireNonNull(event.getButton().getId()).equalsIgnoreCase("Ano")) {
                Main.getInstance().getConfig().set(event.getUser().getId() + ".zprava" + ".ping", "Yes");
                Main.getInstance().saveConfig();
                Main.getInstance().reloadConfig();
                String ping = Main.getInstance().getConfig().getString(event.getUser().getId() + ".ping");
                embed(ping, event.getJDA(), args2);
            }
            if (event.getButton().getId().equalsIgnoreCase("Ne")) {
                Main.getInstance().getConfig().set(event.getUser().getId() + ".zprava" + ".ping", "No");
                Main.getInstance().saveConfig();
                Main.getInstance().reloadConfig();
                String ping = Main.getInstance().getConfig().getString(event.getUser().getId() + ".zprava" + ".ping");
                embed(ping, event.getJDA(), args2);
            }
        }
    }

    public void embed(String ping, JDA jda, String[] args) {
        TextChannel channel;
        if (ping.equalsIgnoreCase("Yes")) {
            channel = jda.getTextChannelById(textChannel.getId());

            Objects.requireNonNull(channel).sendMessage(MessageCommand.message(args).replace("_", " ") + "@everyone").complete();
            args2 = null;
        } else {
            channel = jda.getTextChannelById(textChannel.getId());
            Objects.requireNonNull(channel).sendMessage(MessageCommand.message(args).replace("_", " ")).complete();
            args2 = null;
        }
    }
}

