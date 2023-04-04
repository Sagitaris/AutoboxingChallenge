package dev.lpa;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Customer bob = new Customer("Bob S", 1000.0);
        System.out.println(bob);

        Bank bank = new Bank("Chase");
        bank.addANewCustomer("Jane A", 500.0);
        System.out.println(bank);

        bank.addTransaction("Jane A", -10.25);
        bank.addTransaction("Jane A", -75.01);
        bank.printCustomer("Jane a");

        bank.addANewCustomer("Bob S", 25.00);
        bank.addTransaction("Bob S", 100.00);
        bank.printCustomer("Bob S");
    }
}


record Customer(String name, ArrayList<Double> transactions) {

    public Customer(String name, double initialDeposit) {
        this(name.toUpperCase(),
                new ArrayList<Double>(500));
        transactions.add(initialDeposit);
    }
}

class Bank {
    private String name;
    private ArrayList<Customer> customers = new ArrayList<>(5000);


    public Bank(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "name='" + name + '\'' +
                ", customers=" + customers +
                '}';
    }

    public void addANewCustomer(String customerName, double initialDeposit) {

        if (getCustomer(customerName) == null) {
            Customer customer = new Customer(customerName, initialDeposit);
            customers.add(customer);
            System.out.println("New Customer added: " + customer);
        } else {
            System.out.println("Customer " + customerName + " is already in bank database");
            return;
        }


    }

    public void addTransaction(String name, Double transaction) {

        Customer customer = getCustomer(name);
        if (customer != null) {
            customer.transactions().add(transaction);
        }
    }

    public void printCustomer(String name){

        Customer customer = getCustomer(name);

        if (customer == null) {
            return;
        }

        System.out.println("-".repeat(30));
        System.out.println("Customer Name: " + customer.name());
        System.out.println("Transactions: ");
        for (double d : customer.transactions()) {      //unboxing
            System.out.printf("$%10.2f (%s)%n", d, d < 0 ? "debit" : "credit");
        }

    }

    private Customer getCustomer(String customerName) {

        for(var customer : customers) {
            if (customer.name().equalsIgnoreCase(customerName)) {
                return customer;
            }
        }
        System.out.printf("Customer (%s) wasn't found %n", customerName);

        return null;
    }
}
