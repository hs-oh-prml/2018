// Scheduler Project
#pragma once
#include<stdio.h> // 표준 입출력 헤더
#include<time.h> // 시간 정보 헤더
#include<windows.h> // 1.글씨 색 바꾸기 2. cls
#include<conio.h> // 커서 이동

typedef enum {
	SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}DAY;

typedef struct {
	int y; // 년
	int m; // 월
	int d; // 일
	DAY start_day; // 시작 일
}CALENDAR;

// 함수 원형
void Eraser(int y); // 지우개 함수
void gotoxy(int x, int y); // 커서 좌표 이동 함수

void calendar(CALENDAR *, int years, int month, int date); // calendar 함수
int CountDay(int years, int month); // CountDay 함수
int LeafYears(int years); // LeafYears 함수
DAY Weekday(int years, int month); // 날짜와 요일을 매치 시켜주는 함수

void Sch_Add(int year, int month, int date); //일정 추가 함수
void Sch_Del(int years, int month, int date); //스케쥴이 저장된 파일을 제거하는 함수
void Sch_Read(int years, int month, int date); //저장된 스케쥴을 읽어 오는 함수


// main()함수 
main() {

	system("title Team 10 : Schaduler"); // 콘솔 타이틀
	system("mode con cols=57 lines=30"); // 콘솔 창 크기 조절

	int years, month, date; // 날짜 변수
	int menu; // 메뉴 변수
	char ans; // 대답 변수

	int c = 0; // 버퍼 비우기 변수
	int flag = 0; // 플래그

	time_t t = time(NULL); // time.h의 라이브러리 함수 사용
	struct tm tm = *localtime(&t);

	CALENDAR cal; // 구조체 선언

	/* 테스트 코드
	printf("%d\n", t);
	printf("now: %d-%d-%d  %d : %d : %d \n\n",
		tm.tm_year + 1900, tm.tm_mon + 1, tm.tm_mday,
		tm.tm_hour, tm.tm_min, tm.tm_sec);
	*/

	// 오늘 날짜 저장
	years = tm.tm_year + 1900;
	month = tm.tm_mon + 1;
	date = tm.tm_mday;

	// 구조체에 날짜 정보 입력
	cal.y = years;
	cal.m = month;
	// cal.d = date;
	cal.start_day = Weekday(cal.y, cal.m);
	cal.d = CountDay(cal.y, cal.m);

	// 사용자 입력 시작
	while (1) {
		
		// 기본 화면 세팅
		calendar(&cal, years, month, date);

		/*
		gotoxy(0, 12);
		do {
			printf("날짜를 입력하세요: ");
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

		// 잘못된 입력값을 넣으면 올바른 입력값을 넣을때까지 반복
		gotoxy(0, 11); printf("날짜를 입력하세요: ");
		while (1) {
			gotoxy(19, 11); 
			if (!scanf("%d", &years)){
				gotoxy(0, 12); printf("숫자가 아닙니다.");
				Sleep(1000);
				Eraser(12);
				Eraser(11);
				gotoxy(0, 11); printf("날짜를 입력하세요: ");
				while (c = getchar() != '\n' && c != EOF);
			}
			else {
				if (years >= 1900 && years <= 2100) break;
				else {
					gotoxy(0, 12); printf("1900년과 2100년 사이를 입력하세요.");
					Sleep(1000);
					Eraser(12);
					Eraser(11);
					gotoxy(0, 11); printf("날짜를 입력하세요: ");
				}
			}
		}


		// 윤년일때, 아닐때 따로 계산
		if (LeafYears(years)) {

			while (1) {
				gotoxy(23, 11); printf("   ");
				gotoxy(23, 11); printf("-");
				if (!scanf("%d", &month)) {
					gotoxy(0, 12); printf("숫자가 아닙니다.");
					Sleep(1000);
					Eraser(12);
					Eraser(11);
					gotoxy(0, 11); printf("날짜를 입력하세요: %4d", years);
					while (c = getchar() != '\n' && c != EOF);
				}
				else {
					if (month >= 1 && month <= 12) break;
					else {
						gotoxy(0, 12); printf("1월과 12월 사이를 입력하세요.");
						Sleep(1000);
						Eraser(12);
						Eraser(11);
						gotoxy(0, 11); printf("날짜를 입력하세요: %4d", years);
					}
				}
			}
			while (1) {
				gotoxy(26, 11); printf("   ");
				gotoxy(26, 11); printf("-");
				if (!scanf("%d", &date)) {
					gotoxy(0, 12); printf("숫자가 아닙니다.");
					Sleep(1000);
					Eraser(12);
					Eraser(11);
					gotoxy(0, 11); printf("날짜를 입력하세요: %4d-%d", years, month);
					while (c = getchar() != '\n' && c != EOF);
				}
				else {
					if (month == 2) {
						if (date >= 1 && date <= (29)) break;
						else {
							gotoxy(0, 12); printf("1일과 29일 사이를 입력하세요.");
							Sleep(1000);
							Eraser(12);
							Eraser(11);
							gotoxy(0, 11); printf("날짜를 입력하세요: %4d-%d", years, month);
						}
					}
					else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
						if (date >= 1 && date <= 30) break;
						else {
							gotoxy(0, 12); printf("1일과 30일 사이를 입력하세요.");
							Sleep(1000);
							Eraser(12);
							Eraser(11);
							gotoxy(0, 11); printf("날짜를 입력하세요: %4d-%d", years, month);
						}
					}
					else if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)) {
						if (date >= 1 && date <= 31)break;
						else {
							gotoxy(0, 12); printf("1일과 31일 사이를 입력하세요.");
							Sleep(1000);
							Eraser(12);
							Eraser(11);
							gotoxy(0, 11); printf("날짜를 입력하세요: %4d-%d", years, month);

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
					gotoxy(0, 12); printf("숫자가 아닙니다.");
					Sleep(1000);
					Eraser(12);
					Eraser(11);
					gotoxy(0, 11); printf("날짜를 입력하세요: %4d", years);
					while (c = getchar() != '\n' && c != EOF);
				}
				else {
					if (month >= 1 && month <= 12) break;
					else {
						gotoxy(0, 12); printf("1월과 12월 사이를 입력하세요.");
						Sleep(1000);
						Eraser(12);
						Eraser(11);
						gotoxy(0, 11); printf("날짜를 입력하세요: %4d", years);
					}
				}
			}
			while (1) {
				gotoxy(26, 11); printf("   ");
				gotoxy(26, 11); printf("-");
				if (!scanf("%d", &date)) {
					gotoxy(0, 12); printf("숫자가 아닙니다.");
					Sleep(1000);
					Eraser(12);
					Eraser(11);
					gotoxy(0, 11); printf("날짜를 입력하세요: %4d-%d", years, month);
					while (c = getchar() != '\n' && c != EOF);
				}
				else {
					if (month == 2) {
						if (date >= 1 && date <= (28)) break;
						else {
							gotoxy(0, 12); printf("1일과 28일 사이를 입력하세요.");
							Sleep(1000);
							Eraser(12);
							Eraser(11);
							gotoxy(0, 11); printf("날짜를 입력하세요: %4d-%d", years, month);
						}
					}
					else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
						if (date >= 1 && date <= 30) break;
						else {
							gotoxy(0, 12); printf("1일과 30일 사이를 입력하세요.");
							Sleep(1000);
							Eraser(12);
							Eraser(11);
							gotoxy(0, 11); printf("날짜를 입력하세요: %4d-%d", years, month);
						}
					}
					else if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)) {
						if (date >= 1 && date <= 31)break;
						else {
							gotoxy(0, 12); printf("1일과 31일 사이를 입력하세요.");
							Sleep(1000);
							Eraser(12);
							Eraser(11);
							gotoxy(0, 11); printf("날짜를 입력하세요: %4d-%d", years, month);

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

		// 메뉴
		while (1) {

			gotoxy(0, 12);
			printf("1.일정추가 2.일정삭제 3.일정조회 :");
			if (!scanf("%d", &menu)) {
				gotoxy(0, 13); printf("숫자가 아닙니다.");
				Sleep(1000);
				Eraser(13);
				Eraser(12);
				while (c = getchar() != '\n' && c != EOF);
			}
			else {
				if (menu == 1 || menu == 2 || menu == 3)break;
				gotoxy(0, 13); printf("1,2,3 중 하나를 입력하세요\n");
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


	// 사용자 입력 
	/*
	gotoxy(0, 11); printf("날짜를 입력하세요: ");

	// 잘못된 입력값을 넣으면 올바른 입력값을 넣을때까지 반복
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

	// 메뉴 입력
	gotoxy(5, 12);
	printf("Menu\n1.일정추가 2.일정삭제 3.일정조회 :");
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
			printf("잘못된 숫자를 입력하셨습니다.");
		}
	}
	*/



}

// 지우개 함수
void Eraser(int y) {
	gotoxy(0, y);
	for (int i = 0; i < 57; i++) {
		gotoxy(i, y); printf(" ");
	}
}


// 커서 좌표 이동 함수
void gotoxy(int x, int y)
{

	COORD Pos = { x , y - 1};

	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), Pos);

}

