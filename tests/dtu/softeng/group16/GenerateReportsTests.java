package dtu.softeng.group16;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by Kenny on 13-04-2017.
 */
public class GenerateReportsTests extends LoggedSystemTest{
    
    @Test // white
    public void generateReports() throws Exception{ // Kenny
        for(Project p : main.getProjects().values()){
            for(int i = 0; 5 > i; i++){
                
                String report = p.report(p.getStartWeek() + i);
                
                assertTrue(report.contains(p.getId()));
                
                if(p.getName() != null)
                    assertTrue(report.contains(p.getName()));
                else
                    assertTrue(report.contains("unnamed"));
                
                if(p.getManager() != null)
                    assertTrue(report.contains(p.getManager().getUuid()));
                else
                    assertTrue(report.contains("no manager"));
                
                for(Activity a : p.getActivities()){
                    
                    String areport = a.report(p.getStartWeek() + i);
                    
                    // project report should contain the activity report
                    assertTrue(report.replaceAll("\t", "").contains(areport.replaceAll("\t", ""))); // ignore indent
                    
                    // more specifically, the activity report should contain
                    assertTrue(areport.contains(a.getName()));
                    assertTrue(areport.contains(Integer.toString(a.getStartWeek() - p.getStartWeek())));
                    
                    assertTrue(areport.contains(Main.formatTime(a.getBudgetedTime())));
                    assertTrue(areport.contains(Integer.toString(a.getDuration())));
                    
                }
            }
        }
        
        for(StaticActivity a : main.getStaticActivities()){
            for(int i = 0; 5 > i; i++){
                
                int week = Main.currentWeek() + i;
                String areport = a.report(week);
                assertTrue(areport.contains(a.getName()));
                
                for(Map.Entry<Employee, HashMap<Integer, Integer>> entries : a.getEntries().entrySet()){
                    
                    String uuid = entries.getKey().getUuid();
                    int time = entries.getValue().getOrDefault(week, 0);
                    
                    if(time == 0)
                        continue;
    
                    assertTrue(areport.contains(uuid + ": " + Main.formatTime(time)));
                    
                }
            }
        }
    }
}
