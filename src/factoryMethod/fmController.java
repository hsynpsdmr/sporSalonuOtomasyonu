/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoryMethod;

/**
 *
 * @author Hüseyin
 */
public class fmController {
 
    public static tablo createtablo(Class aClass) throws IllegalAccessException, InstantiationException {
            return (tablo) aClass.newInstance();
    }
}


