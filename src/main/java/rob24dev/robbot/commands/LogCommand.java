package rob24dev.robbot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import rob24dev.robbot.Main;

import java.util.Objects;

public class LogCommand extends ListenerAdapter {

    int id = Main.getInstance().getConfig().getInt("id");

    private void sendPrivateMessage(EmbedBuilder message, User user, String method) {
        if (method.equalsIgnoreCase("embed")) {
            user.openPrivateChannel()
                    .flatMap(privateChannel -> privateChannel.sendMessageEmbeds(message.build()))
                    .queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String[] args = message.getContentRaw().split(" ");
        Role role = Main.jda.getRoleById("1001225877264269374");
        TextChannel channel = Main.jda.getTextChannelById("1005176430876954764");
        if (args[0].equalsIgnoreCase("/log")) {
            if (Objects.requireNonNull(event.getMember()).getRoles().contains(role)) {
                message.delete().complete();
                if (args.length == 7) {
                    int id2 = id += 1;
                    Main.getInstance().getConfig().set("id", id2);
                    Main.getInstance().saveConfig();
                    Main.getInstance().reloadConfig();
                    Objects.requireNonNull(channel).sendMessage("**Nick:** " + args[1] + "\n **Platforma:** " + args[2] + "\n **Důvod:** " + args[3] + "\n **Typ:** " + args[4] + "\n **Délka:** " + args[5] + "\n **ID:** " + id + "\n **Moderátor:** " + args[6]).queue();
                } else {
                    EmbedBuilder pleaseUse = new EmbedBuilder();
                    pleaseUse.setTitle("**Rob | Log**");
                    pleaseUse.setDescription("Použijte /log [jméno] [platforma] [důvod] [typ trestu] [délka] [moderátor]");
                    pleaseUse.setColor(0xf00a0a);
                    sendPrivateMessage(pleaseUse, event.getAuthor(), "embed");
                }
            } else {
                EmbedBuilder donTPermission = new EmbedBuilder();
                donTPermission.setTitle("**Rob | Log**");
                donTPermission.setDescription("Nemáš permise!");
                donTPermission.setColor(0xf00a0a);
                sendPrivateMessage(donTPermission, event.getAuthor(), "embed");
            }
        }
    }
}
