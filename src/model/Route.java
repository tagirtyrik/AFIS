package model;
/*
    любой маршрут должен уметь делать эти вещи
*/

public interface Route {
    public int getId();
    public void setId(int id);
    public int getTakeOffPort();
    public void setTakeOffPort(int takeOffPort);
    
    public int getLandingPort();
    public void setLandingPort(int landingPort);
    
    public double getDistance();
    public void setDistance(double distance);
}
