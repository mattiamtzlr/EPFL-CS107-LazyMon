package ch.epfl.cs107.icmon.gamelogic.actions;

public class LogAction implements Action {
    private final String message;

    /**
     * Prints a log message to the console.
     * @param msg The message to print. (String)
     */
    public LogAction(String msg){
        this.message = msg;
    }

    @Override
    public void perform() {
        System.out.println("LogAction: " + message);
    }
}
