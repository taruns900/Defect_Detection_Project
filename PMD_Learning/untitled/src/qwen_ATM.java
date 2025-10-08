import java.util.*;

/**
 * Main class to run the ATM system
 */
public class qwen_ATM {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}

/**
 * Represents a bank account with essential details and operations
 */
class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private int pin;
    private List<Transaction> transactionHistory;

    /**
     * Constructor to initialize a bank account
     */
    public BankAccount(String accountNumber, String accountHolderName, double balance, int pin) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
    }

    // Getters (encapsulation)
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public int getPin() {
        return pin;
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory); // Return a copy to maintain encapsulation
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
        transactionHistory.add(new Transaction("Deposit", amount, balance));
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
        transactionHistory.add(new Transaction("Withdrawal", amount, balance));
        return true;
    }
}

/**
 * Represents a single transaction with type, amount, and resulting balance
 */
class Transaction {
    private String type;
    private double amount;
    private double balanceAfterTransaction;
    private Date timestamp;

    public Transaction(String type, double amount, double balanceAfterTransaction) {
        this.type = type;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.timestamp = new Date();
    }

    // Getters
    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("%s - %s: ₹%.2f | Balance: ₹%.2f", 
                           timestamp.toString().substring(0, 19), 
                           type, amount, balanceAfterTransaction);
    }
}

/**
 * ATM class that handles user interaction and main menu operations
 */
class ATM {
    private Scanner scanner;
    private Map<String, BankAccount> accounts;
    private BankAccount currentAccount;

    /**
     * Constructor to initialize the ATM with sample accounts
     */
    public ATM() {
        this.scanner = new Scanner(System.in);
        this.accounts = new HashMap<>();
        initializeSampleAccounts();
    }

    /**
     * Initializes sample accounts for demonstration
     */
    private void initializeSampleAccounts() {
        accounts.put("12345", new BankAccount("12345", "John Doe", 10000.0, 1234));
        accounts.put("67890", new BankAccount("67890", "Jane Smith", 5000.0, 5678));
        accounts.put("11111", new BankAccount("11111", "Alice Johnson", 15000.0, 1111));
    }

    /**
     * Starts the ATM system
     */
    public void start() {
        System.out.println("==================================");
        System.out.println("    Welcome to Java ATM System    ");
        System.out.println("==================================");
        
        if (authenticateUser()) {
            displayMainMenu();
        } else {
            System.out.println("Maximum login attempts exceeded. Goodbye!");
        }
        
        scanner.close();
    }

    /**
     * Handles user authentication with account number and PIN
     * @return true if authentication successful, false otherwise
     */
    private boolean authenticateUser() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        while (attempts < MAX_ATTEMPTS) {
            try {
                System.out.print("\nEnter Account Number: ");
                String accountNumber = scanner.nextLine().trim();
                
                System.out.print("Enter PIN: ");
                String pinInput = scanner.nextLine().trim();
                
                // Validate input
                if (accountNumber.isEmpty() || pinInput.isEmpty()) {
                    System.out.println("Account number and PIN cannot be empty!");
                    attempts++;
                    continue;
                }
                
                int pin = Integer.parseInt(pinInput);
                BankAccount account = accounts.get(accountNumber);
                
                if (account != null && account.getPin() == pin) {
                    currentAccount = account;
                    System.out.println("\nAuthentication successful!");
                    System.out.println("Welcome, " + currentAccount.getAccountHolderName() + "!");
                    return true;
                } else {
                    attempts++;
                    System.out.println("Invalid account number or PIN. Attempts remaining: " + 
                                     (MAX_ATTEMPTS - attempts));
                }
            } catch (NumberFormatException e) {
                attempts++;
                System.out.println("Invalid PIN format. Please enter a numeric PIN.");
                System.out.println("Attempts remaining: " + (MAX_ATTEMPTS - attempts));
            }
        }
        
        return false;
    }

    /**
     * Displays the main menu and handles user choices
     */
    private void displayMainMenu() {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n==================================");
            System.out.println("            MAIN MENU             ");
            System.out.println("==================================");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View Transaction History");
            System.out.println("5. Exit");
            System.out.println("==================================");
            
            try {
                System.out.print("Choose option (1-5): ");
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
                        exit = true;
                        System.out.println("Thank you for using Java ATM! Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option! Please choose 1-5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1-5.");
            }
        }
    }

    /**
     * Displays the current account balance
     */
    private void checkBalance() {
        System.out.println("\n==================================");
        System.out.printf("Your current balance is: ₹%.2f%n", currentAccount.getBalance());
        System.out.println("==================================");
    }

    /**
     * Handles deposit operation
     */
    private void depositMoney() {
        try {
            System.out.print("\nEnter deposit amount: ₹");
            double amount = Double.parseDouble(scanner.nextLine().trim());
            
            if (currentAccount.deposit(amount)) {
                System.out.println("==================================");
                System.out.printf("Deposit successful! ₹%.2f deposited.%n", amount);
                System.out.printf("New balance: ₹%.2f%n", currentAccount.getBalance());
                System.out.println("==================================");
            } else {
                System.out.println("==================================");
                System.out.println("Invalid deposit amount! Amount must be positive.");
                System.out.println("==================================");
            }
        } catch (NumberFormatException e) {
            System.out.println("==================================");
            System.out.println("Invalid amount format! Please enter a valid number.");
            System.out.println("==================================");
        }
    }

    /**
     * Handles withdrawal operation
     */
    private void withdrawMoney() {
        try {
            System.out.print("\nEnter withdrawal amount: ₹");
            double amount = Double.parseDouble(scanner.nextLine().trim());
            
            if (currentAccount.withdraw(amount)) {
                System.out.println("==================================");
                System.out.printf("Withdrawal successful! ₹%.2f withdrawn.%n", amount);
                System.out.printf("New balance: ₹%.2f%n", currentAccount.getBalance());
                System.out.println("==================================");
            } else if (amount <= 0) {
                System.out.println("==================================");
                System.out.println("Invalid withdrawal amount! Amount must be positive.");
                System.out.println("==================================");
            } else {
                System.out.println("==================================");
                System.out.println("Insufficient funds! Your balance is insufficient for this withdrawal.");
                System.out.printf("Current balance: ₹%.2f%n", currentAccount.getBalance());
                System.out.println("==================================");
            }
        } catch (NumberFormatException e) {
            System.out.println("==================================");
            System.out.println("Invalid amount format! Please enter a valid number.");
            System.out.println("==================================");
        }
    }

    /**
     * Displays transaction history (last 10 transactions)
     */
    private void viewTransactionHistory() {
        List<Transaction> history = currentAccount.getTransactionHistory();
        
        System.out.println("\n==================================");
        System.out.println("        TRANSACTION HISTORY       ");
        System.out.println("==================================");
        
        if (history.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            // Display last 10 transactions (or all if less than 10)
            int startIndex = Math.max(0, history.size() - 10);
            for (int i = startIndex; i < history.size(); i++) {
                System.out.println(history.get(i));
            }
        }
        System.out.println("==================================");
    }
}