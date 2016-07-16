import java.util.Scanner;


public class MinMaxSample2 {
	
	double min;
	double max;
	
	public MinMaxSample2(){
		
	}
	
	private boolean shouldDivideSizeEqually(int numberOfTemperatures){
		
		boolean shouldDivide = false;
		// if odd or divisible by 4 then split into two approximately equal halves
		if(numberOfTemperatures % 2 == 1 || numberOfTemperatures % 4 == 0) {
			shouldDivide = true;
		}
		return shouldDivide;
	}
	
	private int findMultiplier(int numberOfTemperatures){
		return (numberOfTemperatures - 2)/4;
	}
	
	public MinMaxSample2(double min, double max){
		this.min = min;
		this.max = max;
	}
	
	public MinMaxSample2 findMinMax(double[] temperatures, int lowerIndex, int upperIndex){
		double min,max;
		if(lowerIndex == upperIndex) {
			min = temperatures[lowerIndex];
			max = temperatures[upperIndex];
			return new MinMaxSample2(min,max);
		}
		if(upperIndex == lowerIndex + 1) {
			if(temperatures[upperIndex] > temperatures[lowerIndex]){
				max = temperatures[upperIndex];
				min = temperatures[lowerIndex];
			} else {
				max = temperatures[lowerIndex];
				min = temperatures[upperIndex];
			}
			return new MinMaxSample2(min,max);
		}
		int sizeOfPart = upperIndex - lowerIndex + 1;
		boolean divideEqually = shouldDivideSizeEqually(sizeOfPart);
		int splitIndex;
		if(divideEqually) {
			splitIndex = (lowerIndex + upperIndex)/2;
		} else {
			int multiplier = findMultiplier(sizeOfPart);
			splitIndex = (2 * multiplier - 1) + lowerIndex;
		}
		MinMaxSample2 leftHalf = new MinMaxSample2().findMinMax(temperatures, lowerIndex, splitIndex);
		MinMaxSample2 rightHalf = new MinMaxSample2().findMinMax(temperatures, splitIndex + 1, upperIndex);
		MinMaxSample2 minMaxResult = new MinMaxSample2();
		
		minMaxResult.max = (leftHalf.max > rightHalf.max) ? leftHalf.max : rightHalf.max;
		minMaxResult.min = (leftHalf.min < rightHalf.min) ? leftHalf.min : rightHalf.min;
		
		return minMaxResult;
	}
	
	
	public static void main(String[] args){
		MinMaxSample2 minmax = new MinMaxSample2();
		Scanner scan = new Scanner(System.in);
		double[] temperatures = new double[10];
		System.out.println("Enter 10 numbers");
		for(int index = 0 ; index < 10; index++){
			temperatures[index] = scan.nextDouble();
		}
		scan.close();
		minmax = minmax.findMinMax(temperatures,0,9);
		System.out.println("Max is " + minmax.max);
		System.out.println("Min is " + minmax.min);
	}
}
