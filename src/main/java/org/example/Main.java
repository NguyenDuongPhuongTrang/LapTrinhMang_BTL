
package org.example;

import org.example.rmi.ClientRun;
import org.example.rmi.ServerRun;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("BTL LẬP TRÌNH MẠNG: QUẢN LÝ TÀI SẢN TRONG PHÒNG");
        System.out.println("Giao thức: RMI (Remote Method Invocation)");
        System.out.println("==================================================");
        System.out.println("1. Chạy Server");
        System.out.println("2. Chạy Client");
        System.out.println("==================================================");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Chọn chế độ (1/2): ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.println("Đang khởi động Server...");
            ServerRun.main(args);
        } else if (choice.equals("2")) {
            System.out.println("Đang khởi động Client...");
            ClientRun.main(args);
        } else {
            System.out.println("Lựa chọn không hợp lệ!");
        }
    }
}
