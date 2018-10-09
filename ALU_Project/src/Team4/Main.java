package Team4;

import java.util.Scanner;



public class Main {
	
	// 상태 플래그
	public static int c;
	public static int s;
	public static int z;
	public static int v;
	
	// 상태 플래그 출력 함수
	public static void printFlag() {
		System.out.println( "c플래그: " + c + " z플래그: " + z + " s플래그: " + s + " v플래그: " + v);
		if(z == 1) {
			System.out.println("z플래그는 0이므로 0");
		}
		if(v == 1) {
			System.out.println("v플래그가 1이므로 오버플로우");
		}
	}
	
	// 2진수 변환기
	public static int[] bi(long a, int bit) {
		int MSB; // 최상위 비트 : 양수, 음수 판별
		int[] resister = new int[bit]; // n bit 레지스터
		if(a>=0) {
			MSB = 0; // 양수일 때
		}
		else {
			MSB = 1; // 음수일 때
			a+=1; // 2의 보수 연산시 1을 더해야함
		}
		resister[0]=MSB;
		 
		a = Math.abs(a); // a의 절댓값을 이진수로 변환
		for(int i=resister.length-1;i>0;i--) { // 최하위 비트부터 연산
			if(a%2==1) {
				resister[i]=1;
			}
			else {
				resister[i]=0;
			}
			
			// 음수일때, 보수 연산
			if(MSB==1)
				resister[i] = resister[i] ^ 1;
			
			a/=2;
		}
		return resister;
	}
	
	// 전가산기
	
	// 합
	public static int sum(int a, int b, int carry) {
		int result = a ^ b ^ carry;
		return result;
	}
	// 캐리
	public static int carry(int a, int b, int carry_0) {
		int carry = (a & b) | ((a ^ b) & carry_0);
		return carry;
	}
	
	// 병렬 가산기
	public static int[] adder(int[] resister1, int[] resister2, int MINUSFLAG) { // 마이너스 플래그를 톧해 빼기 연산
		int[] result = new int[resister1.length];
		int[] carry = new int[result.length+1]; // c_0 포함

		c = 0; // 올림수
		s = 0; // 부호
		z = 0; // 오버플로우		
		v = 0; // 0플래그
		carry[result.length] = MINUSFLAG;
		
		for(int i=resister1.length-1;i>=0;i--) {
			result[i] = sum(resister1[i],(resister2[i] ^ MINUSFLAG),carry[i+1]);
			carry[i] = carry(resister1[i],(resister2[i] ^ MINUSFLAG), carry[i+1]);
			z |= result[i];
		}
		
		// 플래그 연산
		s = result[0];
		z = z ^ 1;
		c = carry[0];
		v = carry[0] ^ carry[1];
		
		return result;
	}
	
	// 십진수 변환기
	public static long decimal(int[] resister) {
		long result = 0; // 연산중에 int형의 최대값을 벗어남
		if(resister[0]==0) {
			for(int i=resister.length-1;i>0;i--) {
				result += resister[i] * (long)Math.pow(2,((resister.length-1)-i));
			}
		}
		else {
			result = -(long) Math.pow(2, (resister.length-1));
			for(int i=resister.length-1;i>0;i--) {
				result += (long)Math.pow(2, ((resister.length-1)-i)) * resister[i];
			}
		}
		return result;
	}
	
	// 2진수 배열 출력 함수
	public static void printArr(int[] arr) {
		for(int i=0;i<arr.length;i++) {
			System.out.print(arr[i]);
			if((i+1)%4==0) {
				System.out.print(" "); // 가독성을 위한 띄어쓰기
			}
		}
	}
	
