package cad97.extendedtablecell;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.StringConverter;

/**
 * A {@link ComboBoxTableCell} that exposes a mutator to edit the ComboBox
 * before it is shown to the user.
 *
 * @author Christopher Durham aka CAD97
 * @param <S> The type of the TableView generic type (TableView&lt;S&gt;)
 * @param <T> The type of the elements contained within the TableColumn
 */
public class MutableComboBoxTableCell<S, T> extends ComboBoxTableCell<S, T> {

	private boolean mutated = false;
	private final BiConsumer<ComboBoxTableCell, ComboBox> mutator;

	/**
	 * Creates a default ComboBoxTableCell with an empty items list.
	 *
	 * @param mutator used to mutate ComboBox directly before it is shown to the
	 * user.
	 */
	public MutableComboBoxTableCell(Consumer<ComboBox> mutator) {
		this((cell, box) -> mutator.accept(box));
	}

	/**
	 * Creates a default ComboBoxTableCell with an empty items list.
	 *
	 * @param mutator used to mutate ComboBox directly before it is shown to the
	 * user, using information about the cell.
	 */
	public MutableComboBoxTableCell(BiConsumer<ComboBoxTableCell, ComboBox> mutator) {
		super();
		this.mutator = mutator;
	}

	/**
	 * Creates a default {@link ComboBoxTableCell} instance with the given items
	 * being used to populate the {@link ComboBox} when it is shown.
	 *
	 * @param items The items to show in the ComboBox popup menu when selected
	 * by the user.
	 * @param mutator used to mutate ComboBox directly before it is shown to the
	 * user.
	 */
	public MutableComboBoxTableCell(Consumer<ComboBox> mutator, ObservableList<T> items) {
		this((cell, box) -> mutator.accept(box), items);
	}

	/**
	 * Creates a default {@link ComboBoxTableCell} instance with the given items
	 * being used to populate the {@link ComboBox} when it is shown.
	 *
	 * @param items The items to show in the ComboBox popup menu when selected
	 * by the user.
	 * @param mutator used to mutate ComboBox directly before it is shown to the
	 * user, using information about the cell.
	 */
	public MutableComboBoxTableCell(BiConsumer<ComboBoxTableCell, ComboBox> mutator, ObservableList<T> items) {
		super(items);
		this.mutator = mutator;
	}

	/**
	 * Creates a {@link ComboBoxTableCell} instance with the given items being
	 * used to populate the {@link ComboBox} when it is shown, and the
	 * {@link StringConverter} being used to convert the item in to a
	 * user-readable form.
	 *
	 * @param converter A {@link StringConverter} that can convert an item of
	 * type T into a user-readable string so that it may then be shown in the
	 * ComboBox popup menu.
	 * @param items The items to show in the ComboBox popup menu when selected
	 * by the user.
	 * @param mutator used to mutate ComboBox directly before it is shown to the
	 * user.
	 */
	public MutableComboBoxTableCell(Consumer<ComboBox> mutator, StringConverter<T> converter, ObservableList<T> items) {
		this((cell, box) -> mutator.accept(box), converter, items);
	}

	/**
	 * Creates a {@link ComboBoxTableCell} instance with the given items being
	 * used to populate the {@link ComboBox} when it is shown, and the
	 * {@link StringConverter} being used to convert the item in to a
	 * user-readable form.
	 *
	 * @param converter A {@link StringConverter} that can convert an item of
	 * type T into a user-readable string so that it may then be shown in the
	 * ComboBox popup menu.
	 * @param items The items to show in the ComboBox popup menu when selected
	 * by the user.
	 * @param mutator used to mutate ComboBox directly before it is shown to the
	 * user, using information about the cell.
	 */
	public MutableComboBoxTableCell(BiConsumer<ComboBoxTableCell, ComboBox> mutator, StringConverter<T> converter, ObservableList<T> items) {
		super(converter, items);
		this.mutator = mutator;
	}

	/**
	 * Creates a {@link ComboBoxTableCell} instance with the given items being
	 * used to populate the {@link ComboBox} when it is shown, and the
	 * {@link StringConverter} being used to convert the item in to a
	 * user-readable form.
	 *
	 * @param converter A {@link StringConverter} that can convert an item of
	 * type T into a user-readable string so that it may then be shown in the
	 * ComboBox popup menu.
	 * @param items The items to show in the ComboBox popup menu when selected
	 * by the user.
	 * @param mutator used to mutate ComboBox directly before it is shown to the
	 * user.
	 */
	public MutableComboBoxTableCell(Consumer<ComboBox> mutator, StringConverter<T> converter, T... items) {
		this((cell, box) -> mutator.accept(box), converter, items);
	}

	/**
	 * Creates a {@link ComboBoxTableCell} instance with the given items being
	 * used to populate the {@link ComboBox} when it is shown, and the
	 * {@link StringConverter} being used to convert the item in to a
	 * user-readable form.
	 *
	 * @param converter A {@link StringConverter} that can convert an item of
	 * type T into a user-readable string so that it may then be shown in the
	 * ComboBox popup menu.
	 * @param items The items to show in the ComboBox popup menu when selected
	 * by the user.
	 * @param mutator used to mutate ComboBox directly before it is shown to the
	 * user, using information about the cell.
	 */
	public MutableComboBoxTableCell(BiConsumer<ComboBoxTableCell, ComboBox> mutator, StringConverter<T> converter, T... items) {
		super(converter, items);
		this.mutator = mutator;
	}

	/**
	 * Creates a default {@link ComboBoxTableCell} instance with the given items
	 * being used to populate the {@link ComboBox} when it is shown.
	 *
	 * @param items The items to show in the ComboBox popup menu when selected
	 * by the user.
	 * @param mutator used to mutate ComboBox directly before it is shown to the
	 * user.
	 */
	public MutableComboBoxTableCell(Consumer<ComboBox> mutator, T... items) {
		this((cell, box) -> mutator.accept(box), items);
	}

	/**
	 * Creates a default {@link ComboBoxTableCell} instance with the given items
	 * being used to populate the {@link ComboBox} when it is shown.
	 *
	 * @param items The items to show in the ComboBox popup menu when selected
	 * by the user.
	 * @param mutator used to mutate ComboBox directly before it is shown to the
	 * user, using information about the cell.
	 */
	public MutableComboBoxTableCell(BiConsumer<ComboBoxTableCell, ComboBox> mutator, T... items) {
		super(items);
		this.mutator = mutator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startEdit() {
		super.startEdit();
		if (!mutated && isEditing()) {
			mutator.accept(this, getComboBox());
			mutated = true;
		}
	}

	private ComboBox getComboBox() {
		try {
			Field f = ComboBoxTableCell.class.getField("comboBox");
			f.setAccessible(true);
			return (ComboBox) f.get(this);
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(InjectedTextFieldTableCell.class.getName()).log(Level.SEVERE, String.format(Strings.NO_SUCH_FIELD, "comboBox", "ComboBoxTableCell"), ex);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
			Logger.getLogger(InjectedTextFieldTableCell.class.getName()).log(Level.SEVERE, String.format(Strings.OTHER_EXCEPT, "comboBox", "ComboBoxTableCell"), ex);
		}
		return null;
	}
}
