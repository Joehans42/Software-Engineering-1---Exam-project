import org.junit.Test;

import java.util.Collection;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Kenny on 13-04-2017.
 */
public class EmployeeTests extends FullSystemTest{
    
    @Test
    public void employeeLogsTimeInActivity() throws Exception{
        
        Random rand = new Random();
        int week = Main.currentWeek();
        
        // test all employee, project and activity combinations
        
        for(Employee e : main.getEmployees().values()){
            for(Project p : main.getProjects().values()){
                for(Activity a : p.getActivities()){
                    
                    int t1 = rand.nextInt(10) + 6;
                    int t2 = rand.nextInt(5) + 1;
                    int t3 = rand.nextInt(5) + 1;
                    
                    e.setUnloggedTime(t1 + t2 + t3);
                    
                    assertEquals(a.getLoggedTime(e, week), 0);
                    a.logTime(e, week, t1); // add time to week 0
                    assertEquals(a.getLoggedTime(e, week), t1);
                    
                    a.logTime(e, week + 1, t2); // add time to week 1
                    assertEquals(a.getLoggedTime(e, week + 1), t2);
                    
                    a.logTime(e, week, t3); // add more time to week 0
                    assertEquals(a.getLoggedTime(e, week), t1 + t3);
                    
                    assertEquals(e.getUnloggedTime(), 0);
                    
                    try{
                        
                        a.logTime(e, week, 1);
                        fail();
                        
                    }catch(IllegalArgumentException ex){
                    } // expected
                    
                    a.logTime(e, week, 0); // should work and do nothing
                    assertEquals(a.getLoggedTime(e, week), t1 + t3);
                    
                    a.unlogTime(e, week, t1); // remove t1, value of week 0 = t3
                    assertEquals(a.getLoggedTime(e, week), t3);
                    assertEquals(a.getLoggedTime(e, week + 1), t2);
                    
                    a.unlogTime(e, week + 1, t2); // remove t3, value of week 1 = 0
                    
                    try{
                        
                        a.unlogTime(e, week + 1, 10); // negative time not allowed
                        fail();
                        
                    }catch(IllegalArgumentException ex){
                    } // expected
                    
                }
            }
        }
    }
    
    @Test
    public void employeeLogsIllegalTime() throws Exception{
        for(Employee e : main.getEmployees().values()){
            for(Project p : main.getProjects().values()){
                for(Activity a : p.getActivities()){
                    try{
                        
                        a.logTime(e, Main.currentWeek(), -5);
                        fail();
                        
                    }catch(IllegalArgumentException ex){
                    }
                    
                    try{
                        
                        a.unlogTime(e, Main.currentWeek(), -5);
                        fail();
                        
                    }catch(IllegalArgumentException ex){
                    }
                }
            }
        }
    }
}