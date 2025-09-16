package faou;

import javax.swing.*;
import javax.swing.text.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * NumericFormatter - فئة مساعدة لتنسيق الحقول المالية بفواصل الآلاف
 * 
 * تقوم هذه الفئة بتوفير:
 * 1. DocumentFilter يسمح بالأرقام فقط
 * 2. تنسيق تلقائي بفواصل الآلاف أثناء الكتابة
 * 3. دوال مساعدة لتحويل النصوص المنسقة إلى أرقام
 * 
 * الاستخدام:
 * NumericFormatter.attachThousandsFormatter(textField);
 */
public class NumericFormatter {
    
    // منسق الأرقام بفواصل الآلاف - إنجليزي لضمان استخدام الفواصل بدلاً من المسافات
    private static final DecimalFormat THOUSANDS_FORMAT = 
        (DecimalFormat) NumberFormat.getIntegerInstance(Locale.US);
    
    static {
        THOUSANDS_FORMAT.setGroupingUsed(true);
    }
    
    /**
     * تطبق تنسيق الآلاف على حقل نصي
     * @param field الحقل المراد تنسيقه
     */
    public static void attachThousandsFormatter(JTextField field) {
        if (field != null) {
            // إضافة DocumentFilter للحقل
            ((AbstractDocument) field.getDocument()).setDocumentFilter(new DigitGroupingFilter());
        }
    }
    
    /**
     * تحويل النص المنسق إلى رقم صحيح طويل
     * @param formattedText النص المحتوي على فواصل
     * @return الرقم أو 0 إذا كان النص فارغاً أو غير صالح
     */
    public static long parseNumeric(String formattedText) {
        if (formattedText == null || formattedText.trim().isEmpty()) {
            return 0;
        }
        
        try {
            // إزالة كل الفواصل والمسافات
            String cleaned = formattedText.replaceAll("[,\\s]", "");
            return cleaned.isEmpty() ? 0 : Long.parseLong(cleaned);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    /**
     * تنسيق رقم بفواصل الآلاف
     * @param number الرقم المراد تنسيقه
     * @return النص المنسق
     */
    public static String formatNumber(long number) {
        return THOUSANDS_FORMAT.format(number);
    }
    
    /**
     * DocumentFilter مخصص لتنسيق الأرقام بفواصل الآلاف
     */
    private static class DigitGroupingFilter extends DocumentFilter {
        
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            // إزالة أي محارف غير رقمية من النص المدخل
            String digits = extractDigits(string);
            if (!digits.isEmpty()) {
                // احصل على النص الحالي وأدرج الأرقام الجديدة
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String beforeOffset = currentText.substring(0, offset);
                String afterOffset = currentText.substring(offset);
                
                // إزالة الفواصل من النص الحالي
                String cleanBefore = beforeOffset.replaceAll(",", "");
                String cleanAfter = afterOffset.replaceAll(",", "");
                
                // تكوين الرقم الجديد
                String newNumber = cleanBefore + digits + cleanAfter;
                
                // تنسيق الرقم الجديد
                String formatted = formatDigitString(newNumber);
                
                // استبدال النص بالكامل
                fb.replace(0, fb.getDocument().getLength(), formatted, attr);
                
                // ضبط موضع المؤشر (تقدير تقريبي)
                SwingUtilities.invokeLater(() -> {
                    JTextComponent textComponent = getTextComponent(fb);
                    if (textComponent != null) {
                        int newCaretPosition = calculateNewCaretPosition(cleanBefore + digits, formatted);
                        textComponent.setCaretPosition(Math.min(newCaretPosition, formatted.length()));
                    }
                });
            }
        }
        
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            // إزالة أي محارف غير رقمية
            String digits = extractDigits(text);
            
            // احصل على النص الحالي
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String beforeOffset = currentText.substring(0, offset);
            String afterOffset = currentText.substring(offset + length);
            
            // إزالة الفواصل
            String cleanBefore = beforeOffset.replaceAll(",", "");
            String cleanAfter = afterOffset.replaceAll(",", "");
            
            // تكوين الرقم الجديد
            String newNumber = cleanBefore + digits + cleanAfter;
            
            // تنسيق الرقم الجديد
            String formatted = formatDigitString(newNumber);
            
            // استبدال النص بالكامل
            fb.replace(0, fb.getDocument().getLength(), formatted, attrs);
            
            // ضبط موضع المؤشر
            SwingUtilities.invokeLater(() -> {
                JTextComponent textComponent = getTextComponent(fb);
                if (textComponent != null) {
                    int newCaretPosition = calculateNewCaretPosition(cleanBefore + digits, formatted);
                    textComponent.setCaretPosition(Math.min(newCaretPosition, formatted.length()));
                }
            });
        }
        
        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            // احصل على النص الحالي
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String beforeOffset = currentText.substring(0, offset);
            String afterOffset = currentText.substring(offset + length);
            
            // إزالة الفواصل
            String cleanBefore = beforeOffset.replaceAll(",", "");
            String cleanAfter = afterOffset.replaceAll(",", "");
            
            // تكوين الرقم الجديد
            String newNumber = cleanBefore + cleanAfter;
            
            // تنسيق الرقم الجديد
            String formatted = formatDigitString(newNumber);
            
            // استبدال النص بالكامل
            fb.replace(0, fb.getDocument().getLength(), formatted, null);
            
            // ضبط موضع المؤشر
            SwingUtilities.invokeLater(() -> {
                JTextComponent textComponent = getTextComponent(fb);
                if (textComponent != null) {
                    int newCaretPosition = calculateNewCaretPosition(cleanBefore, formatted);
                    textComponent.setCaretPosition(Math.min(newCaretPosition, formatted.length()));
                }
            });
        }
        
