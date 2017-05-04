package dtu.softeng.group16;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by johan on 27/03/2017.
 */
public class Main{
    
    private final HashMap<String, Employee> employees    = new HashMap<>();
    private final HashMap<String, Project>  projects     = new HashMap<>();
    private final ArrayList<StaticActivity> stactivities = new ArrayList<>();
    
    public ArrayList<Employee> getAvailableEmployees(int week){
        
        HashMap<Employee, Integer> timeMap = new HashMap<>();
        
        //TODO: build timeMap
        
        return timeMap.entrySet()
                      .stream()
                      .sorted(Map.Entry.comparingByValue()) // sort descending
                      .map(Map.Entry::getKey) // transform entry set to employee set
                      .collect(Collectors.toCollection(ArrayList::new)); // as ArrayList
        
    }
    
    public static int toWeek(Date d){ // Kenny
        
        return (int) (TimeUnit.MILLISECONDS.toDays(d.getTime()) / 7L) + 1; // +1 for the week we are in
        
    }
    
    public static int currentWeek(){ // Kenny
        
        return toWeek(new Date());
        
    }
    
    public static String formatWeek(int week){ // Kenny
        
        Date d = new Date(TimeUnit.DAYS.toMillis(7 * week));
        String year = new SimpleDateFormat("yyyy").format(d);
        String weekInYear = new SimpleDateFormat("ww").format(d);
        
        return weekInYear + ", " + year;
        
    }
    
    public static String formatTime(int time){ // Kenny
        
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(0);
        
        return nf.format((time / 2D));
        
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
