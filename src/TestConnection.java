import java.sql.*;

public class TestConnection{
    public static void main(String[] args)
    {
        String url="jdbc:mysql://localhost:3306/library_db";
        String user="root";
        String pass="prady2718";

        try{
            Connection conn=DriverManager.getConnection(url,user,pass);
            System.out.println("Connection Successfull!!!");
            conn.close();
        }catch(Exception e)
        {
            System.out.println("Error: " +e.getMessage());
        }
    }
}