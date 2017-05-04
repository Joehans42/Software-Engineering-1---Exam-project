package dtu.softeng.group16;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Kenny on 24-Apr-17.
 */
public class FindAvailableEmployeesTests{
    
    private Main main;
    
    @Before
    public void setUp() throws Exception{
        
        main = new Main();
        HashMap<String, Employee> employees = main.getEmployees();
        HashMap<String, Project> projects = main.getProjects();
        ArrayList<StaticActivity> stac = main.getStaticActivities();
    
        employees.put("kenn", new Employee("kenn"));
        employees.put("joha", new Employee("joha"));
        employees.put("rasm", new Employee("rasm"));
        
        
        
    }
    
    @Test
    public void findAvailableEmployees() throws Exception{
        
        int week = Main.currentWeek();
        Collection<Employee> employees = main.getAvailableEmployees(week);
        
        //TODO: hvordan tester vi at metoden returnerer de forventede værdier?
        
    }
}
