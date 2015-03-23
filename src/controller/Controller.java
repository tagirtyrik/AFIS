package controller;
import exception.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class Controller {
    private final Model model;
    private final View view;
    private boolean isLaunched=false;
    private final DateFormat formatter =new SimpleDateFormat("dd.MM.yy-kk:mm_z");
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

     "addplane номер :: добавляет новый самолет",
     "getplane id :: выводит самолет по id",
     "getfplane id name number passengersSeats fuelCons useOr:: выводит самолет по заданным параметрам, useOr должен быть 0 или 1 ::"
            + "getfplane null Boeing_747SP SP-500 null null 0",
     "setplane id номер :: изменяет самолет по id",
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

    
    public Controller(Model model,View view) throws IOException, ClassNotFoundException{
        CmdParser.setHelp(help);
        model.loadData();
        this.model=model;
        this.view=view;
        
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
            if(CmdParser.getCommand().equals("hello")){
                view.printSomeInfo("Этот говорит \"Привет!!!\"");
            }  
            else if (CmdParser.getCommand().equals("model")){
                view.printSomeInfo(model.toString());
            }
            else if (CmdParser.getCommand().equals("exit")){
                view.printSomeInfo("До свидания!");
                isLaunched=false;
                model.close();
            }
            else if (CmdParser.getCommand().equals("help")){
                if(CmdParser.getArguments().length>0)
                    view.printSomeInfo(CmdParser.getHelp(CmdParser.getArgument(0)));
                else view.printSomeInfo(CmdParser.getHelp());
            }
///////////////////////////////////////////////////////////////////////////////////////list-block
            else if (CmdParser.getCommand().equals("flightlist")){
                ArrayList <Flight> list =model.getFlights();
                for (Flight list1 : list) {
                    view.printFlight(list1, model.takeRoute(list1.getRoute()), model.takePlane(list1.getPlane()));
                }
                if(list.isEmpty())view.printSomeInfo("Список пуст");
            }
            else if (CmdParser.getCommand().equals("planelist")){
                ArrayList <Plane> list =model.getPlanes();
                for (Plane list1 : list) {
                    view.printPlane(list1);
                }
                if(list.isEmpty())view.printSomeInfo("Список пуст");
            }
            else if (CmdParser.getCommand().equals("routelist")){
                ArrayList <Route> list =model.getRoutes();
                for (Route list1 : list) {
                    view.printRoute(list1);
                }
                if(list.isEmpty())view.printSomeInfo("Список пуст");
            }
            else if (CmdParser.getCommand().equals("portlist")){
                ArrayList <Airport> list =model.getAirports();
                for (Airport list1 : list) {
                    view.printAirport(list1);
                }
                if(list.isEmpty())view.printSomeInfo("Список пуст");
            }
///////////////////////////////////////////////////////////////////////////////////////add-block
            else if (CmdParser.getCommand().equals("addplane")){
                //int id=this.model.nextPlaneId();
                Plane plane=new Boeing747SP(-1,CmdParser.getArgument(0));
                int index=model.addPlane(plane);
                view.printSomeInfo("Самолет добавлен");
               //view.printPlane(model.takePlane(id));
               //model.saveData();
            }
            else if (CmdParser.getCommand().equals("addport")){
                //int id=this.model.nextAirportId();
                Airport port=new InternationalAirport(-1,CmdParser.getArgument(0),CmdParser.getArgument(1));
                int index=model.addAirport(port);
                view.printSomeInfo("Аэропорт добавлен");
               //view.printAirport(model.takeAirport(id));
               //model.saveData();
            }
            else if (CmdParser.getCommand().equals("addroute")){
                //int id=this.model.nextRouteId();
                Route route=new RegularRoute(-1,
                        Integer.parseInt(CmdParser.getArgument(0)),
                        Integer.parseInt(CmdParser.getArgument(1)),
                        Double.parseDouble(CmdParser.getArgument(2)));
                int index=model.addRoute(route);
                view.printSomeInfo("Маршрут добавлен");
               //model.saveData();
            }
            else if (CmdParser.getCommand().equals("addflight")){
               // int id=this.model.nextFlightId();
                Flight flight= new ReguarFlight(-1,
                        (Integer.parseInt(CmdParser.getArgument(0))),
                        (Integer.parseInt(CmdParser.getArgument(1))),
                        formatter.parse(CmdParser.getArgument(2)+"_GMT-0:00"),
                        formatter.parse(CmdParser.getArgument(3)+"_GMT-0:00"));
                int index=model.addFlight(flight);
                view.printSomeInfo("Рейс добавлен");
               //view.printFlight(model.takeFlight(id), model.takeRoute(flight.getRoute()), model.takePlane(flight.getPlane()));
               //model.saveData();
            }
///////////////////////////////////////////////////////////////////////////////////////set-block
            else if (CmdParser.getCommand().equals("setplane")){
                
                Plane plane=model.takePlane(Integer.parseInt(CmdParser.getArgument(0)));
                plane.setNumber(CmdParser.getArgument(1));
                model.setPlane(plane);
                view.printSomeInfo("Самолет модифицирован:");
               view.printPlane(model.takePlane(Integer.parseInt(CmdParser.getArgument(0))));
               //model.saveData();
            }
            else if (CmdParser.getCommand().equals("setport")){
                Airport port=model.takeAirport(Integer.parseInt(CmdParser.getArgument(0)));
                port.setName(CmdParser.getArgument(1));
                port.setLocation(CmdParser.getArgument(2));
                model.setAirport(port);
                view.printSomeInfo("Аэропорт модифицирован:");
               view.printAirport(model.takeAirport(Integer.parseInt(CmdParser.getArgument(0))));
               //model.saveData();
            }
            else if (CmdParser.getCommand().equals("setroute")){
                Route route=model.takeRoute(Integer.parseInt(CmdParser.getArgument(0)));
                route.setTakeOffPort(Integer.parseInt(CmdParser.getArgument(1)));
                route.setLandingPort(Integer.parseInt(CmdParser.getArgument(2)));
                route.setDistance(Double.parseDouble(CmdParser.getArgument(3)));
                model.setRoute(route);
                view.printSomeInfo("Маршрут модифицирован:");
               view.printRoute(model.takeRoute(Integer.parseInt(CmdParser.getArgument(0))));
               //model.saveData();
            }
            else if (CmdParser.getCommand().equals("setflight")){
                Flight flight=model.takeFlight(Integer.parseInt(CmdParser.getArgument(0)));
                flight.setPlane(model.takePlane(Integer.parseInt(CmdParser.getArgument(1))));
                flight.setRoute(model.takeRoute(Integer.parseInt(CmdParser.getArgument(2))));
                flight.setTakeOffTimeShedule(formatter.parse(CmdParser.getArgument(3)+"_GMT-0:00"));
                flight.setLandingTimeShedule(formatter.parse(CmdParser.getArgument(4)+"_GMT-0:00"));
                model.setFlight(flight);
                view.printSomeInfo("Рейс модифицирован:");
                
               view.printFlight(flight, model.takeRoute(flight.getRoute()), model.takePlane(flight.getPlane()));
               //model.saveData();
            }           
///////////////////////////////////////////////////////////////////////////////////////get-block
            else if (CmdParser.getCommand().equals("getplane")){
               view.printPlane(model.takePlane(Integer.parseInt(CmdParser.getArgument(0))));
            }
            else if (CmdParser.getCommand().equals("getfplane")){
                boolean useOr;
                useOr = CmdParser.getArgument(5).equals("1");
               ArrayList<Plane> list=model.getPlanes(useOr
                       , CmdParser.getArgument(0), CmdParser.getArgument(1),
                       CmdParser.getArgument(2), CmdParser.getArgument(3), CmdParser.getArgument(4));
               if(list.isEmpty()){
                   view.printSomeInfo("Список пуст");
               }
               else{
                    for (Plane list1 : list) {
                        view.printPlane(list1);
                    }
               }
            }
            else if (CmdParser.getCommand().equals("getport")){
                view.printAirport(model.takeAirport(Integer.parseInt(CmdParser.getArgument(0))));
            }
            else if (CmdParser.getCommand().equals("getfport")){
                boolean useOr;
                useOr = CmdParser.getArgument(3).equals("1");
               ArrayList<Airport> list=model.getAirports(useOr, CmdParser.getArgument(0),
                       CmdParser.getArgument(1),CmdParser.getArgument(2));
               if(list.isEmpty()){
                   view.printSomeInfo("Список пуст");
               }
               else{
                    for (Airport list1 : list) {
                        view.printAirport(list1);
                    }
               }
            }
            else if (CmdParser.getCommand().equals("getroute")){
                view.printRoute(model.takeRoute(Integer.parseInt(CmdParser.getArgument(0))));
            }
            else if (CmdParser.getCommand().equals("getfroute")){
                boolean useOr;
                useOr = CmdParser.getArgument(4).equals("1");
               ArrayList<Route> list=model.getRoutes(useOr, CmdParser.getArgument(0),
                       CmdParser.getArgument(1),CmdParser.getArgument(2),CmdParser.getArgument(3));
               if(list.isEmpty()){
                   view.printSomeInfo("Список пуст");
               }
               else{
                    for (Route list1 : list) {
                        view.printRoute(list1);
                    }
               }
            }
            else if (CmdParser.getCommand().equals("getflight")){
                Flight flight = model.takeFlight(Integer.parseInt(CmdParser.getArgument(0)));
                view.printFlight(flight, model.takeRoute(flight.getRoute()), model.takePlane(flight.getPlane()));
            }
            else if (CmdParser.getCommand().equals("getfflight")){
                boolean useOr;
                useOr = CmdParser.getArgument(5).equals("1");
               ArrayList<Flight> list=model.getFlights(useOr, CmdParser.getArgument(0),
                       CmdParser.getArgument(1),CmdParser.getArgument(2),CmdParser.getArgument(3),CmdParser.getArgument(4));
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
            else if (CmdParser.getCommand().equals("flightcount")){
                view.printFlightCount(model.flightCount());
            }
            else if (CmdParser.getCommand().equals("routecount")){
                view.printRouteCount(model.routesCount());
            }
            else if (CmdParser.getCommand().equals("planecount")){
                view.printPlaneCount(model.planesCount());
            }
            else if (CmdParser.getCommand().equals("portcount")){
                view.printAirportCount(model.airportsCount());
            }
////////////////////////////////////////////////////////////////////////////////////////// del-block
            else if(CmdParser.getCommand().equals("delplane")){
                 model.deletePlane(Integer.parseInt(CmdParser.getArgument(0)));
                 view.printSomeInfo("Самолет удален"); 
                 //model.saveData();
            }
            else if(CmdParser.getCommand().equals("delport")){
                model.deleteAirport(Integer.parseInt(CmdParser.getArgument(0)));
                view.printSomeInfo("Аэропорт удален");
                //model.saveData();
            }
            else if(CmdParser.getCommand().equals("delroute")){
                model.deleteRoute(Integer.parseInt(CmdParser.getArgument(0)));
                view.printSomeInfo("Маршрут удален"); 
                //model.saveData();
            }
            else if(CmdParser.getCommand().equals("delflight")){
                model.deleteFlight(Integer.parseInt(CmdParser.getArgument(0)));
                view.printSomeInfo("Рейс удален"); 
               // model.saveData();
            }
            else if(CmdParser.getCommand().equals("delall")){
                model.delAll();
                //model.saveData();
                view.printSomeInfo("База данных очищена"); 
            }
///////////////////////////////////////////////////////////////////////////////////////
            else view.printSomeInfo("Комманда не поддерживается в этой версии: "+ CmdParser.getCommand());  //ветка кода не должна быть достигнута
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
            view.flush();//завершает ввод комманды на этот момент должен быть вывод
        }
    }
   
}
