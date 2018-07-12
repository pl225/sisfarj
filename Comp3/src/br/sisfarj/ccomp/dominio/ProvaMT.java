package br.sisfarj.ccomp.dominio;

public class ProvaMT {
	
	public enum NomeProva {
		L_50, L_100, L_200, L_400, L_800, L_1500, C_50, C_100, C_200, P_50, P_100,
		P_200, B_50, B_100, B_200, M_200, M_400;

		@Override
		public String toString() {
			String s[] =  super.toString().split("_");
			return s[1] + s[0];
		}
				
	}
	
	public enum Categoria {
		MASCULINO, FEMININO
	}
	
	public enum Classe {
		MIRIM, MIRIM_I_II, PETIZ_I_II, 
		INFANTIL_I_II, JUVENIL_I_II, JUNIOR_I_II, 
		SENIOR, MASTER, ABSOLUTO, INCULADO
	}
		
}
