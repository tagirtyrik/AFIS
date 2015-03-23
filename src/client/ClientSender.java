package client;
import java.io.*;
import java.net.*;
import java.util.concurrent.TimeoutException;
import net.*;
import model.*;
import exception.*;
import model.airport.*;
import model.route.*;
import java.util.ArrayList;
/**
 *класс для пересылки комманд-ответов
 * @author GeneraL
 */
public class ClientSender {
    private static final String defaultHost="127.0.0.1";
    private static final String lineSplitter="/n";
    private static final String contentSplitter=":";
    private static final String argumentSplitter=" ";
    private String host;
    private int port;
    static BufferedInputStream input=null;
    static BufferedOutputStream output=null;
    Socket socket = null;
    
    public ClientSender()throws UnknownHostException,IOException{
        host=defaultHost;
        port=NetProtocol.defaultPort;
        init();
    }
    public ClientSender(String host,int port)throws UnknownHostException,IOException{
        this.host=host;
        this.port=port;
        init();
        
    }
    /**
     * инициализация соединения. запускать запускается само
     * @throws UnknownHostException
     * @throws IOException 
     */
    public void init() throws UnknownHostException,IOException{
              socket = new Socket(host,port);
              output = new BufferedOutputStream(socket.getOutputStream());
              input = new BufferedInputStream(socket.getInputStream());  
    }
    /**
     * для закрытия соединения
     * @throws IOException 
     */
    public void close()throws IOException{
        output.close();
        input.close();
        socket.close();
    }
    /**
     * основная функция для общения с сервером
     * @param data - ввод
     * @return вывод
     * @throws IOException 
     */
    public byte[] sendData(byte[] data)throws IOException, TimeoutException{
        
        output.write(data);
        output.write(NetProtocol.endOfMessage.getBytes());
        output.flush();

        byte[] buffer=NetProtocol.readInfo(input);
        return buffer;
    }
    /**
     * основная функция для общения с сервером
     * @param data - ввод
     * @return вывод
     * @throws IOException 
     */
   /* public String[] sendData(String[] data)throws IOException,TimeoutException{
        String[] answer=new String[data.length];
        for(int i=0;i<data.length;i++){
               answer[i]=sendData(data[i]);
        }
        return answer;
    }*/
    /**
     * основная функция для общения с сервером
     * @param data - ввод
     * @return вывод
     * @throws IOException 
     */
    public String sendData(String data)throws IOException,TimeoutException{
        System.err.println(data);
         return new String(sendData(data.getBytes()),"UTF-8");
    }
    /**
     * считывает информацию о портах
     * @param cmd - возможные значения: portlist, getport id, addport id name location, delport
     * @return
     * @throws IOException
     * @throws TimeoutException
     * @throws AirportNotFoundException
     * @throws CommandNotFoundException
     * @throws InvalidArgumentsException 
     */
    public ArrayList<Airport> sendPort(String cmd)throws IOException,
            TimeoutException,AirportNotFoundException,
            CommandNotFoundException,InvalidArgumentsException{
        ArrayList<Airport> airports=new ArrayList<Airport>();
        String[] lines=sendData(cmd).split(lineSplitter);
        Integer id=null;
        String name=null;
        String location=null;
        for(int i=0;i<lines.length;i++){
            lines[i]=lines[i].replaceAll("\r\n\r\n", "");//исправление недостатков протокола
            String[] line= lines[i].split(contentSplitter);
            if(line[0].equalsIgnoreCase("id"))id=Integer.parseInt(line[1]);
            else if(line[0].equalsIgnoreCase("Type")){}//если будет больше классов аэропортов, тут можно настроить фабрику
            else if(line[0].equalsIgnoreCase("Name"))name=line[1];
            else if(line[0].equalsIgnoreCase("Location"))location=line[1];
            else if(line[0].equalsIgnoreCase("самолет добавлен"))break;
            else if(line[0].equalsIgnoreCase("Аэропорт удален"))break;
            else if(line[0].equalsIgnoreCase("Список пуст"))break;
            else {//exception
                String[] exeption=line[0].split("\\.");
                if(exeption[0].equalsIgnoreCase("exception")){
                      if(exeption[1].equalsIgnoreCase("AirportNotFoundException"))
                          throw new AirportNotFoundException(line[1]);
                      else if(exeption[1].equalsIgnoreCase("CommandNotFoundException"))
                              throw new CommandNotFoundException(line[1]);
                      else if(exeption[1].equalsIgnoreCase("InvalidArgumentsException"))
                          throw new InvalidArgumentsException(line[1]);
                      else{
                       //выброшено неведомо исключение
                          break;
                      }
                }else{
                    //происходит неведома ерунда
                    //.. или помощь
                    break;
                }
            }
        if(id!=null && name!=null && location!=null){
                airports.add(new InternationalAirport(id,name,location));
                    id=null;
                    name=null;
                    location=null;
            }   
        }
        return airports;
    }
    
     public ArrayList<Route> sendRoute(String cmd)throws IOException,
            TimeoutException,RouteNotFoundException,
            CommandNotFoundException,InvalidArgumentsException{
        ArrayList<Route> routes=new ArrayList<Route>();
        String[] lines=sendData(cmd).split(lineSplitter);
        Integer id=null;
        Double distance=null;
        Integer aId=null;
        Integer bId=null;
        for(int i=0;i<lines.length;i++){
            lines[i]=lines[i].replaceAll("\r\n\r\n", "");//исправление недостатков протокола
            
            String[] line= lines[i].split(contentSplitter);
            if(line[0].equalsIgnoreCase("id"))id=Integer.parseInt(line[1].replace(" ", ""));
            else if(line[0].equalsIgnoreCase("Type")){}//если будет больше классов маршрутов, тут можно настроить фабрику
            else if(line[0].equalsIgnoreCase("- From Id"))aId=Integer.parseInt(line[1].replace(" ", ""));
            else if(line[0].equalsIgnoreCase("- To Id"))bId=Integer.parseInt(line[1].replace(" ", ""));
            else if(line[0].equalsIgnoreCase("Distance"))distance=Double.parseDouble(line[1].replace(" ", ""));
            else if(line[0].equalsIgnoreCase("Маршрут добавлен"))break;
            else if(line[0].equalsIgnoreCase("Маршрут удален"))break;
            else if(line[0].equalsIgnoreCase("Список пуст"))break;
            else {//exception
                String[] exeption=line[0].split("\\.");
                if(exeption[0].equalsIgnoreCase("exception")){
                      if(exeption[1].equalsIgnoreCase("RouteNotFoundException"))
                          throw new RouteNotFoundException(line[1]);
                      else if(exeption[1].equalsIgnoreCase("CommandNotFoundException"))
                              throw new CommandNotFoundException(line[1]);
                      else if(exeption[1].equalsIgnoreCase("InvalidArgumentsException"))
                          throw new InvalidArgumentsException(line[1]);
                      else{
                       //выброшено неведомо исключение
                          break;
                      }
                }else{
                    //происходит неведома ерунда
                    //.. или помощь
                    break;
                }
            }
        if(id!=null && distance!=null && aId!=null && bId!=null){
                routes.add(new RegularRoute(id,aId,bId,distance));
                id=null; 
                distance=null; 
                aId=null ; 
                bId=null;
            }   
        }
        return routes;
    }
    
}

