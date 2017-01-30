package dungeon;

class Item {

	String name;
	int minDamage;
	int maxDamage;
	int maxHealth;
	int health;

	Item(String name, int minDamage, int maxDamage, int maxHealth, int health) {
		this.name = name;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.maxHealth = maxHealth;
		this.health = health;
	}
}