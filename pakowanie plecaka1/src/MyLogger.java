import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {
    static Logger logger = Logger.getLogger(MyLogger.class.getName());


    public MyLogger(){
        logger.setLevel(Level.INFO);
        logger.addHandler(new ConsoleHandler());
    }
    public static void log2(String text, Object...value){
        List myParams = new ArrayList<Object>();
        for(int i=0;i<value.length;i++){
            if(value[i] instanceof int[]){
                int myP[] = (int[]) value[i];
                myParams.add(Arrays.toString(myP));
            } else if(value[i] instanceof boolean[]){
                boolean myP[] = (boolean[]) value[i];
                myParams.add(Arrays.toString(myP));
            } else {
                myParams.add(value[i]);
            }

        }
        logger.log(Level.INFO,text,myParams);
    }

    public static void log(String text, Object...value){
        List myParams = new ArrayList<Object>();
        for(int i=0;i<value.length;i++){
            if(value[i] instanceof int[]){
                int myP[] = (int[]) value[i];
                myParams.add(Arrays.toString(myP));
            } else if(value[i] instanceof boolean[]){
                boolean myP[] = (boolean[]) value[i];
                myParams.add(Arrays.toString(myP));
            } else if(value[i] instanceof Object[]){
                Object myP[] = (Object[]) value[i];
                myParams.add(Arrays.toString(myP));
            }else {
                myParams.add(value[i]);
            }

        }

        System.out.println(text+":"+myParams.toString());
    //logger.log(Level.INFO,text,myParams);
    }
}
