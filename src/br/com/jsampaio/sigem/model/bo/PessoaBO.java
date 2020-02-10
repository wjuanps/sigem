/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.model.dao.SolicitanteDAO;
import br.com.jsampaio.sigem.model.dao.UsuarioDAO;
import br.com.jsampaio.sigem.model.vo.Pessoa;
import br.com.jsampaio.sigem.model.vo.Solicitante;
import br.com.jsampaio.sigem.model.vo.Usuario;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.util.SigemUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Janilson
 */
public class PessoaBO {

    /**
     * 
     * @param matricula
     * @return 
     */
    public Solicitante getPessoa(Long matricula) {
        try {
            final SolicitanteDAO pdao = new SolicitanteDAO();
            return pdao.getSolicitante(matricula);
        } catch (RuntimeException r) {
            Log.saveLog(r, PessoaBO.class);
        }
        return null;
    }
    
    /**
     *
     * @param pesquisa
     * @param paginacao
     * @return
     */
    public List<Solicitante> carregarSolicitantes(String pesquisa, Paginacao paginacao) {
        return carregarSolicitantes(pesquisa, paginacao, null, null);
    }
    
    /**
     * 
     * @param pesquisa
     * @param paginacao
     * @param ativo
     * @param tipo
     * @return 
     */
    public List<Solicitante> carregarSolicitantes(String pesquisa, Paginacao paginacao, String ativo, Pessoa.Tipo tipo) {
        try {
            final SolicitanteDAO pdao = new SolicitanteDAO();
            final List<Solicitante> pessoas = pdao.getList(pesquisa, paginacao, ativo, tipo);
            if (!pessoas.isEmpty()) {
                return pessoas;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, PessoaBO.class);
        }
        return new ArrayList<>();
    }

    /**
     * 
     * @param solicitante 
     */
    public void atualizarPessoa(Solicitante solicitante) {
        try {
            final SolicitanteDAO pdao = new SolicitanteDAO();
            pdao.atualizar(solicitante);
        } catch (RuntimeException r) {
            Log.saveLog(r, PessoaBO.class);
        }
    }
    
    /**
     *
     * @param pessoa
     * @param login
     * @param senha
     * @return
     */
    public String cadastrarPessoa(Pessoa pessoa, String login, String senha) {
        try {
            if (pessoa == null) {
                return "Não foi possível realizar o cadastro";
            }

            if (pessoa.getTipo() == null) {
                return "Tipo inválido";
            }

            if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
                return "Informe o nome";
            }

            if (pessoa.getMatricula() == null || !(pessoa.getMatricula() instanceof Number)) {
                return "Matricula inválida";
            }

            final String emailEsperado = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
            if (pessoa.getEmail() == null || pessoa.getEmail().isEmpty()
                    || !pessoa.getEmail().matches(emailEsperado)) {
                return "Email inválido";
            }

            final String telefoneEsperado = "^\\(\\d{2}\\)\\s{1}9\\s{1}\\d{4}-\\d{4}$";
            if (pessoa.getTelefone() == null || pessoa.getTelefone().isEmpty()
                    || !pessoa.getTelefone().matches(telefoneEsperado)) {
                return "Telefone inválido";
            }

            if (pessoa.getUnidadeCurso() == null) {
                return "Informe a Unidade/Curso";
            }

            if (pessoa.getTipo().equals(Pessoa.Tipo.Usuario)) {

                Usuario usuario = this.getUsuario(pessoa);

                if (login == null || login.isEmpty()) {
                    return "Informe o login";
                }

                if (senha == null || senha.isEmpty()) {
                    return "Informe a senha";
                }

                UsuarioDAO udao = new UsuarioDAO();

                Usuario usuarioTemp = udao.getUsuario(usuario.getMatricula());
                if (usuarioTemp != null) {
                    return String.format("Já existe um cadastro com essa Matricula:%s", pessoa.getMatricula());
                }

                usuarioTemp = udao.getUsuario(login);
                if (usuarioTemp != null) {
                    return String.format("Já existe um cadastro com esse login:%s", login);
                }

                usuario.setLogin(login);
                usuario.setSenha(SigemUtil.criptografar(senha));

                usuario = udao.salvar(usuario);
                if (usuario != null) {
                    return "";
                }
            } else {
                final SolicitanteDAO sdao = new SolicitanteDAO();
                if (pessoa.getCodigo() == null) {
                    final Solicitante solicitanteTemporario = sdao.getSolicitante(pessoa.getMatricula());
                    if (solicitanteTemporario != null) {
                        return String.format("Já existe um cadastro com essa Matricula:%s", pessoa.getMatricula());
                    }
                }

                Solicitante solicitante = sdao.salvar(getSolicitante(pessoa));
                if (solicitante != null) {
                    return "";
                }
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, PessoaBO.class);
        }
        return "Erro ao realizar o cadastro";
    }

    /**
     *
     * @param pessoa
     * @return
     */
    private Solicitante getSolicitante(Pessoa pessoa) {
        final Solicitante solicitante = new Solicitante();
        if (pessoa.getCodigo() != null) {
            solicitante.setCodigo(pessoa.getCodigo());
        }
        solicitante.setNome(pessoa.getNome());
        solicitante.setEmail(pessoa.getEmail());
        solicitante.setMatricula(pessoa.getMatricula());
        solicitante.setTelefone(pessoa.getTelefone());
        solicitante.setTipo(pessoa.getTipo());
        solicitante.setUnidadeCurso(pessoa.getUnidadeCurso());
        return solicitante;
    }

    /**
     *
     * @param pessoa
     * @return
     */
    private Usuario getUsuario(Pessoa pessoa) {
        final Usuario usuario = new Usuario();
        if (pessoa.getCodigo() != null) {
            usuario.setCodigo(pessoa.getCodigo());
        }
        usuario.setNome(pessoa.getNome());
        usuario.setEmail(pessoa.getEmail());
        usuario.setMatricula(pessoa.getMatricula());
        usuario.setTelefone(pessoa.getTelefone());
        usuario.setTipo(pessoa.getTipo());
        usuario.setUnidadeCurso(pessoa.getUnidadeCurso());
        return usuario;
    }
}