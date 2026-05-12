package Imuniku.src.util;

import java.util.Scanner;

/**
 * Helper input console
 */
public class InputHelper {

    private Scanner scanner;

    public InputHelper() {

        scanner = new Scanner(System.in);
    }

    public String readString(String pesan) {

        System.out.print(pesan);

        return scanner.nextLine().trim();
    }

    public int readInt(String pesan) {

        while (true) {

            try {

                System.out.print(pesan);

                return Integer.parseInt(
                    scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                    "[ERROR] Input harus angka!"
                );
            }
        }
    }

    public void close() {

        scanner.close();
    }
}