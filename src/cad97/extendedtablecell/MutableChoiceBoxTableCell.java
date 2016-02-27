package cad97.extendedtablecell;

import static cad97.extendedtablecell.TableCellExtension.getField;
import java.util.function.BiConsumer;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.util.StringConverter;

/**
 * A {@link ChoiceBoxTableCell} that exposes a mutator to edit the ChoiceBox
 * before it is shown to the user.
 *
 * @author Christopher
 * @param <S> The type of the TableView generic type (TableView&lt;S&gt;)
 * @param <T> The type of the elements contained within the TableColumn
 */
public class MutableChoiceBoxTableCell<S, T> extends ChoiceBoxTableCell<S, T> {

	private boolean mutated = false;
	private final BiConsumer<ChoiceBoxTableCell, ChoiceBox> mutator;

	/**
	 * Creates a default ChoiceBoxTableCell with an empty items list.
	 *
	 * @param mutator used to mutate ChoiceBox directly before it is shown to
	 * the user, using information about the cell.
	 */
	public MutableChoiceBoxTableCell(BiConsumer<ChoiceBoxTableCell, ChoiceBox> mutator) {
		super();
		this.mutator = mutator;
	}

	/**
	 * Creates a default {@link ChoiceBoxTableCell} instance with the given
	 * items being used to populate the {@link ChoiceBox} when it is shown.
	 *
	 * @param mutator used to mutate ChoiceBox directly before it is shown to
	 * the user, using information about the cell.
	 * @param items The items to show in the ChoiceBox popup menu when selected
	 * by the user.
	 */
	public MutableChoiceBoxTableCell(BiConsumer<ChoiceBoxTableCell, ChoiceBox> mutator, T... items) {
		super(items);
		this.mutator = mutator;
	}

	/**
	 * Creates a {@link ChoiceBoxTableCell} instance with the given items being
	 * used to populate the {@link ChoiceBox} when it is shown, and the
	 * {@link StringConverter} being used to convert the item in to a
	 * user-readable form.
	 *
	 * @param mutator used to mutate ChoiceBox directly before it is shown to
	 * the user, using information about the cell.
	 * @param converter A {@link StringConverter} that can convert an item of
	 * type T into a user-readable string so that it may then be shown in the
	 * ChoiceBox popup menu.
	 * @param items The items to show in the ChoiceBox popup menu when selected
	 * by the user.
	 */
	public MutableChoiceBoxTableCell(BiConsumer<ChoiceBoxTableCell, ChoiceBox> mutator, StringConverter<T> converter, T... items) {
		super(converter, items);
		this.mutator = mutator;
	}

	/**
	 * Creates a default {@link ChoiceBoxTableCell} instance with the given
	 * items being used to populate the {@link ChoiceBox} when it is shown.
	 *
	 * @param mutator used to mutate ChoiceBox directly before it is shown to
	 * the user, using information about the cell.
	 * @param items The items to show in the ChoiceBox popup menu when selected
	 * by the user.
	 */
	public MutableChoiceBoxTableCell(BiConsumer<ChoiceBoxTableCell, ChoiceBox> mutator, ObservableList<T> items) {
		super(items);
		this.mutator = mutator;
	}

	/**
	 * Creates a {@link ChoiceBoxTableCell} instance with the given items being
	 * used to populate the {@link ChoiceBox} when it is shown, and the
	 * {@link StringConverter} being used to convert the item in to a
	 * user-readable form.
	 *
	 * @param mutator used to mutate ChoiceBox directly before it is shown to
	 * the user, using information about the cell.
	 * @param converter A {@link StringConverter} that can convert an item of
	 * type T into a user-readable string so that it may then be shown in the
	 * ChoiceBox popup menu.
	 * @param items The items to show in the ChoiceBox popup menu when selected
	 * by the user.
	 */
	public MutableChoiceBoxTableCell(BiConsumer<ChoiceBoxTableCell, ChoiceBox> mutator, StringConverter<T> converter, ObservableList<T> items) {
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
			mutator.accept(this, getField(this, "choiceBox"));
			mutated = true;
		}
	}

}
