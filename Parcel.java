import java.io.Serializable;

public class Parcel implements DeliverableItem, Serializable {
    private String type;
    private double weight;
    private String address;
    private String status;
    private Courier assignedCourier;

    public Parcel(String type, double weight, String address) {
        this.type = type;
        this.weight = weight;
        this.address = address;
        this.status = "оформлено";
    }
    @Override
    public int getPrice() {
        return 60 + (int)(weight * 30);
    }
    public String getType() {
        return type;
    }
    public double getWeight() {
        return weight;
    }
    public String getAddress() {
        return address;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Courier getAssignedCourier() {
        return assignedCourier;
    }
    public void setAssignedCourier(Courier assignedCourier) {
        this.assignedCourier = assignedCourier;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}