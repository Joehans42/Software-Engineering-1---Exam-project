import java.util.HashMap;
import java.util.Map;

/**
 * Created by johan on 27/03/2017.
 */
public class Activity{
    private class Entry{
        
        boolean isAssigned;
        HashMap<Integer, Integer> loggedTime = new HashMap<>(); // maps week to time
        
        Entry(boolean isAssigned){
            
            this.isAssigned = isAssigned;
            
        }
    }
    
    private final HashMap<Employee, Entry> entries = new HashMap<>();
    
    private String name;
    private int    budgetedTime;
    private int    startWeek;
    private int    duration;
    
    public Activity(String name, int budgetedTime, int startWeek, int duration, Employee... assignees){
        
        if(duration < 0)
            throw new IllegalArgumentException("Activity duration cannot be negative!");
        
        if(budgetedTime < 0)
            throw new IllegalArgumentException("Activity budgeted time cannot be negative!");
        
        this.name = name;
        this.budgetedTime = budgetedTime;
        this.startWeek = startWeek;
        this.duration = duration;
        
        for(Employee e : assignees)
            entries.put(e, new Entry(true));
        
    }
    
    public void setName(String name){
        
        this.name = name;
        
    }
    
    public String getName(){
        
        return name;
        
    }
    
    public int getBudgetedTime(){
        
        return budgetedTime;
        
    }
    
    public void setBudgetedTime(int budgetedTime){
        
        if(budgetedTime < 0)
            throw new IllegalArgumentException("Activity budgeted time cannot be negative!");
        
        this.budgetedTime = budgetedTime;
        
    }
    
    public void setStartWeek(int startWeek){
        
        this.startWeek = startWeek;
        
    }
    
    public int getStartWeek(){
        
        return startWeek;
        
    }
    
    public int getDuration(){
        
        return duration;
        
    }
    
    public void setDuration(int duration){
        
        if(duration < 0)
            throw new IllegalArgumentException("Activity duration cannot be negative!");
        
        this.duration = duration;
        
    }
    
    public void setAssigned(Employee e, boolean assign){
        
        Entry entry = entries.get(e);
        
        if(entry == null)
            entries.put(e, new Entry(assign));
        else
            entry.isAssigned = assign;
        
    }
    
    public boolean isAssigned(Employee e){
        
        Entry entry = entries.get(e);
        return entry != null && entry.isAssigned;
        
    }
    
    public String report(int week){ //FIXME: not done yet // kenny
        
        String report = "";
        int total = 0;
        
        for(Map.Entry<Employee, Entry> ent : entries.entrySet()){
            
            int time = ent.getValue().loggedTime.getOrDefault(week, 0);
            
            if(time > 0)
                report += ent.getKey().getUuid() + ": " + time / 2D + " hours\n";
            
            total += time;
            
        }
        
        if(total > 0){ // work has been done this week
            
            report = "Time has been logged on activity '" + getName() + "' by\n" + report;
            report += ""; //TODO: percent of expected work this week
            
        }else // nothing this week
            report = "No time has been logged on activity '" + getName() + "' this week.";
        
        return report;
        
    }
    
    public int getLoggedTime(Employee e, int week){
        
        Entry entry = entries.get(e);
        
        if(entry == null)
            return 0;
        
        return entry.loggedTime.getOrDefault(week, 0);
        
    }
    
    public void logTime(Employee e, int week, int time){
        
        if(time < 0)
            throw new IllegalArgumentException("Cannot log negative hours, try unlogging hours instead.");
        
        if(time > e.getUnloggedTime())
            throw new IllegalArgumentException("You cannot log more hours than your current unlogged hours. " +
                                               "Employee " + e.getUuid() + " currently has " + e.getUnloggedTime() / 2D + " unlogged hours.");
        
        Entry entry = entries.computeIfAbsent(e, emp -> new Entry(false));
        
        entry.loggedTime.merge(week, time, (v, t) -> v + t); // add time to current value
        e.setUnloggedTime(e.getUnloggedTime() - time);
        
    }
    
    public void unlogTime(Employee e, int week, int time){
        
        if(time < 0)
            throw new IllegalArgumentException("Cannot unlog negative hours, try logging hours instead.");
        
        Entry entry = entries.computeIfAbsent(e, emp -> new Entry(false));
        int v = entry.loggedTime.getOrDefault(week, 0);
        
        if(v - time < 0)
            throw new IllegalArgumentException("Cannot unlog more hours than have already been logged. " +
                                               "Employee " + e.getUuid() + " currently has " + v / 2D + " hours logged on this activity " +
                                               "in week " + (week - startWeek) + ".");
        else if(v - time == 0)
            entry.loggedTime.remove(week);
        else
            entry.loggedTime.put(week, v - time);
        
        e.setUnloggedTime(e.getUnloggedTime() + time);
        
    }
}
