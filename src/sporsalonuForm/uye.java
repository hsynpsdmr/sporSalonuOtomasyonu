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
import strategy.gold1Ay;
import strategy.gold3Ay;
import strategy.gold6Ay;
import strategy.platin1Ay;
import strategy.platin3Ay;
import strategy.platin6Ay;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Abdullah
 */
public class uye extends javax.swing.JFrame implements tablo {

    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rst = null;
    Connection connDbc = null;
    databaseConnection dbc = new databaseConnection();
    DefaultTableModel dtm = new DefaultTableModel();
    DefaultTableModel ct = new DefaultTableModel();

    public uye() {
        initComponents();
        uyetablo.setModel(ct);
        ct.setColumnIdentifiers(new String[]{"TC", "ÜYELİK TÜRÜ", "BRANSID", "KAYIT TARİHİ"});
        connDbc = dbc.databaseConn();
        tabloListele();
    }
//**************************************METOTLAR********************************************

    @Override
    public void tabloListele() {
        try {
            String sqlSelectDataFromDatabase = "select * from uye ";
            pst = connDbc.prepareStatement(sqlSelectDataFromDatabase);
            rst = pst.executeQuery();
            while (rst.next()) {
                String TC = rst.getString("tc");
                String ÜYELİKTÜRÜ = rst.getString("uyelikturu");
                String BRANS = rst.getString("bransid");
                String KAYİT = rst.getString("kayittarihi");

                DefaultTableModel dftable = (DefaultTableModel) uyetablo.getModel();
                Object[] obj = {TC, ÜYELİKTÜRÜ, BRANS, KAYİT};
                dftable.addRow(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(uye.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void facedkayıt() {
        UyeKayıt();
        UyeGelir();
        yenile();
        TextFieldTemizleme();
    }

    @Override
    public void facedsil() {
        UyeSil();
        yenile();
        TextFieldTemizleme();
    }

    @Override
    public void facedguncelle() {
        UyeGuncelle();
        yenile();
        TextFieldTemizleme();
    }

    @Override
    public void yenile() {
        DefaultTableModel tableModel = (DefaultTableModel) uyetablo.getModel();
        tableModel.setRowCount(0);
        tabloListele();
    }

    public void TextFieldeVeriCekme() {
        int index = uyetablo.getSelectedRow();
        TableModel model = uyetablo.getModel();
        String tc = model.getValueAt(index, 0).toString();
        String uturu = model.getValueAt(index, 1).toString();
        String bransid = model.getValueAt(index, 2).toString();

        uyetc.setText(tc);
        uyelikturu.setText(uturu);
        uyebransid.setText(bransid);
        ara.setText(tc);
    }

    public void TextFieldTemizleme() {
        uyetc.setText(null);
        uyelikturu.setText(null);
        uyebransid.setText(null);
        ara.setText(null);
    }

    public void OncekiSayfayaGecis() {
        anaSayfa as = new anaSayfa();
        as.setVisible(true);
        this.setVisible(false);
    }

    private int ucret;

    public int getUcret() {
        return ucret;
    }

    public void setUcret(int ucret) {
        this.ucret = ucret;
    }

    public void UyeGelirEkle() {

        try {
            String sqlQuery = "INSERT INTO finans (\"kaynak\",\"gelir\",\"gider\",toplam) VALUES (?,?,?,?);";
            pst = connDbc.prepareStatement(sqlQuery);
            String kaynak = "uye kayıt ücreti";
            int sayi1 = ucret;
            int sayi2=0;
            int toplam = sayi1;
            pst.setString(1, kaynak);
            pst.setInt(2, Integer.valueOf(sayi1));
            pst.setInt(3, Integer.valueOf(sayi2));
            pst.setInt(4, Integer.valueOf(toplam));

            pst.executeUpdate();

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void UyeGelir() {
        switch (uyelikturu.getText()) {
            case "1": {
                gold1Ay gbir = new gold1Ay();
                ucret = getUcret();
                gbir.uyeliktipi();
            }
            break;
            case "2": {
                gold3Ay guc = new gold3Ay();
                ucret = getUcret();
                guc.uyeliktipi();
            }
            break;
            case "3": {
                gold6Ay galti = new gold6Ay();
                ucret = getUcret();
                galti.uyeliktipi();
            }
            break;
            case "4": {
                platin1Ay pbir = new platin1Ay();
                ucret = getUcret();
                pbir.uyeliktipi();
            }
            break;
            case "5": {
                platin3Ay puc = new platin3Ay();
                ucret = getUcret();
                puc.uyeliktipi();

            }
            break;
            default:
                platin6Ay palti = new platin6Ay();
                ucret = getUcret();
                palti.uyeliktipi();
                break;
        }

    }

    public void UyeKayıt() {
        try {
            String sqlQuery = "INSERT INTO uye (\"tc\",\"uyelikturu\",bransid) VALUES (?,?,?);";
            pst = connDbc.prepareStatement(sqlQuery);
            pst.setString(1, uyetc.getText());
            pst.setInt(2, Integer.valueOf(uyelikturu.getText()));
            pst.setInt(3, Integer.valueOf(uyebransid.getText()));

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "YENİ ÜYE EKLENDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }

    }

    public void UyeGuncelle() {
        try {
            String sqlQuery = "UPDATE uye " + "SET uyelikturu = ? " + "WHERE tc = ?";
            String sqlQuery1 = "UPDATE uye " + "SET bransid = ? " + "WHERE tc = ?";
            pst = connDbc.prepareStatement(sqlQuery);
            pst1 = connDbc.prepareStatement(sqlQuery1);

            pst.setInt(1, Integer.valueOf(uyelikturu.getText()));
            pst.setString(2, uyetc.getText());
            pst1.setInt(1, Integer.valueOf(uyebransid.getText()));
            pst1.setString(2, uyetc.getText());

            pst.executeUpdate();
            pst1.executeUpdate();
            JOptionPane.showMessageDialog(null, "ÜYE BİLGİLERİ GÜNCELLENDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void UyeSil() {
        try {
            String sqlQuery = "DELETE FROM uye WHERE tc=?";
            pst = connDbc.prepareStatement(sqlQuery);
            pst.setString(1, ara.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "ÜYE BİLGİLERİ SİLİNDİ");
        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void UyeAra() {
        try {
            String sqlQuery = "select*from uye where tc=?";
            pst = connDbc.prepareStatement(sqlQuery);
            pst.setString(1, ara.getText());
            rst = pst.executeQuery();
            uyetablo.setModel(DbUtils.resultSetToTableModel(rst));
            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(uye.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        uyetablo = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        uyebransid = new javax.swing.JTextField();
        uyetc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        uyekaydet = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel8 = new javax.swing.JLabel();
        uyelikturu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jButton3 = new javax.swing.JButton();
        hatalabel = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        ara = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("UYE KAYIT");

        uyetablo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TC", "ÜYELİK TÜRÜ", "BRANS iD", "KAYIT TARİHİ"
            }
        ));
        uyetablo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                uyetabloMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(uyetablo);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ÜYE EKLE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(0, 51, 204))); // NOI18N

        uyebransid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                uyebransidKeyPressed(evt);
            }
        });

        uyetc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                uyetcKeyPressed(evt);
            }
        });

        jLabel3.setText("BRANSİD : ");

        jLabel4.setText("TC : ");

        uyekaydet.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\images.png")); // NOI18N
        uyekaydet.setText("KAYDET");
        uyekaydet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uyekaydetActionPerformed(evt);
            }
        });

        jLabel6.setText("BRANSLAR");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "1)Basketbol", "2)Fitnes", "3)Boks", "4)Plates", "5)Muay-Thai", "6)Wushu", "7)Wing-Chun", "8)BOX", "9)TEAKWANDO" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList1);

