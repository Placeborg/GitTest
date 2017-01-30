package dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player {

	String name;
	int coins;
	List<Item> items;
	Location loc;
	int maxHealth;
	int health;
	int minDamage;
	int maxDamage;
	boolean inPlayMenu;

	Player(String name) {
		this.name = name;
		inPlayMenu = false;
		this.coins = 0;
		this.items = new ArrayList<Item>();
		this.maxHealth = 100;
		this.health = 100;
		this.minDamage = 3;
		this.maxDamage = 10;
	}

	void menu() {
		Scanner input = new Scanner(System.in);
		InputHandler handler = new InputHandler();
		boolean loop = true;
		while (loop) {
			System.out.println(
					"\nInsert a menu item for more information.\nPress 1 for your inventory.\nPress 2 for your health\nPress 3 to continue exploring.");
			switch (handler.getInt(input)) {
			case 1:
				System.out.println("\nYou currently have " + this.coins + " coins.");
				handler.getAnything(input);
				break;
			case 2:
				System.out.println("\nYour health is: " + health + "/" + maxHealth);
				handler.getAnything(input);
				break;
			case 3:
				loop = false;
				break;
			default:
				System.out.println("Please insert a valid option.");
				break;
			}
		}
		this.inPlayMenu = false;
	}
}