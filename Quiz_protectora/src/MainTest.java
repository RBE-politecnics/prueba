import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;

public class MainTest {

    // 1. Prova unitària per mostrarpuntuacio
    @Test
    void testMostraPuntuacio_BonsICasDolent() {
        // Cas bo: puntuació 5 sobre 5
        int total = Main.mostrarpuntuacio(5, 0, 5);
        assertEquals(5, total);

        // Cas dolent: puntuació 0 sobre 5
        total = Main.mostrarpuntuacio(0, 0, 5);
        assertNotEquals(5, total);
    }

    // 2. Prova unitària per preguntarpreguntitas
    @Test
    void testPreguntarPreguntitas() {

        // ---------- CAS BO: resposta correcta ----------
        String inputCorrecta = "A\n";  // simulació d’usuari escrivint "A"
        System.setIn(new java.io.ByteArrayInputStream(inputCorrecta.getBytes()));

        String[] preguntes = {"Pregunta 1"};
        String[][] respostes = {{"A", "B", "C"}};
        String[] correctes = {"A"};

        int puntuacio = Main.preguntarpreguntitas(
                preguntes, respostes, correctes, 1, "", 0
        );

        assertEquals(1, puntuacio);


        // ---------- CAS DOLENT: resposta incorrecta ----------
        String inputIncorrecta = "B\n"; // simulació d’usuari escrivint "B"
        System.setIn(new java.io.ByteArrayInputStream(inputIncorrecta.getBytes()));

        puntuacio = Main.preguntarpreguntitas(
                preguntes, respostes, correctes, 1, "", 0
        );

        assertEquals(0, puntuacio);
    }


    // 3. Prova unitària per escriuretxt
    @Test
    void testEscriureTxt() throws IOException {
        String path = "src/gats.txt";
        String quiz = "";
        String user = "TestUser";
        int num = 3;
        int puntuacio = 2;

        // Comprovem que el fitxer s'escriu sense errors
        Main.escriuretxt(path, quiz, user, num, puntuacio);

        // Llegim l'última línia per assegurar que s'ha escrit alguna cosa
        BufferedReader br = new BufferedReader(new FileReader("src/resultados.txt"));
        String line = null, ultima = null;
        while ((line = br.readLine()) != null) {
            ultima = line;
        }
        br.close();

        assertNotNull(ultima); // L'última línia no ha de ser null
    }
}
