/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.md.ui.components.markdownEditor;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.parser.Parser;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Object for creating markdown editor
 */
public class MarkdownEditor extends VerticalLayout {

    public String currentValue;

    TextArea editorArea;

    Div editorPreview;
    Html htmlPreview;

    HorizontalLayout header;
    MenuBar headerMenuBar;
    Button refresh_button;
    /**
     * Costructor
     */
    public MarkdownEditor(String currentValue){
        if ( currentValue != null ){
            this.currentValue = currentValue;
        }
        else{
            this.currentValue = "till death we do art!";
        }
        prepareCompontents();

        // style layout
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

        // add theme
        getStyle().set("background-image","radial-gradient(white,black)");
        getStyle().set("--lumo-font-family","Monospace");
        getStyle().set("--lumo-primary-color","pink");
        getStyle().set("border-radius","25px");

        // prepare layout
        prepareLayout();
    }

    /**
     * Function for update component from different
     * @param content
     */
    public void update(String content){
        editorArea.setValue(content);
        parseToHtml();
    }

    /**
     * Function for parsing string to md as HTML
     * @return String
     */
    String parseToHtml(){
        currentValue = editorArea.getValue();
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(currentValue);
        String value = "<body><div>"+renderer.render(document)+"</div></body>";
        return value;
    }

    /**
     * Function for preparing components
     */
    void prepareCompontents() {

        editorArea = new TextArea();
        editorArea.setSizeFull();
        editorArea.setValue(currentValue);

        editorPreview = new Div();
        editorPreview.setSizeFull();
        editorPreview.getStyle().set("font-family","Monospace");
        editorPreview.getStyle().set("text-align","left");

        htmlPreview= new Html(parseToHtml());
        editorPreview.add(htmlPreview);

        editorArea.addValueChangeListener(e -> {
            Notification.show("Preview updated!");
            htmlPreview = new   Html(parseToHtml());
            editorPreview.removeAll();
            editorPreview.add(htmlPreview);
        });

        refresh_button = new Button("",VaadinIcon.REFRESH.create(),this::setRefresh_button);
        refresh_button.addThemeVariants(ButtonVariant.LUMO_CONTRAST,ButtonVariant.LUMO_PRIMARY);

        UI.getCurrent().addShortcutListener(this::runContextSuggestion,Key.SPACE,KeyModifier.CONTROL);
        UI.getCurrent().addShortcutListener(this::runRefresh,Key.KEY_R,KeyModifier.CONTROL);
    }

    /**
     * Function for preparing main layout
     */
    void prepareLayout(){

        prepareHeader();

        VerticalLayout leftEditorLayout = new VerticalLayout(new H6("editor"),editorArea);
        leftEditorLayout.setSizeFull();
        leftEditorLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        leftEditorLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        leftEditorLayout.getStyle().set("text-align", "center");

        VerticalLayout rightPreviewLayout = new VerticalLayout(new H6("preview"),editorPreview);
        rightPreviewLayout.setSizeFull();
        rightPreviewLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        rightPreviewLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        rightPreviewLayout.getStyle().set("text-align", "center");

        SplitLayout splitLayout = new SplitLayout(leftEditorLayout,rightPreviewLayout);
        setSizeFull();
        splitLayout.setSizeFull();
        splitLayout.setSplitterPosition(70);

        add(header);
        add(splitLayout);
    }

    /**
     * Function for preparing header data
     */
    void prepareHeader(){
        header = new HorizontalLayout();
        headerMenuBar = new MenuBar();
        headerMenuBar.addThemeVariants(MenuBarVariant.LUMO_CONTRAST,MenuBarVariant.LUMO_PRIMARY);

        MenuItem aimItem = headerMenuBar.addItem(".md editor");
        SubMenu subItems = aimItem.getSubMenu();

        MenuItem subItems1 = subItems.addItem(new HorizontalLayout(VaadinIcon.UPLOAD.create(),new H6("Upload")));
        subItems1.setCheckable(false);
        subItems1.setChecked(false);

        MenuItem subItems2 = subItems.addItem(new HorizontalLayout(VaadinIcon.EXCHANGE.create(),new H6("Export")));
        subItems2.setCheckable(false);
        subItems2.setChecked(false);

        MenuItem subItems3 = subItems.addItem(new HorizontalLayout(VaadinIcon.PLUS.create(),new H6("Add Component")));
        subItems3.setCheckable(false);
        subItems3.setChecked(false);

        // event handler
        ComponentEventListener<ClickEvent<MenuItem>> listener = event -> {
            MenuItem selectedItem = event.getSource();
            if ( selectedItem.equals(subItems1)){
                UploadWindow uw = new UploadWindow(this);
                add(uw.main_dialog);
                uw.main_dialog.open();
            }
            else if (selectedItem.equals(subItems2)){
                if ( !editorArea.getValue().isEmpty() ){
                    ExportWindow ex = new ExportWindow(editorArea.getValue());
                    add(ex.main_dialog);
                    ex.main_dialog.open();
                }
                else{
                    Notification.show("Don't save empty files... :)");
                }
            }
            else if (selectedItem.equals(subItems3)){
                // TODO
            }
        };

        // adding listeners
        subItems1.addClickListener(listener);
        subItems2.addClickListener(listener);
        subItems3.addClickListener(listener);

        // prepare window layout and components
        FlexLayout center_layout = new FlexLayout();
        center_layout.setSizeFull();
        center_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        center_layout.setAlignItems(FlexComponent.Alignment.CENTER);

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.START);
        left_layout.setAlignItems(Alignment.CENTER);
        left_layout.setWidth("80%");
        left_layout.add(headerMenuBar,refresh_button);

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.END);
        right_layout.setAlignItems(Alignment.CENTER);
        right_layout.add(new H6(LocalDateTime.now().toString().split("T")[0]));
        right_layout.setWidth("80%");

        header.add(left_layout,center_layout,right_layout);
        header.setWidth("100%");
        header.setMargin(true);
        header.getStyle().set("color","black");
        header.getStyle().set("border-radius","15px");

        header.setMargin(true);
        header.setAlignItems(Alignment.CENTER);
        header.setVerticalComponentAlignment(Alignment.CENTER);
    }

    // actions

    /**
     * Function for running terminal window
     */
    private void runContextSuggestion(){
        Notification.show("Starting context suggestion!");
        //TODO
    }

    /**
     * Function for refreshing preview
     */
    private void runRefresh(){
        Notification.show("Preview updated!");
        htmlPreview = new   Html(parseToHtml());
        editorPreview.removeAll();
        editorPreview.add(htmlPreview);
    }

    /**
     * refresh_button
     * @param ex
     */
    private void setRefresh_button(ClickEvent ex){
        Notification.show("Preview updated!");
        htmlPreview = new   Html(parseToHtml());
        editorPreview.removeAll();
        editorPreview.add(htmlPreview);
    }
}
