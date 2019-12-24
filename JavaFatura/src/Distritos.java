import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
/**
 * Classe com 11 Distritos de Portugal, no formato de constantes, e e utilizada para associar a localizacao das empresas com valores de deducao 
 * fiscal variados consoante o Distrito. A discriminacao e feita com base no numero de habitantes destes Distritos, beneficiando mais aqueles com
 * menos populacao.
 *
 * @author Nuno Rei, Joao Marques, Luis Abreu
 * @version Maio 2018
 */
public class Distritos implements Serializable
{
    /** Distritos */
        private static final String Lisboa = "1";
        private static final String Porto = "2";
        private static final String Setubal = "3";
        private static final String Braga = "4";
        private static final String Aveiro = "5";
        private static final String Leiria = "6";
        private static final String Santarem = "7";
        private static final String Faro = "8";
        private static final String Coimbra = "9";
        private static final String Viseu = "10";
        private static final String VianadoCastelo = "11";
        private static final String outros = "12";
   
    /**
     * Constructor Vazio
     */
    public Distritos()
    {
    }
    /**
     * Associa o Distrito com um dos que possuem deducao fiscal,e faz a conversao de numero para Distrito associado
     * @param Distrito String com numero
     * @return Distrito
    */
    public static String associa(String dis) {
        String distrito = dis;
        switch(dis) {
            case "1": distrito = "1-Lisboa";
                      break;
            case "2": distrito = "2-Porto";
                      break;
            case "3": distrito = "3-Setubal";
                      break;
            case "4": distrito = "4-Braga";
                      break;
            case "5": distrito = "5-Aveiro";
                      break;
            case "6": distrito = "6-Leiria";
                      break;
            case "7": distrito = "7-Santarem";
                      break;
            case "8": distrito = "8-Faro";
                      break;
            case "9": distrito = "9-Coimbra";
                      break;  
            case "10": distrito = "10-Viseu";
                      break; 
            case "11": distrito = "11-Viana do Castelo";
                      break;
        }
        return distrito;
    }
    /**
     * Atribuicao da deducao associada a cada Distrito, quanto mais populacao o Distrito tiver menos sera a deducao a ele associada
     * @param Distrito 
     * @return Deducao associada
     */
    public static double deducaoAssociada(String dis) 
    {
        double distrito = 0;
        switch(dis) {
            case "1": distrito = 0.02;
                      break;
            case "2": distrito = 0.03;
                      break;
            case "3": distrito = 0.05;
                      break;
            case "4": distrito = 0.05;
                      break;
            case "5": distrito = 0.07;
                      break;
            case "6": distrito = 0.10;
                      break;
            case "7": distrito = 0.10;
                      break;
            case "8": distrito = 0.11;
                      break;
            case "9": distrito = 0.13;
                      break;  
            case "10": distrito = 0.15;
                      break; 
            case "11": distrito = 0.20;
                      break;
        }
        return distrito;
    }
    /**
     * Legenda que seve como menu para a escolha de Distritos
     *
     * @return Legenda com os Distritos associados a numeros.
     */
    public static String legendaDistritos()
    {
        StringBuilder sb =  new StringBuilder();
        sb.append("1 -> Lisboa\n");
        sb.append("2 -> Porto\n");
        sb.append("3 -> Setubal\n");
        sb.append("4 -> Braga\n");
        sb.append("5 -> Aveiro\n");
        sb.append("6 -> Leiria\n");
        sb.append("7 -> Santarem\n");
        sb.append("8 -> Faro\n");
        sb.append("9 -> Coimbra\n");
        sb.append("10 -> Viseu\n");
        sb.append("11 -> Viana do Castelo\n");
        sb.append("12 -> Outros");
        return sb.toString();
    }
}