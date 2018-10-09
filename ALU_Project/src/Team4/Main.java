package Team4;

import java.util.Scanner;



public class Main {
	
	// ���� �÷���
	public static int c;
	public static int s;
	public static int z;
	public static int v;
	
	// ���� �÷��� ��� �Լ�
	public static void printFlag() {
		System.out.println( "c�÷���: " + c + " z�÷���: " + z + " s�÷���: " + s + " v�÷���: " + v);
		if(z == 1) {
			System.out.println("z�÷��״� 0�̹Ƿ� 0");
		}
		if(v == 1) {
			System.out.println("v�÷��װ� 1�̹Ƿ� �����÷ο�");
		}
	}
	
	// 2���� ��ȯ��
	public static int[] bi(long a, int bit) {
		int MSB; // �ֻ��� ��Ʈ : ���, ���� �Ǻ�
		int[] resister = new int[bit]; // n bit ��������
		if(a>=0) {
			MSB = 0; // ����� ��
		}
		else {
			MSB = 1; // ������ ��
			a+=1; // 2�� ���� ����� 1�� ���ؾ���
		}
		resister[0]=MSB;
		 
		a = Math.abs(a); // a�� ������ �������� ��ȯ
		for(int i=resister.length-1;i>0;i--) { // ������ ��Ʈ���� ����
			if(a%2==1) {
				resister[i]=1;
			}
			else {
				resister[i]=0;
			}
			
			// �����϶�, ���� ����
			if(MSB==1)
				resister[i] = resister[i] ^ 1;
			
			a/=2;
		}
		return resister;
	}
	
	// �������
	
	// ��
	public static int sum(int a, int b, int carry) {
		int result = a ^ b ^ carry;
		return result;
	}
	// ĳ��
	public static int carry(int a, int b, int carry_0) {
		int carry = (a & b) | ((a ^ b) & carry_0);
		return carry;
	}
	
	// ���� �����
	public static int[] adder(int[] resister1, int[] resister2, int MINUSFLAG) { // ���̳ʽ� �÷��׸� �z�� ���� ����
		int[] result = new int[resister1.length];
		int[] carry = new int[result.length+1]; // c_0 ����

		c = 0; // �ø���
		s = 0; // ��ȣ
		z = 0; // �����÷ο�		
		v = 0; // 0�÷���
		carry[result.length] = MINUSFLAG;
		
		for(int i=resister1.length-1;i>=0;i--) {
			result[i] = sum(resister1[i],(resister2[i] ^ MINUSFLAG),carry[i+1]);
			carry[i] = carry(resister1[i],(resister2[i] ^ MINUSFLAG), carry[i+1]);
			z |= result[i];
		}
		
		// �÷��� ����
		s = result[0];
		z = z ^ 1;
		c = carry[0];
		v = carry[0] ^ carry[1];
		
		return result;
	}
	
