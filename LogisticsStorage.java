import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogisticsStorage implements Serializable {
    private static LogisticsStorage instance;
    private List<Courier> couriers = new ArrayList<>();
    private List<DeliverableItem> parcels = new ArrayList<>();
    private List<OrderObserver> observers = new ArrayList<>();

    private LogisticsStorage() {
        couriers.add(new Courier("Ігор Шикін", true));
        couriers.add(new Courier("Андрій Куляк", true));
        couriers.add(new Courier("Марія Боріс", true));
    }
    public static LogisticsStorage getInstance() {
        if (instance == null) {
            instance = new LogisticsStorage();
        }
        return instance;
    }

    public static void setInstance(LogisticsStorage instance) {
        LogisticsStorage.instance = instance;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<Courier> couriers) {
        this.couriers = couriers;
    }

    public List<DeliverableItem> getParcels() {
        return parcels;
    }

    public void setParcels(List<DeliverableItem> parcels) {
        this.parcels = parcels;
    }

    public List<OrderObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<OrderObserver> observers) {
        this.observers = observers;
    }

    public void addObserver(OrderObserver obs) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(obs);
    }
    public void notifyObservers(String msg) {
        if (observers != null) {
            for (var obs : observers) {
                obs.onOrderEvent(msg);
            }
        }
    }
    public Parcel createOrder(String type, double weight, String address) {
        if (weight <= 0) {
            throw new LogisticsException("Вага відправлення повинна бути більшою за 0 кг!");
        }
        Courier freeCourier = null;
        for (var c : couriers) {
            if (c.isFree()) {
                freeCourier = c;
                break;
            }
        }
        if (freeCourier == null) {
            throw new LogisticsException("Немає вільних кур'єрів для доставки цього замовлення!");
        }
        freeCourier.setFree(false);
        Parcel newParcel = new Parcel(type, weight, address);
        newParcel.setAssignedCourier(freeCourier);
        newParcel.setStatus("оформлено");
        parcels.add(newParcel);
        notifyObservers("Оформлено посилку [" + type + ", " + weight + "кг] до " + address + ". Призначено кур'єра: " + freeCourier.getName());
        return newParcel;
    }
    public void updateParcelStatus(int index, String newStatus) {
        if (index < 0 || index >= parcels.size()) {
            throw new LogisticsException("Посилку за вказаним індексом не знайдено!");
        }
        Parcel parcel = (Parcel) parcels.get(index);
        String oldStatus = parcel.getStatus();
        parcel.setStatus(newStatus);
        notifyObservers("Зміна статусу посилки №" + index + ": '" + oldStatus + "' --> '" + newStatus + "'");
        if ("доставлено".equalsIgnoreCase(newStatus)) {
            if (parcel.getAssignedCourier() != null) {
                parcel.getAssignedCourier().setFree(true);
                notifyObservers("Кур'єр " + parcel.getAssignedCourier().getName() + " успішно виконав доставку та звільнився.");
            }
        }
    }
    public LogisticsMemento saveState() {
        notifyObservers("Створено зліпок стану логістичної системи (Memento)");
        return new LogisticsMemento(new ArrayList<>(parcels), new ArrayList<>(couriers));
    }
    public void restoreState(LogisticsMemento memento) {
        this.parcels = memento.getSavedParcels();
        this.couriers = memento.getSavedCouriers();
        notifyObservers("Стан логістичної системи успішно відновлено із зліпка (Memento)");
    }
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            LogisticsMemento memento = this.saveState();
            oos.writeObject(memento);
            notifyObservers("Контрольну точку успішно записано у файл: " + filename);
        } catch (Exception e) {
            throw new LogisticsException("Помилка серіалізації стану: " + e.getMessage());
        }
    }
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            LogisticsMemento memento = (LogisticsMemento) ois.readObject();
            this.restoreState(memento);
        } catch (Exception e) {
            throw new LogisticsException("Помилка десеріалізації: файл не знайдено або пошкоджено! " + e.getMessage());
        }
    }
    public void printStatus() {
        System.out.println("\n--- СТАТУС ЛОГІСТИЧНОЇ КОМПАНІЇ ---");
        System.out.println("Персонал (Кур'єри):");
        for (var c : couriers) {
            String courierStatus;
            if (c.isFree()) {
                courierStatus = "Вільний";
            } else {
                courierStatus = "У дорозі/Зайнятий";
            }
            System.out.println(" - " + c.getName() + " [" + courierStatus + "]");
        }
        System.out.println("Зареєстровані відправлення:");
        if (parcels.isEmpty()) {
            System.out.println(" - Активні посилки відсутні у базі.");
        }
        for (int i = 0; i < parcels.size(); i++) {
            Parcel p = (Parcel) parcels.get(i);
            String courierName;
            if (p.getAssignedCourier() != null) {
                courierName = p.getAssignedCourier().getName();
            } else {
                courierName = "Не призначено";
            }
            System.out.println(" #" + (i + 1) + " [" + p.getType() + "] Вага: " + p.getWeight() + "кг | Адреса: " + p.getAddress() +
                    " | Ціна: " + p.getPrice() + " грн | Статус: [" + p.getStatus().toUpperCase() + "] | Кур'єр: " + courierName);
        }
    }
}