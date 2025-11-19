import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewBooksFrame extends JFrame {
    
    JTextField searchField;
    JTable table;
    DefaultTableModel model;

    public ViewBooksFrame(){

        setTitle("View Books");
        setSize(600,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);


        //Give Tite to window
        JLabel title=new JLabel("View Books");
        title.setFont(new Font("Arial",Font.BOLD,24));
        title.setBounds(220,10,200,40);
        add(title);


        //Create Search Field
        searchField=new JTextField();
        searchField.setBounds(50,70,350,30);
        add(searchField);


        //Search Button
        JButton searchBtn=new JButton("Search");
        searchBtn.setBounds(420, 70, 100, 30);
        add(searchBtn);


        //Create Table
        model=new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Qty");


        table=new JTable(model);
        JScrollPane sp=new JScrollPane(table);
        sp.setBounds(50,120,470,300);
        add(sp);


        //Load All Books Initially
        loadBooks("");


        //When CLicking SearchButton
        searchBtn.addActionListener(e->{
            String keyword=searchField.getText().trim();
            loadBooks(keyword);
        });

        setVisible(true);
    }

    //----------------LOAD BOOKS FROM DB---------------------------
    public void loadBooks(String titleKeyword)
    {
        model.setRowCount(0);// Clear all rows

        try{
            Connection con=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/library_db",
                "root",
                "prady2718"
            );

            PreparedStatement pst;

            if(titleKeyword.equals("")){
                pst=con.prepareStatement("SELECT * FROM books");
            }
            else{
                pst=con.prepareStatement("SELECT * FROM books WHERE title LIKE ?");
                pst.setString(1,"%"+ titleKeyword + "%");
            }

            ResultSet rs=pst.executeQuery();

            while(rs.next())
            {
                model.addRow(new Object[]{
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("quantity"),
                });
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
