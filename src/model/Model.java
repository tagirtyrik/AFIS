package model;
//import Xml.OperationWithXml1;
import exception.AirportNotFoundException;
import exception.FlightNotFoundException;
import exception.PlaneNotFoundException;
import exception.RouteNotFoundException;
import exception.ComandException;
import java.io.IOException;
import java.util.ArrayList;
import xml.OperationWithXml;
import db.DataAccessObject;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * модель управляет источником данных, поддерживает работоспособность контроллера
 * @author GeneraL
 */
public class Model {
    private static boolean databaseIsOn=true;//переменная, отвечающая за использование базы данных либо XML-файла
    public Model(){
    }
    public void close()throws SQLException{
        if(databaseIsOn) DataAccessObject.closeConnection();
        
    }
    /**
     * возвращает весь список рейсов
     * @return список рейсов
     */
    public ArrayList getFlights()throws SQLException,java.text.ParseException,IOException, ClassNotFoundException{
        if(databaseIsOn)return DataAccessObject.getFlights();
        else return OperationWithXml.getFlights();
    }
    public ArrayList getFlights(boolean useOr,String id,String routeId,String planeId,
            String takeOffTime,String landingTime)throws SQLException,ComandException,java.text.ParseException{
        if(databaseIsOn)return DataAccessObject.getFlights(useOr,id,routeId,planeId,
                takeOffTime,landingTime);
        else throw new ComandException("В режиме XML команда не поддерживается");
    }
    /**
     * возвращает весь список маршрутов
     * @return список маршрутов
     */
    public ArrayList getRoutes()throws SQLException,IOException, ClassNotFoundException{
        if(databaseIsOn)return DataAccessObject.getRoutes();
        else return OperationWithXml.getRoutes();
    }
    public ArrayList getRoutes(boolean useOr,String id,String takeOffId,String LandId,
            String distanse)throws SQLException,ComandException{
        if(databaseIsOn)return DataAccessObject.getRoutes(useOr, id, takeOffId, LandId,distanse);
        else throw new ComandException("В режиме XML команда не поддерживается");
    }
    /**
     * возвращает весь список аэропортов
     * @return список аэропортов
     */
    public ArrayList getAirports()throws SQLException,IOException, ClassNotFoundException{
        if(databaseIsOn)return DataAccessObject.getAirports();
        else return OperationWithXml.getAirports();
    }
    public ArrayList getAirports(boolean useOr,String id,
            String name,String location)throws SQLException,ComandException{
        if(databaseIsOn)return DataAccessObject.getAirports(useOr, id,
             name, location);
        else throw new ComandException("В режиме XML команда не поддерживается");
    }
    /**
     * возвращает весь список самолетов
     * @return список самолетов
     */
    public ArrayList getPlanes()throws SQLException,IOException, ClassNotFoundException{
        if(databaseIsOn)return DataAccessObject.getPlanes();
        else return  OperationWithXml.getPlanes();
    }
    public ArrayList getPlanes(boolean useOr,String id,String name,String number,
        String passengerSeatsCount,String fuelCons)throws SQLException,ComandException{
        if(databaseIsOn)return DataAccessObject.getPlane(useOr, id, name, number, passengerSeatsCount, fuelCons);
        else throw new ComandException("В режиме XML команда не поддерживается");
    }
    /**
     * выбор рейса по его id
     * @param id идентификатор элемента в коллекции
     * @return Рейс
     */
    public Flight takeFlight(int id)throws SQLException,java.text.ParseException{
        if(databaseIsOn)return DataAccessObject.getFlight(id);
        else return OperationWithXml.getFlight(id);
    }
    /**
     * выбор маршрута по его id
     * @param id идентификатор элемента в коллекции
     * @return маршрут
     */
    public Route takeRoute(int id)throws SQLException{
        if(databaseIsOn)return DataAccessObject.getRoute(id);
        else return OperationWithXml.getRoute(id);
    }
    /**
     * выбор аэропорта по его id
     * @param id идентификатор элемента в коллекции
     * @return аэропорт
     */
    public Airport takeAirport(int id)throws SQLException{
        if(databaseIsOn)return DataAccessObject.getAirport(id);
        else return OperationWithXml.getAirport(id);
    }
    /**
     * выбор самолета по его id
     * @param id идентификатор элемента в коллекции
     * @return самолет
     */
    public Plane takePlane(int id)throws SQLException,IOException, ClassNotFoundException{
        if(databaseIsOn) return DataAccessObject.getPlane(id);
        else return OperationWithXml.getPlane(id);
    }
    public boolean setPlane(Plane plane)throws SQLException,IOException, ClassNotFoundException{
        if(databaseIsOn)    return DataAccessObject.setPlane(plane);
        else return OperationWithXml.setPlane(plane);

    }
    public boolean setAirport(Airport airport)throws SQLException,IOException, ClassNotFoundException{
        if(databaseIsOn)return DataAccessObject.setAirport(airport);
        else return OperationWithXml.setAirport(airport);
    }
     public boolean setRoute(Route route)throws SQLException,IOException, ClassNotFoundException{
        if(databaseIsOn)return DataAccessObject.setRoute(route);
        else return OperationWithXml.setRoute(route);
    } 
     public boolean setFlight(Flight flight)throws SQLException,IOException, ClassNotFoundException{
         if(databaseIsOn)return DataAccessObject.setFlight(flight);
        else return OperationWithXml.setFlight(flight);
     }
    /**
     * добавляет самолет в список, возвращает его номер в списке
     * (если существует - заменяет кго)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param plane добавлекмый самолет
     * @return индекс самолета в коллекции после добавления
     */
    public int addPlane(Plane plane)throws SQLException,IOException, ClassNotFoundException{
        if(databaseIsOn)return DataAccessObject.addPlane(plane);
        else return OperationWithXml.addPlane(plane);
    }
    /**
     * добавляет аэропорт в список, возвращает его номер в списке
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param airport добавляемый аэропорт
     * @return индекс аэропорта в коллекции после добавления
     */
    public int addAirport(Airport airport)throws SQLException,IOException, ClassNotFoundException{
        if(databaseIsOn)return DataAccessObject.addAirport(airport);
        else return OperationWithXml.addAirport(airport);
    }
    /**
     * добавляет маршрут в список, возвращает его номер в списке
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param route добавляемый маршрут
     * @return индекс маршрута в коллекции после добавления
     */
     public int addRoute(Route route)throws SQLException,IOException, ClassNotFoundException{
        if(databaseIsOn)return DataAccessObject.addRoute(route);
        else return OperationWithXml.addRoute(route);
    } 
     /**
     * добавляет рейс в список, возвращает его номер в списке
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param flight добавляемый рейс
     * @return индекс рейса в коллекции после добавления
     */
     public int addFlight(Flight flight)throws SQLException,IOException, ClassNotFoundException{
        if(databaseIsOn) return DataAccessObject.addFlight(flight);
        else return OperationWithXml.addFlight(flight);
     }
      /**
      * удаляет рейс
      * @param id - идентификатор рейса
      */
     public void deleteFlight(int id)throws SQLException,IOException, ClassNotFoundException{
         if(databaseIsOn)DataAccessObject.deleteFlight(id);
         else OperationWithXml.deleteFlight(id);
     }
     /**
      * удаляет аэропорт
      * @param id - идентификатор аэропорта
      */
     public void deleteAirport(int id)throws SQLException,IOException, ClassNotFoundException{
         if(databaseIsOn)DataAccessObject.deleteAirport(id);
         else OperationWithXml.deleteAirport(id);
     }
     /**
      * удаляет маршрут
      * @param id - идентификатор маршрута
      */
     public void deleteRoute(int id)throws SQLException,IOException, ClassNotFoundException{
         if(databaseIsOn)DataAccessObject.deleteRoute(id);
         else OperationWithXml.deleteRoute(id);
     }
     /**
      * удаляет самолет
      * @param id - идентификатор самолета
      */
     public void deletePlane(int id)throws SQLException,IOException, ClassNotFoundException{
         if(databaseIsOn)DataAccessObject.deletePlane(id);
         else OperationWithXml.deletePlane(id);
     }
    public int flightCount()throws SQLException{
        if(databaseIsOn) return DataAccessObject.flightCount();
        else return OperationWithXml.flightCount();
    }
    public int planesCount()throws SQLException{
        if(databaseIsOn) return DataAccessObject.planeCount();
        else return OperationWithXml.planeCount();
    }    
    public int routesCount()throws SQLException{
        if(databaseIsOn)return DataAccessObject.routeCount();
        else return OperationWithXml.routeCount();
    }  
    public int airportsCount()throws SQLException{
        if(databaseIsOn)return DataAccessObject.airportCount();
        else return OperationWithXml.airportCount();
    } 
    public void loadData() throws IOException, ClassNotFoundException{
        if(databaseIsOn){
            try{
            DataAccessObject.initConnection();
            }catch(ClassNotFoundException | SQLException e){
                //Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
                System.err.println(e.toString());
                System.err.println("unable to connect database. using XML");
                OperationWithXml.initData();
                databaseIsOn=false;
            }
        }else{
                   OperationWithXml.initData();
        }
    }
    public void delAll() throws IOException, ClassNotFoundException,SQLException{
        if(databaseIsOn){
            DataAccessObject.dropData();
        }else OperationWithXml.delAll();
    }
}
