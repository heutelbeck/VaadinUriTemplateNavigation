== URI Templates Navigation in Vaadin 8 + Spring Boot

View code example:

```
@SpringView(name = CoreView.TEMPLATE)
public class CoreView extends VerticalLayout implements View {
	SpringNavigator nav;

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(CoreView.class);

	public static final String TEMPLATE = "apples/{appleId}/cores/{coreId}";
	public static final UriTemplate URI_TEMPLATE = new UriTemplate(TEMPLATE);

	Label appleId = new Label();
	Label coreId = new Label();

	public static String getViewName(String appleId, String coreId) {
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("appleId", appleId);
		vars.put("coreId", coreId);
		return URI_TEMPLATE.expand(vars).toString();
	}

	@PostConstruct
	void init() {
		addComponent(new Label("This views shows an individual core in a specific apple  Fragment: '"
				+ UI.getCurrent().getPage().getUriFragment() + "'"));
		addComponent(appleId);
		appleId.setCaption("Apple ID:");
		addComponent(coreId);
		coreId.setCaption("Core ID:");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Map<String, String> map = URI_TEMPLATE.match(event.getViewName());
		for (Entry<String, String> entry : map.entrySet()) {
			LOGGER.info("key = '{}' value='{}'", entry.getKey(), entry.getValue());
		}
		appleId.setValue(map.get("appleId"));
		coreId.setValue(map.get("coreId"));
		LOGGER.info("this: {}", this);
	}

```

Configuration:
```
@SpringBootApplication
public class ApplesApplication {

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(ApplesApplication.class, args);
	}

	@Bean
	@Primary
	@UIScope
	SpringViewProvider templateViewProvider() {
		return new UriTemplateViewProvider(applicationContext, null);
	}
}
```

Example usage of the navigator:
```
    [...]
	navigationBar.addComponent(createNavigationButton("Show one core of apple", CoreView.getViewName("d321", "c5762")));
    [...]
    private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}
```
