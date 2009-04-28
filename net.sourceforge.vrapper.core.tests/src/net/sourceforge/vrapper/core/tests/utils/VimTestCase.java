package net.sourceforge.vrapper.core.tests.utils;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import net.sourceforge.vrapper.keymap.KeyStroke;
import net.sourceforge.vrapper.platform.FileService;
import net.sourceforge.vrapper.platform.HistoryService;
import net.sourceforge.vrapper.platform.KeyMapProvider;
import net.sourceforge.vrapper.platform.Platform;
import net.sourceforge.vrapper.platform.ServiceProvider;
import net.sourceforge.vrapper.platform.UserInterfaceService;
import net.sourceforge.vrapper.platform.ViewportService;
import net.sourceforge.vrapper.utils.DefaultKeyMapProvider;
import net.sourceforge.vrapper.vim.DefaultEditorAdaptor;
import net.sourceforge.vrapper.vim.EditorAdaptor;
import net.sourceforge.vrapper.vim.modes.EditorMode;
import net.sourceforge.vrapper.vim.register.RegisterManager;
import net.sourceforge.vrapper.vim.register.SimpleRegister;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class VimTestCase {

    @Mock protected Platform platform;
    @Mock protected RegisterManager registerManager;
    @Mock protected ViewportService viewportService;
    @Mock protected UserInterfaceService userInterfaceService;
    @Mock protected FileService fileService;
    @Mock protected HistoryService historyService;
    @Mock protected ServiceProvider serviceProvider;
    protected TestTextContent content;
    protected TestCursorAndSelection cursorAndSelection;
    protected EditorAdaptor adaptor;
    protected EditorMode mode;
    protected SimpleRegister defaultRegister;
    protected KeyMapProvider keyMapProvider;

    public VimTestCase() {
        super();
    }

    public void initMocks() {
    	MockitoAnnotations.initMocks(this);
    	cursorAndSelection = new TestCursorAndSelection();
    	content = new TestTextContent(cursorAndSelection);
    	cursorAndSelection.setContent(content);
    	keyMapProvider = new DefaultKeyMapProvider();
    	when(platform.getCursorService()).thenReturn(cursorAndSelection);
    	when(platform.getSelectionService()).thenReturn(cursorAndSelection);
    	when(platform.getModelContent()).thenReturn(content);
    	when(platform.getViewContent()).thenReturn(content);
    	when(platform.getViewportService()).thenReturn(viewportService);
    	when(platform.getUserInterfaceService()).thenReturn(userInterfaceService);
    	when(platform.getFileService()).thenReturn(fileService);
    	when(platform.getHistoryService()).thenReturn(historyService);
    	when(platform.getKeyMapProvider()).thenReturn(keyMapProvider);
    	when(platform.getServiceProvider()).thenReturn(serviceProvider);
    	adaptor = spy(new DefaultEditorAdaptor(platform, registerManager));
    	defaultRegister = new SimpleRegister();
		when(registerManager.getActiveRegister()).thenReturn(defaultRegister);
		when(fileService.isEditable()).thenReturn(true);

    }

    @Before
    public void setUp() {
    	initMocks();
    }

    public void setBuffer(String text) {
        content.setText(text);
    }

    public String getBuffer() {
        return content.getText();
    }

    public void type(Iterable<KeyStroke> keyStrokes) {
        for (KeyStroke stroke: keyStrokes)
            adaptor.handleKey(stroke);
    }

}