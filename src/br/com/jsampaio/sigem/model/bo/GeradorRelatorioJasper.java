/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.model.vo.Relatorio;
import br.com.jsampaio.sigem.util.HibernateUtil;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.view.GraphicsConstants;
import java.awt.Dimension;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Janilson
 */
public class GeradorRelatorioJasper {

    private static JasperViewer viewer;
    
    /**
     * 
     * @param relatorio 
     */
    public static void gerarJasper(Relatorio relatorio) {
        try {
            JasperPrint print = JasperFillManager.fillReport(relatorio.getFile(), 
                    relatorio.getParametros(), HibernateUtil.getConnection());
            viewer = new JasperViewer(print, false);

            viewer.setIconImage(GraphicsConstants.SYSTEM_ICON);
            viewer.setTitle(relatorio.getTitulo());
            viewer.setSize(new Dimension(870, 710));
            viewer.setResizable(true);
            viewer.setVisible(true);
            viewer.setLocationRelativeTo(null);
            
            viewer = null;

        } catch (JRException ex) {
            Log.saveLog(ex, GeradorRelatorioJasper.class);
        }
    }
}