package dtu.softeng.group16.ui;

import dtu.softeng.group16.Activity;
import dtu.softeng.group16.Employee;
import dtu.softeng.group16.Main;
import dtu.softeng.group16.Project;

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
    
    private static String executeCommand(String input){ // Rasmus
        String[] c = input.split(" ");
        try{
            
            // Finds the correct command.
            if(c[0].equals("add") || c[0].equals("new")){
                
                if(c[1].equals("employee")){ // Command: add employee <uuid>
                    checkSize(c, 3);
                    checkNotEmpty(c[2]);
                    main.getEmployees().put(c[2], new Employee(c[2]));
                    return "Added new employee with uuid: " + c[2];
                }
                
                else if(c[1].equals("project")){ // Command: add project <name> <weeks from current week*> <owner>
                    checkSize(c, 5);
                    c[2] = setEmpty(c[2]);
                    checkNotEmpty(c[3]);
                    checkInt(c[3]);
                    c[4] = setEmpty(c[4]);
                    Project project = new Project(c[2], Main.currentWeek() + Integer.parseInt(c[3]), main.getEmployee(c[4]));
                    main.getProjects().put(project.getId(), project);
                    if(project.getManager() != null){
                        c[4] = project.getManager().getUuid();
                    }
                    return "Added new project \"" + project.getId() + "\":" +
                           "\n\tname:\t\t" + project.getName() +
                           "\n\tstart week:\t" + project.getStartWeek() +
                           "\n\towner:\t\t" + c[4];
                }
                
                else if(c[1].equals("activity")){ // Command: add activity <project id*> <name*> <budgetedTime*> <startWeek*> <duration*>
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
                    main.getProject(c[2]).addActivity(activity);
                    return "Added new activity \"" + activity.getName() + "\" in project " + c[2] + ":" +
                           "\n\tbudgeted time:\t" + activity.getBudgetedTime() +
                           "\n\tstart week:\t\t" + activity.getStartWeek() +
                           "\n\tduration:\t\t" + activity.getDuration();
                }
            }
            
            else if(c[0].equals("report")){
                
                if(c[1].equals("project")){ // Command: report project <id*> <week*>
                    checkNotEmpty(c[2]);
                    checkNotEmpty(c[3]);
                    checkInt(c[3]);
                    return main.getProject(c[2]).report(Main.currentWeek() + Integer.parseInt(c[3]));
                }
                
            }
            
        }catch(IllegalArgumentException e){
            return e.getMessage();
        }
        // If command is not recognized.
        return "\"" + input + "\" is not a valid command!";
    }
}
