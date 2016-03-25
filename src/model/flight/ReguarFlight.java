
package model.flight;
import java.io.Serializable;
import java.util.Date;
import model.Flight;
import model.Plane;
import model.Route;
/*
    рейс, совершаемый самолетом по маршруту
*/
public class ReguarFlight implements Flight,Serializable{
    
   // double fuelPrice=26.80;//стоимость литра авиационного керосина(возможно стоит сделать какой-нибудь глобальной константой?)
    
    int planeId;// абстрактный самолет, летящий по рейсу(какой угодно модели)
    int routeId;// абстрактный маршрут самолета
    
    Date takeOffTime;//время взлета самолета
    Date landingTime;//время посадки, если в Plane ввести поле averageSpeed, время посадки можно расчитать
    int id;
    public ReguarFlight(int id,int planeId,int routeId,Date takeOffTime, Date landingTime){//конструктор создает рейс с самолетом, маршрутом и датами
        this.planeId=planeId;
        this.routeId=routeId;
        this.takeOffTime=takeOffTime;
        this.landingTime=landingTime;
        this.id=id;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){return id;}
    public int getPlane(){
        return this.planeId;
    }
    public void setPlane(Plane plane){
        this.planeId=plane.getId();
    }
    
    public int getRoute(){
        return this.routeId;
    }
    public void setRoute(Route route){
        this.routeId=route.getId();
    }
    
    public void setTakeOffTimeShedule(Date date){
        this.takeOffTime=date;
    }
    public Date getTakeOffTimeShedule(){
        return this.takeOffTime;
    }
    
    public void setLandingTimeShedule(Date date){
        this.landingTime=date;
    }
    public Date getLandingTimeShedule(){
        return this.landingTime;
    }
    

    public double ticketPrice(Route route, Plane plane){//возвращает стоимость билета из расчета цена литра керосина * расстояние полета * потребление топлива / пассажирскиш мест
        double coeff = 6000;
        return plane.getFuelConsumption()* coeff/( route.getDistance()*
                plane.getPassengerSeatsCount());
    }
}
