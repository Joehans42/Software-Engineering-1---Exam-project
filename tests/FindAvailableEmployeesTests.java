import org.junit.Test;

import java.util.Collection;

/**
 * Created by Kenny on 24-Apr-17.
 */
public class FindAvailableEmployeesTests extends SystemTest{
    
    @Test
    public void findAvailableEmployees() throws Exception{
        
        int week = Main.currentWeek();
        Collection<Employee> employees = main.getAvailableEmployees(week);
        
        //TODO: hvordan tester vi at metoden returnerer de forventede værdier?
        
    }
}
