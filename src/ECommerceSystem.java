import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

    private final List<Customer> customers = new ArrayList<>();

    private final Map<String, ProductOrder> orders = new TreeMap<>();
    private final Map<String, ProductOrder> shippedOrders = new TreeMap<>();

    private final Map<String, Integer> stats = new TreeMap<>();
    private final Map<Integer, List<String>> ratings = new TreeMap<>();

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

        customers.add(new Customer(id = generateCustomerId(), "Inigo Montoya", "1 SwordMaker Lane, Florin"));
        customers.add(new Customer(id = generateCustomerId(), "Prince Humperdinck", "The Castle, Florin"));
        customers.add(new Customer(id = generateCustomerId(), "Andy Dufresne", "Shawshank Prison, Maine"));
        customers.add(new Customer(id = generateCustomerId(), "Ferris Bueller", "4160 Country Club Drive, Long Beach"));

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
            System.out.println(e.getMessage());
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
     *
     * @throws Customer.NotFoundException If the customer with the given ID does not
     *                                    exist.
     */
    public void printOrderHistory(String customerId) {
        boolean found = false;
        for (Customer c : customers) {
            if (c.getId().equals(customerId)) {
                found = true;
                break; // break early, customer has been found
            }
        }

        if (!found) {
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
    }

    /**
     * Orders a specified product for a specified customer with the given product
     * options. Validates product stock and options, exceptions are thrown if
     * neither is valid.
     *
     * @param productId      The product ID of the product to be ordered.
     * @param customerId     The customer ID of the customer who is ordering the
     *                       product.
     * @param productOptions The product options to be used when ordering the
     *                       product.
     * @return The order number of the order.
     *
     * @throws Product.NotFoundException       If the product ID is not found.
     * @throws Customer.NotFoundException      If the customer ID is not found.
     * @throws Product.InvalidOptionsException If the product options are invalid.
     * @throws Product.NoStockException        If the product is out of stock.
     */
    public String orderProduct(String productId, String customerId, String productOptions) {
        Product product = products.get(productId);
        Customer customer = null;

        for (Customer c : customers) {
            if (c.getId().equals(customerId)) {
                customer = c;
                break;
            }
        }

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

        stats.put(productId, stats.getOrDefault(productId, 0) + 1);

        return orderNumber;
    }

    /**
     * Creates a new customer and saves their data to the system. Validates inputs,
     * and throws an exception is they cannot be validated.
     *
     * @param name    The name of the customer.
     * @param address The address of the customer.
     *
     * @throws Customer.InvalidNameException    If the name is invalid.
     * @throws Customer.InvalidAddressException If the address is invalid.
     */
    public void createCustomer(String name, String address) {
        if (name == null || name.equals("")) {
            throw new Customer.InvalidNameException();
        }

        if (address == null || address.equals("")) {
            throw new Customer.InvalidAddressException();
        }

        customers.add(new Customer(generateCustomerId(), name, address));
    }

    /**
     * Ships an active order. This will remove the order from the active orders and
     * place it into a separate list of shipped orders. This cannot be used with
     * orders that were cancelled or have already been shipped. If the order cannot
     * be found, an exception is thrown.
     *
     * @param orderNumber The order number of the order to be shipped.
     * @return An instance of the shipped order.
     *
     * @throws ProductOrder.NotFoundException If the order cannot be found.
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
     * shipped. If the order is not found, this will throw an exception.
     *
     * @param orderNumber The order number of the order to be cancelled.
     *
     * @throws ProductOrder.NotFoundException If the order cannot be found.
     */
    public void cancelOrder(String orderNumber) {
        ProductOrder order = orders.get(orderNumber);

        if (order == null) {
            throw new ProductOrder.NotFoundException(orderNumber);
        }

        order.cancelOrder();
        orders.remove(orderNumber);

        stats.put(order.getProductId(), stats.getOrDefault(order.getProductId(), 0) - 1);
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

    /**
     * Adds an item to a customers cart with specific options. Options are validated
     * and an exception is thrown if they are not valid. If the item is not in
     * stock, an exception is also thrown. Once everything is validated, the item is
     * added to the customer's cart.
     *
     * @param productId      The product ID of the item to be added.
     * @param customerId     The customer ID of the customer who is adding the item.
     * @param productOptions The product options to be used when adding the item.
     *
     * @throws Product.NotFoundException       If the product ID is not found.
     * @throws Product.InvalidOptionsException If the product options are invalid.
     * @throws Product.NoStockException        If the product is not in stock.
     * @throws Customer.NotFoundException      If the customer ID is not found.
     */
    public void addToCart(String productId, String customerId, String productOptions) {
        Product product = products.get(productId);
        Customer customer = null;

        for (Customer c : customers) {
            if (c.getId().equals(customerId)) {
                customer = c;
                break;
            }
        }

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

        customer.getCart().getItems().add(new CartItem(product, productOptions));
    }

    /**
     * Removes an item from a customers cart. If the item is not in the cart, an
     * exception is thrown.
     *
     * @param productId  The product ID of the item to be removed.
     * @param customerId The customer ID of the customer who is removing the item.
     *
     * @throws Product.NotFoundException  If the product ID is not found.
     * @throws Customer.NotFoundException If the customer ID is not found.
     */
    public void removeFromCart(String productId, String customerId) {
        Product product = products.get(productId);
        Customer customer = null;

        for (Customer c : customers) {
            if (c.getId().equals(customerId)) {
                customer = c;
                break;
            }
        }

        if (product == null) {
            throw new Product.NotFoundException(productId);
        }

        if (customer == null) {
            throw new Customer.NotFoundException(customerId);
        }

        customer.getCart().removeItem(productId);
    }

    /**
     * Prints a customers cart.
     *
     * @param customerId The customer ID of the customer who's cart is to be
     *                   printed.
     *
     * @throws Customer.NotFoundException If the customer ID is not found.
     */
    public void printCart(String customerId) {
        Customer customer = null;

        for (Customer c : customers) {
            if (c.getId().equals(customerId)) {
                customer = c;
                break;
            }
        }

        if (customer == null) {
            throw new Customer.NotFoundException(customerId);
        }

        for (CartItem item : customer.getCart().getItems()) {
            System.out.print(String.format("\nCustomer Id: %3s Product Id: %3s Product Name: %12s Options: %8s",
                    customerId, item.getProduct().getId(), item.getProduct().getName(), item.getOptions()));
        }
    }

    /**
     * Orders all the items from a customers cart, clearing out their cart. Stock is
     * validated once again to ensure items still exist, and an exception is thrown
     * is there is no stock available. Once the item is ordered, it is removed from
     * the customers cart.
     *
     * @param customerId The customer ID of the customer who's cart is to be
     *                   ordered.
     *
     * @throws Customer.NotFoundException If the customer ID is not found.
     * @throws Product.NoStockException   If the product is not in stock.
     */
    public void orderItems(String customerId) {
        Customer customer = null;

        for (Customer c : customers) {
            if (c.getId().equals(customerId)) {
                customer = c;
                break;
            }
        }

        if (customer == null) {
            throw new Customer.NotFoundException(customerId);
        }

        Iterator<CartItem> it = customer.getCart().getItems().iterator();
        while (it.hasNext()) {
            CartItem item = it.next();

            if (!item.getProduct().hasStock(item.getOptions())) {
                throw new Product.NoStockException(item.getProduct());
            }

            String orderNumber = generateOrderNumber();
            ProductOrder order = new ProductOrder(orderNumber, item.getProduct(), customer, item.getOptions());

            item.getProduct().reduceStock(item.getOptions());
            orders.put(orderNumber, order);

            it.remove();
        }
    }

    /**
     * Prints out statistics for how many items a product has been ordered through
     * the system.
     */
    public void printStats() {
        List<Entry<String, Integer>> statList = new ArrayList<>(stats.entrySet());

        Collections.sort(statList, (e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        for (Entry<String, Integer> stat : statList) {
            String productId = stat.getKey();
            int count = stat.getValue();

            System.out.print(String.format("\nName: %-20s ID: %3s Ordered: %d", products.get(productId).getName(),
                    productId, count));
        }
    }

    /**
     * Adds a rating to a specific rating. If the rating is not found, an exception
     * is thrown.
     *
     * @param productId The product ID of the product being rated.
     * @param rating    The rating to be added.
     *
     * @throws Product.NotFoundException      If the product ID is not found.
     * @throws Product.InvalidRatingException If the rating is invalid.
     */
    public void addRating(String productId, int rating) {
        Product product = products.get(productId);

        if (product == null) {
            throw new Product.NotFoundException(productId);
        }

        if (rating < 1 || rating > 5) {
            throw new Product.InvalidRatingException(product, rating);
        }

        if (!ratings.containsKey(rating)) {
            ratings.put(rating, new ArrayList<>());
        }

        ratings.get(rating).add(productId);
    }

    /**
     * Prints out the ratings for a specified product.
     *
     * @param productId The product ID of the product being rated.
     *
     * @throws Product.NotFoundException If the product ID is not found.
     */
    public void printRatingsByProductId(String productId) {
        Product product = products.get(productId);

        if (product == null) {
            throw new Product.NotFoundException(productId);
        }

        System.out.println(String.format("\nProduct Id: %3s Product Name: %12s", productId, product.getName()));
        for (int i = 1; i <= 5; i++) {
            if (!ratings.containsKey(i)) {
                continue;
            }

            int count = 0;
            for (String id : ratings.get(i)) {
                if (id.equals(productId)) {
                    count++;
                }
            }

            System.out.print(String.format("%dx %s", count, new String(new char[i]).replace("\0", "*")));
            System.out.println();
        }
    }

    /**
     * Prints out the ratings for all products in a specified category.
     *
     * @param category  The category of the products being rated.
     * @param threshold The minimum number of ratings required for a product to be
     */
    public void printRatingsByCategory(Product.Category category, int threshold) {
        for (Product p : products.values()) {
            if (p.getCategory().equals(category)) {
                double average = 0;
                int total = 0;

                for (int i = 1; i <= 5; i++) {
                    if (!ratings.containsKey(i)) {
                        continue;
                    }

                    long r = ratings.get(i).stream().filter(x -> x.equals(p.getId())).count(); // get the count of how
                                                                                               // many ratings for each
                                                                                               // product

                    average += r * i;
                    total += r;
                }

                if (total <= 0) {
                    continue;
                }

                average /= total;

                if (average >= threshold) {
                    System.out.print(String.format("\nProduct Id: %3s Product Name: %12s Average Rating: %.2f",
                            p.getId(), p.getName(), average));
                }
            }
        }
    }

    /**
     * Utility method to find the type of a product based off of its productId. If a
     * product cannot be found, it throws an exception.
     *
     * @param productId The productId of the product to find the type of.
     * @return The type of the product.
     *
     * @throws Product.NotFoundException If the product cannot be found.
     */
    public Class<? extends Product> getProductType(String productId) {
        Product product = products.get(productId);
        if (product == null) {
            throw new Product.NotFoundException(productId);
        }

        return product.getClass();
    }
}
