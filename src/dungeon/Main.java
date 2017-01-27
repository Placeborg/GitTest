package dungeon;

import java.util.List;

import java.util.ArrayList;

import java.util.Random;

import java.util.Scanner;

import java.util.InputMismatchException;


public class Dungeon {

  

 public static void main(String[] args)

 {

  Scanner input = new Scanner(System.in);


  
  //Create World (max. xyz)

  World currentWorld = new World(10,10,10);

  
  

  System.out.println("Please insert your name.");

  Player player = new Player(input.next());

  System.out.println("dirk");
  player.loc = currentWorld.playerLoc;

  

  System.out.println("\n" + player.name + ", you enter a dungeon...");

  

  //let the player navigate the dungeon

  currentWorld.crawlDungeon(player);

 }

}


class Row{

 List<Column> columnList = new ArrayList<Column>();

}


class Column{

  List<Room> floorList = new ArrayList<Room>();

}


class Room{

 String type;

 List<Door> doors = new ArrayList<Door>();

 

 void type(){

  System.out.println("\nYou are inside a " + this.type + ".");

 }

 

 public Room(){

  for(int i = 0; i < 6; i++){

   doors.add(new Door());

  }

 }

 

 public Room(String type){

  this.type = type;

 

  for(int i = 0; i < 6; i++){

   doors.add(new Door());

  }

 }

 

 Player event(Player player){

  //no event, just showing the doors

  return showDoors(player);

 }

 

