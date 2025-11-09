import java.util.List;
import java.util.ArrayList;

public class Event {
    public String title;
    public String description;
    public String date;
    public String place;
    private User createdBy;
    public static String type = "Normal event";
    private List<User> participants = new ArrayList<>();
    private UpdateNotification notifications;


    public Event(String title, String description, String date, String place, User creator) {
        this(title, description, date, place, "Just an event", creator);
        this.notifications = new UpdateNotification();
    }

    // Event med type
    public Event(String title, String description, String date, String place, String type, User createdBy) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.place = place;
        this.type = type;
        this.createdBy = createdBy;
        this.notifications = new UpdateNotification();
    }

    public void setUpdateNotification(UpdateNotification notifications) {
        this.notifications = notifications;
    }

    public void addParticipant(User user) {
        participants.add(user);
        if (notifications == null) {
            notifications = new UpdateNotification();
        }
        notifications.addNotifyingUser(user);
        System.out.println(user + " has been added to event: " + title);
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void makeTitle(String title) {
        this.title = title;
    }
    public void makeDescription(String description) {
        this.description =  description;
    }
    public void makeDate(String date) {
        this.date = date;
    }
    public void makePlace(String place) {
        this.place = place;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }
    public String getPlace() {
        return place;
    }
    public String getType() {
        return type;
    }
    public User getCreatedBy() {
        return  createdBy;
    }

    public UpdateNotification getNotifications() {
        return notifications;
    }

    public void changeDate(String date) {
        this.date = date;
        notifyAllUsers("Date for event '" + title + "' (" + type + ") has been changed to: " + date);
    }

    public void changePlace(String place) {
        this.place = place;
        notifyAllUsers("Location for event '" + title + "' (" + type + ") has been changed to: " + place);
    }

    public void changeDescription(String description) {
        this.description = description;
        notifyAllUsers("Description for event '" + title + "' (" + type + ") has been updated to: " + description);
    }

    private void notifyAllUsers(String message) {
        if (notifications != null) {
            notifications.notifyAllUsers("Event '" + title + "': " + message);
        }
    }


    public String toString() {
        return String.format(
                "%s {\n" +
                        "  Title: %s,\n" +
                        "  Description: %s,\n" +
                        "  Date: %s,\n" +
                        "  Place: %s\n" +
                        "}", type,  title, description, date, place);
    }
}

