/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static teste.NewJFrame.tc;
import static teste.NewJFrame.thread;

/**
 *
 * @author thali
 */
public class Servidor implements Runnable {

    private ServerSocket servidor;
    private java.net.Socket cliente;
    private Scanner entrada;

    public void Conectar() throws IOException {
        String text = "";
        int porta = Integer.parseInt(NewJFrame.jPorta.getText());

        if (!NewJFrame.jIP.getText().isEmpty()) {
            porta = porta + 1;
        }
        servidor = new ServerSocket(porta);

        text += "Porta " + porta + " aberta!";
        NewJFrame.jTextArea.setText(text);

        cliente = servidor.accept();

        if (NewJFrame.jIP.getText().isEmpty()) {
            porta = porta + 1;
            NewJFrame.jIP.setText(cliente.getInetAddress().getHostAddress());
            NewJFrame.jPorta.setText(Integer.toString(porta));
            thread = new Thread(tc);
            thread.start();
        }

        NewJFrame.jTextArea.setText("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

        RecebeMensagem();
    }

    public void RecebeMensagem() throws IOException {
        String text = "";

        entrada = new Scanner(cliente.getInputStream());

        while (entrada.hasNextLine()) {
            text = NewJFrame.jIP.getText() + ": " + entrada.nextLine();
            NewJFrame.texto += text + "\n";

            NewJFrame.jTextArea.setText(NewJFrame.texto);

        }

        entrada.close();
    }

    @Override
    public void run() {
        try {
            Conectar();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
