


import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Check extends JFrame implements ActionListener {
    JButton check,back,show;
    JTextField usernameTextField,auteurTextField;
    JLabel tit,aut,image,user;
    JTable bookTable=new JTable();
    DefaultTableModel table;
    
    Check(){
        setLayout(null);
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("biblio.jpg"));
        Image i2=i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        image=new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);
        
        
        user= new JLabel("Username:");
        user.setFont(new Font("Raleway",Font.BOLD,17));
        user.setBounds(80,140,100,30);
        user.setForeground(Color.WHITE);
        image.add(user);
        
        usernameTextField = new JTextField();
        usernameTextField.setFont(new Font("Raleway",Font.BOLD,14));
        usernameTextField.setBounds(300,140,400,30);
        image.add(usernameTextField);
        
        check= new JButton("Check");
        check.setBounds(600,240,150,30);
        check.addActionListener(this);
        image.add(check);
        
        
        show= new JButton("Show books");
        show.setBounds(600,270,150,30);
        show.addActionListener(this);
        image.add(show);
        
        back= new JButton("Back");
        back.setBounds(600,800,150,30);
        back.addActionListener(this);
        image.add(back);
        
       
        getContentPane().setBackground(Color.WHITE);
        setSize(800,1600);
        setVisible(true);
        setLocation(350,200);

        bookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(150, 350, 500, 100);
        scrollPane.setViewportView(bookTable);
        add(scrollPane, BorderLayout.CENTER);
        
        
        
    }
    
    public void actionPerformed(ActionEvent e){
       if(e.getSource()== back){
            setVisible(false);
        }else if(e.getSource()== check){
            String username=usernameTextField.getText();
            DefaultTableModel table= (DefaultTableModel) bookTable.getModel();
            table.setRowCount(0);
            table.setColumnCount(0); 
            table.addColumn("ID du livre");
            table.addColumn("Titre du livre");
            table.addColumn("date d'emprunt");
            table.addColumn("date de retour prévue");//creation d'un tableau pour afficher les livres empruntés par l'utilisateur
            try{
               if(username.equals("")){
                   JOptionPane.showMessageDialog(null,"Username is required");
                }else{
                   Conn c = new Conn();
                   String q = "CALL get_user_borrowed_books('"+username+"')"; //appel de la procédure stockée pour récupérer les livres empruntés par l'utilisateur
                   ResultSet rs=c.s.executeQuery(q);
                   if(rs.next()){
                    do{
                        Object row[] ={
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                        };
                        table.addRow(row); 
                    }while(rs.next());
                   }else{
                    JOptionPane.showMessageDialog(null,"User doesn't exist or has no borrowed books ");
                   }
               }
            }catch(Exception ex){
               System.out.println(ex);
            }
        
        }else if(e.getSource()==show){
            DefaultTableModel table= (DefaultTableModel) bookTable.getModel();
            table.setRowCount(0);
            table.setColumnCount(0); 
            table.addColumn("ID");
            table.addColumn("Title");
            table.addColumn("Author");
            table.addColumn("Available");
            try{
                 Conn c=new Conn();
                 ResultSet rs=c.s.executeQuery("select id, nom, author,available from book"); //requete sql pour recuperer les livres
                 while(rs.next()){
                     Object row[] ={
                         rs.getInt("id"),
                         rs.getString("nom"),
                         rs.getString("author"),
                         rs.getInt("available")
                     };
                     table.addRow(row);
                    }
                 }catch(Exception ex){
                     System.out.println(ex);
                 }
               }
    }
    public static void main(String[] args) {
        new Check();
    }
    
    
}
