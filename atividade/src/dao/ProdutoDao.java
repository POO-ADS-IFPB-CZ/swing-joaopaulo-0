package dao;

import model.Produto;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ProdutoDao {

    private File arquivo;

    public ProdutoDao() {
        arquivo = new File("Produtos");
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean adicionarProduto(Produto produto) {
        try {
            Set<Produto> produtos = getProdutos();
            if (produtos.add(produto)) {
                atualizarArquivo(produtos);
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removerProduto(int id) {
        try {
            Set<Produto> produtos = getProdutos();
            Produto produtoParaRemover = buscarProduto(id);
            if (produtoParaRemover != null) {
                produtos.remove(produtoParaRemover);
                atualizarArquivo(produtos);
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Produto buscarProduto(int id) {
        try {
            for (Produto p : getProdutos()) {
                if (p.getId() == id) {
                    return p;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void atualizarArquivo(Set<Produto> produtos) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(produtos);
        }
    }

    public Set<Produto> getProdutos() throws IOException, ClassNotFoundException {
        if (arquivo.length() == 0) {
            return new HashSet<>();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Set<Produto>) in.readObject();
        }
    }
}
