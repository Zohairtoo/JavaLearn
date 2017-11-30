package GuessTheNumberGame;

import java.util.Scanner;

public class GuessTheNumber {
	Contacts randomContactsSearcher;
	
	
	public GuessTheNumber() {
		randomContactsSearcher = new Contacts();
		randomContactsSearcher.randomContactGenerator(10);
	}
	
	private String getRandomFromContacts() {
		return randomContactsSearcher.getRandomVal();
	}
	public static void main(String[] args) {
		String input;
		String[] contactfound;
		Scanner scan = new Scanner(System.in);
		GuessTheNumber GTN = new GuessTheNumber();
		
		System.out.println("I have randomly choosen a number");
		System.out.println("try to guess it");
		System.out.println("you can make 10 total guesses");
		for (int j=10;j>0; j--) {
			String i = GTN.getRandomFromContacts();
			System.out.println("computer guessed ==> " + i);
			System.out.println("enter your " + j + "th guess");
			input = scan.nextLine();
			if (i.equals(input)) {
				System.out.println("your input and computer input got same");
				System.out.println("\r\n");
				contactfound = GTN.randomContactsSearcher.searchForContactIndex(input);
				if (!(contactfound[0]==null)) {
					System.out.println("its a match");
					System.out.println(contactfound[0] + contactfound[1] + contactfound[2]);
					break;
				}
			}
			
			else {
				System.out.println("not matched " + "computer guessed ==> " + i);
				
			}
		}
		
	}
}
