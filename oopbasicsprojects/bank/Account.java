package oopbasicsprojects.bank;


public class Account{
    private double balance;
    private int account_number;
    private String account_name;
    private AccountStatus status;

 
 public Account(double balance, int account_number, String account_name){
   if (balance < 0) {
        throw new IllegalArgumentException("Initial balance cannot be negative");
    }
    this.balance= balance;
    this.account_number=account_number;
    this.account_name=account_name;
    this.status=AccountStatus.ACTIVE;
 }
 public String getName(){
    return account_name;
 }
 public AccountStatus getStatus(){
    return status;
 }
 public int getAccNumber(){
    return account_number;
 }
 public double getBalance(){
    return balance;
 }


 //setter
 public void setStatus(AccountStatus status){
    this.status= status;

 }
 private void verify(double amount) {
    if (status != AccountStatus.ACTIVE) {
        throw new IllegalArgumentException("Account is not active");
    }

    if (amount <= 0) {
        throw new IllegalArgumentException("Amount must be greater than 0");
    }
}

 public void deposit(double amount){
   verify(amount);
   this.balance+=amount;
  
    
 }
 public void withdraw(double amount) {
    verify(amount);

    if (amount > balance) {
        throw new IllegalArgumentException("Insufficient funds");
    }

    balance -= amount;
}
 
}
