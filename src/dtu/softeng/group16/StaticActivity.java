package dtu.softeng.group16;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kenny on 09-04-2017.
 */
public class StaticActivity{
    
    // mapping of employee to mapping of weeks to time worked
    private final HashMap<Employee, HashMap<Integer, Integer>> entries = new HashMap<>();
    private String name;
    
    public StaticActivity(String name){
        
        this.name = name;
        
    }
    
    public void setName(String name){
        
        this.name = name;
        
    }
    
    public String getName(){
        
        return name;
        
    }
    
    public HashMap<Employee, HashMap<Integer, Integer>> getEntries(){
        
        return entries;
        
    }
    
    public String report(int week){ // Kenny/Rasmus
        
        int total = 0;
        
        String report = "Activity name:\t\t\t" + getName() + "\n";
        String logs = "";
        
        for(Map.Entry<Employee, HashMap<Integer, Integer>> ent : getEntries().entrySet()){
            
            int time = ent.getValue().getOrDefault(week, 0);
            
            if(time > 0)
                logs += ent.getKey().getUuid() + ": " + Main.formatTime(time) + " hour(s)\n";
            
            total += time;
            
        }
        
        if(total > 0){ // work has been done this week
            
            report += "Time has been logged on activity '" + getName() + "' by\n";
            report += logs;
            
        }else // nothing this week
            report += "No time has been logged on activity '" + getName() + "' in week " + Main.formatWeek(week) + ".";
        
        return report;
        
    }
    
    public int getLoggedTime(Employee e, int week){ // Kenny
        
        HashMap<Integer, Integer> entry = getEntries().get(e);
        
        if(entry == null)
            return 0;
        
        return entry.getOrDefault(week, 0);
        
    }
    
    public void logTime(Employee e, int week, int time){ // Kenny
        
        if(time < 0)
            throw new IllegalArgumentException("Cannot log negative hours, try unlogging hours instead.");
        
        HashMap<Integer, Integer> entry = getEntries().computeIfAbsent(e, emp -> new HashMap<>());
        entry.merge(week, time, (v, t) -> v + t); // add time to current value
        
    }
    
    public void unlogTime(Employee e, int week, int time){ // Kenny
        
        if(time < 0)
            throw new IllegalArgumentException("Cannot unlog negative hours, try logging hours instead.");
        
        HashMap<Integer, Integer> entry = getEntries().computeIfAbsent(e, emp -> new HashMap<>());
        int v = entry.getOrDefault(week, 0); // get time already logged this week
        
        if(v - time < 0)
            throw new IllegalArgumentException("Cannot unlog more hours than have already been logged. " +
                                               "Employee " + e.getUuid() + " currently has " + v / 2D +
                                               " hours logged on this activity in week " + Main.formatWeek(week) + ".");
        else if(v - time == 0)
            entry.remove(week);
        else
            entry.put(week, v - time);
        
    }
}
