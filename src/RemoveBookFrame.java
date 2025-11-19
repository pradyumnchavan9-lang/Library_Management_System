import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RemoveBookFrame extends JFrame{
    
    JTextField idField;

    public RemoveBookFrame(){
        
        setTitle("Remove Book");
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Heading label
        JLabel heading=new JLabel("Remove Book");
        heading.setFont(new Font("Arial",Font.BOLD,22));
        heading.setBounds(120,20,200,30);
        add(heading);

        //id Label to enter id for book to be deleted
        JLabel idLabel=new JLabel("Delete");
        idLabel.setBounds(40,80,120,25);
        add(idLabel);

        //text field for the id ti be entered
        idField=new JTextField();
        idField.setBounds(140,80,200,20);
        add(idField);

        //creating a delete button
        JButton deleteBtn=new JButton("Delete");
        deleteBtn.setBounds(130, 150, 120, 35);
        add(deleteBtn);

        //Adding action Listnener to delete btn
        deleteBtn.addActionListener(e-> deleteBook());

        setVisible(true);
    }

    //function to deleteBook
    public void deleteBook(){

        
        //get the id to be deleted
        String idText=idField.getText();

        //if id is Empty show message
        if(idText.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Enter book by id");
            return ;
        }

        try {
            //idText is a string but id is an integer value so typeCaste
            int bookId=Integer.parseInt(idText);


            //Establish Connection with the Database
            Connection con=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/library_db",
                "root",
                "prady2718"
            );

            //Write SQL Query to be excuted
            String sql="DELETE FROM books WHERE book_id=?";

            //Prepare  Statement
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1,bookId);

            int rows=pst.executeUpdate();

            if(rows>0)
            {
                JOptionPane.showMessageDialog(this, "Book Deleted SuccessFully!!!");
            }
            else{
                JOptionPane.showMessageDialog(this, "Book ID not found");
            }

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }

    }

    public static void main(String[] args) {
        new RemoveBookFrame();
    }
}
