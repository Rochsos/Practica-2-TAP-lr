package ufv.tap.ui.vista;

import java.util.ArrayList;
import java.util.List;

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
	ControladorListaTarea controladorListaTarea;

	public VistaTarea(ControladorTarea controlTarea, ControladorListaTarea controlLista) {
		
		this.controladorTarea = controlTarea;
		this.controladorListaTarea = controlLista;
		
		addClassName("list-view");
		setSizeFull();
		configureGrid();

		form = new TareaForm(controlLista.findAll());
		form.addListener(TareaForm.SaveEvent.class, this::saveTarea);
		form.addListener(TareaForm.DeleteEvent.class, this::deleteTarea);
		form.addListener(TareaForm.CloseEvent.class, e -> closeEditorTarea());
		
		formLista = new ListaForm();
		formLista.addListener(ListaForm.SaveEvent.class, this::saveLista);
		formLista.addListener(ListaForm.DeleteEvent.class, this::deleteLista);
		formLista.addListener(ListaForm.CloseEvent.class, e -> closeEditorLista());

		Div content = new Div(grid, form, formLista);
		content.addClassName("content");
		content.setSizeFull();

		add(getToolBar(), content);
		updateTarea();
		closeEditorTarea();
		closeEditorLista();
	}
	
	private void deleteLista(ListaForm.DeleteEvent evt) {
		controladorListaTarea.delete(evt.getLista());
		updateComboBox();
		closeEditorLista();
	}
	
	private void saveLista(ListaForm.SaveEvent evt) {
		controladorListaTarea.save(evt.getLista());
		updateComboBox();
		closeEditorLista();
	}

	private void deleteTarea(TareaForm.DeleteEvent evt) {
		controladorTarea.delete(evt.getTarea());
		updateTarea();
		closeEditorTarea();
	}

	private void saveTarea(TareaForm.SaveEvent evt) {
		controladorTarea.save(evt.getTarea());
		updateTarea();
		closeEditorTarea();
	}

	private HorizontalLayout getToolBar() {
		filterTarea.setLabel("Buscar tareas por nombre...");
		filterTarea.setClearButtonVisible(true);
		filterTarea.setValueChangeMode(ValueChangeMode.LAZY);
		filterTarea.addValueChangeListener(e -> updateTarea());

		Button addTareaButton = new Button("Añadir tarea", click -> {
			
			List<ListaTareas> listas = new ArrayList<>();
			listas.addAll(controladorListaTarea.findAll());
			
			if (listas.isEmpty())
				notification.open();
			else
				addTarea();
		});
		
		List<ListaTareas> listas = new ArrayList<>();
		filterLista.setItems(listas);
		filterLista.setItemLabelGenerator(ListaTareas::getNombre);
		filterLista.setClearButtonVisible(true);
		filterLista.addValueChangeListener(e -> updateList());
		
		Button addListaButton = new Button("Añadir lista", click -> addLista());

		HorizontalLayout buscadores = new HorizontalLayout(filterTarea, filterLista);
		HorizontalLayout botones = new HorizontalLayout(addListaButton, addTareaButton);
		
		botones.addClassName("botones");
		
		HorizontalLayout toolbar = new HorizontalLayout(buscadores, botones);
		
		toolbar.addClassName("toolbar");
		toolbar.setDefaultVerticalComponentAlignment(Alignment.END);
		return toolbar;
	}

	private void addLista() {
		grid.asSingleSelect().clear();
		editLista(new ListaTareas());
	}

	private void editLista(ListaTareas listaTareas) {
		if (listaTareas == null) {
			closeEditorLista();
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
			closeEditorTarea();
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

	private void closeEditorTarea() {
		form.setTarea(null);
		form.setVisible(false);
		removeClassName("editing");
	}
	
	private void closeEditorLista() {
		formLista.setLista(null);
		formLista.setVisible(false);
		removeClassName("editing");
	}

	private void updateTarea() {
		grid.setItems(controladorTarea.findAllTarea(filterTarea.getValue()));
	}
	
	private void updateList() {
		grid.setItems(controladorTarea.findAllLista(filterLista.getValue().toString()));
	}
	
	private void updateComboBox() {
		filterLista.setItems(controladorListaTarea.findAll());
		filterLista.setItemLabelGenerator(ListaTareas::getNombre);
	}
}
