package dtu.softeng.group16.ui;

import dtu.softeng.group16.Employee;
import dtu.softeng.group16.Main;
import dtu.softeng.group16.Project;

import java.util.Scanner;

/**
 * Created by Rasmus on 04-05-2017.
 */
public class SystemUI{
    
    static Main main;
    
    public static void main(String args[]){
        main = new Main();
        Scanner consoleReader = new Scanner(System.in);
        String output;
        
        while(consoleReader.hasNext()){
            output = executeCommand(consoleReader.nextLine());
            System.out.println(output);
        }
    }
    
    private static void printStringArray(String[] strings){
        for(String s : strings){
            System.out.println(s);
        }
    }
    
    private static String executeCommand(String input){
        String[] c = input.split(" ");
        try{
            
            // Finds the correct command.
            if(c[0].equals("add")){
                if(c[1].equals("employee")){ // Command: add employee <uuid>
                    checkSize(c,3);
                    main.getEmployees().put(c[2], new Employee(c[2]));
                    return "Added new employee with uuid: " + c[2];
                }
                
                else if(c[1].equals("project")){ // Command: add project <weeks from current week>
                    checkSize(c,3);
                    checkInt(c[2]);
                    Project project = new Project(null, Main.currentWeek() + Integer.parseInt(c[2]), null);
                    main.getProjects().put(project.getId(), project);
                    return "Added new project with id: " + project.getId();
                }
                
                else if(c[1].equals("activiy")) { // Command: add activity <project id> <name> <budgetedTime> <startWeek> <duration>
                    checkSize(c,7);
                    // TODO make dis.
                }
            }
            
            // If command is not recognized.
            else{
                return "\"" + input + "\" is not a valid command!";
            }
            
        }catch(IllegalArgumentException e){
            return e.getMessage();
        }
        return "";
    }
    
    private static void checkInt(String s) throws IllegalArgumentException {
        if(!s.matches("\\d+")){
            throw new IllegalArgumentException("Argument is not an Integer") ;
        }
    }
    
    private static void checkSize(String[] A, int size) throws IllegalArgumentException {
        if(A.length!=size) {
            throw new IllegalArgumentException("Illegal number of arguments for this command!");
        }
    }
}
