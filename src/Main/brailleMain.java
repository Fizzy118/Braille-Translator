package Main;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import java.io.IOException;
import Dictionary.Letters;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File; 
import java.io.IOException;
import App_gui.App_interface;
import javax.swing.SwingUtilities;
import java.awt.event.*; 
import javax.swing.text.Document;
//testowo
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import java.awt.Image;
import java.awt.image.BufferedImage;


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
    static String finaltext;
    static Mat image;
    public static void main(String[] args)throws IOException{ 
        //initiating interface
        App_interface app = new App_interface();
        app.setVisible(true);
 
        //Waiting loop
        String sourcePath;
        do{     //mozna uzyc nextline() 
        sourcePath =app.getpath();
        System.out.print("");  //czemu bez tego nie działa?
        }
        while(sourcePath==null);
        
        String destinationPath = "D:/obrazek-edited.jpg";
        
        // Welcome message
        System.out.println("Welcome to Braille Translator! \nPath to the chosen photo: " + sourcePath);
   
        // Loading the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ///////////////////////////
        
        // Preparing matrix for changing an image
        Mat imgEdited = new Mat();
        image = Imgcodecs.imread(sourcePath, 1);  

        double w,h;
        w = image.size().width;
        h = image.size().height;
        System.out.println("Image size: \nWidth: " + w + "px\nHeight: " + h +"px");
        
        
        image_edition(imgEdited, image);
        //Function call
        finaltext = letter_translation_by_minDist(w, h, imgEdited, image);
        do{
        System.out.println("");
        }
        while(app.gettrans()==false);           
  //     HighGui.imshow("detected circles", image); //do usuniecia na koniec?
      // HighGui.waitKey();


        if(app.getbool()==true)
        {
            FileWriter file = new FileWriter("Translated.txt");
            BufferedWriter translate = new BufferedWriter(file);
            translate.append(finaltext);
            translate.append(" ");
            translate.close();
            System.out.println("tlumacze");   
        }
       
        //Saving edited image
    //    Imgcodecs.imwrite(destinationPath,imgEdited );//do usuniecia na koniec?
        
        // Successful operation message
        System.out.println("The photo was succesfully edited and saved to a new file! \nPath to the edited photo: " + destinationPath);
        //System.exit(0);
    
   
    }
       public String gettext() 
    {
     return finaltext;
    } 
       public Mat getimage() 
    {
     return image;
    } 
        // METHODS
    
        //image edition method
        static void image_edition(Mat img_Gray, Mat image) {
            System.out.println("The edition has started! I'm currently editing the image...");
            //Grayscale
            Imgproc.cvtColor(image, img_Gray, Imgproc.COLOR_BGR2GRAY);

            //Gaussian Filter
            /*Imgproc.GaussianBlur(img_Gray, img_Gray, new Size(3,3), 0);
            Imgproc.adaptiveThreshold(img_Gray, img_Gray, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 5);
            // Median Filter
            Imgproc.medianBlur(img_Gray, img_Gray, 3);
            Imgproc.threshold(img_Gray, img_Gray, 0, 255, Imgproc.THRESH_OTSU); 
            //Gaussian Filter
            Imgproc.GaussianBlur(img_Gray, img_Gray, new Size(3, 3), 0);
            Imgproc.threshold(img_Gray, img_Gray, 0, 255, Imgproc.THRESH_OTSU);
            //imagie edition message
            System.out.println("The editing is complete!");*/

        }
        
   
