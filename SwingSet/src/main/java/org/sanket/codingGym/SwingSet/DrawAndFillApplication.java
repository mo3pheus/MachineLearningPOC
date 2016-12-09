package org.sanket.codingGym.SwingSet;

import java.awt.*;

public class DrawAndFillApplication{
	private Frame frame = new Frame();
	private Listener listener = new Listener(frame);
	
	public static void main(String[] args) {
		new DrawAndFillApplication();
	}

	public DrawAndFillApplication() {
		//frame = listener.getFrame();
		frame.addWindowListener(listener);
		frame.setVisible(true);
		frame.setSize(600, 600);
		drawShapes(frame.getGraphics());
	}

	private void drawShapes(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.darkGray);
		g2.fillRect(30, 30, 40, 40);
		//g2.drawRect(30, 90, 40, 40);
	}
}