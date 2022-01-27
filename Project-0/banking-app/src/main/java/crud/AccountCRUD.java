package crud;

import objectmodels.AccountModel;
import objectmodels.UserModel;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;

public class AccountCRUD {
    private final Connection connection;
    private UserModel user;

    public AccountCRUD(){
        connection = ConnectionManager.getConnection();
        user = UserModel.getUserModel();
    }

    public boolean createNewAccount(int user_id){
        String accountTableStatement = "INSERT INTO account (current_balance) VALUES (?)";
        String userAccJctTableStatement = "INSERT INTO user_account_jct (account_id, user_id) VALUES (?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(accountTableStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setFloat(1,0.00f);
            statement.executeUpdate();

            int accId;
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()){
                accId = rs.getInt(1);
                statement = connection.prepareStatement(userAccJctTableStatement);
                statement.setInt(1, accId);
                statement.setInt(2, user_id);
                statement.executeUpdate();
                System.out.println("New bank account created!");
                return true;
            } else{
                System.out.println("Error retrieving account id from freshly created account.");
                return false;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("There was a problem in our system when creating your account.");
            return false;
        }


    }


    public void makeDeposit(Integer id, float depositAmount, float oldAmount){
        String sql = "Update account SET current_balance = ? WHERE account_id =?";

        if (depositAmount < 0){
            System.out.println("\n Unable to deposit a negative value");
            return;
        }else if (depositAmount == 0){
            return;
        }else{
                BigDecimal dA = new BigDecimal(depositAmount);
                BigDecimal oA = new BigDecimal(oldAmount);
            try {
                PreparedStatement pStatement = connection.prepareStatement(sql);
                pStatement.setBigDecimal(1,dA.add(oA));
                pStatement.setInt(2,id);
                pStatement.executeUpdate();
                System.out.println("Deposit Successful");
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }





        }
    }


    public void withdrawal(Integer id, float withdrawAmount, float oldAmount){
        String sql = "Update account SET current_balance = ? WHERE account_id =?";

        if (withdrawAmount < 0){
            System.out.println("\n Unable to withdraw a negative value");
            return;
        }else if (withdrawAmount == 0){
            return;
        }else if ((oldAmount-withdrawAmount) < 0) {
            System.out.println("\n Insufficient funds... sorry!");
            return;
        }else{
            BigDecimal dA = new BigDecimal((withdrawAmount * -1));
            BigDecimal oA = new BigDecimal(oldAmount);
            try {
                PreparedStatement pStatement = connection.prepareStatement(sql);
                pStatement.setBigDecimal(1,dA.add(oA));
                pStatement.setInt(2,id);
                pStatement.executeUpdate();
                System.out.println("Withdrawal Successful");
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }





        }
    }
    public LinkedList<AccountModel> retrieveBankAccounts(int user_id){
        LinkedList<AccountModel> accountList= new LinkedList<>();
        LinkedList<Integer> accountNumbers = new LinkedList<>();

        String sqlJct = "SELECT account_id FROM user_account_jct WHERE user_id=?";
        String sqlAcc = "SELECT * FROM account WHERE account_id = ?";

        try {
            PreparedStatement pStatement = connection.prepareStatement(sqlJct);
            pStatement.setInt(1, user_id);
            pStatement.executeQuery();
            ResultSet results = pStatement.getResultSet();
            while (results.next()){
                accountNumbers.add(results.getInt("account_id"));
            }

            pStatement = connection.prepareStatement(sqlAcc);
            for (int i : accountNumbers ) {
                pStatement.setInt(1, i);
                pStatement.executeQuery();
                results = pStatement.getResultSet();
                while(results.next()){
                    accountList.add(new AccountModel(results.getInt(1),results.getFloat(2)));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return accountList;

    }

}
