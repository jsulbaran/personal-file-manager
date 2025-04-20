package com.jsulbaran.apps.personalfilemanager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jsulbaran.apps.personalfilemanager.dto.FileDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class HelloController implements Initializable {

    private final Logger log = LoggerFactory.getLogger(getClass());
    public static final String FILENAME = "files.txt";
    private final Gson mapper = new Gson();
    @FXML
    private Label welcomeText;
    @FXML
    private TextField searchInput;
    @FXML
    private ListView<FileDTO> filesList;
    private final ObservableList<FileDTO> observableList = FXCollections.observableArrayList();
    private final FilteredList<FileDTO> filteredList = new FilteredList<>(observableList);
    private Stage stage;
    private final Desktop desktop = Desktop.getDesktop();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void filterItems() {
        final String textFilter = searchInput.getText();
        if (textFilter == null || textFilter.trim().isEmpty()) {
            initializeList();
            filteredList.setPredicate(null);
        } else {
            Predicate<FileDTO> filter = t -> t.name().contains(textFilter);
            filteredList.setPredicate(filter);
        }
    }
// TODO avoid file duplication
    @FXML
    protected void onOpenFileClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        final File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            var path = file.getAbsolutePath();
            final String name = FilenameUtils.getName(path);
            System.out.println("file : " + path);
            final FileDTO newFile = new FileDTO(path, name);
            observableList.add(newFile);
//            filesList.getItems().add(newFile);
            writeNewItem(newFile);

        }
    }

    private void writeNewItem(FileDTO newFile) {
        final String content = mapper.toJson(newFile) + System.lineSeparator();
        try {
            Files.write(
                    Paths.get(FILENAME),
                    content.getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            log.error("Error opening file", ex);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeList();

        filesList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                final FileDTO selectedItem = filesList.getSelectionModel()
                        .getSelectedItem();
                System.out.println(selectedItem);
                openFile(Path.of(selectedItem.path()).toFile());

            }
        });
    }

    private void initializeList() {
        final File previousState = new File(FILENAME);
        if (!previousState.exists()) {
            createNewFile(previousState);
        } else {
            loadPreviousState(previousState);
        }


    }

    private void loadPreviousState(File previousState) {
        try {
            final List<String> strings = Files.readAllLines(previousState.toPath());
            final List<FileDTO> parsedList = strings.stream().map(this::parse).toList();
            observableList.clear();
            observableList.addAll(parsedList);
            filesList.setItems(filteredList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FileDTO parse(String item) {
        return mapper.fromJson(item, new TypeToken<>() {
        });
    }

    private void createNewFile(File previousState) {
        try {
            boolean newFile = previousState.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}