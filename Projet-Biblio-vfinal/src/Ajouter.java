
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Ajouter extends JFrame implements ActionListener {
    JButton modifier,back,show;
    JTextField titrenameTextField,auteurTextField,genreTextField;
    JLabel tit,aut,gen,image,books;
    JTable bookTable;
    DefaultTableModel table;
    
    Ajouter(){
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
        
        genreTextField = new JTextField();
        genreTextField.setFont(new Font("Raleway",Font.BOLD,14));
        genreTextField.setBounds(300,240,400,30);
        image.add(genreTextField);
        
        gen= new JLabel("Genre:");
        gen.setFont(new Font("Raleway",Font.BOLD,20));
        gen.setBounds(100,240,200,30);
        gen.setForeground(Color.WHITE);
        image.add(gen);
        
        
        modifier= new JButton("Add");
        modifier.setBounds(600,290,150,30);
        modifier.addActionListener(this);
        image.add(modifier);
        
        
        show= new JButton("Show books");
        show.setBounds(600,320,150,30);
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
        
        
        
    }
    public void actionPerformed(ActionEvent e){
       if(e.getSource()== back){
            setVisible(false);
            new PageAdmin("").setVisible(true);
        }else if(e.getSource()== modifier){
            String titre=titrenameTextField.getText();
            String auteur=auteurTextField.getText();
            String genre=genreTextField.getText();
            String status="1";
            try{
               if(titre.equals("")){
                   JOptionPane.showMessageDialog(null,"Title is required");
               }else{
                   Conn c = new Conn();
                   String q1 = "insert into book (nom,author,available,genre) values('"+titre+"','"+auteur+"','"+status+"','"+genre+"')"; 
                   c.s.executeUpdate(q1);      //requete d'insertion du livre dans la base de données
                   JOptionPane.showMessageDialog(null, "Book added successfully.");
               }
              
           }catch(Exception ex){
               System.out.println(ex);
           }
        
        }else if(e.getSource()==show){
            table= new DefaultTableModel();
            table.addColumn("ID");
            table.addColumn("Titre");
            table.addColumn("Author");
            table.addColumn("Genre");
            table.addColumn("Available");
            try{
                 Conn c=new Conn();
                 ResultSet rs=c.s.executeQuery("select id, nom, author,genre,available from book"); //requete sql pour recuperer les livres
                 while(rs.next()){
                     Object row[] ={
                         rs.getInt("id"),
                         rs.getString("nom"),
                         rs.getString("author"),
                         rs.getString("genre"),
                         rs.getInt("available")
                     };
                     table.addRow(row); //ajouter le livre dans le tableau
                    }
                 }catch(Exception ex){
                     System.out.println(ex);
                 }
                 bookTable = new JTable(table);
                 JScrollPane scrollPane = new JScrollPane(bookTable);
                 scrollPane.setBounds(150, 400, 500, 100);
                 add(scrollPane, BorderLayout.CENTER);
       
        }
    }
    public static void main(String[] args) {
        new Modifier();
    }
    
    
}
