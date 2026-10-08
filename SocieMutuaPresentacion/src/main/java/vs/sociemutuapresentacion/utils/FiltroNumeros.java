package vs.sociemutuapresentacion.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Samuel Vega
 */
public class FiltroNumeros extends DocumentFilter {
    
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if(string == null) return;
        if(string.matches("\\d+")) super.insertString(fb, offset, string, attr);
    }
    
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if(text == null) return;
        if(text.matches("\\d+") || text.isEmpty()) super.replace(fb, offset, length, text, attrs);
    }
        
}
