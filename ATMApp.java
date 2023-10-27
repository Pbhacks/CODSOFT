import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Initial Pin is 1234 use change pin feature to create new pin
public class ATMApp extends JFrame {

    private ATM atm;
    private JLabel balanceLabel;
    private JTextField amountField;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton checkBalanceButton;
    private JButton changePinButton;
    private JButton miniStatementButton;

    private String enteredPin;

    public ATMApp() {
        setTitle("ATM Machine");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        atm = new ATM();
        enteredPin = null;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1));

        balanceLabel = new JLabel("Account Balance: $" + atm.checkBalance());
        amountField = new JTextField(10);

        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        checkBalanceButton = new JButton("Check Balance");
        changePinButton = new JButton("Change PIN");
        miniStatementButton = new JButton("Mini Statement");

        mainPanel.add(balanceLabel);
        mainPanel.add(amountField);
        mainPanel.add(withdrawButton);
        mainPanel.add(depositButton);
        mainPanel.add(checkBalanceButton);
        mainPanel.add(changePinButton);
        mainPanel.add(miniStatementButton);

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkPinEntered()) {
                    try {
                        double amount = Double.parseDouble(amountField.getText());
                        if (atm.withdraw(amount)) {
                            showMessage("Withdrawal successful. New balance: $" + atm.checkBalance());
                        } else {
                            showMessage("Insufficient balance for withdrawal.");
                        }
                    } catch (NumberFormatException ex) {
                        showMessage("Invalid input for withdrawal amount.");
                    }
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkPinEntered()) {
                    try {
                        double amount = Double.parseDouble(amountField.getText());
                        atm.deposit(amount);
                        showMessage("Deposit successful. New balance: $" + atm.checkBalance());
                    } catch (NumberFormatException ex) {
                        showMessage("Invalid input for deposit amount.");
                    }
                }
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkPinEntered()) {
                    showMessage("Current balance: $" + atm.checkBalance());
                }
            }
        });

        changePinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showChangePinDialog();
            }
        });

        miniStatementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkPinEntered()) {
                    showMessage(atm.generateMiniStatement());
                }
            }
        });

        add(mainPanel);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
        balanceLabel.setText("Account Balance: $" + atm.checkBalance());
        amountField.setText("");
    }

    private void showChangePinDialog() {
        if (enteredPin == null) {
            showMessage("Please enter your PIN.");
            return;
        }

        String newPin = JOptionPane.showInputDialog(this, "Enter your new PIN:");
        if (newPin != null && newPin.length() == 4) {
            atm.changePin(newPin);
            showMessage("PIN changed successfully.");
        } else {
            showMessage("Invalid PIN format. Please enter a 4-digit PIN.");
        }
    }

    private boolean checkPinEntered() {
        if (enteredPin == null) {
            enteredPin = JOptionPane.showInputDialog(this, "Enter your PIN:");
            if (enteredPin == null || enteredPin.length() != 4) {
                showMessage("Invalid PIN format. Please enter a 4-digit PIN.");
                enteredPin = null;
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ATMApp app = new ATMApp();
                app.setVisible(true);
            }
        });
    }
}

class ATM {
    private BankAccount account;
    private String pin;

    public ATM() {
        account = new BankAccount(1000.0); // Initial balance
        pin = "1234"; // Initial PIN
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= account.getBalance()) {
            account.withdraw(amount);
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            account.deposit(amount);
        }
    }

    public double checkBalance() {
        return account.getBalance();
    }

    public void changePin(String newPin) {
        pin = newPin;
    }

    public String generateMiniStatement() {
        // In a real application, you would return actual transaction history here.
        return "Mini Statement:\nRecent transactions...";
    }
}

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public void deposit(double amount) {
        balance += amount;
    }
}
