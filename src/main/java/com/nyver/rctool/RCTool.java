package com.nyver.rctool;

import javax.swing.*;

/**
 * Description
 *
 * @author Yuri Novitsky
 */
public class RCTool {
    private JTabbedPane MainPane;
    private JPanel MainPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("RCTool");
        frame.setContentPane(new RCTool().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
