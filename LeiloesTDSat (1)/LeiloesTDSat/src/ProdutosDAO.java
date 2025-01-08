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
        
        // Conectar ao banco de dados (comentei aqui porque você já tem um método para isso)
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
                // Exibe mensagem de sucesso
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            } else {
                // Exibe mensagem de erro caso a inserção não tenha sido bem-sucedida
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar o produto.");
            }
            
        } catch (Exception e) {
            // Exibe uma mensagem caso ocorra algum erro no processo de cadastro
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        } finally {
            // Fechar a conexão, se necessário
            try {
                if (conn != null) conn.close();
                if (prep != null) prep.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // Método para listar todos os produtos cadastrados
    public ArrayList<ProdutosDTO> listarProdutos() {
        return listagem;
    }
}
