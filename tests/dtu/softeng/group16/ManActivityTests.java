package dtu.softeng.group16;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kenny on 07/05/2017.
 */
public class ManActivityTests{
    
    @Test
    public void manActivityBasicTest() throws Exception{
        
        Employee e1 = new Employee("e1");
        Employee e2 = new Employee("e2");
        
        Activity a = new Activity("a", 400, Main.currentWeek(), 5, e1, e2);
        
        assertTrue(a.getAssignees().contains(e1));
        assertTrue(a.getAssignees().contains(e2));
        
    }
    
    @Test
    public void manActivityUniquenessTest() throws Exception{
        
        Employee e = new Employee("e");
        Activity a = new Activity("a", 400, Main.currentWeek(), 5, e, e); // added twice
        
        // the collection should have only unique values
        // count all occurrences of e in the collection, should be 1
        long count = a.getAssignees().stream().filter(e::equals).count();
        assertEquals(count, 1);
        
    }
}
