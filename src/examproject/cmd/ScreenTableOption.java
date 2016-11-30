package examproject.cmd;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScreenTableOption {

    public LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
    public Runnable callback;

    public ScreenTableOption(LinkedHashMap<String, String> row, Runnable callback) {
        this.row = row;
        this.callback = callback;
    }

    public String getRowString(int cellWidth) {
        String rowLabel = "";
        String cellLabel = "";
        int cellNr = 1;
        for (Map.Entry<String, String> entry : this.row.entrySet()) {
            if (cellNr == 1) {
                cellLabel= ScreenManager.ellipse(entry.getValue(), cellWidth);
            } else {
                rowLabel += ScreenManager.padRight(cellLabel, cellWidth) + " | ";
                cellLabel = ScreenManager.ellipse(entry.getValue(), cellWidth);
            }
            cellNr++;
        }

        if (cellNr > 2) {
            rowLabel+= ScreenManager.padLeft(cellLabel, cellWidth);
        } else {
            rowLabel+= ScreenManager.padRight(cellLabel, cellWidth);
        }

        return rowLabel;
    }


    /*
     *  Helpers
     */
    public static String getHeaderStringFromRow(LinkedHashMap<String, String> sourceRow, int cellWidth) {
        String rowLabel = "";
        String cellLabel = "";
        int cellNr = 1;
        for (Map.Entry<String, String> entry : sourceRow.entrySet()) {
            if (cellNr == 1) {
                cellLabel= ScreenManager.ellipse(entry.getKey(), cellWidth);
            } else {
                rowLabel += ScreenManager.padRight(cellLabel, cellWidth) + " | ";
                cellLabel = ScreenManager.ellipse(entry.getKey(), cellWidth);
            }
            cellNr++;
        }

        if (cellNr > 2) {
            rowLabel+= ScreenManager.padLeft(cellLabel, cellWidth);
        } else {
            rowLabel+= ScreenManager.padRight(cellLabel, cellWidth);
        }

        return rowLabel;
    }

    public static String getSepStringFromRow(LinkedHashMap<String, String> sourceRow, int cellWidth) {
        String rowLabel = "";
        boolean isFirstCell = true;
        for (Map.Entry<String, String> entry : sourceRow.entrySet()) {
            if (!isFirstCell) {
                rowLabel += "-+-";
            }
            rowLabel += ScreenManager.padRight("", cellWidth, '-');
            isFirstCell = false;
        }

        return rowLabel;
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[row: " + this.row
                + ", callback: " + this.callback + "]";
    }
}
