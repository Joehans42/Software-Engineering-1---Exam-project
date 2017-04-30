import org.junit.Test;

import java.text.NumberFormat;

import static org.junit.Assert.assertTrue;

/**
 * Created by Kenny on 13-04-2017.
 */
public class GenerateReportsTests extends LoggedSystemTest{
    
    @Test
    public void generateReports() throws Exception{
        for(Project p : main.getProjects().values()){
            for(int i = 0; i < 5; i++){
                
                String report = p.report(p.getStartWeek() + i);
                System.out.print(p.report(p.getStartWeek() + i));
                
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
    
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMinimumFractionDigits(0);
                    
                    assertTrue(areport.contains(nf.format(a.getBudgetedTime() / 2D)));
                    assertTrue(areport.contains(Integer.toString(a.getDuration())));
                    
                }
            }
        }
    }
}
