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
    private JLabel lblTitle;

    // مصادر الجمع التلقائي
    private final List<JTextField> sumSources = new ArrayList<>();

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(depositAmountField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(totalAmountField);

        otherCostsField = new JTextField();
        otherCostsField.setBackground(new Color(192, 192, 192));
        otherCostsField.setText("");
        otherCostsField.setHorizontalAlignment(JTextField.CENTER);
        otherCostsField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        otherCostsField.setEditable(true);
        otherCostsField.setBounds(1236, 384, 162, 32);
        panel.add(otherCostsField);
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(otherCostsField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(koshaFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(tablesFlowersFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(foamCakeMoldFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(sparklersFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(shamBandFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(maharField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(djFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(bubblesField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(laserLightingFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(smokeFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(mixerCraneCamerasFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(totalField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(photoFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(videoFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(dataShowFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(studioFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(cakeAmountField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(grandTotalField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(amountDueField);

        cakeCuttingFeeField = new JTextField();
        cakeCuttingFeeField.setText("");
        cakeCuttingFeeField.setHorizontalAlignment(JTextField.CENTER);
        cakeCuttingFeeField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        cakeCuttingFeeField.setEditable(true);
        cakeCuttingFeeField.setBackground(Color.LIGHT_GRAY);
        cakeCuttingFeeField.setBounds(927, 680, 184, 32);
        panel.add(cakeCuttingFeeField);
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(cakeCuttingFeeField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(childPlatePriceField);

        plateWithDrinks2PriceField = new JTextField();
        plateWithDrinks2PriceField.setText("");
        plateWithDrinks2PriceField.setHorizontalAlignment(JTextField.CENTER);
        plateWithDrinks2PriceField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        plateWithDrinks2PriceField.setEditable(true);
        plateWithDrinks2PriceField.setBackground(Color.LIGHT_GRAY);
        plateWithDrinks2PriceField.setBounds(10, 709, 202, 32);
        panel.add(plateWithDrinks2PriceField);
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(plateWithDrinks2PriceField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(appetizersPlatePriceField);

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
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(amountDueReceivedField);

        remainingAmountField = new JTextField();
        remainingAmountField.setText("");
        remainingAmountField.setHorizontalAlignment(JTextField.CENTER);
        remainingAmountField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        remainingAmountField.setEditable(true);
        remainingAmountField.setBackground(Color.LIGHT_GRAY);
        remainingAmountField.setBounds(426, 790, 183, 32);
        panel.add(remainingAmountField);
        
        // تطبيق تنسيق الأرقام بفواصل الآلاف
        NumericFormatter.attachThousandsFormatter(remainingAmountField);

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

    }}