	// 곱하기 *Booth알고리즘
	public static int[] multiplier(int[] resister1, int[] resister2) {
		
		
		
		
		int q; // Q(-1)
		int n; // 계수 
		
		int[] M = resister1; // 피승수
		int[] Q = resister2; // 승수	
		int[] A = new int[M.length]; 
		int[] result = new int[2*M.length]; // 결과 레지스터
		
		
		int iter = 1;
		
		
		// 0 검사
		int count1 = 0;
		int count2 = 0;
		for(int k=0;k<resister1.length;k++) {
			if(resister1[k] == 1) {
				count1++;
			}
		}
		for(int k=0;k<resister2.length;k++) {
			if(resister2[k] == 1) {
				count2++;
			}
		}
		if((count1 == 0)||(count2 == 0)) {
			for(int k=0;k<result.length;k++) {
				result[k] = 0;
			}
			return result;
		}
		
		//초기 상태
		q = 0;
		for(int i=0;i<A.length;i++) {
			A[i] = 0;
		}
		n = A.length;
		
		// 초기상태 출력
		System.out.println("초기 상태");
		System.out.print("계수 : " + n + " ");
		
		System.out.print("A: ");
		printArr(A);
		System.out.print("Q: ");
		printArr(Q);
		System.out.print(" q: " + q);
		System.out.print(" M: ");
		printArr(M);
		System.out.println();
		System.out.println();
		
		while(true){
			
			
			System.out.println(iter + "단계");
			iter++;
			
			System.out.print("계수 : " + n + " ");
			if((Q[Q.length-1] == 0) && (q == 1)) {
				System.out.println("01이므로 A <- A+M");				
				printArr(A);
				System.out.println();
				A = adder(A,M,0); // A <- A+M 
				printArr(A);
				System.out.println();
				System.out.println();
			}
			else if((Q[Q.length-1] == 1) && (q == 0)) {
				System.out.println("10이므로 A <- A-M");
				printArr(A);
				System.out.println();
				A = adder(A,M,1); // A <- A-M
				printArr(A);
				System.out.println();
				System.out.println();
			}
			else {
				
			}
			q = Q[Q.length-1];
			Q = Rshift(Q,A[A.length-1]);
			A = Rshift(A,A[0]);
			System.out.println("A,Q,q 오른쪽 시프트 ");
			
			System.out.print("A: ");
			printArr(A);
			System.out.print("Q: ");
			printArr(Q);
			System.out.print(" q: " + q);
			System.out.print(" M: ");
			printArr(M);
			System.out.println();
			System.out.println();
			
			--n;
			if(n==0) {
				break;
			}
		}
		System.out.print("계수 : " + n + " 종료");
		
		// 결과를 result 배열로 연결
		System.arraycopy(A, 0, result, 0, A.length);
		System.arraycopy(Q, 0, result, A.length, Q.length);
		
		System.out.print("\n결과 : ");
		printArr(result);
		System.out.println(" 십진수: " + decimal(result));
		
		return result;
	}
	
