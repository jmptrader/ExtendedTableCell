package cad97.extendedtablecell;

import java.lang.reflect.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;

/**
 * A {@link TextFieldTableCell} that uses a custom defined TextField.
 * <p>
 * If you just want to use a default TextField but call some mutators on the
 * TextField, use {@link MutableTextFieldTableCell} instead.
 * <p>
 * Provided TextField will have its OnAction and OnKeyReleased set to those as
 * used by the default TextFieldTableCell.
 *
 * @author Christopher Durham aka CAD97
 * @param <S> The type of the TableView generic type (TableView&lt;S&gt;)
 * @param <T> The type of the elements contained within the TableColumn
 */
public class InjectedTextFieldTableCell<S, T> extends TextFieldTableCell<S, T> {

	/**
	 * Creates a TextFieldTableCell that uses the provided {@link TextField}
	 * when put into editing mode that allows editing of the cell content. This
	 * method will work on any TableColumn instance, regardless of its generic
	 * type. However, to enable this, a {@link StringConverter} must be provided
	 * that will convert the given String (from what the user typed in) into an
	 * instance of type T. This item will then be passed along to the
	 * {@link TableColumn#onEditCommitProperty()} callback.
	 *
	 * @param textField The TextField to be used
	 * @param converter A {@link StringConverter converter} that can convert the
	 * given String (from what the user typed in) into an instance of type T.
	 */
	public InjectedTextFieldTableCell(TextField textField, StringConverter<T> converter) {
		super(converter);
		// CellUtils#createTextField [[
		textField.setOnAction(event -> {
			if (converter == null) {
				throw new IllegalStateException(
						"Attempting to convert text input into Object, but provided "
						+ "StringConverter is null. Be sure to set a StringConverter "
						+ "in your cell factory.");
			}
			this.commitEdit(converter.fromString(textField.getText()));
			event.consume();
		});
		textField.setOnKeyReleased(t -> {
			if (t.getCode() == KeyCode.ESCAPE) {
				this.cancelEdit();
				t.consume();
			}
		});
		// ]]
		setTextField(textField);
	}

	private TextField setTextField(TextField textField) {
		try {
			Field f = TextFieldTableCell.class.getField("textField");
			f.setAccessible(true);
			f.set(this, textField);
			return (TextField) f.get(this);
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(InjectedTextFieldTableCell.class.getName()).log(Level.SEVERE, String.format(Strings.NO_SUCH_FIELD, "textField", "TextFieldTableCell"), ex);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
			Logger.getLogger(InjectedTextFieldTableCell.class.getName()).log(Level.SEVERE, String.format(Strings.OTHER_EXCEPT, "textField", "TextFieldTableCell"), ex);
		}
		return null;
	}
}
