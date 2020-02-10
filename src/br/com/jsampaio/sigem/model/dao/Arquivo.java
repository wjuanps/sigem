/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.dao;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Janilson
 */
public class Arquivo {
    
    private static final JFileChooser fileChooser = new JFileChooser();
    
    public static File getArquivo() {
        fileChooser.setDialogTitle("Selecionar Destino");
        
        int resp = fileChooser.showSaveDialog(null);
        return ((resp == JFileChooser.APPROVE_OPTION) ? fileChooser.getSelectedFile() : null);
    }
    
    public static Path selecionarArquivo() {
        fileChooser.setDialogTitle("Selecionar Destino");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos PDF do Acrobat(*.pdf)", "pdf");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        int resp = fileChooser.showSaveDialog(null);
        
        Path path = null;
        if (resp == JFileChooser.APPROVE_OPTION) {
            path = Paths.get(fileChooser.getSelectedFile().toString());
        }
        
        return path;
    }
    
}
