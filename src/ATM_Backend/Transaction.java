package ATM_Backend;

import java.util.Date;

public class Transaction {
    private double amount;
    private Date timeStamp;
    private String memo;
    private Account inAccount;

    /**
     * Create a new Transaction
     * @param amount  the amount Transacted
     * @param inAccount the account the Transaction belongs to
     */

    public Transaction(double amount , Account inAccount){
        this.amount=amount;
        this.timeStamp = new Date();
        this.memo="";
        this.inAccount=inAccount;
    }

    /**
     * Create a new Transaction
     * @param amount  the amount Transacted
     * @param memo    the memo for the Transaction
     * @param inAccount the account the Transaction belongs to
     */
    public Transaction(double amount , String memo, Account inAccount){
        this(amount , inAccount);
        this.memo=memo;
    }

    public double getAmount(){
        return this.amount;
    }

    public String getSummeryLine(){
        if(this.amount>=0){
            return String.format("%s : $%.02f : %s ",this.timeStamp.toString() , this.amount , this.memo);
        }else{
            return String.format("%s : $(%.02f) : %s ",this.timeStamp.toString() , - 1*this.amount , this.memo);
        }
    }


}
