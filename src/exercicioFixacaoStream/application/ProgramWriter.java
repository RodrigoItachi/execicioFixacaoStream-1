package exercicioFixacaoStream.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import exercicioFixacaoStream.entities.Employee;

public class ProgramWriter {

//	/home/hawk/Workspaces/javawebNelio/exercicioSetFixacao/src/exercicioSetFixacao/util/employee.csv
//	/home/hawk/Documentos/java_Nelio_Alvez/employee.csv
	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.CANADA);
		Scanner leia = new Scanner(System.in);

		List<Employee> employees = new ArrayList<>();

		System.out.print("Enter file path: ");
		String sourceFileStr = leia.nextLine();

//		--------------------------------------------------
		try (BufferedReader reader = new BufferedReader(new FileReader(sourceFileStr))) {

			String itemCsv = reader.readLine();
			while (itemCsv != null) {

				String[] fields = itemCsv.split(",");
				String name = fields[0];
				String email = fields[1];
				Double salary = Double.parseDouble(fields[2]);

				employees.add(new Employee(name, email, salary));
				itemCsv = reader.readLine();
			}
//		--------------------------------------------------
			System.out.print("What is the condition value (salary): ");
			double salaryCondition = leia.nextDouble();

			Comparator<String> comparator = (email1, email2) -> email1.toUpperCase().compareTo(email2.toUpperCase());

			List<String> emails = employees.stream().filter(e -> e.getSalary() > salaryCondition).map(e -> e.getEmail())
					.sorted(comparator).collect(Collectors.toList());
			System.out.println("Email of people whose salary is more than " + String.format("%.2f", salaryCondition)+": ");
			emails.forEach(System.out::println);

			double sum = employees.stream().filter(e -> e.getName().toUpperCase().charAt(0) == 'M')
					.map(e -> e.getSalary()).reduce(0.0, (x, y) -> x + y);

			System.out.println("Sum of salary from people whose name starts with 'M': " + String.format("%.2f", sum));
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}

		leia.close();

	}

}