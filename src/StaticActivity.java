/**
 * Created by Kenny on 09-04-2017.
 * 
 */
public class StaticActivity extends Activity{
    public StaticActivity(String name, int budgetedTime, int startWeek, int endWeek){
        
        super(null, name, budgetedTime, startWeek, endWeek);
        
    }

    @Override
    public String report(int week){
        
        return null; //TODO implement
        
    }

    @Override
    public void logTime(Employee employee, int week, int time) {
        
        super.logTime(employee, week, time);
        
    }

    @Override
    public void unlogTime(Employee employee, int week, int time) {
        
        super.unlogTime(employee, week, time);
        
    }
}
