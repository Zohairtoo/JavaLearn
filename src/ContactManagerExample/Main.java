package ContactManagerExample;

public class Main {
	public static void main(String[] args) {
		ContactsManager myContactsManager = new ContactsManager();
		Contact c = new Contact();
		c.name = "Aaa";
		c.phoneNumber = "12345";
		myContactsManager.addContact(c);
		Contact c2 = new Contact();
		c2.name = "Bbb";
		c2.phoneNumber = "67890";
		myContactsManager.addContact(c2);
		Contact c3 = new Contact();
		c3.name = "Bbb";
		c3.phoneNumber = "67890";
		myContactsManager.addContact(c3);
		
		Contact retrievedContact = myContactsManager.searchContact("Bbb");
		System.out.println(retrievedContact.phoneNumber);
	}
}
