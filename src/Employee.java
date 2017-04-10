/**
 * Created by Kenny on 27-03-2017.
 * 
 */
public class Employee {
    
    private final String uuid;
    private int unloggedTime;
    
    public Employee(String uuid, int unloggedTime){
        
        this.uuid = uuid;
        this.unloggedTime = unloggedTime;
        
    }

    public int getUnloggedTime(){
        
        return unloggedTime;
        
    }

    public void setUnloggedTime(int unloggedTime){
        
        this.unloggedTime = unloggedTime;
        
    }

    public String getUuid(){
        
        return uuid;
        
    }
}