//        static void letter_translation(double w,double h,Mat imgEdited,Mat image,FileWriter file) throws IOException 
//        {
//            
//            BufferedWriter translate = new BufferedWriter(file);
//
//            int circleCounter = 0;
//            int radiusSum = 0;
//            double meanDiameter, oneCharacterWithSpace,oneCharacterWithNewLine, numberOfColumns, numberOfRows, lineSpace, rowSpace, numberOfCharacters, dotSize, dotSpace,minDist;
//
//            Mat circles = new Mat();
//            Imgproc.HoughCircles(imgEdited, circles, Imgproc.HOUGH_GRADIENT, 1.0,
//                    10, // change this value to detect circles with different distances to each other
//                    100.0, 30.0, 1 , 100); // change the last two parameters (min_radius & max_radius) to detect larger circles
//                    circleCounter = circles.cols();
//
//            Point [] pointArray = new Point[circleCounter];
//
//            for (int x = 0; x < circles.cols(); x++) {
//                double[] c = circles.get(0, x);
//                Point center = new Point(Math.round(c[0]), Math.round(c[1]));// circle center
//                pointArray[x] = center;
//                Imgproc.circle(image, center, 1, new Scalar(0,100,100), 3, 8, 0 );// circle outline
//                int radius = (int) Math.round(c[2]);
//                Imgproc.circle(image, center, radius, new Scalar(255,0,255), 3, 8, 0 );
//                radiusSum += 2*radius;
//            }
//
//            meanDiameter = radiusSum/circleCounter;
//            
//            double x_dist, y_dist;
//            minDist = 150;
//            for (int i = 0; i < circleCounter; i++){
//                for (int j = 1; j < circleCounter-1; j++){
//                    if (i == j) {break;}
//                    else {
//                        x_dist = pointArray[i].x-pointArray[j].x;
//                        y_dist = pointArray[i].y-pointArray[j].y;
//                        if( Math.sqrt(x_dist*x_dist + y_dist*y_dist) < minDist){
//                            minDist = Math.sqrt(x_dist*x_dist + y_dist*y_dist);
//                        }
//                    }      
//                }
//            }
//            
//            oneCharacterWithSpace = meanDiameter * 6 / 1.2;
//            oneCharacterWithNewLine = meanDiameter * 10 / 1.2;
//            lineSpace = meanDiameter * 2.3 / 1.2;
//            rowSpace = meanDiameter * 2.5 / 1.2;
//            dotSize = meanDiameter * 1.2 / 1.2;
//            dotSpace = meanDiameter * 1.3 / 1.2;
//
//            numberOfColumns = Math.round(w/oneCharacterWithSpace);
//            numberOfRows = Math.round(h/oneCharacterWithNewLine);
//            numberOfCharacters = numberOfColumns * numberOfRows;
//
//            System.out.println("Liczba okręgów wynosi: " + circleCounter);     
//            System.out.println("Średni rozmiar średnicy to: " + meanDiameter + "px");
//            System.out.println("Liczba znaków w wierszu wynosi: " + numberOfColumns);   
//            System.out.println("Liczba wierszy: " + numberOfRows);  
//            System.out.println("Liczba liter na obrazie: " + numberOfCharacters);
//
//             int numOfCharacters = (int)numberOfCharacters;
//            String[] translatedText = new String[numOfCharacters];
//
//
//
//
//            String[] currCharacter = new String[6];
//            for (int i = 0; i < 6; i++){
//                currCharacter[i] = "0";
//            }
//            int currCharNumber = 0;
//            //do poprawienia
//            for (int currRow = 1; currRow < numberOfRows+1; currRow++) {
//                System.out.println("numer wiersza:" +currRow);
//                for (int currCol = 1; currCol < numberOfColumns+1; currCol++){
//                    for (int i = 0; i < 6; i++){
//                            currCharacter[i] = "0";
//                    }
//                    System.out.println("numer kolumny:" +currCol);
//    //                double abc;
//    //                abc = (currCol-1)*oneCharacterWithSpace + dotSize + dotSpace;
//    //                System.out.println("\nwymiar:" +abc);                
//                        for (int j = 0; j < circleCounter; j++){    
//
//                            if (pointArray[j].x < (currCol-1)*oneCharacterWithSpace + dotSize + dotSpace &&
//                                pointArray[j].x > (currCol-1)*oneCharacterWithSpace &&    
//                                pointArray[j].y < (currRow-1)*oneCharacterWithNewLine + dotSize + dotSpace && 
//                                pointArray[j].y > (currRow-1)*oneCharacterWithNewLine){
//                                currCharacter[0] = "1";                            
//                            }
//                            else if (pointArray[j].x < currCol*oneCharacterWithSpace && 
//                                     pointArray[j].x > ((currCol-1)*oneCharacterWithSpace) + dotSize + dotSpace && 
//                                     pointArray[j].y < (currRow-1)*oneCharacterWithNewLine + dotSize + dotSpace && 
//                                     pointArray[j].y > (currRow-1)*oneCharacterWithNewLine){
//                                currCharacter[1] = "1";                            
//                            }
//
//                            else if (pointArray[j].x < (currCol-1)*oneCharacterWithSpace + dotSize + dotSpace &&
//                                     pointArray[j].x > ((currCol-1)*oneCharacterWithSpace) && 
//                                     pointArray[j].y < (currRow-1)*oneCharacterWithNewLine + 2* (dotSize + dotSpace) &&
//                                     pointArray[j].y > (currRow-1)*oneCharacterWithNewLine + dotSize + dotSpace){
//                                currCharacter[2] = "1";
//
//                            }
//                            else if (pointArray[j].x < currCol*oneCharacterWithSpace && 
//                                     pointArray[j].x > ((currCol-1)*oneCharacterWithSpace) + dotSize + dotSpace &&
//                                     pointArray[j].y < (currRow-1)*oneCharacterWithNewLine + 2* (dotSize + dotSpace) &&
//                                     pointArray[j].y > (currRow-1)*oneCharacterWithNewLine + dotSize + dotSpace){
//                                currCharacter[3] = "1";
//
//                            }
//
//                            else if (pointArray[j].x < (currCol-1)*oneCharacterWithSpace + dotSize + dotSpace &&
//                                     pointArray[j].x > ((currCol-1)*oneCharacterWithSpace) &&
//                                     pointArray[j].y < (currRow*oneCharacterWithNewLine - rowSpace)&&
//                                     pointArray[j].y > (currRow-1)*oneCharacterWithNewLine + 2* (dotSize + dotSpace)){
//                                currCharacter[4] = "1";
//
//                            }
//                            else if (pointArray[j].x < currCol*oneCharacterWithSpace && 
//                                     pointArray[j].x > ((currCol-1)*oneCharacterWithSpace) + dotSize + dotSpace &&
//                                     pointArray[j].y < (currRow*oneCharacterWithNewLine - rowSpace) &&
//                                     pointArray[j].y > (currRow-1)*oneCharacterWithNewLine + 2* (dotSize + dotSpace)){
//                                currCharacter[5] = "1";
//
//                            }
//                        }                   
//
//                        String currCode = String.join("", currCharacter);
//                        currCharNumber++;
//                        System.out.println(currCode);
//
//                        
//                        //PORÓWNYWANIE ZNAKÓW I ZAPISYWANIE DO TXT, do zmiany currcharakter
//                        for (int j = 0; j < Letters.numofletters; j++){
//                            if (currCode.equals(Letters.idletters[j])){
//                                translate.append(Letters.trueletters[j]);
//                                                        System.out.println("tlumacze");
//                            }
//                        }
//
//                }
//                translate.append(" ");
//            }
//            translate.close();            
//        }

