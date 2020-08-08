package sporsalonuForm;

import factoryMethod.tablo;
import java.awt.FlowLayout;
import sporsalonu.databaseConnection;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
 * @author Abdullah
 */
public class kisi extends javax.swing.JFrame implements tablo {

    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst2 = null;
    PreparedStatement pst3 = null;
    PreparedStatement pst4 = null;
    ResultSet rst = null;
    Connection connDbc = null;
    databaseConnection dbc = new databaseConnection();
    DefaultTableModel dtm = new DefaultTableModel();

    public kisi() {
        initComponents();
        //tabloduzen();
        jTable1.setModel(dtm);
        dtm.setColumnIdentifiers(new String[]{"tc", "ad", "soyad", "telefon", "adres", "yas"});
        connDbc = dbc.databaseConn();
        tabloListele();

    }

    public void tabloduzen() {

    }

    @Override
    public void tabloListele() {
        try {
            String sqlSelectDataFromDatabase = "select * from kisi ";
            pst = connDbc.prepareStatement(sqlSelectDataFromDatabase);
            rst = pst.executeQuery();
            while (rst.next()) {
                String TC = rst.getString("tc");
                String AD = rst.getString("ad");
                String SOYAD = rst.getString("soyad");
                String TEL = rst.getString("telefon");
                String ADRES = rst.getString("adres");
                String YAŞ = rst.getString("yas");

                DefaultTableModel dftable = (DefaultTableModel) jTable1.getModel();
                Object[] obj = {TC, AD, SOYAD, TEL, ADRES, YAŞ};

                dftable.addRow(obj);

            }

        } catch (SQLException ex) {
            Logger.getLogger(kisi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void facedkayıt() {
        KisiKayıt();
        yenile();
        TextFieldTemizleme();
    }

    @Override
    public void facedsil() {
        KisiSil();
        yenile();
        TextFieldTemizleme();
    }

    @Override
    public void facedguncelle() {
        KisiGuncelle();
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
        String ad = model.getValueAt(index, 1).toString();
        String soyad = model.getValueAt(index, 2).toString();
        String adres = model.getValueAt(index, 3).toString();
        String telefon = model.getValueAt(index, 4).toString();
        String yas = model.getValueAt(index, 5).toString();

        kisitc.setText(tc);
        kisiad.setText(ad);
        kisisoyad.setText(soyad);
        kisiadres.setText(adres);
        kisitelefon.setText(telefon);
        kisiyas.setText(yas);
        ara.setText(tc);
    }

    public void TextFieldTemizleme() {
        kisitc.setText(null);
        kisiad.setText(null);
        kisisoyad.setText(null);
        kisiadres.setText(null);
        kisitelefon.setText(null);
        kisiyas.setText(null);
        ara.setText(null);
        uyari.setText(null);
    }

    public void OncekiSayfayaGecis() {
        anaSayfa as = new anaSayfa();
        as.setVisible(true);
        this.setVisible(false);
    }

    public void KisiKayıt() {
        try {
            String sqlQuery = "INSERT INTO kisi (\"tc\",\"ad\",\"soyad\",\"telefon\",\"adres\",yas) VALUES (?,?,?,?,?,?);";
            pst = connDbc.prepareStatement(sqlQuery);

            pst.setString(1, kisitc.getText());
            pst.setString(2, kisiad.getText());
            pst.setString(3, kisisoyad.getText());
            pst.setString(4, kisitelefon.getText());
            pst.setString(5, kisiadres.getText());
            pst.setInt(6, Integer.valueOf(kisiyas.getText()));

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "YENİ KİŞİ EKLENDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void KisiGuncelle() {
        try {
            String sqlQuery = "UPDATE kisi " + "SET ad = ? " + "WHERE tc = ?";
            String sqlQuery1 = "UPDATE kisi " + "SET soyad = ? " + "WHERE tc = ?";
            String sqlQuery2 = "UPDATE kisi " + "SET telefon = ? " + "WHERE tc = ?";
            String sqlQuery3 = "UPDATE kisi " + "SET adres = ? " + "WHERE tc = ?";
            String sqlQuery4 = "UPDATE kisi " + "SET yas = ? " + "WHERE tc = ?";
            pst = connDbc.prepareStatement(sqlQuery);
            pst1 = connDbc.prepareStatement(sqlQuery1);
            pst2 = connDbc.prepareStatement(sqlQuery2);
            pst3 = connDbc.prepareStatement(sqlQuery3);
            pst4 = connDbc.prepareStatement(sqlQuery4);

            pst.setString(1, kisiad.getText());
            pst.setString(2, kisitc.getText());
            pst1.setString(1, kisisoyad.getText());
            pst1.setString(2, kisitc.getText());
            pst2.setString(1, kisiadres.getText());
            pst2.setString(2, kisitc.getText());
            pst3.setString(1, kisitelefon.getText());
            pst3.setString(2, kisitc.getText());
            pst4.setInt(1, Integer.valueOf(kisiyas.getText()));
            pst4.setString(2, kisitc.getText());

            pst.executeUpdate();
            pst1.executeUpdate();
            pst2.executeUpdate();
            pst3.executeUpdate();
            pst4.executeUpdate();

            JOptionPane.showMessageDialog(null, "KİŞİ BİLGİLERİ GÜNCELLENDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void KisiSil() {
        try {
            String sqlQuery = "DELETE FROM kisi WHERE tc=?";
            pst = connDbc.prepareStatement(sqlQuery);

            pst.setString(1, ara.getText());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "KİŞİ BİLGİLERİ SİLİNDİ");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, sQLException);
        }
    }

    public void KisiAra() {
        try {
            String sqlQuery = "select*from kisi where tc=?";
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        kisitc = new javax.swing.JTextField();
        kisisoyad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        kisiad = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        kisiyas = new javax.swing.JTextField();
        hatalabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        kisiadres = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        kisitelefon = new javax.swing.JTextField();
        kisikaydet = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        kisisil = new javax.swing.JButton();
        uyebuton = new javax.swing.JRadioButton();
        calisanbuton = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        ara = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        uyari = new javax.swing.JLabel();

        setTitle("Spor Salonu Üye Kayıt");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kişi Bilgileri", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18), new java.awt.Color(0, 51, 255))); // NOI18N

        jLabel1.setText("Soyad : ");

        jLabel2.setText("Ad : ");

        kisitc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kisitcActionPerformed(evt);
            }
        });
        kisitc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kisitcKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                kisitcKeyReleased(evt);
            }
        });

