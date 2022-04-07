import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * This class represents the ECommerceSystem. It is responsible for managing
 * all of the products, customers, and product orders. It has the ability to
 * create new customers, place, cancel, and ship orders. It also has the ability
 * to print out all of the products, customers, and orders.
 *
 * @author Ali Rizvi (501039655)
 * @see Product
 * @see Customer
 * @see ProductOrder
 */
public class ECommerceSystem {
    private final ArrayList<Product> products = new ArrayList<Product>();
    private final ArrayList<Customer> customers = new ArrayList<Customer>();

    private final ArrayList<ProductOrder> orders = new ArrayList<ProductOrder>();
    private final ArrayList<ProductOrder> shippedOrders = new ArrayList<ProductOrder>();

    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;

    private String errMsg = null;

    /**
     * Constructs a new ECommerceSystem. This constructor will generate a variety of
     * pre-defined products and customers.
     */
    public ECommerceSystem() {
        products.add(new Product("Acer Laptop", generateProductId(), 989.0, 99, Product.Category.COMPUTERS));
        products.add(new Product("Apex Desk", generateProductId(), 1378.0, 12, Product.Category.FURNITURE));
        products.add(
                new Book("Book", generateProductId(), 45.0, 4, 2, "Ahm Gonna Make You Learn", "T. McInerney", 1999));
        products.add(new Product("DadBod Jeans", generateProductId(), 24.0, 50, Product.Category.CLOTHING));
        products.add(new Product("Polo High Socks", generateProductId(), 5.0, 199, Product.Category.CLOTHING));
        products.add(new Product("Tightie Whities", generateProductId(), 15.0, 99, Product.Category.CLOTHING));
        products.add(new Book("Book", generateProductId(), 35.0, 4, 2, "How to Fool Your Prof", "D. Umbast", 2002));
        products.add(
                new Book("Book", generateProductId(), 45.0, 4, 2, "How to Escape from Prison", "A. Fugitive", 1996));
        products.add(new Book("Book", generateProductId(), 44.0, 14, 12, "Ahm Gonna Make You Learn More",
                "T. McInerney", 2004));
        products.add(new Product("Rock Hammer", generateProductId(), 10.0, 22, Product.Category.GENERAL));
        products.add(new Shoes("Nike Shoes", generateProductId(), 40.0,
                Map.of("black", new int[] { 50, 60, 65, 80, 90 }, "brown", new int[] { 40, 45, 55, 75, 80 })));
        products.add(new Shoes("Adidas Premium", generateProductId(), 155.0,
                Map.of("black", new int[] { 5, 5, 4, 8, 10 }, "brown", new int[] { 5, 4, 6, 5, 3 })));

        customers.add(new Customer(generateCustomerId(), "Inigo Montoya", "1 SwordMaker Lane, Florin"));
        customers.add(new Customer(generateCustomerId(), "Prince Humperdinck", "The Castle, Florin"));
        customers.add(new Customer(generateCustomerId(), "Andy Dufresne", "Shawshank Prison, Maine"));
        customers.add(new Customer(generateCustomerId(), "Ferris Bueller", "4160 Country Club Drive, Long Beach"));
    }

    private String generateOrderNumber() {
        return Integer.toString(orderNumber++);
    }

    private String generateCustomerId() {
        return Integer.toString(customerId++);
    }

    private String generateProductId() {
        return Integer.toString(productId++);
    }

    /**
     * Getter for the error message given by the system.
     *
     * @return The error message given by the system.
     */
    public String getErrorMessage() {
        return errMsg;
    }

    /**
     * Prints out all of the products in the system.
     */
    public void printAllProducts() {
        for (Product p : products) {
            System.out.print(p);
        }
    }

    /**
     * Prints out all of the products in the system that are books.
     */
    public void printAllBooks() {
        for (Product p : products) {
            if (p instanceof Book) {
                System.out.print(p);
            }
        }
    }

    /**
     * Prints out all of the products in the system that are shoes.
     */
    public void printAllShoes() {
        for (Product p : products) {
            if (p instanceof Shoes) {
                System.out.print(p);
            }
        }
    }

    /**
     * Prints all active orders in the system.
     */
    public void printAllOrders() {
        for (ProductOrder o : orders) {
            System.out.print(o);
        }
    }

    /**
     * Prints all shipped orders in the system.
     */
    public void printAllShippedOrders() {
        for (ProductOrder o : shippedOrders) {
            System.out.print(o);
        }
    }

    /**
     * Prints all the customers in the system.
     */
    public void printCustomers() {
        for (Customer c : customers) {
            System.out.print(c);
        }
    }

    /**
     * Prints out the order history of a customer. This will print out all active
     * orders and shipped orders, if any.
     *
     * @param customerId The customer ID of the customer whose order history is to
     *                   be printed.
     * @return True if the order history was successfully printed. If false, use
     *         {@link #getErrorMessage()} to find out what went wrong.
     */
    public boolean printOrderHistory(String customerId) {
        boolean found = false;
        for (Customer c : customers) {
            if (c.getId().equals(customerId)) {
                found = true;
                break; // break early, customer has been found
            }
        }

        if (!found) {
            errMsg = String.format("Customer %s Not Found", customerId);
            return false;
        }

        System.out.println("Current Orders of Customer " + customerId);
        for (ProductOrder o : orders) {
            if (o.fromCustomer(customerId)) {
                System.out.print(o);
            }
        }

        System.out.println("\nShipped Orders of Customer " + customerId);
        for (ProductOrder o : shippedOrders) {
            if (o.fromCustomer(customerId)) {
                System.out.print(o);
            }
        }

        return true;
    }

