// Scheduler Project
#pragma once
#include<stdio.h> // ǥ�� ����� ���
#include<time.h> // �ð� ���� ���
#include<windows.h> // 1.�۾� �� �ٲٱ� 2. cls
#include<conio.h> // Ŀ�� �̵�

typedef enum {
	SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}DAY;

typedef struct {
	int y; // ��
	int m; // ��
	int d; // ��
	DAY start_day; // ���� ��
}CALENDAR;

// �Լ� ����
void Eraser(int y); // ���찳 �Լ�
void gotoxy(int x, int y); // Ŀ�� ��ǥ �̵� �Լ�

void calendar(CALENDAR *, int years, int month, int date); // calendar �Լ�
int CountDay(int years, int month); // CountDay �Լ�
int LeafYears(int years); // LeafYears �Լ�
DAY Weekday(int years, int month); // ��¥�� ������ ��ġ �����ִ� �Լ�

void Sch_Add(int year, int month, int date); //���� �߰� �Լ�
void Sch_Del(int years, int month, int date); //�������� ����� ������ �����ϴ� �Լ�
void Sch_Read(int years, int month, int date); //����� �������� �о� ���� �Լ�


// main()�Լ� 
main() {

	system("title Team 10 : Schaduler"); // �ܼ� Ÿ��Ʋ
	system("mode con cols=57 lines=30"); // �ܼ� â ũ�� ����

	int years, month, date; // ��¥ ����
	int menu; // �޴� ����
	char ans; // ��� ����

	int c = 0; // ���� ���� ����
	int flag = 0; // �÷���

	time_t t = time(NULL); // time.h�� ���̺귯�� �Լ� ���
	struct tm tm = *localtime(&t);

	CALENDAR cal; // ����ü ����

	/* �׽�Ʈ �ڵ�
	printf("%d\n", t);
	printf("now: %d-%d-%d  %d : %d : %d \n\n",
		tm.tm_year + 1900, tm.tm_mon + 1, tm.tm_mday,
		tm.tm_hour, tm.tm_min, tm.tm_sec);
	*/

	// ���� ��¥ ����
	years = tm.tm_year + 1900;
	month = tm.tm_mon + 1;
	date = tm.tm_mday;

	// ����ü�� ��¥ ���� �Է�
	cal.y = years;
	cal.m = month;
	// cal.d = date;
	cal.start_day = Weekday(cal.y, cal.m);
	cal.d = CountDay(cal.y, cal.m);

	// ����� �Է� ����
	while (1) {
		
		// �⺻ ȭ�� ����
		calendar(&cal, years, month, date);

		/*
		gotoxy(0, 12);
		do {
			printf("��¥�� �Է��ϼ���: ");
			if (scanf("%d-%d-%d", &years, &month, &date) != 3)
				continue;
			if (years >= 0 && month >= 1 && month <= 12) {
				if (month == 2 && date >= 1 && date <= (28 + LeafYears(years))) {
					break;
				}
				else if (month == 4 || month == 6 || month == 9 || month == 11) {
					if (date >= 1 && date <= 30)
						break;
				}
				else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
					if (date >= 1 && date <= 31)
						break;
				}
			}
			printf("You are wrong,Please try again!\n");
		} while (1);
		*/

		// �߸��� �Է°��� ������ �ùٸ� �Է°��� ���������� �ݺ�
		gotoxy(0, 11); printf("��¥�� �Է��ϼ���: ");
		while (1) {
			gotoxy(19, 11); 
			if (!scanf("%d", &years)){
				gotoxy(0, 12); printf("���ڰ� �ƴմϴ�.");
				Sleep(1000);
				Eraser(12);
				Eraser(11);
				gotoxy(0, 11); printf("��¥�� �Է��ϼ���: ");
				while (c = getchar() != '\n' && c != EOF);
			}
			else {
				if (years >= 1900 && years <= 2100) break;
				else {
					gotoxy(0, 12); printf("1900��� 2100�� ���̸� �Է��ϼ���.");
					Sleep(1000);
					Eraser(12);
					Eraser(11);
					gotoxy(0, 11); printf("��¥�� �Է��ϼ���: ");
				}
			}
		}


		// �����϶�, �ƴҶ� ���� ���
		if (LeafYears(years)) {

			while (1) {
				gotoxy(23, 11); printf("   ");
				gotoxy(23, 11); printf("-");
				if (!scanf("%d", &month)) {
					gotoxy(0, 12); printf("���ڰ� �ƴմϴ�.");
					Sleep(1000);
					Eraser(12);
					Eraser(11);
					gotoxy(0, 11); printf("��¥�� �Է��ϼ���: %4d", years);
					while (c = getchar() != '\n' && c != EOF);
				}
				else {
					if (month >= 1 && month <= 12) break;
					else {
						gotoxy(0, 12); printf("1���� 12�� ���̸� �Է��ϼ���.");
						Sleep(1000);
						Eraser(12);
						Eraser(11);
						gotoxy(0, 11); printf("��¥�� �Է��ϼ���: %4d", years);
					}
				}
			}
			while (1) {
				gotoxy(26, 11); printf("   ");
				gotoxy(26, 11); printf("-");
				if (!scanf("%d", &date)) {
					gotoxy(0, 12); printf("���ڰ� �ƴմϴ�.");
					Sleep(1000);
					Eraser(12);
					Eraser(11);
					gotoxy(0, 11); printf("��¥�� �Է��ϼ���: %4d-%d", years, month);
					while (c = getchar() != '\n' && c != EOF);
				}
				else {
					if (month == 2) {
						if (date >= 1 && date <= (29)) break;
						else {
							gotoxy(0, 12); printf("1�ϰ� 29�� ���̸� �Է��ϼ���.");
							Sleep(1000);
							Eraser(12);
							Eraser(11);
							gotoxy(0, 11); printf("��¥�� �Է��ϼ���: %4d-%d", years, month);
						}
					}
					else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
						if (date >= 1 && date <= 30) break;
						else {
							gotoxy(0, 12); printf("1�ϰ� 30�� ���̸� �Է��ϼ���.");
							Sleep(1000);
							Eraser(12);
							Eraser(11);
							gotoxy(0, 11); printf("��¥�� �Է��ϼ���: %4d-%d", years, month);
						}
					}
					else if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)) {
						if (date >= 1 && date <= 31)break;
						else {
							gotoxy(0, 12); printf("1�ϰ� 31�� ���̸� �Է��ϼ���.");
							Sleep(1000);
							Eraser(12);
							Eraser(11);
							gotoxy(0, 11); printf("��¥�� �Է��ϼ���: %4d-%d", years, month);

						}
					}
					else {
						gotoxy(26, 11); printf("   ");
					}
				}
			}
		}
		else {
			while (1) {
				gotoxy(23, 11); printf("   ");
				gotoxy(23, 11); printf("-");
				if (!scanf("%d", &month)) {
					gotoxy(0, 12); printf("���ڰ� �ƴմϴ�.");
					Sleep(1000);
					Eraser(12);
					Eraser(11);
					gotoxy(0, 11); printf("��¥�� �Է��ϼ���: %4d", years);
					while (c = getchar() != '\n' && c != EOF);
				}
				else {
					if (month >= 1 && month <= 12) break;
					else {
						gotoxy(0, 12); printf("1���� 12�� ���̸� �Է��ϼ���.");
						Sleep(1000);
						Eraser(12);
						Eraser(11);
						gotoxy(0, 11); printf("��¥�� �Է��ϼ���: %4d", years);
					}
				}
			}
			while (1) {
				gotoxy(26, 11); printf("   ");
				gotoxy(26, 11); printf("-");
				if (!scanf("%d", &date)) {
					gotoxy(0, 12); printf("���ڰ� �ƴմϴ�.");
					Sleep(1000);
					Eraser(12);
					Eraser(11);
					gotoxy(0, 11); printf("��¥�� �Է��ϼ���: %4d-%d", years, month);
					while (c = getchar() != '\n' && c != EOF);
				}
				else {
					if (month == 2) {
						if (date >= 1 && date <= (28)) break;
						else {
							gotoxy(0, 12); printf("1�ϰ� 28�� ���̸� �Է��ϼ���.");
							Sleep(1000);
							Eraser(12);
							Eraser(11);
							gotoxy(0, 11); printf("��¥�� �Է��ϼ���: %4d-%d", years, month);
						}
					}
					else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
						if (date >= 1 && date <= 30) break;
						else {
							gotoxy(0, 12); printf("1�ϰ� 30�� ���̸� �Է��ϼ���.");
							Sleep(1000);
							Eraser(12);
							Eraser(11);
							gotoxy(0, 11); printf("��¥�� �Է��ϼ���: %4d-%d", years, month);
						}
					}
					else if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)) {
						if (date >= 1 && date <= 31)break;
						else {
							gotoxy(0, 12); printf("1�ϰ� 31�� ���̸� �Է��ϼ���.");
							Sleep(1000);
							Eraser(12);
							Eraser(11);
							gotoxy(0, 11); printf("��¥�� �Է��ϼ���: %4d-%d", years, month);

						}
					}
					else {
						gotoxy(26, 11); printf("   ");
					}
				}
			}
		}



		system("cls");

		cal.y = years;
		cal.m = month;
		cal.start_day = Weekday(cal.y, cal.m);
		cal.d = CountDay(cal.y, cal.m);
		calendar(&cal, years, month, date);

		// �޴�
		while (1) {

			gotoxy(0, 12);
			printf("1.�����߰� 2.�������� 3.������ȸ :");
			if (!scanf("%d", &menu)) {
				gotoxy(0, 13); printf("���ڰ� �ƴմϴ�.");
				Sleep(1000);
				Eraser(13);
				Eraser(12);
				while (c = getchar() != '\n' && c != EOF);
			}
			else {
				if (menu == 1 || menu == 2 || menu == 3)break;
				gotoxy(0, 13); printf("1,2,3 �� �ϳ��� �Է��ϼ���\n");
				Sleep(1000);
				Eraser(13);
				Eraser(12);
			}
		}
		switch (menu) {
		case 1:
			Sch_Add(years, month, date);
			break;
		case 2:
			Sch_Del(years, month, date);
			break;
		case 3:
			Sch_Read(years, month, date);
			break;
		}
		printf("Return : R, Exit : E\n");
		char ch = getchar();
		while (ch != 'R' && ch != 'r' && ch != 'e' && ch != 'E') {
			if (ch != '\n')
				printf("Return : R, Exit : E\n");
			ch = getchar();
		}

		if (ch == 'R' || ch == 'r') {
			system("cls");
			continue;
		}
		else {
			return;
		}
	}


	// ����� �Է� 
	/*
	gotoxy(0, 11); printf("��¥�� �Է��ϼ���: ");

	// �߸��� �Է°��� ������ �ùٸ� �Է°��� ���������� �ݺ�
	while (1) {
		
		gotoxy(19, 11); scanf("%d", &years);
		if (years > 1900 && years < 2100) break;
		else {
			Err(1);
		}
	}
	while (1) {
		gotoxy(23, 11); printf("-"); scanf("%d", &month);
		if (month >= 1 && month <= 12) break;
		else {
			gotoxy(23, 11); printf("   ");
		}
	}
	while (1) {
		gotoxy(26, 11); printf("-"); scanf("%d", &date);
		if (month == 2) {
			if (date >= 1 && date <= (28 + LeafYears(years))) break;
			else {
				gotoxy(26, 11); printf("   ");
			}
		}
		else if (month == 4 || month == 6 || month == 9 || month || 11) {
			if (date >= 1 && date <= 30) break;
			else {
				gotoxy(26, 11); printf("   ");
			}
		}
		else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			if (date >= 1 && date <= 31)break;
			else {
				gotoxy(26, 11); printf("   ");
			}
		}
		else {
			gotoxy(26, 11); printf("   ");
		}
	}

	system("cls");

	cal.y = years;
	cal.m = month;
	// cal.d = date;
	cal.start_day = Weekday(cal.y, cal.m);
	cal.d = CountDay(cal.y, cal.m);
	calendar(&cal, years, month, date);
	printf("%d-%d-%d\n\n", years, month, date);

	// �޴� �Է�
	gotoxy(5, 12);
	printf("Menu\n1.�����߰� 2.�������� 3.������ȸ :");
	while (1) {
		gotoxy(35, 12);
		scanf("%d", &menu);
		//if (menu == 1 || menu == 2 || menu == 3)break;
		if (menu == 1) {
			Sch_Add(years, month, date);
			break;
		}
		else if (menu == 2) {
			Sch_Del(years, month, date);
			break;
		}
		else if (menu == 3) {
			Sch_Read(years, month, date);
			break;
		}
		else {
			printf("�߸��� ���ڸ� �Է��ϼ̽��ϴ�.");
		}
	}
	*/



}

