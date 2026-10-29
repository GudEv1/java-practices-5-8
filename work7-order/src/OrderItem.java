public class OrderItem {
    private final Product product;
    private int quantity;
    // Вариант 7: максимальное количество позиции = 40
    private static final int MAX_ITEM_QUANTITY = 40;

    public OrderItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Товар не может быть null");
        }
        if (quantity < 1 || quantity > MAX_ITEM_QUANTITY) {
            throw new IllegalArgumentException(
                "Количество должно быть от 1 до " + MAX_ITEM_QUANTITY + ", получено: " + quantity);
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public void addQuantity(int amount) {
        if (this.quantity + amount > MAX_ITEM_QUANTITY) {
            throw new IllegalArgumentException(
                "Превышен максимум " + MAX_ITEM_QUANTITY + " для одной позиции");
        }
        this.quantity += amount;
    }
}