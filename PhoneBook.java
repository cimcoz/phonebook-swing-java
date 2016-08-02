import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * @author Alexander Tsupko (alexander.tsupko@outlook.com)
 *         Copyright (c) All rights reserved.
 */
public class PhoneBook extends JFrame {
    // instance variables

    private TreeSet<PhoneRecord> phoneBook;
    private JCheckBox jCheckBox;
    private JTextArea jTextArea, jTextAreaSearchResults;
    private JTextField jTextFieldName, jTextFieldPhone, jTextFieldSearch;

    private static String[] names = {
        "Цупко А.В.", "Анфимов Л.В.", "Денисов Г.Л.", "Оверина О.П.", "Ипатов А.Л.",
        "Денисова Л.В.", "Уразова Л.В.", "Якин Н.М.", "Смирнов П.И.", "Фошка С.И.",
        "Шаповалов А.П.", "Смирнов Г.С.", "Ярошевич В.Я.", "Борисов В.Н.", "Зайцев Н.В.",
        "Ковалёв А.С.", "Радченко Р.И.", "Николаенко В.А.", "Гончаров В.В.", "Петровский Н.И.",
        "Хрунин П.С.", "Юрьева В.П.", "Цупко А.А.", "Егоров В.П.", "Оверин Л.П.",
        "Лисицын В.Н.", "Эйсмонт О.Н.", "Жирихин К.А.", "Оверин Н.Н.", "Щепкин В.Л.",
        "Зайцев Н.В.", "Войцеховский Е.В.", "Магонов В.П.", "Ткаченко С.И.", "Чмыхалова Т.А."
    };
    private static String[] phones = {
        "+7 (978) 221-21-67", "+7 (102) 333-11-22", "+7 (918) 326-17-15", "+7 (978) 613-15-27", "+7 (978) 667-13-20",
        "+7 (918) 356-48-16", "+7 (978) 246-99-00", "+7 (033) 888-15-12", "+7 (042) 691-18-18", "+7 (978) 391-00-16",
        "+7 (978) 267-55-55", "+7 (548) 321-98-14", "+7 (040) 315-41-11", "+7 (222) 481-72-19", "+7 (086) 444-16-18",
        "+7 (918) 956-44-17", "+7 (644) 921-99-18", "+7 (041) 356-11-10", "+7 (646) 511-87-13", "+7 (091) 356-99-11",
        "+7 (022) 315-49-15", "+7 (643) 359-88-16", "+7 (978) 206-09-13", "+7 (978) 132-66-39", "+7 (032) 527-11-46",
        "+7 (102) 113-13-11", "+7 (777) 777-11-11", "+7 (613) 555-48-13", "+7 (113) 136-47-86", "+7 (102) 333-15-18",
        "+7 (044) 336-47-58", "+7 (826) 133-49-99", "+7 (049) 111-98-15", "+7 (329) 656-11-98", "+7 (115) 226-11-90"
    };

    // constructor

