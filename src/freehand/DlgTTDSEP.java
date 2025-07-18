/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package freehand;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author perpustakaan
 */
public class DlgTTDSEP extends javax.swing.JDialog {

    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private String username = "";
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int index = 0;
    private Point[] arr = new Point[100000];
    private BufferedImage img;
    private final SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    

    /**
     * Creates new form DlgPemberianObat
     *
     * @param parent
     * @param modal
     */
    public DlgTTDSEP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();        
        setSize(screenSize.width, screenSize.height);
        setResizable(false);
        this.setLocation(0, 0);
//        setSize(300,300);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRM = new widget.TextBox();
        TNoSEP = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRW = new widget.TextBox();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnKeluar = new widget.Button();
        jLabel5 = new widget.Label();
        TPath = new widget.TextBox();
        jLabel4 = new widget.Label();
        TFile = new widget.TextBox();
        panelGlass9 = new freehand.PanelTTD();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), ".:[ Tanda Tangan SEP Pasien ]:.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(300, 300));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(865, 60));
        FormInput.setLayout(null);

        jLabel3.setText("Nama:");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 50, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(230, 10, 90, 23);

        TNoSEP.setEditable(false);
        TNoSEP.setHighlighter(null);
        TNoSEP.setName("TNoSEP"); // NOI18N
        FormInput.add(TNoSEP);
        TNoSEP.setBounds(60, 40, 230, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(330, 10, 270, 23);

        TNoRW.setEditable(false);
        TNoRW.setHighlighter(null);
        TNoRW.setName("TNoRW"); // NOI18N
        FormInput.add(TNoRW);
        TNoRW.setBounds(60, 10, 160, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);
        FormInput.getAccessibleContext().setAccessibleName("");
        FormInput.getAccessibleContext().setAccessibleDescription("");

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(100, 56));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluar.setMnemonic('T');
        BtnKeluar.setToolTipText("Alt+T");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(50, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jLabel5.setText("Path:");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass8.add(jLabel5);

        TPath.setEditable(false);
        TPath.setHighlighter(null);
        TPath.setName("TPath"); // NOI18N
        TPath.setPreferredSize(new java.awt.Dimension(200, 24));
        panelGlass8.add(TPath);

        jLabel4.setText("Nama File:");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass8.add(jLabel4);

        TFile.setEditable(false);
        TFile.setHighlighter(null);
        TFile.setName("TFile"); // NOI18N
        TFile.setPreferredSize(new java.awt.Dimension(200, 24));
        panelGlass8.add(TFile);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass9.setBackground(new java.awt.Color(255, 255, 255));
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(300, 300));
        panelGlass9.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelGlass9MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                panelGlass9MouseMoved(evt);
            }
        });
        panelGlass9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelGlass9MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelGlass9MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelGlass9MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                panelGlass9MouseReleased(evt);
            }
        });
        internalFrame1.add(panelGlass9, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        internalFrame1.getAccessibleContext().setAccessibleName(".:[ Tanda Tangan SEP Pasien ]:.");

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        try {
        // Buat BufferedImage sebesar panelGlass9
        BufferedImage image = new BufferedImage(
                panelGlass9.getWidth(),
                panelGlass9.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        // Gambar ulang panel ke image
        Graphics2D g2d = image.createGraphics();
        panelGlass9.paint(g2d); // repaint panel ke image, bukan ke layar
        g2d.dispose();

        // Simpan gambar ke file
        String filePath = "tmpImageFreehand/" + TFile.getText();
        ImageIO.write(image, "png", new File(filePath));

        // Upload gambar
        uploadImage(TFile.getText(), TPath.getText());

        // Simpan ke database
        String urlImage = "imagefreehand/sep/TTD" + TNoSEP.getText().replaceAll("/", "") + ".png";
        if (Sequel.cariInteger("SELECT COUNT(no_rawat) FROM data_sep_image_marking WHERE no_rawat='" + TNoRW.getText() + "'") > 0) {
            Sequel.mengedittf(
                "data_sep_image_marking",
                "no_rawat=?",
                "no_sep=?,tanggal=?,jam=?,url_image=?",
                5,
                new String[]{
                    TNoSEP.getText(),
                    tanggalNow.format(new Date()),
                    jamNow.format(new Date()),
                    urlImage,
                    TNoRW.getText()
                }
            );
        } else {
            Sequel.menyimpantf(
                "data_sep_image_marking",
                "?,?,?,?,?",
                "No.Rawat", 5,
                new String[]{
                    TNoRW.getText(),
                    TNoSEP.getText(),
                    tanggalNow.format(new Date()),
                    jamNow.format(new Date()),
                    urlImage
                }
            );
        }

        JOptionPane.showMessageDialog(null, "Tanda tangan berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        dispose(); // Tutup dialog
    } catch (IOException ex) {
        Logger.getLogger(DlgTTDSEP.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Gagal menyimpan gambar tanda tangan: " + ex.getMessage(),
                "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed

}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        panelGlass9.clearAll(); // Hapus semua tanda tangan
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed

    }//GEN-LAST:event_BtnHapusKeyPressed

    private void panelGlass9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGlass9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelGlass9MouseClicked

    private void panelGlass9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGlass9MousePressed
        panelGlass9.startNewLine(); // Mulai garis baru
    }//GEN-LAST:event_panelGlass9MousePressed

    private void panelGlass9MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGlass9MouseDragged
        panelGlass9.addPoint(evt.getPoint());
    }//GEN-LAST:event_panelGlass9MouseDragged

    private void panelGlass9MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGlass9MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_panelGlass9MouseMoved

    private void panelGlass9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGlass9MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_panelGlass9MouseExited

    private void panelGlass9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGlass9MouseReleased
        panelGlass9.finishLine(); // Simpan garis ke daftar permanen
    }//GEN-LAST:event_panelGlass9MouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgTTDSEP dialog = new DlgTTDSEP(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private widget.TextBox TFile;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRW;
    private widget.TextBox TNoSEP;
    private widget.TextBox TPasien;
    private widget.TextBox TPath;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.panelisi panelGlass8;
    private freehand.PanelTTD panelGlass9;
    // End of variables declaration//GEN-END:variables

//    private void isPsien() {
////        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
//    }
    public void setNoRm(String norawat, String noRm, String namaPasien,String noSEP,String Path, String NamaFile) {
        TNoRW.setText(norawat);
        TNoSEP.setText(noSEP);
        TNoRM.setText(noRm);
        TPasien.setText(namaPasien);
        TFile.setText(NamaFile);
        TPath.setText(Path);
        //  isPsien();   
//        Sequel.cariIsi("select catatan from catatan_pasien where no_rkm_medis=?",TCatatan,TNoRawat.getText());       
    }

    public void isCek() {
        BtnSimpan.setEnabled(true);

    }

    void uploadImage(String FileName, String docpath) {
        try {
        //    docpath = "rehabpertama";
            File file = new File("tmpImageFreehand/" + TFile.getText());
            byte[] data = new byte[(int) file.length()];
            data = FileUtils.readFileToByteArray(file);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/upload.php?doc=" + TPath.getText());
            ByteArrayBody fileData = new ByteArrayBody(data, FileName);
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileData);
            postRequest.setEntity(reqEntity);
            httpClient.execute(postRequest);
//        HttpResponse response = (HttpResponse) httpClient.execute(postRequest); 
      //      deleteFile();

        } catch (Exception e) {
            System.out.println("Upload error" + e);
        }
    }

    void deleteFile() {
        File file = new File("tmpImageFreehand");
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
    }

}
