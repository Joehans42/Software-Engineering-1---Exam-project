import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by johan on 27/03/2017.
 */
public class Project{
    
    private static final AtomicInteger counter = new AtomicInteger(1);
    
    private String   name;
    private Employee manager;
    final private String id;
    
    private final int startWeek;
    
    private final ArrayList<Activity> activities = new ArrayList<>();
    
    public Project(String name, int startWeek, Employee owner){
        this.id = generateId(startWeek);
        this.name = name;
        this.startWeek = startWeek;
        this.manager = owner;
        
    }
    
    public String getName(){
        
        return name;
        
    }
    
    public void setName(String name){
        
        this.name = name;
        
    }
    
    public int getStartWeek(){
        
        return startWeek;
        
    }
    
    public String getId(){
        
        //return generateId(startWeek);
        return id;
        
    }
    
    public Employee getManager(){
        
        return manager;
        
    }
    
    public void setManager(Employee manager){
        
        this.manager = manager;
        
    }
    
    public ArrayList<Activity> getActivities(){
        
        return activities;
        
    }
    
    public String report(int week){
        
        //TODO: remember that name and manager can be null
        
        //Draft 1.0
        String managerUuid;
        if (manager == null) {
            managerUuid = "no manager";
        } else {
            managerUuid = manager.getUuid();
        }
        String projectName;
        if (getName() == null) {
            projectName = "unnamed";
        } else {
            projectName = getName();
        }

        String report   ="Report of '" + projectName + "'; project week " + week +  "\n\n"+
                    "Project name:\t\t\t" + projectName + "\n"+
                    "Project id:\t\t\t\t" + getId() + "\n"+
                    "Project manager:\t\t" + managerUuid + "\n\n"+
                    "Project activities:\n\n********************************\n\n";
        for(Activity a : getActivities()) {
            report = report + a.report(week, getStartWeek()) + "\n\n";
        }

        report += "********************************\n\n";

        return report;

        //return null; //TODO: implement
        
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
