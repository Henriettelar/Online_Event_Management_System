import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static EventManager instance;
    private List<Event> events = new ArrayList<Event>();

    private EventManager() {}

    public static EventManager getInstance() {

        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void addEvent(Event event) {
        events.add(event);
        System.out.println("Event added: " + event.getTitle());
    }

    public List<Event> getEvents() {
        return events;
    }

    public void listEvents() {
        System.out.println("\n📋 List of events:");
        if (events.isEmpty()) {
            System.out.println(" - No events added yet.");
        } else {
            for (Event e : events) {
                System.out.println(" - " + e.getTitle() + " (" + e.getDate() + " at " + e.getPlace() + ")");
            }
        }
    }
}
