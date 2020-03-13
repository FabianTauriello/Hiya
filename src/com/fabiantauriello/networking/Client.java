package com.fabiantauriello.networking;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Client {

	private static final int SERVER_PORT = 9090;
	private static final String SERVER_HOST = "127.0.0.1";

    Scanner in;
    PrintWriter out;
    JFrame frame = new JFrame("Hiya");
    JTextField textField = new JTextField(50);
    JTextArea messageArea = new JTextArea(16, 50);

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
    
    public Client() {

        textField.setEditable(false);
        messageArea.setEditable(false);        
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.WEST);
        frame.pack();

        // Send on enter then clear to prepare for next message
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());
                textField.setText("");
            }
        });
    }

    private void run() throws IOException {
        try {
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.startsWith("SUBMITNAME")) {
                    out.println(getName());
                } else if (line.startsWith("NAMEACCEPTED")) {
                    this.frame.setTitle("Hiya - " + line.substring(13));
                    textField.setEditable(true);
                } else if (line.startsWith("MESSAGE")) {
                	if (line.startsWith("MESSAGE-JOIN")) {
                		messageArea.append(line.substring(13) + "\n");
					} else if(line.startsWith("MESSAGE-LEAVE")) {
						messageArea.append(line.substring(14) + "\n");
					} else {
						messageArea.append(line.substring(8) + "\n");
					}
                } else if (line.startsWith("DUPLICATEENTRY")) {
                	duplicateAlert();
                	out.println(getName());
                } else if (line.startsWith("QUITHIYA")) {
                	System.exit(0);
                }
            }
        } finally {
            frame.setVisible(false);
            frame.dispose();
        }
    }
    
    // will return null if cancel is pressed
    private String getName() {
        String result = JOptionPane.showInputDialog(frame, "Choose a screen name:", "Screen name selection",
                JOptionPane.PLAIN_MESSAGE);
        if(result == null) {
        	return "QUITHIYA";
        } else {
        	return result;
        }
    }
    
    // I added this modal to notify the user why the name entered is not acceptable.
    
	private void duplicateAlert() {
		JOptionPane.showMessageDialog(frame, "Duplicate Entry. Try a different name", "Alert",
				JOptionPane.ERROR_MESSAGE);
	}

}
