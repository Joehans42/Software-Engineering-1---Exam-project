import java.util.HashMap;

/**
 * Created by johan on 27/03/2017.
 * 
 */
public class Activity {
    private class Entry{

        boolean isAssigned;
        HashMap<Integer, Integer> loggedTime = new HashMap<>(); // maps week to time
        
        Entry(){}
        
        Entry(boolean isAssigned){
            
            this.isAssigned = isAssigned;
            
        }
    }
    
    private final HashMap<Employee, Entry> entries = new HashMap<>();
    
    private String name;
    private int budgetedTime;
    private int startWeek;
    private int duration;
    
    public Activity(String name, int budgetedTime, int startWeek, int duration, Employee... assignees){
        
        this.name = name;
        this.budgetedTime = budgetedTime;
        this.startWeek = startWeek;
        this.duration = duration;
        
        for(Employee e : assignees)
            entries.put(e, new Entry(true));
        
    }

    public void setName(String name) {
        
        this.name = name;
        
    }

    public String getName() {
        
        return name;
        
    }

    public int getBudgetedTime() {
        
        return budgetedTime;
        
    }

    public void setBudgetedTime(int budgetedTime) {
        
        this.budgetedTime = budgetedTime;
        
    }

    public void setStartWeek(int startWeek) {
        
        this.startWeek = startWeek;
        
    }

    public int getStartWeek() {
        
        return startWeek;
        
    }

    public int getDuration() {
        
        return duration;
        
    }

    public void setDuration(int duration) {
        
        this.duration = duration;
        
    }

    public String report(int week){
        
        return null; //TODO: implement
        
    }
    
    public void setAssigned(Employee e, boolean assign){
        
        Entry entry = entries.computeIfAbsent(e, emp -> new Entry());
        entry.isAssigned = assign;
        
    }
    
    public boolean isAssigned(Employee e){

        Entry entry = entries.get(e);
        return entry != null && entry.isAssigned;

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
                    "Employee " + e.getUuid() + " currently has " + e.getUnloggedTime()/2D + " unlogged hours.");
        
        Entry entry = entries.computeIfAbsent(e, emp -> new Entry(false));
        
        entry.loggedTime.merge(week, time, (v, t) -> v+t); // add time to current value
        e.setUnloggedTime(e.getUnloggedTime() - time);
        
    }

    public void unlogTime(Employee e, int week, int time){

        if(time < 0)
            throw new IllegalArgumentException("Cannot unlog negative hours, try logging hours instead.");
        
        Entry entry = entries.computeIfAbsent(e, emp -> new Entry(false));
        int v = entry.loggedTime.getOrDefault(week, 0);

        if(v-time < 0)
            throw new IllegalArgumentException("Cannot unlog more hours than have already been logged. " +
                    "Employee " + e.getUuid() + " currently has " + v/2D + " hours logged on this activity " +
                    "in week " + (week-startWeek) + ".");
        
        entry.loggedTime.put(week, v-time);
        e.setUnloggedTime(e.getUnloggedTime() + time);
        
    }
}
