package GUI;
/*
import RdF.Client.ClientRDF;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import static RdF.Client.ClientRDF.server;

public class  GameManager {

    public String idUsers;
    public String idPlayer;
    public String idManche;

    public ArrayList<String> getHintAndPhrase() {
        return HintAndPhrase;
    }

    public void setHintAndPhrase(ArrayList<String> hintAndPhrase) {
        HintAndPhrase = hintAndPhrase;
    }

    ArrayList<String> HintAndPhrase = new ArrayList<>();

    public GameManager(String idUser) throws RemoteException, SQLException, InterruptedException {
        ArrayList<String> aaaaa = new ArrayList<>();
        idUsers = idUser;
        server = ClientRDF.getServer();
        System.out.println("IDUSER: " + idUser);
        idPlayer = server.getMyIDPlayerS(idUsers);
        System.out.println("getmyidplayers " + idPlayer);
        idManche = server.getMyIDMancheS(idUser);
        System.out.println("getmyidmanche " + idManche);
        aaaaa.add(server.getMyGame(idPlayer).getHintPhrase());
        aaaaa.add(server.getMyGame(idPlayer).getMysteriousPhrase());
        System.out.println("questo è aaaaaa " + aaaaa.get(0) + " e frase " + aaaaa.get(1));
        setHintAndPhrase(aaaaa);
        new Gioco(getHintAndPhrase(), idUser, this);
    }

}

 */