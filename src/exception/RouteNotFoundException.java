
package exception;

/**
 *
 * @author Ксю
 */
public class RouteNotFoundException extends IndexOutOfBoundsException{
    public RouteNotFoundException(int id){
        super("Маршрута не существует с id: "+id);
    }
}
