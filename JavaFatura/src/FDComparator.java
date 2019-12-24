import java.util.Comparator;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
/**
 * Comparador de Datas entre duas Faturas 
 * Retorna -1, se e uma data anterior, 1 se e uma data posterior, 0 caso sejam iguais.
 *
 * @author Nuno Rei
 * @author Joao Marques
 * @author Luis Abreu
 * @version Maio 2018
 */
public class FDComparator implements Comparator<Fatura>, Serializable
{
    @Override
    public int compare (Fatura f1, Fatura f2) 
    {
        int r = 0;
        if ( (f1.getData()).isBefore(f2.getData()) ) r = -1;
        if ( (f1.getData()).isEqual(f2.getData()) ) r = 0;
        if ( (f1.getData()).isAfter(f2.getData()) ) r = 1;
        return r;
    }
}
