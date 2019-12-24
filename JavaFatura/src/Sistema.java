import java.util.Map;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.time.LocalDate;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.IOException;
import java.lang.Object;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.Scanner;
import static java.lang.System.out;
/**
 * Classe em que sao guardados os agentes do Sistema, os Contribuintes individuais e Empresariais, e 
 * em que vao sendo guardadas as Faturas emitidas pelos contribuintes empresariais. 
 * Implementa metodos para suportar todas as funcionalidades do sistema. 
 * @author Nuno Rei, Joao Marques, Luis Abreu 
 * @version Maio 2018
 */
public class Sistema implements Serializable  
{
    /** Contador de faturas e atribuidor de identificador unico para as mesmas */
    private static int Idcounter = 1;
    /** Map de todas as faturas emitidas para o sistema, mapeadas por identificador de fatura */
    private Map<String,Fatura> FaturasEmitidas;
    /** Map de todos os contribuintes registados no sistema, mapeados por numero de identificacao fiscal */
    private Map<String,Contribuinte> users;
    
    /** Construtor Vazio
     * 
    */
    public Sistema() {
        this.FaturasEmitidas = new HashMap<>();
        this.users = new HashMap<>();
    }
    /** Construtor por parametros
     *@param Map de Faturas
     *@param Map de Contribuintes
    */
    public Sistema(HashMap<String,Fatura> faturas, HashMap<String,Contribuinte> users) {
        for (Map.Entry<String,Fatura> f : faturas.entrySet()) {
            this.FaturasEmitidas.put(f.getKey(),f.getValue().clone());
        }
        for (Map.Entry<String,Contribuinte> c : users.entrySet()) {
            this.users.put(c.getKey(),c.getValue().clone());
        }
    }
    /** Construtor copia
     *@param Sistema
    */
    public Sistema(Sistema s) {
        this.FaturasEmitidas = s.getFaturasEmitidas();
        this.users = s.getUsers();
    }
    /**
     * @return Numero em que se encontra o contador de faturas que serve como identificador unico para as faturas
     */
    public int getIdcounter() {
        return this.Idcounter;
    }
    /**
     * @return Map dos Contribuintes
     */
    private Map<String,Contribuinte> getUsers() {
        return this.users;
    }
    /**
     * @return Lista dos Contribuintes registados no Sistema
     */
    public List<Contribuinte> getUsersList() {
        return this.users.values().stream().map(Contribuinte::clone).collect(toList());
    }
    /**
     * @param Numero de faturas ja emitidas para atualizar no contador de unique ids das faturas 
     */
    public void setIdcounter(int counter) {
        this.Idcounter = counter;
    }
    /**
     * @param HashMap de Contribuintes para colocar 
     */
    private void setUsers(HashMap<String,Contribuinte> users) {
        this.users = users;
    }
    /**
     * @return Map das Faturas que foram emitidas para o Sistema
     */
    private Map<String,Fatura> getFaturasEmitidas() {
        return FaturasEmitidas;
    }
    /**
     * @return Lista das Faturas que foram emitidas para o Sistema
    */
    public List<Fatura> getFaturasEmitidasList() {
        return this.FaturasEmitidas.values().stream().map(Fatura::clone).collect(toList());
    }
    /**
     * @param Map de Faturas para colocar
    */
    private void setFaturasEmitidas(HashMap<String,Fatura> FaturasEmitidas) {
        this.FaturasEmitidas = new HashMap<String,Fatura>(FaturasEmitidas);
    } 
    /**
     * @param NIF em formato String
     * @return True caso o Contribuinte esta registado no Sistema, contido no HashMap de Contribuintes,false caso contrario
    */
    public boolean procuraUser(String NIF) {
        return this.users.containsKey(NIF);
    }
    /**
     * @param NIF em formato String
     * @return O contribuinte que corresponde ao NIF passado como parametro
    */
    public Contribuinte retornaUser(String NIF) throws ContribuinteNExExcp {
        if (procuraUser(NIF)==false) {
            throw new ContribuinteNExExcp("Contribuinte "+NIF+" nao existe!");
        }
        return this.users.get(NIF).clone();
    }
    /**
     * Adiciona o Contribuinte a HashMap de Contribuintes  
     * @param Um Contribuinte
    */
    public void adicionaUser(Contribuinte c) {
        this.users.put(c.getNIF(),c);
    }
    /**
     * Numero de utilizadores registados no sistema 
    */
    public int quantosUsers() {
        return ((int)this.users.values().stream().count());
    }
    /**
     * Numero de Faturas emitidas para o sistema 
    */
    public int quantasFaturas() {
        return ((int)this.FaturasEmitidas.values().stream().count());
    }
    /**
     * Adiciona a fatura ao HashMap de FaturasEmitidas, e adiciona tambem ao HashMap de FaturasPendentes do Cliente Individual
     * @param FaturaPendete subclasse de Fatura 
    */
    public void adicionaFaturaPendente(FaturaPendente f) throws ContribuinteNExExcp, NotIndividualExcp {
        if ((procuraUser(f.getNIFcliente()))==false) throw new ContribuinteNExExcp("Contribuinte "+f.getNIFcliente()+" nao existe!");
        else {
           if (retornaUser(f.getNIFcliente()) instanceof Empresariais) throw new NotIndividualExcp("Contribuinte "+f.getNIFcliente()+" nao e Contribuinte Individual!");
           else {
            f.setId(Integer.toString(Idcounter++));
            Individual i = ((Individual)this.users.get(f.getNIFcliente()));
            i.adicionaFaturaPendente((FaturaPendente)f);
            this.FaturasEmitidas.put(f.getId(),f);
            Empresariais e = ((Empresariais)this.users.get(f.getNIFemitente()));
            e.setTotalFaturado(e.getTotalFaturado()+f.getValor());
           }
        }
    }
    /**
     * Adiciona a fatura ao HashMap de FaturasEmitidas, e adiciona tambem a lista de FaturasValidadas do Cliente Individual
     * @param Fatura
    */
    public void adicionaFatura(Fatura f) throws ContribuinteNExExcp, NotIndividualExcp  {
        if (!(procuraUser(f.getNIFcliente()))) throw new ContribuinteNExExcp("Contribuinte "+f.getNIFcliente()+" nao existe!");    
        else {
            if (retornaUser(f.getNIFcliente()) instanceof Empresariais) throw new NotIndividualExcp("Contribuinte "+f.getNIFcliente()+" nao e Contribuinte Individual!");
            else{
                Individual i = ((Individual)this.users.get(f.getNIFcliente()));
                f.setId(Integer.toString(Idcounter++));
                i.adicionaFatura((Fatura)f);
                this.FaturasEmitidas.put(f.getId(),f);
                aumentaDeducao(f);
                Empresariais e = ((Empresariais)this.users.get(f.getNIFemitente()));
                e.setTotalFaturado(e.getTotalFaturado()+f.getValor());
            }
        }
    }
    /**
     * Coloca a Natureza da Despesa correta numa fatura que estava por validar, e valida a mesma removendo-a das Faturas
     * Pendentes do Contribuinte Individual, e colocando-a nas Faturas Validadas. Calculando tambem a Deducao associada a 
     * fatura.
     * @param String Identificador de Fatura 
     * @param String Atividade Economica para colocar 
    */
    public void validaFatura(String id, String ae) 
    {
        Fatura f = this.FaturasEmitidas.get(id); 
        Individual i = ((Individual)this.users.get(f.getNIFcliente()));
        f.setAEconomica(ae);
        i.adicionaFatura((Fatura)f);
        aumentaDeducao(f);
        i.removeFaturaPendente(id);
    }
    /**
     * Calcula a deducao associada a uma Fatura, caso tenha deducao
     * @param Fatura
     * @return deducao da Fatura
    */
    public double calculaDeducao(Fatura f) {
        double deducao = 0;
        Individual i = ((Individual)this.users.get(f.getNIFcliente()));
        double deducaoUser = i.reducaoImposto();
        double deducaoEmpresa = ((Empresariais)this.users.get(f.getNIFemitente())).reducaoImposto();
        if (AtividadesEconomicas.deducaoAssociada(f.getAEconomica()) != 0) {
             deducao = (f.getValor()*AtividadesEconomicas.deducaoAssociada(f.getAEconomica()))+(f.getValor()*deducaoUser)+(f.getValor()*deducaoEmpresa);
        }
        return deducao;
    }
    /**
     * Aumenta a deducao acumulada do contribuinte, cliente da fatura
     * @param Fatura
    */
    public void aumentaDeducao(Fatura f) 
    {
            Individual i = ((Individual)this.users.get(f.getNIFcliente()));
            double deducao = calculaDeducao(f);
            i.setAcumulado(i.getAcumulado()+deducao);
    }
    /**
     * Forma uma String com informacao Detalhada sobre a deducao fiscal acumulada, 
     * percorre todas as faturas ja validadas, e mostra o valor da fatura, o identificador, e quanto e que contribui para o acumulado.
     * @param NIF do Contribuinte Individual 
     * @return String com toda a informacao detalhada sobre a contribuicao de cada fatura para o acumulado de deducao fiscal 
    */
    public String acumuladoDetalhado(String nif) {
        StringBuilder sb = new StringBuilder();
        Individual i = ((Individual)this.users.get(nif));
        for(Fatura f : i.faturasValidadasList()) {
            sb.append("Acumulado: ").append(calculaDeducao(f)).append(", da Fatura ").append(f.getId()).append(", com o Valor ").append(f.getValor()).append("\n");
        }
        sb.append("Montante de deducao fiscal acumulado Total: ").append(i.getAcumulado()).append("\n");
        return sb.toString();
    }
    private double converte (String s) 
    {
        try {
            double acumul = ((Individual)this.users.get(s)).getAcumulado();
            return acumul;
        } catch (Exception e) {
            return 0;
        }
    }
    /**
     * Forma uma String com informacao Detalhada sobre a deducao fiscal acumulada, 
     * percorre todas as faturas ja validadas, e mostra o valor da fatura, o identificador, e quanto e que contribui para o acumulado.
     * @param NIF do Contribuinte Individual 
     * @return String com toda a informacao detalhada sobre a contribuicao de cada fatura para o acumulado de deducao fiscal 
    */
    public double acumuladoAgregadoFamiliar(String nif) {
        Individual i = ((Individual)this.users.get(nif));
        ArrayList<String> nifagregado = i.getNIFs();
        double montante = nifagregado.stream().map(s->converte(s)).mapToDouble(Double::doubleValue).sum(); 
        return montante;                                                        
    }
    /**
     * Ordena as Faturas Emitidas por uma empresa, atraves do comparator, e coloca as Faturas numa lista
     * @param NIF da Empresa 
     * @param Comparator de Faturas
     * @return lista de Faturas ordenadas por valor, ordem crescente do valor
    */
    public List<Fatura> ordenaFaturasEmpresa(String nif, Comparator<Fatura> c) 
    {
        Empresariais e = ((Empresariais)this.users.get(nif));
        List<Fatura> l = new ArrayList<>();
        for(Map.Entry<String, Fatura> entry : this.FaturasEmitidas.entrySet()) {
            if (entry.getValue().getNIFemitente().equals(e.getNIF())) {
               l.add(entry.getValue().clone());
            }
        }
        Collections.sort(l,c);
        return l;
    }
    /**
     * Lista as faturas de um cliente num intervalo de tempo dado
     * @param NIF da Empresa (Classe Empresariais, subclasse de Contribuinte)
     * @param NIF do Cliente (Contribuinte Individual subclasse de Contribuinte)
     * @param Data inicio do intervalo
     * @param Data fim do intervalo
     * @return lista de Faturas do Contribuinte Individual no intervalo de tempo dado
    */
    public List<Fatura> faturasIndividuaisIntervalo (String nif, String c, LocalDate begin, LocalDate end) 
    {
        Empresariais e = ((Empresariais)this.users.get(nif));
        List<Fatura> l = new ArrayList<>();
        for(Map.Entry<String, Fatura> entry : this.FaturasEmitidas.entrySet()) {
            if (entry.getValue().getNIFemitente().equals(e.getNIF()) && entry.getValue().getNIFcliente().equals(c)) {
               l.add(entry.getValue().clone());
            }
        }
        return l.stream().filter(f->(f.getData().isAfter(begin) && f.getData().isBefore(end)) || f.getData().isEqual(begin) || f.getData().isEqual(end)).collect(toList());
    }
    /**
     * Lista as faturas de um cliente por ordem decrescente de valor
     * @param NIF da Empresa (Classe Empresariais, subclasse de Contribuinte)
     * @param NIF do Cliente (Contribuinte Individual subclasse de Contribuinte)
     * @return lista de Faturas do Contribuinte Individual ordem decrescente do valor
    */
    public List<Fatura> faturasIndividuaisValorDecrescente(String nif, String c) 
    {
       Empresariais e = ((Empresariais)this.users.get(nif));
       List<Fatura> l = new ArrayList<>();
       for(Map.Entry<String, Fatura> entry : this.FaturasEmitidas.entrySet()) {
           if (entry.getValue().getNIFemitente().equals(e.getNIF()) && entry.getValue().getNIFcliente().equals(c)) {
               l.add(entry.getValue().clone());
           }
       }
       return l.stream().sorted(Comparator.reverseOrder()).collect(toList());
    }
    /**
     * Calcula o montante faturado por uma empresa num intervalo de tempo dado
     * @param NIF da Empresa (Classe Empresariais, subclasse de Contribuinte)
     * @param Data inicio do intervalo
     * @param Data fim do intervalo
     * @return Retorna o montante faturado por uma empresa num intervalo de tempo dado
    */
    public double faturadoEmpresaIntervalo (String nif, LocalDate begin, LocalDate end) 
    {
        Empresariais e = ((Empresariais)this.users.get(nif));
        List<Fatura> l = ordenaFaturasEmpresa(nif,new FDComparator());
        l = l.stream().filter(f->(f.getData().isAfter(begin) && f.getData().isBefore(end)) || f.getData().isEqual(begin) || f.getData().isEqual(end)).collect(toList());
        return l.stream().map(f->f.getValor()).mapToDouble(f->f.doubleValue()).sum();
    }
    /**
     * Cria uma collection de contribuintes individuais ordenados por ordem decrescente do total gasto.
     * @return Collection de Contribuintes Individuais
    */
    public Collection<Individual> maisDispendiosos() 
    {
        TreeSet u = new TreeSet<Individual>(new TotalGastoComparator());
        for (Contribuinte c : getUsersList()) {
            if (c instanceof Individual) {
                u.add(c.clone());
            } 
        }
        Iterator<Individual> itr=u.iterator();
        TreeSet s = new TreeSet<Individual>(new TotalGastoComparator());
        int n = 10;
        while(itr.hasNext() && n>0){
            s.add(itr.next());
            n--;
        }
        return s;
    }
    /**
     * Cria uma collection de contribuintes empresariais ordenados por ordem decrescente do total faturado.
     * @param N numero de empresas que pretende obter
     * @return Collection de Contribuintes Individuais
    */
    public Collection<Empresariais> maisFaturam(int n) 
    {
        TreeSet u = new TreeSet<Empresariais>(new TotalFaturadoComparator());
        for (Contribuinte c : getUsersList()) {
            if (c instanceof Empresariais) {
                u.add(c.clone());
            } 
        }
        Iterator<Empresariais> itr=u.iterator();
        TreeSet s = new TreeSet<Empresariais>(new TotalFaturadoComparator());
        while(itr.hasNext() && n>0){
            s.add(itr.next());
            n--;
        }
        return s;
    }
    /**
     * Busca a informacao sobre um Contribuinte
     * @param NIF do Contribuinte
     * @return String com toda a sua informacao
    */
    public String sobre(String nif) {
        Contribuinte c = this.users.get(nif);
        return c.toString();
    }
    /**
     * Guarda o Estado da Aplicacao em ficheiro
    */
    public void guardaEstado() throws FileNotFoundException, IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("estado.obj"));
        oos.writeObject(this);
        oos.close();
    }
    /**
     * Le o Estado da Aplicacao do ficheiro
    */
    public static Sistema leEstado() throws ClassNotFoundException, IOException, FileNotFoundException
    {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("estado.obj"));
        Sistema s = (Sistema) ois.readObject();
        return s;
    }
}