	// ������ ��ȯ��
	public static long decimal(int[] resister) {
		long result = 0; // �����߿� int���� �ִ밪�� ���
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
	
	// 2���� �迭 ��� �Լ�
	public static void printArr(int[] arr) {
		for(int i=0;i<arr.length;i++) {
			System.out.print(arr[i]);
			if((i+1)%4==0) {
				System.out.print(" "); // �������� ���� ����
			}
		}
	}
	
	// ���ϱ� *Booth�˰���
	public static int[] multiplier(int[] resister1, int[] resister2) {
		
		
		
		
		int q; // Q(-1)
		int n; // ��� 
		
		int[] M = resister1; // �ǽ¼�
		int[] Q = resister2; // �¼�	
		int[] A = new int[M.length]; 
		int[] result = new int[2*M.length]; // ��� ��������
		
		
		int iter = 1;
		
		
		// 0 �˻�
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
		
		//�ʱ� ����
		q = 0;
		for(int i=0;i<A.length;i++) {
			A[i] = 0;
		}
		n = A.length;
		
		// �ʱ���� ���
		System.out.println("�ʱ� ����");
		System.out.print("��� : " + n + " ");
		
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
			
			
			System.out.println(iter + "�ܰ�");
			iter++;
			
			System.out.print("��� : " + n + " ");
			if((Q[Q.length-1] == 0) && (q == 1)) {
				System.out.println("01�̹Ƿ� A <- A+M");				
				printArr(A);
				System.out.println();
				A = adder(A,M,0); // A <- A+M 
				printArr(A);
				System.out.println();
				System.out.println();
			}
			else if((Q[Q.length-1] == 1) && (q == 0)) {
				System.out.println("10�̹Ƿ� A <- A-M");
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
			System.out.println("A,Q,q ������ ����Ʈ ");
			
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
		System.out.print("��� : " + n + " ����");
		
		// ����� result �迭�� ����
		System.arraycopy(A, 0, result, 0, A.length);
		System.arraycopy(Q, 0, result, A.length, Q.length);
		
		System.out.print("\n��� : ");
		printArr(result);
		System.out.println(" ������: " + decimal(result));
		
		return result;
	}
	
	// ������ 
	public static int[] divider(int[] resister1, int[] resister2, int DIVIDEFLAG) {
		
		// 0 �˻�
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
		
		
		int n; // ��� 
		int aFlag;

		int sign1, sign2;
		
		
		sign1 = resister1[0];
		sign2 = resister2[0];
		
		int[] M = resister2; // ����
		int[] Q = resister1; // ������	
		int[] A = new int[Q.length]; 
		
		int iter = 1;
		
		//�ʱ� ����
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
		
		n = DIVIDEFLAG * Q.length; // �Ǽ��� ���������� ���� ������ ��Ȯ���� ���̱� ����
		
		if(DIVIDEFLAG == 2) {
			n--;
		}
		
		if( DIVIDEFLAG == 1) {
			System.out.print("��� : " + n + " ");
				
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
				System.out.println(iter + "�ܰ�");
			}
			
			iter++;
			
			A = Lshift(A,Q[0]);
			Q = Lshift(Q,0);
			
			if( DIVIDEFLAG == 1) {
				System.out.println("���� ����Ʈ");			
				System.out.print("��� : " + n + " ");
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
					System.out.println("A�� M�� ��ȣ�� �����Ƿ�, A <- A-M");
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
						System.out.println("A�� ��ȣ�� �ٲ�����Ƿ�, A�� �������� ����");
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
					System.out.println("A�� M�� ��ȣ�� ���� �ٸ��Ƿ�, A <- A+M");
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
						System.out.println("A�� ��ȣ�� �ٲ�����Ƿ�, A�� �������� ����");
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
			System.out.print("��� : " + n + " ����");		
		}
		
		// ������ �� 2�� ����
		if(sign1 != sign2 ) { 
			A = bi(-decimal(A),A.length);
			if( DIVIDEFLAG == 1) {
				System.out.println("1.2�� ����ȭ");
			}			
			Q = bi(-decimal(Q),Q.length);
		}

		if( DIVIDEFLAG == 1) {
			System.out.print("\n��� : ��: ");
			printArr(Q);
		}
	
		
		if( DIVIDEFLAG == 1) {
			System.out.print(", ������: ");
			printArr(A);
			System.out.println(" ������: ��: " + decimal(Q) + " , ������: "+ decimal(A));
		}
		
		return Q;
		
	}
	
	// ������  ����Ʈ ����
	public static int[] Rshift(int[] resister, int arr_0) {
		
		for(int i=resister.length-1;i>0;i--) {
			resister[i] = resister[i-1];
		}
		resister[0] = arr_0; // carry�� �ִ� ����Ʈ ����
		return resister;
	}
	
	// ���� ����Ʈ ����
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
		
		System.out.println("ALU�ùķ�����");
		while(true) {
			System.out.print("������ �Է��ϼ��� : ");
			a = scan.nextInt();
			System.out.println();
			System.out.print("������ �Է��ϼ��� : ");
			b = scan.nextInt();
			System.out.println();
			
			resister1 = bi(a, 32);
			resister2 = bi(b, 32);
			System.out.println("2������ ǥ���� ����");
			printArr(resister1);
			System.out.println();
			printArr(resister2);
			System.out.println();
			
			// ���ϱ�
			System.out.println("����: " + a + " + " + b);
			System.out.print("������ : ");
			printArr(adder(resister1,resister2,0));
			System.out.println(", ������ : " + decimal(adder(resister1,resister2,0)));
			printFlag();
			
			// ����
			System.out.println("����: " + a + " - " + b);
			System.out.print("������ : ");
			printArr(adder(resister1,resister2,1));
			System.out.println(", ������ : " + decimal(adder(resister1,resister2,1)));
			printFlag();
			
			
			
			// ���ϱ�
			System.out.println("����: " + a + " * " + b);
			System.out.println();
						
			resister1 = bi(a, 32); // ���� ������ ����� 2��° �迭�� ����Ǵ� ���� �߻�
			resister2 = bi(b, 32); // -> �Լ� ���� �� �迭 �ʱ�ȭ �ʿ�
			
			// ������
			System.out.println("������: " + a + " / " + b);
			divider(resister1,resister2,1);
			
//			System.out.println(); // ������������ ���� �߻�
//			printArr(resister1); // -> �迭 �ʱ�ȭ �ʿ�
//			System.out.println();
//			printArr(resister2);
			
			System.out.println("�����Ϸ��� 0, ����Ϸ��� 1");
			ans = scan.nextInt();
			if(ans == 0) {
				break;
			}
		}
		System.out.println("ALU�ùķ����͸� �����մϴ�.");
	}
	
	// ���� ����
	public static int[] exp_Modifier(int[] resister, int difference) { 
		// ����ȭ�� ���� ���� ���� �ǵ����� ���
		
		// +��ȣ��Ʈ +�ø���Ʈ
		int[] frac_ori = new int[26];
		// ������ 1
		frac_ori[0] = 0;
		frac_ori[1] = 0;
		frac_ori[2] = 1;
		
		System.arraycopy(resister, 0, frac_ori, 3, resister.length-1);
		
		System.out.println("�������͸� " + (difference) + "��ŭ ������ ����Ʈ �����Ѵ�.");
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
		
		
		// ������ ���� ���� ū������ ��������
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
		// ���� ����
		temp_result = adder(temp1,temp2,MINUSFLAG);
		

		
		if(MINUSFLAG == 1) { //  ����
			// temp_result = bi(-(int)decimal(temp_result),26);
			
			//System.out.println("2�� ���� ��");
			//printArr(temp_result);
			//System.out.println();
			
			if(temp_result[0] == 1) { // �����϶�, 2�� ����
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
		else { // ���ϱ�
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
	
	// �Ǽ� ���ϱ�
	public static RealNumber multiplier_Real(RealNumber resister1, RealNumber resister2) {
		
		RealNumber result = new RealNumber();
		
		// 0 �˻�
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
		
		// ������ ���� �ֻ��� ��Ʈ
		frac_temp1[0] = 0;
		frac_temp1[0] = 0;
		
		// ������ 1
		frac_temp1[1] = 1;
		frac_temp2[1] = 1;
		System.arraycopy(resister1.frac, 0, frac_temp1, 2, 23);
		System.arraycopy(resister2.frac, 0, frac_temp2, 2, 23);
		
		// ���� -> ���ϱ�
		frac_temp_multi = multiplier(frac_temp1,frac_temp2);
		// ���� -> ���ϱ�
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
//			System.out.println("���� +1");
//			System.out.println();
			System.arraycopy(frac_temp_multi, 3, result.frac, 0, 23);
			exp_sum++;
		}
		else {
//			System.out.println();
//			System.out.println("���� �״��");
//			System.out.println();
			System.arraycopy(frac_temp_multi, 4, result.frac, 0, 23); 
		}
		
		// result.frac = ;
		result.exp = result.Bias(exp_sum); // ���̾�� ���� ��Ʈȭ
		result.MSB = resister1.MSB ^ resister2.MSB; // ��ȣ XOR�� ��ȣ�� �ٸ��� 1, ������ 0
		result.resister = result.Bond_exp_frac(result.exp,result.frac,result.MSB);
		result.showInfo();
		return result;
	}
	
	// �Ǽ� ������ 
	public static RealNumber divider_Real(RealNumber resister1, RealNumber resister2) {
		
		// 0 �˻�
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
		
		// ������ ���� �ֻ��� ��Ʈ
		frac_temp1[0] = 0;
		frac_temp1[0] = 0;
		
		// ������ 1
		frac_temp1[1] = 1;
		frac_temp2[1] = 1;
		System.arraycopy(resister1.frac, 0, frac_temp1, 2, 23);
		System.arraycopy(resister2.frac, 0, frac_temp2, 2, 23);

		// ���� -> ������
		frac_temp_divide = divider(frac_temp1,frac_temp2,2);
		
		// ���� -> ����
		
		exp_gap = resister1.exp_decimal - resister2.exp_decimal + 127;
		
		while(true) { // ����ȭ
			if(frac_temp_divide[index] == 0) {
//				System.out.println("���� -1");
				exp_gap--;
				index++;
			}
			else
				break;
		}
		
		// ������ ���� �߻�
		System.arraycopy(frac_temp_divide,index+1,result.frac, 0, 23);
		
		result.exp = result.Bias(exp_gap); // ���̾�� ���� ��Ʈȭ
		result.MSB = resister1.MSB ^ resister2.MSB; // ��ȣ XOR�� ��ȣ�� �ٸ��� 1, ������ 0
		result.resister = result.Bond_exp_frac(result.exp,result.frac,result.MSB);

		return result;
	}
	
	public static void FPU_SIMULATOR() {
		Scanner scan = new Scanner(System.in);
		double a, b; // �Ǽ� 
		
		int ans;
		
		// ��� 
		RealNumber result = new RealNumber();
		System.out.println("FPU�ùķ�����");
		while(true) {
			// ù��° �Ǽ� �Է�
			a = scan.nextDouble();
			RealNumber real_a = new RealNumber(a);
			
			System.out.println("A �������� ����");
			real_a.showInfo();
			System.out.println();
			System.out.println();
			
			// �ι�° �Ǽ� �Է�
			b = scan.nextDouble();
			RealNumber real_b = new RealNumber(b);
	
			System.out.println("B �������� ����");
			real_b.showInfo();
			System.out.println();
			System.out.println();
			
			// ���� ����
			System.out.println("����: " + a + " + " + b);
			result = adder_RealNumber(real_a,real_b,0);
			
			System.out.println("��� ����");
			result.showInfo();
			System.out.println();
			System.out.println();
			
			// ���� ����
			System.out.println("����: " + a + " - " + b);
			result = adder_RealNumber(real_a,real_b,1);
			
			System.out.println("��� ����");
			result.showInfo();
			System.out.println();
			
			// ���� ����
			System.out.println("����: " + a + " * " + b);
			result = multiplier_Real(real_a,real_b);
			
			System.out.println("��� ����");
			result.showInfo();
			System.out.println();
			
			// ������ ����
			System.out.println("������: " + a + " / " + b);
			result = divider_Real(real_a,real_b);
			
			System.out.println("��� ����");
			result.showInfo();
			System.out.println();
			
			System.out.println("�����Ϸ��� 0, ����Ϸ��� 1");
			ans = scan.nextInt();
			if(ans == 0) {
				break;
			}
		}
		System.out.println("FPU�ùķ����͸� �����մϴ�.");
		
	}
	
	public static void main(String[] args) {
		
		int select_menu;
		int exit;
		Scanner scan = new Scanner(System.in);
		
		while(true){
			while(true) {
				System.out.println("ALU�ùķ�����: 1, FPU�ùķ�����: 2");
				select_menu = scan.nextInt();
				if((select_menu == 1)|| (select_menu == 2)) {
					break;
				}
				else {
					System.out.println("�߸��� �Է��Դϴ�.");
				}
			}
			
			
			if(select_menu == 1) {
				ALU_SIMULATOR();
			}
			else if(select_menu == 2) {
				FPU_SIMULATOR();
			}
			
			
			
			
			System.out.println("�����Ϸ��� 0, ����Ϸ��� 1");
			exit = scan.nextInt();
			if(exit == 0) {
				break;
			}
		}
		
	}

}
