package Main.MusicCommands;

import java.awt.Color;
import java.io.IOException;
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



public class play extends ListenerAdapter{

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
	  
	  public play() {
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
	
	
    if (command[0].equalsIgnoreCase(Main.BotStartup.prefix + "play") && command.length == 2) {
    	
    	
			GuildVoiceState vc1 = event.getMember().getVoiceState();
	
			if (vc1 != null) {
				AudioManager audioManager = event.getGuild().getAudioManager();
					
				if (!audioManager.isConnected()) {
						audioManager.openAudioConnection(vc1.getChannel());
						current = audioManager;
						VCReset.scheduleAtFixedRate(new TimerTask() {
							@Override
							public void run() {
							
							
								count++;
								//System.out.println("Anzahl" + count);
							}
						}, 1*1000, 1*1000); //jede Sekunde wird der Part in "run" ausgeführt
						
						//Timer reseten
					}
			}
    	
    	
			if (isValidURL(command[1]) == true){
				load(event.getTextChannel(), command[1]);
				//System.out.println("Load and play " + command[1]);
			}
			else 
			if (isValidURL(command[1]) == false) {
				search(event.getTextChannel(), command[1]);
				//System.out.println("search and play " + command[1]);
			}

	
    	
      
    
    
    	}
    

    
    

    	if (command[0].equalsIgnoreCase(Main.BotStartup.prefix + "skip") && command.length == 2) {
    		skipTrack(event.getTextChannel());
    		if(command[1] != null) {
    			String temp = command[1];
    			//System.out.println("temp " + temp);
    			int skips = convert(temp);
    			
    			for(int i = skips; i < 0; i++)
    				skipTrack(event.getTextChannel());
    	}
    		else if(command[1] == null) {
    			skipTrack(event.getTextChannel());
    			//System.out.println("einzelner Skip");
    		}
    	}	
    	
    	if (command[0].equalsIgnoreCase(Main.BotStartup.prefix + "pause")) {
    		pauseTrack(event.getTextChannel());
    	}
    	if (command[0].equalsIgnoreCase(Main.BotStartup.prefix + "stop")) {
    		stopTrack(event.getTextChannel());
    	}
    	
	}
	  public static boolean isValidURL(String urlString) {
		    try {
		        URL url = new URL(urlString);
		        url.toURI();
		        return true;
		    } catch (Exception e) {
		        return false;
		    }
		}
	  public void search(final TextChannel channel, final String Titel) {
			
			String url = null;  
		    
		    url = "ytsearch:" + Titel;
		    
		    
		    load(channel, url);
		}
	  public void load(final TextChannel channel, final String trackUrl) {
		  
		  
		  
		    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
		    
		    
		    playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
		      @Override
		      public void trackLoaded(AudioTrack track) {
		        //channel.sendMessage(track.getInfo().title + " von " + track.getInfo().author + " wird zur Warteschlange hinzugefügt").queue();
		        
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.orange);
				eb.setTitle("Lied zur Warteschlange hinzugefügt");
				eb.setDescription(track.getInfo().title + " von " + track.getInfo().author);
				channel.sendMessageEmbeds(eb.build()).queue();
		        
		        System.out.println("[Bot][Command]  " + track.getInfo().title + " von " + track.getInfo().author + " wird zur Warteschlange hinzugefügt");
		        Date cur = new Date();
		        // In logfile schreiben
		        String text
		            = cur+": " + track.getInfo().title + " von " + track.getInfo().author + " wurde zur Warteschlange hinzugefügt. \n";
		        int c = 0;
		        Path fileName = Path.of(
		            "/home/pi/JeanReis/songlog\"+c+\".txt");
		        	//Verzeichniss pi:   /home/pi/JeanReis/songlog"+c+".txt
		        	//Verzeichniss Linux PC   /media/felix/Data/felix/Dokumente/IT/JeanReisAlphaV2/songlog" +c+ ".txt
		        	//Verzeichniss Windows PC C://songlog"+c+".txt
		        c++;
		        try {
					Files.writeString(fileName, text);
				} catch (IOException e) {
										e.printStackTrace();
				}
		        String file_content;
				try {
					file_content = Files.readString(fileName);
					System.out.println(file_content);
				} catch (IOException e) {
										e.printStackTrace();
				}
		        
				
		        play(channel.getGuild(), musicManager, track);
		      }

