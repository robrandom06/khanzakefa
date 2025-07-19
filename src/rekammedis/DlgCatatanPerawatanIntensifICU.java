package rekammedis;

import simrskhanza.*;
import keuangan.DlgCariPerawatanRanap;
import keuangan.DlgCariPerawatanRanap2;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;
import keuangan.Jurnal;

/**
 *
 * @author perpustakaan
 */
public final class DlgCatatanPerawatanIntensifICU extends javax.swing.JDialog {

    private final DefaultTableModel tabModeObservasi, tabModeVentilator, tabModeInput,
            tabModeOutput, tabModeProgHarian, tabModeVitalising, tabModeGinekologi;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Jurnal jur = new Jurnal();
    private Connection koneksi = koneksiDB.condb();
    public DlgCariPerawatanRanap perawatan = new DlgCariPerawatanRanap(null, false);
    public DlgCariPerawatanRanap2 perawatan2 = new DlgCariPerawatanRanap2(null, false);
    public DlgCariPegawai pegawai = new DlgCariPegawai(null, false);
    public DlgCariPasien pasien = new DlgCariPasien(null, false);
    private RMCari5SOAPTerakhir soapterakhir = new RMCari5SOAPTerakhir(null, false);
    private PreparedStatement ps, ps2, ps3, ps4, ps5, psrekening, ps6;
    private ResultSet rs, rsrekening;
    private int i = 0, tinggi = 0;
    private boolean sukses = false;
    private double ttljmdokter = 0, ttljmperawat = 0, ttlkso = 0, ttlpendapatan = 0, ttljasasarana = 0, ttlbhp = 0, ttlmenejemen = 0;
    private String Suspen_Piutang_Tindakan_Ranap = "", Tindakan_Ranap = "", Beban_Jasa_Medik_Dokter_Tindakan_Ranap = "", Utang_Jasa_Medik_Dokter_Tindakan_Ranap = "",
            Beban_Jasa_Medik_Paramedis_Tindakan_Ranap = "", Utang_Jasa_Medik_Paramedis_Tindakan_Ranap = "", Beban_KSO_Tindakan_Ranap = "", Utang_KSO_Tindakan_Ranap = "",
            Beban_Jasa_Sarana_Tindakan_Ranap = "", Utang_Jasa_Sarana_Tindakan_Ranap = "", Beban_Jasa_Menejemen_Tindakan_Ranap = "", Utang_Jasa_Menejemen_Tindakan_Ranap = "",
            HPP_BHP_Tindakan_Ranap = "", Persediaan_BHP_Tindakan_Ranap = "", kode_poli = "", kamar = "", jenisbayar = "", kodeoperator = "", finger = "";

    /**
     * Creates new form DlgRawatInap
     *
     * @param parent
     * @param modal
     */
    public DlgCatatanPerawatanIntensifICU(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initRawatInap();

        this.setLocation(8, 1);
        setSize(885, 674);

        tabModeObservasi = new DefaultTableModel(null, new Object[]{
            "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Tgl.Rawat", "Jam Rawat", "Terapi Oksigen", "Kesadaran", "Ukuran Pupil", "RR", "HR", "SP02"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbObservasi.setModel(tabModeObservasi);
        //tampilDr();
        tampilObservasi();

        tbObservasi.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObservasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbObservasi.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(180);
            } else if (i == 5) {
                column.setPreferredWidth(90);
            } else if (i == 6) {
                column.setPreferredWidth(180);
            } else if (i == 7) {
                column.setPreferredWidth(80);
            } else if (i == 8) {
                column.setPreferredWidth(75);
            } else if (i == 9) {
                column.setPreferredWidth(90);
            } else if (i == 10) {
                column.setPreferredWidth(90);
            } else if (i == 11) {
                column.setPreferredWidth(90);
            }
        }
        tbObservasi.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeVitalising = new DefaultTableModel(null, new Object[]{
            "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Tgl.Rawat", "Jam Rawat", "Tekanan Darah", "Nadi", "Suhu"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbVitalising.setModel(tabModeVitalising);
        //tampilDr();
        tampilVitalising();

        tbVitalising.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbVitalising.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbVitalising.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(180);
            } else if (i == 5) {
                column.setPreferredWidth(90);
            } else if (i == 6) {
                column.setPreferredWidth(180);
            } else if (i == 7) {
                column.setPreferredWidth(80);
            } else if (i == 8) {
                column.setPreferredWidth(75);
            }
        }
        tbVitalising.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeVentilator = new DefaultTableModel(null, new Object[]{
            "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Tgl.Rawat", "Jam Rawat", "Tipe/Mode", "RR", "I:E:Ratio", "TV", "MV", "IPL", "PEEP", "FIO2", "PEAK PREASSURE", "ETT:DIAMETER KEDALAMAN"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbVentilator.setModel(tabModeVentilator);
        tbVentilator.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbVentilator.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbVentilator.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(180);
            } else if (i == 5) {
                column.setPreferredWidth(90);
            } else if (i == 6) {
                column.setPreferredWidth(180);
            } else if (i == 7) {
                column.setPreferredWidth(80);
            } else if (i == 8) {
                column.setPreferredWidth(75);
            } else if (i == 9) {
                column.setPreferredWidth(90);
            } else if (i == 10) {
                column.setPreferredWidth(90);;
            } else if (i == 11) {
                column.setPreferredWidth(90);
            } else if (i == 12) {
                column.setPreferredWidth(90);
            } else if (i == 13) {
                column.setPreferredWidth(90);
            } else if (i == 14) {
                column.setPreferredWidth(90);
            } else if (i == 15) {
                column.setPreferredWidth(150);
            }
        }
        tbVentilator.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeInput = new DefaultTableModel(null, new Object[]{
            "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Tgl.Rawat", "Jam Rawat", "IVFD I", "IVFD II",
            "Transfusi", "Makan", "Minum", "NGT", "Total Intake"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbInput.setModel(tabModeInput);
        tbInput.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbInput.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbInput.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(180);
            } else if (i == 5) {
                column.setPreferredWidth(90);
            } else if (i == 6) {
                column.setPreferredWidth(180);
            } else if (i == 7) {
                column.setPreferredWidth(90);
            } else if (i == 8) {
                column.setPreferredWidth(180);
            } else if (i == 9) {
                column.setPreferredWidth(80);
            } else if (i == 10) {
                column.setPreferredWidth(75);
            } else if (i == 11) {
                column.setPreferredWidth(90);
            } else if (i == 12) {
                column.setPreferredWidth(90);
            }
        }
        tbInput.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeOutput = new DefaultTableModel(null, new Object[]{
            "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Tgl.Rawat", "Jam", "DRAIN I", "DRAIN II", "Cairan Lambung/NGT(Warna)",
            "Kateter/DG(Jumlah)", "Warna Urin", "BAP", "IWL", "Total Output"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbOutput.setModel(tabModeOutput);
        tbOutput.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbOutput.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbOutput.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(65);
            } else if (i == 5) {
                column.setPreferredWidth(50);
            } else if (i == 6) {
                column.setPreferredWidth(90);
            } else if (i == 7) {
                column.setPreferredWidth(90);
            } else if (i == 8) {
                column.setPreferredWidth(120);
            } else if (i == 9) {
                column.setPreferredWidth(120);
            } else if (i == 10) {
                column.setPreferredWidth(120);
            } else if (i == 11) {
                column.setPreferredWidth(90);
            } else if (i == 12) {
                column.setPreferredWidth(90);
            } else if (i == 13) {
                column.setPreferredWidth(100);
            }
        }
        tbOutput.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeProgHarian = new DefaultTableModel(null, new Object[]{
            "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Tgl.Rawat", "Jam Rawat", "Skema Infus/Drip", "Suntikan", "Oral", "Nebulizer/Suppositoria", "Lain-Lain", "Diet"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbProgHarian.setModel(tabModeProgHarian);
        //tampilDr();
        tampilObservasi();

        tbProgHarian.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbProgHarian.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbProgHarian.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(180);
            } else if (i == 5) {
                column.setPreferredWidth(90);
            } else if (i == 6) {
                column.setPreferredWidth(180);
            } else if (i == 7) {
                column.setPreferredWidth(80);
            } else if (i == 8) {
                column.setPreferredWidth(75);
            } else if (i == 9) {
                column.setPreferredWidth(90);
            } else if (i == 10) {
                column.setPreferredWidth(90);
            } else if (i == 11) {
                column.setPreferredWidth(90);
            }
        }
        tbProgHarian.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeGinekologi = new DefaultTableModel(null, new Object[]{
            "P", "No.Rawat", "No.R.M", "Nama Pasien", "Tgl.Rawat", "Jam Rawat",
            "Inpeksi", "Inspeksi Vulva/Uretra/Vagina", "Inspekulo", "Fluxus",
            "Fluor Albus", "Inspekulo Vulva/Vagina", "Inspekulo Portio", "Inspekulo Sondage",
            "Pemeriksaan Dalam Portio", "Pemeriksaan Dalam Bentuk", "Pemeriksaan Dalam Cavum Uteri", "Mobilitas",
            "Ukuran Cavum Uteri", "Nyeri Tekan", "Pemeriksaan Dalam Adnexa Kanan", "Pemeriksaan Dalam Adnexa Kiri",
            "Pemeriksaan Dalam Cavum Douglas"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class

            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

//        tbPemeriksaanGinekologi.setModel(tabModeGinekologi);
//        tbPemeriksaanGinekologi.setPreferredScrollableViewportSize(new Dimension(500,500));
//        tbPemeriksaanGinekologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        for (i = 0; i < 23; i++) {
//            TableColumn column = tbPemeriksaanGinekologi.getColumnModel().getColumn(i);
//            if(i==0) {
//                column.setPreferredWidth(20);
//            }else if(i==1) {
//                column.setPreferredWidth(105);
//            }else if(i==2) {
//                column.setPreferredWidth(70);
//            }else if(i==3) {
//                column.setPreferredWidth(180);
//            }else if(i==4) {
//                column.setPreferredWidth(80);
//            }else if(i==5) {
//                column.setPreferredWidth(70);
//            }else if(i==6) {
//                column.setPreferredWidth(200);
//            }else if(i==7) {
//                column.setPreferredWidth(200);
//            }else if(i==8) {
//                column.setPreferredWidth(200);
//            }else if(i==9) {
//                column.setPreferredWidth(42);
//            }else if(i==10) {
//                column.setPreferredWidth(62);
//            }else if(i==11) {
//                column.setPreferredWidth(200);
//            }else if(i==12) {
//                column.setPreferredWidth(200);
//            }else if(i==13) {
//                column.setPreferredWidth(200);
//            }else if(i==14) {
//                column.setPreferredWidth(200);
//            }else if(i==15) {
//                column.setPreferredWidth(200);
//            }else if(i==16) {
//                column.setPreferredWidth(200);
//            }else if(i==17) {
//                column.setPreferredWidth(50);
//            }else if(i==18) {
//                column.setPreferredWidth(200);
//            }else if(i==19) {
//                column.setPreferredWidth(67);
//            }else if(i==20) {
//                column.setPreferredWidth(200);
//            }else if(i==21) {
//                column.setPreferredWidth(200);
//            }else if(i==22) {
//                column.setPreferredWidth(200);
//            }  
//        }
//        tbPemeriksaanGinekologi.setDefaultRenderer(Object.class, new WarnaTable());
        venTipe.setDocument(new batasInput((byte) 20).getKata(venTipe));
        in_ivd2.setDocument(new batasInput((byte) 20).getKata(in_ivd2));
        obsOksigen.setDocument(new batasInput((byte) 20).getKata(obsOksigen));
        in_ivd1.setDocument(new batasInput((byte) 20).getKata(in_ivd1));
        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        obsKesadaran.setDocument(new batasInput((byte) 15).getKata(obsKesadaran));
        venRR.setDocument(new batasInput((byte) 15).getKata(venRR));
        in_transfusi.setDocument(new batasInput((byte) 15).getKata(in_transfusi));
//        TSuhu.setDocument(new batasInput((byte)5).getKata(TSuhu));
//        TLetak.setDocument(new batasInput((byte)50).getKata(TLetak));
//        TTensi.setDocument(new batasInput((byte)8).getKata(TTensi));
        TCariPasien.setDocument(new batasInput((byte) 20).getKata(TCariPasien));
//        TKeluhan.setDocument(new batasInput((int)2000).getKata(TKeluhan));  
//        TPemeriksaan.setDocument(new batasInput((int)2000).getKata(TPemeriksaan));    
//        TPenilaian.setDocument(new batasInput((int)2000).getKata(TPenilaian));  
//        TEvaluasi.setDocument(new batasInput((int)2000).getKata(TEvaluasi));
//        TindakLanjut.setDocument(new batasInput((int)2000).getKata(TindakLanjut));  
//        TInstruksi.setDocument(new batasInput((int)2000).getKata(TInstruksi));      
//        TTinggi.setDocument(new batasInput((byte)5).getKata(TTinggi));
//        TBerat.setDocument(new batasInput((byte)5).getKata(TBerat));
//        SpO2.setDocument(new batasInput((byte)3).getOnlyAngka(SpO2));
//        TNadi.setDocument(new batasInput((byte)3).getOnlyAngka(TNadi));
//        TRespirasi.setDocument(new batasInput((byte)3).getOnlyAngka(TRespirasi));      
//        TGCS.setDocument(new batasInput((byte)10).getKata(TGCS)); 
//        TAlergi.setDocument(new batasInput((int)50).getKata(TAlergi));        
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));
//        TInspeksi.setDocument(new batasInput((byte)50).getKata(TInspeksi));
//        TInspeksiVulva.setDocument(new batasInput((byte)50).getKata(TInspeksiVulva));
//        TInspekuloGine.setDocument(new batasInput((byte)50).getKata(TInspekuloGine));
//        TVulvaInspekulo.setDocument(new batasInput((byte)50).getKata(TVulvaInspekulo));
//        TPortioInspekulo.setDocument(new batasInput((byte)50).getKata(TPortioInspekulo));
//        TSondage.setDocument(new batasInput((byte)50).getKata(TSondage));
//        TPortioDalam.setDocument(new batasInput((byte)50).getKata(TPortioDalam));
//        TBentuk.setDocument(new batasInput((byte)50).getKata(TBentuk));
//        TCavumUteri.setDocument(new batasInput((byte)50).getKata(TCavumUteri));
//        TUkuran.setDocument(new batasInput((byte)50).getKata(TUkuran));
//        TAdnexaKanan.setDocument(new batasInput((byte)50).getKata(TAdnexaKanan));
//        TAdnexaKiri.setDocument(new batasInput((byte)50).getKata(TAdnexaKiri));
//        TCavumDouglas.setDocument(new batasInput((byte)50).getKata(TCavumDouglas));
        in_ivd1.setText("0");
        in_ivd2.setText("0");
        in_transfusi.setText("0");
        in_makan.setText("0");
        in_minum.setText("0");
        in_ngt.setText("0");
        in_total.setText("0");
        out_drain1.setText("0");
        out_drain2.setText("0");
        out_cairan.setText("0");
        out_keteter.setText("0");
        out_warna_urin.setText("0");
        out_bap.setText("0");
        out_iwl.setText("0");
        out_total.setText("0");
        vtlTekananDarah.setText("");
        vtlNadi.setText("");
        vtlSuhu.setText("");
        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        if (TabRawat.getSelectedIndex() == 0) {
                            tampilObservasi();
                        } else if (TabRawat.getSelectedIndex() == 1) {
                            tampilVentilator();
                        } else if (TabRawat.getSelectedIndex() == 2) {
                            tampilInput();
                        } else if (TabRawat.getSelectedIndex() == 3) {
                            tampilOutput();
                        } else if (TabRawat.getSelectedIndex() == 4) {
                            tampilProgramHarian();
                        } else if (TabRawat.getSelectedIndex() == 5) {
                            tampilVitalising();
                        }
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        if (TabRawat.getSelectedIndex() == 0) {
                            tampilObservasi();
                        } else if (TabRawat.getSelectedIndex() == 1) {
                            tampilVentilator();
                        } else if (TabRawat.getSelectedIndex() == 2) {
                            tampilInput();
                        } else if (TabRawat.getSelectedIndex() == 3) {
                            tampilOutput();
                        } else if (TabRawat.getSelectedIndex() == 4) {
                            tampilProgramHarian();
                        } else if (TabRawat.getSelectedIndex() == 5) {
                            tampilVitalising();
                        }
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        if (TabRawat.getSelectedIndex() == 0) {
                            tampilObservasi();
                        } else if (TabRawat.getSelectedIndex() == 1) {
                            tampilVentilator();
                        } else if (TabRawat.getSelectedIndex() == 2) {
                            tampilInput();
                        } else if (TabRawat.getSelectedIndex() == 3) {
                            tampilOutput();
                        } else if (TabRawat.getSelectedIndex() == 4) {
                            tampilProgramHarian();
                        } else if (TabRawat.getSelectedIndex() == 5) {
                            tampilVitalising();
                        }
                    }
                }
            });
        }

        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatInap")) {
                    if (pasien.getTable().getSelectedRow() != -1) {
                        TCariPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(), 0).toString());
                    }
                    TCariPasien.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgRawatInap")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        soapterakhir.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
//                if(soapterakhir.getTable().getSelectedRow()!= -1){   
//                    TKeluhan.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),2).toString());
//                    TPemeriksaan.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),3).toString());
//                    TPenilaian.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),4).toString());
//                    TindakLanjut.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),5).toString());
//                    TInstruksi.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),6).toString());
//                    TEvaluasi.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),7).toString());
//                    TEvaluasi.requestFocus();                    
//                }        
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
//                if(akses.getform().equals("DlgRawatJalan")){
//                    if(pegawai.getTable().getSelectedRow()!= -1){   
//                        KdPeg.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
//                        TPegawai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
//                        Jabatan.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),3).toString());
//                        KdPeg.requestFocus();                    
//                    }        
//                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        perawatan.dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatInap")) {
                    if (perawatan.dokter.getTable().getSelectedRow() != -1) {
                        if (TabRawat.getSelectedIndex() == 0) {
                            //obsOksigen.setText(perawatan.dokter.getTable().getValueAt(perawatan.dokter.getTable().getSelectedRow(), 0).toString());
//                            TDokter.setText(perawatan.dokter.getTable().getValueAt(perawatan.dokter.getTable().getSelectedRow(),1).toString());
                            //obsOksigen.requestFocus();
                        } else if (TabRawat.getSelectedIndex() == 2) {
                            //in_ivd1.setText(perawatan.dokter.getTable().getValueAt(perawatan.dokter.getTable().getSelectedRow(), 0).toString());
//                            TDokter2.setText(perawatan.dokter.getTable().getValueAt(perawatan.dokter.getTable().getSelectedRow(),1).toString());
//                            in_ivd1.requestFocus();
                        }
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        perawatan.petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatInap")) {
                    if (perawatan.petugas.getTable().getSelectedRow() != -1) {
                        if (TabRawat.getSelectedIndex() == 1) {
                            venTipe.setText(perawatan.petugas.getTable().getValueAt(perawatan.petugas.getTable().getSelectedRow(), 0).toString());
//                            TPerawat.setText(perawatan.petugas.getTable().getValueAt(perawatan.petugas.getTable().getSelectedRow(),1).toString());
                            venTipe.requestFocus();
                        } else if (TabRawat.getSelectedIndex() == 2) {
                            in_ivd2.setText(perawatan.petugas.getTable().getValueAt(perawatan.petugas.getTable().getSelectedRow(), 0).toString());
//                            TPerawat2.setText(perawatan.petugas.getTable().getValueAt(perawatan.petugas.getTable().getSelectedRow(),1).toString());
                            in_ivd2.requestFocus();
                        }
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        perawatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatInap")) {
                    if (perawatan.getTable().getSelectedRow() != -1) {
                        if (TabRawat.getSelectedIndex() == 0) {
                            obsKesadaran.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 1).toString());
//                            TNmPrw.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),2).toString());
                            BagianRS.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 5).toString());
                            Bhp.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 6).toString());
                            JmDokter.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 7).toString());
                            JmPerawat.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 8).toString());
                            KSO.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 9).toString());
                            Menejemen.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 10).toString());
                            TTnd.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 4).toString());
                            obsKesadaran.requestFocus();
                        } else if (TabRawat.getSelectedIndex() == 1) {
                            venRR.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 1).toString());
//                            TNmPrwPetugas.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),2).toString());
                            BagianRS.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 5).toString());
                            Bhp.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 6).toString());
                            JmDokter.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 7).toString());
                            JmPerawat.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 8).toString());
                            KSO.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 9).toString());
                            Menejemen.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 10).toString());
                            TTnd.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 4).toString());
                            venRR.requestFocus();
                        } else if (TabRawat.getSelectedIndex() == 2) {
                            in_transfusi.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 1).toString());
//                            TNmPrwDokterPetugas.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),2).toString());
                            BagianRS.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 5).toString());
                            Bhp.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 6).toString());
                            JmDokter.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 7).toString());
                            JmPerawat.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 8).toString());
                            KSO.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 9).toString());
                            Menejemen.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 10).toString());
                            TTnd.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(), 4).toString());
                            in_transfusi.requestFocus();
                        }
                    }
                    BtnCariActionPerformed(null);
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        perawatan2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatInap")) {
                    if (perawatan2.getTable().getSelectedRow() != -1) {
                        if (TabRawat.getSelectedIndex() == 0) {
                            obsKesadaran.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 4).toString());
//                            TNmPrw.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),5).toString());
                            BagianRS.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 8).toString());
                            Bhp.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 9).toString());
                            JmDokter.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 10).toString());
                            JmPerawat.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 11).toString());
                            KSO.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 12).toString());
                            Menejemen.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 13).toString());
                            TTnd.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 7).toString());
                            obsKesadaran.requestFocus();
                        } else if (TabRawat.getSelectedIndex() == 1) {
                            venRR.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 4).toString());
//                            TNmPrwPetugas.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),5).toString());
                            BagianRS.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 8).toString());
                            Bhp.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 9).toString());
                            JmDokter.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 10).toString());
                            JmPerawat.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 11).toString());
                            KSO.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 12).toString());
                            Menejemen.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 13).toString());
                            TTnd.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 7).toString());
                            venRR.requestFocus();
                        } else if (TabRawat.getSelectedIndex() == 2) {
                            in_transfusi.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 4).toString());
//                            TNmPrwDokterPetugas.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),5).toString());
                            BagianRS.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 8).toString());
                            Bhp.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 9).toString());
                            JmDokter.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 10).toString());
                            JmPerawat.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 11).toString());
                            KSO.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 12).toString());
                            Menejemen.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 13).toString());
                            TTnd.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(), 7).toString());
                            in_transfusi.requestFocus();
                        }
                    }
                    BtnCariActionPerformed(null);
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

