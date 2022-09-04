import java.util.Scanner;
import java.util.Vector;

public class knapsack {

	public static int[][] solve(int[] profits, int[] weights, int W, int n) {// �������� ������ �� �����

		int[][] dp = new int[W + 1][W + 3]; // ���� ���� ���� ������ , ����� - ����� - ��� ���� ���� �� ������ ����,
											// ������ - ����� ����� - ��� ���� ��� ��� �� ����� ���� ��. ����� ����
											// ������� ���� ���� , ����� ������ �� ����

		if (n >= profits.length)// ���� ����� - ����� ���� ����� �� ��� �������
			return dp;

		int profit = profits[n]; // ����� ����� ����� ������ ���� ��
		int weight = weights[n];
		int[][] next = solve(profits, weights, W, n + 1);// ����� ����� �� ���� ���

		for (int i = 0; i < W + 1; i++) {
			for (int j = 0; j < W + 3; j++) {

				if (n == 0 && i < W) // �� ����� ���� ������ ���� ���� �� �� ����� ������� �����
					break;

				if (j == W + 1) {// �� ����� ������ ���� �������, �� ����� ���������
					int max = maxIndex(dp, i);// ��� �� ������� �� ������ �� ����� ��� ������� �����
					dp[i][j] = max;// ������ ���� ���� ��� ���� ������� �����
					dp[i][j + 1] = dp[i][max];// ������ �� ���� ��� ���� ���� �������� ������� �����
					break;
				} // if

				else if (i == 0 || j == 0)// �� ����� �� ������ ���, �� ��� ���� �� ����� �� ������ ��������� ������
											// ����� ������ ������ �� ��� ���

					dp[i][j] = 0 + next[i][W + 2];

				else if (weight <= i) {// �� ����� ����� �� ����� ���� ���� ������
					if (j * weight <= i)// ���� ��� �� �� ����� ���� ����� ���� ������*��� ������ ����� ����
						dp[i][j] = j * profit + next[i - j * weight][W + 2];// ���� ��� ������ �� ���� ����
				} // else if

				else
					dp[i][j] = 0;// �� ��� ����� ���� ����
			} // for-j
		} // for-i

		return dp;
	}// solve function

	public static int maxIndex(int[][] table, int row) {// ������� ��� ����� ����� ����� ������ ����� ��� �������,
														// ������ �� ������� �� ������ ���
		int max = 0;
		for (int j = 1; j < table[row].length; j++) {
			if (table[row][j] > table[row][max])
				max = j;
		}
		return max;
	}// max index

	public static int maxSol(int[][] table, int column) {// ������� ��� ����� ���� ������, ����� ���� ����� ������ �����
															// - ����� �������� �����������
		int max = 0;
		for (int i = 1; i < table.length; i++) {
			if (table[i][column] > table[max][column])
				max = i;
		}
		return max;
	}// max sol

	public static int idCalculate(int id1, int id2) { // ����� ���� ��������
		int sum = 0;
		while ((id1 > 0) && (id2 > 0)) {
			sum += (id1 % 10) + (id2 % 10);
			id1 /= 10;
			id2 /= 10;
		}
		System.out.println("W=" + sum);
		return sum;
	}// idCalculate

	public static int numOfDigits(int id1, int id2) { // ����� ���� ����� - ���� ������
		int sum = 0;
		while ((id1 > 0) || (id2 > 0)) {
			id1 /= 10;
			id2 /= 10;
			sum += 2;
		}
		System.out.println("n=" + sum);
		return sum;
	}// numOfDigits

	public static void fillArray(int[] array, int id1, int id2) { // ���� �� ��� ����� �� ����� - ����� ������ ��������
																	// �� ��� ���� ��� �� ��
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

		Vector<int[][]> sol = new Vector<int[][]>(); // ����� ������ ��� ���

		for (int stage = 0; stage < profits.length; stage++) {// ����� ������
			sol.add(solve(profits, weights, W, stage));
		} // for

		int optimum = sol.elementAt(0)[W][W + 2];// ������ ���������
		System.out.println("The optimal solution is " + optimum);

		int sizeLeft = W;
		for (int stage = 0; stage < profits.length; stage++) {// ����� ������ �������� ������ ������
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
