package net.bootsfaces.component;

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import net.bootsfaces.C;
import net.bootsfaces.listeners.AddResourcesListener;
import net.bootsfaces.utils.BsfUtils;

@FacesComponent(C.FOCUS_COMPONENT_TYPE)
public class Focus extends UIComponentBase {
	public static final String COMPONENT_TYPE = C.FOCUS_COMPONENT_TYPE;
	public static final String COMPONENT_FAMILY = C.BSFCOMPONENT;

	private Map<String, Object> attributes;

	public Focus() {
		setRendererType(null); // this component renders itself
		AddResourcesListener.addResourceToHeadButAfterJQuery(C.BSF_LIBRARY, "jq/jquery.js");
	}

	@Override
	public Map<String, Object> getAttributes() {
		if (attributes == null)
			attributes = new AttributeMapWrapper(this, super.getAttributes());
		return attributes;
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		//	super.encodeBegin(context);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		if (!isRendered()) {
			return;
		}
		Map<String, Object> attrs = getAttributes();

		String target = (String)attrs.get("target");
		if(BsfUtils.StringIsValued(target)) {
			ResponseWriter rw = context.getResponseWriter();
			String itemToFocusID = BsfUtils.getComponentClientId(target);
		
			rw.startElement("script", this);
			
			rw.writeText("" +
						"$(document).ready(function() { " +
						"   var item = $(jq('" + itemToFocusID + "')); " +
						"   if (item.is('div')) { " +
 						"   	$(jq('input_" + itemToFocusID + "')).focus(); " +
						"   } else { item.focus(); }" +
						"});", null);
			rw.endElement("script");
		} else {
			throw new FacesException("The focus component needs a defined target ID", null);
		}
	}

}
