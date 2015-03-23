package net;
import java.io.*;
import java.util.concurrent.TimeoutException;
import java.util.Timer;
import java.util.TimerTask;
/**
 *класс для хранения методов и констант, относящихся к правилам сетевого взаимодействия
 * @author GeneraL
 */
public class NetProtocol {
    public static final int defaultPort=8080;
    public static final String endOfMessage="\r\n\r\n";
    public static final String encoding="UTF-8";
    public static final int timeOut=5000;
    /**
     * Проверка, заканчивается ли сообщение корректно
     * @param message сообщение
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static boolean endOfMessageReached(byte[] message)throws UnsupportedEncodingException{
        String msg=new String(message,encoding);
        if(endOfMessage.length()>msg.length())return false;
        if(msg.regionMatches(msg.length()-endOfMessage.length(), endOfMessage, 0, endOfMessage.length()))
            return true;
        else 
            return false;
    }
    public static byte[] readInfo(InputStream inputStream)
            throws UnsupportedEncodingException,IOException,TimeoutException{
        
        byte[] allInfo=new byte[0];
        NetTimeOut timer=new NetTimeOut(timeOut);
        do{
        while(inputStream.available()==0){
            if (timer.isExpired)throw new TimeoutException();
        }
            byte[] buffer=new byte[inputStream.available()];
            inputStream.read(buffer);
            /*System.err.println("read "+new String(buffer));
            System.err.println("lenght "+buffer.length);*/
            byte[] temp=new byte[buffer.length+allInfo.length];
            for(int i=0;i<allInfo.length;i++){
                temp[i]=allInfo[i];
            }
            int j=allInfo.length;
            for(int i=0;i<buffer.length;i++){
                temp[j]=buffer[i];
                j++;
            }
            allInfo=temp;
        }while (!endOfMessageReached(allInfo));
        int infoOnlyLenght=allInfo.length-endOfMessage.getBytes().length;
        byte[] infoOnly=new byte[infoOnlyLenght];
        for(int i=0;i<infoOnlyLenght;i++){
                infoOnly[i]=allInfo[i];
            }
        return infoOnly;
    }
}

 class NetTimeOut {
  Timer timer;
  boolean isExpired=false;
  public NetTimeOut(int msec) {
    timer = new Timer();
    timer.schedule(new RemindTask(), msec);
  }

  class RemindTask extends TimerTask{
    public void run(){
        timer.cancel();
        isExpired=true;
    }
  }
 }
