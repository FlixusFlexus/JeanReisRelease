package Main.Commands;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Test extends ListenerAdapter{
	
	
		public void onMessageReceived(MessageReceivedEvent event) {
		String prefix = "-";
			String[] command = event.getMessage().getContentRaw().split(" ");
						
			if (command[0].equalsIgnoreCase(prefix + "ping")) {
				//event.getMessage().reply("pong").queue();	
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.magenta);
				
				eb.setTitle("pong");
				event.getChannel().sendMessageEmbeds(eb.build()).queue();
				System.out.println("[Bot][Command] Ping → Pong");
			}
}
}