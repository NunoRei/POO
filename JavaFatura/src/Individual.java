import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import java.util.Collections;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;

/**
 * Subclasse de Contribuinte, representa um contribuinte individual e acrescenta informacao acerca do agregado familiar, coeficiente fiscal, e montante
 * de deducao fiscal acumulado das despesas que vai efetuando.
 * 
 * @author Nuno Rei, Joao Marques, Luis Abreu
 * @version Maio 2018
 */
public class Individual extends Contribuinte implements Serializable, CalculoImposto {
    /** Numero de membros do agregado familiar */
    private int agregadoF;
    /** Lista com os NIFs do agregado familiar */
    private ArrayList<String> NIFs;
    /** Coeficiente fiscal */
    private double coeficiente;
    /** Montante de deducao fiscal acumulado */
    private double acumulado;
    /** Map de Faturas a espera de validacao por parte do Cliente */
    private Map<String,FaturaPendente> pendentes;
    /** Lista de Faturas ja validadas e com deducao fiscal calculada */
    private ArrayList<Fatura> validadas;
    
    /**
     * Constructor vazio
     */
    public Individual() {
        super();
        this.agregadoF = 0;
        this.NIFs = new ArrayList<String>();
        this.coeficiente = 0;
        this.acumulado = 0;
        this.pendentes = new HashMap<String,FaturaPendente>();
        this.validadas = new ArrayList<Fatura>();
    }
    /**
     * Constructor parametrizado
     * @param NIF, Email, Nome, Morada, Password, numero de membros agregado, lista de nifs do agregado
     */
    public Individual(String NIF, String email, String nome, String morada, String password, int agregado, ArrayList<String> nifs) {
        super(NIF,email,nome,morada,password);
        this.agregadoF = agregado;
        this.NIFs = new ArrayList<String>();
        for (String s : nifs) {
            this.NIFs.add(s);
        }
        this.coeficiente = 0.1;
        this.acumulado = 0;
        this.pendentes = new HashMap<String,FaturaPendente>();
        this.validadas = new ArrayList<Fatura>();
    }
    /**
     * Constructor por Objeto passado
     * @param Individual
     */
    public Individual(Individual i) {
        super(i);
        this.agregadoF = i.getAgregadoF();
        this.NIFs = i.getNIFs();
        this.coeficiente = i.getCoeficiente();
        this.acumulado = i.getAcumulado();
        this.pendentes = i.getPendentes();
        this.validadas = i.getValidadas();
    }
    /**
     * Obter numero de membros do agregado do Contribuinte
     * @return Numero de membros do agregado do Contribuinte
     */
    public int getAgregadoF() {
        return agregadoF;
    }
    /**
     * Obter NIFs de membros do agregado do Contribuinte
     * @return NIFs de membros do agregado do Contribuinte
     */
    public ArrayList<String> getNIFs() {
        return NIFs;
    }
    /**
     * Obter coeficiente de deducao fiscal do Contribuinte
     * @return Coeficiente de deducao fiscal do Contribuinte
     */
    public double getCoeficiente() {
        return coeficiente;
    }
    /**
     * Obter acumulado de deducao fiscal do Contribuinte
     * @return Acumulado de deducao fiscal do Contribuinte
     */
    public double getAcumulado() {
        return acumulado;
    }
    /**
     * Obter Map de Faturas Pendentes
     * @return HashMap de Faturas Pendentes
     */
    public Map<String,FaturaPendente> getPendentes() {
        Map<String,FaturaPendente> e = new HashMap<String,FaturaPendente>();
        for (Map.Entry<String,FaturaPendente> f : this.pendentes.entrySet()) {
            e.put(f.getKey(),f.getValue().clone());
        }
        return e;
    }
    /**
     * Obter Lista de Faturas validadas
     * @return ArrayList de Faturas Validadas
     */
    public ArrayList<Fatura> getValidadas() {
        ArrayList<Fatura> e = new ArrayList<Fatura>();
        for (Fatura f : this.validadas) {
            e.add(f.clone());
        }
        return e;
    }
    /**
     * Altera o numero de membros do agregado familiar do Contribuinte
     * @param numero de membros do agregado familiar do Contribuinte
     */
    public void setAgregadoF(int agregadoF) {
        this.agregadoF = agregadoF;
    }
    /**
     * Altera os NIFs do agregado familiar do Contribuinte
     * @param Lista de NIFs do agregado familiar do Contribuinte
     */
    public void setNIFs(ArrayList<String> nIFs) {
        for (String s : nIFs) {
            this.NIFs.add(s);
        }
    }
    /**
     * Altera o coeficiente fiscal do Contribuinte
     * @param Coeficiente fiscal do Contribuinte
     */
    public void setCoeficiente(double coeficiente) {
        this.coeficiente = coeficiente;
    }
    /**
     * Colocar acumulado de deducao Fiscal.
     * @param Acumulado de deducao Fiscal
     */
    public void setAcumulado(double acumulado) {
        this.acumulado = acumulado;
    }
    /**
     * @param HashMap de FaturasPendentes para colcar nas faturas pendentes 
     */
    private void setPendentes(HashMap<String,FaturaPendente> f) {
        for (Map.Entry<String,FaturaPendente> a : f.entrySet()) {
            this.pendentes.put(a.getKey(),a.getValue().clone());
        }
    }
    /**
     * Coloca Lista de Faturas validadas
     * @param Lista de Faturas para colocar nas faturas validadas.
     */
    public void setValidadas(ArrayList<Fatura> f) {
        for (Fatura a : f) {
            this.validadas.add(a);
        }
    }
    /**
     * Retorna a Fatura Pendente que corresponde ao identificador passado
     * @param Identificador da FaturaPendente
     * @return FaturaPendente correspondente ao identificador passado
     */
    public FaturaPendente getFaturaPendente(String id) {
        return this.pendentes.get(id);
    }
    /**
     * Remove a Fatura Pendente que corresponde ao identificador passado do Map de Faturas Pendentes
     * @param Identificador da FaturaPendente
     */
    public void removeFaturaPendente(String id) 
    {   
        this.pendentes.remove(id);
    }
    /**
     * Lista com todas as faturas ja validadas.
     * @return Lista com todas as faturas validadas.
    */
    public List<Fatura> faturasValidadasList() 
    {
        return this.validadas.stream().map(Fatura::clone).collect(toList());
    }
    /**
     * Lista com todas as faturas por validar.
     * @return Lista de todas as faturas por validar
    */
    public List<FaturaPendente> faturasPendentesList() 
    {
        return this.pendentes.values().stream().map(FaturaPendente::clone).collect(toList());
    }
    /**
     * Adiciona uma fatura Pendente as faturas Pendentes, isto e, a espera de validacao.
     * @param FaturaPendente subclasse de Fatura 
    */
    public void adicionaFaturaPendente(FaturaPendente f) {
        this.pendentes.put(f.getId(),f);
    }
    /**
     * Adiciona uma fatura as faturas validadas.
     * @param Fatura 
    */
    public void adicionaFatura(Fatura f) {
        this.validadas.add(f);
    }
    /**
     * Calcula a reducao de Imposto se o agregado Familiar do Contribuinte possui mais de 4 filhos, e se sim, reduz o 
     * imposto em 5% por cada filho.
     * Assume-se que o agregado e sempre composto por uma familia "normal"
     * @return A reducao de imposto caso o agregado familia possua mais de 4 filhos 
    */
    public double reducaoImposto() 
    {
        double r = (this.getAgregadoF()-2)*0.05;
        if (r>0) return r;
        else return 0;
    }
    /**
     * Obter o total gasto pelo Contribuinte
     * @return Total gasto  
    */
    public double totalGasto() 
    {
        double total = 0;
        total = faturasValidadasList().stream().map(f->f.getValor()).mapToDouble(Double::doubleValue).sum();
        total += faturasPendentesList().stream().map(f->f.getValor()).mapToDouble(Double::doubleValue).sum();
        return total;
    }
    /**
     * Compara a igualdade com outro objecto
     * @param Objeto
     * @return true se os objeto sao iguais, false caso contrario 
    */
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Individual o = (Individual) obj;
        return super.equals(o) && (o.getAgregadoF() == (this.agregadoF)) && o.getNIFs().equals(this.NIFs) && 
            (o.getCoeficiente() == (this.coeficiente)) && o.getAcumulado() == this.getAcumulado();
    }

    /**
     * Devolve uma representação no formato textual
     * @return String que representa o Contribuinte Individual
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Numero de membros do agregado: ").append(this.agregadoF).append("\n");
        sb.append("NIFS Agregado Familiar: ");
        for(String s: NIFs) {
            sb.append(s).append(", ");
        }
        sb.append("\n");
        sb.append("Coeficiente Fiscal: ").append(this.coeficiente).append("\n");
        sb.append("Valor acumulado de deducoes fiscais ").append(this.acumulado).append("\n");
        return "Contribuinte Individual "+super.toString()+sb.toString();
    }
    /**
     * Clone da Classe Individual
     */
    public Individual clone() {
        return new Individual(this);
    }
}
