package dungeon;

import java.util.InputMismatchException;
import java.util.Scanner;

class InputHandler {

	// lets the user only insert an integer
	int getInt(Scanner input) {
		int s = 0;
		boolean loop = true;
		while (loop) {
			try {
				s = (int) input.nextInt();
				loop = false;
			} catch (InputMismatchException e) {
				System.out.println("Please enter a number");
				input = new Scanner(System.in);
			}
		}
		return s;
	}

	void getAnything(Scanner input) {
		System.out.println("Insert anything to continue");
		input.next();
	}
}