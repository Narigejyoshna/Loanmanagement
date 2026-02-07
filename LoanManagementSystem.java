import java.util.Scanner;

class Loan {
    private double principalAmount;
    private double annualInterestRate;
    private int loanTenure;
    private String loanType;

    // Constructor to initialize loan details
    public Loan(double principalAmount, double annualInterestRate, int loanTenure, String loanType) {
        this.principalAmount = principalAmount;
        this.annualInterestRate = annualInterestRate;
        this.loanTenure = loanTenure;
        this.loanType = loanType;
    }

    // Method to calculate the monthly EMI
    public double calculateEMI() {
        double monthlyRate = annualInterestRate / (12 * 100); // Convert annual rate to monthly rate
        // EMI formula: EMI = P * r * (1 + r)^n / [(1 + r)^n - 1]
        return (principalAmount * monthlyRate * Math.pow(1 + monthlyRate, loanTenure)) /
               (Math.pow(1 + monthlyRate, loanTenure) - 1);
    }

    // Method to display the EMI breakdown over the loan tenure
    public void displayEMIBreakdown() {
        double emi = calculateEMI();
        double remainingBalance = principalAmount;
        double monthlyRate = annualInterestRate / (12 * 100);

        System.out.println("\nLoan Repayment Schedule:");
        System.out.println("----------------------------------------------------");
        System.out.println("Loan Type: " + loanType);
        System.out.println("Principal Amount: " + String.format("%.2f", principalAmount));
        System.out.println("Annual Interest Rate: " + annualInterestRate + "%");
        System.out.println("Loan Tenure: " + loanTenure + " months");
        System.out.println("----------------------------------------------------");
        System.out.println("EMI (Monthly Payment): " + String.format("%.2f", emi));
        System.out.println("----------------------------------------------------");

        // Loop through each month to calculate the breakdown
        for (int month = 1; month <= loanTenure; month++) {
            double interestPayment = remainingBalance * monthlyRate;
            double principalPayment = emi - interestPayment;
            remainingBalance -= principalPayment;

            System.out.println("Month " + month + ": ");
            System.out.println("  EMI: " + String.format("%.2f", emi));
            System.out.println("  Interest Payment: " + String.format("%.2f", interestPayment));
            System.out.println("  Principal Payment: " + String.format("%.2f", principalPayment));
            System.out.println("  Remaining Balance: " + String.format("%.2f", remainingBalance));
            System.out.println("----------------------------------------------------");
        }
    }
}

public class LoanManagementSystem {

    // Method to handle user input and create a loan object
    public static Loan getUserLoanDetails() {
        Scanner scanner = new Scanner(System.in);
        double principalAmount = 0;
        double annualInterestRate = 0;
        int loanTenure = 0;
        String loanType = "";
        int age = 0;

        // Get and validate the Age of the user
        while (true) {
            try {
                System.out.print("Enter your Age: ");
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0 || age >= 60) {
                    System.out.println("Sorry, the loan is only available for individuals under 60 years of age.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid numeric value for Age.");
            }
        }

        // Get and validate the Loan Type
        while (true) {
            System.out.println("Select Loan Type: ");
            System.out.println("1. Education Loan");
            System.out.println("2. Personal Loan");
            System.out.println("3. Gold Loan");
            System.out.println("4. Agricultural Loan");
            System.out.println("5. Others Loan (Home, Business, Car Loan)");
            System.out.print("Enter the number corresponding to your loan type: ");
            String loanTypeChoice = scanner.nextLine();
            switch (loanTypeChoice) {
                case "1":
                    loanType = "Education Loan";
                    annualInterestRate = 6.0; // Education Loan
                    break;
                case "2":
                    loanType = "Personal Loan";
                    annualInterestRate = 12.5; // Personal Loan
                    break;
                case "3":
                    loanType = "Gold Loan";
                    annualInterestRate = 9.0; // Gold Loan
                    break;
                case "4":
                    loanType = "Agricultural Loan";
                    annualInterestRate = 7.0; // Agricultural Loan
                    break;
                case "5":
                    loanType = "Others Loan";
                    annualInterestRate = 9.0; // Default for Home, Business, and Car Loan
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid loan type.");
                    continue;
            }
            break;
        }

        // Adjust loan tenure based on loan type
        while (true) {
            try {
                System.out.print("Enter the Loan Tenure (in months): ");
                loanTenure = Integer.parseInt(scanner.nextLine());

                // Validating loan tenure based on loan type
                if (loanType.equals("Education Loan") && loanTenure > 180) {
                    System.out.println("Education Loan tenure cannot exceed 180 months (15 years).");
                    continue;
                } else if (loanType.equals("Personal Loan") && loanTenure > 60) {
                    System.out.println("Personal Loan tenure cannot exceed 60 months (5 years).");
                    continue;
                } else if (loanType.equals("Gold Loan") && loanTenure > 36) {
                    System.out.println("Gold Loan tenure cannot exceed 36 months (3 years).");
                    continue;
                } else if (loanType.equals("Agricultural Loan") && loanTenure > 120) {
                    System.out.println("Agricultural Loan tenure cannot exceed 120 months (10 years).");
                    continue;
                } else if (loanType.equals("Others Loan") && loanTenure > 120) {
                    System.out.println("Others Loan tenure cannot exceed 120 months (10 years).");
                    continue;
                }

                if (loanTenure <= 0) {
                    System.out.println("Loan Tenure must be a positive integer value. Please try again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer for the Loan Tenure.");
            }
        }

        // Get and validate the Principal Amount
        while (true) {
            try {
                System.out.print("Enter the Principal Amount (in your currency): ");
                principalAmount = Double.parseDouble(scanner.nextLine());
                if (principalAmount <= 0) {
                    System.out.println("The Principal Amount must be a positive value. Please try again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid numeric value for the Principal Amount.");
            }
        }

        // Create a Loan object and return it
        return new Loan(principalAmount, annualInterestRate, loanTenure, loanType);
    }

    // Main method to drive the program
    public static void main(String[] args) {
        System.out.println("Welcome to the Loan Management System!");
        Loan loan = getUserLoanDetails();
        loan.displayEMIBreakdown();
    }
}