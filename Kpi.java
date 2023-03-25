import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Kpi {

    public static void main(String[] arg) {
        int errors = 0;
        int success = 0;
        Double kpi = 0.0;
        File archivo = new File("./LogFile.log");

        try (FileReader fr = new FileReader(archivo);    
            BufferedReader br = new BufferedReader(fr)){            

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null)

                if (linea.contains("INFO") || linea.contains("INFORMACIÓN")) {
                    success += 1;
                } else if (linea.contains("SEVERE") || linea.contains("GRAVE")) {
                    errors += 1;
                }
            if (success+errors > 0){
                kpi = (double) success / (success + errors) * 100;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try (FileWriter fichero = new FileWriter("./Kpi.txt");
            PrintWriter pw = new PrintWriter(fichero)) {
            
            pw.println("Los hilos exitosos han sido: " + success);
            pw.println("Los hilos con errores han sido: " + errors);
            pw.println(String.format("Lo que supone una tasa de exito de : %.2f%s", kpi, "%"));

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