// ���찳 �Լ�
void Eraser(int y) {
	gotoxy(0, y);
	for (int i = 0; i < 57; i++) {
		gotoxy(i, y); printf(" ");
	}
}


// Ŀ�� ��ǥ �̵� �Լ�
void gotoxy(int x, int y)
{

	COORD Pos = { x , y - 1};

	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), Pos);

}

// �޷� ��� �Լ�
void calendar(CALENDAR *cal, int years, int month, int date) {
	int day_count = 1;
	int week_judge = SUNDAY;

	// ������(2018�� ����, �������� ���� �����ϰ� �������� ����)
	int hollyday[8][2] = {
		{1,1}, // ����
		{3,1}, // 3.1��
		{5,5}, // ��̳�
		{6,6}, // ������
		{8,15}, // ������
		{10,3}, // ��õ��
		{10,9}, // �ѱ۳�
		{12,25} // ��ź��
	};



	//���� �� �ٲ��ִ� �ڵ�
	// SetConsoleTextAttribute( GetStdHandle( STD_OUTPUT_HANDLE ), �ش��ȣ);
	// �⺻�� : 12, ������ : 4, �Ķ��� : 9, �ʷϻ� : 10



	//���� �����ؼ� ���� Ʋ ���
	gotoxy(24, 1); printf("%d", years);

	// �� ����
	switch (month) {
	case 1: gotoxy(23, 2); printf("January"); break;
	case 2: gotoxy(23, 2); printf("Febuary"); break;
	case 3: gotoxy(24, 2); printf("March"); break;
	case 4: gotoxy(24, 2); printf("April"); break;
	case 5: gotoxy(25, 2); printf("May"); break;
	case 6: gotoxy(25, 2); printf("June"); break;
	case 7: gotoxy(25, 2); printf("July"); break;
	case 8: gotoxy(24, 2); printf("August"); break;
	case 9:  gotoxy(22, 2); printf("September"); break;
	case 10: gotoxy(23, 2); printf("October"); break;
	case 11: gotoxy(23, 2); printf("November"); break;
	case 12: gotoxy(23, 2); printf("December"); break;
	}

	gotoxy(21, 3); printf("%04d.%02d.%02d", years, month, date);

	
	

	gotoxy(0, 4);
	printf("\n");
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 12);
	printf("SUN\t");
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 7);
	printf("MON\tTUE\tWED\tTHU\tFRI\t");
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 9);
	printf("SAT");
	printf("\n");

	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 7); //�⺻�� 

	// �޷� ���
	while (week_judge != cal->start_day) {
		printf("\t");
		week_judge++;
	}
	while (day_count <= cal->d) {


		// �۾��� ����



		if (day_count == date) {
			SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 10); // ���� ��¥ �ʷϻ�
		}

		else {
			if (week_judge == SUNDAY) {
				SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 12); // �Ͽ��� ������
			}
			else if (week_judge == SATURDAY) {
				SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 9); // ����� �Ķ���
			}
			else {
				SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 7); // �⺻�� 
			}
			// ������ ������
			for (int i = 0; i < 8; i++) {
				if (month == hollyday[i][0] && day_count == hollyday[i][1]) {
					SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 12);
				}
			}
		}




		printf("%2d \t", day_count);
		week_judge++;
		day_count++;

		if ((week_judge % 7) == 0) {
			putchar('\n');
			week_judge = 0;
		}
	}
	putchar('\n');
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 7);  // �⺻������ ���ư���
}

