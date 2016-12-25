package dungeon;

import java.util.List;

import java.util.ArrayList;

import java.util.Random;

import java.util.Scanner;

import java.util.InputMismatchException;

import java.io.InputStream;

import java.lang.Math;

class World{

 List<Row> world = new ArrayList<Row>();

 int xSize;

 int ySize;

 int zSize;

 Location playerLoc;

 

 public World(int x, int y, int z){

  //create world

  xSize = x;

  ySize = y;

  zSize = z;

  

  // creating rows (x)

  for(int i=0; i<x; i++){

   world.add(new Row());

   Row currentRow = (Row)world.get(i);

   //adding columns to rows (y)

   for(int ii=0; ii<y; ii++){

    currentRow.columnList.add(new Column());

    Column currentColumn = (Column)currentRow.columnList.get(ii);

    //adding layers (z)

    for(int iii=0; iii<z; iii++){

     currentColumn.floorList.add(new Room("empty"));

    } 

    currentRow.columnList.set(ii, currentColumn);

   }

   world.set(i, currentRow);

  }

  

  //create level and put player at the start

  playerLoc = makePath();

 }

 

 void crawlDungeon(Player player){

  boolean inside = true;

   

  //player is exploring or in menu

  while(inside){

   try{

    if (player.inPlayMenu){

     player.menu();

    } else {

     if(player.health <= 0){

      inside = false;

      System.out.println("\nYou died in the dungeon.\nGame over");

     } else {

     

      //DEBUGGING

      //System.out.println(getRoom(player.loc).type);

      

      getRoom(player.loc).type(); 

      player = getRoom(player.loc).event(player);

     }

    }

   } catch(IndexOutOfBoundsException e){

    //when the player reaches the end, let him know

    inside = false;

    System.out.println("\n" + player.name + ", you made it out of this dungeon and earned a total of " + player.coins + " coins.");

   }

  }

 } 

 

 Location makePath(){

  Random ran = new Random();

  boolean inside = true;

  

  //set a margin on the world in which the origin can spawn

  int marginX = (int)Math.floor(xSize/3);

  int marginY = (int)Math.floor(ySize/3);

  int marginZ = (int)Math.floor(zSize/3);

  Location currentLocation = new Location(ran.nextInt((xSize-(marginX*2)))+marginX, ran.nextInt(ySize-(marginY*2))+marginY, ran.nextInt(zSize-(marginZ)*2)+marginZ);
  
  
 // Location currentLocation = new Location(4,4,4);
  Location origen = new Location(currentLocation.x, currentLocation.y, currentLocation.z);

  

  //set variables that are used when making stray paths

  Location memLocation = new Location(0,0,0);

  boolean stray = false;

  int strayCounterMax = 0;

  int strayCounter = 0;

  

  //create origin room

  getRoom(currentLocation).type = "prison room";

  

  //DEBUGGING

  //System.out.println("origin xyz: " + " " + currentLocation.x + " " + currentLocation.y + " " + currentLocation.z);

  

  //create a random route that leads out of bounds

  while(inside){

   

   //creates a stray path

   if(getRoom(currentLocation).type != "prison room" && ran.nextInt(8)==0 && stray == false){

    memLocation = new Location(currentLocation.x, currentLocation.y, currentLocation.z);

    strayCounterMax = ran.nextInt(3+1);

    strayCounter = 0;

    stray = true;

   }

   if(stray && strayCounter <= strayCounterMax){

    strayCounter++;

   } else if(stray) {

    stray = false;

    currentLocation = new Location(memLocation.x, memLocation.y, memLocation.z);

   }

   //make a location in a random available direction

   Location newLocation = createNewLocation(currentLocation);

   

   try{

    //when the random direction is empty, create a room

    if(getRoom(newLocation).type == "empty"){

    

     //DEBUGGING

     //System.out.println("x: " + newLocation.x + "   y: " + newLocation.y + "   z: " + newLocation.z);

     switch(ran.nextInt(5)){

      case 0:

       setRoom(newLocation, new TreasureRoom("treasure room"));

       break;

      case 1:

       setRoom(newLocation, new MonsterRoom("dirty room"));

       break;

      case 2:

       setRoom(newLocation, new ItemRoom("decorated room"));

       break;

      default:

       setRoom(newLocation, new Room("room"));

        break;

     }

     makeDoors(currentLocation, newLocation);

     currentLocation = newLocation;

    }

   } catch (IndexOutOfBoundsException e) {

    try{

    

     //create doors in both rooms

     makeDoors(currentLocation, newLocation);

    } catch (IndexOutOfBoundsException ex) {
    	//TODO:: fix this errored stuff
    	//ex.printStackTrace();
    	
    }

    

    //stop the loop when path is finished

    inside = false;

   } 

  }


  return origen;

 }

 

 //use current location to create another in a random direction

 Location createNewLocation(Location currentLocation){

  Random ran = new Random();

  Location newLocation = new Location(currentLocation.x, currentLocation.y, currentLocation.z);

  switch(ran.nextInt(3)){

    case 0:

     newLocation.x += ran.nextBoolean() ? 1 : -1;

     break;

    case 1:

     newLocation.y += ran.nextBoolean() ? 1 : -1;

     break;

    case 2:

     newLocation.z += ran.nextBoolean() ? 1 : -1;

    default:

     break;

   }

  return newLocation;

 }

 

 //creating doors to connect the two locations

 void makeDoors(Location source, Location destination){

  int xDifference = source.x - destination.x;

  int yDifference = source.y - destination.y;

  int zDifference = source.z - destination.z;

  switch(xDifference){

   case -1:

    //make source door east

    getRoom(source).doors.set(0, new Door("wood", false));

    //make destination door west

    getRoom(destination).doors.set(1, new Door("wood", false));

    break;

   case 1:

    //make source door west

    getRoom(source).doors.set(1, new Door("wood", false));

    //make destination door east

    getRoom(destination).doors.set(0, new Door("wood", false));

    break;

   default:

    break;

  }

  switch(yDifference){

   case -1:

    //make source door north

    getRoom(source).doors.set(2, new Door("wood", false));

    //make destination door south

    getRoom(destination).doors.set(3, new Door("wood", false));

    break;

   case 1:

    //make source door south

    getRoom(source).doors.set(3, new Door("wood", false));

    //make destination door north

    getRoom(destination).doors.set(2, new Door("wood", false));

    break;

   default:

    break;

  }

  switch(zDifference){

   case -1:

    //make source door up

    getRoom(source).doors.set(4, new Door("wood", false));

    //make destination door down

    getRoom(destination).doors.set(5, new Door("wood", false));

    break;

   case 1:

    //make source door down

    getRoom(source).doors.set(5, new Door("wood", false));

    //make destination door up

    getRoom(destination).doors.set(4, new Door("wood", false));

    break;

   default:

    break;

  }

 }

 

 Room getRoom(Location l){
	 System.out.println(l);
  return (Room)((Column)((Row)world.get(l.x)).columnList.get(l.y)).floorList.get(l.z);

 }

 

 void setRoom(Location l, Room r){

 ((Column)((Row)world.get(l.x)).columnList.get(l.y)).floorList.set(l.z, r);

 }

}