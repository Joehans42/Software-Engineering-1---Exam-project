package dtu.softeng.group16;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * Created by Kenny on 07/05/2017.
 */
public class ActivityTests{
    
    @Test(expected = IllegalArgumentException.class)
    public void illegalBudgetedTimeActivityTest() throws Exception{
        
        new Activity("a", -5, Main.currentWeek(), 5);
        fail();
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void illegalDurationTimeActivityTest() throws Exception{
        
        new Activity("a", 5, Main.currentWeek(), 0);
        fail();
        
    }
    
    @Test
    public void equalsTest() throws Exception{
        
        Activity a1 = new Activity("a1", 5, Main.currentWeek(), 5);
        Activity a2 = new Activity("a2", 5, Main.currentWeek(), 5);
        Activity a3 = new Activity("a1", 5, Main.currentWeek(), 5);
        
        assertNotEquals(a1, a2);
        assertNotEquals(a2, a3);
        
        assertNotEquals(a1, null);
        assertNotEquals(a2, null);
        assertNotEquals(a3, null);
        
        assertEquals(a1, a1);
        assertEquals(a1, a3);
        
    }
}
