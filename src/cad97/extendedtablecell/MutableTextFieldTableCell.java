package cad97.extendedtablecell;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

/**
 * A {@link TextFieldTableCell} that exposes a mutator to edit the TextField
 * before it is shown to the user.
 *
 * @author Christopher Durham aka CAD97
 * @param <S> The type of the TableView generic type (TableView&lt;S&gt;)
 * @param <T> The type of the elements contained within the TableColumn
 */
public class MutableTextFieldTableCell<S, T> extends TextFieldTableCell<S, T> {

	private boolean mutated = false;
	private final BiConsumer<TextFieldTableCell, TextField> mutator;

	/**
	 * Creates a default TextFieldTableCell with a null converter. Without a
	 * {@link StringConverter} specified, this cell will not be able to accept
	 * input from the TextField (as it will not know how to convert this back to
	 * the domain object). It is therefore strongly encouraged to not use this
	 * constructor unless you intend to set the converter separately.
	 *
	 * @param mutator used to mutate TextField directly before it is shown to
	 * the user.
	 */
	public MutableTextFieldTableCell(Consumer<TextField> mutator) {
		this((cell, field) -> mutator.accept(field));
	}

	/**
	 * Creates a default TextFieldTableCell with a null converter. Without a
	 * {@link StringConverter} specified, this cell will not be able to accept
	 * input from the TextField (as it will not know how to convert this back to
	 * the domain object). It is therefore strongly encouraged to not use this
	 * constructor unless you intend to set the converter separately.
	 *
	 * @param mutator used to mutate TextField directly before it is shown to
	 * the user, using information about the cell.
	 */
	public MutableTextFieldTableCell(BiConsumer<TextFieldTableCell, TextField> mutator) {
		super();
		this.mutator = mutator;
	}

	/**
	 * Creates a TextFieldTableCell that provides a {@link TextField} when put
	 * into editing mode that allows editing of the cell content. This method
	 * will work on any TableColumn instance, regardless of its generic type.
	 * However, to enable this, a {@link StringConverter} must be provided that
	 * will convert the given String (from what the user typed in) into an
	 * instance of type T. This item will then be passed along to the
	 * {@link TableColumn#onEditCommitProperty()} callback.
	 *
	 * @param mutator used to mutate TextField directly before it is shown to
	 * the user.
	 * @param converter A {@link StringConverter converter} that can convert the
	 * given String (from what the user typed in) into an instance of type T.
	 */
	public MutableTextFieldTableCell(Consumer<TextField> mutator, StringConverter<T> converter) {
		this((cell, field) -> mutator.accept(field), converter);
	}

	/**
	 * Creates a TextFieldTableCell that provides a {@link TextField} when put
	 * into editing mode that allows editing of the cell content. This method
	 * will work on any TableColumn instance, regardless of its generic type.
	 * However, to enable this, a {@link StringConverter} must be provided that
	 * will convert the given String (from what the user typed in) into an
	 * instance of type T. This item will then be passed along to the
	 * {@link TableColumn#onEditCommitProperty()} callback.
	 *
	 * @param mutator used to mutate TextField directly before it is shown to
	 * the user, using information about the cell.
	 * @param converter A {@link StringConverter converter} that can convert the
	 * given String (from what the user typed in) into an instance of type T.
	 */
	public MutableTextFieldTableCell(BiConsumer<TextFieldTableCell, TextField> mutator, StringConverter<T> converter) {
		super(converter);
		this.mutator = mutator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startEdit() {
		super.startEdit();
		if (!mutated && isEditing()) {
			mutator.accept(this, getTextField());
			mutated = true;
		}
	}

	private TextField getTextField() {
		try {
			Field f = TextFieldTableCell.class.getField("textField");
			f.setAccessible(true);
			return (TextField) f.get(this);
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(InjectedTextFieldTableCell.class.getName()).log(Level.SEVERE, String.format(Strings.NO_SUCH_FIELD, "textField", "TextFieldTableCell"), ex);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
			Logger.getLogger(InjectedTextFieldTableCell.class.getName()).log(Level.SEVERE, String.format(Strings.OTHER_EXCEPT, "textField", "TextFieldTableCell"), ex);
		}
		return null;
	}
}
