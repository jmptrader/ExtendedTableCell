package cad97.extendedtablecell;

import static cad97.extendedtablecell.TableCellExtension.getField;
import java.util.function.BiConsumer;
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
	 * user, using information about the cell.
	 */
	public MutableComboBoxTableCell(BiConsumer<ComboBoxTableCell, ComboBox> mutator, T... items) {
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
	 * user, using information about the cell.
	 */
	public MutableComboBoxTableCell(BiConsumer<ComboBoxTableCell, ComboBox> mutator, ObservableList<T> items) {
		super(items);
		this.mutator = mutator;
	}

	public MutableComboBoxTableCell(BiConsumer<ComboBoxTableCell, ComboBox> mutator, StringConverter<T> converter, ObservableList<T> items) {
		super(converter, items);
		this.mutator = mutator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startEdit() {
		super.startEdit();
		if (!mutated && isEditing()) {
			mutator.accept(this, getField(this, "comboBox"));
			mutated = true;
		}
	}
}
