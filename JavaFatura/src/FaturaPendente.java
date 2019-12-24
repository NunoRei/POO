import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;
/**
 * Subclasse de Fatura, que guarda tambem a lista de Atividades Economicas da empresa emitente da fatura. So empresas com mais do que uma 
 * Atividade Economica e que criam objetos desta classe, para que o contribuinte escolha a Atividade Economica correta entre as atividades 
 * que constam nesta lista de atividades economicas, para assim validarem a fatura.
 * @author Nuno Rei, Joao Marques, Luis Abreu
 * @version Maio 2018
 */
public class FaturaPendente extends Fatura implements Serializable {
    /** Lista de Atividades Economicas da empresa que emite uma FaturaPendente */ 
    private ArrayList<String> AEconomicas;

    /**
     * Construtor vazio
     */
    public FaturaPendente() {
        super();
        this.AEconomicas = new ArrayList<String>();
    }
    /**
     * Construtor parametrizado
     * @param NIFemitente, Designacao, Data, NIFcliente, Descricao, Atividade Economica, valor, Lista de Atividades Economicas
     */
    public FaturaPendente(String e, String design, LocalDate data, String c, String desc, String A, double v, ArrayList<String> AEs)
    {
       super(e,design,data,c,desc,A,v);
       this.AEconomicas = new ArrayList<String>();
       for (String s : AEs) {
           this.AEconomicas.add(s);
       }
    }
    /**
     * Construtor por objeto 
     * @param Objeto desta Classe
     */
    public FaturaPendente(FaturaPendente f) {
       super(f);
       this.AEconomicas = new ArrayList<String>();
       for (String s : f.getAEconomicas()) {
          this.AEconomicas.add(s);
       }
    }
    /**
     * @return Lista de Atividades Economicas
     */
    public ArrayList<String> getAEconomicas() {
        return this.AEconomicas;
    }
    /**
     * Set de Atividades Economicas
     * @param Lista de Atividades Economicas
     */
    public void setAEconomicas(ArrayList<String> AEs) {
       for (String s : AEs) {
          this.AEconomicas.add(s);
       }
    }
    /**
     * Informacao da FaturaPendente em formato String
     * @param String com informaca da fatura
     */
    public String toString() {
        return super.toString();
    }
    /**
     * Clone de FaturaPendente
     * @return Clone da FaturaPendente
     */
    public FaturaPendente clone() {
        return new FaturaPendente(this);
    }
}
