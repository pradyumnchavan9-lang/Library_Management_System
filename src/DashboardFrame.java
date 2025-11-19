import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class DashboardFrame extends JFrame {
    
    public  DashboardFrame(){
        setTitle("Library Dashboard");
        setSize(600,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        //BackGround Panel
        JPanel bg=new JPanel();
        bg.setBackground(new Color(30,30,30));
        bg.setBounds(0,0,600,500);
        bg.setLayout(null);

        //Title
        JLabel title=new JLabel("Library Management Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(120,30,400,30);
        bg.add(title);

        //Button Style
        Font btnFont = new Font("Arial", Font.BOLD, 16);
        Color btnColor = new Color(70, 130, 180);

        //Add Book Btn
        JButton addBookBtn=new JButton("Add Book");
        addBookBtn.setBounds(80,100,180,50);
        addBookBtn.setFont(btnFont);
        addBookBtn.setBackground(btnColor);
        addBookBtn.setForeground(Color.WHITE);
        bg.add(addBookBtn);

        //Remove Book Btn
        JButton removeBookButton=new JButton("Remove Book");
        removeBookButton.setBounds(330,100,180,50);
        removeBookButton.setFont(btnFont);
        removeBookButton.setBackground(btnColor);
        removeBookButton.setForeground(Color.WHITE);
        bg.add(removeBookButton);

        // Issue Book
        JButton issueBookBtn = new JButton("Issue Book");
        issueBookBtn.setBounds(80, 180, 180, 50);
        issueBookBtn.setFont(btnFont);
        issueBookBtn.setBackground(btnColor);
        issueBookBtn.setForeground(Color.WHITE);
        bg.add(issueBookBtn);

        // Return Book
        JButton returnBookBtn = new JButton("Return Book");
        returnBookBtn.setBounds(330, 180, 180, 50);
        returnBookBtn.setFont(btnFont);
        returnBookBtn.setBackground(btnColor);
        returnBookBtn.setForeground(Color.WHITE);
        bg.add(returnBookBtn);

        // View Books
        JButton viewBooksBtn = new JButton("View Books");
        viewBooksBtn.setBounds(80, 260, 180, 50);
        viewBooksBtn.setFont(btnFont);
        viewBooksBtn.setBackground(btnColor);
        viewBooksBtn.setForeground(Color.WHITE);
        bg.add(viewBooksBtn);

        // View Students
        JButton viewStudentsBtn = new JButton("View Students");
        viewStudentsBtn.setBounds(330, 260, 180, 50);
        viewStudentsBtn.setFont(btnFont);
        viewStudentsBtn.setBackground(btnColor);
        viewStudentsBtn.setForeground(Color.WHITE);
        bg.add(viewStudentsBtn);

        //Logout Button
        JButton logoutBtn=new JButton("Logout");
        logoutBtn.setBounds(220, 350, 150, 45);
        logoutBtn.setFont(btnFont);
        logoutBtn.setBackground(new Color(200, 50, 50));
        logoutBtn.setForeground(Color.WHITE);
        bg.add(logoutBtn);

        //Logout btn
        logoutBtn.addActionListener(e->{
            new LoginFrame();
            dispose();
        });

        //Add book btn
        addBookBtn.addActionListener(e->{
            new AddBookFrame();
            
        });

        //View book btn
        viewBooksBtn.addActionListener(e->{
                new ViewBooksFrame();
            });

        //Remove book btn
        removeBookButton.addActionListener(e->{
            new RemoveBookFrame();
        });

        //Issue Book
        issueBookBtn.addActionListener(e->{
            new IssueBookFrame();
            dispose();
        });

        //Return Book
        returnBookBtn.addActionListener(e->{
            new ReturnBookFrame();
            dispose();
        });
        add(bg);

        setVisible(true);
    }
    public static void main(String[] args) {
        new DashboardFrame();
    }
}
