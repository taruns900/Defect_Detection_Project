import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

// --- 1. Transaction Class (Encapsulation) ---
class Transaction {
    private final String type;
    private final double amount;
    private final double newBalance;
    private final long timestamp;

    public Transaction(String type, double amount, double newBalance) {
        this.type = type;
        this.amount = amount;
        this.newBalance = newBalance;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return String.format(
            "[%tH:%tM:%tS] %s: ₹%.2f (New Balance: ₹%.2f)", 
            timestamp, timestamp, timestamp, type, amount, newBalance
        );
    }
}

// --- 2. BankAccount Class (Encapsulation & Core Logic) ---
class BankAccount {
    private final String accountNumber;
    private final String accountHolderName;
    private final String pin;
    private double balance;
    private final List<Transaction> transactionHistory;

    public BankAccount(String accountNumber, String accountHolderName, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    // Getters for Abstraction and controlled access
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
    
    // Core Methods
    public boolean checkPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
        // Record transaction
        transactionHistory.add(new Transaction("Deposit", amount, balance));
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        balance -= amount;
        // Record transaction
        transactionHistory.add(new Transaction("Withdrawal", amount, balance));
    }

    public List<Transaction> getTransactionHistory() {
        // Return a copy or unmodifiable list for safety
        return List.copyOf(transactionHistory);
    }
}

// --- 3. ATM Class (Main Application Logic & User Interface) ---
public class gemini_ATM {
    private final Scanner scanner;
    private final Map<String, BankAccount> accounts;
    private BankAccount currentAccount;

    public gemini_ATM() {
        this.scanner = new Scanner(System.in);
        this.accounts = new HashMap<>();
        // Initialize with multiple sample accounts (Optional Enhancement)
        initializeAccounts(); 
    }

    private void initializeAccounts() {
        accounts.put("12345", new BankAccount("12345", "Alice Smith", "1234", 10000.00));
        accounts.put("98765", new BankAccount("98765", "Bob Johnson", "9876", 500.50));
    }

    // --- Main Control Flow ---
    public void start() {
        System.out.println("\n===== Welcome to Java ATM =====");
        
        if (authenticateUser()) {
            runMainMenu();
        } else {
            System.out.println("❌ ATM Locked. Goodbye!");
        }
    }

    // --- User Authentication ---
    private boolean authenticateUser() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        
        while (attempts < MAX_ATTEMPTS) {
            System.out.print("\nEnter Account Number: ");
            String accNum = scanner.nextLine();
            
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();
            
            BankAccount account = accounts.get(accNum);

            if (account != null && account.checkPin(pin)) {
                this.currentAccount = account;
                System.out.println("\n✅ Authentication Successful. Welcome!");
                return true;
            } else {
                attempts++;
                System.out.println("❌ Invalid Account Number or PIN. Attempts remaining: " + (MAX_ATTEMPTS - attempts));
            }
        }
        return false;
    }

    // --- Main Menu Loop ---
    private void runMainMenu() {
        boolean running = true;
        while (running) {
            displayMenu();
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        checkBalance();
                        break;
                    case 2:
                        depositMoney();
                        break;
                    case 3:
                        withdrawMoney();
                        break;
                    case 4:
                        viewTransactionHistory();
                        break;
                    case 5:
                        running = false;
                        System.out.println("\n👋 Thank you for using the Java ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("⚠️ Invalid option. Please choose from 1 to 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input. Please enter a number.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. View Transaction History");
        System.out.println("5. Exit");
        System.out.print("Choose option: ");
    }

    // --- Core Functionalities ---
    
    private void checkBalance() {
        System.out.printf("\nYour current balance is: ₹%.2f\n", currentAccount.getBalance());
    }

    private void depositMoney() {
        System.out.print("Enter deposit amount: ₹");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            currentAccount.deposit(amount);
            System.out.printf("✅ Deposit successful. New balance: ₹%.2f\n", currentAccount.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a numerical amount.");
        } catch (IllegalArgumentException e) {
            // Catches negative/zero amount from BankAccount class
            System.out.println("❌ Deposit failed: " + e.getMessage());
        }
    }

    private void withdrawMoney() {
        System.out.print("Enter withdrawal amount: ₹");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            currentAccount.withdraw(amount);
            System.out.printf("✅ Withdrawal successful. New balance: ₹%.2f\n", currentAccount.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a numerical amount.");
        } catch (IllegalArgumentException e) {
            // Catches insufficient balance or negative/zero amount
            System.out.println("❌ Withdrawal failed: " + e.getMessage());
        }
    }
    
    private void viewTransactionHistory() {
        List<Transaction> history = currentAccount.getTransactionHistory();
        System.out.println("\n--- Transaction History ---");
        
        if (history.isEmpty()) {
            System.out.println("No transactions recorded yet.");
            return;
        }

        // Display up to the last 10 transactions
        int displayCount = 0;
        for (int i = history.size() - 1; i >= 0 && displayCount < 10; i--, displayCount++) {
            System.out.println(history.get(i));
        }
        
        if (history.size() > 10) {
            System.out.println("... (Showing last 10 transactions)");
        }
        System.out.println("---------------------------");
    }

    // --- Main method to run the program ---
    public static void main(String[] args) {
        gemini_ATM atm = new gemini_ATM();
        atm.start();
    }
}