//        ChkInput.setSelected(false);
//        isForm(); 
//        ChkInput1.setSelected(false);
//        isForm2();
//        ChkInput2.setSelected(false);
//        isForm3(); 
//        ChkAccor.setSelected(true);
//        isMenu();
        jam();

        try {
            psrekening = koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rsrekening = psrekening.executeQuery();
                while (rsrekening.next()) {
                    Suspen_Piutang_Tindakan_Ranap = rsrekening.getString("Suspen_Piutang_Tindakan_Ranap");
                    Tindakan_Ranap = rsrekening.getString("Tindakan_Ranap");
                    Beban_Jasa_Medik_Dokter_Tindakan_Ranap = rsrekening.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ranap = rsrekening.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Beban_Jasa_Medik_Paramedis_Tindakan_Ranap = rsrekening.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ranap");
                    Utang_Jasa_Medik_Paramedis_Tindakan_Ranap = rsrekening.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ranap");
                    Beban_KSO_Tindakan_Ranap = rsrekening.getString("Beban_KSO_Tindakan_Ranap");
                    Utang_KSO_Tindakan_Ranap = rsrekening.getString("Utang_KSO_Tindakan_Ranap");
                    Beban_Jasa_Sarana_Tindakan_Ranap = rsrekening.getString("Beban_Jasa_Sarana_Tindakan_Ranap");
                    Utang_Jasa_Sarana_Tindakan_Ranap = rsrekening.getString("Utang_Jasa_Sarana_Tindakan_Ranap");
                    Beban_Jasa_Menejemen_Tindakan_Ranap = rsrekening.getString("Beban_Jasa_Menejemen_Tindakan_Ranap");
                    Utang_Jasa_Menejemen_Tindakan_Ranap = rsrekening.getString("Utang_Jasa_Menejemen_Tindakan_Ranap");
                    HPP_BHP_Tindakan_Ranap = rsrekening.getString("HPP_BHP_Tindakan_Ranap");
                    Persediaan_BHP_Tindakan_Ranap = rsrekening.getString("Persediaan_BHP_Tindakan_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : " + e);
            } finally {
                if (rsrekening != null) {
                    rsrekening.close();
                }
                if (psrekening != null) {
                    psrekening.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BagianRS = new javax.swing.JTextField();
        Bhp = new javax.swing.JTextField();
        JmDokter = new javax.swing.JTextField();
        JmPerawat = new javax.swing.JTextField();
        TTnd = new javax.swing.JTextField();
        KSO = new javax.swing.JTextField();
        Menejemen = new javax.swing.JTextField();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel20 = new widget.Label();
        TCariPasien = new widget.TextBox();
        btnPasien = new widget.Button();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObservasi = new widget.Table();
        panelGlass7 = new widget.panelisi();
        jLabel5 = new widget.Label();
        obsOksigen = new widget.TextBox();
        jLabel11 = new widget.Label();
        obsKesadaran = new widget.TextBox();
        obsUkuranPupil = new widget.TextBox();
        label1 = new widget.Label();
        jLabel7 = new widget.Label();
        obsRR = new widget.TextBox();
        jLabel15 = new widget.Label();
        obsHR = new widget.TextBox();
        obsSPO2 = new widget.TextBox();
        label2 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbVentilator = new widget.Table();
        panelGlass13 = new widget.panelisi();
        jLabel13 = new widget.Label();
        venTipe = new widget.TextBox();
        jLabel28 = new widget.Label();
        venRR = new widget.TextBox();
        label3 = new widget.Label();
        venRatio = new widget.TextBox();
        label4 = new widget.Label();
        venTV = new widget.TextBox();
        label5 = new widget.Label();
        venMV = new widget.TextBox();
        venIPL = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel30 = new widget.Label();
        venPEEP = new widget.TextBox();
        label6 = new widget.Label();
        venFIO2 = new widget.TextBox();
        label7 = new widget.Label();
        venPEAK = new widget.TextBox();
        label8 = new widget.Label();
        venETT = new widget.TextBox();
        internalFrame4 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbInput = new widget.Table();
        panelGlass11 = new widget.panelisi();
        jLabel14 = new widget.Label();
        in_ivd2 = new widget.TextBox();
        jLabel12 = new widget.Label();
        in_ivd1 = new widget.TextBox();
        jLabel29 = new widget.Label();
        in_transfusi = new widget.TextBox();
        label9 = new widget.Label();
        in_makan = new widget.TextBox();
        in_minum = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel22 = new widget.Label();
        in_ngt = new widget.TextBox();
        jLabel31 = new widget.Label();
        in_total = new widget.TextBox();
        internalFrame7 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        tbOutput = new widget.Table();
        panelGlass15 = new widget.panelisi();
        jLabel23 = new widget.Label();
        out_drain2 = new widget.TextBox();
        jLabel24 = new widget.Label();
        out_drain1 = new widget.TextBox();
        jLabel35 = new widget.Label();
        out_cairan = new widget.TextBox();
        jLabel36 = new widget.Label();
        out_keteter = new widget.TextBox();
        jLabel26 = new widget.Label();
        out_warna_urin = new widget.TextBox();
        jLabel27 = new widget.Label();
        out_bap = new widget.TextBox();
        jLabel37 = new widget.Label();
        out_iwl = new widget.TextBox();
        jLabel38 = new widget.Label();
        out_total = new widget.TextBox();
        internalFrame8 = new widget.InternalFrame();
        Scroll6 = new widget.ScrollPane();
        tbProgHarian = new widget.Table();
        panelGlass16 = new widget.panelisi();
        jLabel57 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        prog_skemaInfus = new widget.TextArea();
        jLabel58 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        prog_suntikan = new widget.TextArea();
        jLabel59 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        prog_oral = new widget.TextArea();
        jLabel61 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        prog_nebulizer = new widget.TextArea();
        jLabel62 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        prog_lain_lain = new widget.TextArea();
        jLabel63 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        prog_diet = new widget.TextArea();
        internalFrame5 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbVitalising = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel8 = new widget.Label();
        vtlTekananDarah = new widget.TextBox();
        jLabel25 = new widget.Label();
        vtlNadi = new widget.TextBox();
        vtlSuhu = new widget.TextBox();
        label10 = new widget.Label();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        DTPTgl = new widget.Tanggal();
        jLabel18 = new widget.Label();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();

        BagianRS.setEditable(false);
        BagianRS.setText("0");
        BagianRS.setName("BagianRS"); // NOI18N

        Bhp.setEditable(false);
        Bhp.setText("0");
        Bhp.setName("Bhp"); // NOI18N

        JmDokter.setEditable(false);
        JmDokter.setText("0");
        JmDokter.setName("JmDokter"); // NOI18N

        JmPerawat.setEditable(false);
        JmPerawat.setText("0");
        JmPerawat.setName("JmPerawat"); // NOI18N

        TTnd.setEditable(false);
        TTnd.setText("0");
        TTnd.setName("TTnd"); // NOI18N

        KSO.setEditable(false);
        KSO.setText("0");
        KSO.setName("KSO"); // NOI18N

        Menejemen.setEditable(false);
        Menejemen.setText("0");
        Menejemen.setName("Menejemen"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Perawatan Intensif ICU ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setBackground(new java.awt.Color(215, 225, 215));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setIconTextGap(3);
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
        BtnBatal.setIconTextGap(3);
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
        BtnHapus.setIconTextGap(3);
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
        BtnEdit.setIconTextGap(3);
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

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setIconTextGap(3);
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setIconTextGap(3);
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

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(95, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(87, 30));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setIconTextGap(3);
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(64, 23));
        panelGlass10.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari2);

        jLabel20.setText("No.RM :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass10.add(jLabel20);

        TCariPasien.setName("TCariPasien"); // NOI18N
        TCariPasien.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass10.add(TCariPasien);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('6');
        btnPasien.setToolTipText("Alt+6");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        btnPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPasienKeyPressed(evt);
            }
        });
        panelGlass10.add(btnPasien);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass10.add(jSeparator5);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(273, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
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
        panelGlass10.add(BtnCari);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObservasi.setAutoCreateRowSorter(true);
        tbObservasi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObservasi.setName("tbObservasi"); // NOI18N
        tbObservasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObservasiMouseClicked(evt);
            }
        });
        tbObservasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObservasiKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbObservasi);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 104));
        panelGlass7.setLayout(null);

        jLabel5.setText("Terapi Oksigen :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(0, 10, 105, 23);

        obsOksigen.setHighlighter(null);
        obsOksigen.setName("obsOksigen"); // NOI18N
        obsOksigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                obsOksigenActionPerformed(evt);
            }
        });
        obsOksigen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                obsOksigenKeyPressed(evt);
            }
        });
        panelGlass7.add(obsOksigen);
        obsOksigen.setBounds(108, 10, 300, 23);

        jLabel11.setText("Kesadaran :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelGlass7.add(jLabel11);
        jLabel11.setBounds(0, 40, 105, 23);

        obsKesadaran.setName("obsKesadaran"); // NOI18N
        obsKesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                obsKesadaranKeyPressed(evt);
            }
        });
        panelGlass7.add(obsKesadaran);
        obsKesadaran.setBounds(108, 40, 300, 23);

        obsUkuranPupil.setName("obsUkuranPupil"); // NOI18N
        panelGlass7.add(obsUkuranPupil);
        obsUkuranPupil.setBounds(110, 70, 300, 24);

        label1.setText("Ukuran Pupil : ");
        label1.setName("label1"); // NOI18N
        panelGlass7.add(label1);
        label1.setBounds(20, 70, 90, 20);

        jLabel7.setText("RR :");
        jLabel7.setName("jLabel7"); // NOI18N
        panelGlass7.add(jLabel7);
        jLabel7.setBounds(480, 10, 105, 23);

        obsRR.setHighlighter(null);
        obsRR.setName("obsRR"); // NOI18N
        obsRR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                obsRRKeyPressed(evt);
            }
        });
        panelGlass7.add(obsRR);
        obsRR.setBounds(590, 10, 340, 23);

        jLabel15.setText("HR :");
        jLabel15.setName("jLabel15"); // NOI18N
        panelGlass7.add(jLabel15);
        jLabel15.setBounds(480, 40, 105, 23);

        obsHR.setName("obsHR"); // NOI18N
        obsHR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                obsHRKeyPressed(evt);
            }
        });
        panelGlass7.add(obsHR);
        obsHR.setBounds(590, 40, 340, 23);

        obsSPO2.setName("obsSPO2"); // NOI18N
        panelGlass7.add(obsSPO2);
        obsSPO2.setBounds(590, 70, 340, 24);

        label2.setText("SPO2 : ");
        label2.setName("label2"); // NOI18N
        panelGlass7.add(label2);
        label2.setBounds(500, 70, 90, 20);

        internalFrame2.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Observasi", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbVentilator.setAutoCreateRowSorter(true);
        tbVentilator.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbVentilator.setName("tbVentilator"); // NOI18N
        tbVentilator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbVentilatorMouseClicked(evt);
            }
        });
        tbVentilator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbVentilatorKeyReleased(evt);
            }
        });
        Scroll1.setViewportView(tbVentilator);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        panelGlass13.setName("panelGlass13"); // NOI18N
        panelGlass13.setPreferredSize(new java.awt.Dimension(44, 165));
        panelGlass13.setLayout(null);

        jLabel13.setText("Tipe/Mode :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelGlass13.add(jLabel13);
        jLabel13.setBounds(0, 10, 105, 23);

        venTipe.setHighlighter(null);
        venTipe.setName("venTipe"); // NOI18N
        venTipe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                venTipeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                venTipeKeyReleased(evt);
            }
        });
        panelGlass13.add(venTipe);
        venTipe.setBounds(108, 10, 260, 23);

        jLabel28.setText("RR :");
        jLabel28.setName("jLabel28"); // NOI18N
        panelGlass13.add(jLabel28);
        jLabel28.setBounds(0, 40, 105, 23);

        venRR.setName("venRR"); // NOI18N
        venRR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                venRRKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                venRRKeyReleased(evt);
            }
        });
        panelGlass13.add(venRR);
        venRR.setBounds(108, 40, 260, 23);

        label3.setText("I : E Ratio : ");
        label3.setName("label3"); // NOI18N
        panelGlass13.add(label3);
        label3.setBounds(30, 70, 80, 20);

        venRatio.setName("venRatio"); // NOI18N
        panelGlass13.add(venRatio);
        venRatio.setBounds(110, 70, 260, 24);

        label4.setText("TV : ");
        label4.setName("label4"); // NOI18N
        panelGlass13.add(label4);
        label4.setBounds(80, 100, 22, 20);

        venTV.setName("venTV"); // NOI18N
        panelGlass13.add(venTV);
        venTV.setBounds(110, 100, 260, 24);

        label5.setText("MV : ");
        label5.setName("label5"); // NOI18N
        panelGlass13.add(label5);
        label5.setBounds(80, 134, 24, 20);

        venMV.setName("venMV"); // NOI18N
        venMV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                venMVActionPerformed(evt);
            }
        });
        panelGlass13.add(venMV);
        venMV.setBounds(110, 130, 260, 24);

        venIPL.setHighlighter(null);
        venIPL.setName("venIPL"); // NOI18N
        venIPL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                venIPLKeyPressed(evt);
            }
        });
        panelGlass13.add(venIPL);
        venIPL.setBounds(650, 10, 260, 23);

        jLabel16.setText("IPL :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelGlass13.add(jLabel16);
        jLabel16.setBounds(540, 10, 105, 23);

        jLabel30.setText("PEEP :");
        jLabel30.setName("jLabel30"); // NOI18N
        panelGlass13.add(jLabel30);
        jLabel30.setBounds(540, 40, 105, 23);

        venPEEP.setName("venPEEP"); // NOI18N
        venPEEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                venPEEPKeyPressed(evt);
            }
        });
        panelGlass13.add(venPEEP);
        venPEEP.setBounds(650, 40, 260, 23);

        label6.setText("FIO2 :  ");
        label6.setName("label6"); // NOI18N
        panelGlass13.add(label6);
        label6.setBounds(570, 70, 80, 20);

        venFIO2.setName("venFIO2"); // NOI18N
        panelGlass13.add(venFIO2);
        venFIO2.setBounds(650, 70, 260, 24);

        label7.setText("PEAK PRESSURE :");
        label7.setName("label7"); // NOI18N
        panelGlass13.add(label7);
        label7.setBounds(542, 100, 100, 20);

        venPEAK.setName("venPEAK"); // NOI18N
        panelGlass13.add(venPEAK);
        venPEAK.setBounds(650, 100, 260, 24);

        label8.setText("ETT : DIAMETER/KEDALAMAN : ");
        label8.setName("label8"); // NOI18N
        panelGlass13.add(label8);
        label8.setBounds(474, 130, 170, 20);

        venETT.setName("venETT"); // NOI18N
        venETT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                venETTKeyReleased(evt);
            }
        });
        panelGlass13.add(venETT);
        venETT.setBounds(650, 130, 260, 24);

        internalFrame3.add(panelGlass13, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Ventilator", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbInput.setAutoCreateRowSorter(true);
        tbInput.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbInput.setName("tbInput"); // NOI18N
        tbInput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbInputMouseClicked(evt);
            }
        });
        tbInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbInputKeyReleased(evt);
            }
        });
        Scroll2.setViewportView(tbInput);

        internalFrame4.add(Scroll2, java.awt.BorderLayout.CENTER);

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 130));
        panelGlass11.setLayout(null);

        jLabel14.setText("IVFD II :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelGlass11.add(jLabel14);
        jLabel14.setBounds(0, 40, 105, 23);

        in_ivd2.setText("0");
        in_ivd2.setHighlighter(null);
        in_ivd2.setName("in_ivd2"); // NOI18N
        in_ivd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                in_ivd2KeyReleased(evt);
            }
        });
        panelGlass11.add(in_ivd2);
        in_ivd2.setBounds(108, 40, 250, 23);

        jLabel12.setText("IVFD I :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelGlass11.add(jLabel12);
        jLabel12.setBounds(0, 10, 105, 23);

        in_ivd1.setText("0");
        in_ivd1.setHighlighter(null);
        in_ivd1.setName("in_ivd1"); // NOI18N
        in_ivd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                in_ivd1KeyReleased(evt);
            }
        });
        panelGlass11.add(in_ivd1);
        in_ivd1.setBounds(108, 10, 250, 23);

        jLabel29.setText("TRANSFUSI :");
        jLabel29.setName("jLabel29"); // NOI18N
        panelGlass11.add(jLabel29);
        jLabel29.setBounds(0, 70, 105, 23);

        in_transfusi.setText("0");
        in_transfusi.setName("in_transfusi"); // NOI18N
        in_transfusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                in_transfusiKeyReleased(evt);
            }
        });
        panelGlass11.add(in_transfusi);
        in_transfusi.setBounds(108, 70, 250, 23);

        label9.setText("MAKAN : ");
        label9.setName("label9"); // NOI18N
        panelGlass11.add(label9);
        label9.setBounds(40, 100, 70, 20);

        in_makan.setText("0");
        in_makan.setName("in_makan"); // NOI18N
        in_makan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                in_makanKeyReleased(evt);
            }
        });
        panelGlass11.add(in_makan);
        in_makan.setBounds(110, 100, 250, 24);

        in_minum.setText("0");
        in_minum.setHighlighter(null);
        in_minum.setName("in_minum"); // NOI18N
        in_minum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                in_minumKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                in_minumKeyReleased(evt);
            }
        });
        panelGlass11.add(in_minum);
        in_minum.setBounds(690, 10, 250, 23);

        jLabel17.setText("MINUM :");
        jLabel17.setName("jLabel17"); // NOI18N
        panelGlass11.add(jLabel17);
        jLabel17.setBounds(580, 10, 105, 23);

        jLabel22.setText("NGT :");
        jLabel22.setName("jLabel22"); // NOI18N
        panelGlass11.add(jLabel22);
        jLabel22.setBounds(580, 40, 105, 23);

        in_ngt.setText("0");
        in_ngt.setHighlighter(null);
        in_ngt.setName("in_ngt"); // NOI18N
        in_ngt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                in_ngtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                in_ngtKeyReleased(evt);
            }
        });
        panelGlass11.add(in_ngt);
        in_ngt.setBounds(690, 40, 250, 23);

        jLabel31.setText("TOTAL INTAKE :");
        jLabel31.setName("jLabel31"); // NOI18N
        panelGlass11.add(jLabel31);
        jLabel31.setBounds(580, 70, 105, 23);

        in_total.setEditable(false);
        in_total.setText("0");
        in_total.setName("in_total"); // NOI18N
        in_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                in_totalKeyPressed(evt);
            }
        });
        panelGlass11.add(in_total);
        in_total.setBounds(690, 70, 250, 23);

        internalFrame4.add(panelGlass11, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Input", internalFrame4);

        internalFrame7.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame7.setBorder(null);
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbOutput.setAutoCreateRowSorter(true);
        tbOutput.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbOutput.setName("tbOutput"); // NOI18N
        tbOutput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbOutputMouseClicked(evt);
            }
        });
        tbOutput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbOutputKeyReleased(evt);
            }
        });
        Scroll5.setViewportView(tbOutput);

        internalFrame7.add(Scroll5, java.awt.BorderLayout.CENTER);

        panelGlass15.setName("panelGlass15"); // NOI18N
        panelGlass15.setPreferredSize(new java.awt.Dimension(44, 130));
        panelGlass15.setLayout(null);

        jLabel23.setText("DRAIN II :");
        jLabel23.setName("jLabel23"); // NOI18N
        panelGlass15.add(jLabel23);
        jLabel23.setBounds(70, 40, 105, 23);

        out_drain2.setHighlighter(null);
        out_drain2.setName("out_drain2"); // NOI18N
        out_drain2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                out_drain2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                out_drain2KeyReleased(evt);
            }
        });
        panelGlass15.add(out_drain2);
        out_drain2.setBounds(180, 40, 270, 23);

        jLabel24.setText("DRAIN I :");
        jLabel24.setName("jLabel24"); // NOI18N
        panelGlass15.add(jLabel24);
        jLabel24.setBounds(70, 10, 105, 23);

        out_drain1.setHighlighter(null);
        out_drain1.setName("out_drain1"); // NOI18N
        out_drain1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                out_drain1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                out_drain1KeyReleased(evt);
            }
        });
        panelGlass15.add(out_drain1);
        out_drain1.setBounds(180, 10, 270, 23);

        jLabel35.setText("CAIRAN LAMBUNG/NGT (WARNA :");
        jLabel35.setName("jLabel35"); // NOI18N
        panelGlass15.add(jLabel35);
        jLabel35.setBounds(5, 70, 170, 23);

        out_cairan.setName("out_cairan"); // NOI18N
        out_cairan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                out_cairanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                out_cairanKeyReleased(evt);
            }
        });
        panelGlass15.add(out_cairan);
        out_cairan.setBounds(180, 70, 270, 23);

        jLabel36.setText("KATETER/DG (JUMLAH)");
        jLabel36.setName("jLabel36"); // NOI18N
        panelGlass15.add(jLabel36);
        jLabel36.setBounds(35, 100, 140, 23);

        out_keteter.setName("out_keteter"); // NOI18N
        out_keteter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                out_keteterKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                out_keteterKeyReleased(evt);
            }
        });
        panelGlass15.add(out_keteter);
        out_keteter.setBounds(180, 100, 270, 23);

        jLabel26.setText("WARNA URIN :");
        jLabel26.setName("jLabel26"); // NOI18N
        panelGlass15.add(jLabel26);
        jLabel26.setBounds(610, 10, 105, 23);

        out_warna_urin.setHighlighter(null);
        out_warna_urin.setName("out_warna_urin"); // NOI18N
        out_warna_urin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                out_warna_urinKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                out_warna_urinKeyReleased(evt);
            }
        });
        panelGlass15.add(out_warna_urin);
        out_warna_urin.setBounds(720, 10, 270, 23);

        jLabel27.setText("BAP :");
        jLabel27.setName("jLabel27"); // NOI18N
        panelGlass15.add(jLabel27);
        jLabel27.setBounds(610, 40, 105, 23);

        out_bap.setHighlighter(null);
        out_bap.setName("out_bap"); // NOI18N
        out_bap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                out_bapKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                out_bapKeyReleased(evt);
            }
        });
        panelGlass15.add(out_bap);
        out_bap.setBounds(720, 40, 270, 23);

        jLabel37.setText("IWL : ");
        jLabel37.setName("jLabel37"); // NOI18N
        panelGlass15.add(jLabel37);
        jLabel37.setBounds(540, 70, 180, 23);

        out_iwl.setName("out_iwl"); // NOI18N
        out_iwl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                out_iwlKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                out_iwlKeyReleased(evt);
            }
        });
        panelGlass15.add(out_iwl);
        out_iwl.setBounds(720, 70, 270, 23);

        jLabel38.setText("TOTAL OUTPUT : ");
        jLabel38.setName("jLabel38"); // NOI18N
        panelGlass15.add(jLabel38);
        jLabel38.setBounds(570, 100, 150, 23);

        out_total.setEditable(false);
        out_total.setName("out_total"); // NOI18N
        out_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                out_totalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                out_totalKeyReleased(evt);
            }
        });
        panelGlass15.add(out_total);
        out_total.setBounds(720, 100, 270, 23);

        internalFrame7.add(panelGlass15, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Output", internalFrame7);

        internalFrame8.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame8.setBorder(null);
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbProgHarian.setAutoCreateRowSorter(true);
        tbProgHarian.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbProgHarian.setName("tbProgHarian"); // NOI18N
        tbProgHarian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProgHarianMouseClicked(evt);
            }
        });
        tbProgHarian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbProgHarianKeyReleased(evt);
            }
        });
        Scroll6.setViewportView(tbProgHarian);

        internalFrame8.add(Scroll6, java.awt.BorderLayout.CENTER);

        panelGlass16.setName("panelGlass16"); // NOI18N
        panelGlass16.setPreferredSize(new java.awt.Dimension(44, 220));
        panelGlass16.setLayout(null);

        jLabel57.setText("SKEMA INFUS/DRIP :");
        jLabel57.setName("jLabel57"); // NOI18N
        panelGlass16.add(jLabel57);
        jLabel57.setBounds(0, 10, 105, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        prog_skemaInfus.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        prog_skemaInfus.setColumns(20);
        prog_skemaInfus.setRows(5);
        prog_skemaInfus.setName("prog_skemaInfus"); // NOI18N
        prog_skemaInfus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                prog_skemaInfusKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(prog_skemaInfus);

        panelGlass16.add(scrollPane1);
        scrollPane1.setBounds(110, 10, 360, 60);

        jLabel58.setText("SUNTIKAN :");
        jLabel58.setName("jLabel58"); // NOI18N
        panelGlass16.add(jLabel58);
        jLabel58.setBounds(0, 80, 105, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        prog_suntikan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        prog_suntikan.setColumns(20);
        prog_suntikan.setRows(5);
        prog_suntikan.setName("prog_suntikan"); // NOI18N
        prog_suntikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                prog_suntikanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(prog_suntikan);

        panelGlass16.add(scrollPane2);
        scrollPane2.setBounds(110, 80, 360, 60);

        jLabel59.setText("ORAL :");
        jLabel59.setName("jLabel59"); // NOI18N
        panelGlass16.add(jLabel59);
        jLabel59.setBounds(0, 150, 105, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        prog_oral.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        prog_oral.setColumns(20);
        prog_oral.setRows(5);
        prog_oral.setName("prog_oral"); // NOI18N
        prog_oral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                prog_oralKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(prog_oral);

        panelGlass16.add(scrollPane3);
        scrollPane3.setBounds(110, 150, 360, 60);

        jLabel61.setText("NEBULIZER/SUPPOSITORIA :");
        jLabel61.setName("jLabel61"); // NOI18N
        panelGlass16.add(jLabel61);
        jLabel61.setBounds(495, 10, 150, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        prog_nebulizer.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        prog_nebulizer.setColumns(20);
        prog_nebulizer.setRows(5);
        prog_nebulizer.setName("prog_nebulizer"); // NOI18N
        prog_nebulizer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                prog_nebulizerKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(prog_nebulizer);

        panelGlass16.add(scrollPane4);
        scrollPane4.setBounds(650, 10, 360, 60);

        jLabel62.setText("LAIN-LAIN :");
        jLabel62.setName("jLabel62"); // NOI18N
        panelGlass16.add(jLabel62);
        jLabel62.setBounds(540, 80, 105, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        prog_lain_lain.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        prog_lain_lain.setColumns(20);
        prog_lain_lain.setRows(5);
        prog_lain_lain.setName("prog_lain_lain"); // NOI18N
        prog_lain_lain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                prog_lain_lainKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(prog_lain_lain);

        panelGlass16.add(scrollPane5);
        scrollPane5.setBounds(650, 80, 360, 60);

        jLabel63.setText("DIET :");
        jLabel63.setName("jLabel63"); // NOI18N
        panelGlass16.add(jLabel63);
        jLabel63.setBounds(540, 150, 105, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        prog_diet.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        prog_diet.setColumns(20);
        prog_diet.setRows(5);
        prog_diet.setName("prog_diet"); // NOI18N
        prog_diet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                prog_dietKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(prog_diet);

        panelGlass16.add(scrollPane6);
        scrollPane6.setBounds(650, 150, 360, 60);

        internalFrame8.add(panelGlass16, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Program Harian", internalFrame8);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbVitalising.setAutoCreateRowSorter(true);
        tbVitalising.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbVitalising.setName("tbVitalising"); // NOI18N
        tbVitalising.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbVitalisingMouseClicked(evt);
            }
        });
        tbVitalising.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbVitalisingKeyReleased(evt);
            }
        });
        Scroll3.setViewportView(tbVitalising);

        internalFrame5.add(Scroll3, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 104));
        panelGlass9.setLayout(null);

        jLabel8.setText("Tekanan Darah :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass9.add(jLabel8);
        jLabel8.setBounds(0, 10, 105, 23);

        vtlTekananDarah.setHighlighter(null);
        vtlTekananDarah.setName("vtlTekananDarah"); // NOI18N
        vtlTekananDarah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vtlTekananDarahActionPerformed(evt);
            }
        });
        vtlTekananDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vtlTekananDarahKeyPressed(evt);
            }
        });
        panelGlass9.add(vtlTekananDarah);
        vtlTekananDarah.setBounds(108, 10, 300, 23);

        jLabel25.setText("Nadi :");
        jLabel25.setName("jLabel25"); // NOI18N
        panelGlass9.add(jLabel25);
        jLabel25.setBounds(0, 40, 105, 23);

        vtlNadi.setName("vtlNadi"); // NOI18N
        vtlNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vtlNadiKeyPressed(evt);
            }
        });
        panelGlass9.add(vtlNadi);
        vtlNadi.setBounds(108, 40, 300, 23);

        vtlSuhu.setName("vtlSuhu"); // NOI18N
        panelGlass9.add(vtlSuhu);
        vtlSuhu.setBounds(110, 70, 300, 24);

        label10.setText("Suhu : ");
        label10.setName("label10"); // NOI18N
        panelGlass9.add(label10);
        label10.setBounds(20, 70, 90, 20);

        internalFrame5.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Vitalising", internalFrame5);

        TabRawat.setSelectedIndex(3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        FormInput.setBackground(new java.awt.Color(215, 225, 215));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(260, 43));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(null);
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TNoRwMouseClicked(evt);
            }
        });
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 125, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(201, 10, 80, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(283, 10, 260, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2025" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(617, 10, 90, 23);

        jLabel18.setText("Tanggal :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(554, 10, 60, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(711, 10, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(776, 10, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(841, 10, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setBorderPainted(true);
        ChkJln.setBorderPaintedFlat(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(906, 10, 23, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
            isPsien();
        } else {
            if (TabRawat.getSelectedIndex() == 0) {
                Valid.pindah(evt, DTPTgl, obsOksigen);
            } else if (TabRawat.getSelectedIndex() == 1) {
                Valid.pindah(evt, DTPTgl, venTipe);
            } else if (TabRawat.getSelectedIndex() == 2) {
                Valid.pindah(evt, DTPTgl, in_ivd1);
            } else if (TabRawat.getSelectedIndex() == 3) {
//                Valid.pindah(evt,DTPTgl,KdPeg);
            } else if (TabRawat.getSelectedIndex() == 4) {
//                Valid.pindah(evt,DTPTgl,TTinggi_uteri);
            } else if (TabRawat.getSelectedIndex() == 5) {
//                Valid.pindah(evt,DTPTgl,TInspeksi);
            }
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    if ((obsOksigen.getText().trim().equals(""))
                            || (!obsKesadaran.getText().trim().equals(""))
                            || (!obsUkuranPupil.getText().trim().equals(""))
                            || (!obsRR.getText().trim().equals(""))
                            || (!obsHR.getText().trim().equals(""))
                            || (!obsSPO2.getText().trim().equals(""))) {
                        if (Sequel.menyimpantf("icu_observasi", "?,?,?,?,?,?,?,?,?", "Data", 9, new String[]{
                            TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                            obsOksigen.getText(), obsKesadaran.getText(), obsUkuranPupil.getText(),
                            obsRR.getText(), obsHR.getText(), obsSPO2.getText()
                        }) == true) {
                            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                            tabModeObservasi.addRow(new Object[]{
                                false, TNoRw.getText(), TNoRM.getText(), TPasien.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                                obsOksigen.getText(), obsKesadaran.getText(), obsUkuranPupil.getText(), obsRR.getText(), obsHR.getText(), obsSPO2.getText()
                            });
                            obsOksigen.setText("");
                            obsKesadaran.setText("");
                            obsUkuranPupil.setText("");
                            obsRR.setText("");
                            obsHR.setText("");
                            obsSPO2.setText("");
                            LCount.setText("" + tabModeObservasi.getRowCount());

                        }
                    }
                    tampilObservasi();
                    break;
                case 1:
                    if ((venTipe.getText().trim().equals(""))
                            || (!venRR.getText().trim().equals(""))
                            || (!venRatio.getText().trim().equals(""))
                            || (!venTV.getText().trim().equals(""))
                            || (!venMV.getText().trim().equals(""))
                            || (!venIPL.getText().trim().equals(""))
                            || (!venPEEP.getText().trim().equals(""))
                            || (!venFIO2.getText().trim().equals(""))
                            || (!venPEAK.getText().trim().equals(""))
                            || (!venETT.getText().trim().equals(""))) {
                        if (Sequel.menyimpantf("icu_ventilator", "?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 13, new String[]{
                            TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                            venTipe.getText(), venRR.getText(), venRatio.getText(), venTV.getText(),
                            venMV.getText(), venIPL.getText(), venPEEP.getText(), venFIO2.getText(), venPEAK.getText(), venETT.getText()
                        }) == true) {
                            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                            tabModeVentilator.addRow(new Object[]{
                                false, TNoRw.getText(), TNoRM.getText(), TPasien.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                                venTipe.getText(), venRatio.getText(), venTV.getText(),
                                venMV.getText(), venIPL.getText(), venPEEP.getText(), venFIO2.getText(), venPEAK.getText(), venETT.getText()
                            });
                            venTipe.setText("");
                            venRatio.setText("");
                            venRR.setText("");
                            venTV.setText("");
                            venMV.setText("");
                            venIPL.setText("");
                            venPEEP.setText("");
                            venFIO2.setText("");
                            venPEAK.setText("");
                            venETT.setText("");
                            LCount.setText("" + tabModeVentilator.getRowCount());

                        }
                    }
                    tampilVentilator();
                    break;
                case 2:
                    if ((in_ivd1.getText().trim().equals(""))
                            || (!in_ivd2.getText().trim().equals(""))
                            || (!in_transfusi.getText().trim().equals(""))
                            || (!in_makan.getText().trim().equals(""))
                            || (!in_minum.getText().trim().equals(""))
                            || (!in_ngt.getText().trim().equals(""))
                            || (!in_total.getText().trim().equals(""))) {
                        if (Sequel.menyimpantf("icu_input", "?,?,?,?,?,?,?,?,?,?", "Data", 10, new String[]{
                            TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                            in_ivd1.getText(), in_ivd2.getText(), in_transfusi.getText(), in_makan.getText(),
                            in_minum.getText(), in_ngt.getText(), in_total.getText()
                        }) == true) {
                            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                            tabModeInput.addRow(new Object[]{
                                false, TNoRw.getText(), TNoRM.getText(), TPasien.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                                in_ivd1.getText(), in_ivd2.getText(), in_transfusi.getText(),
                                in_makan.getText(), in_minum.getText(), in_ngt.getText(), in_total.getText()
                            });
                            in_ivd1.setText("0");
                            in_ivd2.setText("0");
                            in_transfusi.setText("0");
                            in_makan.setText("0");
                            in_minum.setText("0");
                            in_ngt.setText("0");
                            in_total.setText("0");

                            LCount.setText("" + tabModeInput.getRowCount());

                        }
                    }
                    tampilInput();
                    break;
                case 3:
                    if ((out_drain1.getText().trim().equals(""))
                            || (!out_drain2.getText().trim().equals(""))
                            || (!out_cairan.getText().trim().equals(""))
                            || (!out_keteter.getText().trim().equals(""))
                            || (!out_warna_urin.getText().trim().equals(""))
                            || (!out_bap.getText().trim().equals(""))
                            || (!out_iwl.getText().trim().equals(""))
                            || (!out_total.getText().trim().equals(""))) {
                        if (Sequel.menyimpantf("icu_output", "?,?,?,?,?,?,?,?,?,?,?", "Data", 11, new String[]{
                            TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                            out_drain1.getText(), out_drain2.getText(), out_cairan.getText(), out_keteter.getText(),
                            out_warna_urin.getText(), out_bap.getText(), out_iwl.getText(), out_total.getText()
                        }) == true) {
                            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                            tabModeInput.addRow(new Object[]{
                                false, TNoRw.getText(), TNoRM.getText(), TPasien.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                                out_drain1.getText(), out_drain2.getText(), out_cairan.getText(),
                                out_keteter.getText(), out_warna_urin.getText(), out_bap.getText(), out_iwl.getText(), out_total.getText()
                            });
                            out_drain1.setText("0");
                            out_drain2.setText("0");
                            out_cairan.setText("0");
                            out_keteter.setText("0");
                            out_warna_urin.setText("0");
                            out_bap.setText("0");
                            out_iwl.setText("0");
                            out_total.setText("0");

                            LCount.setText("" + tabModeOutput.getRowCount());

                        }
                    }
                    tampilOutput();
                    break;
                case 4:
                    if ((prog_skemaInfus.getText().trim().equals(""))
                            || (!prog_suntikan.getText().trim().equals(""))
                            || (!prog_oral.getText().trim().equals(""))
                            || (!prog_nebulizer.getText().trim().equals(""))
                            || (!prog_lain_lain.getText().trim().equals(""))
                            || (!prog_diet.getText().trim().equals(""))) {
                        if (Sequel.menyimpantf("icu_prog_harian", "?,?,?,?,?,?,?,?,?", "Data", 9, new String[]{
                            TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                            prog_skemaInfus.getText(), prog_suntikan.getText(), prog_oral.getText(),
                            prog_nebulizer.getText(), prog_lain_lain.getText(), prog_diet.getText()
                        }) == true) {
                            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                            tabModeProgHarian.addRow(new Object[]{
                                false, TNoRw.getText(), TNoRM.getText(), TPasien.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                                prog_skemaInfus.getText(), prog_suntikan.getText(), prog_oral.getText(), prog_nebulizer.getText(), prog_lain_lain.getText(), prog_diet.getText()
                            });
                            prog_skemaInfus.setText("");
                            prog_suntikan.setText("");
                            prog_oral.setText("");
                            prog_nebulizer.setText("");
                            prog_lain_lain.setText("");
                            prog_diet.setText("");
                            LCount.setText("" + tabModeProgHarian.getRowCount());

                        }
                    }
                    tampilProgramHarian();
                    break;
                case 5:
                    if ((vtlTekananDarah.getText().trim().equals(""))
                            || (!vtlNadi.getText().trim().equals(""))
                            || (!obsUkuranPupil.getText().trim().equals(""))
                            || (!vtlSuhu.getText().trim().equals(""))) {
                        if (Sequel.menyimpantf("icu_vitalising", "?,?,?,?,?,?", "Data", 6, new String[]{
                            TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                            vtlTekananDarah.getText(), vtlNadi.getText(), vtlSuhu.getText()
                        }) == true) {
                            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                            tabModeVitalising.addRow(new Object[]{
                                false, TNoRw.getText(), TNoRM.getText(), TPasien.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                                vtlTekananDarah.getText(), vtlNadi.getText(), vtlSuhu.getText()
                            });
                            vtlTekananDarah.setText("");
                            vtlSuhu.setText("");
                            vtlNadi.setText("");
                            LCount.setText("" + tabModeVitalising.getRowCount());

                        }
                    }
                    tampilVitalising();
                    break;
                default:
                    break;
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            if (TabRawat.getSelectedIndex() == 0) {
                Valid.pindah(evt, obsKesadaran, BtnBatal);
            } else if (TabRawat.getSelectedIndex() == 1) {
                Valid.pindah(evt, venRR, BtnBatal);
            } else if (TabRawat.getSelectedIndex() == 2) {
                Valid.pindah(evt, in_transfusi, BtnBatal);
            } else if (TabRawat.getSelectedIndex() == 3) {
                Valid.pindah(evt, out_drain1, BtnBatal);
            } else if (TabRawat.getSelectedIndex() == 4) {
//                Valid.pindah(evt,cmbFeto,BtnBatal);
            }
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
//        ChkInput.setSelected(true);
//        ChkInput1.setSelected(true);
        //ChkInput2.setSelected(true);
//        isForm(); 
//        isForm2();
//        isForm3();
//        TSuhu.setText("");
        obsKesadaran.setText("");
        obsHR.setText("");
        obsOksigen.setText("");
        obsRR.setText("");
        obsSPO2.setText("");
        obsUkuranPupil.setText("");

        prog_skemaInfus.setText("");
        prog_suntikan.setText("");
        prog_oral.setText("");
        prog_nebulizer.setText("");
        prog_lain_lain.setText("");
        prog_diet.setText("");
//        TNmPrw.setText("");
        venRR.setText("");
        venETT.setText("");
        venTipe.setText("");
        venRatio.setText("");
        venTV.setText("");
        venMV.setText("");
        venIPL.setText("");
        venPEEP.setText("");
        venFIO2.setText("");
        venPEAK.setText("");
//        TNmPrwPetugas.setText("");        
        in_transfusi.setText("0");
        in_ivd1.setText("0");
        in_ivd2.setText("0");
        in_makan.setText("0");
        in_minum.setText("0");
        in_ngt.setText("0");
        in_total.setText("0");
        out_drain1.setText("0");
        out_drain2.setText("0");
        out_cairan.setText("0");
        out_keteter.setText("0");
        out_warna_urin.setText("0");
        out_bap.setText("0");
        out_iwl.setText("0");
        out_total.setText("0");
        vtlTekananDarah.setText("");
        vtlNadi.setText("");
        vtlSuhu.setText("");
//        TNmPrwDokterPetugas.setText("");
//        TTensi.setText("");
//        TKeluhan.setText("");
//        TPemeriksaan.setText("");
//        TPenilaian.setText("");
//        TindakLanjut.setText("");
//        TBerat.setText("");
//        TTinggi.setText("");
//        TNadi.setText("");
//        SpO2.setText("");
//        TEvaluasi.setText("");
//        TRespirasi.setText("");
//        TGCS.setText("");
//        TAlergi.setText("");
        TTnd.setText("0");
        BagianRS.setText("0");
        Bhp.setText("0");
        JmDokter.setText("0");
        JmPerawat.setText("0");
//        TInstruksi.setText("");
        DTPTgl.setDate(new Date());
//        TTinggi_uteri.setText("");
//        TLetak.setText("");
//        TDenyut.setText("");
//        TVulva.setText("");
//        TPortio.setText("");
//        TTebal.setText("");
//        TPembukaan.setText("");
//        TPenurunan.setText("");
//        TDenominator.setText("");
//        TKualitas_mnt.setText("");
//        TKualitas_dtk.setText("");
//        TInspeksi.setText("");
//        TInspeksiVulva.setText("");
//        TInspekuloGine.setText("");
//        TVulvaInspekulo.setText("");
//        TPortioInspekulo.setText("");
//        TSondage.setText("");
//        TPortioDalam.setText("");
//        TBentuk.setText("");
//        TCavumUteri.setText("");
//        TUkuran.setText("");
//        TAdnexaKanan.setText("");
//        TAdnexaKiri.setText("");
//        TCavumDouglas.setText("");
//        cmbKesadaran.setSelectedIndex(0);
        TNoRw.requestFocus();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnBatalActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if (tabModeObservasi.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data yang dipilih?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Sequel.AutoComitFalse();
                        sukses = true;

                        for (int i = 0; i < tbObservasi.getRowCount(); i++) {
                            if (Boolean.TRUE.equals(tbObservasi.getValueAt(i, 0))) {

                                if (Sequel.queryutf("DELETE FROM icu_observasi WHERE no_rawat='" + tbObservasi.getValueAt(i, 1).toString()
                                        + "' AND tgl_perawatan='" + tbObservasi.getValueAt(i, 4).toString()
                                        + "' AND jam_rawat='" + tbObservasi.getValueAt(i, 5).toString() + "'") == true) {
                                    Sequel.Commit();

//                                    tbRawatDr.setValueAt(TNmPrw.getText(),tbRawatDr.getSelectedRow(), 4);
                                    tbObservasi.setValueAt(obsOksigen.getText(), tbObservasi.getSelectedRow(), 6);
//                                    tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                                    tbObservasi.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""), tbObservasi.getSelectedRow(), 4);
                                    tbObservasi.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbObservasi.getSelectedRow(), 5);
                                    //tbObservasi.setValueAt(Double.parseDouble(TTnd.getText()), tbObservasi.getSelectedRow(), 9);
                                    tbObservasi.setValueAt(obsKesadaran.getText(), tbObservasi.getSelectedRow(), 7);
                                    tbObservasi.setValueAt(obsUkuranPupil.getText(), tbObservasi.getSelectedRow(), 8);
                                    tbObservasi.setValueAt(obsRR.getText(), tbObservasi.getSelectedRow(), 9);
                                    tbObservasi.setValueAt(obsHR.getText(), tbObservasi.getSelectedRow(), 10);
                                    tbObservasi.setValueAt(obsSPO2.getText(), tbObservasi.getSelectedRow(), 11);
//                                tbObservasi.setValueAt(Menejemen.getText(), tbObservasi.getSelectedRow(), 15);
                                    BtnBatalActionPerformed(evt);

                                } else {
                                    sukses = false;
                                }
                            }
                        }

                        if (sukses == true) {
                            Sequel.Commit();
                            JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
                            for (i = 0; i < tbObservasi.getRowCount(); i++) {
                                if (tbObservasi.getValueAt(i, 0).toString().equals("true")) {
                                    tabModeObservasi.removeRow(i);
                                    i--;
                                }
                            }
                            LCount.setText("" + tabModeObservasi.getRowCount());

                        } else {
                            Sequel.RollBack();
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghapus data, transaksi dibatalkan.\nPeriksa kembali data sebelum menghapus.");
                        }

                        Sequel.AutoComitTrue();
                    }
                }
                break;
            case 1:
                if (tabModeVentilator.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data yang dipilih?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Sequel.AutoComitFalse();
                        sukses = true;

                        for (int i = 0; i < tbVentilator.getRowCount(); i++) {
                            if (Boolean.TRUE.equals(tbVentilator.getValueAt(i, 0))) {

                                if (Sequel.queryutf("DELETE FROM icu_ventilator WHERE no_rawat='" + tbVentilator.getValueAt(i, 1).toString()
                                        + "' AND tgl_perawatan='" + tbVentilator.getValueAt(i, 4).toString()
                                        + "' AND jam_rawat='" + tbVentilator.getValueAt(i, 5).toString() + "'") == true) {
                                    Sequel.Commit();

//                                    tbRawatDr.setValueAt(TNmPrw.getText(),tbRawatDr.getSelectedRow(), 4);
                                    tbVentilator.setValueAt(venTipe.getText(), tbVentilator.getSelectedRow(), 6);
//                                    tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                                    tbVentilator.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""), tbVentilator.getSelectedRow(), 4);
                                    tbVentilator.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbVentilator.getSelectedRow(), 5);
                                    //tbVentilator.setValueAt(Double.parseDouble(TTnd.getText()), tbVentilator.getSelectedRow(), 9);
                                    tbVentilator.setValueAt(venRR.getText(), tbVentilator.getSelectedRow(), 7);
                                    tbVentilator.setValueAt(venRatio.getText(), tbVentilator.getSelectedRow(), 8);
                                    tbVentilator.setValueAt(venTV.getText(), tbVentilator.getSelectedRow(), 9);
                                    tbVentilator.setValueAt(venMV.getText(), tbVentilator.getSelectedRow(), 10);
                                    tbVentilator.setValueAt(venIPL.getText(), tbVentilator.getSelectedRow(), 11);
                                    tbVentilator.setValueAt(venPEEP.getText(), tbVentilator.getSelectedRow(), 12);
                                    tbVentilator.setValueAt(venFIO2.getText(), tbVentilator.getSelectedRow(), 13);
                                    tbVentilator.setValueAt(venPEAK.getText(), tbVentilator.getSelectedRow(), 14);
                                    tbVentilator.setValueAt(venETT.getText(), tbVentilator.getSelectedRow(), 15);
//                                tbObservasi.setValueAt(Menejemen.getText(), tbObservasi.getSelectedRow(), 15);
                                    BtnBatalActionPerformed(evt);

                                } else {
                                    sukses = false;
                                }
                            }
                        }

                        if (sukses == true) {
                            Sequel.Commit();
                            JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
                            for (i = 0; i < tbVentilator.getRowCount(); i++) {
                                if (tbVentilator.getValueAt(i, 0).toString().equals("true")) {
                                    tabModeVentilator.removeRow(i);
                                    i--;
                                }
                            }
                            LCount.setText("" + tabModeVentilator.getRowCount());

                        } else {
                            Sequel.RollBack();
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghapus data, transaksi dibatalkan.\nPeriksa kembali data sebelum menghapus.");
                        }

                        Sequel.AutoComitTrue();
                    }
                }
                break;
            case 2:
                if (tabModeInput.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data yang dipilih?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Sequel.AutoComitFalse();
                        sukses = true;

                        for (int i = 0; i < tbInput.getRowCount(); i++) {
                            if (Boolean.TRUE.equals(tbInput.getValueAt(i, 0))) {

                                if (Sequel.queryutf("DELETE FROM icu_input WHERE no_rawat='" + tbInput.getValueAt(i, 1).toString()
                                        + "' AND tgl_perawatan='" + tbInput.getValueAt(i, 4).toString()
                                        + "' AND jam_rawat='" + tbInput.getValueAt(i, 5).toString() + "'") == true) {
                                    Sequel.Commit();

//                                    tbRawatDr.setValueAt(TNmPrw.getText(),tbRawatDr.getSelectedRow(), 4);
                                    tbInput.setValueAt(in_ivd1.getText(), tbInput.getSelectedRow(), 6);
//                                    tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                                    tbInput.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""), tbInput.getSelectedRow(), 4);
                                    tbInput.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbInput.getSelectedRow(), 5);
                                    //tbInput.setValueAt(Double.parseDouble(TTnd.getText()), tbInput.getSelectedRow(), 9);
                                    tbInput.setValueAt(in_ivd2.getText(), tbInput.getSelectedRow(), 7);
                                    tbInput.setValueAt(in_transfusi.getText(), tbInput.getSelectedRow(), 8);
                                    tbInput.setValueAt(in_makan.getText(), tbInput.getSelectedRow(), 9);
                                    tbInput.setValueAt(in_minum.getText(), tbInput.getSelectedRow(), 10);
                                    tbInput.setValueAt(in_ngt.getText(), tbInput.getSelectedRow(), 11);
                                    tbInput.setValueAt(in_total.getText(), tbInput.getSelectedRow(), 12);

//                                tbObservasi.setValueAt(Menejemen.getText(), tbObservasi.getSelectedRow(), 15);
                                    BtnBatalActionPerformed(evt);

                                } else {
                                    sukses = false;
                                }
                            }
                        }

                        if (sukses == true) {
                            Sequel.Commit();
                            JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
                            for (i = 0; i < tbInput.getRowCount(); i++) {
                                if (tbInput.getValueAt(i, 0).toString().equals("true")) {
                                    tabModeInput.removeRow(i);
                                    i--;
                                }
                            }
                            LCount.setText("" + tabModeInput.getRowCount());

                        } else {
                            Sequel.RollBack();
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghapus data, transaksi dibatalkan.\nPeriksa kembali data sebelum menghapus.");
                        }

                        Sequel.AutoComitTrue();
                    }
                }
                break;
            case 3:
                if (tabModeOutput.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data yang dipilih?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Sequel.AutoComitFalse();
                        sukses = true;

                        for (int i = 0; i < tbOutput.getRowCount(); i++) {
                            if (Boolean.TRUE.equals(tbOutput.getValueAt(i, 0))) {

                                if (Sequel.queryutf("DELETE FROM icu_output WHERE no_rawat='" + tbOutput.getValueAt(i, 1).toString()
                                        + "' AND tgl_perawatan='" + tbOutput.getValueAt(i, 4).toString()
                                        + "' AND jam_rawat='" + tbOutput.getValueAt(i, 5).toString() + "'") == true) {
                                    Sequel.Commit();

//                                    tbRawatDr.setValueAt(TNmPrw.getText(),tbRawatDr.getSelectedRow(), 4);
                                    tbOutput.setValueAt(out_drain1.getText(), tbOutput.getSelectedRow(), 6);
//                                    tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                                    tbOutput.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""), tbOutput.getSelectedRow(), 4);
                                    tbOutput.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbOutput.getSelectedRow(), 5);
                                    //tbOutput.setValueAt(Double.parseDouble(TTnd.getText()), tbOutput.getSelectedRow(), 9);
                                    tbOutput.setValueAt(out_drain2.getText(), tbOutput.getSelectedRow(), 7);
                                    tbOutput.setValueAt(out_cairan.getText(), tbOutput.getSelectedRow(), 8);
                                    tbOutput.setValueAt(out_keteter.getText(), tbOutput.getSelectedRow(), 9);
                                    tbOutput.setValueAt(out_warna_urin.getText(), tbOutput.getSelectedRow(), 10);
                                    tbOutput.setValueAt(out_bap.getText(), tbOutput.getSelectedRow(), 11);
                                    tbOutput.setValueAt(out_iwl.getText(), tbOutput.getSelectedRow(), 12);
                                    tbOutput.setValueAt(out_total.getText(), tbOutput.getSelectedRow(), 13);

//                                tbObservasi.setValueAt(Menejemen.getText(), tbObservasi.getSelectedRow(), 15);
                                    BtnBatalActionPerformed(evt);

                                } else {
                                    sukses = false;
                                }
                            }
                        }

                        if (sukses == true) {
                            Sequel.Commit();
                            JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
                            for (i = 0; i < tbOutput.getRowCount(); i++) {
                                if (tbOutput.getValueAt(i, 0).toString().equals("true")) {
                                    tabModeOutput.removeRow(i);
                                    i--;
                                }
                            }
                            LCount.setText("" + tabModeOutput.getRowCount());

                        } else {
                            Sequel.RollBack();
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghapus data, transaksi dibatalkan.\nPeriksa kembali data sebelum menghapus.");
                        }

                        Sequel.AutoComitTrue();
                    }
                }
                break;
            case 4:
                if (tabModeProgHarian.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data yang dipilih?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Sequel.AutoComitFalse();
                        sukses = true;

                        for (int i = 0; i < tbProgHarian.getRowCount(); i++) {
                            if (Boolean.TRUE.equals(tbProgHarian.getValueAt(i, 0))) {

                                if (Sequel.queryutf("DELETE FROM icu_prog_harian WHERE no_rawat='" + tbProgHarian.getValueAt(i, 1).toString()
                                        + "' AND tgl_perawatan='" + tbProgHarian.getValueAt(i, 4).toString()
                                        + "' AND jam_rawat='" + tbProgHarian.getValueAt(i, 5).toString() + "'") == true) {
                                    Sequel.Commit();

//                                    tbRawatDr.setValueAt(TNmPrw.getText(),tbRawatDr.getSelectedRow(), 4);
                                    tbProgHarian.setValueAt(prog_skemaInfus.getText(), tbProgHarian.getSelectedRow(), 6);
//                                    tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                                    tbProgHarian.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""), tbProgHarian.getSelectedRow(), 4);
                                    tbProgHarian.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbProgHarian.getSelectedRow(), 5);
                                    //tbProgHarian.setValueAt(Double.parseDouble(TTnd.getText()), tbProgHarian.getSelectedRow(), 9);
                                    tbProgHarian.setValueAt(prog_suntikan.getText(), tbProgHarian.getSelectedRow(), 7);
                                    tbProgHarian.setValueAt(prog_oral.getText(), tbProgHarian.getSelectedRow(), 8);
                                    tbProgHarian.setValueAt(prog_nebulizer.getText(), tbProgHarian.getSelectedRow(), 9);
                                    tbProgHarian.setValueAt(prog_lain_lain.getText(), tbProgHarian.getSelectedRow(), 10);
                                    tbProgHarian.setValueAt(prog_diet.getText(), tbProgHarian.getSelectedRow(), 11);

//                                tbObservasi.setValueAt(Menejemen.getText(), tbObservasi.getSelectedRow(), 15);
                                    BtnBatalActionPerformed(evt);

                                } else {
                                    sukses = false;
                                }
                            }
                        }

                        if (sukses == true) {
                            Sequel.Commit();
                            JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
                            for (i = 0; i < tbProgHarian.getRowCount(); i++) {
                                if (tbProgHarian.getValueAt(i, 0).toString().equals("true")) {
                                    tabModeProgHarian.removeRow(i);
                                    i--;
                                }
                            }
                            LCount.setText("" + tabModeProgHarian.getRowCount());

                        } else {
                            Sequel.RollBack();
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghapus data, transaksi dibatalkan.\nPeriksa kembali data sebelum menghapus.");
                        }

                        Sequel.AutoComitTrue();
                    }
                }
                break;
            case 5:
                if (tabModeVitalising.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data yang dipilih?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Sequel.AutoComitFalse();
                        sukses = true;

                        for (int i = 0; i < tbVitalising.getRowCount(); i++) {
                            if (Boolean.TRUE.equals(tbVitalising.getValueAt(i, 0))) {

                                if (Sequel.queryutf("DELETE FROM icu_vitalising WHERE no_rawat='" + tbVitalising.getValueAt(i, 1).toString()
                                        + "' AND tgl_perawatan='" + tbVitalising.getValueAt(i, 4).toString()
                                        + "' AND jam_rawat='" + tbVitalising.getValueAt(i, 5).toString() + "'") == true) {
                                    Sequel.Commit();

//                                    tbRawatDr.setValueAt(TNmPrw.getText(),tbRawatDr.getSelectedRow(), 4);
                                    tbVitalising.setValueAt(vtlTekananDarah.getText(), tbVitalising.getSelectedRow(), 6);
//                                    tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                                    tbVitalising.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""), tbVitalising.getSelectedRow(), 4);
                                    tbVitalising.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbVitalising.getSelectedRow(), 5);
                                    //tbObservasi.setValueAt(Double.parseDouble(TTnd.getText()), tbObservasi.getSelectedRow(), 9);
                                    tbVitalising.setValueAt(vtlNadi.getText(), tbVitalising.getSelectedRow(), 7);
                                    tbVitalising.setValueAt(vtlSuhu.getText(), tbVitalising.getSelectedRow(), 8);

//                                tbObservasi.setValueAt(Menejemen.getText(), tbObservasi.getSelectedRow(), 15);
                                    BtnBatalActionPerformed(evt);

                                } else {
                                    sukses = false;
                                }
                            }
                        }

                        if (sukses == true) {
                            Sequel.Commit();
                            JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
                            for (i = 0; i < tbVitalising.getRowCount(); i++) {
                                if (tbVitalising.getValueAt(i, 0).toString().equals("true")) {
                                    tabModeVitalising.removeRow(i);
                                    i--;
                                }
                            }
                            LCount.setText("" + tabModeVitalising.getRowCount());

                        } else {
                            Sequel.RollBack();
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghapus data, transaksi dibatalkan.\nPeriksa kembali data sebelum menghapus.");
                        }

                        Sequel.AutoComitTrue();
                    }
                }
                break;
            default:
                break;
        }

        BtnBatalActionPerformed(evt);
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!TCari.getText().trim().equals("")) {
            BtnCariActionPerformed(evt);
        }
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if (tabModeObservasi.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (tabModeObservasi.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    param.put("ruang", Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?", TNoRw.getText()));
                    param.put("no_rm", TNoRw.getText());
                    param.put("tanggal", Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    kodeoperator = Sequel.cariIsi(
                            "SELECT dokter.kd_dokter FROM dokter "
                            + "JOIN dpjp_ranap ON dpjp_ranap.kd_dokter = dokter.kd_dokter "
                            + "JOIN icu_vitalising AS ic ON ic.no_rawat = dpjp_ranap.no_rawat "
                            + "WHERE dpjp_ranap.no_rawat = ? AND ic.tgl_perawatan = ?",
                            TNoRw.getText(), Valid.SetTgl(DTPCari1.getSelectedItem().toString())
                    );

                    finger = Sequel.cariIsi(
                            "SELECT sha1(sidikjari.sidikjari) FROM sidikjari "
                            + "INNER JOIN pegawai ON pegawai.id = sidikjari.id "
                            + "WHERE pegawai.nik = ?", kodeoperator
                    );

                    String a = Sequel.cariIsi(
                            "SELECT dokter.nm_dokter FROM dokter "
                            + "JOIN dpjp_ranap ON dpjp_ranap.kd_dokter = dokter.kd_dokter "
                            + "JOIN icu_vitalising AS ic ON ic.no_rawat = dpjp_ranap.no_rawat "
                            + "WHERE dpjp_ranap.no_rawat = ?", TNoRw.getText()
                    );

                    param.put("finger",
                            "Dikeluarkan di " + akses.getnamars()
                            + ", Kabupaten/Kota " + akses.getkabupatenrs()
                            + "\nDitandatangani secara elektronik oleh " + a
                            + "\nID " + (finger.equals("") ? kodeoperator : finger)
                            + "\n" + Valid.SetTgl(DTPCari1.getSelectedItem().toString())
                    );
//                    try {
//                ps = koneksi.prepareStatement("select dpjp_ranap.kd_dokter,dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? and dpjp_ranap.kd_dokter<>?");
//                try {
//                    ps.setString(1, "");
//                    ps.setString(2, "");
//                    rs = ps.executeQuery();
//                    i = 2;
//                    while (rs.next()) {
//                        if (i == 2) {
//                            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
//                            param.put("finger2", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + Valid.SetTgl(DTPTgl.getSelectedItem().toString()));
//                            param.put("namadokter2", rs.getString("nm_dokter"));
//                        }
//                        if (i == 3) {
//                            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
//                            param.put("finger3", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + Valid.SetTgl(DTPTgl.getSelectedItem().toString()));
//                            param.put("namadokter3", rs.getString("nm_dokter"));
//                        }
//                        i++;
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                } finally {
//                    if (rs != null) {
//                        rs.close();
//                    }
//                    if (ps != null) {
//                        ps.close();
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : " + e);
//            }

//                    param.put("SUBREPORT_DIR", "C:/Users/ACER/Documents/Project Leona/New folder/Update/khanzakefa/report/");
                    String reportPath = System.getProperty("user.dir") + File.separator + "report" + File.separator;
                    param.put("SUBREPORT_DIR", reportPath);

                    param.put("REPORT_CONNECTION", koneksi); // koneksi = objek java.sql.Connection aktif

                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " icu_vitalising.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    File file = new File("C:/Users/ACER/Documents/Project Leona/New folder/Update/khanzakefa/report/subVentilator_fixed.jasper");
                    System.out.println("File found? " + file.exists());
                    System.out.println("no_rm: " + TNoRw.getText());
                    System.out.println("tanggal: " + Valid.SetTgl(DTPCari1.getSelectedItem().toString()));

                    Valid.MyReportqry("TesGrafik.jasper", "report", "::[ Data Catatan Perawatan Intensif ICU ]::", "SELECT "
                            + "icu_vitalising.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, "
                            + "reg_periksa.umurdaftar, reg_periksa.sttsumur, "
                            + "icu_vitalising.tgl_perawatan, icu_vitalising.jam_rawat, icu_vitalising.tekanan_darah, "
                            + "icu_vitalising.nadi, icu_vitalising.suhu, DATE_FORMAT(icu_vitalising.jam_rawat, '%H:%i') AS jam, "
                            + "icu_observasi.terapi_oksigen, icu_observasi.kesadaran, icu_observasi.ukuran_pupil, "
                            + "icu_observasi.rr AS rr_observasi, icu_observasi.hr AS hr_observasi, icu_observasi.sp02, "
                            + "icu_ventilator.tipe, icu_ventilator.rr AS rrven, icu_ventilator.ratio, "
                            + "dpjp_ranap.kd_dokter,dokter.nm_dokter, "
                            + "icu_ventilator.tv, icu_ventilator.mv, icu_ventilator.ipl, icu_ventilator.peep, "
                            + "icu_ventilator.fio2, icu_ventilator.peak, icu_ventilator.ett "
                            + "FROM icu_vitalising "
                            + "INNER JOIN reg_periksa ON icu_vitalising.no_rawat = reg_periksa.no_rawat "
                            + "inner join dpjp_ranap on dpjp_ranap.no_rawat=reg_periksa.no_rawat "
                            + "inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter "
                            + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                            + "LEFT JOIN icu_observasi ON icu_vitalising.no_rawat = icu_observasi.no_rawat "
                            + "    AND icu_vitalising.jam_rawat = icu_observasi.jam_rawat "
                            + "LEFT JOIN icu_ventilator ON icu_vitalising.no_rawat = icu_ventilator.no_rawat "
                            + "    AND icu_vitalising.jam_rawat = icu_ventilator.jam_rawat "
                            + "WHERE " + tgl + " AND ("
                            + "icu_vitalising.no_rawat LIKE '%" + TCari.getText().trim() + "%' OR "
                            + "reg_periksa.no_rkm_medis LIKE '%" + TCari.getText().trim() + "%' OR "
                            + "pasien.nm_pasien LIKE '%" + TCari.getText().trim() + "%') "
                            + "ORDER BY icu_vitalising.jam_rawat DESC",
                            param
                    );

                }
                break;
            case 1:
                if (tabModeVentilator.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (tabModeVentilator.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    param.put("ruang", Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?", TNoRw.getText()));
                    param.put("no_rm", TNoRw.getText());
                    param.put("tanggal", Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    kodeoperator = Sequel.cariIsi(
                            "SELECT dokter.kd_dokter FROM dokter "
                            + "JOIN dpjp_ranap ON dpjp_ranap.kd_dokter = dokter.kd_dokter "
                            + "JOIN icu_vitalising AS ic ON ic.no_rawat = dpjp_ranap.no_rawat "
                            + "WHERE dpjp_ranap.no_rawat = ? AND ic.tgl_perawatan = ?",
                            TNoRw.getText(), Valid.SetTgl(DTPCari1.getSelectedItem().toString())
                    );

                    finger = Sequel.cariIsi(
                            "SELECT sha1(sidikjari.sidikjari) FROM sidikjari "
                            + "INNER JOIN pegawai ON pegawai.id = sidikjari.id "
                            + "WHERE pegawai.nik = ?", kodeoperator
                    );

                    String a = Sequel.cariIsi(
                            "SELECT dokter.nm_dokter FROM dokter "
                            + "JOIN dpjp_ranap ON dpjp_ranap.kd_dokter = dokter.kd_dokter "
                            + "JOIN icu_vitalising AS ic ON ic.no_rawat = dpjp_ranap.no_rawat "
                            + "WHERE dpjp_ranap.no_rawat = ?", TNoRw.getText()
                    );

                    param.put("finger",
                            "Dikeluarkan di " + akses.getnamars()
                            + ", Kabupaten/Kota " + akses.getkabupatenrs()
                            + "\nDitandatangani secara elektronik oleh " + a
                            + "\nID " + (finger.equals("") ? kodeoperator : finger)
                            + "\n" + Valid.SetTgl(DTPCari1.getSelectedItem().toString())
                    );
//                    try {
//                ps = koneksi.prepareStatement("select dpjp_ranap.kd_dokter,dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? and dpjp_ranap.kd_dokter<>?");
//                try {
//                    ps.setString(1, "");
//                    ps.setString(2, "");
//                    rs = ps.executeQuery();
//                    i = 2;
//                    while (rs.next()) {
//                        if (i == 2) {
//                            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
//                            param.put("finger2", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + Valid.SetTgl(DTPTgl.getSelectedItem().toString()));
//                            param.put("namadokter2", rs.getString("nm_dokter"));
//                        }
//                        if (i == 3) {
//                            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
//                            param.put("finger3", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + Valid.SetTgl(DTPTgl.getSelectedItem().toString()));
//                            param.put("namadokter3", rs.getString("nm_dokter"));
//                        }
//                        i++;
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                } finally {
//                    if (rs != null) {
//                        rs.close();
//                    }
//                    if (ps != null) {
//                        ps.close();
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : " + e);
//            }

//                    param.put("SUBREPORT_DIR", "C:/Users/ACER/Documents/Project Leona/New folder/Update/khanzakefa/report/");
                    String reportPath = System.getProperty("user.dir") + File.separator + "report" + File.separator;
                    param.put("SUBREPORT_DIR", reportPath);

                    param.put("REPORT_CONNECTION", koneksi); // koneksi = objek java.sql.Connection aktif

                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " icu_vitalising.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    File file = new File("C:/Users/ACER/Documents/Project Leona/New folder/Update/khanzakefa/report/subVentilator_fixed.jasper");
                    System.out.println("File found? " + file.exists());
                    System.out.println("no_rm: " + TNoRw.getText());
                    System.out.println("tanggal: " + Valid.SetTgl(DTPCari1.getSelectedItem().toString()));

                    Valid.MyReportqry("TesGrafik.jasper", "report", "::[ Data Catatan Perawatan Intensif ICU ]::", "SELECT "
                            + "icu_vitalising.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, "
                            + "reg_periksa.umurdaftar, reg_periksa.sttsumur, "
                            + "icu_vitalising.tgl_perawatan, icu_vitalising.jam_rawat, icu_vitalising.tekanan_darah, "
                            + "icu_vitalising.nadi, icu_vitalising.suhu, DATE_FORMAT(icu_vitalising.jam_rawat, '%H:%i') AS jam, "
                            + "icu_observasi.terapi_oksigen, icu_observasi.kesadaran, icu_observasi.ukuran_pupil, "
                            + "icu_observasi.rr AS rr_observasi, icu_observasi.hr AS hr_observasi, icu_observasi.sp02, "
                            + "icu_ventilator.tipe, icu_ventilator.rr AS rrven, icu_ventilator.ratio, "
                            + "dpjp_ranap.kd_dokter,dokter.nm_dokter, "
                            + "icu_ventilator.tv, icu_ventilator.mv, icu_ventilator.ipl, icu_ventilator.peep, "
                            + "icu_ventilator.fio2, icu_ventilator.peak, icu_ventilator.ett "
                            + "FROM icu_vitalising "
                            + "INNER JOIN reg_periksa ON icu_vitalising.no_rawat = reg_periksa.no_rawat "
                            + "inner join dpjp_ranap on dpjp_ranap.no_rawat=reg_periksa.no_rawat "
                            + "inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter "
                            + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                            + "LEFT JOIN icu_observasi ON icu_vitalising.no_rawat = icu_observasi.no_rawat "
                            + "    AND icu_vitalising.jam_rawat = icu_observasi.jam_rawat "
                            + "LEFT JOIN icu_ventilator ON icu_vitalising.no_rawat = icu_ventilator.no_rawat "
                            + "    AND icu_vitalising.jam_rawat = icu_ventilator.jam_rawat "
                            + "WHERE " + tgl + " AND ("
                            + "icu_vitalising.no_rawat LIKE '%" + TCari.getText().trim() + "%' OR "
                            + "reg_periksa.no_rkm_medis LIKE '%" + TCari.getText().trim() + "%' OR "
                            + "pasien.nm_pasien LIKE '%" + TCari.getText().trim() + "%') "
                            + "ORDER BY icu_vitalising.jam_rawat DESC",
                            param
                    );
                }
                break;
            case 2:
                if (tabModeInput.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (tabModeInput.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    param.put("ruang", Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?", TNoRw.getText()));
                    param.put("no_rm", TNoRw.getText());
                    param.put("tanggal", Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    kodeoperator = Sequel.cariIsi(
                            "SELECT dokter.kd_dokter FROM dokter "
                            + "JOIN dpjp_ranap ON dpjp_ranap.kd_dokter = dokter.kd_dokter "
                            + "JOIN icu_vitalising AS ic ON ic.no_rawat = dpjp_ranap.no_rawat "
                            + "WHERE dpjp_ranap.no_rawat = ? AND ic.tgl_perawatan = ?",
                            TNoRw.getText(), Valid.SetTgl(DTPCari1.getSelectedItem().toString())
                    );

                    finger = Sequel.cariIsi(
                            "SELECT sha1(sidikjari.sidikjari) FROM sidikjari "
                            + "INNER JOIN pegawai ON pegawai.id = sidikjari.id "
                            + "WHERE pegawai.nik = ?", kodeoperator
                    );

                    String a = Sequel.cariIsi(
                            "SELECT dokter.nm_dokter FROM dokter "
                            + "JOIN dpjp_ranap ON dpjp_ranap.kd_dokter = dokter.kd_dokter "
                            + "JOIN icu_vitalising AS ic ON ic.no_rawat = dpjp_ranap.no_rawat "
                            + "WHERE dpjp_ranap.no_rawat = ?", TNoRw.getText()
                    );

                    param.put("finger",
                            "Dikeluarkan di " + akses.getnamars()
                            + ", Kabupaten/Kota " + akses.getkabupatenrs()
                            + "\nDitandatangani secara elektronik oleh " + a
                            + "\nID " + (finger.equals("") ? kodeoperator : finger)
                            + "\n" + Valid.SetTgl(DTPCari1.getSelectedItem().toString())
                    );
//                    try {
//                ps = koneksi.prepareStatement("select dpjp_ranap.kd_dokter,dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? and dpjp_ranap.kd_dokter<>?");
//                try {
//                    ps.setString(1, "");
//                    ps.setString(2, "");
//                    rs = ps.executeQuery();
//                    i = 2;
//                    while (rs.next()) {
//                        if (i == 2) {
//                            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
//                            param.put("finger2", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + Valid.SetTgl(DTPTgl.getSelectedItem().toString()));
//                            param.put("namadokter2", rs.getString("nm_dokter"));
//                        }
//                        if (i == 3) {
//                            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
//                            param.put("finger3", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + Valid.SetTgl(DTPTgl.getSelectedItem().toString()));
//                            param.put("namadokter3", rs.getString("nm_dokter"));
//                        }
//                        i++;
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                } finally {
//                    if (rs != null) {
//                        rs.close();
//                    }
//                    if (ps != null) {
//                        ps.close();
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : " + e);
//            }

//                    param.put("SUBREPORT_DIR", "C:/Users/ACER/Documents/Project Leona/New folder/Update/khanzakefa/report/");
                    String reportPath = System.getProperty("user.dir") + File.separator + "report" + File.separator;
                    param.put("SUBREPORT_DIR", reportPath);

                    param.put("REPORT_CONNECTION", koneksi); // koneksi = objek java.sql.Connection aktif

                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " icu_vitalising.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    File file = new File("C:/Users/ACER/Documents/Project Leona/New folder/Update/khanzakefa/report/subVentilator_fixed.jasper");
                    System.out.println("File found? " + file.exists());
                    System.out.println("no_rm: " + TNoRw.getText());
                    System.out.println("tanggal: " + Valid.SetTgl(DTPCari1.getSelectedItem().toString()));

                    Valid.MyReportqry("TesGrafik.jasper", "report", "::[ Data Catatan Perawatan Intensif ICU ]::", "SELECT "
                            + "icu_vitalising.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, "
                            + "reg_periksa.umurdaftar, reg_periksa.sttsumur, "
                            + "icu_vitalising.tgl_perawatan, icu_vitalising.jam_rawat, icu_vitalising.tekanan_darah, "
                            + "icu_vitalising.nadi, icu_vitalising.suhu, DATE_FORMAT(icu_vitalising.jam_rawat, '%H:%i') AS jam, "
                            + "icu_observasi.terapi_oksigen, icu_observasi.kesadaran, icu_observasi.ukuran_pupil, "
                            + "icu_observasi.rr AS rr_observasi, icu_observasi.hr AS hr_observasi, icu_observasi.sp02, "
                            + "icu_ventilator.tipe, icu_ventilator.rr AS rrven, icu_ventilator.ratio, "
                            + "dpjp_ranap.kd_dokter,dokter.nm_dokter, "
                            + "icu_ventilator.tv, icu_ventilator.mv, icu_ventilator.ipl, icu_ventilator.peep, "
                            + "icu_ventilator.fio2, icu_ventilator.peak, icu_ventilator.ett "
                            + "FROM icu_vitalising "
                            + "INNER JOIN reg_periksa ON icu_vitalising.no_rawat = reg_periksa.no_rawat "
                            + "inner join dpjp_ranap on dpjp_ranap.no_rawat=reg_periksa.no_rawat "
                            + "inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter "
                            + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                            + "LEFT JOIN icu_observasi ON icu_vitalising.no_rawat = icu_observasi.no_rawat "
                            + "    AND icu_vitalising.jam_rawat = icu_observasi.jam_rawat "
                            + "LEFT JOIN icu_ventilator ON icu_vitalising.no_rawat = icu_ventilator.no_rawat "
                            + "    AND icu_vitalising.jam_rawat = icu_ventilator.jam_rawat "
                            + "WHERE " + tgl + " AND ("
                            + "icu_vitalising.no_rawat LIKE '%" + TCari.getText().trim() + "%' OR "
                            + "reg_periksa.no_rkm_medis LIKE '%" + TCari.getText().trim() + "%' OR "
                            + "pasien.nm_pasien LIKE '%" + TCari.getText().trim() + "%') "
                            + "ORDER BY icu_vitalising.jam_rawat DESC",
                            param
                    );
                }
                break;
            case 3:
                if (tabModeOutput.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (tabModeOutput.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    param.put("ruang", Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?", TNoRw.getText()));
                    param.put("no_rm", TNoRw.getText());
                    param.put("tanggal", Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    kodeoperator = Sequel.cariIsi(
                            "SELECT dokter.kd_dokter FROM dokter "
                            + "JOIN dpjp_ranap ON dpjp_ranap.kd_dokter = dokter.kd_dokter "
                            + "JOIN icu_vitalising AS ic ON ic.no_rawat = dpjp_ranap.no_rawat "
                            + "WHERE dpjp_ranap.no_rawat = ? AND ic.tgl_perawatan = ?",
                            TNoRw.getText(), Valid.SetTgl(DTPCari1.getSelectedItem().toString())
                    );

                    finger = Sequel.cariIsi(
                            "SELECT sha1(sidikjari.sidikjari) FROM sidikjari "
                            + "INNER JOIN pegawai ON pegawai.id = sidikjari.id "
                            + "WHERE pegawai.nik = ?", kodeoperator
                    );

                    String a = Sequel.cariIsi(
                            "SELECT dokter.nm_dokter FROM dokter "
                            + "JOIN dpjp_ranap ON dpjp_ranap.kd_dokter = dokter.kd_dokter "
                            + "JOIN icu_vitalising AS ic ON ic.no_rawat = dpjp_ranap.no_rawat "
                            + "WHERE dpjp_ranap.no_rawat = ?", TNoRw.getText()
                    );

                    param.put("finger",
                            "Dikeluarkan di " + akses.getnamars()
                            + ", Kabupaten/Kota " + akses.getkabupatenrs()
                            + "\nDitandatangani secara elektronik oleh " + a
                            + "\nID " + (finger.equals("") ? kodeoperator : finger)
                            + "\n" + Valid.SetTgl(DTPCari1.getSelectedItem().toString())
                    );
//                    try {
//                ps = koneksi.prepareStatement("select dpjp_ranap.kd_dokter,dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? and dpjp_ranap.kd_dokter<>?");
//                try {
//                    ps.setString(1, "");
//                    ps.setString(2, "");
//                    rs = ps.executeQuery();
//                    i = 2;
//                    while (rs.next()) {
//                        if (i == 2) {
//                            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
//                            param.put("finger2", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + Valid.SetTgl(DTPTgl.getSelectedItem().toString()));
//                            param.put("namadokter2", rs.getString("nm_dokter"));
//                        }
//                        if (i == 3) {
//                            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
//                            param.put("finger3", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + Valid.SetTgl(DTPTgl.getSelectedItem().toString()));
//                            param.put("namadokter3", rs.getString("nm_dokter"));
//                        }
//                        i++;
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                } finally {
//                    if (rs != null) {
//                        rs.close();
//                    }
//                    if (ps != null) {
//                        ps.close();
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : " + e);
//            }

//                    param.put("SUBREPORT_DIR", "C:/Users/ACER/Documents/Project Leona/New folder/Update/khanzakefa/report/");
                    String reportPath = System.getProperty("user.dir") + File.separator + "report" + File.separator;
                    param.put("SUBREPORT_DIR", reportPath);

                    param.put("REPORT_CONNECTION", koneksi); // koneksi = objek java.sql.Connection aktif

                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " icu_vitalising.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    File file = new File("C:/Users/ACER/Documents/Project Leona/New folder/Update/khanzakefa/report/subVentilator_fixed.jasper");
                    System.out.println("File found? " + file.exists());
                    System.out.println("no_rm: " + TNoRw.getText());
                    System.out.println("tanggal: " + Valid.SetTgl(DTPCari1.getSelectedItem().toString()));

                    Valid.MyReportqry("TesGrafik.jasper", "report", "::[ Data Catatan Perawatan Intensif ICU ]::", "SELECT "
                            + "icu_vitalising.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, "
                            + "reg_periksa.umurdaftar, reg_periksa.sttsumur, "
                            + "icu_vitalising.tgl_perawatan, icu_vitalising.jam_rawat, icu_vitalising.tekanan_darah, "
                            + "icu_vitalising.nadi, icu_vitalising.suhu, DATE_FORMAT(icu_vitalising.jam_rawat, '%H:%i') AS jam, "
                            + "icu_observasi.terapi_oksigen, icu_observasi.kesadaran, icu_observasi.ukuran_pupil, "
                            + "icu_observasi.rr AS rr_observasi, icu_observasi.hr AS hr_observasi, icu_observasi.sp02, "
                            + "icu_ventilator.tipe, icu_ventilator.rr AS rrven, icu_ventilator.ratio, "
                            + "dpjp_ranap.kd_dokter,dokter.nm_dokter, "
                            + "icu_ventilator.tv, icu_ventilator.mv, icu_ventilator.ipl, icu_ventilator.peep, "
                            + "icu_ventilator.fio2, icu_ventilator.peak, icu_ventilator.ett "
                            + "FROM icu_vitalising "
                            + "INNER JOIN reg_periksa ON icu_vitalising.no_rawat = reg_periksa.no_rawat "
                            + "inner join dpjp_ranap on dpjp_ranap.no_rawat=reg_periksa.no_rawat "
                            + "inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter "
                            + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                            + "LEFT JOIN icu_observasi ON icu_vitalising.no_rawat = icu_observasi.no_rawat "
                            + "    AND icu_vitalising.jam_rawat = icu_observasi.jam_rawat "
                            + "LEFT JOIN icu_ventilator ON icu_vitalising.no_rawat = icu_ventilator.no_rawat "
                            + "    AND icu_vitalising.jam_rawat = icu_ventilator.jam_rawat "
                            + "WHERE " + tgl + " AND ("
                            + "icu_vitalising.no_rawat LIKE '%" + TCari.getText().trim() + "%' OR "
                            + "reg_periksa.no_rkm_medis LIKE '%" + TCari.getText().trim() + "%' OR "
                            + "pasien.nm_pasien LIKE '%" + TCari.getText().trim() + "%') "
                            + "ORDER BY icu_vitalising.jam_rawat DESC",
                            param
                    );
                }
                break;
            case 4:
                if (tabModeProgHarian.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (tabModeProgHarian.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    param.put("ruang", Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?", TNoRw.getText()));
                    param.put("no_rm", TNoRw.getText());
                    param.put("tanggal", Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    kodeoperator = Sequel.cariIsi(
                            "SELECT dokter.kd_dokter FROM dokter "
                            + "JOIN dpjp_ranap ON dpjp_ranap.kd_dokter = dokter.kd_dokter "
                            + "JOIN icu_vitalising AS ic ON ic.no_rawat = dpjp_ranap.no_rawat "
                            + "WHERE dpjp_ranap.no_rawat = ? AND ic.tgl_perawatan = ?",
                            TNoRw.getText(), Valid.SetTgl(DTPCari1.getSelectedItem().toString())
                    );

                    finger = Sequel.cariIsi(
                            "SELECT sha1(sidikjari.sidikjari) FROM sidikjari "
                            + "INNER JOIN pegawai ON pegawai.id = sidikjari.id "
                            + "WHERE pegawai.nik = ?", kodeoperator
                    );

                    String a = Sequel.cariIsi(
                            "SELECT dokter.nm_dokter FROM dokter "
                            + "JOIN dpjp_ranap ON dpjp_ranap.kd_dokter = dokter.kd_dokter "
                            + "JOIN icu_vitalising AS ic ON ic.no_rawat = dpjp_ranap.no_rawat "
                            + "WHERE dpjp_ranap.no_rawat = ?", TNoRw.getText()
                    );

                    param.put("finger",
                            "Dikeluarkan di " + akses.getnamars()
                            + ", Kabupaten/Kota " + akses.getkabupatenrs()
                            + "\nDitandatangani secara elektronik oleh " + a
                            + "\nID " + (finger.equals("") ? kodeoperator : finger)
                            + "\n" + Valid.SetTgl(DTPCari1.getSelectedItem().toString())
                    );
//                    try {
//                ps = koneksi.prepareStatement("select dpjp_ranap.kd_dokter,dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? and dpjp_ranap.kd_dokter<>?");
//                try {
//                    ps.setString(1, "");
//                    ps.setString(2, "");
//                    rs = ps.executeQuery();
//                    i = 2;
//                    while (rs.next()) {
//                        if (i == 2) {
//                            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
//                            param.put("finger2", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + Valid.SetTgl(DTPTgl.getSelectedItem().toString()));
//                            param.put("namadokter2", rs.getString("nm_dokter"));
//                        }
//                        if (i == 3) {
//                            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
//                            param.put("finger3", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + Valid.SetTgl(DTPTgl.getSelectedItem().toString()));
//                            param.put("namadokter3", rs.getString("nm_dokter"));
//                        }
//                        i++;
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                } finally {
//                    if (rs != null) {
//                        rs.close();
//                    }
//                    if (ps != null) {
//                        ps.close();
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : " + e);
//            }

//                    param.put("SUBREPORT_DIR", "C:/Users/ACER/Documents/Project Leona/New folder/Update/khanzakefa/report/");
                    String reportPath = System.getProperty("user.dir") + File.separator + "report" + File.separator;
                    param.put("SUBREPORT_DIR", reportPath);

                    param.put("REPORT_CONNECTION", koneksi); // koneksi = objek java.sql.Connection aktif

                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " icu_vitalising.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    File file = new File("C:/Users/ACER/Documents/Project Leona/New folder/Update/khanzakefa/report/subVentilator_fixed.jasper");
                    System.out.println("File found? " + file.exists());
                    System.out.println("no_rm: " + TNoRw.getText());
                    System.out.println("tanggal: " + Valid.SetTgl(DTPCari1.getSelectedItem().toString()));

                    Valid.MyReportqry("TesGrafik.jasper", "report", "::[ Data Catatan Perawatan Intensif ICU ]::", "SELECT "
                            + "icu_vitalising.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, "
                            + "reg_periksa.umurdaftar, reg_periksa.sttsumur, "
                            + "icu_vitalising.tgl_perawatan, icu_vitalising.jam_rawat, icu_vitalising.tekanan_darah, "
                            + "icu_vitalising.nadi, icu_vitalising.suhu, DATE_FORMAT(icu_vitalising.jam_rawat, '%H:%i') AS jam, "
                            + "icu_observasi.terapi_oksigen, icu_observasi.kesadaran, icu_observasi.ukuran_pupil, "
                            + "icu_observasi.rr AS rr_observasi, icu_observasi.hr AS hr_observasi, icu_observasi.sp02, "
                            + "icu_ventilator.tipe, icu_ventilator.rr AS rrven, icu_ventilator.ratio, "
                            + "dpjp_ranap.kd_dokter,dokter.nm_dokter, "
                            + "icu_ventilator.tv, icu_ventilator.mv, icu_ventilator.ipl, icu_ventilator.peep, "
                            + "icu_ventilator.fio2, icu_ventilator.peak, icu_ventilator.ett "
                            + "FROM icu_vitalising "
                            + "INNER JOIN reg_periksa ON icu_vitalising.no_rawat = reg_periksa.no_rawat "
                            + "inner join dpjp_ranap on dpjp_ranap.no_rawat=reg_periksa.no_rawat "
                            + "inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter "
                            + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                            + "LEFT JOIN icu_observasi ON icu_vitalising.no_rawat = icu_observasi.no_rawat "
                            + "    AND icu_vitalising.jam_rawat = icu_observasi.jam_rawat "
                            + "LEFT JOIN icu_ventilator ON icu_vitalising.no_rawat = icu_ventilator.no_rawat "
                            + "    AND icu_vitalising.jam_rawat = icu_ventilator.jam_rawat "
                            + "WHERE " + tgl + " AND ("
                            + "icu_vitalising.no_rawat LIKE '%" + TCari.getText().trim() + "%' OR "
                            + "reg_periksa.no_rkm_medis LIKE '%" + TCari.getText().trim() + "%' OR "
                            + "pasien.nm_pasien LIKE '%" + TCari.getText().trim() + "%') "
                            + "ORDER BY icu_vitalising.jam_rawat DESC",
                            param
                    );
                }
                break;
            case 5:
                if (tabModeVitalising.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (tabModeVitalising.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    param.put("ruang", Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?", TNoRw.getText()));
                    param.put("no_rm", TNoRw.getText());
                    param.put("tanggal", Valid.SetTgl(DTPCari1.getSelectedItem().toString()));
                    kodeoperator = Sequel.cariIsi(
                            "SELECT dokter.kd_dokter FROM dokter "
                            + "JOIN dpjp_ranap ON dpjp_ranap.kd_dokter = dokter.kd_dokter "
                            + "JOIN icu_vitalising AS ic ON ic.no_rawat = dpjp_ranap.no_rawat "
                            + "WHERE dpjp_ranap.no_rawat = ? AND ic.tgl_perawatan = ?",
                            TNoRw.getText(), Valid.SetTgl(DTPCari1.getSelectedItem().toString())
                    );

                    finger = Sequel.cariIsi(
                            "SELECT sha1(sidikjari.sidikjari) FROM sidikjari "
                            + "INNER JOIN pegawai ON pegawai.id = sidikjari.id "
                            + "WHERE pegawai.nik = ?", kodeoperator
                    );

                    String a = Sequel.cariIsi(
                            "SELECT dokter.nm_dokter FROM dokter "
                            + "JOIN dpjp_ranap ON dpjp_ranap.kd_dokter = dokter.kd_dokter "
                            + "JOIN icu_vitalising AS ic ON ic.no_rawat = dpjp_ranap.no_rawat "
                            + "WHERE dpjp_ranap.no_rawat = ?", TNoRw.getText()
                    );

                    param.put("finger",
                            "Dikeluarkan di " + akses.getnamars()
                            + ", Kabupaten/Kota " + akses.getkabupatenrs()
                            + "\nDitandatangani secara elektronik oleh " + a
                            + "\nID " + (finger.equals("") ? kodeoperator : finger)
                            + "\n" + Valid.SetTgl(DTPCari1.getSelectedItem().toString())
                    );
//                    try {
//                ps = koneksi.prepareStatement("select dpjp_ranap.kd_dokter,dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? and dpjp_ranap.kd_dokter<>?");
//                try {
//                    ps.setString(1, "");
//                    ps.setString(2, "");
//                    rs = ps.executeQuery();
//                    i = 2;
//                    while (rs.next()) {
//                        if (i == 2) {
//                            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
//                            param.put("finger2", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + Valid.SetTgl(DTPTgl.getSelectedItem().toString()));
//                            param.put("namadokter2", rs.getString("nm_dokter"));
//                        }
//                        if (i == 3) {
//                            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
//                            param.put("finger3", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + Valid.SetTgl(DTPTgl.getSelectedItem().toString()));
//                            param.put("namadokter3", rs.getString("nm_dokter"));
//                        }
//                        i++;
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                } finally {
//                    if (rs != null) {
//                        rs.close();
//                    }
//                    if (ps != null) {
//                        ps.close();
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : " + e);
//            }

//                    param.put("SUBREPORT_DIR", "C:/Users/ACER/Documents/Project Leona/New folder/Update/khanzakefa/report/");
                    String reportPath = System.getProperty("user.dir") + File.separator + "report" + File.separator;
                    param.put("SUBREPORT_DIR", reportPath);

                    param.put("REPORT_CONNECTION", koneksi); // koneksi = objek java.sql.Connection aktif

                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " icu_vitalising.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    File file = new File("C:/Users/ACER/Documents/Project Leona/New folder/Update/khanzakefa/report/subVentilator_fixed.jasper");
                    System.out.println("File found? " + file.exists());
                    System.out.println("no_rm: " + TNoRw.getText());
                    System.out.println("tanggal: " + Valid.SetTgl(DTPCari1.getSelectedItem().toString()));

                    Valid.MyReportqry("TesGrafik.jasper", "report", "::[ Data Catatan Perawatan Intensif ICU ]::", "SELECT "
                            + "icu_vitalising.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, "
                            + "reg_periksa.umurdaftar, reg_periksa.sttsumur, "
                            + "icu_vitalising.tgl_perawatan, icu_vitalising.jam_rawat, icu_vitalising.tekanan_darah, "
                            + "icu_vitalising.nadi, icu_vitalising.suhu, DATE_FORMAT(icu_vitalising.jam_rawat, '%H:%i') AS jam, "
                            + "icu_observasi.terapi_oksigen, icu_observasi.kesadaran, icu_observasi.ukuran_pupil, "
                            + "icu_observasi.rr AS rr_observasi, icu_observasi.hr AS hr_observasi, icu_observasi.sp02, "
                            + "icu_ventilator.tipe, icu_ventilator.rr AS rrven, icu_ventilator.ratio, "
                            + "dpjp_ranap.kd_dokter,dokter.nm_dokter, "
                            + "icu_ventilator.tv, icu_ventilator.mv, icu_ventilator.ipl, icu_ventilator.peep, "
                            + "icu_ventilator.fio2, icu_ventilator.peak, icu_ventilator.ett "
                            + "FROM icu_vitalising "
                            + "INNER JOIN reg_periksa ON icu_vitalising.no_rawat = reg_periksa.no_rawat "
                            + "inner join dpjp_ranap on dpjp_ranap.no_rawat=reg_periksa.no_rawat "
                            + "inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter "
                            + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                            + "LEFT JOIN icu_observasi ON icu_vitalising.no_rawat = icu_observasi.no_rawat "
                            + "    AND icu_vitalising.jam_rawat = icu_observasi.jam_rawat "
                            + "LEFT JOIN icu_ventilator ON icu_vitalising.no_rawat = icu_ventilator.no_rawat "
                            + "    AND icu_vitalising.jam_rawat = icu_ventilator.jam_rawat "
                            + "WHERE " + tgl + " AND ("
                            + "icu_vitalising.no_rawat LIKE '%" + TCari.getText().trim() + "%' OR "
                            + "reg_periksa.no_rkm_medis LIKE '%" + TCari.getText().trim() + "%' OR "
                            + "pasien.nm_pasien LIKE '%" + TCari.getText().trim() + "%') "
                            + "ORDER BY icu_vitalising.jam_rawat DESC",
                            param
                    );

                }
                break;
            default:
                break;
        }

        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        TCariPasien.setText("");
        BtnCariActionPerformed(null);
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampilObservasi();
                break;
            case 1:
                tampilVentilator();
                break;
            case 2:
                tampilInput();
                break;
            case 3:
                tampilOutput();
                break;
            case 4:
                tampilProgramHarian();
                break;
            case 5:
                tampilVitalising();
                break;
            default:
                break;
        }
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        isJns();
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampilObservasi();
                break;
            case 1:
                tampilVentilator();
                break;
            case 2:
                tampilInput();
                break;
            case 3:
                tampilOutput();
                break;
            case 4:
                tampilProgramHarian();
                break;
            case 5:
                tampilVitalising();
                break;
            default:
                break;
        }
}//GEN-LAST:event_TabRawatMouseClicked

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt, obsKesadaran, cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt, DTPTgl, cmbMnt);
}//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt, cmbJam, cmbDtk);
}//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
//        Valid.pindah(evt,cmbMnt,TKeluhan);
}//GEN-LAST:event_cmbDtkKeyPressed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        akses.setform("DlgRawatInap");
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(rootPaneCheckingEnabled);
}//GEN-LAST:event_btnPasienActionPerformed

    private void btnPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPasienKeyPressed
        Valid.pindah(evt, TCariPasien, DTPCari1);
}//GEN-LAST:event_btnPasienKeyPressed

    private void tbObservasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObservasiMouseClicked
        if (tabModeObservasi.getRowCount() != 0) {
            try {
                getDataObservasi();
            } catch (java.lang.NullPointerException e) {
            }

        }
}//GEN-LAST:event_tbObservasiMouseClicked

    private void tbVentilatorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentilatorMouseClicked
        if (tabModeVentilator.getRowCount() != 0) {
            try {
                getDataVentilator();
            } catch (java.lang.NullPointerException e) {
            }

        }
}//GEN-LAST:event_tbVentilatorMouseClicked

private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
    if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
        Valid.textKosong(TNoRw, "No.Rawat");
    } else {
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if ((obsOksigen.getText().trim().equals(""))
                        || (obsKesadaran.getText().trim().equals(""))
                        || (!obsUkuranPupil.getText().trim().equals(""))
                        || (!obsRR.getText().trim().equals(""))
                        || (!obsHR.getText().trim().equals(""))
                        || (!obsSPO2.getText().trim().equals(""))) {//||TDokter.getText().trim().equals("")){
                    Sequel.AutoComitFalse();
                    sukses = true;
                    if (Sequel.mengedittf("icu_observasi", "no_rawat='" + tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 1)
                            + "' and tgl_perawatan='" + tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 4)
                            + "' and jam_rawat='" + tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 5)
                            + "' and terapi_oksigen='" + tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 6)
                            + "' and kesadaran='" + tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 7)
                            + "' and ukuran_pupil='" + tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 8)
                            + "' and rr='" + tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 9)
                            + "' and hr='" + tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 10)
                            + "' and sp02='" + tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 11) + "'", "no_rawat='" + TNoRw.getText()
                            + "', tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "")
                            + "', jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "'"
                            + // Tanda kutip diperbaiki
                            ", terapi_oksigen='" + obsOksigen.getText()
                            + "', kesadaran='" + obsKesadaran.getText()
                            + "', ukuran_pupil='" + obsUkuranPupil.getText()
                            + "', rr='" + obsRR.getText()
                            + "', hr='" + obsHR.getText()
                            + "', sp02='" + obsSPO2.getText()
                            + "'" // Tambahkan koma sebelum terapi_oksigen
                    ) == true) {
                        if (sukses == true) {
                            Sequel.Commit();
                            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                            tbObservasi.setValueAt(TNoRw.getText(), tbObservasi.getSelectedRow(), 1);
                            tbObservasi.setValueAt(TNoRM.getText(), tbObservasi.getSelectedRow(), 2);
                            tbObservasi.setValueAt(TPasien.getText(), tbObservasi.getSelectedRow(), 3);
//                                    tbRawatDr.setValueAt(TNmPrw.getText(),tbRawatDr.getSelectedRow(), 4);
                            tbObservasi.setValueAt(obsOksigen.getText(), tbObservasi.getSelectedRow(), 6);
//                                    tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                            tbObservasi.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""), tbObservasi.getSelectedRow(), 4);
                            tbObservasi.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbObservasi.getSelectedRow(), 5);
                            //tbObservasi.setValueAt(Double.parseDouble(TTnd.getText()), tbObservasi.getSelectedRow(), 9);
                            tbObservasi.setValueAt(obsKesadaran.getText(), tbObservasi.getSelectedRow(), 7);
                            tbObservasi.setValueAt(obsUkuranPupil.getText(), tbObservasi.getSelectedRow(), 8);
                            tbObservasi.setValueAt(obsRR.getText(), tbObservasi.getSelectedRow(), 9);
                            tbObservasi.setValueAt(obsHR.getText(), tbObservasi.getSelectedRow(), 10);
                            tbObservasi.setValueAt(obsSPO2.getText(), tbObservasi.getSelectedRow(), 11);
//                                tbObservasi.setValueAt(Menejemen.getText(), tbObservasi.getSelectedRow(), 15);
                            BtnBatalActionPerformed(evt);

                        } else {
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }
                        Sequel.AutoComitTrue();

                    }
                    tampilObservasi();
                }
                break;
            case 1:
                if ((venTipe.getText().trim().equals(""))
                        || (venRR.getText().trim().equals(""))
                        || (!venRatio.getText().trim().equals(""))
                        || (!venTV.getText().trim().equals(""))
                        || (!venMV.getText().trim().equals(""))
                        || (!venIPL.getText().trim().equals(""))
                        || (!venPEEP.getText().trim().equals(""))
                        || (!venFIO2.getText().trim().equals(""))
                        || (!venPEAK.getText().trim().equals(""))
                        || (!venETT.getText().trim().equals(""))) {//||TDokter.getText().trim().equals("")){
                    Sequel.AutoComitFalse();
                    sukses = true;
                    if (Sequel.mengedittf("icu_ventilator", "no_rawat='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 1)
                            + "' and tgl_perawatan='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 4)
                            + "' and jam_rawat='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 5)
                            + "' and tipe='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 6)
                            + "' and rr='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 7)
                            + "' and ratio='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 8)
                            + "' and tv='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 9)
                            + "' and mv='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 10)
                            + "' and ipl='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 11)
                            + "' and peep='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 12)
                            + "' and fio2='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 13)
                            + "' and peak='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 14)
                            + "' and ett='" + tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 15) + "'", "no_rawat='" + TNoRw.getText()
                            + "', tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "")
                            + "', jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "'"
                            + // Tanda kutip diperbaiki
                            ", tipe='" + venTipe.getText()
                            + "', rr='" + venRR.getText()
                            + "', ratio='" + venRatio.getText()
                            + "', tv='" + venTV.getText()
                            + "', mv='" + venMV.getText()
                            + "', ipl='" + venIPL.getText()
                            + "', peep='" + venPEEP.getText()
                            + "', fio2='" + venFIO2.getText()
                            + "', peak='" + venPEAK.getText()
                            + "', ett='" + venETT.getText()
                            + "'" // Tambahkan koma sebelum terapi_oksigen
                    ) == true) {
                        if (sukses == true) {
                            Sequel.Commit();
                            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                            tbVentilator.setValueAt(TNoRw.getText(), tbVentilator.getSelectedRow(), 1);
                            tbVentilator.setValueAt(TNoRM.getText(), tbVentilator.getSelectedRow(), 2);
                            tbVentilator.setValueAt(TPasien.getText(), tbVentilator.getSelectedRow(), 3);
//                                    tbRawatDr.setValueAt(TNmPrw.getText(),tbRawatDr.getSelectedRow(), 4);
                            tbVentilator.setValueAt(venTipe.getText(), tbVentilator.getSelectedRow(), 6);
//                                    tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                            tbVentilator.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""), tbVentilator.getSelectedRow(), 4);
                            tbVentilator.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbVentilator.getSelectedRow(), 5);
                            //tbVentilator.setValueAt(Double.parseDouble(TTnd.getText()), tbVentilator.getSelectedRow(), 9);
                            tbVentilator.setValueAt(venRR.getText(), tbVentilator.getSelectedRow(), 7);
                            tbVentilator.setValueAt(venRatio.getText(), tbVentilator.getSelectedRow(), 8);
                            tbVentilator.setValueAt(venTV.getText(), tbVentilator.getSelectedRow(), 9);
                            tbVentilator.setValueAt(venMV.getText(), tbVentilator.getSelectedRow(), 10);
                            tbVentilator.setValueAt(venIPL.getText(), tbVentilator.getSelectedRow(), 11);
                            tbVentilator.setValueAt(venPEEP.getText(), tbVentilator.getSelectedRow(), 12);
                            tbVentilator.setValueAt(venFIO2.getText(), tbVentilator.getSelectedRow(), 13);
                            tbVentilator.setValueAt(venPEAK.getText(), tbVentilator.getSelectedRow(), 14);
                            tbVentilator.setValueAt(venETT.getText(), tbVentilator.getSelectedRow(), 15);
//                                tbVentilator.setValueAt(Menejemen.getText(), tbVentilator.getSelectedRow(), 15);
                            BtnBatalActionPerformed(evt);

                        } else {
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }
                        Sequel.AutoComitTrue();

                    }
                    tampilVentilator();
                }
                break;
            case 2:
                if ((in_ivd1.getText().trim().equals(""))
                        || (in_ivd2.getText().trim().equals(""))
                        || (!in_transfusi.getText().trim().equals(""))
                        || (!in_makan.getText().trim().equals(""))
                        || (!in_minum.getText().trim().equals(""))
                        || (!in_ngt.getText().trim().equals(""))
                        || (!in_total.getText().trim().equals(""))) {//||TDokter.getText().trim().equals("")){
                    Sequel.AutoComitFalse();
                    sukses = true;
                    if (Sequel.mengedittf("icu_input", "no_rawat='" + tbInput.getValueAt(tbInput.getSelectedRow(), 1)
                            + "' and tgl_perawatan='" + tbInput.getValueAt(tbInput.getSelectedRow(), 4)
                            + "' and jam_rawat='" + tbInput.getValueAt(tbInput.getSelectedRow(), 5)
                            + "' and ivfd1='" + tbInput.getValueAt(tbInput.getSelectedRow(), 6)
                            + "' and ivfd2='" + tbInput.getValueAt(tbInput.getSelectedRow(), 7)
                            + "' and transfusi='" + tbInput.getValueAt(tbInput.getSelectedRow(), 8)
                            + "' and makan='" + tbInput.getValueAt(tbInput.getSelectedRow(), 9)
                            + "' and minum='" + tbInput.getValueAt(tbInput.getSelectedRow(), 10)
                            + "' and ngt='" + tbInput.getValueAt(tbInput.getSelectedRow(), 11)
                            + "' and total_intake='" + tbInput.getValueAt(tbInput.getSelectedRow(), 12) + "'", "no_rawat='" + TNoRw.getText()
                            + "', tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "")
                            + "', jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "'"
                            + // Tanda kutip diperbaiki
                            ", ivfd1='" + in_ivd1.getText()
                            + "', ivfd2='" + in_ivd2.getText()
                            + "', transfusi='" + in_transfusi.getText()
                            + "', makan='" + in_makan.getText()
                            + "', minum='" + in_minum.getText()
                            + "', ngt='" + in_ngt.getText()
                            + "', total_intake='" + in_total.getText()
                            + "'" // Tambahkan koma sebelum terapi_oksigen
                    ) == true) {
                        if (sukses == true) {
                            Sequel.Commit();
                            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                            tbInput.setValueAt(TNoRw.getText(), tbInput.getSelectedRow(), 1);
                            tbInput.setValueAt(TNoRM.getText(), tbInput.getSelectedRow(), 2);
                            tbInput.setValueAt(TPasien.getText(), tbInput.getSelectedRow(), 3);
//                                    tbRawatDr.setValueAt(TNmPrw.getText(),tbRawatDr.getSelectedRow(), 4);
                            tbInput.setValueAt(in_ivd1.getText(), tbInput.getSelectedRow(), 6);
//                                    tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                            tbInput.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""), tbInput.getSelectedRow(), 4);
                            tbInput.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbInput.getSelectedRow(), 5);
                            //tbInput.setValueAt(Double.parseDouble(TTnd.getText()), tbInput.getSelectedRow(), 9);
                            tbInput.setValueAt(in_ivd2.getText(), tbInput.getSelectedRow(), 7);
                            tbInput.setValueAt(in_transfusi.getText(), tbInput.getSelectedRow(), 8);
                            tbInput.setValueAt(in_makan.getText(), tbInput.getSelectedRow(), 9);
                            tbInput.setValueAt(in_minum.getText(), tbInput.getSelectedRow(), 10);
                            tbInput.setValueAt(in_ngt.getText(), tbInput.getSelectedRow(), 11);
                            tbInput.setValueAt(in_total.getText(), tbInput.getSelectedRow(), 12);

//                                tbVentilator.setValueAt(Menejemen.getText(), tbVentilator.getSelectedRow(), 15);
                            BtnBatalActionPerformed(evt);

                        } else {
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }
                        Sequel.AutoComitTrue();

                    }
                    tampilInput();
                }
                break;
            case 3:
                if ((out_drain1.getText().trim().equals(""))
                        || (out_drain2.getText().trim().equals(""))
                        || (!out_cairan.getText().trim().equals(""))
                        || (!out_keteter.getText().trim().equals(""))
                        || (!out_warna_urin.getText().trim().equals(""))
                        || (!out_bap.getText().trim().equals(""))
                        || (!out_iwl.getText().trim().equals(""))
                        || (!out_total.getText().trim().equals(""))) {//||TDokter.getText().trim().equals("")){
                    Sequel.AutoComitFalse();
                    sukses = true;
                    if (Sequel.mengedittf("icu_output", "no_rawat='" + tbOutput.getValueAt(tbOutput.getSelectedRow(), 1)
                            + "' and tgl_perawatan='" + tbOutput.getValueAt(tbOutput.getSelectedRow(), 4)
                            + "' and jam_rawat='" + tbOutput.getValueAt(tbOutput.getSelectedRow(), 5)
                            + "' and drain1='" + tbOutput.getValueAt(tbOutput.getSelectedRow(), 6)
                            + "' and drain2='" + tbOutput.getValueAt(tbOutput.getSelectedRow(), 7)
                            + "' and cairan_lambung='" + tbOutput.getValueAt(tbOutput.getSelectedRow(), 8)
                            + "' and kateter='" + tbOutput.getValueAt(tbOutput.getSelectedRow(), 9)
                            + "' and warna_urin='" + tbOutput.getValueAt(tbOutput.getSelectedRow(), 10)
                            + "' and bap='" + tbOutput.getValueAt(tbOutput.getSelectedRow(), 11)
                            + "' and iwl='" + tbOutput.getValueAt(tbOutput.getSelectedRow(), 12)
                            + "' and total_output='" + tbOutput.getValueAt(tbOutput.getSelectedRow(), 13) + "'", "no_rawat='" + TNoRw.getText()
                            + "', tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "")
                            + "', jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "'"
                            + // Tanda kutip diperbaiki
                            ", drain1='" + out_drain1.getText()
                            + "', drain2='" + out_drain2.getText()
                            + "', cairan_lambung='" + out_cairan.getText()
                            + "', kateter='" + out_keteter.getText()
                            + "', warna_urin='" + out_warna_urin.getText()
                            + "', bap='" + out_bap.getText()
                            + "', iwl='" + out_iwl.getText()
                            + "', total_output='" + out_total.getText()
                            + "'" // Tambahkan koma sebelum terapi_oksigen
                    ) == true) {
                        if (sukses == true) {
                            Sequel.Commit();
                            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                            tbOutput.setValueAt(TNoRw.getText(), tbOutput.getSelectedRow(), 1);
                            tbOutput.setValueAt(TNoRM.getText(), tbOutput.getSelectedRow(), 2);
                            tbOutput.setValueAt(TPasien.getText(), tbOutput.getSelectedRow(), 3);
//                                    tbRawatDr.setValueAt(TNmPrw.getText(),tbRawatDr.getSelectedRow(), 4);
                            tbOutput.setValueAt(out_drain1.getText(), tbOutput.getSelectedRow(), 6);
//                                    tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                            tbOutput.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""), tbOutput.getSelectedRow(), 4);
                            tbOutput.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbOutput.getSelectedRow(), 5);
                            //tbOutput.setValueAt(Double.parseDouble(TTnd.getText()), tbOutput.getSelectedRow(), 9);
                            tbOutput.setValueAt(out_drain2.getText(), tbOutput.getSelectedRow(), 7);
                            tbOutput.setValueAt(out_cairan.getText(), tbOutput.getSelectedRow(), 8);
                            tbOutput.setValueAt(out_keteter.getText(), tbOutput.getSelectedRow(), 9);
                            tbOutput.setValueAt(out_warna_urin.getText(), tbOutput.getSelectedRow(), 10);
                            tbOutput.setValueAt(out_bap.getText(), tbOutput.getSelectedRow(), 11);
                            tbOutput.setValueAt(out_iwl.getText(), tbOutput.getSelectedRow(), 12);
                            tbOutput.setValueAt(out_total.getText(), tbOutput.getSelectedRow(), 13);

//                                tbVentilator.setValueAt(Menejemen.getText(), tbVentilator.getSelectedRow(), 15);
                            BtnBatalActionPerformed(evt);

                        } else {
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }
                        Sequel.AutoComitTrue();

                    }
                    tampilOutput();
                }
                break;
            case 4:
                if ((prog_skemaInfus.getText().trim().equals(""))
                        || (prog_suntikan.getText().trim().equals(""))
                        || (!prog_oral.getText().trim().equals(""))
                        || (!prog_nebulizer.getText().trim().equals(""))
                        || (!prog_lain_lain.getText().trim().equals(""))
                        || (!prog_diet.getText().trim().equals(""))) {//||TDokter.getText().trim().equals("")){
                    Sequel.AutoComitFalse();
                    sukses = true;
                    if (Sequel.mengedittf("icu_prog_harian", "no_rawat='" + tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 1)
                            + "' and tgl_perawatan='" + tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 4)
                            + "' and jam_rawat='" + tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 5)
                            + "' and skema_infus='" + tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 6)
                            + "' and suntikan='" + tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 7)
                            + "' and oral='" + tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 8)
                            + "' and nebulizer='" + tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 9)
                            + "' and lain_lain='" + tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 10)
                            + "' and diet='" + tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 11) + "'", "no_rawat='" + TNoRw.getText()
                            + "', tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "")
                            + "', jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "'"
                            + // Tanda kutip diperbaiki
                            ", skema_infus='" + prog_skemaInfus.getText()
                            + "', suntikan='" + prog_suntikan.getText()
                            + "', oral='" + prog_oral.getText()
                            + "', nebulizer='" + prog_nebulizer.getText()
                            + "', lain_lain='" + prog_lain_lain.getText()
                            + "', diet='" + prog_diet.getText()
                            + "'" // Tambahkan koma sebelum terapi_oksigen
                    ) == true) {
                        if (sukses == true) {
                            Sequel.Commit();
                            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                            tbProgHarian.setValueAt(TNoRw.getText(), tbProgHarian.getSelectedRow(), 1);
                            tbProgHarian.setValueAt(TNoRM.getText(), tbProgHarian.getSelectedRow(), 2);
                            tbProgHarian.setValueAt(TPasien.getText(), tbProgHarian.getSelectedRow(), 3);
//                                    tbRawatDr.setValueAt(TNmPrw.getText(),tbRawatDr.getSelectedRow(), 4);
                            tbProgHarian.setValueAt(prog_skemaInfus.getText(), tbProgHarian.getSelectedRow(), 6);
//                                    tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                            tbProgHarian.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""), tbProgHarian.getSelectedRow(), 4);
                            tbProgHarian.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbProgHarian.getSelectedRow(), 5);
                            //tbProgHarian.setValueAt(Double.parseDouble(TTnd.getText()), tbProgHarian.getSelectedRow(), 9);
                            tbProgHarian.setValueAt(prog_suntikan.getText(), tbProgHarian.getSelectedRow(), 7);
                            tbProgHarian.setValueAt(prog_oral.getText(), tbProgHarian.getSelectedRow(), 8);
                            tbProgHarian.setValueAt(prog_nebulizer.getText(), tbProgHarian.getSelectedRow(), 9);
                            tbProgHarian.setValueAt(prog_lain_lain.getText(), tbProgHarian.getSelectedRow(), 10);
                            tbProgHarian.setValueAt(prog_diet.getText(), tbProgHarian.getSelectedRow(), 11);
//                                tbProgHarian.setValueAt(Menejemen.getText(), tbObservasi.getSelectedRow(), 15);
                            BtnBatalActionPerformed(evt);

                        } else {
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }
                        Sequel.AutoComitTrue();

                    }
                    tampilProgramHarian();
                }
                break;
            case 5:
                if ((vtlTekananDarah.getText().trim().equals(""))
                        || (vtlNadi.getText().trim().equals(""))
                        || (!vtlSuhu.getText().trim().equals(""))) {//||TDokter.getText().trim().equals("")){
                    Sequel.AutoComitFalse();
                    sukses = true;
                    if (Sequel.mengedittf("icu_vitalising", "no_rawat='" + tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 1)
                            + "' and tgl_perawatan='" + tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 4)
                            + "' and jam_rawat='" + tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 5)
                            + "' and tekanan_darah='" + tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 6)
                            + "' and nadi='" + tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 7)
                            + "' and suhu='" + tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 8) + "'", "no_rawat='" + TNoRw.getText()
                            + "', tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "")
                            + "', jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "'"
                            + // Tanda kutip diperbaiki
                            ", tekanan_darah='" + vtlTekananDarah.getText()
                            + "', nadi='" + vtlNadi.getText()
                            + "', suhu='" + vtlSuhu.getText()
                            + "'" // Tambahkan koma sebelum terapi_oksigen
                    ) == true) {
                        if (sukses == true) {
                            Sequel.Commit();
                            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                            tbVitalising.setValueAt(vtlTekananDarah.getText(), tbVitalising.getSelectedRow(), 6);
//                                    tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                            tbVitalising.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""), tbVitalising.getSelectedRow(), 4);
                            tbVitalising.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbVitalising.getSelectedRow(), 5);
                            //tbObservasi.setValueAt(Double.parseDouble(TTnd.getText()), tbObservasi.getSelectedRow(), 9);
                            tbVitalising.setValueAt(vtlNadi.getText(), tbVitalising.getSelectedRow(), 7);
                            tbVitalising.setValueAt(vtlSuhu.getText(), tbVitalising.getSelectedRow(), 8);
                            BtnBatalActionPerformed(evt);

                        } else {
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }
                        Sequel.AutoComitTrue();

                    }
                    tampilVitalising();
                }
                break;
            default:
                break;
        }
    }
}//GEN-LAST:event_BtnEditActionPerformed