        kisisoyad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kisisoyadActionPerformed(evt);
            }
        });
        kisisoyad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kisisoyadKeyPressed(evt);
            }
        });

        jLabel3.setText("Tc : ");

        kisiad.setName("kisiad"); // NOI18N
        kisiad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kisiadActionPerformed(evt);
            }
        });
        kisiad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kisiadKeyPressed(evt);
            }
        });

        jLabel8.setText("Yaş:");

        kisiyas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kisiyasKeyPressed(evt);
            }
        });

        hatalabel.setForeground(java.awt.Color.red);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(kisitc, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(kisiad, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(kisiyas, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .addComponent(kisisoyad))))
                .addContainerGap(168, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hatalabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(kisitc, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kisiad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kisisoyad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kisiyas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hatalabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "İletişim Bilgileri", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18), new java.awt.Color(0, 51, 255))); // NOI18N

        jLabel4.setText("Adres:");

        kisiadres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kisiadresKeyPressed(evt);
            }
        });

        jLabel5.setText("Telefon : ");

        kisitelefon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kisitelefonActionPerformed(evt);
            }
        });
        kisitelefon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kisitelefonKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kisiadres, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kisitelefon, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(kisiadres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(kisitelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        kisikaydet.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\images.png")); // NOI18N
        kisikaydet.setText("Kişilere Kaydet");
        kisikaydet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kisikaydetActionPerformed(evt);
            }
        });

        jLabel7.setText("Kayıt Türünü Seçiniz : ");

        jTable1.setForeground(new java.awt.Color(0, 51, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tc", "Ad", "Soyad", "E-posta", "Telefon", "Yaş"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        kisisil.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\627249-delete3-512.png")); // NOI18N
        kisisil.setText("Kişiyi Sil");
        kisisil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kisisilActionPerformed(evt);
            }
        });

        buttonGroup1.add(uyebuton);
        uyebuton.setText("UYE");
        uyebuton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uyebutonActionPerformed(evt);
            }
        });

        buttonGroup1.add(calisanbuton);
        calisanbuton.setText("CALİSAN");

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\175-512.png")); // NOI18N
        jButton1.setText("GERİ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        jButton2.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\refresh-arrow-icon-special-yellow-round-button-refresh-arrow-icon-isolated-special-yellow-round-button-abstract-illustration-105906481.jpg")); // NOI18N
        jButton2.setText("Yenile");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel10.setText("Ara : ");

        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\serpil\\Desktop\\neyöyamü\\sync-circle-blue-512.png")); // NOI18N
        jButton3.setText("Güncelle");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        uyari.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        uyari.setForeground(java.awt.Color.red);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(857, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(uyebuton)
                                            .addComponent(calisanbuton)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton3)
                                        .addGap(42, 42, 42)
                                        .addComponent(kisikaydet))))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(uyari, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ara, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(kisisil)
                                .addContainerGap())
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(18, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(uyebuton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calisanbuton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uyari, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kisikaydet, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ara, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kisisil, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(65, 65, 65)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void kisiadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kisiadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kisiadActionPerformed

    private void kisisoyadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kisisoyadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kisisoyadActionPerformed

    private void kisitcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kisitcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kisitcActionPerformed

    private void kisitelefonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kisitelefonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kisitelefonActionPerformed

    private void kisikaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kisikaydetActionPerformed
        

        if (calisanbuton.isSelected() == true) {
            facedkayıt();
            calisan c = new calisan();
            c.setVisible(true);

        } else if (uyebuton.isSelected() == true) {
            facedkayıt();
            uye u = new uye();
            u.setVisible(true);
        } else {
            uyari.setText("Lütfen Seçim Yapınız  ! ! ");

        }


    }//GEN-LAST:event_kisikaydetActionPerformed

    private void kisisilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kisisilActionPerformed
        facedsil();
    }//GEN-LAST:event_kisisilActionPerformed

    private void uyebutonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uyebutonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uyebutonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        OncekiSayfayaGecis();
    }//GEN-LAST:event_jButton1ActionPerformed


    private void araKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_araKeyReleased
        KisiAra();
    }//GEN-LAST:event_araKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        yenile();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void araActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_araActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_araActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        facedguncelle();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        TextFieldeVeriCekme();
    }//GEN-LAST:event_jTable1MouseClicked

    private void kisitcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kisitcKeyPressed
        try {
            int i = Integer.parseInt(kisitc.getText());
            hatalabel.setText("");
        } catch (NumberFormatException e1) {
            hatalabel.setText("Lütfen sadece rakam giriniz!");
        }

    }//GEN-LAST:event_kisitcKeyPressed

    private void kisitcKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kisitcKeyReleased

    }//GEN-LAST:event_kisitcKeyReleased

    private void kisiyasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kisiyasKeyPressed
        try {
            int t = Integer.parseInt(kisiyas.getText());
            hatalabel.setText("");
        } catch (NumberFormatException e2) {
            hatalabel.setText("Lütfen sadece rakam giriniz!");
        }    }//GEN-LAST:event_kisiyasKeyPressed

    private void kisiadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kisiadKeyPressed
        hatalabel.setText("");
    }//GEN-LAST:event_kisiadKeyPressed

    private void kisisoyadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kisisoyadKeyPressed
        hatalabel.setText("");
    }//GEN-LAST:event_kisisoyadKeyPressed

    private void kisiadresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kisiadresKeyPressed
        hatalabel.setText("");
    }//GEN-LAST:event_kisiadresKeyPressed

    private void kisitelefonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kisitelefonKeyPressed
        try {
            int t = Integer.parseInt(kisitelefon.getText());
            hatalabel.setText("");
        } catch (NumberFormatException e2) {
            hatalabel.setText("Lütfen sadece rakam giriniz!");
        }
    }//GEN-LAST:event_kisitelefonKeyPressed

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
            java.util.logging.Logger.getLogger(kisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kisi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ara;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton calisanbuton;
    private javax.swing.JLabel hatalabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField kisiad;
    private javax.swing.JTextField kisiadres;
    private javax.swing.JButton kisikaydet;
    private javax.swing.JButton kisisil;
    private javax.swing.JTextField kisisoyad;
    private javax.swing.JTextField kisitc;
    private javax.swing.JTextField kisitelefon;
    private javax.swing.JTextField kisiyas;
    private javax.swing.JLabel uyari;
    private javax.swing.JRadioButton uyebuton;
    // End of variables declaration//GEN-END:variables

}
