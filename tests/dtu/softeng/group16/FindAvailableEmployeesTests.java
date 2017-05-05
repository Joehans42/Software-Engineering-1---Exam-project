package dtu.softeng.group16;

import com.sun.org.apache.xpath.internal.operations.Equals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kenny on 24-Apr-17.
 */
public class FindAvailableEmployeesTests{
    
    private Main main;

    @Before
    public void setUp() throws Exception{ //Johannes
        
        main = new Main();
        HashMap<String, Employee> employees = main.getEmployees();
        HashMap<String, Project> projects = main.getProjects();
        ArrayList<StaticActivity> stac = main.getStaticActivities();

        Employee kenn = new Employee("kenn");
        Employee joha = new Employee("joha");
        Employee rasm = new Employee("rasm");

        employees.put("kenn", kenn);
        employees.put("joha", joha);
        employees.put("rasm", rasm);

        Project project = new Project("TestProject",Main.currentWeek(),rasm);
        main.getProjects().put(project.getId(),project);

        // Set up a controlled scenario, where we know the time schedule of each employee for every week
        project.getActivities().add(new Activity("aktivitet1",500,Main.currentWeek(),2,kenn,joha));
        project.getActivities().add(new Activity("aktivitet2",150,Main.currentWeek()+2,3,rasm,kenn));
        project.getActivities().add(new Activity("aktivitet3",300,Main.currentWeek()+1,6,kenn,rasm));
        project.getActivities().add(new Activity("aktivitet4",100,Main.currentWeek(),4,joha));
        project.getActivities().add(new Activity("aktivitet5",400,Main.currentWeek()+3,4,rasm,joha));
    }
    
    @Test
    public void findAvailableEmployees() throws Exception{ // Johannes

        // Tests for week 1 (Unsorted list)
        int week = Main.currentWeek();
        testWeek(week,"rasm","kenn","joha");
        // Week 4 (Sorted list)
        week+=3;
        testWeek(week,"kenn","joha","rasm");
        // Week 5 (Two with same time)
        week+=1;
        testWeek(week,"joha","kenn","rasm");

    }

    public void testWeek(int week,String mostTime,String nmostTime,String leastTime){ // Johannes

        ArrayList<Employee> employees = main.getAvailableEmployees(week);

        // Test if the first employee in the list is the one, who we know should have most time available this week
        assertEquals("Employee with most time on week " + week + " must be " + mostTime,mostTime,employees.get(0).getUuid());
        // Test if the second employee in the list is the one, who we know should have second most time available this week
        assertEquals("Employee with second most time on week " + week + " must be " + nmostTime,nmostTime,employees.get(1).getUuid());
        // Same test as above for last employee
        assertEquals("Employee with least time on week " + week + " must be " + leastTime,leastTime,employees.get(2).getUuid());
    }

}
