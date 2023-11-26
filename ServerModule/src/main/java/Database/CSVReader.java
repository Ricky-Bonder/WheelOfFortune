package Database;


import RdF.Server.ServerInterface;
import RdF.Server.ServerRDF;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVReader {

    ServerRDF serverRDF;
    DBHelper dbHelper;
    ArrayList<String> hintPhrase;

    /** Ritorna un ArrayList con gli Hints e Phrases delle frasi prese dal .csv **/
    public ArrayList<String> CSVReader(String Path) throws Exception {
        try {
            dbHelper = new DBHelper(serverRDF);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        hintPhrase = new ArrayList<>();
        int i = 0;
        Scanner sc = null;
        try {
            sc = new Scanner(new File(""+Path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.useDelimiter(",");   //sets the delimiter pattern
        while (sc.hasNext())  //returns a boolean value
        {
            hintPhrase.add(i, sc.next());
            hintPhrase.add(i+1, sc.next());
            try {
                dbHelper.insertPhrases(hintPhrase.get(i), hintPhrase.get(i+1));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(hintPhrase.get(i));
            System.out.println(hintPhrase.get(i+1));
        }
        sc.close();  //closes the scanner
        return hintPhrase;
    }
}
