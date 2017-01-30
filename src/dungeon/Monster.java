package dungeon;

import java.util.Random;
import java.util.Scanner;

class Monster {

	String name;
	int maxHealth;
	int health;
	int minDamage;
	int maxDamage;
	int minCoins;
	int maxCoins;

	Monster() {
	}

	Monster(String name, int maxHealth, int minDamage, int maxDamage, int minCoins, int maxCoins) {
		this.name = name;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.minCoins = minCoins;
		this.maxCoins = maxCoins;
	}

	Player encounter(Player player) {

		Scanner input = new Scanner(System.in);
		InputHandler handler = new InputHandler();
		Random ran = new Random();
		boolean inFight = true;
		int damageReceived;
		int damageDone;

		System.out.println("\nYou spot a " + this.name + ".");

		while (inFight) {
			System.out.println("\nWill you:\n1: attack\n2: run away\n3: view your character menu");
			switch (handler.getInt(input)) {
			case 1:
				damageDone = ran.nextInt(player.maxDamage - player.minDamage) + player.minDamage + 1;
				damageReceived = ran.nextInt(this.maxDamage - this.minDamage) + this.minDamage + 1;
				player.health -= damageReceived;
				this.health -= damageDone;
				System.out.println("\nYou attack the " + this.name + " and do " + damageDone + " damage.\nThe "
						+ this.name + " fights back and does " + damageReceived + " damage.");
				break;
			case 2:
				if (ran.nextBoolean()) {
					damageReceived = ran.nextInt(this.maxDamage - this.minDamage) + (minDamage * 2) + 1;
					System.out.println("\nYou run away, but the " + this.name
							+ " manages to hurt you while doing so.\nYou lose " + damageReceived + " health.");
					if (player.health <= 0) {
						inFight = false;
					} else {
						System.out.println("When you get back to the room the " + this.name + " is gone.");
					}
				} else {
					System.out.println("\nYou manage to get away safely\nWhen you get back to the room the " + this.name
							+ " is gone.");
				}
				inFight = false;
				break;
			case 3:
				player.menu();
				System.out.println("The " + this.name + "looks angry at you.");
				break;
			default:
				break;
			}

			if (player.health <= 0) {
				inFight = false;
			} else if (this.health <= 0) {
				int monsterCoins = ran.nextInt(this.maxCoins - this.minCoins) + this.minCoins + 1;
				player.coins += monsterCoins;
				System.out.println(
						"\nYou killed the " + this.name + "." + "\nYou find " + monsterCoins + " coins on the corpse.");
				inFight = false;
			}
		}
		return player;
	}
}