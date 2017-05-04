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
            throw new IllegalArgumentException("Cannot set employee's unlogged time to zero!");
        
        this.unloggedTime = unloggedTime;
        
    }
    
    public String getUuid(){
        
        return uuid;
        
    }
    
    public boolean equals(Object o){
        
        if(this == o)
            return true;
        
        if(!(o instanceof Employee))
            return false;
        
        Employee employee = (Employee) o;
        
        if(unloggedTime != employee.unloggedTime)
            return false;
        
        return uuid.equals(employee.uuid);
    }
    
    @Override
    public int hashCode(){
        int result = uuid.hashCode();
        result = 31 * result + unloggedTime;
        return result;
    }
}
