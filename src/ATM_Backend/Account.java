package ATM_Backend;
import java.util.*;
public class Account {
    private String name;
    private double balance;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;

    /**
     * @param name   the name of the account
     * @param holder the user object that holds this account
     * @param TheBank   the bank that issuse the account
     */
    public Account(String name , User holder,Bank TheBank){
        this.name = name;
        this.holder = holder;

        this.uuid = TheBank.getNewAccountUUID();
        this.transactions = new ArrayList<Transaction>();



    }
    public String getUUID(){
        return this.uuid;
    }
    public String getSummeryLine(){
        double balance=this.getBalance();
        if(balance>=0){
            return String.format("%s : $%.02f : %s",this.uuid,balance,this.name);

        }else {
            return String.format("%s : $(%.02f): %s",this.uuid,balance,this.name);
        }
    }

    public double getBalance(){
        double balance =0 ;
        for(Transaction t : this.transactions) {
            balance += t.getAmount();

        }
        return balance;
    }

    public void printTransHisory(){
        System.out.printf("\nTransaction history for this account %s\n",this.uuid);
        for(int t = this.transactions.size()-1;t>=0;t--){
            System.out.println(this.transactions.get(t).getSummeryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount , String memo){
        Transaction trans = new Transaction(amount , memo , this);
        this.transactions.add(trans);
    }
}
