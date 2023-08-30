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

public class SurveyNumbersCommand extends ListenerAdapter {

    String[] args2;
    public int number;

    public static String message(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 2; i < args.length; ++i) {
            builder.append(args[i]).append(" ");
        }
        return builder.toString();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String[] args = message.getContentRaw().split(" ");
        if(event.getAuthor().getId().equalsIgnoreCase("872819574108733450") | event.getAuthor().getId().equalsIgnoreCase("746710850319941652")) {
            if (args[0].equalsIgnoreCase("/anketanumber")) {
                message.delete().complete();
                if (!(args.length > 2)) {
                    EmbedBuilder pleaseUse = new EmbedBuilder();
                    pleaseUse.setTitle("**Rob | Anketa**");
                    pleaseUse.setDescription("Použijte /anketanumber [čísla] [zpráva]");
                    pleaseUse.setFooter("Typ: Čísla");


                    pleaseUse.setColor(0xf00a0a);
                    sendPrivateMessage(pleaseUse, event.getAuthor(), "embed");
                } else {
                    number = Integer.parseInt(args[1]);
                    if(number > 10 | number == 0 | number < 0) {
                        EmbedBuilder pleaseUse = new EmbedBuilder();
                        pleaseUse.setTitle("**Rob | Anketa**");
                        pleaseUse.setDescription("Maximální počet čísel je **10** a minimální **1**");
                        pleaseUse.setFooter("Typ: Čísla");
                        pleaseUse.setColor(0xf00a0a);
                        sendPrivateMessage(pleaseUse, event.getAuthor(), "embed");
                    } else {
                        EmbedBuilder pleaseUse2 = new EmbedBuilder();
                        pleaseUse2.setTitle("**Rob | Anketa**");
                        pleaseUse2.setDescription("Pokud chceš anketu s pingem, klikni na button chci anketu s pingem, pokud ne klikni na button nechci anketu s pingem");
                        pleaseUse2.setFooter("Typ: Čísla");
                        pleaseUse2.setColor(0xf00a0a);
                        sendPrivateMessage(pleaseUse2, event.getAuthor(), "embedbuttons");
                        args2 = args;
                    }
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
            Button ano = Button.success("Ano", "Klikni, pokud chceš anketu s pingem");
            Button ne = Button.success("Ne", "Klikni, pokud nechceš anketu s pingem");
            user.openPrivateChannel()
                .flatMap(privateChannel -> privateChannel.sendMessageEmbeds(message.build())
                                                         .setActionRows(ActionRow.of(ano, ne))).queue();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (event.getMessage().getChannel().getType().equals(ChannelType.PRIVATE)) {
            if (Objects.requireNonNull(event.getButton().getId()).equalsIgnoreCase("Ano")) {
                Main.getInstance().getConfig().set(event.getUser().getId() + ".anketa" + ".2" + ".ping", "Yes");
                Main.getInstance().saveConfig();
                Main.getInstance().reloadConfig();
                String ping = Main.getInstance().getConfig().getString(event.getUser().getId() + ".ping");
                embed(ping, event.getJDA(), args2);
            }
            if (event.getButton().getId().equalsIgnoreCase("Ne")) {
                Main.getInstance().getConfig().set(event.getUser().getId() + ".anketa" + ".2" + ".ping", "No");
                Main.getInstance().saveConfig();
                Main.getInstance().reloadConfig();
                String ping = Main.getInstance().getConfig().getString(event.getUser().getId() + ".anketa" + ".2" + ".ping");
                embed(ping, event.getJDA(), args2);
            }
        }
    }

    public void embed(String ping, JDA jda, String[] args) {
        TextChannel channel = jda.getTextChannelById("1002282497641562253");
        if (ping.equalsIgnoreCase("Yes")) {
            EmbedBuilder  embed = new EmbedBuilder();
            embed.setDescription(SurveyNumbersCommand.message(args).replace("_", " "));
            embed.setColor(0xf00a0a);
            Message message = Objects.requireNonNull(channel).sendMessageEmbeds(embed.build()).complete();
            channel.sendMessage("<@&1040666887698272277>").complete();
            args2 = null;
            addNumbers(number, message);
        } else {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setDescription(SurveyNumbersCommand.message(args).replace("_", " "));
            embed.setColor(0xf00a0a);
            Message message = Objects.requireNonNull(channel).sendMessageEmbeds(embed.build()).complete();
            args2 = null;
            addNumbers(number, message);
        }
    }

    private void addNumbers(int i, Message message) {
        if(i == 10) {
            message.addReaction("1_:1002271500285526077").queue();
            message.addReaction("2_:1002271601632497785").queue();
            message.addReaction("3_:1002271572884738079").queue();
            message.addReaction("4_:1002271792393633912").queue();
            message.addReaction("5_:1002271684042178711").queue();
            message.addReaction("6_:1002271755819294811").queue();
            message.addReaction("7_:1002271635354701905").queue();
            message.addReaction("8_:1002271721170161776").queue();
            message.addReaction("9_:1002271824203239426").queue();
            message.addReaction("10:1002271537103122584").queue();
        }
        if(i == 9) {
            message.addReaction("1_:1002271500285526077").queue();
            message.addReaction("2_:1002271601632497785").queue();
            message.addReaction("3_:1002271572884738079").queue();
            message.addReaction("4_:1002271792393633912").queue();
            message.addReaction("5_:1002271684042178711").queue();
            message.addReaction("6_:1002271755819294811").queue();
            message.addReaction("7_:1002271635354701905").queue();
            message.addReaction("8_:1002271721170161776").queue();
            message.addReaction("9_:1002271824203239426").queue();
        }
        if(i == 8) {
            message.addReaction("1_:1002271500285526077").queue();
            message.addReaction("2_:1002271601632497785").queue();
            message.addReaction("3_:1002271572884738079").queue();
            message.addReaction("4_:1002271792393633912").queue();
            message.addReaction("5_:1002271684042178711").queue();
            message.addReaction("6_:1002271755819294811").queue();
            message.addReaction("7_:1002271635354701905").queue();
            message.addReaction("8_:1002271721170161776").queue();
        }
        if(i == 7) {
            message.addReaction("1_:1002271500285526077").queue();
            message.addReaction("2_:1002271601632497785").queue();
            message.addReaction("3_:1002271572884738079").queue();
            message.addReaction("4_:1002271792393633912").queue();
            message.addReaction("5_:1002271684042178711").queue();
            message.addReaction("6_:1002271755819294811").queue();
            message.addReaction("7_:1002271635354701905").queue();
        }
        if(i == 6) {
            message.addReaction("1_:1002271500285526077").queue();
            message.addReaction("2_:1002271601632497785").queue();
            message.addReaction("3_:1002271572884738079").queue();
            message.addReaction("4_:1002271792393633912").queue();
            message.addReaction("5_:1002271684042178711").queue();
            message.addReaction("6_:1002271755819294811").queue();
        }
        if(i == 5) {
            message.addReaction("1_:1002271500285526077").queue();
            message.addReaction("2_:1002271601632497785").queue();
            message.addReaction("3_:1002271572884738079").queue();
            message.addReaction("4_:1002271792393633912").queue();
            message.addReaction("5_:1002271684042178711").queue();
        }
        if(i == 4) {
            message.addReaction("1_:1002271500285526077").queue();
            message.addReaction("2_:1002271601632497785").queue();
            message.addReaction("3_:1002271572884738079").queue();
            message.addReaction("4_:1002271792393633912").queue();
        }
        if(i == 3) {
            message.addReaction("1_:1002271500285526077").queue();
            message.addReaction("2_:1002271601632497785").queue();
            message.addReaction("3_:1002271572884738079").queue();
        }
        if(i == 2) {
            message.addReaction("1_:1002271500285526077").queue();
            message.addReaction("2_:1002271601632497785").queue();
        }
        if(i == 1) {
            message.addReaction("1_:1002271500285526077").queue();
        }
    }
}

