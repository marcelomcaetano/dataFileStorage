
Projeto desenvolvido para a cria��o de arquivos a partir das de informa��es extraidas de um Pojo simples.
Entende-se por Pojo simples (neste sen�rio)
	- N�o extede nenhuma classe;
	- Cont�m somente atributos do tipo String, primitivos e/ou seus respectivos Wrappers (int -> Integer, double -> Double, etc);
	
-= Funcionamento =-

Para a cria��o de um arquivo, por exmplo ".csv", dos dados extra�dos da seguinte entidade:

	public class Person(){
	
		private int id;
		
		private String name;
		
		// get and set
	}

1� passo:
	
	Criar uma classe que implementa a interface br.com.dfs.parse.ParseType
	Ex.: public class CSV implements ParseType { ... }
	
2� passo:

	Implementar os m�todos definidos pela interface.
	
	- public String createContent(String[] fields, List<String[]> values)
		Utilizado para criar o conte�do, de acordo com a regra definida, do arquivo que ser� gerado
		 * fields -> nome dos atributos(v�lidos) extra�dos da entiade;
		 * values -> valor de cada atributo extra�do da entidade
		 Obs. a ordem do conte�do dos dois atributos ser� apresentada de acordo com a ordem declarada na classe.
	
	- getExtensionFile() ...
		Utilizado para recuperar a exten��o do aquivo que ser� criado.
		Ex.: csv
		
3� passo

	Utiliza��o da classe br.com.dfs.parse.ParseFile para a cria��o do arquivo.
	Ex.: 
		ParseFile<Person> parseFile = new ParseFile<Person>(new CSV());
		OutputStream out = parseFile.parse(entity);


-= Funcionamento Interno =-

	br.com.dfs.parse.ParseFile
	Utilizando generic, � definido o tipo de entidade ao qual ser�o extraidas as informa��es referente ao(s) nome(s) do(s) atributo(s);
	No construtor � passado qual o tipo de formatador de conte�do ser� utilizado;
	No m�todo parseFile.parse(T object) � passado objeto criado.
	Obs. � poss�vel passar um lista de objetos a partir do m�todo de mesmo nome (sobrecarga de m�todo)
		Ex. parseFile.parse(List<T> object)

	No processamento do m�todo 'parse' � feito uma s�rie de verifica��es em rela��o a entidade passada e ao objeto passado (seja �nico ou uma lista).
	Quatro exe��es, em espec�fico, podem ser geradas caso as seguintes informa��es estiverem incorretas.
	
	- HasSuperClassException : caso a entidade informada possua uma super class; 
		Ex. public class Man extends Person{...}
	
	- NotHasAttributes : caso a entidade n�o possua nenhum atributo;
		Ex. public class Person{  public int getId(){...} }
	
	- NotHasValidAttributeType : caso a entidade possua somente atributos n�o permitidos.
		Ex. public class Person{  public String[] list; }
		
	- InvalidParameterException : caso a exten��o do arquivo n�o seja informada atrav�s do m�todos getExtensionFile() definido na interface ParseType
	
	Obs,: As tr�s primeiras regras s�o validades na classe br.com.dfs.utils.ValidateClass que faz usso massivo de 'reflection' para consequir extrair as informa��es da entidade informada e assim efetuar as regras definidas para o sen�rio proposto.
			
	O processamento dos m�todos � registrado utilizando o recurso da biblioteca java.util.logging.Logger sendo que uma mensagem � gerada para v�rios passos do processamento da cria��o do arquivo.
	

	




