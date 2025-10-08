import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Transaction class to record transaction history
class Transaction {
    private String type;
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;

    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return String.format("%-12s | ₹%-10.2f | Balance: ₹%-10.2f | %s", 
                           type, amount, balanceAfter, timestamp.format(formatter));
    }
}

// BankAccount class to manage account details
class BankAccount {
    private String accountNumber;
    private String holderName;
    private double balance;
    private String pin;
    private ArrayList<Transaction> transactionHistory;

    public BankAccount(String accountNumber, String holderName, double balance, String pin) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    // Validate PIN
    public boolean validatePin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    // Deposit money
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        transactionHistory.add(new Transaction("DEPOSIT", amount, balance));
        return true;
    }

    // Withdraw money
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        transactionHistory.add(new Transaction("WITHDRAWAL", amount, balance));
        return true;
    }

    // Get transaction history
    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

// ATM class to handle user interaction
class ATM {
    private HashMap<String, BankAccount> accounts;
    private BankAccount currentAccount;
    private Scanner scanner;

    public ATM() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
        initializeAccounts();
    }

    // Initialize some sample accounts
    private void initializeAccounts() {
        accounts.put("12345", new BankAccount("12345", "John Doe", 10000.00, "1234"));
        accounts.put("67890", new BankAccount("67890", "Jane Smith", 25000.00, "5678"));
        accounts.put("11111", new BankAccount("11111", "Bob Johnson", 5000.00, "9999"));
    }

    // Start the ATM system
    public void start() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║     Welcome to Java ATM System     ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println();

        if (authenticateUser()) {
            showMainMenu();
        } else {
            System.out.println("\n❌ Too many failed attempts. Your card has been blocked.");
            System.out.println("Please contact your bank for assistance.");
        }

        scanner.close();
    }

    // User authentication
    private boolean authenticateUser() {
        int attempts = 3;

        while (attempts > 0) {
            try {
                System.out.print("Enter Account Number: ");
                String accountNumber = scanner.nextLine().trim();

                System.out.print("Enter PIN: ");
                String pin = scanner.nextLine().trim();

                if (accounts.containsKey(accountNumber)) {
                    BankAccount account = accounts.get(accountNumber);
                    if (account.validatePin(pin)) {
                        currentAccount = account;
                        System.out.println("\n✓ Authentication Successful!");
                        System.out.println("Welcome, " + currentAccount.getHolderName() + "!\n");
                        return true;
                    }
                }

                attempts--;
                if (attempts > 0) {
                    System.out.println("\n❌ Invalid account number or PIN.");
                    System.out.println("Attempts remaining: " + attempts + "\n");
                } else {
                    System.out.println("\n❌ Invalid account number or PIN.");
                }

            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
                attempts--;
            }
        }

        return false;
    }

    // Display main menu
    private void showMainMenu() {
        boolean running = true;

        while (running) {
            try {
                System.out.println("\n┌────────────────────────────────┐");
                System.out.println("│          MAIN MENU             │");
                System.out.println("├────────────────────────────────┤");
                System.out.println("│ 1. Check Balance               │");
                System.out.println("│ 2. Deposit Money               │");
                System.out.println("│ 3. Withdraw Money              │");
                System.out.println("│ 4. Transaction History         │");
                System.out.println("│ 5. Exit                        │");
                System.out.println("└────────────────────────────────┘");
                System.out.print("\nChoose option: ");

                int choice = Integer.parseInt(scanner.nextLine().trim());

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
                        System.out.println("\n╔════════════════════════════════════╗");
                        System.out.println("║   Thank you for using Java ATM!    ║");
                        System.out.println("║        Have a great day!           ║");
                        System.out.println("╚════════════════════════════════════╝");
                        break;
                    default:
                        System.out.println("\n❌ Invalid option. Please choose between 1-5.");
                }

            } catch (NumberFormatException e) {
                System.out.println("\n❌ Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println("\n❌ An error occurred: " + e.getMessage());
            }
        }
    }

    // Check balance
    private void checkBalance() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("         BALANCE INQUIRY");
        System.out.println("═══════════════════════════════════");
        System.out.printf("Account Number: %s%n", currentAccount.getAccountNumber());
        System.out.printf("Account Holder: %s%n", currentAccount.getHolderName());
        System.out.printf("Current Balance: ₹%.2f%n", currentAccount.getBalance());
        System.out.println("═══════════════════════════════════");
    }

    // Deposit money
    private void depositMoney() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("           DEPOSIT MONEY");
        System.out.println("═══════════════════════════════════");

        try {
            System.out.print("Enter amount to deposit: ₹");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            if (currentAccount.deposit(amount)) {
                System.out.println("\n✓ Deposit Successful!");
                System.out.printf("Deposited: ₹%.2f%n", amount);
                System.out.printf("New Balance: ₹%.2f%n", currentAccount.getBalance());
            } else {
                System.out.println("\n❌ Invalid amount. Amount must be positive.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("\n❌ Transaction failed: " + e.getMessage());
        }

        System.out.println("═══════════════════════════════════");
    }

    // Withdraw money
    private void withdrawMoney() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("          WITHDRAW MONEY");
        System.out.println("═══════════════════════════════════");
        System.out.printf("Available Balance: ₹%.2f%n", currentAccount.getBalance());

        try {
            System.out.print("Enter amount to withdraw: ₹");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            if (amount <= 0) {
                System.out.println("\n❌ Invalid amount. Amount must be positive.");
            } else if (amount > currentAccount.getBalance()) {
                System.out.println("\n❌ Insufficient balance!");
                System.out.printf("Available Balance: ₹%.2f%n", currentAccount.getBalance());
            } else if (currentAccount.withdraw(amount)) {
                System.out.println("\n✓ Withdrawal Successful!");
                System.out.printf("Withdrawn: ₹%.2f%n", amount);
                System.out.printf("New Balance: ₹%.2f%n", currentAccount.getBalance());
            }

        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("\n❌ Transaction failed: " + e.getMessage());
        }

        System.out.println("═══════════════════════════════════");
    }

    // View transaction history
    private void viewTransactionHistory() {
        System.out.println("\n═══════════════════════════════════════════════════════════════════════════");
        System.out.println("                            TRANSACTION HISTORY");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");

        ArrayList<Transaction> history = currentAccount.getTransactionHistory();

        if (history.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Type         | Amount       | Balance After  | Date & Time");
            System.out.println("─────────────────────────────────────────────────────────────────────────");
            
            // Display last 10 transactions
            int start = Math.max(0, history.size() - 10);
            for (int i = start; i < history.size(); i++) {
                System.out.println(history.get(i));
            }

            if (history.size() > 10) {
                System.out.println("\n(Showing last 10 transactions)");
            }
        }

        System.out.println("═══════════════════════════════════════════════════════════════════════════");
    }
}

// Main class
public class claude_ATM {
    public static void main(String[] args) {
        gemini_ATM atm = new gemini_ATM();
        atm.start();
    }
}