        /**
         * استخراج الأرقام فقط من النص
         */
        private String extractDigits(String text) {
            if (text == null) return "";
            return text.replaceAll("[^0-9]", "");
        }
        
        /**
         * تنسيق سلسلة الأرقام بفواصل الآلاف
         */
        private String formatDigitString(String digits) {
            if (digits == null || digits.isEmpty()) {
                return "";
            }
            
            // إزالة الأصفار البادئة (باستثناء رقم واحد)
            digits = digits.replaceFirst("^0+", "");
            if (digits.isEmpty()) {
                return "0";
            }
            
            try {
                long number = Long.parseLong(digits);
                return THOUSANDS_FORMAT.format(number);
            } catch (NumberFormatException e) {
                return digits; // عودة للنص الأصلي في حالة الخطأ
            }
        }
        
        /**
         * حساب موضع المؤشر الجديد بعد التنسيق
         */
        private int calculateNewCaretPosition(String beforeCaret, String formatted) {
            if (beforeCaret.isEmpty()) return 0;
            
            // عد الأرقام قبل المؤشر
            int digitCount = beforeCaret.length();
            
            // ابحث عن الموضع في النص المنسق الذي يحتوي على نفس عدد الأرقام
            int digitsSeen = 0;
            for (int i = 0; i < formatted.length(); i++) {
                if (Character.isDigit(formatted.charAt(i))) {
                    digitsSeen++;
                    if (digitsSeen == digitCount) {
                        return i + 1;
                    }
                }
            }
            return formatted.length();
        }
        
        /**
         * الحصول على مكون النص من FilterBypass
         */
        private JTextComponent getTextComponent(FilterBypass fb) {
            try {
                Document doc = fb.getDocument();
                Object property = doc.getProperty("owner");
                if (property instanceof JTextComponent) {
                    return (JTextComponent) property;
                }
                
                // بحث بديل - تفتيش في النوافذ المفتوحة
                for (java.awt.Window window : java.awt.Window.getWindows()) {
                    JTextComponent found = findTextComponentWithDocument(window, doc);
                    if (found != null) {
                        return found;
                    }
                }
            } catch (Exception e) {
                // تجاهل الأخطاء
            }
            return null;
        }
        
        /**
         * البحث عن مكون النص بالمستند المحدد
         */
        private JTextComponent findTextComponentWithDocument(java.awt.Container container, Document doc) {
            for (java.awt.Component component : container.getComponents()) {
                if (component instanceof JTextComponent) {
                    JTextComponent textComp = (JTextComponent) component;
                    if (textComp.getDocument() == doc) {
                        return textComp;
                    }
                } else if (component instanceof java.awt.Container) {
                    JTextComponent found = findTextComponentWithDocument((java.awt.Container) component, doc);
                    if (found != null) {
                        return found;
                    }
                }
            }
            return null;
        }
    }
}