/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MeanMedianModeGroupedData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Ahsan
 */
public class First {

	String limits[][] = new String[2][];
	Double upperBoundary[];
	Double lowerBoundary[];
	Double midPointsOfClass[];
	Integer Frequency[];
	Integer commulativeFrequency[];
	Integer totalFrequency;

	BigDecimal computeMeanDeviation() {

		BigDecimal MeanDeviation = new BigDecimal("0.0");
		BigDecimal tempMeanDeviation;
		BigDecimal mean = computeMean();

		System.out.println("\n MMMean is " + mean);

		for (int i = 0; i < midPointsOfClass.length; i++) {
			// MeanDeviation = MeanDeviation.add(new
			// BigDecimal(midPointsOfClass[i].toString()).subtract(mean).abs()
			// .multiply((new BigDecimal(Frequency[i]))));

			tempMeanDeviation = new BigDecimal(midPointsOfClass[i].toString()).subtract(mean);
			tempMeanDeviation = tempMeanDeviation.abs().multiply((new BigDecimal(Frequency[i])));
			MeanDeviation = MeanDeviation.add(tempMeanDeviation);
		}

		MeanDeviation = MeanDeviation.divide(
				new BigDecimal(commulativeFrequency[commulativeFrequency.length - 1].toString()), 4,
				RoundingMode.CEILING);

		return (MeanDeviation);
	}

	BigDecimal computeMode() {

		Integer modalClassIndex = 0;
		Integer maxFrequency = Frequency[0];
		Integer delta1, delta2;
		Double lowerclassBoundaryOfModalClass, sizeOfModalClass;
		BigDecimal mode;

		for (int i = 1; i < Frequency.length; i++)

			if (maxFrequency < Frequency[i]) {
				maxFrequency = Frequency[i];
				modalClassIndex = i;
			}

		maxFrequency = null;

		lowerclassBoundaryOfModalClass = lowerBoundary[modalClassIndex];
		sizeOfModalClass = upperBoundary[modalClassIndex] - lowerBoundary[modalClassIndex];

		if (modalClassIndex == 0)
			delta1 = Frequency[modalClassIndex];
		else
			delta1 = Frequency[modalClassIndex] - Frequency[modalClassIndex - 1];

		if (modalClassIndex == Frequency.length - 1)
			delta2 = Frequency[modalClassIndex];
		else
			delta2 = Frequency[modalClassIndex] - Frequency[modalClassIndex + 1];

		mode = new BigDecimal(delta1 + delta2);
		mode = new BigDecimal(delta1).divide(mode, 4, RoundingMode.CEILING);
		mode = mode.multiply(new BigDecimal(sizeOfModalClass));
		mode = mode.add(new BigDecimal(lowerclassBoundaryOfModalClass));
		return (mode);
	}

	BigDecimal[] computeMedian() {

		int medianClassindex = -1;
		Double sizeOfMedianClass;
		BigDecimal median[] = new BigDecimal[3];

		Double halfOfN = commulativeFrequency[commulativeFrequency.length - 1] / 2.0;

		for (int i = 0; i < commulativeFrequency.length; i++)

			if (commulativeFrequency[i] >= halfOfN) {

				medianClassindex = i;
				break;
			}

		if (medianClassindex == -1) {

			System.out.println("\nCan not find Median Class. Please check your entered data.");
			System.exit(0);
		}

		sizeOfMedianClass = upperBoundary[medianClassindex] - lowerBoundary[medianClassindex];

		median[2] = new BigDecimal(medianClassindex);
		median[0] = new BigDecimal(halfOfN.toString())
				.subtract(new BigDecimal(commulativeFrequency[medianClassindex - 1]));
		median[0] = median[0].multiply(new BigDecimal(sizeOfMedianClass.toString()));

		median[0] = median[0].divide(new BigDecimal(Frequency[medianClassindex]), 4, RoundingMode.CEILING);

		median[1] = median[0];

		median[0] = median[0].add(new BigDecimal(lowerBoundary[medianClassindex]));

		median[1] = median[1].add(new BigDecimal(limits[0][medianClassindex]));

		System.out.println("\nTotal Obsevations " + commulativeFrequency[commulativeFrequency.length - 1]);
		System.out.println("Lower Limit of median class l = " + lowerBoundary[medianClassindex]);
		System.out.println("CF before median class CF = " + commulativeFrequency[medianClassindex - 1]);
		System.out.println("F of median class F = " + Frequency[medianClassindex]);
		System.out.println("Class Size S =" + sizeOfMedianClass);
		System.out.println("N/2 " + halfOfN);
		return (median);
	}

