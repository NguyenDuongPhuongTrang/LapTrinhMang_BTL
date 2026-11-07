package org.example.rmi;

import org.example.Control.ServerControl;
import org.example.View.ServerView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRun {
    public static void main(String[] args) {
        int port = 1099;
        String serviceName = "QuanLyTaiSanService";

        ServerView view = new ServerView();
        view.showMessage("==================================================");
        view.showMessage("KHỞI CHẠY HỆ THỐNG QUẢN LÝ TÀI SẢN TRONG PHÒNG");
        view.showMessage("Giao thức: RMI (Remote Method Invocation)");
        view.showMessage("==================================================");

        try {
            ServerControl serverControl = new ServerControl(view);

            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(port);
                view.showMessage("Đã tạo mới RMI Registry tại cổng " + port);
            } catch (Exception e) {
                registry = LocateRegistry.getRegistry(port);
                view.showMessage("Đã kết nối tới RMI Registry sẵn có tại cổng " + port);
            }

            registry.rebind(serviceName, serverControl);
            registry.rebind("RMIInterface", serverControl);

            view.showMessage("Đăng ký thành công dịch vụ: '" + serviceName + "' và 'RMIInterface'");
            view.showMessage("RMI Server đang lắng nghe và sẵn sàng phục vụ các yêu cầu từ Client!");
            view.showMessage("--------------------------------------------------");
        } catch (Exception e) {
            view.showMessage("LỖI KHỞI CHẠY RMI SERVER: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