    private PhoneBook() {
        this.phoneBook = new TreeSet<>(); // initializing the set to contain phone records

        // Creating the window

        setSize(new Dimension(600, 800));
        setLocationRelativeTo(null);
        setTitle("Телефонный справочник");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Creating the left panel and adding the text area to it

        JPanel jPanelLeft = new JPanel(new BorderLayout());
        jPanelLeft.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
        add(jPanelLeft, BorderLayout.WEST);

        jTextArea = new JTextArea(22, 22);
        jTextArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jPanelLeft.add(jScrollPane);

        // Creating the right panel and adding the text fields to it

        JPanel jPanelRight = new JPanel(new BorderLayout());
        jPanelRight.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(jPanelRight, BorderLayout.CENTER);

        JPanel jPanelFieldsAndButtons = new JPanel(new BorderLayout());
        jPanelRight.add(jPanelFieldsAndButtons, BorderLayout.NORTH);

        JPanel jPanelFields = new JPanel(new BorderLayout());
        jPanelFieldsAndButtons.add(jPanelFields, BorderLayout.NORTH);

        JPanel jPanelName = new JPanel(new BorderLayout());
        jPanelFields.add(jPanelName, BorderLayout.NORTH);

        JLabel jLabelName = new JLabel("Ф.И.О.: ");
        jPanelName.add(jLabelName, BorderLayout.WEST);

        jTextFieldName = new MyTextField(18, "Введите фамилию И.О...");
        jTextFieldName.addActionListener(e -> onAddAction()); // add the record if Enter pressed on the name field
        jPanelName.add(jTextFieldName);

        JPanel jPanelPhone = new JPanel(new BorderLayout());
        jPanelFields.add(jPanelPhone, BorderLayout.SOUTH);

        JLabel jLabelPhone = new JLabel("Номер: ");
        jPanelPhone.add(jLabelPhone, BorderLayout.WEST);

        jTextFieldPhone = new MyTextField(18, "Введите номер телефона...");
        jTextFieldPhone.addActionListener(e -> onAddAction()); // add the record if Enter pressed on the phone field
        jPanelPhone.add(jTextFieldPhone);

        // Adding buttons with their functionality to the right panel

        JPanel jPanelButtons = new JPanel(); // FlowLayout() by default
        jPanelFieldsAndButtons.add(jPanelButtons, BorderLayout.CENTER);

        JButton jButtonAdd = new JButton("Добавить");
        jButtonAdd.addActionListener(e -> onAddAction()); // add the record if the button Add pressed
        jButtonAdd.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onAddAction(); // add the record if Enter pressed on the button Add
                }
            }
        });
        jPanelButtons.add(jButtonAdd);

        JButton jButtonRemove = new JButton("Удалить");
        jButtonRemove.addActionListener(e -> onRemoveAction()); // remove the record if the button Remove pressed
        jButtonRemove.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onRemoveAction(); // remove the record if Enter pressed on the button Remove
                }
            }
        });
        jPanelButtons.add(jButtonRemove);

        jPanelFieldsAndButtons.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.SOUTH); // add the separator

        // Adding the search field and the search results text area

        JPanel jPanelSearch = new JPanel(new BorderLayout());
        jPanelRight.add(jPanelSearch, BorderLayout.CENTER);

        jTextFieldSearch = new MyTextField(18, "Введите строку для поиска...");
        jTextFieldSearch.addActionListener(e -> onFindAction()); // find the records if Enter pressed on the search field
        jPanelSearch.add(jTextFieldSearch, BorderLayout.NORTH);

        JPanel jPanelSearchButton = new JPanel(); // FlowLayout() by default
        jPanelSearch.add(jPanelSearchButton, BorderLayout.CENTER);

        jCheckBox = new JCheckBox("Искать по первой букве");
        jCheckBox.setSelected(true);
        jCheckBox.addActionListener(e -> onFindAction()); // find the records if the check box changes its status
        jPanelSearchButton.add(jCheckBox);

        JButton jButtonFind = new JButton("Найти");
        jButtonFind.addActionListener(e -> onFindAction()); // find the records if the button Find pressed
        jButtonFind.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onFindAction(); // find the records if Enter pressed on the button Find
                }
            }
        });
        jPanelSearchButton.add(jButtonFind);

        jPanelSearch.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.SOUTH); // add the separator

        JPanel jPanelSearchResults = new JPanel(new BorderLayout());
        jPanelRight.add(jPanelSearchResults, BorderLayout.SOUTH);

        jTextAreaSearchResults = new JTextArea("(здесь будут отображаться результаты поиска)");
        jTextAreaSearchResults.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jTextAreaSearchResults.setEditable(false);

