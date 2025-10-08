import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a single transaction in the ATM system
 * Demonstrates encapsulation by keeping transaction details private
 */
class Transaction {
    private final String type;
    private final double amount;
    private final Date timestamp;
    private final double balanceAfter;
    
    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = new Date();
    }
    
    // Getters - demonstrating encapsulation
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public Date getTimestamp() { return timestamp; }
    public double getBalanceAfter() { return balanceAfter; }
    
    @Override
    public String toString() {
        return String.format("%s: ₹%.2f | Balance: ₹%.2f | %s", 
                           type, amount, balanceAfter, timestamp);
    }
}

/**
 * Represents a bank account with account details and transaction history
 * Demonstrates encapsulation by protecting sensitive data
 */
class BankAccount {
    private final String accountNumber;
    private final String accountHolder;
    private double balance;
    private final String pin;
    private final List<Transaction> transactionHistory;
    
    public BankAccount(String accountNumber, String accountHolder, double initialBalance, String pin) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
        // Record initial balance as first transaction
        this.transactionHistory.add(new Transaction("ACCOUNT CREATED", initialBalance, initialBalance));
    }
    
    // Getters - providing controlled access to private data
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public double getBalance() { return balance; }
    public String getPin() { return pin; }
    public List<Transaction> getTransactionHistory() { 
        return new ArrayList<>(transactionHistory); // Return copy to maintain encapsulation
    }
    
    /**
     * Deposits money into the account
     * @param amount the amount to deposit
     * @return true if successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        
        balance += amount;
        transactionHistory.add(new Transaction("DEPOSIT", amount, balance));
        return true;
    }
    
    /**
     * Withdraws money from the account
     * @param amount the amount to withdraw
     * @return true if successful, false otherwise
     */
    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        
        balance -= amount;
        transactionHistory.add(new Transaction("WITHDRAWAL", amount, balance));
        return true;
    }
    
    /**
     * Adds a transaction to the history (for transfers, etc.)
     * @param transaction the transaction to add
     */
    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }
    
    /**
     * Gets the last N transactions
     * @param count number of recent transactions to return
     * @return list of recent transactions
     */
    public List<Transaction> getRecentTransactions(int count) {
        int startIndex = Math.max(0, transactionHistory.size() - count);
        return new ArrayList<>(transactionHistory.subList(startIndex, transactionHistory.size()));
    }
}

/**
 * Main ATM class that handles user interaction and menu operations
 * Demonstrates abstraction by hiding complex operations behind simple methods
 */
public class deepseek_ATM {
    private final Scanner scanner;
    private final Map<String, BankAccount> accounts;
    private BankAccount currentAccount;
    
    public deepseek_ATM() {
        this.scanner = new Scanner(System.in);
        this.accounts = new HashMap<>();
        initializeSampleAccounts();
    }
    
    /**
     * Creates some sample accounts for testing
     */
    private void initializeSampleAccounts() {
        accounts.put("12345", new BankAccount("12345", "John Doe", 10000.0, "1111"));
        accounts.put("67890", new BankAccount("67890", "Jane Smith", 5000.0, "2222"));
        accounts.put("11111", new BankAccount("11111", "Alice Johnson", 15000.0, "3333"));
    }
    
    /**
     * Main method to start the ATM system
     */
    public void start() {
        System.out.println("=" .repeat(50));
        System.out.println("          WELCOME TO JAVA ATM SYSTEM");
        System.out.println("=" .repeat(50));
        
        while (true) {
            if (authenticateUser()) {
                showMainMenu();
            }
            
            System.out.print("\nDo you want to perform another transaction? (yes/no): ");
            String continueChoice = scanner.nextLine().toLowerCase();
            if (!continueChoice.equals("yes")) {
                System.out.println("\nThank you for using Java ATM. Goodbye!");
                break;
            }
            currentAccount = null; // Log out current user
        }
        
        scanner.close();
    }
    
    /**
     * Handles user authentication with PIN verification
     * @return true if authentication successful, false otherwise
     */
    private boolean authenticateUser() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("\n" + "-".repeat(30));
            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine();
            
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();
            
