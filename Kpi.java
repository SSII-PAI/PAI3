import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Kpi {

    public static void main(String[] arg) {
        File archivo = null;
        FileReader fr = null;
        int errors = 0;
        int success = 0;
        Double kpi = 0.0;

        try (BufferedReader br = new BufferedReader(fr)) {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("./LogFile.log");
            fr = new FileReader(archivo);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null)

                if (linea.contains("INFO")) {
                    success += 1;
                } else if (linea.contains("SEVERE")) {
                    errors += 1;
                }
            kpi = (double) success / (success + errors) * 100;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        FileWriter fichero = null;
        try (PrintWriter pw = new PrintWriter(fichero)) {
            fichero = new FileWriter("./Kpi.txt");
            pw.println("Los hilos exitosos han sido: " + success);
            pw.println("Los hilos con errores han sido: " + errors);
            pw.println(String.format("Lo que supone una tasa de exito de : %.2f%s", kpi, "%"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
