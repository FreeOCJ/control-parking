package pe.cp.web;

import pe.cp.MyVaadinUI;
import pe.cp.core.Inject;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class AppUIProvider extends UIProvider {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public Class getUIClass(UIClassSelectionEvent event) {
        return MyVaadinUI.class;
    }
	 
    @Override
    public UI createInstance(UICreateEvent event) {
        UI instance = super.createInstance(event);
        Inject.inject(instance);
        return instance;
    }	
}
