/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App_gui;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*; 
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author piotr
 */
public class App_interface extends JFrame
{
    String path;
    public App_interface()
    {
 //setting layout and main panel initiation
    JPanel mainPanel = new JPanel();
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    GroupLayout layout = new GroupLayout(mainPanel);
    mainPanel.setPreferredSize(new Dimension(750,550));
    mainPanel.setLayout(layout);
    this.getContentPane().add(mainPanel);
    
    
     
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
    
    
    //buttons initiation
    JButton button1= new JButton("Translate");
    mainPanel.add(button1);
    button1.setSize(15,15); 
  
    JButton button2= new JButton(new ImageIcon("C:\\Users\\piotr\\Desktop\\Braille-Trans\\Braille-Project\\src\\App_gui\\info.png"));
    mainPanel.add(button2);
    
    ActionListener action1 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null,"Braille Translator is a console application that allows user to translate Braille alphabate to traditional English alphabet. \n" +
        "  User needs to prepare a sharp image of a text written in Braille and to provide information about a path to the file. \n" +
        "  Then using OpenCV 4.5.0 app edits the image and using special algorithm translates text to finally save it to a new file.","information",JOptionPane.INFORMATION_MESSAGE);
        }};

    button2.addActionListener(action1);
    //Check box initiation
    JCheckBox checkBox=new JCheckBox("save as .txt");  
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
            addComponent(checkBox).addComponent(button1));
   layout.setVerticalGroup(vGroup);
    }
   public String getpath() 
   {
   return path;
   }
}
