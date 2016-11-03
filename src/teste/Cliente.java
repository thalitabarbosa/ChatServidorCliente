/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static teste.NewJFrame.tc;
import static teste.NewJFrame.thread;
import static teste.NewJFrame.ts;

/**
 *
 * @author thali
 */
public class Cliente implements Runnable {

    private Socket cliente;

    public void Conectar() throws IOException {
        //System.out.println("IP" + NewJFrame.jIP.getText());
        cliente = new Socket(NewJFrame.jIP.getText(), Integer.parseInt(NewJFrame.jPorta.getText()));
        NewJFrame.jTextArea.setText("O cliente se conectou ao servidor!");

        thread = new Thread(ts);
        thread.start();
    }

    public void EnviarMsg(String message) throws IOException {
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        saida.println(message);
        NewJFrame.texto += "Eu: " + message + "\n";

        NewJFrame.jTextArea.setText(NewJFrame.texto);

    }

    @Override
    public void run() {
        try {
            Conectar();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