	BigDecimal computeMean() {

		BigDecimal mean = new BigDecimal("0");

		System.out.println();
		for (int i = 0; i < Frequency.length; i++)
			mean = mean.add(new BigDecimal(Frequency[i]).multiply(new BigDecimal(midPointsOfClass[i])));
		// System.out.println(mean);

		return (mean.divide(new BigDecimal(totalFrequency), 4, RoundingMode.CEILING));
	}

	void computeCommulativeFrequency() {

		totalFrequency = 0;
		commulativeFrequency = new Integer[limits[0].length];

		System.out.print("\nCommulative Frequencies ");

		commulativeFrequency[0] = Frequency[0];
		System.out.print(commulativeFrequency[0] + " ");

		for (int i = 1; i < limits[0].length; i++) {
			commulativeFrequency[i] = Frequency[i] + commulativeFrequency[i - 1];
			System.out.print(commulativeFrequency[i] + " ");
		}
		totalFrequency = commulativeFrequency[Frequency.length - 1];
	}

	void computeMidPoint() {
		midPointsOfClass = new Double[limits[0].length];

		System.out.print("Mid Points ");
		for (int i = 0; i < limits[0].length; i++) {
			midPointsOfClass[i] = (Integer.parseInt(limits[0][i]) + Integer.parseInt(limits[1][i])) / 2.0;
			System.out.print(midPointsOfClass[i] + " ");
		}
	}

	void computeBondaries() {
		lowerBoundary = new Double[limits[0].length];
		upperBoundary = new Double[limits[1].length];

		System.out.println();
		System.out.print("Lower Boundaries and Upper Boundaries\n");
		for (int i = 0; i < limits[0].length; i++)
			lowerBoundary[i] = Integer.parseInt(limits[0][i]) - 0.5;

		for (int i = 0; i < limits[0].length; i++) {

			/*if ((i + 1) < limits[0].length && Integer.parseInt(limits[1][i]) == Integer.parseInt(limits[0][i + 1]))
				upperBoundary[i] = Integer.parseInt(limits[1][i]) - 1 + 0.5;

			else
				upperBoundary[i] = Integer.parseInt(limits[1][i]) + 0.5;*/
                        
                        upperBoundary[i] = Integer.parseInt(limits[1][i]) + 0.5;

			System.out.print(lowerBoundary[i] + "-");
			System.out.println(upperBoundary[i]);
		}

		System.out.println();
	}

	void readFile() throws IOException {

		String x;
		String y[][] = new String[3][];
		File f = new File(System.getProperty("user.dir") + "/Input0.txt");
				//"C:/Users/Ahsan/Desktop/JAVA/Java/Programs in Eclipse/SP/MeanMedianModebyGroupdata/Median/Input0.txt");
		// "C:/Users/Ahsan/Desktop/Java/Programs in
		// Eclipse/SP/MeanMedianModebyGroupdata/Median/Input14.txt");
		// f.createNewFile();

		// "C:\\Users\\Ahsan\\Desktop\\JAVA\\Java\\Programs in
		// Eclipse\\SP\\MeanMedianModebyGroupdata\\Median\Input14.txt"

		BufferedReader rd = new BufferedReader(new FileReader(f));

		/*
		 * uLlLf[0] = rd.readLine(); uLlLf[1] = rd.readLine(); uLlLf[2] = rd.readLine();
		 */

		for (int i = 0; i < 3; i++) {

			x = rd.readLine();
			// System.out.println(x);

			if (x.contains(","))
				if (x.contains(", "))
					y[i] = x.split(", ");
				else
					y[i] = x.split(",");

			else if (x.contains(" "))
				y[i] = x.split(" ");

			else {
				System.out.println("Please enter data in valid format.");
				System.exit(0);
			}
		}

		if (y[0].length != y[1].length || y[0].length != y[2].length) {
			System.out.println("No. of Upper and limts should be same.");
			System.exit(0);
		}

		limits[0] = y[0];
		limits[1] = y[1];

		System.out.println("Boundaries");
		for (int i = 0; i < limits[0].length; i++)
			System.out.println(limits[0][i] + "-" + limits[1][i]);
		Frequency = new Integer[y[2].length];

		for (Integer i = 0; i < y[2].length; i++)
			Frequency[i] = Integer.parseInt(y[2][i]);

		y = null;

		rd.close();
	}

	public static void main(String[] args) throws IOException {

		First g = new First();

		BigDecimal median[];
		g.readFile();
		g.computeBondaries();
		g.computeMidPoint();
		g.computeCommulativeFrequency();
		System.out.println("\nMean is " + g.computeMean());
		median = g.computeMedian();
		System.out.println("\nWhen l = " + g.lowerBoundary[median[2].intValue()] + " Median is " + median[0]);
		System.out.println("\nWhen l = " + g.limits[0][median[2].intValue()] + " Median is " + median[1]);
		System.out.println("\nMode is " + g.computeMode());
		System.out.println("Mean Deviation is " + g.computeMeanDeviation());
	}

}