/*
 * Kontribusi dari iman banik
 */


package surat;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import fungsi.FileUploader;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariDokter2;


/**
 *
 * @author perpustakaan
 */
public final class PRawatInap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariDokter2 dokter2=new DlgCariDokter2(null,false);
    private String aktifjadwal="",FileName="";
   
        private String finger = "";
    public PRawatInap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tabMode = new DefaultTableModel(null, new Object[]{
           "No.RM", "No.Rawat","Tanggal","Nama Pasien", "KD.DPJP", "Nama DPJP", "KD.DR.IGD","Nama Dokter IGD", "Diagnosa", "Pengobatan"
        }) {
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false;
        }
    };
        
    tbObat.setModel(tabMode);
    tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
    tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    // Set lebar kolom
    for (i = 0; i < 10; i++) {
          TableColumn column = tbObat.getColumnModel().getColumn(i);
        switch (i) {
            case 0: column.setPreferredWidth(50); break;    // No.RM
            case 1: column.setPreferredWidth(120); break;   // No. Rawat
            case 2: column.setPreferredWidth(100); break;   // Tanggal
            case 3: column.setPreferredWidth(220); break;   // Nama Pasien
            case 4: column.setPreferredWidth(60); break;    // KD.DPJP
            case 5: column.setPreferredWidth(220); break;   // Nama DPJP
            case 6: column.setPreferredWidth(60); break;    // KD.IGD
            case 7: column.setPreferredWidth(220); break;   // Nama Dokter IGD
            case 8: column.setPreferredWidth(250); break;   // Diagnosa
            case 9: column.setPreferredWidth(250); break;  // Pengobatan
        }
    }

    tbObat.setDefaultRenderer(Object.class, new WarnaTable());
    TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
    Tdiagnosa.setDocument(new batasInput(800).getKata(Tdiagnosa));
    TPengobatan.setDocument(new batasInput(800).getKata(TPengobatan));      
    TCari.setDocument(new batasInput((int)100).getKata(TCari));    
    
    if(koneksiDB.CARICEPAT().equals("aktif")){
        TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(TCari.getText().length()>2){
                    tampil();
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if(TCari.getText().length()>2){
                    tampil();
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                if(TCari.getText().length()>2){
                    tampil();
                }
            }
        });
    }   
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                    
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
//                    if(aktifjadwal.equals("aktif")){
//                        kuota=Integer.parseInt(dokter.getTable().getValueAt(dokter2.getTable().getSelectedRow(),13).toString());
//                    }
//                    isNomer();                        
                }      
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
           
      
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu2 = new javax.swing.JPopupMenu();
        cetakPengatarRawatInap = new javax.swing.JMenuItem();
        UploadBerkasDigitalPRawatInap = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jSeparator14 = new javax.swing.JSeparator();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        Tanggal = new widget.Tanggal();
        TAlamat = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel18 = new widget.Label();
        jLabel31 = new widget.Label();
        jLabel33 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        Tdiagnosa = new widget.TextArea();
        scrollPane4 = new widget.ScrollPane();
        TPengobatan = new widget.TextArea();
        TPekerjaan = new widget.TextBox();
        KdDokter = new widget.TextBox();
        jLabel16 = new widget.Label();
        BtnDokter = new widget.Button();
        NmDokter = new widget.TextBox();
        NmDKirim = new widget.TextBox();
        kdDKirim = new widget.TextBox();
        jLabel17 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        jPopupMenu2.setName("jPopupMenu2"); // NOI18N

        cetakPengatarRawatInap.setBackground(new java.awt.Color(255, 255, 254));
        cetakPengatarRawatInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        cetakPengatarRawatInap.setForeground(new java.awt.Color(50, 50, 50));
        cetakPengatarRawatInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        cetakPengatarRawatInap.setText("Laporan Pengantar Rawat Inap");
        cetakPengatarRawatInap.setToolTipText("");
        cetakPengatarRawatInap.setComponentPopupMenu(jPopupMenu2);
        cetakPengatarRawatInap.setName("cetakPengatarRawatInap"); // NOI18N
        cetakPengatarRawatInap.setPreferredSize(new java.awt.Dimension(220, 26));
        cetakPengatarRawatInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakPengatarRawatInapActionPerformed(evt);
            }
        });
        jPopupMenu2.add(cetakPengatarRawatInap);

        UploadBerkasDigitalPRawatInap.setBackground(new java.awt.Color(255, 255, 254));
        UploadBerkasDigitalPRawatInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        UploadBerkasDigitalPRawatInap.setForeground(new java.awt.Color(50, 50, 50));
        UploadBerkasDigitalPRawatInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        UploadBerkasDigitalPRawatInap.setText("Upload Berkas Digital Pengantar Rawat Inap");
        UploadBerkasDigitalPRawatInap.setToolTipText("");
        UploadBerkasDigitalPRawatInap.setComponentPopupMenu(jPopupMenu2);
        UploadBerkasDigitalPRawatInap.setName("UploadBerkasDigitalPRawatInap"); // NOI18N
        UploadBerkasDigitalPRawatInap.setPreferredSize(new java.awt.Dimension(220, 26));
        UploadBerkasDigitalPRawatInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadBerkasDigitalPRawatInapActionPerformed(evt);
            }
        });
        jPopupMenu2.add(UploadBerkasDigitalPRawatInap);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengantar Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setToolTipText("");
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 663));
        FormInput.setLayout(null);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 861, 880, 0);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(100, 20, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(350, 20, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(240, 20, 100, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(30, 50, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        TglLahir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglLahirActionPerformed(evt);
            }
        });
        FormInput.add(TglLahir);
        TglLahir.setBounds(100, 50, 240, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        Jk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JkActionPerformed(evt);
            }
        });
        FormInput.add(Jk);
        Jk.setBounds(100, 80, 240, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(20, 20, 70, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(730, 20, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(60, 80, 30, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-05-2025 17:51:53" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(810, 20, 130, 23);

        TAlamat.setEditable(false);
        TAlamat.setHighlighter(null);
        TAlamat.setName("TAlamat"); // NOI18N
        TAlamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlamatKeyPressed(evt);
            }
        });
        FormInput.add(TAlamat);
        TAlamat.setBounds(100, 140, 530, 23);

        jLabel15.setText("Alamat :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(30, 140, 60, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Pekerjaan :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(30, 110, 70, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Diagnosa :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(100, 260, 170, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Pengobatan Sementara/intruksi yang telah diberikan :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(530, 260, 370, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        Tdiagnosa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tdiagnosa.setColumns(20);
        Tdiagnosa.setRows(5);
        Tdiagnosa.setName("Tdiagnosa"); // NOI18N
        Tdiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TdiagnosaKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(Tdiagnosa);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(100, 280, 410, 230);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        TPengobatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPengobatan.setColumns(20);
        TPengobatan.setRows(5);
        TPengobatan.setName("TPengobatan"); // NOI18N
        TPengobatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPengobatanKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(TPengobatan);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(530, 280, 410, 230);

        TPekerjaan.setEditable(false);
        TPekerjaan.setHighlighter(null);
        TPekerjaan.setName("TPekerjaan"); // NOI18N
        TPekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(TPekerjaan);
        TPekerjaan.setBounds(100, 110, 530, 23);

        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(100, 210, 140, 23);

        jLabel16.setText("Dokter Pengirim :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 170, 90, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('X');
        BtnDokter.setToolTipText("Alt+X");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(640, 210, 28, 23);

        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmDokterKeyPressed(evt);
            }
        });
        FormInput.add(NmDokter);
        NmDokter.setBounds(250, 210, 380, 23);

        NmDKirim.setEditable(false);
        NmDKirim.setName("NmDKirim"); // NOI18N
        NmDKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmDKirimActionPerformed(evt);
            }
        });
        FormInput.add(NmDKirim);
        NmDKirim.setBounds(250, 170, 380, 23);
        NmDKirim.getAccessibleContext().setAccessibleName("");

        kdDKirim.setEditable(false);
        kdDKirim.setHighlighter(null);
        kdDKirim.setName("kdDKirim"); // NOI18N
        FormInput.add(kdDKirim);
        kdDKirim.setBounds(100, 170, 140, 23);

        jLabel17.setText("DPJP :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(30, 210, 60, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pengantar Rawat Inap", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.setComponentPopupMenu(jPopupMenu2);
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Pindah :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-05-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-05-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(197, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Daftar Pengantar Rawat Inap", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(TglLahir.getText().trim().equals("")){
            Valid.textKosong(TglLahir,"Tanggal Lahir");
        }
        else if(kdDKirim.getText().trim().equals("")){
            Valid.textKosong(kdDKirim,"Kode Dokter");
        }
        else if(NmDKirim.getText().trim().equals("")){
            Valid.textKosong(NmDKirim,"Nama Dokter");
        }
        else if(TPekerjaan.getText().trim().equals("")){
            Valid.textKosong(TPekerjaan,"Pekerjaan");
        }else if(TAlamat.getText().trim().equals("")){
            Valid.textKosong(TAlamat,"Alamat");
        }else if(Tdiagnosa.getText().trim().equals("")){
            Valid.textKosong(Tdiagnosa,"Diagnosa");        
         }else if(TPengobatan.getText().trim().equals("")){
            Valid.textKosong(Tdiagnosa,"Pengobatan");
        }
        else{
          //  if(akses.getkode().equals("Admin Utama")){
                simpan();
           // }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else {
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }             
            
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(TPekerjaan.getText().trim().equals("")){
            Valid.textKosong(TPekerjaan,"Asal Ruang");
        }else if(TAlamat.getText().trim().equals("")){
            Valid.textKosong(TAlamat,"Ruang Selanjutnya");
        }else if(Tdiagnosa.getText().trim().equals("")){
            Valid.textKosong(Tdiagnosa,"Obat Yang Diberikan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);

        }else{
//           Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
//            try {
//                isPhoto();
//                panggilPhoto();
//            } catch (java.lang.NullPointerException e) {
//            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        //Valid.pindah2(evt,TNoRw,TanggalPindah);
    }//GEN-LAST:event_TanggalKeyPressed

    private void TAlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlamatKeyPressed
      //  Valid.pindah(evt,AsalRuang,MetodePemindahan);
    }//GEN-LAST:event_TAlamatKeyPressed

    private void TdiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TdiagnosaKeyPressed
      //  Valid.pindah2(evt,ProsedurDilakukan,PemeriksaanPenunjang);
    }//GEN-LAST:event_TdiagnosaKeyPressed

    private void TPengobatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPengobatanKeyPressed
       // Valid.pindah2(evt,ObatYangDiberikan,PeralatanMenyertai);
    }//GEN-LAST:event_TPengobatanKeyPressed

    private void TPekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPekerjaanKeyPressed
       // Valid.pindah(evt,KeteranganIndikasiPindahRuang,RuangSelanjutnya);
    }//GEN-LAST:event_TPekerjaanKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        if(aktifjadwal.equals("aktif")){
            if(akses.getkode().equals("Admin Utama")){
                dokter.isCek();
                dokter.TCari.requestFocus();
                dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dokter.setLocationRelativeTo(internalFrame1);
                dokter.setVisible(true);
            }else{
               // dokter2.setPoli(NmPoli.getText());
                dokter2.isCek();
              //  dokter2.SetHari(TanggalPeriksa.getDate());
                dokter2.tampil();
                dokter2.TCari.requestFocus();
                dokter2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dokter2.setLocationRelativeTo(internalFrame1);
                dokter2.setVisible(true);
            }
        }else{
            dokter.isCek();
            dokter.TCari.requestFocus();
            dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dokter.setLocationRelativeTo(internalFrame1);
            dokter.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnDokterActionPerformed(null);
        }else{
//            Valid.pindah(evt,Rtl2,BtnPoli);
        }
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void NmDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmDokterKeyPressed

    private void JkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JkActionPerformed

    private void TglLahirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglLahirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglLahirActionPerformed

    private void NmDKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmDKirimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmDKirimActionPerformed

    private void cetakPengatarRawatInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakPengatarRawatInapActionPerformed
          if (tbObat.getSelectedRow() > -1) {
              this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                String noRawat = tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString();
                param.put("NoRawat", noRawat); // <-- INI YANG KURANG SEBELUMNYA
                System.out.println( noRawat);
                // "No.RM", "No.Rawat","Tanggal","Nama Pasien", "KD.DPJP", "Nama DPJP", "KD.DR.IGD","Nama Dokter IGD", "Diagnosa", "Pengobatan"
                finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            param.put("finger", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString() + "\nID " + (finger.equals("") ? tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString() : finger) + "\n" + Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString()));
                Valid.MyReport("rptCetakPengantarRawatInap.jasper",param, "::[ Cetak Pengantar Rawat Inap ]::");
                this.setCursor(Cursor.getDefaultCursor());
    } else {
        JOptionPane.showMessageDialog(null, "Silakan pilih data terlebih dahulu!");
    }
    }//GEN-LAST:event_cetakPengatarRawatInapActionPerformed

    private void UploadBerkasDigitalPRawatInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadBerkasDigitalPRawatInapActionPerformed
        // TODO add your handling code here:
        FileName = "PENGANTAR RANAP_"+ tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString().replaceAll("/", "") + "_" + tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString();
        CreatePDF(FileName);
        String filePath = "tmpPDF/" + FileName;
        FileUploader.UploadPDF(FileName, "berkasrawat/pages/upload/", "PENGANTAR RANAP", tbObat,1);
    }//GEN-LAST:event_UploadBerkasDigitalPRawatInapActionPerformed
    private void CreatePDF(String FileName){
        if (tbObat.getSelectedRow() > -1) {
              this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                String noRawat = tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString();
                param.put("NoRawat", noRawat); // <-- INI YANG KURANG SEBELUMNYA
                System.out.println( noRawat);
                // "No.RM", "No.Rawat","Tanggal","Nama Pasien", "KD.DPJP", "Nama DPJP", "KD.DR.IGD","Nama Dokter IGD", "Diagnosa", "Pengobatan"
                finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            param.put("finger", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString() + "\nID " + (finger.equals("") ? tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString() : finger) + "\n" + Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString()));
//                Valid.MyReport("rptCetakPengantarRawatInap.jasper",param, "::[ Cetak Pengantar Rawat Inap ]::");
                Valid.MyReportPDFUpload("rptCetakPengantarRawatInap.jasper", "report", "::[ Laporan Pengantar Rawat Inap ]::",FileName, param);
                this.setCursor(Cursor.getDefaultCursor());
    } else {
        JOptionPane.showMessageDialog(null, "Silakan pilih data terlebih dahulu!");
    }
    }
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            PRawatInap dialog = new PRawatInap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.Label LCount;
    private widget.TextBox NmDKirim;
    private widget.TextBox NmDokter;
    private widget.ScrollPane Scroll;
    private widget.TextBox TAlamat;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPekerjaan;
    private widget.TextArea TPengobatan;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.TextArea Tdiagnosa;
    private widget.TextBox TglLahir;
    private javax.swing.JMenuItem UploadBerkasDigitalPRawatInap;
    private javax.swing.JMenuItem cetakPengatarRawatInap;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel31;
    private widget.Label jLabel33;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JSeparator jSeparator14;
    private widget.TextBox kdDKirim;
    private widget.Label label11;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

   public void tampil() {
    Valid.tabelKosong(tabMode);
    try {
        if (TCari.getText().trim().equals("")) {
            ps = koneksi.prepareStatement(
                "SELECT t.id, t.tanggal, t.no_rawat, t.no_rkm_medis, p.nm_pasien, " +
                "t.kd_dpjp, d.nm_dokter AS nama_dpjp, " +
                "t.kd_drigd, di.nm_dokter AS nama_drigd, " +
                "t.diagnosa, t.pengobatan " +
                "FROM pengantar_rawat_inap t " +
                "INNER JOIN pasien p ON t.no_rkm_medis = p.no_rkm_medis " +
                "INNER JOIN dokter d ON t.kd_dpjp = d.kd_dokter " +
                "INNER JOIN dokter di ON t.kd_drigd = di.kd_dokter " +
                "WHERE t.tanggal BETWEEN ? AND ? " +
                "ORDER BY t.tanggal"
            );
        } else {
            ps = koneksi.prepareStatement(
                "SELECT t.id, t.tanggal, t.no_rawat, t.no_rkm_medis, p.nm_pasien, " +
                "t.kd_dpjp, d.nm_dokter AS nama_dpjp, " +
                "t.kd_drigd, di.nm_dokter AS nama_drigd, " +
                "t.diagnosa, t.pengobatan " +
                "FROM pengantar_rawat_inap t " +
                "INNER JOIN pasien p ON t.no_rkm_medis = p.no_rkm_medis " +
                "INNER JOIN dokter d ON t.kd_dpjp = d.kd_dokter " +
                "INNER JOIN dokter di ON t.kd_drigd = di.kd_dokter " +
                "WHERE t.tanggal BETWEEN ? AND ? AND (t.no_rawat LIKE ? OR t.no_rkm_medis LIKE ? OR p.nm_pasien LIKE ?) " +
                "ORDER BY t.tanggal"
            );
        }

        String tgl1 = Valid.SetTgl(DTPCari1.getSelectedItem() + "");
        String tgl2 = Valid.SetTgl(DTPCari2.getSelectedItem() + "");

        if (TCari.getText().trim().equals("")) {
            ps.setString(1, tgl1);
            ps.setString(2, tgl2);
        } else {
            String keyword = "%" + TCari.getText().trim() + "%";
            ps.setString(1, tgl1);
            ps.setString(2, tgl2);
            ps.setString(3, keyword);
            ps.setString(4, keyword);
            ps.setString(5, keyword);
        }

        rs = ps.executeQuery();
        while (rs.next()) {
            tabMode.addRow(new String[]{
                rs.getString("no_rkm_medis"),
                rs.getString("no_rawat"),
                rs.getString("tanggal"),
                rs.getString("nm_pasien"),
                rs.getString("kd_dpjp"),
                rs.getString("nama_dpjp"),
                rs.getString("kd_drigd"),
                rs.getString("nama_drigd"),
                rs.getString("diagnosa"),
                rs.getString("pengobatan")
            });
        }
    } catch (Exception e) {
        System.out.println("Notif tampil(): " + e);
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (Exception e) {
            System.out.println("Notif close rs/ps: " + e);
        }
    }

    LCount.setText("" + tabMode.getRowCount());
}
 

    public void emptTeks() {
      
        Tdiagnosa.setText("");
        TPengobatan.setText("");
        KdDokter.setText("");
        NmDokter.setText("");
        TabRawat.setSelectedIndex(0);
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()); 
           // Tanggal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()); 
            kdDKirim.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            NmDKirim.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            Tdiagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
            TPengobatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()); 
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.pekerjaan, pasien.alamat,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TPekerjaan.setText(rs.getString("pekerjaan"));
                    TAlamat.setText(rs.getString("alamat"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
 
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);  
        kdDKirim.setText(akses.getkode());
        NmDKirim.setText(dokter.tampil3(kdDKirim.getText()));
         if(NmDKirim.getText().equals("")){
                kdDKirim.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        isRawat(); 
    }
        
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettransfer_pasien_antar_ruang());
        BtnHapus.setEnabled(akses.gettransfer_pasien_antar_ruang());
        BtnEdit.setEnabled(akses.gettransfer_pasien_antar_ruang());
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("DELETE FROM pengantar_rawat_inap WHERE no_rawat=? AND tanggal=?", 2, new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString(),  // no_rawat
            tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString()   // tanggal (pastikan kolom ke-5 adalah tanggal!)
        }) == true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText("" + tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
        }
    }

    private void ganti() {
        // Mengedit data pada tabel pengantar_rawat_inap
        if(Sequel.mengedittf("pengantar_rawat_inap", 
            "no_rawat=?", // kondisi WHERE
            "no_rawat=?, tanggal=?, no_rkm_medis=?, kd_dpjp=?, kd_drigd=?, diagnosa=?, pengobatan=?",
            8,
            new String[] {
                TNoRw.getText(), // no_rawat
                Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Tanggal.getSelectedItem().toString().substring(11, 19), // tanggal
                TNoRM.getText(), // no_rkm_medis
                KdDokter.getText(), // kd_dpjp
                kdDKirim.getText(), // kd_drigd
                Tdiagnosa.getText(), // diagnosa
                TPengobatan.getText(), // pengobatan
                TNoRw.getText() // 8 - no_rawat kondisi
            }) == true) {
            // Update nilai di tabel UI setelah berhasil edit
            tbObat.setValueAt(TNoRM.getText(), tbObat.getSelectedRow(), 0); // Update no_rkm_medis
            tbObat.setValueAt(TNoRw.getText(), tbObat.getSelectedRow(), 1); // Update no_rawat
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Tanggal.getSelectedItem().toString().substring(11, 19), tbObat.getSelectedRow(), 2); // Update tanggal
            tbObat.setValueAt(TPasien.getText(), tbObat.getSelectedRow(), 3); // Update nama pasien       
            tbObat.setValueAt(KdDokter.getText(), tbObat.getSelectedRow(), 4); // Update kd_dpjp
            tbObat.setValueAt(NmDokter.getText(), tbObat.getSelectedRow(), 5); // Update nama dpjp
            tbObat.setValueAt(kdDKirim.getText(), tbObat.getSelectedRow(), 6); // Update kd_drigd
            tbObat.setValueAt(NmDKirim.getText(), tbObat.getSelectedRow(), 7); // Update nama dr. igd
            tbObat.setValueAt(Tdiagnosa.getText(), tbObat.getSelectedRow(), 8); // Update diagnosa
            tbObat.setValueAt(TPengobatan.getText(), tbObat.getSelectedRow(), 9); // Update pengobatan
            emptTeks();  // Reset form input setelah update
            TabRawat.setSelectedIndex(1);  // Pindahkan ke tab yang diinginkan
        }
    }

    private void simpan() {
        if (Sequel.cariInteger("SELECT COUNT(*) FROM pengantar_rawat_inap WHERE no_rawat=?", TNoRw.getText()) > 0) {
            JOptionPane.showMessageDialog(null, "Data pengantar rawat inap untuk No. Rawat ini sudah ada.");
            return;
        }
        if(Sequel.menyimpantf("pengantar_rawat_inap","?,?,?,?,?,?,?,?", "No.Rawat",8,new String[]{
            Valid.autoNomer("pengantar_rawat_inap","PR",6), // contoh auto ID kalau perlu
            TNoRM.getText(), // no_rkm_medis
            TNoRw.getText(), // no_rawat        
            KdDokter.getText(), // kd_dpjp
            kdDKirim.getText(), // kd_drigd
            Tdiagnosa.getText(), // diagnosa
            TPengobatan.getText(), // pengobatan
            Valid.SetTgl(Tanggal.getSelectedItem()+"")
        })==true){
            emptTeks(); // fungsi untuk mengosongkan field setelah simpan
        }        
    }        
}
    