 Player showDoors(Player player){

  Scanner input = new Scanner(System.in);

  InputHandler handler = new InputHandler();

  Location currentLoc = player.loc;

  List<Door> doorMenuList = new ArrayList<Door>();

  

  //showing the doors that lead out the room

   int choiceE = 0;

   int choiceW = 0;

   int choiceN = 0;

   int choiceS = 0;

   int choiceU = 0;

   int choiceD = 0;

  

    //viewing the available doors af the location

    

    //DEBUGGING

    //System.out.println("\nYou are at this location\nx:" + currentLoc.x + " y:" + currentLoc.y + " z:" + currentLoc.z);

    

    System.out.println("\nYou see these doors:");

    

    for(int i = 0; i < doors.size(); i++){

     Door d = (Door)doors.get(i);

     if(d.existence){

      switch(i){

       case 0:

        doorMenuList.add(d);

        choiceE = doorMenuList.size();

        System.out.println(choiceE + ": east");

        //east = true;

        break;

       case 1:

        doorMenuList.add(d);

        choiceW = doorMenuList.size();

        System.out.println(choiceW + ": west");

        //west = true;

        break;

       case 2:

        doorMenuList.add(d);

        choiceN = doorMenuList.size();

        System.out.println(choiceN + ": north");

        //north = true;

        break;

       case 3:

        doorMenuList.add(d);

        choiceS = doorMenuList.size();

        System.out.println(choiceS + ": south");

        //south = true;

        break;

       case 4:

        doorMenuList.add(d);

        choiceU = doorMenuList.size();

        System.out.println(choiceU + ": up");

        //up = true;

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

    

    //let the player choose a door and move the player

    boolean loop = true;

    while(loop){

     System.out.println("Insert the number of the door\n(or 0 for your character menu)");

     int s = handler.getInt(input);

     

     if(s == 1337){

      loop = false;

      currentLoc.x = -1;

     } else if (s == 0){

      loop = false;

      player.inPlayMenu = true;

     } else if (s == choiceE){

      System.out.println("\nYou move east");

      currentLoc.x += 1;

      loop = false;

     } else if (s == choiceW){

      System.out.println("\nYou move west");

      currentLoc.x += -1;

      loop = false;

     } else if (s == choiceN){

      System.out.println("\nYou move north");

      currentLoc.y += 1;

      loop = false;

     } else if (s == choiceS){

      System.out.println("\nYou move south");

      currentLoc.y += -1;

      loop = false;

     } else if (s == choiceU){

      System.out.println("\nYou move up");

      currentLoc.z += 1;

      loop = false;

     } else if (s == choiceD){

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


class TreasureRoom extends Room{

 boolean visited = false;

 

 TreasureRoom(String name){

  super(name);

 }

 

 @Override

 Player event(Player player){

  Location currentLoc = player.loc;

  if (visited){

   return showDoors(player);

  } else {

   Scanner input = new Scanner(System.in);

   InputHandler handler = new InputHandler();

   Random ran = new Random();

  

   //creating variables that are needed at the puzzle

   int puzzleX = ran.nextInt(7)+4;

   int puzzleY = ran.nextInt(7)+4;

   int puzzleZ = ran.nextInt(8)+2;

   

   //question and user feedback

   System.out.println("\nAn old man asks you a question:\nIf I have " + puzzleX + " stacks of coins, every stack has " + puzzleY + " coins and I give away " + puzzleZ + " coins. How much coins do I have left?");

   if((puzzleX*puzzleY)-puzzleZ == handler.getInt(input)){

    player.coins += puzzleZ;

    System.out.println("\nThat answer is correct!\nThe old man gives you " + puzzleZ + " coins!");

   } else {

    int damage = ran.nextInt(5);

    player.health -= damage;

    System.out.println("\nThat answer is wrong!\nThe old man kicks you before he dissapears in the shadows.\nYou lose " + damage + " health.");

   }

   visited = true;

   

   //clause when player dies

   if(player.health <= 0){

    return player;

   } else {

    this.type = "empty treasure room";

    return showDoors(player);

   }

  }

 }

}


class MonsterRoom extends Room{

 boolean visited = false;

 

 MonsterRoom(String name){

  super(name);

 }

 

 @Override

 Player event(Player player){

  Location currentLoc = player.loc;

  if (visited){

   return showDoors(player);

  } else {

   Scanner input = new Scanner(System.in);

   InputHandler handler = new InputHandler();

   Random ran = new Random();

   Monster enemy = new Monster();

   

   //create monster

   switch(ran.nextInt(3)){

    case 0:

     enemy = new Monster("giant rat", 15, 1, 3, 1, 3);

     break;

    case 1:

     enemy = new Monster("goblin", 25, 2, 5, 3, 6);

     break;

    case 2:

     enemy = ran.nextInt(3)==0 ? new Monster("vampire", 40, 2, 8, 10, 20) : new Monster();

    default:

     break;

   }

   

   if(enemy.name==null){

    System.out.println("You feel something is off, but nothing happens.");

   } else {

    enemy.encounter(player);

   }

   

   visited = true;

   if(player.health <= 0){

    return player;

   } else {

    this.type = "room";

    return showDoors(player);

   }

  }

 }

}


class ItemRoom extends Room{

 boolean visited = false;

 

 ItemRoom(String name){

  super(name);

 }

 

 @Override

 Player event(Player player){

  Location currentLoc = player.loc;

  if (visited){

   return showDoors(player);

  } else {

   Scanner input = new Scanner(System.in);

   InputHandler handler = new InputHandler();

   Random ran = new Random();

   boolean menu = true;

   

   System.out.println("In the middle of the room is a large chest.");

   while(menu){

    System.out.println("\nWhat will you do?\n1: Open the chest\n2: Leave the chest and continue");

    switch(handler.getInt(input)){

     case 1:

      switch(ran.nextInt(3)){

       case 0:

        int chestCoins = ran.nextInt(11)+5;

        player.coins += chestCoins;

        System.out.println("\nYou find " + chestCoins + " coins in the chest.");

        menu = false;

        break;

       case 1:

        Item chestItem = new Item("sword", 2, 4, 0, 0);

        System.out.println("\nYou find a " + chestItem.name + ".\nIt improves your damage (by " + chestItem.minDamage + " to " + chestItem.maxDamage + ").");

        if(player.items.size()==0){

         System.out.println("You currently do not have another weapon.");

        } else {

         Item currentWeapon = (Item)player.items.get(0);

         System.out.println("Your current weapon is a " + currentWeapon.name + ".\nIt improves your damage (by " + currentWeapon.minDamage + " to " + currentWeapon.maxDamage + ").");

        }

        boolean menuTwo = true;

        while(menuTwo){

         System.out.println("\nWould you like to pick up the " + chestItem.name + "?\n1: Yes\n2: No");

         switch(handler.getInt(input)){

          case 1:

           if(player.items.size() == 0){

            player.items.add(chestItem);

           } else {

            player.items.set(0, chestItem);

           }

           player.minDamage += ((Item)player.items.get(0)).minDamage;

           player.maxDamage += ((Item)player.items.get(0)).maxDamage;

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

      if(ran.nextBoolean()){

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


class Door{

 boolean existence;

 String material;

 boolean locked;

 

 public Door(){

  this.existence = false;

 }

 

 public Door(String material, boolean locked){

  this.existence = true;

  this.material = material;

  this.locked = locked;

 }

}


class Player{

 String name;

 int coins;

 List<Item> items;

 Location loc;

 int maxHealth;

 int health;

 int minDamage;

 int maxDamage;

 boolean inPlayMenu;

 

 Player(String name){

  this.name = name;

  inPlayMenu = false;

  this.coins = 0;

  this.items = new ArrayList<Item>();

  this.maxHealth = 100;

  this.health = 100;

  this.minDamage = 3;

  this.maxDamage = 10;

 }

 

 void menu(){

  Scanner input = new Scanner(System.in);

  InputHandler handler = new InputHandler();

  boolean loop = true;

  

  while(loop){

   System.out.println("\nInsert a menu item for more information.\nPress 1 for your inventory.\nPress 2 for your health\nPress 3 to continue exploring.");

   switch(handler.getInt(input)){

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


class InputHandler{

 

 //lets the user only insert an integer

 int getInt(Scanner input){

  int s = 0;

  boolean loop = true;

  

  while(loop){

   try{

    s = (int)input.nextInt();

    loop = false;

   } catch (InputMismatchException e) {

    System.out.println("Please enter a number");

    input = new Scanner(System.in);

   }

  }

  return s;

 }

 

 void getAnything(Scanner input){

  System.out.println("Insert anything to continue");

  input.next();

 }

}


class Monster{

 String name;

 int maxHealth;

 int health;

 int minDamage;

 int maxDamage;

 int minCoins;

 int maxCoins;

 

 Monster(){}

 

 Monster(String name, int maxHealth, int minDamage, int maxDamage, int minCoins, int maxCoins){

  this.name = name;

  this.maxHealth = maxHealth;

  this.health = maxHealth;

  this.minDamage = minDamage;

  this.maxDamage = maxDamage;

  this.minCoins = minCoins;

  this.maxCoins = maxCoins;

 }

 

 Player encounter(Player player){

  Scanner input = new Scanner(System.in);

  InputHandler handler = new InputHandler();

  Random ran = new Random();

  boolean inFight = true;

  int damageReceived;

  int damageDone;

  System.out.println("\nYou spot a " + this.name + ".");

  

   while(inFight){

    System.out.println("\nWill you:\n1: attack\n2: run away\n3: view your character menu");

    switch(handler.getInt(input)){

     case 1:

      damageDone = ran.nextInt(player.maxDamage-player.minDamage)+player.minDamage+1;

      damageReceived = ran.nextInt(this.maxDamage-this.minDamage)+this.minDamage+1;

      player.health -= damageReceived;

      this.health -= damageDone;

       System.out.println("\nYou attack the " + this.name + " and do " + damageDone + " damage.\nThe " + this.name + " fights back and does " + damageReceived + " damage.");

      break;

     case 2:

      if(ran.nextBoolean()){

       damageReceived = ran.nextInt(this.maxDamage-this.minDamage)+(minDamage*2)+1;

       System.out.println("\nYou run away, but the " + this.name + " manages to hurt you while doing so.\nYou lose " + damageReceived + " health.");

       if(player.health<=0){

        inFight = false;

       } else {

        System.out.println("When you get back to the room the " + this.name + " is gone.");

       }

      } else {

       System.out.println("\nYou manage to get away safely\nWhen you get back to the room the " + this.name + " is gone.");

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

    if(player.health<=0){

     inFight = false;

    } else if(this.health<=0){

     int monsterCoins = ran.nextInt(this.maxCoins-this.minCoins)+this.minCoins+1;

     player.coins += monsterCoins;

     System.out.println("\nYou killed the " + this.name + "." + "\nYou find " + monsterCoins + " coins on the corpse.");

     inFight = false;

    }

   }

  return player;

 }

 

}




class Item{

 String name;

 int minDamage;

 int maxDamage;

 int maxHealth;

 int health;

 

 Item(String name, int minDamage, int maxDamage, int maxHealth, int health){

  this.name = name;

  this.minDamage = minDamage;

  this.maxDamage = maxDamage;

  this.maxHealth = maxHealth;

  this.health = health;

 }

}