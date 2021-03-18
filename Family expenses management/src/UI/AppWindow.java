package UI;

import Controller.ControllerCheltuiala;
import Domain.Cheltuiala;
import Domain.Persoana;

import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Vector;

public class AppWindow {
    public JPanel panelMain;
    private JTable tableMenu;
    private JButton adaugaButton;
    private JButton stergeButton;
    private JButton updateButton;
    private JButton cheltuieliTotalePeCategoriiPeLunaDataButton;
    private JTable tabelOptiuni;
    private JButton cheltuieliTotalePePersoanaDataButton;
    private JButton cheltuieliLunarePeLunaSiCategoriaDataButton;
    private JButton afisareCheltuieliInFisierButton;
    private JPanel panel1;
    private ControllerCheltuiala controller;

    public AppWindow(ControllerCheltuiala c) {

        this.controller = c;
        adaugaInTabel();
        Adauga();
        Sterge();
        Update();
        cheltuieliTotalePeCategoriiPeLunaData();
        cheltuieliTotalePePersoanaData();
        setCheltuieliLunarePeLunaSiCategoriaData();
        afisareInFisier();
        Dimension zeroDim = new Dimension(350, 30);
        adaugaButton.setPreferredSize(zeroDim);
        adaugaButton.setMinimumSize(zeroDim);
        adaugaButton.setMaximumSize(zeroDim);

        stergeButton.setPreferredSize(zeroDim);
        stergeButton.setMinimumSize(zeroDim);
        stergeButton.setMaximumSize(zeroDim);

        updateButton.setPreferredSize(zeroDim);
        updateButton.setMinimumSize(zeroDim);
        updateButton.setMaximumSize(zeroDim);

        cheltuieliLunarePeLunaSiCategoriaDataButton.setPreferredSize(zeroDim);
        cheltuieliLunarePeLunaSiCategoriaDataButton.setMinimumSize(zeroDim);
        cheltuieliLunarePeLunaSiCategoriaDataButton.setMaximumSize(zeroDim);

        cheltuieliTotalePePersoanaDataButton.setPreferredSize(zeroDim);
        cheltuieliTotalePePersoanaDataButton.setMinimumSize(zeroDim);
        cheltuieliTotalePePersoanaDataButton.setMaximumSize(zeroDim);

        afisareCheltuieliInFisierButton.setPreferredSize(zeroDim);
        afisareCheltuieliInFisierButton.setMinimumSize(zeroDim);
        afisareCheltuieliInFisierButton.setMaximumSize(zeroDim);

        cheltuieliTotalePeCategoriiPeLunaDataButton.setPreferredSize(zeroDim);
        cheltuieliTotalePeCategoriiPeLunaDataButton.setMinimumSize(zeroDim);
        cheltuieliTotalePeCategoriiPeLunaDataButton.setMaximumSize(zeroDim);


    }

