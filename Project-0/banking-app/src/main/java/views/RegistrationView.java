package views;

import crud.CredentialChecker;
import crud.UserCRUD;
import objectmodels.UserModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RegistrationView extends View {


    public RegistrationView(){
        viewName = "RegistrationForm";
        viewManager = ViewManager.getViewManager();

    }

    @Override
    public void renderView() {
        // Method renders the registration form for user.
        // INPUT: user input
        // OUTPUT: user interface to console.
        // RETURN: Void
        UserModel user = UserModel.getUserModel();
        CredentialChecker check = CredentialChecker.getCredentialChecker();
        String firstName;
        String lastName;
        String phoneNumber;
        String email;
        String username;
        String password;

        try {
            File file = new File(System.getProperty("user.dir") + "/src/main/java/arttxt/registration-title");
            Scanner welcomeText = new Scanner(file);
            System.out.print("\n\n\n\n");
            while (welcomeText.hasNext()) {
                System.out.println(welcomeText.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("There was a problem with the registration-text file.");
            System.out.println(e);
            System.out.println("Please fill out the registration form below.");
        } finally {
            System.out.print("\n\nPlease enter your first name: ");
            firstName = viewManager.getScanner().nextLine();
            if (check.isNameValid(firstName)){
                user.setFirstName(firstName);
            }
            //user.setFirstName(viewManager.getScanner().nextLine());
            System.out.print("\nPlease enter you last name: ");
            lastName = viewManager.getScanner().nextLine();
            if (check.isNameValid(lastName)){
                user.setLastName(lastName);
            }

            //user.setLastName(viewManager.getScanner().nextLine());
            System.out.print("\nPlease enter your phone number: ");
            phoneNumber = viewManager.getScanner().nextLine();
            if (check.isPhoneValid(phoneNumber)){
                user.setPhoneNumber(phoneNumber);
            }
            //user.setPhoneNumber(viewManager.getScanner().nextLine());
            System.out.print("\nPlease enter email: ");
            email = viewManager.getScanner().nextLine();
            if (check.isEmailValid(email)){
                user.setEmail(email);
            }
            //user.setEmail(viewManager.getScanner().nextLine());
            System.out.print("\nPlease enter desired username: ");
            username = viewManager.getScanner().nextLine();
            if (check.isUsernameValid(username)){
                user.setUsername(username);
            }
            //user.setUsername(viewManager.getScanner().nextLine());
            System.out.print("\nPlease enter a password: ");
            user.setPassword(viewManager.getScanner().nextLine());


            //user.setPassword(viewManager.getScanner().nextLine());
            if (user.isNull()){
                System.out.println("\n User input error during registration, try again.");
                System.out.println("Press enter to continue...");
                viewManager.getScanner().nextLine();
                renderView();
                return;
            }else {

                UserCRUD crud = new UserCRUD();
                int id = crud.createNewUser(user);
                if (id == -1) {
                    System.out.println("\n\nError in adding you to our database. Would you like to try again?(y/n)");
                    String answer = viewManager.getScanner().nextLine();
                    if (answer.equals("y")) {
                        renderView();
                    } else {
                        user.setId(id);
                        viewManager.quit();
                    }

                } else {
                    viewBasedOnUserInputValue = "login";
                    nextView();

                }
            }
        }
    }
    @Override
    public void nextView() {
        viewManager.navigate(viewBasedOnUserInputValue);
    }

    @Override
    public void invalidEntry() {
        // Method to prompt user to correct mistakes
        // INPUT: user input
        // OUTPUT: user interface to console.
        // RETURN: Void
        String firstName;
        String lastName;
        String phone;
        System.out.println("\n You've entered invalid characters in one or more fields.(such as a percent symbol in your name... etc ");
        System.out.println("Please try again.");
        System.out.print("\nEnter your first name: ");
        firstName = viewManager.getScanner().nextLine();
        System.out.print("\nPlease enter you last name: ");
        lastName = viewManager.getScanner().nextLine();
        System.out.print("\nPlease enter your phone number: ");
        phone = viewManager.getScanner().nextLine();
    }
}
