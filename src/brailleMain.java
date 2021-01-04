/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import java.io.IOException;

<<<<<<< HEAD
import Dictionary.Letters;
=======

>>>>>>> 66788d5fcb6f30043995bd85d84ec514c7b11ed1
import App_gui.App_interface;
import javax.swing.SwingUtilities;
import java.awt.event.*; 

//testowo
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import java.awt.Image;
import java.awt.image.BufferedImage;

<<<<<<< HEAD
=======


>>>>>>> 66788d5fcb6f30043995bd85d84ec514c7b11ed1
/**
 * Braille Translator is a console application that allows user to translate Braille alphabate to traditional English alphabet. 
 * User needs to prepare a sharp image of a text written in Braille and to provide information about a path to the file. 
 * Then using OpenCV 4.5.0 app edits the image and using special algorithm translates text to finally save it to a new file.
 * 
 * @author Zuzanna Adamiuk & Piotr Kielak
 */
public class brailleMain{

    /**
     * 
     * @param args the command line arguments
     */  

    public static void main(String[] args)throws IOException{ 
<<<<<<< HEAD
        //initiating interface
    
 
               App_interface app = new App_interface();
               app.setVisible(true);
 
        String sourcePath;
        do{
        sourcePath =app.getpath();
        System.out.println(".");  //czemu bez tego nie działa XD
        }
        while(sourcePath==null);
        
=======
//        //initiating interface
//       Runnable thread=new Runnable()
//       {
//           @Override
//           public void run()
//           {
//                App_interface app = new App_interface();
//                app.setVisible(true);
//           }
//       };
//        SwingUtilities.invokeLater(thread);

//        App_interface app = new App_interface();
//        String sourcePath =app.getpath();
        
        String sourcePath = "D:/obrazek.jpg";
>>>>>>> 66788d5fcb6f30043995bd85d84ec514c7b11ed1
        String destinationPath = "D:/obrazek-edited.jpg";
        
        // Welcome message
        System.out.println("Welcome to Braille Translator! \nPath to the chosen photo: " + sourcePath);
            
        // Loading the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        // Preparing matrix for changing an image
        Mat imgEdited = new Mat();
        Mat image = Imgcodecs.imread(sourcePath, 1);  

        double w,h;
        w = image.size().width;
        h = image.size().height;
        System.out.println("Image size: \nWidth: " + w + "px\nHeight: " + h +"px");
    
        //Function call
        image_edition(imgEdited, image);
<<<<<<< HEAD
=======
        
        
        
>>>>>>> 66788d5fcb6f30043995bd85d84ec514c7b11ed1
        //TESTOWANIE OKREGOW
        
        int circleCounter = 0;
        int radiusSum = 0;
        double meanDiameter, oneCharacterWithSpace,oneCharacterWithNewLine, numberOfColumns, numberOfRows, lineSpace, rowSpace, numberOfCharacters;
       
        Mat circles = new Mat();
        Imgproc.HoughCircles(imgEdited, circles, Imgproc.HOUGH_GRADIENT, 1.0,
                50, // change this value to detect circles with different distances to each other
                100.0, 30.0, 1 , 100); // change the last two parameters
                // (min_radius & max_radius) to detect larger circles
                circleCounter = circles.cols();
        
        for (int x = 0; x < circles.cols(); x++) {
            double[] c = circles.get(0, x);
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));
            // circle center
            Imgproc.circle(image, center, 1, new Scalar(0,100,100), 3, 8, 0 );
            // circle outline
            int radius = (int) Math.round(c[2]);
            Imgproc.circle(image, center, radius, new Scalar(255,0,255), 3, 8, 0 );
            radiusSum += 2*radius;
        }
        

        HighGui.imshow("detected circles", image);
        HighGui.waitKey();
        
        meanDiameter = radiusSum/circleCounter;
        oneCharacterWithSpace = meanDiameter * 6 / 1.2;
        oneCharacterWithNewLine = meanDiameter * 10 / 1.2;
        
        lineSpace = meanDiameter * 2.3 / 1.2;
        rowSpace = meanDiameter * 2.5 / 1.2;
        
        numberOfColumns = Math.round(w/oneCharacterWithSpace);
        numberOfRows = Math.round(h/oneCharacterWithNewLine);
        numberOfCharacters = numberOfColumns * numberOfRows;

        System.out.println("Liczba okręgów wynosi: " + circleCounter);     
        System.out.println("Średni rozmiar średnicy to: " + meanDiameter + "px");
        System.out.println("Liczba znaków w wierszu wynosi: " + numberOfColumns);   
        System.out.println("Liczba wierszy: " + numberOfRows);  
        System.out.println("Liczba liter na obrazie: " + numberOfCharacters);
        

        // KONIEC TESTOWANIA
        
<<<<<<< HEAD
        //Saving edited image
        Imgcodecs.imwrite(destinationPath,imgEdited );
        // Successful operation message
        System.out.println("The photo was succesfully edited and saved to a new file! \nPath to the edited photo: " + destinationPath);
           
       
=======
        

        //Saving edited image
        Imgcodecs.imwrite(destinationPath,imgEdited );
        
        // Successful operation message
        System.out.println("The photo was succesfully edited and saved to a new file! \nPath to the edited photo: " + destinationPath);
        System.exit(0);

>>>>>>> 66788d5fcb6f30043995bd85d84ec514c7b11ed1
    }
    
        // METHODS
        //image edition method
        static void image_edition(Mat img_Gray, Mat image) {
            System.out.println("The edition has started! I'm currently editing the image...");
            //Grayscale
            Imgproc.cvtColor(image, img_Gray, Imgproc.COLOR_BGR2GRAY);

            //Gaussian Filter
            Imgproc.GaussianBlur(img_Gray, img_Gray, new Size(3,3), 0);
            Imgproc.adaptiveThreshold(img_Gray, img_Gray, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 5);
            // Median Filter
            Imgproc.medianBlur(img_Gray, img_Gray, 3);
            Imgproc.threshold(img_Gray, img_Gray, 0, 255, Imgproc.THRESH_OTSU); 
            //Gaussian Filter
            Imgproc.GaussianBlur(img_Gray, img_Gray, new Size(3, 3), 0);
            Imgproc.threshold(img_Gray, img_Gray, 0, 255, Imgproc.THRESH_OTSU);
            //imagie edition message
            System.out.println("The editing is complete!");
<<<<<<< HEAD
        }  
=======
        }   
       

>>>>>>> 66788d5fcb6f30043995bd85d84ec514c7b11ed1
}
