/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.md.ui.components.markdownEditor;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Window for exporting data to file
 */
public class ExportWindow {

    // variables for setting x and y of window
    public String width = "50%";
    public String height = "30%";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    String mdContent;
    Button export_button;
    TextField fileName_field;

    /**
     * Constructor
     */
    public ExportWindow(String mdContent){
        this.mdContent = mdContent;
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        // set components
        export_button = new Button("Export to File", VaadinIcon.FILE.create(),this::setExport_button);
        export_button.setWidthFull();
        export_button.addThemeVariants(ButtonVariant.LUMO_CONTRAST,ButtonVariant.LUMO_PRIMARY);
        export_button.getStyle().set("--lumo-font-family","Monospace");

        fileName_field = new TextField(".md name");
        fileName_field.setValue("mdAppNote");
        fileName_field.setWidthFull();
        fileName_field.getStyle().set("--lumo-font-family","Monospace");
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog(){
        prepare_components();
        // set layout
        main_layout.add(new H6("CREATE A FILE NAME"));
        main_layout.add(new H6(mdContent.length()+" character(s)"));
        main_layout.add(fileName_field);
        main_layout.add(export_button);

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
     * export_button action
     * @param ex
     */
    private void setExport_button(ClickEvent ex){
        try{
            if ( !fileName_field.getValue().isEmpty() ){
                String fileName = fileName_field.getValue().replaceAll(".md","");
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName+".md"));
                writer.write(mdContent);
                writer.close();
                File file = new File(fileName+".md");
                if ( file.exists() ){
                    FileDownloaderComponent fdc = new FileDownloaderComponent(file);
                    main_layout.add(fdc.dialog);
                    fdc.dialog.open();
                    main_dialog.close();
                }
                else{
                    Notification.show("Failed to save file on the server, check log!");
                }
            }
            else{
                Notification.show("Name of the file cannot be empty!");
            }
        } catch (IOException e) {
            Notification.show(e.toString());
        }

    }
}