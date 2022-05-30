package Main.Commands;

import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;


public class ueber extends ListenerAdapter{
	
	
	
		public void onMessageReceived(MessageReceivedEvent event) {

			String[] command = event.getMessage().getContentRaw().split(" ");
			
			if (command[0].equalsIgnoreCase(Main.BotStartup.prefix + "über")) {
				//event.getChannel().sendMessage("über mich").setActionRow(aboutmeSmu()).queue();
				
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.GREEN);
				eb.setTitle("Über mich");
				eb.setDescription("Wähle aus was du wissen willst");
				//eb.addField("Über mich","Wähle aus was du wissen willst", false);
				
				event.getChannel().sendMessageEmbeds(eb.build()).setActionRow(aboutmeSmu()).queue();
				
			}	
		}
		private static SelectionMenu aboutmeSmu() {
			return SelectionMenu.create("über")//Name des Menüs
				.setPlaceholder("Wähle eine Option")//wird angezeigt wenn keine Option ausgewählt ist
				.addOption("Entwickler", "developer", "klicke um dir die Ersteller anzeigen zu lassen ")//1. Auswahlmöglichkeit, Entwickler
				.addOption("Hintergrund", "background",  "klicke um dir anzeigen zu lassen warum wir diesen Bot erstellt haben")//2. Auswahlmöglichkeit, Hintergrund
				.addOption("Befehle", "commands", "klicke um dir eine Liste an Befehlen anzeigen zu lassen")
				.addOption("Hilfe", "help", "Was tue ich bei Problemen")
				.addOption("Neue Funktionen", "new","Neue Features des letzten Updates")
				.setRequiredRange(1, 1)//Ausgewählt werden können min. 1 Optin, max. 1 Option
				.build();
		}

}
