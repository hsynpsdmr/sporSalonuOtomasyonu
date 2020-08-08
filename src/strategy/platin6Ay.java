/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strategy;

import sporsalonuForm.uye;

/**
 *
 * @author Hüseyin
 */
public class platin6Ay implements uyelikTipi {

    @Override
    public void uyeliktipi() {
        uye u = new uye();
        u.setUcret(600);
        u.UyeGelirEkle();
    }

}
