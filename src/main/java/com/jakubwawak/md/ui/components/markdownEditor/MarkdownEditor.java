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
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.parser.Parser;
import org.yaml.snakeyaml.error.Mark;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Object for creating markdown editor
 */
public class MarkdownEditor extends VerticalLayout {

    public String currentValue;
    public String mode = "LIGHT"; // values - DARK / LIGHT
    public SplitLayout splitLayout;

    TextArea editorArea;

    Div editorPreview;
    Html htmlPreview;

    HorizontalLayout header;
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
        getStyle().set("background-color","transparent");
        getStyle().set("color","black");
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
     * Function for refreshing preview from other component
     */
    public void refreshPreview(){
        parseToHtml();
        showNotification("Preview refreshed!");
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

        // set color mode
        if ( mode.equals("DARK") ){
            this.getElement().setAttribute("theme", Lumo.DARK);
            getStyle().set("background-image","linear-gradient(gray,black)");
            getStyle().set("color","black");
        }
        else{
            this.getElement().setAttribute("theme", Lumo.LIGHT);
            getStyle().set("background-image","linear-gradient(gray,white)");
            getStyle().set("color","black");
            editorArea.getStyle().set("color","black");
        }

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

        splitLayout = new SplitLayout(leftEditorLayout,rightPreviewLayout);
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

        StreamResource res = new StreamResource("logo.png", () -> {
            return MarkdownEditor.class.getClassLoader().getResourceAsStream("images/logo.png");
        });
        Image logo = new Image(res,"logo");
        logo.setHeight("5rem");
        logo.setWidth("5rem");

        // prepare window layout and components
        FlexLayout center_layout = new FlexLayout();
        center_layout.setSizeFull();
        center_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        center_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        center_layout.add(logo);

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.START);
        left_layout.setAlignItems(Alignment.CENTER);

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.END);
        right_layout.setAlignItems(Alignment.CENTER);

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
        TerminalWindow tw = new TerminalWindow(this);
        add(tw.main_dialog);
        tw.main_dialog.open();
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


    // interconnectors

    /**
     * Function for showing notification on the component
     * @param notificationText
     */
    public void showNotification(String notificationText){
        Notification notification = Notification.show(notificationText, 5000,
                Notification.Position.MIDDLE);
        notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
    }
}
