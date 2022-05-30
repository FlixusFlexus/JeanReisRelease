package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class BotStartup {
	public static JDA shardMan;
	public static JDABuilder builder;
	public static  String prefix = "-";
	public static String[] commandlist;
	public static String[] commanddescript;
	public static void main(String[] args) throws LoginException, IOException {
		
		builder = JDABuilder.createDefault("TOKEN HIER EINFÜGEN");

		builder.setActivity(Activity.playing(" 🎵 Musik 🎶"));
		builder.setStatus(OnlineStatus.ONLINE);
		
		builder.addEventListeners(new Main.Commands.Muenze());
		builder.addEventListeners(new Main.Commands.Test());
		builder.addEventListeners(new Main.Commands.wetter());
		builder.addEventListeners(new Main.Commands.ueber());
		builder.addEventListeners(new Main.Commands.MakeSelection());
		builder.addEventListeners(new Main.MusicCommands.joinvc());
		builder.addEventListeners(new Main.MusicCommands.play());
		//builder.addEventListeners(new Main.debug.debug());
		//builder.addEventListeners(new Main.MusicCommands.lyrics());
		//builder.addEventListeners(new Main.MusicCommands.playlist());
		
		shardMan = builder.build();
		
		System.out.println("[Bot] Online");
		
		stop();
		
	}
	
	//Reset-Timer start
	
	
	public static void stop() throws IOException  {
		while (true) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			if (reader.readLine().equals(("stop"))) {
				
				reader.close();
				builder.setStatus(OnlineStatus.OFFLINE);
				shardMan.shutdown();
				System.out.println("[Bot] Offline ");
			}
			if (reader.readLine().equals(("server"))) {
				System.out.println("[Bot][DEBUG] Aktuelle Server: ");
				
				System.out.println(shardMan.getGuilds());
				
				System.out.println("--Ende der Serveraufzählung--");
			}
			if (reader.readLine().equals(("ping"))) {
				System.out.print("[Bot][DEBUG]");
				System.out.println(" Pong");
			}
		}
	}







	
}
