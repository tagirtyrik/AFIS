
package exception;

/**
 *
 * @author Ксю
 */
public class PlaneNotFoundException extends IndexOutOfBoundsException{
    public PlaneNotFoundException(int id){
        super("Самолета не существует с id: "+id);
    }
}
