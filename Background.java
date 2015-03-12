package frogger;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @authors Chaitali Gondhalekar, Jingru Zhu
 */
public class Background {

	/**
	 * draws background of the game
	 * @param g
	 */
	public void drawBackground(Graphics g) {// how to pass view? or how to get
											// panel width n height?

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Frogger.getROADLENGTH(), Frogger.getSIDE());
		
		g.setColor(Color.GRAY);
		g.fillRect(0, Frogger.getSIDE(), Frogger.getROADLENGTH(),
				Frogger.getLANE());
		
		g.setColor(Color.YELLOW);
		g.fillRect(0, Frogger.getSIDE() + Frogger.getLANE(),
				Frogger.getROADLENGTH(), Frogger.getDIVIDER());
		
		g.setColor(Color.GRAY);
		g.fillRect(0,
				Frogger.getSIDE() + Frogger.getLANE() + Frogger.getDIVIDER(),
				Frogger.getROADLENGTH(), Frogger.getLANE());
		
		g.setColor(Color.WHITE);
		g.fillRect(0, Frogger.getROADWIDTH()-Frogger.getSIDE(), Frogger.getROADLENGTH(), Frogger.getSIDE());
	}

}
