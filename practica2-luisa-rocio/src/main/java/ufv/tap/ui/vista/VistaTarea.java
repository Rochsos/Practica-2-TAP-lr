package ufv.tap.ui.vista;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import ufv.tap.backend.controlador.ControladorListaTarea;
import ufv.tap.backend.controlador.ControladorTarea;
import ufv.tap.backend.modelo.ListaTareas;
import ufv.tap.backend.modelo.Tarea;
import ufv.tap.ui.MainLayout;

@Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("Tareas | Vaadin")
public class VistaTarea extends VerticalLayout {

	TareaForm form;
	ListaForm formLista;
	Grid<Tarea> grid = new Grid<>(Tarea.class);
	TextField filterTarea = new TextField();
	ComboBox<ListaTareas> filterLista = new ComboBox<>("Buscar tareas por lista...");
	
	Notification notification = new Notification("No has añadido una lista aún. Por favor, añadela para poder crear una tarea.", 5000, Position.MIDDLE);

	ControladorTarea controladorTarea;
	ListaTareas listaTareas = new ListaTareas();

	public VistaTarea(ControladorTarea controladorTarea, ControladorListaTarea controladorListaTarea) {
		
		this.controladorTarea = controladorTarea;
		addClassName("list-view");
		setSizeFull();
		configureGrid();

		form = new TareaForm(controladorListaTarea.findAll());
		form.addListener(TareaForm.SaveEvent.class, this::saveTarea);
		form.addListener(TareaForm.DeleteEvent.class, this::deleteTarea);
		form.addListener(TareaForm.CloseEvent.class, e -> closeEditor());

		Div content = new Div(grid, form, formLista);
		content.addClassName("content");
		content.setSizeFull();

		add(getToolBar(), content);
		updateList();
		closeEditor();
	}

	private void deleteTarea(TareaForm.DeleteEvent evt) {
		controladorTarea.delete(evt.getTarea());
		updateList();
		closeEditor();
	}

	private void saveTarea(TareaForm.SaveEvent evt) {
		controladorTarea.save(evt.getTarea());
		updateList();
		closeEditor();
	}

	private HorizontalLayout getToolBar() {
		filterTarea.setPlaceholder("Buscar tarea por nombre...");
		filterTarea.setClearButtonVisible(true);
		filterTarea.setValueChangeMode(ValueChangeMode.LAZY);
		filterTarea.addValueChangeListener(e -> updateList());

		Button addTareaButton = new Button("Añadir tarea", click -> {
			if (listaTareas.getTareas().isEmpty())
				notification.open();
			else
				addTarea();
		});
		
		Button addListaButton = new Button("Añadir lista", click -> addLista());

		HorizontalLayout toolbar = new HorizontalLayout(filterTarea, addTareaButton);
		toolbar.addClassName("toolbar");
		return toolbar;
	}

	private void addLista() {
		grid.asSingleSelect().clear();
		editLista(new ListaTareas());
	}

	private void editLista(ListaTareas listaTareas) {
		if (listaTareas == null) {
			closeEditor();
		} else {
			formLista.setLista(listaTareas);
			formLista.setVisible(true);
			addClassName("editing");
		}
	}

	private void addTarea() {
		grid.asSingleSelect().clear();
		editTarea(new Tarea());
	}
	
	private void editTarea(Tarea tarea) {
		if (tarea == null) {
			closeEditor();
		} else {
			form.setTarea(tarea);
			form.setVisible(true);
			addClassName("editing");
		}
	}

	private void configureGrid() {

		grid.addClassName("tarea-grid");
		grid.setSizeFull();
		grid.setColumns("nombre", "descripcion", "prioridad", "deadline", "estadoTarea", "listaTareas");

		grid.getColumns().forEach(col -> col.setAutoWidth(true));

		grid.asSingleSelect().addValueChangeListener(evt -> editTarea(evt.getValue()));
	}

	private void closeEditor() {
		form.setTarea(null);
		form.setVisible(false);
		removeClassName("editing");
	}

	private void updateList() {
		grid.setItems(controladorTarea.findAll(filterTarea.getValue()));
	}
}
