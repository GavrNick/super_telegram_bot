package org.example;

import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

public final double KELVIN = 273.15;
    @Override
    public String getBotUsername() {
        return "WeatherGavrBot";
    }

    @Override
    public String getBotToken() {
        return "5790335944:AAGrJmTf91aQHG-heKJi-K68gfZTnR8qq50";
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            List<City> list = new ArrayList<>();
            list.add(new City("Минск", "53.90453979999999", "27.561524400000053"));
            list.add(new City("Брест", "52.0975", "23.6877"));
            list.add(new City("Витебск", "55.1904", "30.2049"));
            list.add(new City("Гомель", "53.893009", "27.567444"));
            list.add(new City("Гродно", "53.6693538", "23.8131306"));
            list.add(new City("Могилёв", "53.9086685", "30.3429108"));
            list.add(new City("Могилев", "53.9086685", "30.3429108"));


            for (City c : list) {
                if (c.getName().equalsIgnoreCase(update.getMessage().getText())) {
                    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + c.getLat() + "&lon=" + c.getLon() + "&appid=63908483baab88f3eb6b426d8f3233bb");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((url.openStream())));
                    String data = bufferedReader.readLine();
                    JSONObject jsonObject = new JSONObject(data);
                    String main = jsonObject.get("main").toString();
                    jsonObject = new JSONObject(main);
                    String temp = jsonObject.get("temp").toString();

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(update.getMessage().getChatId());
                    sendMessage.setText("Погода в городе " + c.getName() + " = " + String.valueOf(Math.ceil(Double.parseDouble(temp)-KELVIN)) + " °С");

                    execute(sendMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
