
package exception;

/**
 *
 * @author Ксю
 */
public class AirportNotFoundException extends IndexOutOfBoundsException{
    public AirportNotFoundException(int id){
        super("Аэропорта не существует с id: "+id);
    }
}
