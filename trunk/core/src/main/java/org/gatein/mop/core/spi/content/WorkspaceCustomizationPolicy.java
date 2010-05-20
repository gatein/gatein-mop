/**
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.gatein.mop.core.spi.content;

import org.gatein.mop.api.workspace.WorkspaceCustomizationContext;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class WorkspaceCustomizationPolicy
{

   public boolean contains(WorkspaceCustomizationContext c1, WorkspaceCustomizationContext c2)
   {
/*
    if (c1 instanceof Workspace) {
      if (c2 instanceof Workspace) {
        return c1 == c2;
      } else if (c2 instanceof Site) {
        Site s2 = (Site)c2;
        return c1 == s2.getWorkspace();
      } else if (c2 instanceof Page) {
        Page p2 = (Page)c2;
        return contains(c1, p2.getSite());
      } else if (c2 instanceof UIWindow) {
        UIWindow w2 = (UIWindow)c2;
        return contains(c1, w2.getPage());
      } else {
        throw new AssertionError();
      }
    } else if (c1 instanceof Site) {
      if (c2 instanceof Site) {
        return c1 == c2;
      } else if (c2 instanceof Page) {
        Page p2 = (Page)c2;
        return c1 == p2.getSite();
      } else if (c2 instanceof UIWindow) {
        UIWindow w2 = (UIWindow)c2;
        return contains(c1, w2.getPage());
      } else {
        throw new AssertionError();
      }
    } else if (c1 instanceof Page) {
      if (c2 instanceof Page) {
        return c1 == c2;
      } else if (c2 instanceof UIWindow) {
        UIWindow w2 = (UIWindow)c2;
        return c1 == w2.getPage();
      } else {
        throw new AssertionError();
      }
    } else if (c1 instanceof UIWindow) {
      if (c2 instanceof UIWindow) {
        return c1 == c2;
      } else {
        throw new AssertionError();
      }
    } else {
      throw new AssertionError();
    }
*/
      throw new UnsupportedOperationException();
   }
}
