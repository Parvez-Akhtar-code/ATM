package ATM_Backend;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.security.MessageDigest;
public class User {
    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];

    private ArrayList<Account> accounts;

    /**
     * @param firstName the user's first name
     * @param lastName  the user's last name
     * @param pin       the user's account pin number
     * @param TheBank   the bank object that the user is a customer of
     */
    public User(String firstName, String lastName , String pin , Bank TheBank) {
        this.firstName = firstName;
        this.lastName = lastName;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (Exception e){
            System.err.println("error , caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        this.uuid = TheBank.getNewUserUUID();
        this.accounts = new ArrayList<Account>();
        System.out.printf("New User %s ,%s with ID %s created.\n",lastName,firstName,this.uuid);
    }

    /**
     * Add an Account for the user
     *
     * @param act
     */
    public void addAccount(Account act){
        this.accounts.add(act);
    }

    /**
     * return the user's UUID
     *
     * @return
     */
    public String getUUID(){
        return this.uuid;
    }


    /**
     * check Whether a given pin matches the true user pin
     *
     * @param pin the pin to check
     * @return Whether the pin is valid or not
     */
    public boolean validatePin(String pin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()),this.pinHash);
        } catch (NoSuchAlgorithmException e){
            System.err.println("error , caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }


    /**
     * Return the user's first name
     *
     * @return the first name
     */
    public String getFirstName(){
        return this.firstName;
    }

    public void printAccountsSummery(){
        System.out.printf("\n\n%s's Account Summery \n",this.firstName);
        for(int a=0;a<accounts.size();a++){
            System.out.printf(   "%d) %s\n", a+1,this.accounts.get(a).getSummeryLine());
        }
        System.out.println();
    }

    public int numAccounts(){
        return this.accounts.size();
    }

    public void printAcctTransHistory(int acctIdx){
        this.accounts.get(acctIdx).printTransHisory();
    }

    public double getAcctBalance(int acctIdx){
        return this.accounts.get(acctIdx).getBalance();
    }

    public String getAcctUUID(int acctIdx){
        return this.accounts.get(acctIdx).getUUID();
    }

    public void addAcctTransaction(int acctIdx , double amount ,  String memo){
        this.accounts.get(acctIdx).addTransaction(amount , memo);
    }

}