// 달력 출력 함수
void calendar(CALENDAR *cal, int years, int month, int date) {
	int day_count = 1;
	int week_judge = SUNDAY;

	// 공휴일(2018년 기준, 음력으로 세는 공휴일과 선거일은 제외)
	int hollyday[8][2] = {
		{1,1}, // 신정
		{3,1}, // 3.1절
		{5,5}, // 어린이날
		{6,6}, // 현충일
		{8,15}, // 광복절
		{10,3}, // 개천절
		{10,9}, // 한글날
		{12,25} // 성탄절
	};



	//글자 색 바꿔주는 코드
	// SetConsoleTextAttribute( GetStdHandle( STD_OUTPUT_HANDLE ), 해당번호);
	// 기본색 : 12, 빨간색 : 4, 파란색 : 9, 초록색 : 10



	//색깔 적용해서 요일 틀 출력
	gotoxy(24, 1); printf("%d", years);

	// 월 정리
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

	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 7); //기본색 

	// 달력 출력
	while (week_judge != cal->start_day) {
		printf("\t");
		week_judge++;
	}
	while (day_count <= cal->d) {


		// 글씨색 설정



		if (day_count == date) {
			SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 10); // 선택 날짜 초록색
		}

		else {
			if (week_judge == SUNDAY) {
				SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 12); // 일요일 빨간색
			}
			else if (week_judge == SATURDAY) {
				SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 9); // 토요일 파란색
			}
			else {
				SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 7); // 기본색 
			}
			// 공휴일 빨간색
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
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 7);  // 기본색으로 돌아가기
}

