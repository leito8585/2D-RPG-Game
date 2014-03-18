package com.leito.game.gfx;

public class Font {

	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      "
			+ "0123456789.,:;'\"!?$%()-=+/      ";
	
	 public static void render(String msg, Screen screen, int x, int y){
		 msg = msg.toUpperCase();
		 
		 for(int i = 0; i < msg.length(); i++){
			 int charInadex = chars.indexOf(msg.charAt(i));
			 //if(charInadex >= 0) screen.render(x + (i * 8), y, (charInadex / 2) + 15 * 16 , false, false);
		 }
	 }
}
