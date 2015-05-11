package controller;
import exception.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import model.Airport;
import model.Flight;
import model.Model;
import model.Plane;
import model.Route;
import model.aircraft.Boeing747SP;
import model.airport.InternationalAirport;
import model.flight.ReguarFlight;
import model.route.RegularRoute;
import view.View;
import xml.OperationWithXml;

import javax.servlet.AsyncEvent;

public class Controller {
    private Model model=null;
    private View view=null;
    private boolean isLaunched=false;
    public final DateFormat formatter =new SimpleDateFormat("dd.MM.yy-HH:mm");
    private final String[] help=new String[]{//справка
     "hello :: выводит приветствие",
     "model :: выводит toString()-представление модели",
     "exit :: выход из программы",
     "help команда :: помощь по команде",
     
     "routelist :: выводит все маршруты из БД",
     "portlist :: выводит все аэропорты из БД",
     "planelist :: выводит все самолеты из БД",
     "flightlist :: выводит все рейсы из БД",
     
     "flightcount :: выводит число рейсов в БД",
     "planecount :: выводит число самолетов в БД",
     "routecount :: выводит число маршрутов в БД",
     "portcount :: выводит число аэропортов в БД",

     "addplane номер имя passengersSeats fuelCons :: добавляет новый самолет",
     "getplane id :: выводит самолет по id",
     "getfplane id name number passengersSeats fuelCons useOr:: выводит самолет по заданным параметрам, useOr должен быть 0 или 1 ::"
            + "getfplane null Boeing_747SP SP-500 null null 0",
     "setplane id номер имя passengersSeats fuelCons:: изменяет самолет по id",
     "delplane id :: удаляет самолет по id",
     
     "addport название местоположение :: добавляет аэропорт",
     "getport id :: выводит порт по id",
     "getfport id name location useOr:: выводит аэропорт по заданным параметрам, useOr должен быть 0 или 1 ::"
            + "getfport null null Дододедово 0",
     "setport id название местоположение :: изменяет аэропорт по id",
     "delport id :: удаляет аэропорт по id",
      
     "addroute портId1 портId2 дистанция :: добавляет маршрут",
     "getroute id :: выводит маршрут по id ",
     "getfroute id портId1 портId2 дистанция useOr:: выводит маршрут по заданным параметрам, useOr должен быть 0 или 1 ::"
            + "getfroute null null null 500.0 0",
     "setroute id портId1 портId2 дистанция :: изменяет маршрут по id ",
     "delroute id :: удаляет маршрут по id",
      
     "addflight самолетId маршрутId время_взлета время_посадки :: добавляет полет,формат ввода времени- dd.MM.yy-kk:mm часовой пояс - GMT-0:00::"
             +"addflight 1 1 10.01.12-18:00 10.01.12-20:00",
     "getflight id :: выводит полет по id ",
     "getfflight id самолетId маршрутId время_взлета время_посадки useOr:: выводит полет по заданным параметрам, useOr должен быть 0 или 1 ::"
            + "getfflight null null null 10.01.12-18:00 null 0",
     "setflight id самолетId маршрутId время_взлета время_посадки :: изменяет маршрут по id,формат ввода времени- dd.MM.yy-kk:mm часовой пояс - GMT-0:00::"
             +"setflight 8 1 1 10.01.12-18:00 10.01.12-20:00",
     "delflight id :: удаляет маршрут по id ",
      
     "delall :: удаляет все ваши данные" 
    };

   public Controller(Model model)throws IOException,
           ClassNotFoundException{
       CmdParser.setHelp(help);
       model.loadData();
       this.model=model;
       this.view=null;
      // formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
   }
    public Controller(Model model,View view) throws IOException,
            ClassNotFoundException{
        CmdParser.setHelp(help);
        model.loadData();
        this.model=model;
        this.view=view;
       // formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        
    }

