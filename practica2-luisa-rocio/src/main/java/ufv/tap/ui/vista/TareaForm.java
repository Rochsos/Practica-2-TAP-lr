package ufv.tap.ui.vista;

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

public class TareaForm extends FormLayout {
	
	ListaForm listaForm = new ListaForm();
	
	TextField nombre = new TextField("Nombre de la tarea");
	TextField descripcion = new TextField("Descripcion");
	ComboBox<Tarea.Prioridad> prioridad = new ComboBox<>("Prioridad");
	DatePicker deadline = new DatePicker("Fecha límite");
	ComboBox<Tarea.Estado> estadoTarea = new ComboBox<>("Estado");
	ComboBox<ListaTareas> listaTareas = new ComboBox<>("Lista a la que pertenece");

	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button close = new Button("Cancel");

	Binder<Tarea> binder = new BeanValidationBinder<>(Tarea.class);

	public TareaForm(List<ListaTareas> listas) {
		addClassName("tarea-form");
		
		listas = listaForm.getListas();
		
		binder.bindInstanceFields(this);
		prioridad.setItems(Tarea.Prioridad.values());
		estadoTarea.setItems(Tarea.Estado.values());
		listaTareas.setItems(listas);
		listaTareas.setItemLabelGenerator(ListaTareas::getNombre);

		add(nombre, descripcion, prioridad, deadline, estadoTarea, listaTareas, createButtonsLayout());
	}

	public void setTarea(Tarea tarea) {
		binder.setBean(tarea);
	}

	private Component createButtonsLayout() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		save.addClickShortcut(Key.ENTER);
		close.addClickShortcut(Key.ESCAPE);

		save.addClickListener(click -> validateAndSave());
		delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
		close.addClickListener(click -> fireEvent(new CloseEvent(this)));

		binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

		return new HorizontalLayout(save, delete, close);
	}

	private void validateAndSave() {
		if (binder.isValid()) {
			fireEvent(new SaveEvent(this, binder.getBean()));
		}
	}

	// Events
	public static abstract class TareaFormEvent extends ComponentEvent<TareaForm> {
		private Tarea tarea;

		protected TareaFormEvent(TareaForm source, Tarea tarea) {
			super(source, false);
			this.tarea = tarea;
		}

		public Tarea getTarea() {
			return tarea;
		}
	}

	public static class SaveEvent extends TareaFormEvent {
		SaveEvent(TareaForm source, Tarea tarea) {
			super(source, tarea);
		}
	}

	public static class DeleteEvent extends TareaFormEvent {
		DeleteEvent(TareaForm source, Tarea tarea) {
			super(source, tarea);
		}

	}

	public static class CloseEvent extends TareaFormEvent {
		CloseEvent(TareaForm source) {
			super(source, null);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {
		return getEventBus().addListener(eventType, listener);
	}
}
