package ATM_Backend;
import java.util.*;
public class ATM {
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Bank TheBank = new Bank("State Bank Of India");

    User aUser = TheBank.addUser("Parvez","Akhtar","1234");

    Account newAccount = new Account("Checking",aUser,TheBank);
    aUser.addAccount(newAccount);
    TheBank.addAccount(newAccount);

    User curUser;
    while(true){
        curUser = ATM.MainMenuPrompt(TheBank , scanner);

        ATM.printUserMenu(curUser ,scanner);

    }




    }

    public static User MainMenuPrompt(Bank TheBank , Scanner sc){
        String UserID;
        String pin;
        User authUser;
        do{
            System.out.printf("\n\n Welcome to %s\n\n",TheBank.getName());
            System.out.print("Enter User ID : ");
            UserID = sc.nextLine();
            System.out.print("Enter Pin : ");
            pin=sc.nextLine();

            authUser = TheBank.userLogin(UserID , pin);
            if(authUser==null){
                System.out.println("Incorrect UserId/Pin Combination "+ "Please Try Again");
            }
        }while (authUser==null);
        return authUser;
    }
    public static void printUserMenu(User theUser  , Scanner sc ){
        theUser.printAccountsSummery();
        int choice;
        do{
            System.out.printf("Welcome %s , What whould you like to do?\n", theUser.getFirstName());
            System.out.println(" 1) Show Transaction history");
            System.out.println(" 2) Withdrawl ");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.print("\nEnter your choice : ");
            choice=sc.nextInt();

            if(choice<1 || choice>5){
                System.out.println("Incorrect choice ::");
            }
        }while (choice<1||choice>5);
        switch (choice) {
            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawlFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                sc.nextLine();
                break;
        }
        if(choice!=5){
            ATM.printUserMenu(theUser ,sc);
        }
    }

    public static void showTransHistory(User theUser , Scanner sc){
        int theAcct;

        do{
            System.out.printf("Enter the number(1-%d) of the account\n"+"whose transaction  you want to see : ",theUser.numAccounts());
            theAcct=sc.nextInt()-1;
            if(theAcct<0 || theAcct>=theUser.numAccounts()){
                System.out.println("Invalid Account , Please Try Again.");
            }
        }while(theAcct<0 || theAcct>=theUser.numAccounts());
        theUser.printAcctTransHistory(theAcct);
    }

    public static void transferFunds(User theUser  ,  Scanner sc){
        int fromAcct;
        int toAcct;
        double amount;
        double acctbal ;
         do{
             System.out.printf("Enter the number(1-%d) of the account \n"+"to transfer from : ",theUser.numAccounts());
             fromAcct = sc.nextInt()-1;
             if(fromAcct<0 || fromAcct>=theUser.numAccounts()){
                 System.out.println("Invalid Account ,  Please Try again");
             }
         }while (fromAcct<0 || fromAcct>=theUser.numAccounts());
         acctbal = theUser.getAcctBalance(fromAcct);
        do{
            System.out.printf("Enter the number(1-%d) of the account \n"+"to transfer to : ",theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct<0 || toAcct>=theUser.numAccounts()){
                System.out.println("Invalid Account ,  Please Try again");
            }
        }while (toAcct<0 || toAcct>=theUser.numAccounts());

        do {
            System.out.printf("Enter the amount to transfer (max : $%.02f) : $",
                    acctbal);
            amount = sc.nextInt();
            if(amount<0){
                System.out.println("amount must be greater than zero");
            }else if(amount>acctbal){
                System.out.printf("amount must not be greater than\n"+" balance of %.02f.\n ", acctbal);
            }
        }while(amount<0 || amount>acctbal);

        theUser.addAcctTransaction(fromAcct , -1*amount ,String.format("Transfer to Account %s ",theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct , amount ,String.format("Transfer from Account %s ",theUser.getAcctUUID(fromAcct)));



    }

    public static void withdrawlFunds(User theUser , Scanner sc){
        int fromAcct;
        double amount;
        double acctbal ;
        String memo;
        do{
            System.out.printf("Enter the number(1-%d) of the account \n"+"to Withdraw from : ",theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct<0 || fromAcct>=theUser.numAccounts()){
                System.out.println("Invalid Account ,  Please Try again");
            }
        }while (fromAcct<0 || fromAcct>=theUser.numAccounts());
        acctbal = theUser.getAcctBalance(fromAcct);

        do {
            System.out.printf("Enter the amount to Withdraw (max : $%.02f) : $",acctbal);
            amount = sc.nextInt();
            if(amount<0){
                System.out.println("amount must be greater than zero");
            }else if(amount>acctbal){
                System.out.printf("amount must not be greater than\n"+" balance of %.02f.\n ", acctbal);
            }
        }while(amount<0 || amount>acctbal);

        sc.nextLine();

        System.out.println("Enter a memo : ");
        memo = sc.nextLine();

        theUser.addAcctTransaction(fromAcct , -1*amount , memo);

    }

    public static void depositFunds(User theUser , Scanner  sc ){
        int toAcct;
        double amount;
        double acctbal ;
        String memo;
        do{
            System.out.printf("Enter the number(1-%d) of the account \n"+"to Deposit in : ",theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct<0 || toAcct>=theUser.numAccounts()){
                System.out.println("Invalid Account ,  Please Try again");
            }
        }while (toAcct<0 || toAcct>=theUser.numAccounts());
        acctbal = theUser.getAcctBalance(toAcct);

        do {
            System.out.printf("Enter the amount to Deposit (max : $%.02f) : $",acctbal);
            amount = sc.nextInt();
            if(amount<0){
                System.out.println("amount must be greater than zero");
            }
        }while(amount<0);

        sc.nextLine();

        System.out.println("Enter a memo : ");
        memo = sc.nextLine();

        theUser.addAcctTransaction(toAcct , amount , memo);
    }
}
