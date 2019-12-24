/**
 * Exceccao para Contribuintes que nao sao individuais
 *
 * @author Nuno Rei
 * @author Joao Marques
 * @author Luis Abreu
 * @version Maio 2018
 */
public class NotIndividualExcp extends Exception
{
   NotIndividualExcp() 
    {
       super();
    }
   NotIndividualExcp(String s) 
   {
       super(s);
   }
}
