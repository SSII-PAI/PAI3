import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class Kpi {
    private static final Logger log = Logger.getLogger(Kpi.class.getName());

    public static void main(String[] arg) {
        int errors = 0;
        int success = 0;
        Double kpi = 0.0;
        File archivo = new File("./LogFile.log");

        try (FileReader fr = new FileReader(archivo);
                BufferedReader br = new BufferedReader(fr)) {

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null)

                if (linea.contains("INFO") || linea.contains("INFORMACIÓN")) {
                    // Contar hilos exitosos
                    success += 1;
                } else if (linea.contains("SEVERE") || linea.contains("GRAVE")) {
                    // Contar hilos con errores
                    errors += 1;
                }
            if (success + errors > 0) {
                // Calcular kpi
                kpi = (double) success / (success + errors) * 100;
            }

        } catch (IOException e) {
            log.severe(e.getMessage());
        }
        try (FileWriter fichero = new FileWriter("./Kpi.txt");
                PrintWriter pw = new PrintWriter(fichero)) {

            // Escritura del fichero
            pw.println("Los hilos exitosos han sido: " + success);
            pw.println("Los hilos con errores han sido: " + errors);
            pw.println(String.format("Lo que supone una tasa de exito de : %.2f%s", kpi, "%"));

        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }
}
