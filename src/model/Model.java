package model;
import exception.ComandException;
import java.io.IOException;
import java.util.*;
import db.DataAccessObject;
import java.sql.SQLException;

/**
 * модель управляет источником данных, поддерживает работоспособность контроллера
 * @author Ксю
 */
public class Model {
   // public Model(boolean useDataSourse){
     //   DataAccessObject.setUseDataSourse(useDataSourse);
   // }
    public Model(){
    }
    /**
     * возвращает весь список рейсов
     * @return список рейсов
     */
    public ArrayList getFlights()throws SQLException,java.text.ParseException,IOException, ClassNotFoundException{
        return DataAccessObject.getFlights();
    }
    public ArrayList getFlights(String id,String routeId,String planeId, String takeOffTime,String landingTime)throws SQLException,ComandException,java.text.ParseException{
        return DataAccessObject.getFlightsSearch(id,routeId, planeId, takeOffTime, landingTime);
    }
    /**
     * возвращает весь список маршрутов
     * @return список маршрутов
     */
    public ArrayList getRoutes()throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.getRoutes();
    }
    public ArrayList getRoutes(String id,String takeOffId,String LandId, String distanse)throws SQLException,ComandException{
        return DataAccessObject.getRoutesSearch(id, takeOffId, LandId, distanse);
    }
    /**
     * возвращает весь список аэропортов
     * @return список аэропортов
     */
    public ArrayList getAirports()throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.getAirports();
    }
    public ArrayList getAirports(String id, String name,String location)throws SQLException,ComandException{
        return DataAccessObject.getAirportsSearch(id, name, location);
    }
    /**
     * возвращает весь список самолетов
     * @return список самолетов
     */
    public ArrayList getPlanes()throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.getPlanes();
    }
    public ArrayList getPlanes(String id,String name,String number,
                               String passengerSeatsCount,String fuelCons) throws SQLException, ComandException, ClassNotFoundException {
        return DataAccessObject.getPlaneSearch(id, name, number, passengerSeatsCount, fuelCons);
    }
    /**
     * выбор рейса по его id
     * @param id идентификатор элемента в коллекции
     * @return Рейс
     */
    public Flight takeFlight(int id)throws SQLException,java.text.ParseException{
        return DataAccessObject.getFlight(id);
    }
    /**
     * выбор маршрута по его id
     * @param id идентификатор элемента в коллекции
     * @return маршрут
     */
    public Route takeRoute(int id)throws SQLException{
        return DataAccessObject.getRoute(id);
    }
    /**
     * выбор аэропорта по его id
     * @param id идентификатор элемента в коллекции
     * @return аэропорт
     */
    public Airport takeAirport(int id)throws SQLException{
        return DataAccessObject.getAirport(id);
    }
    /**
     * выбор самолета по его id
     * @param id идентификатор элемента в коллекции
     * @return самолет
     */
    public Plane takePlane(int id)throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.getPlane(id);
    }
    public void setPlane(Plane plane)throws SQLException,IOException, ClassNotFoundException{
        DataAccessObject.setPlane(plane);

    }
    public void setAirport(Airport airport)throws SQLException,IOException, ClassNotFoundException{
         DataAccessObject.setAirport(airport);
    }
     public void setRoute(Route route)throws SQLException,IOException, ClassNotFoundException{
          DataAccessObject.setRoute(route);
    } 
     public void setFlight(Flight flight)throws SQLException,IOException, ClassNotFoundException{
          DataAccessObject.setFlight(flight);
     }
    /**
     * добавляет самолет в список, возвращает его номер в списке
     * (если существует - заменяет кго)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param plane добавлекмый самолет
     * @return индекс самолета в коллекции после добавления
     */
    public void addPlane(Plane plane)throws SQLException,IOException, ClassNotFoundException{
        DataAccessObject.addPlane(plane);
    }
    /**
     * добавляет аэропорт в список, возвращает его номер в списке
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param airport добавляемый аэропорт
     * @return индекс аэропорта в коллекции после добавления
     */
    public void addAirport(Airport airport)throws SQLException,IOException, ClassNotFoundException{
         DataAccessObject.addAirport(airport);
    }
    /**
     * добавляет маршрут в список, возвращает его номер в списке
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param route добавляемый маршрут
     * @return индекс маршрута в коллекции после добавления
     */
     public void addRoute(Route route)throws SQLException,IOException, ClassNotFoundException{
         DataAccessObject.addRoute(route);
    } 
     /**
     * добавляет рейс в список, возвращает его номер в списке
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param flight добавляемый рейс
     * @return индекс рейса в коллекции после добавления
     */
     public void addFlight(Flight flight)throws SQLException,IOException, ClassNotFoundException{
          DataAccessObject.addFlight(flight);
     }

    /**
      * удаляет рейс
      * @param id - идентификатор рейса
      */
     public void deleteFlight(int id)throws SQLException,IOException, ClassNotFoundException{
         DataAccessObject.deleteFlight(id);
     }
     /**
      * удаляет аэропорт
      * @param id - идентификатор аэропорта
      */
     public void deleteAirport(int id)throws SQLException,IOException, ClassNotFoundException{
         DataAccessObject.deleteAirport(id);
     }
     /**
      * удаляет маршрут
      * @param id - идентификатор маршрута
      */
     public void deleteRoute(int id)throws SQLException,IOException, ClassNotFoundException{
         DataAccessObject.deleteRoute(id);
     }
     /**
      * удаляет самолет
      * @param id - идентификатор самолета
      */
     public void deletePlane(int id)throws SQLException,IOException, ClassNotFoundException{
         DataAccessObject.deletePlane(id);
     }
    public void loadData() throws IOException, ClassNotFoundException {
            try
            {
                DataAccessObject.initConnection();
            }
            catch(ClassNotFoundException | SQLException e)
            {
                System.err.println(e.toString());
                System.err.println("unable to connect database");
            }
    }
}
