package vs.sociemutuapresentacion.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Samuel Vega
 */
public class FiltroDecimales extends DocumentFilter {
    private final String PATTERN = "^\\d*(\\.\\d*)?$";
    
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if(string == null) return;
        
        String actualText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String purposedText = actualText.substring(0, offset) + string + actualText.substring(offset);
        
        if(purposedText.matches(PATTERN)) super.insertString(fb, offset, string, attr);
    }
    
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if(text == null) return;
        
        String actualText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String purposedText = actualText.substring(0, offset) + text + actualText.substring(offset + length);
        
        if(purposedText.matches(PATTERN)) super.replace(fb, offset, length, text, attrs);
    }
    
}
