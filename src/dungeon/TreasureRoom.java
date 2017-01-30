package dungeon;

import java.util.Random;
import java.util.Scanner;

class TreasureRoom extends Room {

	boolean visited = false;

	TreasureRoom(String name) {
		super(name);
	}

	@Override
	Player event(Player player) {
		if (visited) {
			return showDoors(player);
		} else {
			Scanner input = new Scanner(System.in);
			InputHandler handler = new InputHandler();
			Random ran = new Random();

			// creating variables that are needed at the puzzle
			int puzzleX = ran.nextInt(7) + 4;
			int puzzleY = ran.nextInt(7) + 4;
			int puzzleZ = ran.nextInt(8) + 2;

			// question and user feedback
			System.out.println(
					"\nAn old man asks you a question:\nIf I have " + puzzleX + " stacks of coins, every stack has "
							+ puzzleY + " coins and I give away " + puzzleZ + " coins. How much coins do I have left?");
			if ((puzzleX * puzzleY) - puzzleZ == handler.getInt(input)) {
				player.coins += puzzleZ;
				System.out.println("\nThat answer is correct!\nThe old man gives you " + puzzleZ + " coins!");
			} else {
				int damage = ran.nextInt(5);
				player.health -= damage;
				System.out.println(
						"\nThat answer is wrong!\nThe old man kicks you before he dissapears in the shadows.\nYou lose "
								+ damage + " health.");
			}

			visited = true;

			// clause when player dies
			if (player.health <= 0) {
				return player;
			} else {
				this.type = "empty treasure room";
				return showDoors(player);
			}
		}
	}
}