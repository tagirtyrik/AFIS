package controller;
import exception.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Airport;
import model.Flight;
import model.Model;
import model.Plane;
import model.Route;
import model.aircraft.Aircraft;
import model.airport.InternationalAirport;
import model.flight.ReguarFlight;
import model.route.RegularRoute;
import view.View;


public class Controller {
    private Model model=null;
    private View view=null;
    private boolean isLaunched=false;
    public final DateFormat formatter =new SimpleDateFormat("dd.MM.yy-HH:mm");

   public Controller(Model model)throws IOException,
           ClassNotFoundException{
       model.loadData();
       this.model=model;
       this.view=null;
   }
    public Controller(Model model,View view) throws IOException,
            ClassNotFoundException{
        model.loadData();
        this.model=model;
        this.view=view;
        
    }

    public void parse(String cmd,String[] arguments)throws AirportNotFoundException, FlightNotFoundException,
            PlaneNotFoundException,  RouteNotFoundException, InvalidArgumentsException,
            java.sql.SQLException, java.lang.NumberFormatException,ComandException,java.text.ParseException,
            IOException,ClassNotFoundException{
        if (cmd.equals("model")){view.printSomeInfo(model().toString());}
        else if (cmd.equals("exit")){exit();}
///////////////////////////////////////////////////////////////////////////////////////list-block
        else if (cmd.equals("flightlist")){
            ArrayList <Flight> list =flightList();
            for (Flight list1 : list) {
                view.printFlight(list1, model.takeRoute(list1.getRoute()), model.takePlane(list1.getPlane()));
            }
            if(list.isEmpty())view.printSomeInfo("Список пуст");
        }
        else if (cmd.equals("planelist")){
            ArrayList <Plane> list =planeList();
            for (Plane list1 : list) {
                view.printPlane(list1);
            }
            if(list.isEmpty())view.printSomeInfo("Список пуст");
        }
        else if (cmd.equals("routelist")){
            ArrayList <Route> list =routeList();
            for (Route list1 : list) {
                view.printRoute(list1);
            }
            if(list.isEmpty())view.printSomeInfo("Список пуст");
        }
        else if (cmd.equals("portlist")){
            ArrayList <Airport> list =portList();
            for (Airport list1 : list) {
                view.printAirport(list1);
            }
            if(list.isEmpty())view.printSomeInfo("Список пуст");
        }
///////////////////////////////////////////////////////////////////////////////////////add-block
        else if (cmd.equals("addplane")){

            Plane plane=new Aircraft(Integer.parseInt(arguments[0]), arguments[1]);
            addPlane(plane);
            view.printSomeInfo("Самолет добавлен");
        }
        else if (cmd.equals("addport")){
            Airport port=new InternationalAirport(Integer.parseInt(arguments[0]),arguments[1],arguments[2]);
            addPort(port);
            view.printSomeInfo("Аэропорт добавлен");
        }
        else if (cmd.equals("addroute")){
            Route route=new RegularRoute(Integer.parseInt(arguments[0]),0,0,0);
            addRoute(route);
            view.printSomeInfo("Маршрут добавлен");
        }
        else if (cmd.equals("addflight")){

            Flight flight= new ReguarFlight((Integer.parseInt(arguments[0])),
                    (Integer.parseInt(arguments[1])),
                    (Integer.parseInt(arguments[2])),
                    formatter.parse(arguments[3]),
                    formatter.parse(arguments[4]));
            addFlight(flight);
            view.printSomeInfo("Рейс добавлен");
        }
///////////////////////////////////////////////////////////////////////////////////////set-block
        else if (cmd.equals("setplane")){

            Plane plane=model.takePlane(Integer.parseInt(arguments[0]));
            plane.setNumber(arguments[1]);
            if(arguments.length>2)
                plane.setName(arguments[2]);
            if(arguments.length>3)
                plane.setPassengerSeatsCount(Integer.parseInt(arguments[3]));
            if(arguments.length>4)
                plane.setFuelConsumption(Double.parseDouble(arguments[4]));
            model.setPlane(plane);
            view.printSomeInfo("Самолет модифицирован:");
            view.printPlane(model.takePlane(Integer.parseInt(arguments[0])));
        }
        else if (cmd.equals("setport")){
            Airport port=model.takeAirport(Integer.parseInt(arguments[0]));
            port.setName(arguments[1]);
            port.setLocation(arguments[2]);
            model.setAirport(port);
            view.printSomeInfo("Аэропорт модифицирован:");
            view.printAirport(model.takeAirport(Integer.parseInt(arguments[0])));
        }
        else if (cmd.equals("setroute")){
            Route route=model.takeRoute(Integer.parseInt(arguments[0]));
            route.setTakeOffPort(Integer.parseInt(arguments[1]));
            route.setLandingPort(Integer.parseInt(arguments[2]));
            route.setDistance(Double.parseDouble(arguments[3]));
            model.setRoute(route);
            view.printSomeInfo("Маршрут модифицирован:");
            view.printRoute(model.takeRoute(Integer.parseInt(arguments[0])));
        }
        else if (cmd.equals("setflight")){
            Flight flight=model.takeFlight(Integer.parseInt(arguments[0]));
            flight.setPlane(model.takePlane(Integer.parseInt(arguments[1])));
            flight.setRoute(model.takeRoute(Integer.parseInt(arguments[2])));
            flight.setTakeOffTimeShedule(formatter.parse(arguments[3]));
            flight.setLandingTimeShedule(formatter.parse(arguments[4]));
            model.setFlight(flight);
            view.printSomeInfo("Рейс модифицирован:");

            view.printFlight(flight, model.takeRoute(flight.getRoute()), model.takePlane(flight.getPlane()));
        }
///////////////////////////////////////////////////////////////////////////////////////get-block
        else if (cmd.equals("getplane")){
            view.printPlane(getPlane(Integer.parseInt(arguments[0])));
        }
        else if (cmd.equals("getfplane")){
            ArrayList<Plane> list=getFPlane( arguments[0], arguments[1],
                    arguments[2], arguments[3], arguments[4]);
            if(list.isEmpty()){
                view.printSomeInfo("Список пуст");
            }
            else{
                for (Plane list1 : list) {
                    view.printPlane(list1);
                }
            }
        }
        else if (cmd.equals("getport")){
            view.printAirport(getAirport(Integer.parseInt(arguments[0])));
        }
        else if (cmd.equals("getfport")){
            ArrayList<Airport> list=model.getAirports(arguments[0],
                    arguments[1],arguments[2]);
            if(list.isEmpty()){
                view.printSomeInfo("Список пуст");
            }
            else{
                for (Airport list1 : list) {
                    view.printAirport(list1);
                }
            }
        }
        else if (cmd.equals("getroute")){
            view.printRoute(getRoute(Integer.parseInt(arguments[0])));
        }
        else if (cmd.equals("getfroute")){
            ArrayList<Route> list=model.getRoutes(arguments[0],
                    arguments[1],arguments[2],arguments[3]);
            if(list.isEmpty()){
                view.printSomeInfo("Список пуст");
            }
            else{
                for (Route list1 : list) {
                    view.printRoute(list1);
                }
            }
        }
        else if (cmd.equals("getflight")){
            Flight flight = model.takeFlight(Integer.parseInt(arguments[0]));
            view.printFlight(flight, model.takeRoute(flight.getRoute()), model.takePlane(flight.getPlane()));
        }
        else if (cmd.equals("getfflight")){
            ArrayList<Flight> list= getFFlight( arguments[0],
                    arguments[2], arguments[1], arguments[3], arguments[4]);
            if(list.isEmpty()){
                view.printSomeInfo("Список пуст");
            }
            else{
                for (Flight list1 : list) {
                    view.printFlight(list1, model.takeRoute(list1.getRoute()), model.takePlane(list1.getPlane()));
                }
            }
        }
////////////////////////////////////////////////////////////////////////////////////////// del-block
        else if(cmd.equals("delplane")){
            int id = Integer.parseInt(arguments[0]);
            if(id!=0) {
                delPlane(Integer.parseInt(arguments[0]));
                view.printSomeInfo("Самолет удален");
            }
            else view.printSomeInfo("Не стоит удалять последний элемент.");
        }
        else if(cmd.equals("delport")){
            int id = Integer.parseInt(arguments[0]);
            if(id!=0) {
                delPort(Integer.parseInt(arguments[0]));
                view.printSomeInfo("Самолет удален");
            }
            else view.printSomeInfo("Не стоит удалять последний элемент.");
        }
        else if(cmd.equals("delroute")){
            int id = Integer.parseInt(arguments[0]);
            if(id!=0) {
                delRoute(Integer.parseInt(arguments[0]));
                view.printSomeInfo("Самолет удален");
            }
            else view.printSomeInfo("Не стоит удалять последний элемент.");
        }
        else if(cmd.equals("delflight")){
            int id = Integer.parseInt(arguments[0]);
            if(id!=0) {
                delFlight(Integer.parseInt(arguments[0]));
                view.printSomeInfo("Самолет удален");
            }
            else view.printSomeInfo("Не стоит удалять последний элемент.");
        }
        else if(cmd.equals("delall")){
            delAll();
            view.printSomeInfo("База данных очищена");
        }

///////////////////////////////////////////////////////////////////////////////////////
        else {
            throw  new ComandException("Комманда не поддерживается в этой версии: " + cmd);
        }

    }
    /**
     * launch() запускает цикл ввода команд в программу. команды зависимы. 
     * параметры команд регистрозависимы,разделяются пробелом
     * <p> Hазвание Параметры(через пробел) :: Описание :: Пример
     * @param commandList - список комманд на выполнение. после выполнения комманд из списка комманды выполняются с консоли
     */
    public void launch(String[] commandList)throws IOException, ClassNotFoundException{
        this.launch(commandList,false);
    }
    /**
     * launch() запускает цикл ввода команд в программу. команды зависимы. 
     * параметры команд регистрозависимы,разделяются пробелом
     * <p> Hазвание Параметры(через пробел) :: Описание :: Пример
     * @param commandList - список комманд на выполнение. после выполнения комманд из списка комманды выполняются с консоли
     * @param stopOnComplete - если true, то по завершении выполнения списка коммманд из commandList, приложение закрывается
     */
    public void launch(String[] commandList,boolean stopOnComplete) throws IOException, ClassNotFoundException{
        
        
        isLaunched=true;
        while(isLaunched){//бесконечный цикл ввода
          try{
            
            if(commandList.length!=0){//выбор источнтка комманд
            CmdParser.parseInput(commandList[0]);
                String[] temp=new String[commandList.length-1];
                for(int i=1;i<commandList.length;i++){
                    temp[i-1]=commandList[i];
                }
                commandList=temp;
            }
            else {
                if(stopOnComplete){//если stopOnComplete, выходим из цикла ввода
                    isLaunched=false;
                    break;
                }
                String input=null;
                   input=view.giveInput();
                if(input!=null)CmdParser.parseInput(input);
                else continue;
            }
            System.err.println("execute "+ CmdParser.getCommand());
              parse(CmdParser.getCommand(),CmdParser.getArguments());
        }
        catch (AirportNotFoundException | FlightNotFoundException | PlaneNotFoundException | 
                RouteNotFoundException | CommandNotFoundException  | InvalidArgumentsException |
                java.sql.SQLException | java.lang.NumberFormatException e){
            view.printSomeInfo(e.toString());
        }
        catch (ComandException e){
            view.printSomeInfo(e.toString());
        }
        catch (java.text.ParseException e){
            view.printSomeInfo("Wrong Data Format. Should be dd.MM.yy-kk:mm");
        }
            view.flush();
        }
    }
    public Model model() {
        return model;
    }
     public  void exit() throws SQLException{
         isLaunched=false;
         model.close();
     }
    public ArrayList<Route> routeList() throws  SQLException,ParseException,
            IOException,ClassNotFoundException{
    return model.getRoutes();
    }
    public ArrayList<Airport>   portList()throws  SQLException,ParseException,
            IOException,ClassNotFoundException{
        return model.getAirports();
    }
    public ArrayList<Plane> planeList()throws  SQLException,ParseException,
            IOException,ClassNotFoundException{
        return model.getPlanes();
    }
    public ArrayList<Flight>   flightList()throws  SQLException,ParseException,
    IOException,ClassNotFoundException{
        return model.getFlights();
    }
    public void addPlane(Plane plane)throws SQLException,IOException
            ,ClassNotFoundException{
        model.addPlane(plane);
    }
    public Plane getPlane(int id)throws SQLException,IOException
            ,ClassNotFoundException{
        return(model.takePlane(id));
    }
    public Route getRoute(int id)throws SQLException,IOException
            ,ClassNotFoundException {
        return(model.takeRoute(id));
    }
    public Airport getAirport(int id)throws SQLException,IOException
            ,ClassNotFoundException {
        return(model.takeAirport(id));
    }
    public  ArrayList<Flight> getFFlight(String id,String routeId,String planeId,
                            String takeOffTime,String landingTime)
            throws SQLException,IOException
            ,ClassNotFoundException,ParseException{
        return model.getFlights(id, routeId, planeId,takeOffTime,landingTime);

    }
    public  ArrayList<Route> getFRoute(String id,String takeOffId,String landId,
                                       String distance)
            throws SQLException,IOException
            ,ClassNotFoundException{
        return model.getRoutes(id, takeOffId, landId, distance);

    }
    public  ArrayList<Airport> getFPort(String id,String name,String location)
            throws SQLException,IOException
            ,ClassNotFoundException{
        return model.getAirports(id, name, location);

    }
    public  ArrayList<Plane> getFPlane(String id,String name,String number,
                                       String passengersSeats,String fuelCons)
            throws SQLException,IOException
            ,ClassNotFoundException{
        return model.getPlanes(id,name,number, passengersSeats, fuelCons);

    }
    public void addPort(Airport port)throws SQLException,IOException
            ,ClassNotFoundException{
        model.addAirport(port);
    }
    public void delPlane(int id)throws SQLException,IOException
            ,ClassNotFoundException{
        model.deletePlane(id);
    }
    public void delPort(int id)throws SQLException,IOException
            ,ClassNotFoundException{
        model.deleteAirport(id);
    }
    public void delFlight(int id)throws SQLException,IOException
            ,ClassNotFoundException{
        model.deleteFlight(id);
    }
    public void addRoute(Route route)throws SQLException,IOException
            ,ClassNotFoundException{
        model.addRoute(route);
    }
    public void addFlight(Flight flight)throws SQLException,IOException
            ,ClassNotFoundException{
        model.addFlight(flight);
    }
    public void delRoute(int id)throws SQLException,IOException
            ,ClassNotFoundException{
        model.deleteRoute(id);
    }
    public void delAll()throws SQLException,IOException
            ,ClassNotFoundException{
        model.delAll();
    }

}
