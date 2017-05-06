package dtu.softeng.group16;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kenny on 06/05/2017.
 */
public class UtilityTests{
    
    @Test // white
    public void testToWeek() throws Exception{ // Kenny
    
        System.out.println(DateTimeFormatter.ofPattern("ww, yyyy").format(LocalDate.now()));
        
        assertEquals(2470, Main.toWeek(LocalDate.of(2017, 5, 1)));
        assertEquals(2470, Main.toWeek(LocalDate.of(2017, 5, 3)));
        assertEquals(2470, Main.toWeek(LocalDate.of(2017, 5, 7)));
        assertEquals(2471, Main.toWeek(LocalDate.of(2017, 5, 8)));
        
    }
    
    @Test // white
    public void testFormatWeek() throws Exception{ // Kenny
        
        assertEquals("18, 2017", Main.formatWeek(Main.toWeek(LocalDate.of(2017, 5, 7))));
        assertEquals("19, 2017", Main.formatWeek(Main.toWeek(LocalDate.of(2017, 5, 8))));
        assertEquals("19, 2017", Main.formatWeek(Main.toWeek(LocalDate.of(2017, 5, 14))));
        assertEquals("20, 2017", Main.formatWeek(Main.toWeek(LocalDate.of(2017, 5, 15))));
        
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
