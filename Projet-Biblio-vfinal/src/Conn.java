import java.sql.*;

public class Conn {
    
    Connection c;
    Statement s;
    public Conn(){
        try{
            c= DriverManager.getConnection("jdbc:mysql://localhost:3306/database_name","database_username (example:root) ","database_password");
            s=c.createStatement();
            
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
}

