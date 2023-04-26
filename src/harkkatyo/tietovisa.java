/**
 * 
 */

package harkkatyo;

import java.util.Scanner;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * This class is a trivia game.
 * 
 * @author Ilona Kauppila, Charlotte Levula, Jessica Laamanen, Saana
 *         Paaso-Rantala
 * @version 1.0
 * @since 2023-04-20
 * 
 */

public class tietovisa {

    private static ArrayList<String> kaikkiKysymykset = new ArrayList<String>();
    private static ArrayList<String> vastaukset = new ArrayList<String>();

    public static final Scanner scanner = new Scanner(System.in);
    public static Random random = new Random();

    /**
     * The Main method starts the Quiz.
     * 
     * This method asks user whether they would like to read the instructions for
     * the game, asks user's name, picks which area of questions user would prefer,
     * reads the picked questions and answers froom a external file, and plays the
     * quiz game.
     * 
     * @param args   The command-line argument passses to the program.
     * @param answer Takes answer y or n from user's input.
     * @param area   Takes the given area of questions (1 or 2).
     */
    public static void main(String[] args) {
        System.out.println("Tervetuloa tietovisaan!");
        System.out.println("Haluatko lukea käyttöohjeet ennen kuin aloitat pelin? (y/n)");
        String answer = scanner.next();
        scanner.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            printManual();
        } else if (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
            System.out.print("Vastaus ei kelpaa. Haluatko lukea käyttöohjeet ennen kuin aloitat pelin? (y/n)");
        }
        askUsersName();
        int area = pickArea();
        readQuestions(area);
        playGame(kaikkiKysymykset, vastaukset, area);

    }

    /**
     * This method prints the manual for the Quiz and asks users to confirm they
     * have read the instructions pressing "Q" and want to continue to the actual
     * game.
     * 
     * @param answer Takes the continue command "Q" from user's input.
     */
    public static void printManual() {
        System.out.println("Pelin käyttöohjeet:");
        System.out.println("Peli pyytää sinua syöttämään nimesi ja painamaan enter.");
        System.out.println("Sen jälkeen saat valita kysymysten kategorian: 1. Yleistieto tai 2. Elokuvat.");
        System.out.println("Kysymykset arvotaan satunnaisessa järjestyksessä.");
        System.out.println("Peli ilmoittaa jokaisen kysymyksen jälkeen onko vastauksesi oikein vai väärin.");
        System.out.println("Vastattuasi viiteen kysymykseen, peli päättyy.");
        System.out.println("Lopuksi näet pisteesi.");
        System.out.println("Syötä Q ja paina enter jatkaaksesi.");

        String answer = scanner.next();

        if (!answer.equalsIgnoreCase("q")) {
            System.out.println("Syöte ei kelpaa. Syötä Q jatkaaksesi.");
        }
    }

    /**
     * Method asks player's name and writes it to the file.
     * 
     * @param name Takes user's input.
     */
    public static void askUsersName() {
        System.out.println("Anna nimesi: ");
        String name = scanner.next();

        if (name != null) {
            try {
                FileWriter writer = new FileWriter("pelaajatiedot.txt", true);
                writer.write("Pelaaja :" + name + "\n");
                writer.close();
            } catch (IOException e) {
                System.out.println("Jotain meni pieleen, yritä uudelleen.");
                e.printStackTrace();
            }
        }

    }

    /**
     * The method returns the area of questions user has picked.
     * 
     * @param area Takes user's input for area (1 or 2).
     * @return Returns the picked area.
     */
    public static int pickArea() {
        System.out.println("Millaisiin kysymyksiin haluaisit vastata? Valitse aihealue painamalla 1 tai 2.");
        System.out.println("1. Yleistieto");
        System.out.println("2. Elokuvat");
        int area = scanner.nextInt();

        if (area == 1 || area == 2) {
            return area;
        } else {
            System.out.println("Virheellinen vastaus. Valitse 1 tai 2.");
            return pickArea();
        }
    }

    /**
     * Method reads question from the file depending of which area has been chosen
     * (1 or 2).
     * 
     * @param kysymysrivi Reads a question from the file..
     * @param vastausrivi Reads an answer from the file.
     * @return Returns ArrayList which has been filled with questions from the file.
     */
    public static ArrayList<String> readQuestions(int area) {

        if (area == 1) {
            try (BufferedReader kysymysLukija = new BufferedReader(new FileReader("kysymystiedosto1.txt"));
                    BufferedReader vastausLukija = new BufferedReader(new FileReader("vastaustiedosto1.txt"))) {
                String kysymysrivi;
                String vastausrivi;

                while ((kysymysrivi = kysymysLukija.readLine()) != null) {
                    vastausrivi = vastausLukija.readLine().trim();
                    kaikkiKysymykset.add(kysymysrivi);
                    vastaukset.add(vastausrivi);
                }

            } catch (FileNotFoundException e) {
                System.out.println("Tapahtui virhe. Tiedostoa ei löydy.");
            } catch (IOException e) {
                System.out.println("Tapahtui virhe.");
            }
        }

        if (area == 2) {
            try (BufferedReader kysymysLukija = new BufferedReader(new FileReader("kysymystiedosto2.txt"));
                    BufferedReader vastausLukija = new BufferedReader(new FileReader("vastaustiedosto2.txt"))) {
                String kysymysrivi;
                String vastausrivi;

                while ((kysymysrivi = kysymysLukija.readLine()) != null) {
                    vastausrivi = vastausLukija.readLine().trim();
                    kaikkiKysymykset.add(kysymysrivi);
                    vastaukset.add(vastausrivi);
                }

            } catch (FileNotFoundException e) {
                System.out.println("Tapahtui virhe. Tiedostoa ei löydy.");
            } catch (IOException e) {
                System.out.println("Tapahtui virhe.");
            }
        }

        return kaikkiKysymykset;
    }

    /**
     * The method gives a random number that will determine the question.
     * 
     * @param numero The variable that stores the random number.
     * @return Returns the value of the variable numero.
     */
    public static int randomQuestion() {

        int numero = random.nextInt(kaikkiKysymykset.size());
        return numero;
    }

    /**
     * The method prints the questions and accepts the answer as user input, after
     * which it will compare the answer to the correct answer got from the answers
     * file.
     * Once the player has answered the number of questions set with the
     * kysymystenMaara variable, the method announces the game is over and prints
     * the player's
     * points, and writes them in the file.
     * 
     * @param kysymystenMaara how many questions will be asked
     * @param kaytettyLuku    keeps track of how many questions have been asked
     * @param pisteet         keeps track of the points the player has gotten from
     *                        correct answers
     * @param indeksi         the index of the question being asked, the row number
     *                        of it
     * @param kysymys         the question read from the questions file
     * @param oikeaVastaus    the correct answer read from the answers file
     * @param vastaus         the answer input given by the player
     */
    public static void playGame(ArrayList<String> kysymykset, ArrayList<String> vastaukset, int area) {
        int kysymystenMaara = 5;
        int kaytettyLuku = 0;
        int pisteet = 0;
        ArrayList<Integer> usedIndexes = new ArrayList<Integer>();

        System.out.println(
                "Anna vastaukset kokonaisina sanoina, ei numeroita käyttäen. Isot kirjaimet jätetään huomiotta.");

        while (kaytettyLuku < kysymystenMaara) {
            int indeksi = randomQuestion();
            if (usedIndexes.contains(indeksi)) {
                continue;
            }
            usedIndexes.add(indeksi);

            String kysymys = kysymykset.get(indeksi);
            String oikeaVastaus = vastaukset.get(indeksi).trim();

            System.out.println("Kysymys: " + kysymys);
            String vastaus = scanner.next().trim().toLowerCase();
            scanner.nextLine();

            if (vastaus.trim().equalsIgnoreCase(oikeaVastaus.trim())) {
                System.out.println("Oikein!");
                pisteet++;
            } else {
                System.out.println("Väärin!");
            }

            kaytettyLuku++;
        }

        System.out.println("Peli on ohi! Sait yhteensä " + pisteet + " pistettä.");

        try {
            FileWriter writer = new FileWriter("pelaajatiedot.txt", true);
            writer.write("Pisteet: " + pisteet + "\n" + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Jotain meni pieleen, yritä uudelleen.");
            e.printStackTrace();
        }

    }

}
