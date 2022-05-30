package Main.MusicCommands;

import java.util.Timer;

public class Timeout{
	public static int count;
	public static Timer VCReset = new Timer();
	public static boolean doTimeout = false;
	public static int getCount() {
		return count;
	}
	public static Timer getVCReset() {
		return VCReset;
	}
	public static boolean getDoTimeout() {
		return doTimeout;
	}
	public static void time_main(){
	if (count >= 30) {
		count = 0;
		doTimeout = true;
	}
	}

	
	}