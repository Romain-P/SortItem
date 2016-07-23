package org.sortitem.core;

import javax.swing.*;

/**
 * Created by Romain on 23/07/2016.
 */
public class Generator {
    private final Main main;

    public Generator(Main main) {
        this.main = main;
    }

    public void generateLines() {
        String[] items = main.getData().getText().split(";");
        JTextArea sql = main.getGenerated();
        int count = 0, npc = Integer.parseInt(main.getTextField().getText());

        if(sql.getText().length() > 0) {
            sql.setText(sql.getText().substring(0, sql.getText().length()-1));
        }

        StringBuilder builder = new StringBuilder();
        boolean first = sql.getText().isEmpty();

        for(String item: items) {
            String[] data = item.split(",");
            int id = Integer.parseInt(data[0]);
            int slot;

            try {
                slot = Integer.parseInt(data[1]);
            } catch(Exception e) {
                slot = (count = count + 1);
            }

            builder.append(getTemplate(!first, id, slot, npc));

            if (first) first = !first;
        }

        sql.setText(sql.getText() + builder.toString() + ";");
    }

    private String getTemplate(boolean full, int item, int slot, int npc) {
        String request = !full
                ? "INSERT INTO `world`.`npc_vendor` " +
                "(`entry`, `slot`, `item`, `maxcount`, `incrtime`, `ExtendedCost`, `VerifiedBuild`) " +
                "VALUES ('%s', '%s', '%s', '0', '0', '0', '0')"
                : ",('%s', '%s', '%s', '0', '0', '0', '0')";

        return String.format(request, npc, slot, item);
    }
}