            // Validate account exists and PIN matches
            if (accounts.containsKey(accountNumber)) {
                BankAccount account = accounts.get(accountNumber);
                if (account.getPin().equals(pin)) {
                    currentAccount = account;
                    System.out.println("\n✓ Authentication successful!");
                    System.out.println("Welcome, " + account.getAccountHolder() + "!");
                    return true;
                }
            }
            
            attempts++;
            System.out.println("\n✗ Invalid account number or PIN. Attempts left: " + (MAX_ATTEMPTS - attempts));
        }
        
        System.out.println("\n✗ Too many failed attempts. Please contact customer support.");
        return false;
    }
    
    /**
     * Displays the main menu and handles user choices
     */
    private void showMainMenu() {
        while (true) {
            displayMenuOptions();
            System.out.print("\nChoose option (1-5): ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    checkBalance();
                    break;
                case "2":
                    depositMoney();
                    break;
                case "3":
                    withdrawMoney();
                    break;
                case "4":
                    viewTransactionHistory();
                    break;
                case "5":
                    System.out.println("\nLogging out... Thank you!");
                    return;
                default:
                    System.out.println("\n✗ Invalid option. Please choose 1-5.");
            }
            
            System.out.println("\n" + "=".repeat(50));
        }
    }
    
    /**
     * Displays the formatted menu options
     */
    private void displayMenuOptions() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("         MAIN MENU");
        System.out.println("=".repeat(30));
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transaction History");
        System.out.println("5. Exit");
    }
    
    /**
     * Displays the current account balance
     */
    private void checkBalance() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("     ACCOUNT BALANCE");
        System.out.println("-".repeat(30));
        System.out.printf("Your current balance: ₹%,.2f%n", currentAccount.getBalance());
    }
    
    /**
     * Handles money deposit operation with input validation
     */
    private void depositMoney() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("       DEPOSIT MONEY");
        System.out.println("-".repeat(30));
        
        try {
            System.out.print("Enter amount to deposit: ₹");
            double amount = Double.parseDouble(scanner.nextLine());
            
            if (amount <= 0) {
                System.out.println("\n✗ Deposit amount must be positive.");
                return;
            }
            
            if (currentAccount.deposit(amount)) {
                System.out.println("\n✓ Deposit successful!");
                System.out.printf("New balance: ₹%,.2f%n", currentAccount.getBalance());
            } else {
                System.out.println("\n✗ Deposit failed. Please try again.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("\n✗ Invalid amount format. Please enter a valid number.");
        }
    }
    
    /**
     * Handles money withdrawal operation with validation
     */
    private void withdrawMoney() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("      WITHDRAW MONEY");
        System.out.println("-".repeat(30));
        
        try {
            System.out.print("Enter amount to withdraw: ₹");
            double amount = Double.parseDouble(scanner.nextLine());
            
            if (amount <= 0) {
                System.out.println("\n✗ Withdrawal amount must be positive.");
                return;
            }
            
            if (currentAccount.withdraw(amount)) {
                System.out.println("\n✓ Withdrawal successful!");
                System.out.printf("Remaining balance: ₹%,.2f%n", currentAccount.getBalance());
            } else {
                System.out.println("\n✗ Withdrawal failed. Insufficient balance or invalid amount.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("\n✗ Invalid amount format. Please enter a valid number.");
        }
    }
    
    /**
     * Displays recent transaction history
     */
    private void viewTransactionHistory() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("          TRANSACTION HISTORY");
        System.out.println("-".repeat(50));
        
        List<Transaction> recentTransactions = currentAccount.getRecentTransactions(5);
        
        if (recentTransactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Last " + recentTransactions.size() + " transactions:");
            System.out.println("-".repeat(50));
            for (int i = 0; i < recentTransactions.size(); i++) {
                System.out.println((i + 1) + ". " + recentTransactions.get(i));
            }
        }
    }
    
    /**
     * Main method to launch the ATM system
     */
    public static void main(String[] args) {
        deepseek_ATM atm = new deepseek_ATM();
        atm.start();
    }
}