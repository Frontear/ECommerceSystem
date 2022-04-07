import java.util.Scanner;

/**
 * This class represents the front-end of our ECommerceSystem. It allows the
 * user to interact with the system. From here, users can create and view
 * customers, view products and place orders, cancel orders, and ship them out
 * to customers.
 *
 * @author Ali Rizvi (501039655)
 * @see ECommerceSystem
 * @see Customers
 * @see Products
 * @see ProductOrder
 */
public class ECommerceUserInterface {
    public static void main(String[] args) {
        ECommerceSystem amazon = new ECommerceSystem();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print(">");

            while (scanner.hasNextLine()) {
                String line = normalize(scanner.nextLine());

                if (line == null || line.equals("")) {
                    System.out.print("\n>");
                    continue;
                } else if (line.equalsIgnoreCase("Q") || line.equalsIgnoreCase("QUIT")) {
                    return;
                } else if (line.equalsIgnoreCase("PRODS")) {
                    amazon.printAllProducts();
                } else if (line.equalsIgnoreCase("BOOKS")) {
                    amazon.printAllBooks();
                } else if (line.equalsIgnoreCase("SHOES")) {
                    amazon.printAllShoes();
                } else if (line.equalsIgnoreCase("CUSTS")) {
                    amazon.printCustomers();
                } else if (line.equalsIgnoreCase("ORDERS")) {
                    amazon.printAllOrders();
                } else if (line.equalsIgnoreCase("SHIPPED")) {
                    amazon.printAllShippedOrders();
                } else if (line.equalsIgnoreCase("NEWCUST")) {
                    String name = "";
                    String address = "";

                    System.out.print("Name: ");
                    if (scanner.hasNextLine()) {
                        name = normalize(scanner.nextLine());
                    }

                    System.out.print("\nAddress: ");
                    if (scanner.hasNextLine()) {
                        address = normalize(scanner.nextLine());
                    }

                    boolean success = amazon.createCustomer(name, address);
                    if (!success) {
                        System.out.println(amazon.getErrorMessage());
                    }
                } else if (line.equalsIgnoreCase("SHIP")) {
                    String orderNumber = "";

                    System.out.print("Order Number: ");
                    if (scanner.hasNextLine()) {
                        orderNumber = normalize(scanner.nextLine());
                    }

                    ProductOrder order = amazon.shipOrder(orderNumber);
                    if (order == null) {
                        System.out.println(amazon.getErrorMessage());
                    } else {
                        System.out.print(order);
                    }
                } else if (line.equalsIgnoreCase("CUSTORDERS")) {
                    String customerId = "";

                    System.out.print("Customer Id: ");
                    if (scanner.hasNextLine()) {
                        customerId = normalize(scanner.nextLine());
                    }

                    boolean success = amazon.printOrderHistory(customerId);
                    if (!success) {
                        System.out.println(amazon.getErrorMessage());
                    }
                } else if (line.equalsIgnoreCase("ORDER")) {
                    String productId = "";
                    String customerId = "";

                    System.out.print("Product Id: ");
                    if (scanner.hasNextLine()) {
                        productId = normalize(scanner.nextLine());
                    }

                    System.out.print("\nCustomer Id: ");
                    if (scanner.hasNextLine()) {
                        customerId = normalize(scanner.nextLine());
                    }

                    String orderNumber = amazon.orderProduct(productId, customerId, "");
                    if (orderNumber == null) {
                        System.out.println(amazon.getErrorMessage());
                    } else {
                        System.out.println(String.format("Order #%s", orderNumber));
                    }
                } else if (line.equalsIgnoreCase("ORDERBOOK")) {
                    String productId = "";
                    String customerId = "";
                    String options = "";

                    System.out.print("Product Id: ");
                    if (scanner.hasNextLine()) {
                        productId = normalize(scanner.nextLine());
                    }

                    System.out.print("\nCustomer Id: ");
                    if (scanner.hasNextLine()) {
                        customerId = normalize(scanner.nextLine());
                    }

                    System.out.print("\nFormat [Paperback Hardcover EBook]: ");
                    if (scanner.hasNextLine()) {
                        options = normalize(scanner.nextLine());
                    }

                    String orderNumber = amazon.orderProduct(productId, customerId, options);
                    if (orderNumber == null) {
                        System.out.println(amazon.getErrorMessage());
                    } else {
                        System.out.println(String.format("Order #%s", orderNumber));
                    }
                } else if (line.equalsIgnoreCase("ORDERSHOES")) {
                    String productId = "";
                    String customerId = "";
                    String options = "";

                    System.out.print("Product Id: ");
                    if (scanner.hasNextLine()) {
                        productId = normalize(scanner.nextLine());
                    }

                    System.out.print("\nCustomer Id: ");
                    if (scanner.hasNextLine()) {
                        customerId = normalize(scanner.nextLine());
                    }

                    System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
                    if (scanner.hasNextLine()) {
                        options = normalize(scanner.nextLine());
                    }

                    System.out.print("\nColor: \"Black\" \"Brown\": ");
                    if (scanner.hasNextLine()) {
                        options += " " + scanner.nextLine();
                    }

                    String orderNumber = amazon.orderProduct(productId, customerId, options);
                    if (orderNumber == null) {
                        System.out.println(amazon.getErrorMessage());
                    } else {
                        System.out.println(String.format("Order #%s", orderNumber));
                    }
                } else if (line.equalsIgnoreCase("CANCEL")) {
                    String orderNumber = "";

                    System.out.print("Order Number: ");
                    if (scanner.hasNextLine()) {
                        orderNumber = normalize(scanner.nextLine());
                    }

                    boolean success = amazon.cancelOrder(orderNumber);
                    if (!success) {
                        System.out.println(amazon.getErrorMessage());
                    }
                } else if (line.equalsIgnoreCase("PRINTBYPRICE")) {
                    amazon.printByPrice();
                } else if (line.equalsIgnoreCase("PRINTBYNAME")) {
                    amazon.printByName();
                } else if (line.equalsIgnoreCase("SORTCUSTS")) {
                    amazon.sortCustomersByName();
                } else if (line.equalsIgnoreCase("BOOKSBYAUTHOR")) {
                    String author = "";

                    System.out.print("Author: ");
                    if (scanner.hasNextLine()) {
                        author = normalize(scanner.nextLine());
                    }

                    amazon.printBooksByAuthor(author);
                }

                System.out.print("\n>");
            }
        }
    }

    // normalizes to remove whitespace. necessary to prevent "900 " or " 900" from
    // being interpreted as a different string, and to prevent " " of any length
    // from being accepted where empty data is not allowed (like customer names).
    private static String normalize(String line) {
        return line.trim();
    }
}