//        JScrollPane jScrollPaneSearchResults = new JScrollPane(jTextAreaSearchResults);
//        jPanelSearchResults.add(jScrollPaneSearchResults);
        jPanelSearchResults.add(jTextAreaSearchResults); // if placed within a scroll pane, it looks not very good

        // Adding events pertaining to the window itself

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                showRecordsInGUI();
                jTextFieldSearch.grabFocus();
            }
        });

        // Setting the window visible

        setVisible(true);
    }

    // GUI-related instance methods

    private void showRecordsInGUI() {
        jTextArea.setText("");
        for (PhoneRecord phoneRecord : phoneBook) {
            jTextArea.append(phoneRecord.toString());
            jTextArea.append("\n");
        }
    }

    private void onAddAction() {
        if (!jTextFieldName.getText().trim().isEmpty() && !jTextFieldPhone.getText().trim().isEmpty()) {
            addRecord(new PhoneRecord(jTextFieldName.getText().trim(), jTextFieldPhone.getText().trim()));
            showRecordsInGUI();
            jTextFieldName.grabFocus();
        } else {
            if (!jTextFieldName.getText().trim().isEmpty()) {
                jTextFieldPhone.grabFocus();
            } else {
                jTextFieldName.grabFocus();
            }
        }
    }

    private void onFindAction() {
        if (!jTextFieldSearch.getText().trim().isEmpty()) {
            ArrayList<PhoneRecord> phoneRecords;
            if (jCheckBox.isSelected()) {
                phoneRecords = findRecords(jTextFieldSearch.getText().trim());
            } else {
                phoneRecords = findAllRecords(jTextFieldSearch.getText().trim());
            }
            jTextAreaSearchResults.setText("");
            for (PhoneRecord phoneRecord : phoneRecords) {
                jTextAreaSearchResults.append(phoneRecord.toString());
                jTextAreaSearchResults.append("\n");
            }
            jTextFieldName.grabFocus();
        } else {
            jTextAreaSearchResults.setText("");
            jTextFieldSearch.setText("");
            jTextFieldSearch.grabFocus();
        }
    }

    private void onRemoveAction() {
        if (!jTextFieldName.getText().trim().isEmpty() && jTextFieldPhone.getText().trim().isEmpty()) {
            ArrayList<PhoneRecord> phoneRecords;
            if (jCheckBox.isSelected()) {
                phoneRecords = findRecords(jTextFieldName.getText().trim());
            } else {
                phoneRecords = findAllRecords(jTextFieldName.getText().trim());
            }
            if (phoneRecords.size() == 1) {
                phoneBook.remove(phoneRecords.get(0));
                jTextFieldName.setText("");
                jTextFieldPhone.setText("");
                jTextFieldSearch.setText("");
                jTextAreaSearchResults.setText("");
                showRecordsInGUI();
                jTextFieldName.grabFocus();
            } else {
                jTextFieldPhone.setText("");
                jTextAreaSearchResults.setText("");
                for (PhoneRecord phoneRecord : phoneRecords) {
                    jTextAreaSearchResults.append(phoneRecord.toString());
                    jTextAreaSearchResults.append("\n");
                }
                jTextFieldPhone.grabFocus();
            }
        } else {
            if (!jTextFieldName.getText().trim().isEmpty() && !jTextFieldPhone.getText().trim().isEmpty()) {
                ArrayList<PhoneRecord> phoneRecords;
                if (jCheckBox.isSelected()) {
                    phoneRecords = findRecords(jTextFieldName.getText().trim());
                } else {
                    phoneRecords = findAllRecords(jTextFieldName.getText().trim());
                }
                if (phoneRecords.size() == 1 && phoneRecords.get(0).getPhone().equals(jTextFieldPhone.getText().trim())) {
                    phoneBook.remove(phoneRecords.get(0));
                    jTextFieldName.setText("");
                    jTextFieldPhone.setText("");
                    jTextFieldSearch.setText("");
                    showRecordsInGUI();
                    jTextFieldSearch.grabFocus();
                } else {
                    if (phoneRecords.size() > 1) {
                        boolean flag = false;
                        for (PhoneRecord phoneRecord : phoneRecords) {
                            if (phoneRecord.getPhone().equals(jTextFieldPhone.getText().trim())) {
                                phoneBook.remove(phoneRecord);
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            jTextFieldName.setText("");
                            jTextFieldPhone.setText("");
                            jTextFieldSearch.setText("");
                            jTextAreaSearchResults.setText("");
                            showRecordsInGUI();
                            jTextFieldName.grabFocus();
                        } else {
                            jTextAreaSearchResults.setText("");
                            for (PhoneRecord phoneRecord : phoneRecords) {
                                jTextAreaSearchResults.append(phoneRecord.toString());
                                jTextAreaSearchResults.append("\n");
                            }
                            jTextFieldPhone.grabFocus();
                        }
                    } else {
                        jTextFieldPhone.grabFocus();
                    }
                }
            } else {
                jTextFieldName.setText("");
                jTextFieldPhone.setText("");
                showRecordsInGUI();
                jTextFieldName.grabFocus();
            }
        }
    }

    // phone book-related instance methods

    private void addRecord(PhoneRecord phoneRecord) {
        if (!phoneBook.contains(phoneRecord)) {
            phoneBook.add(phoneRecord);
        } else {
            int reply = JOptionPane.showConfirmDialog(this, "Дублирующие записи не разрешены. Хотите удалить?",
                    "Удалить имеющуюся запись?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                onRemoveAction();
            }
        }
    }

    private ArrayList<PhoneRecord> findRecords(String string) {
        ArrayList<PhoneRecord> phoneRecords = new ArrayList<>();
        for (PhoneRecord phoneRecord : phoneBook) {
            if (phoneRecord.getName().toLowerCase().charAt(0) == string.toLowerCase().charAt(0)) {
                phoneRecords.add(phoneRecord);
            }
        }
        return phoneRecords;
    }

    private ArrayList<PhoneRecord> findAllRecords(String string) {
        ArrayList<PhoneRecord> phoneRecords = new ArrayList<>();
        for (PhoneRecord phoneRecord : phoneBook) {
            if (phoneRecord.getName().toLowerCase().contains(string.toLowerCase())) {
                phoneRecords.add(phoneRecord);
            }
        }
        return phoneRecords;
    }

    // test client

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PhoneBook phoneBook = new PhoneBook();
            int size = names.length;
            for (int i = 0; i < size; i++) {
                phoneBook.addRecord(new PhoneRecord(names[i], phones[i]));
            }
        });
    }
}
