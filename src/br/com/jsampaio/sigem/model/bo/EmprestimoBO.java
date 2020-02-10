/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.dao.EmprestimoDAO;
import br.com.jsampaio.sigem.model.dao.EquipamentoDAO;
import br.com.jsampaio.sigem.model.dao.SolicitanteDAO;
import br.com.jsampaio.sigem.model.vo.Emprestimo;
import br.com.jsampaio.sigem.model.vo.Equipamento;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.model.vo.Solicitante;
import br.com.jsampaio.sigem.util.Log;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Janilson
 */
public final class EmprestimoBO {

    /**
     * 
     * @param matricula
     * @return 
     */
    public Solicitante getPessoa(Long matricula) {
        try {
            final SolicitanteDAO pdao = new SolicitanteDAO();
            final Solicitante pessoa = pdao.getSolicitante(matricula);

            if (pessoa != null) {
                return pessoa;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, EmprestimoBO.class);
        }
        return null;
    }

    public Equipamento getEquipamento(Long numTombamento) {
        try {
            final EquipamentoDAO edao = new EquipamentoDAO();
            final Equipamento equipamento = edao.getEquipamentoPeloNumTombamento(numTombamento);

            if (equipamento != null) {
                return equipamento;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, EmprestimoBO.class);
        }
        return null;
    }

    public Emprestimo getEmprestimo(Long numProtocolo) {
        try {
            final EmprestimoDAO edao = new EmprestimoDAO();
            final Emprestimo emprestimo = edao.getEmprestimo(numProtocolo);
            
            if (emprestimo != null) {
                return emprestimo;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, EmprestimoBO.class);
        }
        
        return null;
    }

    /**
     *
     * @param solicitante
     * @param equipamentos
     * @param dataPrevistaEntrega
     * @return
     */
    public Emprestimo confirmarEmprestimo(Solicitante solicitante, List<Equipamento> equipamentos, Date dataPrevistaEntrega) {
        try {
            if (solicitante == null) {
                Messenger.showMessage("Informe a pessoa que está solicitando o emprestimo", MessageType.ERROR);
                return null;
            }
            
            final EmprestimoDAO edao = new EmprestimoDAO();
            
            if (equipamentos == null || equipamentos.isEmpty()) {
                Messenger.showMessage("Informe os equipamentos que serão emprestados", MessageType.ERROR);
                return null;
            }

            if (Session.getUsuario() == null) {
                Messenger.showMessage("Falha ao carregar o usuário.\nVerifique se você está logado.\n\nEm caso de dúvida chame o suporte.", MessageType.ERROR);
                return null;
            }

            if (dataPrevistaEntrega == null) {
                Messenger.showMessage("Informe a data prevista de develução", MessageType.ERROR);
                return null;
            }

            
            final Emprestimo emprestimo = new Emprestimo();
            emprestimo.setSolicitante(solicitante);
            emprestimo.setUsuario(Session.getUsuario());
            
            Long numProtocolo = 0L;
            do {
                numProtocolo = this.gerarProtocolo();
            } while (edao.hasNumProtocolo(numProtocolo));
            
            emprestimo.setNumeroProtocolo(numProtocolo);

            equipamentos.forEach((equipamento) -> {
                emprestimo.addEquipamento(equipamento, dataPrevistaEntrega);
            });
            
            edao.realizarEmprestimo(emprestimo);
            return emprestimo;
            
        } catch (RuntimeException r) {
            Log.saveLog(r, EmprestimoBO.class);
        }
        return null;
    }
    
    /**
     * 
     * @param solicitante
     * @return 
     */
    public Set<Emprestimo> getEmprestimo(Solicitante solicitante) {
        final EmprestimoDAO edao = new EmprestimoDAO();
        final Set<Emprestimo> emprestimo = edao.getEmprestimoAtrasado(solicitante);
        return emprestimo;
    }
    
    /**
     * 
     * @param emprestimo
     * @return 
     */
    public String confirmarDevolução(Emprestimo emprestimo) {
        try {
            final EmprestimoDAO edao = new EmprestimoDAO();
            edao.atualizar(emprestimo);
        } catch (RuntimeException r) {
            Log.saveLog(r, EmprestimoBO.class);
            return "Erro ao realizar a devolução";
        }
        return "";
    }
    
    /**
     * 
     * @return 
     */
    private Long gerarProtocolo() {
        final StringBuilder numProtocoloTemporario = new StringBuilder();
        final int ano = Calendar.getInstance().get(Calendar.YEAR);
        numProtocoloTemporario.append(ano);
        final Random random = new Random();
        for (int i = 0; i < 6; i++) {
            numProtocoloTemporario.append(random.nextInt(10));
        }
        
        return Long.valueOf(numProtocoloTemporario.toString());
    }
}