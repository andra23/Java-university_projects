package Domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cheltuiala {
    public enum Categorie {   // constructor tip enum pentru categorie
        ALIMENTE(0),
        IMBRACAMINTE(1),
        INTRETINERE(2),
        CULTURA(3),
        RELAXARE(4);

        private int cod;

        Categorie(int cod) {
            this.cod = cod;
        }

        public static Categorie fromCode(int cod) {  // transforma o categorie in string din codul dat
            switch (cod) {
                case 0:
                    return ALIMENTE;
                case 1:
                    return IMBRACAMINTE;
                case 2:
                    return INTRETINERE;
                case 3:
                    return CULTURA;
                case 4:
                    return RELAXARE;
                default:
                    return null;
            }
        }

        public static Categorie fromString(String c) { // afla codul categoriei dintr un string dat
            switch (c) {
                case "ALIMENTE":
                    return ALIMENTE;
                case "IMBRACAMINTE":
                    return IMBRACAMINTE;
                case "INTRETINERE":
                    return INTRETINERE;
                case "CULTURA":
                    return CULTURA;
                case "RELAXARE":
                    return RELAXARE;
                default:
                    return null;
            }
        }

        public int getCod() {
            return cod;
        }

        public String getString() {  // retuneaza categoria ca string
            switch (this) {
                case ALIMENTE:
                    return "ALIMENTE";
                case IMBRACAMINTE:
                    return "IMBRACAMINTE";
                case INTRETINERE:
                    return "INTRETINERE";
                case CULTURA:
                    return "CULTURA";
                case RELAXARE:
                    return "RELAXARE";
                default:
                    return null;

            }
        }
    }
    private Date data;
    private Categorie categorie;
    private Double suma;
    private Persoana persoana;

    public Cheltuiala() {
        this.data = null;
        this.categorie = null;
        this.suma = 0.0;
        this.persoana = new Persoana();
    }

    public Cheltuiala(Date data, Categorie categorie, Double suma, Persoana persoana) {  // constr cu param
        this.data = data;
        this.categorie = categorie;
        this.suma = suma;
        this.persoana = persoana;
    }

    public Cheltuiala(Cheltuiala c) {  // constr de copiere
        this.data = c.data;
        this.categorie = c.categorie;
        this.suma = c.suma;
        this.persoana = c.persoana;
    }

    public Date getData() {
        return this.data;
    }

    public Categorie getCategorie() {
        return this.categorie;
    }

    public Double getSuma() {
        return this.suma;
    }

    public Persoana getPersoana() {
        return this.persoana;
    }

    public void setData(Date d) {
        this.data = d;

    }

    public void setCategorie(Categorie c) {
        this.categorie = c;
    }

    public void setSuma(Double s) {
        this.suma = s;
    }

    public void setPersoana(Persoana p) {
        this.persoana = p;
    }

    public String toString() { //  retuneaza stringul pentru obiectul curent
        String pattern = "dd/MM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(this.data);
        return date + "," + this.categorie.getString() + "," + this.suma + "," + this.persoana.toString();
    }

    public String dataToString() {  // converteste data la string
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.data);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day + "/" + month;
    }

    public int getLuna() {  // retuneaza luna
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.data);
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    @Override
    public boolean equals(Object o) { // verifica daca doua obiecte de tip cheltuiala sunt egale
        if (o instanceof Cheltuiala) {
            Cheltuiala c = (Cheltuiala) o;

            return (dataToString().equals(c.dataToString())) && (categorie.equals(c.categorie)) && (Double.compare(this.suma, c.suma) == 0) && (persoana.equals(c.persoana));
        }
        return false;
    }


}
