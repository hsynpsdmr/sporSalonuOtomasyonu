package sporsalonuForm;

import factoryMethod.tablo;
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
import sporsalonu.databaseConnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Abdullah
 */
public class urunler extends javax.swing.JFrame implements tablo {

    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst2 = null;
    ResultSet rst = null;
    Connection connDbc = null;
    databaseConnection dbc = new databaseConnection();
    DefaultTableModel dtm = new DefaultTableModel();

    public urunler() {
        initComponents();
        jTable1.setModel(dtm);
        dtm.setColumnIdentifiers(new String[]{"tc", "ekipman", "besin", "fiyat"});
        connDbc = dbc.databaseConn();
        tabloListele();

    }

    @Override
    public void tabloListele() {
        try {
            String sqlSelectDataFromDatabase = "select * from urunler ";
            pst = connDbc.prepareStatement(sqlSelectDataFromDatabase);
            rst = pst.executeQuery();
            while (rst.next()) {
                String TC = rst.getString("tc");
                String EKİPMAN = rst.getString("ekipman");
                String BESİN = rst.getString("besin");
                String FİYAT = rst.getString("fiyat");

                DefaultTableModel dftable = (DefaultTableModel) jTable1.getModel();
                Object[] obj = {TC, EKİPMAN, BESİN, FİYAT};

                dftable.addRow(obj);
            }

        } catch (SQLException ex) {
            Logger.getLogger(urunler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void facedkayıt() {
        UrunKayıt();
        UrunGelirKayıt();
        yenile();
        TextFieldTemizleme();
    }

    @Override
    public void facedsil() {
        UrunSil();
        yenile();
        TextFieldTemizleme();
    }

    @Override
    public void facedguncelle() {
        UrunGuncelle();
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
        String tc = model.getValueAt(index, 0).toString();
        String ekipman = model.getValueAt(index, 1).toString();
        String besin = model.getValueAt(index, 2).toString();
        String fiyat = model.getValueAt(index, 3).toString();

        uruntc.setText(tc);
        urunekipman.setText(ekipman);
        urunbesin.setText(besin);
        urunfiyat.setText(fiyat);
        ara.setText(tc);
    }

    public void TextFieldTemizleme() {
        uruntc.setText(null);
        urunekipman.setText(null);
        urunbesin.setText(null);
        urunfiyat.setText(null);
        ara.setText(null);
    }

    public void UrunKayıt() {
        try {
            String sqlQuery = "INSERT INTO urunler (\"tc\",\"ekipman\",\"besin\",fiyat) VALUES (?,?,?,?);";
            pst = connDbc.prepareStatement(sqlQuery);

            pst.setString(1, uruntc.getText());
            pst.setString(2, urunekipman.getText());
            pst.setString(3, urunbesin.getText());
            pst.setInt(4, Integer.valueOf(urunfiyat.getText()));

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "YENİ ÜRÜNLER EKLENDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void UrunGelirKayıt() {
        try {
            String sqlQuery = "INSERT INTO finans (\"kaynak\",\"gelir\",toplam) VALUES (?,?,?);";
            pst = connDbc.prepareStatement(sqlQuery);
            String kaynak = "Ürün satım";
            pst.setString(1, kaynak);
            pst.setInt(2, Integer.valueOf(urunfiyat.getText()));

            int sayi1 = Integer.parseInt(urunfiyat.getText());
            int toplam = sayi1;
            pst.setInt(3, Integer.valueOf(toplam));
            pst.executeUpdate();
            yenile();

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void UrunGuncelle() {
        try {
            String sqlQuery = "UPDATE urunler " + "SET ekipman = ? " + "WHERE tc = ?";
            String sqlQuery1 = "UPDATE uye " + "SET besin = ? " + "WHERE tc = ?";
            String sqlQuery2 = "UPDATE uye " + "SET fiyat = ? " + "WHERE tc = ?";

            pst = connDbc.prepareStatement(sqlQuery);
            pst1 = connDbc.prepareStatement(sqlQuery1);
            pst2 = connDbc.prepareStatement(sqlQuery2);

            pst.setString(1, urunekipman.getText());
            pst.setString(2, uruntc.getText());
            pst1.setString(1, urunbesin.getText());
            pst1.setString(2, uruntc.getText());
            pst2.setInt(1, Integer.valueOf(urunfiyat.getText()));
            pst2.setString(2, uruntc.getText());

            pst.executeUpdate();
            pst1.executeUpdate();
            pst2.executeUpdate();
            JOptionPane.showMessageDialog(null, "ÜRÜN BİLGİLERİ GÜNCELLENDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void UrunSil() {
        try {
            String sqlQuery = "DELETE FROM urunler WHERE tc=?";
            pst = connDbc.prepareStatement(sqlQuery);

            pst.setString(1, ara.getText());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "ÜRÜN BİLGİLERİ SİLİNDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void UrunAra() {
        try {
            String sqlQuery = "select*from urunler where tc=?";
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        uruntc = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        urunfiyat = new javax.swing.JTextField();
        urunekipman = new javax.swing.JTextField();
        urunbesin = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        hatalabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        ara = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ÜRÜNLER", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(0, 102, 255))); // NOI18N

        jLabel1.setText("EKİPMAN");

        jLabel2.setText("BESİN");

        jLabel3.setText("FİYAT");

        uruntc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                uruntcKeyPressed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\images.png")); // NOI18N
        jButton1.setText("SATIŞ YAP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText("KİŞİ TC");

        urunfiyat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                urunfiyatKeyPressed(evt);
            }
        });

        urunekipman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                urunekipmanKeyPressed(evt);
            }
        });

        urunbesin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                urunbesinKeyPressed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(hatalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(urunbesin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(urunfiyat))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(38, 38, 38)
                            .addComponent(uruntc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(urunekipman, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uruntc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(urunekipman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(urunbesin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(urunfiyat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(hatalabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton5))
                .addContainerGap(134, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "KİŞİ TC", "EKİPMAN", "BESİN", "FİYAT"
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

        jButton4.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\refresh-arrow-icon-special-yellow-round-button-refresh-arrow-icon-isolated-special-yellow-round-button-abstract-illustration-105906481.jpg")); // NOI18N
        jButton4.setText("Yenile");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\627249-delete3-512.png")); // NOI18N
        jButton3.setText("SİL");
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
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ara, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(16, 16, 16))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ara, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(12, 12, 12)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        anaSayfa as = new anaSayfa();
        as.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        facedsil();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void araActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_araActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_araActionPerformed

    private void araKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_araKeyReleased
        UrunAra();
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

    private void uruntcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_uruntcKeyPressed
        try {
            int i = Integer.parseInt(uruntc.getText());
            hatalabel.setText("");
        } catch (NumberFormatException e1) {
            hatalabel.setText("Lütfen sadece rakam giriniz!");
        }
    }//GEN-LAST:event_uruntcKeyPressed

    private void urunfiyatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_urunfiyatKeyPressed
        try {
            int i = Integer.parseInt(urunfiyat.getText());
            hatalabel.setText("");
        } catch (NumberFormatException e1) {
            hatalabel.setText("Lütfen sadece rakam giriniz!");
        }
    }//GEN-LAST:event_urunfiyatKeyPressed

    private void urunekipmanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_urunekipmanKeyPressed
        hatalabel.setText("");
    }//GEN-LAST:event_urunekipmanKeyPressed

    private void urunbesinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_urunbesinKeyPressed
        hatalabel.setText("");
    }//GEN-LAST:event_urunbesinKeyPressed

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
            java.util.logging.Logger.getLogger(urunler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(urunler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(urunler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(urunler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new urunler().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ara;
    private javax.swing.JLabel hatalabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField urunbesin;
    private javax.swing.JTextField urunekipman;
    private javax.swing.JTextField urunfiyat;
    private javax.swing.JTextField uruntc;
    // End of variables declaration//GEN-END:variables

}
