import javax.swing.*;
import java.awt.*;
import java.sql.*;
public class AddBookFrame extends JFrame{
    
    JTextField titleField,authorField,qtyField;

    public AddBookFrame(){

        setTitle("Add Book");
        setSize(400,430);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel heading=new JLabel("Add Book");
        heading.setFont(new Font("Arial",Font.BOLD,22));
        heading.setBounds(120,20,200,30);
        add(heading);

        JLabel titleLabel=new JLabel("Title");
        titleLabel.setBounds(40,80,120,25);
        add(titleLabel);

        titleField=new JTextField();
        titleField.setBounds(140,80,200,25);
        add(titleField);

        JLabel authorLabel=new JLabel("Author");
        authorLabel.setBounds(40,120,120,25);
        add(authorLabel);

        authorField = new JTextField();
        authorField.setBounds(140, 120, 200, 25);
        add(authorField);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setBounds(40, 160, 120, 25);
        add(qtyLabel);

        qtyField = new JTextField();
        qtyField.setBounds(140, 160, 200, 25);
        add(qtyField);

        JButton addBtn = new JButton("Add Book");
        addBtn.setBounds(130, 220, 120, 35);
        add(addBtn);

        addBtn.addActionListener(e -> addBookToDB());

        setVisible(true);
        
    }

    //-----------------JDBC INSERT FUNCTION------------------------
    private void addBookToDB(){
        String title=titleField.getText();
        String author=authorField.getText();
        String qty=qtyField.getText();

        if(title.isEmpty() || author.isEmpty() || qty.isEmpty())
        {
            JOptionPane.showMessageDialog(this,"All Fields are Required!!!");
            return;
        }
        try {
            int quantity=Integer.parseInt(qty);

            //Establish Connection
            Connection con=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/library_db",
                "root",
                "prady2718"
            );

            //Write the Query
            String q="INSERT INTO books(title,author,quantity) VALUES(?,?,?)";

            PreparedStatement ps=con.prepareStatement(q);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, quantity);
            
            //Execute the Query
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book Added SuccessFully!!!");

            //Close the connection
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new AddBookFrame();
    }
}
