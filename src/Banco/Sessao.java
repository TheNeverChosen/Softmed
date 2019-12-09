/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco;

/**
 *
 * @author Fernando
 */
public class Sessao {
    private static String LoginPac, LoginMed;
    private static int id;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Sessao.id = id;
    }

    public static String getLoginPac() {
        return LoginPac;
    }

    public static void setLoginPac(String LoginPac) {
        Sessao.LoginPac = LoginPac;
    }

    public static String getLoginMed() {
        return LoginMed;
    }

    public static void setLoginMed(String LoginMed) {
        Sessao.LoginMed = LoginMed;
    }

    
}
