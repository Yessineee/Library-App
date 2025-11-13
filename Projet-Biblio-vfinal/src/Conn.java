import java.sql.*;

public class Conn {
    
    Connection c;
    Statement s;
    public Conn(){
        try{
            String dbURL = "jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME";
            String dbUsername = "YOUR_USERNAME";
            String dbPassword = "YOUR_PASSWORD";
            c= DriverManager.getConnection(dbURL,dbUsername,dbPassword);
            s=c.createStatement();
            
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
}


