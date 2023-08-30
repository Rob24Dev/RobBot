package rob24dev.robbot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import rob24dev.robbot.Main;

import java.util.Objects;

public class SurveyCommand extends ListenerAdapter {


    Modal modal;
    TextInput answer;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase("anketa")) return;
        if (event.getUser().getId().equalsIgnoreCase("872819574108733450") | event.getUser().getId().equalsIgnoreCase("746710850319941652")) {
            OptionMapping question = event.getOption("otazka");
            String questionText = Objects.requireNonNull(question).getAsString();
            OptionMapping ping = event.getOption("ping");
            boolean pingBol = Objects.requireNonNull(ping).getAsBoolean();
            EmbedBuilder survey = new EmbedBuilder();
            survey.setDescription("**Anketa:** \n  \n **Otázka:** *" + questionText + "*");
            survey.setColor(0xf00a0a);
            Button button = Button.success("Anketa", "Klikni pro odpověď");
            event.replyEmbeds(survey.build()).addActionRow(button).queue();
            modalCreate(questionText);
            if (pingBol) {
                event.getTextChannel().sendMessage("<@&1040666887698272277>").queue();
            }
        }
        int i = 0;
        Bukkit.getConsoleSender().sendMessage(String.valueOf(i));
    }

    private void modalCreate(String questionText) {

        answer = TextInput.create("Anketa", "Otázka", TextInputStyle.PARAGRAPH)
                .setMinLength(1)
                .setMaxLength(500)
                .setRequired(true)
                .setPlaceholder("Zde napište vaší odpověď")
                .build();

        modal = Modal.create("Anketa", "Otázka: " + questionText).addActionRow(answer).build();

    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (Objects.requireNonNull(event.getButton().getId()).equalsIgnoreCase("Anketa")) {
            boolean question = Main.getInstance().getConfig().getBoolean("PlayerData" + "." + event.getUser().getId() + "." + modal.getTitle() + ".odpoved");
            User user = event.getUser();
            if(question) {
                EmbedBuilder questionEmbed = new EmbedBuilder();
                questionEmbed.setDescription("**Anketa:** \n  \n Již jste odpověděli!");
                questionEmbed.setColor(0xf00a0a);
                Objects.requireNonNull(user).openPrivateChannel().flatMap(privateChannel -> privateChannel.sendMessageEmbeds(questionEmbed.build())).queue();
            } else {
                event.replyModal(modal).queue();
            }
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        if(event.getModalId().equalsIgnoreCase("Anketa")) {
            TextChannel textChannel = Main.jda.getTextChannelById("1001225877784371363");
            String answer = Objects.requireNonNull(event.getValue("Anketa")).getAsString();
            Objects.requireNonNull(textChannel).sendMessage("Uživatel **" + event.getUser().getName() + "** odpověděl v anketě **" + modal.getTitle() + "** **" + answer + "**.").queue();
            Main.getInstance().getConfig().set("PlayerData" + "." + event.getUser().getId() + "." + modal.getTitle() + ".odpoved", true);
            Main.getInstance().saveConfig();
            Main.getInstance().reloadConfig();
            event.reply("Úspěšně jsi odpověděl!").setEphemeral(true).queue();
        }
    }
}
