import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MinMax {

  double min;
  double max;

  public MinMax() {

  }

  public MinMax(double min, double max) {
    this.min = min;
    this.max = max;
  }

  private boolean shouldDivideSizeEqually(int numberOfTemperatures) {

    boolean shouldDivide = false;
    // if odd or divisible by 4 then split into two approximately equal halves
    if (numberOfTemperatures % 2 == 1 || numberOfTemperatures % 4 == 0) {
      shouldDivide = true;
    }
    return shouldDivide;
  }

  private int findMultiplier(int numberOfTemperatures) {
    return (numberOfTemperatures - 2) / 4;
  }


  /**.
   * @param sensorData - array of SensorData objects.
   * @param lowerIndex - lower index of the "sub array".
   * @param upperIndex - upper index of the "sub array".
   * @return MinMax - record containing min, max as properties.
   */
  public MinMax findMinMax(SensorData[] sensorData, int lowerIndex, int upperIndex) {
    double min;
    double max;
    if (lowerIndex == upperIndex) {
      min = sensorData[lowerIndex].getConvertedTemperatureValue();
      max = sensorData[upperIndex].getConvertedTemperatureValue();
      return new MinMax(min, max);
    }
    if (upperIndex == lowerIndex + 1) {
      if (sensorData[upperIndex].getConvertedTemperatureValue() > sensorData[lowerIndex]
          .getConvertedTemperatureValue()) {
        max = sensorData[upperIndex].getConvertedTemperatureValue();
        min = sensorData[lowerIndex].getConvertedTemperatureValue();
      } else {
        max = sensorData[lowerIndex].getConvertedTemperatureValue();
        min = sensorData[upperIndex].getConvertedTemperatureValue();
      }
      return new MinMax(min, max);
    }
    int sizeOfPart = upperIndex - lowerIndex + 1;
    boolean divideEqually = shouldDivideSizeEqually(sizeOfPart);
    int splitIndex;
    if (divideEqually) {
      splitIndex = (lowerIndex + upperIndex) / 2;
    } else {
      int multiplier = findMultiplier(sizeOfPart);
      splitIndex = (2 * multiplier - 1) + lowerIndex;
    }
    MinMax leftHalf = new MinMax().findMinMax(sensorData, lowerIndex, splitIndex);
    MinMax rightHalf =
        new MinMax().findMinMax(sensorData, splitIndex + 1, upperIndex);
    MinMax minMaxResult = new MinMax();

    minMaxResult.max = (leftHalf.max > rightHalf.max) ? leftHalf.max : rightHalf.max;
    minMaxResult.min = (leftHalf.min < rightHalf.min) ? leftHalf.min : rightHalf.min;

    return minMaxResult;
  }


  /**.
   * @param args - Expecting full file path to be passed in from which input will be read.
   * @throws IOException - will be thrown if file is not found at path specified.
   */
  public static void main(String[] args) throws IOException {
    MinMax minmax = new MinMax();
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
    minmax = minmax.findMinMax(sensorData, 0, sensorData.length - 1);
    System.out.println("Max is " + minmax.max + " degrees Celsius");
    System.out.println("Min is " + minmax.min + " degrees Celsius");
  }
}
