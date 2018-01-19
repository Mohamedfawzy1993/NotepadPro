package notepad;

import javafx.scene.effect.*;
import java.lang.*;
import java.util.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class NotepadBase extends BorderPane {

    protected final MenuBar menuBar;
    protected final Menu menu;
    protected final MenuItem menuItem;
    protected final MenuItem menuItem0;
    protected final MenuItem menuItem1;
    protected final SeparatorMenuItem separatorMenuItem;
    protected final MenuItem menuItem2;
    protected final Menu menu0;
    protected final MenuItem menuItem3;
    protected final SeparatorMenuItem separatorMenuItem0;
    protected final MenuItem menuItem4;
    protected final MenuItem menuItem5;
    protected final MenuItem menuItem6;
    protected final SeparatorMenuItem separatorMenuItem1;
    protected final MenuItem menuItem7;
    protected final Menu menu1;
    protected final MenuItem menuItem8;
    protected final TextArea textArea;

    public NotepadBase() {

        menuBar = new MenuBar();
        menu = new Menu();
        menuItem = new MenuItem();
        menuItem0 = new MenuItem();
        menuItem1 = new MenuItem();
        separatorMenuItem = new SeparatorMenuItem();
        menuItem2 = new MenuItem();
        menu0 = new Menu();
        menuItem3 = new MenuItem();
        separatorMenuItem0 = new SeparatorMenuItem();
        menuItem4 = new MenuItem();
        menuItem5 = new MenuItem();
        menuItem6 = new MenuItem();
        separatorMenuItem1 = new SeparatorMenuItem();
        menuItem7 = new MenuItem();
        menu1 = new Menu();
        menuItem8 = new MenuItem();
        textArea = new TextArea();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(menuBar, javafx.geometry.Pos.CENTER);

        menu.setMnemonicParsing(false);
        menu.setText("File");

        menuItem.setMnemonicParsing(false);
        menuItem.setText("New");

        menuItem0.setMnemonicParsing(false);
        menuItem0.setText("Open ...");

        menuItem1.setMnemonicParsing(false);
        menuItem1.setText("Save");

        separatorMenuItem.setMnemonicParsing(false);

        menuItem2.setMnemonicParsing(false);
        menuItem2.setText("Close");

        menu0.setMnemonicParsing(false);
        menu0.setText("Edit");

        menuItem3.setMnemonicParsing(false);
        menuItem3.setText("Undo");

        separatorMenuItem0.setMnemonicParsing(false);

        menuItem4.setMnemonicParsing(false);
        menuItem4.setText("Cut");

        menuItem5.setMnemonicParsing(false);
        menuItem5.setText("Copy");

        menuItem6.setMnemonicParsing(false);
        menuItem6.setText("Paste");

        separatorMenuItem1.setMnemonicParsing(false);

        menuItem7.setMnemonicParsing(false);
        menuItem7.setText("Select All");

        menu1.setMnemonicParsing(false);
        menu1.setText("Help");

        menuItem8.setMnemonicParsing(false);
        menuItem8.setText("About Notepad");
        setTop(menuBar);

        BorderPane.setAlignment(textArea, javafx.geometry.Pos.CENTER);
        textArea.setPrefRowCount(1000);
        setCenter(textArea);
        

        menu.getItems().add(menuItem);
        menu.getItems().add(menuItem0);
        menu.getItems().add(menuItem1);
        menu.getItems().add(separatorMenuItem);
        menu.getItems().add(menuItem2);
        menuBar.getMenus().add(menu);
        menu0.getItems().add(menuItem3);
        menu0.getItems().add(separatorMenuItem0);
        menu0.getItems().add(menuItem4);
        menu0.getItems().add(menuItem5);
        menu0.getItems().add(menuItem6);
        menu0.getItems().add(separatorMenuItem1);
        menu0.getItems().add(menuItem7);
        menuBar.getMenus().add(menu0);
        menu1.getItems().add(menuItem8);
        menuBar.getMenus().add(menu1);

    }
}
