
public class FibonacciDynamicProgramming {

//Recursive Dynamic Programming Solution using memoization

	public static int fib(int n, int[] fib) {
		if (n <= 1) // base condition
			return n;
		else {
			fib[n] = fib(n - 1, fib) + fib(n - 2, fib);
			return fib[n];
		} // else
	}// fib

	public static void main(String[] args) {
		long nano_startTime = System.nanoTime();

		int n = 10;
		int[] fib = new int[n + 1]; // we add +1 to the array's size to handle the case n=0
		System.out.println(fib(n, fib));

		long nano_endTime = System.nanoTime();
		System.out.println("Time taken in nano seconds: " + (nano_endTime - nano_startTime));
	}// main

}// FibonacciDynamicProgramming
