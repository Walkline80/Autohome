package com.walkline.util.ui;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class ListStyleButtonSet extends VerticalFieldManager
{
	int lastFocusIndex=0;

	public ListStyleButtonSet()
    {
        super (VERTICAL_SCROLL);
        setMargin(5, 5, 5, 5);
    }

	public void getFocus()
	{
		Field field = getLeafFieldWithFocus();

		if (field instanceof ListStyleButtonField)
		{
			lastFocusIndex = field.getIndex();
		}
	}

    protected void sublayout(int maxWidth, int maxHeight)
    {
        super.sublayout(maxWidth, maxHeight);

        int numChildren = this.getFieldCount();
        if (numChildren > 0)
        {
            if (numChildren == 1)
            {
                Field child = getField(0);
                if (child instanceof ListStyleButtonField)
                {
                    ((ListStyleButtonField) child).setDrawPosition(ListStyleButtonField.DRAWPOSITION_SINGLE);
                }
            } else {
                int index = 0;
                Field child = getField(index);

                if (child instanceof ListStyleButtonField)
                {
                    ((ListStyleButtonField) child).setDrawPosition(ListStyleButtonField.DRAWPOSITION_TOP);
                }

                for (index=1; index<numChildren-1; index++)
                {
                    child = getField(index);

                    if (child instanceof ListStyleButtonField)
                    {
                        ((ListStyleButtonField) child).setDrawPosition(ListStyleButtonField.DRAWPOSITION_MIDDLE);
                    }
                }

                child = getField(index);
                if (child instanceof ListStyleButtonField)
                {
                    ((ListStyleButtonField) child).setDrawPosition(ListStyleButtonField.DRAWPOSITION_BOTTOM);
                }
            }
        }
    }
}