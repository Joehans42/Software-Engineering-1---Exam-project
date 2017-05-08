package dtu.softeng.group16.ui;

import dtu.softeng.group16.*;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Rasmus on 04-05-2017.
 */
public class SystemUI{
    
    private static Main main = new Main();
    
    public static void main(String args[]){ // Rasmus
        main = new Main();
        Scanner consoleReader = new Scanner(System.in);
        String output;
        
        System.out.println("Type \"help\" to begin...");
        while(consoleReader.hasNext()){
            output = executeCommand(consoleReader.nextLine());
            System.out.println(output);
        }
    }
    
    public Main getMain(){
        return main;
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
    
    public static Employee getEmployee(String key){
        if(key == null){
            return null;
        }
        Employee employee = main.getEmployees().get(key);
        if(employee == null){
            throw new IllegalArgumentException("No employee with uuid: " + key);
        }
        return employee;
    }
    
    public static Project getProject(String key){ // Rasmus
        Project project = main.getProjects().get(key);
        if(project == null){
            throw new IllegalArgumentException("No project with id: " + key);
        }
        return project;
    }
    
    public static void deleteProject(String key){
        main.getProjects().remove(key);
    }
    
    public static void addStaticActivity(StaticActivity a){ // Rasmus
        
        HashSet<StaticActivity> activities = main.getStaticActivities();
        
        if(!activities.add(a)) // if an activity with same name already exists, error
            throw new IllegalArgumentException("Activity already exists: " + a.getName());
        
    }
    
    public static StaticActivity getStaticActivity(String activityName){ // Rasmus
        for(StaticActivity a : main.getStaticActivities()){
            if(a.getName().equals(activityName)){
                return a;
            }
        }
        throw new IllegalArgumentException("No static activity with name: " + activityName);
    }
    
    public static void addActivity(Project p, Activity activity){ // Rasmus
        
        HashSet<Activity> activities = p.getActivities();
        
        if(!activities.add(activity)) // if an activity with same name already exists, error
            throw new IllegalArgumentException("Static activity already exists: " + activity.getName());
        
    }
    
    public static Activity getActivity(String projectKey, String activityName){ // Rasmus
        for(Activity a : getProject(projectKey).getActivities()){
            if(a.getName().equals(activityName)){
                return a;
            }
        }
        throw new IllegalArgumentException("No activity with name: " + activityName);
    }
    
    public static void deleteActivity(String projectKey, String activityName){ // Rasmus
        getProject(projectKey).getActivities().remove(getActivity(projectKey, activityName));
    }
    
    public static String executeCommand(String input){ // Rasmus
        String[] c = input.split(" ");
        try{
            
            // Finds the correct command.
            if(c[0].equals("help")){
                return "An empty argument is given with \"_\". All arguments with a star \"*\" cannot be empty.\n" +
                       "activity = a, project = p, staticAcivity = sa\n" +
                       "Commands:\n" +
                       "\tadd employee <uuid*>\n" +
                       "\tadd project <name> <week*> <owner>\n" +
                       "\tadd activity <project_id*> <name*> <budgetedTime*> <startWeek*> <duration*>\n" +
                       "\tadd staticActivity <name*>\n" +
                       "\tedit project manager <id*> <uuid>\n" +
                       "\tedit project name <id*> <name>\n" +
                       "\tedit activity name <project_id*> <name*> <newName*>\n" +
                       "\tedit activity startWeek <project_id*> <name*> <startWeek*>\n" +
                       "\tedit activity budgetedTime <project_id*> <name*> <budgetedTime*>\n" +
                       "\tedit activity duration <project_id*> <name*> <duration*>\n" +
                       "\tedit activity employee <project_id*> <name*> <uuid*>\n" +
                       "\treport project <id*> <week*>\n" +
                       "\treport activity <project_id*> <name*> <week*>\n" +
                       "\tlog staticActivity <name*> <uuid*> <week*> <time*>\n" +
                       "\tlog <project_id*> <name*> <uuid*> <week*> <time*>\n" +
                       "\tunlog staticActivity <name*> <uuid*> <week*> <time*>\n" +
                       "\tunlog <project_id*> <name*> <uuid*> <week*> <time*>\n" +
                       "\tdelete project <id*>\n" +
                       "\tdelete activity <project_id*> <name*>";
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
                
                else if(c[1].equals("staticActivity") || c[1].equals("sa")){ // Command: add staticActivity <name*>
                    checkSize(c, 3);
                    checkNotEmpty(c[2]);
                    addStaticActivity(new StaticActivity(c[2]));
                    return "Added new static activity \"" + c[2] + "\".";
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
                    return getActivity(c[2], c[3]).report(Main.currentWeek() + Integer.parseInt(c[4]));
                }
                
                else if(c[1].equals("staticActivity") || c[1].equals("sa")){ // Command: report staticActivity <name*> <week*>
                    checkSize(c, 4);
                    checkNotEmpty(c[2]);
                    checkNotEmpty(c[3]);
                    checkInt(c[3]);
                    return getStaticActivity(c[2]).report(Integer.parseInt(c[3]));
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
                        getActivity(c[3], c[4]).setName(c[5]);
                        return "Name of activity \"" + c[4] + "\" chanced to \"" + c[5] + "\".";
                    }
                    
                    else if(c[2].equals("startWeek")){ // Command: edit activity startWeek <id*> <name*> <startWeek*>
                        checkSize(c, 6);
                        checkNotEmpty(c[3]);
                        checkNotEmpty(c[4]);
                        checkNotEmpty(c[5]);
                        checkInt(c[5]);
                        getActivity(c[3], c[4]).setStartWeek(Integer.parseInt(c[5]));
                        return "Start week of activity \"" + c[4] + "\" chanced to \"" + c[5] + "\".";
                    }
                    
                    else if(c[2].equals("budgetedTime")){ // Command: edit activity budgetedTime <id*> <name*> <budgetedTime*>
                        checkSize(c, 6);
                        checkNotEmpty(c[3]);
                        checkNotEmpty(c[4]);
                        checkNotEmpty(c[5]);
                        checkInt(c[5]);
                        getActivity(c[3], c[4]).setBudgetedTime(Integer.parseInt(c[5]));
                        return "Budgeted time of activity \"" + c[4] + "\" chanced to \"" + c[5] + "\".";
                    }
                    
                    else if(c[2].equals("duration")){ // Command: edit activity duration <id*> <name*> <duration*>
                        checkSize(c, 6);
                        checkNotEmpty(c[3]);
                        checkNotEmpty(c[4]);
                        checkNotEmpty(c[5]);
                        checkInt(c[5]);
                        getActivity(c[3], c[4]).setDuration(Integer.parseInt(c[5]));
                        return "Duration of activity \"" + c[4] + "\" chanced to \"" + c[5] + "\".";
                    }
                    
                    else if(c[2].equals("employee")){ // Command: edit activity employee <project_id*> <name*> <uuid*>
                        checkSize(c, 6);
                        checkNotEmpty(c[3]);
                        checkNotEmpty(c[4]);
                        checkNotEmpty(c[5]);
                        getActivity(c[3], c[4]).getAssignees().add(getEmployee(c[5]));
                        return "Employee add to activity" + c[4];
                    }
                    
                }
                
            }
            
            else if(c[0].equals("log")){
                
                if(c[1].equals("staticActivity") || c[1].equals("sa")){ // Command: log staticActivity <name*> <uuid*> <week*> <time*>
                    checkSize(c, 6);
                    checkNotEmpty(c[2]);
                    checkNotEmpty(c[3]);
                    checkNotEmpty(c[4]);
                    checkNotEmpty(c[5]);
                    checkInt(c[4]);
                    checkInt(c[5]);
                    getStaticActivity(c[2]).logTime(getEmployee(c[3]), Integer.parseInt(c[4]), Integer.parseInt(c[5]));
                    return "User \"" + c[3] + "\" has successfully logged time on static activity \"" + c[2] + "\".";
                }
                
                else{ // Command: log <id*> <name*> <uuid*> <week*> <time*>
                    checkSize(c, 6);
                    checkNotEmpty(c[1]);
                    checkNotEmpty(c[2]);
                    checkNotEmpty(c[3]);
                    checkNotEmpty(c[4]);
                    checkNotEmpty(c[5]);
                    checkInt(c[4]);
                    checkInt(c[5]);
                    getActivity(c[1], c[2]).logTime(getEmployee(c[3]), Integer.parseInt(c[4]), Integer.parseInt(c[5]));
                    return "User \"" + c[3] + "\" has successfully logged time on activity \"" + c[2] + "\".";
                }
                
            }
            
            else if(c[0].equals("unlog")){
                
                if(c[1].equals("staticActivity") || c[1].equals("sa")){ // Command: unlog staticActivity <name*> <uuid*> <week*> <time*>
                    checkSize(c, 6);
                    checkNotEmpty(c[2]);
                    checkNotEmpty(c[3]);
                    checkNotEmpty(c[4]);
                    checkNotEmpty(c[5]);
                    checkInt(c[4]);
                    checkInt(c[5]);
                    getStaticActivity(c[2]).unlogTime(getEmployee(c[3]), Integer.parseInt(c[4]), Integer.parseInt(c[5]));
                    return "User \"" + c[3] + "\" has successfully unlogged time on static activity \"" + c[2] + "\".";
                }
                
                else{ // Command: unlog <id*> <name*> <uuid*> <week*> <time*>
                    checkSize(c, 6);
                    checkNotEmpty(c[1]);
                    checkNotEmpty(c[2]);
                    checkNotEmpty(c[3]);
                    checkNotEmpty(c[4]);
                    checkNotEmpty(c[5]);
                    checkInt(c[4]);
                    checkInt(c[5]);
                    getActivity(c[1], c[2]).unlogTime(getEmployee(c[3]), Integer.parseInt(c[4]), Integer.parseInt(c[5]));
                    return "User \"" + c[3] + "\" has successfully unlogged time on activity \"" + c[2] + "\".";
                }
                
            }
            
            else if(c[0].equals("delete")){
                
                if(c[1].equals("activity") || c[1].equals("a")){ // Command: delete activity <id*> <name*>
                    checkSize(c, 4);
                    checkNotEmpty(c[2]);
                    checkNotEmpty(c[3]);
                    deleteActivity(c[2], c[3]);
                    return "Activty \"" + c[3] + "\" was deleted from project \"" + c[2] + "\".";
                }
                
                else if(c[1].equals("project") || c[1].equals("p")){ // Command: delete project <id*>
                    checkSize(c, 3);
                    checkNotEmpty(c[2]);
                    deleteProject(c[2]);
                    return "Project \"" + c[2] + "\" was deleted.";
                }
                
            }
            
        }catch(IllegalArgumentException e){
            return e.getMessage();
        }
        // If command is not recognized.
        return "\"" + input + "\" is not a valid command!";
    }
}
