import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Logger implements OrderObserver, Serializable {
    private List<String> logs = new ArrayList<>();

    @Override
    public void onOrderEvent(String message) {
        addLog(message);
    }
    public void addLog(String message) {
        logs.add(message);
    }
    public void logs() {
        System.out.println("\n=== МОНИТОРИНГ СИСТЕМИ ЛОГІСТИКИ (OBSERVER) ===");
        if (logs.isEmpty()) {
            System.out.println("Журнал подій порожній.");
        }
        for (int i = 0; i < logs.size(); i++) {
            System.out.println((i + 1) + ". " + logs.get(i));
        }
        System.out.println();
    }
    public List<String> getLogs() {
        return logs;
    }
    public void setLogs(List<String> logs) {
        this.logs = logs;
    }
}
