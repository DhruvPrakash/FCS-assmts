import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MinMaxSample2 {
	
	double min;
	double max;
	
	public MinMaxSample2(){
		
	}
	
	public MinMaxSample2(double min, double max){
		this.min = min;
		this.max = max;
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
	
	
	public MinMaxSample2 findMinMax(SensorData[] sensorData, int lowerIndex, int upperIndex){
		double min,max;
		if(lowerIndex == upperIndex) {
			min = sensorData[lowerIndex].getConvertedTemperatureValue();
			max = sensorData[upperIndex].getConvertedTemperatureValue();
			return new MinMaxSample2(min,max);
		}
		if(upperIndex == lowerIndex + 1) {
			if(sensorData[upperIndex].getConvertedTemperatureValue() > sensorData[lowerIndex].getConvertedTemperatureValue()){
				max = sensorData[upperIndex].getConvertedTemperatureValue();
				min = sensorData[lowerIndex].getConvertedTemperatureValue();
			} else {
				max = sensorData[lowerIndex].getConvertedTemperatureValue();
				min = sensorData[upperIndex].getConvertedTemperatureValue();
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
	
	
	public static void main(String[] args) throws IOException{
		MinMaxSample2 minmax = new MinMaxSample2();
		ArrayList<SensorData> sensorArray = new ArrayList<SensorData>();		
		FileReader in = new FileReader(args[0]);
	    BufferedReader br = new BufferedReader(in);
	    String sensorStringData = br.readLine();
	    while (sensorStringData != null) {	    	
	    	SensorData sensorData = new SensorData(sensorStringData);
	    	sensorArray.add(sensorData);
	    	sensorStringData = br.readLine();
	    }
	    in.close();
		SensorData[] sensorData = sensorArray.toArray(new SensorData[sensorArray.size()]);
		minmax = minmax.findMinMax(sensorData,0,sensorData.length - 1);
		System.out.println("Max is " + minmax.max);
		System.out.println("Min is " + minmax.min);
	}
}
