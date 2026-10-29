public class OrderApp {
    public static void main(String[] args) {
        SelfCheck.run();

        System.out.println("=== Корпоративный заказ оборудования (Вариант 7: MAX_QTY = 40) ===\n");

        // Создаём товары
        Product laptop = new Product("L001", "Ноутбук", 75000);
        Product mouse = new Product("M002", "Мышь", 1500);
        Product monitor = new Product("D003", "Монитор", 25000);

        Order order = new Order();

        // Тест 1: Добавление товаров
        System.out.println("Тест 1: Добавление товаров");
        order.addItem(laptop, 2);
        order.addItem(mouse, 5);
        order.addItem(monitor, 1);
        printOrder(order);

        // Тест 2: Объединение позиций (тот же товар)
        System.out.println("\nТест 2: Объединение позиций (добавляем ещё 3 ноутбука)");
        order.addItem(laptop, 3);
        printOrder(order);

        // Тест 3: Нижняя граница (количество = 1)
        System.out.println("\nТест 3: Нижняя граница (количество = 1)");
        Order order2 = new Order();
        order2.addItem(mouse, 1);
        printOrder(order2);

        // Тест 4: Верхняя граница (количество = 40, вариант 7)
        System.out.println("\nТест 4: Верхняя граница (количество = 40)");
        Order order3 = new Order();
        order3.addItem(mouse, 40);
        printOrder(order3);

        // Тест 5: Превышение максимума (41)
        System.out.println("\nТест 5: Превышение максимума (41)");
        try {
            Order order4 = new Order();
            order4.addItem(mouse, 41);
        } catch (Exception e) {
            System.out.println("  → Ошибка: " + e.getMessage());
        }

        // Тест 6: Подтверждение заказа
        System.out.println("\nТест 6: Подтверждение заказа и попытка изменения");
        order.confirm();
        System.out.println("  Заказ подтверждён. Пытаемся добавить товар...");
        try {
            order.addItem(mouse, 1);
        } catch (Exception e) {
            System.out.println("  → Ошибка: " + e.getMessage());
        }

        // Тест 7: Пустой заказ — попытка подтвердить
        System.out.println("\nТест 7: Попытка подтвердить пустой заказ");
        try {
            Order emptyOrder = new Order();
            emptyOrder.confirm();
        } catch (Exception e) {
            System.out.println("  → Ошибка: " + e.getMessage());
        }

        // Тест 8: Неверный товар (null)
        System.out.println("\nТест 8: Добавление null-товара");
        try {
            Order order5 = new Order();
            order5.addItem(null, 1);
        } catch (Exception e) {
            System.out.println("  → Ошибка: " + e.getMessage());
        }
    }

    private static void printOrder(Order order) {
        System.out.println("  Позиций: " + order.getItemCount());
        for (OrderItem item : order.getItems()) {
            System.out.printf("  - %s (%s): %d шт. × %.2f = %.2f руб.%n",
                item.getProduct().getName(),
                item.getProduct().getId(),
                item.getQuantity(),
                item.getProduct().getPrice(),
                item.getProduct().getPrice() * item.getQuantity());
        }
        System.out.printf("  ИТОГО: %.2f руб.%n", order.calculateTotal());
    }
}