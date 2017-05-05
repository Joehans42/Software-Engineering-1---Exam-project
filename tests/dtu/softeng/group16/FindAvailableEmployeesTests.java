package dtu.softeng.group16;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

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
        
        Project project = new Project("TestProject", Main.currentWeek(), rasm);
        ArrayList<Activity> activities = project.getActivities();
        
        projects.put(project.getId(), project);
        
        // Set up a controlled scenario, where we know the time schedule of each employee for every week
        activities.add(new Activity("aktivitet1", 500, Main.currentWeek(), 2, kenn, joha));
        activities.add(new Activity("aktivitet2", 150, Main.currentWeek() + 2, 3, rasm, kenn));
        activities.add(new Activity("aktivitet3", 300, Main.currentWeek() + 1, 6, kenn, rasm));
        activities.add(new Activity("aktivitet4", 100, Main.currentWeek(), 4, joha));
        activities.add(new Activity("aktivitet5", 400, Main.currentWeek() + 3, 4, rasm, joha));
        
        //TODO: lav nogle static activities (ferie, kurser, mm.) 
        //TODO: og log noget tid på dem i de forskellige uger for forskellige medarbejdere
        
        //TODO: og tilpas datasættene så de virker
        
    }
    
    @Test
    public void findAvailableEmployees() throws Exception{ // Johannes
        
        // Tests for week 1 (Unsorted list)
        int week = Main.currentWeek();
        testWeek(week, "rasm", "kenn", "joha");
        // Week 4 (Sorted list)
        week += 3;
        testWeek(week, "kenn", "joha", "rasm");
        // Week 5 (Two with same time)
        week += 1;
        testWeek(week, "joha", "kenn", "rasm");
        
    }
    
    public void testWeek(int week, String mostTime, String nmostTime, String leastTime){ // Johannes
        
        LinkedHashMap<Employee, Integer> timeMap = main.getAvailableEmployees(week);
        
        for(Map.Entry<Employee, Integer> entry : timeMap.entrySet())
            System.out.println(entry.getKey().getUuid() + ": " + entry.getValue());
        
        System.out.println();
        
        Set<Employee> employees = main.getAvailableEmployees(week).keySet();
        Iterator<Employee> it = employees.iterator();
        
        // Test if the first employee in the list is the one, who we know should have most time available this week
        assertEquals("Employee with most time on week " + week + " must be " + mostTime, mostTime, it.next().getUuid());
        // Test if the second employee in the list is the one, who we know should have second most time available this week
        assertEquals("Employee with second most time on week " + week + " must be " + nmostTime, nmostTime, it.next().getUuid());
        // Same test as above for last employee
        assertEquals("Employee with least time on week " + week + " must be " + leastTime, leastTime, it.next().getUuid());
        
    }
}