// 날짜와 요일을 매치 시켜주는 함수
DAY Weekday(int years, int month) {
	int count = 1;
	// 1년 1월 1일 일요일 부터 오늘까지 일수 계산
	long int total_day = (years - 1) + ((years - 1) / 4 - (years - 1) / 100 + (years - 1) / 400) + 1;

	// 365 % 7 = 1 -> 해가 바뀔 때 마다 요일이 밀림

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

// 윤년 계산 함수
int LeafYears(int years) {

	// 윤년 계산
	// 윤년의 조건 
	// - 4로 나누어지면서 100으로 나누어 떨어지지 않는 년
	// - 400으로 나누어 떨어지는 년
	// 윤년일 경우 2월 29일존재

	if ((years % 4 == 0) && (years % 100 != 0) || (years % 400 == 0)) {
		return 1;
	}
	else {
		return 0;
	}
}



// 월의 일 수 계산하는 함수
int CountDay(int years, int month) {
	// 각 월별로 일의 수
	// 1, 3, 5, 7, 8, 10, 12 : 31일
	// 4, 6, 9, 11 : 30일
	// 2 : 28일 (*윤년일 경우 29일)

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

//일정 추가 함수
void Sch_Add(int year, int month, int date) {
	char file_path[255];
	sprintf(file_path, "schedule/%04d%02d%02d.txt", year, month, date);
	FILE* f = fopen(file_path, "r");
	if (f != NULL) {
		printf("파일이 존재합니다. 덮어쓰겠습니까?(y/n)");
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
		perror("파일을 열 수 없습니다:");
		return;
	}
	printf("메모를 입력하세요(종료: crtl + z키 입력 후 enter)\n");
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
	printf("일정이 추가되었습니다.\n");
}

//스케쥴이 저장된 파일을 제거하는 함수

void Sch_Del(int year, int month, int date) {
	char file_path[255];
	sprintf(file_path, "schedule/%04d%02d%02d.txt", year, month, date);
	FILE* f = fopen(file_path, "r");
	if (f != NULL) {
		printf("파일이 존재합니다.삭제하시겠습니까?(y/n)");
		fclose(f);
		char ch = getchar();
		while (ch != 'Y' && ch != 'y' && ch != 'N' && ch != 'n') {
			if (ch != '\n')
				printf("파일이 존재합니다.삭제하시겠습니까?(y/n)\n");
			ch = getchar();
		}
		if (ch == 'N' || ch == 'n')
			return;
		else {
			remove(file_path);
			printf("파일이 삭제되었습니다.\n");
		}
	}
	else {
		printf("파일이 존재하지 않습니다.\n");
	}
}


//저장된 스케쥴을 읽어 오는 함수

void Sch_Read(int year, int month, int date) {
	char file_path[255];
	sprintf(file_path, "schedule/%04d%02d%02d.txt", year, month, date);
	FILE* f = fopen(file_path, "r");
	if (f == NULL) {
		perror("파일이 존재하지 않습니다..\n");
		return;
	}
	char memo[1024];
	printf("%04d.%02d.%02d 일정:", year, month, date);
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

	sprintf(a, "%d", years); //int 형인 years 를 char 형태로 변경
	sprintf(b, "%d", month);
	sprintf(c, "%d", date);

	strcat(a, b); //a가 담긴 문자열 뒤에 b가 담긴 문자열을 붙여주기
	strcat(a, c);
	strcat(a, txt);
	//printf("%s\n", a); 문자열이 제대로 붙었는지 출력해보기


	FILE *fp;

	fp = fopen(a, "r"); //저장된 파일을 불러들임

	if (fp == NULL) { //저장된 파일이 없으면 오류메세지 출력
		printf("일치하는 파일이 없습니다.\n");
		return 0;
	}

	fclose(fp); //파일 읽기 종료


	int g = remove(a); //저장된 파일이 있으니 그 파일을 제거
	printf("파일이 삭제 되었습니다.");
	if (g == 0) //파일이 제거가 제대로 되는지 알아보는 곳
	{
		printf("성공");
	}
	else if (g == -1) {
		perror("실패");

	}
	return 0;


} */
/*
void Sch_Read(int years, int month, int date) { 
//	int years = 10; 잘 구동되는지 유닛 테스트에서 사용한 임의의 값
	//int month = 03;
	//int date = 01;
	char a[30], b[4], c[4];
	char txt[8] = ".txt";

	sprintf(a, "%d", years); //int 형인 years 를 char 형태로 변경
	sprintf(b, "%d", month);
	sprintf(c, "%d", date);

	strcat(a, b); //a가 담긴 문자열 뒤에 b가 담긴 문자열을 붙여주기
	strcat(a, c);
	strcat(a, txt);
	//printf("%s\n", a); 문자열이 제대로 붙었는지 출력해보기

	//FILE *fp;
	FILE *fp2;

	char f[1000];
	//fp = fopen("abc.txt", "w");
	//if (fp == NULL) {
	//	printf("일치하는 파일이 없습니다.\n");
	//	return 1;
	//}
	//fprintf(fp, "example.");
	//fclose(fp);
	//printf("끝");
	//return 0;



	fp2 = fopen(a, "r"); //저장된 파일을 불러들임

	if (fp2 == NULL) { //저장된 파일이 없으면 오류메세지 출력
		printf("일치하는 파일이 없습니다.\n");
		return 0;
	}



	while (fgets(f, 1000, fp2) != NULL) { //파일내의 내용을 출력
		printf("%s", f);
	}

	fclose(fp2); //파일 읽기 종료

	return 0;



} */



//일정 삭제를 가능함의 test code
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
//일정 조회를 가능함의 test code
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

