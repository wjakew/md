/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.md.ui.components.markdownEditor;

import com.jakubwawak.md.ui.components.terminal.TerminalInput;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.Autocomplete;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Window for showing content on the screen
 */
public class TerminalWindow {

    // variables for setting x and y of window
    public String width = "80%";
    public String height = "20%";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    MarkdownEditor editor;
    TerminalInput terminal;

    /**
     * Constructor
     */
    public TerminalWindow(MarkdownEditor editor){
        this.editor = editor;
        main_dialog = new Dialog();
        main_dialog.setModal(true);
        main_dialog.setResizable(false);
        main_dialog.setDraggable(true);
        main_layout = new VerticalLayout();
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        // set components
        terminal = new TerminalInput("100%",editor);
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog(){
        prepare_components();
        // set layout
        main_layout.add(terminal);

        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_layout.getStyle().set("border-radius","25px");
        main_layout.getStyle().set("background-color",backgroundStyle);
        main_layout.getStyle().set("--lumo-font-family","Monospace");
        main_dialog.add(main_layout);
        main_dialog.setWidth(width);main_dialog.setHeight(height);
    }

    /**
     * Function for closing window
     */
    private void closeWindow(){
        main_dialog.close();
    }
}