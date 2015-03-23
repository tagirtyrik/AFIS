
package model;
import java.util.Date;
/*
    любой рейс должен уметь делать эти вещи
    все время пока пусть будет в GMT 
*/
public interface Flight {
    public int getId();
    public void setId(int id);
    public int getPlane();
    public void setPlane(Plane plane);
    
    public int getRoute();
    public void setRoute(Route route);
    
    public void setTakeOffTimeShedule(Date date);
    public Date getTakeOffTimeShedule();
    
    public void setLandingTimeShedule(Date date);
    public Date getLandingTimeShedule();
    
    public String timeAtFlight();
    
    public double ticketPrice(Route route, Plane plane);
}
