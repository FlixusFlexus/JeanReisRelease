package Main.MusicCommands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

public class joinvc extends ListenerAdapter{
	AudioManager current;
	Timer VCReset = Main.MusicCommands.Timeout.getVCReset();
	int count = Main.MusicCommands.Timeout.getCount();
	boolean doTimeout = Timeout.getDoTimeout();
//	public void Timeout() {
//		if(doTimeout == true) {
//			current.closeAudioConnection();
//		}
//	}
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] command = event.getMessage().getContentRaw().split(" ",2);
		
		
		if(command[0].equalsIgnoreCase(Main.BotStartup.prefix + "join")) {

			
			GuildVoiceState vc1 = event.getMember().getVoiceState();
			
			
			if (vc1 != null) {
				AudioManager audioManager = event.getGuild().getAudioManager();
					
				if (!audioManager.isConnected()) {
						audioManager.openAudioConnection(vc1.getChannel());
						current = audioManager;
						//Timer starten
						EmbedBuilder eb = new EmbedBuilder();
						eb.setColor(Color.orange);
						eb.setTitle("Sprachkanal beigetreten");
						eb.addField("Verbunden mit "+ vc1.getChannel().getName(),"", false);
						event.getChannel().sendMessageEmbeds(eb.build()).queue();
						
						//event.getChannel().sendMessage("Verbunden mit " + vc1.getChannel().getName()).queue();
						
							
						VCReset.scheduleAtFixedRate(new TimerTask() {
								@Override
								public void run() {
								
								//System.out.println("Ich bin da");
								count++;
								//System.out.println("Anzahl: " + count);
								}
							}, 1*1000, 1*1000); //jede Sekunde wird der Part in "run" ausgeführt

							
						
				}
			}
			
			else {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.orange);
				eb.addField("Du bist mit keinem Sprachkanal verbunden. ","", false);
				event.getChannel().sendMessageEmbeds(eb.build()).queue();
				//event.getChannel().sendMessage("Du bist mit keinem Sprachkanal verbunden").queue();
			}
			
			
		}
		if(command[0].equalsIgnoreCase(Main.BotStartup.prefix + "leave")) {
			AudioManager audioManager = event.getGuild().getAudioManager();
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(Color.orange);
			eb.setTitle("Sprachkanal verlassen");
			//eb.addField("Sprachkanal verlassen","", false);
			event.getChannel().sendMessageEmbeds(eb.build()).queue();
			VCReset.cancel(); //timer reseten
						count = 0;
				
				audioManager.closeAudioConnection();
		}


	}
	
    public String VCName(AudioChannel audioChannel) {
		
		String VCName = "" + audioChannel;

		VCName = VCName.substring(3,  VCName.length() - 20);
		
		
		return VCName;
	}

}
