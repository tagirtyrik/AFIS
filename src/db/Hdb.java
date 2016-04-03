package db;

import model.aircraft.Aircraft;
import model.airport.InternationalAirport;
import model.flight.ReguarFlight;
import model.route.RegularRoute;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;
//import javax.transaction.*;

import java.util.*;

/**
 * Created by Ксю on 26.03.2016.
 */
public class Hdb {


    static class Plane
    {
        public static ArrayList<model.Plane> getAll()
        {
            Session session = HibernateUtils.getSession();
            return (ArrayList) session.createCriteria(Aircraft.class).list();
        }

        public static Aircraft get(int id)
        {
            Session session = HibernateUtils.getSession();
            List<Aircraft> aircrafts = session.createCriteria(Aircraft.class).list();
            for (Aircraft a: aircrafts)
            {
                if(a.getId() == id)
                {
                    return  a;
                }
            }
            return null;
        }

        public static ArrayList<model.Plane> getWhere(String id,String name,String number, String passengerSeatsCount, String fuelCons)
        {
            Session session = HibernateUtils.getSession();
            List<Aircraft> aircrafts = session.createCriteria(Aircraft.class).list();
            ArrayList<model.Plane> arr = new ArrayList<>();
            for(Aircraft a: aircrafts)
            {
                if( String.valueOf(a.getId()).equals(id) || a.getNumber().equals(number) || a.getName().equals(name)
                        || String.valueOf(a.getFuelConsumption()).equals(fuelCons)
                        || String.valueOf(a.getPassengerSeatsCount()).equals(passengerSeatsCount))
                {
                    arr.add(a);
                }
            }
            if(arr.size() == 0)
            {
                return (ArrayList) aircrafts;
            }
            else
            {
                return arr;
            }

        }


        public  static void  update(Object o){
            Session session = HibernateUtils.getSession();
            Transaction t = session.beginTransaction();
            session.update(o);
            t.commit();
        }

        public  static void  add(Object o)
        {
            Session session = HibernateUtils.getSession();
            Transaction t = session.beginTransaction();
            session.save(o);
            t.commit();
        }

        public  static void  delete(int id)
        {
            Session session = HibernateUtils.getSession();
            Transaction t = session.beginTransaction();
            List<Aircraft> aircrafts = session.createCriteria(Aircraft.class).list();
            for(Aircraft a: aircrafts)
            {
                if(a.getId()== id)
                {
                    session.delete(a);
                    break;
                }
            }
            t.commit();
   //         session.close();
        }
    }

    static class Airport
    {
        public static ArrayList<model.Airport> getAll()
        {
            Session session = HibernateUtils.getSession();
            return (ArrayList) session.createCriteria(InternationalAirport.class).list();
        }

        public static model.Airport get(int id)
        {
            Session session = HibernateUtils.getSession();
            List<model.Airport> aircrafts = session.createCriteria(InternationalAirport.class).list();
            for (model.Airport a: aircrafts)
            {
                if(a.getId() == id)
                {
                    return  a;
                }
            }
            return null;
        }


        public static ArrayList<model.Airport> getWhere(String id,String location,String title)
        {
            Session session = HibernateUtils.getSession();
            List<model.Airport> airports = session.createCriteria(InternationalAirport.class).list();
            ArrayList<model.Airport> arr = new ArrayList<>();
            for(model.Airport a: airports)
            {
                if(String.valueOf(a.getId()).equals(id) || a.getLocation().equals(location)|| a.getName().equals(title))
                {
                    arr.add(a);
                }
            }
            if(arr.size() == 0)
            {
                return (ArrayList) airports;
            }
            else
            {
                return arr;
            }
        }


        public  static void  update(Object o){
            Session session = HibernateUtils.getSession();
            Transaction t = session.beginTransaction();
            session.update(o);
            t.commit();
        }

        public  static void  add(Object o)
        {
            Session session = HibernateUtils.getSession();
            Transaction t = session.beginTransaction();
            session.save(o);
            t.commit();
        }

        public  static void  delete(int id)
        {
            Session session = HibernateUtils.getSession();
            Transaction t = session.beginTransaction();
            List<model.Airport> aircrafts = session.createCriteria(InternationalAirport.class).list();
            for(model.Airport a: aircrafts)
            {
                if(a.getId()== id)
                {
                    session.delete(a);
                    break;
                }
            }
            t.commit();
         //   session.close();
        }
    }