// ��¥�� ������ ��ġ �����ִ� �Լ�
DAY Weekday(int years, int month) {
	int count = 1;
	// 1�� 1�� 1�� �Ͽ��� ���� ���ñ��� �ϼ� ���
	long int total_day = (years - 1) + ((years - 1) / 4 - (years - 1) / 100 + (years - 1) / 400) + 1;

	// 365 % 7 = 1 -> �ذ� �ٲ� �� ���� ������ �и�

	DAY weekday;

	while (count != month) {
		total_day += CountDay(years, count);
		count++;
	}
	total_day %= 7;
	if (total_day == 0) {
		weekday = SUNDAY;
	}
	else if (total_day == 1) {
		weekday = MONDAY;
	}
	else if (total_day == 2) {
		weekday = TUESDAY;
	}
	else if (total_day == 3) {
		weekday = WEDNESDAY;
	}
	else if (total_day == 4) {
		weekday = THURSDAY;
	}
	else if (total_day == 5) {
		weekday = FRIDAY;
	}
	else if (total_day == 6) {
		weekday = SATURDAY;
	}
	return weekday;
}

// ���� ��� �Լ�
int LeafYears(int years) {

	// ���� ���
	// ������ ���� 
	// - 4�� ���������鼭 100���� ������ �������� �ʴ� ��
	// - 400���� ������ �������� ��
	// ������ ��� 2�� 29������

	if ((years % 4 == 0) && (years % 100 != 0) || (years % 400 == 0)) {
		return 1;
	}
	else {
		return 0;
	}
}