    /**
     * метод парсинга комманд принимает на вход команду и перечень аргументов
     * @param cmd
     * @param arguments
     * @throws AirportNotFoundException
     * @throws FlightNotFoundException
     * @throws PlaneNotFoundException
     * @throws RouteNotFoundException
     * @throws InvalidArgumentsException
     * @throws java.sql.SQLException
     * @throws java.lang.NumberFormatException
     * @throws ComandException
     * @throws java.text.ParseException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void parse(String cmd,String[] arguments)throws AirportNotFoundException, FlightNotFoundException,
            PlaneNotFoundException,  RouteNotFoundException, InvalidArgumentsException,
            java.sql.SQLException, java.lang.NumberFormatException,ComandException,java.text.ParseException,
            IOException,ClassNotFoundException{
        if(cmd.equals("hello")){view.printSomeInfo(hello());}
        else if (cmd.equals("model")){view.printSomeInfo(model().toString());}
        else if (cmd.equals("exit")){view.printSomeInfo(exit());}
        else if (cmd.equals("help")){
            if(arguments.length>0)
                view.printSomeInfo(help(arguments[0]));
            else view.printSomeInfo(help());
        }
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

            Plane plane=new Boeing747SP(-1,arguments[0]);
            if(arguments.length>1)
                plane.setName(arguments[1]);
            if(arguments.length>2)
                plane.setPassengerSeatsCount(Integer.parseInt(arguments[2]));
            if(arguments.length>3)
                plane.setFuelConsumption(Double.parseDouble(arguments[3]));
            addPlane(plane);
            view.printSomeInfo("Самолет добавлен");
        }
        else if (cmd.equals("addport")){
            Airport port=new InternationalAirport(-1,arguments[0],arguments[1]);
            addPort(port);
            view.printSomeInfo("Аэропорт добавлен");
        }
        else if (cmd.equals("addroute")){
            Route route=new RegularRoute(-1,
                    Integer.parseInt(arguments[0]),
                    Integer.parseInt(arguments[1]),
                    Double.parseDouble(arguments[2]));
            addRoute(route);
            view.printSomeInfo("Маршрут добавлен");
        }
        else if (cmd.equals("addflight")){

            Flight flight= new ReguarFlight(-1,
                    (Integer.parseInt(arguments[0])),
                    (Integer.parseInt(arguments[1])),
                    formatter.parse(arguments[2]),
                    formatter.parse(arguments[3]));
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

            //model.saveData();
        }
        else if (cmd.equals("setport")){
            Airport port=model.takeAirport(Integer.parseInt(arguments[0]));
            port.setName(arguments[1]);
            port.setLocation(arguments[2]);
            model.setAirport(port);
            view.printSomeInfo("Аэропорт модифицирован:");
            view.printAirport(model.takeAirport(Integer.parseInt(arguments[0])));
            //model.saveData();
        }
        else if (cmd.equals("setroute")){
            Route route=model.takeRoute(Integer.parseInt(arguments[0]));
            route.setTakeOffPort(Integer.parseInt(arguments[1]));
            route.setLandingPort(Integer.parseInt(arguments[2]));
            route.setDistance(Double.parseDouble(arguments[3]));
            model.setRoute(route);
            view.printSomeInfo("Маршрут модифицирован:");
            view.printRoute(model.takeRoute(Integer.parseInt(arguments[0])));
            //model.saveData();
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
            //model.saveData();
        }
///////////////////////////////////////////////////////////////////////////////////////get-block
        else if (cmd.equals("getplane")){
            view.printPlane(getPlane(Integer.parseInt(arguments[0])));
        }
        else if (cmd.equals("getfplane")){
            boolean useOr;
            useOr = arguments[5].equals("1");
            ArrayList<Plane> list=getFPlane( arguments[0], arguments[1],
                    arguments[2], arguments[3], arguments[4],useOr);
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
            boolean useOr;
            useOr = arguments[3].equals("1");
            ArrayList<Airport> list=model.getAirports(useOr, arguments[0],
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
            boolean useOr;
            useOr = arguments[4].equals("1");
            ArrayList<Route> list=model.getRoutes(useOr, arguments[0],
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
            boolean useOr;
            useOr = arguments[5].equals("1");
            //getfflight id самолетId маршрутId время_взлета время_посадки useOr
            ArrayList<Flight> list= getFFlight( arguments[0],
                    arguments[2], arguments[1], arguments[3], arguments[4],useOr);
            if(list.isEmpty()){
                view.printSomeInfo("Список пуст");
            }
            else{
                for (Flight list1 : list) {
                    view.printFlight(list1, model.takeRoute(list1.getRoute()), model.takePlane(list1.getPlane()));
                }
            }
        }
/////////////////////////////////////////////////////////////////////////////////////////count-block
        else if (cmd.equals("flightcount")){
            view.printFlightCount(model.flightCount());
        }
        else if (cmd.equals("routecount")){
            view.printRouteCount(model.routesCount());
        }
        else if (cmd.equals("planecount")){
            view.printPlaneCount(model.planesCount());
        }
        else if (cmd.equals("portcount")){
            view.printAirportCount(model.airportsCount());
        }
////////////////////////////////////////////////////////////////////////////////////////// del-block
        else if(cmd.equals("delplane")){
            delPlane(Integer.parseInt(arguments[0]));
            view.printSomeInfo("Самолет удален");
        }
        else if(cmd.equals("delport")){
            delPort(Integer.parseInt(arguments[0]));
            view.printSomeInfo("Аэропорт удален");
        }
        else if(cmd.equals("delroute")){
            delRoute(Integer.parseInt(arguments[0]));
            view.printSomeInfo("Маршрут удален");
        }
        else if(cmd.equals("delflight")){
            delFlight(Integer.parseInt(arguments[0]));
            view.printSomeInfo("Рейс удален");
        }
        else if(cmd.equals("delall")){
            delAll();
            view.printSomeInfo("База данных очищена");
        }
        else if(cmd.equals("saveXml"))
        {
            String xmlList;
            if (arguments[0].equals("planes"))
            {
                xmlList = xmlPlane();
            }
            else if (arguments[0].equals("ports"))
            {
                xmlList = xmlPort();
            }
            else if (arguments[0].equals("routes"))
            {
                xmlList = xmlRoute();
            }
            else if (arguments[0].equals("flights"))
            {
                xmlList = xmlFlight();
            }
            else
            {
                xmlList = "";
            }
            view.printSomeInfo(xmlList);
        }
        //////////////////////////////////////////////////////////////////////////// сохранение данных в xml
        else if(cmd.equals("saveXml"))
        {
            String xmlList;
            if (arguments[0].equals("planes"))
            {
                xmlList = xmlPlane();
            }
            else if (arguments[0].equals("ports"))
            {
                xmlList = xmlPort();
            }
            else if (arguments[0].equals("routes"))
            {
                xmlList = xmlRoute();
            }
            else if (arguments[0].equals("flights"))
            {
                xmlList = xmlFlight();
            }
            else
            {
                xmlList = "";
            }
            view.printSomeInfo(xmlList);
        }
        ////////////////////////////////////////////////////////////////////////////восстановление данных из xml
        else if (cmd.equals("recoveryplane"))
        {
            Plane plane = new Boeing747SP(Integer.parseInt(arguments[0]), arguments[1]);
            plane.setName(arguments[2]);
            plane.setPassengerSeatsCount(Integer.parseInt(arguments[3]));
            plane.setFuelConsumption(Double.parseDouble(arguments[4]));
            addPlaneManual(plane);
        }
        else if (cmd.equals("recoveryport"))
        {
            Airport port = new InternationalAirport(Integer.parseInt(arguments[0]), arguments[1], arguments[2]);
            addPortManual(port);
        }
        else if (cmd.equals("recoveryroute"))
        {
            Route route=new RegularRoute(Integer.parseInt(arguments[0]),
                    Integer.parseInt(arguments[1]),
                    Integer.parseInt(arguments[2]),
                    Double.parseDouble(arguments[3]));
            addRouteManual(route);
        }
        else if (cmd.equals("recoveryflight"))
        {
            Flight flight= new ReguarFlight(Integer.parseInt(arguments[0]),
                    Integer.parseInt(arguments[1]),
                    Integer.parseInt(arguments[2]),
                    formatter.parse(arguments[3]),
                    formatter.parse(arguments[4]));
            addFlightManual(flight);
        }

///////////////////////////////////////////////////////////////////////////////////////
        else view.printSomeInfo("Комманда не поддерживается в этой версии: "+ cmd);  //ветка кода не должна быть достигнута
    }
    /**
     * launch() запускает цикл ввода команд в программу. команды зависимы. 
     * параметры команд регистрозависимы,разделяются пробелом 
     * <p> Список поддерживаемых команд обязательно должен содержаться в поле help в формате
     * <p> Hазвание Параметры(через пробел) :: Описание :: Пример
     * @param commandList - список комманд на выполнение. после выполнения комманд из списка комманды выполняются с консоли
     */
    public void launch(String[] commandList)throws IOException, ClassNotFoundException{
        this.launch(commandList,false);
    }
    /**
     * launch() запускает цикл ввода команд в программу. команды зависимы. 
     * параметры команд регистрозависимы,разделяются пробелом 
     * <p> Список поддерживаемых команд обязательно должен содержаться в поле help в формате
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
    public String hello() {
        return "Hello!!!";
    }
    public Model model() {
        return model;
    }
     public  String exit() throws SQLException{
         isLaunched=false;
         model.close();
         return ("До свидания!");
     }
    public String help() {
        return CmdParser.getHelp();
    }
     public String help(String cmd) {
         return CmdParser.getHelp(cmd);
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
    public int flightCount()throws  SQLException,ParseException,
            IOException,ClassNotFoundException {
    return model.flightCount();
    }
    public int planeCount()throws  SQLException,ParseException,
            IOException,ClassNotFoundException {
            return model.planesCount();
    }
    public int routeCount()throws  SQLException,ParseException,
            IOException,ClassNotFoundException {
        return model.routesCount();
    }
    public int portCount() throws  SQLException,ParseException,
            IOException,ClassNotFoundException{
        return model.airportsCount();
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
                            String takeOffTime,String landingTime,boolean useOr)
            throws SQLException,IOException
            ,ClassNotFoundException,ParseException{
        return model.getFlights(useOr, id, routeId, planeId,takeOffTime,landingTime);

    }
    public  ArrayList<Route> getFRoute(String id,String takeOffId,String landId,
                                       String distance,boolean useOr)
            throws SQLException,IOException
            ,ClassNotFoundException{
        return model.getRoutes(useOr, id, takeOffId, landId, distance);

    }
    public  ArrayList<Airport> getFPort(String id,String name,String location,boolean useOr)
            throws SQLException,IOException
            ,ClassNotFoundException{
        return model.getAirports(useOr, id, name, location);

    }
    public  ArrayList<Plane> getFPlane(String id,String name,String number,
                                       String passengersSeats,String fuelCons,boolean useOr)
            throws SQLException,IOException
            ,ClassNotFoundException{
        return model.getPlanes(useOr, id,name,number, passengersSeats, fuelCons);

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
    public String xmlPlane() throws SQLException, IOException, ClassNotFoundException {
        return model.getXmlPlane();
    }
    public String xmlPort() throws SQLException, IOException, ClassNotFoundException {
        return model.getXmlPort();
    }
    public String xmlRoute() throws SQLException, IOException, ClassNotFoundException {
        return model.getXmlRoute();
    }
    public String xmlFlight() throws SQLException, IOException, ClassNotFoundException, ParseException {
        return model.getXmlFlight();
    }
    public void addPlaneManual(Plane plane) throws SQLException, IOException, ClassNotFoundException {
        model.addPlaneManual(plane);
    }
    public void addPortManual(Airport port) throws SQLException, IOException, ClassNotFoundException {
        model.addAirportManual(port);
    }
    public void addRouteManual(Route route) throws SQLException, IOException, ClassNotFoundException {
        model.addRouteManual(route);
    }
    public void addFlightManual(Flight flight) throws SQLException, IOException, ClassNotFoundException {
        model.addFlightManual(flight);
    }

}
