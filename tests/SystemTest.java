import org.junit.Before;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kenny on 12-04-2017.
 */
public class SystemTest{
    
    Main main;
    
    @Before
    public void setUp() throws Exception{
        
        main = new Main();
        
        HashMap<String, Employee> employees = main.getEmployees();
        HashMap<String, Project> projects = main.getProjects();
        ArrayList<StaticActivity> stactivities = main.getStaticActivities();
        
        // create employees, projects and activities
        Employee e1 = new Employee("kenn");
        Employee e2 = new Employee("joha");
        Employee e3 = new Employee("rasm");
        
        employees.put(e1.getUuid(), e1);
        employees.put(e2.getUuid(), e2);
        employees.put(e3.getUuid(), e3);
        
        // project 1 (ongoing project)
        
        Date p1start = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7));
        int p1week = Main.toWeek(p1start);
        
        Project p1 = new Project("Forårsrengøring", p1week, e1);
        ArrayList<Activity> p1a = p1.getActivities();
        
        p1a.add(new Activity("Fej gulvet", 1, p1week, 1, e1));
        p1a.add(new Activity("Møde", 2, p1week, 1, e1, e2, e3));
        p1a.add(new Activity("Mal kontoret", 20, p1week + 1, 2, e1, e2, e3));
        p1a.add(new Activity("Skaf nye stole", 5, p1week + 2, 3, e3));
        
        projects.put(p1.getId(), p1);
        
        // project 2 (old project, already completed)
        
        Date p2start = new SimpleDateFormat("dd/MM/yyyy").parse("03/03/2012");
        int p2week = Main.toWeek(p2start);
        
        Project p2 = new Project("Bil software", p2week, e2);
        ArrayList<Activity> p2a = p2.getActivities();
        
        p2a.add(new Activity("User stories", 6, p2week, 1, e1, e2, e3));
        p2a.add(new Activity("Use cases", 6, p2week, 1, e1, e2, e3));
        p2a.add(new Activity("Klassediagram", 4, p2week + 1, 2, e2));
        p2a.add(new Activity("Sekvensdiagrammer", 6, p2week + 2, 1, e3));
        p2a.add(new Activity("Skriv tests", 20, p2week + 2, 3, e1, e2, e3));
        
        projects.put(p2.getId(), p2);
        
        // project 3 (future project, not started, untitled, has no owner)
        
        Date p3start = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30));
        int p3week = Main.toWeek(p3start);
        
        Project p3 = new Project(null, p3week, null);
        ArrayList<Activity> p3a = p3.getActivities();
        
        p3a.add(new Activity("User stories", 6, p3week, 1, e1, e3));
        p3a.add(new Activity("Use cases", 6, p3week, 1, e1, e2));
        p3a.add(new Activity("Klassediagram", 4, p3week + 1, 2, e2));
        p3a.add(new Activity("Sekvensdiagrammer", 6, p3week + 2, 1, e3));
        p3a.add(new Activity("Skriv tests", 20, p3week + 2, 3, e1, e2, e3));
        
        projects.put(p3.getId(), p3);
        
        // TODO: create static activities
        StaticActivity sa1 = new StaticActivity("Ferie");
        
    }
}