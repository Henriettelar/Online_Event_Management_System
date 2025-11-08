import java.util.ArrayList;
import java.util.List;

public class User {
    protected String name;
    protected String pass;
    protected String email;
    protected boolean isAdmin;
    int loginAttempts = 0;
    private boolean locked = false;

    public User(String name, String email, String pass, boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPass(){
        return pass;
    }

    public boolean isLocked() {
        return locked;
    }

    public void resetLoginAttempts() {
        loginAttempts = 0;
    }

    public void incrementLoginAttempts() {
        loginAttempts++;
        if (loginAttempts >= 3) {
            locked = true;
            System.out.println("User " + name + " has been locked after 3 failed attempts at login.");
        }
    }

    public void unlock(Admin admin) {
        locked = false;
        loginAttempts = 0;
        System.out.println("User " + name + " has been unlocked by admin " + admin.getName());
    }

    public void receiveNotification(String message) {
        System.out.println("Received notification to " + name + ": " + message);
    }

    @Override
    public String toString() {
        return name + " (" + email + ")" + (locked ? " [LÅST]" : "");
    }



}