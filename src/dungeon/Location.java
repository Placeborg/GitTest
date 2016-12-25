package dungeon;

class Location{

 int x;

 int y;

 int z;

 

 Location(int x, int y, int z){

  this.x = x;

  this.y = y;

  this.z = z;

 }
 @Override
	public String toString() {
		
		return "loc[ x: " + x + ", y: " + y + ", Z:" + z + "]";
		
	}

} 
