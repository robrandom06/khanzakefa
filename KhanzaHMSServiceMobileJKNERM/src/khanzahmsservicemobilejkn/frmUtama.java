package khanzahmsservicemobilejkn;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.ApiMobileJKN;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.Timer;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
public class frmUtama extends javax.swing.JFrame {
    private  Connection koneksi=koneksiDB.condb();
    private  sekuel Sequel=new sekuel();
    private  String requestJson,URL="",utc="",link="",datajam="",
              nol_jam = "",nol_menit = "",nol_detik = "",jam="",menit="",detik="",hari="",noresep="",task3="",task4="",task5="",task6="",task7="",task99="",
              kodepoli="",kodedokter="",kodebpjs=Sequel.cariIsi("select password_asuransi.kd_pj from password_asuransi");
    private  ApiMobileJKN api=new ApiMobileJKN();
    private  HttpHeaders headers;
    private  HttpEntity requestEntity;
    private  ObjectMapper mapper= new ObjectMapper();
    private  JsonNode root;
    private  JsonNode nameNode;
    private  PreparedStatement ps,ps2,ps3;
    private  ResultSet rs,rs2,rs3;
    private  Calendar cal = Calendar.getInstance();
    private  int day = cal.get(Calendar.DAY_OF_WEEK);
    private  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private  SimpleDateFormat tanggalFormat = new SimpleDateFormat("yyyy-MM-dd");
    private  Date parsedDate;
    private  Date date = new Date();     
    public frmUtama() {
        initComponents();
        try {
            link=koneksiDB.URLAPIMOBILEJKN();
        } catch (Exception e) {
            System.out.println("E : "+e);
        }        
        this.setSize(390,340);        
        date = new Date();  
        Tanggal1.setText(tanggalFormat.format(date)); 
        Tanggal2.setText(tanggalFormat.format(date)); 
        jam();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TeksArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Tanggal1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Tanggal2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SIMKES Khanza Service Mobile JKN");

        TeksArea.setColumns(20);
        TeksArea.setRows(5);
        jScrollPane1.setViewportView(TeksArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Tanggal :");
        jLabel1.setPreferredSize(new java.awt.Dimension(70, 23));
        jPanel1.add(jLabel1);

        Tanggal1.setPreferredSize(new java.awt.Dimension(100, 23));
        jPanel1.add(Tanggal1);

        jLabel3.setText("s.d.");
        jLabel3.setPreferredSize(new java.awt.Dimension(28, 23));
        jPanel1.add(jLabel3);

        Tanggal2.setPreferredSize(new java.awt.Dimension(100, 23));
        jPanel1.add(Tanggal2);

        jLabel2.setPreferredSize(new java.awt.Dimension(30, 23));
        jPanel1.add(jLabel2);

        jButton1.setText("Keluar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed
  
    public static void main(String args[]) {       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Tanggal1;
    private javax.swing.JTextField Tanggal2;
    private javax.swing.JTextArea TeksArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                nol_jam = "";
                nol_menit = "";
                nol_detik = "";
                Date now = Calendar.getInstance().getTime();
                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                nilai_jam = now.getHours();
                nilai_menit = now.getMinutes();
                nilai_detik = now.getSeconds();
                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                jam = nol_jam + Integer.toString(nilai_jam);
                menit = nol_menit + Integer.toString(nilai_menit);
                detik = nol_detik + Integer.toString(nilai_detik);
                if(jam.equals("01")&&menit.equals("01")&&detik.equals("01")){
                    TeksArea.setText("");
                    date = new Date();  
                    Tanggal1.setText(tanggalFormat.format(date)); 
                    Tanggal2.setText(tanggalFormat.format(date)); 
                }
                if(detik.equals("01")&&((nilai_menit%5)==0)){
                    day=cal.get(Calendar.DAY_OF_WEEK);
                    switch (day) {
                        case 1:
                            hari="AKHAD";
                            break;
                        case 2:
                            hari="SENIN";
                            break;
                        case 3:
                            hari="SELASA";
                            break;
                        case 4:
                            hari="RABU";
                            break;
                        case 5:
                            hari="KAMIS";
                            break;
                        case 6:
                            hari="JUMAT";
                            break;
                        case 7:
                            hari="SABTU";
                            break;
                        default:
                            break;
                    }
                    
                    try {
                        koneksi=koneksiDB.condb();
                        TeksArea.append("Menjalankan WS tambah antrian Mobile JKN Pasien BPJS\n");
                        
    //pasien JKN
                        ps=koneksi.prepareStatement(
                                "SELECT referensi_mobilejkn_bpjs.nobooking,referensi_mobilejkn_bpjs.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,referensi_mobilejkn_bpjs.nohp,referensi_mobilejkn_bpjs.nomorkartu,"+
                                "referensi_mobilejkn_bpjs.nik,referensi_mobilejkn_bpjs.tanggalperiksa,poliklinik.nm_poli,dokter.nm_dokter,referensi_mobilejkn_bpjs.jampraktek,"+
                                "referensi_mobilejkn_bpjs.jeniskunjungan,referensi_mobilejkn_bpjs.nomorreferensi,referensi_mobilejkn_bpjs.status,referensi_mobilejkn_bpjs.validasi,"+
                                "referensi_mobilejkn_bpjs.kodepoli,referensi_mobilejkn_bpjs.pasienbaru,referensi_mobilejkn_bpjs.kodedokter,referensi_mobilejkn_bpjs.jampraktek,"+
                                "referensi_mobilejkn_bpjs.nomorantrean,referensi_mobilejkn_bpjs.angkaantrean,referensi_mobilejkn_bpjs.estimasidilayani,referensi_mobilejkn_bpjs.sisakuotajkn,"+
                                "referensi_mobilejkn_bpjs.kuotajkn,referensi_mobilejkn_bpjs.sisakuotanonjkn,referensi_mobilejkn_bpjs.kuotanonjkn "+
                                "FROM referensi_mobilejkn_bpjs INNER JOIN reg_periksa ON referensi_mobilejkn_bpjs.no_rawat=reg_periksa.no_rawat "+
                                "INNER JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "INNER JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli "+
                                "INNER JOIN dokter ON reg_periksa.kd_dokter=dokter.kd_dokter "+
                                "WHERE referensi_mobilejkn_bpjs.statuskirim='Belum' and referensi_mobilejkn_bpjs.tanggalperiksa between "+
                                (Tanggal1.getText().equals(Tanggal2.getText())?"SUBDATE('"+Tanggal2.getText()+"',INTERVAL 6 DAY) and '"+Tanggal2.getText()+"'":"'"+Tanggal1.getText()+"' and '"+Tanggal2.getText()+"'")+
                                "order by referensi_mobilejkn_bpjs.tanggalperiksa");
                        try {
                             TeksArea.append("Menjalankan WS tambah antrian Mobile JKN Pasien BPJSan\n");
                            rs=ps.executeQuery();
                            while(rs.next()){
                                try {     
                                    headers = new HttpHeaders();
                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                    headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                    utc=String.valueOf(api.GetUTCdatetimeAsString());
                                    headers.add("x-timestamp",utc);
                                    headers.add("x-signature",api.getHmac(utc));
                                    headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                    requestJson ="{" +
                                                    "\"kodebooking\": \""+rs.getString("nobooking")+"\"," +
                                                    "\"jenispasien\": \"JKN\"," +
                                                    "\"nomorkartu\": \""+rs.getString("nomorkartu")+"\"," +
                                                    "\"nik\": \""+rs.getString("nik")+"\"," +
                                                    "\"nohp\": \""+rs.getString("nohp")+"\"," +
                                                    "\"kodepoli\": \""+rs.getString("kodepoli")+"\"," +
                                                    "\"namapoli\": \""+rs.getString("nm_poli")+"\"," +
                                                    "\"pasienbaru\": "+rs.getString("pasienbaru")+"," +
                                                    "\"norm\": \""+rs.getString("no_rkm_medis")+"\"," +
                                                    "\"tanggalperiksa\": \""+rs.getString("tanggalperiksa")+"\"," +
                                                    "\"kodedokter\": "+rs.getString("kodedokter")+"," +
                                                    "\"namadokter\": \""+rs.getString("nm_dokter")+"\"," +
                                                    "\"jampraktek\": \""+rs.getString("jampraktek")+"\"," +
                                                    "\"jeniskunjungan\": "+rs.getString("jeniskunjungan").substring(0,1)+"," +
                                                    "\"nomorreferensi\": \""+rs.getString("nomorreferensi")+"\"," +
                                                    "\"nomorantrean\": \""+rs.getString("nomorantrean")+"\"," +
                                                    "\"angkaantrean\": "+Integer.parseInt(rs.getString("angkaantrean"))+"," +
                                                    "\"estimasidilayani\": "+rs.getString("estimasidilayani")+"," +
                                                    "\"sisakuotajkn\": "+rs.getString("sisakuotajkn")+"," +
                                                    "\"kuotajkn\": "+rs.getString("kuotajkn")+"," +
                                                    "\"sisakuotanonjkn\": "+rs.getString("sisakuotanonjkn")+"," +
                                                    "\"kuotanonjkn\": "+rs.getString("kuotanonjkn")+"," +
                                                    "\"keterangan\": \"Peserta harap 30 menit lebih awal guna pencatatan administrasi.\"" +
                                                "}";
                                    TeksArea.append("JSON : "+requestJson+"\n");
                                    requestEntity = new HttpEntity(requestJson,headers);
                                    URL = link+"/antrean/add";	
                                    System.out.println("URL : "+URL);
                                    //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                    nameNode = root.path("metadata");
                                    if(nameNode.path("code").asText().equals("200")||nameNode.path("code").asText().equals("208")||nameNode.path("message").asText().equals("Ok")){
                                        Sequel.queryu2("update referensi_mobilejkn_bpjs set statuskirim='Sudah' where nobooking='"+rs.getString("nobooking")+"'");
                                    }
                                    if(!nameNode.path("code").asText().equals("200")){
                                        String errorMessage = nameNode.path("message").asText();
                                        String errorCode = nameNode.path("code").asText();
                                        Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "3", errorCode, errorMessage});
                                        System.out.println("respon WS BPJS gagal : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                    }
                                    TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                     System.out.println("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                }catch (Exception ex) {
                                    System.out.println("Notifikasi Bridging : "+ex);
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Notif Ketersediaan : "+ex);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }
                        
                        
                        
                        TeksArea.append("Menjalankan WS batal antrian Mobile JKN Pasien BPJS\n");
//                        TeksArea.append("Menjalankan WS batal antrian Mobile JKN Pasien BPJS\n");
                        ps=koneksi.prepareStatement(
                                "SELECT * FROM referensi_mobilejkn_bpjs_batal where referensi_mobilejkn_bpjs_batal.statuskirim='Belum' and referensi_mobilejkn_bpjs_batal.tanggalbatal between "+(Tanggal1.getText().equals(Tanggal2.getText())?"SUBDATE('"+Tanggal2.getText()+"',INTERVAL 6 DAY) and '"+Tanggal2.getText()+"'":"'"+Tanggal1.getText()+"' and '"+Tanggal2.getText()+"'"));
                        try {
                            rs=ps.executeQuery();
//                            System.out.println("kk");
                            while(rs.next()){
                                try {     
                                    headers = new HttpHeaders();
                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                    headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                    utc=String.valueOf(api.GetUTCdatetimeAsString());
                                    headers.add("x-timestamp",utc);
                                    headers.add("x-signature",api.getHmac(utc));
                                    headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                    requestJson ="{" +
                                                     "\"kodebooking\": \""+rs.getString("nobooking")+"\"," +
                                                     "\"keterangan\": \""+rs.getString("keterangan")+"\"" +
                                                  "}";
                                    TeksArea.append("JSON : "+requestJson+"\n");
                                    requestEntity = new HttpEntity(requestJson,headers);
                                    URL = link+"/antrean/batal";	
                                    System.out.println("URL : "+URL);
                                    //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                    nameNode = root.path("metadata");
                                    if(nameNode.path("code").asText().equals("200")){
                                        Sequel.queryu2("update referensi_mobilejkn_bpjs_batal set statuskirim='Sudah' where nomorreferensi='"+rs.getString("nomorreferensi")+"'");
                                        datajam=rs.getString("tanggalbatal");
                                        if(!datajam.equals("")){
                                            if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat_batal"),"99",datajam})==true){
                                                parsedDate = dateFormat.parse(datajam);
                                                try {     
                                                    TeksArea.append("Menjalankan WS taskid batal pelayanan poli Mobile JKN Pasien BPJS\n");
                                                    headers = new HttpHeaders();
                                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                                    headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                    utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                    headers.add("x-timestamp",utc);
                                                    headers.add("x-signature",api.getHmac(utc));
                                                    headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                    requestJson ="{" +
                                                                     "\"kodebooking\": \""+rs.getString("nobooking")+"\"," +
                                                                     "\"taskid\": \"99\"," +
                                                                     "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                                  "}";
                                                    TeksArea.append("JSON : "+requestJson+"\n");
                                                    requestEntity = new HttpEntity(requestJson,headers);
                                                    URL = link+"/antrean/updatewaktu";	
                                                    System.out.println("URL : "+URL);
                                                    //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                    nameNode = root.path("metadata");
                                                    if(!nameNode.path("code").asText().equals("200")){
                                                        Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='99' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                         String errorMessage = nameNode.path("message").asText();
                                        String errorCode = nameNode.path("code").asText();
                                        Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "3", errorCode, errorMessage});
                                                    }  
                                                    TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                                    System.out.println("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                                }catch (Exception ex) {
                                                    System.out.println("Notifikasi Bridging : "+ex);
                                                }
                                            }
                                        }
                                    }  
                                    TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                }catch (Exception ex) {
                                    System.out.println("Notifikasi Bridging : "+ex);
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Notif Ketersediaan : "+ex);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }
                        
                        ps=koneksi.prepareStatement(
                                "SELECT referensi_mobilejkn_bpjs.nobooking,referensi_mobilejkn_bpjs.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,referensi_mobilejkn_bpjs.nohp,referensi_mobilejkn_bpjs.nomorkartu,"+
                                "referensi_mobilejkn_bpjs.nik,referensi_mobilejkn_bpjs.tanggalperiksa,poliklinik.nm_poli,dokter.nm_dokter,referensi_mobilejkn_bpjs.jampraktek,"+
                                "referensi_mobilejkn_bpjs.jeniskunjungan,referensi_mobilejkn_bpjs.nomorreferensi,referensi_mobilejkn_bpjs.status,referensi_mobilejkn_bpjs.validasi,"+
                                "referensi_mobilejkn_bpjs.kodepoli,referensi_mobilejkn_bpjs.pasienbaru,referensi_mobilejkn_bpjs.kodedokter,referensi_mobilejkn_bpjs.jampraktek,"+
                                "referensi_mobilejkn_bpjs.nomorantrean,referensi_mobilejkn_bpjs.angkaantrean,referensi_mobilejkn_bpjs.estimasidilayani,referensi_mobilejkn_bpjs.sisakuotajkn,"+
                                "referensi_mobilejkn_bpjs.kuotajkn,referensi_mobilejkn_bpjs.sisakuotanonjkn,referensi_mobilejkn_bpjs.kuotanonjkn "+
                                "FROM referensi_mobilejkn_bpjs INNER JOIN reg_periksa ON referensi_mobilejkn_bpjs.no_rawat=reg_periksa.no_rawat "+
                                "INNER JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "INNER JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli "+
                                "INNER JOIN dokter ON reg_periksa.kd_dokter=dokter.kd_dokter "+
                                "WHERE referensi_mobilejkn_bpjs.status='Checkin' and referensi_mobilejkn_bpjs.tanggalperiksa between '"+Tanggal1.getText()+"' and '"+Tanggal2.getText()+"' "+
                                "order by referensi_mobilejkn_bpjs.tanggalperiksa");
                        try {
                            rs=ps.executeQuery();
                            while(rs.next()){
                                task3="";task4="";task5="";task6="";task7="";task99="";
                                ps2=koneksi.prepareStatement("select referensi_mobilejkn_bpjs_taskid.taskid from referensi_mobilejkn_bpjs_taskid where referensi_mobilejkn_bpjs_taskid.no_rawat=?");
                                try {
                                   ps2.setString(1,rs.getString("no_rawat"));
                                   rs2=ps2.executeQuery();
                                   while(rs2.next()){
                                       if(rs2.getString("taskid").equals("3")){
                                           task3="Sudah";
                                       }
                                       if(rs2.getString("taskid").equals("4")){
                                           task4="Sudah";
                                       }
                                       if(rs2.getString("taskid").equals("5")){
                                           task5="Sudah";
                                       }
                                       if(rs2.getString("taskid").equals("6")){
                                           task6="Sudah";
                                       }
                                       if(rs2.getString("taskid").equals("7")){
                                           task7="Sudah";
                                       }
                                       if(rs2.getString("taskid").equals("99")){
                                           task99="Sudah";
                                       }
                                   }
                                } catch (Exception ex) {
                                    System.out.println("Notif : "+ex);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }
                                
                                if(task3.equals("")){
//                                     TeksArea.append("Menjalankan WS taskid mulai tunggu poli Mobile JKN Pasien BPJSkkk\n");
                                    datajam=Sequel.cariIsi("select referensi_mobilejkn_bpjs.validasi from referensi_mobilejkn_bpjs where referensi_mobilejkn_bpjs.no_rawat=?",rs.getString("no_rawat"));
//                                    datajam=Sequel.cariIsi("select mutasi_berkas.dikirim from mutasi_berkas where mutasi_berkas.no_rawat=?",rs.getString("no_rawat"));
                                    if(!datajam.equals("")){
                                        if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat"),"3",datajam})==true){
                                            parsedDate = dateFormat.parse(datajam);
                                            try {     
                                                TeksArea.append("Menjalankan WS taskid mulai tunggu poli Mobile JKN Pasien BPJS\n");
                                                headers = new HttpHeaders();
                                                headers.setContentType(MediaType.APPLICATION_JSON);
                                                headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                headers.add("x-timestamp",utc);
                                                headers.add("x-signature",api.getHmac(utc));
                                                headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                requestJson ="{" +
                                                                 "\"kodebooking\": \""+rs.getString("nobooking")+"\"," +
                                                                 "\"taskid\": \"3\"," +
                                                                 "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                              "}";
                                                TeksArea.append("JSON : "+requestJson+"\n");
                                                requestEntity = new HttpEntity(requestJson,headers);
                                                URL = link+"/antrean/updatewaktu";	
                                                System.out.println("URL : "+URL);
                                                //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                nameNode = root.path("metadata");
                                                if(!nameNode.path("code").asText().equals("200")){
                                                    String errorMessage = nameNode.path("message").asText();
                                                    String errorCode = nameNode.path("code").asText();
                                                    Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "3", errorCode, errorMessage});
                                                    Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='3' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                }  
                                                TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                            }catch (Exception ex) {
                                                System.out.println("Notifikasi Bridging : "+ex);
                                            }
                                        }
                                    }
                                }
                                
                                if(task3.equals("Sudah")&&task4.equals("")){
                                    datajam=Sequel.cariIsi("select concat(pemeriksaan_ralan.tgl_perawatan,' ',pemeriksaan_ralan.jam_rawat) from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat=?",rs.getString("no_rawat"));
                                    if(datajam.equals("")){
                                        datajam=Sequel.cariIsi("select if(diterima='0000-00-00 00:00:00','',diterima) from mutasi_berkas where mutasi_berkas.no_rawat=?",rs.getString("no_rawat"));
                                    }
                                    if(!datajam.equals("")){
                                        if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat"),"4",datajam})==true){
                                            parsedDate = dateFormat.parse(datajam);
                                            try {     
                                                TeksArea.append("Menjalankan WS taskid mulai pelayanan poli Mobile JKN Pasien BPJS\n");
                                                headers = new HttpHeaders();
                                                headers.setContentType(MediaType.APPLICATION_JSON);
                                                headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                headers.add("x-timestamp",utc);
                                                headers.add("x-signature",api.getHmac(utc));
                                                headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                requestJson ="{" +
                                                                 "\"kodebooking\": \""+rs.getString("nobooking")+"\"," +
                                                                 "\"taskid\": \"4\"," +
                                                                 "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                              "}";
                                                TeksArea.append("JSON : "+requestJson+"\n");
                                                requestEntity = new HttpEntity(requestJson,headers);
                                                URL = link+"/antrean/updatewaktu";	
                                                System.out.println("URL : "+URL);
                                                //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                nameNode = root.path("metadata");
                                                if(!nameNode.path("code").asText().equals("200")){
                                                     String errorMessage = nameNode.path("message").asText();
                                                    String errorCode = nameNode.path("code").asText();
                                                    Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "4", errorCode, errorMessage});
                                                    Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='4' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                }   
                                                TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                            }catch (Exception ex) {
                                                System.out.println("Notifikasi Bridging : "+ex);
                                            }
                                        }
                                    }
                                }
                                
                                if(task4.equals("Sudah")&&task5.equals("")){
                                    datajam=Sequel.cariIsi("select if(kembali='0000-00-00 00:00:00','',kembali) from mutasi_berkas where mutasi_berkas.no_rawat=?",rs.getString("no_rawat"));
                                    if(datajam.equals("")){
                                        datajam=Sequel.cariIsi("select now() from reg_periksa where reg_periksa.stts='Sudah' and reg_periksa.no_rawat=?",rs.getString("no_rawat"));
                                    }
                                    if(!datajam.equals("")){
                                        if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat"),"5",datajam})==true){
                                            parsedDate = dateFormat.parse(datajam);
                                            try {     
                                                TeksArea.append("Menjalankan WS taskid selesai pelayanan poli Mobile JKN Pasien BPJS\n");
                                                headers = new HttpHeaders();
                                                headers.setContentType(MediaType.APPLICATION_JSON);
                                                headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                headers.add("x-timestamp",utc);
                                                headers.add("x-signature",api.getHmac(utc));
                                                headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                requestJson ="{" +
                                                                 "\"kodebooking\": \""+rs.getString("nobooking")+"\"," +
                                                                 "\"taskid\": \"5\"," +
                                                                 "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                              "}";
                                                TeksArea.append("JSON : "+requestJson+"\n");
                                                requestEntity = new HttpEntity(requestJson,headers);
                                                URL = link+"/antrean/updatewaktu";	
                                                System.out.println("URL : "+URL);
                                                //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                nameNode = root.path("metadata");
                                                if(!nameNode.path("code").asText().equals("200")){
                                                    Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='5' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                     String errorMessage = nameNode.path("message").asText();
                                        String errorCode = nameNode.path("code").asText();
                                        Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "5", errorCode, errorMessage});
                                                }  
                                                TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                            }catch (Exception ex) {
                                                System.out.println("Notifikasi Bridging : "+ex);
                                            }
                                        }
                                    }
                                }   
                                
