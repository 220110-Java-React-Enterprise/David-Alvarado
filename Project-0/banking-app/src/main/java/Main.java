import views.*;

public class Main {
    public static void main(String[] args){
        //Singleton initialized
        ViewManager viewManager = ViewManager.getViewManager();
        // views registered.
        viewManager.registerView(new WelcomeView());
        viewManager.registerView(new RegistrationView());
        viewManager.registerView(new LoginView());
        viewManager.registerView(new AccountView());
        viewManager.navigate("WelcomeMenu");


        // While loop used to keep program running
        while(viewManager.isRunning()){
            viewManager.render();
        }

    }


}
