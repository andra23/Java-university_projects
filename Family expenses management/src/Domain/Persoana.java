package Domain;

public class Persoana {
    private String nume;
    private String rol;

    public Persoana() { // constructor implicit
        this.nume = null;
        this.rol = null;
    }

    public Persoana(String nume, String rol) { // constructor cu parametri
        this.nume = nume;
        this.rol = rol;
    }

    public Persoana(Persoana p) { // constructor de copiere
        this.nume = p.nume;
        this.rol = p.rol;
    }

    public String getNume() {     // get si set
        return this.nume;
    }

    public String getRol() {
        return this.rol;
    }

    public void setNume(String n) {
        this.nume = n;
    }

    public void setRol(String r) {
        this.rol = r;
    }

    public String toString() {  // transforma un obect persoana in string
        return this.nume + "," + this.rol;
    }

    @Override
    public boolean equals(Object o) { // verifica daca 2 obiecte de tip persoana sunt egale
        if (o instanceof Persoana) {
            Persoana p = (Persoana) o;

            return (nume.toLowerCase().equals(p.nume.toLowerCase())) && (rol.equals(p.rol));
        }
        return false;
    }


}
