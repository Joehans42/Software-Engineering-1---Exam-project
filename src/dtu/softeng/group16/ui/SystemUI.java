package dtu.softeng.group16.ui;

import dtu.softeng.group16.Activity;
import dtu.softeng.group16.Employee;
import dtu.softeng.group16.Main;
import dtu.softeng.group16.Project;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Rasmus on 04-05-2017.
 */
public class SystemUI{
    
    private static Main main;
    
    public static void main(String args[]){
        main = new Main();
        Scanner consoleReader = new Scanner(System.in);
        String output;
    
        System.out.println("Type \"help\" to begin...");
        while(consoleReader.hasNext()){
            output = executeCommand(consoleReader.nextLine());
            System.out.println(output);
        }
    }
    
    private static void printStringArray(String[] strings){ // Rasmus
        for(String s : strings){
            System.out.println(s);
        }
    }
    
    private static void checkInt(String s) throws IllegalArgumentException{ // Rasmus
        if(!s.matches("\\d+")){
            throw new IllegalArgumentException("Argument is not an Integer");
        }
    }
    
    private static void checkSize(String[] A, int size) throws IllegalArgumentException{ // Rasmus
        if(A.length != size){
            throw new IllegalArgumentException("Illegal number of arguments for this command!");
        }
    }
    
    private static String setEmpty(String s){ // Rasmus
        if(s.equals("_")){
            return null;
        }
        else{
            return s;
        }
    }
    
    private static void checkNotEmpty(String s){ // Rasmus
        if(s.equals("_")){
            throw new IllegalArgumentException("A necessary argument is missing!");
        }
    }
    
    public static Employee getEmployee(String key) {
        if (key==null) {
            return null;
        }
        Employee employee = main.getEmployees().get(key);
        if (employee==null) {
            throw new IllegalArgumentException("No employee with uuid: " + key);
        }
        return employee;
    }
    
    public static Project getProject(String key) { // Rasmus
        Project project = main.getProjects().get(key);
        if (project==null) {
            throw new IllegalArgumentException("No project with id: " + key);
        }
        return project;
    }
    
    public static void addActivity(Project p, Activity activity){ // Rasmus
    
        HashSet<Activity> activities = p.getActivities();
        
        if(!activities.add(activity)) // if an activity with same name already exists, error
            throw new IllegalArgumentException("Activity already exists: " + activity.getName());
        
    }
    
    public static Activity getActivity(String projectKey, String activityName){ // Rasmus
        for(Activity a:getProject(projectKey).getActivities()) {
            if(a.getName().equals(activityName)) {
                return a;
            }
        }
        throw new IllegalArgumentException("No activity with name: " + activityName);
    }
    
