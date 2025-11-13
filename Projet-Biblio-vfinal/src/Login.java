

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener{
    
    JButton login,signup,clear;
    JRadioButton clt,adm;
    JTextField cinTextField;
    JPasswordField passTextField;
    Login(){
            setTitle("Application de gestion des prêts d’ouvrages dans une bibliothèque");
            setLayout(null);
            ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("book.png"));
            Image i2=i1.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            ImageIcon i3=new ImageIcon(i2);
            JLabel label= new JLabel(i3);
            label.setBounds(200,10,100,100);
            add(label);
            JLabel text=new JLabel("Bienvenue");
            text.setFont(new Font("Osward",Font.BOLD,30));
            text.setBounds(300,40,400,40);
            add(text);
            
            JLabel cinno=new JLabel("Username:");
            cinno.setFont(new Font("Raleway",Font.BOLD,28));
            cinno.setBounds(120,150,150,40);
            add(cinno);
            
            cinTextField = new JTextField();
            cinTextField.setBounds(300,154,250,30);
            cinTextField.setFont(new Font("Arial",Font.BOLD,14));
            add(cinTextField);
            
            JLabel pass=new JLabel("Password:");
            pass.setFont(new Font("Raleway",Font.BOLD,28));
            pass.setBounds(120,220,250,40);
            add(pass);
            
            passTextField = new JPasswordField();
            passTextField.setBounds(300,220,250,30);
            passTextField.setFont(new Font("Arial",Font.BOLD,14));
            add(passTextField);
            
            clt=new JRadioButton("Client");
            clt.setBounds(300,280,150,30);
            clt.setBackground(Color.WHITE);
            add(clt);
        
            adm=new JRadioButton("Admin");
            adm.setBounds(450,280,100,30);
            adm.setBackground(Color.WHITE);
            add(adm);
            ButtonGroup btg=new ButtonGroup();
            btg.add(clt);
            btg.add(adm);
            
            login= new JButton("SIGN IN");
            login.setBounds(300,320,100,30);
            login.setBackground(Color.BLACK);
            login.setForeground(Color.WHITE);
            login.addActionListener(this);
            add(login);
            
            clear= new JButton("CLEAR");
            clear.setBounds(430,320,100,30);
            clear.setBackground(Color.BLACK);
            clear.setForeground(Color.WHITE);
            clear.addActionListener(this);
            add(clear);
            
            signup= new JButton("SIGNUP");
            signup.setBounds(300,370,230,30);
            signup.setBackground(Color.BLACK);
            signup.setForeground(Color.WHITE);
            signup.addActionListener(this);
            add(signup);
            
            getContentPane().setBackground(Color.WHITE);
            setSize(800,480);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            setLocation(350,200);
            
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == clear){
            cinTextField.setText("");
            passTextField.setText("");
        }else if(e.getSource() == login){
            Conn c=new Conn();
            String u=cinTextField.getText();
            String password=String.valueOf(passTextField.getPassword());
            String q1="select * from admn where username = '"+u+"'"; 
            String q2="select * from usr where username = '"+u+"'";
            try{
                if(clt.isSelected()){                            //si le bouton radio client est selectionne
                    ResultSet rs= c.s.executeQuery(q2);          //requete sql pour verifier si l'utilisateur existe dans la table utilisateur
                    if(rs.next()){
                        if(rs.getString("mdp").equals(password)){ //verifier si le mot de passe est correct
                            setVisible(false);
                            new UserPage(u).setVisible(true); //ouvrir la fenetre d'accueil de l'utilisateur
                        }
                        else{JOptionPane.showMessageDialog(null,"Password is incorrect");}
                    }else{
                        JOptionPane.showMessageDialog(null,"this user does not exist");
                    }
                    
                }else if(adm.isSelected()){                   //si le bouton radio admin est selectionne
                    ResultSet rs= c.s.executeQuery(q1);       //requete sql pour verifier si l'utilisateur existe dans la table admin
                    if(rs.next()){
                        setVisible(false);  //fermer la fenetre de connexion
                        new PageAdmin(u).setVisible(true); //ouvrir la fenetre d'accueil de l'admin
                    }else{
                        JOptionPane.showMessageDialog(null,"Incorrect Username or Password");
                    }
                } 
               
            }catch(Exception ex){
                System.out.println(e);
            }
        }else if(e.getSource() == signup){
            
            setVisible(false);             //fermer la fenetre de connexion
            new Create().setVisible(true); //ouvrir la fenetre d'inscription
               
        }
    }
    public static void main(String args[]){
        new Login();
        
    }
}
