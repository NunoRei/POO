import java.util.Comparator;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
/**
 * Comparador do total faturado entre duas empresas.
 * -1 se a primeira empresa faturou menos, 1 se faturou mais, e se faturaram o mesmo, o desempate e feito com a diferenca entre NIFs.
 *
 * @author Nuno Rei
 * @author Joao Marques
 * @author Luis Abreu
 * @version Maio 2018
 */
public class TotalFaturadoComparator implements Comparator<Empresariais>, Serializable  
{
    @Override
    public int compare (Empresariais e1, Empresariais e2) 
    {
        double res = e2.getTotalFaturado()-e1.getTotalFaturado(); 
        if (res != 0) return (int)res;
        return Integer.parseInt(e2.getNIF())-Integer.parseInt(e1.getNIF());
    }
}
