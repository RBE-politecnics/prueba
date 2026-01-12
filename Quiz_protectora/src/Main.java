import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String path = "";
        int numpreguntes = 0;
        String [] preguntitas = new String [20];
        String [][] respuestitas = new String [20][3];
        String [] correctitas = new String [20];
        String username = "";
        String respuestaactual ="";
        int puntuaciototal = 0;
        double percentatgetotal = 0;
        boolean tornaracomencar = true;
        int cambiarboolean = 1;
        int camino = 0;
        String quizfet = "";


        username = pedirusername(username);
        do {
            path = escollircategoria(path,camino);

            numpreguntes = escollirnumpreguntes(numpreguntes);

            llegirpregunta(preguntitas, respuestitas, correctitas, path);

            randomitzarpreguntes(preguntitas, respuestitas, correctitas, numpreguntes);

            puntuaciototal = preguntarpreguntitas(preguntitas, respuestitas, correctitas, numpreguntes, respuestaactual, puntuaciototal);

            puntuaciototal = mostrarpuntuacio(puntuaciototal, percentatgetotal, numpreguntes);

            escriuretxt(path, quizfet, username, numpreguntes, puntuaciototal);

            tornaracomencar = tornaracomencar(tornaracomencar);
        } while(tornaracomencar == true);
    }

    public static void escriuretxt(String path, String quizfet, String username, int numpreguntes, int puntuaciototal) {

        LocalDateTime hora = LocalDateTime.now();

        if (path.equals("src/gats.txt")) {
            quizfet = "Gats";
        } else {
            quizfet = "Gossos";
        }

        try (FileWriter fw = new FileWriter("src/resultados.txt", true);
            PrintWriter writter = new PrintWriter(fw)) {

            System.out.println("Els resutats estan a src/resultats.txt");
            writter.println("=".repeat(20) + "RESULTATS" + "=".repeat(20));
            writter.println("NOM: " + username);
            writter.println("QUIZ FET: " + quizfet);
            writter.println("Numero d preguntes fetes: " + numpreguntes);
            writter.println("Respostes correctes: " + puntuaciototal);
            writter.printf("Dia i hora de finalitzacio: " + "%1$tT %1$td/%1$tm/%1$tY%n", hora);
            System.out.println("Els resutats estan a src/resultats.txt");

        }catch (Exception e) {
            System.out.println("Error al escriure: " + e.getMessage());
        }

    }

    public static boolean tornaracomencar(boolean tornaracomencar) {
        Scanner sc = new Scanner(System.in);
        int cambiarboolean;

        while (true) {
            System.out.println("Vols repetir el quiz? SI(1) NO(2)");

            if (sc.hasNextInt()) {
                cambiarboolean = sc.nextInt();
            } else {
                System.out.println("Resposta invalida");
                sc.next(); // descarta l'entrada incorrecta
                continue;
            }

            switch (cambiarboolean) {
                case 1:
                    tornaracomencar = true;
                    return tornaracomencar;
                case 2:
                    tornaracomencar = false;
                    return tornaracomencar;
                default:
                    System.out.println("Resposta invalida");
            }
        }
    }


    public static int mostrarpuntuacio(int  puntuaciototal, double percentatgetotal, double numpreguntes) {
        System.out.println("la teva puntuació es: " + puntuaciototal);
        percentatgetotal = ((double)puntuaciototal / numpreguntes) * 100;
        //        System.out.println("Has acertat el " + percentatgetotal + "%");
        if (percentatgetotal == 100) {
            System.out.println("PERFECTE, has acertat el 100%");
        } else if (percentatgetotal >= 67 && percentatgetotal <= 99) {
            System.out.println("MOLT BÉ, has encertat entre el 67 i el 99%");
        }else if (percentatgetotal >= 34 && percentatgetotal <= 66) {
            System.out.println("SUFICIENT, has encertat entre el 34 i el 66%");
        }else if (percentatgetotal >= 0 && percentatgetotal <= 33) {
            System.out.println("INSUFICIENT, has encertat entre el 33 i el 0%");
        }
        return puntuaciototal;
    }

    public static int preguntarpreguntitas(String[] preguntitas, String[][] respuestitas, String[] correctitas, int numpreguntes, String respuestaactual, int puntuaciototal) {
        for (int i = 0; i < numpreguntes; i++) {
            do {
                System.out.println(preguntitas[i]);
                System.out.println(respuestitas[i][0]);
                System.out.println(respuestitas[i][1]);
                System.out.println(respuestitas[i][2]);

                Scanner sc = new Scanner(System.in);
                respuestaactual = sc.nextLine();

                switch (respuestaactual) {
                    case "a","A":
                        respuestaactual = "A";
                        break;
                    case "b", "B":
                        respuestaactual = "B";
                        break;
                    case "c", "C":
                        respuestaactual = "C";
                        break;
                    default:
                        System.out.println("Resposta invalida");
                }                                                                                                           //=============================
            }while ((!respuestaactual.equals("A")) && (!respuestaactual.equals("B")) && (!respuestaactual.equals("C")));    //PREGUNTAR PER QUE && I NO ||
                                                                                                                            //=============================
            if (respuestaactual.equals(correctitas[i].toUpperCase())) {
                puntuaciototal++;
            }
        }
        return puntuaciototal;
    }

    public static void randomitzarpreguntes(String[] preguntitas, String[][] respuestitas, String[] correctitas, int numpreguntes) {

        int[] indexos = new int[20];                    // Crea un array de 20

        for (int i = 0; i < 20; i++) {                  // Omplim amb 0..19
            indexos[i] = i;
        }


        for (int i = 19; i > 0; i--) {                  //Resta 1 a i per cada iteracio del bucle
            int j = (int)(Math.random() * (i + 1));     //Crea un numero random del 1 al 20 i el guarda a j


            int temp = indexos[i];                      //Cambies i per j
            indexos[i] = indexos[j];
            indexos[j] = temp;
        }
    }

    public static int escollirnumpreguntes(int numpreguntes) {
       do {
           System.out.println("Cuantes preguntes vols respondre?");
           Scanner sc = new Scanner(System.in);
           try {
               numpreguntes = sc.nextInt();

               if (numpreguntes < 5 || numpreguntes > 20) {
                   System.out.println("escull un altre numero entre 5 i 20");
               }
           } catch (Exception e) {
               System.out.println("Torna a escollir");
           }
       } while (numpreguntes < 5 || numpreguntes > 20);
       return numpreguntes;
    }
    public static String escollircategoria(String path, int camino) {
        camino = 0;
        do {
            System.out.println("Vols cuidar d'un gat (1) o d'un gos (2) ?");
            Scanner sc = new Scanner(System.in);
            try {
            camino = sc.nextInt();
            switch (camino) {
                case 1:
                    path = "src/gats.txt";
                    break;
                case 2:
                    path = "src/gossos.txt";
                    break;
                default:
                    System.out.println("Torna a escollir");
            }
            } catch (Exception e) {
                System.out.println("Torna a escollir");
            }
        } while (camino < 1 || camino > 2);
        return path;
    }

    public static void llegirpregunta(String [] preguntitas, String[][] respuestitas, String[] correctitas, String path) {
        ArrayList<String> linies = new ArrayList<>();
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linia;
            while ((linia = br.readLine()) != null) {  // Leer línea a línea
                linies.add(linia);

                String[] dades = linia.split("\\|");  // Dividir por "|"
                 preguntitas[i] = dades[0];
                 respuestitas[i][0] = dades[1];
                 respuestitas[i][1] = dades[2];
                 respuestitas[i][2] = dades[3];
                 correctitas[i] = dades[4];
                i++;
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero:" + e.getMessage());
            return;
        }
    }

    public static String pedirusername(String username) {
        do {
            System.out.println("Introduce el nombre del usuario: ");
            Scanner sc = new Scanner(System.in);
            username = sc.nextLine();
        } while (username == null);
        return username;
    }

}