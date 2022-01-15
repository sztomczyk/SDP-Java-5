package com.tomczyk;

public class Main {

    public static void main(String[] args) {
        EmployeeService es = new EmployeeService();
        es.insert(10);
        es.delete(24);
        es.select(100);
        es.cleanUp();
    }
}
