import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final List<OrderItem> items = new ArrayList<>();
    private boolean isConfirmed = false;

    public void addItem(Product product, int quantity) {
        if (isConfirmed) {
            throw new IllegalStateException("Нельзя изменить подтверждённый заказ");
        }
        if (product == null) {
            throw new IllegalArgumentException("Товар не может быть null");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Количество должно быть >= 1");
        }

        // Ищем существующую позицию с таким же товаром
        for (OrderItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.addQuantity(quantity);
                return;
            }
        }
        // Если товара нет в заказе — добавляем новую позицию
        items.add(new OrderItem(product, quantity));
    }

    public boolean removeItem(String productId) {
        if (isConfirmed) {
            throw new IllegalStateException("Нельзя изменить подтверждённый заказ");
        }
        return items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public void confirm() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Нельзя подтвердить пустой заказ");
        }
        this.isConfirmed = true;
    }

    public boolean isConfirmed() { return isConfirmed; }

    public List<OrderItem> getItems() {
        // Возвращаем неизменяемую копию — защита инкапсуляции
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    public int getItemCount() { return items.size(); }
}