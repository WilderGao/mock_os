import execute.FcfsMethod;
import execute.RrMethod;
import pcb.Pcb;
import pcb.PcbManager;
import ui.MyFrame;
import util.PcbUtil;

import java.util.List;

/**
 * @author WilderGao
 * time 2019-01-02 17:45
 * motto : everything is no in vain
 * description
 */
public class MainProcess {
    private static int THREAD_NUM = 5;
    private static boolean isContinue = true;
    private MyFrame myFrame = MyFrame.instance();

    public static void endOrStart(){
        if (isContinue){
            isContinue = false;
        }else {
            isContinue = true;
        }
    }

    public void execute() throws InterruptedException {
        FcfsMethod outer = new FcfsMethod();
        RrMethod in = new RrMethod(5);

        while (isContinue){
            myFrame.updateFrame();
            outer.doFcfs();
            in.doSchedule();
            PcbManager.current.incrementAndGet();
            Thread.sleep(1000);
        }
        System.out.println("Bye！");
    }

    public static void main(String[] args) {
        try {
            new MainProcess().execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
