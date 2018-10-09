package Team4;

//실수를 이진수로 변환한 정보를 담는 클래스
public class RealNumber {
	// IEEE754 표준 (32bits)
	public int[] exp = new int[8]; // 지수부 : 8bit
	public int[] frac = new int[23]; // 가수부 : 23bits
	public int MSB = 0; // 부호 비트
	public int[] resister = new int[32];
	
	public int exp_decimal;
	
	// 생성자
	public RealNumber() {
		this(0.0);
	}
	public RealNumber(int[] exp, int[]frac, int MSB) { // 지수비트, 가수비트, MSB를 직접 입력
		this.exp = exp;
		this.frac = frac; 
		this.MSB = MSB;
		resister[0] = MSB;
		System.arraycopy(exp, 0, resister, 1, exp.length);
		System.arraycopy(frac, 0, resister, 9, frac.length);
	}
	public RealNumber(double number) { // 숫자 대입
		resister = biReal(number);
	}
	
	// 배열 0으로 초기화
	public int[] initArr_zero(int[] arr) {
		for(int i=0;i<arr.length;i++) {
			arr[i] = 0;
		}
		return arr;
	}
	
	// 이진화 연산
	private int[] biReal(double number) {
		
		if(number == 0) {
			this.MSB = 0;
			this.exp_decimal = 0;
			this.exp = initArr_zero(this.exp);
			this.frac = initArr_zero(this.frac);
			this.resister = initArr_zero(this.resister);
			return this.resister;
		}
		
		int num_integer;  // 정수
		double num_decimal; // 소수
		
		// 2진수 표현을 위한 변수
		int[] integer; // 정수부
		int[] decimal; // 가수부
		int  integer_size = 0; // 정수부분 배열 크기
		
		int temp_int; // 임시 변수
		int temp_exp = 0;
		int bias; // 바이어스 
		
		if(number >= 0) { // 부호 게산
			MSB = 0;
		}
		else {
			MSB = 1;
		}
		
		System.out.println("비트화 할 실수 : " + number);
		System.out.println("부호 비트: " + MSB);
		
		number = Math.abs(number);
		
		
		num_integer = (int) Math.floor(number);
		System.out.println("정수 부분 : " + num_integer);
		
		num_decimal = number - num_integer;
		System.out.println("소수 부분 : " + num_decimal);
		
		// 소수 부분 연산

		decimal = bi_decimal(num_decimal);
		
		// 정수 부분 연산
		if(num_integer != 0) {
			
			temp_int = num_integer;
			while(temp_int != 0) { // 정수부분 배열 크기 계산
				integer_size++;
				temp_int/=2;
			}
		
			integer = new int[integer_size];
			for(int i=integer_size-1;i>=0;i--) { // 최하위 비트부터 연산
				if(num_integer%2==1) {
					integer[i]=1;
				}
				else {
					integer[i]=0;
				}
				num_integer/=2;
			}
			
			// test code
			System.out.println("정수 부분 이진화 : ");
			printArr(integer);
			System.out.println();
			
			bias = (integer_size - 1) + 127; // 127 바이어스 -> 지수
			exp_decimal = bias;
			// test code
			System.out.println("바이어스 : " + (integer_size - 1) + " + " + 127 + " = " + bias);
			
			// 지수 부분 
			Bias(exp_decimal);
			System.out.println("지수부분 비트화 : ");
			printArr(exp);
			System.out.println();
			
			
			System.arraycopy(integer, 1, frac, 0, integer.length-1); // 정규화로 인해 0번째는 포함안함
			System.arraycopy(decimal, 0, frac, integer.length-1, frac.length-(integer.length-1));
			
			System.out.println("가수부분 비트화(정규화) : ");
			printArr(frac);
			System.out.println();
		}
		else {
			for(int i=0;i<decimal.length;i++) {
				temp_exp ++;				
				if(decimal[i] == 1) {
					break;
				}
			}
			
			bias = (-temp_exp + 127);
			exp_decimal = bias;
			// test code
			System.out.println("바이어스 : " + (-temp_exp) + " + " + 127 + " = " + bias);

			Bias(bias);
			System.out.println("지수부분 비트화 : ");
			printArr(exp);
			System.out.println();
			
			
			System.arraycopy(decimal, temp_exp, frac, 0, decimal.length-temp_exp);
			
			System.out.println("가수부분 비트화(정규화) : ");
			printArr(frac);
			System.out.println();
			
		}
		
		// MSB, 지수부, 가수부 합치기
		resister = Bond_exp_frac(exp,frac,MSB);
		
		System.out.println("비트화 : ");
		printArr(resister);
		System.out.println();
	return resister;
	
	}
	// 소수부분의 이진화
	public int[] bi_decimal(double num_decimal) {
		
		int decimal_size = 23;
		int[] decimal = new int[decimal_size];
		
		for(int i=0;i<decimal_size-1;i++) {
			num_decimal *= 2;
			if(Math.floor(num_decimal * 2) == 0) {
				decimal[i] = 0;
				}
			if(Math.floor(num_decimal) == 1) {
				decimal[i] = 1;
				num_decimal -= 1;
			}
			
		}
		
		// test code
		System.out.println("소수 부분 이진화 : ");
		printArr(decimal);
		System.out.println();
		
		return decimal;
	}
	
