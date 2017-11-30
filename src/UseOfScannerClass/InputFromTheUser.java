package UseOfScannerClass;

import java.util.Scanner;

public class InputFromTheUser {

	private String name;
	private String education;
	private int rollNo;
	
	public InputFromTheUser() {
		this.name="";
		this.education="";
		this.rollNo=0;
	}
	public InputFromTheUser(String name, String education, int rollNo) {
		this.name = name;
		this.education = education;
		this.rollNo= rollNo;
	}
	
	public static void main(String[] args) {
		InputFromTheUser iFTU = new InputFromTheUser();
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter your name");
		iFTU.name = scan.nextLine();
		System.out.println("Please enter your education");
		iFTU.education = scan.nextLine();
		System.out.println("Please enter your rol number");
		iFTU.rollNo = Integer.parseInt(scan.nextLine().toString());
		
		System.out.println(iFTU.name + " " + iFTU.education + " " + iFTU.rollNo);
	}
	
	
}