////////////////////////////////////////// testowanie
    
static  String letter_translation_by_minDist(double w,double h,Mat imgEdited,Mat image) throws IOException 
        {
            
            //BufferedWriter translate = new BufferedWriter(file);
            
            int circleCounter = 0;
            int radiusSum = 0;
            double meanDiameter, oneCharacterWithSpace,oneCharacterWithNewLine, numberOfColumns, numberOfRows, lineSpace, rowSpace, numberOfCharacters, dotSize, dotSpace,minDist;

            Mat circles = new Mat();
            Imgproc.HoughCircles(imgEdited, circles, Imgproc.HOUGH_GRADIENT, 1.0,
                    10, // change this value to detect circles with different distances to each other
                    100.0, 30.0, 1 , 50); // change the last two parameters (min_radius & max_radius) to detect larger circles
                    circleCounter = circles.cols();

            Point [] pointArray = new Point[circleCounter];

            for (int x = 0; x < circles.cols(); x++) {
                double[] c = circles.get(0, x);
                Point center = new Point(Math.round(c[0]), Math.round(c[1]));// circle center
                pointArray[x] = center;
                Imgproc.circle(image, center, 1, new Scalar(0,100,100), 3, 8, 0 );// circle outline
                int radius = (int) Math.round(c[2]);
                Imgproc.circle(image, center, radius, new Scalar(255,0,255), 3, 8, 0 );
                radiusSum += 2*radius;
            }

            meanDiameter = radiusSum/circleCounter;
            
            double x_dist, y_dist;
            minDist = 150;
            for (int i = 0; i < circleCounter; i++){
                for (int j = 1; j < circleCounter-1; j++){
                    if (i == j) {break;}
                    else {
                        x_dist = pointArray[i].x-pointArray[j].x;
                        y_dist = pointArray[i].y-pointArray[j].y;
                        if( Math.sqrt(x_dist*x_dist + y_dist*y_dist) < minDist){
                            minDist = Math.sqrt(x_dist*x_dist + y_dist*y_dist);
                        }
                    }      
                }
            }
            
            oneCharacterWithSpace = minDist * 6 / 2.5;
            oneCharacterWithNewLine = minDist * 10 / 2.5;
            lineSpace = minDist * 2.3 / 2.5;
            rowSpace = minDist * 2.5 / 2.5;
            dotSize = minDist * 1.2 / 2.5;
            dotSpace = minDist * 1.3 / 2.5;

            numberOfColumns = Math.round(w/oneCharacterWithSpace);
            numberOfRows = Math.round(h/oneCharacterWithNewLine);
            numberOfCharacters = numberOfColumns * numberOfRows;

            System.out.println("Liczba okręgów wynosi: " + circleCounter);     
            System.out.println("Średni rozmiar średnicy to: " + meanDiameter + "px");
            System.out.println("NAjmniejsza odległość między środkami punktów to: " + minDist + "px");
            System.out.println("Liczba znaków w wierszu wynosi: " + numberOfColumns);   
            System.out.println("Liczba wierszy: " + numberOfRows);  
            System.out.println("Liczba liter na obrazie: " + numberOfCharacters);

            int numOfCharacters = (int)numberOfCharacters;
            
            String translated_text="";
            String[] currCharacter = new String[6];
            for (int i = 0; i < 6; i++){
                currCharacter[i] = "0";
            }

            for (int currRow = 1; currRow < numberOfRows+1; currRow++) {
                
                System.out.println("numer wiersza:" +currRow);
                
                for (int currCol = 1; currCol < numberOfColumns+1; currCol++){
                    
                    for (int i = 0; i < 6; i++){
                            currCharacter[i] = "0";
                    }
                    
                    System.out.println("numer kolumny:" +currCol);
    //                double abc;
    //                abc = (currCol-1)*oneCharacterWithSpace + dotSize + dotSpace;
    //                System.out.println("\nwymiar:" +abc);                
                        for (int j = 0; j < circleCounter; j++){    

                            if (pointArray[j].x < (currCol-1)*oneCharacterWithSpace + minDist &&
                                pointArray[j].x > (currCol-1)*oneCharacterWithSpace &&    
                                pointArray[j].y < (currRow-1)*oneCharacterWithNewLine + minDist && 
                                pointArray[j].y > (currRow-1)*oneCharacterWithNewLine){
                                currCharacter[0] = "1";                            
                            }
                            else if (pointArray[j].x < currCol*oneCharacterWithSpace && 
                                     pointArray[j].x > ((currCol-1)*oneCharacterWithSpace) + minDist && 
                                     pointArray[j].y < (currRow-1)*oneCharacterWithNewLine + minDist && 
                                     pointArray[j].y > (currRow-1)*oneCharacterWithNewLine){
                                currCharacter[1] = "1";                            
                            }

                            else if (pointArray[j].x < (currCol-1)*oneCharacterWithSpace + minDist &&
                                     pointArray[j].x > ((currCol-1)*oneCharacterWithSpace) && 
                                     pointArray[j].y < (currRow-1)*oneCharacterWithNewLine + 2*minDist &&
                                     pointArray[j].y > (currRow-1)*oneCharacterWithNewLine + minDist){
                                currCharacter[2] = "1";

                            }
                            else if (pointArray[j].x < currCol*oneCharacterWithSpace && 
                                     pointArray[j].x > ((currCol-1)*oneCharacterWithSpace) + minDist &&
                                     pointArray[j].y < (currRow-1)*oneCharacterWithNewLine + 2* (minDist) &&
                                     pointArray[j].y > (currRow-1)*oneCharacterWithNewLine + minDist){
                                currCharacter[3] = "1";

                            }

                            else if (pointArray[j].x < (currCol-1)*oneCharacterWithSpace + minDist &&
                                     pointArray[j].x > ((currCol-1)*oneCharacterWithSpace) &&
                                     pointArray[j].y < (currRow*oneCharacterWithNewLine - rowSpace)&&
                                     pointArray[j].y > (currRow-1)*oneCharacterWithNewLine + 2* (minDist)){
                                currCharacter[4] = "1";

                            }
                            else if (pointArray[j].x < currCol*oneCharacterWithSpace && 
                                     pointArray[j].x > ((currCol-1)*oneCharacterWithSpace) + minDist &&
                                     pointArray[j].y < (currRow*oneCharacterWithNewLine - rowSpace) &&
                                     pointArray[j].y > (currRow-1)*oneCharacterWithNewLine + 2* (minDist)){
                                currCharacter[5] = "1";

                                
                            }
                        }                   

                        String currCode = String.join("", currCharacter);
                        
                        System.out.println(currCode);

                        //PORÓWNYWANIE ZNAKÓW I ZAPISYWANIE DO TXT, do zmiany currcharakter
//                        if(app.getbool()){
//                        for (int j = 0; j < Letters.numofletters; j++){
//                            if (currCode.equals(Letters.idletters[j])){
//                                translate.append(Letters.trueletters[j]);
//                                System.out.println("tlumacze");
//                                break;
//                            }
//                        }
//                        }
                        for (int p = 0; p < Letters.numofletters; p++){
                            if (currCode.equals(Letters.idletters[p])){
                                translated_text =translated_text + Letters.trueletters[p];
                                
                                break;
                            }
                        }
                        
                }
                translated_text =translated_text + " ";
                //translate.append(" ");
            }
           // translate.close();  
            return translated_text;
        }
}
