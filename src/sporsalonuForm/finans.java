/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sporsalonuForm;

import factoryMethod.tablo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sporsalonu.DbUtils;
import sporsalonu.databaseConnection;

/**
 *
 * @author Hüseyin
 */
public class finans extends javax.swing.JFrame implements tablo {

    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst2 = null;
    PreparedStatement pst3 = null;   
    ResultSet rst = null;
    Connection connDbc = null;
    databaseConnection dbc = new databaseConnection();
    DefaultTableModel dtm = new DefaultTableModel();

    public finans() {
        initComponents();
        jTable1.setModel(dtm);
        dtm.setColumnIdentifiers(new String[]{"id", "kaynak", "gelir", "gider", "toplam", "tarih"});
        connDbc = dbc.databaseConn();
        tabloListele();
    }

    @Override
    public void tabloListele() {
        try {
            String sqlSelectDataFromDatabase = "select * from finans ";
            pst = connDbc.prepareStatement(sqlSelectDataFromDatabase);
            rst = pst.executeQuery();
            while (rst.next()) {
                String ID = rst.getString("id");
                String KAYNAK = rst.getString("kaynak");
                String GELİR = rst.getString("gelir");
                String GİDER = rst.getString("gider");
                String TOPLAM = rst.getString("toplam");
                String TARİH = rst.getString("tarih");

                DefaultTableModel dftable = (DefaultTableModel) jTable1.getModel();
                Object[] obj = {ID, KAYNAK, GELİR, GİDER, TOPLAM, TARİH};

                dftable.addRow(obj);

            }

        } catch (SQLException ex) {
            Logger.getLogger(finans.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void facedkayıt() {
        FinansKayıt();
        yenile();
        TextFieldTemizleme();
    }

    @Override
    public void facedsil() {
        FinansSil();
        yenile();
        TextFieldTemizleme();
    }

    @Override
    public void facedguncelle() {
        FinansGuncelle();
        yenile();
        TextFieldTemizleme();
    }

    @Override
    public void yenile() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        tabloListele();
    }

    public void TextFieldeVeriCekme() {
        int index = jTable1.getSelectedRow();
        TableModel model = jTable1.getModel();
        String fid = model.getValueAt(index, 0).toString();
        String fkaynak = model.getValueAt(index, 1).toString();
        String fekgelir = model.getValueAt(index, 2).toString();
        String fekgider = model.getValueAt(index, 3).toString();
        String ftoplam = model.getValueAt(index, 4).toString();

        id.setText(fid);
        kaynak.setText(fkaynak);
        ekGelir.setText(fekgelir);
        ekGider.setText(fekgider);
        toplam.setText(ftoplam);
        ara.setText(fid);
    }

    public void TextFieldTemizleme() {
        id.setText(null);
        kaynak.setText(null);
        ekGelir.setText(null);
        ekGider.setText(null);
        toplam.setText(null);
        ara.setText(null);
    }

    public void OncekiSayfayaGecis() {
        anaSayfa as = new anaSayfa();
        as.setVisible(true);
        this.setVisible(false);
    }

    public void FinansKayıt() {
        try {
            String sqlQuery = "INSERT INTO finans (\"kaynak\",\"gelir\",\"gider\",toplam) VALUES (?,?,?,?);";
            pst = connDbc.prepareStatement(sqlQuery);
            String kaynak = "ek gelir-gider";

            pst.setString(1, String.valueOf(kaynak));
            pst.setInt(2, Integer.valueOf(ekGelir.getText()));
            pst.setInt(3, Integer.valueOf(ekGider.getText()));

            int sayi1 = Integer.parseInt(ekGelir.getText());
            int sayi2 = Integer.parseInt(ekGider.getText());
            int toplam = sayi1 - sayi2;

            pst.setInt(4, Integer.valueOf(toplam));

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "YENİ EK GELİR-EK GİDER EKLENDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void FinansGuncelle() {
        try {
            String sqlQuery = "UPDATE finans " + "SET kaynak = ? " + "WHERE id = ?";
            String sqlQuery1 = "UPDATE finans " + "SET gelir = ? " + "WHERE id = ?";
            String sqlQuery2 = "UPDATE finans " + "SET gider = ? " + "WHERE id = ?";
            String sqlQuery3 = "UPDATE finans " + "SET toplam = ? " + "WHERE id = ?";
            pst = connDbc.prepareStatement(sqlQuery);
            pst1 = connDbc.prepareStatement(sqlQuery1);
            pst2 = connDbc.prepareStatement(sqlQuery2);
            pst3 = connDbc.prepareStatement(sqlQuery3);

            pst.setInt(1, Integer.valueOf(kaynak.getText()));
            pst.setString(2, id.getText());
            pst1.setInt(1, Integer.valueOf(ekGelir.getText()));
            pst1.setString(2, id.getText());
            pst2.setInt(1, Integer.valueOf(ekGider.getText()));
            pst2.setString(2, id.getText());
            pst3.setInt(1, Integer.valueOf(toplam.getText()));
            pst3.setString(2, id.getText());

            pst.executeUpdate();
            pst1.executeUpdate();
            pst2.executeUpdate();
            pst3.executeUpdate();
            JOptionPane.showMessageDialog(null, "FİNANS BİLGİLERİ GÜNCELLENDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void FinansSil() {
        try {
            String sqlQuery = "DELETE FROM finans WHERE id=?";
            pst = connDbc.prepareStatement(sqlQuery);

            pst.setInt(1, Integer.valueOf(ara.getText()));

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "FİNANS BİLGİLERİ SİLİNDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void FinansAra() {
        try {
            String sqlQuery = "select*from finans where kaynak=?";
            pst = connDbc.prepareStatement(sqlQuery);
            pst.setString(1, ara.getText());
            rst = pst.executeQuery();

            jTable1.setModel(DbUtils.resultSetToTableModel(rst));

            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(kisi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ekGelir = new javax.swing.JTextField();
        ekGider = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        ara = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        hatalabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        kaynak = new javax.swing.JTextField();
        toplam = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Ek Gelir");

        jLabel2.setText("Ek Gider");

        ekGelir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ekGelirKeyPressed(evt);
            }
        });

        ekGider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ekGiderActionPerformed(evt);
            }
        });
        ekGider.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ekGiderKeyPressed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\images.png")); // NOI18N
        jButton1.setText("EKLE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "KAYNAK", "GELİR", "GİDER", "TOPLAM", "TARİH"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton2.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\175-512.png")); // NOI18N
        jButton2.setText("GERİ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\627249-delete3-512.png")); // NOI18N
        jButton3.setText("SİL");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

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

        jLabel10.setText("Ara : ");

        jButton4.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\refresh-arrow-icon-special-yellow-round-button-refresh-arrow-icon-isolated-special-yellow-round-button-abstract-illustration-105906481.jpg")); // NOI18N
        jButton4.setText("Yenile");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setText(" ");

        jButton5.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\sync-circle-blue-512.png")); // NOI18N
        jButton5.setText("Güncelle");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        hatalabel.setForeground(java.awt.Color.red);

        jLabel3.setText("Kaynak");

        jLabel5.setText("Toplam");

        jLabel6.setText("ID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(133, 133, 133))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hatalabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton1)
                                .addComponent(jButton5))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(25, 25, 25))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel5)
                                                    .addComponent(jLabel2))
                                                .addGap(18, 18, 18)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(ekGelir)
                                            .addComponent(ekGider, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                            .addComponent(toplam)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(27, 27, 27)
                                        .addComponent(kaynak, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(490, 490, 490)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ara, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ara, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton4)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(kaynak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hatalabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(ekGelir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(ekGider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(toplam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        facedkayıt();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        OncekiSayfayaGecis();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        facedsil();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void araActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_araActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_araActionPerformed

    private void araKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_araKeyReleased
        FinansAra();
    }//GEN-LAST:event_araKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        yenile();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        facedguncelle();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        TextFieldeVeriCekme();
    }//GEN-LAST:event_jTable1MouseClicked

    private void ekGelirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ekGelirKeyPressed
        try {
            int i = Integer.parseInt(ekGelir.getText());
            hatalabel.setText("");
        } catch (NumberFormatException e1) {
            hatalabel.setText("Lütfen sadece rakam giriniz!");

        }
    }//GEN-LAST:event_ekGelirKeyPressed

    private void ekGiderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ekGiderKeyPressed
        try {
            int i = Integer.parseInt(ekGider.getText());
            hatalabel.setText("");
        } catch (NumberFormatException e1) {
            hatalabel.setText("Lütfen sadece rakam giriniz!");
        }
    }//GEN-LAST:event_ekGiderKeyPressed

    private void ekGiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ekGiderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ekGiderActionPerformed

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
            java.util.logging.Logger.getLogger(finans.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(finans.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(finans.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(finans.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new finans().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ara;
    private javax.swing.JTextField ekGelir;
    private javax.swing.JTextField ekGider;
    private javax.swing.JLabel hatalabel;
    private javax.swing.JTextField id;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField kaynak;
    private javax.swing.JTextField toplam;
    // End of variables declaration//GEN-END:variables

}
