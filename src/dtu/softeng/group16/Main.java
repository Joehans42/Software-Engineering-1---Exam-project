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
    
    // a regular HashMap has no ordering, but a LinkedHashMap has a predictable order
    public LinkedHashMap<Employee, Integer> getAvailableEmployees(int week){ // Kenny
        
        HashMap<Employee, Integer> timeMap = new HashMap<>();
        
        for(Employee e : getEmployees().values())
            timeMap.put(e, 0);
        
        // we count the projected work hours in the given week
        for(Project p : getProjects().values()){
            for(Activity a : p.getActivities()){
                
                // make sure the activity takes place during the given week
                
                if(a.getStartWeek() > week)
                    continue;
                
                if(a.getStartWeek() + a.getDuration() < week)
                    continue;
                
                int budget = a.getBudgetedTime();
                int duration = a.getDuration();
                
                int proj = budget / duration; // how many expected hours per week
                
                for(Employee e : a.getAssignees())
                    timeMap.merge(e, proj, (i1, i2) -> i1 + i2);
                
            }
        }
        
        // we should also count hours logged on static activities
        // an employee who is on vacation for instance isn't available
        for(StaticActivity sa : getStaticActivities()){
            for(Map.Entry<Employee, HashMap<Integer, Integer>> entry : sa.getEntries().entrySet()){
                
                Employee e = entry.getKey();
                int hours = entry.getValue().getOrDefault(week, 0);
                
                timeMap.merge(e, hours, (i1, i2) -> i1 + i2);
                
            }
        }
        
        return timeMap.entrySet()
                      .stream()
                      .sorted(Map.Entry.comparingByValue()) // sort ascending
                      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                (e1, e2) -> e1, LinkedHashMap::new));
        
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