private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
        BtnEditActionPerformed(null);
    } else {
        Valid.pindah(evt, BtnHapus, BtnPrint);
    }
}//GEN-LAST:event_BtnEditKeyPressed

    private void tbInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbInputMouseClicked
        if (tabModeInput.getRowCount() != 0) {
            try {
                getDataInput();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }//GEN-LAST:event_tbInputMouseClicked

    private void obsOksigenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_obsOksigenKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
//            TDokter.setText(perawatan.dokter.tampil3(KdDok.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
//            BtnSeekDokterActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, obsKesadaran);
        }
    }//GEN-LAST:event_obsOksigenKeyPressed

    private void obsKesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_obsKesadaranKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isJns();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
//            btnTindakanActionPerformed(null);
        } else {
            Valid.pindah(evt, obsOksigen, BtnSimpan);
        }
    }//GEN-LAST:event_obsKesadaranKeyPressed

    private void venTipeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_venTipeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
//            TPerawat.setText(perawatan.petugas.tampil3(kdptg.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
//            BtnSeekPetugasActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, venRR);
        }
    }//GEN-LAST:event_venTipeKeyPressed

    private void venRRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_venRRKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isJns();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
//            btnTindakan4ActionPerformed(null);
        } else {
            Valid.pindah(evt, venTipe, BtnSimpan);
        }
    }//GEN-LAST:event_venRRKeyPressed

    private void tbObservasiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObservasiKeyReleased
        if (tabModeObservasi.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataObservasi();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }//GEN-LAST:event_tbObservasiKeyReleased

    private void tbVentilatorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbVentilatorKeyReleased
        if (tabModeVentilator.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataVentilator();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }//GEN-LAST:event_tbVentilatorKeyReleased

    private void tbInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbInputKeyReleased
        if (tabModeInput.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataInput();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }//GEN-LAST:event_tbInputKeyReleased

    private void TNoRwMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TNoRwMouseClicked
        Window[] wins = Window.getWindows();
        for (Window win : wins) {
            if (win instanceof JDialog) {
                win.setLocationRelativeTo(internalFrame1);
                win.toFront();
            }
        }
    }//GEN-LAST:event_TNoRwMouseClicked

    private void tbOutputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbOutputMouseClicked
        // TODO add your handling code here:
        if (tabModeOutput.getRowCount() != 0) {
            try {
                getDataOutput();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }//GEN-LAST:event_tbOutputMouseClicked

    private void tbOutputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbOutputKeyReleased
        // TODO add your handling code here:
        if (tabModeOutput.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataOutput();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }//GEN-LAST:event_tbOutputKeyReleased

    private void out_drain2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_drain2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_out_drain2KeyPressed

    private void out_drain1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_drain1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_out_drain1KeyPressed

    private void out_cairanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_cairanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_out_cairanKeyPressed

    private void tbProgHarianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProgHarianMouseClicked
        // TODO add your handling code here:
        if (tabModeProgHarian.getRowCount() != 0) {
            try {
                getDataProgramHarian();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }//GEN-LAST:event_tbProgHarianMouseClicked

    private void tbProgHarianKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbProgHarianKeyReleased
        // TODO add your handling code here:
        if (tabModeProgHarian.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataProgramHarian();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }//GEN-LAST:event_tbProgHarianKeyReleased

    private void obsRRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_obsRRKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_obsRRKeyPressed

    private void obsHRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_obsHRKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_obsHRKeyPressed

    private void venIPLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_venIPLKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_venIPLKeyPressed

    private void venPEEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_venPEEPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_venPEEPKeyPressed

    private void in_minumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_in_minumKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_in_minumKeyPressed

    private void in_ngtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_in_ngtKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_in_ngtKeyPressed

    private void in_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_in_totalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_in_totalKeyPressed

    private void out_keteterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_keteterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_out_keteterKeyPressed

    private void out_warna_urinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_warna_urinKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_out_warna_urinKeyPressed

    private void out_bapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_bapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_out_bapKeyPressed

    private void out_iwlKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_iwlKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_out_iwlKeyPressed

    private void out_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_totalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_out_totalKeyPressed

    private void prog_skemaInfusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prog_skemaInfusKeyPressed
//        Valid.pindah2(evt,KdPeg,TPemeriksaan);
    }//GEN-LAST:event_prog_skemaInfusKeyPressed

    private void prog_suntikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prog_suntikanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_prog_suntikanKeyPressed

    private void prog_oralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prog_oralKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_prog_oralKeyPressed

    private void prog_nebulizerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prog_nebulizerKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_prog_nebulizerKeyPressed

    private void prog_lain_lainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prog_lain_lainKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_prog_lain_lainKeyPressed

    private void prog_dietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prog_dietKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_prog_dietKeyPressed

    private void obsOksigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_obsOksigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_obsOksigenActionPerformed

    private void venMVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_venMVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_venMVActionPerformed

    private void venETTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_venETTKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_venETTKeyReleased

    private void venTipeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_venTipeKeyReleased
        // TODO add your handling code here
        //hitung();
    }//GEN-LAST:event_venTipeKeyReleased

    private void venRRKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_venRRKeyReleased
        // TODO add your handling code here:
        //hitung();
    }//GEN-LAST:event_venRRKeyReleased

    private void in_ivd1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_in_ivd1KeyReleased
        // TODO add your handling code here:
        hitung();
    }//GEN-LAST:event_in_ivd1KeyReleased

    private void in_ivd2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_in_ivd2KeyReleased
        // TODO add your handling code here:
        hitung();
    }//GEN-LAST:event_in_ivd2KeyReleased

    private void in_transfusiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_in_transfusiKeyReleased
        // TODO add your handling code here
        hitung();
    }//GEN-LAST:event_in_transfusiKeyReleased

    private void in_makanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_in_makanKeyReleased
        // TODO add your handling code here:
        hitung();
    }//GEN-LAST:event_in_makanKeyReleased

    private void in_minumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_in_minumKeyReleased
        // TODO add your handling code here:
        hitung();
    }//GEN-LAST:event_in_minumKeyReleased

    private void in_ngtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_in_ngtKeyReleased
        // TODO add your handling code here:
        hitung();
    }//GEN-LAST:event_in_ngtKeyReleased

    private void out_drain1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_drain1KeyReleased
        // TODO add your handling code here:
        hitungOut();
    }//GEN-LAST:event_out_drain1KeyReleased

    private void out_drain2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_drain2KeyReleased
        // TODO add your handling code here:
        hitungOut();
    }//GEN-LAST:event_out_drain2KeyReleased

    private void out_cairanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_cairanKeyReleased
        // TODO add your handling code here:
        hitungOut();
    }//GEN-LAST:event_out_cairanKeyReleased

    private void out_keteterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_keteterKeyReleased
        // TODO add your handling code here:
        hitungOut();
    }//GEN-LAST:event_out_keteterKeyReleased

    private void out_warna_urinKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_warna_urinKeyReleased
        // TODO add your handling code here:
        hitungOut();
    }//GEN-LAST:event_out_warna_urinKeyReleased

    private void out_bapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_bapKeyReleased
        // TODO add your handling code here:
        hitungOut();
    }//GEN-LAST:event_out_bapKeyReleased

    private void out_iwlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_iwlKeyReleased
        // TODO add your handling code here:
        hitungOut();
    }//GEN-LAST:event_out_iwlKeyReleased

    private void out_totalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_out_totalKeyReleased
        // TODO add your handling code here:
        hitungOut();
    }//GEN-LAST:event_out_totalKeyReleased

    private void tbVitalisingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVitalisingMouseClicked
        // TODO add your handling code here:
        if (tabModeVitalising.getRowCount() != 0) {
            try {
                getDataVitalising();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }//GEN-LAST:event_tbVitalisingMouseClicked

    private void tbVitalisingKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbVitalisingKeyReleased
        // TODO add your handling code here:
        if (tabModeVitalising.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataVitalising();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }//GEN-LAST:event_tbVitalisingKeyReleased

    private void vtlTekananDarahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vtlTekananDarahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vtlTekananDarahActionPerformed

    private void vtlTekananDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vtlTekananDarahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_vtlTekananDarahKeyPressed

    private void vtlNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vtlNadiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_vtlNadiKeyPressed
    private void hitung() {
        try {
            int angka1 = Integer.parseInt(in_ivd1.getText());
            int angka2 = Integer.parseInt(in_ivd2.getText());
            int angka3 = Integer.parseInt(in_transfusi.getText());
            int angka4 = Integer.parseInt(in_makan.getText());
            int angka5 = Integer.parseInt(in_minum.getText());
            int angka6 = Integer.parseInt(in_ngt.getText());
            int hasil = angka1 + angka2 + angka3 + angka4 + angka5 + angka6;
            in_total.setText(String.valueOf(hasil));
        } catch (NumberFormatException e) {
            in_total.setText("0");
        }

    }

    private void hitungOut() {
        try {
            int angka1 = Integer.parseInt(out_drain1.getText());
            int angka2 = Integer.parseInt(out_drain2.getText());
            int angka3 = Integer.parseInt(out_cairan.getText());
            int angka4 = Integer.parseInt(out_keteter.getText());
            int angka5 = Integer.parseInt(out_warna_urin.getText());
            int angka6 = Integer.parseInt(out_bap.getText());
            int angka7 = Integer.parseInt(out_iwl.getText());
            int hasil = angka1 + angka2 + angka3 + angka4 + angka5 + angka6 + angka7;
            out_total.setText(String.valueOf(hasil));
        } catch (NumberFormatException e) {
            out_total.setText("0");
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCatatanPerawatanIntensifICU dialog = new DlgCatatanPerawatanIntensifICU(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField BagianRS;
    private javax.swing.JTextField Bhp;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private javax.swing.JTextField JmDokter;
    private javax.swing.JTextField JmPerawat;
    private javax.swing.JTextField KSO;
    private widget.Label LCount;
    private javax.swing.JTextField Menejemen;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.TextBox TCari;
    private widget.TextBox TCariPasien;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTextField TTnd;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Button btnPasien;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.TextBox in_ivd1;
    private widget.TextBox in_ivd2;
    private widget.TextBox in_makan;
    private widget.TextBox in_minum;
    private widget.TextBox in_ngt;
    private widget.TextBox in_total;
    private widget.TextBox in_transfusi;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel5;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator5;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label2;
    private widget.Label label3;
    private widget.Label label4;
    private widget.Label label5;
    private widget.Label label6;
    private widget.Label label7;
    private widget.Label label8;
    private widget.Label label9;
    private widget.TextBox obsHR;
    private widget.TextBox obsKesadaran;
    private widget.TextBox obsOksigen;
    private widget.TextBox obsRR;
    private widget.TextBox obsSPO2;
    private widget.TextBox obsUkuranPupil;
    private widget.TextBox out_bap;
    private widget.TextBox out_cairan;
    private widget.TextBox out_drain1;
    private widget.TextBox out_drain2;
    private widget.TextBox out_iwl;
    private widget.TextBox out_keteter;
    private widget.TextBox out_total;
    private widget.TextBox out_warna_urin;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass13;
    private widget.panelisi panelGlass15;
    private widget.panelisi panelGlass16;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.TextArea prog_diet;
    private widget.TextArea prog_lain_lain;
    private widget.TextArea prog_nebulizer;
    private widget.TextArea prog_oral;
    private widget.TextArea prog_skemaInfus;
    private widget.TextArea prog_suntikan;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.Table tbInput;
    private widget.Table tbObservasi;
    private widget.Table tbOutput;
    private widget.Table tbProgHarian;
    private widget.Table tbVentilator;
    private widget.Table tbVitalising;
    private widget.TextBox venETT;
    private widget.TextBox venFIO2;
    private widget.TextBox venIPL;
    private widget.TextBox venMV;
    private widget.TextBox venPEAK;
    private widget.TextBox venPEEP;
    private widget.TextBox venRR;
    private widget.TextBox venRatio;
    private widget.TextBox venTV;
    private widget.TextBox venTipe;
    private widget.TextBox vtlNadi;
    private widget.TextBox vtlSuhu;
    private widget.TextBox vtlTekananDarah;
    // End of variables declaration//GEN-END:variables
    private widget.Button BtnSkorBromagePascaAnestesi, BtnPenilaianPreInduksi, BtnHasilPemeriksaanUSGUrologi, BtnHasilPemeriksaanUSGGynecologi, BtnHasilPemeriksaanEKG, BtnHasilPemeriksaanUSGNeonatus, BtnHasilEndoskopiFaringLaring;

    public void tampilObservasi() {
        Valid.tabelKosong(tabModeObservasi);
        try {

            ps = koneksi.prepareStatement("select icu_observasi.no_rawat,"
                    + "reg_periksa.no_rkm_medis,"
                    + "pasien.nm_pasien,"
                    + "icu_observasi.tgl_perawatan,"
                    + "icu_observasi.jam_rawat,"
                    + "icu_observasi.terapi_oksigen,"
                    + "icu_observasi.kesadaran,"
                    + "icu_observasi.ukuran_pupil,"
                    + "icu_observasi.rr,icu_observasi.hr,icu_observasi.sp02 "
                    + "from pasien inner join reg_periksa inner join icu_observasi "
                    + "on icu_observasi.no_rawat=reg_periksa.no_rawat "
                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "where icu_observasi.tgl_perawatan between ? and ? and icu_observasi.no_rawat = ? order by icu_observasi.no_rawat,icu_observasi.tgl_perawatan,icu_observasi.jam_rawat desc");

            try {

                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, (TCari.getText() + ""));

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeObservasi.addRow(new Object[]{
                        false, rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11)

                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeObservasi.getRowCount());
    }

    private void getDataObservasi() {
        if (tbObservasi.getSelectedRow() != -1) {
            TNoRw.setText(tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 1).toString());
            TNoRM.setText(tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 2).toString());
            TPasien.setText(tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 3).toString());
            obsOksigen.setText(tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 6).toString());
//            TDokter.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),6).toString());
            cmbJam.setSelectedItem(tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 5).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 5).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 5).toString().substring(6, 8));