// ���� �� �� ����ϴ� �Լ�
int CountDay(int years, int month) {
	// �� ������ ���� ��
	// 1, 3, 5, 7, 8, 10, 12 : 31��
	// 4, 6, 9, 11 : 30��
	// 2 : 28�� (*������ ��� 29��)

	int days = 0;

	if (month == 2) {
		days = 28 + LeafYears(years);
	}
	else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
		days = 30;
	}
	else {
		days = 31;
	}
	return days;
}

//���� �߰� �Լ�
void Sch_Add(int year, int month, int date) {
	char file_path[255];
	sprintf(file_path, "schedule/%04d%02d%02d.txt", year, month, date);
	FILE* f = fopen(file_path, "r");
	if (f != NULL) {
		printf("������ �����մϴ�. ����ڽ��ϱ�?(y/n)");
		fclose(f);
		char ch = getchar();
		while (ch != 'Y' && ch != 'y' && ch != 'N' && ch != 'n') {
			if (ch != '\n')
				printf("file exists! do you want to override it?(y/n)");
			ch = getchar();
		}
		if (ch == 'N' || ch == 'n') {
			return;
		}
	}

	f = fopen(file_path, "w");
	if (f == NULL) {
		perror("������ �� �� �����ϴ�:");
		return;
	}
	printf("�޸� �Է��ϼ���(����: crtl + zŰ �Է� �� enter)\n");
	char memo[1024] = { 0 };
	for (int i = 0; i < 1024; i++) {
		int ch = getchar();
		if (ch != -1)
			memo[i] = ch;
		else
			break;
	}
	fputs(memo, f);
	fclose(f);
	printf("������ �߰��Ǿ����ϴ�.\n");
}

