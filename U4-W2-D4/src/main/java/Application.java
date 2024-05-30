import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public class Application {
    public static void main (String[] args) {

        Product product1 = new Product("T-Shirt", "Clothes", 9.99);
        Product product2 = new Product("CD Queen", "Music", 25.00);
        Product product3 = new Product("Baby Bottle", "Baby", 15.79);
        Product product4 = new Product("CD Bob Marley", "Music", 30.79);
        Product product5 = new Product("Shorts", "Clothes", 35.89);
        Product product6 = new Product("SkateBoard", "Boys", 15.25);
        Product product7 = new Product("Pasta de Cecco", "Food & Beverage", 3.89);
        Product product8 = new Product("FranciaCorta", "Food & Beverage", 45);
        Product product9 = new Product("CD Janis Joplin", "Music", 25);
        Product product10 = new Product("Volley Ball", "Boys", 20);

        List <Product> Products = new ArrayList<>();
        Products.add(product1);
        Products.add(product2);
        Products.add(product3);
        Products.add(product4);
        Products.add(product5);
        Products.add(product6);
        Products.add(product7);
        Products.add(product8);
        Products.add(product9);
        Products.add(product10);

        Supplier<Customer> customerSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            Random random = new Random();
            return new Customer(faker.howIMetYourMother().character(), random.nextInt(5));
        };
        List<Customer> customerList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            customerList.add(customerSupplier.get());
        }
        customerList.forEach(System.out::println);

        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now().minusDays(5);
        LocalDate date3 = LocalDate.now().minus(150, ChronoUnit.DAYS);
        LocalDate date4 = LocalDate.now().plus(6, ChronoUnit.MONTHS);
        LocalDate date5 = LocalDate.now().minusYears(1).plus(4, ChronoUnit.DAYS);
        LocalDate delivery1 = date1.plusDays(3);
        LocalDate delivery2 = date2.plusDays(3);
        LocalDate delivery3 = date3.plusDays(3);
        LocalDate delivery4 = date4.plusDays(3);
        LocalDate delivery5 = date5.plusDays(3);

        Random random = new Random();

        Order order1 = new Order("Success", customerList.get(random.nextInt(9)), date1, delivery1);
        Order order2 = new Order("Success", customerList.get(random.nextInt(9)), date2, delivery2);
        Order order3 = new Order("Success", customerList.get(random.nextInt(9)), date3, delivery3);
        Order order4= new Order("Success", customerList.get(random.nextInt(9)), date4, delivery4);
        Order order5 = new Order("Success", customerList.get(random.nextInt(9)), date5, delivery5);

        order1.getProducts().add(product1);
        order1.getProducts().add(product2);
        order1.getProducts().add(product5);
        order2.getProducts().add(product8);
        order2.getProducts().add(product5);
        order3.getProducts().add(product6);
        order3.getProducts().add(product2);
        order3.getProducts().add(product9);
        order3.getProducts().add(product10);
        order4.getProducts().add(product9);
        order4.getProducts().add(product10);
        order4.getProducts().add(product1);
        order4.getProducts().add(product3);
        order5.getProducts().add(product2);
        order5.getProducts().add(product4);


        List<Order> OrderList = new ArrayList<>();
        OrderList.add(order1);
        OrderList.add(order2);
        OrderList.add(order3);
        OrderList.add(order4);
        OrderList.add(order5);

        //Esercizio 1
        System.out.println("ESERCIZIO !");
        System.out.println("");

        Map<Customer, List<Order>> CustomerOrder = OrderList.stream().collect(Collectors.groupingBy(Order::getCustomer));
        CustomerOrder.forEach((customer, orders ) -> {
            System.out.println("Customer: "+customer);
            System.out.println("Orders: ");
            orders.forEach(System.out::println);

            System.out.println("");
            System.out.println("ESERCIZIO 2");
            System.out.println("");

        } );
        Map<Customer, Double> expenseXCustomer = OrderList.stream()
                .collect(Collectors.groupingBy(Order::getCustomer,
                        Collectors.summingDouble(order -> order.getProducts().stream()
                                .mapToDouble(Product::getPrice).sum())));
        expenseXCustomer.forEach((customer, totalSum) -> {
            System.out.println("Customer: " + customer.getName() + ", Total Shopping Cart: " + totalSum + "€");
        });

        System.out.println("");
        System.out.println("Esercizio 3");
        System.out.println("");

        List<Product> ExpensiveItems = Products.stream().sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .limit(3).toList();
        System.out.println(ExpensiveItems);
        System.out.println("");
        System.out.println("Esercizio 4");

        Double averagePrice = Products.stream().mapToDouble(Product::getPrice).average().getAsDouble();

        System.out.println(averagePrice);

    }
}
