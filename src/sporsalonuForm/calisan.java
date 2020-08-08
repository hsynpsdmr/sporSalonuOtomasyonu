package sporsalonuForm;

import factoryMethod.tablo;
import sporsalonu.databaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sporsalonu.DbUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hüseyin
 */
public class calisan extends javax.swing.JFrame implements tablo {

    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rst = null;
    Connection connDbc = null;
    databaseConnection dbc = new databaseConnection();
    DefaultTableModel dtm = new DefaultTableModel();

    DefaultTableModel ct = new DefaultTableModel();

    public calisan() {
        initComponents();
        calisantablo.setModel(ct);
        ct.setColumnIdentifiers(new String[]{"TC", "ÜNVAN", "İŞ BAŞLANGIÇ", "MAAŞ"});
        connDbc = dbc.databaseConn();
        tabloListele();
    }

    @Override
    public void tabloListele() {
        try {
            String sqlSelectDataFromDatabase = "select * from calisan ";
            pst = connDbc.prepareStatement(sqlSelectDataFromDatabase);
            rst = pst.executeQuery();
            while (rst.next()) {

                String TC = rst.getString("tc");
                String UNVANİ = rst.getString("unvani");
                String KAYİT = rst.getString("kayittarihi");
                String MAAŞ = rst.getString("maas");

                DefaultTableModel dftable = (DefaultTableModel) calisantablo.getModel();
                Object[] obj = {TC, UNVANİ, KAYİT, MAAŞ};
                dftable.addRow(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(calisan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void facedkayıt() {
        CalisanKayıt();
        yenile();
        CalisanGiderEkle();
        TextFieldTemizleme();
    }

    @Override
    public void facedsil() {
        CalisanSil();
        yenile();
        TextFieldTemizleme();
    }

    @Override
    public void facedguncelle() {
        CalisanGuncelle();
        yenile();
        TextFieldTemizleme();
    }

    @Override
    public void yenile() {
        DefaultTableModel tableModel = (DefaultTableModel) calisantablo.getModel();
        tableModel.setRowCount(0);
        tabloListele();
    }

    public void TextFieldeVeriCekme() {
        int index = calisantablo.getSelectedRow();
        TableModel model = calisantablo.getModel();
        String tc = model.getValueAt(index, 0).toString();
        String unvan = model.getValueAt(index, 1).toString();
        String maas = model.getValueAt(index, 3).toString();

        ctc.setText(tc);
        cunvani.setText(unvan);
        cmaas.setText(maas);
        ara.setText(tc);
    }

    public void TextFieldTemizleme() {
        ctc.setText(null);
        cunvani.setText(null);
        cmaas.setText(null);
        ara.setText(null);
    }

    public void OncekiSayfayaGecis() {
        anaSayfa as = new anaSayfa();
        as.setVisible(true);
        this.setVisible(false);
    }

    public void CalisanKayıt() {
        try {
            String sqlQuery = "INSERT INTO calisan (\"tc\",\"unvani\",maas) VALUES (?,?,?);";
            pst = connDbc.prepareStatement(sqlQuery);
            pst.setString(1, ctc.getText());
            pst.setString(2, cunvani.getText());
            pst.setInt(3, Integer.valueOf(cmaas.getText()));

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "YENİ ÇALIŞAN EKLENDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void CalisanGiderEkle() {
        try {
            String sqlQuery = "INSERT INTO finans (\"kaynak\",\"gelir\",\"gider\",toplam) VALUES (?,?,?,?);";
            PreparedStatement pst = connDbc.prepareStatement(sqlQuery);
            String kaynak = "Çalışan maaş";
            pst.setString(1, kaynak);
            pst.setInt(3, Integer.valueOf(cmaas.getText()));

            int sayi1 = Integer.parseInt(cmaas.getText());
            int toplam = sayi1;
            int sayi2 = 0;
            pst.setInt(2, Integer.valueOf(sayi2));
            pst.setInt(4, Integer.valueOf(toplam));
            pst.executeUpdate();
            yenile();
        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void CalisanGuncelle() {
        try {
            String sqlQuery = "UPDATE calisan " + "SET unvani = ? " + "WHERE tc = ?";
            String sqlQuery1 = "UPDATE calisan " + "SET maas = ? " + "WHERE tc = ?";
            pst = connDbc.prepareStatement(sqlQuery);
            pst1 = connDbc.prepareStatement(sqlQuery1);

            pst.setString(1, cunvani.getText());
            pst.setString(2, ctc.getText());
            pst1.setInt(1, Integer.valueOf(cmaas.getText()));
            pst1.setString(2, ctc.getText());

            pst.executeUpdate();
            pst1.executeUpdate();
            JOptionPane.showMessageDialog(null, "ÇALIŞAN BİLGİLERİ GÜNCELLENDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void CalisanSil() {
        try {
            String sqlQuery = "DELETE FROM calisan WHERE tc=?";
            pst = connDbc.prepareStatement(sqlQuery);
            pst.setString(1, ara.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "ÇALIŞAN BİLGİLERİ SİLİNDİ");
        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void CalisanAra() {
        try {
            String sqlQuery = "select*from calisan where tc=?";
            pst = connDbc.prepareStatement(sqlQuery);
            pst.setString(1, ara.getText());
            rst = pst.executeQuery();

            calisantablo.setModel(DbUtils.resultSetToTableModel(rst));

            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(kisi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        calisantablo = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        ctc = new javax.swing.JTextField();
        cmaas = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cKaydet = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cunvani = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        hatalabel = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        ara = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CALİSAN KAYDI");

        calisantablo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TC", "ÜNVAN", "İŞ BAŞLANGICI", "MAAŞ"
            }
        ));
        calisantablo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calisantabloMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(calisantablo);
        if (calisantablo.getColumnModel().getColumnCount() > 0) {
            calisantablo.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Çalısan  Kayıt", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(0, 102, 204))); // NOI18N

        ctc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ctcKeyPressed(evt);
            }
        });

        cmaas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmaasActionPerformed(evt);
            }
        });
        cmaas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmaasKeyPressed(evt);
            }
        });

        jLabel2.setText("Ünvan");

        jLabel3.setText("TC");

        cKaydet.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\images.png")); // NOI18N
        cKaydet.setText("KAYDET");
        cKaydet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cKaydetActionPerformed(evt);
            }
        });

        jLabel1.setText("Maaş ");

        cunvani.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cunvaniKeyPressed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\sync-circle-blue-512.png")); // NOI18N
        jButton5.setText("Güncelle");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        hatalabel.setForeground(java.awt.Color.red);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hatalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jButton5))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cKaydet)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ctc)
                                .addComponent(cmaas)
                                .addComponent(cunvani, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ctc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cunvani, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmaas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hatalabel)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cKaydet)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\175-512.png")); // NOI18N
        jButton2.setText("GERİ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\627249-delete3-512.png")); // NOI18N
        jButton1.setText("SİL");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel10.setText("Ara : ");

        ara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                araActionPerformed(evt);
            }
        });
        ara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                araKeyReleased(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\refresh-arrow-icon-special-yellow-round-button-refresh-arrow-icon-isolated-special-yellow-round-button-abstract-illustration-105906481.jpg")); // NOI18N
        jButton3.setText("Yenile");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ara, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(7, 7, 7)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ara, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cKaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cKaydetActionPerformed
        facedkayıt();
    }//GEN-LAST:event_cKaydetActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        OncekiSayfayaGecis();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cmaasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmaasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmaasActionPerformed

    private void araActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_araActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_araActionPerformed

    private void araKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_araKeyReleased
        CalisanAra();
    }//GEN-LAST:event_araKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        yenile();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        facedsil();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        facedguncelle();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void calisantabloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calisantabloMouseClicked
        TextFieldeVeriCekme();
    }//GEN-LAST:event_calisantabloMouseClicked

    private void ctcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ctcKeyPressed
        try {
            int i = Integer.parseInt(ctc.getText());
            hatalabel.setText("");
        } catch (NumberFormatException e1) {
            hatalabel.setText("Lütfen sadece rakam giriniz!");
        }
    }//GEN-LAST:event_ctcKeyPressed

    private void cunvaniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cunvaniKeyPressed
        hatalabel.setText("");
    }//GEN-LAST:event_cunvaniKeyPressed

    private void cmaasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmaasKeyPressed
        try {
            int i = Integer.parseInt(cmaas.getText());
            hatalabel.setText("");
        } catch (NumberFormatException e1) {
            hatalabel.setText("Lütfen sadece rakam giriniz!");
        }
    }//GEN-LAST:event_cmaasKeyPressed

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
            java.util.logging.Logger.getLogger(calisan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(calisan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(calisan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(calisan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new calisan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ara;
    private javax.swing.JButton cKaydet;
    private javax.swing.JTable calisantablo;
    private javax.swing.JTextField cmaas;
    private javax.swing.JTextField ctc;
    private javax.swing.JTextField cunvani;
    private javax.swing.JLabel hatalabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
