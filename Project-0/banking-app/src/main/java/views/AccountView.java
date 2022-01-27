package views;

import crud.AccountCRUD;
import objectmodels.AccountModel;
import objectmodels.UserModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class AccountView extends View {

    public AccountView(){
        viewName = "accounts";
        viewManager = ViewManager.getViewManager();
        viewBasedOnUserInputValue = null;

    }

    @Override
    public void renderView() {
        try {
            File file = new File(System.getProperty("user.dir") + "/src/accounts-title");
            Scanner welcomeText = new Scanner(file);
            System.out.print("\n\n\n\n");
            while (welcomeText.hasNext()) {
                System.out.println(welcomeText.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("There was a problem with the accounts-title file.");
            System.out.println(e);
            System.out.println("Welcome to your accounts page");
        } finally {
            System.out.print("\n\n\n\n");
            System.out.println("Press (1) to view your accounts.");
            System.out.println("Press (2) to create a new account.");
            System.out.println("Press (3) to deposit to an account.");
            System.out.println("Press (4) to withdraw from an account.");
            System.out.println("Press (Q) to quit.");
            viewBasedOnUserInputValue = viewManager.getScanner().nextLine();

            switch (viewBasedOnUserInputValue){
                case "1":
                    accountSummary();
                    break;
                case "2":
                    createNewBankAcc();
                    break;
                case "3":
                    depositToExistingAcc();
                    break;
                case"4":
                    withdrawFromExisitngAcc();
                    break;
                case "Q":
                    viewManager.quit();
                    break;

            }

        }
    }

    public void withdrawFromExisitngAcc(){
        UserModel currentUser = UserModel.getUserModel();
        AccountCRUD crud = new AccountCRUD();
        LinkedList<AccountModel> accList;
        LinkedList<Integer> accountNumbers = new LinkedList<>();
        accList = crud.retrieveBankAccounts(currentUser.getId());

        if (accList != null) {
            System.out.println("\n Account summary: \n");
            for (AccountModel account : accList) {
                System.out.println("Account #: " + account.getAccountId() +
                        " Balance: $" + String.format("%.2f", account.getCurrentBalance()));
                accountNumbers.add(account.getAccountId());
            }

            System.out.println("\n Please enter one of the account numbers above");
            System.out.println("that you wish to WITHDRAW FROM: ");
            int answer = viewManager.getScanner().nextInt();
            viewManager.getScanner().nextLine();
            if (accountNumbers.contains(answer)){

                System.out.println("Enter the amount you want to WITHDRAW (positive numbers only): ");
                float withdrawAmount = 0.00f;
                try{
                    withdrawAmount = viewManager.getScanner().nextFloat();
                }catch (InputMismatchException e){
                    System.out.println("\n Error, you did not enter a valid number. Try again.");
                    viewManager.getScanner().nextLine();
                    withdrawFromExisitngAcc();
                    return;
                }
                viewManager.getScanner().nextLine();

                crud.withdrawal(answer, withdrawAmount, accList.get(accountNumbers.indexOf(answer)).getCurrentBalance());
                System.out.println("\n\n Press any key followed by enter to continue...");
                viewManager.getScanner().nextLine();
            }else{
                System.out.println("Not a valid account number.");
                depositToExistingAcc();

            }

            accountSummary();

        }else {
            System.out.println("\n No accounts were found/created...");
            renderView();
        }
    }



    public void depositToExistingAcc() {
        UserModel currentUser = UserModel.getUserModel();
        AccountCRUD crud = new AccountCRUD();
        LinkedList<AccountModel> accList;
        LinkedList<Integer> accountNumbers = new LinkedList<>();
        accList = crud.retrieveBankAccounts(currentUser.getId());

        if (accList != null) {
            System.out.println("\n Account summary: \n");
            for (AccountModel account : accList) {
                System.out.println("Account #: " + account.getAccountId() +
                        " Balance: $" + String.format("%.2f", account.getCurrentBalance()));
                accountNumbers.add(account.getAccountId());
            }

            System.out.println("\n Please enter one of the account numbers above");
            System.out.println("that you wish to make a DEPOSIT to: ");
            int answer = viewManager.getScanner().nextInt();
            viewManager.getScanner().nextLine();
            if (accountNumbers.contains(answer)){

                System.out.println("Enter the amount you want to DEPOSIT (positive numbers only): ");
                float depositAmount = 0.00f;
                try{
                    depositAmount = viewManager.getScanner().nextFloat();
                    viewManager.getScanner().nextLine();
                }catch (InputMismatchException e){
                    System.out.println("\n Error, you did not enter a valid number. Try again.");
                    viewManager.getScanner().nextLine();
                    depositToExistingAcc();
                    return;
                }

                crud.makeDeposit(answer, depositAmount, accList.get(accountNumbers.indexOf(answer)).getCurrentBalance());
                System.out.println("\n\n Press any key followed by enter to continue...");
                viewManager.getScanner().nextLine();
            }else{
                System.out.println("Not a valid account number.");
                depositToExistingAcc();

            }

            accountSummary();

        }else {
            System.out.println("\n No accounts were found/created...");
            renderView();
        }
    }
    public void accountSummary(){
        UserModel currentUser = UserModel.getUserModel();
        AccountCRUD crud = new AccountCRUD();
        LinkedList<AccountModel> accList;
        accList = crud.retrieveBankAccounts(currentUser.getId());
        if (accList != null){
            System.out.println("\n Account summary: \n");

            for(AccountModel account : accList ){
                System.out.println("Account #: " + account.getAccountId() +
                " Balance: $" + String.format("%.2f",account.getCurrentBalance()));
            }
            System.out.println("\n\n Press any key followed by enter to continue...");
            viewManager.getScanner().nextLine();
        }else {
            System.out.println("\n No accounts were found/created...");

        }
    }
    public void createNewBankAcc(){
        UserModel currentUser = UserModel.getUserModel();
        AccountCRUD crud = new AccountCRUD();
        if(crud.createNewAccount(currentUser.getId())){
            renderView();
        }else{
            System.out.println("Since there was a problem creating your account, program will terminate.");
            viewManager.quit();
        }

    }

    @Override
    public void nextView() {
        System.out.println("Ready to retrieve next view");
    }

    @Override
    public void invalidEntry() {
        System.out.println("invalid entry/input");
    }


}