			@Override
		      public void noMatches() {//Wenn das Lied nicht verügbar ist
		        //channel.sendMessage("an Stelle " + trackUrl + " wurde nichts gefunden").queue();
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.orange);
				eb.setTitle("an Stelle " + trackUrl + " wurde nichts gefunden");
				//eb.addField("an Stelle \" + trackUrl + \" wurde nichts gefunden","", false);
				channel.sendMessageEmbeds(eb.build()).queue();
		        
		      }

		      @Override
		      public void loadFailed(FriendlyException exception) {//WEnn es einen Fehler beim laden des Liedes gab
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(Color.orange);
					eb.setTitle("Es ist leider ein Fehler aufgetreten. ");
					//eb.addField("Es ist leider ein Fehler aufgetreten. ","", false);
					channel.sendMessageEmbeds(eb.build()).queue();
		        //channel.sendMessage("Ich kann dieses Lied leider nicht abspielen, da: " + exception.getMessage()).queue();
		      }

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				//System.out.println("Playlist gefunden");
				AudioTrack firstTrack = playlist.getSelectedTrack();

		        if (firstTrack == null) {
		          firstTrack = playlist.getTracks().get(0);
		        }
		        
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.orange);
				eb.setTitle("Lied zur Warteschlange hinzugefügt");
				eb.setDescription(firstTrack.getInfo().title + " von " + firstTrack.getInfo().author);
				//eb.addField("Zur Warteschlange hinzugefügt", firstTrack.getInfo().title + " von " + firstTrack.getInfo().author , false);
				channel.sendMessageEmbeds(eb.build()).queue();
				
		        //channel.sendMessage(firstTrack.getInfo().title + " von " + firstTrack.getInfo().author + " wurde zur Warteschlange hinzugefügt").queue();
		        
		        for(int i = 0; i < 25; i++) {
		        firstTrack = playlist.getTracks().get(i);
		        play(channel.getGuild(), musicManager, firstTrack);
		        //System.out.println("in for Schleife: " + i);
		        }
			}
		    });
		    //return(channel.getGuild(), musicManager, track);
		  }
	  private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {

		  musicManager.scheduler.queue(track);
		
	}
	  private void skipTrack(TextChannel channel) {
		    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
		    musicManager.scheduler.nextTrack();
		    
		    
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(Color.orange);
			eb.setTitle("Lied übersprungen");
			channel.sendMessageEmbeds(eb.build()).queue();
			
			
		    //channel.sendMessage("Lied übersprungen").queue();
		  }
	  private void pauseTrack(TextChannel channel) {
		    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
		    
		    if(musicManager.player.isPaused()) {
		    	musicManager.player.setPaused(false);
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.orange);
				eb.setTitle("nicht mehr pausiert");
				channel.sendMessageEmbeds(eb.build()).queue();
		    }
		    else if (!musicManager.player.isPaused()) {
		    	musicManager.player.setPaused(true);
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.orange);
				eb.setTitle("pausiert");
				channel.sendMessageEmbeds(eb.build()).queue();
		    }
		    

		    
		    //channel.sendMessage("Lied übersprungen").queue();
		  }
	  private void stopTrack(TextChannel channel) {
		  GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
		  
		  if(musicManager.player.isPaused()) {
		  	musicManager.player.setPaused(false);
		  }
		  else if (!musicManager.player.isPaused()) {
		  	musicManager.player.stopTrack();
		  }
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(Color.orange);
			eb.setTitle("Gestoppt");
			channel.sendMessageEmbeds(eb.build()).queue();
		  //channel.sendMessage("Lied übersprungen").queue();
		}
	  public static int convert(String str) {
	        int val = 0;
	        //System.out.println("String = " + str);
	  
	        	        try {
	            val = Integer.parseInt(str);
	        }
	        catch (NumberFormatException e) {

	            //System.out.println("Invalid String");
	        }
	        return val;
	   }
	  
	 
}
