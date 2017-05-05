package dtu.softeng.group16;

import java.util.Random;

/**
 * Created by Kenny on 24/04/2017.
 * An extension of the SystemTest environment with pre-logged hours on some activities.
 */
public class LoggedSystemTest extends SystemTest{
    
    public void setUp() throws Exception{ // Kenny
        
        super.setUp(); // set up SystemTest
        Random rand = new Random();
        
        for(Employee e : main.getEmployees().values()){
            for(Project p : main.getProjects().values()){
                for(Activity a : p.getActivities()){
                    
                    int start = a.getStartWeek();
                    int duration = a.getDuration();
                    
                    for(int i = start; start + duration > i; i++){
                        if(a.getAssignees().contains(e)){ // assume assigned workers are more likely to log hours
                            
                            if(rand.nextInt(3) != 0) // wont always log time
                                continue;
                            
                            e.setUnloggedTime(rand.nextInt(5) + 5);
                            a.logTime(e, i, e.getUnloggedTime());
                            
                        }else{
                            
                            if(rand.nextInt(6) != 0) // less chance of logging time
                                continue;
                            
                            e.setUnloggedTime(rand.nextInt(3) + 2); // less time too
                            a.logTime(e, i, e.getUnloggedTime());
                        }
                    }
                }
            }
            
            for(StaticActivity sa : main.getStaticActivities()){
                
                if(rand.nextInt(5) != 0)
                    continue;
                
                int week = Main.currentWeek() + rand.nextInt(4) - 2;
                sa.logTime(e, week, rand.nextInt(5));
                
            }
        }
    }
}
