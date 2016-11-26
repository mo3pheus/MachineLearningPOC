package org.sanket.codingGym.SwingSet;

import java.awt.Color;

import javax.swing.*;

public class WindowMaker {

	/**
	 * 
	 * @param args
	 * 
	 *            Notes: This is going to be very interesting! You need to
	 *            define the environment and the walls and the boundaries and
	 *            the reward cells and then watch your program learn!
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JButton button = new JButton("clickMe");
		CustomListener listener = new CustomListener(button);
		button.setForeground(new Color(255, 0, 0));
		button.setSize(40, 20);
		button.addMouseListener(listener);
		frame.getContentPane().add(button);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 400);
		frame.setVisible(true);
	}

	public static void changeIt(JButton button) {
		button.setText("I am changed!");
	}
}
