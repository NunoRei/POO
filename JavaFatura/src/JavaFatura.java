import java.util.Scanner;
import java.time.LocalDate;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.time.DateTimeException;
import java.util.InputMismatchException;
/**
 * @author Nuno Rei, Joao Marques e Luis Abreu
 *
 */
public class JavaFatura {
    private Sistema prog;
    
    public JavaFatura() 
    {
        try
        {
            prog = Sistema.leEstado();
            prog.setIdcounter(prog.getFaturasEmitidasList().size()+1);
        }
        catch (Exception e) 
        {
            prog = new Sistema();
            new Dados(prog);
        }
    }
    
    public void run() 
    {   
        int op;
        do {
           op = executaMenu();
           switch (op) {
               case 1: login();
                       break;
               case 2: register();
                       break;
               case 3: loginasAdmin();
                       break;
               case 4: op = 0;
                       break;
            }
        } while (op != 0);
        try {
            prog.guardaEstado();
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public int executaMenu() 
    {
        Scanner reader = new Scanner(System.in);
        String opcao = "";
        while (opcao.equals("1") == false && opcao.equals("2") == false && opcao.equals("3") == false && opcao.equals("4") == false) {
            out.println("Bem-Vindo ao JavaFatura!");
            out.println("Prima: ");
            out.println("1 - Para fazer login na aplicação");
            out.println("2 - Para se registar na aplicação");
            out.println("3 - Entrar como administrador");
            out.println("4 - Sair da Aplicacao");
            opcao = reader.nextLine();
        }
        reader.close();
        return Integer.parseInt(opcao);
    }
    
    public void login() 
    {   
        Scanner reader = new Scanner(System.in);
        out.println("NIF: ");
        String nif = reader.nextLine();
        if (prog.procuraUser(nif) == false) {
            out.println("O utilizador ainda não se encontra registado no sistema");
        }
        else {
            out.println("Password: ");
            String pass = reader.nextLine();
            int tentativas = 2;
            try {
                while (prog.retornaUser(nif).autenticador(pass) == false && tentativas > 0) {
                    out.println("Password incorreta tente novamente: ");
                    pass = reader.nextLine();
                    tentativas--;
                }
                if (tentativas != 0) {
                    out.println("Bem-Vindo "+prog.retornaUser(nif).getNome()+"!");
                    if (prog.retornaUser(nif) instanceof Individual) logadoComoIndividual(((Individual)prog.retornaUser(nif)));
                    if (prog.retornaUser(nif) instanceof Empresariais) logadoComoEmpresa(((Empresariais)prog.retornaUser(nif)));
                }
                else {
                    out.println("Esgotou as tentativas para se logar");
                }
            } catch (ContribuinteNExExcp e) {
                out.println(e.getMessage()+"\n");
            }
        }
        reader.close();
    }
    
    public void loginasAdmin() 
    {
        Scanner reader = new Scanner(System.in);
        out.println("Password: ");
        String pass = reader.nextLine();
        int tentativas = 2;
        while (pass.equals("admin") == false && tentativas > 0) {
             out.println("Password incorreta tente novamente: ");
             pass = reader.nextLine();
             tentativas--;
        }
        if (tentativas != 0) {
            String opcao = "";
            while (opcao.equals("")==true) {
                out.println("Bem-Vindo Administrador!");
                out.println("O que pretende fazer?");
                out.println("1 - Obter 10 Contribuintes que mais gastam");
                out.println("2 - N empresas que mais faturam");
                out.println("3 - Numero de Faturas emitidas para o sistema");
                out.println("4 - Numero de Contribuintes registados no sistema");
                out.println("5 - Logout");
                opcao = reader.nextLine();
                if (opcao.equals("1")==true) prog.maisDispendiosos().forEach(c->out.println(c.toString()));
                if (opcao.equals("2")==true) {
                    try{
                        out.println("Numero de empresas (N) ?");
                        int n = reader.nextInt();
                        prog.maisFaturam(n).forEach(c->out.println(c.toString()));
                    } catch (InputMismatchException e) {
                        out.println("Input invalido (Nao e um numero)\n");
                    }   
                }
                if (opcao.equals("3")==true) out.println("Existem "+prog.quantasFaturas()+" faturas emitidas para o sistema neste momento.");
                if (opcao.equals("4")==true) out.println("Existem "+prog.quantosUsers()+" Contribuintes registados no sistema neste momento.");
                if (opcao.equals("5")==true) break;
                opcao = "";
            }
        }
        else {
            out.println("Esgotou as tentativas para se logar");
        }
        reader.close();
    }
    
    public void register() 
    {
        Scanner reader = new Scanner(System.in);
        String opcao = "";
        String nif;
        String email;
        String nome;
        String morada;
        String pass;
        out.println("Registar como: 1-Empresa 2-Contribuinte Individual ");
        opcao = reader.nextLine();
        if (opcao.equals("2") == true) {
            try {    
                int agregadoF;
                ArrayList membros = new ArrayList<String>();
                out.println("Insira o NIF: ");
                nif = reader.nextLine();
                Long.valueOf(nif).longValue();
                if (prog.procuraUser(nif) == false) {
                    out.println("Email: ");
                    email = reader.nextLine();
                    out.println("Nome: ");
                    nome = reader.nextLine();
                    out.println("Morada: ");
                    morada = reader.nextLine();
                    out.println("Password: ");
                    pass = reader.nextLine();
                    try {
                        out.println("Numero de membros do agregado familiar(Incluindo-se a si): ");
                        agregadoF = Integer.parseInt(reader.nextLine());
                        membros.add(nif);
                        int n = agregadoF;
                        while(n > 1) {
                            out.println("NIF de membro do agregado: (um de cada vez, o seu ja foi adicionando)");
                            String member = reader.nextLine();
                            long test = Long.valueOf(member).longValue();
                            membros.add(member);
                            n--;
                        }
                        Individual novo = new Individual(nif,email,nome,morada,pass,agregadoF,membros);
                        prog.adicionaUser(novo);
                        out.println("Utilizador Registado com sucesso!");
                        opcao = "";
                    } catch (InputMismatchException e) {
                        out.println("Erro: Nao e um numero, utilizador nao foi registado.\n");
                    } catch (NumberFormatException e) {
                        out.println("Erro: Nao e um nif valido, Utilizador nao foi registado.\n");
                    }
                }    
                else out.println("Utilizador ja se encontra registado\n");
            } catch (NumberFormatException e) {
                out.println("Erro: NIF invalido.\n");
            }
        }
        if (opcao.equals("1") == true) {
            try {
                ArrayList ae = new ArrayList<String>(); 
                boolean nacional;
                out.println("Insira o NIF: ");
                nif = (reader.nextLine());
                Long.valueOf(nif).longValue();
                if (prog.procuraUser(nif) == false) {
                    out.println("Email: ");
                    email = (reader.nextLine());
                    out.println("Nome: ");
                    nome = (reader.nextLine());
                    out.println("Morada: ");
                    morada = (reader.nextLine());
                    out.println("Password: ");
                    pass = (reader.nextLine());
                    boolean a = true;
                    out.println("Sectores de Atividade Economica da Empresa: ");
                    out.println(AtividadesEconomicas.legendaAE());
                    while(a == true) {
                        out.println("Use a legenda acima para escolher um setor (ser lhe a perguntado se pretende adicionar mais algum setor)");
                        String aeco = reader.nextLine();
                        if (aeco.equals("1") || aeco.equals("2") || aeco.equals("3") || aeco.equals("4") || aeco.equals("5") || aeco.equals("6") || aeco.equals("7") || aeco.equals("8") || aeco.equals("9") || aeco.equals("10") || aeco.equals("11") || aeco.equals("12")) {   
                            if (aeco.equals("12")) {
                                out.println("Especifique: ");
                                aeco = reader.nextLine();
                                ae.add(aeco);
                            }
                            else {
                                ae.add(aeco);
                            }
                            out.println("Pretende adicionar mais algum setor? 1-Sim 2-Nao");
                            if(reader.nextLine().equals("1")==true) a = true;
                            else a = false;
                        }
                        else {
                            out.println("Opcao invalida");
                        }
                    }
                    out.println("Distrito onde se localiza a empresa?");
                    out.println(Distritos.legendaDistritos());
                    a=true;
                    String dist = "";
                    while(a == true) {
                        out.println("Use a legenda acima para escolher um Distrito");
                        dist = reader.nextLine();
                        if (dist.equals("1") || dist.equals("2") || dist.equals("3") || dist.equals("4") || dist.equals("5") || dist.equals("6") || dist.equals("7") || dist.equals("8") || dist.equals("9") || dist.equals("10") ||dist.equals("11") || dist.equals("12")) {   
                            if (dist.equals("12")) {
                                out.println("Especifique: ");
                                dist = reader.nextLine();
                            }
                            a = false;
                        }
                        else {
                            out.println("Opcao invalida\n");
                        }
                    }
                    Empresariais novo = new Empresariais(nif,email,nome,morada,pass,ae,dist);
                    prog.adicionaUser(novo);
                    out.println("Utilizador Registado com sucesso!");
                    opcao = "";
                }
                else out.println("Utilizador ja se encontra registado\n");
            } catch (NumberFormatException e) {
                out.println("Erro: NIF invalido.\n");
            }
        }
        reader.close();
    }
    
    private void logadoComoIndividual(Individual user) {
            String opcao = "";
            Scanner reader = new Scanner(System.in);
            while (opcao.equals("") == true) {
                out.println("O que pretende fazer? ");
                out.println("1-Despesas em seu Nome");
                out.println("2-Verificar Montante de deducao Fiscal Acumulado");
                out.println("3-Verificar Faturas Pendentes, a espera de aprovacao");
                out.println("4-Sobre");
                out.println("5-Logout");
                opcao = reader.nextLine();
                if (opcao.equals("1")==true) {
                    try {
                        if (((Individual)prog.retornaUser(user.getNIF())).faturasValidadasList().isEmpty()) out.println("Nao tem qualquer despesa em seu nome.");
                        ((Individual)prog.retornaUser(user.getNIF())).faturasValidadasList().forEach(f->out.println(f.toString()));
                    } catch (ContribuinteNExExcp e) {
                        out.println(e.getMessage());
                    }
                }    
                if (opcao.equals("2")==true) {
                    opcao = "";
                    while(opcao.equals("1")==false && opcao.equals("2")==false) {
                        out.println("1-Individual 2-Agregado Familiar");
                        opcao = reader.nextLine();
                    }
                    if (opcao.equals("1")==true) {
                        out.println("Montante Acumulado por fatura: ");
                        out.println(prog.acumuladoDetalhado(user.getNIF()));
                    }
                    if (opcao.equals("2")==true) {
                        out.println("Montante Acumulado Total do Agregado Familiar: "+prog.acumuladoAgregadoFamiliar(user.getNIF())+"\n");
                    }
                }
                if (opcao.equals("3")==true) menuValidacao(user);
                if (opcao.equals("4")==true) out.println(prog.sobre(user.getNIF())); 
                if (opcao.equals("5")==true) break;
                opcao = "";
            }
            reader.close();
    }
    
    private void menuValidacao(Individual user) 
    {
        Scanner reader = new Scanner(System.in);
        if (user.getPendentes().isEmpty()==false) {    
            user.faturasPendentesList().forEach(f->out.println(f.toString()));;
            String opcao = "";
            while(user.getPendentes().isEmpty()==false && opcao == "") {
                out.println("Insira o identificador da fatura que pretende validar:");
                opcao = reader.nextLine();
                if(user.getPendentes().containsKey(opcao) == true) {
                    FaturaPendente f = user.getPendentes().get(opcao);
                    out.println(f.toString());
                    out.println("Use a legenda abaixo para associar a Despesa Correta:");
                    for(String a: f.getAEconomicas()) {
                        out.println(AtividadesEconomicas.associa(a));
                    }
                    String ae = reader.nextLine();
                    while (f.getAEconomicas().contains(ae)==false) {
                        out.println("Atividade Economica invalida, por favor escolha uma das opcoes");
                        ae = reader.nextLine();
                    }
                    prog.validaFatura(opcao,ae);
                    user.removeFaturaPendente(opcao);
                    out.println("Fatura Validada com sucesso\n");
                }
                else out.println("Identificador de Fatura nao encontrado\n");
                opcao = "";
            }
        }
        else {
            out.println("Neste momento nao tem faturas por Validar\n");
        }
    }
    
    private void logadoComoEmpresa(Empresariais user) {
            String opcao = "";
            Scanner reader = new Scanner(System.in);
            while (opcao.equals("") == true) {
                out.println("O que pretende fazer? ");
                out.println("1-Emitir Fatura");
                out.println("2-Lista de Faturas por Contribuinte, num determinado intervalo");
                out.println("3-Lista de Faturas por Contribuinte, (Ordem Decrescente do Valor da fatura)");
                out.println("4-Total Faturado num determinado intervalo");
                out.println("5-Lista de faturas emitidas");
                out.println("6-Sobre");
                out.println("7-Logout");
                opcao = reader.nextLine();
                if (opcao.equals("1")==true) {
                    if (user.getAEconomicas().size() > 1) {
                        try {
                            out.println("Designacao: ");
                            String design = reader.nextLine();
                            out.println("NIF do Cliente: ");
                            String c = reader.nextLine();
                            out.println("Descricao: ");
                            String desc = reader.nextLine();
                            out.println("Valor: ");
                            double valor = reader.nextDouble();
                            FaturaPendente f = user.criaFaturaPendente(design,c,desc,"",valor);
                            prog.adicionaFaturaPendente(f);
                            out.println("Fatura Emitida com sucesso!\n");
                        } catch (NumberFormatException e) {
                            out.println("Valor invalido\n");
                        } catch (NotIndividualExcp e) {
                            out.println(e.getMessage()+"\n");
                        } catch (ContribuinteNExExcp e) {
                            out.println(e.getMessage()+"\n");
                        } catch (InputMismatchException e) {
                            out.println("Erro: Valor inserido nao e um numero\n");
                        }
                    }
                    else {
                        try {
                        out.println("Designacao: ");
                        String design = reader.nextLine();
                        out.println("NIF do Cliente: ");
                        String c = reader.nextLine();
                        out.println("Descricao: ");
                        String desc = reader.nextLine();
                        out.println("Valor: ");
                        double valor = reader.nextDouble();
                        Fatura f = user.criaFatura(design,c,desc,user.getAEconomicas().get(0),valor);
                        prog.adicionaFatura(f);
                        out.println("Fatura Emitida com sucesso!");
                        } catch (NumberFormatException e) { 
                            out.println("Valor invalido.\n");
                        } catch (ContribuinteNExExcp e) {
                            out.println(e.getMessage()+"\n");
                        } catch (InputMismatchException e) {
                            out.println("Erro: Valor inserido nao e um numero.\n");
                        } catch (NotIndividualExcp e) {
                            out.println(e.getMessage()+"\n");
                        }
                    }
                }
                if (opcao.equals("2")==true) {
                    try {    
                        out.println("NIF do cliente:");
                        String nifcliente = reader.nextLine();
                        out.println("Qual o intervalo?");
                        out.println("Data de inicio: (ano-mes-dia)");
                        LocalDate begin = LocalDate.parse(reader.nextLine());
                        out.println("Date de fim: (ano-mes-dia)");
                        LocalDate end = LocalDate.parse(reader.nextLine());
                        prog.faturasIndividuaisIntervalo(user.getNIF(),nifcliente,begin,end).forEach(f->out.println(f.toString()));
                    } catch (DateTimeException e) {
                        out.println("Erro: Formato Incorreto de data.\n");
                    } 
                }
                if (opcao.equals("3")==true) {
                    out.println("NIF do cliente:");
                    String nifcliente = reader.nextLine();
                    prog.faturasIndividuaisValorDecrescente(user.getNIF(),nifcliente).forEach(f->out.println(f.toString()));
                }
                if (opcao.equals("4")==true) {
                    try {
                        out.println("Qual o intervalo?");
                        out.println("Data de inicio: (ano-mes-dia)");
                        LocalDate begin = LocalDate.parse(reader.nextLine());
                        out.println("Date de fim: (ano-mes-dia)");
                        LocalDate end = LocalDate.parse(reader.nextLine());
                        out.println("Total faturado: "+prog.faturadoEmpresaIntervalo(user.getNIF(),begin,end));
                    } catch (DateTimeException e) {
                        out.println("Erro: Formato Incorreto de data.\n");
                    }
                }
                if (opcao.equals("5")==true) {
                    opcao = "";
                    while (opcao.equals("1")==false && opcao.equals("2")==false) {
                        out.println("Ordenada por: 1-Data de Emissao 2-Valor");
                        opcao = reader.nextLine();
                    }   
                    if (opcao.equals("1")==true) prog.ordenaFaturasEmpresa(user.getNIF(), new FDComparator()).forEach(f->out.println(f.toString()));
                    if (opcao.equals("2")==true) prog.ordenaFaturasEmpresa(user.getNIF(),new FVComparator()).forEach(f->out.println(f.toString()));
                }    
                if (opcao.equals("6")==true) out.println(prog.sobre(user.getNIF())); 
                if (opcao.equals("7")==true) break;
                opcao = "";
            }
            reader.close();
    }    
     /**
     * @param args
     */
    public static void main(String[] args) {
        JavaFatura app = new JavaFatura();
        app.run();
    }
}
