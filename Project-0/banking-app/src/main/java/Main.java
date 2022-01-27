import views.*;

public class Main {
    public static void main(String[] args){
        ViewManager viewManager = ViewManager.getViewManager();

        viewManager.registerView(new WelcomeView());
        viewManager.registerView(new RegistrationView());
        viewManager.registerView(new LoginView());
        viewManager.registerView(new AccountView());
        viewManager.navigate("WelcomeMenu");



        while(viewManager.isRunning()){
            viewManager.render();
        }

    }


}
