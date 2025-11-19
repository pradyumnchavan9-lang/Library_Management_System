import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class ReturnBookFrame extends JFrame{
    
    JComboBox<Integer> issueIdBox;
    JTextField studentField,bookIdField,issueDateField;
    JButton returnBtn;

    public ReturnBookFrame(){
        setTitle("Return Book");
        setSize(400,350);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(230,255,230));

        //Heading
        JLabel heading=new JLabel("Return Book");
        heading.setFont(new Font("Arial",Font.BOLD,22));
        heading.setBounds(120,20,300,30);
        heading.setForeground(new Color(0,120,0));
        add(heading);

        //Issue ID Label
        JLabel issueLabel=new JLabel("Issue Id");
        issueLabel.setBounds(40,70,120,25);
        issueLabel.setForeground(new Color(0,80,0));
        add(issueLabel);

        issueIdBox=new JComboBox<>();
        issueIdBox.setBounds(160,70,180,25);
        add(issueIdBox);

        //Student Name
        JLabel studentLabel=new JLabel("Student Name");
        studentLabel.setBounds(40,110,120,25);
        studentLabel.setForeground(new Color(0,80,0));
        add(studentLabel);

        studentField=new JTextField();
        studentField.setBounds(160,110,180,25);
        studentField.setEditable(false);
        add(studentField);

        //Book ID
        JLabel bookLabel=new JLabel("Book ID");
        bookLabel.setBounds(40,150,120,25);
        bookLabel.setForeground(new Color(0,80,0));
        add(bookLabel);

        bookIdField=new JTextField();
        bookIdField.setBounds(160, 150, 180, 25);
        bookIdField.setEditable(false);
        add(bookIdField);

        //Issue Date
        JLabel issueDateLabel = new JLabel("Issued Date:");
        issueDateLabel.setBounds(40, 190, 120, 25);
        issueDateLabel.setForeground(new Color(0, 80, 0));
        add(issueDateLabel);

        issueDateField = new JTextField();
        issueDateField.setBounds(160, 190, 180, 25);
        issueDateField.setEditable(false);
        add(issueDateField);

        //Return Button
        returnBtn=new JButton("Return");
        returnBtn.setBounds(120, 250, 150, 35);
        returnBtn.setBackground(new Color(0, 150, 0));
        returnBtn.setForeground(Color.WHITE);
        add(returnBtn);

        loadIssueIds();

        issueIdBox.addActionListener(e->fetchIssueDetails());

        returnBtn.addActionListener(e->returnBookToDB());

        setVisible(true);
    }
        //-------------LOAD ISSUE IDS (Get all books which are not returned returnDate==null)--------------
        public void loadIssueIds(){
            
                try {
                    //Establlish connnection
                    Connection con=DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/library_db",
                        "root",
                        "prady2718"
                    );

                    //Write SQL Query
                    String sql="SELECT id FROM issued_books WHERE return_Date is NULL";
                    //Prepared Statement
                    PreparedStatement pst=con.prepareStatement(sql);
                    //Result Set
                    ResultSet rs=pst.executeQuery();
                    
                    //Loop over rs to get all ids
                    while(rs.next())
                    {
                        issueIdBox.addItem(rs.getInt("id"));
                    }

                    //Close connection
                    con.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }   
        }
        //----Fetch Details(get name,book id and issue date of the selected id and display on th page)---

            public void fetchIssueDetails(){
                if(issueIdBox.getSelectedItem()==null) return;// return if nothing selected

                //get the issue id selected from the combo box
                int issueId=(int)issueIdBox.getSelectedItem();

                try {
                    Connection con=DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/library_db",
                        "root",
                        "prady2718"
                    );

                    String sql="SELECT student_name,book_id,issue_date FROM issued_books WHERE id=?";

                    PreparedStatement pst=con.prepareStatement(sql);
                    pst.setInt(1, issueId);

                    ResultSet rs=pst.executeQuery();

                    if(rs.next())
                    {
                        studentField.setText(rs.getString("student_name"));
                        bookIdField.setText(String.valueOf(rs.getInt("book_id")));
                        issueDateField.setText(rs.getString("issue_date"));
                    }

                    con.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            //----Return Book to DB(Remove row from issued_books and inceae the qeuantity of book by 1------
            public void returnBookToDB(){
                if(issueIdBox.getSelectedItem()==null)
                {
                    JOptionPane.showMessageDialog(this, "Select Issue ID first!");
                    return;
                }

                int issueId=(int)issueIdBox.getSelectedItem();
                int bookId=Integer.parseInt(bookIdField.getText());

                try {
                    Connection con=DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/library_db",
                        "root",
                        "prady2718"
                    );

                    //Update issue return date
                    String updateIssue="UPDATE issued_books SET return_date=CURDATE() where id=?";
                    PreparedStatement pst1=con.prepareStatement(updateIssue);
                    pst1.setInt(1,issueId);
                    pst1.executeUpdate();

                    //Increase Book Quantity
                    String updateBook="UPDATE books SET quantity=quantity+1 WHERE book_id=?";
                    PreparedStatement pst2 = con.prepareStatement(updateBook);
                    pst2.setInt(1, bookId);
                    pst2.executeUpdate();

                     JOptionPane.showMessageDialog(this, "Book Returned Successfully!");

                     con.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            public static void main(String[] args) {
                new RemoveBookFrame();
            }
}
