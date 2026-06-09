import java.io.Serializable;
import java.util.List;

public class LogisticsMemento implements Serializable {
    private List<DeliverableItem> savedParcels;
    private List<Courier> savedCouriers;

    public LogisticsMemento(List<DeliverableItem> parcels, List<Courier> couriers) {
        this.savedParcels = parcels;
        this.savedCouriers = couriers;
    }
    public List<DeliverableItem> getSavedParcels() {
        return savedParcels;
    }
    public List<Courier> getSavedCouriers() {
        return savedCouriers;
    }

    public void setSavedParcels(List<DeliverableItem> savedParcels) {
        this.savedParcels = savedParcels;
    }

    public void setSavedCouriers(List<Courier> savedCouriers) {
        this.savedCouriers = savedCouriers;
    }
}
