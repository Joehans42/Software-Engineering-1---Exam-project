import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by johan on 27/03/2017.
 * 
 */
public class Activity {
    private class Entry{

        boolean isAssigned;
        ArrayList<Integer> loggedTime = new ArrayList<>(); // logged time per week (week n = startWeek+n)
        
    }
    
    private final HashMap<Employee, Entry> entries = new HashMap<>();
    private final Project parent;
    
    private String name;
    private int budgetedTime;
    private int startWeek;
    private int endWeek;
    
    public Activity(Project parent, String name, int budgetedTime, int startWeek, int endWeek){
        
        this.parent = parent;
        
        this.name = name;
        this.budgetedTime = budgetedTime;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        
    }

    public Project getParent() {
        
        return parent;
        
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

    public int getEndWeek() {
        
        return endWeek;
        
    }

    public void setEndWeek(int endWeek) {
        
        this.endWeek = endWeek;
        
    }

    public String report(int week){
        
        return null; //TODO: implement
        
    }
    
    public void setAssigned(Employee e, boolean assign){
        
        //TODO: implement
        
    }
    
    public boolean isAssigned(Employee e){

        return false; //TODO: implement

    }
    
    public int getLoggedTime(Employee e, int week){
        
        return 0; //TODO: implement
        
    }
    
    public void logTime(Employee e, int week, int time){
        
        //TODO: implement
        
    }

    public void unlogTime(Employee employee, int week, int time){

        //TODO: implement

    }
}
