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
package org.gatein.mop.core.api.workspace;

import org.chromattic.api.annotations.PrimaryType;
import org.gatein.mop.api.workspace.ui.UIComponent;
import org.gatein.mop.api.workspace.ObjectType;
import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.Name;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "mop:uicomponent")
public abstract class UIComponentImpl extends WorkspaceObjectImpl implements UIComponent
{

   @ManyToOne
   public abstract UIContainerImpl getParentContainer();

   @ManyToOne
   public abstract PageImpl getParentPage();

   public abstract ObjectType<? extends UIComponent> getObjectType();

   public final UIContainerImpl getParent()
   {
      return getParentContainer();
   }

   public final PageImpl getPage()
   {
      UIContainerImpl container = getParentContainer();
      if (container != null)
      {
         return container.getPage();
      }
      else
      {
         return getParentPage();
      }
   }
}
