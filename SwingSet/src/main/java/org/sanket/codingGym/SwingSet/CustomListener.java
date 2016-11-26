package org.sanket.codingGym.SwingSet;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class CustomListener implements MouseListener {

	private static final String[]	NAMES	= { "Hello World", "Chalo World" };
	private JButton					button	= null;
	private int						flip	= 0;

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		button.setText(NAMES[flip]);
		flip = (flip == 0) ? 1 : 0;
		// System.out.println("Hello World");
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public CustomListener(JButton button) {
		this.button = button;
	}

}
