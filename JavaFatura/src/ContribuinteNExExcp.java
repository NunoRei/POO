/**
 * Exceccao para Contribuintes que nao se encontram registados no sistema
 *
 * @author Nuno Rei
 * @author Joao Marques
 * @author Luis Abreu
 * @version Maio 2018
 */
public class ContribuinteNExExcp extends Exception
{
    ContribuinteNExExcp() 
    {
        super();
    }
    ContribuinteNExExcp(String s) 
    {
        super(s);
    }
}
