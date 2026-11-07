package org.example.Control;

import org.example.Model.Phong;
import org.example.Model.TaiSan;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ClientControl {
    private String serverHost = "localhost";
    private int serverPort = 1099;
    private String serviceName = "QuanLyTaiSanService";
    private RMIInterface rmiServer;

    public ClientControl() {
    }

    public ClientControl(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public boolean openConnection() throws Exception {
        try {
            Registry registry = LocateRegistry.getRegistry(serverHost, serverPort);

            rmiServer = (RMIInterface) registry.lookup("QuanLyTaiSanService");

            return rmiServer != null;

        } catch (Exception e) {
            rmiServer = null;

            throw new Exception(
                    "Không thể kết nối đến RMI Server tại "
                            + serverHost + ":" + serverPort
                            + ". " + e.getMessage(),
                    e
            );
        }
    }

    private void ensureConnected() throws Exception {
        if (rmiServer == null) {
            openConnection();
        }
    }

    public boolean suaPhong(Phong phong) throws Exception {
        ensureConnected();
        return rmiServer.suaPhong(phong);
    }

    public boolean suaTaiSan(TaiSan taiSan) throws Exception {
        ensureConnected();
        return rmiServer.suaTaiSan(taiSan);
    }

    public Phong getPhongByMa(String maPhong) throws Exception {
        ensureConnected();
        return rmiServer.getPhongByMa(maPhong);
    }

    public TaiSan getTaiSanByMa(String maTaiSan) throws Exception {
        ensureConnected();
        return rmiServer.getTaiSanByMa(maTaiSan);
    }

    public List<Phong> layTatCaPhong() throws Exception {
        ensureConnected();
        return rmiServer.layTatCaPhong();
    }

    public List<TaiSan> layTatCaTaiSan() throws Exception {
        ensureConnected();
        return rmiServer.layTatCaTaiSan();
    }

    public void closeConnection() {
        rmiServer = null;
    }
}
