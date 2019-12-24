import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
/**
 * Classe Contribuinte
 * @author Nuno Rei, Joao Marques, Luis Abreu
 * @version Maio 2018
 */
public abstract class Contribuinte implements Comparable<Contribuinte>, Serializable {
    /** Numero de Identificacao Fiscal */
    private String NIF;
    /** Email do Contribuinte */
    private String email;
    /** Nome do Contribuinte */
    private String nome;
    /** Morada do Contribuinte */
    private String morada;
    /** Password do Contribuinte */
    private String password;
    
    /** 
     * Construtor vazio
     */
    public Contribuinte() {
        this.NIF = "n/d";
        this.email = "n/d";
        this.nome = "n/d";
        this.morada = "n/d";
        this.password = "";
    }
    /** 
     * Construtor parametrizado
     * @param NIF,email,nome,morada,password
     */
    public Contribuinte(String NIF, String email, String nome, String morada, String password) {
        this.NIF = NIF;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.password = password;
    }
    /** 
     * Construtor copia 
     */
    public Contribuinte(Contribuinte c) {
        this.NIF = c.getNIF();
        this.email = c.getEmail();
        this.nome = c.getNome();
        this.morada = c.getMorada();
        this.password = c.getPassword();
    }
    /**
     * Obter Identificador de Contribuinte
     * @return Identificador do Contribuinte
     */
    public String getNIF() {
        return this.NIF;
    }
    /**
     * Obter Email do Contribuinte
     * @return Email do Contribuinte
     */
    public String getEmail() {
        return this.email;
    }
    /**
     * Obter Nome do Contribuinte
     * @return Nome do Contribuinte
     */
    public String getNome() {
        return this.nome;
    }
    /**
     * Obter Morada do Contribuinte
     * @return Morada do Contribuinte
     */
    public String getMorada() {
        return this.morada;
    }
    /**
     * Obter Password do Contribuinte
     * @return Password do Contribuinte
     */
    private String getPassword() {
        return this.password;
    }
    /**
     * Altera o Identificador do Contribuinte
     * @param Identificador do Contribuinte
     */
    public void setNIF(String nIF) {
        this.NIF = nIF;
    }
    /**
     * Altera o Email do Contribuinte
     * @param Email do Contribuinte
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Altera o Nome do Contribuinte
     * @param Nome do Contribuinte
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Altera a Morada do Contribuinte
     * @param Morada do Contribuinte
     */
    public void setMorada(String morada) {
        this.morada = morada;
    }
    /**
     * Altera a Password do Contribuinte
     * @param Password do Contribuinte
     */
    private void setPassword(String password) {
        this.password = password;
    }
    /**
    * Compara a igualdade com outro objecto
    * @param objeto
    * @return True se igual, False caso contrario
    */
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Contribuinte o = (Contribuinte) obj;
        return o.getNIF().equals(this.NIF) && o.getEmail().equals(this.email) && 
        o.getNome().equals(this.nome) && o.getMorada() == this.morada && o.getPassword() == this.password;
    }
    /**
     * Devolve uma representação no formato textual do Contribuinte
     * @return String com toda a informacao sobre o Contribuinte 
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.nome).append("\n");
        sb.append("NIF: ").append(this.NIF).append("\n");
        sb.append("Email: ").append(this.email).append("\n");
        sb.append("Morada: ").append(this.morada).append("\n");
        return sb.toString();
    }
    /**
     * Clone do Contribuinte 
     */
    public abstract Contribuinte clone();
    /**
     * Compara os NIFs de dois Contribuintes
     * @param Contribinte 
     */
    public int compareTo(Contribuinte c) {
        return c.getNIF().compareTo(c.getNIF());
    }
    /**
     * Verificar se a password esta correta
     * @param Password
     * @return True se correta, False caso contrario
     */
    public boolean autenticador(String pass) 
    {
        return pass.equals(this.getPassword());
    }   
}
