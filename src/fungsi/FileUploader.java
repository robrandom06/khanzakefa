package fungsi;

import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author RS Leona
 */
public class FileUploader {

    private static sekuel Sequel = new sekuel();
    private static String kodeberkas = "";

    public static void UploadPDF(String FileName, String docpath, String jenisBerkas, JTable tbData, int indexNoRawat) {
        try {
            // Pastikan ada baris yang dipilih
            if (tbData.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Pilih data yang ingin diupload!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Ambil No. Rawat dari JTable menggunakan parameter index
            String noRawat = tbData.getValueAt(tbData.getSelectedRow(), indexNoRawat).toString().trim();

            // Pastikan noRawat tidak kosong
            if (noRawat.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No. Rawat tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Periksa apakah No. Rawat ada di tabel reg_periksa
            if (Sequel.cariInteger("SELECT COUNT(*) FROM reg_periksa WHERE no_rawat='" + noRawat + "'") == 0) {
                JOptionPane.showMessageDialog(null, "No. Rawat tidak ditemukan dalam reg_periksa!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Persiapan file
            File file = new File("tmpPDF/" + FileName + ".pdf");
            byte[] data = FileUtils.readFileToByteArray(file);

            // Membuat HTTP Client
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" 
                    + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/upload.php?doc=" + docpath);

            // Menyiapkan file untuk dikirim
            ByteArrayBody fileData = new ByteArrayBody(data, FileName + ".pdf");
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileData);
            postRequest.setEntity(reqEntity);

            // Eksekusi request
            httpClient.execute(postRequest);

            // Menyimpan ke database
            kodeberkas = Sequel.cariIsi("SELECT kode FROM master_berkas_digital WHERE nama LIKE '%" + jenisBerkas + "%'");

            boolean uploadSuccess;
            if (Sequel.cariInteger("SELECT COUNT(no_rawat) AS jumlah FROM berkas_digital_perawatan WHERE lokasi_file='pages/upload/" + FileName + ".pdf'") > 0) {
                uploadSuccess = Sequel.mengedittf("berkas_digital_perawatan", "lokasi_file=?", "no_rawat=?,kode=?, lokasi_file=?", 4, new String[]{
                    noRawat, kodeberkas, "pages/upload/" + FileName + ".pdf", "pages/upload/" + FileName + ".pdf"
                });
            } else {
                uploadSuccess = Sequel.menyimpantf("berkas_digital_perawatan", "?,?,?", "No.Rawat", 3, new String[]{
                    noRawat, kodeberkas, "pages/upload/" + FileName + ".pdf"
                });
            }

            // Menampilkan notifikasi
            if (uploadSuccess) {
                JOptionPane.showMessageDialog(null, "Upload berhasil!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Upload gagal disimpan ke database.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println("Upload error: " + e);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat upload: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }
}
