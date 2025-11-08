public class ConferenceEvent extends Event {
    public ConferenceEvent(String title, String description, String date, String place, User createdBy) {
        super(title, description, date, place, createdBy);
        type = "Conference";
    }
}