	// 지수 부분 연산
	public int[] Bias(int bias) {
		for(int i=exp.length-1;i>=0;i--) {
				
				if(bias%2==1) {
					exp[i]=1;
				}
				else {
					exp[i]=0;
				}
				bias/=2;
			}
		
		return exp;
	}
	
	
	// 2진수 배열 출력 함수
	public void printArr(int[] arr) {
		for(int i=0;i<arr.length;i++) {
			System.out.print(arr[i]);
			if((i+1)%4==0) {
				System.out.print(" "); // 가독성을 위한 띄어쓰기
			}
		}
	}
	
	// 실수에서의 10진수 변환기
	public double biToReal() {
		double result;
		int S = MSB;
		int E = 0;
		double M = 1;
		S = this.resister[0];
		for(int i=7;i>=0;i--) {
			E += this.exp[i] * Math.pow(2, 7-i);
		}
		E -= 127;
		
		for(int i=0;i<23;i++) {
			M += this.frac[i] * Math.pow(2, -(i+1));
		}
		
		result = Math.pow(-1,S) * M * Math.pow(2, E);
		
		return result;
	}
	
	// 합치기
	public int[] Bond_exp_frac(int[] exp, int[] frac, int MSB) {
		int[] total = new int[32];
		total[0] = MSB;
		System.arraycopy(exp, 0, total, 1, 8);
		System.arraycopy(frac, 0, total, 9, 23);
		
		return total;
	}
	
	// 레지스터의 상태를 출력
	public void showInfo() {
		
		int count = 0;
		for(int i=0;i<frac.length;i++) {
				if(frac[i] == 1) {
					count++;
				}
			}
		// 예외 처리
		if(exp_decimal == 255) {
			if(count != 0) {
				System.out.println("예외처리 : NaN");
			}
			else {
				System.out.println("예외처리 : Overflow");
			}
		}
		else if((exp_decimal > 0) && (exp_decimal < 255)) {
			System.out.println("레지스터 정보 출력");
			System.out.print("부호 비트 : " + MSB + ", 지수 비트 : ");
			printArr(exp);
			System.out.print(" 가수 비트 : ");
			printArr(frac);
			System.out.println();
			System.out.println("십진수 : " + biToReal());
		}
		else if(exp_decimal == 0) {
			
			if(count == 0) {
				System.out.println("예외처리 : 0");
			}
			else {
				System.out.println("예외처리 : Underflow");
			}
		}
		
		
		
	}
	
	
}

