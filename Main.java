import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LogisticsStorage storage = LogisticsStorage.getInstance();
        Logger logger = new Logger();
        storage.addObserver(logger);
        Scanner sc = new Scanner(System.in);
        System.out.println("=== АВТОМАТИЗОВАНА СИСТЕМА ЛОГІСТИКИ ===");
        while (true) {
            try {
                System.out.println("\n1. Показати поточний статус компанії (кур'єри та посилки)");
                System.out.println("2. Створити нове замовлення на доставку (Factory Method)");
                System.out.println("3. Змінити статус посилки (Обробка подій)");
                System.out.println("4. Показати моніторинг подій та статистику (Observer)");
                System.out.println("5. Зберегти стан системи у файл (Memento)");
                System.out.println("6. Завантажити стан системи з файлу (Memento)");
                System.out.println("7. Вихід");
                System.out.print("Оберіть дію: ");
                byte choice = sc.nextByte();
                if (choice == 7)
                    break;
                switch (choice) {
                    case 1:
                        storage.printStatus();
                        break;
                    case 2:
                        System.out.print("Введіть тип посилки (Одежа, Електроніка, Документи): ");
                        String type = sc.nextLine();
                        sc.nextLine();
                        System.out.print("Введіть вагу (кг): ");
                        double weight = sc.nextDouble();
                        System.out.print("Введіть адресу отримувача: ");
                        String address = sc.nextLine();
                        Parcel parcel = storage.createOrder(type, weight, address);
                        System.out.println("Замовлення успішно створено! Розрахована вартість: " + parcel.getPrice() + " грн.");
                        break;
                    case 3:
                        System.out.print("Введіть індекс посилки для зміни статусу (починаючи з 0): ");
                        int index = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Введіть новий статус (оформлено / у дорозі / доставлено): ");
                        String status = sc.nextLine();
                        storage.updateParcelStatus(index, status);
                        break;
                    case 4:
                        logger.logs();
                        break;
                    case 5:
                        storage.saveToFile("logistics_data.ser");
                        break;
                    case 6:
                        storage.loadFromFile("logistics_data.ser");
                        break;
                    default:
                        System.out.println("Невірний вибір. Спробуйте ще раз.");
                }
            } catch (LogisticsException e) {
                System.out.println("Попередження системи: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Некоректний формат даних! " + e.getMessage());
            }
        }
        System.out.println("Роботу програми завершено.");
    }
}