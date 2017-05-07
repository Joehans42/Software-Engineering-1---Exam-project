package dtu.softeng.group16;

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by Kenny on 07/05/2017.
 */
public class EmployeeTests{
    
    @Test(expected = IllegalArgumentException.class)
    public void illegalNameTest() throws Exception{
        
        Employee e = new Employee("dwaddfaw");
        fail();
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setUnloggedTimeTest() throws Exception{
        
        Employee e = new Employee("dwad");
        e.setUnloggedTime(-5);
        
        fail();
        
    }
}
