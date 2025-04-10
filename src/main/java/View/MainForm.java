package View;

import Controller.ProductGroupController;
import Entity.ProductGroupEntity;
import Utils.JMenuBarUtil;
import Utils.Result;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainForm extends JFrame {
    private JMenuBar menuBar;

    public MainForm() {
        setTitle("Main");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Встановлюємо сучасний вигляд (Nimbus)
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception ex) {
            System.out.println("Nimbus L&F не вдалося встановити.");
        }

        initComponents();
    }

    private void initComponents() {
        menuBar = JMenuBarUtil.getMenuBar();
        setJMenuBar(menuBar);
    }
}
