import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import com.ecommerce.DBConnection;
@WebServlet("/ProductDetails")
public class ProductDetails extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    public ProductDetails()
    {
    super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
      try {
           PrintWriter out = response.getWriter();
           out.println("<html><body>");
           InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
           Properties props = new Properties();
           DBConnection conn = new DBConnection("jdbc:mysql://localhost:3306/demo", "root", "Kanish@26");
           Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
           ResultSet rst = stmt.executeQuery("select * from products");
           String productSearch = request.getParameter("search");
           if(productSearch == null){	
	           out.println("Viewing the table.." + "<Br>" + "<Br>");
	           while (rst.next()) {
	               out.println(rst.getInt("ProductId") + " : "+"  "+ rst.getString("ProductName")+"   $"+rst.getDouble("Price") + "<Br>");
	           }
           }
           else{
               String sql_res= "select * from products where ProductId=" + productSearch;
               ResultSet inTable = stmt.executeQuery(sql_res);
               if(inTable.next())
                  out.println(inTable.getInt("ProductId")+" : "+"  " + inTable.getString("ProductName")+"     $" + inTable.getDouble("Price") + "<Br>");
               else
                  out.println("There was no element with product ID: " + productSearch + " found in the table, please try again");
           }
        stmt.close();
        out.println("</body></html>");
        conn.closeConnection();
       } 
      catch (ClassNotFoundException e){
          e.printStackTrace();
       } 
      catch (SQLException e){
          e.printStackTrace();
      }
     }
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                // TODO Auto-generated method stub
                doGet(request, response);
        }

}