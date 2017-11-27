package ContactManagerExample;


public class ContactsManager {
	Contact[] myfriendsContact;
	int friendcounter;
	
	public ContactsManager() {
		this.myfriendsContact = new Contact[500];
		this.friendcounter+=0;
	}
	
	void addContact(Contact _contact) {
		myfriendsContact[friendcounter] = _contact;
		friendcounter++;
	}
	
	Contact searchContact(String _searchString) {
		for (int i=0; i<=friendcounter;i++) {
			if (myfriendsContact[i].name.equals(_searchString)) {
				return myfriendsContact[i];
			}
		}
		return null;
	}
	
}
