package algorithm;

import java.util.Vector;

public class FuzzyFunction {

	public static Vector<Double> funcVitali(double vitali) { // Ȱ��ȭ �Լ�
		Vector<Double> results = new Vector<Double>();

		double result; // �����
		double result2; // �����

		double a; // ����1
		double a2; // ����2
		double b; // y����
		double b2; // y����

		if (1.0 <= vitali && vitali < 3.0) {
			a = (1.0 - 0.0) / (3.0 - 1.0);
			b = -a;

			result = (int) ((a * vitali + b) * 100) / 100.0;
			results.add(result);
		}

		else if (3.0 <= vitali && vitali < 5.0) {
			a = (0.0 - 1.0) / (5.0 - 3.0);
			b = -a * 3.0 + 1.0;

			a2 = (1.0 - 0.0) / (5.0 - 3.0);
			b2 = -a2 * 3.0;

			result = (int) ((a * vitali + b) * 100) / 100.0;
			result2 = (int) ((a2 * vitali + b2) * 100) / 100.0;

			results.add(result);
			results.add(result2);
		}

		else { // x������ 5~7
			a = (0.0 - 1.0) / (7.0 - 5.0);
			b = -a * 5.0 + 1.0;

			a2 = (1.0 - 0.0) / (7.0 - 5.0);
			b2 = -a2 * 5.0;

			result = (int) ((a * vitali + b) * 100) / 100.0;
			result2 = (int) ((a2 * vitali + b2) * 100) / 100.0;

			results.add(result);
			results.add(result2);
		}
		return results;
	}
	
	public static Vector<Double> funcPlea(double plea) { // ��/���� �Լ�
		Vector<Double> results = new Vector<Double>();

		double result; // �����
		double result2; // �����

		double a; // ����1
		double a2; // ����2
		double b; // y����
		double b2; // y����

		if (1.0 <= plea && plea < 2.5) {
			a = (1.0 - 0.0) / (2.5 - 1.0);
			b = -a;

			result = (int) ((a * plea + b) * 100) / 100.0;
			results.add(result);
		}

		else if (2.5 <= plea && plea < 4.0) {
			a = (0.0 - 1.0) / (4.0 - 2.5);
			b = -a * 2.5 + 1.0;

			a2 = (1.0 - 0.0) / (4.0 - 2.5);
			b2 = -a2 * 2.5;

			result = (int) ((a * plea + b) * 100) / 100.0;
			result2 = (int) ((a2 * plea + b2) * 100) / 100.0;

			results.add(result);
			results.add(result2);
		}
		
		else if (4.0 <= plea && plea < 5.5) {
			a = (0.0 - 1.0) / (5.5 - 4.0);
			b = -a * 4.0 + 1.0;

			a2 = (1.0 - 0.0) / (5.5 - 4.0);
			b2 = -a2 * 4.0;

			result = (int) ((a * plea + b) * 100) / 100.0;
			result2 = (int) ((a2 * plea + b2) * 100) / 100.0;

			results.add(result);
			results.add(result2);
		}

		else { // x������ 5.5~7
			a = (0.0 - 1.0) / (7.0 - 5.5);
			b = -a * 5.5 + 1.0;

			a2 = (1.0 - 0.0) / (7.0 - 5.5);
			b2 = -a2 * 5.5;

			result = (int) ((a * plea + b) * 100) / 100.0;
			result2 = (int) ((a2 * plea + b2) * 100) / 100.0;

			results.add(result);
			results.add(result2);
		}
		return results;
	}

	//�׽�Ʈ -> �ǹ�X
	public static void main(String[] args) {
		Vector<Double> results_ = new Vector<Double>();
		results_ = funcPlea(4.33);

		for (int i = 0; i < results_.size(); i++) {
			Object obj = results_.get(i);
			System.out.println((double) obj);
		}

	}

}
