package dtu.softeng.group16;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by johan on 27/03/2017.
 */
public class Project{
    
    // year mapped to counter
    private static final HashMap<Integer, AtomicInteger> counters = new HashMap<>();
    
    private final HashSet<Activity> activities = new HashSet<>();
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
    
    public HashSet<Activity> getActivities(){
        
        return activities;
        
    }
    
    public String report(int week){ // Kenny/Rasmus
        
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
        
        assert week > 0;
        
        LocalDate monday = Main.toMonday(week);
        int year = monday.getYear();
        
        AtomicInteger counter = counters.computeIfAbsent(year, y -> new AtomicInteger(0));
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy");
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMinimumIntegerDigits(4);
        
        int oldc = counter.get();
        int c = counter.incrementAndGet();
        
        String formatted = dtf.format(monday) + nf.format(c);
        
        assert oldc + 1 == c;
        
        return formatted;
        
    }
    
    public static void resetIdCounters(){ // exists for testing purposes
        
        counters.clear();
        
    }
}
