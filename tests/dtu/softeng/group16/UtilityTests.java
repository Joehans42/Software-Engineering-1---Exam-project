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
    
        assertEquals(2470, Main.toWeek(LocalDate.of(2017, 5, 1)));
        assertEquals(2470, Main.toWeek(LocalDate.of(2017, 5, 3)));
        assertEquals(2470, Main.toWeek(LocalDate.of(2017, 5, 7)));
        assertEquals(2471, Main.toWeek(LocalDate.of(2017, 5, 8)));
        
    }
    
    @Test // white
    public void testFormatWeek() throws Exception{ // Kenny
        
        assertEquals("18, 2017", Main.formatWeek(Main.toWeek(LocalDate.of(2017, 5, 1))));
        assertEquals("18, 2017", Main.formatWeek(Main.toWeek(LocalDate.of(2017, 5, 3))));
        assertEquals("18, 2017", Main.formatWeek(Main.toWeek(LocalDate.of(2017, 5, 7))));
        assertEquals("19, 2017", Main.formatWeek(Main.toWeek(LocalDate.of(2017, 5, 8))));
        
    }
    
    @Test // white, tests the Project.generateId() method by proxy
    public void testProjectId() throws Exception{ // Kenny
        
        Project.resetIdCounters();
        
        Project p1 = new Project(null, 2460, null);
        Project p2 = new Project(null, 2470, null);
        Project p3 = new Project(null, 1939, null);
        
        assertEquals("170001", p1.getId()); // test in 2017
        assertEquals("170002", p2.getId());
        assertEquals("070001", p3.getId());
        
    }
}
