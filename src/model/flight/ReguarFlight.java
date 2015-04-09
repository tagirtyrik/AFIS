
package model.flight;
import java.io.Serializable;
import java.util.Date;
import model.Flight;
import model.Plane;
import model.Route;
/*
    рейс, совершаемый самолетом по маршруту
    это самый обычный скучный рейс
*/
public class ReguarFlight implements Flight,Serializable{
    
    double fuelPrice=26.80;//стоимость литра авиационного керосина(возможно стоит сделать какой-нибудь глобальной константой?)
    
    int planeId;// абстрактный самолет, летящий по рейсу(какой угодно модели)
    int routeId;// абстрактный маршрут самолета
    
   /* Plane plane;
    Route route;*/
    
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
    
    public String timeAtFlight(){//возвращяет время самолета в полете(в мимисекундах)

        long msec =  this.landingTime.getTime() - this.takeOffTime.getTime();
        
        int hrs=(int)msec/1000/60/60;//расчет часовой разницы
        int min=(int)msec/1000/60-hrs*60;//расчет минутной разницы
        int sec=(int)msec/1000-min*60-hrs*60*60;//расчет секундной разницы
        
        StringBuffer time=new StringBuffer();//представление времени в 00:00:00
        if(hrs<10){
            time.append("0");
            time.append(hrs);}
        else time.append(hrs);
        
        time.append(":");
        
        if(min<10){time.append("0");
            time.append(min);}
        else time.append(min);
        
        time.append(":");
        
        if(sec<10){
            time.append("0");
            time.append(sec);}
        else time.append(sec);
        return new String(time);
    }
    public double ticketPrice(Route route, Plane plane){//возвращает стоимость билета из расчета цена литра керосина * расстояние полета * потребление топлива / пассажирскиш мест
        //PS мне сильно не нравится этот метод расчета
        
        return this.fuelPrice*route.getDistance()*plane.getFuelConsumption()/
                plane.getPassengerSeatsCount();//надеюсь сделает что надо
    }
    public String toString(){
        StringBuilder rezult=new StringBuilder("<flight>\n");
        rezult.append("<id>");
        rezult.append(this.id);
        rezult.append("</id>");
        rezult.append("<planeId>");
        rezult.append(this.getPlane());
        rezult.append("</planeId>\n");
        rezult.append("<routeId>");
        rezult.append(this.getRoute());
        rezult.append("</routeId>\n");
        rezult.append("<takeOffTime>");
        rezult.append(this.getTakeOffTimeShedule().toString());
        rezult.append("</takeOffTime>\n");
        rezult.append("<landingTime>");
        rezult.append(this.getLandingTimeShedule().toString());
        rezult.append("</landingTime>\n");
        rezult.append("</flight>");
        return new String(rezult);
    }
}
