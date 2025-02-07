package view;

import model.Produto;
import dao.ProdutoDao;
import java.time.LocalDate;
import javax.swing.*;
import java.io.IOException;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ProdutoDao produtoDao = new ProdutoDao();
        int opcao;

        do {
            opcao = Integer.parseInt(JOptionPane.showInputDialog("Escolha uma opção:\n" +
                    "1 - Cadastrar produto\n" +
                    "2 - Listar produtos\n" +
                    "3 - Atualizar produto\n" +
                    "4 - Excluir produto\n" +
                    "5 - Sair"));

            switch (opcao) {
                case 1:
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do produto:"));
                    String descricao = JOptionPane.showInputDialog("Digite a descrição do produto:");
                    float preco = Float.parseFloat(JOptionPane.showInputDialog("Digite o preço do produto:"));
                    LocalDate validade = LocalDate.parse(JOptionPane.showInputDialog("Digite a validade do produto:"));

                    Produto produto = new Produto(id, descricao, preco, validade);
                    produtoDao.adicionarProduto(produto);
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                    break;

                case 2:
                    Set<Produto> produtos = produtoDao.getProdutos();
                    if (produtos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado.");
                    } else {
                        StringBuilder listaProdutos = new StringBuilder("Lista de Produtos:\n");
                        for (Produto p : produtos) {
                            listaProdutos.append(p.toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, listaProdutos.toString());
                    }
                    break;

                case 3:
                    int idAtualizar = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do produto para atualizar:"));
                    Produto produtoParaAtualizar = produtoDao.buscarProduto(idAtualizar);
                    if (produtoParaAtualizar != null) {
                        String novaDescricao = JOptionPane.showInputDialog("Digite a nova descrição:");
                        float novoPreco = Float.parseFloat(JOptionPane.showInputDialog("Digite o novo preço:"));
                        LocalDate novaValidade = LocalDate.parse(JOptionPane.showInputDialog("Digite a nova validade:"));

                        produtoDao.removerProduto(idAtualizar);
                        produtoDao.adicionarProduto(new Produto(idAtualizar, novaDescricao, novoPreco, novaValidade));
                        JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Produto não encontrado!");
                    }
                    break;

                case 4:
                    int idExcluir = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do produto para excluir:"));
                    produtoDao.removerProduto(idExcluir);
                    JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
                    break;

                case 5:
                    JOptionPane.showMessageDialog(null, "Saindo do programa.");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while (opcao != 5);
    }
}
