package dtu.softeng.group16;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kenny on 06/05/2017.
 */
public class UtilityTests{
    
    @Test // white
    public void testToWeek() throws Exception{ // Kenny
        
        LocalDate d = LocalDate.of(2017, 5, 6); // 06/05/2017
        int week = Main.toWeek(d);
        
        assertEquals(2470, week); // http://www.howlongagogo.com/date/1970/january/1 says it should be 2470
        
    }
    
    @Test // white
    public void testFormatWeek() throws Exception{ // Kenny
        
        LocalDate d = LocalDate.of(2017, 5, 10); // 08/05/2017
        int week = Main.toWeek(d);
        
        assertEquals("19, 2017", Main.formatWeek(week));
        
    }
    
    @Test // white, tests the Project.generateId() method by proxy
    public void testProjectId() throws Exception{ // Kenny
        
        Project.resetIdCounters();
        
        Project p1 = new Project("p1", Main.currentWeek(), null);
        Project p2 = new Project("p2", Main.currentWeek(), null);
        Project p3 = new Project("p3", Main.toWeek(LocalDate.of(2005, 5, 25)), null); // old project
        
        assertEquals("170001", p1.getId()); // test in 2017
        assertEquals("170002", p2.getId());
        
        assertEquals("050001", p3.getId()); // 2005 test
        
    }
}
