package handy.tools.stock.indicator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import handy.tools.math.MathHelper;

public class IndicatorCalcHelper {
	
	
	public static double MA(String[] closePrice) {
				
		return MathHelper.getAverage(closePrice);
	}
	
	public static double BollMB(String[] closePrice) {
		
		String[] data = new String[closePrice.length-1];
		System.arraycopy(closePrice, 0, data, 0, closePrice.length-1);
				
		return MathHelper.getAverage(data);
		
	}
	
	public static double DzhBollMB(String[] closePrice) {
				
		return MathHelper.getAverage(closePrice);		
	}
	
	public static double BollUP(String[] closePrice, int k) {
		
		return (DzhBollMB(closePrice) + 
				k*(MathHelper.getStandardDevition(closePrice)));		
	}
	
	public static double BollDN(String[] closePrice, int k) {
		
		return (DzhBollMB(closePrice) - 
				k*(MathHelper.getStandardDevition(closePrice)));
	}
	
	public static double getAR(String[] high, String[] open, String[] low) {
		
		double[] high_open = new double[open.length];
		double[] open_low = new double[low.length];
		for(int i = 0; i < open.length; i++) {
			high_open[i] = Double.parseDouble(high[i]) - Double.parseDouble(open[i]);
			open_low[i] = Double.parseDouble(open[i]) - Double.parseDouble(low[i]);
		}
		return MathHelper.Sum(high_open)/MathHelper.Sum(open_low)*100;
	}

	public static double getBR(String[] high, String[] low, String ZuoShou) {
		
		double[] high_ZuoShou = new double[high.length];
		double[] zuoShou_low = new double[high.length];
		for(int i = 0; i < high.length; i++) {
			high_ZuoShou[i] = Double.parseDouble(high[i])- Double.parseDouble(ZuoShou);
			zuoShou_low[i] = Double.parseDouble(ZuoShou) - Double.parseDouble(low[i]);	
		}
		return MathHelper.Sum(high_ZuoShou)/MathHelper.Sum(zuoShou_low)*100;

	}
	
	
	/*
	 * return param:
	 * indicator[0] is MB
	 * indicator[1] is UP
	 * indicator[2] is DN
	 * 
	 * */
	public static double[] getBollIndicators(String[] closePrice, int k) {
		
		double[] indicators = new double[3];
		
		indicators[0] = DzhBollMB(closePrice);
		System.out.println("MB: " + indicators[0]);
		
		double stdDev = MathHelper.getStandardDevition(closePrice);
		System.out.println("stdDev: " + stdDev);
		
		indicators[1] = indicators[0] + k*stdDev;
		indicators[2] = indicators[0] - k*stdDev;
		
		System.out.println("UP: " + indicators[1]);
		System.out.println("DN: " + indicators[2]);
		
		return indicators;
		
	}

}