	// 나누기 
	public static int[] divider(int[] resister1, int[] resister2, int DIVIDEFLAG) {
		
		// 0 검사
		int count = 0;
		
		for(int k=0;k<resister2.length;k++) {					
			if(resister2[k] == 1) {
				count++;
			}
		}
			
		if(count == 0) {
			System.out.println("Error : Division by 0");
			return resister2;
		}
		
		
		int n; // 계수 
		int aFlag;

		int sign1, sign2;
		
		
		sign1 = resister1[0];
		sign2 = resister2[0];
		
		int[] M = resister2; // 젯수
		int[] Q = resister1; // 피젯수	
		int[] A = new int[Q.length]; 
		
		int iter = 1;
		
		//초기 상태
		if(Q[0] == 0) {
			for(int i=0;i<A.length;i++) {
				A[i] = 0;
			}
		}
		else {
			for(int i=0;i<A.length;i++) {
				A[i] = 1;
			}
		}
		
		n = DIVIDEFLAG * Q.length; // 실수의 나눗셈에서 가수 연산의 정확도를 높이기 위함
		
		if(DIVIDEFLAG == 2) {
			n--;
		}
		
		if( DIVIDEFLAG == 1) {
			System.out.print("계수 : " + n + " ");
				
			System.out.print("A: ");
			printArr(A);
			System.out.print("Q: ");
			printArr(Q);
			System.out.print(" M: ");
			printArr(M);
				
			System.out.println();
		}
			
		
		
		while(true) {
			
			if( DIVIDEFLAG == 1) {
				System.out.println(iter + "단계");
			}
			
			iter++;
			
			A = Lshift(A,Q[0]);
			Q = Lshift(Q,0);
			
			if( DIVIDEFLAG == 1) {
				System.out.println("왼쪽 쉬프트");			
				System.out.print("계수 : " + n + " ");
				System.out.print("A: ");
				printArr(A);
				System.out.print("Q: ");
				printArr(Q);
				System.out.print(" M: ");
				printArr(M);
				
				System.out.println();
			}

			if(A[0] == M[0]) {
				aFlag = A[0];
				A = adder(A,M,1);
				
				if( DIVIDEFLAG == 1) {
					System.out.println("A와 M의 부호가 같으므로, A <- A-M");
					printArr(A);
					System.out.println();
				}
				if((aFlag == A[0]) || (decimal(A) == 0)){
					Q[Q.length-1] = 1;
					
					if( DIVIDEFLAG == 1) {
						System.out.println("Q_0 = 1");
					}
				}
				if( (aFlag != A[0]) && (decimal(A) != 0) ) {
					Q[Q.length-1] = 0;
					A = adder(A,M,0);
					
					if( DIVIDEFLAG == 1) {
						System.out.println("Q_0 = 0");
						System.out.println("A와 부호가 바뀌었으므로, A의 원래값을 복구");
						printArr(A);
						System.out.println();
						System.out.println();
					}
				}
			}
			else { 
				aFlag = A[0];				
				A = adder(A,M,0);
				
				if( DIVIDEFLAG == 1) {
					System.out.println("A와 M의 부호가 서로 다르므로, A <- A+M");
					printArr(A);
				System.out.println();
				}
				
				if((aFlag == A[0]) || (decimal(A) == 0)){
					Q[Q.length-1] = 1;
					
					if( DIVIDEFLAG == 1) {
						System.out.println("Q_0 = 1");
					}					
				}
				if( (aFlag != A[0]) && (decimal(A) != 0) ) {
					Q[Q.length-1] = 0;
					A = adder(A,M,1);
					
					if( DIVIDEFLAG == 1) {
						System.out.println("Q_0 = 0");
						System.out.println("A와 부호가 바뀌었으므로, A의 원래값을 복구");
						printArr(A);
						System.out.println();
					}
				}
			}
			
			--n;
			if(n==0) {
				break;
			}
		}
		if( DIVIDEFLAG == 1) {
			System.out.print("계수 : " + n + " 종료");		
		}
		
		// 음수일 때 2의 보수
		if(sign1 != sign2 ) { 
			A = bi(-decimal(A),A.length);
			if( DIVIDEFLAG == 1) {
				System.out.println("1.2의 보수화");
			}			
			Q = bi(-decimal(Q),Q.length);
		}

		if( DIVIDEFLAG == 1) {
			System.out.print("\n결과 : 몫: ");
			printArr(Q);
		}
	
		
		if( DIVIDEFLAG == 1) {
			System.out.print(", 나머지: ");
			printArr(A);
			System.out.println(" 십진수: 몫: " + decimal(Q) + " , 나머지: "+ decimal(A));
		}
		
		return Q;
		
	}
	
	// 오른쪽  시프트 연산
	public static int[] Rshift(int[] resister, int arr_0) {
		
		for(int i=resister.length-1;i>0;i--) {
			resister[i] = resister[i-1];
		}
		resister[0] = arr_0; // carry가 있는 시프트 연산
		return resister;
	}
	
	// 왼쪽 시프트 연산
	public static int[] Lshift(int[] resister, int arr_0) {
		for(int i=0;i<resister.length-1;i++) {
			resister[i] = resister[i+1];
		}
		resister[resister.length-1] = arr_0; 
		return resister;
	}

