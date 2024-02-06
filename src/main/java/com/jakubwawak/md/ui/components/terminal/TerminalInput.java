/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.md.ui.components.terminal;

import com.jakubwawak.md.ui.components.markdownEditor.MarkdownEditor;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Function for creating terminal input component
 */
public class TerminalInput extends VerticalLayout {

    String width;

    String placeHolder;

    TextField terminalInput;
    MarkdownEditor editor;

    /**
     * Constructor
     */
    public TerminalInput(String width, MarkdownEditor editor){
        this.editor = editor;
        this.width = "100%";
        setWidth("100%");
        setHeightFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
        prepareComponents();
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        terminalInput = new TextField();
        terminalInput.setWidth(width);
        terminalInput.setPlaceholder("set command...");
    }

    /**
     * Function for preparing component layout
     */
    void prepareLayout(){
        add(terminalInput);
        terminalInput.focus();
    }
}
