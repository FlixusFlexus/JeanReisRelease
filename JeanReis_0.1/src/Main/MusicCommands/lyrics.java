package Main.MusicCommands;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class lyrics extends ListenerAdapter{
	
	
	public void onMessageReceived(MessageReceivedEvent event) {
		
		String[] command = event.getMessage().getContentRaw().split(" ",3);
		
		
	
		if (command[0].equalsIgnoreCase(Main.BotStartup.prefix + "lyrics") && command.length == 3) {
			System.out.println("command0:" +command[0]);
			System.out.println("command1:" +command[1]);
			System.out.println("command2:" +command[2]);

			String Songinfo = null;
			String Titel = command[2];
			String Kuenstler= command[1];
			Kuenstler = Kuenstler.replace(" ", "%20");
			Titel = Titel.replace(" ", "%20");
			String Songlink = "https://www.stands4.com/services/v2/lyrics.php?uid=10299&tokenid=VNMbR7B6vnru1nBA&term=" + Titel + "&artist=" + Kuenstler + "&format=json";
			System.out.println("SongLink: " + Songlink);
			
			download(Songlink, "aktuelleSonginfo.json");
			reader();
			
				System.out.println("Songinfo:" + Songinfo);

		
		
		
		}}
	
	
	@SuppressWarnings("unchecked")
	public static void reader() {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("/home/felix/Dokumente/IT/JeanReisAlphaV2/test.json"));
			System.out.println("Datei gefunden!");
			System.out.println("Object: " + obj);
			
			JSONObject jsonObject = (JSONObject) obj;
 			JSONArray songlink = (JSONArray) jsonObject.get("song-link");
 			System.out.println("JSONobj" + jsonObject);

			Iterator<JSONObject> iterator = songlink.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void download(String address, String localFileName) {
        OutputStream out = null;
        URLConnection conn = null;
        InputStream  in = null;
        try {
                URL url = new URL(address);
                out = new BufferedOutputStream(
                        new FileOutputStream(localFileName));
                conn = url.openConnection();
                in = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int numRead;
                long numWritten = 0;
                while ((numRead = in.read(buffer)) != -1)
                {
                        out.write(buffer, 0, numRead);
                        numWritten += numRead;
                        //System.out.println(buffer.length);
                        //System.out.println(" " + buffer.hashCode());
                }
               System.out.println(localFileName + "\t" + numWritten);
        } catch (Exception exception) {exception.printStackTrace();} finally {
                try {
                        if (in != null) {
                                in.close();
                        }
                        if (out != null) {
                                out.close();
                        }
                } catch (IOException ioe) {}
        }
}

}