
package exception;

/**
 *
 * @author GeneraL
 */
public class AirportNotFoundException extends IndexOutOfBoundsException{
    public AirportNotFoundException(){
        super();
    }
    public AirportNotFoundException(String s){
        super(s);
    }
    public AirportNotFoundException(int id){
        super("Аэропорта не существует с id: "+id);
    }
}
