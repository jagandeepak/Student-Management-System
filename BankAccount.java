import java.util.Scanner;

public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = Math.max(0.0, initialBalance);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Deposited: %.2f | New Balance: %.2f%n", amount, balance);
        } else {
            System.out.println("Deposit amount must be greater than 0.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than 0.");
        } else if (amount > balance) {
            System.out.println("Insufficient funds.");
        } else {
            balance -= amount;
            System.out.printf("Withdrew: %.2f | Remaining Balance: %.2f%n", amount, balance);
        }
    }

    private static double readPositiveDouble(Scanner scanner) {
        while (true) {
            System.out.print("Enter amount: ");
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value >= 0) {
                    return value;
                }
                System.out.println("Please enter a non-negative number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter initial balance:");
        double initialBalance = readPositiveDouble(scanner);
        BankAccount account = new BankAccount(initialBalance);

        System.out.println("Enter deposit amount:");
        double depositAmount = readPositiveDouble(scanner);
        account.deposit(depositAmount);

        System.out.println("Enter withdrawal amount:");
        double withdrawAmount = readPositiveDouble(scanner);
        account.withdraw(withdrawAmount);

        scanner.close();
    }
}