//            TNmPrw.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),4).toString());
            obsKesadaran.setText(tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 7).toString());
            obsUkuranPupil.setText(tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 8).toString());
            obsRR.setText(tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 9).toString());
            obsHR.setText(tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 10).toString());
            obsSPO2.setText(tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 11).toString());
            Valid.SetTgl(DTPTgl, tbObservasi.getValueAt(tbObservasi.getSelectedRow(), 4).toString());
            isJns();
        }
    }

    private void tampilVitalising() {
        Valid.tabelKosong(tabModeVitalising);
        try {

            ps = koneksi.prepareStatement("select icu_vitalising.no_rawat,"
                    + "reg_periksa.no_rkm_medis,"
                    + "pasien.nm_pasien,"
                    + "icu_vitalising.tgl_perawatan,"
                    + "icu_vitalising.jam_rawat,"
                    + "icu_vitalising.tekanan_darah,"
                    + "icu_vitalising.nadi,"
                    + "icu_vitalising.suhu "
                    + "from pasien inner join reg_periksa inner join icu_vitalising "
                    + "on icu_vitalising.no_rawat=reg_periksa.no_rawat "
                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "where icu_vitalising.tgl_perawatan between ? and ? and icu_vitalising.no_rawat = ? order by icu_vitalising.no_rawat,icu_vitalising.tgl_perawatan,icu_vitalising.jam_rawat desc");

            try {

                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, (TCari.getText() + ""));

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeVitalising.addRow(new Object[]{
                        false, rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)

                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeVitalising.getRowCount());
    }

    private void getDataVitalising() {
        if (tbVitalising.getSelectedRow() != -1) {
            TNoRw.setText(tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 1).toString());
            TNoRM.setText(tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 2).toString());
            TPasien.setText(tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 3).toString());
            vtlTekananDarah.setText(tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 6).toString());
