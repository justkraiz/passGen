package tools.code;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class PasswordGenerator {


    // Метод для генерации паролей и записи их в файл
    public static void generatePasswordsToFile(String chars, int length, String outputFileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            generate("", chars, length, writer);
            System.out.println("Пароли успешно записаны в файл: " + outputFileName);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }


    // Рекурсивный метод для генерации паролей
    private static void generate(String prefix, String chars, int length, BufferedWriter writer) throws IOException {
        if (prefix.length() == length) {
            writer.write(prefix);
            writer.newLine();
            return;
        }
        for (int i = 0; i < chars.length(); i++) {
            generate(prefix + chars.charAt(i), chars, length, writer);
        }
    }


    // Чтение символов из файла
    private static String readCharsFromFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName))).trim();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла символов: " + fileName, e);
        }
    }


    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Использование: ./app [passLength] [путь к файлу с символами] [путь к выходному файлу]");
            return;
        }


        try {
            // Получаем длину пароля
            int passwordLength = Integer.parseInt(args[0]);


            // Читаем символы из файла
            String charsFilePath = args[1];
            String chars = readCharsFromFile(charsFilePath);


            // Путь к выходному файлу
            String outputFilePath = args[2];


            // Проверяем, что длина символов больше нуля
            if (chars.isEmpty()) {
                System.err.println("Файл с символами пуст или содержит только пробелы.");
                return;
            }


            // Генерируем пароли и записываем их в файл
            generatePasswordsToFile(chars, passwordLength, outputFilePath);


        } catch (NumberFormatException e) {
            System.err.println("Длина пароля должна быть целым числом.");
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
    }
}

