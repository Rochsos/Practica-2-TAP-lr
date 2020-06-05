package ufv.tap.ui.vista;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import ufv.tap.backend.modelo.ListaTareas;
import ufv.tap.backend.modelo.Tarea;

public class ListaForm extends FormLayout{
	
	String nombreLista;
	List<ListaTareas> listas = new ArrayList<>();
	ListaTareas nuevaLista = new ListaTareas();

	TextField nombre = new TextField("Nombre de la lista");

	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button close = new Button("Cancel");

	Binder<ListaTareas> binder = new BeanValidationBinder<>(ListaTareas.class);

	public ListaForm() {
		addClassName("tarea-form");

		binder.bindInstanceFields(this);

		add(nombre, createButtonsLayout());
	}

	public void setLista(ListaTareas listaTareas) {
		binder.setBean(listaTareas);
	}

	private Component createButtonsLayout() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		save.addClickShortcut(Key.ENTER);
		close.addClickShortcut(Key.ESCAPE);

		save.addClickListener(click -> validateAndSave());
		delete.addClickListener(click -> {
			nuevaLista.setNombre(nombreLista);
			nuevaLista.setTareas(null);
			listas.remove(nuevaLista);
			fireEvent(new DeleteEvent(this, binder.getBean()));
		});
		close.addClickListener(click -> fireEvent(new CloseEvent(this)));

		binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

		return new HorizontalLayout(save, delete, close);
	}
	
	public String getNombreLista() {
		return nombreLista;
	}
	
	public List<ListaTareas> getListas() {
		return listas;
	}

	private void validateAndSave() {
		if (binder.isValid()) {
			nombreLista = nombre.getValue();
			nuevaLista.setNombre(nombreLista);
			nuevaLista.setTareas(null);
			listas.add(nuevaLista);
			fireEvent(new SaveEvent(this, binder.getBean()));
		}
	}

	// Events
	public static abstract class ListaFormEvent extends ComponentEvent<ListaForm> {
		private ListaTareas listaTareas;

		protected ListaFormEvent(ListaForm source, ListaTareas listaTareas) {
			super(source, false);
			this.listaTareas = listaTareas;
		}

		public ListaTareas getLista() {
			return listaTareas;
		}
	}

	public static class SaveEvent extends ListaFormEvent {
		SaveEvent(ListaForm source, ListaTareas listaTareas) {
			super(source, listaTareas);
		}
	}

	public static class DeleteEvent extends ListaFormEvent {
		DeleteEvent(ListaForm source, ListaTareas listaTareas) {
			super(source, listaTareas);
		}

	}

	public static class CloseEvent extends ListaFormEvent {
		CloseEvent(ListaForm source) {
			super(source, null);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {
		return getEventBus().addListener(eventType, listener);
	}
}
