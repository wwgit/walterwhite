package handy.tools.math;

import java.text.DecimalFormat;

public class MathHelper {
	
	//private static DecimalFormat df = new DecimalFormat("#.00");
	private static int deservedDigit = 0;
	
	public static double getAverage(String[] data) {
		
		double result = 0;
		
		for(int i = 0; i < data.length; i++) {
			result += Double.parseDouble(data[i]);
			//System.out.println(result);
		}
		
		result = result/(data.length);
		
		if(0 == deservedDigit) {
			return result;
		} else {
			return Math.round(result*(10*deservedDigit))/(10*deservedDigit);
		}

	}
		
	public static double getStandardDevition(String[] data) {
		
		double result = 0, tmp = 0;
		double avg = getAverage(data);
		
		for(int i = 0; i < data.length; i++) {
			tmp = Double.parseDouble(data[i])-avg;
			result += Math.pow(tmp,2);
			//System.out.println(result);
			//System.out.println(tmp);
		}
		result = Math.sqrt(result/(data.length-1));
		
		if(0 == deservedDigit) {
			return result;
		} else {
			return Math.round(result*(10*deservedDigit))/(10*deservedDigit);
		}

	}
	
	public static double Sum(double[] value) {

		double result = 0;
		for(int i = 0; i < value.length; i++) {	
			result += value[i];	
		}
		return result;
	}

}
