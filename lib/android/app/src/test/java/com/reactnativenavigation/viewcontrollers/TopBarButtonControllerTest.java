package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.mocks.ImageLoaderMock;
import com.reactnativenavigation.mocks.TitleBarReactViewCreatorMock;
import com.reactnativenavigation.mocks.TopBarBackgroundViewCreatorMock;
import com.reactnativenavigation.mocks.TopBarButtonCreatorMock;
import com.reactnativenavigation.parse.Options;
import com.reactnativenavigation.parse.params.Bool;
import com.reactnativenavigation.parse.params.Button;
import com.reactnativenavigation.parse.params.Color;
import com.reactnativenavigation.parse.params.NullText;
import com.reactnativenavigation.parse.params.Text;
import com.reactnativenavigation.utils.DrawableTinter;
import com.reactnativenavigation.viewcontrollers.topbar.TopBarBackgroundViewController;
import com.reactnativenavigation.viewcontrollers.topbar.TopBarController;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class TopBarButtonControllerTest extends BaseTest {

    private TopBarButtonController uut;
    private StackController stackController;
    private Button button;
    private DrawableTinter drawableTinter = spy(new DrawableTinter());

    @Override
    public void beforeEach() {
        button = new Button();
        final Activity activity = newActivity();

        TopBarButtonCreatorMock buttonCreatorMock = new TopBarButtonCreatorMock();
        uut = new TopBarButtonController(activity, ImageLoaderMock.mock(), drawableTinter, button, buttonCreatorMock, (buttonId) -> {});
        stackController = spy(new StackController(activity,
                buttonCreatorMock,
                new TitleBarReactViewCreatorMock(),
                new TopBarBackgroundViewController(activity, new TopBarBackgroundViewCreatorMock()),
                new TopBarController(),
                "stack",
                new Options())
        );
        stackController.getView().layout(0, 0, 1080, 1920);
        stackController.getTopBar().layout(0, 0, 1080, 200);
        stackController.getTopBar().getTitleBar().layout(0, 0, 1080, 200);
    }

    @Test
    public void buttonDoesNotClearStackOptionsOnAppear() {
        setReactComponentButton();
        uut.ensureViewIsCreated();
        uut.onViewAppeared();
        verify(stackController, times(0)).clearOptions();
    }

    @Test
    public void setIconColor_enabled() {
        stackController.ensureViewIsCreated();

        setIconButton(true);
        Toolbar titleBar = stackController.getTopBar().getTitleBar();
        uut.addToMenu(titleBar, 0);

        assertThat(titleBar.getMenu().size()).isOne();
        verify(drawableTinter, times(1)).tint(any(), eq(android.graphics.Color.RED));
    }

    @Test
    public void setIconColor_disabled() {
        stackController.ensureViewIsCreated();

        setIconButton(false);
        uut.addToMenu(stackController.getTopBar().getTitleBar(), 0);

        verify(drawableTinter, times(1)).tint(any(), eq(android.graphics.Color.LTGRAY));
    }

    @Test
    public void setIconColor_disabledColor() {
        stackController.ensureViewIsCreated();

        setIconButton(false);
        button.disabledColor = new Color(android.graphics.Color.BLACK);
        uut.addToMenu(stackController.getTopBar().getTitleBar(), 0);

        verify(drawableTinter, times(1)).tint(any(), eq(android.graphics.Color.BLACK));
    }

    @Test
    public void disableIconTint() {
        stackController.ensureViewIsCreated();

        setIconButton(false);
        button.disableIconTint = new Bool(true);
        uut.addToMenu(stackController.getTopBar().getTitleBar(), 0);
        verifyZeroInteractions(drawableTinter);
    }

    private void setIconButton(boolean enabled) {
        button.id = "btn1";
        button.icon = new Text("someIcon");
        button.color = new Color(android.graphics.Color.RED);
        button.component.name = new NullText();
        button.component.componentId = new NullText();
        button.enabled = new Bool(enabled);
        button.showAsAction = MenuItem.SHOW_AS_ACTION_ALWAYS;
    }

    private void setReactComponentButton() {
        button.id = "btnId";
        button.component.name = new Text("com.example.customBtn");
        button.component.componentId = new Text("component666");
    }
}