//            TDokter.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),6).toString());
            cmbJam.setSelectedItem(tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 5).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 5).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 5).toString().substring(6, 8));
//            TNmPrw.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),4).toString());
            vtlNadi.setText(tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 7).toString());
            vtlSuhu.setText(tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 8).toString());
            Valid.SetTgl(DTPTgl, tbVitalising.getValueAt(tbVitalising.getSelectedRow(), 4).toString());
            isJns();
        }
    }

    private void tampilVentilator() {
        Valid.tabelKosong(tabModeVentilator);
        try {

            ps = koneksi.prepareStatement("select icu_ventilator.no_rawat,"
                    + "reg_periksa.no_rkm_medis,"
                    + "pasien.nm_pasien,"
                    + "icu_ventilator.tgl_perawatan,"
                    + "icu_ventilator.jam_rawat,"
                    + "icu_ventilator.tipe,"
                    + "icu_ventilator.rr,"
                    + "icu_ventilator.ratio,"
                    + "icu_ventilator.tv,icu_ventilator.mv,icu_ventilator.ipl,icu_ventilator.peep,icu_ventilator.fio2,icu_ventilator.peak,icu_ventilator.ett "
                    + "from pasien inner join reg_periksa inner join icu_ventilator "
                    + "on icu_ventilator.no_rawat=reg_periksa.no_rawat "
                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "where icu_ventilator.tgl_perawatan between ? and ? and icu_ventilator.no_rawat = ? order by icu_ventilator.no_rawat,icu_ventilator.tgl_perawatan,icu_ventilator.jam_rawat desc");

            try {

                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, (TCari.getText() + ""));

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeVentilator.addRow(new Object[]{
                        false, rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15)

                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeVentilator.getRowCount());
    }

    private void getDataVentilator() {
        if (tbVentilator.getSelectedRow() != -1) {
            TNoRw.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 1).toString());
            TNoRM.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 2).toString());
            TPasien.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 3).toString());
            venTipe.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 6).toString());
