package trabalhocontatos;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String pwd = req.getParameter("loginPass");
		String dados;
		
		HttpSession s = req.getSession();
		Object o = s.getAttribute(login);
		PrintWriter writer = resp.getWriter();	
		
		//Confere se no banco possui login e senha informado
		if(banco.confere(null, "C:\\Utilizador\\", "TWEB.txt", login, pwd, "L")){
			//Verifica usuário esta na sessão
			if(o == null){
				s.setAttribute("login", login);
				s.setAttribute("pwd", pwd);
				o = s.getAttribute("login");
			}				
			dados = banco.abrir(null, "C:\\Utilizador\\", "TWEB.txt", o.toString(), "C");
		}else{
			dados = bloqueio(login);
		}
		writer.write(dados);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("excluir") != null){
			int position = Integer.parseInt(req.getParameter("excluir"));			
			HttpSession s = req.getSession();
			Object o = s.getAttribute("login");
			PrintWriter writer = resp.getWriter();
			String dados;
			if(o.toString() != null){
				banco.apagar(null, "C:\\Utilizador\\", "TWEB.txt",o.toString(), "C", position);
				dados = banco.abrir(null, "C:\\Utilizador\\", "TWEB.txt", o.toString(), "C");
				writer.write(dados);
			}
		}else{		
			HttpSession s = req.getSession();
			Object o = s.getAttribute("login");
			PrintWriter writer = resp.getWriter();
			String dados;
			if(o.toString() != null){
				banco.escrever("C:\\Utilizador\\", "TWEB.txt","C|" + o.toString() + "|" + req.getParameter("nContato") + "|" + req.getParameter("nTelefone") + "\r\n");
				dados = banco.abrir(null, "C:\\Utilizador\\", "TWEB.txt", o.toString(), "C");
				writer.write(dados);
			}			
		}
	}

	public String bloqueio(String login){
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<link rel='stylesheet' href='resources/bootstrap-3.3.5-dist/css/bootstrap.min.css'>");
		sb.append("<link rel='stylesheet' href='resources/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css'>");
		sb.append("<style>");
		sb.append("table {");
		sb.append("width:100%;");
		sb.append("}");
		sb.append("table, th, td {");
		sb.append("border: 1px solid black;");
		sb.append("border-collapse: collapse;");
		sb.append("}");
		sb.append("th, td {");
		sb.append("padding: 5px;");
		sb.append("text-align: left;");
		sb.append("}");
		sb.append("table#t01 tr:nth-child(even) {");
		sb.append("background-color: #eee;");
		sb.append("}");
		sb.append("table#t01 tr:nth-child(odd) {");
		sb.append("background-color:#fff;");
		sb.append("}");
		sb.append("table#t01 th	{");
		sb.append("background-color: black;");
		sb.append("color: white;");
		sb.append("}");
		sb.append("</style>");		
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div class='container'>");
		sb.append("<div class='panel panel-default'>");
		sb.append("<div class='panel-heading'>");
		sb.append("<h3 class='panel-title'>Login</h3>");
		sb.append("<a href='index.html'>voltar</a>");	
		sb.append("</div>");
		sb.append("<div class='panel-body'>");
		sb.append("Usuário não cadastrado ou senha inválida.");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");		
		return sb.toString();
	}

}

