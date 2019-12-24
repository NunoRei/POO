import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;
/**
 * Classe Fatura
 * @author Nuno Rei, Joao Marques, Luis Abreu
 * @version Maio 2018
 */
public class Fatura implements Comparable <Fatura>, Serializable {
    /** Identificador unico da fatura */
    private String Id;
    /** NIF do emitente */
    private String NIFemitente;
    /** Designacao da fatura */
    private String designacao;
    /** Data da emissao */
    private LocalDate data;
    /** NIF do cliente */
    private String NIFcliente;
    /** Descricao */
    private String descricao;
    /** Atividade Economica */
    private String AEconomica;
    /** Valor da fatura */
    private double valor;
    
    /**
     * Construtor vazio
     */
    public Fatura() {
        this.Id = "0";
        this.setNIFemitente("n/d");
        this.setDesignacao("n/d");
        this.setData(LocalDate.now());
        this.setNIFcliente("n/d");
        this.setDescricao("n/d");
        this.setAEconomica("n/d");
        this.setValor(0);
    }
    /**
     * Construtor parametrizado
     * @param NIFemitente, Designacao, Data, NIFcliente, Descricao, Atividade Economica, valor
     */
    public Fatura(String e, String design, LocalDate data, String c, String desc, String A, double v) {
        this.setNIFemitente(e);
        this.setDesignacao(design);
        this.setData(data);
        this.setNIFcliente(c);
        this.setDescricao(desc);
        this.setAEconomica(A);
        this.setValor(v);
    }
    /**
     * Construtor por objeto Fatura passado
     * @param Objeto Fatura
     */
    public Fatura(Fatura f) {
        this.Id = f.getId();
        this.NIFemitente = f.getNIFemitente();
        this.designacao = f.getDesignacao();
        this.data = f.getData();
        this.NIFcliente = f.getNIFcliente();
        this.descricao = f.getDescricao();
        this.AEconomica = f.getAEconomica();
        this.valor = f.getValor();
    }
    /**
     * Obter Identificador de Fatura
     * @return Identificador de Fatura
     */
    public String getId() {
        return this.Id;
    }
    /**
     * Obter NIF do emitente da Fatura
     * @return NIF do emitente da Fatura
     */
    public String getNIFemitente() {
        return this.NIFemitente;
    }
    /**
     * Obter Designacao da Fatura
     * @return Designacao da Fatura
     */
    public String getDesignacao() {
        return this.designacao;
    }
    /**
     * Obter Data de emissao da Fatura
     * @return Data de emissao da Fatura
     */
    public LocalDate getData() {
        return this.data;
    }
    /**
     * Obter NIF do cliente da Fatura
     * @return NIF do cliente da Fatura
     */
    public String getNIFcliente() {
        return this.NIFcliente;
    }
    /**
     * Obter Descricao da Fatura
     * @return Descricao da Fatura
     */
    public String getDescricao() {
        return this.descricao;
    }
    /**
     * Obter Atividade Economica da Fatura
     * @return Atividade Economica da Fatura
     */
    public String getAEconomica() {
        return this.AEconomica;
    }
    /**
     * Obter Valor da Fatura
     * @return Valor da Fatura
     */
    public double getValor() {
        return this.valor;
    }
    /**
     * Altera o Identificador da Fatura
     * @param Identificador de Fatura
     */
    public void setId(String id) {
        this.Id = id;
    }
    /**
     * Altera o NIF do Emitente da Fatura
     * @param Emitente de Fatura
     */
    public void setNIFemitente(String nIFemitente) {
        NIFemitente = nIFemitente;
    }
    /**
     * Altera a Designacao da Fatura
     * @param Designacao da Fatura
     */
    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }
    /**
     * Altera a Data de Emissao da Fatura
     * @param Data de Emissao da Fatura
     */
    public void setData(LocalDate data) {
        this.data = data;
    }
    /**
     * Altera o NIF do cliente da Fatura
     * @param NIF do cliente de Fatura
     */
    public void setNIFcliente(String nIFcliente) {
        NIFcliente = nIFcliente;
    }
    /**
     * Altera a Descricao da Fatura
     * @param Descricao da Fatura
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    /**
     * Altera a Atividade Economica da Fatura
     * @param Atividade Economica da Fatura
     */
    public void setAEconomica(String aEconomica) {
        AEconomica = aEconomica;
    }
    /**
     * Altera o Valor da Fatura
     * @param Valor da Fatura
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    /**
    * Compara a igualdade com outro objecto
    * @param objeto
    * @return True se forem iguais, False caso contrario
    */
    public boolean equals(Object obj) {
        if(obj == this) {
           return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
           return false;
        }
        Fatura o = (Fatura) obj;
        return o.getId().equals(this.Id) && o.getNIFemitente().equals(this.NIFemitente) && o.getDesignacao().equals(this.designacao) && 
        o.getData().equals(this.data) && o.getNIFcliente().equals(this.NIFcliente) && o.getDescricao().equals(this.AEconomica) &&
        o.getValor() == this.valor;
     }
    /**
    * Devolve uma representação da Fatura no formato textual
    * @return String com toda a informacao da Fatura 
    */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Identificador da Fatura: ").append(this.Id).append("\n");
        sb.append("Numero de identificacao fiscal do emitente: ");
        sb.append(this.NIFemitente).append("\n");
        sb.append("Designacao do emitente: ").append(this.designacao).append("\n");
        sb.append("Data da despesa: ").append(this.data).append("\n");
        sb.append("Número fiscal do cliente: ").append(this.NIFcliente).append("\n");
        sb.append("descricao da despesa: ").append(this.descricao).append("\n");
        sb.append("Natureza da despesa: ").append(AtividadesEconomicas.associa(this.AEconomica)).append("\n");
        sb.append("Valor da despesa: ").append(this.valor).append("\n");
        return sb.toString();
    }
    /**
     * Clone da Fatura
     * @return Clone da Fatura
     */
    public Fatura clone() {
        return new Fatura(this);
    }
    /**
    * Compara duas Faturas quanto ao valor
    * @param Fatura
    * @return -1 se menor, 1 se maior, 0 se igual
    */
    public int compareTo(Fatura f) {
       double r = ((Fatura) f).getValor();
       int cr = 0;
        if (this.valor<r) cr = -1;
        if (this.valor>r) cr = 1;
        return cr;
    }  
}

