package org.sortitem.core;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Romain on 23/07/2016.
 */
public class Main extends JFrame {
    private final Generator generator;

    private final JPanel content;
    private final JTextField textField, next;
    private final JSeparator separator;
    private final JButton btnGenerate, btnClear;
    private final JTextArea data, generated;
    private final JScrollPane sdata, sgenerated;
    private final JLabel copyrights, lblNext, lblNpcId;
    private final JCheckBox box;

    public Main() {
        this.generator = new Generator(this);
        this.content = new JPanel();
        this.lblNpcId = new JLabel("Npc ID");
        this.textField = new JTextField();
        this.next = new JTextField();
        this.separator = new JSeparator();
        this.btnGenerate = new JButton("Generate");
        this.lblNext = new JLabel("Next Slot");
        this.generated = new JTextArea();
        this.data = new JTextArea();
        this.sdata = new JScrollPane();
        this.sgenerated = new JScrollPane();
        this.copyrights = new JLabel("http://github.com/romain-p");
        this.btnClear = new JButton("Clear");
        this.box = new JCheckBox("SET Mode");
    }

    public static void main(String[] args) {
        //system look
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().initialize();
            }
        });
    }

    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(content);
        content.setLayout(null);

        //content
        lblNpcId.setBounds(496, 73, 40, 14);
        content.add(lblNpcId);

        lblNext.setBounds(496, 25, 65, 14);
        content.add(lblNext);

        separator.setBounds(24, 156, 537, 2);
        content.add(separator);

        btnGenerate.setBounds(475, 122, 89, 23);
        content.add(btnGenerate);

        textField.setBounds(475, 91, 86, 20);
        content.add(textField);
        textField.setColumns(10);

        copyrights.setBounds(223, 330, 146, 14);
        content.add(copyrights);

        sdata.setBounds(24, 25, 441, 120);
        sdata.setBorder(null);
        content.add(sdata);

        data.setBorder(new TitledBorder(null, "Item Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        data.setText("item,slot;item,slot... or item;item;item...");
        data.setLineWrap(true);
        sdata.setViewportView(data);

        sgenerated.setBounds(24, 169, 537, 146);
        sgenerated.setBorder(null);
        content.add(sgenerated);

        generated.setBorder(new TitledBorder(null, "Generated", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        generated.setLineWrap(true);
        generated.setEditable(false);
        sgenerated.setViewportView(generated);

        btnClear.setBounds(24, 326, 89, 23);
        content.add(btnClear);

        next.setColumns(10);
        next.setText("0");
        next.setBounds(475, 43, 86, 20);
        content.add(next);

        box.setBounds(475, 326, 97, 23);
        content.add(box);
        //end

        setResizable(false);
        setBounds(100, 100, 595, 394);
        setVisible(true);
        listen();
    }

    private void listen() {
        btnGenerate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent arg0) {
                try {
                    generator.generateLines();
                }catch(Exception e) {
                    generated.setText("fkin tard, you cant put correct values?");
                }
            }
        });

        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent arg0) {
                generated.setText("");
            }
        });

        generated.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                generated.selectAll();
                generated.copy();
            }
        });
    }

    public JTextArea getData() {
        return data;
    }

    public JTextArea getGenerated() {
        return generated;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JTextField getNext() {
        return this.next;
    }

    public boolean selected() {
        return box.isSelected();
    }
}
