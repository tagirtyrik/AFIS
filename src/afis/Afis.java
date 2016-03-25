package afis;
import controller.Controller;
import java.io.IOException;


import model.Model;
import model.Plane;
import view.*;
/**
 * точка входа в сервер
 * @author Ксю
 * фывмфытимфыатимываьимывьтфыивтифывм пои
 */
public class Afis {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
            Model model = new Model();
            Controller controller = new Controller(model);
            controller.launch(new String[0]);
    }
}
