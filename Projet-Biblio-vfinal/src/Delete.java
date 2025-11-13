


import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Delete extends JFrame implements ActionListener {
    JButton check,back,show,delete;
    JTextField usernameTextField,auteurTextField;
    JLabel tit,aut,image,user;
    JTable bookTable,userTable;
    DefaultTableModel table;
    
    Delete(){
        setLayout(null);
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("biblio.jpg"));
        Image i2=i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        image=new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);
        
        
        user= new JLabel("Username:");
        user.setFont(new Font("Raleway",Font.BOLD,20));
        user.setBounds(100,140,100,30);
        user.setForeground(Color.WHITE);
        image.add(user);
        
        usernameTextField = new JTextField();
        usernameTextField.setFont(new Font("Raleway",Font.BOLD,12));
        usernameTextField.setBounds(300,140,400,30);
        image.add(usernameTextField);
        
        delete= new JButton("Delete");
        delete.setBounds(600,240,150,30);
        delete.addActionListener(this);
        image.add(delete);
        
        show= new JButton("Show");
        show.setBounds(600,290,150,30);
        show.addActionListener(this);
        image.add(show);
        
        back= new JButton("Back");
        back.setBounds(600,800,150,30);
        back.addActionListener(this);
        image.add(back);
        
       
        getContentPane().setBackground(Color.WHITE);
        setSize(800,1600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocation(350,200);
        
        
        
    }
    
    public void actionPerformed(ActionEvent e){
       if(e.getSource()== back){
            setVisible(false);
            new PageAdmin("").setVisible(true);
        }else if(e.getSource()== delete){
            String u=usernameTextField.getText();
            try{
               if(u.equals("")){
                   JOptionPane.showMessageDialog(null,"Username is required");
                }else if(u.length()>20){
                    JOptionPane.showMessageDialog(null,"Username can't be longer than 20c haracters");
                }
                else{
                   Conn c = new Conn();
                   if(!c.s.executeQuery("select * from usr where username ='"+u+"'").next()){
                    JOptionPane.showMessageDialog(null,"User doesn't exist");
                   }
                   else if(!c.s.executeQuery("select * from usr where username ='"+u+"' and nlivre =0").next()){
                    JOptionPane.showMessageDialog(null,"User a des livres a retourner \n On ne peut pas le supprimer");
                   }else{
                    String q = "delete from usr where username= '"+u+"'";
                    c.s.executeUpdate(q);
                    JOptionPane.showMessageDialog(null,"User deleted successfully!");
                   }
               }
            }catch(Exception ex){
               System.out.println(ex);
            }
            userTable = new JTable(table);
            JScrollPane scrollPane = new JScrollPane(userTable);
            scrollPane.setBounds(150, 350, 500, 100);
            add(scrollPane, BorderLayout.CENTER);
        
        }else if(e.getSource()==show){
            String titre=usernameTextField.getText();
            table= new DefaultTableModel();
            table.addColumn("Username");
            table.addColumn("Nom");
            table.addColumn("Prenom");
            table.addColumn("Password");
            table.addColumn("Fin de penalite");
            try{
                if(titre.equals("")){
                   JOptionPane.showMessageDialog(null,"Username is required");
                }else{
                 Conn c=new Conn();
                 ResultSet rs=c.s.executeQuery("select username, nom, prenom, mdp,finpenalty from usr where username= '"+titre+"'");
                 while(rs.next()){
                     Object row[] ={
                         rs.getString("username"),
                         rs.getString("nom"),
                         rs.getString("prenom"),
                         rs.getString("mdp"),
                         rs.getString("finpenalty")
                     };
                     table.addRow(row);}                 }
                 }catch(Exception ex){
                     System.out.println(ex);
                 }
                 bookTable = new JTable(table);
                 JScrollPane scrollPane = new JScrollPane(bookTable);
                 scrollPane.setBounds(150, 350, 500, 100);
                 add(scrollPane, BorderLayout.CENTER);
                }
    }
    public static void main(String[] args) {
        new Delete();
    }
    
    
}
