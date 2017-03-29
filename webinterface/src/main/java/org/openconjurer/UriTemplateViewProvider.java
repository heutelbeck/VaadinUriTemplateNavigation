package org.openconjurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.web.util.UriTemplate;

import com.vaadin.spring.navigator.SpringViewProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UriTemplateViewProvider extends SpringViewProvider {

	private static final long serialVersionUID = -2310440004073323751L;

	static class ViewInfoComparator implements Comparator<ViewInfo> {
		@Override
		public int compare(ViewInfo o1, ViewInfo o2) {
			int numOfSegments1 = o1.getViewName().split("/").length;
			int numOfSegments2 = o2.getViewName().split("/").length;
			return Integer.signum(numOfSegments2 - numOfSegments1);
		}
	}

	static class ViewNameComparator implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			int numOfSegments1 = o1.split("/").length;
			int numOfSegments2 = o2.split("/").length;
			return Integer.signum(numOfSegments2 - numOfSegments1);
		}
	}
	
	public UriTemplateViewProvider(ApplicationContext applicationContext,
			BeanDefinitionRegistry beanDefinitionRegistry) {
		super(applicationContext, beanDefinitionRegistry);
	}

	@Override
	protected String getViewName(String viewAndParameters, List<ViewInfo> views) {

//		// check all the vies and show if they match .. just testing delete this later
//		for (ViewInfo view : views) {
//			if (!view.getViewName().isEmpty())
//				log.info("'{}' - '{}' - '{}' for '{}'", view.getBeanName(), view.getViewName(),
//						new UriTemplate(view.getViewName()).matches(viewAndParameters), viewAndParameters);
//		}

		// look for exact matches
		for (ViewInfo view : views) {
			if (view.getViewName().equals(viewAndParameters)) {
				log.info("getViewName exact match - [{}] is a valid view", view.getViewName());
				return view.getViewName();
			}
		}

		// sort the names with regards to the number of path segments in
		// the string, so that longer ones take priority over the shorter ones
		// when matching templates

		Collections.sort(views, new ViewInfoComparator());

		// perform template matching

		for (ViewInfo view : views) {
			if (!view.getViewName().isEmpty() && new UriTemplate(view.getViewName()).matches(viewAndParameters)) {
				log.info("getViewName template match - [{}] is a valid view, match with {}", view.getViewName(), viewAndParameters);
				return viewAndParameters;//view.getViewName();
			}
		}
		
		// then look for prefix matches
		int lastSlash = -1;
		String viewPart = viewAndParameters;
		while ((lastSlash = viewPart.lastIndexOf('/')) > -1) {
			viewPart = viewPart.substring(0, lastSlash);
			log.info("getViewName Checking if [{}] is a valid view", viewPart);
			for (ViewInfo view : views) {
				log.info("viewName: {}", view.getViewName());
				if (view.getViewName().equals(viewPart)) {
					log.info("getViewName prefix match - [{}] is a valid view", view.getViewName());
					return view.getViewName();
				} 
			}
		}
		return null;
	}
	
	@Override
    protected List<ViewInfo> getAllowedViewsForCurrentUI(String viewName) {
        List<ViewInfo> views = new ArrayList<ViewInfo>();
        Set<String> allViews = getViewNameToBeanNamesMap().get(viewName);
        if (allViews == null) {
        	// there is no direct match. so all have to be inspected for template matches
        	Set<String> viewNamesSet = getViewNameToBeanNamesMap().keySet();
        	List<String> viewNames = new ArrayList<>(viewNamesSet);

    		// sort the names with regards to the number of path segments in
    		// the string, so that longer ones take priority over the shorter ones
    		// when matching templates

    		Collections.sort(viewNames, new ViewNameComparator());
    		
    		// perform template matching
    		for (String name : viewNames) {
    			if (!name.isEmpty() && new UriTemplate(name).matches(viewName)) {
    				log.info("getAllowedViewsForCurrentUI template match - [{}] is a valid view, match with {}", name, viewName);
   					allViews = new HashSet<>();
    				// add the matching bean names
    				allViews.addAll(getViewNameToBeanNamesMap().get(name));
    				break;
    			}
    		}
        }
        
        if (allViews != null) {
            for (String beanName : allViews) {
                ViewInfo viewInfo = new ViewInfo(viewName, beanName);
                if (isViewValidForCurrentUI(viewInfo)
                        && isAccessGranted(viewInfo)) {
                    views.add(viewInfo);
                }
            }
        }
        return views;
    }
    
}
