package com.tomczyk;

import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

public class EmployeeService {

    private Connection conn = null;
    private Statement stmt = null;

    public EmployeeService() {
        try {
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/sdp", "postgres", "postgres");
            this.conn.setAutoCommit(false);
            this.stmt = conn.createStatement();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void insert(int numberOfEmloyees) {
        try {
            for(int i = 0; i < numberOfEmloyees; i++) {
                Faker plFaker = new Faker((new Locale("pl")));

                String sql = String.format("INSERT INTO company.employees (name, surname, phone)\n" +
                        "    VALUES ('%s', '%s', '%s');", plFaker.name().firstName(), plFaker.name().lastName(), plFaker.phoneNumber().cellPhone());
                stmt.executeUpdate(sql);
            }

            conn.commit();

            System.out.println(String.format("Successfully inserted %d employees", numberOfEmloyees));
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void delete(int employeeId) {
        try {
            String sql = String.format("DELETE from company.employees where id = %d;", employeeId);
            stmt.executeUpdate(sql);
            conn.commit();

            System.out.println(String.format("Successfully deleted employee with id: %d", employeeId));
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void select(int limit) {
        try {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM company.employees LIMIT %d;", limit));
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phone = rs.getString("phone");
                System.out.println("ID = " + id);
                System.out.println("NAME = " + name);
                System.out.println("SURNAME = " + surname);
                System.out.println("PHONE = " + phone);
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void cleanUp() {
        try {
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }
}
