

import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class Demande extends javax.swing.JFrame {
    public Demande(String s) {
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

        /*setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);*/
        setPreferredSize(new java.awt.Dimension(400, 225));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(225, 223, 223));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Noto Naskh Arabic", 1, 14)); // NOI18N
        jLabel1.setText("Donner L'ID du livre a demander:");

        btn.setBackground(new java.awt.Color(51, 51, 51));
        btn.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        btn.setForeground(new java.awt.Color(255, 255, 255));
        btn.setText("Demander");
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
            int n=Integer.parseInt(id.getText());
            // les requette utilisé pour verifier la demande d'un livre
            if(c.s.executeQuery("select * from usr where nlivre =5 and username = '"+username+"'").next()){
                ans.setText("Vous avez déja le nombre maximal de livres (5)");

            }
            else if(c.s.executeQuery("select * from usr where username = '"+username+"' and finpenalty >= CURDATE() ").next()){
                
                ResultSet r=c.s.executeQuery("select finpenalty from usr where username = '"+username+"'");
                r.next();
                String d1=r.getString(1);
                ans.setText("Vous etes maintenant pénalisé !! \nVotre penalité termine: "+d1);
            }
            else if(c.s.executeQuery("select * from historique where dateretour is null and datepret + interval 30 day < CURDATE() and usr ='"+username+"'").next()){
                ans.setText("Vous avez un livre qui a dépasser 30 jours \n Il faut le retourner avant demander d'autre livres");
            }
            else if(c.s.executeQuery("select * from book where id = '"+n+"' and available =1").next()){
                c.s.executeUpdate("insert into historique values ('"+username+"',"+n+",CURDATE(),null)");
                // inserer dans la table historique la transaction avec date de pret est la date actuelle, date de retour est null
                c.s.executeUpdate("Update book set available = 0 where id ="+n);
                // changer la disponibilité du livre
                c.s.executeUpdate("Update usr set nlivre = nlivre+1 where username = '"+username+"'");
                // incrementer l'attribut nombre de livre de l'utilisateur
                ans.setForeground(new java.awt.Color(0, 128, 0));;
                ans.setText("Transaction succesif!");
            }
            else{
                ans.setText("Ce livre n'est pas disponible");
            }
        }
        catch (NumberFormatException e){
            ans.setText("Id doit etre un entier!");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Demande.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Demande.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Demande.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Demande.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Demande("").setVisible(true);
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