//�������� ����� ������ �����ϴ� �Լ�

void Sch_Del(int year, int month, int date) {
	char file_path[255];
	sprintf(file_path, "schedule/%04d%02d%02d.txt", year, month, date);
	FILE* f = fopen(file_path, "r");
	if (f != NULL) {
		printf("������ �����մϴ�.�����Ͻðڽ��ϱ�?(y/n)");
		fclose(f);
		char ch = getchar();
		while (ch != 'Y' && ch != 'y' && ch != 'N' && ch != 'n') {
			if (ch != '\n')
				printf("������ �����մϴ�.�����Ͻðڽ��ϱ�?(y/n)\n");
			ch = getchar();
		}
		if (ch == 'N' || ch == 'n')
			return;
		else {
			remove(file_path);
			printf("������ �����Ǿ����ϴ�.\n");
		}
	}
	else {
		printf("������ �������� �ʽ��ϴ�.\n");
	}
}


//����� �������� �о� ���� �Լ�

void Sch_Read(int year, int month, int date) {
	char file_path[255];
	sprintf(file_path, "schedule/%04d%02d%02d.txt", year, month, date);
	FILE* f = fopen(file_path, "r");
	if (f == NULL) {
		perror("������ �������� �ʽ��ϴ�..\n");
		return;
	}
	char memo[1024];
	printf("%04d.%02d.%02d ����:", year, month, date);
	while (fgets(memo, 1024, f)) {
		puts(memo);
	}
	fclose(f);
}