//            TPerawat.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),6).toString());
            cmbJam.setSelectedItem(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 5).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 5).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 5).toString().substring(6, 8));
//            TNmPrwPetugas.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),4).toString());
            venRR.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 7).toString());
            venRatio.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 8).toString());
            venTV.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 9).toString());
            venMV.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 10).toString());
            venIPL.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 11).toString());
            venPEEP.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 12).toString());
            venFIO2.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 13).toString());
            venPEAK.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 14).toString());
            venETT.setText(tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 15).toString());
            Valid.SetTgl(DTPTgl, tbVentilator.getValueAt(tbVentilator.getSelectedRow(), 4).toString());
            isJns();
        }
    }

    public void tampilInput() {
        Valid.tabelKosong(tabModeInput);
        try {

            ps = koneksi.prepareStatement("select icu_input.no_rawat,"
                    + "reg_periksa.no_rkm_medis,"
                    + "pasien.nm_pasien,"
                    + "icu_input.tgl_perawatan,"
                    + "icu_input.jam_rawat,"
                    + "icu_input.ivfd1,"
                    + "icu_input.ivfd2,"
                    + "icu_input.transfusi,"
                    + "icu_input.makan,"
                    + "icu_input.minum,icu_input.ngt,icu_input.total_intake "
                    + "from pasien inner join reg_periksa inner join icu_input "
                    + "on icu_input.no_rawat=reg_periksa.no_rawat "
                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "where icu_input.tgl_perawatan between ? and ? and icu_input.no_rawat = ? order by icu_input.no_rawat,icu_input.tgl_perawatan,icu_input.jam_rawat desc");

            try {

                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, (TCari.getText() + ""));

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeInput.addRow(new Object[]{
                        false, rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeInput.getRowCount());
    }

    private void getDataInput() {
        if (tbInput.getSelectedRow() != -1) {
            TNoRw.setText(tbInput.getValueAt(tbInput.getSelectedRow(), 1).toString());
            TNoRM.setText(tbInput.getValueAt(tbInput.getSelectedRow(), 2).toString());
            TPasien.setText(tbInput.getValueAt(tbInput.getSelectedRow(), 3).toString());
            in_ivd1.setText(tbInput.getValueAt(tbInput.getSelectedRow(), 6).toString());
//            TDokter2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),6).toString());
            in_ivd2.setText(tbInput.getValueAt(tbInput.getSelectedRow(), 7).toString());
//            TPerawat2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),8).toString());
            cmbJam.setSelectedItem(tbInput.getValueAt(tbInput.getSelectedRow(), 5).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbInput.getValueAt(tbInput.getSelectedRow(), 5).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbInput.getValueAt(tbInput.getSelectedRow(), 5).toString().substring(6, 8));
//            TNmPrwDokterPetugas.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),4).toString());
            in_transfusi.setText(tbInput.getValueAt(tbInput.getSelectedRow(), 8).toString());
            in_makan.setText(tbInput.getValueAt(tbInput.getSelectedRow(), 9).toString());
            in_minum.setText(tbInput.getValueAt(tbInput.getSelectedRow(), 10).toString());
            in_ngt.setText(tbInput.getValueAt(tbInput.getSelectedRow(), 11).toString());
            in_total.setText(tbInput.getValueAt(tbInput.getSelectedRow(), 12).toString());
            Valid.SetTgl(DTPTgl, tbInput.getValueAt(tbInput.getSelectedRow(), 4).toString());
            isJns();
        }
    }

    private void isRawat() {
        Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ", TNoRM, TNoRw.getText());
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=? ", TPasien, TNoRM.getText());
    }

    private void isJns() {
        if (TabRawat.getSelectedIndex() == 0) {
            if (!obsKesadaran.getText().equals("")) {
//                Sequel.cariIsi("select jns_perawatan_inap.nm_perawatan from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",TNmPrw,TKdPrw.getText());
                Sequel.cariIsi("select jns_perawatan_inap.bhp from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", Bhp, obsKesadaran.getText());
                Sequel.cariIsi("select jns_perawatan_inap.material from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", BagianRS, obsKesadaran.getText());
                Sequel.cariIsi("select jns_perawatan_inap.tarif_tindakandr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", JmDokter, obsKesadaran.getText());
                Sequel.cariIsi("select jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", JmPerawat, obsKesadaran.getText());
                Sequel.cariIsi("select jns_perawatan_inap.kso from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", KSO, obsKesadaran.getText());
                Sequel.cariIsi("select jns_perawatan_inap.menejemen from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", Menejemen, obsKesadaran.getText());
                Sequel.cariIsi("select jns_perawatan_inap.total_byrdr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", TTnd, obsKesadaran.getText());
            }
        } else if (TabRawat.getSelectedIndex() == 1) {
            if (!venRR.getText().equals("")) {
//                Sequel.cariIsi("select jns_perawatan_inap.nm_perawatan from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",TNmPrwPetugas,TKdPrwPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.bhp from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", Bhp, venRR.getText());
                Sequel.cariIsi("select jns_perawatan_inap.material from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", BagianRS, venRR.getText());
                Sequel.cariIsi("select jns_perawatan_inap.tarif_tindakandr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", JmDokter, venRR.getText());
                Sequel.cariIsi("select jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", JmPerawat, venRR.getText());
                Sequel.cariIsi("select jns_perawatan_inap.kso from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", KSO, venRR.getText());
                Sequel.cariIsi("select jns_perawatan_inap.menejemen from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", Menejemen, venRR.getText());
                Sequel.cariIsi("select jns_perawatan_inap.total_byrpr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", TTnd, venRR.getText());
            }
        } else if (TabRawat.getSelectedIndex() == 2) {
            if (!in_transfusi.getText().equals("")) {
//                Sequel.cariIsi("select jns_perawatan_inap.nm_perawatan from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",TNmPrwDokterPetugas,TKdPrwDokterPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.bhp from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", Bhp, in_transfusi.getText());
                Sequel.cariIsi("select jns_perawatan_inap.material from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", BagianRS, in_transfusi.getText());
                Sequel.cariIsi("select jns_perawatan_inap.tarif_tindakandr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", JmDokter, in_transfusi.getText());
                Sequel.cariIsi("select jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", JmPerawat, in_transfusi.getText());
                Sequel.cariIsi("select jns_perawatan_inap.kso from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", KSO, in_transfusi.getText());
                Sequel.cariIsi("select jns_perawatan_inap.menejemen from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", Menejemen, in_transfusi.getText());
                Sequel.cariIsi("select jns_perawatan_inap.total_byrdrpr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ", TTnd, in_transfusi.getText());
            }
        }
    }

    public void setNoRm(String norwt, Date awal) {
        TNoRw.setText(norwt);
//        obsOksigen.setText(Sequel.cariIsi("select dpjp_ranap.kd_dokter from dpjp_ranap where dpjp_ranap.no_rawat=?", norwt));
        TCariPasien.setText(Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ", norwt));
//        TDokter.setText(perawatan.dokter.tampil3(KdDok.getText()));
//        in_ivd1.setText(obsOksigen.getText());
//        TDokter2.setText(TDokter.getText()); 
        isRawat();
        isPsien();
        DTPCari1.setDate(awal);
        //DTPCari2.setDate(akhir);
        TCari.setText(norwt);
//        ChkInput.setSelected(true);
//        isForm();
//        ChkInput1.setSelected(true);
//        isForm2(); 
        TabRawatMouseClicked(null);
    }

    public void setKamar(String kamar) {
        this.kamar = kamar;
    }

    public void setJenisBayar(String jenisbayar) {
        this.jenisbayar = jenisbayar;
    }

//    private void isForm(){
//        if(ChkInput.isSelected()==true){
//            ChkInput.setVisible(false);
//            PanelInput1.setPreferredSize(new Dimension(WIDTH,273));
//            panelGlass12.setVisible(true);      
//            ChkInput.setVisible(true);
//        }else if(ChkInput.isSelected()==false){           
//            ChkInput.setVisible(false);            
//            PanelInput1.setPreferredSize(new Dimension(WIDTH,20));
//            panelGlass12.setVisible(false);      
//            ChkInput.setVisible(true);
//        }
//    }
    public void isCek() {
        tinggi = 0;
        BtnSimpan.setEnabled(akses.gettindakan_ranap());
        BtnHapus.setEnabled(akses.gettindakan_ranap());
        BtnEdit.setEnabled(akses.gettindakan_ranap());
        BtnPrint.setEnabled(akses.gettindakan_ranap());

        BtnSkorBromagePascaAnestesi.setVisible(akses.getskor_bromage_pasca_anestesi());
        if (akses.getskor_bromage_pasca_anestesi() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianPreInduksi.setVisible(akses.getpenilaian_pre_induksi());
        if (akses.getpenilaian_pre_induksi() == true) {
            tinggi = tinggi + 24;
        }
        BtnHasilPemeriksaanUSGUrologi.setVisible(akses.gethasil_usg_urologi());
        if (akses.gethasil_usg_urologi() == true) {
            tinggi = tinggi + 24;
        }
        BtnHasilPemeriksaanUSGNeonatus.setVisible(akses.gethasil_usg_neonatus());
        if (akses.gethasil_usg_neonatus() == true) {
            tinggi = tinggi + 24;
        }
        BtnHasilPemeriksaanUSGGynecologi.setVisible(akses.gethasil_usg_gynecologi());
        if (akses.gethasil_usg_gynecologi() == true) {
            tinggi = tinggi + 24;
        }
        BtnHasilPemeriksaanEKG.setVisible(akses.gethasil_pemeriksaan_ekg());
        if (akses.gethasil_pemeriksaan_ekg() == true) {
            tinggi = tinggi + 24;
        }
        BtnHasilEndoskopiFaringLaring.setVisible(akses.gethasil_endoskopi_faring_laring());
        if (akses.gethasil_endoskopi_faring_laring() == true) {
            tinggi = tinggi + 24;
        }

//        FormMenu.setPreferredSize(new Dimension(195,(tinggi+10)));
//        if(akses.getjml2()>=1){
//            KdPeg.setText(akses.getkode());
//            TPegawai.setText(pegawai.tampil3(KdPeg.getText()));
//            Jabatan.setText(pegawai.tampilJbatan(KdPeg.getText()));
//        }
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;

            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if (ChkJln.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else if (ChkJln.isSelected() == false) {
                    nilai_jam = cmbJam.getSelectedIndex();
                    nilai_menit = cmbMnt.getSelectedIndex();
                    nilai_detik = cmbDtk.getSelectedIndex();
                }

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
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void tampilOutput() {
        Valid.tabelKosong(tabModeOutput);
        try {

            ps = koneksi.prepareStatement("select icu_output.no_rawat,"
                    + "reg_periksa.no_rkm_medis,"
                    + "pasien.nm_pasien,"
                    + "icu_output.tgl_perawatan,"
                    + "icu_output.jam_rawat,"
                    + "icu_output.drain1,"
                    + "icu_output.drain2,"
                    + "icu_output.cairan_lambung,"
                    + "icu_output.kateter,"
                    + "icu_output.warna_urin,icu_output.bap,icu_output.iwl,icu_output.total_output "
                    + "from pasien inner join reg_periksa inner join icu_output "
                    + "on icu_output.no_rawat=reg_periksa.no_rawat "
                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "where icu_output.tgl_perawatan between ? and ? and icu_output.no_rawat = ? order by icu_output.no_rawat,icu_output.tgl_perawatan,icu_output.jam_rawat desc");

            try {

                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, (TCari.getText() + ""));

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeOutput.addRow(new Object[]{
                        false, rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeOutput.getRowCount());
    }

    private void getDataOutput() {
        if (tbOutput.getSelectedRow() != -1) {
            TNoRw.setText(tbOutput.getValueAt(tbOutput.getSelectedRow(), 1).toString());
            TNoRM.setText(tbOutput.getValueAt(tbOutput.getSelectedRow(), 2).toString());
            TPasien.setText(tbOutput.getValueAt(tbOutput.getSelectedRow(), 3).toString());
            out_drain1.setText(tbOutput.getValueAt(tbOutput.getSelectedRow(), 6).toString());
            out_drain2.setText(tbOutput.getValueAt(tbOutput.getSelectedRow(), 7).toString());
            out_cairan.setText(tbOutput.getValueAt(tbOutput.getSelectedRow(), 8).toString());
            out_keteter.setText(tbOutput.getValueAt(tbOutput.getSelectedRow(), 9).toString());
            out_warna_urin.setText(tbOutput.getValueAt(tbOutput.getSelectedRow(), 10).toString());
            out_bap.setText(tbOutput.getValueAt(tbOutput.getSelectedRow(), 11).toString());
            out_iwl.setText(tbOutput.getValueAt(tbOutput.getSelectedRow(), 12).toString());
            out_total.setText(tbOutput.getValueAt(tbOutput.getSelectedRow(), 13).toString());

            cmbJam.setSelectedItem(tbOutput.getValueAt(tbOutput.getSelectedRow(), 5).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbOutput.getValueAt(tbOutput.getSelectedRow(), 5).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbOutput.getValueAt(tbOutput.getSelectedRow(), 5).toString().substring(6, 8));
            Valid.SetTgl(DTPTgl, tbOutput.getValueAt(tbOutput.getSelectedRow(), 4).toString());
        }
    }

    private void tampilProgramHarian() {
        Valid.tabelKosong(tabModeProgHarian);
        try {

            ps = koneksi.prepareStatement("select icu_prog_harian.no_rawat,"
                    + "reg_periksa.no_rkm_medis,"
                    + "pasien.nm_pasien,"
                    + "icu_prog_harian.tgl_perawatan,"
                    + "icu_prog_harian.jam_rawat,"
                    + "icu_prog_harian.skema_infus,"
                    + "icu_prog_harian.suntikan,"
                    + "icu_prog_harian.oral,"
                    + "icu_prog_harian.nebulizer,icu_prog_harian.lain_lain,icu_prog_harian.diet "
                    + "from pasien inner join reg_periksa inner join icu_prog_harian "
                    + "on icu_prog_harian.no_rawat=reg_periksa.no_rawat "
                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "where icu_prog_harian.tgl_perawatan between ? and ? and icu_prog_harian.no_rawat = ? order by icu_prog_harian.no_rawat,icu_prog_harian.tgl_perawatan,icu_prog_harian.jam_rawat desc");

            try {

                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, (TCari.getText() + ""));

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeProgHarian.addRow(new Object[]{
                        false, rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11)

                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeProgHarian.getRowCount());
    }

    private void getDataProgramHarian() {
        if (tbProgHarian.getSelectedRow() != -1) {
            TNoRw.setText(tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 1).toString());
            TNoRM.setText(tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 2).toString());
            TPasien.setText(tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 3).toString());
            prog_skemaInfus.setText(tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 6).toString());
//            TDokter.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),6).toString());
            cmbJam.setSelectedItem(tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 5).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 5).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 5).toString().substring(6, 8));
//            TNmPrw.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),4).toString());
            prog_suntikan.setText(tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 7).toString());
            prog_oral.setText(tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 8).toString());
            prog_nebulizer.setText(tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 9).toString());
            prog_lain_lain.setText(tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 10).toString());
            prog_diet.setText(tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 11).toString());
            Valid.SetTgl(DTPTgl, tbProgHarian.getValueAt(tbProgHarian.getSelectedRow(), 4).toString());
            isJns();
        }
    }
