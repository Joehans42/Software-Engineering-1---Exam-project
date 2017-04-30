/**
 * Created by Kenny on 24/04/2017.
 * An extension of the SystemTest environment with pre-logged hours on some activities.
 */
public class LoggedSystemTest extends SystemTest{
    
    public void setUp() throws Exception{
        
        super.setUp(); // set up SystemTest
        
        for(Project p : main.getProjects().values()){
            for(Activity a : p.getActivities()){
                for(Employee e : main.getEmployees().values()){
                    
                    // for every employee, consider making an entry
                    
                    if(a.isAssigned(e)){ // assume assigned workers are more likely to log hours
                        
                        
                        
                    }else{
                        
                        
                        
                    }
                }
            }
        }
    }
}
