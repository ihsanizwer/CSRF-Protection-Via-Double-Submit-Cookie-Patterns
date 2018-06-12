package data;

public class AccountDetails {
    private static double balance = 1000.00;

    public static Double getBalance(){
        return balance;
    }

    public static void setBalance(double bal) {
        balance = bal;
    }
}
