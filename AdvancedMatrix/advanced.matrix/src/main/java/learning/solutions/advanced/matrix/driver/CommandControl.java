package learning.solutions.advanced.matrix.driver;

import java.util.Scanner;

import learning.solutions.advanced.matrix.kafka.MatrixController;
import learning.solutions.advanced.matrix.kafka.MatrixController.KafkaShipmentBuilder;

public class CommandControl {
	public static void main(String[] args) throws Exception {
		String path = CommandControl.class.getResource("/kafka.properties").getPath();
		KafkaShipmentBuilder builder = new KafkaShipmentBuilder().withPropertyFileAt(path)
				.withSourceTopic("nebuchadnezzar.main.deck.com.0").build();
		MatrixController zionComs = new MatrixController(builder);

		int choice = 0;
		while (choice != 1) {
			System.out.println("0. Send Message");
			System.out.println("1. Exit");
			System.out.println("Please enter your choice");
			Scanner scanner = new Scanner(System.in);
			choice = scanner.nextInt();

			switch (choice) {
			case 0: {
				zionComs.sendMessage();
			}
				;
				break;
			case 1: {
				scanner.close();
				System.out.println("This is Zion coms signing off.");
			}
				;
				break;
			default: {
			}
				;
			}
		}
	}

}
