package trabalhocontatos;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class banco {
	//Lista Contatos User	
	public static String abrir(String[] args, String caminho, String nome, String user, String tipo) throws IOException{
		Path path = Paths.get(caminho + nome);
		String auxtxt[];
		String auxid;		
		String auxnome;
		String contato;
		String fone;
		int count = 0;
		StringBuilder sb = new StringBuilder();		
		List<String> readAllLines = Files.readAllLines(path, Charset.defaultCharset());
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
		sb.append("<h3 class='panel-title'>Agenda</h3>");
		sb.append("<a href='index.html'>voltar</a>");	
		sb.append("</div>");
		sb.append("<div class='panel-body'>");
		sb.append("<form class=\"form-inline\" id=\"incluir\" action=\"login\" method=\"get\" role=\"form\">");
		sb.append("<input type=\"text\" name=\"nContato\" id=\"nContato\" placeholder=\"Contato\" required autofocus>");
		sb.append("<input type=\"text\" name=\"nTelefone\" id=\"nTelefone\" placeholder=\"Telefone\" required autofocus>");
		sb.append("<button id=btnI type=\"submit\">Incluir</button></TD>");
		sb.append("</form>");
		sb.append("<Table class=\"table table-hover\">");		
		sb.append("<TR>");
		sb.append("<TH>ID</TH>");
		sb.append("<TH>Contato</TH>");
		sb.append("<TH>Fone</TH>");
		sb.append("</TR>");
		for (String linha : readAllLines){
			auxtxt = linha.split("\\|", 7);
			auxid = auxtxt[0];
			auxnome = auxtxt[1];
			contato = auxtxt[2];
			fone = auxtxt[3];
			if(user.equals(auxnome) && tipo.equals(auxid)){
				sb.append("<TR>");
				sb.append("<TD id=reg" + count + ">" + count + "</TD>");
				sb.append("<TD>" + contato + "</TD>");
				sb.append("<TD>" + fone + "</TD>");
				//sb.append("<TD width=\"10px\"><button id=btnE" + count + "type=\"button\">Excluir</button></TD>");
				sb.append("<TD><a href=\"login?excluir=" + count + "\" class=\"btn btn-default  active\" role=\"button\">Excluir</a></TD>");
				sb.append("</TR>");
				count++;
			}				
		}
		sb.append("</Table>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");		
		return sb.toString();
	}
	
	//Confere User
	public static boolean confere(String[] args, String caminho, String nome, String user, String pwd, String tipo) throws IOException{
		Path path = Paths.get(caminho + nome);
		String auxtxt[];
		String auxnome;
		String auxid;
		String auxpwd;
		List<String> readAllLines = Files.readAllLines(path, Charset.defaultCharset());
		for (String linha : readAllLines){
			auxtxt = linha.split("\\|", 5);
			auxid = auxtxt[0];
			auxnome = auxtxt[1];
			auxpwd = auxtxt[2];
			if(auxnome.equals(user) && auxid.equals(tipo) && auxpwd.equals(pwd)){
				return true;
			}
		}
		return false;
	}
	
	public static void escrever(String caminho, String nome, String conteudo) throws IOException{
		Path path = Paths.get(caminho, nome);
		OpenOption modoAbertura = Files.exists(path) ? APPEND : CREATE;
		Files.write(path, conteudo.getBytes() , modoAbertura);
	}
	
	public static void apagar(String[] args, String caminho, String nome, String user, String tipo, int position) throws IOException{
		Path path = Paths.get(caminho + nome);
		String auxtxt[];
		String auxuser;
		String auxid = "";
		int CountA = 0;
		List<String> readAllLines = Files.readAllLines(path, Charset.defaultCharset());
		Files.delete(path);
		for (String linha : readAllLines){
			OpenOption modoAbertura = Files.exists(path) ? APPEND : CREATE;			
			auxtxt = linha.split("\\|", 5);
			auxid = auxtxt[0];
			auxuser = auxtxt[1];
			if(auxuser.equals(user) && auxid.equals(tipo)){
				if(position != CountA){
					linha = linha + "\r\n";
					Files.write(path, linha.getBytes(), modoAbertura);
				}
				CountA++;
			}else{
				linha = linha + "\r\n";
				Files.write(path, linha.getBytes(), modoAbertura);
			}
		}
	}
}