    public void afisareInFisier() {
        afisareCheltuieliInFisierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.cheltuieliTotalePeFiecareLuna();

                if (Desktop.isDesktopSupported()) {
                    try {
                        File myFile = new File("CheltuieliTotale.txt");
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        // no application registered for PDFs
                    }
                }
            }
        });
    }

    public void setCheltuieliLunarePeLunaSiCategoriaData() {
        cheltuieliLunarePeLunaSiCategoriaDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(2, 2, 2, 2);
                JLabel lunaLabel = new JLabel("Luna:");
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(lunaLabel, gbc);
                JTextField lunaBox = new JTextField(30);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 0;
                panel.add(lunaBox, gbc);
                JLabel categorieLabel = new JLabel("Categorie:");
                gbc.gridwidth = 1;
                gbc.gridx = 0;
                gbc.gridy = 1;
                panel.add(categorieLabel, gbc);
                String[] categorieStrings = {"ALIMENTE", "IMBRACAMINTE", "INTRETINERE", "CULTURA", "RELAXARE"};
                JComboBox categorieList = new JComboBox<String>(categorieStrings);
                gbc.gridwidth = 1;
                gbc.gridx = 1;
                gbc.gridy = 1;
                panel.add(categorieList, gbc);
                JLabel spacer = new JLabel(" ");
                gbc.gridx = 0;
                gbc.gridy = 2;
                panel.add(spacer, gbc);
                JButton btnOk = new JButton("Ok");
                //btnOk.addActionListener(this);
                gbc.gridwidth = 1;
                gbc.gridx = 1;
                gbc.gridy = 2;
                panel.add(btnOk, gbc);
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                btnOk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Vector<Cheltuiala> c = controller.cheltuieliLunarePeLunaSiCategorieData(Integer.parseInt(lunaBox.getText()), Cheltuiala.Categorie.fromString((String) categorieList.getSelectedItem()));
                        DefaultTableModel model = new DefaultTableModel();
                        model.addColumn("Data");
                        model.addColumn("Categoria");
                        model.addColumn("Suma");
                        model.addColumn("Persoana");
                        int i = 0;
                        while (i < c.size()) {
                            model.addRow(new String[]{c.get(i).dataToString(), c.get(i).getCategorie().getString(), String.valueOf(c.get(i).getSuma()),
                                    c.get(i).getPersoana().toString()});
                            i++;
                        }
                        tabelOptiuni.setModel(model);
                        Font headerFont = new Font("Verdana", Font.PLAIN, 20);
                        tabelOptiuni.getTableHeader().setFont(headerFont);
                        tabelOptiuni.setRowHeight(30);
                        tabelOptiuni.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    }
                });
            }
        });
    }

    public void cheltuieliTotalePePersoanaData() {
        cheltuieliTotalePePersoanaDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(2, 2, 2, 2);
                JLabel numeLabel = new JLabel("Nume:");
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(numeLabel, gbc);
                JTextField numeBox = new JTextField(30);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 0;
                panel.add(numeBox, gbc);

                JLabel rolLabel = new JLabel("Rol:");
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 1;
                panel.add(rolLabel, gbc);
                JTextField rolBox = new JTextField(30);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 1;
                panel.add(rolBox, gbc);
                JLabel spacer = new JLabel(" ");
                gbc.gridx = 0;
                gbc.gridy = 1;
                panel.add(spacer, gbc);
                JButton btnOk = new JButton("Ok");
                //btnOk.addActionListener(this);
                gbc.gridwidth = 1;
                gbc.gridx = 0;
                gbc.gridy = 3;
                panel.add(btnOk, gbc);
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                btnOk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Persoana persoana = new Persoana(numeBox.getText(), rolBox.getText());
                        Vector<Cheltuiala> rez = controller.cheltuieliTotalePePersoanaData(persoana);
                        DefaultTableModel model = new DefaultTableModel();
                        model.addColumn("Data");
                        model.addColumn("Categoria");
                        model.addColumn("Suma");
                        int i = 0;
                        while (i < rez.size()) {
                            model.addRow(new String[]{rez.get(i).dataToString(), rez.get(i).getCategorie().getString(), String.valueOf(rez.get(i).getSuma())});
                            i++;
                        }
                        tabelOptiuni.setModel(model);
                        Font headerFont = new Font("Verdana", Font.PLAIN, 20);
                        tabelOptiuni.getTableHeader().setFont(headerFont);
                        tabelOptiuni.setRowHeight(30);
                        tabelOptiuni.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    }
                });
                frame.revalidate();
                frame.repaint();
            }
        });
    }

    public void cheltuieliTotalePeCategoriiPeLunaData() {
        cheltuieliTotalePeCategoriiPeLunaDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(2, 2, 2, 2);
                JLabel lunaLabel = new JLabel("Luna:");
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(lunaLabel, gbc);
                JTextField lunaBox = new JTextField(30);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 0;
                panel.add(lunaBox, gbc);
                JLabel spacer = new JLabel(" ");
                gbc.gridx = 0;
                gbc.gridy = 2;
                panel.add(spacer, gbc);
                JButton btnOk = new JButton("Ok");
                //btnOk.addActionListener(this);
                gbc.gridwidth = 1;
                gbc.gridx = 1;
                gbc.gridy = 1;
                panel.add(btnOk, gbc);
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                btnOk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Map<Cheltuiala.Categorie, Double> map = controller.cheltuieliTotalePeCategoriePeLunaData(Integer.parseInt(lunaBox.getText()));

                        DefaultTableModel model = new DefaultTableModel();
                        model.addColumn("Categoria");
                        model.addColumn("Suma totala");
                        for (Map.Entry<Cheltuiala.Categorie, Double> entry : map.entrySet()) {
                            model.addRow(new String[]{entry.getKey().getString(), String.valueOf(entry.getValue())});

                        }
                        tabelOptiuni.setModel(model);
                        Font headerFont = new Font("Verdana", Font.PLAIN, 20);
                        tabelOptiuni.getTableHeader().setFont(headerFont);
                        tabelOptiuni.setRowHeight(30);
                        tabelOptiuni.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    }
                });

            }
        });
    }

    public void Update() {
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(2, 2, 2, 2);

//                JLabel idLabel = new JLabel("Id:");
//                gbc.fill = GridBagConstraints.HORIZONTAL;
//                gbc.gridx = 0;
//                gbc.gridy = 0;
//                panel.add(idLabel, gbc);
//                JTextField idBox = new JTextField(30);
//                gbc.gridwidth = 2;
//                gbc.gridx = 1;
//                gbc.gridy = 0;
//                panel.add(idBox, gbc);

                int row = tableMenu.getSelectedRow();
                if (row == -1) {
                    showMessageDialog(null, "Selecteaza o inregistrare!");
                    return;
                }

                Object id = tableMenu.getValueAt(row, 0);
                int identifier = Integer.parseInt(String.valueOf(id));

                JLabel dataLabel = new JLabel("Data:");
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(dataLabel, gbc);
                JTextField dataBox = new JTextField(30);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 0;
                panel.add(dataBox, gbc);

                JLabel categorieLabel = new JLabel("Categorie:");
                gbc.gridwidth = 1;
                gbc.gridx = 0;
                gbc.gridy = 1;
                panel.add(categorieLabel, gbc);

                String[] categorieStrings = {"ALIMENTE", "IMBRACAMINTE", "INTRETINERE", "CULTURA", "RELAXARE"};
                JComboBox categorieList = new JComboBox<String>(categorieStrings);
                gbc.gridwidth = 1;
                gbc.gridx = 1;
                gbc.gridy = 1;
                panel.add(categorieList, gbc);

                JLabel sumaLabel = new JLabel("Suma:");
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 2;
                panel.add(sumaLabel, gbc);
                JTextField sumaBox = new JTextField(30);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 2;
                panel.add(sumaBox, gbc);

                JLabel numeLabel = new JLabel("Nume:");
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 3;
                panel.add(numeLabel, gbc);
                JTextField numeBox = new JTextField(30);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 3;
                panel.add(numeBox, gbc);

                JLabel rolLabel = new JLabel("Rol:");
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 4;
                panel.add(rolLabel, gbc);
                JTextField rolBox = new JTextField(30);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 4;
                panel.add(rolBox, gbc);

                JLabel spacer = new JLabel(" ");
                gbc.gridx = 0;
                gbc.gridy = 5;
                panel.add(spacer, gbc);
                JButton btnOk = new JButton("Ok");

                gbc.gridwidth = 1;
                gbc.gridx = 0;
                gbc.gridy = 6;
                panel.add(btnOk, gbc);
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);

                dataBox.setText( String.valueOf(tableMenu.getValueAt(row,1)));
                categorieList.setSelectedItem(String.valueOf(tableMenu.getValueAt(row,2)));
                sumaBox.setText(String.valueOf(tableMenu.getValueAt(row,3)));
                String persoana= String.valueOf(tableMenu.getValueAt(row,4));

                numeBox.setText(String.valueOf(persoana.split(",")[0]));
                rolBox.setText(String.valueOf(persoana.split(",")[1]));
                btnOk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        Persoana persoana = new Persoana(numeBox.getText(), rolBox.getText());
                        try {
                            Cheltuiala cheltuiala = new Cheltuiala(new SimpleDateFormat("dd/MM").parse(dataBox.getText()),
                                    Cheltuiala.Categorie.fromString((String) categorieList.getSelectedItem()), Double.parseDouble(sumaBox.getText()), persoana);
                            controller.update(identifier, cheltuiala);
                        } catch (Exception exp) {
                            showMessageDialog(null, exp.getMessage());
                        }
                        adaugaInTabel();
                        dataBox.setText(null);
                        sumaBox.setText(null);
                        numeBox.setText(null);
                        rolBox.setText(null);
                    }
                });

            }
        });

    }

    public void Sterge() {

        stergeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableMenu.getSelectedRow();
                if (row == -1) {
                    showMessageDialog(null, "Selecteaza o inregistrare!");
                    return;
                }

                Object id = tableMenu.getValueAt(row, 0);
                int identifier = Integer.parseInt(String.valueOf(id));
                try {
                    controller.delete(identifier);
                } catch (Exception exception) {
                    exception.getMessage();

                }
                adaugaInTabel();


            }
        });
    }

    public void Adauga() {
        adaugaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(2, 2, 2, 2);
                JLabel dataLabel = new JLabel("Data:");
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(dataLabel, gbc);
                JTextField dataBox = new JTextField(30);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 0;
                panel.add(dataBox, gbc);

                JLabel categorieLabel = new JLabel("Categorie:");
                gbc.gridwidth = 1;
                gbc.gridx = 0;
                gbc.gridy = 1;
                panel.add(categorieLabel, gbc);

                String[] categorieStrings = {"ALIMENTE", "IMBRACAMINTE", "INTRETINERE", "CULTURA", "RELAXARE"};
                JComboBox categorieList = new JComboBox<String>(categorieStrings);
                gbc.gridwidth = 1;
                gbc.gridx = 1;
                gbc.gridy = 1;
                panel.add(categorieList, gbc);

                JLabel sumaLabel = new JLabel("Suma:");
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 2;
                panel.add(sumaLabel, gbc);
                JTextField sumaBox = new JTextField(30);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 2;
                panel.add(sumaBox, gbc);

                JLabel numeLabel = new JLabel("Nume:");
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 3;
                panel.add(numeLabel, gbc);
                JTextField numeBox = new JTextField(30);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 3;
                panel.add(numeBox, gbc);

                JLabel rolLabel = new JLabel("Rol:");
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 4;
                panel.add(rolLabel, gbc);
                JTextField rolBox = new JTextField(30);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 4;
                panel.add(rolBox, gbc);

                JLabel spacer = new JLabel(" ");
                gbc.gridx = 0;
                gbc.gridy = 5;
                panel.add(spacer, gbc);
                JButton btnOk = new JButton("Ok");

                gbc.gridwidth = 1;
                gbc.gridx = 0;
                gbc.gridy = 6;
                panel.add(btnOk, gbc);
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

                btnOk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Object source = e.getSource();
                        if (source == btnOk) {

                            Persoana persoana = new Persoana(numeBox.getText(), rolBox.getText());
                            try {
                                Cheltuiala cheltuiala = new Cheltuiala(new SimpleDateFormat("dd/MM").parse(dataBox.getText()),
                                        Cheltuiala.Categorie.fromString((String) categorieList.getSelectedItem()), Double.parseDouble(sumaBox.getText()), persoana);
                                controller.add(cheltuiala);
                            } catch (Exception exp) {
                                showMessageDialog(null, exp.getMessage());
                            }
                            adaugaInTabel();
                            dataBox.setText(null);
                            sumaBox.setText(null);
                            numeBox.setText(null);
                            rolBox.setText(null);


                        }
                    }
                });
            }
        });
    }

    private void adaugaInTabel() {

        DefaultTableModel model = new DefaultTableModel();
        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn("Tabel Optiuni");
        model.addColumn(" ");
        model.addColumn("Data");
        model.addColumn("Categoria");
        model.addColumn("Suma");
        model.addColumn("Persoana");
        Vector<Cheltuiala> c = controller.getAll();
        int i = 0;
        while (i < c.size()) {
            model.addRow(new String[]{String.valueOf(i), c.get(i).dataToString(), c.get(i).getCategorie().getString(), c.get(i).getSuma().toString(),
                    c.get(i).getPersoana().toString()});
            i++;
        }
        tableMenu.setModel(model);
        Font headerFont = new Font("Verdana", Font.PLAIN, 20);
        Font headerFont1 = new Font("Verdana", Font.PLAIN, 20);
        tableMenu.getTableHeader().setFont(headerFont);
        tabelOptiuni.getTableHeader().setFont(headerFont1);
        tableMenu.setRowHeight(30);
        tableMenu.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableMenu.getTableHeader().setBackground(Color.orange);
        tableMenu.getTableHeader().setForeground(Color.black);
        tabelOptiuni.setModel(model1);
        tabelOptiuni.getTableHeader().setBackground(Color.orange);
        tabelOptiuni.getTableHeader().setForeground(Color.black);


    }


}
