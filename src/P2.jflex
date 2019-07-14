import java.io.*;
%%
%class P2
%unicode
%line
%column
%eofval{
   return 0;
%eofval}
%type Object

%{
String last_s;
int last_i;
String last;
boolean or_det = false;
static final int GOES=1100;
static final int SEMI=1200;
static final int OR=1300;
static final int EPSILON=1400;
static final int EOL=1500;
%}
%type int
%%
[a-z]	{//System.out.print(yytext()); 
		last_s = yytext(); last_i = (int)last_s.charAt(0); return(last_i);}
[A-Z]	{//System.out.print(yytext()); 
		last_s = yytext(); last_i = (int)last_s.charAt(0); return(last_i);}
"-->"	{//System.out.print(yytext()); 
		last = "GOES"; return(GOES);}
";"		{//System.out.print(yytext()); 
		last = "SEMI"; return(SEMI);}
"|"		{//System.out.print(yytext()); 
		last = "OR"; return(OR);}
\"[^\"]*\"							{;}
.									{;}	
[\n]	{System.out.print("EOL"); return(EOL);}	
//Scape sequence






/* Self-defined tokens */
/*
static final int A_TOKEN=1;
static final int B_TOKEN=2;
static final int C_TOKEN=3;
static final int D_TOKEN=4;
static final int E_TOKEN=5;
static final int F_TOKEN=6;
static final int G_TOKEN=7;
static final int H_TOKEN=8;
static final int I_TOKEN=9;
static final int J_TOKEN=10;
static final int K_TOKEN=11;
static final int L_TOKEN=12;
static final int M_TOKEN=13;
static final int O_TOKEN=14;
static final int P_TOKEN=15;
static final int Q_TOKEN=16;
static final int R_TOKEN=17;
static final int S_TOKEN=18;
static final int T_TOKEN=19;
static final int U_TOKEN=20;
static final int V_TOKEN=21;
static final int W_TOKEN=22;
static final int X_TOKEN=23;
static final int Y_TOKEN=24;
static final int Z_TOKEN=25;
*/
/* Self-defined tokens */
/*
static final int a_token=26;
static final int b_token=27;
static final int c_token=28;
static final int d_token=29;
static final int e_token=30;
static final int f_token=31;
static final int g_token=32;
static final int h_token=33;
static final int i_token=34;
static final int j_token=35;
static final int k_token=36;
static final int l_token=37;
static final int m_token=38;
static final int o_token=39;
static final int p_token=40;
static final int q_token=41;
static final int r_token=42;
static final int s_token=43;
static final int t_token=44;
static final int u_token=45;
static final int v_token=46;
static final int w_token=47;
static final int x_token=48;
static final int y_token=49;
static final int z_token=50;*/