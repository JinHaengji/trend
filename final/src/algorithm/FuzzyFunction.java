package algorithm;

import java.util.Vector;

public class FuzzyFunction {

	public Vector<Double> funcVitali(double vitali) { // 활성화 함수
		Vector<Double> results = new Vector<Double>(); //활성화 값
		

		double result; // 결과값
		double result2; // 결과값

		double a; // 기울기1
		double a2; // 기울기2
		double b; // y절편
		double b2; // y절편

		if (1.0 <= vitali && vitali < 3.0) {
			a = (1.0 - 0.0) / (3.0 - 1.0);
			b = -a;

			result = (int) ((a * vitali + b) * 100) / 100.0;
			results.add(result);
			results.add(1.0);
		}

		else if (3.0 <= vitali && vitali < 5.0) {
			a = (0.0 - 1.0) / (5.0 - 3.0);
			b = -a * 3.0 + 1.0;

			a2 = (1.0 - 0.0) / (5.0 - 3.0);
			b2 = -a2 * 3.0;

			result = (int) ((a * vitali + b) * 100) / 100.0;
			result2 = (int) ((a2 * vitali + b2) * 100) / 100.0;

			results.add(result);
			results.add(1.0);
			results.add(result2);
			results.add(2.0);
		}

		else { // x범위가 5~7
			a = (0.0 - 1.0) / (7.0 - 5.0);
			b = -a * 5.0 + 1.0;

			a2 = (1.0 - 0.0) / (7.0 - 5.0);
			b2 = -a2 * 5.0;

			result = (int) ((a * vitali + b) * 100) / 100.0;
			result2 = (int) ((a2 * vitali + b2) * 100) / 100.0;

			results.add(result);
			results.add(2.0);
			results.add(result2);
			results.add(3.0);
		}
		return results;
	}
	
	public Vector<Double> funcPlea(double plea) { // 쾌/불쾌 함수
		Vector<Double> results = new Vector<Double>();

		double result; // 결과값
		double result2; // 결과값

		double a; // 기울기1
		double a2; // 기울기2
		double b; // y절편
		double b2; // y절편

		if (1.0 <= plea && plea < 2.5) {
			a = (1.0 - 0.0) / (2.5 - 1.0);
			b = -a;

			result = (int) ((a * plea + b) * 100) / 100.0;
			results.add(result);
			results.add(1.0);
		}

		else if (2.5 <= plea && plea < 4.0) {
			a = (0.0 - 1.0) / (4.0 - 2.5);
			b = -a * 2.5 + 1.0;

			a2 = (1.0 - 0.0) / (4.0 - 2.5);
			b2 = -a2 * 2.5;

			result = (int) ((a * plea + b) * 100) / 100.0;
			result2 = (int) ((a2 * plea + b2) * 100) / 100.0;

			results.add(result);
			results.add(1.0);
			results.add(result2);
			results.add(2.0);
		}
		
		else if (4.0 <= plea && plea < 5.5) {
			a = (0.0 - 1.0) / (5.5 - 4.0);
			b = -a * 4.0 + 1.0;

			a2 = (1.0 - 0.0) / (5.5 - 4.0);
			b2 = -a2 * 4.0;

			result = (int) ((a * plea + b) * 100) / 100.0;
			result2 = (int) ((a2 * plea + b2) * 100) / 100.0;

			results.add(result);
			results.add(2.0);
			results.add(result2);
			results.add(3.0);
		}

		else { // x범위가 5.5~7
			a = (0.0 - 1.0) / (7.0 - 5.5);
			b = -a * 5.5 + 1.0;

			a2 = (1.0 - 0.0) / (7.0 - 5.5);
			b2 = -a2 * 5.5;

			result = (int) ((a * plea + b) * 100) / 100.0;
			result2 = (int) ((a2 * plea + b2) * 100) / 100.0;

			results.add(result);
			results.add(3.0);
			results.add(result2);
			results.add(4.0);
		}
		return results;
	}

	//테스트 -> 의미X
/*	public static void main(String[] args) {
		Vector<Double> results_ = new Vector<Double>();
		results_ = funcPlea(6.16);

		for (int i = 0; i < results_.size(); i++) {
			Object obj = results_.get(i);
			System.out.println((double) obj);
		}

	}*/

}
