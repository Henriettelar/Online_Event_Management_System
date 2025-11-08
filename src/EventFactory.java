public class EventFactory {
    public static Event createEvent(String type, String title, String description, String date, String place, User createdBy) {
        switch (type.toLowerCase()) {
            case "koncert":
                return new ConcertEvent(title, description, date, place, createdBy);
            case "workshop":
                return new WorkshopEvent(title, description, date, place, createdBy);
            case "konference":
                return new ConferenceEvent(title, description, date, place, createdBy);
            default:
                return new Event(title, description, date, place, createdBy);
        }
    }
}