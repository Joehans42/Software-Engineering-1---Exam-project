import java.util.ArrayList;

/**
 * Created by johan on 27/03/2017.
 * 
 */
public class Project {
    
    private String name;
    private int id;
    private Employee owner;
    
    private final ArrayList<Activity> activities = new ArrayList<>();
    
    public Project(String name, int id, Employee owner){
        
        this.name = name;
        this.id = id;
        this.owner = owner;
        
    }
    
    public String getName() {
        
        return name;
        
    }
    
    public void setName(String name) {
        
        this.name = name;
        
    }
    
    public int getId() {
        
        return id;
        
    }
    
    public void setId(int id) {
        
        this.id = id;
        
    }
    
    public Employee getOwner() {
        
        return owner;
        
    }
    
    public void setOwner(Employee owner) {
        
        this.owner = owner;
        
    }

    public ArrayList<Activity> getActivities() {
        
        return activities;
        
    }
    
    public String report(int week){
        
        return null; //TODO: implement
        
    }
}
