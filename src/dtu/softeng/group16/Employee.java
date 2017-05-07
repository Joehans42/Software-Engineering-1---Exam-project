package dtu.softeng.group16;

/**
 * Created by Kenny on 27-03-2017.
 */
public class Employee{
    
    private final String uuid;
    private int unloggedTime = 0;
    
    public Employee(String uuid){
        
        if(uuid.length() > 4)
            throw new IllegalArgumentException("Employee uuid can only be less than or equal to 4!");
        
        this.uuid = uuid;
        
    }
    
    public int getUnloggedTime(){
        
        return unloggedTime;
        
    }
    
    public void setUnloggedTime(int unloggedTime){
        
        if(unloggedTime < 0)
            throw new IllegalArgumentException("Cannot set employee's unlogged time to negative time!");
        
        this.unloggedTime = unloggedTime;
        
    }
    
    public String getUuid(){
        
        return uuid;
        
    }
    
    // required for HashMap and HashSet in case of hash collision
    public boolean equals(Object o){ // Kenny
        
        if(this == o)
            return true;
        
        return o instanceof Employee && uuid.equals(((Employee) o).uuid);
    }
    
    // required for HashMap and HashSet
    public int hashCode(){ // Kenny
        
        return uuid.hashCode();
        
    }
}
