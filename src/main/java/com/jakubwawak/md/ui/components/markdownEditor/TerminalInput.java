/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.md.ui.components.markdownEditor;

import com.jakubwawak.md.ui.components.markdownEditor.MarkdownEditor;
import com.jakubwawak.md.ui.components.markdownEditor.TerminalInterpreter;
import com.jakubwawak.md.ui.components.markdownEditor.TerminalWindow;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
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
    TerminalInterpreter terminalInterpreter;
    MarkdownEditor editor;
    TerminalWindow terminalWindow;

    /**
     * Constructor
     */
    public TerminalInput(String width, MarkdownEditor editor, TerminalWindow terminalWindow){
        this.editor = editor;
        this.width = "100%";
        this.terminalWindow = terminalWindow;
        terminalInterpreter = new TerminalInterpreter(editor,terminalWindow);
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

        terminalInput.addKeyPressListener(e->{
            if ( e.getKey().equals(Key.ENTER) ){
                if ( !terminalInput.getValue().isEmpty()){
                    String userInput = terminalInput.getValue();
                    int ans = terminalInterpreter.runTerminal(userInput);
                    if ( ans == 1 ) {
                        terminalInput.setValue("");
                    }
                }
                else{
                    editor.showNotification("Terminal is empty!");
                }
            }
            else{
                String userInput = terminalInput.getValue();
                // find something similar
                // TODO
            }
        });
    }

    /**
     * Function for preparing component layout
     */
    void prepareLayout(){
        add(terminalInput);
        terminalInput.focus();
    }
}
