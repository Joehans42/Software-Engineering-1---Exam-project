import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by johan on 27/03/2017.
 */
public class Main{
    
    private final HashMap<String, Employee> employees    = new HashMap<>();
    private final HashMap<String, Project>  projects     = new HashMap<>();
    private final ArrayList<StaticActivity> stactivities = new ArrayList<>();
    
    public static void main(String args[]){
        
        //TODO: build the GUI here, text-based
        
    }
    
    public Collection<Employee> getAvailableEmployees(int week){
        
        return null; //TODO: implement
        
    }
    
    public static int toWeek(Date d){
        
        return (int) (TimeUnit.MILLISECONDS.toDays(d.getTime()) / 7L);
        
    }
    
    public static int currentWeek(){
        
        return toWeek(new Date());
        
    }
    
    public HashMap<String, Employee> getEmployees(){
        return employees;
    }
    
    public HashMap<String, Project> getProjects(){
        return projects;
    }
    
    public ArrayList<StaticActivity> getStaticActivities(){
        return stactivities;
    }
}
