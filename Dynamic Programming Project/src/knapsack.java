import java.util.Scanner;
import java.util.Vector;

public class knapsack {

	public static int[][] solve(int[] profits, int[] weights, int W, int n) {// הפונקציה שפותרת את הבעיה

		int[][] dp = new int[W + 1][W + 3]; // טבלת העזר לשלב הנוכחי , שורות - מצבים - כמה מקום נשאר לי בתחילת השלב,
											// עמודות - משתני החלטה - כמה לקחת מכל סוג של חבילה בשלב זה. עמודה לפני
											// האחרונה איקס כוכב , עמודה אחרונה אף כוכב

		if (n >= profits.length)// תנאי עצירה - החזרת טבלת אפסים של שלב פיקטיבי
			return dp;

		int profit = profits[n]; // שמירת המשקל וכמות המסכות לשלב זה
		int weight = weights[n];
		int[][] next = solve(profits, weights, W, n + 1);// שמירת הטבלה של השלב הבא

		for (int i = 0; i < W + 1; i++) {
			for (int j = 0; j < W + 3; j++) {

				if (n == 0 && i < W) // אם אנחנו בשלב הראשון נרצה למלא רק את השורה האחרונה בטבלה
					break;

				if (j == W + 1) {// אם הגענו לעמודה לפני האחרונה, של האיקס האופטימלי
					int max = maxIndex(dp, i);// מצא את האינדקס של העמודה בה התקבל ערך מקסימלי בשורה
					dp[i][j] = max;// בעמודת איקס כוכב בצע השמה לאינדקס שמצאת
					dp[i][j + 1] = dp[i][max];// בעמודת אף כוכב בצע השמה לערך המקסימלי באינדקס שמצאת
					break;
				} // if

				else if (i == 0 || j == 0)// אם השורה או העמודה אפס, קח אפס בשלב זה וסכום עם הפתרון האופטימלי המתאים
											// למצבך הנוכחי מהטבלה של שלב הבא

					dp[i][j] = 0 + next[i][W + 2];

				else if (weight <= i) {// אם המקום שנשאר לך מספיק עבור משקל החבילה
					if (j * weight <= i)// בדוק האם יש לי מספיק מקום בשביל משקל החבילה*כמה חבילות שבחרת לקחת
						dp[i][j] = j * profit + next[i - j * weight][W + 2];// בשלב הבא מגיעים עם פחות מקום
				} // else if

				else
					dp[i][j] = 0;// אם אין מספיק מקום בתיק
			} // for-j
		} // for-i

		return dp;
	}// solve function

	public static int maxIndex(int[][] table, int row) {// פונקציה אשר מחפשת בטבלה נתונה ובשורה נתונה ערך מקסימלי,
														// ושומרת את האינדקס של העמודה שלו
		int max = 0;
		for (int j = 1; j < table[row].length; j++) {
			if (table[row][j] > table[row][max])
				max = j;
		}
		return max;
	}// max index

	public static int maxSol(int[][] table, int column) {// פונקציה אשר נקראת בשלב הראשון, מקבלת טבלה נתונה ועמודה נתונה
															// - עמודת הפתרונות האופטימליים
		int max = 0;
		for (int i = 1; i < table.length; i++) {
			if (table[i][column] > table[max][column])
				max = i;
		}
		return max;
	}// max sol

	public static int idCalculate(int id1, int id2) { // חישוב הנפח המקסימלי
		int sum = 0;
		while ((id1 > 0) && (id2 > 0)) {
			sum += (id1 % 10) + (id2 % 10);
			id1 /= 10;
			id2 /= 10;
		}
		System.out.println("W=" + sum);
		return sum;
	}// idCalculate

	public static int numOfDigits(int id1, int id2) { // חישוב סכום ספרות - מספר השלבים
		int sum = 0;
		while ((id1 > 0) || (id2 > 0)) {
			id1 /= 10;
			id2 /= 10;
			sum += 2;
		}
		System.out.println("n=" + sum);
		return sum;
	}// numOfDigits

	public static void fillArray(int[] array, int id1, int id2) { // מערך של נפח ומערך של שלבים - קריאה פעמיים לפונקציה
																	// כל פעם בסדר אחר של תז
		int i = 17;
		while ((id1 > 0) && (i >= 0)) {
			array[i] = (id1 % 10);
			if (array[i] == 0) {
				array[i] = 10; // if digit is zero, needs to put 10
			}
			id1 /= 10;
			i--;
		}
		while ((id2 > 0) && (i >= 0)) {
			array[i] = (id2 % 10);
			if (array[i] == 0) {
				array[i] = 10; // if digit is zero, needs to put 10
			}
			id2 /= 10;
			i--;
		}
	}

	public static void main(String[] args) {
		Scanner F = new Scanner(System.in);
		System.out.print("Enter ID(1): ");
		int id1 = F.nextInt();
		System.out.print("Enter ID(2): ");
		int id2 = F.nextInt();
		int W = idCalculate(id1, id2);
		int n = numOfDigits(id1, id2);
		int[] weights = new int[n];
		int[] profits = new int[n];

		fillArray(profits, id1, id2);
		fillArray(weights, id2, id1); // Profits are filled reverse

		Vector<int[][]> sol = new Vector<int[][]>(); // וקטור טלבאות לכל שלב

		for (int stage = 0; stage < profits.length; stage++) {// מילוי הוקטור
			sol.add(solve(profits, weights, W, stage));
		} // for

		int optimum = sol.elementAt(0)[W][W + 2];// הפתרון האופטימלי
		System.out.println("The optimal solution is " + optimum);

		int sizeLeft = W;
		for (int stage = 0; stage < profits.length; stage++) {// הדפסת טבלאות המדיניות ושחזור הפתרון
			System.out.println();
			System.out.println("Table for stage " + (stage + 1));
			System.out.println("S*" + (stage + 1) + "\t" + "X*" + (stage + 1) + "\t" + "F*" + (stage + 1) + "\t");
			for (int i = 0; i < sol.elementAt(stage).length; i++) {
				System.out.print(i + "\t");
				for (int j = 0; j < sol.elementAt(stage)[i].length; j++) {
					if (j == W+1 || j == W+2)
					System.out.print(sol.elementAt(stage)[i][j] + "\t");
				} // for-j
				System.out.println();
			} // for-i

			if (stage == 0) {
				System.out.println("X" + (stage + 1) + "=" + sol.elementAt(stage)[W][W + 1]);
				sizeLeft -= sol.elementAt(stage)[W][W + 1] * weights[stage];
			} // if

			else {
				System.out.println("X" + (stage + 1) + "=" + sol.elementAt(stage)[sizeLeft][W + 1]);
				sizeLeft -= sol.elementAt(stage)[sizeLeft][W + 1] * weights[stage];
			} // else
		} // for

	}// main
}// class
