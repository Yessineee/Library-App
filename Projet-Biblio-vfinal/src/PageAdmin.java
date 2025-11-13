

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;


public class PageAdmin extends JFrame implements ActionListener {
    JButton ajout,modif,suppr,exit,show,check,delete;
    String usern;
    JLabel books,id,titre,auteur,av,image;
    JTable bookTable=new JTable();
    DefaultTableModel table;

    public PageAdmin(String usern){
       this.usern=usern;
       setTitle("Application de gestion des prêts d'ouvrages dans une bibliothèque");
       setLayout(null);
//       
//       ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("biblio.jpg"));
//       Image i2=i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
//       ImageIcon i3=new ImageIcon(i2);
//       image=new JLabel(i3);
//       image.setBounds(0,0,900,900);
//       add(image);
       
       JLabel text=new JLabel("Bienvenue");
       text.setFont(new Font("Osward",Font.BOLD,38));
       text.setBounds(290,50,400,40);
       text.setForeground(Color.WHITE);
       add(text);
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
       
       ajout= new JButton("Ajouter");
       ajout.setBounds(50,150,150,30);
       ajout.addActionListener(this);
       add(ajout);
       
       modif= new JButton("Modifier");
       modif.setBounds(300,150,150,30);
       modif.addActionListener(this);
       add(modif);
       
       suppr= new JButton("Supprimer");
       suppr.setBounds(550,150,150,30);
       suppr.addActionListener(this);
       add(suppr);
       
       show= new JButton("Show books");
       show.setBounds(150,200,470,30);
       show.addActionListener(this);
       add(show);
       
       check= new JButton("Check user historique");
       check.setBounds(150,250,470,30);
       check.addActionListener(this);
       add(check);
       
       delete= new JButton("Delete user");
       delete.setBounds(150,300,470,30);
       delete.addActionListener(this);
       add(delete);
       
       
       
       exit= new JButton("Exit");
       exit.setBounds(600,800,150,30);
       exit.addActionListener(this);
       add(exit);
       
       
       
       getContentPane().setBackground(Color.WHITE);
       setSize(800,900);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setVisible(true);
       setLocation(350,200);
    }
   
    public void actionPerformed(ActionEvent e){
       if(e.getSource()== exit){
            System.exit(0);
        }else if(e.getSource()==ajout){
            new Ajouter().setVisible(true);
        }else if(e.getSource()==modif){
            new Modifier().setVisible(true);
        }else if(e.getSource()==suppr){
            new Supprimer().setVisible(true);
        }else if(e.getSource()==check){
            new Check().setVisible(true);
        }else if(e.getSource()==delete){
            setVisible(false);
            new Delete().setVisible(true);
        }else if(e.getSource()==show){
            DefaultTableModel table= (DefaultTableModel) bookTable.getModel();
            table.setRowCount(0);
            table.setColumnCount(0);
            table.addColumn("ID");
            table.addColumn("Title");
            table.addColumn("Author");
            table.addColumn("Available");        //creer un tableau pourt afficher les livres
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
                     table.addRow(row); //ajouter le livre dans le tableau
                 }
                 }catch(Exception ex){
                     System.out.println(ex);
                 }
       

        }
   }
   
    
    public static void main(String[] args) {
        new PageAdmin("");
    }
    
}
