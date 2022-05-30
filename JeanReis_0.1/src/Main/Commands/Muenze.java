package Main.Commands;

import java.awt.Color;
import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Muenze extends ListenerAdapter{

	
		public void onMessageReceived(MessageReceivedEvent event) {
		String prefix = "-";
			String[] command = event.getMessage().getContentRaw().split(" ");
			
			if (command[0].equalsIgnoreCase(prefix + "Münze")) {
				String M = null;
				
				Random randomGenerator = new Random();
			   //for (int idx = 1; idx <= 10; ++idx)
			      boolean randomBool = randomGenerator.nextBoolean();
			      System.out.println("[Bot][Command] Münze generiert: " + randomBool);
				if (randomBool == true) {
					M = "Kopf";
				}
				else if (randomBool == false){
					M = "Zahl";
				}
				
				
				
				//event.getChannel().sendMessage("Ich habe " + M).queue();
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.magenta);
				eb.setTitle("Ergebnis");
				eb.setDescription("Ich habe " + M);
				//eb.addField("Ergebnis", "Ich habe " + M, false);
				event.getChannel().sendMessageEmbeds(eb.build()).queue();
		
				}
}
}