import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by johan on 27/03/2017.
 * 
 */
public class Project {
    
    private static final AtomicInteger counter = new AtomicInteger(1);
    
    private String name;
    private String id;
    private Employee owner;
    
    private final ArrayList<Activity> activities = new ArrayList<>();
    
    public Project(String name, int startWeek, Employee owner){
        
        this.name = name;
        this.id = generateId(startWeek);
        this.owner = owner;
        
    }
    
    public String getName() {
        
        return name;
        
    }
    
    public void setName(String name) {
        
        this.name = name;
        
    }
    
    public String getId() {
        
        return id;
        
    }
    
    public void setId(String id) {
        
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
        
        //TODO: remember that name and owner can be null
        
        return null; //TODO: implement
        
    }
    
    private static String generateId(int week){ // Kenny
        
        long time = TimeUnit.DAYS.toMillis(week * 7L);
        Date d = new Date(time);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMinimumIntegerDigits(4);
        
        return sdf.format(d) + nf.format(counter.getAndIncrement());
        
    }
}
