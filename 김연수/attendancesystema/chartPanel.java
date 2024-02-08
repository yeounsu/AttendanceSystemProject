package attendancesystema;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;

public class chartPanel extends JPanel {

    public chartPanel(JFreeChart chart) {
        super(new BorderLayout());
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }
}