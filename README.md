![md_icon](https://github.com/wjakew/md/blob/master/readme_resource/md_icon.png)

## .md
Web application for editing .md files!

![homeview_screenshot](https://github.com/wjakew/md/blob/master/readme_resource/homeview_screenshot.png)

#### Technologies.
1. Java
2. Vaadin
3. Spring-Boot
#### Core functionality.
1. Creating and editing .md files.
2. Importing / Exporting data.
3. Fast editing and preview.
#### Editor terminal.
![terminal_screenshot](https://github.com/wjakew/md/blob/master/readme_resource/terminal_screenshot.png)

The editor has a terminal that allows you to perform operations on data.
A few basic commands allow you to edit content, export data or add content.
- editor export - export data to file
- editor import - import data to file
- editor clear - clear editor
- editor split 'value' - split editor to given %
- editor rotate 'vert/hori' - rotate split section
- editor focus - focus on editor component
- editor refresh - refresh preview ( also moving mouse away of the editor updates preview)
- component heading1 - add heading 1 to content
- component heading2 - add heading 2 to content
- component heading3 - add heading 3 to content
- component heading4 - add heading 4 to content
- component chapter - adding chapter to content
- component blank - adding blank space
- component break - adding break in text
- component italic - changing to italic
- component bold - changing to bold
- component quote - adding quote
- component code - adding code component
- exit - exiting from editor ( if editor is floating window )

#### Deployment.
Simply download the newest release, unzip in the desired location and then run the command:
```bash
java -jar md.jar
```
