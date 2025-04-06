package backend.apiscart.component.util.log;

public interface LogUtil { 
    public enum TYPELOG {
        INFO,ERROR,WARNING,DEBUG
    }
    boolean write(TYPELOG typeLog, String message, Object... arguments) ;
}
