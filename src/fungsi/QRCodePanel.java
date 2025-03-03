/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fungsi;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import com.google.zxing.WriterException;
import fungsi.QRCodeGenerator;
/**
 *
 * @author RS Leona
 */
public class QRCodePanel extends JPanel {
    private BufferedImage qrImage;

    public QRCodePanel(String data) {
        try {
            qrImage = QRCodeGenerator.generateQRCodeImage(data, 300, 300);
            setPreferredSize(new Dimension(320, 320));
        } catch (WriterException e) {
            JOptionPane.showMessageDialog(this, "Gagal membuat QR Code: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (qrImage != null) {
            int x = (getWidth() - qrImage.getWidth()) / 2;
            int y = (getHeight() - qrImage.getHeight()) / 2;
            g.drawImage(qrImage, x, y, this);
        }
    }
}
