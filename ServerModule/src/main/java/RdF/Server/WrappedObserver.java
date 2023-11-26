package RdF.Server;
/*
import RdF.Client.ClientInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

public class WrappedObserver implements Observer, Serializable {

    private static final long serialVersionUID = 5L;

    private ClientInterface clientInterface = null;

    public WrappedObserver(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            clientInterface.remoteUpdate(o, arg);
        } catch (RemoteException e) {
            System.out.println("Remote exception removing observer:" + this);
            o.deleteObserver(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

 */