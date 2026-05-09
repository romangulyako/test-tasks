package io.romangulyako.live_coding_training.stream_api.order_handler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Order {
    private String customer;
    private List<Item> items;
    private BigDecimal total;
    private LocalDate date;

    public Order(String customer, BigDecimal total, LocalDate date, List<Item> items) {
        this.customer = customer;
        this.total = total;
        this.date = date;
        this.items = items;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer='" + customer + '\'' +
                ", items=" + items +
                ", total=" + total +
                ", date=" + date +
                '}';
    }
}
