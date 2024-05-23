import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Final {

    public static class mainMenu {

        public static class profile {
            private String userName;
            private String password;
            private int age;
            private int balance; // Added balance field
            private List<String> transactionHistory; // Added transaction history field
            public static List<profile> listProfiles = new ArrayList<>();
            public static int activeProfileIndex = -1;

            public profile(String userName, String password, int age) {
                this.userName = userName;
                this.password = password;
                this.age = age;
                this.balance = 1000; // Start with a balance of 1000
                this.transactionHistory = new ArrayList<>();
            }

            public static void createProfile() {
                String userName = JOptionPane.showInputDialog(null, "Enter your username");
                String password = JOptionPane.showInputDialog(null, "Enter your password");
                int age = learnAge();

                profile newProfile = new profile(userName, password, age);
                listProfiles.add(newProfile);
                activeProfileIndex = listProfiles.indexOf(newProfile);

                JOptionPane.showMessageDialog(null, "Account created successfully!");
                financialMenu();
            }

            private static int learnAge() {
                int age = 0;
                boolean validAge = false;

                while (!validAge) {
                    String ageChoiceString = JOptionPane.showInputDialog(null, "Enter your age");
                    try {
                        age = Integer.parseInt(ageChoiceString);
                        validAge = true;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Age must be an integer");
                    }
                }

                return age;
            }

            public static void logIn() {
                // Unchanged logIn code
            }

            public String getUserName() {
                return userName;
            }

            public int getBalance() {
                return balance;
            }

            public void addToTransactionHistory(String transaction) {
                transactionHistory.add(transaction);
            }

            public List<String> getTransactionHistory() {
                return transactionHistory;
            }

            public static void financialMenu() {
                JFrame f = new JFrame("Financial Menu");
                JButton withdrawButton = new JButton("Withdraw");
                JButton depositButton = new JButton("Deposit");
                JButton checkBalanceButton = new JButton("Check Balance");
                JButton transactionHistoryButton = new JButton("Transaction History"); // Added button

                withdrawButton.setBounds(50, 100, 150, 30);
                depositButton.setBounds(50, 150, 150, 30);
                checkBalanceButton.setBounds(50, 200, 150, 30);
                transactionHistoryButton.setBounds(50, 250, 150, 30); // Added button

                withdrawButton.addActionListener(e -> {
                    int amount = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter withdrawal amount:"));
                    withdraw(amount);
                    updateBalanceLabel(f);
                });

                depositButton.addActionListener(e -> {
                    int amount = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter deposit amount:"));
                    deposit(amount);
                    updateBalanceLabel(f);
                });

                checkBalanceButton.addActionListener(e -> {
                    int balance = listProfiles.get(activeProfileIndex).getBalance();
                    JOptionPane.showMessageDialog(null, "Current Balance: " + balance);
                });

                transactionHistoryButton.addActionListener(e -> {
                    showTransactionHistory();
                });

                JLabel l = new JLabel("Financial Menu");
                l.setBounds(50, 50, 150, 30);

                f.add(l);
                f.add(withdrawButton);
                f.add(depositButton);
                f.add(checkBalanceButton);
                f.add(transactionHistoryButton); // Added button
                f.setSize(300, 350); // Increased the height to accommodate the new button
                f.setLayout(null);
                f.setVisible(true);
            }

            private static void withdraw(int amount) {
                int currentBalance = listProfiles.get(activeProfileIndex).getBalance();
                if (amount > 0 && amount <= currentBalance) {
                    listProfiles.get(activeProfileIndex).balance -= amount;
                    listProfiles.get(activeProfileIndex).addToTransactionHistory("Withdrawal: " + amount);
                    JOptionPane.showMessageDialog(null, "Withdrawal successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid withdrawal amount.");
                }
            }

            private static void deposit(int amount) {
                if (amount > 0) {
                    listProfiles.get(activeProfileIndex).balance += amount;
                    listProfiles.get(activeProfileIndex).addToTransactionHistory("Deposit: " + amount);
                    JOptionPane.showMessageDialog(null, "Deposit successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid deposit amount.");
                }
            }

            private static void updateBalanceLabel(JFrame frame) {
                frame.dispose(); // Close the financial menu screen
                financialMenu(); // Open the financial menu screen again to reflect updated balance
            }

            private static void showTransactionHistory() {
                JFrame transactionHistoryFrame = new JFrame("Transaction History");
                JTextArea textArea = new JTextArea();

                List<String> transactionHistory = listProfiles.get(activeProfileIndex).getTransactionHistory();
                for (String transaction : transactionHistory) {
                    textArea.append(transaction + "\n");
                }

                transactionHistoryFrame.add(new JScrollPane(textArea));
                transactionHistoryFrame.setSize(300, 300);
                transactionHistoryFrame.setLocationRelativeTo(null);
                transactionHistoryFrame.setVisible(true);
            }
        }

        public static void signedOutMenu() {
            JFrame f = new JFrame("Profile Manager MainMenu");
            JButton signInButton = new JButton("Sign In");
            JButton signUpButton = new JButton("Sign Up");
            signInButton.setBounds(150, 150, 100, 30);
            signUpButton.setBounds(150, 200, 100, 30);

            signInButton.addActionListener(e -> {
                profile.logIn();

                if (!profile.listProfiles.isEmpty() &&
                        profile.activeProfileIndex >= 0 &&
                        profile.activeProfileIndex < profile.listProfiles.size()) {
                    JOptionPane.showMessageDialog(null, "Welcome back " +
                            profile.listProfiles.get(profile.activeProfileIndex).getUserName());
                    profile.financialMenu();
                    f.dispose();
                } else {
                    // User canceled after clicking "Sign In," go back to the menu screen
                    signedOutMenu();
                    f.dispose();
                }
            });

            signUpButton.addActionListener(e -> profile.createProfile());

            JLabel l = new JLabel("Not Signed In");
            l.setBounds(150, 50, 100, 100);

            f.add(l);
            f.add(signInButton);
            f.add(signUpButton);
            f.setSize(400, 400);
            f.setLayout(null);
            f.setVisible(true);
        }
    }

    public static void main(String[] args) {
        mainMenu.signedOutMenu();
    }
}
