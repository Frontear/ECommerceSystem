import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    private final Map<String, Product> products = new TreeMap<>();

    private final Map<String, Customer> customers = new TreeMap<>();
    private final List<Customer> sortedCustomers = new ArrayList<>(0); // remains empty until sortCustomersByName() is
                                                                       // called

    private final Map<String, ProductOrder> orders = new TreeMap<>();
    private final Map<String, ProductOrder> shippedOrders = new TreeMap<>();

    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;

    /**
     * Constructs a new ECommerceSystem. This constructor will generate a variety of
     * pre-defined products and customers.
     */
    public ECommerceSystem() {
        String id;
        /*
         * products.put(id = generateProductId(), new Product("Acer Laptop", id, 989.0,
         * 99, Product.Category.COMPUTERS));
         * products.put(id = generateProductId(), new Product("Apex Desk", id, 1378.0,
         * 12, Product.Category.FURNITURE));
         * products.put(id = generateProductId(),
         * new Book("Book", id, 45.0, 4, 2, "Ahm Gonna Make You Learn", "T. McInerney",
         * 1999));
         * products.put(id = generateProductId(), new Product("DadBod Jeans", id, 24.0,
         * 50, Product.Category.CLOTHING));
         * products.put(id = generateProductId(), new Product("Polo High Socks", id,
         * 5.0, 199, Product.Category.CLOTHING));
         * products.put(id = generateProductId(), new Product("Tightie Whities", id,
         * 15.0, 99, Product.Category.CLOTHING));
         * products.put(id = generateProductId(),
         * new Book("Book", id, 35.0, 4, 2, "How to Fool Your Prof", "D. Umbast",
         * 2002));
         * products.put(id = generateProductId(),
         * new Book("Book", id, 45.0, 4, 2, "How to Escape from Prison", "A. Fugitive",
         * 1996));
         * products.put(id = generateProductId(),
         * new Book("Book", id, 44.0, 14, 12, "Ahm Gonna Make You Learn More",
         * "T. McInerney", 2004));
         * products.put(id = generateProductId(), new Product("Rock Hammer", id, 10.0,
         * 22, Product.Category.GENERAL));
         * products.put(id = generateProductId(), new Shoes("Nike Shoes", id, 40.0,
         * Map.of("black", new int[] { 50, 60, 65, 80, 90 }, "brown", new int[] { 40,
         * 45, 55, 75, 80 })));
         * products.put(id = generateProductId(), new Shoes("Adidas Premium", id, 155.0,
         * Map.of("black", new int[] { 5, 5, 4, 8, 10 }, "brown", new int[] { 5, 4, 6,
         * 5, 3 })));
         */

        customers.put(id = generateCustomerId(), new Customer(id, "Inigo Montoya", "1 SwordMaker Lane, Florin"));
        customers.put(id = generateCustomerId(), new Customer(id, "Prince Humperdinck", "The Castle, Florin"));
        customers.put(id = generateCustomerId(), new Customer(id, "Andy Dufresne", "Shawshank Prison, Maine"));
        customers.put(id = generateCustomerId(),
                new Customer(id, "Ferris Bueller", "4160 Country Club Drive, Long Beach"));

        try (BufferedReader reader = Files.newBufferedReader(Paths.get("products.txt"))) {
            String[] lines = new String[5]; // 5 lines per product

            for (int i = 0; (lines[i] = reader.readLine()) != null; i = (i + 1) % lines.length) {
                if (i == lines.length - 1) {
                    Product.Category category = Product.Category.valueOf(lines[0]);
                    String name = lines[1];
                    double price = Double.parseDouble(lines[2]);

                    if (category == Product.Category.BOOKS) {
                        String[] stock = lines[3].split(" ");

                        int paperbackStock = Integer.parseInt(stock[0]);
                        int hardcoverStock = Integer.parseInt(stock[1]);

                        String[] info = lines[4].split(":");

                        String title = info[0];
                        String author = info[1];
                        int year = Integer.parseInt(info[2]);

                        products.put(id = generateProductId(),
                                new Book(name, id, price, paperbackStock, hardcoverStock, title, author, year));
                    } else {
                        int stock = Integer.parseInt(lines[3]);

                        products.put(id = generateProductId(), new Product(name, id, price, stock, category));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
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
     * Prints out all of the products in the system.
     */
    public void printAllProducts() {
        for (Product p : products.values()) {
            System.out.print(p);
        }
    }

    /**
     * Prints out all of the products in the system that are books.
     */
    public void printAllBooks() {
        for (Product p : products.values()) {
            if (p instanceof Book) {
                System.out.print(p);
            }
        }
    }

    /**
     * Prints out all of the products in the system that are shoes.
     */
    public void printAllShoes() {
        for (Product p : products.values()) {
            if (p instanceof Shoes) {
                System.out.print(p);
            }
        }
    }

    /**
     * Prints all active orders in the system.
     */
    public void printAllOrders() {
        for (ProductOrder o : orders.values()) {
            System.out.print(o);
        }
    }

    /**
     * Prints all shipped orders in the system.
     */
    public void printAllShippedOrders() {
        for (ProductOrder o : shippedOrders.values()) {
            System.out.print(o);
        }
    }

    /**
     * Prints all the customers in the system.
     */
    public void printCustomers() {
        for (Customer c : customers.values()) {
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
        if (!customers.containsKey(customerId)) {
            throw new Customer.NotFoundException(customerId);
        }

        System.out.println("Current Orders of Customer " + customerId);
        for (ProductOrder o : orders.values()) {
            if (o.fromCustomer(customerId)) {
                System.out.print(o);
            }
        }

        System.out.println("\nShipped Orders of Customer " + customerId);
        for (ProductOrder o : shippedOrders.values()) {
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
        Product product = products.get(productId);
        Customer customer = customers.get(customerId);

        if (product == null) {
            throw new Product.NotFoundException(productId);
        }

        if (customer == null) {
            throw new Customer.NotFoundException(customerId);
        }

        if (!product.validOptions(productOptions)) {
            throw new Product.InvalidOptionsException(product, productOptions);
        }

        if (!product.hasStock(productOptions)) {
            throw new Product.NoStockException(product);
        }

        String orderNumber = generateOrderNumber();
        ProductOrder order = new ProductOrder(orderNumber, product, customer, productOptions);

        product.reduceStock(productOptions);
        orders.put(orderNumber, order);

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
            throw new Customer.InvalidNameException();
        }

        if (address == null || address.equals("")) {
            throw new Customer.InvalidAddressException();
        }

        String id;
        customers.put(id = generateCustomerId(), new Customer(id, name, address));

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
        ProductOrder order = orders.get(orderNumber);

        if (order == null) {
            throw new ProductOrder.NotFoundException(orderNumber);
        }

        orders.remove(orderNumber);
        shippedOrders.put(orderNumber, order);

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
        ProductOrder order = orders.get(orderNumber);

        if (order == null) {
            throw new ProductOrder.NotFoundException(orderNumber);
        }

        order.cancelOrder();
        orders.remove(orderNumber);

        return true;
    }

    /**
     * Sorts products by their price, in ascending order.
     */
    public void printByPrice() {
        List<Product> sortedProducts = new ArrayList<>(products.values());
        Collections.sort(sortedProducts, (p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));

        for (Product p : sortedProducts) {
            System.out.print(p);
        }
    }

    /**
     * Sorts products by their name, in alphabetical order.
     */
    public void printByName() {
        List<Product> sortedProducts = new ArrayList<>(products.values());
        Collections.sort(sortedProducts, (p1, p2) -> p1.getName().compareTo(p2.getName()));

        for (Product p : sortedProducts) {
            System.out.print(p);
        }
    }

    /**
     * Sorts customers by their name, in alphabetical order.
     */
    public void sortCustomersByName() {
        sortedCustomers.clear();
        sortedCustomers.addAll(customers.values());

        Collections.sort(sortedCustomers);
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
        for (Product p : products.values()) {
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
