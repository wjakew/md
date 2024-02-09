/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.md.ui.components.markdownEditor;

import com.vaadin.flow.component.splitlayout.SplitLayout;

import java.util.ArrayList;

public class TerminalInterpreter {

    MarkdownEditor editor;
    TerminalWindow terminalWindow;
    ArrayList<String> history;
    ArrayList<String> commandCollection;

    /**
     * Constructor
     * @param editor
     */
    public TerminalInterpreter(MarkdownEditor editor, TerminalWindow terminalWindow){
        this.editor = editor;
        this.terminalWindow = terminalWindow;
        history = new ArrayList<>();
        commandCollection = new ArrayList<>();

        // add commands to command collection
        commandCollection.add("editor export");
        commandCollection.add("editor import");
        commandCollection.add("editor clear");
        commandCollection.add("editor split 'value'");
        commandCollection.add("editor rotate 'vert/hori'");
        commandCollection.add("editor focus");
        commandCollection.add("editor refresh");
        commandCollection.add("component heading1");
        commandCollection.add("component heading2");
        commandCollection.add("component heading3");
        commandCollection.add("component heading4");
        commandCollection.add("component chapter");
        commandCollection.add("component blank");
        commandCollection.add("component break");
        commandCollection.add("component italic");
        commandCollection.add("component bold");
        commandCollection.add("component quote");
        commandCollection.add("component code");
        commandCollection.add("exit");

    }

    /**
     * Function for running terminal
     * @param userEntry
     * @return Integer
     *  1 - terminal executed
     * -1 - error executing
     *  0 - input empty
     */
    public int runTerminal(String userEntry){
        history.add(userEntry);
        String[] words = userEntry.split(" ");
        if ( words.length >= 1 ){
            // at least one record
            // look for key word in first word
            switch(words[0]){
                case "editor":
                {
                    // editor options
                    return terminalEditor(userEntry);
                }
                case "exit":
                {
                    terminalWindow.main_dialog.close();
                    return 1;
                }
                case "component":
                {
                    componentTerminal(userEntry);
                    return 1;
                }
                case "help":
                {
                    HelpView hv = new HelpView(this);
                    editor.add(hv.main_dialog);
                    hv.main_dialog.open();
                    return 1;
                }
            }
            return 1;
        }
        else{
            // terminal empty
            editor.showNotification("No user entry!");
            return 0;
        }
    }

    /**
     *
     * @param userEntry
     * @return
     */
    int terminalEditor(String userEntry){
        String[] words = userEntry.split(" ");
        if ( words.length >= 2 ){
            switch(words[1]){
                case "export":
                {
                    ExportWindow ew = new ExportWindow(editor.editorArea.getValue());
                    terminalWindow.main_dialog.add(ew.main_dialog);
                    ew.main_dialog.open();
                    terminalWindow.main_dialog.close();
                    break;
                }
                case "import":
                {
                    UploadWindow up = new UploadWindow(editor);
                    terminalWindow.main_dialog.add(up.main_dialog);
                    up.main_dialog.open();
                    terminalWindow.main_dialog.close();
                    break;
                }
                case "clear":
                {
                    editor.update("");
                    editor.showNotification("Cleared!");
                    terminalWindow.main_dialog.close();
                }
                case "refresh":
                {
                    editor.refreshPreview();
                    break;
                }
                case "split":
                {
                    try{
                        int size = Integer.parseInt(words[2]);
                        editor.splitLayout.setSplitterPosition(size);
                        editor.showNotification("Split set to "+size);
                        terminalWindow.main_dialog.close();
                    }catch(Exception ex){
                        editor.showNotification(ex.toString());
                    }
                    break;
                }
                case "rotate":
                {
                    if ( words.length == 3 ){
                        if ( words[2].equals("vert")){
                            editor.splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
                            editor.showNotification("Editor orientation: vertical");
                            terminalWindow.main_dialog.close();
                        }
                        else if ( words[2].equals("hori")){
                            editor.splitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
                            editor.showNotification("Editor orientation: horizontal");
                            terminalWindow.main_dialog.close();
                        }
                    }
                    break;
                }
                case "focus":
                {
                    terminalWindow.main_dialog.close();
                    editor.editorArea.focus();
                    break;
                }
            }
            return 1;
        }
        else{
            // show help (?)
            editor.showNotification("Wrong terminal input");
            return 0;
        }
    }

    /**
     * Function for creating terminal
     * @param userEntry
     */
    public int componentTerminal(String userEntry){
        String[] words = userEntry.split(" ");
        if ( words.length == 2 ){
                switch(words[1]){
                    case "heading1":
                    {
                        editor.addComponent("# Heading level 1");
                        break;
                    }
                    case "heading2":
                    {
                        editor.addComponent("## Heading level 2");
                        break;
                    }
                    case "heading3":
                    {
                        editor.addComponent("### Heading level 3");
                        break;
                    }
                    case "heading4":
                    {
                        editor.addComponent("#### Heading level 4");
                        break;
                    }
                    case "chapter":
                    {
                        editor.addComponent("Heading level 1\n" + "===============");
                        break;
                    }
                    case "blank":
                    {
                        editor.addComponent("...");
                        break;
                    }
                    case "break":
                    {
                        editor.addComponent("<br>");
                        break;
                    }
                    case "italic":
                    {
                        editor.addComponent("*italic*");
                        break;
                    }
                    case "bold":
                    {
                        editor.addComponent("***bold***");
                        break;
                    }
                    case "quote":
                    {
                        editor.addComponent("> your wise quote");
                        break;
                    }
                    case "code":
                    {
                        editor.addComponent("```code\nyour interesting code\n```");
                        break;
                    }
                    default:
                    {
                        editor.showNotification("No component named "+words[1]);
                    }
                }
            }
            else{
                editor.showNotification("Wrong terminal input");
            }
            terminalWindow.main_dialog.close();
            editor.editorArea.focus();
            return 1;
    }
}