        jLabel8.setText("ÜYELİK TÜRÜ :");

        uyelikturu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uyelikturuActionPerformed(evt);
            }
        });
        uyelikturu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                uyelikturuKeyPressed(evt);
            }
        });

        jLabel7.setText("ÜYELİK TÜRÜ");

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "1)Gold 1 Ay", "2)Gold 3 Ay", "3)Gold 6 Ay", "4)Platin 1 Ay", "5)Platin 3 Ay", "6)Platin 6 Ay", " ", " ", " ", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jList2);

        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\sync-circle-blue-512.png")); // NOI18N
        jButton3.setText("Güncelle");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        hatalabel.setForeground(java.awt.Color.red);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hatalabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jButton3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(uyekaydet))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(uyebransid))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(uyelikturu))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(uyetc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uyetc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uyelikturu, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uyebransid, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hatalabel)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uyekaydet)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)))
                .addContainerGap())
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

        jButton4.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\refresh-arrow-icon-special-yellow-round-button-refresh-arrow-icon-isolated-special-yellow-round-button-abstract-illustration-105906481.jpg")); // NOI18N
        jButton4.setText("Yenile");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ara, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ara, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1)
                                .addComponent(jButton4)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
//*********************************İŞLEMLER******************************************
    private void uyekaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uyekaydetActionPerformed
        facedkayıt();
    }//GEN-LAST:event_uyekaydetActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        facedsil();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        OncekiSayfayaGecis();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void uyelikturuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uyelikturuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uyelikturuActionPerformed

    private void araActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_araActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_araActionPerformed

    private void araKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_araKeyReleased
        UyeAra();
    }//GEN-LAST:event_araKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        yenile();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        facedguncelle();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void uyetabloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_uyetabloMouseClicked
        TextFieldeVeriCekme();
    }//GEN-LAST:event_uyetabloMouseClicked

    private void uyetcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_uyetcKeyPressed
        try {
            int t = Integer.parseInt(uyetc.getText());
            hatalabel.setText("");
        } catch (NumberFormatException e2) {
            hatalabel.setText("Lütfen sadece rakam giriniz!");
        }
    }//GEN-LAST:event_uyetcKeyPressed

    private void uyelikturuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_uyelikturuKeyPressed
        hatalabel.setText("");
    }//GEN-LAST:event_uyelikturuKeyPressed

    private void uyebransidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_uyebransidKeyPressed
        hatalabel.setText("");
    }//GEN-LAST:event_uyebransidKeyPressed

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
            java.util.logging.Logger.getLogger(uye.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(uye.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(uye.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(uye.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new uye().setVisible(true);
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField uyebransid;
    private javax.swing.JButton uyekaydet;
    private javax.swing.JTextField uyelikturu;
    private javax.swing.JTable uyetablo;
    private javax.swing.JTextField uyetc;
    // End of variables declaration//GEN-END:variables

}
