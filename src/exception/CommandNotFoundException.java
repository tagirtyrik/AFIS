
package exception;

/**
 *
 * @author Ксю
 */
public class CommandNotFoundException extends ComandException{
    public CommandNotFoundException(){
        super();
    }
    public CommandNotFoundException(String cmd){
        super("Комманда \""+cmd+"\" не найдена");
    }
    
}
