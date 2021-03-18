package UI;

import Controller.ControllerCheltuiala;
import Domain.Cheltuiala;
import Domain.Persoana;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UICheltuiala {
    private ControllerCheltuiala controller;

    public UICheltuiala(ControllerCheltuiala c) {
        this.controller = c;
    }

    public int Citire(String sir) {
        System.out.print(sir);
        try {
            //citire sir si parsare
            Scanner scanner = new Scanner(System.in);
            int l = scanner.nextInt();                //in int
            return l;
        } catch (Exception exp) {
            System.out.println("Ati gresit! Optiunea trebuie sa fie un numar intreg!");
            return Citire(sir);                //apel recursiv pe orice eroare
        }
    }

    public void add() throws Exception {  // adauga o cheltuiala citita de la tastatura
        System.out.println("Data: ");
        Date data = null;
        Scanner scanner = new Scanner(System.in);
        try {

            data = new SimpleDateFormat("dd/MM").parse(scanner.nextLine()); // citire data
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Categorie: ");
        int cod_categorie = scanner.nextInt();
        Cheltuiala.Categorie categorie = Cheltuiala.Categorie.fromCode(cod_categorie);
        System.out.println("Suma: ");
        Double suma = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Persoana ");
        System.out.println("Nume: ");
        String nume = scanner.nextLine();
        System.out.println("Rol in familie: ");
        String rol = scanner.nextLine();
        Persoana persoana = new Persoana(nume, rol);
        Cheltuiala cheltuiala = new Cheltuiala(data, categorie, suma, persoana);
        controller.add(cheltuiala);
    }

    public void delete() throws Exception {  // sterge categoria din baza de date

        System.out.println("Dati indexul inregistrarii pe care doriti sa o stergeti: ");
        Scanner scanner = new Scanner(System.in);
        int key = scanner.nextInt();
        controller.delete(key);
    }

    public void update() throws Exception { // actualizeaza categoria
        System.out.println("Dati indexul inregistrarii pe care doriti sa o actualizati: ");
        Scanner scanner = new Scanner(System.in);
        int key = scanner.nextInt();
        scanner.nextLine();
        if (!controller.searchByIndex(key)) {
            throw new Exception("Inregistrarea pe care doriti sa o actualizati nu exista in baza de date!");
        }
        System.out.println("Dati noua inregistrare: ");
        System.out.println("Data: ");
        Date data = null;
        try {
            data = new SimpleDateFormat("dd/MM").parse(scanner.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Categorie: ");
        Cheltuiala.Categorie categorie = Cheltuiala.Categorie.fromString(scanner.nextLine());
        System.out.println("Suma: ");
        Double suma = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Persoana: ");
        System.out.println("Nume: ");
        String nume = scanner.nextLine();
        System.out.println("Rol in familie: ");
        String rol = scanner.nextLine();
        Persoana persoana = new Persoana(nume, rol);
        Cheltuiala cheltuiala = new Cheltuiala(data, categorie, suma, persoana);

        controller.update(key, cheltuiala);
    }

    // afiseaza cheltuielile pe categorie dintr o luna data de la tastatura
    public void cheltuieliTotalePeCategoriePeLunaData() {
        Vector<Cheltuiala> c = controller.getAll();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Dati luna: ");
        int luna = scanner.nextInt();
        Map<Cheltuiala.Categorie, Double> map = controller.cheltuieliTotalePeCategoriePeLunaData(luna);
        System.out.println("Luna: " + luna);
        capTabel2();
        for (Map.Entry<Cheltuiala.Categorie, Double> entry : map.entrySet()) {

            System.out.format("|%-25s|%13.2f|\n", entry.getKey().getString(), entry.getValue());    //afisare formatata
        }
    }

    // afiseaza toate cheltuielile unei persoane date
    public void cheltuieliTotalePePersoanaData() {
        Vector<Cheltuiala> c = controller.getAll();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Dati persoana: ");
        System.out.println("Nume: ");
        String nume = scanner.nextLine();
        System.out.println("Rol in familie: ");
        String rol = scanner.nextLine();
        Persoana p = new Persoana(nume, rol);
        Vector<Cheltuiala> rez = controller.cheltuieliTotalePePersoanaData(p);
        capTabel3();
        int i = 0;
        while (i < rez.size()) {
            System.out.format("|%-14s|%-17s|%12.2f|\n", rez.get(i).dataToString(), rez.get(i).getCategorie().getString(), rez.get(i).getSuma());
            i++;   // afisare formatata
        }
    }

    // afiseaza toate cheltuielile din luna si categoria data de la tastatura
    public void cheltuieliLunarePeLunaSiCategorieData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Luna: ");
        int luna = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Categoria: ");
        //Cheltuiala.Categorie categoria = Cheltuiala.Categorie.fromString(scanner.nextLine());
        Cheltuiala.Categorie categoria = Cheltuiala.Categorie.fromCode(scanner.nextInt());
        Vector<Cheltuiala> c = controller.cheltuieliLunarePeLunaSiCategorieData(luna, categoria);
        capTabel1();
        int i = 0;
        while (i < c.size()) {

            System.out.format("|%-6s|%-17s|%-12.2f|%-16s|\n", c.get(i).dataToString(), c.get(i).getCategorie().getString(), c.get(i).getSuma(),
                    c.get(i).getPersoana().toString());    //afisare formatata
            i++;
        }
    }

    // scrie in fisier cheltuielile totale pe fiecare luna
    public void afisareCheltuieliTotalePeFiecareLunaInFisier() {
        controller.cheltuieliTotalePeFiecareLuna();
    }

    public void capTabel1() {
        String sir = "| Data |     Categorie   |    Suma    |    Persoana    |";
        String linii = "========================================================";
        System.out.println(linii);
        System.out.println(sir);
        System.out.println(linii);
    }

    public void capTabel2() {
        String sir = "|        Categorie        | Suma Totala |";
        String linii = "=========================================";
        System.out.println(linii);
        System.out.println(sir);
        System.out.println(linii);
    }

    public void capTabel3() {
        String sir = "| Data |        Categorie        |    Suma    |";
        String linii = "==============================================";
        System.out.println(linii);
        System.out.println(sir);
        System.out.println(linii);
    }

    public void afisareCheltuieli() {
        Vector<Cheltuiala> c = controller.getAll();
        capTabel1();
        int i = 0;
        while (i < c.size()) {
            System.out.format("|%-6s|%-17s|%-12.2f|%-16s|\n", c.get(i).dataToString(), c.get(i).getCategorie().getString(), c.get(i).getSuma(),
                    c.get(i).getPersoana().toString());    //afisare formatata
            i++;
        }
    }

    public long displayMenu() {
        System.out.println("----------MENU----------");
        System.out.println("1. Afisare cheltuieli");
        System.out.println("2. Adauga cheltuiala");
        System.out.println("3. Sterge cheltuiala");
        System.out.println("4. Update cheltuiala");
        System.out.println("5. Afisarea cheltuielilor totale pe categorii pe o luna data");
        System.out.println("6. Afisarea cheltuielilor totale pentru o persoana data");
        System.out.println("7. Afisarea cheltuielilor lunare ( luna si categoria data)");
        System.out.println("8. Afisarea cheltuielilor totale pe fiecare luna(in fisier)");
        long Opt = Citire("optiunea dvs:");
        return Opt;
    }

    public void runApp() {

        long Op = displayMenu();
        while (Op != 0) {
            try {
                switch ((int) Op) {
                    case 1:
                        afisareCheltuieli();
                        break;
                    case 2:
                        add();
                        System.out.println("Inregistrarea a fost adaugata cu succes!");
                        break;
                    case 3:
                        delete();
                        System.out.println("Inregistrarea a fost stearsa cu succes!");
                        break;
                    case 4:
                        update();
                        System.out.println("Inregistrarea a fost actualizata cu succes!");
                        break;
                    case 5:
                        cheltuieliTotalePeCategoriePeLunaData();
                        break;
                    case 6:
                        cheltuieliTotalePePersoanaData();
                        break;
                    case 7:
                        cheltuieliLunarePeLunaSiCategorieData();
                        break;
                    case 8:
                        afisareCheltuieliTotalePeFiecareLunaInFisier();
                        break;
                    default:
                        System.out.println("Ai gresit optiunea, mai incearca");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Op = displayMenu();
        }
        System.out.println("Program terminat");
    }
}


