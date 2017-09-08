
public class Test {
	public static void main(String args[]){
	//	A = 9 ;
	//	B = 7 ; 
	//	C = 4 ;
	//	D = 10 ;
	//	FOR 5 B += A ; A *= 2 ; FOR 10 A += B ; ENDFOR A += 2 ; ENDFOR
	//	PRINT A ;
	//	PRINT B ;
	//	PRINT C ;
		
		int A =  9;
		int B = 7; 
		int C = 4;
		int D = 10;
		
		for(int i = 0; i < 5; i++){
			B += A; 
			A *= 2;
			for(int j = 0; j < 10; j++){
				A += B;
			}
			A+=2;
		}
		
		
		System.out.println("A = "+ A);
		System.out.println("B = "+ B);
		System.out.println("C = "+ C);
	}
}
