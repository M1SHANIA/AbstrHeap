package Heap.Pamatky;

import Heap.AbstrHeap.AbstrHeap;
import Heap.AbstrHeap.IAbstrHeap;
import Heap.Model.Zamek;
import Heap.Model.ZamekObal;
import Heap.Table.BinStromException;
import Heap.enun.eTypProhl;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;



public class PamatkyNew {
    private final double placeY=5007.3245 ;
    private final double placeX=1611.8987;

    private AbstrHeap<ZamekObal> abstrHeap ;


    public PamatkyNew() {
        abstrHeap = new AbstrHeap<>();
    }

    public void vybuduj(){
        abstrHeap.vybuduj();

    }

    public void odeberNejblizsi() throws BinStromException {
        abstrHeap.odeberMax();


    }
    public void zrus(){
        abstrHeap.zrus();

    }
    public int importDatZTXT(String soubor)throws BinStromException {
        List<ZamekObal> arrayList = new ArrayList<>();
        int pocet=0;
            try{
                Scanner scanner = new Scanner(new File(soubor));
                while (scanner.hasNext()){
                    pocet++;
                    String[]result=scanner.nextLine().split("\\s+");
                    String xSouboru=result[1].substring(1)+result[2] ;
                    String ySouboru=result[3].substring(1)+result[4] ;
                    Zamek zamek = new Zamek(pocet, result[0],"N"+xSouboru+" "+"E"+ySouboru);
                    double vzdalenost = scitat(xSouboru,ySouboru);
                    ZamekObal obal = new ZamekObal(zamek,vzdalenost);
                    arrayList.add(obal);
                }
                abstrHeap.prebuduj(arrayList.toArray(new ZamekObal[0]));


            } catch (FileNotFoundException e) {
                error("File was not found");
            }

            return pocet;
        }

    private double scitat(String  x,String y) {
        return Math.abs(Math.sqrt(Math.pow((placeX-Double.parseDouble(x)),2)+Math.pow((placeY-Double.parseDouble(y)),2)));
    }


    public void vloz(ZamekObal data){
        abstrHeap.vloz(data);

    }
    public ZamekObal zpristupniNejblizsi(){
       return abstrHeap.zpristupniMax();


    }
    public Iterator vytvorIterator(eTypProhl typ){
       return abstrHeap.vytvorIterator(typ);
    }


    private void error(String message) {
        new Alert(Alert.AlertType.ERROR,message, ButtonType.OK).show();
    }
    public void poloha(){



    }

}
