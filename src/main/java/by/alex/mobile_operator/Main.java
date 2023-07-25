package by.alex.mobile_operator;

import by.alex.mobile_operator.service.UIService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        UIService uiService = new UIService();

        uiService.start();
    }
}
