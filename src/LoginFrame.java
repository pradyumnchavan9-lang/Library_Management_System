import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class LoginFrame extends JFrame {
    
    public LoginFrame(){

        setTitle("Library Login");
        setSize(400,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        //Background Panel
        JPanel bg=new JPanel();
        bg.setBackground(new Color(30, 30, 30));
        bg.setBounds(0,0,400,500);
        bg.setLayout(null);

        //Login box panel
        JPanel box=new JPanel();
        box.setBackground(new Color(45, 45, 45));
        box.setBounds(50, 100, 300, 300);
        box.setLayout(null);

        //Title
        JLabel title=new JLabel("Login");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBounds(110, 20, 200, 40);
        box.add(title);

        //Username Label
        JLabel userLabel=new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(30, 80, 100, 25);
        box.add(userLabel);

        //Username Field
        JTextField username=new JTextField();
        username.setBounds(30,110,240,30);
        box.add(username);

        // Password Label
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(30, 150, 100, 25);
        box.add(passLabel);

        //Password Field
        JPasswordField password=new JPasswordField();
        password.setBounds(30,180,240,30);

        box.add(password);

        //Login Button
        JButton loginBtn=new JButton("Login");
        loginBtn.setBounds(90, 230, 120, 35);
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        box.add(loginBtn);

        //add box panel in bg panel
        bg.add(box);
        add(bg);
        
        setVisible(true);

        loginBtn.addActionListener(e->{
            
            //Get the username(user) and password(pass) from the TextFields
            String user=username.getText();
            String pass=new String(password.getPassword());

            try {
                
                //Load Driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                //Establish Connection
                Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library_db",
                    "root",
                    "prady2718"
                );

                //SQL query
                String sql="SELECT * FROM users WHERE username=? AND password=?";
                PreparedStatement pst=con.prepareStatement(sql);
                pst.setString(1, user);
                pst.setString(2, pass);

                //Execute
                ResultSet rs=pst.executeQuery();

                if(rs.next())
                {
                    JOptionPane.showMessageDialog(this, "Login Successfull!!!");

                    // TODO: open Dashboard
                    new DashboardFrame();

                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Invalid username or password");
                }
                con.close();

            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
