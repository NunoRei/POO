import java.util.ArrayList;
import java.time.LocalDate;
/**
 * Write a description of class Dados here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public final class Dados
{
   public Dados(Sistema prog) 
   {
            //Cria Individuais
            ArrayList<String> ai1 = new ArrayList<String>();
            ai1.add("1");
            ai1.add("3");
            ai1.add("4");
            Individual i1 = new Individual("1","coisa@coisa","Teste1","moradateste 1","teste",3,ai1);
            prog.adicionaUser(i1);
            ArrayList<String> ai2 = new ArrayList<String>();
            ai2.add("2");
            Individual i2 = new Individual("2","coisa@coisa","Teste2","moradateste 2","teste",1,ai2);
            prog.adicionaUser(i2);
            ArrayList<String> ai3 = new ArrayList<String>();
            ai3.add("3");
            ai3.add("1");
            ai3.add("4");
            Individual i3 = new Individual("3","coisa@coisa","Teste3","moradateste 3","teste",3,ai3);
            prog.adicionaUser(i3);
            ArrayList<String> ai4 = new ArrayList<String>();
            ai4.add("4");
            ai4.add("1");
            ai4.add("3");
            Individual i4 = new Individual("4","coisa@coisa","Teste4","moradateste 4","teste",3,ai4);
            prog.adicionaUser(i4);
            ArrayList<String> ai5 = new ArrayList<String>();
            ai5.add("5");
            Individual i5 = new Individual("5","coisa@coisa","Teste5","moradateste 5","teste",1,ai5);
            prog.adicionaUser(i5);
            //Cria Empresas
            ArrayList<String> ae6 = new ArrayList<String>();
            ae6.add("5");
            Empresariais e6 = new Empresariais("6","coisa@coisa","Teste6","moradateste 6","teste",ae6,"4");
            prog.adicionaUser(e6);
            ArrayList<String> ae7 = new ArrayList<String>();
            ae7.add("2");
            ae7.add("4");
            Empresariais e7 = new Empresariais("7","coisa@coisa","Teste7","moradateste 7","teste",ae7,"1");
            prog.adicionaUser(e7);
            //Cria Fatura 
            try {
                Fatura fteste = e6.criaFatura("fatura teste 1","1","teste1",e6.getAEconomicas().get(0),20);
                fteste.setData(LocalDate.of(2018,02,03));
                prog.adicionaFatura(fteste);
                Fatura fteste2 = e6.criaFatura("fatura teste 2","1","teste2",e6.getAEconomicas().get(0),5.90);
                fteste2.setData(LocalDate.of(2018,06,30));
                prog.adicionaFatura(fteste2);
                Fatura fteste3 = e6.criaFatura("fatura teste 3","1","teste3",e6.getAEconomicas().get(0),15.30);
                fteste3.setData(LocalDate.of(2018,01,30));
                prog.adicionaFatura(fteste3);
                Fatura fteste4 = e6.criaFatura("fatura teste 4","2","teste4",e6.getAEconomicas().get(0),200);
                fteste.setData(LocalDate.of(2018,04,03));
                prog.adicionaFatura(fteste4);
                FaturaPendente fteste5 = e7.criaFaturaPendente("fatura pendente teste 1","4","testependente1","",59.99);
                fteste.setData(LocalDate.of(2018,01,20));
                prog.adicionaFaturaPendente(fteste5);
                FaturaPendente fteste6 = e7.criaFaturaPendente("fatura pendente teste 2","1","testependente2","",99.99);
                fteste.setData(LocalDate.of(2018,04,20));
                prog.adicionaFaturaPendente(fteste6);
                Fatura fteste7 = e6.criaFatura("fatura teste 5","5","teste5",e6.getAEconomicas().get(0),150);
                fteste.setData(LocalDate.of(2018,03,24));
                prog.adicionaFatura(fteste7);
            } catch (ContribuinteNExExcp e) {
                e.getMessage();
            } catch (NotIndividualExcp e) {
                e.getMessage();
            }
    } 
}
