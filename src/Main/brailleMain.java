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
 * @author Zuzanna Adamiuk, Piotr Kielak
 */
public class brailleMain{

    static String finaltext;
    
    static Mat image;
    
    public static void main(String[] args)throws IOException{ 

        App_interface app = new App_interface();
        app.setVisible(true);

        boolean done = false;
        
        do {
            String sourcePath;
            do {    
                sourcePath = app.getPath();
                System.out.print("");
            } while(sourcePath==null);

            String destinationPath = "./image-edited.jpg";

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            Mat imgEdited = new Mat();
            image = Imgcodecs.imread(sourcePath, 1);  

            double w,h;
            w = image.size().width;
            h = image.size().height;

            image_edition(imgEdited, image);
            
            finaltext = letter_translation_by_minDist(w, h, imgEdited, image);
            
            do {
                System.out.print("");
            } while(app.getTranslation()==false); 
            
            // Display window with detected circles
            //HighGui.imshow("detected circles", image); 
            //HighGui.waitKey();
            
            if(app.getSave()==true){
                FileWriter file = new FileWriter("Translated.txt");
                BufferedWriter translate = new BufferedWriter(file);
                translate.append(finaltext);
                translate.append(" ");
                translate.close(); 
            }
            
        } while(!done);
    }
    
    /**
     * Method returning translated text
     * @return translated text
     */
    public String getText(){
        return finaltext;
    } 
    
    /**
     * Method returning original image matrix
     * @return original image matrix
     */
    public Mat getImage(){
        return image;
    } 

    /** 
    * Method preparing an image for translation - editing matrix with imported image
    * @param imgEdited edited image Matrix 
    * @param image uploaded image - Matrix to be edited
    */
    public static void image_edition(Mat imgEdited, Mat image) {
        //Grayscale
        Imgproc.cvtColor(image,imgEdited, Imgproc.COLOR_BGR2GRAY);
        //Gaussian Filter
        /*Imgproc.GaussianBlur(imgEdited, imgEdited, new Size(3,3), 0);
        Imgproc.adaptiveThreshold(imgEdited, imgEdited, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 5);
        // Median Filter
        Imgproc.medianBlur(imgEdited, imgEdited, 3);
        Imgproc.threshold(imgEdited, imgEdited, 0, 255, Imgproc.THRESH_OTSU); 
        //Gaussian Filter
        Imgproc.GaussianBlur(imgEdited, imgEdited, new Size(3, 3), 0);
        Imgproc.threshold(imgEdited, imgEdited, 0, 255, Imgproc.THRESH_OTSU);
        //imagie edition message
        System.out.println("The editing is complete!");*/

    }


     /** Method translating text written in Braille Alphabet using proportions based on 
       minimal distance between two closest detected circles.
     * @param w edited image width
     * @param h edited image height
     * @param imgEdited edited image matrix
     * @param image source image matrix
     * @return translated text
     * @throws IOException 
     */
    public static  String letter_translation_by_minDist(double w, double h, Mat imgEdited, Mat image) throws IOException{

        int circleCounter = 0;
        int radiusSum = 0;
        double meanDiameter, oneCharacterWithSpace,oneCharacterWithNewLine, numberOfColumns, numberOfRows, lineSpace, rowSpace, numberOfCharacters, dotSize, dotSpace,minDist;
        String translated_text="";
        
        // We are using HoughCircles method from OpenCV lib, which allows 
        // us to detect circles in edited image matrix
         Mat circles = new Mat();
        Imgproc.HoughCircles(imgEdited, circles, Imgproc.HOUGH_GRADIENT, 1.0,
            10, // value allowing to detect circles close to each other 
                // (the lower it is, the closer circles can be detected)
            100.0, 30.0, 
            1 , // minimal radius to detect
            100); // maximal radius to detect

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

        // Algorithm finding minimal distance between dwo circles' centers
        double x_dist, y_dist;
        minDist = 150; //random value allowing to set the real one
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
        
        translated_text = "Number of circles: " + String.valueOf(circleCounter)+ 
                          "\nMean diameter size: " + String.valueOf(meanDiameter) + "px" +
                          "\nDistance between two closest circles' centres: " + String.valueOf(minDist) + "px" +
                          "\nNumber of characters in a row: " + String.valueOf(numberOfColumns) +
                          "\nNumber of rows: " + String.valueOf(numberOfRows) + 
                          "\nMaximal number of chracters in the picture: " + String.valueOf(numberOfCharacters) +
                          "\n\nText from your image means: \n";

        String[] currCharacter = new String[6];
        for (int i = 0; i < 6; i++){
            currCharacter[i] = "0";
        }

        String currCode = "000000";

        for (int currRow = 1; currRow < numberOfRows+1; currRow++) {
            for (int currCol = 1; currCol < numberOfColumns+1; currCol++){

                for (int i = 0; i < 6; i++){
                        currCharacter[i] = "0";
                }

                for (int j = 0; j < circleCounter; j++){ 

                    // Algorithm translating a single character 
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

                currCode = String.join("", currCharacter);

                // Displaying current character code - just to check whether it's translated correctly
                //System.out.println(currCode);

                for (int p = 0; p < Letters.numofletters; p++){
                    if (currCode.equals(Letters.idletters[p])){
                        translated_text =translated_text + Letters.trueletters[p];
                        break;
                    }
                }
            }
            if (!currCode.equals("000000"))
                translated_text =translated_text + " ";
        }
        return translated_text;
    }
}

