package ui;

import lombok.Data;
import memory.MemoryManager;
import memory.Zone;
import pcb.PcbManager;

import javax.swing.*;
import java.awt.*;

/**
 * @author WilderGao
 * time 2019-01-03 11:55
 * motto : everything is no in vain
 * description
 */
@Data
public class MyFrame extends JFrame {
    private JPanel panel1;
    private JList<String> outerList ;
    private JList<String> innerList;
    private JList<String> finishList;
    private JPanel pcbPanel;

    private JPanel memoryPanel;
    private JTextField currentTime;
    private JButton exitButton;
    private JTextField currentPcb;
    private JList<String> memoryList;
    private JTable table1;


    private static MyFrame myFrame;

    private MyFrame() {
        JFrame frame = new JFrame("模拟操作系统");
        frame.setContentPane(this.panel1);
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        exitButton.addActionListener(v-> {
                    JOptionPane.showMessageDialog(panel1, "再见~");
                    System.exit(0);
        });

        this.outerList.setCellRenderer(new WhiteBlueCellRenderer());
        this.innerList.setCellRenderer(new WhiteBlueCellRenderer());
        this.finishList.setCellRenderer(new WhiteBlueCellRenderer());
        this.memoryList.setCellRenderer(new IsUsedCellRenderer());
    }

    public static MyFrame instance() {
        if (myFrame == null) {
            synchronized (MyFrame.class) {
                if (myFrame == null) {
                    myFrame = new MyFrame();
                }
            }
        }
        return myFrame;
    }

    public void updateFrame(){
        updateCurrentTime();
        updatePcbPanel();
        updateMemoryPanel();
    }

    private void updatePcbPanel() {
        DefaultComboBoxModel<String> outerModel = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> innerModel = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> finishModel = new DefaultComboBoxModel<>();
        PcbManager.outerDiskPcbList.forEach(v -> outerModel.addElement(v.toStringOuter()));
        PcbManager.insideDiskPcbList.forEach(v -> innerModel.addElement(v.toStringInner()));
        PcbManager.finishPcbList.forEach(v -> finishModel.addElement(v.toStringFinish()));

        this.outerList.setModel(outerModel);
        this.innerList.setModel(innerModel);
        this.finishList.setModel(finishModel);
    }

    private void updateMemoryPanel(){
        DefaultComboBoxModel<String> memoryModel = new DefaultComboBoxModel<>();
        MemoryManager.memory.getZones().forEach(v->memoryModel.addElement(v.toString()));
        this.memoryList.setModel(memoryModel);
    }

    private void updateCurrentTime() {
        currentTime.setText("当前时间：  " + PcbManager.current.get());
    }


    class WhiteBlueCellRenderer extends DefaultListCellRenderer{
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (index % 2 == 0){
                component.setBackground(Color.WHITE);
            }else {
                component.setBackground(Color.CYAN);
            }
            return component;
        }
    }


    class IsUsedCellRenderer extends DefaultListCellRenderer{
        String check = "true";
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if ((check).equals((value.toString().split(":")[1]))){
                //证明这块区域没有被使用，显示为绿色
                component.setBackground(Color.GREEN);
            }else {
                component.setBackground(Color.RED);
            }
            return component;
        }
    }
}
