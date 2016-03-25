
package model.route;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import model.Route;
import model.flight.ReguarFlight;

import javax.persistence.*;

/*
    маршрут между двумя точками
*/
@SequenceGenerator(name = "SEQ_ID", sequenceName = "SEQ_ID")
@Entity
@Table(name = "route")
public class RegularRoute implements Route,Serializable{
   // private Airport takeOffPort; //..откуда
  //  private Airport landingPort;//..куда
   @javax.persistence.Id
   @GeneratedValue(generator = "SEQ_ID")
   @Column(name = "route_id")
   private int id;

    @Column(name = "takeoffport")
    private int takeOffPortId;

    @Column(name = "landingport")
    private int landingPortId;

    @Column(name = "distance")
    private double distance;// расстояние

    @OneToMany(mappedBy = "route_id",cascade = CascadeType.ALL,fetch = FetchType.LAZY,targetEntity = ReguarFlight.class)
    private List<ReguarFlight> albums = new LinkedList<ReguarFlight>();


    public RegularRoute()
    {

    }

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
    public void setDistance(double distance){this.distance=distance;}
}
