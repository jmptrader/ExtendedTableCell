ExtendedTableCells is a small library designed to make it easier to lightly customize the default JavaFX TableCell implementations. It does so by exposing a way for you to inject the `Control` that is used to edit the TableCell, or by providing a `BiConsumer` callback that runs directly before the Control is shown to the user to modify it.

[You can see the JavaDocs here.](http://cad97.github.io/ExtendedTableCell/)

An example use case would be modifying the options in a ComboBox in a Table dependent on what the value of a different field of the backing data object is. This is accomplished like so:

```Java
tableColumn.setCellFactory(column -> {
	return new MutableComboBoxTableCell<>((cell, comboBox) -> {
		DataObject d = (DataObject) cell.getTableRow().getItem();
		comboBox.itemsProperty().bind(
				Bindings.when(d.yourField.isEqualTo(desiered)).then(listA).otherwise(listB)
		);
	});
});
```

<p xmlns:dct="http://purl.org/dc/terms/" xmlns:vcard="http://www.w3.org/2001/vcard-rdf/3.0#"> <a rel="license" href="http://creativecommons.org/publicdomain/zero/1.0/"> <img src="http://i.creativecommons.org/p/zero/1.0/88x31.png" style="border-style: none;" alt="CC0"/> </a> <br/> To the extent possible under law, <a rel="dct:publisher" href="http://cad97.com/"> <span property="dct:title">Christopher Durham</span></a> has waived all copyright and related or neighboring rights to <span property="dct:title">ExtendedTableCells</span>.</p>
