package com.company;

import Controller.ControllerCheltuiala;
import Repository.RepositoryCheltuiala;
import UI.AppWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        String numeFisier= "Cheltuieli.txt";
        RepositoryCheltuiala repo= new RepositoryCheltuiala(numeFisier);
        ControllerCheltuiala controller= new ControllerCheltuiala(repo);
        JFrame frame= new JFrame("AppWindow");
        frame.setContentPane(new AppWindow(controller).panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
