import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
/**
 * Classe com 11 Atividades Economicas, no formato de constantes, e que apresentam valores de deducao 
 * fiscal variados consoante a atividade em questao.
 * 
 * @author Nuno Rei, Joao Marques, Luis Abreu
 * @version Maio 2018
 */
public  class AtividadesEconomicas implements Serializable
{
    /** Atividades Economicas */   
        private static final String DespesasFamiliares = "1";
        private static final String Saude = "2";
        private static final String Educacao = "3";
        private static final String Imoveis = "4";
        private static final String Lares = "5";
        private static final String RepManutVeiculos = "6";
        private static final String Alimentacao = "7";
        private static final String Alojamento = "8";
        private static final String Cabeleireiros = "9";
        private static final String Veterinarios = "10";
        private static final String PassesTransportesPub = "11";
        private static final String outros = "12";
   
    /**
     * Construtor vazio
     */
    public AtividadesEconomicas()
    {

    }
    /**
     * Associa a Atividade Economica com uma das que possuem deducao fiscal,e faz a conversao de numero para Atividade Economica associado
     * @param Atividade Economica numa String com numero
     * @return Atividade Economica
    */
    public static String associa(String ae) {
        String aeconomica = ae;
        switch(ae) {
            case "1": aeconomica = "1-Despesas Familiares Gerais";
                      break;
            case "2": aeconomica = "2-Saude";
                      break;
            case "3": aeconomica = "3-Educacao";
                      break;
            case "4": aeconomica = "4-Imoveis";
                      break;
            case "5": aeconomica = "5-Lares";
                      break;
            case "6": aeconomica = "6-Reparacao e Manutencao de Veiculos";
                      break;
            case "7": aeconomica = "7-Alimentacao";
                      break;
            case "8": aeconomica = "8-Alojamento, Restauracao, e Similares";
                      break;
            case "9": aeconomica = "9-Atividades de Salao de Cabeleireiros e Institutos de Beleza";
                      break;  
            case "10": aeconomica = "10-Veterinarios";
                      break; 
            case "11": aeconomica = "11-Passes para Transportes Publicos";
                      break;
        }
        return aeconomica;
    }
    /**
     * Atribuicao da deducao associada a cada Atividade Economica
     * @param Atividade Economica 
     * @return Deducao associada
     */
    public static double deducaoAssociada(String ae) 
    {
        double aeconomica = 0;
        switch(ae) {
            case "1": aeconomica = 0.25;
                      break;
            case "2": aeconomica = 0.15;
                      break;
            case "3": aeconomica = 0.3;
                      break;
            case "4": aeconomica = 0.15;
                      break;
            case "5": aeconomica = 0.25;
                      break;
            case "6": aeconomica = 0.1;
                      break;
            case "7": aeconomica = 0.2;
                      break;
            case "8": aeconomica = 0.15;
                      break;
            case "9": aeconomica = 0.05;
                      break;  
            case "10": aeconomica = 0.1;
                      break; 
            case "11": aeconomica = 0.25;
                      break;
        }
        return aeconomica;
    }
    /**
     * Legenda que seve como menu para a escolha de Atividades Economicas
     *
     * @return Legenda com as Atividades Economicas associadas a numeros.
     */
    public static String legendaAE()
    {
        StringBuilder sb =  new StringBuilder();
        sb.append("1 -> Despesas Familiares Gerais\n");
        sb.append("2 -> Saude\n");
        sb.append("3 -> Educacao\n");
        sb.append("4 -> Imoveis\n");
        sb.append("5 -> Lares\n");
        sb.append("6 -> Reparacao e Manutencao de Veiculos\n");
        sb.append("7 -> Alimentacao\n");
        sb.append("8 -> Alojamento, Restauracao, e Similares\n");
        sb.append("9 -> Atividades de Salao de Cabeleireiros e Institutos de Beleza\n");
        sb.append("10 -> Veterinarios\n");
        sb.append("11 -> Passes para Transportes Publicos\n");
        sb.append("12 -> Outros");
        return sb.toString();
    }
}
