package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MetricsTest {
    private List<Order> orders;


    @BeforeEach
    void setUp() {
        Customer customer1 = new Customer("C001", "Alice", "alice@example.com", LocalDateTime.now().minusYears(2), 30, "New York");
        Customer customer2 = new Customer("C002", "Bob", "bob@example.com", LocalDateTime.now().minusYears(1), 25, "Los Angeles");
        Customer customer3 = new Customer("C003", "Charlie", "charlie@example.com", LocalDateTime.now().minusMonths(6), 40, "Chicago");
        Customer customer4 = new Customer("C004", "Diana", "diana@example.com", LocalDateTime.now().minusMonths(3), 22, "New York");



        OrderItem item1 = new OrderItem("Laptop", 1, 1200, Category.ELECTRONICS);
        OrderItem item2 = new OrderItem("T-Shirt", 2, 25, Category.CLOTHING);
        OrderItem item3 = new OrderItem("Book", 3, 15, Category.BOOKS);
        OrderItem item4 = new OrderItem("Vacuum Cleaner", 1, 200, Category.HOME);
        OrderItem item5 = new OrderItem("Lipstick", 2, 20, Category.BEAUTY);
        OrderItem item6 = new OrderItem("Toy Car", 4, 10, Category.TOYS);
        OrderItem item7 = new OrderItem("Laptop", 2, 1100, Category.ELECTRONICS);

        orders = new ArrayList<>();

        orders.add(new Order("O001", LocalDateTime.now().minusDays(10), customer1, List.of(item1, item2), OrderStatus.DELIVERED));
        orders.add(new Order("O002", LocalDateTime.now().minusDays(8), customer2, List.of(item3), OrderStatus.CANCELLED));
        orders.add(new Order("O003", LocalDateTime.now().minusDays(6), customer1, List.of(item4), OrderStatus.SHIPPED));
        orders.add(new Order("O004", LocalDateTime.now().minusDays(4), customer3, List.of(item5, item6), OrderStatus.DELIVERED));
        orders.add(new Order("O005", LocalDateTime.now().minusDays(2), customer1, List.of(item7), OrderStatus.DELIVERED));
        orders.add(new Order("O006", LocalDateTime.now().minusDays(1), customer1, List.of(item2, item3), OrderStatus.NEW));
        orders.add(new Order("O007", LocalDateTime.now().minusDays(3), customer4, List.of(item5), OrderStatus.DELIVERED));
        orders.add(new Order("O008", LocalDateTime.now().minusDays(5), customer1, List.of(item6), OrderStatus.DELIVERED));
        orders.add(new Order("O009", LocalDateTime.now().minusDays(7), customer1, List.of(item3), OrderStatus.DELIVERED));
        orders.add(new Order("O010", LocalDateTime.now().minusDays(9), customer1, List.of(item1), OrderStatus.PROCESSING));
    }


    @Test
    void uniqueCitiesTest() {
        List<String> uniq = orders.stream()
                .map(order -> order.getCustomer().getCity())
                .distinct()
                .toList();


        assertTrue(uniq.contains("New York"));
        assertTrue(uniq.contains("Los Angeles"));
        assertTrue(uniq.contains("Chicago"));

        assertEquals(3, uniq.size());
    }

    @Test
    void totalIncome() {

        double totalIncome = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(OrderItem::getPrice)
                .sum();

        assertEquals(20.0, totalIncome);
    }


    @Test
    void mostPopularItem() {
        String mostPopularItem = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        OrderItem::getProductName,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        assertEquals("Laptop", mostPopularItem);
    }


    @Test
    void averageCheck(){
        double averageCheck = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getItems().stream()
                        .mapToDouble(OrderItem::getPrice)
                        .sum())
                .average()
                .orElse(0);


        System.out.println(averageCheck);
    }



    @Test
    void customersWhoHaveMoreThen5Orders() {
        List<String> customers = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCustomer().getCustomerId(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        assertEquals(List.of("C001"), customers);
    }





}
