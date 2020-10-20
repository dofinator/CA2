/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

/**
 *
 * @author lukas
 */
public class Main {
    public static void main(String[] args) {
    PersonFacade p = new PersonFacade();
        System.out.println(p.getAllPersonsByHobby("fodbold"));

    }
}
