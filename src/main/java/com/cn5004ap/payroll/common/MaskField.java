package com.cn5004ap.payroll.common;

import io.github.palexdev.materialfx.controls.BoundTextField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.skins.MFXTextFieldSkin;
import javafx.beans.property.*;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Skin;
import javafx.scene.control.TextFormatter;

import java.util.ArrayList;
import java.util.List;

public class MaskField
    extends MFXTextField
{
    /**
     * Position "D" in the mask will allow you to enter only numbers
     */
    public static final char MASK_DIGIT = 'D';

    /**
     * Position "W" in the mask will allow you to enter either numbers or letters
     */
    public static final char MASK_DIG_OR_CHAR = 'W';

    /**
     * Position "A" in the mask will allow you to enter only letters
     */
    public static final char MASK_CHARACTER = 'A';


    public static final char WHAT_MASK_CHAR = '#';

    public static final char WHAT_MASK_NO_CHAR = '-';


    public static final char PLACEHOLDER_CHAR_DEFAULT = '_';

    protected final BoundTxtField boundTxtField;

    private List<Position> objectMask = new ArrayList<>();

    /**
     * Plain text without masking
     */
    private StringProperty plainText;

    /**
     * The mask itself visible in the input field
     */
    private StringProperty mask;

    /**
     * if the mask should display characters that are reserved for the mask, then an additional hint is given where is
     * the mask symbol and where is just a symbol
     */
    private StringProperty whatMask;

    /**
     * These are replacement characters
     */
    private StringProperty placeholder;

    public MaskField()
    {
        this("");
    }

    public MaskField(String text)
    {
        this(text, "");
    }

    public MaskField(String text, String promptText)
    {
        this(text, promptText, "");
    }

    public MaskField(String text, String promptText, String floatingText)
    {
        super(text, promptText, floatingText);

        this.boundTxtField = new BoundTxtField(this);
    }

    public final String getPlainText()
    {
        return plainTextProperty().get();
    }

    public final void setPlainText(String value)
    {
        plainTextProperty().set(value);
        updateShowingField();
    }

    public final StringProperty plainTextProperty()
    {
        if (plainText == null)
            plainText = new SimpleStringProperty(this, "plainText", "");
        return plainText;
    }

    public final String getMask()
    {
        return maskProperty().get();
    }

    public final void setMask(String value)
    {
        maskProperty().set(value);
        rebuildObjectMask();
        updateShowingField();
    }

    public final StringProperty maskProperty()
    {
        if (mask == null)
            mask = new SimpleStringProperty(this, "mask");

        return mask;
    }

    public final String getWhatMask()
    {
        return whatMaskProperty().get();
    }

    public final void setWhatMask(String value)
    {
        whatMaskProperty().set(value);
        rebuildObjectMask();
        updateShowingField();
    }

    public final StringProperty whatMaskProperty()
    {
        if (whatMask == null)
        {
            whatMask = new SimpleStringProperty(this, "whatMask");
        }
        return whatMask;
    }

    public final String getPlaceholder()
    {
        return placeholderProperty().get();
    }

    public final void setPlaceholder(String value)
    {
        placeholderProperty().set(value);
        rebuildObjectMask();
        updateShowingField();
    }

    public final StringProperty placeholderProperty()
    {
        if (placeholder == null)
            placeholder = new SimpleStringProperty(this, "placeholder");
        return placeholder;
    }

    /**
     * Generates a list of Position objects for each mask character
     */
    private void rebuildObjectMask()
    {
        objectMask = new ArrayList<>();

        for (int i = 0; i < getMask().length(); i++)
        {
            char m = getMask().charAt(i);
            char w = WHAT_MASK_CHAR;
            char p = PLACEHOLDER_CHAR_DEFAULT;

            if (getWhatMask() != null && i < getWhatMask().length())
            {
                // the mask character is specifically indicated whether it is or not
                if (getWhatMask().charAt(i) != WHAT_MASK_CHAR)
                {
                    w = WHAT_MASK_NO_CHAR;
                }
            }
            else
            {
                // since it is not indicated what kind of symbol - we understand it
                // ourselves and if the symbol is not among the symbols of the mask -
                // then it is considered a simple literal
                if (m != MASK_CHARACTER && m != MASK_DIG_OR_CHAR && m != MASK_DIGIT)
                    w = WHAT_MASK_NO_CHAR;

            }

            if (getPlaceholder() != null && i < getPlaceholder().length())
                p = getPlaceholder().charAt(i);

            objectMask.add(new Position(m, w, p));
        }
    }

    /**
     * Superimposes simply the plainText text on the given mask, corrects the position of the caret
     */
    private void updateShowingField()
    {
        int    counterPlainCharInMask      = 0;
        int    lastPositionPlainCharInMask = 0;
        int    firstPlaceholderInMask      = -1;
        String textMask                    = "";
        String textPlain                   = getPlainText();
        for (int i = 0; i < objectMask.size(); i++)
        {
            Position p = objectMask.get(i);
            if (p.isPlainCharacter())
            {
                if (textPlain.length() > counterPlainCharInMask)
                {

                    char c = textPlain.charAt(counterPlainCharInMask);
                    while (!p.isCorrect(c))
                    {
                        // cutting out what doesn't fit
                        textPlain = textPlain.substring(0, counterPlainCharInMask) + textPlain.substring(
                            counterPlainCharInMask + 1);

                        if (textPlain.length() > counterPlainCharInMask)
                            c = textPlain.charAt(counterPlainCharInMask);
                        else
                            break;
                    }

                    textMask += c;
                    lastPositionPlainCharInMask = i;
                }
                else
                {
                    textMask += p.placeholder;
                    if (firstPlaceholderInMask == -1)
                        firstPlaceholderInMask = i;
                }

                counterPlainCharInMask++;

            }
            else
            {
                textMask += p.mask;
            }
        }

        setText(textMask);

        if (firstPlaceholderInMask == -1)
            firstPlaceholderInMask = 0;

        int caretPosition = (textPlain.length() > 0 ? lastPositionPlainCharInMask + 1 : firstPlaceholderInMask);
        selectRange(caretPosition, caretPosition);

        if (textPlain.length() > counterPlainCharInMask)
            textPlain = textPlain.substring(0, counterPlainCharInMask);

        if (!textPlain.equals(getPlainText()))
            setPlainText(textPlain);

    }

    private int interpretMaskPositionInPlainPosition(int posMask)
    {
        int posPlain = 0;

        for (int i = 0; i < objectMask.size() && i < posMask; i++)
        {
            Position p = objectMask.get(i);
            if (p.isPlainCharacter())
                posPlain++;
        }

        return posPlain;
    }

    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new MFXTextFieldSkin(this, this.boundTxtField);
    }

    @Override
    public void cut()
    {
        this.boundTxtField.cut();
    }

    @Override
    public void copy()
    {
        this.boundTxtField.copy();
    }

    @Override
    public void paste()
    {
        this.boundTxtField.paste();
    }

    public void selectBackward()
    {
        this.boundTxtField.selectBackward();
    }

    @Override
    public void selectForward()
    {
        this.boundTxtField.selectForward();
    }

    @Override
    public void previousWord()
    {
        this.boundTxtField.previousWord();
    }

    @Override
    public void nextWord()
    {
        this.boundTxtField.nextWord();
    }

    @Override
    public void endOfNextWord()
    {
        this.boundTxtField.endOfNextWord();
    }

    @Override
    public void selectPreviousWord()
    {
        this.boundTxtField.selectPreviousWord();
    }

    @Override
    public void selectNextWord()
    {
        this.boundTxtField.selectNextWord();
    }

    @Override
    public void selectEndOfNextWord()
    {
        this.boundTxtField.selectEndOfNextWord();
    }

    @Override
    public void selectAll()
    {
        this.boundTxtField.selectAll();
    }

    @Override
    public void home()
    {
        this.boundTxtField.home();
    }

    @Override
    public void end()
    {
        this.boundTxtField.end();
    }

    @Override
    public void selectHome()
    {
        this.boundTxtField.selectHome();
    }

    @Override
    public void selectEnd()
    {
        this.boundTxtField.selectEnd();
    }

    @Override
    public void forward()
    {
        this.boundTxtField.forward();
    }

    @Override
    public void backward()
    {
        this.boundTxtField.backward();
    }

    @Override
    public void positionCaret(int pos)
    {
        this.boundTxtField.positionCaret(pos);
    }

    @Override
    public void selectPositionCaret(int pos)
    {
        this.boundTxtField.selectPositionCaret(pos);
    }

    @Override
    public void selectRange(int anchor, int caretPosition)
    {
        this.boundTxtField.selectRange(anchor, caretPosition);
    }

    @Override
    public void extendSelection(int pos)
    {
        this.boundTxtField.extendSelection(pos);
    }

    @Override
    public void clear()
    {
        this.boundTxtField.clear();
    }

    @Override
    public void deselect()
    {
        this.boundTxtField.deselect();
    }

    @Override
    public void replaceSelection(String replacement)
    {
        this.boundTxtField.replaceSelection(replacement);
    }

    @Override
    public TextFormatter<?> delegateGetTextFormatter()
    {
        return this.boundTxtField.getTextFormatter();
    }

    @Override
    public ObjectProperty<TextFormatter<?>> delegateTextFormatterProperty()
    {
        return this.boundTxtField.textFormatterProperty();
    }

    @Override
    public void delegateSetTextFormatter(TextFormatter<?> textFormatter)
    {
        this.boundTxtField.setTextFormatter(textFormatter);
    }

    @Override
    public int delegateGetAnchor()
    {
        return this.boundTxtField.getAnchor();
    }

    @Override
    public ReadOnlyIntegerProperty delegateAnchorProperty()
    {
        return this.boundTxtField.anchorProperty();
    }

    @Override
    public int delegateGetCaretPosition()
    {
        return this.boundTxtField.getCaretPosition();
    }

    @Override
    public ReadOnlyIntegerProperty delegateCaretPositionProperty()
    {
        return this.boundTxtField.caretPositionProperty();
    }

    @Override
    public String delegateGetSelectedText()
    {
        return this.boundTxtField.getSelectedText();
    }

    @Override
    public ReadOnlyStringProperty delegateSelectedTextProperty()
    {
        return this.boundTxtField.selectedTextProperty();
    }

    @Override
    public IndexRange delegateGetSelection()
    {
        return this.boundTxtField.getSelection();
    }

    @Override
    public ReadOnlyObjectProperty<IndexRange> delegateSelectionProperty()
    {
        return this.boundTxtField.selectionProperty();
    }

    @Override
    public boolean delegateIsRedoable()
    {
        return this.boundTxtField.isRedoable();
    }

    @Override
    public ReadOnlyBooleanProperty delegateRedoableProperty()
    {
        return this.boundTxtField.redoableProperty();
    }

    @Override
    public boolean delegateIsUndoable()
    {
        return this.boundTxtField.isUndoable();
    }

    @Override
    public ReadOnlyBooleanProperty delegateUndoableProperty()
    {
        return this.boundTxtField.undoableProperty();
    }

    @Override
    public boolean delegateIsFocused()
    {
        return this.boundTxtField.isFocused();
    }

    @Override
    public ReadOnlyBooleanProperty delegateFocusedProperty()
    {
        return this.boundTxtField.focusedProperty();
    }

    private class Position
    {
        public char mask;

        public char whatMask;

        public char placeholder;

        public Position(char mask, char whatMask, char placeholder)
        {
            this.mask = mask;
            this.placeholder = placeholder;
            this.whatMask = whatMask;
        }

        public boolean isPlainCharacter()
        {
            return whatMask == WHAT_MASK_CHAR;
        }

        public boolean isCorrect(char c)
        {
            switch (mask)
            {
                case MASK_DIGIT:
                    return Character.isDigit(c);
                case MASK_CHARACTER:
                    return Character.isLetter(c);
                case MASK_DIG_OR_CHAR:
                    return Character.isLetter(c) || Character.isDigit(c);
            }
            return false;
        }
    }

    public class BoundTxtField
        extends BoundTextField
    {
        public BoundTxtField(MFXTextField textField)
        {
            super(textField);
        }

        @Override
        public void replaceText(int start, int end, String text)
        {
            int plainStart = interpretMaskPositionInPlainPosition(start);
            int plainEnd   = interpretMaskPositionInPlainPosition(end);

            String plainText1 = "";
            if (getPlainText().length() > plainStart)
                plainText1 = getPlainText().substring(0, plainStart);
            else
                plainText1 = getPlainText();

            String plainText2 = "";
            if (getPlainText().length() > plainEnd)
                plainText2 = getPlainText().substring(plainEnd);
            else
                plainText2 = "";

            setPlainText(plainText1 + text + plainText2);
        }
    }
}
