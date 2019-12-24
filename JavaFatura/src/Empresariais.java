import java.util.ArrayList;
import java.time.LocalDate;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
/**
 * Subclasse de Contribuinte, Empresariais, ou seja Contribuinte Coletivo do sistema. Acrescenta os campos: Atividades Economicas em que participa,
 * Distrito em que se encontra sediada, e total faturado. Entidade que presta servicos ao contribuinte Individual e que alimenta o sistema com faturas.
 * @author Nuno Rei, Joao Marques, Luis Abreu
 * @version Maio 2018
 */
public class Empresariais extends Contribuinte implements Serializable, CalculoImposto {
    /** Lista de Atividades Economicas em que participa */
    private ArrayList<String> AEconomicas;
    /** Distrito em que se encontra sediada */
    private String distrito;
    /** Total faturado pela empresa */
    private double totalFaturado;
    
    /**
     * Constructor vazio
     */
    public Empresariais() {
        super();
        this.AEconomicas = new ArrayList<String>();
        this.distrito = "n/d";
        this.totalFaturado = 0;
    }
    /**
     * Constructor parametrizado
     * @param NIF, Email, Nome, Morada, Password, lista de atividades economicas em que participa, e distrito em que se localiza
     */
    public Empresariais(String NIF, String email, String nome, String morada, String password, ArrayList<String> A, String dist) {
        super(NIF,email,nome,morada,password);
        this.AEconomicas = new ArrayList<String>(A);
        this.distrito = dist;
        this.totalFaturado = 0;
    }
    /**
     * Constructor por Objeto passado
     * @param Empresariais
     */
    public Empresariais(Empresariais e) {
        super(e);
        this.AEconomicas = e.getAEconomicas();
        this.distrito = e.getDistrito();
        this.totalFaturado = e.getTotalFaturado();
    }
    /**
     * Obter Lista de atividades economicas em que a empresa participa
     * @return Lista de atividades economicas em que a empresa participa
     */
    public ArrayList<String> getAEconomicas() {
        ArrayList<String> e = new ArrayList<String>();
        for (String s : this.AEconomicas) {
            e.add(s);
        }
        return e;
    }
    /**
     * Obter o distrito em que se localiza a empresa
     * @return Distrito em que se localiza a empresa
     */
    public String getDistrito() {
        return this.distrito;
    }
    /**
     * Obter o total faturado pela empresa
     * @return Total faturado pela empresa
     */
    public double getTotalFaturado() {
        return this.totalFaturado;
    }
    /**
     * Altera as atividades economicas em que a empresa participa
     * @param Lista com atividades economicas
     */
    public void setAEconomicas(ArrayList<String> aEconomicas) {
        for (String s : aEconomicas) {
            this.AEconomicas.add(s);
        }
    }
    /**
     * Altera o Distrito em que a empresa se localiza
     * @param Distrito
     */
    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
    /**
     * Altera o total faturado pela empresa
     * @param Total faturado
     */
    public void setTotalFaturado(double totalFaturado) {
        this.totalFaturado = totalFaturado;
    }
    /**
     * Calcula a reducao de imposto associada ao Distrito em que se localiza
     * @return Deducao associada
     */
    public double reducaoImposto() 
    {
        return Distritos.deducaoAssociada(this.distrito);
    }
    /**
    * Compara a igualdade com outro objecto
    * @param objeto
    * @return True se igual
    */
    public boolean equals(Object obj) {
        if(obj == this) {
           return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
           return false;
        }
        Empresariais o = (Empresariais) obj;
        return super.equals(o) && o.getAEconomicas().equals(this.AEconomicas) && (o.getDistrito() == (this.distrito)) && o.getTotalFaturado() == this.getTotalFaturado();
    }
    /**
    * Devolve uma representação no formato textual da Empresa
    * @return String com toda a informacao da Empresa
    */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Atividades Económicas em que participa:");
        for(String s: AEconomicas) {
            sb.append(AtividadesEconomicas.associa(s)).append(", ");
        }
        sb.append("\n");
        sb.append("Distrito: ").append(Distritos.associa(this.distrito)).append("\n");
        sb.append("Total faturado: ").append(this.totalFaturado).append("\n");
        return "Contribuinte Empresarial "+super.toString()+sb.toString();
    }
    /**
    * Cria uma FaturaPendente 
    * @param Designacao da fatura, NIF do cliente, Descricao da fatura, Atividade Economica, valor
    * @return FaturaPendente
    */
    public FaturaPendente criaFaturaPendente(String design, String c, String desc, String ae, double valor) 
    {
        FaturaPendente fatura = new FaturaPendente(this.getNIF(),design,LocalDate.now(),c,desc,ae,valor,this.getAEconomicas());
        return fatura;
    }
    /**
    * Cria uma Fatura que sera validada automaticamente 
    * @param Designacao da fatura, NIF do cliente, Descricao da fatura, Atividade Economica, valor
    * @return Fatura
    */
    public Fatura criaFatura(String design, String c, String desc, String ae, double valor) 
    {
        Fatura fatura  = new Fatura(this.getNIF(),design,LocalDate.now(),c,desc,ae,valor);
        return fatura;
    }  
    /**
    * Clone do Objeto Empresariais
    * @return Clone de Empresariais
    */
    public Empresariais clone() {
        return new Empresariais(this);
    }
}
