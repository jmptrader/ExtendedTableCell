package cad97.extendedtablecell;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;

/**
 * Methods static class for TableCell injection.
 *
 * @author Christopher
 */
public final class TableCellExtension {

	private TableCellExtension() {
		throw new UnsupportedOperationException("TableCellExtension is a utility class and should not be instantiated.");
	}

	/**
	 * Injects a {@link javafx.scene.control.Control} into the corresponding
	 * TableCell. The Node will be configured as it would be when created by the
	 * TableCell.
	 * <p>
	 * Make sure to set the cell&rsquo;s converterProperty, or you won't be able
	 * to set/get the value correctly!
	 * <p>
	 * The TextField has its OnAction and OnKeyReleased set to the default
	 * behavior for the TableCell.
	 * <p>
	 * Unless you are using a custom extention to TextField, you should probably
	 * use {@link MutableTextFieldTableCell} instead and modify the default
	 * TextField in that mutator.
	 *
	 * @param textField the TextField to inject
	 * @param cell the TableCell to be injected
	 * @return The injected TableCell
	 */
	public static TableCell injectTableCell(TextField textField, TextFieldTableCell cell) {
		// CellUtils#createTextField [[
		textField.setOnAction(event -> {
			if (cell.getConverter() == null) {
				throw new IllegalStateException(
						"Attempting to convert text input into Object, but provided "
						+ "StringConverter is null. Be sure to set a StringConverter "
						+ "in your cell factory.");
			}
			cell.commitEdit(cell.getConverter().fromString(textField.getText()));
			event.consume();
		});
		textField.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				cell.cancelEdit();
				event.consume();
			}
		});
		// ]]
		setField(cell, "textField", textField);
		return cell;
	}

	/**
	 * Injects a {@link javafx.scene.control.Control} into the corresponding
	 * TableCell. The Node will be configured as it would be when created by the
	 * TableCell.
	 * <p>
	 * Make sure to set the cell&rsquo;s converterProperty, or you won't be able
	 * to set/get the value correctly!
	 * <p>
	 * The ComboBox has its converterProperty and maxWidth set to the default
	 * behavior for the TableCell.
	 * <p>
	 * Unless you are using a custom extention to ComboBox, you should probably
	 * use {@link MutableComboBoxTableCell} instead and modify the default
	 * ComboBox in that mutator.
	 *
	 * @param comboBox the ComboBox to inject
	 * @param cell the TableCell to be injected
	 * @return The injected TableCell
	 */
	public static TableCell injectTableCell(ComboBox comboBox, ComboBoxTableCell cell) {
		// CellUtils#createComboBox [[
		comboBox.converterProperty().bind(cell.converterProperty());
		comboBox.setMaxWidth(Double.MAX_VALUE);
		comboBox.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
			if (cell.isEditing()) {
				cell.commitEdit(newValue);
			}
		});
		// ]]
		setField(cell, "comboBox", comboBox);
		return cell;
	}
	
	/**
	 * Reflectively sets a field.
	 * 
	 * @param obj Object to set the field on
	 * @param name Name of the field as declared
	 * @param value Value to set the field to
	 */
	static void setField(Object obj, String name, Object value) {
		try {
			Field f = obj.getClass().getDeclaredField(name);
			f.setAccessible(true);
			f.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
			logSetException(ex, obj.getClass().getSimpleName(), name, value.getClass().getName());
		}
	}

	/**
	 * Reflectively gets the value of the field.
	 * 
	 * @param <T> type of the Field
	 * @param obj object to get the field from
	 * @param name name of the field as declared
	 * @return The value of the field
	 */
	static <T> T getField(Object obj, String name) {
		try {
			Field f = obj.getClass().getDeclaredField(name);
			f.setAccessible(true);
			return (T) f.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
			logGetException(ex, obj.getClass().getSimpleName(), name);
			return null;
		}
	}

	private static void logSetException(Exception ex, String clazz, String field, String value) {
		String format;
		if (ex instanceof IllegalArgumentException) {
			format = "Given value type %3$s is not the type of %1$s#%2$s.";
		} else if (ex instanceof IllegalAccessException) {
			format = "Access to field %1$s#%2$s is Illegal.";
		} else if (ex instanceof NoSuchFieldException) {
			format = "Field %2$s does not exist in %1$s.";
		} else if (ex instanceof SecurityException) {
			format = "Acces to field %1$s#%2$s breaks Security.";
		} else {
			format = "Unexpeted Exception";
		}
		Logger.getLogger(TableCellExtension.class.getName()).log(Level.SEVERE, String.format(format, clazz, field, value), ex);
	}

	private static void logGetException(Exception ex, String clazz, String field) {
		String format;
		if (ex instanceof IllegalArgumentException) {
			format = "This should be impossible.";
		} else if (ex instanceof IllegalAccessException) {
			format = "Access to field %1$s#%2$s is Illegal.";
		} else if (ex instanceof NoSuchFieldException) {
			format = "Field %2$s does not exist in %1$s.";
		} else if (ex instanceof SecurityException) {
			format = "Acces to field %1$s#%2$s breaks Security.";
		} else {
			format = "Unexpeted Exception";
		}
		Logger.getLogger(TableCellExtension.class.getName()).log(Level.SEVERE, String.format(format, clazz, field), ex);
	}
}
