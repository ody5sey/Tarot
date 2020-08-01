package burp;

/*
 * @(#)IContextMenuFactory.java
 *
 * Copyright PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

import javax.swing.*;
import java.util.List;

/**
 * Extensions can implement this interface and then call
 * <code>IBurpExtenderCallbacks.registerContextMenuFactory()</code> to register
 * a factory for custom context menu items.
 */
public interface IContextMenuFactory
{
    /**
     * This method will be called by Burp when the user invokes a context menu
     * anywhere within Burp. The factory can then provide any custom context
     * menu items that should be displayed in the context menu, based on the
     * details of the menu invocation.
     *
     * @param invocation An object that implements the
     * <code>IContextMenuInvocation</code> interface, which the extension can
     * query to obtain details of the context menu invocation.
     * @return
     */
    List<JMenuItem> createMenuItems(IContextMenuInvocation invocation);
}
