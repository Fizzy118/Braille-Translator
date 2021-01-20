package App_gui;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*; 
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import javax.swing.JFrame; 
import java.awt.Dialog;
import javax.swing.JDialog;
import Main.brailleMain; //import main

/**
 * This is the code providing graphic interface for "brailleMain" aplication
 * @author Zuzanna Adamiuk, Piotr Kielak
 */
public class App_interface extends JFrame 
{
    String path;
    boolean save_file=false;
    boolean translate=false;
    JCheckBox checkBox;
    
    /**
     * App_interface is a code providing information about application's GUI
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public App_interface() {
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setPreferredSize(new Dimension(750,550));
        mainPanel.setLayout(layout);
        this.getContentPane().add(mainPanel);
        this.setLocationRelativeTo( null );
        brailleMain main = new brailleMain();

        JLabel label=new JLabel("Choose image with text written in Braille:");
        mainPanel.add(label);

        JFileChooser fc=new JFileChooser();
        mainPanel.add(fc);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and JPG images", "png", "jpg");
        fc.addChoosableFileFilter(filter);

        ActionListener actionChooseFile = new ActionListener() {       
            @Override
            public void actionPerformed(ActionEvent ae) {
                File file = fc.getSelectedFile();
                path = file.getAbsolutePath();
                JOptionPane.showMessageDialog(null, "The image was successfully chosen!");
            }
        };
        fc.addActionListener(actionChooseFile);

        JButton buttonTranslate= new JButton("Translate");
        mainPanel.add(buttonTranslate);
        buttonTranslate.setSize(15,15); 

        ActionListener actionTranslate = new ActionListener() {       
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (path!=null) {
                    translate=true;
                    JDialog mydialog = new JDialog();
                    mydialog.setLocationRelativeTo( null );
                    mydialog.setSize(new Dimension(700,600));
                    mydialog.setTitle("Translated text");
                    mydialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL); // prevent user from doing something else
                    
                    JTextArea area = new JTextArea("Translation summary: \n\n" + main.getText()); 
                    area.setMargin(new Insets(50,50,50,50));
                    area.setFont(area.getFont().deriveFont(20f));
                    area.setBounds(10,30, 200,200);  
                    mydialog.add(area);  
                    mydialog.setVisible(true);
                    // HighGui.imshow("detected circles", main.getImage()); //do usuniecia na koniec?
                    //HighGui.waitKey();
                    path=null;
                } else { 
                    JOptionPane.showMessageDialog(null,"You have not choose picture to translate yet!","Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        buttonTranslate.addActionListener(actionTranslate);

        JButton buttonInfo= new JButton(new ImageIcon("src\\App_gui\\info.png"));
        mainPanel.add(buttonInfo);

        ActionListener actionInfo = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Braille Translator is a console application that allows user to translate Braille alphabate to traditional English alphabet. \n" +
            "  User needs to prepare a sharp image of a text written in Braille and to provide information about a path to the file. \n" +
            "  Then using OpenCV 4.5.0 app edits the image and using special algorithm translates text to finally save it to a new file.",
            "information",JOptionPane.INFORMATION_MESSAGE);
            }};

        buttonInfo.addActionListener(actionInfo);

        checkBox = new JCheckBox("save as .txt");
        checkBox.setMnemonic(KeyEvent.VK_G); 
        checkBox.addItemListener(new CustomItemListener());
        mainPanel.add(checkBox);


        this.setTitle("Braille Translator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        layout.setAutoCreateGaps( true );
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                addComponent(label).addComponent(fc).addComponent(checkBox));
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                addComponent(buttonInfo).addComponent(buttonTranslate));
        layout.setHorizontalGroup(hGroup);


        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(label).addComponent(buttonInfo));
        vGroup.addGroup(layout.createParallelGroup().addComponent(fc));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
               addComponent(buttonTranslate).addComponent(checkBox));

        layout.setVerticalGroup(vGroup);
    }
    
    /**
     * Method returning path to the file chosen by user
     * @return path to the chosen file
     */
    public String getPath(){
        return path;
    }
    
    /**
     * Method returning information whether text should be saved to txt file or not
     * @return bool - to save a file or not
     */
    public boolean getSave() {
        return save_file;
    }
    
    /**
     * Method returning information whether text should be translated or not
     * @return bool - to translate text or not
     */
    public boolean getTranslation(){
        return translate;
    }

    class CustomItemListener implements ItemListener {
          public void itemStateChanged(ItemEvent e) {
             if(e.getSource()==checkBox)  
             save_file = true;
          }    
       }   
}