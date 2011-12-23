import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;

public class ChatRoom extends Hashtable {

    public static Hashtable clients = new Hashtable();

	public ChatRoom() {

		System.out.println("Lancement du ChatRoom :.......... status --> "
				+ this.login("ROOM"));
	}

	public int getNumberOfUsersOnline() {
		return ChatRoom.clients.size();
	}

	private boolean isOnline(String id) {
		return ChatRoom.clients.containsKey(id);
	}

	public boolean login(String id) {
		boolean status;

		if (this.isOnline(id))
			status = false;
		else {
			status = true;
			ChatRoom.clients.put(id, new Vector());
		}
		return status;
	}

	public boolean logout(String id) {
		boolean status;

		if (this.isOnline(id)) {
			status = true;
			ChatRoom.clients.remove(id);
		} else
			status = false;

		return status;
	}

	public String getUsersOnline() {

		String usersList = ":";

		for (Enumeration e = ChatRoom.clients.keys(); e.hasMoreElements();) {
			usersList += "" + (String) e.nextElement() + ":";
		}

		return usersList;
	}

	public Vector getMessages(String id) {

		Vector messages = null;

		if (this.isOnline(id)) {
			messages = ((Vector) ((Vector) ChatRoom.clients.get(id)).clone());
			((Vector) ChatRoom.clients.get(id)).clear();
		}

		return messages;
	}
	
	public String getFirstMessages(String id, String id_sender) {
		String message = null;

		if (this.isOnline(id)) {
		    if (!"ROOM".equals(id_sender)) {
    			for (int i=0; i<((Vector) ChatRoom.clients.get(id)).size(); i++)
	    		    if ( ((String) (((Vector) ChatRoom.clients.get(id)).get(i))).indexOf(""+id_sender+"-->"+id+":") != -1) {
	    		        message = (String) ((Vector) ChatRoom.clients.get(id)).get(i);
	    		        ((Vector) ChatRoom.clients.get(id)).remove(i);
	    		        return message;
	    			}
			}
			else {
			    for (int i=0; i<((Vector) ChatRoom.clients.get(id)).size(); i++)
	    		    if ( ((String) (((Vector) ChatRoom.clients.get(id)).get(i))).indexOf("-->ROOM:") != -1) {
	    		        message = (String) ((Vector) ChatRoom.clients.get(id)).get(i);
	    		        ((Vector) ChatRoom.clients.get(id)).remove(i);
	    		        return message;
	    			}
			}
		}

		return message;
	}

	public boolean sendMessage(String id_sender, String id_receiver,
			String message) {

		boolean status;

		if (!"ROOM".equals(id_receiver)) {
			if (!this.isOnline(id_sender) || !this.isOnline(id_receiver))
				status = false;
			else {
				status = true;
				((Vector) ChatRoom.clients.get(id_receiver)).add(("" + id_sender + "-->"
						+ id_receiver + ":" + message));
			}
		} else {
			if (!this.isOnline(id_sender))
				status = false;
			else {
				status = true;

				for (Enumeration e = ChatRoom.clients.keys(); e.hasMoreElements();)
					((Vector) ChatRoom.clients.get(e.nextElement())).add(("" + id_sender
							+ "-->ROOM:" + message));
			}
		}

		return status;
	}
}
