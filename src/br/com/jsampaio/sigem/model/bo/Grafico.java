/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.util.Log;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Janilson
 */
public abstract class Grafico {
    
    /**
     * 
     * @param dados
     * @param titulo
     * @return 
     */
    public static JPanel gerarGraficoPizza(Map<String, Double> dados, String titulo) {
        try {
            final DefaultPieDataset dataset = new DefaultPieDataset();
            final JFreeChart chart;
            final Set<String> sets = dados.keySet();
            sets.forEach((set) -> {
                dataset.setValue(set, dados.get(set));
            });
            chart = ChartFactory.createPieChart3D(titulo, dataset, true, true, true);
            if (chart != null) {
                return new ChartPanel(chart);
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, Grafico.class);
        }
        return null;
    }
}
