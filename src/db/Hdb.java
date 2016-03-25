package db;

import model.Plane;
import model.aircraft.Aircraft;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ксю on 26.03.2016.
 */
public class Hdb {
    public static ArrayList<Plane> getPlane(){
        Session session = HibernateUtils.getSession();
        return (ArrayList) session.createCriteria(Aircraft.class).list();
    }
}
