package Main.debug;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class debug extends ListenerAdapter{
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] command = event.getMessage().getContentRaw().split(" ");

		if (command[0].equalsIgnoreCase(Main.BotStartup.prefix + "debug")) {
			System.out.println("debug START");

			EmbedBuilder eb = new EmbedBuilder();

			/*
			    Set the title:
			    1. Arg: title as string
			    2. Arg: URL as string or could also be null
			 */
			eb.setTitle("Title", null);

			/*
			    Set the color
			 */
			eb.setColor(Color.red);
			eb.setColor(new Color(0xF40C0C));
			eb.setColor(new Color(255, 0, 54));

			/*
			    Set the text of the Embed:
			    Arg: text as string
			 */
			eb.setDescription("Text");

			/*
			    Add fields to embed:
			    1. Arg: title as string
			    2. Arg: text as string
			    3. Arg: inline mode true / false
			 */
			eb.addField("Title of field", "test of field", false);

			/*
			    Add spacer like field
			    Arg: inline mode true / false
			 */
			eb.addBlankField(false);

			/*
			    Add embed author:
			    1. Arg: name as string
			    2. Arg: url as string (can be null)
			    3. Arg: icon url as string (can be null)
			 */
			eb.setAuthor("name", null, "https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/zekroBot_Logo_-_round_small.png");

			/*
			    Set footer:
			    1. Arg: text as string
			    2. icon url as string (can be null)
			 */
			eb.setFooter("Text", "https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/zekroBot_Logo_-_round_small.png");

			/*
			    Set image:
			    Arg: image url as string
			 */
			eb.setImage("https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/logo%20-%20title.png");

			/*
			    Set thumbnail image:
			    Arg: image url as string
			 */
			eb.setThumbnail("https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/logo%20-%20title.png");
			
			event.getChannel().sendMessageEmbeds(eb.build()).queue();
			event.getChannel().sendMessage("Embed").queue();
		}
	}
}