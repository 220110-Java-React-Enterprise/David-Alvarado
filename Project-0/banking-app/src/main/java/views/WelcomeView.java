package views;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Class in charge of the first view that all users see when
// starting app.
public class WelcomeView extends View {

    public WelcomeView(){
        viewName = "WelcomeMenu";
        viewManager = ViewManager.getViewManager();
        viewBasedOnUserInputValue = null;

    }

    @Override
    public void renderView() {
        // Method renders the welcome menu view of the account.
        // INPUT: user input
        // OUTPUT: user interface to console.
        // RETURN: Void
        try {
            File file = new File(System.getProperty("user.dir") + "/src/main/java/arttxt/welcome-title");
            Scanner welcomeText = new Scanner(file);
            while (welcomeText.hasNext()) {
                System.out.println(welcomeText.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("There was a problem with the welcome-title file.");
            System.out.println(e);
            System.out.println("Welcome to RevBanking");
        } finally {
            System.out.print("\n\n\n\n");
            System.out.println("Press (1) you're an existing customer.");
            System.out.println("Press (2) if you're a new customer.");
            System.out.println("Press (Q) to quit.");
            System.out.print("So, what type of customer are you?  ");
            viewBasedOnUserInputValue = viewManager.getScanner().nextLine();
            nextView();

        }
    }


    @Override
    public void nextView() {
        // Method changes view based on user input
        // INPUT: user input saved to local variable and accessed here.
        // OUTPUT: next view or quit program
        // RETURN: Void
        if (viewBasedOnUserInputValue.equals("2")){
            viewManager.navigate("RegistrationForm");
        }else if (viewBasedOnUserInputValue.equals("1")){
            viewManager.navigate("login");
        } else if (viewBasedOnUserInputValue.equals("Q")){
            viewManager.quit();
        }else{
            invalidEntry();
        }
    }


    public void invalidEntry(){
        // Method used to let user know they made mistake
        // INPUT: none
        // OUTPUT: prints to console
        // RETURN: Void
        System.out.println("You've entered an invalid entry. Please try again.");
        System.out.println("If you're an existing customer, please type \"existing\".");
        System.out.println("Otherwise, if you're a new customer, please type in \"new\".");
        System.out.print("So, what type of customer are you?  ");
        viewBasedOnUserInputValue = viewManager.getScanner().nextLine();

    }
}
