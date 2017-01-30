package dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Room {

	String type;
	List<Door> doors = new ArrayList<Door>();

	void type() {
		System.out.println("\nYou are inside a " + this.type + ".");
	}

	public Room() {
		for (int i = 0; i < 6; i++) {
			doors.add(new Door());
		}

	}

	public Room(String type) {
		this.type = type;
		for (int i = 0; i < 6; i++) {
			doors.add(new Door());
		}
	}

	Player event(Player player) {
		// no event, just showing the doors
		return showDoors(player);
	}

	Player showDoors(Player player) {

		Scanner input = new Scanner(System.in);
		InputHandler handler = new InputHandler();
		Location currentLoc = player.loc;
		List<Door> doorMenuList = new ArrayList<Door>();

		// showing the doors that lead out the room
		int choiceE = 0;
		int choiceW = 0;
		int choiceN = 0;
		int choiceS = 0;
		int choiceU = 0;
		int choiceD = 0;

		// DEBUGGING
		// System.out.println("\nYou are at this location\nx:" + currentLoc.x +
		// " y:" + currentLoc.y + " z:" + currentLoc.z);

		// viewing the available doors af the location
		System.out.println("\nYou see these doors:");
		for (int i = 0; i < doors.size(); i++) {
			Door d = (Door) doors.get(i);
			if (d.existence) {
				switch (i) {
				case 0:
					doorMenuList.add(d);
					choiceE = doorMenuList.size();
					System.out.println(choiceE + ": east");
					break;
				case 1:
					doorMenuList.add(d);
					choiceW = doorMenuList.size();
					System.out.println(choiceW + ": west");
					break;
				case 2:
					doorMenuList.add(d);
					choiceN = doorMenuList.size();
					System.out.println(choiceN + ": north");
					break;
				case 3:
					doorMenuList.add(d);
					choiceS = doorMenuList.size();
					System.out.println(choiceS + ": south");
					break;
				case 4:
					doorMenuList.add(d);
					choiceU = doorMenuList.size();
					System.out.println(choiceU + ": up");
					break;
				case 5:
					doorMenuList.add(d);
					choiceD = doorMenuList.size();
					System.out.println(choiceD + ": down");
					break;
				default:
					break;
				}
			}
		}

		// let the player choose a door and move the player
		boolean loop = true;

		while (loop) {
			System.out.println("Insert the number of the door\n(or 0 for your character menu)");
			int s = handler.getInt(input);
			if (s == 1337) {
				loop = false;
				currentLoc.x = -1;
			} else if (s == 0) {
				loop = false;
				player.inPlayMenu = true;
			} else if (s == choiceE) {
				System.out.println("\nYou move east");
				currentLoc.x += 1;
				loop = false;
			} else if (s == choiceW) {
				System.out.println("\nYou move west");
				currentLoc.x += -1;
				loop = false;
			} else if (s == choiceN) {
				System.out.println("\nYou move north");
				currentLoc.y += 1;
				loop = false;
			} else if (s == choiceS) {
				System.out.println("\nYou move south");
				currentLoc.y += -1;
				loop = false;
			} else if (s == choiceU) {
				System.out.println("\nYou move up");
				currentLoc.z += 1;
				loop = false;
			} else if (s == choiceD) {
				System.out.println("\nYou move down");
				currentLoc.z += -1;
				loop = false;
			} else {
				System.out.println("Not a valid option, please select and type a given doornumber.");
			}
		}
		return player;
	}
}