	public static void ALU_SIMULATOR() {
		Scanner scan = new Scanner(System.in);
		long a, b, ans;
		
		int[] resister1;
		int[] resister2;
		
		System.out.println("ALU시뮬레이터");
		while(true) {
			System.out.print("정수를 입력하세요 : ");
			a = scan.nextInt();
			System.out.println();
			System.out.print("정수를 입력하세요 : ");
			b = scan.nextInt();
			System.out.println();
			
			resister1 = bi(a, 32);
			resister2 = bi(b, 32);
			System.out.println("2진수로 표현된 정수");
			printArr(resister1);
			System.out.println();
			printArr(resister2);
			System.out.println();
			
			// 더하기
			System.out.println("덧셈: " + a + " + " + b);
			System.out.print("이진수 : ");
			printArr(adder(resister1,resister2,0));
			System.out.println(", 십진수 : " + decimal(adder(resister1,resister2,0)));
			printFlag();
			
			// 빼기
			System.out.println("뺄셈: " + a + " - " + b);
			System.out.print("이진수 : ");
			printArr(adder(resister1,resister2,1));
			System.out.println(", 십진수 : " + decimal(adder(resister1,resister2,1)));
			printFlag();
			
			
			
			// 곱하기
			System.out.println("곱셈: " + a + " * " + b);
			System.out.println();
						
			resister1 = bi(a, 32); // 곱셈 연산후 결과가 2번째 배열에 저장되는 오류 발생
			resister2 = bi(b, 32); // -> 함수 실행 후 배열 초기화 필요
			
			// 나누기
			System.out.println("나눗셈: " + a + " / " + b);
			divider(resister1,resister2,1);
			
//			System.out.println(); // 나눗셈에서도 오류 발생
//			printArr(resister1); // -> 배열 초기화 필요
//			System.out.println();
//			printArr(resister2);
			
			System.out.println("종료하려면 0, 계속하려면 1");
			ans = scan.nextInt();
			if(ans == 0) {
				break;
			}
		}
		System.out.println("ALU시뮬레이터를 종료합니다.");
	}
	
	// 지수 조정
	public static int[] exp_Modifier(int[] resister, int difference) { 
		// 정규화된 수를 원래 수로 되돌려서 계산
		
		// +부호비트 +올림비트
		int[] frac_ori = new int[26];
		// 생략된 1
		frac_ori[0] = 0;
		frac_ori[1] = 0;
		frac_ori[2] = 1;
		
		System.arraycopy(resister, 0, frac_ori, 3, resister.length-1);
		
		System.out.println("레지스터를 " + (difference) + "만큼 오른쪽 시프트 연산한다.");
		for(int i=0;i<(difference);i++) {
			frac_ori = Rshift(frac_ori,0);
		}
		
		
		return frac_ori;
	}
	
	public static RealNumber adder_RealNumber(RealNumber resister1, RealNumber resister2, int MINUSFLAG) {
		
		int difference;
		
		int[] temp1 = new int[26];
		int[] temp2 = new int[26];
		int[] temp_result = new int[26];
		int temp_exp = 0;
		
		
		RealNumber result = new RealNumber();
		System.out.println();
		System.out.println();
		
		
		// 지수가 작은 쪽을 큰쪽으로 지수조정
		if(resister1.exp_decimal>resister2.exp_decimal) {
			temp_exp =  resister1.exp_decimal;
			difference = resister1.exp_decimal - resister2.exp_decimal;
			temp2 = exp_Modifier(resister2.frac, difference);
			
//			printArr(temp2);
//			System.out.println();
			
			temp1 = exp_Modifier(resister1.frac, 0);
			
//			printArr(temp1);
//			System.out.println();
			
			
			if(MINUSFLAG == 1) {
				result.MSB = 0;
			}
		}
		else if(resister1.exp_decimal<resister2.exp_decimal) {
			temp_exp =  resister2.exp_decimal;
			difference = resister2.exp_decimal - resister1.exp_decimal;
			temp1 = exp_Modifier(resister1.frac, difference);
			
//			printArr(temp1);
//			System.out.println();
			
			temp2 = exp_Modifier(resister2.frac, 0);
			
//			printArr(temp2);
//			System.out.println();
			
			if(MINUSFLAG == 1) {
				result.MSB = 1;
			}
		}
		else {
			temp_exp =  resister1.exp_decimal;
			temp1 = exp_Modifier(resister1.frac, 0);
			temp2 = exp_Modifier(resister2.frac, 0);
			
//			printArr(temp1);
//			System.out.println();
//			printArr(temp2);
//			System.out.println();
			result.MSB = 1;
		}
		
		result.exp_decimal = temp_exp;
		// 가수 연산
		temp_result = adder(temp1,temp2,MINUSFLAG);
		

		
		if(MINUSFLAG == 1) { //  빼기
			// temp_result = bi(-(int)decimal(temp_result),26);
			
			//System.out.println("2의 보수 후");
			//printArr(temp_result);
			//System.out.println();
			
			if(temp_result[0] == 1) { // 음수일때, 2의 보수
				temp_result = bi(-decimal(temp_result),26);
			}
			
			if(temp_result[1] == 0 && temp_result[2] == 0) {
					for(int i=1;i<temp_result.length;i++) {
					// temp_exp--;
					temp_result = Lshift(temp_result, 0);
					if(temp_result[i] == 1) {
						break;
					}
				}
				System.arraycopy(temp_result, 1, result.frac, 0, result.frac.length-1);
			}
			else {
				System.arraycopy(temp_result, 3, result.frac, 0, result.frac.length-1);
			}
		}
		else { // 더하기
			if(temp_result[1] == 1) {
				temp_exp++;
				System.arraycopy(temp_result, 2, result.frac, 0, result.frac.length-1);
			}	
			else {
				System.arraycopy(temp_result, 3, result.frac, 0, result.frac.length-1);
			}
		}
		
		
//		System.out.println("temp_result");
//		printArr(temp_result);
//		System.out.println();
//		
//		
//		
//		
//		
//		System.out.println("result.frac");
//		printArr(result.frac);
//		System.out.println();
		
		result.exp = result.Bias(temp_exp);
		
		result.resister = result.Bond_exp_frac(result.exp, result.frac, result.MSB);
		
		return result;
	}
	
