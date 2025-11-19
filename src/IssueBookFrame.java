import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class IssueBookFrame extends JFrame{
        
    JTextField studentField, bookIdField;
    JLabel dateLabel;

    public IssueBookFrame(){

        setTitle("Issue Book");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(null); // 🔥 REQUIRED
        getContentPane().setBackground(new Color(230,255,230)); // background

        //Add Heading
        JLabel heading=new JLabel("Issue Book");
        heading.setFont(new Font("Arial",Font.BOLD,22));
        heading.setBounds(120,20,200,30);
        heading.setForeground(new Color(0,120,0)); // dark green
        add(heading);

        //Student Name
        JLabel studentLabel=new JLabel("Student Name");
        studentLabel.setBounds(40,80,120,25);
        studentLabel.setForeground(new Color(0,80,0));
        add(studentLabel);

        //Student Field
        studentField=new JTextField();
        studentField.setBounds(160,80,180,25);
        studentField.setBackground(Color.WHITE);
        studentField.setForeground(new Color(0,60,0));
        add(studentField);

        //Book ID
        JLabel bookIdLabel=new JLabel("Book Id");
        bookIdLabel.setBounds(40,120,120,25);
        bookIdLabel.setForeground(new Color(0,80,0));
        add(bookIdLabel);

        //Book ID Field
        bookIdField = new JTextField();
        bookIdField.setBounds(160, 120, 180, 25);
        bookIdField.setBackground(Color.WHITE);
        bookIdField.setForeground(new Color(0,60,0));
        add(bookIdField);

        //Issue Date Label
        JLabel issDateLabel=new JLabel("Issue Date");
        issDateLabel.setBounds(40, 160, 120, 25);
        issDateLabel.setForeground(new Color(0,80,0));
        add(issDateLabel);

        // Today's Date
        dateLabel=new JLabel(LocalDate.now().toString());
        dateLabel.setBounds(160,160,180,25);
        dateLabel.setForeground(new Color(0,100,0)); // green-ish
        add(dateLabel);

        //Issue Button
        JButton issueBtn=new JButton("Issue Book");
        issueBtn.setBounds(130, 220, 130, 35);
        issueBtn.setBackground(new Color(0,150,0)); // green button
        issueBtn.setForeground(Color.WHITE);
        add(issueBtn);

        issueBtn.addActionListener(e -> issueBookToDB());

        setVisible(true);
    }

    //--------------------------- JDBC INSERT OPERATION ---------------------------------
    public void issueBookToDB(){

        String student = studentField.getText();
        String bookIdText = bookIdField.getText();
        String issueDate = dateLabel.getText();

        if(student.isEmpty() || bookIdText.isEmpty() || issueDate.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "All Fields are Required!!!");
            return;
        }

        try {
            int bookId=Integer.parseInt(bookIdText);

            //Establish Connection
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library_db",
                    "root",
                    "prady2718"
            );

            //Step1 Check Quantity
            String checkQty="SELECT quantity FROM books WHERE book_id=?";
            PreparedStatement pstCheck=con.prepareStatement(checkQty);
            pstCheck.setInt(1, bookId);

            ResultSet rs=pstCheck.executeQuery();
            if(rs.next())
            {
                int qty=rs.getInt("quantity");
                if(qty<=0)
                {
                    JOptionPane.showMessageDialog(this,
                    "Book Not Available! Quantity is 0.");
                con.close();
                return;
                }
            }else{
                JOptionPane.showMessageDialog(this, "Invalid Book ID!");
                con.close();
                return;
            }

            //Step 2 Insert into issued books
            String sql="INSERT INTO issued_books(student_name,book_id,issue_date) VALUES(?,?,?)";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1, student);
            pst.setInt(2, bookId);
            pst.setString(3,issueDate);
            pst.executeUpdate();

            //Step 3 Reduce Quantity by 1
            String updateQty="UPDATE books SET quantity=quantity-1 WHERE book_id=?";
            PreparedStatement pst2=con.prepareStatement(updateQty);
            pst2.setInt(1, bookId);
            pst2.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book Issued Successfully!");

            con.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }


    public static void main(String[] args) {
        new IssueBookFrame();
    }
}
