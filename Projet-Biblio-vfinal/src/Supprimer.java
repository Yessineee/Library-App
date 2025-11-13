import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Supprimer extends JFrame implements ActionListener {
    JButton delete,back,show;
    JTextField titrenameTextField,auteurTextField,stTextField;
    JLabel tit,aut,av,image,books;
    JTable bookTable;
    DefaultTableModel table;
    Supprimer(){
        
        setLayout(null);
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("biblio.jpg"));
        Image i2=i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        image=new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);
        
        
        tit= new JLabel("Titre:");
        tit.setFont(new Font("Raleway",Font.BOLD,20));
        tit.setBounds(100,140,100,30);
        tit.setForeground(Color.WHITE);
        image.add(tit);
        
        titrenameTextField = new JTextField();
        titrenameTextField.setFont(new Font("Raleway",Font.BOLD,14));
        titrenameTextField.setBounds(300,140,400,30);
        image.add(titrenameTextField);
        
        aut= new JLabel("Auteur:");
        aut.setFont(new Font("Raleway",Font.BOLD,20));
        aut.setBounds(100,190,200,30);
        aut.setForeground(Color.WHITE);
        image.add(aut);
        
        auteurTextField = new JTextField();
        auteurTextField.setFont(new Font("Raleway",Font.BOLD,14));
        auteurTextField.setBounds(300,190,400,30);
        image.add(auteurTextField);
        
        delete= new JButton("Delete");
        delete.setBounds(600,240,150,30);
        delete.addActionListener(this);
        image.add(delete);
        
        
        show= new JButton("Show books");
        show.setBounds(600,270,150,30);
        show.addActionListener(this);
        image.add(show);
        
        back= new JButton("Back");
        back.setBounds(600,800,150,30);
        back.addActionListener(this);
        image.add(back);
        
        
//        String status=stTextField.getText();
        
        
        
//       JLabel ti=new JLabel("Titre");
//       ti.setBounds(180,240,400,200);
//       ti.setForeground(Color.WHITE);
//       ti.setFont(new Font("Raleway",Font.BOLD,20));
//       image.add(ti);
//       
//       JLabel au=new JLabel("Auteur");
//       au.setBounds(350,240,400,200);
//       au.setForeground(Color.WHITE);
//       au.setFont(new Font("Raleway",Font.BOLD,20));
//       image.add(au);
//       
//       JLabel av=new JLabel("Availability");
//       av.setBounds(500,240,400,200);
//       av.setForeground(Color.WHITE);
//       av.setFont(new Font("Raleway",Font.BOLD,20));
//       image.add(av);
//       
       
        
        getContentPane().setBackground(Color.WHITE);
        setSize(800,1600);
        setVisible(true);
        setLocation(350,200);
        
        
        
    }
    public void actionPerformed(ActionEvent e){
       if(e.getSource()== back){
            setVisible(false);
            new PageAdmin("").setVisible(true);
        }else if(e.getSource()== delete){
            String titre=titrenameTextField.getText();
            String auteur=auteurTextField.getText();
            try{
               if(titre.equals("")){
                   JOptionPane.showMessageDialog(null,"Title is required");
               }else{
                   Conn c = new Conn();
                   if(!c.s.executeQuery("select * from book where nom ='"+titre+"' and author = '"+auteur+"'").next()){ //verifier si le livre existe
                       JOptionPane.showMessageDialog(null,"book doesn't exist");
                    JOptionPane.showMessageDialog(null,"book doesn't exist");
                   }
                   else if(c.s.executeQuery("select * from book where nom = '"+titre+"' and author = '"+auteur+"' and available=0 ").next()){ //verifier si le livre est disponible
                    JOptionPane.showMessageDialog(null,"Ce livre est actuellement emprunté \n On ne peut pas le supprimer");
                   }else{
                    String q = "delete from book where nom= '"+titre+"' and author = '"+auteur+"'"; //requete de suppression du livre
                    c.s.executeUpdate(q);
                    JOptionPane.showMessageDialog(null,"book deleted successfully!");
                   }
               }
              
           }catch(Exception ex){
               System.out.println(ex);
           }
        
        }else if(e.getSource()==show){
            table= new DefaultTableModel();
            table.addColumn("ID");
            table.addColumn("Title");
            table.addColumn("Author");
            table.addColumn("Available");
            try{
                 Conn c=new Conn();
                 ResultSet rs=c.s.executeQuery("select id, nom, author,available from book where available=true"); //requete sql pour recuperer les livres
                 while(rs.next()){
                     Object row[] ={
                         rs.getInt("id"),
                         rs.getString("nom"),
                         rs.getString("author"),
                         rs.getInt("available")
                     };
                     table.addRow(row);  //ajouter le livre dans le tableau  
                }
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
        new Supprimer();
    }
    
    
}

