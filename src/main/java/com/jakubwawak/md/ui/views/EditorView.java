/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.md.ui.views;

import com.jakubwawak.md.ui.components.markdownEditor.MarkdownEditor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * Main application web view
 */
@PageTitle("md editor")
@Route("editor")
@RouteAlias("/")
public class EditorView extends VerticalLayout {

    MarkdownEditor markdownEditor;

    /**
     * Constructor
     */
    public EditorView(){
        this.getElement().setAttribute("theme", Lumo.DARK);
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        markdownEditor = new MarkdownEditor(null);
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){
        prepareComponents();
        add(markdownEditor);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
        getStyle().set("background-color","white");
        getStyle().set("--lumo-font-family","Monospace");
        getStyle().set("--lumo-primary-color","pink");
    }

}