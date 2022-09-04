public class FibonacciRecursiveSolution {

//naive recursive solution

	static int fib(int n) {
		if (n <= 1)
			return n;
		return fib(n - 1) + fib(n - 2);
	}// fib

	public static void main(String args[]) {
		long nano_startTime = System.nanoTime();
		int n = 40;
		System.out.println(fib(n));
		long nano_endTime = System.nanoTime();
		System.out.println("Time taken in nano seconds: " + (nano_endTime - nano_startTime));
	}// main
}// FibonacciRecursiveSolution class
