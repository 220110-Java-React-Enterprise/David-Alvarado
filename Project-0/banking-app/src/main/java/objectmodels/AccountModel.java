package objectmodels;

import java.sql.Connection;

// Model used to hold all information pertaining to account side of app.
public class AccountModel {
    private int accountId;
    private float currentBalance;


    public AccountModel(){

    }

    public AccountModel(int accountId, float currentBalance){
        this.accountId = accountId;
        this.currentBalance = currentBalance;
    }


    public float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(float currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

}

