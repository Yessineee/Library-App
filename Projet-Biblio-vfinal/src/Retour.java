import java.sql.ResultSet;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class Retour extends javax.swing.JFrame {

    /**
     * Creates new form retour
     */
    public Retour(String s) {
        username=s;
        c=new Conn();
        initComponents();
    }

    private void initComponents() {
        setLocation(350,200);

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ans = new javax.swing.JTextPane();
        ans.setEditable(false);

        /*setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);*/
        setPreferredSize(new java.awt.Dimension(400, 225));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(225, 223, 223));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Noto Naskh Arabic", 1, 14)); // NOI18N
        jLabel1.setText("Donner L'ID du livre a retourner:");

        btn.setBackground(new java.awt.Color(51, 51, 51));
        btn.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        btn.setForeground(new java.awt.Color(255, 255, 255));
        btn.setText("Retourner");
        btn.setFocusable(false);
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActionPerformed(evt);
            }
        });

        ans.setBackground(new java.awt.Color(225, 223, 223));
        ans.setBorder(null);
        ans.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        ans.setForeground(new java.awt.Color(255, 0, 51));
        jScrollPane1.setViewportView(ans);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(55, 55, 55))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActionPerformed(java.awt.event.ActionEvent evt) {
        try{
        int i=Integer.parseInt(id.getText());
        // les requettes utilisé pour verifier et retourner un livre
        ResultSet r=c.s.executeQuery("Select datepret from historique where usr = '"+username+"' and livre= "+i+" and dateretour is null");
        if(r.next()){
            LocalDate d=LocalDate.now().plusDays(-30);
            String d1=r.getString("datepret");
            String d2=d.toString();
            c.s.executeUpdate("update book set available = 1 where id="+i); // rendre le livre disponible
            c.s.executeUpdate("update usr set nlivre = nlivre-1 where username = '"+username+"'"); // décrementer l'attribut de nombre de livre du user
            c.s.executeUpdate("update historique set dateretour = CURDATE() where usr = '"+username+"' and livre= "+i+" and dateretour is null");
            // changer la date de retour (initialement nulle) avec la date actuelle
            if (d2.compareTo(d1)>0){
                // d2 contient date de retour moins 30 jours on compare cette valeur avec date de pret pour verifier si l'utilisateur doit etre penalisé
                ans.setText("Livre retourné après la date limite \n Pour cela vous serez penalisé");
                r=c.s.executeQuery("select finpenalty from usr where username = '"+username+"'");
                r.next();
                d1=r.getString(1);
                d2=LocalDate.now().toString();
                if(d1==null || d2.compareTo(d1)>0){
                    // la penalité est un ban du user d'emprunté des livres pour 3 mois
                    // si l'utilisateur n'est pas en periode de penalité la date de fin de penalité est la date actuelle + 3 mois
                    c.s.executeUpdate("update usr set finpenalty = CURDATE() + interval 3 MONTH where username = '"+username+"'");
                }
                else{
                    // si user déja penalisé on incremente la date de fin de penalité par 3 mois
                    c.s.executeUpdate("update usr set finpenalty = finpenalty + interval 3 MONTH where username = '"+username+"'");
                }
            }
            else{
                r=c.s.executeQuery("select finpenalty from usr where username = '"+username+"'");
                r.next();
                d1=r.getString(1);
                ans.setForeground(new java.awt.Color(0, 128, 0));
                ans.setText("Livre retourné avant la date limite \nPas de penalité !! \nVotre penalité se termine: "+d1);

            }
        }
        else{
            ans.setText("vous ne possédez pas ce livre");
        }
    }
    catch (NumberFormatException e){
        ans.setText("Id doit etre un entier!");}
        
    catch(Exception e){
        JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    public static void main(String args[]) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Retour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Retour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Retour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Retour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Retour("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane ans;
    private javax.swing.JButton btn;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private String username;
    private Conn c;
}
