package faou;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.border.BevelBorder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class hmx extends JFrame {

    // مرجع للاستدعاءات للخارج (تحديث التقويم مثلاً)
    private Consumer<String> bookingStateCallback;

    // حقول واجهة منظمة بأسماء واضحة
    // الأساسيات
    private JTextField nameField, phoneField, maharField;

    // القوائم
    private JComboBox<String> comboBox, comtime, comphont, genderCombo;
    private JComboBox<String> sweetsProviderCombo, tableClothColorCombo, djNameCombo;

    // الأرقام/النصوص (اليسار/اليمين)
    private JTextField numberField, depositAmountField, totalAmountField, otherCostsField;
    private JTextField koshaFeeField, koshaNumberField, tablesFlowersFeeField;

    // مؤثرات
    private JTextField foamCakeMoldFeeField, sparklersFeeField, shamBandFeeField, djFeeField;
    private JTextField bubblesField, laserLightingFeeField, smokeFeeField, mixerCraneCamerasFeeField;

    // تصوير/داتاشو/ستوديو
    private JTextField extraLeftField, totalField, photoFeeField, photosCountField;
    private JTextField videoFeeField, dataShowFeeField, dataShowCountField, studioFeeField;

    // الكيك
    private JTextField cakeMoldsCountField, cakeMoldNumberField, cakeAmountField;
    private JTextField cakePiecesCountField, cakeCuttingFeeField, cakeMoldNumber2Field;

    // الأطفال والمقبلات
    private JTextField childPlatesCountField, childPlatePriceField;
    private JTextField plateWithDrinks2PriceField, appetizersPlatePriceField;

    // خيارات العشاء والمقبلات والأطفال
    private JComboBox<String> dinnerOption1Combo, dinnerOption2Combo, dinnerOption3Combo, dinnerOption4Combo, dinnerOption5Combo;
    private JComboBox<String> appetizersOption1Combo, appetizersOption2Combo, kidsPlateOption1Combo, kidsPlateOption2Combo;

    // شعّالات/خبز/كرين
    private JComboBox<String> sparklersCountCombo, breadOvenTypeCombo;
    private JCheckBox craneCheckbox;

    // إجماليات
    private JTextField grandTotalField, amountDueField, amountDueReceivedField, remainingAmountField;

    // أزرار وعنوان
    private JButton btnFinalBooking, btnTempBooking, btnCancelBooking, btnprint;
    private JButton btnMorning, btnEvening;
    private JLabel lblTitle;

    // فرض فترة معينة للنافذة (صباحا أو مساء)
    private String forcedEventTime = null;

    // مصادر الجمع التلقائي
    private final List<JTextField> sumSources = new ArrayList<>();
    
    // قائمة حقول المبالغ للتحجيم التلقائي
    private final List<JTextField> amountFields = new ArrayList<>();
    
    // حراس التحديث الداخلي
    private boolean internalUpdate = false;
    private boolean recalcScheduled = false;

    public hmx(int day, int month, int year, String hallName, Consumer<String> bookingCallback) {
        this.bookingStateCallback = bookingCallback;

        setTitle("حجز يوم " + day + " / " + month + " / " + year + " - " + hallName);

        try {
            String iconPath = "C:\\Users\\mohammed\\Pictures\\logo_icon_64.png";
            setIconImage(new ImageIcon(iconPath).getImage());
        } catch (Exception e) {
            System.out.println("لم يتم العثور على الأيقونة!");
        }

        setSize(1550, 941);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.setBackground(new Color(245, 255, 250));
        panel.setBounds(10, 10, 1516, 814);
        panel.setLayout(null);
        getContentPane().add(panel);

        lblTitle = new JLabel("إدخال معلومات الحجز ليوم " + day);
        lblTitle.setBounds(635, 7, 245, 44);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitle);

        JLabel lblName = new JLabel("الاسم:");
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblName.setBounds(1409, 82, 104, 32);
        panel.add(lblName);

        nameField = new JTextField();
        nameField.setBackground(new Color(192, 192, 192));
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        nameField.setBounds(1202, 82, 197, 32);
        panel.add(nameField);

        JLabel lblPhone = new JLabel("رقم الهاتف:");
        lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPhone.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhone.setBounds(1409, 137, 104, 31);
        panel.add(lblPhone);

        phoneField = new JTextField();
        phoneField.setBackground(new Color(192, 192, 192));
        phoneField.setHorizontalAlignment(SwingConstants.CENTER);
        phoneField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        phoneField.setBounds(1202, 136, 197, 32);
        panel.add(phoneField);

        comboBox = new JComboBox<>(new DefaultComboBoxModel<>(new String[] { "?", "زواج", "مهر", "خطوبة", "تخرج", "موتمر", "حنه" }));
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        comboBox.setToolTipText("");
        comboBox.setBounds(890, 82, 156, 32);
        panel.add(comboBox);

        btnFinalBooking = new JButton("حجز نهائي");
        btnFinalBooking.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnFinalBooking.setForeground(new Color(255, 255, 255));
        btnFinalBooking.setBackground(new Color(128, 0, 0));
        btnFinalBooking.setBounds(10, 23, 280, 37);
        panel.add(btnFinalBooking);

        btnTempBooking = new JButton("حجز مؤقت (24 ساعة)");
        btnTempBooking.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnTempBooking.setBackground(new Color(143, 188, 143));
        btnTempBooking.setBounds(10, 69, 280, 37);
        panel.add(btnTempBooking);

        btnCancelBooking = new JButton("إلغاء الحجز");
        btnCancelBooking.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnCancelBooking.setForeground(new Color(255, 255, 255));
        btnCancelBooking.setBackground(new Color(65, 105, 225));
        btnCancelBooking.setBounds(10, 163, 280, 37);
        panel.add(btnCancelBooking);

        // إضافة أزرار النوافذ المنفصلة للفترات
        btnMorning = new JButton("نافذة صباحية");
        btnMorning.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnMorning.setBackground(new Color(218, 165, 32));
        btnMorning.setBounds(10, 210, 130, 32);
        panel.add(btnMorning);

        btnEvening = new JButton("نافذة مسائية");
        btnEvening.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnEvening.setForeground(Color.WHITE);
        btnEvening.setBackground(new Color(72, 61, 139));
        btnEvening.setBounds(160, 210, 130, 32);
        panel.add(btnEvening);

        JLabel lblmnasba = new JLabel("نوع المناسبة:");
        lblmnasba.setFont(new Font("Dialog", Font.BOLD, 15));
        lblmnasba.setHorizontalAlignment(SwingConstants.CENTER);
        lblmnasba.setBounds(1051, 82, 96, 32);
        panel.add(lblmnasba);

        JLabel lbltime = new JLabel("وقت المناسبة:");
        lbltime.setHorizontalAlignment(SwingConstants.CENTER);
        lbltime.setFont(new Font("Dialog", Font.BOLD, 15));
        lbltime.setBounds(766, 82, 104, 32);
        panel.add(lbltime);

        comtime = new JComboBox<>(new DefaultComboBoxModel<>(new String[] { "?", "صباحا", "مساء" }));
        comtime.setToolTipText("");
        comtime.setFont(new Font("Tahoma", Font.PLAIN, 16));
        comtime.setBounds(601, 81, 162, 32);
        panel.add(comtime);

        JLabel lblnumber = new JLabel("عدد المدعويين:");
        lblnumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblnumber.setHorizontalAlignment(SwingConstants.CENTER);
        lblnumber.setBounds(1408, 189, 121, 25);
        panel.add(lblnumber);

        numberField = new JTextField();
        numberField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        numberField.setHorizontalAlignment(SwingConstants.CENTER);
        numberField.setBackground(new Color(192, 192, 192));
        numberField.setBounds(1202, 188, 197, 32);
        panel.add(numberField);
        numberField.setColumns(10);

        comphont = new JComboBox<>(new DefaultComboBoxModel<>(new String[] { "؟", "نعم", "لا" }));
        comphont.setToolTipText("");
        comphont.setFont(new Font("Tahoma", Font.PLAIN, 16));
        comphont.setBounds(601, 136, 162, 32);
        panel.add(comphont);

        genderCombo = new JComboBox<>(new DefaultComboBoxModel<>(new String[] { "؟", "مختلط", "اناث", "ذكور" }));
        genderCombo.setToolTipText("");
        genderCombo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        genderCombo.setBounds(890, 137, 156, 32);
        panel.add(genderCombo);

        JLabel lblGender = new JLabel("نوع الجنس:");
        lblGender.setHorizontalAlignment(SwingConstants.CENTER);
        lblGender.setFont(new Font("Dialog", Font.BOLD, 15));
        lblGender.setBounds(1051, 137, 96, 32);
        panel.add(lblGender);

        JLabel lblPhonePolicy = new JLabel("سحب الهواتف:");
        lblPhonePolicy.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhonePolicy.setFont(new Font("Dialog", Font.BOLD, 15));
        lblPhonePolicy.setBounds(766, 137, 104, 32);
        panel.add(lblPhonePolicy);

        depositAmountField = new JTextField();
        depositAmountField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        depositAmountField.setHorizontalAlignment(JTextField.CENTER);
        depositAmountField.setBackground(new Color(192, 192, 192));
        depositAmountField.setColumns(10);
        depositAmountField.setBounds(1236, 273, 162, 32);
        panel.add(depositAmountField);

        JLabel lblDepositAmount = new JLabel("المبلغ المدفوع مقدما:");
        lblDepositAmount.setHorizontalAlignment(SwingConstants.CENTER);
        lblDepositAmount.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDepositAmount.setBounds(1409, 271, 120, 32);
        panel.add(lblDepositAmount);

        totalAmountField = new JTextField();
        totalAmountField.setBackground(new Color(192, 192, 192));
        totalAmountField.setText("");
        totalAmountField.setHorizontalAlignment(JTextField.CENTER);
        totalAmountField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        totalAmountField.setEditable(true);
        totalAmountField.setBounds(1236, 325, 162, 32);
        panel.add(totalAmountField);

        otherCostsField = new JTextField();
        otherCostsField.setBackground(new Color(192, 192, 192));
        otherCostsField.setText("");
        otherCostsField.setHorizontalAlignment(JTextField.CENTER);
        otherCostsField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        otherCostsField.setEditable(true);
        otherCostsField.setBounds(1236, 384, 162, 32);
        panel.add(otherCostsField);

        JLabel lblOtherCosts = new JLabel("تكاليف اخرى:");
        lblOtherCosts.setHorizontalAlignment(SwingConstants.CENTER);
        lblOtherCosts.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblOtherCosts.setBounds(1408, 384, 120, 32);
        panel.add(lblOtherCosts);

        sweetsProviderCombo = new JComboBox<>(new DefaultComboBoxModel<>(new String[] { "?", "الزيتون", "سويت بوكس" }));
        sweetsProviderCombo.setToolTipText("");
        sweetsProviderCombo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        sweetsProviderCombo.setBounds(330, 82, 162, 32);
        panel.add(sweetsProviderCombo);

        tableClothColorCombo = new JComboBox<>(new DefaultComboBoxModel<>(new String[] { "?", "وردي", "زيتوني", "ابيض", "احمر", "فضي", "اسود" }));
        tableClothColorCombo.setToolTipText("");
        tableClothColorCombo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tableClothColorCombo.setBounds(890, 188, 156, 32);
        panel.add(tableClothColorCombo);

        JLabel lblTableClothColor = new JLabel("لون فرش الطاولات");
        lblTableClothColor.setHorizontalAlignment(SwingConstants.CENTER);
        lblTableClothColor.setFont(new Font("Dialog", Font.BOLD, 15));
        lblTableClothColor.setBounds(1051, 188, 96, 32);
        panel.add(lblTableClothColor);

        JLabel lblHallFloorFee = new JLabel("اجور ارضية القاعة:");
        lblHallFloorFee.setHorizontalAlignment(SwingConstants.CENTER);
        lblHallFloorFee.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblHallFloorFee.setBounds(1408, 325, 121, 32);
        panel.add(lblHallFloorFee);

        JLabel lblOvens = new JLabel("افران");
        lblOvens.setHorizontalAlignment(SwingConstants.CENTER);
        lblOvens.setFont(new Font("Dialog", Font.BOLD, 15));
        lblOvens.setBounds(495, 82, 96, 32);
        panel.add(lblOvens);

        koshaFeeField = new JTextField();
        koshaFeeField.setText("");
        koshaFeeField.setHorizontalAlignment(JTextField.CENTER);
        koshaFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        koshaFeeField.setEditable(true);
        koshaFeeField.setBackground(Color.LIGHT_GRAY);
        koshaFeeField.setBounds(1236, 435, 164, 32);
        panel.add(koshaFeeField);

        JLabel lblKoshaFee = new JLabel("اجور الكوشة:");
        lblKoshaFee.setHorizontalAlignment(SwingConstants.CENTER);
        lblKoshaFee.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblKoshaFee.setBounds(1409, 435, 120, 32);
        panel.add(lblKoshaFee);

        koshaNumberField = new JTextField();
        koshaNumberField.setText("");
        koshaNumberField.setHorizontalAlignment(JTextField.CENTER);
        koshaNumberField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        koshaNumberField.setEditable(true);
        koshaNumberField.setBackground(Color.LIGHT_GRAY);
        koshaNumberField.setBounds(1236, 487, 163, 32);
        panel.add(koshaNumberField);

        JLabel lblKoshaNumber = new JLabel("رقم الكوشة:");
        lblKoshaNumber.setHorizontalAlignment(SwingConstants.CENTER);
        lblKoshaNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblKoshaNumber.setBounds(1409, 487, 120, 32);
        panel.add(lblKoshaNumber);

        tablesFlowersFeeField = new JTextField();
        tablesFlowersFeeField.setText("");
        tablesFlowersFeeField.setHorizontalAlignment(JTextField.CENTER);
        tablesFlowersFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        tablesFlowersFeeField.setEditable(true);
        tablesFlowersFeeField.setBackground(Color.LIGHT_GRAY);
        tablesFlowersFeeField.setBounds(927, 275, 168, 32);
        panel.add(tablesFlowersFeeField);

        JLabel lblTablesFlowersFee = new JLabel("اجور ورد طاولات:");
        lblTablesFlowersFee.setHorizontalAlignment(SwingConstants.CENTER);
        lblTablesFlowersFee.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblTablesFlowersFee.setBounds(1111, 273, 114, 32);
        panel.add(lblTablesFlowersFee);

        foamCakeMoldFeeField = new JTextField();
        foamCakeMoldFeeField.setText("");
        foamCakeMoldFeeField.setHorizontalAlignment(JTextField.CENTER);
        foamCakeMoldFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        foamCakeMoldFeeField.setEditable(true);
        foamCakeMoldFeeField.setBackground(Color.LIGHT_GRAY);
        foamCakeMoldFeeField.setBounds(927, 327, 168, 32);
        panel.add(foamCakeMoldFeeField);

        JLabel lblFoamCakeMoldFee = new JLabel("اجور قالب كيك فلين:");
        lblFoamCakeMoldFee.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoamCakeMoldFee.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblFoamCakeMoldFee.setBounds(1111, 327, 114, 32);
        panel.add(lblFoamCakeMoldFee);

        sparklersFeeField = new JTextField();
        sparklersFeeField.setText("");
        sparklersFeeField.setHorizontalAlignment(JTextField.CENTER);
        sparklersFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        sparklersFeeField.setEditable(true);
        sparklersFeeField.setBackground(Color.LIGHT_GRAY);
        sparklersFeeField.setBounds(927, 386, 168, 32);
        panel.add(sparklersFeeField);

        JLabel lblSparklersFee = new JLabel("اجور الشعالات");
        lblSparklersFee.setHorizontalAlignment(SwingConstants.CENTER);
        lblSparklersFee.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblSparklersFee.setBounds(1111, 386, 114, 32);
        panel.add(lblSparklersFee);

        shamBandFeeField = new JTextField();
        shamBandFeeField.setText("");
        shamBandFeeField.setHorizontalAlignment(JTextField.CENTER);
        shamBandFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        shamBandFeeField.setEditable(true);
        shamBandFeeField.setBackground(Color.LIGHT_GRAY);
        shamBandFeeField.setBounds(927, 435, 169, 32);
        panel.add(shamBandFeeField);

        JLabel lblShamBandFee = new JLabel("اجور الفرقة الشامية:");
        lblShamBandFee.setHorizontalAlignment(SwingConstants.CENTER);
        lblShamBandFee.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblShamBandFee.setBounds(1111, 435, 115, 32);
        panel.add(lblShamBandFee);

        maharField = new JTextField();
        maharField.setText("");
        maharField.setHorizontalAlignment(JTextField.CENTER);
        maharField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        maharField.setEditable(true);
        maharField.setBackground(Color.LIGHT_GRAY);
        maharField.setBounds(927, 487, 169, 32);
        panel.add(maharField);

        JLabel lblmahar = new JLabel("اجور طاولة مهر:");
        lblmahar.setHorizontalAlignment(SwingConstants.CENTER);
        lblmahar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblmahar.setBounds(1111, 487, 115, 32);
        panel.add(lblmahar);

        djNameCombo = new JComboBox<>(new DefaultComboBoxModel<>(new String[] { "?", "محمود", "مصطفى", "ايه" }));
        djNameCombo.setToolTipText("");
        djNameCombo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        djNameCombo.setBounds(601, 188, 162, 32);
        panel.add(djNameCombo);

        JLabel lblDJ = new JLabel("D.J");
        lblDJ.setHorizontalAlignment(SwingConstants.CENTER);
        lblDJ.setFont(new Font("Dialog", Font.BOLD, 15));
        lblDJ.setBounds(766, 189, 104, 32);
        panel.add(lblDJ);

        djFeeField = new JTextField();
        djFeeField.setText("");
        djFeeField.setHorizontalAlignment(JTextField.CENTER);
        djFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        djFeeField.setEditable(true);
        djFeeField.setBackground(Color.LIGHT_GRAY);
        djFeeField.setBounds(619, 273, 168, 32);
        panel.add(djFeeField);

        JLabel lblDJFee = new JLabel("اجور الDJ:");
        lblDJFee.setHorizontalAlignment(SwingConstants.CENTER);
        lblDJFee.setFont(new Font("Dialog", Font.BOLD, 15));
        lblDJFee.setBounds(797, 273, 96, 32);
        panel.add(lblDJFee);

        bubblesField = new JTextField();
        bubblesField.setText("");
        bubblesField.setHorizontalAlignment(JTextField.CENTER);
        bubblesField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        bubblesField.setEditable(true);
        bubblesField.setBackground(Color.LIGHT_GRAY);
        bubblesField.setBounds(619, 325, 168, 32);
        panel.add(bubblesField);

        JLabel lblBubbles = new JLabel("الفقاعات:");
        lblBubbles.setHorizontalAlignment(SwingConstants.CENTER);
        lblBubbles.setFont(new Font("Dialog", Font.BOLD, 15));
        lblBubbles.setBounds(797, 325, 96, 32);
        panel.add(lblBubbles);

        laserLightingFeeField = new JTextField();
        laserLightingFeeField.setText("");
        laserLightingFeeField.setHorizontalAlignment(JTextField.CENTER);
        laserLightingFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        laserLightingFeeField.setEditable(true);
        laserLightingFeeField.setBackground(Color.LIGHT_GRAY);
        laserLightingFeeField.setBounds(619, 384, 168, 32);
        panel.add(laserLightingFeeField);

        JLabel lblLaserLightingFee = new JLabel("أجور الانارة الليزرية:");
        lblLaserLightingFee.setHorizontalAlignment(SwingConstants.CENTER);
        lblLaserLightingFee.setFont(new Font("Dialog", Font.BOLD, 13));
        lblLaserLightingFee.setBounds(797, 385, 96, 32);
        panel.add(lblLaserLightingFee);

        smokeFeeField = new JTextField();
        smokeFeeField.setText("");
        smokeFeeField.setHorizontalAlignment(JTextField.CENTER);
        smokeFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        smokeFeeField.setEditable(true);
        smokeFeeField.setBackground(Color.LIGHT_GRAY);
        smokeFeeField.setBounds(619, 437, 168, 32);
        panel.add(smokeFeeField);

        JLabel lblSmokeFee = new JLabel("دخان:");
        lblSmokeFee.setHorizontalAlignment(SwingConstants.CENTER);
        lblSmokeFee.setFont(new Font("Dialog", Font.BOLD, 14));
        lblSmokeFee.setBounds(797, 437, 96, 32);
        panel.add(lblSmokeFee);

        mixerCraneCamerasFeeField = new JTextField();
        mixerCraneCamerasFeeField.setText("");
        mixerCraneCamerasFeeField.setHorizontalAlignment(JTextField.CENTER);
        mixerCraneCamerasFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        mixerCraneCamerasFeeField.setEditable(true);
        mixerCraneCamerasFeeField.setBackground(Color.LIGHT_GRAY);
        mixerCraneCamerasFeeField.setBounds(619, 487, 168, 32);
        panel.add(mixerCraneCamerasFeeField);

        JLabel lblMixerCraneCamerasFee = new JLabel("اجور-مكسر كرين كامرات:");
        lblMixerCraneCamerasFee.setHorizontalAlignment(SwingConstants.CENTER);
        lblMixerCraneCamerasFee.setFont(new Font("Dialog", Font.BOLD, 12));
        lblMixerCraneCamerasFee.setBounds(798, 488, 96, 32);
        panel.add(lblMixerCraneCamerasFee);

        extraLeftField = new JTextField(); // عدد الكامرات (يسار)
        extraLeftField.setText("");
        extraLeftField.setHorizontalAlignment(JTextField.CENTER);
        extraLeftField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        extraLeftField.setEditable(true);
        extraLeftField.setBackground(Color.LIGHT_GRAY);
        extraLeftField.setBounds(352, 273, 178, 32);
        panel.add(extraLeftField);

        JLabel lblSparklersCount = new JLabel("عدد الشعالات");
        lblSparklersCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblSparklersCount.setFont(new Font("Dialog", Font.BOLD, 15));
        lblSparklersCount.setBounds(495, 136, 96, 32);
        panel.add(lblSparklersCount);

        sparklersCountCombo = new JComboBox<>(new DefaultComboBoxModel<>(new String[] {
            "?", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"
        }));
        sparklersCountCombo.setToolTipText("");
        sparklersCountCombo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        sparklersCountCombo.setBounds(330, 136, 162, 32);
        panel.add(sparklersCountCombo);

        JLabel lblBreadOven = new JLabel("الصمون");
        lblBreadOven.setHorizontalAlignment(SwingConstants.CENTER);
        lblBreadOven.setFont(new Font("Dialog", Font.BOLD, 15));
        lblBreadOven.setBounds(495, 182, 96, 32);
        panel.add(lblBreadOven);

        breadOvenTypeCombo = new JComboBox<>(new DefaultComboBoxModel<>(new String[] { "?", "حجري", "كهربائي" }));
        breadOvenTypeCombo.setToolTipText("");
        breadOvenTypeCombo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        breadOvenTypeCombo.setBounds(330, 182, 162, 32);
        panel.add(breadOvenTypeCombo);

        craneCheckbox = new JCheckBox("كرين");
        craneCheckbox.setFont(new Font("Tahoma", Font.BOLD, 12));
        craneCheckbox.setHorizontalAlignment(SwingConstants.CENTER);
        craneCheckbox.setBounds(352, 300, 92, 25);
        panel.add(craneCheckbox);

        JLabel lblCamerasCount = new JLabel("عدد الكامرات:");
        lblCamerasCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblCamerasCount.setFont(new Font("Dialog", Font.BOLD, 14));
        lblCamerasCount.setBounds(531, 273, 82, 32);
        panel.add(lblCamerasCount);

        totalField = new JTextField();
        totalField.setText("");
        totalField.setHorizontalAlignment(JTextField.CENTER);
        totalField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        totalField.setEditable(true);
        totalField.setBackground(Color.LIGHT_GRAY);
        totalField.setBounds(352, 325, 178, 32);
        panel.add(totalField);

        JLabel lblTotal = new JLabel("المجموع:");
        lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotal.setFont(new Font("Dialog", Font.BOLD, 15));
        lblTotal.setBounds(531, 325, 78, 32);
        panel.add(lblTotal);

        photoFeeField = new JTextField();
        photoFeeField.setText("");
        photoFeeField.setHorizontalAlignment(JTextField.CENTER);
        photoFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        photoFeeField.setEditable(true);
        photoFeeField.setBackground(Color.LIGHT_GRAY);
        photoFeeField.setBounds(352, 384, 178, 32);
        panel.add(photoFeeField);

        JLabel lblPhoto = new JLabel("الفوتو:");
        lblPhoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhoto.setFont(new Font("Dialog", Font.BOLD, 15));
        lblPhoto.setBounds(531, 384, 78, 32);
        panel.add(lblPhoto);

        photosCountField = new JTextField();
        photosCountField.setText("");
        photosCountField.setHorizontalAlignment(JTextField.CENTER);
        photosCountField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        photosCountField.setEditable(true);
        photosCountField.setBackground(Color.LIGHT_GRAY);
        photosCountField.setBounds(352, 437, 178, 32);
        panel.add(photosCountField);

        JLabel lblPhotosCount = new JLabel("عدد الصور:");
        lblPhotosCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhotosCount.setFont(new Font("Dialog", Font.BOLD, 15));
        lblPhotosCount.setBounds(531, 437, 78, 32);
        panel.add(lblPhotosCount);

        videoFeeField = new JTextField();
        videoFeeField.setText("");
        videoFeeField.setHorizontalAlignment(JTextField.CENTER);
        videoFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        videoFeeField.setEditable(true);
        videoFeeField.setBackground(Color.LIGHT_GRAY);
        videoFeeField.setBounds(352, 487, 178, 32);
        panel.add(videoFeeField);

        JLabel lblVideo = new JLabel("الفيديو:");
        lblVideo.setHorizontalAlignment(SwingConstants.CENTER);
        lblVideo.setFont(new Font("Dialog", Font.BOLD, 15));
        lblVideo.setBounds(531, 487, 78, 32);
        panel.add(lblVideo);

        dataShowFeeField = new JTextField();
        dataShowFeeField.setText("");
        dataShowFeeField.setHorizontalAlignment(JTextField.CENTER);
        dataShowFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        dataShowFeeField.setEditable(true);
        dataShowFeeField.setBackground(Color.LIGHT_GRAY);
        dataShowFeeField.setBounds(81, 273, 178, 32);
        panel.add(dataShowFeeField);

        JLabel lblDataShowFee = new JLabel("اجور DataShow:");
        lblDataShowFee.setHorizontalAlignment(SwingConstants.CENTER);
        lblDataShowFee.setFont(new Font("Dialog", Font.BOLD, 11));
        lblDataShowFee.setBounds(260, 273, 82, 32);
        panel.add(lblDataShowFee);

        JLabel lblDataShowCount = new JLabel("العدد:");
        lblDataShowCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblDataShowCount.setFont(new Font("Dialog", Font.BOLD, 15));
        lblDataShowCount.setBounds(260, 325, 78, 32);
        panel.add(lblDataShowCount);

        dataShowCountField = new JTextField();
        dataShowCountField.setText("");
        dataShowCountField.setHorizontalAlignment(JTextField.CENTER);
        dataShowCountField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        dataShowCountField.setEditable(true);
        dataShowCountField.setBackground(Color.LIGHT_GRAY);
        dataShowCountField.setBounds(81, 325, 178, 32);
        panel.add(dataShowCountField);

        studioFeeField = new JTextField();
        studioFeeField.setText("");
        studioFeeField.setHorizontalAlignment(JTextField.CENTER);
        studioFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        studioFeeField.setEditable(true);
        studioFeeField.setBackground(Color.LIGHT_GRAY);
        studioFeeField.setBounds(81, 384, 178, 32);
        panel.add(studioFeeField);

        JLabel lblStudio = new JLabel("الاستديو:");
        lblStudio.setHorizontalAlignment(SwingConstants.CENTER);
        lblStudio.setFont(new Font("Dialog", Font.BOLD, 15));
        lblStudio.setBounds(260, 384, 78, 32);
        panel.add(lblStudio);

        JLabel lblCakeMoldsCount = new JLabel("عدد قوالب الكيك:");
        lblCakeMoldsCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblCakeMoldsCount.setFont(new Font("Dialog", Font.BOLD, 15));
        lblCakeMoldsCount.setBounds(1425, 568, 104, 32);
        panel.add(lblCakeMoldsCount);

        cakeMoldsCountField = new JTextField();
        cakeMoldsCountField.setText("");
        cakeMoldsCountField.setHorizontalAlignment(JTextField.CENTER);
        cakeMoldsCountField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        cakeMoldsCountField.setEditable(true);
        cakeMoldsCountField.setBackground(Color.LIGHT_GRAY);
        cakeMoldsCountField.setBounds(1236, 568, 179, 32);
        panel.add(cakeMoldsCountField);

        cakeMoldNumberField = new JTextField();
        cakeMoldNumberField.setText("");
        cakeMoldNumberField.setHorizontalAlignment(JTextField.CENTER);
        cakeMoldNumberField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        cakeMoldNumberField.setEditable(true);
        cakeMoldNumberField.setBackground(Color.LIGHT_GRAY);
        cakeMoldNumberField.setBounds(1236, 626, 178, 32);
        panel.add(cakeMoldNumberField);

        JLabel lblCakeMoldNumber = new JLabel("رقم قالب الكيك:");
        lblCakeMoldNumber.setHorizontalAlignment(SwingConstants.CENTER);
        lblCakeMoldNumber.setFont(new Font("Dialog", Font.BOLD, 15));
        lblCakeMoldNumber.setBounds(1424, 626, 104, 32);
        panel.add(lblCakeMoldNumber);

        JLabel lblCakeAmount = new JLabel("مبلغ الكيك:");
        lblCakeAmount.setHorizontalAlignment(SwingConstants.CENTER);
        lblCakeAmount.setFont(new Font("Dialog", Font.BOLD, 15));
        lblCakeAmount.setBounds(1121, 568, 104, 32);
        panel.add(lblCakeAmount);

        cakeAmountField = new JTextField();
        cakeAmountField.setText("");
        cakeAmountField.setHorizontalAlignment(JTextField.CENTER);
        cakeAmountField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        cakeAmountField.setEditable(true);
        cakeAmountField.setBackground(Color.LIGHT_GRAY);
        cakeAmountField.setBounds(927, 568, 184, 32);
        panel.add(cakeAmountField);

        cakePiecesCountField = new JTextField();
        cakePiecesCountField.setText("");
        cakePiecesCountField.setHorizontalAlignment(JTextField.CENTER);
        cakePiecesCountField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        cakePiecesCountField.setEditable(true);
        cakePiecesCountField.setBackground(Color.LIGHT_GRAY);
        cakePiecesCountField.setBounds(927, 626, 183, 32);
        panel.add(cakePiecesCountField);

        JLabel lblCakePiecesCount = new JLabel("عدد قطع الكيك:");
        lblCakePiecesCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblCakePiecesCount.setFont(new Font("Dialog", Font.BOLD, 15));
        lblCakePiecesCount.setBounds(1120, 626, 104, 32);
        panel.add(lblCakePiecesCount);

        grandTotalField = new JTextField();
        grandTotalField.setText("");
        grandTotalField.setHorizontalAlignment(JTextField.CENTER);
        grandTotalField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        grandTotalField.setEditable(true);
        grandTotalField.setBackground(Color.LIGHT_GRAY);
        grandTotalField.setBounds(1258, 790, 184, 32);
        panel.add(grandTotalField);

        JLabel lblGrandTotal = new JLabel("المجموع:");
        lblGrandTotal.setForeground(Color.RED);
        lblGrandTotal.setHorizontalAlignment(SwingConstants.CENTER);
        lblGrandTotal.setFont(new Font("Dialog", Font.BOLD, 15));
        lblGrandTotal.setBounds(1431, 790, 82, 32);
        panel.add(lblGrandTotal);

        JLabel lblAmountDue = new JLabel("الواجب الدفع:");
        lblAmountDue.setForeground(Color.RED);
        lblAmountDue.setHorizontalAlignment(SwingConstants.CENTER);
        lblAmountDue.setFont(new Font("Dialog", Font.BOLD, 15));
        lblAmountDue.setBounds(1170, 790, 78, 32);
        panel.add(lblAmountDue);

        amountDueField = new JTextField();
        amountDueField.setText("");
        amountDueField.setHorizontalAlignment(JTextField.CENTER);
        amountDueField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        amountDueField.setEditable(true);
        amountDueField.setBackground(Color.LIGHT_GRAY);
        amountDueField.setBounds(979, 790, 189, 32);
        panel.add(amountDueField);

        cakeCuttingFeeField = new JTextField();
        cakeCuttingFeeField.setText("");
        cakeCuttingFeeField.setHorizontalAlignment(JTextField.CENTER);
        cakeCuttingFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        cakeCuttingFeeField.setEditable(true);
        cakeCuttingFeeField.setBackground(Color.LIGHT_GRAY);
        cakeCuttingFeeField.setBounds(927, 680, 184, 32);
        panel.add(cakeCuttingFeeField);

        cakeMoldNumber2Field = new JTextField();
        cakeMoldNumber2Field.setText("");
        cakeMoldNumber2Field.setHorizontalAlignment(JTextField.CENTER);
        cakeMoldNumber2Field.setFont(new Font("Tahoma", Font.PLAIN, 17));
        cakeMoldNumber2Field.setEditable(true);
        cakeMoldNumber2Field.setBackground(Color.LIGHT_GRAY);
        cakeMoldNumber2Field.setBounds(1236, 680, 179, 32);
        panel.add(cakeMoldNumber2Field);

        JLabel lblCakeCuttingFee = new JLabel("اجور تقطيع الكيك:");
        lblCakeCuttingFee.setHorizontalAlignment(SwingConstants.CENTER);
        lblCakeCuttingFee.setFont(new Font("Dialog", Font.BOLD, 15));
        lblCakeCuttingFee.setBounds(1425, 680, 104, 32);
        panel.add(lblCakeCuttingFee);

        JLabel lblCakeMoldNumber2 = new JLabel("رقم قالب الكيك:");
        lblCakeMoldNumber2.setHorizontalAlignment(SwingConstants.CENTER);
        lblCakeMoldNumber2.setFont(new Font("Dialog", Font.BOLD, 15));
        lblCakeMoldNumber2.setBounds(1115, 680, 104, 32);
        panel.add(lblCakeMoldNumber2);

        childPlatesCountField = new JTextField();
        childPlatesCountField.setText("");
        childPlatesCountField.setHorizontalAlignment(JTextField.CENTER);
        childPlatesCountField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        childPlatesCountField.setEditable(true);
        childPlatesCountField.setBackground(Color.LIGHT_GRAY);
        childPlatesCountField.setBounds(416, 690, 114, 32);
        panel.add(childPlatesCountField);

        childPlatePriceField = new JTextField();
        childPlatePriceField.setText("");
        childPlatePriceField.setHorizontalAlignment(JTextField.CENTER);
        childPlatePriceField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        childPlatePriceField.setEditable(true);
        childPlatePriceField.setBackground(Color.LIGHT_GRAY);
        childPlatePriceField.setBounds(619, 690, 168, 32);
        panel.add(childPlatePriceField);

        plateWithDrinks2PriceField = new JTextField();
        plateWithDrinks2PriceField.setText("");
        plateWithDrinks2PriceField.setHorizontalAlignment(JTextField.CENTER);
        plateWithDrinks2PriceField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        plateWithDrinks2PriceField.setEditable(true);
        plateWithDrinks2PriceField.setBackground(Color.LIGHT_GRAY);
        plateWithDrinks2PriceField.setBounds(10, 709, 202, 32);
        panel.add(plateWithDrinks2PriceField);

        JLabel lblDinner = new JLabel("العشاء");
        lblDinner.setHorizontalAlignment(SwingConstants.CENTER);
        lblDinner.setFont(new Font("Dialog", Font.BOLD, 15));
        lblDinner.setBounds(62, 419, 197, 32);
        panel.add(lblDinner);

        JLabel lblPlateWithDrinks2Price = new JLabel("سعر الطبق مع علبة مشروبات2:");
        lblPlateWithDrinks2Price.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlateWithDrinks2Price.setFont(new Font("Dialog", Font.BOLD, 15));
        lblPlateWithDrinks2Price.setBounds(222, 709, 162, 32);
        panel.add(lblPlateWithDrinks2Price);

        JLabel lblChildPlatePrice = new JLabel("سعر طبق الطفل:");
        lblChildPlatePrice.setHorizontalAlignment(SwingConstants.CENTER);
        lblChildPlatePrice.setFont(new Font("Dialog", Font.BOLD, 14));
        lblChildPlatePrice.setBounds(797, 690, 96, 32);
        panel.add(lblChildPlatePrice);

        JLabel lblChildPlatesCount = new JLabel("العدد:");
        lblChildPlatesCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblChildPlatesCount.setFont(new Font("Dialog", Font.BOLD, 12));
        lblChildPlatesCount.setBounds(541, 691, 72, 32);
        panel.add(lblChildPlatesCount);

        dinnerOption3Combo = new JComboBox<>();
        dinnerOption3Combo.setToolTipText("");
        dinnerOption3Combo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        dinnerOption3Combo.setBounds(81, 548, 178, 32);
        panel.add(dinnerOption3Combo);

        dinnerOption4Combo = new JComboBox<>();
        dinnerOption4Combo.setToolTipText("");
        dinnerOption4Combo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        dinnerOption4Combo.setBounds(81, 600, 178, 32);
        panel.add(dinnerOption4Combo);

        dinnerOption2Combo = new JComboBox<>();
        dinnerOption2Combo.setToolTipText("");
        dinnerOption2Combo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        dinnerOption2Combo.setBounds(81, 499, 178, 32);
        panel.add(dinnerOption2Combo);

        dinnerOption1Combo = new JComboBox<>();
        dinnerOption1Combo.setToolTipText("");
        dinnerOption1Combo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        dinnerOption1Combo.setBounds(81, 448, 178, 32);
        panel.add(dinnerOption1Combo);

        dinnerOption5Combo = new JComboBox<>();
        dinnerOption5Combo.setToolTipText("");
        dinnerOption5Combo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        dinnerOption5Combo.setBounds(81, 654, 178, 32);
        panel.add(dinnerOption5Combo);

        appetizersOption1Combo = new JComboBox<>();
        appetizersOption1Combo.setToolTipText("");
        appetizersOption1Combo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        appetizersOption1Combo.setBounds(390, 572, 178, 32);
        panel.add(appetizersOption1Combo);

        appetizersOption2Combo = new JComboBox<>();
        appetizersOption2Combo.setToolTipText("");
        appetizersOption2Combo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        appetizersOption2Combo.setBounds(390, 636, 178, 32);
        panel.add(appetizersOption2Combo);

        JLabel lblAppetizers = new JLabel("المقبلات");
        lblAppetizers.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAppetizers.setHorizontalAlignment(SwingConstants.CENTER);
        lblAppetizers.setBounds(386, 548, 182, 24);
        panel.add(lblAppetizers);

        appetizersPlatePriceField = new JTextField();
        appetizersPlatePriceField.setText("");
        appetizersPlatePriceField.setHorizontalAlignment(JTextField.CENTER);
        appetizersPlatePriceField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        appetizersPlatePriceField.setEditable(true);
        appetizersPlatePriceField.setBackground(Color.LIGHT_GRAY);
        appetizersPlatePriceField.setBounds(10, 767, 202, 32);
        panel.add(appetizersPlatePriceField);

        JLabel lblAppetizersPlatePrice = new JLabel("سعر طبق المقبلات:");
        lblAppetizersPlatePrice.setHorizontalAlignment(SwingConstants.CENTER);
        lblAppetizersPlatePrice.setFont(new Font("Dialog", Font.BOLD, 15));
        lblAppetizersPlatePrice.setBounds(222, 767, 141, 32);
        panel.add(lblAppetizersPlatePrice);

        JLabel lblKidsPlates = new JLabel("اطباق الاطفال");
        lblKidsPlates.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblKidsPlates.setHorizontalAlignment(SwingConstants.CENTER);
        lblKidsPlates.setBounds(683, 548, 104, 25);
        panel.add(lblKidsPlates);

        kidsPlateOption1Combo = new JComboBox<>();
        kidsPlateOption1Combo.setToolTipText("");
        kidsPlateOption1Combo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        kidsPlateOption1Combo.setBounds(652, 572, 178, 32);
        panel.add(kidsPlateOption1Combo);

        kidsPlateOption2Combo = new JComboBox<>();
        kidsPlateOption2Combo.setToolTipText("");
        kidsPlateOption2Combo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        kidsPlateOption2Combo.setBounds(652, 636, 178, 32);
        panel.add(kidsPlateOption2Combo);

        JLabel lblAmountDueReceived = new JLabel("الواصل من الواجب الدفع:");
        lblAmountDueReceived.setForeground(Color.RED);
        lblAmountDueReceived.setHorizontalAlignment(SwingConstants.CENTER);
        lblAmountDueReceived.setFont(new Font("Dialog", Font.BOLD, 13));
        lblAmountDueReceived.setBounds(864, 791, 113, 32);
        panel.add(lblAmountDueReceived);

        amountDueReceivedField = new JTextField();
        amountDueReceivedField.setText("");
        amountDueReceivedField.setHorizontalAlignment(JTextField.CENTER);
        amountDueReceivedField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        amountDueReceivedField.setEditable(true);
        amountDueReceivedField.setBackground(Color.LIGHT_GRAY);
        amountDueReceivedField.setBounds(671, 790, 183, 32);
        panel.add(amountDueReceivedField);

        remainingAmountField = new JTextField();
        remainingAmountField.setText("");
        remainingAmountField.setHorizontalAlignment(JTextField.CENTER);
        remainingAmountField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        remainingAmountField.setEditable(true);
        remainingAmountField.setBackground(Color.LIGHT_GRAY);
        remainingAmountField.setBounds(426, 790, 183, 32);
        panel.add(remainingAmountField);

        JLabel lblRemaining = new JLabel("المتبقي:");
        lblRemaining.setForeground(Color.RED);
        lblRemaining.setHorizontalAlignment(SwingConstants.CENTER);
        lblRemaining.setFont(new Font("Dialog", Font.BOLD, 15));
        lblRemaining.setBounds(609, 790, 64, 32);
        panel.add(lblRemaining);

        JLabel lblCakeSection = new JLabel("الكيك");
        lblCakeSection.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblCakeSection.setHorizontalAlignment(SwingConstants.CENTER);
        lblCakeSection.setBounds(927, 536, 588, 24);
        panel.add(lblCakeSection);

        // زر الطباعة
        btnprint = new JButton("طباعه");
        btnprint.setForeground(new Color(255, 255, 255));
        btnprint.setBackground(new Color(47, 79, 79));
        btnprint.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnprint.setBounds(10, 116, 280, 37);
        panel.add(btnprint);
        btnprint.addActionListener(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String mahar = maharField.getText().trim();
            if (name.isEmpty() || phone.isEmpty() || mahar.isEmpty()) {
                JOptionPane.showMessageDialog(this, "يرجى إدخال جميع المعلومات!", "Mohammed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            long price = parseMoney(grandTotalField.getText());
            if (price <= 0) price = 300;
            InvoiceDialog invoice = new InvoiceDialog(this, hallName, day, month, year, name, phone, mahar, (int)price);
            invoice.setVisible(true);
        });

        // ===================== Listeners للحفظ =====================

        btnFinalBooking.addActionListener(e -> {
            String name  = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String mahar = maharField.getText().trim();

            if (name.isEmpty() || phone.isEmpty() || mahar.isEmpty()) {
                JOptionPane.showMessageDialog(this, "يرجى إدخال جميع المعلومات!", "خطأ", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String eventType            = String.valueOf(comboBox.getSelectedItem()).trim();
            String eventTime            = String.valueOf(comtime.getSelectedItem()).trim();
            String phonePolicy          = String.valueOf(comphont.getSelectedItem()).trim();
            String genderType           = String.valueOf(genderCombo.getSelectedItem()).trim();
            String sweetsProvider       = String.valueOf(sweetsProviderCombo.getSelectedItem()).trim();
            String tableClothColor      = String.valueOf(tableClothColorCombo.getSelectedItem()).trim();
            String djName               = String.valueOf(djNameCombo.getSelectedItem()).trim();

            String guestsCount          = numberField.getText().trim();
            String depositAmount        = depositAmountField.getText().trim();
            String totalAmount          = totalAmountField.getText().trim();
            String otherCosts           = otherCostsField.getText().trim();
            String koshaFee             = koshaFeeField.getText().trim();
            String koshaNumber          = koshaNumberField.getText().trim();
            String tablesFlowersFee     = tablesFlowersFeeField.getText().trim();

            String foamCakeMoldFee      = foamCakeMoldFeeField.getText().trim();
            String sparklersFee         = sparklersFeeField.getText().trim();
            String shamBandFee          = shamBandFeeField.getText().trim();
            String djFee                = djFeeField.getText().trim();
            String bubblesFee           = bubblesField.getText().trim();
            String laserLightingFee     = laserLightingFeeField.getText().trim();
            String smokeFee             = smokeFeeField.getText().trim();
            String mixerCraneCamerasFee = mixerCraneCamerasFeeField.getText().trim();

            String camerasCount         = extraLeftField.getText().trim();
            String camerasTotal         = totalField.getText().trim();
            String photoFee             = photoFeeField.getText().trim();
            String photosCount          = photosCountField.getText().trim();
            String videoFee             = videoFeeField.getText().trim();
            String datashowFee          = dataShowFeeField.getText().trim();
            String datashowCount        = dataShowCountField.getText().trim();
            String studioFee            = studioFeeField.getText().trim();

            String cakeMoldsCount       = cakeMoldsCountField.getText().trim();
            String cakeMoldNumber       = cakeMoldNumberField.getText().trim();
            String cakeAmount           = cakeAmountField.getText().trim();
            String cakePiecesCount      = cakePiecesCountField.getText().trim();
            String cakeCuttingFee       = cakeCuttingFeeField.getText().trim();
            String cakeMoldNumber2      = cakeMoldNumber2Field.getText().trim();

            String childPlatesCount     = childPlatesCountField.getText().trim();
            String childPlatePrice      = childPlatePriceField.getText().trim();
            String plateWithDrinks2Price= plateWithDrinks2PriceField.getText().trim();
            String appetizersPlatePrice = appetizersPlatePriceField.getText().trim();

            String dinnerOption1        = String.valueOf(dinnerOption1Combo.getSelectedItem()).trim();
            String dinnerOption2        = String.valueOf(dinnerOption2Combo.getSelectedItem()).trim();
            String dinnerOption3        = String.valueOf(dinnerOption3Combo.getSelectedItem()).trim();
            String dinnerOption4        = String.valueOf(dinnerOption4Combo.getSelectedItem()).trim();
            String dinnerOption5        = String.valueOf(dinnerOption5Combo.getSelectedItem()).trim();

            String appetizersOption1    = String.valueOf(appetizersOption1Combo.getSelectedItem()).trim();
            String appetizersOption2    = String.valueOf(appetizersOption2Combo.getSelectedItem()).trim();

            String kidsPlateOption1     = String.valueOf(kidsPlateOption1Combo.getSelectedItem()).trim();
            String kidsPlateOption2     = String.valueOf(kidsPlateOption2Combo.getSelectedItem()).trim();

            String sparklersCountVal    = String.valueOf(sparklersCountCombo.getSelectedItem()).trim();
            String breadOvenType        = String.valueOf(breadOvenTypeCombo.getSelectedItem()).trim();
            String crane                = craneCheckbox.isSelected() ? "نعم" : "لا";

            String grandTotal           = grandTotalField.getText().trim();
            String amountDue            = amountDueField.getText().trim();
            String amountDueReceived    = amountDueReceivedField.getText().trim();
            String remainingAmount      = remainingAmountField.getText().trim();

            deleteTempBookings(hallName, day, month, year);

            // نستخدم الـ overload بدون bookingType
            if (saveBooking1(
                    hallName, day, month, year, name, phone, mahar,
                    eventType, eventTime, phonePolicy, genderType, sweetsProvider, tableClothColor, djName,
                    guestsCount, depositAmount, totalAmount, otherCosts, koshaFee, koshaNumber, tablesFlowersFee,
                    foamCakeMoldFee, sparklersFee, shamBandFee, djFee, bubblesFee, laserLightingFee, smokeFee, mixerCraneCamerasFee,
                    camerasCount, camerasTotal, photoFee, photosCount, videoFee, datashowFee, datashowCount, studioFee,
                    cakeMoldsCount, cakeMoldNumber, cakeAmount, cakePiecesCount, cakeCuttingFee, cakeMoldNumber2,
                    childPlatesCount, childPlatePrice, plateWithDrinks2Price, appetizersPlatePrice,
                    dinnerOption1, dinnerOption2, dinnerOption3, dinnerOption4, dinnerOption5,
                    appetizersOption1, appetizersOption2, kidsPlateOption1, kidsPlateOption2,
                    sparklersCountVal, breadOvenType, crane,
                    grandTotal, amountDue, amountDueReceived, remainingAmount
            )) {
                if (bookingStateCallback != null) bookingStateCallback.accept("final");
                JOptionPane.showMessageDialog(this, "تم حفظ الحجز النهائي بنجاح.", "نجاح", JOptionPane.INFORMATION_MESSAGE);
                loadBookingAndFillUI(hallName, day, month, year);
                scheduleRecalc();
            }
        });

        btnTempBooking.addActionListener(e -> {
            String name  = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String mahar = maharField.getText().trim();

            if (name.isEmpty() || phone.isEmpty() || mahar.isEmpty()) {
                JOptionPane.showMessageDialog(this, "يرجى إدخال جميع المعلومات!", "خطأ", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String eventType            = String.valueOf(comboBox.getSelectedItem()).trim();
            String eventTime            = String.valueOf(comtime.getSelectedItem()).trim();
            String phonePolicy          = String.valueOf(comphont.getSelectedItem()).trim();
            String genderType           = String.valueOf(genderCombo.getSelectedItem()).trim();
            String sweetsProvider       = String.valueOf(sweetsProviderCombo.getSelectedItem()).trim();
            String tableClothColor      = String.valueOf(tableClothColorCombo.getSelectedItem()).trim();
            String djName               = String.valueOf(djNameCombo.getSelectedItem()).trim();

            String guestsCount          = numberField.getText().trim();
            String depositAmount        = depositAmountField.getText().trim();
            String totalAmount          = totalAmountField.getText().trim();
            String otherCosts           = otherCostsField.getText().trim();
            String koshaFee             = koshaFeeField.getText().trim();
            String koshaNumber          = koshaNumberField.getText().trim();
            String tablesFlowersFee     = tablesFlowersFeeField.getText().trim();

            String foamCakeMoldFee      = foamCakeMoldFeeField.getText().trim();
            String sparklersFee         = sparklersFeeField.getText().trim();
            String shamBandFee          = shamBandFeeField.getText().trim();
            String djFee                = djFeeField.getText().trim();
            String bubblesFee           = bubblesField.getText().trim();
            String laserLightingFee     = laserLightingFeeField.getText().trim();
            String smokeFee             = smokeFeeField.getText().trim();
            String mixerCraneCamerasFee = mixerCraneCamerasFeeField.getText().trim();

            String camerasCount         = extraLeftField.getText().trim();
            String camerasTotal         = totalField.getText().trim();
            String photoFee             = photoFeeField.getText().trim();
            String photosCount          = photosCountField.getText().trim();
            String videoFee             = videoFeeField.getText().trim();
            String datashowFee          = dataShowFeeField.getText().trim();
            String datashowCount        = dataShowCountField.getText().trim();
            String studioFee            = studioFeeField.getText().trim();

            String cakeMoldsCount       = cakeMoldsCountField.getText().trim();
            String cakeMoldNumber       = cakeMoldNumberField.getText().trim();
            String cakeAmount           = cakeAmountField.getText().trim();
            String cakePiecesCount      = cakePiecesCountField.getText().trim();
            String cakeCuttingFee       = cakeCuttingFeeField.getText().trim();
            String cakeMoldNumber2      = cakeMoldNumber2Field.getText().trim();

            String childPlatesCount     = childPlatesCountField.getText().trim();
            String childPlatePrice      = childPlatePriceField.getText().trim();
            String plateWithDrinks2Price= plateWithDrinks2PriceField.getText().trim();
            String appetizersPlatePrice = appetizersPlatePriceField.getText().trim();

            String dinnerOption1        = String.valueOf(dinnerOption1Combo.getSelectedItem()).trim();
            String dinnerOption2        = String.valueOf(dinnerOption2Combo.getSelectedItem()).trim();
            String dinnerOption3        = String.valueOf(dinnerOption3Combo.getSelectedItem()).trim();
            String dinnerOption4        = String.valueOf(dinnerOption4Combo.getSelectedItem()).trim();
            String dinnerOption5        = String.valueOf(dinnerOption5Combo.getSelectedItem()).trim();

            String appetizersOption1    = String.valueOf(appetizersOption1Combo.getSelectedItem()).trim();
            String appetizersOption2    = String.valueOf(appetizersOption2Combo.getSelectedItem()).trim();

            String kidsPlateOption1     = String.valueOf(kidsPlateOption1Combo.getSelectedItem()).trim();
            String kidsPlateOption2     = String.valueOf(kidsPlateOption2Combo.getSelectedItem()).trim();

            String sparklersCountVal    = String.valueOf(sparklersCountCombo.getSelectedItem()).trim();
            String breadOvenType        = String.valueOf(breadOvenTypeCombo.getSelectedItem()).trim();
            String crane                = craneCheckbox.isSelected() ? "نعم" : "لا";

            String grandTotal           = grandTotalField.getText().trim();
            String amountDue            = amountDueField.getText().trim();
            String amountDueReceived    = amountDueReceivedField.getText().trim();
            String remainingAmount      = remainingAmountField.getText().trim();

            if (saveTempBooking1(
                    hallName, day, month, year, name, phone, mahar,
                    eventType, eventTime, phonePolicy, genderType, sweetsProvider, tableClothColor, djName,
                    guestsCount, depositAmount, totalAmount, otherCosts, koshaFee, koshaNumber, tablesFlowersFee,
                    foamCakeMoldFee, sparklersFee, shamBandFee, djFee, bubblesFee, laserLightingFee, smokeFee, mixerCraneCamerasFee,
                    camerasCount, camerasTotal, photoFee, photosCount, videoFee, datashowFee, datashowCount, studioFee,
                    cakeMoldsCount, cakeMoldNumber, cakeAmount, cakePiecesCount, cakeCuttingFee, cakeMoldNumber2,
                    childPlatesCount, childPlatePrice, plateWithDrinks2Price, appetizersPlatePrice,
                    dinnerOption1, dinnerOption2, dinnerOption3, dinnerOption4, dinnerOption5,
                    appetizersOption1, appetizersOption2, kidsPlateOption1, kidsPlateOption2,
                    sparklersCountVal, breadOvenType, crane,
                    grandTotal, amountDue, amountDueReceived, remainingAmount
            )) {
                if (bookingStateCallback != null) bookingStateCallback.accept("temp");
                JOptionPane.showMessageDialog(this, "تم الحجز المؤقت بنجاح!", "حجز", JOptionPane.INFORMATION_MESSAGE);
                loadBookingAndFillUI(hallName, day, month, year);
                scheduleRecalc();
            }
        });

        btnCancelBooking.addActionListener(e -> {
            if (deleteLatestBookingForDate(hallName, day, month, year)) {
                loadBookingAndFillUI(hallName, day, month, year);
                if (bookingStateCallback != null) bookingStateCallback.accept("cancel");
                JOptionPane.showMessageDialog(this, "تم إلغاء آخر حجز!", "إلغاء", JOptionPane.INFORMATION_MESSAGE);
                scheduleRecalc();
            }
        });

        // أزرار النوافذ المنفصلة للفترات
        btnMorning.addActionListener(e -> {
            hmx win = new hmx(day, month, year, hallName, bookingStateCallback);
            SwingUtilities.invokeLater(() -> {
                win.forceEventTime("صباحا");
                win.loadBookingAndFillUI(hallName, day, month, year);
                win.setVisible(true);
            });
        });

        btnEvening.addActionListener(e -> {
            hmx win = new hmx(day, month, year, hallName, bookingStateCallback);
            SwingUtilities.invokeLater(() -> {
                win.forceEventTime("مساء");
                win.loadBookingAndFillUI(hallName, day, month, year);
                win.setVisible(true);
            });
        });

        // ربط الجمع التلقائي لكل الحقول (مع استثناء الهاتف والمجموع نفسه)
        registerAmountFields();
        attachAmountScalingBehavior();
        setupAutoSum(panel);

        // ربط تحديثات المبالغ المشتقة (الواجب الدفع والمتبقي)
        hookDerivedCalculations();

        // تحميل البيانات عند فتح النافذة
        loadBookingAndFillUI(hallName, day, month, year);

        // بعد التحميل: أعِد حساب المجموع IQ والمبالغ المشتقة باستخدام الجدولة المؤجلة
        scheduleRecalc();
    }

    // فرض فترة معينة وقفل التغيير
    public void forceEventTime(String value) {
        forcedEventTime = value;
        if (comtime != null) {
            comtime.setSelectedItem(value);
            comtime.setEnabled(false); // منع التغيير
        }
        // إعادة تحميل البيانات لتلك الفترة فقط
        // يجب استدعاؤها بعد اكتمال البناء مع البيانات المناسبة
    }

    // ============================ دوال الجمع التلقائي (IQ) ============================

    private void setupAutoSum(Container root) {
        sumSources.clear();
        collectSumSources(root);
        for (JTextField tf : sumSources) {
            tf.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { 
                    if (!internalUpdate) scheduleRecalc(); 
                }
                @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { 
                    if (!internalUpdate) scheduleRecalc(); 
                }
                @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { 
                    if (!internalUpdate) scheduleRecalc(); 
                }
            });
        }
        scheduleRecalc();
    }

    private void collectSumSources(Container container) {
        for (Component child : container.getComponents()) {
            if (child instanceof JTextField) {
                JTextField tf = (JTextField) child;
                // استثناء رقم الهاتف وعدم جمع حقل المجموع نفسه والمبالغ المشتقة والمبلغ المستلم
                if (tf == phoneField) continue;
                if (tf == grandTotalField) continue;
                if (tf == amountDueField) continue;
                if (tf == remainingAmountField) continue;
                if (tf == amountDueReceivedField) continue; // المبالغ المستلمة ليست تكاليف
                sumSources.add(tf);
            }
            if (child instanceof Container) {
                collectSumSources((Container) child);
            }
        }
    }

    private void recomputeGrandTotal() {
        long total = 0L;
        for (JTextField tf : sumSources) {
            total += parseMoney(tf.getText());
        }
        if (grandTotalField != null) {
            String newTotalText = toIQ(total);
            if (!newTotalText.equals(grandTotalField.getText())) {
                internalUpdate = true;
                grandTotalField.setText(newTotalText); // "12345 IQ"
                internalUpdate = false;
            }
        }
    }

    // يربط Listeners لحقول المبالغ المشتقة
    private void hookDerivedCalculations() {
        javax.swing.event.DocumentListener dl = new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { 
                if (!internalUpdate) scheduleRecalc(); 
            }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { 
                if (!internalUpdate) scheduleRecalc(); 
            }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { 
                if (!internalUpdate) scheduleRecalc(); 
            }
        };
        JTextField[] drivers = { grandTotalField, depositAmountField, amountDueReceivedField };
        for (JTextField tf : drivers) {
            if (tf != null && tf.getDocument() != null) {
                tf.getDocument().addDocumentListener(dl);
            }
        }
    }

    // يحسب الواجب الدفع والمتبقي ويعرضهما بصيغة IQ
    private void computeDerivedTotals() {
        if (grandTotalField == null || amountDueField == null || remainingAmountField == null) return;

        long grandTotal = parseMoney(grandTotalField.getText());
        long deposit    = parseMoney(depositAmountField != null ? depositAmountField.getText() : "0");
        long due        = Math.max(0L, grandTotal - Math.max(0L, deposit));
        
        String newDueText = toIQ(due);
        if (!newDueText.equals(amountDueField.getText())) {
            internalUpdate = true;
            amountDueField.setText(newDueText);
            internalUpdate = false;
        }

        long received   = parseMoney(amountDueReceivedField != null ? amountDueReceivedField.getText() : "0");
        long remaining  = Math.max(0L, due - Math.max(0L, received));
        
        String newRemainingText = toIQ(remaining);
        if (!newRemainingText.equals(remainingAmountField.getText())) {
            internalUpdate = true;
            remainingAmountField.setText(newRemainingText);
            internalUpdate = false;
        }
    }

    // ============================ جلب العرض والتعبئة ============================

    private static class BookingDataFull {
        int id;
        String bookingType;

        String hallName; int day; int month; int year;
        String name; String phone; String mahar;

        String eventType, eventTime, phonePolicy, genderType, sweetsProvider, tableClothColor, djName;

        String guestsCount, depositAmount, totalAmount, otherCosts, koshaFee, koshaNumber, tablesFlowersFee;

        String foamCakeMoldFee, sparklersFee, shamBandFee, djFee, bubblesFee, laserLightingFee, smokeFee, mixerCraneCamerasFee;

        String camerasCount, camerasTotal, photoFee, photosCount, videoFee, datashowFee, datashowCount, studioFee;

        String cakeMoldsCount, cakeMoldNumber, cakeAmount, cakePiecesCount, cakeCuttingFee, cakeMoldNumber2;

        String childPlatesCount, childPlatePrice, plateWithDrinks2Price, appetizersPlatePrice;

        String dinnerOption1, dinnerOption2, dinnerOption3, dinnerOption4, dinnerOption5;

        String appetizersOption1, appetizersOption2, kidsPlateOption1, kidsPlateOption2;

        String sparklersCount, breadOvenType, crane;

        String grandTotal, amountDue, amountDueReceived, remainingAmount;

        java.sql.Timestamp createdAt;
    }

    private BookingDataFull getBookingFull(String hallName, int day, int month, int year) {
        String base = "SELECT * FROM bookings WHERE hall_name=? AND day=? AND month=? AND year=?";
        if (forcedEventTime != null) {
            base += " AND event_time=?";
        }
        base += " ORDER BY CASE booking_type WHEN 'final' THEN 0 ELSE 1 END, id DESC";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement st = conn.prepareStatement(base)) {
            st.setString(1, hallName);
            st.setInt(2, day);
            st.setInt(3, month);
            st.setInt(4, year);
            if (forcedEventTime != null) {
                st.setString(5, forcedEventTime);
            }
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String type = rs.getString("booking_type");
                    java.sql.Timestamp createdAt = rs.getTimestamp("created_at");
                    if ("final".equals(type)) {
                        return mapRow(rs);
                    } else if ("temp".equals(type)) {
                        long now = System.currentTimeMillis();
                        long t = createdAt != null ? createdAt.getTime() : now;
                        long diffHours = (now - t) / (1000 * 60 * 60);
                        if (diffHours < 24) return mapRow(rs);
                        // انتهى المؤقت: احذف السطر
                        deleteById(rs.getInt("id"));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private BookingDataFull mapRow(ResultSet rs) throws Exception {
        BookingDataFull b = new BookingDataFull();
        b.id = rs.getInt("id");
        b.bookingType = rs.getString("booking_type");
        b.hallName = rs.getString("hall_name");
        b.day = rs.getInt("day");
        b.month = rs.getInt("month");
        b.year = rs.getInt("year");
        b.name = rs.getString("name");
        b.phone = rs.getString("phone");
        b.mahar = rs.getString("mahar");

        b.eventType = rs.getString("event_type");
        b.eventTime = rs.getString("event_time");
        b.phonePolicy = rs.getString("phone_policy");
        b.genderType = rs.getString("gender_type");
        b.sweetsProvider = rs.getString("sweets_provider");
        b.tableClothColor = rs.getString("table_cloth_color");
        b.djName = rs.getString("dj_name");

        b.guestsCount = rs.getString("guests_count");
        b.depositAmount = rs.getString("deposit_amount");
        b.totalAmount = rs.getString("total_amount");
        b.otherCosts = rs.getString("other_costs");
        b.koshaFee = rs.getString("kosha_fee");
        b.koshaNumber = rs.getString("kosha_number");
        b.tablesFlowersFee = rs.getString("tables_flowers_fee");

        b.foamCakeMoldFee = rs.getString("foam_cake_mold_fee");
        b.sparklersFee = rs.getString("sparklers_fee");
        b.shamBandFee = rs.getString("sham_band_fee");
        b.djFee = rs.getString("dj_fee");
        b.bubblesFee = rs.getString("bubbles_fee");
        b.laserLightingFee = rs.getString("laser_lighting_fee");
        b.smokeFee = rs.getString("smoke_fee");
        b.mixerCraneCamerasFee = rs.getString("mixer_crane_cameras_fee");

        b.camerasCount = rs.getString("cameras_count");
        b.camerasTotal = rs.getString("cameras_total");
        b.photoFee = rs.getString("photo_fee");
        b.photosCount = rs.getString("photos_count");
        b.videoFee = rs.getString("video_fee");
        b.datashowFee = rs.getString("datashow_fee");
        b.datashowCount = rs.getString("datashow_count");
        b.studioFee = rs.getString("studio_fee");

        b.cakeMoldsCount = rs.getString("cake_molds_count");
        b.cakeMoldNumber = rs.getString("cake_mold_number");
        b.cakeAmount = rs.getString("cake_amount");
        b.cakePiecesCount = rs.getString("cake_pieces_count");
        b.cakeCuttingFee = rs.getString("cake_cutting_fee");
        b.cakeMoldNumber2 = rs.getString("cake_mold_number2");

        b.childPlatesCount = rs.getString("child_plates_count");
        b.childPlatePrice = rs.getString("child_plate_price");
        b.plateWithDrinks2Price = rs.getString("plate_with_drinks2_price");
        b.appetizersPlatePrice = rs.getString("appetizers_plate_price");

        b.dinnerOption1 = rs.getString("dinner_option1");
        b.dinnerOption2 = rs.getString("dinner_option2");
        b.dinnerOption3 = rs.getString("dinner_option3");
        b.dinnerOption4 = rs.getString("dinner_option4");
        b.dinnerOption5 = rs.getString("dinner_option5");

        b.appetizersOption1 = rs.getString("appetizers_option1");
        b.appetizersOption2 = rs.getString("appetizers_option2");
        b.kidsPlateOption1 = rs.getString("kids_plate_option1");
        b.kidsPlateOption2 = rs.getString("kids_plate_option2");

        b.sparklersCount = rs.getString("sparklers_count");
        b.breadOvenType = rs.getString("bread_oven_type");
        b.crane = rs.getString("crane");

        b.grandTotal = rs.getString("grand_total");
        b.amountDue = rs.getString("amount_due");
        b.amountDueReceived = rs.getString("amount_due_received");
        b.remainingAmount = rs.getString("remaining_amount");

        b.createdAt = rs.getTimestamp("created_at");
        return b;
    }

    private static String nz(String s) { return s == null ? "" : s; }
    private static void selectIfPresent(JComboBox<?> combo, String value) {
        if (combo == null || value == null) return;
        combo.setSelectedItem(value);
    }

    private void fillAllFieldsFrom(BookingDataFull b) {
        nameField.setText(nz(b.name));
        phoneField.setText(nz(b.phone));
        maharField.setText(nz(b.mahar));

        selectIfPresent(comboBox, b.eventType);
        selectIfPresent(comtime, b.eventTime);
        selectIfPresent(comphont, b.phonePolicy);
        selectIfPresent(genderCombo, b.genderType);
        selectIfPresent(sweetsProviderCombo, b.sweetsProvider);
        selectIfPresent(tableClothColorCombo, b.tableClothColor);
        selectIfPresent(djNameCombo, b.djName);

        numberField.setText(nz(b.guestsCount));
        depositAmountField.setText(nz(b.depositAmount));
        totalAmountField.setText(nz(b.totalAmount));
        otherCostsField.setText(nz(b.otherCosts));
        koshaFeeField.setText(nz(b.koshaFee));
        koshaNumberField.setText(nz(b.koshaNumber));
        tablesFlowersFeeField.setText(nz(b.tablesFlowersFee));

        foamCakeMoldFeeField.setText(nz(b.foamCakeMoldFee));
        sparklersFeeField.setText(nz(b.sparklersFee));
        shamBandFeeField.setText(nz(b.shamBandFee));
        djFeeField.setText(nz(b.djFee));
        bubblesField.setText(nz(b.bubblesFee));
        laserLightingFeeField.setText(nz(b.laserLightingFee));
        smokeFeeField.setText(nz(b.smokeFee));
        mixerCraneCamerasFeeField.setText(nz(b.mixerCraneCamerasFee));

        extraLeftField.setText(nz(b.camerasCount));
        totalField.setText(nz(b.camerasTotal));
        photoFeeField.setText(nz(b.photoFee));
        photosCountField.setText(nz(b.photosCount));
        videoFeeField.setText(nz(b.videoFee));
        dataShowFeeField.setText(nz(b.datashowFee));
        dataShowCountField.setText(nz(b.datashowCount));
        studioFeeField.setText(nz(b.studioFee));

        cakeMoldsCountField.setText(nz(b.cakeMoldsCount));
        cakeMoldNumberField.setText(nz(b.cakeMoldNumber));
        cakeAmountField.setText(nz(b.cakeAmount));
        cakePiecesCountField.setText(nz(b.cakePiecesCount));
        cakeCuttingFeeField.setText(nz(b.cakeCuttingFee));
        cakeMoldNumber2Field.setText(nz(b.cakeMoldNumber2));

        childPlatesCountField.setText(nz(b.childPlatesCount));
        childPlatePriceField.setText(nz(b.childPlatePrice));
        plateWithDrinks2PriceField.setText(nz(b.plateWithDrinks2Price));
        appetizersPlatePriceField.setText(nz(b.appetizersPlatePrice));

        selectIfPresent(dinnerOption1Combo, b.dinnerOption1);
        selectIfPresent(dinnerOption2Combo, b.dinnerOption2);
        selectIfPresent(dinnerOption3Combo, b.dinnerOption3);
        selectIfPresent(dinnerOption4Combo, b.dinnerOption4);
        selectIfPresent(dinnerOption5Combo, b.dinnerOption5);

        selectIfPresent(appetizersOption1Combo, b.appetizersOption1);
        selectIfPresent(appetizersOption2Combo, b.appetizersOption2);
        selectIfPresent(kidsPlateOption1Combo, b.kidsPlateOption1);
        selectIfPresent(kidsPlateOption2Combo, b.kidsPlateOption2);

        selectIfPresent(sparklersCountCombo, b.sparklersCount);
        selectIfPresent(breadOvenTypeCombo, b.breadOvenType);
        if (craneCheckbox != null) craneCheckbox.setSelected("نعم".equals(b.crane));

        grandTotalField.setText(nz(b.grandTotal));
        amountDueField.setText(nz(b.amountDue));
        amountDueReceivedField.setText(nz(b.amountDueReceived));
        remainingAmountField.setText(nz(b.remainingAmount));

        // بعد التعبئة من قاعدة البيانات احسب المجموع مجدداً بصيغة IQ باستخدام الجدولة المؤجلة
        scheduleRecalc();
    }

    // تحميل بيانات يوم محدد وتعبئة الواجهة والتحكم بحالة الأزرار
    private void loadBookingAndFillUI(String hallName, int day, int month, int year) {
        BookingDataFull booking = getBookingFull(hallName, day, month, year);
        if (booking != null) {
            // تعبئة الحقول من قاعدة البيانات
            fillAllFieldsFrom(booking);

            // قفل التحرير لأن اليوم محجوز
            setAllEditable(false);

            // ضبط حالة الأزرار والعنوان
            btnCancelBooking.setEnabled(true);
            lblTitle.setText("اليوم محجوز: " + booking.name + " (" + booking.phone + ") " + (booking.mahar == null ? "" : booking.mahar));
            if (bookingStateCallback != null) bookingStateCallback.accept(booking.bookingType);

            if ("final".equals(booking.bookingType)) {
                btnFinalBooking.setEnabled(false);
                btnTempBooking.setEnabled(false);
            } else { // temp
                btnFinalBooking.setEnabled(true);
                btnTempBooking.setEnabled(false);
            }
        } else {
            // لا يوجد حجز: فعّل التحرير والأزرار
            setAllEditable(true);

            btnFinalBooking.setEnabled(true);
            btnTempBooking.setEnabled(true);
            btnCancelBooking.setEnabled(false);
            lblTitle.setText("لا يوجد حجز لليوم المحدد");
            if (bookingStateCallback != null) bookingStateCallback.accept("none");
        }

        // أعِد حساب المبالغ دائماً بعد التبديل
        scheduleRecalc();
    }

    private void setAllEditable(boolean editable) {
        // TextFields
        JTextField[] tfs = {
            nameField, phoneField, maharField, numberField, depositAmountField, totalAmountField, otherCostsField,
            koshaFeeField, koshaNumberField, tablesFlowersFeeField,
            foamCakeMoldFeeField, sparklersFeeField, shamBandFeeField, djFeeField, bubblesField, laserLightingFeeField,
            smokeFeeField, mixerCraneCamerasFeeField, extraLeftField, totalField, photoFeeField, photosCountField,
            videoFeeField, dataShowFeeField, dataShowCountField, studioFeeField, cakeMoldsCountField, cakeMoldNumberField,
            cakeAmountField, cakePiecesCountField, cakeCuttingFeeField, cakeMoldNumber2Field, childPlatesCountField,
            childPlatePriceField, plateWithDrinks2PriceField, appetizersPlatePriceField, grandTotalField, amountDueField,
            amountDueReceivedField, remainingAmountField
        };
        for (JTextField tf : tfs) if (tf != null) tf.setEditable(editable);

        // ComboBoxes
        JComboBox<?>[] cbs = {
            comboBox, comtime, comphont, genderCombo, sweetsProviderCombo, tableClothColorCombo, djNameCombo,
            dinnerOption1Combo, dinnerOption2Combo, dinnerOption3Combo, dinnerOption4Combo, dinnerOption5Combo,
            appetizersOption1Combo, appetizersOption2Combo, kidsPlateOption1Combo, kidsPlateOption2Combo,
            sparklersCountCombo, breadOvenTypeCombo
        };
        for (JComboBox<?> cb : cbs) if (cb != null) cb.setEnabled(editable);

        if (craneCheckbox != null) craneCheckbox.setEnabled(editable);
    }

    // ============================ الحفظ ============================

    // Overload بدون bookingType — يعيد التوجيه ويضع booking_type="final"
    private boolean saveBooking1(
            String hallName, int day, int month, int year,
            String name, String phone, String mahar,

            String eventType, String eventTime, String phonePolicy, String genderType, String sweetsProvider, String tableClothColor, String djName,

            String guestsCount, String depositAmount, String totalAmount, String otherCosts, String koshaFee, String koshaNumber, String tablesFlowersFee,

            String foamCakeMoldFee, String sparklersFee, String shamBandFee, String djFee, String bubblesFee, String laserLightingFee, String smokeFee, String mixerCraneCamerasFee,

            String camerasCount, String camerasTotal, String photoFee, String photosCount, String videoFee, String datashowFee, String datashowCount, String studioFee,

            String cakeMoldsCount, String cakeMoldNumber, String cakeAmount, String cakePiecesCount, String cakeCuttingFee, String cakeMoldNumber2,

            String childPlatesCount, String childPlatePrice, String plateWithDrinks2Price, String appetizersPlatePrice,

            String dinnerOption1, String dinnerOption2, String dinnerOption3, String dinnerOption4, String dinnerOption5,

            String appetizersOption1, String appetizersOption2, String kidsPlateOption1, String kidsPlateOption2,

            String sparklersCount, String breadOvenType, String crane,

            String grandTotal, String amountDue, String amountDueReceived, String remainingAmount
    ) {
        return saveBooking1(
            hallName, day, month, year, name, phone, mahar, "final",
            eventType, eventTime, phonePolicy, genderType, sweetsProvider, tableClothColor, djName,
            guestsCount, depositAmount, totalAmount, otherCosts, koshaFee, koshaNumber, tablesFlowersFee,
            foamCakeMoldFee, sparklersFee, shamBandFee, djFee, bubblesFee, laserLightingFee, smokeFee, mixerCraneCamerasFee,
            camerasCount, camerasTotal, photoFee, photosCount, videoFee, datashowFee, datashowCount, studioFee,
            cakeMoldsCount, cakeMoldNumber, cakeAmount, cakePiecesCount, cakeCuttingFee, cakeMoldNumber2,
            childPlatesCount, childPlatePrice, plateWithDrinks2Price, appetizersPlatePrice,
            dinnerOption1, dinnerOption2, dinnerOption3, dinnerOption4, dinnerOption5,
            appetizersOption1, appetizersOption2, kidsPlateOption1, kidsPlateOption2,
            sparklersCount, breadOvenType, crane,
            grandTotal, amountDue, amountDueReceived, remainingAmount
        );
    }

    // حفظ نهائي — النسخة الكاملة مع bookingType
    private boolean saveBooking1(
            String hallName, int day, int month, int year,
            String name, String phone, String mahar, String bookingType,

            String eventType, String eventTime, String phonePolicy, String genderType, String sweetsProvider, String tableClothColor, String djName,

            String guestsCount, String depositAmount, String totalAmount, String otherCosts, String koshaFee, String koshaNumber, String tablesFlowersFee,

            String foamCakeMoldFee, String sparklersFee, String shamBandFee, String djFee, String bubblesFee, String laserLightingFee, String smokeFee, String mixerCraneCamerasFee,

            String camerasCount, String camerasTotal, String photoFee, String photosCount, String videoFee, String datashowFee, String datashowCount, String studioFee,

            String cakeMoldsCount, String cakeMoldNumber, String cakeAmount, String cakePiecesCount, String cakeCuttingFee, String cakeMoldNumber2,

            String childPlatesCount, String childPlatePrice, String plateWithDrinks2Price, String appetizersPlatePrice,

            String dinnerOption1, String dinnerOption2, String dinnerOption3, String dinnerOption4, String dinnerOption5,

            String appetizersOption1, String appetizersOption2, String kidsPlateOption1, String kidsPlateOption2,

            String sparklersCount, String breadOvenType, String crane,

            String grandTotal, String amountDue, String amountDueReceived, String remainingAmount
    ) {
        final String[] COLS = new String[] {
            "hall_name","day","month","year","name","phone","mahar","booking_type",
            "event_type","event_time","phone_policy","gender_type","sweets_provider","table_cloth_color","dj_name",
            "guests_count","deposit_amount","total_amount","other_costs","kosha_fee","kosha_number","tables_flowers_fee",
            "foam_cake_mold_fee","sparklers_fee","sham_band_fee","dj_fee","bubbles_fee","laser_lighting_fee","smoke_fee","mixer_crane_cameras_fee",
            "cameras_count","cameras_total","photo_fee","photos_count","video_fee","datashow_fee","datashow_count","studio_fee",
            "cake_molds_count","cake_mold_number","cake_amount","cake_pieces_count","cake_cutting_fee","cake_mold_number2",
            "child_plates_count","child_plate_price","plate_with_drinks2_price","appetizers_plate_price",
            "dinner_option1","dinner_option2","dinner_option3","dinner_option4","dinner_option5",
            "appetizers_option1","appetizers_option2","kids_plate_option1","kids_plate_option2",
            "sparklers_count","bread_oven_type","crane",
            "grand_total","amount_due","amount_due_received","remaining_amount"
        };

        String placeholders = String.join(",", java.util.Collections.nCopies(COLS.length, "?"));
        String sql = "INSERT INTO bookings (" + String.join(", ", COLS) + ") VALUES (" + placeholders + ")";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int i = 1;
            stmt.setString(i++, hallName);
            stmt.setInt(i++, day);
            stmt.setInt(i++, month);
            stmt.setInt(i++, year);
            stmt.setString(i++, name);
            stmt.setString(i++, phone);
            stmt.setString(i++, mahar);
            stmt.setString(i++, bookingType);

            stmt.setString(i++, eventType);
            stmt.setString(i++, eventTime);
            stmt.setString(i++, phonePolicy);
            stmt.setString(i++, genderType);
            stmt.setString(i++, sweetsProvider);
            stmt.setString(i++, tableClothColor);
            stmt.setString(i++, djName);

            stmt.setString(i++, guestsCount);
            stmt.setString(i++, depositAmount);
            stmt.setString(i++, totalAmount);
            stmt.setString(i++, otherCosts);
            stmt.setString(i++, koshaFee);
            stmt.setString(i++, koshaNumber);
            stmt.setString(i++, tablesFlowersFee);

            stmt.setString(i++, foamCakeMoldFee);
            stmt.setString(i++, sparklersFee);
            stmt.setString(i++, shamBandFee);
            stmt.setString(i++, djFee);
            stmt.setString(i++, bubblesFee);
            stmt.setString(i++, laserLightingFee);
            stmt.setString(i++, smokeFee);
            stmt.setString(i++, mixerCraneCamerasFee);

            stmt.setString(i++, camerasCount);
            stmt.setString(i++, camerasTotal);
            stmt.setString(i++, photoFee);
            stmt.setString(i++, photosCount);
            stmt.setString(i++, videoFee);
            stmt.setString(i++, datashowFee);
            stmt.setString(i++, datashowCount);
            stmt.setString(i++, studioFee);

            stmt.setString(i++, cakeMoldsCount);
            stmt.setString(i++, cakeMoldNumber);
            stmt.setString(i++, cakeAmount);
            stmt.setString(i++, cakePiecesCount);
            stmt.setString(i++, cakeCuttingFee);
            stmt.setString(i++, cakeMoldNumber2);

            stmt.setString(i++, childPlatesCount);
            stmt.setString(i++, childPlatePrice);
            stmt.setString(i++, plateWithDrinks2Price);
            stmt.setString(i++, appetizersPlatePrice);

            stmt.setString(i++, dinnerOption1);
            stmt.setString(i++, dinnerOption2);
            stmt.setString(i++, dinnerOption3);
            stmt.setString(i++, dinnerOption4);
            stmt.setString(i++, dinnerOption5);

            stmt.setString(i++, appetizersOption1);
            stmt.setString(i++, appetizersOption2);
            stmt.setString(i++, kidsPlateOption1);
            stmt.setString(i++, kidsPlateOption2);

            stmt.setString(i++, sparklersCount);
            stmt.setString(i++, breadOvenType);
            stmt.setString(i++, crane);

            stmt.setString(i++, grandTotal);
            stmt.setString(i++, amountDue);
            stmt.setString(i++, amountDueReceived);
            stmt.setString(i++, remainingAmount);

            int setCount = i - 1;
            if (setCount != COLS.length) {
                throw new IllegalStateException("Params mismatch: set=" + setCount + " expected=" + COLS.length);
            }

            stmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "خطأ في حفظ الحجز النهائي!\n" + ex.getMessage(), "خطأ", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // حفظ مؤقت — booking_type = temp
    private boolean saveTempBooking1(
            String hallName, int day, int month, int year,
            String name, String phone, String mahar,

            String eventType, String eventTime, String phonePolicy, String genderType, String sweetsProvider, String tableClothColor, String djName,

            String guestsCount, String depositAmount, String totalAmount, String otherCosts, String koshaFee, String koshaNumber, String tablesFlowersFee,

            String foamCakeMoldFee, String sparklersFee, String shamBandFee, String djFee, String bubblesFee, String laserLightingFee, String smokeFee, String mixerCraneCamerasFee,

            String camerasCount, String camerasTotal, String photoFee, String photosCount, String videoFee, String datashowFee, String datashowCount, String studioFee,

            String cakeMoldsCount, String cakeMoldNumber, String cakeAmount, String cakePiecesCount, String cakeCuttingFee, String cakeMoldNumber2,

            String childPlatesCount, String childPlatePrice, String plateWithDrinks2Price, String appetizersPlatePrice,

            String dinnerOption1, String dinnerOption2, String dinnerOption3, String dinnerOption4, String dinnerOption5,

            String appetizersOption1, String appetizersOption2, String kidsPlateOption1, String kidsPlateOption2,

            String sparklersCount, String breadOvenType, String crane,

            String grandTotal, String amountDue, String amountDueReceived, String remainingAmount
    ) {
        final String[] COLS = new String[] {
            "hall_name","day","month","year","name","phone","mahar","booking_type",
            "event_type","event_time","phone_policy","gender_type","sweets_provider","table_cloth_color","dj_name",
            "guests_count","deposit_amount","total_amount","other_costs","kosha_fee","kosha_number","tables_flowers_fee",
            "foam_cake_mold_fee","sparklers_fee","sham_band_fee","dj_fee","bubbles_fee","laser_lighting_fee","smoke_fee","mixer_crane_cameras_fee",
            "cameras_count","cameras_total","photo_fee","photos_count","video_fee","datashow_fee","datashow_count","studio_fee",
            "cake_molds_count","cake_mold_number","cake_amount","cake_pieces_count","cake_cutting_fee","cake_mold_number2",
            "child_plates_count","child_plate_price","plate_with_drinks2_price","appetizers_plate_price",
            "dinner_option1","dinner_option2","dinner_option3","dinner_option4","dinner_option5",
            "appetizers_option1","appetizers_option2","kids_plate_option1","kids_plate_option2",
            "sparklers_count","bread_oven_type","crane",
            "grand_total","amount_due","amount_due_received","remaining_amount"
        };

        String placeholders = String.join(",", java.util.Collections.nCopies(COLS.length, "?"));
        String sql = "INSERT INTO bookings (" + String.join(", ", COLS) + ") VALUES (" + placeholders + ")";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int i = 1;
            stmt.setString(i++, hallName);
            stmt.setInt(i++, day);
            stmt.setInt(i++, month);
            stmt.setInt(i++, year);
            stmt.setString(i++, name);
            stmt.setString(i++, phone);
            stmt.setString(i++, mahar);
            stmt.setString(i++, "temp"); // booking_type

            stmt.setString(i++, eventType);
            stmt.setString(i++, eventTime);
            stmt.setString(i++, phonePolicy);
            stmt.setString(i++, genderType);
            stmt.setString(i++, sweetsProvider);
            stmt.setString(i++, tableClothColor);
            stmt.setString(i++, djName);

            stmt.setString(i++, guestsCount);
            stmt.setString(i++, depositAmount);
            stmt.setString(i++, totalAmount);
            stmt.setString(i++, otherCosts);
            stmt.setString(i++, koshaFee);
            stmt.setString(i++, koshaNumber);
            stmt.setString(i++, tablesFlowersFee);

            stmt.setString(i++, foamCakeMoldFee);
            stmt.setString(i++, sparklersFee);
            stmt.setString(i++, shamBandFee);
            stmt.setString(i++, djFee);
            stmt.setString(i++, bubblesFee);
            stmt.setString(i++, laserLightingFee);
            stmt.setString(i++, smokeFee);
            stmt.setString(i++, mixerCraneCamerasFee);

            stmt.setString(i++, camerasCount);
            stmt.setString(i++, camerasTotal);
            stmt.setString(i++, photoFee);
            stmt.setString(i++, photosCount);
            stmt.setString(i++, videoFee);
            stmt.setString(i++, datashowFee);
            stmt.setString(i++, datashowCount);
            stmt.setString(i++, studioFee);

            stmt.setString(i++, cakeMoldsCount);
            stmt.setString(i++, cakeMoldNumber);
            stmt.setString(i++, cakeAmount);
            stmt.setString(i++, cakePiecesCount);
            stmt.setString(i++, cakeCuttingFee);
            stmt.setString(i++, cakeMoldNumber2);

            stmt.setString(i++, childPlatesCount);
            stmt.setString(i++, childPlatePrice);
            stmt.setString(i++, plateWithDrinks2Price);
            stmt.setString(i++, appetizersPlatePrice);

            stmt.setString(i++, dinnerOption1);
            stmt.setString(i++, dinnerOption2);
            stmt.setString(i++, dinnerOption3);
            stmt.setString(i++, dinnerOption4);
            stmt.setString(i++, dinnerOption5);

            stmt.setString(i++, appetizersOption1);
            stmt.setString(i++, appetizersOption2);
            stmt.setString(i++, kidsPlateOption1);
            stmt.setString(i++, kidsPlateOption2);

            stmt.setString(i++, sparklersCount);
            stmt.setString(i++, breadOvenType);
            stmt.setString(i++, crane);

            stmt.setString(i++, grandTotal);
            stmt.setString(i++, amountDue);
            stmt.setString(i++, amountDueReceived);
            stmt.setString(i++, remainingAmount);

            int setCount = i - 1;
            if (setCount != COLS.length) {
                throw new IllegalStateException("Params mismatch: set=" + setCount + " expected=" + COLS.length);
            }

            stmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "خطأ في حفظ الحجز المؤقت!\n" + ex.getMessage(), "خطأ", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ============================ الحذف ============================

    private boolean deleteTempBookings(String hallName, int day, int month, int year) {
        String sql = "DELETE FROM bookings WHERE booking_type='temp' AND hall_name=? AND day=? AND month=? AND year=?";
        try (Connection conn = JDBC.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, hallName);
            st.setInt(2, day);
            st.setInt(3, month);
            st.setInt(4, year);
            st.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "خطأ في حذف الحجوزات المؤقتة!\n" + ex.getMessage(), "خطأ",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean deleteLatestBookingForDate(String hallName, int day, int month, int year) {
        String sql =
            "DELETE FROM bookings WHERE id = (" +
            "  SELECT id FROM (" +
            "    SELECT id FROM bookings WHERE hall_name=? AND day=? AND month=? AND year=? ORDER BY id DESC LIMIT 1" +
            "  ) x" +
            ")";
        try (Connection conn = JDBC.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, hallName);
            st.setInt(2, day);
            st.setInt(3, month);
            st.setInt(4, year);
            int affected = st.executeUpdate();
            if (affected > 0) return true;
            JOptionPane.showMessageDialog(this, "لا يوجد حجز لهذا اليوم لإلغائه.", "معلومة",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "خطأ في إلغاء الحجز!\n" + ex.getMessage(), "خطأ",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean deleteById(int id) {
        String sql = "DELETE FROM bookings WHERE id=?";
        try (Connection conn = JDBC.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // ============================ أدوات مساعدة ============================

    // يحوّل أي نص إلى رقم؛ يتجاهل الأحرف مثل IQ والفواصل
    private static long parseMoney(String s) {
        if (s == null) return 0L;
        s = s.trim();
        if (s.isEmpty()) return 0L;
        s = s.replaceAll("[^0-9-]", "");
        if (s.isEmpty() || "-".equals(s)) return 0L;
        try { return Long.parseLong(s); } catch (NumberFormatException ex) { return 0L; }
    }

    // تنسيق بسيط للمجموع مع اللاحقة " IQ"
    private static String toIQ(long value) {
        // لإضافة فواصل آلاف: return String.format("%,d IQ", value);
        return value + " IQ";
    }

    // ============================ دوال التحجيم والحساب المؤجل ============================

    // تسجيل حقول المبالغ للتحجيم
    private void registerAmountFields() {
        amountFields.clear();
        amountFields.add(totalAmountField);
        amountFields.add(depositAmountField);
        amountFields.add(otherCostsField);
        amountFields.add(koshaFeeField);
        amountFields.add(tablesFlowersFeeField);
        amountFields.add(foamCakeMoldFeeField);
        amountFields.add(sparklersFeeField);
        amountFields.add(shamBandFeeField);
        amountFields.add(djFeeField);
        amountFields.add(bubblesField);
        amountFields.add(laserLightingFeeField);
        amountFields.add(smokeFeeField);
        amountFields.add(mixerCraneCamerasFeeField);
        amountFields.add(extraLeftField);
        amountFields.add(totalField);
        amountFields.add(photoFeeField);
        amountFields.add(videoFeeField);
        amountFields.add(dataShowFeeField);
        amountFields.add(studioFeeField);
        amountFields.add(cakeAmountField);
        amountFields.add(cakeCuttingFeeField);
        amountFields.add(childPlatePriceField);
        amountFields.add(plateWithDrinks2PriceField);
        amountFields.add(appetizersPlatePriceField);
        amountFields.add(amountDueReceivedField); // يمكن إزالتها لاحقاً إذا أراد المستخدم
    }

    // ربط سلوك التحجيم بحقول المبالغ
    private void attachAmountScalingBehavior() {
        for (JTextField tf : amountFields) {
            if (tf != null) {
                // عند فقدان التركيز
                tf.addFocusListener(new java.awt.event.FocusListener() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {
                        // إزالة اللاحقة " IQ" عند التركيز للتحرير
                        String text = tf.getText().trim();
                        if (text.endsWith(" IQ")) {
                            tf.setText(text.substring(0, text.length() - 3).trim());
                        }
                    }
                    
                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {
                        applyScaling(tf);
                    }
                });
                
                // عند الضغط على Enter
                tf.addActionListener(e -> applyScaling(tf));
            }
        }
    }

    // تطبيق التحجيم على حقل المبلغ
    private void applyScaling(JTextField tf) {
        String text = tf.getText().trim();
        if (text.isEmpty()) return;
        
        // إزالة اللاحقة إذا كانت موجودة
        if (text.endsWith(" IQ")) {
            text = text.substring(0, text.length() - 3).trim();
        }
        
        try {
            long value = Long.parseLong(text);
            // تحجيم القيم
            if (tf == totalAmountField) {
                value *= 1_000_000L; // ضرب في مليون للمبلغ الإجمالي
            } else {
                value *= 1_000L; // ضرب في ألف للحقول الأخرى
            }
            
            internalUpdate = true;
            tf.setText(toIQ(value));
            internalUpdate = false;
            
        } catch (NumberFormatException ex) {
            // في حالة خطأ التحويل، اتركه كما هو
        }
    }

    // جدولة إعادة الحساب المؤجل
    private void scheduleRecalc() {
        if (recalcScheduled) return;
        recalcScheduled = true;
        SwingUtilities.invokeLater(this::doRecalc);
    }

    // تنفيذ إعادة الحساب الفعلي
    private void doRecalc() {
        recalcScheduled = false;
        internalUpdate = true;
        try {
            recomputeGrandTotal();
            computeDerivedTotals();
        } finally {
            internalUpdate = false;
        }
    }
}