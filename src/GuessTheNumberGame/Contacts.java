package GuessTheNumberGame;

import java.text.ParseException;
import java.util.Random;

public class Contacts {
	private String Name;
	private String Address;
	private String Phone;
	private int index;
	
	private Random random = new Random();
	private Contacts[] myC;
	
	public Contacts() {
		// TODO Auto-generated constructor stub
	}
	public Contacts(String _name, String _address, String _phone) {
		this.Name = _name;
		this.Address = _name;
		this.Phone = _name;
	}
	
	public int setIndex(int _index) {
		return this.index = _index;
	}
	public int getIndex() {
		return this.index;
	}
	
	public boolean isEqualentToIndex(int numberGuessed) {
		for (Contacts Contact : myC) {
			if (Contact.index==numberGuessed) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	public Contacts[] randomContactGenerator(int numberOfContacts) {
		myC = new Contacts[numberOfContacts];
		if (!(numberOfContacts==0)) {
			for (int i=0; i< numberOfContacts; i++) {
				Contacts c = new Contacts(getRandomVal(), getRandomVal()+getRandomVal()+getRandomVal()+getRandomVal(), getRandomVal()+getRandomVal());
				c.index = i+1;
				myC[i] = c;
			}
			
		}
		return myC;
	}
	
	public String[] searchForContactIndex(String comparingValue) {
		String[] foundcontact = new String[4];
		if (myC.length!=0) {
			int intformOfComparingString = Integer.parseInt(comparingValue);
			for (int i=0; i< myC.length;i++) {
				
				if (myC[i].index==intformOfComparingString) {
					foundcontact[0] = myC[i].Name;
					foundcontact[1] = myC[i].Address;
					foundcontact[2] = myC[i].Phone;
					foundcontact[3] = Integer.toString(myC[i].index);
				}
			}
		}
		return foundcontact;
	}
	
	protected String getRandomVal() {
		
	return Integer.toString(random.nextInt(15));
	}
}
