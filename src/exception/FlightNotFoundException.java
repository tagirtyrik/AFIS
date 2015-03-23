
package exception;

/**
 *
 * @author GeneraL
 */
public class FlightNotFoundException extends IndexOutOfBoundsException{
    public FlightNotFoundException(){
        super();
    }
    public FlightNotFoundException(String s){
        super(s);
    }
    public FlightNotFoundException(int id){
        super("Рейса не существует с id: "+id);
    }
}
