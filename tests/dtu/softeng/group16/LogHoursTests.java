package dtu.softeng.group16;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Kenny on 13-04-2017.
 * Disse tests er baseret paa use case 'registrerer arbejdstimer'
 * 
 * 04-05-2017: splittede stor test op i mindre tests //Kenny
 */
public class LogHoursTests extends SystemTest{
    
    @Test // white
    public void employeeLogsTimeInActivity() throws Exception{ // Kenny
        
        int week = Main.currentWeek();
        
        for(Employee e : main.getEmployees().values()){
            for(Project p : main.getProjects().values()){
                for(Activity a : p.getActivities()){
                    
                    int t1 = 11;
                    int t2 = 4;
                    int t3 = 7;
                    
                    e.setUnloggedTime(t1 + t2 + t3);
                    
                    // time logged before this test
                    int week0 = a.getLoggedTime(e, week);
                    int week1 = a.getLoggedTime(e, week+1);
                    
                    a.logTime(e, week, t1); // add time to week 0
                    
                    assertEquals(a.getLoggedTime(e, week), week0+t1);
                    assertEquals(a.getLoggedTime(e, week+1), week1);
                    
                    a.logTime(e, week + 1, t2); // add time to week 1
                    
                    assertEquals(a.getLoggedTime(e, week), week0+t1);
                    assertEquals(a.getLoggedTime(e, week + 1), week1+t2);
                    
                    a.logTime(e, week, t3); // add more time to week 0
                    assertEquals(a.getLoggedTime(e, week), week0 + t1 + t3);
                    assertEquals(a.getLoggedTime(e, week + 1), week1+t2);
                    
                    // should have used up all unlogged time now
                    assertEquals(e.getUnloggedTime(), 0);
                    
                    // now unlog some time
                    
                    a.unlogTime(e, week, t1); // remove t1, value of week 0 = t3
                    assertEquals(a.getLoggedTime(e, week), week0 + t3);
                    assertEquals(a.getLoggedTime(e, week + 1), week1+t2);
                    
                    a.unlogTime(e, week, t3); // remove t3, value of week 1 = 0
                    assertEquals(a.getLoggedTime(e, week), week0);
                    assertEquals(a.getLoggedTime(e, week + 1), week1+t2);
                    
                }
            }
        }
    }
    
    @Test // white
    public void employeeLogsZeroHours() throws Exception{ // Kenny
        
        int week = Main.currentWeek();
        
        for(Employee e : main.getEmployees().values()){
            for(Project p : main.getProjects().values()){
                for(Activity a : p.getActivities()){
                    
                    int time = a.getLoggedTime(e, week);
                    a.logTime(e, week, 0); // should work and do nothing
                    assertEquals(a.getLoggedTime(e, week), time);
                    
                }
            }
        }
    }
    
    @Test // black
    public void employeeHasNoUnloggedHours() throws Exception{ // Kenny
        
        int week = Main.currentWeek();
        
        for(Employee e : main.getEmployees().values()){
            for(Project p : main.getProjects().values()){
                for(Activity a : p.getActivities()){
                    try{
                        
                        e.setUnloggedTime(0);
                        
                        // employee has no more unlogged time, should fail
                        a.logTime(e, week, 1);
                        fail();
                        
                    }catch(IllegalArgumentException ex){
                    } // expected
                }
            }
        }
    }
    
    @Test // black
    public void employeeUnlogsTooMuch() throws Exception{ // Kenny
        
        int week = Main.currentWeek();
        
        for(Employee e : main.getEmployees().values()){
            for(Project p : main.getProjects().values()){
                for(Activity a : p.getActivities()){
                    try{
                        
                        int time = a.getLoggedTime(e, week);
                        a.unlogTime(e, week, time + 1); // negative time not allowed
                        fail();
                        
                    }catch(IllegalArgumentException ex){
                    } // expected
                }
            }
        }
    }
    
    @Test // black
    public void employeeLogsIllegalTime() throws Exception{ // Kenny
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