                                if(task5.equals("Sudah")&&task6.equals("")){
                                    noresep=Sequel.cariIsi("select resep_obat.no_resep from resep_obat where resep_obat.no_rawat=?",rs.getString("no_rawat"));
                                    if(!noresep.equals("")){
                                        try {     
                                            TeksArea.append("Menjalankan WS tambah antrian farmasi Mobile JKN Pasien BPJS\n");
                                            headers = new HttpHeaders();
                                            headers.setContentType(MediaType.APPLICATION_JSON);
                                            headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                            utc=String.valueOf(api.GetUTCdatetimeAsString());
                                            headers.add("x-timestamp",utc);
                                            headers.add("x-signature",api.getHmac(utc));
                                            headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                            requestJson ="{" +
                                                             "\"kodebooking\": \""+rs.getString("nobooking")+"\"," +
                                                             "\"jenisresep\": \""+(Sequel.cariInteger("select count(resep_dokter_racikan.no_resep) from resep_dokter_racikan where resep_dokter_racikan.no_resep=?",noresep)>0?"Racikan":"Non Racikan")+"\"," +
                                                             "\"nomorantrean\": "+Integer.parseInt(StringUtils.right(noresep,4))+"," +
                                                             "\"keterangan\": \"Resep dibuat secara elektronik di poli\"" +
                                                          "}";
                                            TeksArea.append("JSON : "+requestJson+"\n");
                                            requestEntity = new HttpEntity(requestJson,headers);
                                            URL = link+"/antrean/farmasi/add";	
                                            System.out.println("URL : "+URL);
                                            //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                            nameNode = root.path("metadata");
                                            TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                        }catch (Exception ex) {
                                            System.out.println("Notifikasi Bridging : "+ex);
                                        }
                                    }

                                    datajam=Sequel.cariIsi("select concat(resep_obat.tgl_perawatan,' ',resep_obat.jam) from resep_obat where resep_obat.tgl_perawatan<>'0000-00-00' and resep_obat.status='ralan' and resep_obat.no_rawat=?",rs.getString("no_rawat"));
                                    if(!datajam.equals("")){
                                        if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat"),"6",datajam})==true){
                                            parsedDate = dateFormat.parse(datajam);
                                            try {     
                                                TeksArea.append("Menjalankan WS taskid permintaan resep poli Mobile JKN Pasien BPJS\n");
                                                headers = new HttpHeaders();
                                                headers.setContentType(MediaType.APPLICATION_JSON);
                                                headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                headers.add("x-timestamp",utc);
                                                headers.add("x-signature",api.getHmac(utc));
                                                headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                requestJson ="{" +
                                                                 "\"kodebooking\": \""+rs.getString("nobooking")+"\"," +
                                                                 "\"taskid\": \"6\"," +
                                                                 "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                              "}";
                                                TeksArea.append("JSON : "+requestJson+"\n");
                                                requestEntity = new HttpEntity(requestJson,headers);
                                                URL = link+"/antrean/updatewaktu";	
                                                System.out.println("URL : "+URL);
                                                //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                nameNode = root.path("metadata");
                                                if(!nameNode.path("code").asText().equals("200")){
                                                    Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='6' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                     String errorMessage = nameNode.path("message").asText();
                                        String errorCode = nameNode.path("code").asText();
                                        Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "6", errorCode, errorMessage});
                                                }  
                                                TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                            }catch (Exception ex) {
                                                System.out.println("Notifikasi Bridging : "+ex);
                                            }
                                        }
                                    }
                                }
                                
                                if(task6.equals("Sudah")&&task7.equals("")){
                                    datajam=Sequel.cariIsi("select concat(resep_obat.tgl_penyerahan,' ',resep_obat.jam_penyerahan) from resep_obat where resep_obat.status='ralan' and resep_obat.no_rawat=? and concat(resep_obat.tgl_penyerahan,' ',resep_obat.jam_penyerahan)<>'0000-00-00 00:00:00'",rs.getString("no_rawat"));
                                    if(!datajam.equals("")){
                                        if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat"),"7",datajam})==true){
                                            parsedDate = dateFormat.parse(datajam);
                                            try {     
                                                TeksArea.append("Menjalankan WS taskid validasi resep poli Mobile JKN Pasien BPJS\n");
                                                headers = new HttpHeaders();
                                                headers.setContentType(MediaType.APPLICATION_JSON);
                                                headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                headers.add("x-timestamp",utc);
                                                headers.add("x-signature",api.getHmac(utc));
                                                headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                requestJson ="{" +
                                                                 "\"kodebooking\": \""+rs.getString("nobooking")+"\"," +
                                                                 "\"taskid\": \"7\"," +
                                                                 "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                              "}";
                                                TeksArea.append("JSON : "+requestJson+"\n");
                                                requestEntity = new HttpEntity(requestJson,headers);
                                                URL = link+"/antrean/updatewaktu";	
                                                System.out.println("URL : "+URL);
                                                //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                nameNode = root.path("metadata");
                                                if(!nameNode.path("code").asText().equals("200")){
                                                    Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='7' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                     String errorMessage = nameNode.path("message").asText();
                                        String errorCode = nameNode.path("code").asText();
                                        Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "7", errorCode, errorMessage});
                                                }  
                                                TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                            }catch (Exception ex) {
                                                System.out.println("Notifikasi Bridging : "+ex);
                                            }
                                        }
                                    }
                                }
                                
                                if(task99.equals("")){
                                    datajam=Sequel.cariIsi("select now() from reg_periksa where reg_periksa.stts='Batal' and reg_periksa.no_rawat=?",rs.getString("no_rawat"));
                                    if(!datajam.equals("")){
                                        if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat"),"99",datajam})==true){
                                            parsedDate = dateFormat.parse(datajam);
                                            try {     
                                                TeksArea.append("Menjalankan WS taskid batal pelayanan poli Mobile JKN Pasien BPJS\n");
                                                headers = new HttpHeaders();
                                                headers.setContentType(MediaType.APPLICATION_JSON);
                                                headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                headers.add("x-timestamp",utc);
                                                headers.add("x-signature",api.getHmac(utc));
                                                headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                requestJson ="{" +
                                                                 "\"kodebooking\": \""+rs.getString("nobooking")+"\"," +
                                                                 "\"taskid\": \"99\"," +
                                                                 "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                              "}";
                                                TeksArea.append("JSON : "+requestJson+"\n");
                                                requestEntity = new HttpEntity(requestJson,headers);
                                                URL = link+"/antrean/updatewaktu";	
                                                System.out.println("URL : "+URL);
                                                //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                nameNode = root.path("metadata");
                                                if(!nameNode.path("code").asText().equals("200")){
                                                    Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='99' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                     String errorMessage = nameNode.path("message").asText();
                                        String errorCode = nameNode.path("code").asText();
                                        Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "99", errorCode, errorMessage});
                                                }  
                                                TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                            }catch (Exception ex) {
                                                System.out.println("Notifikasi Bridging : "+ex);
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Notif : "+ex);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }
                        
                        //pasien Non JKN
                        TeksArea.append("Menjalankan WS tambah antrian Mobile JKN Pasien Non BPJS/BJS Onsite\n");
                        ps=koneksi.prepareStatement(
                                "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.kd_poli,poliklinik.nm_poli,reg_periksa.stts_daftar,reg_periksa.no_rkm_medis,reg_periksa.kd_pj "+
                                "from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.tgl_registrasi between '"+Tanggal1.getText()+"' and '"+Tanggal2.getText()+"' "+
                                "and reg_periksa.no_rawat not in (select referensi_mobilejkn_bpjs.no_rawat from referensi_mobilejkn_bpjs where referensi_mobilejkn_bpjs.tanggalperiksa between '"+Tanggal1.getText()+"' and '"+Tanggal2.getText()+"') "+
                                "order by concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg)");
                        try {
                            rs=ps.executeQuery();
                            while(rs.next()){
                                ps2=koneksi.prepareStatement("select * from jadwal where jadwal.hari_kerja=? and jadwal.kd_dokter=? and jadwal.kd_poli=?");
                                try {
                                    ps2.setString(1,hari);
                                    ps2.setString(2,rs.getString("kd_dokter"));
                                    ps2.setString(3,rs.getString("kd_poli"));
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        kodedokter=Sequel.cariIsi("select maping_dokter_dpjpvclaim.kd_dokter_bpjs from maping_dokter_dpjpvclaim where maping_dokter_dpjpvclaim.kd_dokter=?",rs.getString("kd_dokter"));
                                        kodepoli=Sequel.cariIsi("select maping_poli_bpjs.kd_poli_bpjs from maping_poli_bpjs where maping_poli_bpjs.kd_poli_rs=?",rs.getString("kd_poli"));
                                        if((!kodedokter.equals(""))&&(!kodepoli.equals(""))){
                                            task3="";task4="";task5="";task6="";task7="";task99="";
                                            ps3=koneksi.prepareStatement("select referensi_mobilejkn_bpjs_taskid.taskid from referensi_mobilejkn_bpjs_taskid where referensi_mobilejkn_bpjs_taskid.no_rawat=?");
                                            try {
                                               ps3.setString(1,rs.getString("no_rawat"));
                                               rs3=ps3.executeQuery();
                                               while(rs3.next()){
                                                   if(rs3.getString("taskid").equals("3")){
                                                       task3="Sudah";
                                                   }
                                                   if(rs3.getString("taskid").equals("4")){
                                                       task4="Sudah";
                                                   }
                                                   if(rs3.getString("taskid").equals("5")){
                                                       task5="Sudah";
                                                   }
                                                   if(rs3.getString("taskid").equals("6")){
                                                       task6="Sudah";
                                                   }
                                                   if(rs3.getString("taskid").equals("7")){
                                                       task7="Sudah";
                                                   }
                                                   if(rs3.getString("taskid").equals("99")){
                                                       task99="Sudah";
                                                   }
                                               }
                                            } catch (Exception ex) {
                                                System.out.println("Notif : "+ex);
                                            } finally{
                                                if(rs3!=null){
                                                    rs3.close();
                                                }
                                                if(ps3!=null){
                                                    ps3.close();
                                                }
                                            }
                                            
                                            if(task3.equals("")){
                                                try {     
                                                    datajam=Sequel.cariIsi("select DATE_ADD(concat('"+rs.getString("tgl_registrasi")+"',' ','"+rs2.getString("jam_mulai")+"'),INTERVAL "+(Integer.parseInt(rs.getString("no_reg"))*5)+" MINUTE) ");
                                                    parsedDate = dateFormat.parse(datajam);
                                                    if(!rs.getString("kd_pj").equals(kodebpjs)){
                                                        headers = new HttpHeaders();
                                                        headers.setContentType(MediaType.APPLICATION_JSON);
                                                        headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                        utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                        headers.add("x-timestamp",utc);
                                                        headers.add("x-signature",api.getHmac(utc));
                                                        headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                        requestJson ="{" +
                                                                        "\"kodebooking\": \""+rs.getString("no_rawat")+"\"," +
                                                                        "\"jenispasien\": \"NON JKN\"," +
                                                                        "\"nomorkartu\": \"-\"," +
                                                                        "\"nik\": \"-\"," +
                                                                        "\"nohp\": \"-\"," +
                                                                        "\"kodepoli\": \""+Sequel.cariIsi("select maping_poli_bpjs.kd_poli_bpjs from maping_poli_bpjs where maping_poli_bpjs.kd_poli_rs=?",rs.getString("kd_poli"))+"\"," +
                                                                        "\"namapoli\": \""+rs.getString("nm_poli")+"\"," +
                                                                        "\"pasienbaru\": "+rs.getString("stts_daftar").replaceAll("Baru","1").replaceAll("Lama","0").replaceAll("-","0")+"," +
                                                                        "\"norm\": \""+rs.getString("no_rkm_medis")+"\"," +
                                                                        "\"tanggalperiksa\": \""+rs.getString("tgl_registrasi")+"\"," +
                                                                        "\"kodedokter\": "+Sequel.cariIsi("select maping_dokter_dpjpvclaim.kd_dokter_bpjs from maping_dokter_dpjpvclaim where maping_dokter_dpjpvclaim.kd_dokter=?",rs.getString("kd_dokter"))+"," +
                                                                        "\"namadokter\": \""+rs.getString("nm_dokter")+"\"," +
                                                                        "\"jampraktek\": \""+rs2.getString("jam_mulai").substring(0,5)+"-"+rs2.getString("jam_selesai").substring(0,5)+"\"," +
                                                                        "\"jeniskunjungan\": 3," +
                                                                        "\"nomorreferensi\": \"-\"," +
                                                                        "\"nomorantrean\": \""+rs.getString("no_reg")+"\"," +
                                                                        "\"angkaantrean\": "+Integer.parseInt(rs.getString("no_reg"))+"," +
                                                                        "\"estimasidilayani\": "+parsedDate.getTime()+"," +
                                                                        "\"sisakuotajkn\": "+(rs2.getInt("kuota")-Integer.parseInt(rs.getString("no_reg")))+"," +
                                                                        "\"kuotajkn\": "+rs2.getString("kuota")+"," +
                                                                        "\"sisakuotanonjkn\": "+(rs2.getInt("kuota")-Integer.parseInt(rs.getString("no_reg")))+"," +
                                                                        "\"kuotanonjkn\": "+rs2.getString("kuota")+"," +
                                                                        "\"keterangan\": \"Peserta harap 30 menit lebih awal guna pencatatan administrasi.\"" +
                                                                    "}";
                                                        TeksArea.append("JSON : "+requestJson+"\n");
                                                        requestEntity = new HttpEntity(requestJson,headers);
                                                        URL = link+"/antrean/add";	
                                                        System.out.println("URL : "+URL);
                                                        //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                        root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                        nameNode = root.path("metadata");  
                                                        
//                                                        if(!nameNode.path("code").asText().equals("200")){
                                                            String errorMessage = nameNode.path("message").asText();
                                                            String errorCode = nameNode.path("code").asText();
//                                                        }  
                                                        
                                                       if(!nameNode.path("code").asText().equals("200")){
                                                          Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",3,new String[]{rs.getString("no_rawat"), "6", errorCode, errorMessage});
                                                        Sequel.menyimpan("referensi_mobilejkn_bpjs_gagal","'"+rs.getString("no_rawat")+"','"+datajam+"','"+nameNode.path("message").asText()+"'");
                                                        }
                                                        TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                                    }
                                                }catch (Exception ex) {
                                                    System.out.println("Notifikasi Bridging : "+ex);
                                                }
                                                
                                                String newjam = Sequel.cariIsi("SELECT IF(mutasi_berkas.dikirim='0000-00-00 00:00:00', '', mutasi_berkas.dikirim) FROM mutasi_berkas WHERE mutasi_berkas.no_rawat=?",rs.getString("no_rawat"));
                                                // datajam=Sequel.cariIsi("select if(concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg)>concat('"+rs.getString("tgl_registrasi")+"',' ','"+rs2.getString("jam_mulai")+"'),concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg),concat('"+rs.getString("tgl_registrasi")+"',' ','"+newjam+"')) as tanggal from reg_periksa where reg_periksa.no_rawat=?",rs.getString("no_rawat"));
                                               datajam = Sequel.cariIsi(
                                                                    "SELECT IF(CONCAT(reg_periksa.tgl_registrasi, ' ', reg_periksa.jam_reg) > CONCAT('0+rs.getString("tgl_registrasi") + "', ' ', '" + rs2.getString("jam_mulai") + "'), " +
                                                                    "CONCAT(reg_periksa.tgl_registrasi, ' ', reg_periksa.jam_reg), " +
                                                                    "CONCAT('" + rs.getString("tgl_registrasi") + "', ' ', '" + newjam + "')) AS tanggal " +
                                                                    "FROM reg_periksa WHERE reg_periksa.no_rawat = '" + rs.getString("no_rawat") + "'"
                                                                );
                                               System.out.println(datajam);

                                                //datajam=Sequel.cariIsi("select if(concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg)>concat('"+rs.getString("tgl_registrasi")+"',' ','"+rs2.getString("jam_mulai")+"'),concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg),concat('"+rs.getString("tgl_registrasi")+"',' ','"+rs2.getString("jam_mulai")+"')) as tanggal from reg_periksa where reg_periksa.no_rawat=?",rs.getString("no_rawat"));
                                                if(!datajam.equals("")){
                                                    if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat"),"3",datajam})==true){
                                                        parsedDate = dateFormat.parse(datajam);
                                                        try {     
                                                            TeksArea.append("Menjalankan WS taskid mulai tunggu poli Mobile JKN Pasien Non BPJS/BPS Onsite\n");
                                                            headers = new HttpHeaders();
                                                            headers.setContentType(MediaType.APPLICATION_JSON);
                                                            headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                            utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                            headers.add("x-timestamp",utc);
                                                            headers.add("x-signature",api.getHmac(utc));
                                                            headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                            requestJson ="{" +
                                                                             "\"kodebooking\": \""+rs.getString("no_rawat")+"\"," +
                                                                             "\"taskid\": \"3\"," +
                                                                             "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                                          "}";
                                                            TeksArea.append("JSON : "+requestJson+"\n");
                                                            requestEntity = new HttpEntity(requestJson,headers);
                                                            URL = link+"/antrean/updatewaktu";	
                                                            System.out.println("URL : "+URL);
                                                            //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                            nameNode = root.path("metadata");
                                                            if(!nameNode.path("code").asText().equals("200")){
                                                                Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='3' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                                String errorMessage = nameNode.path("message").asText();
                                                                String errorCode = nameNode.path("code").asText();
                                                                Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "3", errorCode, errorMessage});
                                                            }  
                                                            TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                                        }catch (Exception ex) {
                                                            System.out.println("Notifikasi Bridging : "+ex);
                                                        }
                                                    }
                                                }
                                            }
                                            
                                            if(task3.equals("Sudah")&&task4.equals("")){
                                                datajam=Sequel.cariIsi("select if(mutasi_berkas.diterima='0000-00-00 00:00:00','',mutasi_berkas.diterima) from mutasi_berkas where mutasi_berkas.no_rawat=?",rs.getString("no_rawat"));
                                                if(datajam.equals("")){
                                                    datajam=Sequel.cariIsi("select concat(pemeriksaan_ralan.tgl_perawatan,' ',pemeriksaan_ralan.jam_rawat) from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat=?",rs.getString("no_rawat"));
                                                }
                                                if(!datajam.equals("")){
                                                    if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat"),"4",datajam})==true){
                                                        parsedDate = dateFormat.parse(datajam);
                                                        try {     
                                                            TeksArea.append("Menjalankan WS taskid mulai pelayanan poli Mobile JKN Pasien Non BPJS/BPS Onsite\n");
                                                            headers = new HttpHeaders();
                                                            headers.setContentType(MediaType.APPLICATION_JSON);
                                                            headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                            utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                            headers.add("x-timestamp",utc);
                                                            headers.add("x-signature",api.getHmac(utc));
                                                            headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                            requestJson ="{" +
                                                                             "\"kodebooking\": \""+rs.getString("no_rawat")+"\"," +
                                                                             "\"taskid\": \"4\"," +
                                                                             "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                                          "}";
                                                            TeksArea.append("JSON : "+requestJson+"\n");
                                                            requestEntity = new HttpEntity(requestJson,headers);
                                                            URL = link+"/antrean/updatewaktu";	
                                                            System.out.println("URL : "+URL);
                                                            //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                            nameNode = root.path("metadata");
                                                            if(!nameNode.path("code").asText().equals("200")){
                                                                Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='4' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                                  String errorMessage = nameNode.path("message").asText();
                                                                  String errorCode = nameNode.path("code").asText();
                                                                  Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "4", errorCode, errorMessage});
                                                            }   
                                                            TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                                        }catch (Exception ex) {
                                                            System.out.println("Notifikasi Bridging : "+ex);
                                                        }
                                                    }
                                                }
                                            }

                                            if(task4.equals("Sudah")&&task5.equals("")){
                                                  datajam=Sequel.cariIsi("select date_format(date_add(concat(pemeriksaan_ralan.tgl_perawatan, ' ', pemeriksaan_ralan.jam_rawat), interval 10 minute), '%Y-%m-%d %H:%i:%s') from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat =?",rs.getString("no_rawat"));
                                               
                                               // datajam=Sequel.cariIsi("select if(mutasi_berkas.kembali='0000-00-00 00:00:00','',mutasi_berkas.kembali) from mutasi_berkas where mutasi_berkas.no_rawat=?",rs.getString("no_rawat"));
                                                if(datajam.equals("")){
                                                    datajam=Sequel.cariIsi("select now() from reg_periksa where reg_periksa.stts='Sudah' and reg_periksa.no_rawat=?",rs.getString("no_rawat"));
                                                }
                                                if(!datajam.equals("")){
                                                    if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat"),"5",datajam})==true){
                                                        parsedDate = dateFormat.parse(datajam);
                                                        try {     
                                                            TeksArea.append("Menjalankan WS taskid selesai pelayanan poli Mobile JKN Pasien Non BPJS/BPS Onsite\n");
                                                            headers = new HttpHeaders();
                                                            headers.setContentType(MediaType.APPLICATION_JSON);
                                                            headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                            utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                            headers.add("x-timestamp",utc);
                                                            headers.add("x-signature",api.getHmac(utc));
                                                            headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                            requestJson ="{" +
                                                                             "\"kodebooking\": \""+rs.getString("no_rawat")+"\"," +
                                                                             "\"taskid\": \"5\"," +
                                                                             "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                                          "}";
                                                            TeksArea.append("JSON : "+requestJson+"\n");
                                                            requestEntity = new HttpEntity(requestJson,headers);
                                                            URL = link+"/antrean/updatewaktu";	
                                                            System.out.println("URL : "+URL);
                                                            //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                            nameNode = root.path("metadata");
                                                            if(!nameNode.path("code").asText().equals("200")){
                                                                Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='5' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                                String errorMessage = nameNode.path("message").asText();
                                                                String errorCode = nameNode.path("code").asText();
                                                                Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "5", errorCode, errorMessage});
                                                            }  
                                                            TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                                        }catch (Exception ex) {
                                                            System.out.println("Notifikasi Bridging : "+ex);
                                                        }
                                                    }
                                                }
                                            }

                                            if(task5.equals("Sudah")&&task6.equals("")){
                                                noresep=Sequel.cariIsi("select resep_obat.no_resep from resep_obat where resep_obat.no_rawat=?",rs.getString("no_rawat"));
                                                if(!noresep.equals("")){
                                                    try {     
                                                        TeksArea.append("Menjalankan WS tambah antrian farmasi Mobile JKN Pasien Non BPJS/BPS Onsite\n");
                                                        headers = new HttpHeaders();
                                                        headers.setContentType(MediaType.APPLICATION_JSON);
                                                        headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                        utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                        headers.add("x-timestamp",utc);
                                                        headers.add("x-signature",api.getHmac(utc));
                                                        headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                        requestJson ="{" +
                                                                         "\"kodebooking\": \""+rs.getString("no_rawat")+"\"," +
                                                                         "\"jenisresep\": \""+(Sequel.cariInteger("select count(resep_dokter_racikan.no_resep) from resep_dokter_racikan where resep_dokter_racikan.no_resep=?",noresep)>0?"Racikan":"Non Racikan")+"\"," +
                                                                         "\"nomorantrean\": "+Integer.parseInt(StringUtils.right(noresep,4))+"," +
                                                                         "\"keterangan\": \"Resep dibuat secara elektronik di poli\"" +
                                                                      "}";
                                                        TeksArea.append("JSON : "+requestJson+"\n");
                                                        requestEntity = new HttpEntity(requestJson,headers);
                                                        URL = link+"/antrean/farmasi/add";	
                                                        System.out.println("URL : "+URL);
                                                        //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                        root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                        nameNode = root.path("metadata");
                                                        TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                                    }catch (Exception ex) {
                                                        System.out.println("Notifikasi Bridging : "+ex);
                                                    }
                                                }
                                                  datajam=Sequel.cariIsi("select concat(resep_obat.tgl_perawatan,' ',resep_obat.jam_peresepan) from resep_obat where resep_obat.tgl_perawatan<>'0000-00-00' and resep_obat.status='ralan' and resep_obat.no_rawat=?",rs.getString("no_rawat"));
                                                //datajam=Sequel.cariIsi("select concat(resep_obat.tgl_perawatan,' ',resep_obat.jam) from resep_obat where resep_obat.tgl_perawatan<>'0000-00-00' and resep_obat.status='ralan' and resep_obat.no_rawat=?",rs.getString("no_rawat"));
                                                if(!datajam.equals("")){
                                                    if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat"),"6",datajam})==true){
                                                        parsedDate = dateFormat.parse(datajam);
                                                        try {     
                                                            TeksArea.append("Menjalankan WS taskid permintaan resep poli Mobile JKN Pasien Non BPJS/BPS Onsite\n");
                                                            headers = new HttpHeaders();
                                                            headers.setContentType(MediaType.APPLICATION_JSON);
                                                            headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                            utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                            headers.add("x-timestamp",utc);
                                                            headers.add("x-signature",api.getHmac(utc));
                                                            headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                            requestJson ="{" +
                                                                             "\"kodebooking\": \""+rs.getString("no_rawat")+"\"," +
                                                                             "\"taskid\": \"6\"," +
                                                                             "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                                          "}";
                                                            TeksArea.append("JSON : "+requestJson+"\n");
                                                            requestEntity = new HttpEntity(requestJson,headers);
                                                            URL = link+"/antrean/updatewaktu";	
                                                            System.out.println("URL : "+URL);
                                                            //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                            nameNode = root.path("metadata");
                                                            if(!nameNode.path("code").asText().equals("200")){
                                                                Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='6' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                                String errorMessage = nameNode.path("message").asText();
                                                                String errorCode = nameNode.path("code").asText();
                                                                Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "6", errorCode, errorMessage});
                                                            }  
                                                            TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                                        }catch (Exception ex) {
                                                            System.out.println("Notifikasi Bridging : "+ex);
                                                        }
                                                    }
                                                }
                                            }

                                            if(task6.equals("Sudah")&&task7.equals("")){
                                                  datajam=Sequel.cariIsi("select concat(resep_obat.tgl_perawatan,' ',resep_obat.jam) from resep_obat where resep_obat.status='ralan' and resep_obat.no_rawat=? and concat(resep_obat.tgl_perawatan,' ',resep_obat.jam)<>'0000-00-00 00:00:00'",rs.getString("no_rawat"));
                                               
                                              //  datajam=Sequel.cariIsi("select concat(resep_obat.tgl_penyerahan,' ',resep_obat.jam_penyerahan) from resep_obat where resep_obat.status='ralan' and resep_obat.no_rawat=? and concat(resep_obat.tgl_penyerahan,' ',resep_obat.jam_penyerahan)<>'0000-00-00 00:00:00'",rs.getString("no_rawat"));
                                                if(!datajam.equals("")){
                                                    if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat"),"7",datajam})==true){
                                                        parsedDate = dateFormat.parse(datajam);
                                                        try {     
                                                            TeksArea.append("Menjalankan WS taskid validasi resep poli Mobile JKN Pasien Non BPJS/BPS Onsite\n");
                                                            headers = new HttpHeaders();
                                                            headers.setContentType(MediaType.APPLICATION_JSON);
                                                            headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                            utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                            headers.add("x-timestamp",utc);
                                                            headers.add("x-signature",api.getHmac(utc));
                                                            headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                            requestJson ="{" +
                                                                             "\"kodebooking\": \""+rs.getString("no_rawat")+"\"," +
                                                                             "\"taskid\": \"7\"," +
                                                                             "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                                          "}";
                                                            TeksArea.append("JSON : "+requestJson+"\n");
                                                            requestEntity = new HttpEntity(requestJson,headers);
                                                            URL = link+"/antrean/updatewaktu";	
                                                            System.out.println("URL : "+URL);
                                                            //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                            nameNode = root.path("metadata");
                                                            if(!nameNode.path("code").asText().equals("200")){
                                                                Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='7' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                                String errorMessage = nameNode.path("message").asText();
                                                                String errorCode = nameNode.path("code").asText();
                                                                Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "7", errorCode, errorMessage});
                                                            }  
                                                            TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                                        }catch (Exception ex) {
                                                            System.out.println("Notifikasi Bridging : "+ex);
                                                        }
                                                    }
                                                }
                                            }

                                            if(task99.equals("")){
                                                datajam=Sequel.cariIsi("select now() from reg_periksa where reg_periksa.stts='Batal' and reg_periksa.no_rawat=?",rs.getString("no_rawat"));
                                                if(!datajam.equals("")){
                                                    if(Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid","?,?,?","task id",3,new String[]{rs.getString("no_rawat"),"99",datajam})==true){
                                                        parsedDate = dateFormat.parse(datajam);
                                                        try {     
                                                            TeksArea.append("Menjalankan WS taskid batal pelayanan poli Mobile JKN Pasien Non BPJS/BPS Onsite\n");
                                                            headers = new HttpHeaders();
                                                            headers.setContentType(MediaType.APPLICATION_JSON);
                                                            headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                                                            utc=String.valueOf(api.GetUTCdatetimeAsString());
                                                            headers.add("x-timestamp",utc);
                                                            headers.add("x-signature",api.getHmac(utc));
                                                            headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                                                            requestJson ="{" +
                                                                             "\"kodebooking\": \""+rs.getString("no_rawat")+"\"," +
                                                                             "\"taskid\": \"99\"," +
                                                                             "\"waktu\": \""+parsedDate.getTime()+"\"" +
                                                                          "}";
                                                            TeksArea.append("JSON : "+requestJson+"\n");
                                                            requestEntity = new HttpEntity(requestJson,headers);
                                                            URL = link+"/antrean/updatewaktu";	
                                                            System.out.println("URL : "+URL);
                                                            //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                                            nameNode = root.path("metadata");
                                                            if(!nameNode.path("code").asText().equals("200")){
                                                                Sequel.queryu2("delete from referensi_mobilejkn_bpjs_taskid where taskid='99' and no_rawat='"+rs.getString("no_rawat")+"'");
                                                                String errorMessage = nameNode.path("message").asText();
                                                                String errorCode = nameNode.path("code").asText();
                                                                Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid_gagal","?,?,?,?","task id error",4,new String[]{rs.getString("no_rawat"), "99", errorCode, errorMessage});
                                                            }  
                                                            TeksArea.append("respon WS BPJS : "+nameNode.path("code").asText()+" "+nameNode.path("message").asText()+"\n");
                                                        }catch (Exception ex) {
                                                            System.out.println("Notifikasi Bridging : "+ex);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception ex) {
                                    System.out.println("Notif : "+ex);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Notif Ketersediaan : "+ex);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }
                        
                        TeksArea.append("Proses update selesai\n");
                    } catch (Exception ez) {
                        System.out.println("Notif : "+ez);
                    }
                }
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
}