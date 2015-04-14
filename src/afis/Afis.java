package afis;
import controller.Controller;
import java.io.IOException;


import model.Model;
import model.Plane;
import view.*;
/**
 * точка входа в сервер
 * @author GeneraL
 * фывмфытимфыатимываьимывьтфыивтифывм пои
 */
public class Afis {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
            Model model = new Model();
            LocalView view = new LocalView();//для локалбного ввода-вывода команд
            //ServerView view=new ServerView(8080);//для ввода-вывода команд в сеть
            Controller controller = new Controller(model, view);
            controller.launch(new String[0]);
    }
}
