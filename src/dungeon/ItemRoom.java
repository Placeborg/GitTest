package dungeon;

import java.util.Random;
import java.util.Scanner;

class ItemRoom extends Room {

	boolean visited = false;

	ItemRoom(String name) {
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
			boolean menu = true;
			System.out.println("In the middle of the room is a large chest.");
			while (menu) {
				System.out.println("\nWhat will you do?\n1: Open the chest\n2: Leave the chest and continue");
				switch (handler.getInt(input)) {
				case 1:
					switch (ran.nextInt(3)) {
					case 0:
						int chestCoins = ran.nextInt(11) + 5;
						player.coins += chestCoins;
						System.out.println("\nYou find " + chestCoins + " coins in the chest.");
						menu = false;
						break;
					case 1:
						Item chestItem = new Item("sword", 2, 4, 0, 0);
						System.out.println("\nYou find a " + chestItem.name + ".\nIt improves your damage (by "
								+ chestItem.minDamage + " to " + chestItem.maxDamage + ").");
						if (player.items.size() == 0) {
							System.out.println("You currently do not have another weapon.");
						} else {
							Item currentWeapon = (Item) player.items.get(0);
							System.out.println(
									"Your current weapon is a " + currentWeapon.name + ".\nIt improves your damage (by "
											+ currentWeapon.minDamage + " to " + currentWeapon.maxDamage + ").");

						}

						boolean menuTwo = true;

						while (menuTwo) {

							System.out
									.println("\nWould you like to pick up the " + chestItem.name + "?\n1: Yes\n2: No");
							switch (handler.getInt(input)) {
							case 1:
								if (player.items.size() == 0) {
									player.items.add(chestItem);
								} else {
									player.items.set(0, chestItem);
								}

								player.minDamage += ((Item) player.items.get(0)).minDamage;
								player.maxDamage += ((Item) player.items.get(0)).maxDamage;

								menuTwo = false;
								break;
							case 2:
								menuTwo = false;
								break;
							default:
								System.out.println("Please insert a valid option.");
								break;
							}
						}
						menu = false;
						break;
					case 2:
						System.out.println("\nWhen you try to open the chest it comes to live and attacks you!");
						Monster enemy = new Monster("chest", 20, 2, 4, 10, 20);
						enemy.encounter(player);
						menu = false;
						break;
					case 3:
						System.out.println("\nThe chest appears to be empty.");
						menu = false;
					default:
						break;
					}
					break;
				case 2:
					if (ran.nextBoolean()) {
						System.out.println("\nWhen you walk away the chest comes to live and attacks you!");
						Monster enemy = new Monster("chest", 20, 2, 4, 10, 20);
						enemy.encounter(player);
					} else {
						System.out.println("\nYou leave the chest and look around the room.");
					}
					menu = false;
					break;
				default:
					break;
				}
			}
			visited = true;
			this.type = "room";
			return showDoors(player);
		}
	}
}