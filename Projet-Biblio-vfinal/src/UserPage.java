
import java.sql.ResultSet;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ademm
 */
public class UserPage extends javax.swing.JFrame {

    /**
     * Creates new form UserPage
     */
   public UserPage(String s) {
        username=s;
        c=new Conn();
        d=LocalDate.now().toString();
        q1="select * from book where available =1"; // requette qui remettre les livres disponible dans la bibiothèque
        q2="select * from historique join book on livre=id where usr = '"+ username+"' and dateretour is null";
        //requette pour les livres actuellement emprunté par l'utilisateur 
        q3="select * from historique join book on livre=id where usr = '"+ username+"'";
        //requette pour tous livres actuellement emprunté par l'utilisateur
        condition="";
        // condition initialement vide, elle serai utilisé pour le filtrage 
        lastbtn=0;
        // un entier qui contient un identifiant pour la dernière button pressé: 1 pour 'browse', 2 pour 'vos livres', 3 pour 'historique'  
        initComponents();
    }

    
    private void initComponents() {

        setLocation(300,150);

        jPanel1 = new javax.swing.JPanel();
        History = new javax.swing.JButton();
        browse = new javax.swing.JButton();
        livres = new javax.swing.JButton();
        Dt = new javax.swing.JLabel();
        descirption = new javax.swing.JLabel();
        retour = new javax.swing.JButton();
        demande = new javax.swing.JButton();
        livrepref = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        affichage = new javax.swing.JTable();
        genrepref1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        author = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        genre = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(110, 70, 19));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Welcome, "+username, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18), new java.awt.Color(231, 217, 217))); // NOI18N

