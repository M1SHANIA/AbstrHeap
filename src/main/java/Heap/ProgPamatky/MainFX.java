package Heap.ProgPamatky;


import Heap.Model.Zamek;
import Heap.Model.ZamekObal;
import Heap.Pamatky.PamatkyNew;
import Heap.Table.BinStromException;
import Heap.enun.eTypProhl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

import java.util.Iterator;
import java.util.Optional;

import static Heap.enun.eTypKey.GPS;
import static Heap.enun.eTypKey.NAZEV;

public class MainFX extends Application {

    private PamatkyNew pamatky;
    private ListView<ZamekObal> langsListView;
    private TextField field;
    private eTypProhl typProhl;


    public static void main(String[] args) {
        launch(args);
    }


    private ControlPanelVbox comandsV() throws BinStromException {
        var controlV = new ControlPanelVbox();
        controlV.newButton("FIRST", nastavPrvni());
        controlV.newButton("NEXT", nastavDalsi());
        controlV.newButton("PREVIOUS", nastavPred());
        controlV.newButton("LAST", nastavPosledni());
        controlV.newButton("UKAZ NEJBLIZ",najdiNejblizsi());
        controlV.newButton("ODEBER NEJBLIZ",odeberNejblizsi());
        controlV.newComboBox("ITERATION",typIteraci());
//        controlV.newButton("NEW POLOHA",change());
        return controlV;
    }
//    private EventHandler<ActionEvent> change() throws BinStromException {
//        return actionEvent -> {
//            TextInputDialog dialog = new TextInputDialog();
//            dialog.setTitle("GPS");
//            dialog.setHeaderText("Zadejte gps");
//            dialog.setContentText("GPS:");
//            Optional<String> result = dialog.showAndWait();
//            if (result.isPresent()) {
//                String klic = result.get();
////                    pamatky.setPoloha(klic);
//                field.setText(klic);
//            }
//        };
//    }
    private EventHandler<ActionEvent> odeberNejblizsi() {
        return actionEvent -> {
            try {
                pamatky.odeberNejblizsi();
            } catch (BinStromException e) {
                throw new RuntimeException(e);
            }
            obnovList();
        };
    }


    private EventHandler<ActionEvent> typIteraci() {
        return actionEvent -> {
            typProhl = ((ComboBox<eTypProhl>)actionEvent.getSource()).getValue();
            obnovList();
        };
    }

    private EventHandler<ActionEvent> nastavPred() {
        return actionEvent -> {
            langsListView.getSelectionModel().select(langsListView.getSelectionModel().getSelectedIndex()-1);
        };
    }


    private EventHandler<ActionEvent> najdiNejblizsi() {
        return actionEvent -> {
            langsListView.getSelectionModel().select(pamatky.zpristupniNejblizsi());
            langsListView.scrollTo(pamatky.zpristupniNejblizsi());
        };
    }


    private ControlPanelHBox commandsH() {
        var controlH = new ControlPanelHBox();
        controlH.newLabel("");
        controlH.newButton("IMPORT", nacist());
        controlH.newButton("CLEAR ALL", zrusit());
//        controlH.newButton("NEW DATA",novyA());
//        controlH.newButton("Novy Zamek",novyA());
        controlH.newButton("VYBUDUJ", prebuduj());
//        field = controlH.newField(pamatky.getPoloha());
        return controlH;
    }


    private EventHandler<ActionEvent> prebuduj() {
        return actionEvent -> {
            pamatky.vybuduj();
            obnovList();
        };
    }


    @Override
    public void start(Stage stage) throws Exception {
        pamatky = new PamatkyNew();
        typProhl = eTypProhl.HLOUBKA;
        FlowPane root = new FlowPane(creatList(),comandsV(),commandsH());
        Scene scene = new Scene(root, 620,590);
        stage.setScene(scene);
        stage.setMaxWidth(640);
        stage.setMaxHeight(620);
        stage.setResizable(false);
        stage.setTitle("Zamky");
        stage.show();
    }


//    private EventHandler<ActionEvent> novyA() {
//        return event ->{
//            try {
//                ProcesDialog procesDialog = new ProcesDialog(new Zamek("", "", ""));
//                procesDialog.showAndWait();
//                Zamek zamek = procesDialog.vratit();
//                procesDialog.close();
//                if (zamek != null) {
//                    ZamekObal zamekCompare = new ZamekObal(zamek, Coordinates.nacist(pamatky.getPoloha(), zamek.getGps()));
//                    pamatky.vloz(zamekCompare);
//                    obnovList();
//                }
//            } catch (BinStromException e) {
//                alert(e.getMessage());
//            }
//
//        };
//    }

    private void obnovList() {
        Iterator<ZamekObal> iterator = pamatky.vytvorIterator(eTypProhl.HLOUBKA);
        langsListView.getItems().clear();
        if (typProhl == eTypProhl.SIRKA) {
            iterator = pamatky.vytvorIterator(eTypProhl.SIRKA);
        }
        while (iterator.hasNext()) {
            langsListView.getItems().add(iterator.next());
        }
    }

    private EventHandler<ActionEvent> zrusit() {
        return event ->{
            langsListView.getSelectionModel().select(null);
            pamatky.zrus();
            langsListView.getItems().clear();
        };

    }

    private EventHandler<ActionEvent> nacist() {
        return actionEvent -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("File");
            dialog.setHeaderText("File");
            dialog.setContentText("FileName:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                int i = 0;
                try {
                    i = pamatky.importDatZTXT(result.get());
                } catch (BinStromException e) {
                    alert(e.getMessage());
                }
                if(i>0)obnovList();
                else new Alert(Alert.AlertType.INFORMATION,"Chyba cteni dat",ButtonType.OK).show();
            }
        };

    }




    private EventHandler<ActionEvent>   nastavPosledni() {
        return event -> {
            langsListView.getSelectionModel().selectLast();
            langsListView.scrollTo(langsListView.getSelectionModel().getSelectedItem());
        };
    }

    private EventHandler<ActionEvent> nastavDalsi() {
        return event ->{
            langsListView.getSelectionModel().selectNext();
        };
    }


    private EventHandler<ActionEvent> nastavPrvni() {
        return event -> {
            langsListView.getSelectionModel().selectFirst();
            langsListView.scrollTo(langsListView.getSelectionModel().getSelectedItem());
        };
    }


    private ListView<ZamekObal> creatList(){
        ObservableList<ZamekObal> langs = FXCollections.observableArrayList();
        langsListView = new ListView<>(langs);
        langsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        langsListView.setPrefSize(500, 500);
        return langsListView;
    }

    private void alert(String msg){
        new Alert(Alert.AlertType.INFORMATION,msg,ButtonType.OK).show();
    }

}
