import java.util.*;

// Class to represent a Bank Account
class BankAccount {
    private String accountNumber;
    private String holderName;
    private double balance;
    private String pin;
    private List<String> transactions;

    // Constructor
    public BankAccount(String accountNumber, String holderName, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    // Getter for account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Verify PIN
    public boolean validatePIN(String enteredPIN) {
        return pin.equals(enteredPIN);
    }

    // Check Balance
    public double getBalance() {
        return balance;
    }

    // Deposit Money
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            recordTransaction("Deposited ₹" + amount);
            System.out.println("₹" + amount + " deposited successfully.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Withdraw Money
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            recordTransaction("Withdrew ₹" + amount);
            System.out.println("₹" + amount + " withdrawn successfully.");
        }
    }

    // Record a transaction
    private void recordTransaction(String detail) {
        if (transactions.size() == 5) { // keep last 5 transactions
            transactions.remove(0);
        }
        transactions.add(detail + " | Balance: ₹" + balance);
    }

    // View transaction history
    public void viewTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("---- Transaction History ----");
            for (String t : transactions) {
                System.out.println(t);
            }
        }
    }
}

// ATM Class to handle user interaction
class ATM {
    private Map<String, BankAccount> accounts;
    private Scanner scanner;
    private BankAccount currentAccount;

    public ATM() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
        loadDummyAccounts(); // Add some sample data
    }

    // Load some pre-defined accounts
    private void loadDummyAccounts() {
        accounts.put("12345", new BankAccount("12345", "Aman Singh", "1111", 10000));
        accounts.put("67890", new BankAccount("67890", "Tarun Singh", "2222", 15000));
    }

    // Start the ATM
    public void start() {
        System.out.println("===================================");
        System.out.println("   Welcome to Java ATM Machine");
        System.out.println("===================================");

        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter Account Number: ");
            String accNo = scanner.nextLine();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            BankAccount account = accounts.get(accNo);

            if (account != null && account.validatePIN(pin)) {
                currentAccount = account;
                System.out.println("\nLogin successful. Welcome, " + account.getAccountNumber() + "!");
                showMenu();
                return;
            } else {
                attempts++;
                System.out.println("Invalid account number or PIN. Attempts left: " + (3 - attempts));
            }
        }

        System.out.println("Too many failed attempts. Exiting...");
    }

    // Display the main menu
    private void showMenu() {
        while (true) {
            System.out.println("\n---------- Main Menu ----------");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("Current Balance: ₹" + currentAccount.getBalance());
                        break;

                    case 2:
                        System.out.print("Enter amount to deposit: ₹");
                        double deposit = Double.parseDouble(scanner.nextLine());
                        currentAccount.deposit(deposit);
                        break;

                    case 3:
                        System.out.print("Enter amount to withdraw: ₹");
                        double withdraw = Double.parseDouble(scanner.nextLine());
                        currentAccount.withdraw(withdraw);
                        break;

                    case 4:
                        currentAccount.viewTransactions();
                        break;

                    case 5:
                        System.out.println("Thank you for using Java ATM!");
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}

// Main class to run the program
public class chatGpt_ATM {
    public static void main(String[] args) {
        deepseek_ATM atm = new deepseek_ATM();
        atm.start();
    }
}
