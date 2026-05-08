package Imuniku.src.main;

import Imuniku.src.service.AnakService;
import Imuniku.src.driver.DatabaseConnection;
import Imuniku.src.dao.AnakDAO;
import Imuniku.src.model.Anak;

public class Main {

    public static void main(String[] args) {

        DatabaseConnection.getConnection();

            Anak anak = new Anak(
            0,
            "PeapDro",
            "2020-05-10",
            "L",
            "Margareth",
            "Medan"
);

            AnakDAO dao = new AnakDAO();

            dao.tambahAnak(anak);

            AnakService service = new AnakService();

                Anak anakUpdate = new Anak(
                2,
                "Ventyola",
                "2020-05-11",
                "L",
                "Abdul",
                "Bali"
            );

            dao.updateAnak(anakUpdate);

            service.tampilkanDataAnak();

      
    }
}