
package model.route;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import model.Route;
import model.airport.InternationalAirport;
import model.flight.ReguarFlight;

import javax.persistence.*;

/*
    маршрут между двумя точками
*/
@SequenceGenerator(name = "SEQ_ID", sequenceName = "regular_route_seq_id")
@Entity
@Table(name = "route")
public class RegularRoute implements Route,Serializable{
   // private Airport takeOffPort; //..откуда
  //  private Airport landingPort;//..куда
   @javax.persistence.Id
  // @GeneratedValue(generator = "SEQ_ID")
   @Column(name = "route_id")
   private int id;

    @Column(name = "takeoffport")
    private int takeOffPortId;

    @Column(name = "landingport")
    private int landingPortId;

    @Column(name = "distance")
    private double distance;// расстояние

   @OneToMany(mappedBy = "portOffTime",cascade = CascadeType.ALL,fetch = FetchType.LAZY,targetEntity = InternationalAirport.class)
    private Set<ReguarFlight> ListPartsOfTime = new HashSet<ReguarFlight>(0);

    @OneToMany(mappedBy = "landingTuime",cascade = CascadeType.ALL,fetch = FetchType.LAZY,targetEntity = InternationalAirport.class)
    private Set<ReguarFlight> ListLandingOfTime = new HashSet<ReguarFlight>(0);

    public ReguarFlight getRFlight() {
        return RFlight;
    }

    public void setRFlight(ReguarFlight RFlight) {
        this.RFlight = RFlight;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLIGHT_ID")
    private ReguarFlight RFlight;

    public Set<ReguarFlight> getListPartsOfTime() {
        return ListPartsOfTime;
    }

    public void setListPartsOfTime(Set<ReguarFlight> listPartsOfTime) {
        ListPartsOfTime = listPartsOfTime;
    }

    public Set<ReguarFlight> getListLandingOfTime() {
        return ListLandingOfTime;
    }

    public void setListLandingOfTime(Set<ReguarFlight> listLandingOfTime) {
        ListLandingOfTime = listLandingOfTime;
    }

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