//    private void isForm2(){
//        if(ChkInput1.isSelected()==true){
//            ChkInput1.setVisible(false);
//            PanelInput2.setPreferredSize(new Dimension(WIDTH,156));
//            panelGlass14.setVisible(true);      
//            ChkInput1.setVisible(true);
//        }else if(ChkInput1.isSelected()==false){           
//            ChkInput1.setVisible(false);            
//            PanelInput2.setPreferredSize(new Dimension(WIDTH,20));
//            panelGlass14.setVisible(false);      
//            ChkInput1.setVisible(true);
//        }
//    }

    private void tampilPemeriksaanGinekologi() {
        Valid.tabelKosong(tabModeGinekologi);
        try {
            ps6 = koneksi.prepareStatement("select pemeriksaan_ginekologi_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                    + "pemeriksaan_ginekologi_ranap.tgl_perawatan,pemeriksaan_ginekologi_ranap.jam_rawat,pemeriksaan_ginekologi_ranap.inspeksi,pemeriksaan_ginekologi_ranap.inspeksi_vulva,pemeriksaan_ginekologi_ranap.inspekulo_gine, "
                    + "pemeriksaan_ginekologi_ranap.fluxus_gine,pemeriksaan_ginekologi_ranap.fluor_gine,pemeriksaan_ginekologi_ranap.vulva_inspekulo, "
                    + "pemeriksaan_ginekologi_ranap.portio_inspekulo,pemeriksaan_ginekologi_ranap.sondage,pemeriksaan_ginekologi_ranap.portio_dalam,pemeriksaan_ginekologi_ranap.bentuk, "
                    + "pemeriksaan_ginekologi_ranap.cavum_uteri,pemeriksaan_ginekologi_ranap.mobilitas,pemeriksaan_ginekologi_ranap.ukuran, pemeriksaan_ginekologi_ranap.nyeri_tekan, pemeriksaan_ginekologi_ranap.adnexa_kanan, pemeriksaan_ginekologi_ranap.adnexa_kiri,"
                    + "pemeriksaan_ginekologi_ranap.cavum_douglas "
                    + "from pasien inner join reg_periksa inner join pemeriksaan_ginekologi_ranap "
                    + "on pemeriksaan_ginekologi_ranap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "
                    + "pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ginekologi_ranap.no_rawat like ? or "
                    + "pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.no_rkm_medis like ? or "
                    + "pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pasien.nm_pasien like ? or  "
                    + "pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ginekologi_ranap.inspeksi like ? or "
                    + "pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ginekologi_ranap.inspeksi_vulva like ? or "
                    + "pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ginekologi_ranap.inspekulo_gine like ? "
                    + "order by pemeriksaan_ginekologi_ranap.no_rawat,pemeriksaan_ginekologi_ranap.tgl_perawatan,pemeriksaan_ginekologi_ranap.jam_rawat desc");
            try {
                ps6.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps6.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps6.setString(3, "%" + TCariPasien.getText() + "%");
                ps6.setString(4, "%" + TCari.getText().trim() + "%");
                ps6.setString(5, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps6.setString(6, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps6.setString(7, "%" + TCariPasien.getText() + "%");
                ps6.setString(8, "%" + TCari.getText().trim() + "%");
                ps6.setString(9, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps6.setString(10, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps6.setString(11, "%" + TCariPasien.getText() + "%");
                ps6.setString(12, "%" + TCari.getText().trim() + "%");
                ps6.setString(13, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps6.setString(14, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps6.setString(15, "%" + TCariPasien.getText() + "%");
                ps6.setString(16, "%" + TCari.getText().trim() + "%");
                ps6.setString(17, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps6.setString(18, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps6.setString(19, "%" + TCariPasien.getText() + "%");
                ps6.setString(20, "%" + TCari.getText().trim() + "%");
                ps6.setString(21, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps6.setString(22, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps6.setString(23, "%" + TCariPasien.getText() + "%");
                ps6.setString(24, "%" + TCari.getText().trim() + "%");

                rs = ps6.executeQuery();
                while (rs.next()) {
                    tabModeGinekologi.addRow(new Object[]{
                        false, rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                        rs.getString("tgl_perawatan"), rs.getString("jam_rawat"), rs.getString("inspeksi"),
                        rs.getString("inspeksi_vulva"), rs.getString("inspekulo_gine"), rs.getString("fluxus_gine"),
                        rs.getString("fluor_gine"), rs.getString("vulva_inspekulo"), rs.getString("portio_inspekulo"),
                        rs.getString("sondage"), rs.getString("portio_dalam"), rs.getString("bentuk"),
                        rs.getString("cavum_uteri"), rs.getString("mobilitas"), rs.getString("ukuran"),
                        rs.getString("nyeri_tekan"), rs.getString("adnexa_kanan"), rs.getString("adnexa_kiri"),
                        rs.getString("cavum_douglas")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps5 != null) {
                    ps5.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi :" + e);
        }
        LCount.setText("" + tabModeGinekologi.getRowCount());
    }

//    private void getDataPemeriksaanGinekologi() {
//         if(tbPemeriksaanGinekologi.getSelectedRow()!= -1) {
//            TNoRw.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),1).toString());
//            TNoRM.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),2).toString());
//            TPasien.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),3).toString());
//            Valid.SetTgl(DTPTgl,tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),4).toString());
//            cmbJam.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5).toString().substring(0,2));
//            cmbMnt.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5).toString().substring(3,5));
//            cmbDtk.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5).toString().substring(6,8));
//            TInspeksi.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),6).toString());
//            TInspeksiVulva.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),7).toString());
//            TInspekuloGine.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),8).toString());
//            cmbFluxusGine.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),9).toString());
//            cmbFluorGine.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),10).toString());
//            TVulvaInspekulo.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),11).toString());
//            TPortioInspekulo.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),12).toString());
//            TSondage.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),13).toString());
//            TPortioDalam.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),14).toString());
//            TBentuk.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),15).toString());
//            TCavumUteri.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),16).toString());
//            cmbMobilitas.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),17).toString());
//            TUkuran.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),18).toString());
//            cmbNyeriTekan.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),19).toString());
//            TAdnexaKanan.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),20).toString());
//            TAdnexaKiri.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),21).toString());
//            TCavumDouglas.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),22).toString());
//        }
//    }
//    private void isForm3(){
//        if(ChkInput2.isSelected()==true){
//            ChkInput2.setVisible(false);
//            PanelInput3.setPreferredSize(new Dimension(WIDTH,246));
//            panelGlass15.setVisible(true);      
//            ChkInput2.setVisible(true);
//        }else if(ChkInput2.isSelected()==false){           
//            ChkInput2.setVisible(false);            
//            PanelInput3.setPreferredSize(new Dimension(WIDTH,20));
//            panelGlass15.setVisible(false);      
//            ChkInput2.setVisible(true);
//        }
//    }
//    private void isMenu(){
//        if(ChkAccor.isSelected()==true){
//            ChkAccor.setVisible(false);
//            PanelAccor.setPreferredSize(new Dimension(205,HEIGHT));
//            FormMenu.setVisible(true); 
//            ChkAccor.setVisible(true);
//        }else if(ChkAccor.isSelected()==false){  
//            ChkAccor.setVisible(false);
//            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
//            FormMenu.setVisible(false);    
//            ChkAccor.setVisible(true);
//        }
//    }
    private void initRawatInap() {
        BtnSkorBromagePascaAnestesi = new widget.Button();
        BtnSkorBromagePascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnSkorBromagePascaAnestesi.setText("Skor Bromage Pasca Anestesi");
        BtnSkorBromagePascaAnestesi.setFocusPainted(false);
        BtnSkorBromagePascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnSkorBromagePascaAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkorBromagePascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkorBromagePascaAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkorBromagePascaAnestesi.setName("BtnSkorBromagePascaAnestesi");
        BtnSkorBromagePascaAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkorBromagePascaAnestesi.setRoundRect(false);
//        BtnSkorBromagePascaAnestesi.addActionListener(this::BtnSkorBromagePascaAnestesiActionPerformed);

        BtnPenilaianPreInduksi = new widget.Button();
        BtnPenilaianPreInduksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnPenilaianPreInduksi.setText("Penilaian Pre Induksi");
        BtnPenilaianPreInduksi.setFocusPainted(false);
        BtnPenilaianPreInduksi.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnPenilaianPreInduksi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreInduksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreInduksi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreInduksi.setName("Penilaian Pre Induksi");
        BtnPenilaianPreInduksi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreInduksi.setRoundRect(false);
//        BtnPenilaianPreInduksi.addActionListener(this::BtnPenilaianPreInduksiActionPerformed);

        BtnHasilPemeriksaanUSGUrologi = new widget.Button();
        BtnHasilPemeriksaanUSGUrologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanUSGUrologi.setText("Hasil USG Urologi");
        BtnHasilPemeriksaanUSGUrologi.setFocusPainted(false);
        BtnHasilPemeriksaanUSGUrologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanUSGUrologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSGUrologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSGUrologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSGUrologi.setName("BtnHasilPemeriksaanUSGUrologi"); // NOI18N
        BtnHasilPemeriksaanUSGUrologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSGUrologi.setRoundRect(false);
//        BtnHasilPemeriksaanUSGUrologi.addActionListener(this::BtnHasilPemeriksaanUSGUrologiActionPerformed);

        BtnHasilPemeriksaanUSGGynecologi = new widget.Button();
        BtnHasilPemeriksaanUSGGynecologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanUSGGynecologi.setText("Hasil USG Gynecologi");
        BtnHasilPemeriksaanUSGGynecologi.setFocusPainted(false);
        BtnHasilPemeriksaanUSGGynecologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanUSGGynecologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSGGynecologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSGGynecologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSGGynecologi.setName("BtnHasilPemeriksaanUSGGynecologi"); // NOI18N
        BtnHasilPemeriksaanUSGGynecologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSGGynecologi.setRoundRect(false);
//        BtnHasilPemeriksaanUSGGynecologi.addActionListener(this::BtnHasilPemeriksaanUSGGynecologiActionPerformed);

        BtnHasilPemeriksaanUSGNeonatus = new widget.Button();
        BtnHasilPemeriksaanUSGNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanUSGNeonatus.setText("Hasil USG Neonatus");
        BtnHasilPemeriksaanUSGNeonatus.setFocusPainted(false);
        BtnHasilPemeriksaanUSGNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnHasilPemeriksaanUSGNeonatus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSGNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSGNeonatus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSGNeonatus.setName("BtnHasilPemeriksaanUSGNeonatus");
        BtnHasilPemeriksaanUSGNeonatus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSGNeonatus.setRoundRect(false);
//        BtnHasilPemeriksaanUSGNeonatus.addActionListener(this::BtnHasilPemeriksaanUSGNeonatusActionPerformed);

        BtnHasilPemeriksaanEKG = new widget.Button();
        BtnHasilPemeriksaanEKG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanEKG.setText("Hasil Pemeriksaan EKG");
        BtnHasilPemeriksaanEKG.setFocusPainted(false);
        BtnHasilPemeriksaanEKG.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanEKG.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanEKG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanEKG.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanEKG.setName("BtnHasilPemeriksaanEKG"); // NOI18N
        BtnHasilPemeriksaanEKG.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanEKG.setRoundRect(false);
//        BtnHasilPemeriksaanEKG.addActionListener(this::BtnHasilPemeriksaanEKGActionPerformed);

        BtnHasilEndoskopiFaringLaring = new widget.Button();
        BtnHasilEndoskopiFaringLaring.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilEndoskopiFaringLaring.setText("Hasil Endoskopi Faring/Laring");
        BtnHasilEndoskopiFaringLaring.setFocusPainted(false);
        BtnHasilEndoskopiFaringLaring.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnHasilEndoskopiFaringLaring.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilEndoskopiFaringLaring.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilEndoskopiFaringLaring.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilEndoskopiFaringLaring.setName("BtnHasilEndoskopiFaringLaring");
        BtnHasilEndoskopiFaringLaring.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilEndoskopiFaringLaring.setRoundRect(false);
//        BtnHasilEndoskopiFaringLaring.addActionListener(this::BtnHasilEndoskopiFaringLaringActionPerformed);

//        FormMenu.add(BtnRiwayat);
//        FormMenu.add(BtnResepObat);
//        FormMenu.add(BtnCopyResep);
//        FormMenu.add(BtnPermintaanStok);
//        FormMenu.add(BtnPermintaanResepPulang);
//        FormMenu.add(BtnInputObat);
//        FormMenu.add(BtnObatBhp);
//        FormMenu.add(BtnBerkasDigital);
//        FormMenu.add(BtnPermintaanLab);
//        FormMenu.add(BtnPermintaanRad);
//        FormMenu.add(BtnJadwalOperasi);
//        FormMenu.add(BtnSKDP);
//        FormMenu.add(BtnRujukKeluar);
//        FormMenu.add(BtnDiagnosa);
//        FormMenu.add(BtnResume);
//        FormMenu.add(BtnAwalKeperawatanUmum);
//        FormMenu.add(BtnAwalKeperawatanKandungan);
//        FormMenu.add(BtnAwalFisioterapi);
//        FormMenu.add(BtnAwalMedis);
//        FormMenu.add(BtnAwalMedisKandungan);
//        FormMenu.add(BtnAwalMedisHemodialisa);
//        FormMenu.add(BtnPenilaianPreInduksi);
//        FormMenu.add(BtnChecklistPreOperasi);
//        FormMenu.add(BtnSignInSebelumAnestesi);
//        FormMenu.add(BtnTimeOutSebelumInsisi);
//        FormMenu.add(BtnSignOutSebelumMenutupLuka);
//        FormMenu.add(BtnChecklistPostOperasi);
//        FormMenu.add(BtnPenilaianPreOperasi);
//        FormMenu.add(BtnPenilaianPreAnestesi);
//        FormMenu.add(BtnSkorAldrettePascaAnestesi);
//        FormMenu.add(BtnSkorStewardPascaAnestesi);
//        FormMenu.add(BtnSkorBromagePascaAnestesi);
//        FormMenu.add(BtnPenilaianPsikolog);
//        FormMenu.add(BtnPerencanaanPemulangan);
//        FormMenu.add(BtnPenilaianLanjutanResikoJatuhDewasa);
//        FormMenu.add(BtnPenilaianLanjutanResikoJatuhAnak);
//        FormMenu.add(BtnPenilaianLanjutanResikoJatuhLansia);
//        FormMenu.add(BtnPenilaianLanjutanResikoJatuhNeonatus);
//        FormMenu.add(BtnPenilaianLanjutanResikoJatuhGeriatri);
//        FormMenu.add(BtnPenilaianLanjutanResikoJatuhPsikiatri);
//        FormMenu.add(BtnPenilaianLanjutanSkriningFungsional);
//        FormMenu.add(BtnPenilaianResikoDekubitus);
//        FormMenu.add(BtnHasilPemeriksaanUSG);
//        FormMenu.add(BtnHasilPemeriksaanUSGUrologi);
//        FormMenu.add(BtnHasilPemeriksaanUSGNeonatus);
//        FormMenu.add(BtnHasilPemeriksaanUSGGynecologi);
//        FormMenu.add(BtnHasilPemeriksaanEKG);
//        FormMenu.add(BtnHasilEndoskopiFaringLaring);
//        FormMenu.add(BtnDokumentasiESWL);
//        FormMenu.add(BtnCatatanPersalinan);
//        FormMenu.add(BtnCatatan);
//        FormMenu.add(BtnCatatanObservasiRanap);
//        FormMenu.add(BtnCatatanObservasiRanapKebidanan);
//        FormMenu.add(BtnCatatanObservasiRanapPostPartum);
//        FormMenu.add(BtnFollowUpDBD);
//        FormMenu.add(BtnCatatanKeperawatan);
//        FormMenu.add(BtnCatatanCekGDS);
//        FormMenu.add(BtnPenilaianUlangNyeri);
//        FormMenu.add(BtnPemantauanPEWSAnak);
//        FormMenu.add(BtnPemantauanPEWSDewasa);
//        FormMenu.add(BtnPemantauanMEOWS);
//        FormMenu.add(BtnPemantauanEWSNeonatus);
//        FormMenu.add(BtnChecklistKriteriaMasukHCU);
//        FormMenu.add(BtnChecklistKriteriaKeluarHCU);
//        FormMenu.add(BtnChecklistKriteriaMasukICU);
//        FormMenu.add(BtnChecklistKriteriaKeluarICU);
//        FormMenu.add(BtnMonitoringReaksiTranfusi);
//        FormMenu.add(BtnSkriningNutrisiDewasa);
//        FormMenu.add(BtnSkriningNutrisiLansia);
//        FormMenu.add(BtnSkriningNutrisiAnak);
//        FormMenu.add(BtnSkriningGiziLanjut);
//        FormMenu.add(BtnAsuhanGizi);
//        FormMenu.add(BtnMonitoringAsuhanGizi);
//        FormMenu.add(BtnCatatanADIMEGizi);
//        FormMenu.add(BtnKonselingFarmasi);
//        FormMenu.add(BtnInformasiObat);
//        FormMenu.add(BtnRekonsiliasiObat);
//        FormMenu.add(BtnTransferAntarRuang);
//        FormMenu.add(BtnPengkajianRestrain);
//        FormMenu.add(BtnPenilaianPasienTerminal);
//        FormMenu.add(BtnPenilaianKorbanKekerasan);
//        FormMenu.add(BtnPenilaianKecemasanAnak);
//        FormMenu.add(BtnPenilaianPasienPenyakitMenular);
//        FormMenu.add(BtnPenilaianTambahanGeriatri);
//        FormMenu.add(BtnPenilaianTambahanBunuhDiri);
//        FormMenu.add(BtnPenilaianTambahanPerilakuKekerasan);
//        FormMenu.add(BtnPenilaianTambahanMelarikanDiri);
    }

}
