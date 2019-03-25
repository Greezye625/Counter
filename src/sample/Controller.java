package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    private long diff;
    private long diffSeconds;
    private long diffMinutes;
    private long diffHours;
    private long diffDays;
    private LocalDateTime date = LocalDateTime.now();

    @FXML
    private Label daysLabel;

    @FXML
    private Label hoursLabel;

    @FXML
    private Label minutesLabel;

    @FXML
    private Label secondsLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label dlabel;

    @FXML
    private Label hlabel;

    @FXML
    private Label mlabel;

    @FXML
    private Label slabel;

    public void initialize() {
        int year = date.getYear();

        if (date.getDayOfYear() != 20) {
            if (date.getDayOfYear() > 20) {
                year++;
            }
            String dateStop = "20/1/" + year + " 00:00:00";

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            refresh();
                        }
                    });

                }
            }, 0, 1000);


            Runnable runnable =
                    new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                date = LocalDateTime.now();
                                if (date.getDayOfYear() == 20) {
                                    anniversary();
                                    break;
                                }
                                StringBuilder sb = new StringBuilder();
                                sb.append(date.getDayOfMonth());
                                sb.append("/");
                                sb.append(date.getMonthValue());
                                sb.append("/");
                                sb.append(date.getYear());
                                sb.append(" ");
                                sb.append(date.getHour());
                                sb.append(":");
                                sb.append(date.getMinute());
                                sb.append(":");
                                sb.append(date.getSecond());

                                String dateStart = String.valueOf(sb);

                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                                Date d1 = null;
                                Date d2 = null;

                                try {
                                    d1 = format.parse(dateStart);
                                    d2 = format.parse(dateStop);

                                    //in milliseconds
                                    diff = d2.getTime() - d1.getTime();
                                    diffSeconds = diff / 1000 % 60;
                                    diffMinutes = diff / (60 * 1000) % 60;
                                    diffHours = diff / (60 * 60 * 1000) % 24;
                                    diffDays = diff / (24 * 60 * 60 * 1000);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };

            Thread thread = new Thread(runnable);
            thread.start();
        } else {
            anniversary();
        }
    }

    private void refresh() {
        daysLabel.setText(String.valueOf(diffDays));
        hoursLabel.setText(String.valueOf(diffHours));
        minutesLabel.setText(String.valueOf(diffMinutes));
        secondsLabel.setText(String.valueOf(diffSeconds));
    }

    private void anniversary() {
        daysLabel.setOpacity(0);
        hoursLabel.setOpacity(0);
        minutesLabel.setOpacity(0);
        secondsLabel.setOpacity(0);
        dlabel.setOpacity(0);
        hlabel.setOpacity(0);
        mlabel.setOpacity(0);
        slabel.setOpacity(0);
        messageLabel.setPrefWidth(500);
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setText("!!!Birthday!!!");
    }
}
