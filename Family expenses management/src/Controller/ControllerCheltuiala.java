package Controller;

import Domain.Cheltuiala;
import Domain.Persoana;
import Repository.RepositoryCheltuiala;

import javax.imageio.ImageTranscoder;
import javax.xml.crypto.Data;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ControllerCheltuiala {

    private RepositoryCheltuiala repo;

    public ControllerCheltuiala(RepositoryCheltuiala r) {
        this.repo = r;

    }

    public void add(Cheltuiala c) throws Exception { // adauga cheltuiala
        repo.add(c);

    }
    public void delete(int key) throws Exception { // seterge cheltuiala
        repo.delete(key);
    }

    public void update(int key, Cheltuiala c) throws Exception { // actualizeaza cheltuiala
        repo.update(key, c);

    }

    public boolean searchByIndex(int key) {
        return repo.searchByIndex(key);
    } // cauta dupa index -true sau false

    public Vector<Cheltuiala> getAll() {
        return repo.getAll();
    }

    public Cheltuiala getElement(int key) {
        return repo.getElement(key);
    }

    // returneaza cheltuielile totale pe categorii pe o luna data de la tastatura
    public Map<Cheltuiala.Categorie, Double> cheltuieliTotalePeCategoriePeLunaData(int luna) {
        Vector<Cheltuiala> c = repo.getAll();
        Map<Cheltuiala.Categorie, Double> map = new HashMap<Cheltuiala.Categorie, Double>();
        for (int i = 0; i < c.size(); i++) {
            if (c.get(i).getLuna() == luna) {
                if (map.get(c.get(i).getCategorie()) == null) {
                    map.put(c.get(i).getCategorie(), c.get(i).getSuma());

                } else {

                    map.put(c.get(i).getCategorie(), map.get(c.get(i).getCategorie()) + c.get(i).getSuma());
                }
            }
        }
        return map;
    }

    /* public Map<String, Double> cheltuieliTotalePePersoanaData(Persoana persoana) {
         Vector<Cheltuiala> c = repo.getAll();
         Map<String, Double> map = new HashMap<String, Double>();
         for (int i = 0; i < c.size(); i++) {
             if (c.get(i).getPersoana().equals(persoana)) {
                 if (map.get(c.get(i).getCategorie()) == null) {
                     map.put(c.get(i).getCategorie(), c.get(i).getSuma());

                 } else {

                     map.put(c.get(i).getCategorie(), map.get(c.get(i).getCategorie()) + c.get(i).getSuma());
                 }

             }
         }
         return map;

     }
     */

    // returneaza toate cheltuielile persoanei date de la tastatura
    public Vector<Cheltuiala> cheltuieliTotalePePersoanaData(Persoana persoana) {
        Vector<Cheltuiala> c = repo.getAll();
        Vector<Cheltuiala> rez = new Vector<>();
        for (int i = 0; i < c.size(); i++) {
            if (c.get(i).getPersoana().equals(persoana)) {
                rez.add(c.get(i));

            }
        }
        return rez;
    }
    // afiseaza cheltuielile din luna si categoria data
    public Vector<Cheltuiala> cheltuieliLunarePeLunaSiCategorieData(int luna, Cheltuiala.Categorie categoria) {
        Vector<Cheltuiala> c = repo.getAll();
        Vector<Cheltuiala> rez = new Vector<>();

        for (int i = 0; i < c.size(); i++) {
            if (Double.compare(c.get(i).getLuna(), luna) == 0 && (c.get(i).getCategorie().equals(categoria))) { // verifca luna si categoria
                rez.add(c.get(i));
            }
        }
        return rez;
    }

    // scrie in fisier cheltuielile totale pe fiecare luna
    public void cheltuieliTotalePeFiecareLuna() {
        Vector<Cheltuiala> c = repo.getAll();
        Map<Integer, Double> map = new HashMap<>();
        for (int i = 0; i < c.size(); i++) {

            if (map.get(c.get(i).getLuna()) == null) {   // daca suma nu este nula ( pentru prima inregistrare)
                map.put(c.get(i).getLuna(), c.get(i).getSuma());

            } else {

                map.put(c.get(i).getLuna(), map.get(c.get(i).getLuna()) + c.get(i).getSuma());
            }

        }
        try {
            FileWriter myWriter = new FileWriter("cheltuieliTotale.txt");  // scriere in fisier
            for (Map.Entry<Integer, Double> entry : map.entrySet()) {
                myWriter.write("Luna: " + entry.getKey() + " Suma totala: " +  new BigDecimal(entry.getValue()).setScale(2, RoundingMode.HALF_UP) + "\n");

            }
            myWriter.close();
            System.out.println("Fisierul s-a scris cu succes!");
        } catch (IOException e) {
            System.out.println("Eroare la scrierea in fisier!");
            e.printStackTrace();
        }
    }


}
