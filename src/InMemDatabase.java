import java.util.HashMap;
import java.util.Map;

// singleton
public class InMemDatabase {
    private static InMemDatabase inMemDatabase;

    public Map<Integer, Employee> employeeMap;
    public Map<Integer, Organization> organizationMap;

    private InMemDatabase() {
        employeeMap = new HashMap<>();
        organizationMap = new HashMap<>();
    }

    public static InMemDatabase getInstance() {
        if (inMemDatabase == null) {
            inMemDatabase = new InMemDatabase();
        }
        return inMemDatabase;
    }
}