	// 실수 곱하기
	public static RealNumber multiplier_Real(RealNumber resister1, RealNumber resister2) {
		
		RealNumber result = new RealNumber();
		
		// 0 검사
		int count1 = 0;
		int count2 = 0;
		if((resister1.exp_decimal == 0) || (resister2.exp_decimal == 0)) {
			
			for(int i=0;i<resister1.frac.length;i++) {
				if(resister2.frac[i] == 1) {
					count1++;
				}
			}
			for(int i=0;i<resister2.frac.length;i++) {
				if(resister2.frac[i] == 1) {
					count2++;
				}
			}
			if((count1 == 0)||(count2 == 0)) {
				return result;
			}
		}
		
		int exp_sum;
		
		int[] frac_temp1 = new int[25];
		int[] frac_temp2 = new int[25];
		int[] frac_temp_multi;
		
		// 연산을 위한 최상위 비트
		frac_temp1[0] = 0;
		frac_temp1[0] = 0;
		
		// 숨겨진 1
		frac_temp1[1] = 1;
		frac_temp2[1] = 1;
		System.arraycopy(resister1.frac, 0, frac_temp1, 2, 23);
		System.arraycopy(resister2.frac, 0, frac_temp2, 2, 23);
		
		// 가수 -> 곱하기
		frac_temp_multi = multiplier(frac_temp1,frac_temp2);
		// 지수 -> 더하기
		exp_sum = resister1.exp_decimal+resister2.exp_decimal-127;
		
		
//		System.out.println("frac_temp1");
//		printArr(frac_temp1);
//		System.out.println();
//		System.out.println("frac_temp2");
//		printArr(frac_temp2);
//		System.out.println();
//		
//		
//		System.out.println(resister1.exp_decimal);
//		System.out.println(resister2.exp_decimal);
//		System.out.println(resister1.exp_decimal+resister2.exp_decimal-127);
//
//		
//		System.out.println("frac_temp_multi");
//		printArr(frac_temp_multi);
//		System.out.println();
		
		if(frac_temp_multi[2] == 1) {
//			System.out.println();
//			System.out.println("지수 +1");
//			System.out.println();
			System.arraycopy(frac_temp_multi, 3, result.frac, 0, 23);
			exp_sum++;
		}
		else {
//			System.out.println();
//			System.out.println("지수 그대로");
//			System.out.println();
			System.arraycopy(frac_temp_multi, 4, result.frac, 0, 23); 
		}
		
		// result.frac = ;
		result.exp = result.Bias(exp_sum); // 바이어스한 지수 비트화
		result.MSB = resister1.MSB ^ resister2.MSB; // 부호 XOR로 부호가 다르면 1, 같으면 0
		result.resister = result.Bond_exp_frac(result.exp,result.frac,result.MSB);
		result.showInfo();
		return result;
	}
	
