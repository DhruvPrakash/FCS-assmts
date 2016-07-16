
public class SensorData {
	
	private int sensorId;
	private String location;
	private String timeStamp;
	private double temperatureValue;
	private char temperatureUnit;
	
	public SensorData(String datum){
		String[] splitDatum = datum.split(",");
		this.sensorId = Integer.parseInt(splitDatum[0]);
		this.location = splitDatum[1];
		this.timeStamp = splitDatum[2];
		this.temperatureValue = Double.parseDouble(splitDatum[3]);
		this.temperatureUnit = splitDatum[4].charAt(0);
	}
	
	public double getConvertedTemperatureValue(){
		return (this.temperatureUnit == 'F') ? (this.temperatureValue - 32) * 5/9 : this.temperatureValue;
	}
}
