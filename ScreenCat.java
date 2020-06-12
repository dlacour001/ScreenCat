/*
    Dalton LaCour 
    ScreenCat revived
    3/16/2017
*/
package screencat;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebParam.Mode;
import javax.swing.UnsupportedLookAndFeelException;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
public class ScreenCat extends Application
{
    private int runs;	
    private Font myFont;
    private Font error;
    private final double yLoc;
    private double xLoc;
    private boolean dir;
    private Random randgen;	
    private Color col;	
    private long sleep;
    private boolean start;
    private Timeline timeline;
    private AnimationTimer timer;
    private  ArrayList <String> textL;
    private Text t;
    public Mode mode;	
    private final int[] lines={68,67,67,66,66,63,63,65,66,68,65,67,67,69,69,70,71,69,70,69,63,66,73,72,73,75,76,75,76,75,75};
    public ScreenCat() throws FileNotFoundException 
    {
        runs = 0;
        xLoc =800.0;
        yLoc= 200.0;
        dir = false;
        myFont = new Font("Consolas", 10);
        error = new Font("Consolas", 20);	
        randgen = new Random();	
        sleep = 66;
        start=true;	
        col = Color.DARKGRAY;
        textL= new ArrayList<>();			
    }
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException 
    {
	launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception 
    {
        printSCat();
        Group root=new Group();
        Scene scene;		
        t = new Text(xLoc, yLoc, textL.get(runs));
        t.setFont(myFont);
        t.setFill(col);
        Text wm= new Text(200, 50, "We are experiencing technical difficulties, Please wait...");
        wm.setFill(Color.RED);
        wm.setFont(error);
        Text wm2 = new Text(200, 70,"In the mean time enjoy this dancing cat!" );
        wm2.setFill(Color.RED);
        wm2.setFont(error);
        root.getChildren().addAll(t, wm, wm2);
	timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timer = new AnimationTimer() 
        {
            @Override
            public void handle(long l) 
               {
                    try 
                    {
                        Thread.sleep(sleep);
                    }
                    catch (InterruptedException e)
                    {}
                    if(!dir && start)
                    {
                    	runs++;
            		if(runs >=30)
            		{
                            dir=true;
                            changeColor();
                            t.setFill(col);
                        }
                    }
            	else if(dir && start)
            	{
                    runs--;
                    if (runs <=0)
                    {
                    	dir=false;
                    	start = false;
                    	changeColor();
                        t.setFill(col);
                    }
            	}
            	else if(!dir && !start)
            	{            		
                    runs++;
                    xLoc-=30;
                    if(runs >=30)
                    {
                    	dir=true;
                    	changeColor();
                        t.setFill(col);
                    }
            	}
            	else if(dir && !start)
            	{
                    runs--;
                    xLoc+=30;
                    if (runs <=0)
                    {
                    	dir=false;
                    	changeColor();
                        t.setFill(col);
                    }
            	}
            	t.setText(textL.get(runs));
            	t.setX(xLoc);
            } 
        };
        KeyValue keyValueX = new KeyValue(t.xProperty(),1);
        KeyValue keyValueY = new KeyValue(t.yProperty(),1);
        Duration duration = Duration.seconds(64);
        KeyFrame keyFrame = new KeyFrame(duration, keyValueX, keyValueY); 
        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame); 
        timeline.play();
        timer.start();
        stage.setFullScreen(true);
        scene =new Scene(root,Color.BLACK);
        scene.setCursor(Cursor.NONE);
     // Close the screen saver when any key is being pressed.  
        EventHandler<KeyEvent> key = (KeyEvent event) -> 
        {
            event.consume();
            stage.close();
        };// Close the screen saver when the mouse is moved.  
	EventHandler<MouseEvent> mou =new EventHandler<MouseEvent>() 
        {			   
            private long firstMouseMove = -1;  
            @Override  
	    public void handle(MouseEvent event)
            {  
                if (firstMouseMove != -1 && firstMouseMove < System.currentTimeMillis())
                {  
		    event.consume();  
		    stage.close();  
		} 
                else 
                {  
		    firstMouseMove = System.currentTimeMillis();  
		}  
            }   
	};
            scene.setOnKeyTyped(key);
            scene.setOnMouseMoved(mou);
	    stage.setScene(scene);
	    stage.show();		
	}	
	public void printSCat()
	{
            String str;
            int x=0;
            BufferedReader c=null;
            try 
            {
		c=new BufferedReader(new FileReader("src\\screencat\\screenCat.txt"));
			
		while(x<=30)
		{       
                    str= "";
                    for(int i=0; i<lines[x]-1; i++)
                    {
                        str+= c.readLine();
                        str= str+ "\n";      		
                    }
                    str+= c.readLine();
                    textL.add(str);
                    x++;
                }            
            } 
            catch (FileNotFoundException ex) 
            {
                Logger.getLogger(ScreenCat.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ScreenCat.class.getName()).log(Level.SEVERE, null, ex);
            } 
            finally 
            {
                try 
                {
                    c.close();
                }   
                catch (IOException ex) 
                {
                    Logger.getLogger(ScreenCat.class.getName()).log(Level.SEVERE, null, ex);
                }            
            }	
	}
	public void changeColor()
	{
		int rand = randgen.nextInt(10)-1;
            switch (rand) {
                case 1:
                    col = Color.DARKGRAY;
                    break;
                case 2:
                    col = Color.GREEN;
                    break;
                case 3:
                    col=Color.BLUE;
                    break;
                case 4:
                    col = Color.CYAN;
                    break;
                case 5:
                    col =Color.MAGENTA;
                    break;
                case 6:
                    col =Color.YELLOW;
                    break;
                case 7:
                    col =Color.PINK;
                    break;
                case 8:
                    col =Color.ORANGE;
                    break;
                case 9:
                    col =Color.LIGHTGRAY;
                    break;
                default:
                    col =Color.WHITE;
                    break;
            }
	}	
}
