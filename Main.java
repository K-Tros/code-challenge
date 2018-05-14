import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        // error check number of arguments (must be 1 arguments: template-name)
        if (args.length != 1) {
            System.out.println("Invalid number of arguments.");
            return;
        }

        // check if template file exist
        File templateFile = new File(args[0]);

        if (!templateFile.exists()) {
            System.out.println("Template file does not exist.");
            return;
        }

        // turn data file into a map
        HashMap<String, String> dataMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] splitString = line.split("=");
            // Ignore any incorrectly formatted input data.
            // Must be "key=value" format, one per line.
            if (splitString.length == 2) {
                dataMap.put(splitString[0].trim(), splitString[1].trim());
            }
        }

        // get the whole template as a string
        try {
            String template = new String(Files.readAllBytes(Paths.get(args[0])));
            // for each key-value in the map, replace all instances of the key
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                template = template.replace("((" + entry.getKey() + "))", entry.getValue());
            }

            // if the template string does not still contain any double parens, print the filled in template
            if (!(template.contains("((") || template.contains("))"))) {
                System.out.print(template);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

    }
}
