
package exception;

/**
 *
 * @author GeneraL
 */
public class PlaneNotFoundException extends IndexOutOfBoundsException{
    public PlaneNotFoundException(){
        super();
    }
    public PlaneNotFoundException(String s){
        super(s);
    }
    public PlaneNotFoundException(int id){
        super("Самолета не существует с id: "+id);
    }
}
