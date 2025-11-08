import java.util.ArrayList;
import java.util.List;

public class UserAdmin {
    private static UserAdmin instance;

    private UserAdmin() {
        users.add(new Admin("admin123", "IAmAdmin@mail.com", "security321"));
    }

    public static UserAdmin getInstance() {
        if (instance == null) {
            instance = new UserAdmin();
        }
        return instance;
    }

    private List<User> users = new ArrayList<User>();
    private User currentUser;

    public void registerUser(String name, String email, String pass, boolean isAdmin) {
        if (isAdmin && !(currentUser instanceof Admin)) {
            System.out.println("Error! Only  admins can add a new admin-user!");
        }
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                System.out.println("User with this email already exists!");
                return;
            }
        }
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                System.out.println("Username is already used.");
                return;
            }

        }
        User newUser = isAdmin ? new Admin(name, email, pass) : new User(name, email, pass, isAdmin);
        users.add(newUser);
        System.out.println("User " + name + " has been added. " + (isAdmin ? "This user is an Admin" : "This user is a normal user"));

    }

    public User login(String name, String pass) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {

                if (user.isLocked()) {
                    System.out.println("User " + user.getName() + " is locked. Please contact an Administrator!");
                    return null;
                }

                if (user.getPass().equals(pass)) {
                    user.resetLoginAttempts();
                    currentUser = user;
                    System.out.println("Logged in as:  " + user.getName());
                    return user;
                } else {
                    user.incrementLoginAttempts();
                    if (!user.isLocked()) {
                        System.out.println("Wrong password. " + (3 - user.loginAttempts) + " tries left");
                    }
                    return null;
                }
            }
        }
        System.out.println("No user found with this username");
        return null;
    }

    public void logout() {
        if (currentUser != null) {
            System.out.println(currentUser.getName() + " is logged out.");
            currentUser = null;
        } else {
            System.out.println("No user is logged in");
        }
    }

    public void removeUser(String name) {
        if (!(currentUser instanceof Admin)) {
            System.out.println("Error! Only admins can remove users!");
            return;
        }
        User toRemove = null;
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                toRemove = user;
                break;
            }
        }
        if (toRemove != null) {
            if (toRemove == currentUser) {
                System.out.println("You can't remove this admin user.");
                return;
            }
            users.remove(toRemove);
            System.out.println("User " + toRemove.getName() + " has been deleted.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void unlockUser(String name) {
        if (!(currentUser instanceof Admin)) {
            System.out.println("Error! Only admins can unlock users!");
            return;
        }

        Admin admin = (Admin) currentUser;

        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                user.unlock(admin);
                return;
            }
        }

        System.out.println("User not found.");
    }


    public void listUsers() {
        System.out.println("\n List of users:");
        for (User user : users) {
            System.out.println(" - " + user.getName() + " (" + (user instanceof Admin ? "Admin" : "User") + ")"
                    + (user.isLocked() ? " [LOCKED]" : ""));
        }
        System.out.println();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getAllUsers() {
        return users;
    }



}