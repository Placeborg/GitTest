package dungeon;

import java.util.Random;

class MonsterRoom extends Room {

	boolean visited = false;

	MonsterRoom(String name) {
		super(name);
	}

	@Override
	Player event(Player player) {
		if (visited) {
			return showDoors(player);
		} else {
			Random ran = new Random();
			Monster enemy = new Monster();

			// create monster
			switch (ran.nextInt(3)) {
			case 0:
				enemy = new Monster("giant rat", 15, 1, 3, 1, 3);
				break;
			case 1:
				enemy = new Monster("goblin", 25, 2, 5, 3, 6);
				break;
			case 2:
				enemy = ran.nextInt(3) == 0 ? new Monster("vampire", 40, 2, 8, 10, 20) : new Monster();
			default:
				break;
			}

			if (enemy.name == null) {
				System.out.println("You feel something is off, but nothing happens.");
			} else {
				enemy.encounter(player);
			}

			visited = true;

			// clause when player dies
			if (player.health <= 0) {
				return player;
			} else {
				this.type = "room";
				return showDoors(player);
			}
		}
	}
}