import java.util.List;
import java.util.ArrayList;
public class UpdateNotification {
    private List<User> users = new ArrayList<>();

    public void addNotifyingUser(User user) {
        if (!users.contains(user)){
            users.add(user);
        }
    }

    public void removeNotifyingUser(User user) {
        users.remove(user);
    }

    public void notifyAllUsers(String message) {
        for (User user : users) {
            user.receiveNotification(message);
        }
    }

    public void listOfUsersForEvent(){
        System.out.println("List of users for event: " + Event.type);
        for (User user : users){
            System.out.println("-" + user.getName());
        }
    }
}


