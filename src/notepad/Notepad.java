
package notepad;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.IndexRange;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Notepad extends Application{
    protected NotepadBase np;
    protected File file ;
    boolean isFileSaved = true;
    protected Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
    List<String> undoable = new ArrayList<String>();
    String copiedtxt ;
    
    public void inVokeUI() {
       launch();  
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        np = new NotepadBase();
        Scene sc = new Scene(np , 500 ,500);
        
        np.menuItem.setOnAction((event) -> {newFileOption(primaryStage);});
        np.menuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        np.menuItem0.setOnAction((event) -> {openFileOption(primaryStage);});
        np.menuItem0.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        np.menuItem1.setOnAction((event) -> {saveFileOption(primaryStage);});
        np.menuItem1.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        np.menuItem2.setOnAction((event) -> {closeFileOption(primaryStage);});
        np.menuItem3.setOnAction((event) -> {undoOption(primaryStage);});
        np.menuItem3.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        np.menuItem4.setOnAction((event) -> {cutOption();});
        np.menuItem4.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        np.menuItem5.setOnAction((event) -> {copyOption();});
        np.menuItem5.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        np.menuItem6.setOnAction((event) -> {pasteOption();});
        np.menuItem6.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        np.menuItem7.setOnAction((event) -> {selectAllOption();});
        np.menuItem7.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        np.menuItem8.setOnAction((event) -> {AboutOption(primaryStage);});
        np.textArea.setOnKeyReleased((event) -> { keyTypeHandler(event); });
        
        saveDialogBuild();
        primaryStage.setScene(sc);
        primaryStage.setTitle("Notepad");
        primaryStage.show();
        
        
    }
    
    
    protected void undoOption(Stage primaryStage)
    {
        if(undoable.size() > 0)
        {
            np.textArea.setText(undoable.get(undoable.size()-1));
            undoable.remove(undoable.size()-1);
            np.textArea.end();

        }
    }  
    //Method Invoked when New File Menu Option clicked
    protected void newFileOption(Stage primaryStage)
    {
        if(isFileSaved)
        {
            np.textArea.clear();
            undoable.clear();
        }
        else
        {
            if(saveFileDialog(primaryStage))
            {
                np.textArea.clear();
                undoable.clear();
            }
        }
    }
    //Method Invoked when Open File Menu Option clicked
    protected void openFileOption(Stage primaryStage)
    {
        if(isFileSaved)
        {
            openFile(primaryStage);
        }
        else
        {
            if(saveFileDialog(primaryStage))
            {
                openFile(primaryStage);
            }
        }
    }
    //Method Invoked when Save File Menu Option clicked
    protected void saveFileOption(Stage primaryStage)
    {
        saveFileDialog(primaryStage);
    }
    protected boolean saveFileDialog(Stage primaryStage)
    {
        Optional<ButtonType> result =  saveAlert.showAndWait();
        String res = result.get().getText() ; 
        System.out.println();
        if(res.toLowerCase().equals("yes"))
        {
           
            FileChooser openFileChooser = new FileChooser();
            openFileChooser.setTitle("Open Text File");
            openFileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text File", "*.txt"));
            file = openFileChooser.showSaveDialog(primaryStage);
            if(file != null)
            {
                try {  
                    PrintWriter fr = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                    fr.write(this.np.textArea.getText());
                    fr.close();
                    isFileSaved = true;
                    return true;
                } catch (IOException ex) {
                    new Alert(Alert.AlertType.ERROR , "Error in Saving file").show();
                    Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);  
                }
              
            }
            
        }
        else if(res.toLowerCase().equals("no"))
        {
            return true;
        }
        else if(res.toLowerCase().equals("cancel"))
        {
             return false;
        }
         return false;
    }
    //Method Invoked when Close File Menu Option clicked
    protected void closeFileOption(Stage primaryStage)
    {
        if(isFileSaved)
        {
            
        }
        else
        {
            if(saveFileDialog(primaryStage))
            {
                System.exit(0);
            }
        }

    }
    protected void openFile(Stage primaryStage){
        FileChooser openFileChooser = new FileChooser();
            openFileChooser.setTitle("Open Text File");
            openFileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text File", "*.txt"));
            file = openFileChooser.showOpenDialog(primaryStage);
            if(file != null)
                fileLoader();
    }
    
    protected void fileLoader()  
    {
        this.np.textArea.setText("");
        isFileSaved = true;
        try {
            String line ; 
            BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while((line = fr.readLine()) != null)
                this.np.textArea.appendText(line+"\n");
            fr.close();
        } catch (IOException ex) {
            Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
    protected void saveDialogBuild()
    {
        saveAlert.setTitle("Do you want to save file !");
        saveAlert.setHeaderText("Do you want to save the file ?");
        ButtonType yesButton = new ButtonType("Yes" , ButtonData.YES);
        ButtonType noButton = new ButtonType("No" , ButtonData.NO);
        ButtonType cancelButton = new ButtonType("Cancel" , ButtonData.CANCEL_CLOSE);
        saveAlert.getButtonTypes().setAll(yesButton , noButton , cancelButton);
    }

    private void keyTypeHandler(KeyEvent event) {
        if(event.getCode() == KeyCode.SPACE  || event.getCode() == KeyCode.BACK_SPACE)
        {   
            if(undoable.isEmpty())
               undoable.add("");
               undoable.add(np.textArea.getText());
        }
        isFileSaved = false ;
    }

    private void cutOption() {
        IndexRange selectedText = np.textArea.getSelection();
        StringBuilder sb = new StringBuilder(np.textArea.getText());
        if(selectedText.getStart() != selectedText.getEnd())
        {
            copiedtxt = np.textArea.getSelectedText();
            sb.delete(selectedText.getStart(), selectedText.getEnd());
            np.textArea.setText(sb.toString());
            np.textArea.end();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(copiedtxt), null);
        }
    }
    private void copyOption()
    {
        IndexRange selectedText = np.textArea.getSelection();
        StringBuilder sb = new StringBuilder(np.textArea.getText());
        if(selectedText.getStart() != selectedText.getEnd())
        {
            copiedtxt = np.textArea.getSelectedText();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(copiedtxt), null);
        }
    }

    private void selectAllOption() {
        np.textArea.selectAll();
    }

    private void pasteOption() {
        try {
            String data = Toolkit.getDefaultToolkit().getSystemClipboard()
                    .getData(DataFlavor.stringFlavor).toString();
            StringBuilder sb = new StringBuilder(np.textArea.getText());
            IndexRange selectedText = np.textArea.getSelection();

            if(selectedText.getStart() != selectedText.getEnd())
            {
                sb.delete(selectedText.getStart(), selectedText.getEnd());
                sb.insert(selectedText.getStart(), data);
                np.textArea.setText(sb.toString());
                np.textArea.end();
            }
            else
            {
                int caretPos = np.textArea.getCaretPosition();
                sb.insert(caretPos, data);
                np.textArea.setText(sb.toString());
                np.textArea.end();
            }
            if(undoable.isEmpty())
               undoable.add("");
               undoable.add(np.textArea.getText());

        } catch (UnsupportedFlavorException | IOException ex) {
            Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AboutOption(Stage primaryStage) {
        
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About Notepad");
        about.setContentText("Notepad App Developed by Muhammed Fawzy");
        about.setHeaderText("About Notepad Application");
        about.show();

    }
}








