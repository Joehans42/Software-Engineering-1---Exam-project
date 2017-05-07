package dtu.softeng.group16;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
                
                if(a.getStartWeek() + a.getDuration() <= week)
                    continue;
                
                int budget = a.getBudgetedTime();
                int duration = a.getDuration();
                
                HashSet<Employee> assignees = a.getAssignees();
                int proj = budget / duration / assignees.size(); // how many expected hours per week
                
                for(Employee e : assignees)
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
        
        // sort the timeMap by value (the time) in ascending order
        // and then collect stream key-value pairs in a new LinkedHashMap
        return timeMap.entrySet()
                      .stream()
                      .sorted(Map.Entry.comparingByValue()) // sort ascending
                      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                (e1, e2) -> e1, LinkedHashMap::new));
        
    }
    
    public static int currentWeek(){ // Kenny
        
        return toWeek(LocalDate.now());
        
    }
    
    // returns date of the first day of week (monday)
    public static LocalDate toMonday(int week){ // Kenny
        
        // january 1st 1970 was a thursday, in order to offset this in terms
        // of week numbers, we have to subtract 3 days
        
        return LocalDate.ofEpochDay(0).plusWeeks(week).minusDays(3);
        
    }
    
    public static int toWeek(LocalDate d){ // Kenny
        
        // plus 3 days to correctly fit monday-sunday week boundaries
        // (again because epoch time began on a thursday)
        
        assert d != null;
        return (int) ChronoUnit.WEEKS.between(LocalDate.ofEpochDay(0), d.plusDays(3));
        
    }
    
    public static String formatWeek(int week){ // Kenny
        
        assert week > 0;
        return DateTimeFormatter.ofPattern("ww, yyyy").format(toMonday(week));
        
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
