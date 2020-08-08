/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoryMethod;

import sporsalonuForm.branslar;
import sporsalonuForm.calisan;
import sporsalonuForm.finans;
import sporsalonuForm.kisi;
import sporsalonuForm.urunler;
import sporsalonuForm.uye;

/**
 *
 * @author Hüseyin
 */
public class factoryMain {

    public static void main(String args[]) {
        try {
            branslar branslar = (branslar) fmController.createtablo(branslar.class);
            branslar.tabloListele();
            branslar.yenile();
            
            calisan calisan = (calisan) fmController.createtablo(calisan.class);
            calisan.tabloListele();
            calisan.yenile();
            
            finans finans = (finans) fmController.createtablo(finans.class);
            finans.tabloListele();
            finans.yenile();
            
            kisi kisi = (kisi) fmController.createtablo(kisi.class);
            kisi.tabloListele();
            kisi.yenile();
            
            urunler urunler = (urunler) fmController.createtablo(urunler.class);
            urunler.tabloListele();
            urunler.yenile();
            
            uye uye = (uye) fmController.createtablo(uye.class);
            uye.tabloListele();
            uye.yenile();
            
          
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

