import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    private static Scanner input = new Scanner(System.in);
    private static UserAdmin firstUsing = UserAdmin.getInstance();
    private static EventManager firstManager = EventManager.getInstance();


    public static void main(String[] args) {
        boolean runProgram = true;

        System.out.println("---Online Event Management System---");

        while(runProgram){
            if (firstUsing.getCurrentUser() == null){
                System.out.println("\n---Menu---");
                System.out.println("1. Register user");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                String choice = input.nextLine();
                switch (choice) {
                    case "1" -> registerMenu();
                    case "2" -> loginMenu();
                    case "3" -> {
                        System.out.println("System closing...");
                        runProgram = false;
                    }
                    default -> {
                        System.out.println("Invalid choice.");
                    }
                }
            } else {
                User current = firstUsing.getCurrentUser();

                if (current instanceof Admin){
                    adminMenu((Admin) current);
                } else {
                    userMenu(current);
                }
            }
        }

    }

    private static void registerMenu() {
        System.out.println("\n---Registering new user---");
        System.out.print("Enter email: ");
        String email = input.nextLine();
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        firstUsing.registerUser(username, email, password, false);
    }

    private static void loginMenu() {
        System.out.println("\n---Login---");
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        firstUsing.login(username, password);
    }

    private static void userMenu(User user) {
        System.out.println("\n---User Menu---");
        System.out.println("1. Create event");
        System.out.println("2. View events");
        System.out.println("3. Join events");
        System.out.println("4. Update my events");
        System.out.println("5. See participants for an event");
        System.out.println("6. Leave event");
        System.out.println("7. Log out");
        System.out.print("Choose an option: ");
        String choice = input.nextLine();
        switch (choice) {
            case "1" -> createEvent(user);
            case "2" -> firstManager.listEvents();
            case "3" -> joinEvent(user);
            case "4" -> updateEvent(user);
            case "5" -> viewParticipants(user);
            case "6" -> leaveEvent(user);
            case "7" -> firstUsing.logout();
            default -> System.out.println("Invalid choice.");
        }

    }

    private static void adminMenu(Admin admin) {
        System.out.println("\n--- ADMINMENU ---");
        System.out.println("1. View events");
        System.out.println("2. Generate admin-report");
        System.out.println("3. Register new admin");
        System.out.println("4. Register new user");
        System.out.println("5. Delete user");
        System.out.println("6. Unlock user");
        System.out.println("7. Log out");
        System.out.print("choose an option: ");
        String choice = input.nextLine();

        switch (choice) {
            case "1" -> firstManager.listEvents();
            case "2" -> generateAdminReport(admin);
            case "3" -> createAdmin();
            case "4" -> registerMenu();
            case "5" -> deleteUser();
            case "6" -> unlockUserMenu();
            case "7" -> firstUsing.logout();
            default -> System.out.println("Invalid input");
        }
    }


    private static void createEvent(User user) {
        System.out.println("\n--- Create Event ---");
        System.out.print("Do you want to specify a type? (yes/no): ");
        String choice = input.nextLine().trim().toLowerCase();

        String type = "Normal event";
        if (choice.equals("yes")) {
            System.out.print("Enter type (e.g., Concert, Workshop, Conference): ");
            type = input.nextLine();
        }

        System.out.print("Title: ");
        String title = input.nextLine();
        System.out.print("Description: ");
        String desc = input.nextLine();
        System.out.print("Date: ");
        String date = input.nextLine();
        System.out.print("Place: ");
        String place = input.nextLine();

        Event event;
        if (choice.equals("yes")) {
            event = new Event(title, desc, date, place, type, user);
        } else {
            event = new Event(title, desc, date, place, user);
        }

        firstManager.addEvent(event);
        System.out.println("Event '" + title + "' (" + event.getType() + ") created by " + user.getName());
    }

    private static void joinEvent(User user) {
        System.out.println("\n--- Join Event ---");
        firstManager.listEvents();
        System.out.print("Enter name of event you want to participate in: ");
        String title = input.nextLine();

        for (Event e : firstManager.getEvents()) {
            if (e.getTitle().equalsIgnoreCase(title)) {
                e.addParticipant(user);
                return;
            }
        }
        System.out.println("Event not found.");
    }

    private static void leaveEvent(User user) {
        firstManager.listEvents();
        System.out.print("Enter event title to leave: ");
        String title = input.nextLine();

        for (Event e : firstManager.getEvents()) {
            if (e.getTitle().equalsIgnoreCase(title)) {
                e.getNotifications().removeNotifyingUser(user);
                System.out.println(user.getName() + " has left the event '" + e.getTitle() + "'.");
                return;
            }
        }
        System.out.println("Event not found.");
    }

    private static void viewParticipants(User user) {
        firstManager.listEvents();
        System.out.print("Enter event title to view participants: ");
        String title = input.nextLine();

        for (Event e : firstManager.getEvents()) {
            if (e.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Participants for event: " + e.getTitle());
                e.getNotifications().listOfUsersForEvent();
                return;
            }
        }
        System.out.println("Event not found.");
    }

    private static void generateAdminReport(Admin admin) {
        Report basic = new BasicReport();
        Report adminReport = new AdminReport(basic, admin);
        adminReport.generateReport();
    }

    private static void createAdmin() {
        System.out.print("\nNew admin name: ");
        String name = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();
        firstUsing.registerUser(name, email, password, true);
    }

    private static void updateEvent(User user) {
        System.out.println("\n--- Update Your Events ---");

        List<Event> myEvents = new ArrayList<>();
        for (Event e : firstManager.getEvents()) {
            if (e.getCreatedBy().equals(user)) {
                myEvents.add(e);
            }
        }

        if (myEvents.isEmpty()) {
            System.out.println("You haven’t created any events yet.");
            return;
        }

        System.out.println("Your created events:");
        for (int i = 0; i < myEvents.size(); i++) {
            System.out.println((i + 1) + ". " + myEvents.get(i).getTitle());
        }

        System.out.print("Choose event number to update: ");
        int choice;
        try {
            choice = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (choice < 1 || choice > myEvents.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Event selectedEvent = myEvents.get(choice - 1);

        System.out.println("What would you like to update?");
        System.out.println("1. Date");
        System.out.println("2. Place");
        System.out.println("3. Description");
        System.out.print("Choose: ");
        String option = input.nextLine();

        switch (option) {
            case "1" -> {
                System.out.print("Enter new date: ");
                String newDate = input.nextLine();
                selectedEvent.changeDate(newDate);
            }
            case "2" -> {
                System.out.print("Enter new place: ");
                String newPlace = input.nextLine();
                selectedEvent.changePlace(newPlace);
            }
            case "3" -> {
                System.out.print("Enter new description: ");
                String newDesc = input.nextLine();
                selectedEvent.changeDescription(newDesc);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void deleteUser() {
        boolean done = false;

        while (!done) {
            System.out.println("\n--- Delete Users ---");
            firstUsing.listUsers();
            System.out.print("Which user would you like to delete? (or type 'exit' to go back): ");
            String choice = input.nextLine().trim();

            if (choice.equalsIgnoreCase("exit")) {
                System.out.println("Returning to admin menu...");
                done = true;
                break;
            }

            boolean removed = firstUsing.removeUser(choice);
            if (removed) {
                done = true;
            } else {
                System.out.println("No user found with that name. Please try again.\n");
            }
        }
    }

    private static void unlockUserMenu() {
        System.out.println("\n--- Unlock user ---");
        firstUsing.listUsers();

        System.out.print("Enter name of user you want to unlock: ");
        String name = input.nextLine();

        firstUsing.unlockUser(name);
    }



}