import java.util.ArrayList;
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
	
	public MinMaxSample2 findMinMax(SensorData[] sensorData, int lowerIndex, int upperIndex){
		double min,max;
		if(lowerIndex == upperIndex) {
			min = sensorData[lowerIndex].temperature;
			max = sensorData[upperIndex].temperature;
			return new MinMaxSample2(min,max);
		}
		if(upperIndex == lowerIndex + 1) {
			if(sensorData[upperIndex].temperature > sensorData[lowerIndex].temperature){
				max = sensorData[upperIndex].temperature;
				min = sensorData[lowerIndex].temperature;
			} else {
				max = sensorData[lowerIndex].temperature;
				min = sensorData[upperIndex].temperature;
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
		MinMaxSample2 leftHalf = new MinMaxSample2().findMinMax(sensorData, lowerIndex, splitIndex);
		MinMaxSample2 rightHalf = new MinMaxSample2().findMinMax(sensorData, splitIndex + 1, upperIndex);
		MinMaxSample2 minMaxResult = new MinMaxSample2();
		
		minMaxResult.max = (leftHalf.max > rightHalf.max) ? leftHalf.max : rightHalf.max;
		minMaxResult.min = (leftHalf.min < rightHalf.min) ? leftHalf.min : rightHalf.min;
		
		return minMaxResult;
	}
	
	
	public static void main(String[] args){
		MinMaxSample2 minmax = new MinMaxSample2();
		Scanner scan = new Scanner(System.in);
		ArrayList<SensorData> sensorArray = new ArrayList<SensorData>();
		for(int index = 0 ; index < 10; index++){
			SensorData sensorData = new SensorData();		
			System.out.println("Enter id");
			sensorData.id = scan.nextInt();
			System.out.println("Enter temperature");
			sensorData.temperature = scan.nextDouble();
			sensorArray.add(sensorData);
		}
		scan.close();
		SensorData[] sensorData = sensorArray.toArray(new SensorData[sensorArray.size()]);
		minmax = minmax.findMinMax(sensorData,0,9);
		System.out.println("Max is " + minmax.max);
		System.out.println("Min is " + minmax.min);
	}
}
