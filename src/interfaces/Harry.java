package interfaces;

public interface Harry {
	
	String hoi = "hoi";
	
	public default void dirge(){
		System.out.println("DEFAULT@!!:");
		
	
	}
	
	
	public default String getHoi(){
		return hoi;
	}
	
}