/*
void Sch_Del(int years, int month, int date) { 
	//int years = 10;
	//int month = 03;
	//int date = 01;
	char a[30], b[4], c[4];
	char txt[8] = ".txt";

	sprintf(a, "%d", years); //int ���� years �� char ���·� ����
	sprintf(b, "%d", month);
	sprintf(c, "%d", date);

	strcat(a, b); //a�� ��� ���ڿ� �ڿ� b�� ��� ���ڿ��� �ٿ��ֱ�
	strcat(a, c);
	strcat(a, txt);
	//printf("%s\n", a); ���ڿ��� ����� �پ����� ����غ���


	FILE *fp;

	fp = fopen(a, "r"); //����� ������ �ҷ�����

	if (fp == NULL) { //����� ������ ������ �����޼��� ���
		printf("��ġ�ϴ� ������ �����ϴ�.\n");
		return 0;
	}

	fclose(fp); //���� �б� ����


	int g = remove(a); //����� ������ ������ �� ������ ����
	printf("������ ���� �Ǿ����ϴ�.");
	if (g == 0) //������ ���Ű� ����� �Ǵ��� �˾ƺ��� ��
	{
		printf("����");
	}
	else if (g == -1) {
		perror("����");

	}
	return 0;


} */
/*
void Sch_Read(int years, int month, int date) { 
//	int years = 10; �� �����Ǵ��� ���� �׽�Ʈ���� ����� ������ ��
	//int month = 03;
	//int date = 01;
	char a[30], b[4], c[4];
	char txt[8] = ".txt";

	sprintf(a, "%d", years); //int ���� years �� char ���·� ����
	sprintf(b, "%d", month);
	sprintf(c, "%d", date);

	strcat(a, b); //a�� ��� ���ڿ� �ڿ� b�� ��� ���ڿ��� �ٿ��ֱ�
	strcat(a, c);
	strcat(a, txt);
	//printf("%s\n", a); ���ڿ��� ����� �پ����� ����غ���

	//FILE *fp;
	FILE *fp2;

	char f[1000];
	//fp = fopen("abc.txt", "w");
	//if (fp == NULL) {
	//	printf("��ġ�ϴ� ������ �����ϴ�.\n");
	//	return 1;
	//}
	//fprintf(fp, "example.");
	//fclose(fp);
	//printf("��");
	//return 0;



	fp2 = fopen(a, "r"); //����� ������ �ҷ�����

	if (fp2 == NULL) { //����� ������ ������ �����޼��� ���
		printf("��ġ�ϴ� ������ �����ϴ�.\n");
		return 0;
	}



	while (fgets(f, 1000, fp2) != NULL) { //���ϳ��� ������ ���
		printf("%s", f);
	}

	fclose(fp2); //���� �б� ����

	return 0;



} */



//���� ������ �������� test code
/*void Sch_Del(int year, int month, int date) {
	char file_path[255];
	sprintf(file_path, "schedule/%04d%02d%02d.txt", year, month, date);
	FILE* f = fopen(file_path, "r");
	if (f != NULL) {
		printf("file exists! do you want to remove it?(y/n)");
		fclose(f);
		char ch = getchar();
		while (ch != 'Y' && ch != 'y' && ch != 'N' && ch != 'n'){
			if (ch != '\n')
				printf("file exists! do you want to remove it?(y/n)");
			ch = getchar();
		}
		if (ch == 'N' || ch == 'n')
			return;
		else {
			remove(file_path);
		}
	}else {
		printf("file does not exist!");
	}
}*/
//���� ��ȸ�� �������� test code
/*void Sch_read(int year, int month, int date) {
	char file_path[255];
	sprintf(file_path, "schedule/%04d%02d%02d.txt", year, month, date);
	FILE* f = fopen(file_path, "r");
	if (f == NULL) {
		perror("cannot open the file:");
	}
	char memo[1024];
	printf("memo for %04d-%02d-%02d:", year, month, date);
	while (fgets(memo, 1024, f)) {
		puts(memo);
	}
	fclose(f);
}*/

