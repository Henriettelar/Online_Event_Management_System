public class BasicReport implements Report {
    @Override
    public void generateReport() {
        System.out.println("Generating standard-report: Overview over your registered events.");
    }
}