
package model;
/*
    любой аэродром должен уметь делать эти вещи
*/
public interface Airport {
    public int getId();
    public void setId(int id);
    public String getName();
    public void setName(String airportName);
    public String getLocation();
    public void setLocation(String airportLocation);
}
