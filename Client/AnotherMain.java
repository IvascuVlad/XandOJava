import GUI.MainFrame;
import GUI.ToServerSingleton;

import java.io.IOException;
import java.io.PrintWriter;

public class AnotherMain {
    public static void main (String[] args) throws IOException {
        MainFrame da = new MainFrame();
        da.setVisible(true);
        da.pack();

        ToServerSingleton instance = ToServerSingleton.getInstance();
        PrintWriter out = instance.getOutput();
    }

}
