
package model.route;
import java.io.Serializable;
import model.Route;
/*
    простой маршрут между двумя точками
*/
public class RegularRoute implements Route,Serializable{
   // private Airport takeOffPort; //..откуда
  //  private Airport landingPort;//..куда
    private int takeOffPortId;
    private int landingPortId;
    private double distance;// расстояние
    int id;
    public RegularRoute(int id, int takeOffPort,int landingPort,double distance){//конструктор инициализирует все переменные
        this.takeOffPortId=takeOffPort;
        this.landingPortId=landingPort;
        this.distance=distance;
        this.id=id;
    }
    public int getId(){return id;}
    public void setId(int id){
        this.id=id;
    }
    public int getTakeOffPort(){
        return this.takeOffPortId;
    }
    public void setTakeOffPort(int takeOffPort){
        this.takeOffPortId=takeOffPort;
    }
    
    public int getLandingPort(){
        return this.landingPortId;
    }
    public void setLandingPort(int landingPort){
        this.landingPortId=landingPort;
    }
    
    public double getDistance(){
        return this.distance;
    }
    public void setDistance(double distance){
        this.distance=distance;
    }
}
