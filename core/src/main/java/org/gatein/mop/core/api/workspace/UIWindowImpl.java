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

import org.chromattic.api.annotations.NodeMapping;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.Create;
import org.gatein.mop.api.workspace.ui.UIWindow;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.WorkspaceCustomizationContext;
import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.api.content.Customization;
import org.gatein.mop.api.content.ContentType;
import org.gatein.mop.core.api.workspace.content.WorkspaceClone;
import org.gatein.mop.core.api.workspace.content.WorkspaceSpecialization;
import org.gatein.mop.core.api.workspace.content.WorkspaceCustomization;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@NodeMapping(name = "mop:uiwindow")
public abstract class UIWindowImpl extends UIComponentImpl implements UIWindow, CustomizationContext
{

   public ObjectType<? extends UIWindow> getObjectType()
   {
      return ObjectType.WINDOW;
   }

   // Abstract **********************************************************************************************************

   @OneToOne
   @MappedBy("customization")
   public abstract WorkspaceCustomization getCustomization();

   public abstract void setCustomization(WorkspaceCustomization customization);

   @Create
   public abstract WorkspaceClone create();

   @Create
   public abstract WorkspaceSpecialization create2();

   // UIWindow implementation *******************************************************************************************

   public <S> Customization<S> customize(ContentType<S> contentType, String contentId, S state)
   {
      if (getCustomization() != null)
      {
         throw new IllegalStateException("Already customized");
      }
      WorkspaceClone customization = create();
      setCustomization(customization);
      customization.setMimeType(contentType.getMimeType());
      customization.setContentId(contentId);
      customization.setState(state);
      return (Customization<S>)customization;
   }

   public <S> Customization<S> customize(Customization<S> customization)
   {
      if (customization == null)
      {
         throw new NullPointerException();
      }
      else if (customization instanceof WorkspaceCustomization)
      {
         if (getCustomization() != null)
         {
            throw new IllegalStateException("Already customized");
         }

         // Get parent customization
         WorkspaceCustomization parentCustomization = (WorkspaceCustomization)customization;

         // Create
         WorkspaceSpecialization specialization = create2();

         // Persist
         setCustomization(specialization);

         // Configuration
         specialization.setMimeType(parentCustomization.getMimeType());
         specialization.setContentId(parentCustomization.getContentId());

         // Create relationship
         specialization.setCustomization(parentCustomization);

         //
         return (Customization<S>)specialization;
      }
      else
      {
         throw new IllegalArgumentException("implement customization of " + customization);
      }
   }

   // WorkspaceCustomizationContext implementation **********************************************************************

   public String getContextType()
   {
      return WorkspaceCustomizationContext.TYPE;
   }

   public String getContextId()
   {
      return getObjectId();
   }

   public boolean contains(CustomizationContext that)
   {
      return contains(this, that);
   }
}