        History.setBackground(new java.awt.Color(255, 228, 129));
        History.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        History.setText("Historique");
        History.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        History.setFocusable(false);
        History.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistoryActionPerformed();
            }
        });

        browse.setBackground(new java.awt.Color(255, 228, 129));
        browse.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        browse.setText("Browse");
        browse.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        browse.setFocusable(false);
        browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseActionPerformed();
            }
        });

        livres.setBackground(new java.awt.Color(255, 228, 129));
        livres.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        livres.setText("Vos livres");
        livres.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        livres.setFocusable(false);
        livres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                livresActionPerformed();
            }
        });

        Dt.setBackground(new java.awt.Color(246, 230, 206));
        Dt.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        Dt.setForeground(new java.awt.Color(231, 217, 217));
        Dt.setText("Date D'Aujord'hui:  "+d);

        descirption.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        descirption.setForeground(new java.awt.Color(231, 217, 217));

        retour.setBackground(new java.awt.Color(194, 190, 190));
        retour.setFont(new java.awt.Font("Constantia", 1, 14)); // NOI18N
        retour.setText("Retourner un livre");
        retour.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        retour.setFocusable(false);
        retour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retourActionPerformed(evt);
            }
        });

        demande.setBackground(new java.awt.Color(194, 190, 190));
        demande.setFont(new java.awt.Font("Constantia", 1, 14)); // NOI18N
        demande.setText("Demander un livre");
        demande.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        demande.setFocusable(false);
        demande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                demandeActionPerformed(evt);
            }
        });

        livrepref.setBackground(new java.awt.Color(246, 230, 206));
        livrepref.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        livrepref.setForeground(new java.awt.Color(231, 217, 217));

        genrepref1.setBackground(new java.awt.Color(246, 230, 206));
        genrepref1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        genrepref1.setForeground(new java.awt.Color(231, 217, 217));
        try{
        ResultSet r=c.s.executeQuery("select nom,author,count(*) as c from historique join book on livre =id where usr= '"+username +"' group by id order by c desc limit 1;");
        //                            une requette qui remettre le livre emprunté le plus de fois par l'utlisateur             
        if(r.next()){
            livrepref.setText("Votre livre préferé: "+r.getString(1)+" par "+r.getString(2));
        }else{
            livrepref.setText("Que serait votre premier livre?");
        }
        r=c.s.executeQuery("select nom,author,count(*) as c from historique join book on livre =id group by id order by c desc limit 1;");
        //                     une requette qui remettre le livre le plus emprunté par tous les utilisateurs
        if(r.next()){
        genrepref1.setText("Le livre le plus populaire dans la bibliothèque: "+r.getString(1)+" par "+r.getString(2));}
    }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        jScrollPane1.setBorder(null);
        jScrollPane1.setFont(new java.awt.Font("Segoe UI Historic", 0, 12)); // NOI18N
        
        affichage.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        affichage.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(affichage);

        

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("filter by:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Genre:");
        author.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                action();
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Auteur:");
        try{
            ResultSet r=c.s.executeQuery("select count(distinct author) as c from book");
            //requette pour avoir le nombre d'auteurs
            r.next();
            String[] s= new String[r.getInt("c")+1];
            s[0]="Any";
            r=c.s.executeQuery("select distinct author from book");
            // requette qui remet la liste des auteurs
            int i=1;
            while(r.next()){
                s[i]=r.getString("author");
                i++;
            }
            author.setModel(new javax.swing.DefaultComboBoxModel<>(s));
            r=c.s.executeQuery("select count(distinct genre) as c from book");
            // de meme pour le genre
            r.next();
            s= new String[r.getInt("c")+1];
            s[0]="Any";
            r=c.s.executeQuery("select distinct genre from book");
            i=1;
            while(r.next()){
                s[i]=r.getString("genre");
                i++;
            }
            genre.setModel(new javax.swing.DefaultComboBoxModel<>(s));

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());

        }

        genre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                action();
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(livrepref, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descirption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Dt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(browse, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(livres, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(27, 27, 27)
                                .addComponent(History, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(17, 17, 17))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(retour, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(demande, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(genrepref1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(author, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(genre, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Dt, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(browse, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(livres, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(History, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descirption, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(author, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(genre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(livrepref, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(genrepref1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(retour, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(demande, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void browseActionPerformed() {//GEN-FIRST:event_browseActionPerformed
        lastbtn=1;
        descirption.setText("Voici la liste des livres actuellement disponibles dans la bibliothèque:");
        DefaultTableModel m= (DefaultTableModel) affichage.getModel();
        m.setRowCount(0);
        m.setColumnIdentifiers(new Object[]{"Id","Nom","Auteur","Genre"});
        try{
            ResultSet r=c.s.executeQuery(q1+condition);
            while(r.next()){
                m.addRow(new Object[]{r.getString(1),r.getString(2),r.getString(3),r.getString(4)});
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        
    }

    private void retourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retourActionPerformed
        new Retour(username).setVisible(true);
    }

    private void demandeActionPerformed(java.awt.event.ActionEvent evt) {
        new Demande(username).setVisible(true);
    }


    private void livresActionPerformed() {//GEN-FIRST:event_livresActionPerformed
        lastbtn=2;
        DefaultTableModel m= (DefaultTableModel) affichage.getModel();
        m.setColumnIdentifiers(new Object[]{"Id","Nom","Auteur","Genre","Date de pret"});
        m.setRowCount(0);
        descirption.setText("Voici la liste des livres que vous possèdez:");
        try{
        ResultSet r=c.s.executeQuery(q2+condition);
            while(r.next()){ 
                
                String a=r.getString("id");
                String b=r.getString("nom");
                String c=r.getString("author");
                String d=r.getString("genre");
                String e=r.getString("datepret");
                m.addRow(new Object[]{a,b,c,d,e});
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

    }
    private void HistoryActionPerformed() {//GEN-FIRST:event_HistoryActionPerformed
        lastbtn=3;
        DefaultTableModel m= (DefaultTableModel) affichage.getModel();
        m.setColumnIdentifiers(new Object[]{"Id","Nom","Date de pret","date de retour"});
        m.setRowCount(0);
        descirption.setText("Voici la liste de toutes vos transactions dans la bibliothèque:");
        try{
        ResultSet r=c.s.executeQuery(q3+condition);
            while(r.next()){ 
                
                String a=r.getString("id");
                String b=r.getString("nom");
                String c=r.getString("datepret");
                String d=r.getString("dateretour");
                if(d == null){d="pas encore";}
                m.addRow(new Object[]{a,b,c,d});
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }


    private void action(){
        String g=genre.getSelectedItem().toString();
        String a=author.getSelectedItem().toString();
        condition="";
        if(!a.equals("Any")){
            condition+=" and author = '"+a+"'";}
        if(!g.equals("Any")){
            condition+=" and genre = '"+g+"'";}
        switch (lastbtn) {
            case 1:
                browseActionPerformed();
                break;
            case 2:
                livresActionPerformed();
                break;
            case 3:
                HistoryActionPerformed();
                break;
            default:
                break;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserPage("test").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Dt;
    private javax.swing.JButton History;
    private javax.swing.JTable affichage;
    private javax.swing.JComboBox<String> author;
    private javax.swing.JButton browse;
    private javax.swing.JButton demande;
    private javax.swing.JLabel descirption;
    private javax.swing.JComboBox<String> genre;
    private javax.swing.JLabel genrepref1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel livrepref;
    private javax.swing.JButton livres;
    private javax.swing.JButton retour;
    // End of variables declaration//GEN-END:variables

    private String username;
    private Conn c;
    private String d;
    private String q1;
    private String q2;
    private String q3;
    private String condition;
    private int lastbtn;
}