    private static String executeCommand(String input){ // Rasmus
        String[] c = input.split(" ");
        try{
            
            // Finds the correct command.
            if(c[0].equals("help")) {
                return  "An empty argument is given with \"_\". All arguments with a star \"*\" cannot be empty.\n"+
                        "Commands:\n" + 
                        "\tadd project <name> <weeks from current week*> <owner>\n"+ 
                        "\tadd activity <project id*> <name*> <budgetedTime*> <startWeek*> <duration*>\n"+ 
                        "\tedit project manager <id*> <uuid>\n"+ 
                        "\tedit project name <id*> <name>\n"+ 
                        "\tedit activity name <id*> <name*> <newName*>\n"+ 
                        "\tedit activity startWeek <id*> <name*> <startWeek*>\n"+ 
                        "\tedit activity budgetedTime <id*> <name*> <budgetedTime*>\n"+ 
                        "\tedit activity duration <id*> <name*> <duration*>\n"+ 
                        "\treport project <id*> <week*>\n"+ 
                        "\treport activity <id*> <name*> <week*>";
            }
            
            else if(c[0].equals("add") || c[0].equals("new")){
                
                if(c[1].equals("employee") || c[1].equals("e")){ // Command: add employee <uuid>
                    checkSize(c, 3);
                    checkNotEmpty(c[2]);
                    main.getEmployees().put(c[2], new Employee(c[2]));
                    return "Added new employee with uuid: " + c[2];
                }
                
                else if(c[1].equals("project") || c[1].equals("p")){ // Command: add project <name> <weeks from current week*> <owner>
                    checkSize(c, 5);
                    c[2] = setEmpty(c[2]);
                    checkNotEmpty(c[3]);
                    checkInt(c[3]);
                    c[4] = setEmpty(c[4]);
                    Project project = new Project(c[2], Main.currentWeek() + Integer.parseInt(c[3]), getEmployee(c[4]));
                    main.getProjects().put(project.getId(), project);
                    if(project.getManager() != null){
                        c[4] = project.getManager().getUuid();
                    }
                    return "Added new project \"" + project.getId() + "\":" +
                           "\n\tname:\t\t" + project.getName() +
                           "\n\tstart week:\t" + Main.formatWeek(project.getStartWeek()) +
                           "\n\towner:\t\t" + c[4];
                }
                
                else if(c[1].equals("activity") || c[1].equals("a")){ // Command: add activity <project id*> <name*> <budgetedTime*> <startWeek*> <duration*>
                    checkSize(c, 7);
                    checkNotEmpty(c[2]);
                    checkNotEmpty(c[3]);
                    checkNotEmpty(c[4]);
                    checkNotEmpty(c[5]);
                    checkNotEmpty(c[6]);
                    checkInt(c[4]);
                    checkInt(c[5]);
                    checkInt(c[6]);
                    Activity activity = new Activity(c[3], Integer.parseInt(c[4]), Main.currentWeek() + Integer.parseInt(c[5]), Integer.parseInt(c[6]));
                    addActivity(getProject(c[2]), activity);
                    return "Added new activity \"" + activity.getName() + "\" in project " + c[2] + ":" +
                           "\n\tbudgeted time:\t" + activity.getBudgetedTime() +
                           "\n\tstart week:\t\t" + Main.formatWeek(activity.getStartWeek()) +
                           "\n\tduration:\t\t" + activity.getDuration();
                }
            }
            
            else if(c[0].equals("report") || c[0].equals("r")){
                
                if(c[1].equals("project") || c[1].equals("p")){ // Command: report project <id*> <week*>
                    checkSize(c, 4);
                    checkNotEmpty(c[2]);
                    checkNotEmpty(c[3]);
                    checkInt(c[3]);
                    return getProject(c[2]).report(Main.currentWeek() + Integer.parseInt(c[3]));
                }
                
                else if(c[1].equals("activity") || c[1].equals("a")){ // Command: report activity <id*> <name*> <week*>
                    checkSize(c, 5);
                    checkNotEmpty(c[2]);
                    checkNotEmpty(c[3]);
                    checkNotEmpty(c[4]);
                    checkInt(c[4]);
                    return getActivity(c[2],c[3]).report(Main.currentWeek() + Integer.parseInt(c[4]));
                }
                
            }
            
            else if(c[0].equals("edit")){
                
                if(c[1].equals("project") || c[1].equals("p")){
                    
                    if(c[2].equals("manager")){ // Command: edit project manager <id*> <uuid>
                        checkSize(c, 5);
                        checkNotEmpty(c[3]);
                        c[4] = setEmpty(c[4]);
                        getProject(c[3]).setManager(getEmployee(c[4]));
                        return "Manager chanced to " + c[4] + ".";
                    }
                    
                    else if(c[2].equals("name")){ // Command: edit project name <id*> <name>
                        checkSize(c, 5);
                        checkNotEmpty(c[3]);
                        c[4] = setEmpty(c[4]);
                        getProject(c[3]).setName(c[4]);
                        return "Name chanced to " + c[4] + ".";
                    }
                    
                }
                
                else if(c[1].equals("activity") || c[1].equals("a")){
                    
                    if(c[2].equals("name")){ // Command: edit activity name <id*> <name*> <newName*>
                        checkSize(c, 6);
                        checkNotEmpty(c[3]);
                        checkNotEmpty(c[4]);
                        checkNotEmpty(c[5]);
                        getActivity(c[3],c[4]).setName(c[5]);
                        return "Name of activity \"" + c[4] + "\" chanced to \"" + c[5] + "\".";
                    }
                    
                    else if(c[2].equals("startWeek")){ // Command: edit activity startWeek <id*> <name*> <startWeek*>
                        checkSize(c, 6);
                        checkNotEmpty(c[3]);
                        checkNotEmpty(c[4]);
                        checkNotEmpty(c[5]);
                        checkInt(c[5]);
                        getActivity(c[3],c[4]).setStartWeek(Integer.parseInt(c[5]));
                        return "Start week of activity \"" + c[4] + "\" chanced to \"" + c[5] + "\".";
                    }
                    
                    else if(c[2].equals("budgetedTime")){ // Command: edit activity budgetedTime <id*> <name*> <budgetedTime*>
                        checkSize(c, 6);
                        checkNotEmpty(c[3]);
                        checkNotEmpty(c[4]);
                        checkNotEmpty(c[5]);
                        checkInt(c[5]);
                        getActivity(c[3],c[4]).setBudgetedTime(Integer.parseInt(c[5]));
                        return "Budgeted time of activity \"" + c[4] + "\" chanced to \"" + c[5] + "\".";
                    }
                    
                    else if(c[2].equals("duration")){ // Command: edit activity duration <id*> <name*> <duration*>
                        checkSize(c, 6);
                        checkNotEmpty(c[3]);
                        checkNotEmpty(c[4]);
                        checkNotEmpty(c[5]);
                        checkInt(c[5]);
                        getActivity(c[3],c[4]).setDuration(Integer.parseInt(c[5]));
                        return "Duration of activity \"" + c[4] + "\" chanced to \"" + c[5] + "\".";
                    }
                    
                }
                
            }
            
        }catch(IllegalArgumentException e){
            return e.getMessage();
        }
        // If command is not recognized.
        return "\"" + input + "\" is not a valid command!";
    }
}
