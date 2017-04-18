import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Created by Kenny on 13-04-2017.
 */
public class ProjectManagerTests extends FullSystemTest{
    
    @Test
    public void findAvailableEmployees() throws Exception{
        
        int week = Main.currentWeek();
        Collection<Employee> employees = main.getAvailableEmployees(week);
        
    }
    
    @Test
    public void generateReports() throws Exception{
        for(Project p : main.getProjects().values()){
            for(int i = 0; i < 5; i++){

                String report = p.report(p.getStartWeek() + i);
                System.out.print(p.report(i));

                assertTrue(report.contains(p.getId()));
                assertTrue(report.contains(Integer.toString(i))); // week number relative to start
                
                if(p.getName() != null)
                    assertTrue(report.contains(p.getName()));
                else
                    assertTrue(report.contains("unnamed"));
                
                if(p.getManager() != null)
                    assertTrue(report.contains(p.getManager().getUuid()));
                else
                    assertTrue(report.contains("no manager"));
                
                for(Activity a : p.getActivities()){
                    
                    String areport = a.report(p.getStartWeek() + i, p.getStartWeek());
                    
                    // project report should contain the activity report
                    assertTrue(report.contains(areport));
                    // more specifically, the activity report should contain
                    assertTrue(areport.contains(a.getName()));
                    assertTrue(areport.contains(Integer.toString(a.getStartWeek() - p.getStartWeek())));
                    assertTrue(areport.contains(Double.toString(a.getBudgetedTime() / 2D)));
                    assertTrue(areport.contains(Integer.toString(a.getDuration())));
                    
                }
            }
        }
    }
}
