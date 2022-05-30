package Main.MusicCommands;

import java.awt.Color;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Lavaplayer.GuildMusicManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;



public class playlist extends ListenerAdapter{

	  private final AudioPlayerManager playerManager;
	  private final Map<Long, GuildMusicManager> musicManagers;

	  
	  
		AudioManager current;
		Timer VCReset = Main.MusicCommands.Timeout.getVCReset();
		int count = Main.MusicCommands.Timeout.getCount();
		boolean doTimeout = Timeout.getDoTimeout();
//		public void Timeout() {
//			if(doTimeout == true) {
//				current.closeAudioConnection();
//			}
//		}
	  
	  public playlist() {
	    this.musicManagers = new HashMap<>();

	    this.playerManager = new DefaultAudioPlayerManager();
	    AudioSourceManagers.registerRemoteSources(playerManager);
	    AudioSourceManagers.registerLocalSource(playerManager);
	  }
	  public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
	    long guildId = Long.parseLong(guild.getId());
	    GuildMusicManager musicManager = musicManagers.get(guildId);

	    if (musicManager == null) {
	      musicManager = new GuildMusicManager(playerManager);
	      musicManagers.put(guildId, musicManager);
	    }

	    guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

	    return musicManager;
	  }
	  public void onMessageReceived(MessageReceivedEvent event) {
			String[] command = event.getMessage().getContentRaw().split(" ",2);
			
		    if (command[0].equalsIgnoreCase(Main.BotStartup.prefix + "playlist") && command.length == 2) {
		    	System.out.println("Command abgerufen");
		    	
					GuildVoiceState vc1 = event.getMember().getVoiceState();
			
					if (vc1 != null) {
						AudioManager audioManager = event.getGuild().getAudioManager();
							
						if (!audioManager.isConnected()) {
								audioManager.openAudioConnection(vc1.getChannel());
								current = audioManager;
								VCReset.scheduleAtFixedRate(new TimerTask() {
									public void run() {
									
									
										count++;
										//System.out.println("Anzahl" + count);
									}
								}, 1*1000, 1*1000); //jede Sekunde wird der Part in "run" ausgeführt
								
								//Timer reseten
							
						
					    GuildMusicManager musicManager = getGuildAudioPlayer(event.getTextChannel().getGuild());
					    	
					    	load(event.getTextChannel(), command[1]);
					   /* if (isValidURL(command[1]) == true){
					    	load(event.getTextChannel(), command[1]);
					    }
					    else
							if (isValidURL(command[1]) == false) {
								System.out.println("Error_Playlistundefined");
							}
					     */
						}
						//System.out.println("Command beendet");
						
		    	}} 
			
	  }

			

	  public void load(final TextChannel channel, final String trackUrl) {
		  
		  
		  
		    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
		    
		    
		    playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
		      @Override
		    	public void trackLoaded(AudioTrack track) {
		    	  System.out.println("Track LOADED");
		    	  AudioTrack firstTrack = track;
		      
		    	  if (firstTrack == null) {
			         // firstTrack = playlist.getTracks().get(0);
		  			EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(Color.orange);
					eb.addField(firstTrack.getInfo().title + " von " + firstTrack.getInfo().author + " wurde zur Warteschlange hinzugefügt","", false);
					channel.sendMessageEmbeds(eb.build()).queue();
		    		  
			        //channel.sendMessage(firstTrack.getInfo().title + " von " + firstTrack.getInfo().author + " wurde zur Warteschlange hinzugefügt").queue();
		    	  }
			        for(int i = 0; i < 25; i++) {
			    //    firstTrack = playlist.getTracks().get(i);
			        play(channel.getGuild(), musicManager, firstTrack);
			        //System.out.println("in for Schleife: " + i);
		      }
			        }

			@Override
		      public void noMatches() {//Wenn das Lied nicht verügbar ist
	  			EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.orange);
				eb.addField("an Stelle " + trackUrl + " wurde nichts gefunden","", false);
				channel.sendMessageEmbeds(eb.build()).queue();
		        //channel.sendMessage("an Stelle " + trackUrl + " wurde nichts gefunden").queue();
		        
		      }

		      @Override
		      public void loadFailed(FriendlyException exception) {//WEnn es einen Fehler beim laden des Liedes gab
		  		EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.orange);
				eb.addField("Ich kann dieses Lied leider nicht abspielen, da: " + exception.getMessage(),"", false);
				channel.sendMessageEmbeds(eb.build()).queue();
		        //channel.sendMessage("Ich kann dieses Lied leider nicht abspielen, da: " + exception.getMessage()).queue();
		      }

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				//System.out.println("Playlist LOADED");
				 AudioTrack firstTrack = playlist.getSelectedTrack();

			        if (firstTrack == null) {
			          firstTrack = playlist.getTracks().get(0);
			        }

			        
			        String ausnahme = playlist.getName();
			        String search  = "Search reuslts for:";

			        if ( ausnahme.toLowerCase().indexOf(search.toLowerCase()) == -1 ) {
			  
			        	
			        	
			  			EmbedBuilder eb = new EmbedBuilder();
						eb.setColor(Color.orange);
						eb.addField(firstTrack.getInfo().title + " von " + firstTrack.getInfo().author + " wurde zur Warteschlange hinzugefügt","", false);
						channel.sendMessageEmbeds(eb.build()).queue();
			        //channel.sendMessage(firstTrack.getInfo().title + " von " + firstTrack.getInfo().author + " wurde zur Warteschlange hinzugefügt").queue();
			        
			        //System.out.println(playlist.getName());
			        
			        }
			        
			        else  {
			        	
			        	
			  			EmbedBuilder eb = new EmbedBuilder();
						eb.setColor(Color.orange);
						eb.addField(firstTrack.getInfo().title + " von " + firstTrack.getInfo().author + " , aus der Playlist " + playlist.getName() + " wurde zur Warteschlange hinzugefügt ","", false);
						channel.sendMessageEmbeds(eb.build()).queue();
			        channel.sendMessage(firstTrack.getInfo().title + " von " + firstTrack.getInfo().author + " , aus der Playlist " + playlist.getName() + " wurde zur Warteschlange hinzugefügt ").queue();
			        	
			        //System.out.println(playlist.getName());
			        }
			        
			        

			        play(channel.getGuild(), musicManager, firstTrack);
			      
			}
			

			
		
		    //return(channel.getGuild(), musicManager, track);
		  
	  private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {

		  musicManager.scheduler.queue(track);
		
	}
	  
	 
});}}




