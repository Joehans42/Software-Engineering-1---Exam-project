package dtu.softeng.group16;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by johan on 27/03/2017.
 */
public class Project{
    
    private static final AtomicInteger counter = new AtomicInteger(1);
    
    private final ArrayList<Activity> activities = new ArrayList<>();
    private final String id;
    private final int    startWeek;
    
    private String   name;
    private Employee manager;
    
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
        
        Employee manager = getManager();
        String managerUuid = manager == null ? "no manager" : manager.getUuid();
        String projectName = Objects.toString(getName(), "unnamed");
        
        String report = "Report for '" + projectName + "' week " + Main.formatWeek(week) + "\n\n" +
                        "\tProject name:\t\t\t" + projectName + "\n" +
                        "\tProject id:\t\t\t\t" + getId() + "\n" +
                        "\tProject manager:\t\t" + managerUuid + "\n\n" +
                        "\tProject activities:\n\n********************************\n\n";
        
        for(Activity a : getActivities())
            report = report + "\t" + a.report(week).replaceAll("\n", "\n\t") + "\n\n";
        
        return report + "********************************\n\n";
        
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
