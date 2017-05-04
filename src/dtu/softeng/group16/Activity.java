package dtu.softeng.group16;

import java.text.NumberFormat;
import java.util.Map;

/**
 * Created by johan on 27/03/2017.
 */
public class Activity extends StaticActivity{
    
    private int budgetedTime;
    private int startWeek;
    private int duration;
    
    public Activity(String name, int budgetedTime, int startWeek, int duration, Employee... assignees){
        
        super(name, assignees);
        
        if(duration < 0)
            throw new IllegalArgumentException("Activity duration cannot be negative!");
        
        if(budgetedTime < 0)
            throw new IllegalArgumentException("Activity budgeted time cannot be negative!");
        
        this.budgetedTime = budgetedTime;
        this.startWeek = startWeek;
        this.duration = duration;
        
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
    
    public String report(int week){
        
        int total = 0;
        
        String report = "Activity name:\t\t\t" + getName() + "\n" +
                        "Start week:\t\t\t\t" + Main.formatWeek(startWeek) + " \n" +
                        "Budgeted time:\t\t\t" + Main.formatTime(getBudgetedTime()) + " hour(s)\n" +
                        "Duration:\t\t\t\t" + getDuration() + " week(s)\n\n";
        
        String work = "";
        
        for(Map.Entry<Employee, Entry> ent : entries.entrySet()){
            
            int time = ent.getValue().loggedTime.getOrDefault(week, 0);
            
            if(time > 0)
                work += ent.getKey().getUuid() + ": " + Main.formatTime(time) + " hour(s)\n";
            
            total += time;
            
        }
        
        if(total > 0){ // work has been done this week
            
            report += "Time has been logged on activity '" + getName() + "' in week " + Main.formatWeek(week) + "by\n";
            report += work;
            
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(0);
            
            double expected = (double) getBudgetedTime()/getDuration(); // we expect even work per week distribution (?)
            double pct = total / expected;
            
            report += "Total work done: " + total + " (" + nf.format(pct) + "% of expected work this week).";
            
        }
        else // nothing this week
            report += "No time has been logged on activity '" + getName() + "' in week " + Main.formatWeek(week) + ".";
        
        return report;
        
    }
    
    public void logTime(Employee e, int week, int time){
        
        if(time > e.getUnloggedTime())
            throw new IllegalArgumentException("You cannot log more hours than your current unlogged hours. " +
                                               "Employee " + e.getUuid() + " currently has " +
                                               e.getUnloggedTime() / 2D + " unlogged hours.");
        
        super.logTime(e, week, time);
        e.setUnloggedTime(e.getUnloggedTime() - time);
        
    }
    
    public void unlogTime(Employee e, int week, int time){
        
        super.unlogTime(e, week, time);
        e.setUnloggedTime(e.getUnloggedTime() + time);
        
    }
}