	// 실수 나누기 
	public static RealNumber divider_Real(RealNumber resister1, RealNumber resister2) {
		
		// 0 검사
		int count = 0;
		if(resister2.exp_decimal == 0) {
			for(int i=0;i<resister2.frac.length;i++) {
				if(resister2.frac[i] == 1) {
					count++;
				}
			}
			if(count == 0) {
				System.out.println("Error : Division by 0");
				return resister2;
			}
		}
		
		RealNumber result = new RealNumber();
		
		int exp_gap;
		int index = 0;
		
		int[] frac_temp1 = new int[25];
		int[] frac_temp2 = new int[25];
		int[] frac_temp_divide;
		
		// 연산을 위한 최상위 비트
		frac_temp1[0] = 0;
		frac_temp1[0] = 0;
		
		// 숨겨진 1
		frac_temp1[1] = 1;
		frac_temp2[1] = 1;
		System.arraycopy(resister1.frac, 0, frac_temp1, 2, 23);
		System.arraycopy(resister2.frac, 0, frac_temp2, 2, 23);

		// 가수 -> 나누기
		frac_temp_divide = divider(frac_temp1,frac_temp2,2);
		
		// 지수 -> 뺴기
		
		exp_gap = resister1.exp_decimal - resister2.exp_decimal + 127;
		
		while(true) { // 정규화
			if(frac_temp_divide[index] == 0) {
//				System.out.println("지수 -1");
				exp_gap--;
				index++;
			}
			else
				break;
		}
		
		// 나눗셈 오차 발생
		System.arraycopy(frac_temp_divide,index+1,result.frac, 0, 23);
		
		result.exp = result.Bias(exp_gap); // 바이어스한 지수 비트화
		result.MSB = resister1.MSB ^ resister2.MSB; // 부호 XOR로 부호가 다르면 1, 같으면 0
		result.resister = result.Bond_exp_frac(result.exp,result.frac,result.MSB);

		return result;
	}
	
	public static void FPU_SIMULATOR() {
		Scanner scan = new Scanner(System.in);
		double a, b; // 실수 
		
		int ans;
		
		// 결과 
		RealNumber result = new RealNumber();
		System.out.println("FPU시뮬레이터");
		while(true) {
			// 첫번째 실수 입력
			a = scan.nextDouble();
			RealNumber real_a = new RealNumber(a);
			
			System.out.println("A 레지스터 정보");
			real_a.showInfo();
			System.out.println();
			System.out.println();
			
			// 두번째 실수 입력
			b = scan.nextDouble();
			RealNumber real_b = new RealNumber(b);
	
			System.out.println("B 레지스터 정보");
			real_b.showInfo();
			System.out.println();
			System.out.println();
			
			// 덧셈 연산
			System.out.println("덧셈: " + a + " + " + b);
			result = adder_RealNumber(real_a,real_b,0);
			
			System.out.println("결과 정보");
			result.showInfo();
			System.out.println();
			System.out.println();
			
			// 뺄셈 연산
			System.out.println("뺄셈: " + a + " - " + b);
			result = adder_RealNumber(real_a,real_b,1);
			
			System.out.println("결과 정보");
			result.showInfo();
			System.out.println();
			
			// 곱셈 연산
			System.out.println("곱셈: " + a + " * " + b);
			result = multiplier_Real(real_a,real_b);
			
			System.out.println("결과 정보");
			result.showInfo();
			System.out.println();
			
			// 나눗셈 연산
			System.out.println("나눗셈: " + a + " / " + b);
			result = divider_Real(real_a,real_b);
			
			System.out.println("결과 정보");
			result.showInfo();
			System.out.println();
			
			System.out.println("종료하려면 0, 계속하려면 1");
			ans = scan.nextInt();
			if(ans == 0) {
				break;
			}
		}
		System.out.println("FPU시뮬레이터를 종료합니다.");
		
	}
	
	public static void main(String[] args) {
		
		int select_menu;
		int exit;
		Scanner scan = new Scanner(System.in);
		
		while(true){
			while(true) {
				System.out.println("ALU시뮬레이터: 1, FPU시뮬레이터: 2");
				select_menu = scan.nextInt();
				if((select_menu == 1)|| (select_menu == 2)) {
					break;
				}
				else {
					System.out.println("잘못된 입력입니다.");
				}
			}
			
			
			if(select_menu == 1) {
				ALU_SIMULATOR();
			}
			else if(select_menu == 2) {
				FPU_SIMULATOR();
			}
			
			
			
			
			System.out.println("종료하려면 0, 계속하려면 1");
			exit = scan.nextInt();
			if(exit == 0) {
				break;
			}
		}
		
	}

}
