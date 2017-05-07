package dtu.softeng.group16;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Kenny on 07/05/2017.
 * Disse tests er baseret på use case 'Opret/manage aktiviteter'
 */
public class ManActivityTests{

    @Test
    public void newActivityBasicTest() throws Exception{ // Johannes

        Employee e1 = new Employee("e1");
        Employee e2 = new Employee("e2");

        Activity a = new Activity("a", 400, Main.currentWeek(), 5, e1, e2);

        // Test if the activity has the parameters we gave it
        assertEquals("a",a.getName());
        assertEquals(400,a.getBudgetedTime());
        assertEquals(Main.currentWeek(),a.getStartWeek());
        assertEquals(5,a.getDuration());

        assertTrue(a.getAssignees().contains(e1));
        assertTrue(a.getAssignees().contains(e2));
    }

    @Test
    public void manActivityBasicTest() throws Exception{ // Johannes
        
        Employee e1 = new Employee("e1");
        Employee e2 = new Employee("e2");
        
        Activity a = new Activity("a", 400, Main.currentWeek(), 5, e1, e2);

        // Manage budgeted time
        a.setBudgetedTime(500);
        assertEquals(500,a.getBudgetedTime());

        // Manage name
        a.setName("testActivity");
        assertEquals("testActivity",a.getName());

        // Manage start week
        a.setStartWeek(5);
        assertEquals(5,a.getStartWeek());

        // Manage duration
        a.setDuration(3);
        assertEquals(3,a.getDuration());

        
    }
    
    @Test
    public void manActivityUniquenessTest() throws Exception{ // Kenny
        
        Employee e = new Employee("e");
        Activity a = new Activity("a", 400, Main.currentWeek(), 5, e, e); // added twice
        
        // the collection should have only unique values
        // count all occurrences of e in the collection, should be 1
        long count = a.getAssignees().stream().filter(e::equals).count();
        assertEquals(count, 1);
        
    }
}
