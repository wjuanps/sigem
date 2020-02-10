/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.util.HibernateUtil;
import br.com.jsampaio.sigem.controller.InicioController;
import br.com.jsampaio.sigem.model.dao.Arquivo;
import br.com.jsampaio.sigem.model.vo.Relatorio;
import br.com.jsampaio.sigem.model.vo.Requisicao;
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Janilson
 */
public class RelatorioBO {

    
    
    /**
     *
     * @param relatorio
     */
    public void gerarRelatorioXLS(Relatorio relatorio) {
        HSSFWorkbook hSSFWorkbook = new HSSFWorkbook();
        HSSFSheet hssfs = hSSFWorkbook.createSheet(relatorio.getAba());

        File file = Arquivo.getArquivo();

        if (file == null) {
            return;
        }

        if (!file.getName().endsWith(".xls")) {
            file = new File(file.toString().concat(".xls"));
        }

        if (file.exists()) {
            final int resp = JOptionPane.showConfirmDialog(null, "O arquivo já existe, deseja substituí-lo?");
            if (resp != JOptionPane.OK_OPTION) {
                return;
            }
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {

            int linha = 0;

            HSSFRow titulo = hssfs.createRow(linha++);
            for (int i = 0; i < relatorio.getHeader().length; i++) {
                titulo.createCell(i).setCellValue(relatorio.getHeader()[i]);
            }

            for (List<Object> objects : relatorio.getObjects()) {
                HSSFRow row = hssfs.createRow(linha++);

                int cell = 0;
                for (Object object : objects) {
                    row.createCell(cell++).setCellValue(object.toString());
                }
            }

            hSSFWorkbook.write(fos);
            fos.flush();

            Runtime.getRuntime().exec("cmd.exe /C start " + file);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gerarRelatorioPDF(Relatorio relatorio) {

//        Document document = null;
//        try {
//            File file = Arquivo.getArquivo();
//
//            if (file == null) {
//                return;
//            }
//
//            if (!file.getName().endsWith(".pdf")) {
//                file = new File(file.toString().concat(".pdf"));
//            }
//
//            if (file.exists()) {
//                final int resp = JOptionPane.showConfirmDialog(null, "O arquivo já existe, deseja substituí-lo?");
//                if (resp != JOptionPane.OK_OPTION) {
//                    return;
//                }
//            }
//
//            document = new Document(PageSize.A4, 2, 2, 20, 2);
//            PdfWriter.getInstance(document, new FileOutputStream(file));
//            document.open();
//
//            PdfPTable table = new PdfPTable(new float[]{.75f, .25f});
//            table.getDefaultCell().setPaddingBottom(10.0f);
//            table.getDefaultCell().setBorder(0);
//            table.getDefaultCell().setBorderWidthBottom(1.0f);
//
//            Paragraph paragraph = new Paragraph();
//            paragraph.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));
//            paragraph.add(new Phrase("Universidade Federal do Pará"));
//            paragraph.add(new Phrase("\nSistema de Gerenciamento de Equipamentos e Materiais"));
//            paragraph.add(new Phrase("\nRelatório de Requisições"));
//            table.addCell(paragraph);
//
//            paragraph = new Paragraph();
//            paragraph.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));
//            paragraph.add(new Phrase("Mês: Novembro"));
//            paragraph.add(new Phrase("\nData: 03/11/2017"));
//            table.addCell(paragraph);
//            document.add(table);
//
//            table = new PdfPTable(new float[]{.25f, .25f, .25f, .125f, .125f});
//            table.getDefaultCell().setBorder(0);
//            table.setSpacingBefore(5);
//            for (String string : relatorio.getHeader()) {
//                paragraph = new Paragraph();
//                paragraph.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD, BaseColor.BLACK));
//                paragraph.add(new Phrase(string));
//                PdfPCell header = new PdfPCell(paragraph);
//                header.setBorder(0);
//                table.addCell(header);
//            }
//
//            document.add(table);
//
//            Runtime.getRuntime().exec("cmd.exe /C start " + file);
//
//        } catch (FileNotFoundException | DocumentException ex) {
//            Logger.getLogger(RelatorioBO.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(RelatorioBO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (document != null) {
//                document.close();
//            }
//        }
    }

    public void gerarRelatorioCSV(Relatorio relatorio) {
        File file = Arquivo.getArquivo();

        if (file == null) {
            return;
        }

        if (!file.getName().endsWith(".csv")) {
            file = new File(file.toString().concat(".csv"));
        }

        if (file.exists()) {
            final int resp = JOptionPane.showConfirmDialog(null, "O arquivo já existe, deseja substituí-lo?");
            if (resp != JOptionPane.OK_OPTION) {
                return;
            }
        }

        final StringBuilder builder = new StringBuilder();
        for (String head : relatorio.getHeader()) {
            builder.append(head).append(",");
        }

        final StringBuilder texto = new StringBuilder();
        relatorio.getObjects().forEach((List<Object> object) -> {
            texto.append("\n");
            object.forEach((ob) -> {
                String str = Normalizer.normalize(ob.toString(), Normalizer.Form.NFKD).replaceAll(
                        "\\p{InCombiningDiacriticalMarks}+", "");
                texto.append(str).append(",");
            });
        });

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.toString()), StandardCharsets.UTF_8)) {
            writer.write(builder.toString());
            writer.write(texto.toString());
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(RelatorioBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Runtime.getRuntime().exec("cmd.exe /C start " + file);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int count(String nome) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return ((Number) session.createCriteria(Requisicao.class)
                    .createAlias("solicitante", "sol")
                    .createAlias("sol.unidadeCurso", "uc")
                    .createAlias("requisicaoMateriais", "rm")
                    .add(Restrictions.eq("uc.unidadeCurso", nome))
                    .setProjection(Projections.sum("rm.quantidadeEntregue"))
                    .uniqueResult()).intValue();
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }
    
    //public static void main(String[] args) {


        
//        DefaultPieDataset data = new DefaultPieDataset();
//        
//        nomes.forEach((nome) -> {
//            data.setValue(nome, new Double(count(nome)));
//        });
//        
//        JFreeChart grafico = ChartFactory.createPieChart("Juan Soares", data, true, true, true);
//        
//        JFrame frame = new JFrame("Teste Gráfico");
//        ChartPanel chart = new ChartPanel(grafico);
//        
//        frame.add(chart);
//        
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(new Dimension(900, 600));
//        frame.setVisible(true);
//        frame.setLocationRelativeTo(null);
        
        
//        RelatorioBO r = new RelatorioBO();
//        Relatorio relatorio = new Relatorio();
//        List<Requisicao> requisicaos = new RequisicaoDAO().getList();
//        final String[] header = {
//            "Requisicoo", "Unidade/Curso", "Requisitante", "Recebedor",
//            "Material", "Un.", "Qtd. Requisitada", "Qtd. Recebida", "Data Entrega"
//        };
//
//        final List<List<Object>> objects = new ArrayList<>();
//        requisicaos.forEach((requisicao) -> {
//            requisicao.getRequisicaoMateriais().stream().map((rm) -> {
//                final List<Object> obs = new ArrayList<>();
//                obs.add(requisicao.getCodigo());
//                obs.add(requisicao.getSolicitante().getUnidadeCurso().getUnidadeCurso());
//                obs.add(rm.getRequisicao().getSolicitante().getNome());
//                obs.add(rm.getRequisicao().getRecebedor().getNome());
//                obs.add(rm.getMaterial().getDescricao());
//                obs.add(rm.getMaterial().getUnidade());
//                obs.add(rm.getQuantidadeRequisitada());
//                obs.add(rm.getQuantidadeEntregue());
//                obs.add(rm.getDataEntrega());
//                return obs;
//            }).forEachOrdered((obs) -> {
//                objects.add(obs);
//            });
//        });
//
//        relatorio.setAba("Requisições");
//        relatorio.setHeader(header);
//        relatorio.setObjects(objects);
//
//        r.gerarRelatorioCSV(relatorio);
    //}
}
