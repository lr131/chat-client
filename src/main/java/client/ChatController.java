package client;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Контроллер чата.
 *
 * @author Kristina Retivykh
 */
public class ChatController {

    public TextArea chatArea;
    public TextField message;
    private Date currentDate;
    private DateFormat dateFormatter = new SimpleDateFormat(
            "dd MMM yyyy");
    private DateFormat timeFormatter = new SimpleDateFormat(
            "HH:mm");

    /**
     * Метод отправляет сообщение по нажатию кнопки "Send".
     * Под отправкой понимается перемещение сообщения в общее текстовое поле.
     *
     * @param actionEvent
     *        событие, возникающее при нажатии на кнопку "Send".
     */
    public void sendMessage(ActionEvent actionEvent) {
        send();
    }

    /**
     * Обрабатывает события нажатия клавиш. В случае нажатия клавиши Enter
     * производит отправку сообщения.
     *
     * @param keyEvent
     *        событие нажатия клавиши на клавиатуре.
     */
    public void inputMessage(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            send();
        }
    }

    /**
     * Метод отправляет сообщение.
     * Под отправкой понимается перемещение сообщения в общее текстовое поле.
     */
    private void send() {
        Date date = new Date();
        String history = chatArea.getText();
        if (history.isEmpty() || isNewDay(date)) {
            //если переписка только началась, то помечаем начало переписки
            // или если в процессе переписки наступили новые сутки
            history = dateFormatter.format(date) + "\n";
            currentDate = date;
        }
        chatArea.setText(history + "(" + timeFormatter.format(date) + ") You: "
                + message.getText() + "\n");
        message.clear();
    }

    /**
     * Устанавливает, наступили ли новые сутки или ещё нет.
     * @param date
     *        дата отправки сообщения.
     * @return {@code true} новые сутки наступили.
     *         {@code false} новые сутки не наступили.
     */
    private boolean isNewDay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String currentDateStr = formatter.format(currentDate);
        String newDateStr = formatter.format(date);

        Date current = null;
        Date newDate = null;

        try {
            current = formatter.parse(currentDateStr);
            newDate = formatter.parse(newDateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Количество дней между датами в миллисекундах
        long diff = newDate.getTime() - current.getTime();
        // Перевод количества дней между датами из миллисекунд в дни
        int days =  (int)(diff / (24 * 60 * 60 * 1000));

        return days > 0;
    }
}
