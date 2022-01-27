import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RegistrationView extends View{


    public RegistrationView(){
        viewName = "RegistrationForm";
        viewManager = ViewManager.getViewManager();

    }

    @Override
    public void renderView() {
        UserModel user = UserModel.getUserModel();
        try {
            File file = new File(System.getProperty("user.dir") + "/src/registration-title");
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
            user.setFirstName(viewManager.getScanner().nextLine());
            System.out.print("\nPlease enter you last name: ");
            user.setLastName(viewManager.getScanner().nextLine());
            System.out.print("\nPlease enter your phone number: ");
            user.setPhoneNumber(viewManager.getScanner().nextLine());
            System.out.print("\nPlease enter email: ");
            user.setEmail(viewManager.getScanner().nextLine());
            System.out.print("\nPlease enter desired username: ");
            user.setUsername(viewManager.getScanner().nextLine());
            System.out.print("\nPlease enter a password: ");
            user.setPassword(viewManager.getScanner().nextLine());


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
    @Override
    public void nextView() {
        viewManager.navigate(viewBasedOnUserInputValue);
    }

    @Override
    public void invalidEntry() {
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
