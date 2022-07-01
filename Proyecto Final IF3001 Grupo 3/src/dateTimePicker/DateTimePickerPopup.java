package dateTimePicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.control.DateCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

public class DateTimePickerPopup extends VBox implements Initializable {

    private final DateTimePicker parentControl;
    private final HoursPicker hoursPicker;
    private final MinuteSecondPicker minutesPicker;
  //  private final MinuteSecondPicker secondsPicker;

    private int hour;
    private int minute;
    private int second;

    @FXML
    private Accordion accordion;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TitledPane dateTitledPane;
    @FXML
    private TitledPane timeTitledPane;
    @FXML
    private HBox timeButtonsPane;
    @FXML
    private Button hoursButton;
    @FXML
    private Button minutesButton;
    @FXML
    private Button secondsButton;
    @FXML
    private Button okButton;

    public DateTimePickerPopup(final DateTimePicker parentControl) {
        this.hour = parentControl.dateTimeProperty().get().getHour();
        this.minute = parentControl.dateTimeProperty().get().getMinute();
        //this.second = parentControl.dateTimeProperty().get().getSecond();

        this.parentControl = parentControl;
        this.hoursPicker = new HoursPicker(this);
        this.minutesPicker = new MinuteSecondPicker(this);

        // Load FXML
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DateTimePickerPopup.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            // Should never happen.  If it does however, we cannot recover
            // from this
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the button text
        setTimeButtonText();

        // Initialize date to value in parent control
        datePicker.valueProperty().set(parentControl.dateTimeProperty().get().toLocalDate());
        
        //Para darle una forma al dataPicker
       Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
        public void updateItem(LocalDate item, boolean empty) {

        super.updateItem(item, empty);
        
        this.setDisable(false);
        this.setBackground(null);
        this.setTextFill(Color.BLACK);

        // deshabilitar las fechas futuras
        if (item.isBefore(LocalDate.now())) {
            this.setDisable(true);
        }

        // marcar los dias de quincena
        DayOfWeek day = item.getDayOfWeek();
        if(day == DayOfWeek.FRIDAY) {
//            Paint color = Color.RED;
//            BackgroundFill fill = new BackgroundFill(color, null, null);
            this.setTextFill(Color.CORAL);
//            this.setBackground(new Background(fill));
//            this.setTextFill(Color.WHITESMOKE);
        }
        
        // fines de semana en color verde
        DayOfWeek dayweek = item.getDayOfWeek();
        if (dayweek == DayOfWeek.SATURDAY || dayweek == DayOfWeek.SUNDAY) {
                Paint color = Color.CHOCOLATE;
            BackgroundFill fill = new BackgroundFill(color, null, null);
            this.setBackground(new Background(fill));
            this.setTextFill(Color.WHITE);
            this.setDisable(true);
        }
    }
    };
       
       datePicker.setDayCellFactory(dayCellFactory);
        

        // Start with time pane expanded
        accordion.setExpandedPane(accordion.getPanes().get(1));

        // Listen to the dateTimeProperty changes and update hours picker
        parentControl.dateTimeProperty().addListener((observable, oldValue, newValue) -> hoursPicker.setHour(newValue.getHour()));

        hoursButton.setOnAction(this::handleHoursButtonAction);
        minutesButton.setOnAction(this::handleMinutesButtonAction);
    //    secondsButton.setOnAction(this::handleSecondsButtonAction);
        okButton.setOnAction(this::handleOkButtonAction);

        Locale locale = Locale.getDefault();

            dateTitledPane.setText("Date");
            timeTitledPane.setText("Time");
            okButton.setText("OK");
        
    }

    void setDate(final LocalDate date) {
        datePicker.setValue(date);
    }

    LocalDate getDate() {
        return datePicker.getValue();
    }

    void setTime(final LocalTime time) {
        hour = time.getHour();
        minute = time.getMinute();
      //  second = time.getSecond();
        setTimeButtonText();
    }

    LocalTime getTime() {
        return LocalTime.of(hour, minute);
    }

    int getHour() {
        return hour;
    }

    void restoreTimePanel() {
        // Update hour
        hour = hoursPicker.getHour();
        minute = minutesPicker.getValue();
       // second = secondsPicker.getValue();
        setTimeButtonText();

        // Restore original panel
        timeTitledPane.setContent(timeButtonsPane);
    }

    @FXML
    public void handleHoursButtonAction(ActionEvent actionEvent) {
        timeTitledPane.setContent(hoursPicker);
    }

    @FXML
    public void handleMinutesButtonAction(ActionEvent actionEvent) {
        timeTitledPane.setContent(minutesPicker);
    }

//    @FXML
//    public void handleSecondsButtonAction(ActionEvent actionEvent) {
//        timeTitledPane.setContent(secondsPicker);
//    }

    @FXML
    public void handleOkButtonAction(ActionEvent actionEvent) {
        hour = hoursPicker.getHour();
        setTimeButtonText();

        parentControl.hidePopup();
    }

    private void setTimeButtonText() {
        hoursButton.setText(String.format("%02d", hour));
        minutesButton.setText(String.format("%02d", minute));
       secondsButton.setText(String.format("%02d", second));
    }
}
