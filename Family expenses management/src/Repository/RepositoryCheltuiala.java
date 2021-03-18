package Repository;

import Domain.Cheltuiala;
import Domain.Persoana;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class RepositoryCheltuiala {
    private Vector<Cheltuiala> vectorCheltuieli;
    private String numeFisier;

    public RepositoryCheltuiala() { // constructor implicit
        vectorCheltuieli = null;
        this.numeFisier = null;
    }

    public RepositoryCheltuiala(String numeFisier) {  // constructorul care citeste fisierul primit ca parametru
        this.vectorCheltuieli = new Vector<Cheltuiala>();
        this.numeFisier = numeFisier;
        readFromFile();
    }

    public RepositoryCheltuiala(Vector<Cheltuiala> v, String numeFisier) {
        this.vectorCheltuieli = v;
        this.numeFisier = numeFisier;
    }

    public boolean search(Cheltuiala c) {

        return vectorCheltuieli.contains(c);
    }

    public boolean searchByIndex(int key) {
        return (key >= 0 && key < vectorCheltuieli.size());
    }

    // returneaza true daca cheltuiala se gaseste in baza de date si false daca nu
    public void add(Cheltuiala c) throws Exception {  // adauga o cheltuiala in fisier

        boolean exista = search(c);
        if (!exista) {
            vectorCheltuieli.add(c);
            saveToFile();
        } else throw new Exception("Aceasta inregistrare exista deja in baza de date!");

    }

    public void delete(int key) throws Exception { // sterge o cheltuiala din fisier

        if (key >= 0 && key < vectorCheltuieli.size()) {
            vectorCheltuieli.remove(key);
            saveToFile();
        } else throw new Exception("Inregistrarea pe care doriti sa o stergeti nu exista in baza de date!");


    }

    public void update(int key, Cheltuiala c) throws Exception {  // actualizeaza o cheltuiala

        if (key >= 0 && key < vectorCheltuieli.size()) {
            vectorCheltuieli.setElementAt(c, key);
            saveToFile();
        } else throw new Exception("Inregistrarea pe care doriti sa o actualizati nu exista in baza de date!");


    }

    public Vector<Cheltuiala> getAll() {
        return vectorCheltuieli;
    } // returneaza toate cheltuielile

    public Cheltuiala getElement(int key) {
        return vectorCheltuieli.get(key);
    } // retuneaza o cheltuiala de la o pozitie  data

    public void readFromFile() {  // citeste din fisier
        try {

            FileInputStream fileInput = new FileInputStream(numeFisier);
            InputStreamReader isr = new InputStreamReader(fileInput);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while ((line = br.readLine()) != null && !line.isEmpty()) {  // atat timp cat exista linii si nu sunt nule
                String[] components = line.split(",");    // face split la linia citita
                Date data = new SimpleDateFormat("dd/MM").parse(components[0].trim());  // convertesc string la data
                Cheltuiala.Categorie categorie = Cheltuiala.Categorie.fromString(components[1].trim());
                // fromString afla din stringul dat codul categoriei
                Double suma = Double.parseDouble(components[2].trim()); // conversie la double pentru suma
                String nume = components[3].trim();
                String rol = components[4].trim();
                Persoana persoana = new Persoana(nume, rol);
                Cheltuiala cheltuiala = new Cheltuiala(data, categorie, suma, persoana);
                vectorCheltuieli.add(cheltuiala);
            }
        } catch (IOException | ParseException e) {
            System.err.println("Este o problema la citirea din fisier!");
        }

    }

    public void saveToFile() throws IOException {  // salvarea in fisier
        File fileOutput = new File(numeFisier);
        FileOutputStream fos = new FileOutputStream(fileOutput);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        for (int i = 0; i < vectorCheltuieli.size(); i++) {
            bw.write(vectorCheltuieli.get(i).toString());
            bw.newLine();
        }
        bw.close();

    }


}
