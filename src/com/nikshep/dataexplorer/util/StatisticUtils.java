package com.nikshep.dataexplorer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import com.nikshep.dataexplorer.model.Outlier;

public class StatisticUtils {
	
	
	/**
	 * IQR method to calculate the outliers for numeric fields
	 * 
	 * 
	 * 
	 * @param data
	 */
	public static Outlier getOutliers(double[] data){
		
		DescriptiveStatistics statistics = new DescriptiveStatistics(data); //imported from commons-math from apache
		
		double q1 = statistics.getPercentile(25);
		double q3 = statistics.getPercentile(75);

		double iqr = q3 - q1;
		double lowerRange = q1 - 1.5 * iqr;
		double upperRange = q3 + 1.5 * iqr;

		/*
		 * Outliers are found using IQR method
		 * In this we find the first quartile(25th percentile) and third quartile(75th percentile)
		 * The InterQuatrileRange is calculated as iqr = q3-q1 then find the lower range and upper range of the data as 
		 * 		lowerRange = q1-1.5*iqr
		 * 		upperRange = q3+1.5*iqr
		 */
		List<Integer> outlierIndicesList = new ArrayList<Integer>(); //collections - List and ArrayList 
		for (int index=0; index<data.length; index++) {
			double d = data[index];
			
			if(!isMissingValue(d)) {
				if (d < lowerRange || d > upperRange) {
					outlierIndicesList.add(index);
				}
			}
		}
		
		int[] outlierIndices = new int[outlierIndicesList.size()];
		for(int i=0; i<outlierIndicesList.size(); i++) {
			Integer outlierIndex = outlierIndicesList.get(i);
			outlierIndices[i] = outlierIndex.intValue();
		}

		Outlier out = new Outlier();
		out.setOutlierIndices(outlierIndices);
		out.setLowerRange(lowerRange);
		out.setUpperRange(upperRange);

		return out;
	}
	
	
	public static boolean isMissingValue(String point) {
		boolean missing = false;
		
		if(point == null || point.isEmpty()) {
			missing = true;
		}
		
		return missing;
	}
	
	public static boolean isMissingValue(double point) {
		boolean missing = false;
		
		if(point == Double.NaN || point == Double.POSITIVE_INFINITY) { //NaN (not a number) and positive_infinity for checking an empty value of the field
			
			missing = true;
		}
		
		return missing;
	}

}
