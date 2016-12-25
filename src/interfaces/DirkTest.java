package interfaces;

import java.util.List;
import java.util.ArrayList;

public class DirkTest {

	public static void main(String[] args) {
		List<Harry> harries =  new ArrayList<Harry>();
		
		harries.add(new Dirk());
		harries.add(new Henry());
		harries.add(new Dirk());
		harries.add(new Henry());
		harries.add(new Dirk());
		harries.add(new Henry());
		harries.add(new Dirk());
		harries.add(new Henry());
		harries.add(new Dirk());
		
		for(Harry harry : harries){
			
			System.out.println(harry.getHoi());
		}
				
	}

}
