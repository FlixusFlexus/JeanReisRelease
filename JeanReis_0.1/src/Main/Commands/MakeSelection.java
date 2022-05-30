package Main.Commands;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

public class MakeSelection extends ListenerAdapter{////Auswahloptionen der Liste aus Main.Commands.ueber
	
	public void onSelectionMenu(SelectionMenuEvent event) {
		if (event.getComponent().getId().equals("über")) {
			
			for (int i = 0; i < event.getValues().size(); i++) {
				
				switch (event.getValues().get(i)) {
				
				case "developer":
					event.getUser().openPrivateChannel().queue( privateChannel -> {
						EmbedBuilder eb6 = new EmbedBuilder();
						eb6.setColor(Color.cyan);
						eb6.setTitle("Entwickler");
						eb6.setDescription("Erstellt von Hermes#6770 und FlixusFlexus#6446 .");
						event.getChannel().sendMessageEmbeds(eb6.build()).queue();
						//privateChannel.sendMessage("Erstellt von Hermes#6770 und FlixusFlexus#6446 . ").queue();
					});
					break;
					
				case "background":
					event.getUser().openPrivateChannel().queue( privateChannel -> {
						EmbedBuilder eb4 = new EmbedBuilder();
						eb4.setColor(Color.cyan);
						eb4.setTitle("Hintergrund");
						eb4.setDescription("Dieser Bot entstand als Seminarkursprojekt in J1. ");
						event.getChannel().sendMessageEmbeds(eb4.build()).queue();
						//privateChannel.sendMessage("Dieser Bot entstand als Seminarkursprojekt in J1. ").queue();
					});
					break;
				case "commands":
					EmbedBuilder eb3 = new EmbedBuilder();
					eb3.setColor(Color.green);
					
					eb3.setTitle("Befehle");
					eb3.addField(Main.BotStartup.prefix + "**Münze**","→ Wirf eine Münze, gibt entweder Kopf oder Zahl zurück", false);
					eb3.addField(Main.BotStartup.prefix + "**wetter**, *Ort*","→ Gib -wetter und eine Stadt ein um dir das Wetter in dieser Stadt anzeigen zu lassen", false);
					eb3.addField(Main.BotStartup.prefix + "**über**","→ Zeigt Informationen über den Bot an", false);
					eb3.addField(Main.BotStartup.prefix + "**join**","→ Tritt einem Sprachkanal bei. Du musst selbst in diesem Sprachkanal sein", false);
					eb3.addField(Main.BotStartup.prefix + "**leave**","→ Verlässt den momentanen Sprachkanal", false);
					eb3.addField(Main.BotStartup.prefix + "**play** ,*Interpret*,*Titel* oder **-play ** ,*YouTube Video oder Playlistlink*","→ Spielt ein Lied oder eine YouTube Playlist ab", false);
					eb3.addField(Main.BotStartup.prefix + "**skip** *Anzahl Lieder*","→ Überspringt die Anzahl der Lieder nach skip, Beispiel: skip 3 überspringt 3 Lieder", false);
					eb3.addField(Main.BotStartup.prefix + "**pause**","→ Pausiert das momentan laufende Lied *coming soon*", false);
					eb3.addField(Main.BotStartup.prefix + "**stop**","→ Stopt alle Lieder", false);
					
					event.getChannel().sendMessageEmbeds(eb3.build()).queue();
					//	event.getTextChannel().sendMessage("Befehle:").setActionRow(commands()).queue();
					
					break;
				case "help":
					event.getUser().openPrivateChannel().queue( privateChannel -> {
						EmbedBuilder eb1 = new EmbedBuilder();
						eb1.setColor(Color.green);
						eb1.setTitle("Hilfe");
						eb1.setDescription("Versuche den Bot neu zu starten, falls das nicht funktioniert schau bitte erst nach ob dein Problem unter bekannten Problemen aufgelistet ist und kontaktiere dann die Entwickler. ");
						event.getChannel().sendMessageEmbeds(eb1.build()).setActionRow(problems()).queue();
						
					});
					break;
				case "new":
					event.getUser().openPrivateChannel().queue( privateChannel -> {
						EmbedBuilder eb2 = new EmbedBuilder();
						eb2.setColor(Color.CYAN);
						eb2.setTitle("**Neue Features**: ");
						eb2.setDescription(
						"-unter [" + Main.BotStartup.prefix + "über] können Informationen über den Bot eingesehen werden \n" + 
						"-Grafische Überarbeitung mit Embed Nachrichten \n" +
						"-neue Konsolenbefehle \n" + 
						"-Bugfixes \n");
						event.getChannel().sendMessageEmbeds(eb2.build()).queue();
						
					});
					break;
					
				}
				
			}
			//event.reply("Dir wurden alle Optionen Privat geschickt").setEphemeral(true).queue();
			
		}
		
	if (event.getComponent().getId().equals("problems")) {
		for (int i = 0; i < event.getValues().size(); i++) {	
			switch (event.getValues().get(i)) {
			case "vcconnect":
				event.getUser().openPrivateChannel().queue( privateChannel -> {
					EmbedBuilder eb2 = new EmbedBuilder();
					eb2.setColor(Color.darkGray);
					eb2.setTitle("Verbindung fehlgeschlagen: ");
					eb2.setDescription("du musst in dem Channel sein den der Bot betreten soll, stelle außerdem sicher dass er die Berechtigung zum beitreten hat");
					event.getChannel().sendMessageEmbeds(eb2.build()).queue();
					//event.getChannel().sendMessage("du musst in dem Channel sein den der Bot betreten soll, stelle außerdem sicher dass er die Berechtigung zum beitreten hat").queue();
				});
				break;
			case "vcnotplay":
				event.getUser().openPrivateChannel().queue( privateChannel -> {
					EmbedBuilder eb3 = new EmbedBuilder();
					eb3.setColor(Color.darkGray);
					eb3.setTitle("Verbindung fehlgeschlagen: ");
					eb3.setDescription("du musst einen Liedtitel und einen Interpreten oder einen Link zu einen YouTube Video oder einer Playlist eingeben, stelle außerdem sicher dass er die Berechtigung zum Ton wiedergeben hat");
					event.getChannel().sendMessageEmbeds(eb3.build()).queue();
					//event.getChannel().sendMessage("du musst einen Liedtitel und einen Interpreten oder einen Link zu einen YouTube Video oder einer Playlist eingeben, stelle außerdem sicher dass er die Berechtigung zum Ton wiedergeben hat").queue();
				});
				break;
			}
		}
		//event.reply("Dir wurden alle Optionen Privat geschickt").setEphemeral(true).queue();
	}
	}
	
	/*private static SelectionMenu commands() {
		return SelectionMenu.create("commands")//Name des Menüs
			.setPlaceholder("Wähle eine Option")//wird angezeigt wenn keine Option ausgewählt ist
			.addOption("Münze","coin")
			.addOption("Wetter","weather")
			.addOption("über","aboutme")
			.addOption("join","join")
			.addOption("leave","leave")
			.addOption("play","play")
			.addOption("skip", "skip")
			.addOption("pause", "pause")
			.addOption("stop", "stop")
			//.addOption("", "")
	
			.setRequiredRange(1, 9)//Ausgewählt werden können min. 1 Option, max. 9 Option
			.build();
	}
	*/
	private static SelectionMenu problems() {
		return SelectionMenu.create("problems")//Name des Menüs
			.setPlaceholder("Wähle eine Option")//wird angezeigt wenn keine Option ausgewählt ist
			.addOption("Audioprobleme", "vcconnect", "der Bot tritt keinem Sprachkanal bei")
			.addOption("Audioprobleme", "vcnotplay", "der Bot spielt keine Musik")
			.setRequiredRange(1,1)
			.build();

}
	}
