import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoginView extends View {

    LoginView(){
        viewName = "login";
        viewManager = ViewManager.getViewManager();
        viewBasedOnUserInputValue = null;
    }
    @Override
    public void renderView() {
        String username;
        String password;
        try {
            File file = new File(System.getProperty("user.dir") + "/src/login-title");
            Scanner welcomeText = new Scanner(file);
            System.out.print("\n\n\n\n");
            while (welcomeText.hasNext()) {
                System.out.println(welcomeText.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("There was a problem with the login-title file.");
            System.out.println(e);
            System.out.println("Please login with your username and password.");
        } finally {
            System.out.print("\n\nPlease enter your Username: ");
            username = viewManager.getScanner().nextLine();
            System.out.print("\nPassword: ");
            password = viewManager.getScanner().nextLine();

            UserCRUD crud = new UserCRUD();
            if (crud.login(username,password)){
                nextView();
            }else{
                invalidEntry();
            }

        }
    }

    @Override
    public void nextView() {
        viewManager.navigate("accounts");
    }
    @Override
    public void invalidEntry(){renderView();}

}
