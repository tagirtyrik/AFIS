package view;
import java.util.Scanner;
import model.Airport;
import model.Flight;
import model.Plane;
import model.Route;
/**
 * @author GeneraL
 * View управляет вводом-выводом информации в программу, не раздкляет пользователей на работников и пользователей(пока)
 */
public class LocalView implements View{
    /**
     * распечатывает произвольное сообщение с контроллера. добавляет символ \n в конец сообщения
     * @param info  сообщение с контроллера
     */
    public void printSomeInfo(String info){
        System.out.print(info+"\n");
    } 
    /**
     * вводит данные пользователя
     * @return строка, введеная пользователем
     */
    public String giveInput(){
        Scanner input = new Scanner( System.in );
        input.useDelimiter("\n");
        return input.next();
    }
    /**
     * выводит информацию по азропорту:<li>Id <li>Тип <li>Имя <li>Местоположение 
     * @param airport аэропорт
     */
    public void printAirport(Airport airport){
        System.out.println("Id:"+airport.getId());
        System.out.println("Type:"+airport.toString());
        System.out.println("Name:"+airport.getName());
        System.out.println("Location:"+airport.getLocation());
    }
    /**
     * выводит информацию по самолету:<li>Id <li>Тип <li>Имя 
     * <li>Бортовой номер <li>Потребление топлива 
     * <li>число пассажирских мест
     * @param plane самолет
     */
    public void printPlane(Plane plane){
        System.out.println("Id:"+plane.getId());
        System.out.println("Type:"+plane.toString());
        System.out.println("Name:"+plane.getName());
        System.out.println("Number:"+plane.getNumber());
        System.out.println("Fuel Consumption:"+plane.getFuelConsumption());
        System.out.println("Passenger Seats:"+plane.getPassengerSeatsCount());
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
        System.out.println("Flight Id: "+flight.getId());
        System.out.println("Flight type: "+flight.toString());
        System.out.println("Plane Id: "+flight.getPlane());
        System.out.println("Route Id: "+flight.getRoute());
        System.out.println("Ticket price: "+flight.ticketPrice(route, plane));
        System.out.println("Takeoff time: "+flight.getTakeOffTimeShedule());
        System.out.println("Landing time: "+flight.getLandingTimeShedule());
        System.out.println("Time at flight: "+flight.timeAtFlight());
    }
    /**
     * выводит информацию по маршруту:<li>Id<li>Тип<li>Id порта отправления <li> Id порта прибытия
     * @param route маршрут
     */
        public void printRoute(Route route){
        System.out.println("Id: "+route.getId());
        System.out.println("Type: "+route.toString());
        System.out.println("- From Id: "+route.getTakeOffPort());
        System.out.println("- To Id: "+route.getLandingPort());
        System.out.println("Distance "+route.getDistance());
    }
        /**
         * печатает число рейсов в БД
         * @param flightCount число рейсов
         */
    public void printFlightCount(int flightCount){
        System.out.println("Number of flighs: "+flightCount);
    }
         /**
         * печатает число маршрутов в БД
         * @param routeCount число маршрутов
         */
    public void printRouteCount(int routeCount){
        System.out.println("Number of routs: "+routeCount);
    }
         /**
         * печатает число самолетов в БД
         * @param planeCount число самолетов
         */
    public void printPlaneCount(int planeCount){
        System.out.println("Number of planes: "+planeCount);
    }
            /**
         * печатает число аэропортов в БД
         * @param portCount число аэропортов
         */
    public void printAirportCount(int portCount){
        System.out.println("Number of airports: "+portCount);
    }
    public void flush(){
        
    }
}
