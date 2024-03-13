package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {
    private final JTextField nrClienti;
    private final JTextField nrQueue;
    JButton btnStartSimulation = new JButton("Start");
    JButton btnRestart = new JButton("Stop");
    private JTextField minService;
    private JTextField interval;
    private JTextField time;
    private JTextField maxTime;
    private JTextField maxService;
    public static JTextArea textArea = new JTextArea();

    public SimulationFrame() {
        setBackground(new Color(255, 192, 203));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1171, 590);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 240, 245));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("QUEUES MANAGEMENT APPLICATION");
        lblTitle.setBounds(396, 10, 414, 30);
        lblTitle.setBackground(new Color(255, 228, 225));
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        contentPane.add(lblTitle);

        JLabel clientLabel = new JLabel("Number of clients:");
        clientLabel.setBounds(20, 70, 150, 23);
        clientLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        contentPane.add(clientLabel);

        JLabel queueLabel = new JLabel("Number of queues:");
        queueLabel.setBounds(20, 110, 150, 23);
        queueLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        contentPane.add(queueLabel);

        JLabel intervalLabel = new JLabel("Simulation interval:");
        intervalLabel.setBounds(20, 150, 158, 23);
        intervalLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        contentPane.add(intervalLabel);

        JLabel minTimeLabel = new JLabel("Minimum arrival time:");
        minTimeLabel.setBounds(20, 190, 204, 23);
        minTimeLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        contentPane.add(minTimeLabel);

        JLabel maxTimeLabel = new JLabel("Maximum arrival time:");
        maxTimeLabel.setBounds(20, 230, 183, 23);
        maxTimeLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        contentPane.add(maxTimeLabel);

        JLabel minServiceLabel = new JLabel("Minimum service time:");
        minServiceLabel.setBounds(20, 270, 183, 23);
        minServiceLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        contentPane.add(minServiceLabel);

        JLabel maxServiceLabel = new JLabel("Maximum service time:");
        maxServiceLabel.setBounds(20, 316, 204, 23);
        maxServiceLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        contentPane.add(maxServiceLabel);

        nrClienti = new JTextField();
        nrClienti.setBounds(257, 72, 100, 23);
        nrClienti.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        contentPane.add(nrClienti);
        nrClienti.setColumns(10);

        nrQueue = new JTextField();
        nrQueue.setBounds(257, 112, 100, 23);
        nrQueue.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        nrQueue.setColumns(10);
        contentPane.add(nrQueue);

        btnStartSimulation.setBounds(20, 380, 337, 58);
        btnStartSimulation.setBackground(new Color(255, 192, 203));
        btnStartSimulation.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 21));
        contentPane.add(btnStartSimulation);

        minService = new JTextField();
        minService.setBounds(257, 272, 100, 23);
        minService.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        minService.setColumns(10);
        contentPane.add(minService);

        interval = new JTextField();
        interval.setBounds(257, 152, 100, 23);
        interval.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        interval.setColumns(10);
        contentPane.add(interval);

        time = new JTextField();
        time.setBounds(257, 192, 100, 23);
        time.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        time.setColumns(10);
        contentPane.add(time);

        maxTime = new JTextField();
        maxTime.setBounds(257, 232, 100, 23);
        maxTime.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        maxTime.setColumns(10);
        contentPane.add(maxTime);

        maxService = new JTextField();
        maxService.setBounds(257, 318, 100, 23);
        maxService.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        maxService.setColumns(10);
        contentPane.add(maxService);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(412, 70, 726, 455);
        contentPane.add(scrollPane);

        textArea = new JTextArea();
        textArea.setBackground(new Color(255, 247, 251));
        textArea.setBounds(10, 70, 706, 357);
        textArea.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);

        btnRestart.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 21));
        btnRestart.setBackground(new Color(255, 192, 203));
        btnRestart.setBounds(20, 465, 337, 60);
        contentPane.add(btnRestart);

    }

    public int getClienti() {
        return Integer.parseInt(nrClienti.getText());
    }

    public int getQueue() {
        return Integer.parseInt(nrQueue.getText());
    }

    public int getInterval() {
        return Integer.parseInt(interval.getText());
    }

    public int getMinTime() {
        return Integer.parseInt(time.getText());
    }

    public int getMaxTime() {
        return Integer.parseInt(maxTime.getText());
    }

    public int getMinService() {
        return Integer.parseInt(minService.getText());
    }

    public int getMaxService() {
        return Integer.parseInt(maxService.getText());
    }

    public void setSimulationArea(String s) {
        textArea.insert(s, 0);
    }

    public static JTextArea getTextArea() {
        return textArea;
    }

    public static void setTextArea(JTextArea textArea) {
        SimulationFrame.textArea = textArea;
    }

    public void addSimulationListener(ActionListener simulate) {
        btnStartSimulation.addActionListener(simulate);
    }

    public void addStopListener(ActionListener stop) {
        btnRestart.addActionListener(stop);
    }


}