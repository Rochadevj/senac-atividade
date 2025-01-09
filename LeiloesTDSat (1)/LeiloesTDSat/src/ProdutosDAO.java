import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    // Método para cadastrar um produto no banco de dados
    public void cadastrarProduto(ProdutosDTO produto) {
        //conn = new ConectaDAO().connectDB();
        
        // Comando SQL para inserir os dados
        String sql = "INSERT INTO produtos (nome, descricao, preco) VALUES (?, ?, ?)";
        
        try {
            // Preparar a consulta
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setString(2, produto.getDescricao());
            prep.setDouble(3, produto.getPreco());
            
            // Executar a consulta
            int rowsAffected = prep.executeUpdate();
            
            // Verificar se o produto foi cadastrado com sucesso
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar o produto.");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
                if (prep != null) prep.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        // Limpar a lista antes de preencher para evitar duplicação
        listagem.clear();
        
        // Comando SQL para buscar todos os produtos
        String sql = "SELECT * FROM produtos";
        
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            // Preencher a lista com os produtos retornados
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setDescricao(resultset.getString("descricao"));
                produto.setPreco(resultset.getDouble("preco"));
                listagem.add(produto);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar os produtos: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
                if (prep != null) prep.close();
                if (resultset != null) resultset.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return listagem;
    }

    // Método para listar produtos vendidos
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        listagem.clear(); // Limpa a lista para evitar duplicação
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setDescricao(resultset.getString("descricao"));
                produto.setPreco(resultset.getDouble("preco"));
                listagem.add(produto);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
                if (prep != null) prep.close();
                if (resultset != null) resultset.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listagem;
    }

    // Método para vender um produto
    public void venderProduto(int idProduto) {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1, idProduto);

            int rowsAffected = prep.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro: Produto não encontrado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender o produto: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
                if (prep != null) prep.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
}
