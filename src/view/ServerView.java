/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.*;

import java.net.*;
import net.*;
import model.Airport;
import model.Flight;
import model.Plane;
import model.Route;
import java.util.concurrent.TimeoutException;
import java.util.ArrayList;
/**
 *
 * @author GeneraL
 */
public class ServerView implements View{
    
    private Socket socket;
    private ArrayList<Socket> sockets=new ArrayList<Socket>();
    
    
    
    private int currentClient;
    ServerSocket serverSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
    public ServerView() throws IOException{
        serverSocket = new ServerSocket(NetProtocol.defaultPort);
        System.err.println("server started");
    }
    public ServerView(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        System.err.println("server started");
    }
    
    public void printSomeInfo(String info){
        writeResponse(new String(info+"\n"));
    } 

    private void serchClients(){
        try{
        Socket socket;
        socket = serverSocket.accept();
        }        catch (IOException e){
            System.err.println("IOException while reading client");
        }
        
    }
    /**
     * вводит данные пользователя
     * @return строка, введеная пользователем
     */
    
    public String giveInput(){
        try{
        socket = serverSocket.accept();
        inputStream= new BufferedInputStream(socket.getInputStream());
        outputStream= new BufferedOutputStream(socket.getOutputStream());
        //System.err.println("socket accepted");
        byte[] command=NetProtocol.readInfo(inputStream);
        return new String(command,NetProtocol.encoding);

        }
        catch (IOException e){
            System.err.println("IOException while reading client");
            return null;
        }
        catch (TimeoutException e){
           System.err.println("TimeoutException while reading client");
            return null;
        }
    }
    /**
     * выводит информацию по азропорту:<li>Id <li>Тип <li>Имя <li>Местоположение 
     * @param airport аэропорт
     */
    public void printAirport(Airport airport){
        StringBuffer res=new StringBuffer();
        res.append("Id:"+airport.getId());
        res.append("/n");
        res.append("Type:"+airport.toString());
        res.append("/n");
        res.append("Name:"+airport.getName());
        res.append("/n");
        res.append("Location:"+airport.getLocation());
        res.append("/n");
        writeResponse(new String(res));
    }
    /**
     * выводит информацию по самолету:<li>Id <li>Тип <li>Имя 
     * <li>Бортовой номер <li>Потребление топлива 
     * <li>число пассажирских мест
     * @param plane самолет
     */
    public void printPlane(Plane plane){
        StringBuffer res=new StringBuffer();
        res.append("Id:"+plane.getId());
        res.append("/n");
        res.append("Type:"+plane.toString());
        res.append("/n");
        res.append("Name:"+plane.getName());
        res.append("/n");
        res.append("Number:"+plane.getNumber());
        res.append("/n");
        res.append("Fuel Consumption:"+plane.getFuelConsumption());
        res.append("/n");
        res.append("Passenger Seats:"+plane.getPassengerSeatsCount());
        writeResponse(new String(res));
    }
    /**
     * выводит информацию по рейсу:<li>Id рейса<li>Тип рейса<li>Id самолета <li> Id маршрута
     * <li>стоимость билета
     * <li>время взлета
     * <li>время посадки
     * <li>время в полете
     * @param flight рейс
     */
    public void printFlight(Flight flight, Route route, Plane plane){
        StringBuffer res=new StringBuffer();
        res.append("Flight Id: "+flight.getId());
        res.append("/n");
        res.append("Flight type: "+flight.toString());
        res.append("/n");
        res.append("Plane Id: "+flight.getPlane());
        res.append("/n");
        res.append("Route Id: "+flight.getRoute());
        res.append("/n");
        res.append("Ticket price: "+flight.ticketPrice(route, plane));
        res.append("/n");
        res.append("Takeoff time: "+flight.getTakeOffTimeShedule());
        res.append("/n");
        res.append("Landing time: "+flight.getLandingTimeShedule());
        res.append("/n");
        res.append("Time at flight: "+flight.timeAtFlight());
        writeResponse(new String(res));
    }
    /**
     * выводит информацию по маршруту:<li>Id<li>Тип<li>Id порта отправления <li> Id порта прибытия
     * @param route маршрут
     */
   public void printRoute(Route route){ 
        StringBuffer res=new StringBuffer();
            res.append("Id: "+route.getId());
            res.append("/n");
            res.append("Type: "+route.toString());
            res.append("/n");
            res.append("- From Id: "+route.getTakeOffPort());
            res.append("/n");
            res.append("- To Id: "+route.getLandingPort());
            res.append("/n");
            res.append("Distance: "+route.getDistance());
            res.append("/n");
        writeResponse(new String(res));
    }
        /**
         * печатает число рейсов в БД
         * @param flightCount число рейсов
         */
    public void printFlightCount(int flightCount){
        writeResponse("Number of flighs: "+flightCount);
    }
         /**
         * печатает число маршрутов в БД
         * @param routeCount число маршрутов
         */
    public void printRouteCount(int routeCount){
        writeResponse("Number of routs: "+routeCount);
    }
         /**
         * печатает число самолетов в БД
         * @param planeCount число самолетов
         */
    public void printPlaneCount(int planeCount){
        writeResponse("Number of planes: "+planeCount);
    }
         /**
         * печатает число аэропортов в БД
         * @param portCount число аэропортов
         */
    public void printAirportCount(int portCount){
        writeResponse("Number of airports: "+portCount);
    }
    private void writeResponse(byte[] response){
        try{
            outputStream.write(response);
        }catch (IOException e){
            System.err.println("IOException while ansvering to client");
        }
    }
    private void writeResponse(String response){
            writeResponse(response.getBytes());
    }
    public void flush(){
        try{
           outputStream.write(NetProtocol.endOfMessage.getBytes());
            outputStream.flush();
        }catch (IOException e){
            System.err.println("IOException flushing connection");
        }
    }
    
}
 