    /**
     * Orders a specified product for a specified customer with the given product
     * options.
     *
     * @param productId      The product ID of the product to be ordered.
     * @param customerId     The customer ID of the customer who is ordering the
     *                       product.
     * @param productOptions The product options to be used when ordering the
     *                       product.
     * @return The order number of the order if successful. If false, use
     *         {@link #getErrorMessage()} to find out what went wrong.
     */
    public String orderProduct(String productId, String customerId, String productOptions) {
        Customer customer = null;
        for (Customer c : customers) {
            if (c.getId().equals(customerId)) {
                customer = c;
                break;
            }
        }

        if (customer == null) {
            errMsg = String.format("Customer %s Not Found", customerId);
            return null;
        }

        Product product = null;
        for (Product p : products) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }

        if (product == null) {
            errMsg = String.format("Product %s Not Found", productId);
            return null;
        }

        if (!product.validOptions(productOptions)) {
            errMsg = String.format("Product %s ProductId %s Invalid Options: %s", product.getName(), productId,
                    productOptions);
            return null;
        }

        if (!product.hasStock(productOptions)) {
            errMsg = String.format("Product %s ProductId %s Out of Stock", product.getName(), productId);
            return null;
        }

        String orderNumber = generateOrderNumber();
        ProductOrder order = new ProductOrder(orderNumber, product, customer, productOptions);

        product.reduceStock(productOptions);
        orders.add(order);

        return orderNumber;
    }

    /**
     * Creates a new customer and saves their data to the system.
     *
     * @param name    The name of the customer.
     * @param address The address of the customer.
     * @return True if the customer was successfully created. If false, use
     *         {@link #getErrorMessage()} to find out what went wrong.
     */
    public boolean createCustomer(String name, String address) {
        if (name == null || name.equals("")) {
            errMsg = "Invalid Customer Name";
            return false;
        }

        if (address == null || address.equals("")) {
            errMsg = "Invalid Customer Address";
            return false;
        }

        Customer customer = new Customer(generateCustomerId(), name, address);
        customers.add(customer);

        return true;
    }

    /**
     * Ships an active order. This will remove the order from the active orders and
     * place it into a separate list of shipped orders. This cannot be used with
     * orders that were cancelled or have already been shipped.
     *
     * @param orderNumber The order number of the order to be shipped.
     * @return True if the product order is successfully shipped. If false, use
     *         {@link #getErrorMessage()} to find out what went wrong.
     */
    public ProductOrder shipOrder(String orderNumber) {
        ProductOrder order = null;
        for (ProductOrder o : orders) {
            if (o.isOrder(orderNumber)) {
                order = o;
                break;
            }
        }

        if (order == null) {
            errMsg = String.format("Order %s Not Found", orderNumber);
            return null;
        }

        orders.remove(order);
        shippedOrders.add(order);

        return order;
    }

    /**
     * Cancels an active order. This will remove the order from the active orders.
     * This will not work for orders that have already been cancelled or have
     * shipped.
     *
     * @param orderNumber The order number of the order to be cancelled.
     * @return True if the product order is successfully cancelled. If false, use
     *         {@link #getErrorMessage()} to find out what went wrong.
     */
    public boolean cancelOrder(String orderNumber) {
        ProductOrder order = null;
        for (ProductOrder o : orders) {
            if (o.isOrder(orderNumber)) {
                order = o;
                break;
            }
        }

        if (order == null) {
            errMsg = String.format("Order %s Not Found", orderNumber);
            return false;
        }

        order.cancelOrder();
        orders.remove(order);

        return true;
    }

    /**
     * Sorts products by their price, in ascending order.
     */
    public void sortByPrice() {
        Collections.sort(products, (p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
    }

    /**
     * Sorts products by their name, in alphabetical order.
     */
    public void sortByName() {
        Collections.sort(products, (p1, p2) -> p1.getName().compareTo(p2.getName()));
    }

    /**
     * Sorts customers by their name, in alphabetical order.
     */
    public void sortCustomersByName() {
        Collections.sort(customers);
    }

    /**
     * Prints all books in the system that were written by a specific author. This
     * will also print them in ascending order based on the year they were
     * published.
     *
     * @param author The author to search for.
     */
    public void printBooksByAuthor(String author) {
        ArrayList<Book> books = new ArrayList<>();
        for (Product p : products) {
            if (p instanceof Book && ((Book) p).getAuthor().equals(author)) {
                books.add((Book) p); // hacky cast which will always pass due to the instanceof check
            }
        }

        Collections.sort(books, (b1, b2) -> Integer.compare(b1.getYear(), b2.getYear()));

        for (Book b : books) {
            System.out.print(b);
        }
    }
}
