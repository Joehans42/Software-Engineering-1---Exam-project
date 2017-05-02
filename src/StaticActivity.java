import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kenny on 09-04-2017.
 */
public class StaticActivity{
    protected class Entry{
        
        boolean isAssigned;
        HashMap<Integer, Integer> loggedTime = new HashMap<>(); // maps week to time
        
        Entry(boolean isAssigned){
            
            this.isAssigned = isAssigned;
            
        }
    }
    
    protected final HashMap<Employee, StaticActivity.Entry> entries = new HashMap<>();
    private String name;
    
    public StaticActivity(String name, Employee... assignees){
        
        this.name = name;
        
        for(Employee e : assignees)
            entries.put(e, new StaticActivity.Entry(true));
        
    }
    
    public void setName(String name){
        
        this.name = name;
        
    }
    
    public String getName(){
        
        return name;
        
    }
    
    public void setAssigned(Employee e, boolean assign){
        
        StaticActivity.Entry entry = entries.get(e);
        
        if(entry == null)
            entries.put(e, new StaticActivity.Entry(assign));
        else
            entry.isAssigned = assign;
        
    }
    
    public boolean isAssigned(Employee e){
        
        StaticActivity.Entry entry = entries.get(e);
        return entry != null && entry.isAssigned;
        
    }
    
    public String report(int week){
        
        int total = 0;
        
        String report = "Activity name:\t\t\t" + getName() + "\n";
        String logs = "";
        
        for(Map.Entry<Employee, StaticActivity.Entry> ent : entries.entrySet()){
            
            int time = ent.getValue().loggedTime.getOrDefault(week, 0);
            
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
    
    public int getLoggedTime(Employee e, int week){
        
        StaticActivity.Entry entry = entries.get(e);
        
        if(entry == null)
            return 0;
        
        return entry.loggedTime.getOrDefault(week, 0);
        
    }
    
    public void logTime(Employee e, int week, int time){
        
        if(time < 0)
            throw new IllegalArgumentException("Cannot log negative hours, try unlogging hours instead.");
        
        StaticActivity.Entry entry = entries.computeIfAbsent(e, emp -> new StaticActivity.Entry(false));
        entry.loggedTime.merge(week, time, (v, t) -> v + t); // add time to current value
        
    }
    
    public void unlogTime(Employee e, int week, int time){
        
        if(time < 0)
            throw new IllegalArgumentException("Cannot unlog negative hours, try logging hours instead.");
        
        StaticActivity.Entry entry = entries.computeIfAbsent(e, emp -> new StaticActivity.Entry(false));
        int v = entry.loggedTime.getOrDefault(week, 0); // get time already logged this week
        
        if(v - time < 0)
            throw new IllegalArgumentException("Cannot unlog more hours than have already been logged. " +
                                               "Employee " + e.getUuid() + " currently has " + v / 2D +
                                               " hours logged on this activity in week " + Main.formatWeek(week) + ".");
        
        else if(v - time == 0)
            entry.loggedTime.remove(week);
        else
            entry.loggedTime.put(week, v - time);
        
    }
}
