import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public void removeItem(String productId) {
        Iterator<CartItem> it = items.iterator();

        while (it.hasNext()) {
            CartItem item = it.next();

            if (item.getProduct().getId().equals(productId)) {
                it.remove();
                return;
            }
        }

        throw new Product.NotFoundException(productId);
    }
}
