package org.sortitem.core;

import javax.swing.*;

/**
 * Created by Romain on 23/07/2016.
 */
public class Generator {
    private final Main main;
    private int lastNpc;

    private int swap;

    public Generator(Main main) {
        this.main = main;
        this.lastNpc = 0;
        this.swap = 1;
    }

    public void generateLines() {
        String[] items = main.getData().getText().split(";");
        JTextArea sql = main.getGenerated();
        StringBuilder builder = new StringBuilder();

        boolean sets = main.selected();

        int npc = Integer.parseInt(main.getTextField().getText());
        int count;

        if (lastNpc != npc && lastNpc != 0) {
            main.getNext().setText("1");
            if(!sql.getText().isEmpty())
                builder.append(";");
            count = 0;
            swap = 1;
        } else {
           count = Integer.parseInt(main.getNext().getText()) - 1;
            if (sets) count++;
        }

        if(!sql.getText().isEmpty()) {
            sql.setText(sql.getText().substring(0, sql.getText().length()-1));
        }

        boolean first = sql.getText().isEmpty()
                || lastNpc != npc;

        int slot = 0;

        for(String item: items) {
            String[] data = item.split(",");
            int id = Integer.parseInt(data[0]);

            try {
                slot = Integer.parseInt(data[1]);
            } catch(Exception e) {
                if (sets) {
                    builder.append(getTemplate(!first, npc, count, id));

                    if (swap < 5) {
                        count = count+2;
                        swap++;
                    } else if(swap == 5) {
                        count = count-7;
                        swap++;
                    } else if(swap < 10) {
                        count = count+2;
                        swap++;
                    } else if(swap == 10) {
                        swap = 1;
                        count++;
                    }
                } else {
                    slot = count==10?slot+1:slot+2;
                }
            }

            if (!sets) builder.append(getTemplate(!first, npc, slot, id));

            if (first) first = !first;
        }

        sql.setText(sql.getText() + builder.toString() + ";");
        lastNpc = npc;

        int val = sets ? count:count+1;
        main.getNext().setText(String.valueOf((val)));
    }

    private String getTemplate(boolean full, int npc, int slot, int item) {
        String request = !full
                ? "INSERT INTO `world`.`npc_vendor` " +
                "(`entry`, `slot`, `item`, `maxcount`, `incrtime`, `ExtendedCost`, `VerifiedBuild`) " +
                "VALUES ('%s', '%s', '%s', '0', '0', '0', '0')"
                : ",('%s', '%s', '%s', '0', '0', '0', '0')";

        return String.format(request, npc, slot, item);
    }
}
