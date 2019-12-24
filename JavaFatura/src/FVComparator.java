import java.util.Comparator;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
/**
 * Comparador do Valor entre duas Faturas 
 * -1 se a primeira e menor, 1 se for maior, 0 se forem iguais.
 *
 * @author Nuno Rei
 * @author Joao Marques
 * @author Luis Abreu
 * @version Maio 2018
 */
public class FVComparator implements Comparator<Fatura>, Serializable 
{
    public int compare (Fatura f1, Fatura f2) 
    {
        double r1 = ((Fatura) f1).getValor();
        double r2 = ((Fatura) f2).getValor();
        int cr = 0;
        if (r1<r2) cr = -1;
        if (r1>r2) cr = 1;
        return cr;
    }
}
