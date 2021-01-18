package App_gui;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*; 
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JTextPane;  

import java.awt.Dialog;
import javax.swing.JDialog;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.BadLocationException;  
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import Main.brailleMain; //import main
import java.awt.BorderLayout;  
import java.awt.Color;  
import java.awt.Container;  
import javax.swing.JFrame; 
import java.awt.*;    
import java.awt.event.*; 
import org.opencv.highgui.HighGui;
import javax.swing.JScrollPane;  
import javax.swing.JTextPane;  
import javax.swing.text.BadLocationException;  
import javax.swing.text.Document;  
import javax.swing.text.SimpleAttributeSet;  
import javax.swing.text.StyleConstants;
/**
 * This is the code that provides graphic interface for "brailleMain" aplication
 * @author Zuzanna Adamiuk & Piotr Kielak
 */
public class App_interface extends JFrame 
{
    String path;
    boolean save_file=false;
    boolean translate=false;
    JCheckBox checkBox;
    @SuppressWarnings("LeakingThisInConstructor")
    public App_interface() 
    {
 //setting layout and main panel initiation
    JPanel mainPanel = new JPanel();
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    GroupLayout layout = new GroupLayout(mainPanel);
    mainPanel.setPreferredSize(new Dimension(750,550));
    mainPanel.setLayout(layout);
    this.getContentPane().add(mainPanel);
    
    brailleMain main = new brailleMain();
     
    //label initiation
    JLabel label=new JLabel("Choose image with text written in Braille:");
    mainPanel.add(label);

    //file chooser initiation
    JFileChooser fc=new JFileChooser();
    mainPanel.add(fc);
    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fc.setAcceptAllFileFilterUsed(false);
    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and JPG images", "png", "jpg");
    fc.addChoosableFileFilter(filter);
    
   ActionListener action2 = new ActionListener() {       
       

        @Override
        public void actionPerformed(ActionEvent ae) {
            File file = fc.getSelectedFile();
            path = file.getAbsolutePath();
            JOptionPane.showMessageDialog(null, "Wybrano obraz!");
        }
   };
    fc.addActionListener(action2);
    
    //new dialog
    
    //button "translate" initiation
    JButton button1= new JButton("Translate");
    mainPanel.add(button1);
    button1.setSize(15,15); 
    
  
    ActionListener action3 = new ActionListener() {       
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (path!=null)
            {
                translate=true;
                JDialog mydialog = new JDialog();
                mydialog.setSize(new Dimension(700,600));
                mydialog.setTitle("Translated text");
                mydialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL); // prevent user from doing something else
               
                JTextArea area=new JTextArea("Text from Your picture means: " + main.gettext());  
                area.setBounds(10,30, 200,200);  
                mydialog.add(area);  
                mydialog.setVisible(true);
               // HighGui.imshow("detected circles", main.getimage()); //do usuniecia na koniec?
                //HighGui.waitKey();
                path=null;
            }
            else
            { 
                JOptionPane.showMessageDialog(null,"You have not choose picture to translate yet!","Warning",JOptionPane.WARNING_MESSAGE);
            }
            }
        };
    button1.addActionListener(action3);
  
    //button "info" initiation
    JButton button2= new JButton(new ImageIcon("src\\App_gui\\info.png"));
    mainPanel.add(button2);
    
    ActionListener action1 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //ImageIcon icon = new ImageIcon("test images\\braille-proportions.png");
            JOptionPane.showMessageDialog(null, "Braille Translator is a console application that allows user to translate Braille alphabate to traditional English alphabet. \n" +
        "  User needs to prepare a sharp image of a text written in Braille and to provide information about a path to the file. \n" +
        "  Then using OpenCV 4.5.0 app edits the image and using special algorithm translates text to finally save it to a new file.","information",JOptionPane.INFORMATION_MESSAGE);
        }};

    button2.addActionListener(action1);
    
    //checkbox initiation 
    checkBox = new JCheckBox("save as .txt");
    checkBox.setMnemonic(KeyEvent.VK_G); 
    checkBox.addItemListener(new CustomItemListener());
    mainPanel.add(checkBox);
    
    
    
    //setting title and size
    this.setTitle("Braille Translator");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack();
    
    layout.setAutoCreateGaps( true );
    layout.setAutoCreateContainerGaps(true);
    GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

   hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
            addComponent(label).addComponent(fc).addComponent(checkBox));
   hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
            addComponent(button2).addComponent(button1));
   layout.setHorizontalGroup(hGroup);


   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
   vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
            addComponent(label).addComponent(button2));
   vGroup.addGroup(layout.createParallelGroup().addComponent(fc));
   vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
           addComponent(button1).addComponent(checkBox));

   layout.setVerticalGroup(vGroup);
    }
   public String getpath() 
   {
   return path;
   }
   public boolean getbool() 
   {
   return save_file;
   }
   public boolean gettrans() 
   {
   return translate;
   }
   //checkbox item listener  
class CustomItemListener implements ItemListener {
      public void itemStateChanged(ItemEvent e) {
         if(e.getSource()==checkBox)  
         save_file = true;
      }    
   }   
}
