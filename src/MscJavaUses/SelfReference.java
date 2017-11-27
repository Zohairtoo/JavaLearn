package MscJavaUses;

public class SelfReference {	
	
	private int row;
	private double height;
	private String name="";
	
	public SelfReference() {
		this(0,0.0); //but this only valid for int (primitive types) and not String types
		this.name = "noName";
	}
	
	//	creating the constructor with parameters and using this (self reference) keyword
	public SelfReference(int _row, double _height) {
		this.row = _row;
		this.height = _height;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
