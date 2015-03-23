
package exception;

/**
 *
 * @author GeneraL
 */
public class RouteNotFoundException extends IndexOutOfBoundsException{
    public RouteNotFoundException(){
            super();
    }
    public RouteNotFoundException(String s){
        super(s);
    }
    public RouteNotFoundException(int id){
        super("Маршрута не существует с id: "+id);
    }
}
