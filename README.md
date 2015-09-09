
Projeto desenvolvido para a criação de arquivos a partir das de informações extraidas de um Pojo simples.

Entende-se por Pojo simples (neste senário)

	- Não extede nenhuma classe;
	- Contém somente atributos do tipo String, primitivos e/ou seus respectivos Wrappers (int -> Integer, double -> Double, etc);
	
-= Funcionamento =-

Para a criação de um arquivo, por exmplo ".csv", dos dados extraídos da seguinte entidade:

	public class Person(){
	
		private int id;
		
		private String name;
		
		// get and set
	}

1º passo:
	
	Criar uma classe que implementa a interface br.com.dfs.parse.ParseType
	Ex.: public class CSV implements ParseType { ... }
	
2º passo:

	Implementar os métodos definidos pela interface.
	
	- public String createContent(String[] fields, List<String[]> values)
		Utilizado para criar o conteúdo, de acordo com a regra definida, do arquivo que será gerado
		 * fields -> nome dos atributos(válidos) extraídos da entiade;
		 * values -> valor de cada atributo extraído da entidade
		 Obs. a ordem do conteúdo dos dois atributos será apresentada de acordo com a ordem declarada na classe.
	
	- getExtensionFile() ...
		Utilizado para recuperar a extenção do aquivo que será criado.
		Ex.: csv
		
3º passo

	Utilização da classe br.com.dfs.parse.ParseFile para a criação do arquivo.
	Ex.: 
		ParseFile<Person> parseFile = new ParseFile<Person>(new CSV());
		OutputStream out = parseFile.parse(entity);


-= Funcionamento Interno =-

	br.com.dfs.parse.ParseFile
	Utilizando generic, é definido o tipo de entidade ao qual serão extraidas as informações referente ao(s) nome(s) do(s) atributo(s);
	No construtor é passado qual o tipo de formatador de conteúdo será utilizado;
	No método parseFile.parse(T object) é passado objeto criado.
	Obs. É possível passar um lista de objetos a partir do método de mesmo nome (sobrecarga de método)
		Ex. parseFile.parse(List<T> object)

	No processamento do método 'parse' é feito uma série de verificações em relação a entidade passada e ao objeto passado (seja único ou uma lista).
	Quatro exeções, em específico, podem ser geradas caso as seguintes informações estiverem incorretas.
	
	- HasSuperClassException : caso a entidade informada possua uma super class; 
		Ex. public class Man extends Person{...}
	
	- NotHasAttributes : caso a entidade não possua nenhum atributo;
		Ex. public class Person{  public int getId(){...} }
	
	- NotHasValidAttributeType : caso a entidade possua somente atributos não permitidos.
		Ex. public class Person{  public String[] list; }
		
	- InvalidParameterException : caso a extenção do arquivo não seja informada através do métodos getExtensionFile() definido na interface ParseType
	
	Obs,: As três primeiras regras são validades na classe br.com.dfs.utils.ValidateClass que faz usso massivo de 'reflection' para consequir extrair as informações da entidade informada e assim efetuar as regras definidas para o senário proposto.
			
	O processamento dos métodos é registrado utilizando o recurso da biblioteca java.util.logging.Logger sendo que uma mensagem é gerada para vários passos do processamento da criação do arquivo.
	

	




