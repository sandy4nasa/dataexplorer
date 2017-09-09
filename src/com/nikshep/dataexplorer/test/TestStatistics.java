package com.nikshep.dataexplorer.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.nikshep.dataexplorer.model.ColumnData;
import com.nikshep.dataexplorer.model.Datatype;
import com.nikshep.dataexplorer.model.NumericColumn;
import com.nikshep.dataexplorer.model.Outlier;
import com.nikshep.dataexplorer.util.CSVReader;
import com.nikshep.dataexplorer.util.DatatypeParser;
import com.nikshep.dataexplorer.util.StatisticUtils;

public class TestStatistics {

	
	public static void main(String[] args) {
		
		try{
			FileInputStream fis = new FileInputStream(new File("E:\\work\\PAD Datasets\\Iris.csv"));
			List<ColumnData> colData = CSVReader.readDataUsingScanner(fis);
			
			for(ColumnData col : colData){
				System.out.println(col.getName());
				List<String> data = col.getData();
				Datatype type = DatatypeParser.guessDatatype(data);
				if(type == Datatype.NUMERIC){
					NumericColumn newColumn = (NumericColumn)DatatypeParser.createColumn(col, type);
					double[] numData = newColumn.getData();
					Outlier out = StatisticUtils.getOutliers(numData);
					System.out.println("Lower range: "+out.getLowerRange());
					System.out.println("Upper range: "+out.getUpperRange());
					System.out.println("Outlier count: "+out.getOutlierCount());
					System.out.println("--------------------------------------------------------------");
				}
			}
	
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
