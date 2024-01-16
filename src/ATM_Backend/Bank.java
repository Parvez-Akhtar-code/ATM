package ATM_Backend;
import java.util.*;

public class Bank {
    private String name;
    private ArrayList<Account> accounts;
    private ArrayList<User> users;

    public Bank(String name){
        this.name=name;
        this.users=new ArrayList<User>();
        this.accounts=new ArrayList<Account>();

    }
    public String getNewUserUUID() {
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        do{
            uuid ="";
            for(int i=0;i<len;i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique=false;
            for(User u : this.users){
                if(uuid.compareTo(u.getUUID())==0){
                    nonUnique=true;
                    break;
                }
            }
        }while (nonUnique);
        return uuid;
    }
    public String getName(){
        return this.name;
    }
    public String getNewAccountUUID(){
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;
        do{
            uuid ="";
            for(int i=0;i<len;i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique=false;
            for(Account a : this.accounts){
                if(uuid.compareTo(a.getUUID())==0){
                    nonUnique=true;
                    break;
                }
            }
        }while (nonUnique);
        return uuid;
    }
    public void addAccount(Account act){
    this.accounts.add(act);
    }

    public User addUser(String firstName , String lastname , String pin ){
        User user = new User(firstName,lastname,pin,this);
        this.users.add(user);

        Account account = new Account("Savings",user,this);
        user.addAccount(account);
        this.accounts.add(account);

        return user;
    }
    public User userLogin(String userId , String pin){
        for( User u : this.users){
            if(u.getUUID().compareTo(userId)==0 && u.validatePin(pin)){
                return u;
            }
        }
        return null;
    }
}
