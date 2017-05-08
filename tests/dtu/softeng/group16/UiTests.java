package dtu.softeng.group16;

import dtu.softeng.group16.ui.SystemUI;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by REC on 05-May-17.
 */

public class UiTests{
    
    // A few tests for some of the basic ui commands. Tests are proof of concept and not for every command.
    
    @Test
    public void uiAddEmployeeTest(){ // Rasmus
        SystemUI ui = new SystemUI();
        
        Employee e = new Employee("rasm");
        assertTrue(!ui.getMain().getEmployees().containsValue(e));
        ui.executeCommand("add employee rasm");
        assertTrue(ui.getMain().getEmployees().containsValue(e));
    }
    
    public void uiBasicProjectTest(){ // Rasmus
        SystemUI ui = new SystemUI();
        
        assertTrue(ui.getMain().getProjects().isEmpty());
        
        // Creating new project to get id using generateId().
        Project p = new Project(null, Main.currentWeek() + 4, null);
        String id = "" + Integer.parseInt(p.getId()) + 1;
        
        // Should create new project with "id".
        ui.executeCommand("add project _ 4 _");
        // Looks for project with id "id" and checks start week, owner and name.
        assertTrue(ui.getMain().getProjects().get(id).getStartWeek() == Main.currentWeek() + 4);
        assertTrue(ui.getMain().getProjects().get(id).getName() == null);
        assertTrue(ui.getMain().getProjects().get(id).getManager() == null);
        
        // Makes and edit to project.
        ui.executeCommand("edit project name " + id + " User_stories");
        
        // Looks for project with id "id" and checks start week, owner and name.
        assertTrue(ui.getMain().getProjects().get(id).getStartWeek() == Main.currentWeek() + 4);
        assertTrue(ui.getMain().getProjects().get(id).getName() == "User_stories");
        assertTrue(ui.getMain().getProjects().get(id).getManager() == null);
        
        // Deletes project.
        ui.executeCommand("delete project " + id);
        
        // checks if the project was deleted
        assertTrue(ui.getMain().getProjects().isEmpty());
        
    }
}