    static class Route
    {
        public static ArrayList<model.Route> getAll()
        {
            Session session = HibernateUtils.getSession();
            return (ArrayList) session.createCriteria(RegularRoute.class).list();
        }

        public static model.Route get(int id)
        {
            Session session = HibernateUtils.getSession();
            List<model.Route> routes = session.createCriteria(RegularRoute.class).list();
            for (model.Route a: routes)
            {
                if(a.getId() == id)
                {
                    return  a;
                }
            }
            return null;
        }


        public static ArrayList<model.Route> getWhere(String id, String distance, String tp, String lp)
        {
            Session session = HibernateUtils.getSession();
            List<model.Route> routes = session.createCriteria(RegularRoute.class).list();
            ArrayList<model.Route> arr = new ArrayList<>();
            for(model.Route r: routes)
            {
                if(String.valueOf(r.getId()).equals(id) || String.valueOf(r.getDistance()).equals(distance) || String.valueOf(r.getLandingPort()).equals(lp)
                        || String.valueOf(r.getTakeOffPort()).equals(tp))
                {
                    arr.add(r);
                }
            }
            if(arr.size() == 0)
            {
                return (ArrayList) routes;
            }
            else
            {
                return arr;
            }
        }


        public  static void  update(Object o){
            Session session = HibernateUtils.getSession();
            Transaction t = session.beginTransaction();
            session.update(o);
            t.commit();
        }

        public  static void  add(Object o)
        {
            Session session = HibernateUtils.getSession();
            Transaction t = session.beginTransaction();
            session.save(o);
            t.commit();
        }

        public  static void  delete(int id)
        {
            Session session = HibernateUtils.getSession();
            Transaction t = session.beginTransaction();
            List<model.Route> routes = session.createCriteria(RegularRoute.class).list();
            for(model.Route a: routes)
            {
                if(a.getId()== id)
                {
                    session.delete(a);
                    break;
                }
            }
            t.commit();
        }
    }

    static class Flight
    {
        public static ArrayList<model.Flight> getAll()
        {
            Session session = HibernateUtils.getSession();
            return (ArrayList) session.createCriteria(ReguarFlight.class).list();
        }

        public static model.Flight get(int id)
        {
            Session session = HibernateUtils.getSession();
            List<model.Flight> flights = session.createCriteria(ReguarFlight.class).list();
            for (model.Flight f: flights)
            {
                if(f.getId() == id)
                {
                    session.close();
                    return  f;
                }
            }
            session.close();
            return null;
        }


        public static ArrayList<model.Flight> getWhere(String id, String idp, String idr, String tiof, String lt)
        {
            Session session = HibernateUtils.getSession();
            List<model.Flight> flights = session.createCriteria(ReguarFlight.class).list();
            ArrayList<model.Flight> arr = new ArrayList<>();
            for(model.Flight f: flights)
            {
                if(String.valueOf(f.getId()).equals(id) || String.valueOf(f.getRoute()).equals(idr)
                        || String.valueOf(f.getPlane()).equals(idp) || f.getLandingTimeShedule().equals(lt) || f.getTakeOffTimeShedule().equals(tiof))
                {
                    arr.add(f);
                }
            }
            if(arr.size()==0)
            {
                return (ArrayList) flights;
            }
            else
            {
                return arr;
            }

        }


        public  static void  update(Object o){
            Session session = HibernateUtils.getSession();
            Transaction t = session.beginTransaction();
            session.update(o);
            t.commit();
        }

        public  static void  add(Object o)
        {
            Session session = HibernateUtils.getSession();
            Transaction t = session.beginTransaction();
            session.save(o);
            t.commit();
        }

        public  static void  delete(int id)
        {
            Session session = HibernateUtils.getSession();
            Transaction t = session.beginTransaction();
            List<model.Flight> flights = session.createCriteria(ReguarFlight.class).list();
            for(model.Flight f: flights)
            {
                if(f.getId()== id)
                {
                    session.delete(f);
                    break;
                }
            }
            t.commit();
          //  session.close();
        }
    }
}
