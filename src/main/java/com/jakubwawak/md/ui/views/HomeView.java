/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.md.ui.views;

import com.jakubwawak.md.MdApplication;
import com.jakubwawak.md.ui.components.markdownEditor.EditorWindow;
import com.jakubwawak.md.ui.components.markdownEditor.MarkdownEditor;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * Main application web view
 */
@PageTitle("md by Jakub Wawak")
@Route("home")
@RouteAlias("/")
public class HomeView extends VerticalLayout {


    Button openEditor_button;
    Button openFullScreen_button;

    /**
     * Constructor
     */
    public HomeView(){
        this.getElement().setAttribute("theme", Lumo.DARK);
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        openEditor_button = new Button("Create New Editor", VaadinIcon.EDIT.create(),this::setOpenEditor_button);
        openEditor_button.getStyle().set("background-color","black");
        openEditor_button.getStyle().set("color","white");
        openEditor_button.getStyle().set("font-family","Monospace");
        openEditor_button.setWidth("50%");

        openFullScreen_button = new Button("Open Board", VaadinIcon.WORKPLACE.create(),this::setOpenFullScreen_button);
        openFullScreen_button.getStyle().set("background-color","black");
        openFullScreen_button.getStyle().set("color","white");
        openFullScreen_button.getStyle().set("font-family","Monospace");
        openFullScreen_button.setWidth("50%");
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){
        prepareComponents();
        StreamResource res = new StreamResource("logo.png", () -> {
            return MarkdownEditor.class.getClassLoader().getResourceAsStream("images/logo.png");
        });
        Image logo = new Image(res,"logo");
        logo.setHeight("15rem");
        logo.setWidth("15rem");

        H4 headerName = new H4("by Jakub Wawak");
        headerName.getStyle().set("color","black");
        headerName.getStyle().set("font-family","Monospace");
        add(headerName);

        add(logo);

        H4 headerVersion = new H4(MdApplication.version+"/"+MdApplication.build);
        headerVersion.getStyle().set("color","black");
        headerVersion.getStyle().set("font-family","Monospace");
        add(headerVersion);

        add(openEditor_button);
        add(openFullScreen_button);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
        getStyle().set("background-color","white");
        getStyle().set("color","black");
        getStyle().set("--lumo-font-family","Monospace");
        getStyle().set("--lumo-primary-color","pink");
    }

    /**
     * openeditor_button action
     * @param ex
     */
    private void setOpenEditor_button(ClickEvent ex){
        EditorWindow ew = new EditorWindow();
        add(ew.main_dialog);
        ew.main_dialog.open();
    }

    private void setOpenFullScreen_button(ClickEvent ex){
        openFullScreen_button.getUI().ifPresent(ui -> ui.navigate("editor"));
    }

}