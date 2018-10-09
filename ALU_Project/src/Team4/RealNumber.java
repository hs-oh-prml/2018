package Team4;

//�Ǽ��� �������� ��ȯ�� ������ ��� Ŭ����
public class RealNumber {
	// IEEE754 ǥ�� (32bits)
	public int[] exp = new int[8]; // ������ : 8bit
	public int[] frac = new int[23]; // ������ : 23bits
	public int MSB = 0; // ��ȣ ��Ʈ
	public int[] resister = new int[32];
	
	public int exp_decimal;
	
	// ������
	public RealNumber() {
		this(0.0);
	}
	public RealNumber(int[] exp, int[]frac, int MSB) { // ������Ʈ, ������Ʈ, MSB�� ���� �Է�
		this.exp = exp;
		this.frac = frac; 
		this.MSB = MSB;
		resister[0] = MSB;
		System.arraycopy(exp, 0, resister, 1, exp.length);
		System.arraycopy(frac, 0, resister, 9, frac.length);
	}
	public RealNumber(double number) { // ���� ����
		resister = biReal(number);
	}
	
	// �迭 0���� �ʱ�ȭ
	public int[] initArr_zero(int[] arr) {
		for(int i=0;i<arr.length;i++) {
			arr[i] = 0;
		}
		return arr;
	}
	
	// ����ȭ ����
	private int[] biReal(double number) {
		
		if(number == 0) {
			this.MSB = 0;
			this.exp_decimal = 0;
			this.exp = initArr_zero(this.exp);
			this.frac = initArr_zero(this.frac);
			this.resister = initArr_zero(this.resister);
			return this.resister;
		}
		
		int num_integer;  // ����
		double num_decimal; // �Ҽ�
		
		// 2���� ǥ���� ���� ����
		int[] integer; // ������
		int[] decimal; // ������
		int  integer_size = 0; // �����κ� �迭 ũ��
		
		int temp_int; // �ӽ� ����
		int temp_exp = 0;
		int bias; // ���̾ 
		
		if(number >= 0) { // ��ȣ �Ի�
			MSB = 0;
		}
		else {
			MSB = 1;
		}
		
		System.out.println("��Ʈȭ �� �Ǽ� : " + number);
		System.out.println("��ȣ ��Ʈ: " + MSB);
		
		number = Math.abs(number);
		
		
		num_integer = (int) Math.floor(number);
		System.out.println("���� �κ� : " + num_integer);
		
		num_decimal = number - num_integer;
		System.out.println("�Ҽ� �κ� : " + num_decimal);
		
		// �Ҽ� �κ� ����

		decimal = bi_decimal(num_decimal);
		
		// ���� �κ� ����
		if(num_integer != 0) {
			
			temp_int = num_integer;
			while(temp_int != 0) { // �����κ� �迭 ũ�� ���
				integer_size++;
				temp_int/=2;
			}
		
			integer = new int[integer_size];
			for(int i=integer_size-1;i>=0;i--) { // ������ ��Ʈ���� ����
				if(num_integer%2==1) {
					integer[i]=1;
				}
				else {
					integer[i]=0;
				}
				num_integer/=2;
			}
			
			// test code
			System.out.println("���� �κ� ����ȭ : ");
			printArr(integer);
			System.out.println();
			
			bias = (integer_size - 1) + 127; // 127 ���̾ -> ����
			exp_decimal = bias;
			// test code
			System.out.println("���̾ : " + (integer_size - 1) + " + " + 127 + " = " + bias);
			
			// ���� �κ� 
			Bias(exp_decimal);
			System.out.println("�����κ� ��Ʈȭ : ");
			printArr(exp);
			System.out.println();
			
			
			System.arraycopy(integer, 1, frac, 0, integer.length-1); // ����ȭ�� ���� 0��°�� ���Ծ���
			System.arraycopy(decimal, 0, frac, integer.length-1, frac.length-(integer.length-1));
			
			System.out.println("�����κ� ��Ʈȭ(����ȭ) : ");
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
			System.out.println("���̾ : " + (-temp_exp) + " + " + 127 + " = " + bias);

			Bias(bias);
			System.out.println("�����κ� ��Ʈȭ : ");
			printArr(exp);
			System.out.println();
			
			
			System.arraycopy(decimal, temp_exp, frac, 0, decimal.length-temp_exp);
			
			System.out.println("�����κ� ��Ʈȭ(����ȭ) : ");
			printArr(frac);
			System.out.println();
			
		}
		
		// MSB, ������, ������ ��ġ��
		resister = Bond_exp_frac(exp,frac,MSB);
		
		System.out.println("��Ʈȭ : ");
		printArr(resister);
		System.out.println();
	return resister;
	
	}
	// �Ҽ��κ��� ����ȭ
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
		System.out.println("�Ҽ� �κ� ����ȭ : ");
		printArr(decimal);
		System.out.println();
		
		return decimal;
	}
	
	// ���� �κ� ����
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
	
	
	// 2���� �迭 ��� �Լ�
	public void printArr(int[] arr) {
		for(int i=0;i<arr.length;i++) {
			System.out.print(arr[i]);
			if((i+1)%4==0) {
				System.out.print(" "); // �������� ���� ����
			}
		}
	}
	
	// �Ǽ������� 10���� ��ȯ��
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
	
	// ��ġ��
	public int[] Bond_exp_frac(int[] exp, int[] frac, int MSB) {
		int[] total = new int[32];
		total[0] = MSB;
		System.arraycopy(exp, 0, total, 1, 8);
		System.arraycopy(frac, 0, total, 9, 23);
		
		return total;
	}
	
	// ���������� ���¸� ���
	public void showInfo() {
		
		int count = 0;
		for(int i=0;i<frac.length;i++) {
				if(frac[i] == 1) {
					count++;
				}
			}
		// ���� ó��
		if(exp_decimal == 255) {
			if(count != 0) {
				System.out.println("����ó�� : NaN");
			}
			else {
				System.out.println("����ó�� : Overflow");
			}
		}
		else if((exp_decimal > 0) && (exp_decimal < 255)) {
			System.out.println("�������� ���� ���");
			System.out.print("��ȣ ��Ʈ : " + MSB + ", ���� ��Ʈ : ");
			printArr(exp);
			System.out.print(" ���� ��Ʈ : ");
			printArr(frac);
			System.out.println();
			System.out.println("������ : " + biToReal());
		}
		else if(exp_decimal == 0) {
			
			if(count == 0) {
				System.out.println("����ó�� : 0");
			}
			else {
				System.out.println("����ó�� : Underflow");
			}
		}
		
		
		
	}
	
	
}

