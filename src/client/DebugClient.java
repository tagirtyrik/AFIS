package client;
import client.view.CommandClass;
import client.view.MainForm;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author GeneraL
 */
public class DebugClient {
        /*
        точку входа для отладки ClientSender
        ВНИМАНИЕ! если сервер не видит вменяемой активности клиента в течении NetProtocol.timeOut милисекунд,
                                                    он сбрасывает соединение без подтверждения 
        */
    public static void main(String[] args) throws InterruptedException{
    run:{
        MainForm.main();
        try{
            
        while (true){
            ClientSender sender=new ClientSender();
            System.err.println("Соединение создано");
            System.out.print(sender.sendData(giveInput()));
           
            
            System.out.print(sender.sendData(CommandClass.getCommand()));
            CommandClass.setSomeInformation(CommandClass.getCommand());
            }
            
        }catch (UnknownHostException e){
            System.err.println(e.getMessage());
        }catch (IOException e){
            System.err.println(e.getMessage());
        }catch( TimeoutException e ){ 
            System.err.println("Нет ответа с сервера");
            break run;
        }
    }
    }
        public static String giveInput(){
        Scanner input = new Scanner( System.in );
        input.useDelimiter("\n");
        
        return input.next();
        
    }
}
