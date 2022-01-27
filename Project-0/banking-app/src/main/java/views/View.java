package views;

// Abstract class for views to inherit from.
public abstract class View {

    protected String viewName;
    protected ViewManager viewManager;
    protected String viewBasedOnUserInputValue;


    public String getViewName() {
        return viewName;
    }

    public abstract void renderView();
    public abstract void nextView();
    public abstract void invalidEntry();
}
