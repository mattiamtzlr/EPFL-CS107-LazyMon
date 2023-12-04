package ch.epfl.cs107.icmon.gamelogic.actions;

public class LogAction implements Action {
    private String message;
    public LogAction(String msg){
        this.message = msg;
    }

    @Override
    public void perform() {
        System.out.println("LogAction: " + message);
    }
}
