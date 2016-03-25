
package exception;

/**
 *
 * @author Ксю
 */
public class InvalidArgumentsException extends IndexOutOfBoundsException{
    public InvalidArgumentsException(){
        super();
    }
    public InvalidArgumentsException(String cmd,String help){
        super("Неверные аргументы комманды \""+cmd+"\"\n"+help);
    }
    public InvalidArgumentsException(String cmd){
        super("Неверные аргументы комманды \""+cmd+"\"");
    }
}
