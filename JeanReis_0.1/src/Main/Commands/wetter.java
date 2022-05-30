
package Main.Commands;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;



public class wetter extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent event) {
	
		int Temperaturint = 0;
		String Ort 			= "Error";
		String Temperatur			= "Error";


		
		String[] command = event.getMessage().getContentRaw().split(" ",2);
		
		

		
		
		
		if (command[0].equalsIgnoreCase(Main.BotStartup.prefix + "wetter") && command.length == 2) {
			
			Ort = command[1];
		

			String tempo = "https://api.openweathermap.org/data/2.5/weather?q=" + Ort + "&appid=7b84cb052e821a3c097be242cb3a23dc";

////////////////////////////////////////////////Link ausgeben////////////////////////////////////////////////////////////////////////////////
			URL url = null;
			try {
				url = new URL(tempo);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		    InputStream is = null;
			try {
				is = url.openConnection().getInputStream();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}

		    BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );

		    String Tempps = null;
			

		    try {
		    	
				while( (  Tempps = reader.readLine() ) != null )  {
					Tempps.split(",")[0].split(":");
					

					
					//System.out.println();
					
					
					
///////////////////////////////////////Temperatur finden///////////////////////////////////////////////////////////////////////////////				    
			//System.out.println(Tempps + " Vorhersage");
			String[] Tempout = Tempps.split("");
		
			int intIndexT = Tempps.indexOf("temp");
			//System.out.println("Index " + intIndexT);
			intIndexT = intIndexT + 6 /* 3 Zeichen bis zum eigentlichen Wert + 3 Temperaturwerte*/;
			//System.out.println("Index " + intIndexT);
			
				Temperatur ="";
				
				
				for (int i = intIndexT; i< intIndexT + 3
						//Wert besitzt immer 6 Zeichen
						; i++){  
	
					//System.out.println("Tempout " + Tempout[i]);
					Temperatur = "" + Temperatur + Tempout[i];	
				}
		Temperaturint = Integer.parseInt(Temperatur);
		
//////////////////////TODO exakte Werte!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		//System.out.println(Temperaturint);
		Temperaturint = Temperaturint - 273;
		
		//System.out.println(Temperaturint);
		Temperatur = "" + Temperaturint;
				


///////////////////////////////////////Wolken Finden///////////////////////////////////////////////////////////////////////////////	

///////////////////////////////////////Regen finden////////////////////////////////////////////////////////////////////////////////		



				}	    
					
	
				}
				
		    catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
			}
		    

			
			
			try {													//Reader Schlie�en
				reader.close();
		} 
		catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
		}			
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			System.out.println("[Bot][Command] Wettervorhersage für " + Ort + " generiert, abrufbar hier: https://api.openweathermap.org/data/2.5/weather?q=" + Ort + "&appid=7b84cb052e821a3c097be242cb3a23dc");
///////////////////////////////////////////////Werte übersetzen///////////////////////////////////////////////////////////////////
			//Ort -> String Ort
			//Temperatur -> Temperatur
			//http://api.openweathermap.org/data/2.5/weather?q=Ort&appid=7b84cb052e821a3c097be242cb3a23dc
			
 
		
			EmbedBuilder eb = new EmbedBuilder();
			eb.setColor(Color.red);
			
			//eb.setImage("https://media.boingboing.net/wp-content/uploads/2014/01/1488301_1423634404536952_1742104483_n.jpg");  //Bild von Stadt COMING SOON
			eb.setTitle("Wettervorhersage");
			eb.addField(Ort,"Die Temperatur beträgt " +Temperatur + "°C", false);
			
			event.getChannel().sendMessageEmbeds(eb.build()).queue();
												
			//event.getChannel().sendMessage("Die Temperatur in " + Ort + " beträgt " + Temperatur + "°C. " ).queue();
    		//System.out.println(command[1]);

		}
	}


}