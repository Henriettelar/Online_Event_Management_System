public class WorkshopEvent extends Event {
    public WorkshopEvent(String title, String description, String date, String place, User createdBy) {
        super(title, description, date, place, createdBy);
        type = "Workshop";
    }
}
