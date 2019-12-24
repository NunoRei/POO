import java.util.Comparator;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
/**
 * Comparador do total gasto entre dois Contribuintes Individuais.
 * -1 caso o primero tenha gasto menos, 1 se gastou mais, desempate feito atraves da diferenca entre nifs.
 *
 * @author Nuno Rei
 * @author Joao Marques
 * @author Luis Abreu
 * @version Maio 2018
 */
public class TotalGastoComparator implements Comparator<Individual>, Serializable
{
    @Override
    public int compare (Individual i1, Individual i2) 
    {
        double res = i2.totalGasto()-i1.totalGasto(); 
        if (res != 0) return (int)res;
        return Integer.parseInt(i2.getNIF())-Integer.parseInt(i1.getNIF());
    }
}
