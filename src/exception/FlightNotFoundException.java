
package exception;

/**
 *
 * @author Ксю
 */
public class FlightNotFoundException extends IndexOutOfBoundsException{
    public FlightNotFoundException(int id){
        super("Рейса не существует с id: "+id);
    }
}
