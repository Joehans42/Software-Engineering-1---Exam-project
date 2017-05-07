package dtu.softeng.group16;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kenny on 07/05/2017.
 */
public class ProjectTests{
    
    @Test
    public void managerTest() throws Exception{
        
        Employee e = new Employee("e");
        Employee e2 = new Employee("e2");
        
        Project p = new Project("p", Main.currentWeek(), null);
        Project p2 = new Project("p", Main.currentWeek(), e);
        
        p.setManager(e); // assign manager first time
        assertEquals(e, p.getManager());
        
        p2.setManager(e2); // reassign manager
        assertEquals(e2, p2.getManager());
        
    }
    
    @Test
    public void nameTest() throws Exception{
        
        Project p = new Project(null, Main.currentWeek(), null);
        Project p2 = new Project("p", Main.currentWeek(), null);
        
        p.setName("p");
        assertEquals("p", p.getName());
        
        p2.setName("p2");
        assertEquals("p2", p2.getName());
        
    }
}
