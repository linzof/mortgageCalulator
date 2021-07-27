package com.linzof;

import java.text.NumberFormat; //convert into currency
import java.util.Scanner; //to read the user input
public class Main {
    final static byte MONTHS_IN_YEAR = 12; //constants
    final static byte PERCENT = 100; //constants

    public static void main(String[] args) {
        int principal = (int) readNumber("Principal: ", 1000, 1_000_000); // listed in sub methods
        float annualInterest = (float) readNumber("Annual Interest Rate: ", 1, 30); // listed in sub methods
        byte years = (byte) readNumber("Period (Years): ", 1, 30); // listed in sub methods

        printCalculateMortgage(principal, annualInterest, years); // refactored the calculate mortgage
        printPaymentSchedule(principal, annualInterest, years); // refactored the payment schedule
    }

    // calculate mortgage method
    private static void printCalculateMortgage(int principal, float annualInterest, byte years) {
        double mortgage = calculateMortgage(principal, annualInterest, years);
        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);
        System.out.println();
        System.out.println("Mortgage");
        System.out.println("--------");
        System.out.println("Monthly Payments: " + mortgageFormatted);
    }

    // payment schedule method
    private static void printPaymentSchedule(int principal, float annualInterest, byte years) {
        System.out.println();
        System.out.println("PAYMENT SCHEDULE");
        System.out.println("----------------");
        for (short month = 1; month <= years * MONTHS_IN_YEAR; month++) {
            double balance = calculateBalance(principal, annualInterest, years, month);
            System.out.println(NumberFormat.getCurrencyInstance().format(balance));
        }
    }
    // input validation
    public static double readNumber(String prompt, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double value;
        while (true) {
            System.out.println(prompt);
            value = scanner.nextFloat();
            if (value >= min && value <= max)
                break;
            System.out.println("Enter a value between " + min + " and " + max);
        }
        return value;
    }

    // calculate the remainder balance method using this formula { B = L[(1 + c)^n - (1 + c)^p]/[(1 + c)^n - 1] } https://mtgprofessor.com/FORMULAS.HTM
    public static double calculateBalance(
            int principal,
            float annualInterest,
            byte years,
            short numberOfPaymentsMade
    ){


        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        short numberOfPayments = (short) (years * MONTHS_IN_YEAR);

        double balance = principal *
                (Math.pow(1+ monthlyInterest, numberOfPayments) - Math.pow(1 + monthlyInterest, numberOfPaymentsMade))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);
        return balance;
    }

    //calculate the mortgage method using this formula  { M= P * { {r * (1+r)^n} / {(1+r)^n - 1} } https://www.wikihow.com/Calculate-Mortgage-Payments
    public static double calculateMortgage(
            int principal,
            float annualInterest,
            byte years) {


        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        short numberOfPayments = (short) (years * MONTHS_IN_YEAR);
        double mortgage = principal
                * (monthlyInterest * Math.pow(1 + monthlyInterest, numberOfPayments))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);

        return mortgage;

    }
}

