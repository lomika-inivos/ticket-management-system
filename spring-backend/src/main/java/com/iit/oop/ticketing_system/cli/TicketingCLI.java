package com.iit.oop.ticketing_system.cli;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.util.Scanner;

@Component
public class TicketingCLI implements CommandLineRunner {





    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Real-Time Ticketing System!");


        System.out.print("Enter number of vendors: ");

        System.out.print("Enter number of customers: ");

        System.out.print("Enter max ticket capacity: ");

        System.out.print("Enter total tickets for vendors: ");

        System.out.print("Enter ticket release rate for vendors (seconds): ");

        System.out.print("Enter customer retrieval rate (seconds): ");




    }
}
