package com.dynious.blex.gui.widget;

import java.util.List;
import com.dynious.blex.gui.IGuiParent;

public interface IGuiBlExWidgetBase extends IGuiParent
{
    public void setParent(IGuiParent parent);

    public IGuiParent getParent();

    void setSize(int w, int h);

    void setPos(int x, int y);

    void setVisible(boolean visible);

    boolean isVisible();

    public List<String> getTooltip(int mouseX, int mouseY);

    public void drawForeground(int mouseX, int mouseY);

    public void drawBackground(int mouseX, int mouseY);

    public void draw(int mouseX, int mouseY);

    public void mouseClicked(int mouseX, int mouseY, int type, boolean isShiftKeyDown);

    public boolean keyTyped(char c, int i);

    public void handleMouseInput();

    public void update();
}
