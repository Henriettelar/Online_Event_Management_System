public class ConcertEvent extends Event {
    public ConcertEvent(String title, String description, String date, String place, User createdBy) {
        super(title, description, date, place, createdBy);
        type = "Concert";
    }
}
