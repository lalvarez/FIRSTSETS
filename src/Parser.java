import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser
{
   static int lookahead = 0;
   static char print;
   static int error_found = 0;
   static P2 lexer = null;
   static ArrayList<Integer> grammar = new ArrayList<Integer>();
   static ArrayList<Integer> grammarEpsilon = new ArrayList<Integer>();
   static HashMap< Integer, ArrayList<Integer> > first = new HashMap< Integer,ArrayList<Integer>>();
   static boolean modified = false;
   public static void main(String [] args) throws IOException
  
   {
       lexer = new P2(new FileReader(args[0]));
       lookahead = lexer.yylex();
	   System.out.println(lookahead);
       error_found = 0;
       prod_list();
	   System.out.println("error" + error_found);
	   System.out.println(lookahead);
       if ((error_found == 0) && (lookahead == 0)){
			System.out.println("String accepted");
			System.out.println(grammar);
			}
	   
       else{
			System.out.println("String rejected");
			return;}
		
		addEpsilon();
		System.out.println(grammarEpsilon);
		printGrammar();
		createMap();
		modified = true;
		System.out.println("First sets");
		while (modified== true) {get_firsts();}
		printMap();
   }
     private static void error(String where) {
      System.out.println("Error found in function " + where + " on line " );
      error_found = 1;
   }
   private static void match() throws IOException
   {
		grammar.add(lookahead);
		print = (char) lookahead;
		lookahead = lexer.yylex();
		return;
   }
   private static void prod_list() throws IOException
   {
       production(); prod_list_2();
   }
   private static void production() throws IOException
   {
           if (lookahead >= 65 && lookahead <= 90) {
			   match();
			   if(lookahead == P2.GOES){
		       match();
		       production_body();
			   if(lookahead == P2.SEMI){
				  match();
				  if(lookahead == P2.EOL){
					  match();
					 return;
					}
				}
			}
		}
		
			else error("production");
			return;
   }
   private static void prod_list_2() throws IOException
   {
	   if(lookahead >= 65 && lookahead <= 90){
			production();
			prod_list_2();
			return;
		}
		else{
			return;
		}
   }
   private static void production_body() throws IOException
   {
       rule();
	   production_body_2();
	   return;
   }
   private static void production_body_2() throws IOException
   {
       if(lookahead == P2.EPSILON){
		   System.out.println("epsilon");
			match();
			return;
	   }
	    if(lookahead == P2.OR){
			System.out.println("or");
			match();
			rule();
			production_body_2();
			return;
		}
		 if(lookahead == P2.SEMI){
			return;
		}
       else error("production_body_2");
   }
   private static void rule() throws IOException
   {
       if (lookahead >= 65 && lookahead <= 90) { match(); rule();} 
       else if (lookahead >= 97 && lookahead <= 122) { match(); rule(); } 
       else if ((lookahead == P2.EOL) ) {match(); return;}
       else error("rule");
   }
   private static void addEpsilon() {
	   int item, epsilon = 100000, size = grammar.size(), j =0;
     for(int i =0; i < size; i++){
		 item = grammar.get(i);
		 grammarEpsilon.add(j,item);
		 if (item == 1300){
			 if(grammar.get(i+1) == 1500){
				 if(grammar.get(i+2) == 1200 || grammar.get(i+2) == 1300){
					epsilon = j;
				 }
			 }
		 }
		 if (epsilon == j){
			 j = j +1;
			 grammarEpsilon.add(j,1400);
		 }
		 j++;
	 }
   
   }
    private static void printGrammar() {
		int item, size = grammarEpsilon.size(), j =0;
		char item_c;
		for(int i =0; i < size; i++){
			 item = grammarEpsilon.get(i);
			 if(item == 1100) System.out.print(" -->");
			 else if(item == 1200) System.out.print(";");
			 else if(item == 1300) System.out.print("   |");
			 else if(item == 1400) System.out.print(" ");
			 else if(item == 1500) System.out.print("\n");
			 else{
				 item_c = (char)item;
				 System.out.print(" " + item_c);
			 }
	 }
		System.out.println();
   }
   private static void createMap(){
	   int item, size = grammarEpsilon.size(), j =0;
	   for(int i =0; i < size; i++){
		 item = grammarEpsilon.get(i);
		 if(item == 1100) {
			 first.put(grammarEpsilon.get(i -1),null);
			 j++;
		 }
	   }
   }
   private static void printMap(){
	   char x, y;
	   int k;
	   for (int i : first.keySet()) {
			x = (char) i;
			System.out.print(x + ": {");
			if(first.get(i) != null){
			for(int j : first.get(i) ){
				if(j == 1400)
					System.out.print(" epsilon");
				else{
				y = (char) j;
				System.out.print(" " +y);}
			}}
			else{
				System.out.print("empty" );
			}
			System.out.print(" }");
			System.out.println();
	   }
   }
	private static void get_firsts(){
	   int item, nt, next_in_prod,j = 0,size = grammarEpsilon.size();
	   boolean primero = true;
	   modified = false;
	   for(int i =0; i < size; i++){
		 item = grammarEpsilon.get(i);
		 if(item == 1100) {
			 //If GOES, save nt 
			 nt = grammarEpsilon.get(i-1);
			 i++; //Analyse first element of the production
			 next_in_prod = grammarEpsilon.get(i);
			 while(next_in_prod != 1200){
				 if(next_in_prod == 1300){
					 //OR
					 i++;next_in_prod = grammarEpsilon.get(i);
					 primero = true;
				 }
				 else if (next_in_prod >= 65 && next_in_prod <= 90){
					//NO TERMINAL
					 if(primero ==true){
						primero = false;
						addToList2(nt, first.get(next_in_prod));
						if (first.get(next_in_prod) != null){
							if(first.get(next_in_prod).contains(1400)){
							primero = true;
						}}
					}
					i++;next_in_prod = grammarEpsilon.get(i);
					
				 }
				 else if(next_in_prod == 1400){
					 //EPSILON 
					 if (grammarEpsilon.get(i+1)== 1500) 
						 addToList(nt, next_in_prod);
					 i++;next_in_prod = grammarEpsilon.get(i);
					 primero = false;
				 }
				 else if (next_in_prod >= 97 && next_in_prod <= 122){
					//TERMINAL 
					if(primero == true){
						//if is the first terminal in the left side of a production
						//we add it to the first set list
						addToList(nt, next_in_prod);
						primero = false;
					}
					i++;next_in_prod = grammarEpsilon.get(i);
					
				 }
				 else{
					 i++;next_in_prod = grammarEpsilon.get(i);
				 }
			 }
			 primero = true;
			}
		}
		return;
	}
	
	public static void addToList(int mapKey, int first_value) {
		ArrayList<Integer> first_list = first.get(mapKey);
		if(first_list == null) {
			 first_list = new ArrayList<Integer>();
			 first_list.add(first_value);
			 modified =true;
			 first.put(mapKey, first_list);
		} else {
			if(!first_list.contains(first_value)){ first_list.add(first_value);modified =true;}
		}
		return;
	}
	public static void addToList2(int mapKey, ArrayList<Integer> first_values) { 
		ArrayList<Integer> t_list = first.get(mapKey);
		if(first_values != null){
			if(t_list == null) {
				t_list = new ArrayList<Integer>();
				for( int j = 0; j <first_values.size();j++){
				if(!t_list.contains(first_values.get(j))){ 
				modified =true;
				t_list.add(first_values.get(j));
				}
			}
				first.put(mapKey, t_list);
		}
			else {
			for( int j =0; j <first_values.size();j++){
			if(!t_list.contains(first_values.get(j))){ 
			modified = true;
			t_list.add(first_values.get(j));
			}
			}
		}
		}
	}
}
