
package exception;

/**
 *
 * @author GeneraL
 */
public class CommandNotFoundException extends ComandException{
    public CommandNotFoundException(){
        super();
    }
    public CommandNotFoundException(String cmd){
        super("Комманда \""+cmd+"\" не найдена");
    }
    
}
