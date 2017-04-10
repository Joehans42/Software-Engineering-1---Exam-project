import java.util.ArrayList;
import java.util.SortedSet;

/**
 * Created by johan on 27/03/2017.
 * 
 */
public class Main {
    
    private static final Main instance = new Main();
    private static final ArrayList<Employee> employees = new ArrayList<>();
    private static final ArrayList<Project> projects = new ArrayList<>();
    private static final ArrayList<StaticActivity> stactivities = new ArrayList<>();
    
    private Main(){}
    
    public static Main getInstance(){
        
        return instance;
        
    }

    public static ArrayList<Employee> getEmployees() {
        
        return employees;
        
    }

    public static ArrayList<Project> getProjects() {
        
        return projects;
        
    }

    public static ArrayList<StaticActivity> getStaticActivities() {
        
        return stactivities;
        
    }
    
    public static SortedSet<Employee> getAvailableEmployees(){
        
        return null; //TODO: implement
        
    }
}
